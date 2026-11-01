"""Reproduce lossless exports from the supplied JAR and CFR output. Python 3, stdlib only.
See docs/MAP_FORMAT.md. Never changes the JAR or raw extracted entries.
"""
from pathlib import Path
import zipfile, struct, hashlib, json, re, collections
ROOT = Path(__file__).resolve().parents[1]
def write(path, value):
    p=ROOT/path; p.parent.mkdir(parents=True,exist_ok=True); p.write_text(json.dumps(value,separators=(',',':'),ensure_ascii=False)+'\n')
def assignments(source, pattern):
    return {int(i):int(v) for i,v in re.findall(pattern,source)}
def main():
    jars=sorted(ROOT.glob('*.jar'))
    if len(jars)!=1: raise ValueError('Expected exactly one source JAR in workspace root')
    z=zipfile.ZipFile(jars[0]); inventory=[]
    for entry in z.infolist():
        if entry.is_dir(): continue
        p=Path(entry.filename)
        if p.is_absolute() or '..' in p.parts: raise ValueError('Unsafe archive member')
        raw=z.read(entry); out=ROOT/'evidence/original'/p;out.parent.mkdir(parents=True,exist_ok=True);out.write_bytes(raw)
        row={'path':entry.filename,'bytes':len(raw),'sha256':hashlib.sha256(raw).hexdigest()}
        if raw.startswith(b'\x89PNG'): row['size']=struct.unpack_from('>II',raw,16)
        inventory.append(row)
    write(Path('evidence/archive-inventory.json'),inventory)
    data=z.read('data/D0');counts=struct.unpack_from('<15H',data);offset=30; sheets=[]
    groups={0:'ui',1:'environment',2:'animals',3:'characters',4:'environment',5:'characters',6:'objects',7:'environment',8:'environment',9:'characters',10:'ui',11:'environment',12:'ui',13:'objects',14:'ui'}
    for bank,count in enumerate(counts):
        path=f'assets/{groups[bank]}/BS{bank}.png';p=ROOT/'public'/path;p.write_bytes(z.read(f'data/BS{bank}.png'))
        width,height=struct.unpack_from('>II',p.read_bytes(),16);frames=[]
        for frame in range(count):
            x,y,w,h,ax,ay=struct.unpack_from('<6h',data,offset); offset+=12
            flip=bool(w & 0x8000);w &=0x7fff;h &=0x7fff
            assert x>=0 and y>=0 and x+w<=width and y+h<=height,(bank,frame,x,y,w,h)
            frames.append({'x':x,'y':y,'width':w,'height':h,'anchorX':ax,'anchorY':ay,'flipX':flip})
        sheets.append({'bank':bank,'url':'/'+path,'width':width,'height':height,'frames':frames})
    assert offset==9366
    # ac.java: 4 banks, 5 locales, little-endian offsets, NUL-terminated unsigned LEB128 code points.
    textbanks=[]
    for bank in range(4):
        table=80049+27*bank;count=struct.unpack_from('<H',data,table)[0];locales=[]
        for locale in range(5):
            fileid,pos=struct.unpack_from('<BI',data,table+2+locale*5);assert fileid==0;strings=[]
            for _ in range(count):
                chars=[]
                while data[pos]:
                    value=0;shift=0
                    while True:
                        b=data[pos];pos+=1;value|=(b&127)<<shift;shift+=7
                        if not b&128:break
                    chars.append(chr(value))
                pos+=1;strings.append(''.join(chars))
            locales.append(strings)
        textbanks.append(locales)
    write(Path('evidence/localized-text.json'),{'languages':['en','fr','it','de','es'],'banks':textbanks})
    g=(ROOT/'evidence/decompiled/data/g.java').read_text();y=(ROOT/'evidence/decompiled/data/y.java').read_text();h=(ROOT/'evidence/decompiled/data/h.java').read_text()
    decor=assignments(g,r'this\.a\.p\[(\d+)\] = (\d+);');borders=assignments(g,r'this\.a\.o\[(\d+)\] = (\d+);');icons=assignments(y,r'this\.a\.f\[(\d+)\] = (-?\d+);')
    # h.java is straight-line composite draw code. Recover each constant offset relative to tile-center.
    composites={}
    for name,body in re.findall(r'public final void (\w+)\(int n2, int n3\) \{(.*?)\n    \}',h,re.S):
        commands=[]
        for line in body.splitlines():
            m=re.search(r'this\.a\.a\.a\(this\.a\.a\(\), (.*?), this\.a\.aM',line)
            if not m:continue
            ref=m[1];idx=re.fullmatch(r'this\.a\.p\[(\d+)\]',ref);key=decor[int(idx[1])] if idx else int(ref)
            parts=line.split('this.a.M / 2')
            assert len(parts)==3,line
            def delta(s):
                match=re.match(r'\s*([+-])\s*(\d+)',s);return (1 if match[1]=='+' else -1)*int(match[2]) if match else 0
            commands.append({'sprite':key,'dx':delta(parts[1]),'dy':delta(parts[2])})
        if commands:composites[name]=commands
    variants={18:list('abcdefghijklm'),1:list('nopqrstu'),5:list('vwxyzABCD'),13:list('EFGHIJKLMNOP'),21:list('QRST'),22:list('UVVV'),49:['W','X','Y','Z','aa','ab']}
    rows=[line.split(';') for line in z.read('objects.csv').decode().splitlines() if not line.startswith('@')];assert len(rows)==13 and all(len(row)==120 for row in rows)
    objects=[]
    for i,name in enumerate(textbanks[1][0]):
        def pairs(value):
            return [] if value=='-1' else [{'item':int(a),'quantity':int(b)} for a,b in (part.split('|') for part in value.split('#'))]
        recipe=pairs(rows[8][i])
        objects.append({'id':i,'mapId':i+1,'name':name,'description':textbanks[2][0][i] if i<60 else textbanks[3][0][i-60], 'sprite':icons.get(i,-1),'rawFields':[r[i] for r in rows],'recipe':recipe,'craftingTools':pairs(rows[9][i]),'category':int(rows[12][i]),'interaction':{'pickupTool':int(rows[0][i]),'useTool':int(rows[1][i]),'huntingTool':int(rows[2][i])},'effects':{name:int(rows[row][i]) for name,row in [('hunger',3),('thirst',4),('morale',5),('energy',6),('strength',7)]}})
    # y.d case 13 -> p.e byte[]; aa.c(StringBuffer) accepts -1 / 9 / 10 by global camp tier.
    for item in objects:
        item['shelterRequirement'] = int(rows[10][item['id']])
        item['clothingSlot'] = int(rows[11][item['id']])
    write(Path('public/assets/objects/objects.json'),objects)
    write(Path('public/assets/ui/atlas.json'),{'sheets':sheets,'decor':decor,'borders':borders,'icons':icons,'composites':composites,'variants':variants})
    write(Path('public/assets/ui/story.json'),textbanks[0][0])
    layers={};checksums={}
    for name in ['FULL','Borders','Rocks','Decor','Objects']:
        original=f'BIGisland{name}.byt';raw=z.read(original);assert len(raw)==180*180
        (ROOT/'public/assets/maps'/original).write_bytes(raw);layers[name]=list(raw);checksums[name]=hashlib.sha256(raw).hexdigest()
    write(Path('public/assets/maps/island.json'),{'version':1,'width':180,'height':180,'tileWidth':16,'tileHeight':16,'encoding':'unsigned bytes; row-major x + y * 180','layers':layers,'sha256':checksums})
    write(Path('evidence/objects-raw.json'),rows)
    from recover_wildlife import recover
    recover()
    from recover_coconut import recover as recover_coconut
    recover_coconut()
    from recover_fishing import recover as recover_fishing
    recover_fishing()
    from recover_rod_fishing import recover as recover_rod_fishing
    recover_rod_fishing()
    from recover_hunting import recover as recover_hunting
    recover_hunting()
    from recover_clothing import recover as recover_clothing
    recover_clothing()
    from recover_hazards import recover as recover_hazards
    recover_hazards()
    from recover_weight import recover as recover_weight
    recover_weight()
    print(f'Exported {len(inventory)} archive entries, {sum(counts)} exact sprite regions, 5 lossless layers, {len(composites)} composites, 120 objects, 5 languages.')
if __name__=='__main__':main()
