"""Optional visual QA. Requires Pillow. Uses the same recovered drawing metadata as Phaser."""
from PIL import Image
from pathlib import Path
import json
R=Path(__file__).resolve().parents[1];atlas=json.loads((R/'public/assets/ui/atlas.json').read_text());m=json.loads((R/'public/assets/maps/island.json').read_text());items=json.loads((R/'public/assets/objects/objects.json').read_text());images={s['bank']:Image.open(R/'public'/s['url'][1:]).convert('RGBA') for s in atlas['sheets']};out=Image.new('RGBA',(2880,2880),'#258eaa')
def draw(key,x,y):
 if key<0:return
 bank=key>>16;i=key&65535;f=atlas['sheets'][bank]['frames'][i];im=images[bank].crop((f['x'],f['y'],f['x']+f['width'],f['y']+f['height']))
 if f['flipX']:im=im.transpose(Image.Transpose.FLIP_LEFT_RIGHT)
 out.alpha_composite(im,(int(x-f['anchorX']),int(y-f['anchorY'])))
for i,v in enumerate(m['layers']['FULL']):draw(65536+v,(i%180)*16,(i//180)*16)
for i,v in enumerate(m['layers']['Rocks']):
 if 0<v<50:draw(262144+v-1,(i%180)*16,(i//180)*16)
for i,v in enumerate(m['layers']['Borders']):
 if v:draw(atlas['borders'][str(v-1)],(i%180)*16+8,(i//180)*16+8)
for i in range(32400):
 v=m['layers']['Decor'][i];x=(i%180)*16+8;y=(i//180)*16+8
 if v:
  draw(atlas['decor'][str(v)],x,y)
  if str(v) in atlas['variants']:
   variants=atlas['variants'][str(v)];name=variants[(i*17+v)%len(variants)]
   for c in atlas['composites'][name]:draw(c['sprite'],x+c['dx'],y+c['dy'])
 obj=m['layers']['Objects'][i]
 if obj:draw(items[obj-1]['sprite'],x,y)
out.convert('RGB').save(R/'evidence/island-reference.png');out.resize((540,540),Image.Resampling.NEAREST).save(R/'public/assets/maps/island-overview.png');out.crop((0,2160,640,2640)).resize((1280,960),Image.Resampling.NEAREST).save(R/'evidence/spawn-reference.png')
print('Rendered reference world and spawn region')
