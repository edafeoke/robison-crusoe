"""Extract g.e() animal constructors and frame arrays, without naming guessed regions."""
import json, re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]

def recover():
    source = (ROOT / 'evidence/decompiled/data/g.java').read_text()
    body = source[source.index('public final void e()'):source.index('private void o()')]
    items = json.loads((ROOT / 'public/assets/objects/objects.json').read_text())
    species = []
    for match in re.finditer(r'case (\d+): \{(.*?)(?=\n                case |\n            \})', body, re.S):
        map_id, block = int(match[1]), match[2]
        ctor = re.search(r'new ([ulqdi])\(this.a, this.a.l\[n19\], n19 % 180, n19 / 180, (\d+), (?:(\d+), (\d+), )?(.*?)\);', block)
        if not ctor:
            continue
        arrays = {}
        for name, index, value in re.findall(r'(nArray\d*)\[(\d+)\] = (\d+);', block):
            arrays.setdefault(name, {})[int(index)] = int(value)
        for name, values in re.findall(r'int\[\] (nArray\d*) = new int\[\]\{([^}]+)\};', block):
            arrays[name] = dict(enumerate(map(int, values.split(','))))
        args = [arrays[name.strip()] for name in ctor[5].split(',')]
        sequences = [[a[i] for i in range(len(a))] for a in args]
        family = {'u':'bird','l':'passive','q':'prey','d':'predator','i':'shark'}[ctor[1]]
        keys = ['left', 'right', 'flyLeft', 'flyRight', 'idle'] if family == 'bird' else ['left','right','idle']
        species.append({'mapId':map_id,'name':items[map_id-1]['name'],'sourceClass':ctor[1],
                        'family':family,'radius':None if family=='shark' else int(ctor[2]),
                        'speedTier':int(ctor[3] or ctor[2]),'restBase':int(ctor[4] or 0),
                        'frames':dict(zip(keys, sequences))})
    assert len(species) == 16 and len({s['mapId'] for s in species}) == 16
    output = {'version':1,'source':'data.g.e; data.u/l/q/d/i; data.p.b(11)',
              'tickMs':1000//11,'species':species}
    path = ROOT / 'public/assets/animals/wildlife.json'
    path.write_text(json.dumps(output,separators=(',',':'))+'\n')
    print(f'Exported {len(species)} source-verified wildlife profiles.')

if __name__ == '__main__': recover()
