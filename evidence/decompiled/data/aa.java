/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Font
 */
package data;

import data.ac;
import data.k;
import data.p;
import java.util.Date;
import javax.microedition.lcdui.Font;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class aa {
    private p a;
    public int a;
    private int J;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h = 0;
    public int i = 0;
    public int j = 0;
    private int K = 0;
    private StringBuffer e;
    private StringBuffer f;
    public StringBuffer a;
    public int k = 0;
    public int l = Font.getFont((int)0, (int)1, (int)8).charWidth('a');
    public boolean a;
    public StringBuffer b;
    public StringBuffer c;
    public int m = 0;
    private boolean b;
    private boolean c;
    private boolean d;
    private Date a;
    private long a;
    private long b = 0L;
    private long c = 0L;
    private long d;
    private long e;
    private long f;
    private long g = 0L;
    private boolean e = 0L;
    private StringBuffer[] a;
    private int[] a;
    private int L;
    public int n;
    public int o;
    public int p;
    public int q;
    public int r;
    public int s;
    public int t;
    private boolean f = 0L;
    public int u;
    public int v;
    public int w;
    public int x;
    public int y;
    public int z;
    public int A;
    public int B;
    public int C;
    public int D;
    public int E;
    public int F;
    public int G;
    public StringBuffer d = 0L;
    public String a = 0L;
    public int H;
    public int I;

    public aa(p p2) {
        new StringBuffer().append("Collisions");
        new StringBuffer().append("Rocks");
        new StringBuffer().append("Borders");
        new StringBuffer().append("Decors");
        new StringBuffer().append("Objects");
        new StringBuffer().append("Tile Pos");
        new StringBuffer().append("Grid Display");
        new StringBuffer().append("Speed Up");
        new StringBuffer().append("FPS");
        new StringBuffer().append("Immortal");
        new StringBuffer().append("RobSpeed");
        new StringBuffer().append("MiniGame");
        new StringBuffer().append("on");
        new StringBuffer().append("off");
        this.e = true;
        this.a = new StringBuffer[6];
        this.a = new int[6];
        this.L = 0;
        this.n = 0;
        this.o = 0;
        this.p = 0;
        this.q = 0;
        this.r = 0;
        this.s = 0;
        this.t = 0;
        this.f = true;
        this.u = 0;
        this.v = 0;
        this.w = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.A = 0;
        this.B = 0;
        this.C = 0;
        this.D = 0;
        this.E = 0;
        this.F = 0;
        this.G = 0;
        this.a = new String("");
        this.H = 0;
        this.I = 0;
        this.a = p2;
        if (data.k.b() < 160) {
            this.b = 655360;
            this.c = 655361;
            this.d = 655362;
            this.a = 26;
            this.J = -3;
            this.e = 7;
            this.f = 15;
            this.g = 7;
            this.h = 7;
            return;
        }
        if (data.k.b() < 205) {
            this.b = 655375;
            this.c = 655376;
            this.d = 655377;
            this.a = 26;
            this.J = -3;
            this.e = 7;
            this.f = 15;
            this.g = 7;
            this.h = 7;
            return;
        }
        if (data.k.b() < 290) {
            this.b = 655375;
            this.c = 655376;
            this.d = 655377;
            this.a = 28;
            this.J = -3;
            this.e = 0;
            this.f = 0;
            this.g = 0;
            this.h = 0;
            return;
        }
        if (data.k.b() < 321) {
            this.b = 655375;
            this.c = 655376;
            this.d = 655377;
            this.a = 28;
            this.J = -3;
            this.e = 0;
            this.f = 0;
            this.g = 0;
            this.h = 0;
            return;
        }
        this.b = 655375;
        this.c = 655376;
        this.d = 655377;
        this.a = 28;
        this.J = -3;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.h = 0;
    }

    public final void a() {
        if (this.a.aw) {
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
            this.a.aw = false;
            this.a.a.a(((k)this.a).a, 917504, data.k.a() / 2, 0);
            int n2 = 1;
            while (n2 < this.a.b + 1) {
                this.a.a.a(((k)this.a).a, this.b, data.k.a() / 6, data.k.b() / this.a.b + 1 + (this.a.aP + this.a.aL - this.a.aQ) * n2 - this.a.aR);
                int n3 = 1;
                while (n3 < this.a.aK + 1) {
                    this.a.a.a(((k)this.a).a, this.c, data.k.a() / 6 + 2 * n3, data.k.b() / this.a.b + 1 + (this.a.aP + this.a.aL - this.a.aQ) * n2 - this.a.aR);
                    ++n3;
                }
                this.a.a.a(((k)this.a).a, this.d, data.k.a() - data.k.a() / 6, data.k.b() / this.a.b + 1 + (this.a.aP + this.a.aL - this.a.aQ) * n2 - this.a.aR);
                this.L = n2 == this.a.r + 1 ? 0xFFFFFF : 39423;
                if (n2 == 1) {
                    if (!this.a.b) {
                        this.L = 0x444444;
                    }
                    this.a.a.a(((k)this.a).a, this.a.a.a(50), data.k.a() >> 1, data.k.b() / this.a.b + 1 + (this.a.aP + this.a.aL - this.a.aQ) * n2 - this.a.aR + (this.a.aP - this.a.aJ) / 2, this.L, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                }
                if (n2 == 2) {
                    this.a.a.a(((k)this.a).a, this.a.a.a(51), data.k.a() >> 1, data.k.b() / this.a.b + 1 + (this.a.aP + this.a.aL - this.a.aQ) * n2 - this.a.aR + (this.a.aP - this.a.aJ) / 2, this.L, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                }
                if (n2 == 3) {
                    this.a.a.a(((k)this.a).a, this.a.a.a(52), data.k.a() >> 1, data.k.b() / this.a.b + 1 + (this.a.aP + this.a.aL - this.a.aQ) * n2 - this.a.aR + (this.a.aP - this.a.aJ) / 2, this.L, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                }
                if (n2 == 4) {
                    this.a.a.a(((k)this.a).a, this.a.a.a(53), data.k.a() >> 1, data.k.b() / this.a.b + 1 + (this.a.aP + this.a.aL - this.a.aQ) * n2 - this.a.aR + (this.a.aP - this.a.aJ) / 2, this.L, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                }
                if (n2 == 5) {
                    this.a.a.a(((k)this.a).a, this.a.a.a(54), data.k.a() >> 1, data.k.b() / this.a.b + 1 + (this.a.aP + this.a.aL - this.a.aQ) * n2 - this.a.aR + (this.a.aP - this.a.aJ) / 2, this.L, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                }
                if (n2 == 6 && this.a.a) {
                    this.a.a.a(((k)this.a).a, this.a.a.a(111), data.k.a() >> 1, data.k.b() / this.a.b + 1 + (this.a.aP + this.a.aL - this.a.aQ) * n2 - this.a.aR + (this.a.aP - this.a.aJ) / 2, this.L, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                }
                ++n2;
            }
            if (this.a.hasPointerEvents()) {
                if (this.a.aW != 0) {
                    this.a.bl = 0;
                    this.a.bm = 20;
                } else {
                    this.a.bl = data.k.b() - 20;
                    this.a.bm = data.k.b();
                }
            }
            this.a.a.a(((k)this.a).a, this.a.a.a(55), this.a.bb, this.a.bc, 39423, 0x40 | this.a.ba | 0x100, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            this.a.a.a(((k)this.a).a, this.a.a.a(56), this.a.bd, this.a.be, 39423, 328, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
        }
    }

    public final void a(StringBuffer stringBuffer) {
        if (this.a.aw) {
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655375, 0, 0);
                int n2 = 1;
                while (n2 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655376, 2 * n2, 0);
                    ++n2;
                }
                this.a.a.a(((k)this.a).a, 655377, data.k.a() - 2, 0);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, (24 - this.a.aJ) / 2, 39423, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, 655360, 0, 0);
                int n3 = 1;
                while (n3 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n3, 0);
                    ++n3;
                }
                this.a.a.a(((k)this.a).a, 655362, data.k.a() - 2, 0);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, (16 - this.a.aJ) / 2, 39423, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            }
            this.K = this.a.A == 0 ? 0xFFFFFF : 39423;
            this.e = this.a.ah ? new StringBuffer().append((Object)this.a.a.a(47)) : new StringBuffer().append((Object)this.a.a.a(48));
            this.a.a.a(((k)this.a).a, this.e, data.k.a() >> 1, data.k.b() / 2 - this.a.aJ * 2, this.K, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            this.K = this.a.A == 1 ? 0xFFFFFF : 39423;
            this.f = new StringBuffer().append((Object)this.a.a.a(49));
            this.a.a.a(((k)this.a).a, this.f, data.k.a() >> 1, data.k.b() / 2 + this.a.aJ, this.K, 17, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            this.c();
        }
    }

    public final void a(int n2, int n3) {
        if (this.a.l) {
            this.a = this.a.a.a(n3);
            this.a.a.a(this.a, data.k.a() >> 1, 32, data.k.a(), data.k.b() - 32 - 20 - 5);
            this.a.l = false;
        }
        if (this.a.n) {
            this.a.n = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655375, 0, 0);
                int n4 = 1;
                while (n4 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655376, 2 * n4, 0);
                    ++n4;
                }
                this.a.a.a(((k)this.a).a, 655377, data.k.a() - 2, 0);
                this.a.a.a(((k)this.a).a, this.a.a.a(n2), data.k.a() >> 1, (24 - this.a.aJ) / 2, 39423, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, 655360, 0, 0);
                int n5 = 1;
                while (n5 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n5, 0);
                    ++n5;
                }
                this.a.a.a(((k)this.a).a, 655362, data.k.a() - 2, 0);
                this.a.a.a(((k)this.a).a, this.a.a.a(n2), data.k.a() >> 1, (16 - this.a.aJ) / 2, 39423, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            }
            ac.c(Integer.parseInt(this.a.b.elementAt(this.a.h).toString()));
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, this.a, data.k.a() >> 1, 40, 0xFFFFFF, 1041, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b() - 32 - 20 - 5, 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, this.a, data.k.a() >> 1, 32, 0xFFFFFF, 1041, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b() - 32 - 20 - 5, 0, 0);
            }
            this.c();
        }
        if (this.a.m) {
            if (this.a.hasPointerEvents()) {
                this.a.a.a((data.k.a() >> 1) - 12, 29, data.k.b() - 24);
                return;
            }
            this.a.a.a((data.k.a() >> 1) - 8, 21, data.k.b() - 16);
        }
    }

    /*
     * Unable to fully structure code
     */
    public final void a(int var1_1) {
        block27: {
            if (this.a.hasPointerEvents()) {
                var2_2 = 29;
                var3_3 = data.k.b() - 24;
            } else {
                var2_2 = 21;
                var3_3 = data.k.b() - 16;
            }
            var4_4 = false;
            var5_5 = data.k.b() - 37 - 20;
            var6_6 = var5_5 / (this.a.aJ + 5);
            var7_7 = var2_2 + 8 + 5;
            var8_8 = 0;
            var9_9 = 0;
            var10_10 = 0;
            if (!this.a.l) break block27;
            this.a.a.addElement(new Integer(0));
            ** GOTO lbl28
            {
                var8_8 = this.a.a.a(null, this.a.a.a(33 + var10_10), 20, var7_7 + 5, this.k, 20, 0, 1, 8, this.a.aJ, data.k.a() - 40, data.k.b(), 0, 0);
                this.m = (var8_8 - var7_7) / this.a.aJ;
                var9_9 += this.m;
                var7_7 = var8_8;
                if (++var10_10 == this.a.e) ** GOTO lbl24
                do {
                    if (var9_9 < var6_6) continue block0;
lbl24:
                    // 2 sources

                    this.a.a.addElement(new Integer(var10_10));
                    --var10_10;
                    var9_9 = 0;
                    var7_7 = var2_2 + 8 + 5;
lbl28:
                    // 2 sources

                } while (var10_10 + 1 != this.a.e);
            }
            this.a.l = false;
            return;
        }
        if (this.a.aw) {
            this.a.aw = false;
            this.a.a.setColor(13158);
            this.a.a.fillRect(0, 0, data.k.a(), data.k.b());
            if (this.a.hasPointerEvents()) {
                this.a.a.a(this.a.a, 655375, 0, 0);
                var11_11 = 1;
                while (var11_11 < this.a.aV + 1) {
                    this.a.a.a(this.a.a, 655376, 2 * var11_11, 0);
                    ++var11_11;
                }
                this.a.a.a(this.a.a, 655377, data.k.a() - 2, 0);
                this.a.a.a(this.a.a, this.a.a.a(var1_1), data.k.a() >> 1, (24 - this.a.aJ) / 2, 39423, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            } else {
                this.a.a.a(this.a.a, 655360, 0, 0);
                var11_11 = 1;
                while (var11_11 < this.a.aV + 1) {
                    this.a.a.a(this.a.a, 655361, 2 * var11_11, 0);
                    ++var11_11;
                }
                this.a.a.a(this.a.a, 655362, data.k.a() - 2, 0);
                this.a.a.a(this.a.a, this.a.a.a(var1_1), data.k.a() >> 1, (16 - this.a.aJ) / 2, 39423, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            }
            var7_7 = var2_2 + 8 + 5;
            var11_11 = (Integer)this.a.a.elementAt(this.a.f);
            while (var11_11 < (Integer)this.a.a.elementAt(this.a.g)) {
                this.k = var11_11 == this.a.d ? 0xFFFFFF : 39423;
                var7_7 = var8_8 = this.a.a.a(this.a.a, this.a.a.a(33 + var11_11), 20, var7_7 + 5, this.k, 20, 0, 1, 8, this.a.aJ, data.k.a() - 40, data.k.b(), 0, 0);
                ++var11_11;
            }
            this.c();
        }
        if (this.a.e > (Integer)this.a.a.elementAt(1) && (this.a.n != 1 || this.a.m == 2)) {
            if (this.a.n > 500L) {
                if (this.a.h) {
                    this.a.h = false;
                }
                if (this.a.i) {
                    this.a.i = false;
                }
            }
            if (this.a.hasPointerEvents()) {
                if (this.a.h) {
                    this.a.a.a(this.a.a, 655419, (data.k.a() >> 1) - 12, var2_2);
                } else if (this.a.f > 0) {
                    this.a.a.a(this.a.a, 655417, (data.k.a() >> 1) - 12, var2_2);
                } else {
                    this.a.a.a(this.a.a, 655421, (data.k.a() >> 1) - 12, var2_2);
                }
                if (this.a.i) {
                    this.a.a.a(this.a.a, 655420, (data.k.a() >> 1) - 12, var3_3);
                    return;
                }
                if ((Integer)this.a.a.elementAt(this.a.g) < this.a.e) {
                    this.a.a.a(this.a.a, 655418, (data.k.a() >> 1) - 12, var3_3);
                    return;
                }
                this.a.a.a(this.a.a, 655422, (data.k.a() >> 1) - 12, var3_3);
                return;
            }
            if (this.a.h) {
                this.a.a.a(this.a.a, 655407, (data.k.a() >> 1) - 8, var2_2);
            } else if (this.a.f > 0) {
                this.a.a.a(this.a.a, 655405, (data.k.a() >> 1) - 8, var2_2);
            } else {
                this.a.a.a(this.a.a, 655409, (data.k.a() >> 1) - 8, var2_2);
            }
            if (this.a.i) {
                this.a.a.a(this.a.a, 655408, (data.k.a() >> 1) - 8, var3_3);
                return;
            }
            if ((Integer)this.a.a.elementAt(this.a.g) < this.a.e) {
                this.a.a.a(this.a.a, 655406, (data.k.a() >> 1) - 8, var3_3);
                return;
            }
            this.a.a.a(this.a.a, 655410, (data.k.a() >> 1) - 8, var3_3);
        }
    }

    public final void a(int n2, StringBuffer stringBuffer, int[] nArray, int n3, StringBuffer stringBuffer2) {
        if (this.a.l) {
            this.a = stringBuffer;
            if (nArray == null) {
                if (this.a.hasPointerEvents()) {
                    this.j = 29;
                    this.i = data.k.b() - 53 - 20;
                } else {
                    this.j = 21;
                    this.i = data.k.b() - 37 - 20;
                }
            } else if (stringBuffer2 == null) {
                if (this.a.hasPointerEvents()) {
                    this.j = 68;
                    this.i = data.k.b() - 92 - 20;
                } else {
                    this.j = 52;
                    this.i = data.k.b() - 68 - 20;
                }
            } else if (this.a.hasPointerEvents()) {
                this.j = 88;
                this.i = data.k.b() - 112 - 20;
            } else {
                this.j = 72;
                this.i = data.k.b() - 88 - 20;
            }
            if (this.a.hasPointerEvents()) {
                this.a.a.a(this.a, data.k.a() >> 1, this.j + 16, data.k.a(), this.i);
            } else {
                this.a.a.a(this.a, data.k.a() >> 1, this.j + 24, data.k.a(), this.i);
            }
            this.a.l = false;
        }
        if (this.a.n) {
            int n4;
            int n5;
            this.a.n = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655375, 0, 0);
                n5 = 1;
                while (n5 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655376, 2 * n5, 0);
                    ++n5;
                }
                this.a.a.a(((k)this.a).a, 655377, data.k.a() - 2, 0);
                this.a.a.a(((k)this.a).a, this.a.a.a(n2), data.k.a() >> 1, (24 - this.a.aJ) / 2, 39423, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                if (nArray != null) {
                    n4 = 0;
                    while (n4 < nArray.length) {
                        this.a.a.a(((k)this.a).a, this.a.u[1], data.k.a() / 2 - ((nArray.length - 1) * 10 + (nArray.length - 1) * 3) + n4 * 26, 48);
                        if (nArray[n4] == n3) {
                            this.a.a.a(((k)this.a).a, this.a.u[0], data.k.a() / 2 - ((nArray.length - 1) * 10 + (nArray.length - 1) * 3) + n4 * 26, 48);
                        }
                        this.a.a.a(((k)this.a).a, this.a.u[nArray[n4]], data.k.a() / 2 - ((nArray.length - 1) * 10 + (nArray.length - 1) * 3) + n4 * 26, 48);
                        ++n4;
                    }
                }
            } else {
                this.a.a.a(((k)this.a).a, 655360, 0, 0);
                n5 = 1;
                while (n5 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n5, 0);
                    ++n5;
                }
                this.a.a.a(((k)this.a).a, 655362, data.k.a() - 2, 0);
                this.a.a.a(((k)this.a).a, this.a.a.a(n2), data.k.a() >> 1, (16 - this.a.aJ) / 2, 39423, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                if (nArray != null) {
                    n4 = 0;
                    while (n4 < nArray.length) {
                        this.a.a.a(((k)this.a).a, this.a.u[1], data.k.a() / 2 - ((nArray.length - 1) * 10 + (nArray.length - 1) * 3) + n4 * 26, 32);
                        if (nArray[n4] == n3) {
                            this.a.a.a(((k)this.a).a, this.a.u[0], data.k.a() / 2 - ((nArray.length - 1) * 10 + (nArray.length - 1) * 3) + n4 * 26, 32);
                        }
                        this.a.a.a(((k)this.a).a, this.a.u[nArray[n4]], data.k.a() / 2 - ((nArray.length - 1) * 10 + (nArray.length - 1) * 3) + n4 * 26, 32);
                        ++n4;
                    }
                }
            }
            if (this.a.hasPointerEvents()) {
                if (stringBuffer2 != null) {
                    this.a.a.a(((k)this.a).a, 655375, 0, 68);
                    n5 = 1;
                    while (n5 < this.a.aV + 1) {
                        this.a.a.a(((k)this.a).a, 655376, 2 * n5, 68);
                        ++n5;
                    }
                    this.a.a.a(((k)this.a).a, 655377, data.k.a() - 2, 68);
                    this.a.a.a(((k)this.a).a, stringBuffer2, data.k.a() >> 1, 68 + (24 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                    this.a.a.a(((k)this.a).a, 655411, 12, 68);
                    this.a.a.a(((k)this.a).a, 655412, data.k.a() - 24, 68);
                }
            } else if (stringBuffer2 != null) {
                this.a.a.a(((k)this.a).a, 655360, 0, 52);
                n5 = 1;
                while (n5 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n5, 52);
                    ++n5;
                }
                this.a.a.a(((k)this.a).a, 655362, data.k.a() - 2, 52);
                this.a.a.a(((k)this.a).a, stringBuffer2, data.k.a() >> 1, 52 + (16 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                this.a.a.a(((k)this.a).a, 655399, 8, 52);
                this.a.a.a(((k)this.a).a, 655400, data.k.a() - 16, 52);
            }
            ac.c(Integer.parseInt(this.a.b.elementAt(this.a.h).toString()));
            if (ac.b() != -1) {
                if (this.a.hasPointerEvents()) {
                    this.a.a.a(((k)this.a).a, this.a, data.k.a() >> 1, this.j + 12 + 8, 0xFFFFFF, 1041, 0, 1, 8, this.a.aJ, data.k.a(), this.i, 0, 0);
                } else {
                    this.a.a.a(((k)this.a).a, this.a, data.k.a() >> 1, this.j + 16, 0xFFFFFF, 1041, 0, 1, 8, this.a.aJ, data.k.a(), this.i, 0, 0);
                }
            }
            this.c();
        }
        if (this.a.m) {
            if (this.a.hasPointerEvents()) {
                this.a.a.a((data.k.a() >> 1) - 12, this.j, data.k.b() - 24);
                return;
            }
            this.a.a.a((data.k.a() >> 1) - 8, this.j, data.k.b() - 16);
        }
    }

    public final void b() {
        int n2;
        int n3 = (int)(this.a.a * 100.0f / this.a.d);
        int n4 = (int)(this.a.b * 100.0f / this.a.d);
        int n5 = (int)(this.a.c * 100.0f / this.a.d);
        this.a.a.a(((k)this.a).a, 655360, 0, 0);
        int n6 = 1;
        while (n6 < this.a.aV + 1) {
            this.a.a.a(((k)this.a).a, 655361, 2 * n6, 0);
            ++n6;
        }
        this.a.a.a(((k)this.a).a, 655362, data.k.a() - 2, 0);
        ((k)this.a).a.setColor(0);
        this.a.a.a(((k)this.a).a, 655363, 2, 4);
        ((k)this.a).a.fillRect(14, 6, data.k.a() / 3 - 16, 4);
        if (this.b && n3 <= 10) {
            this.a.I += this.a.f;
            if (this.a.I > 300L) {
                this.b = false;
                ((k)this.a).a.setColor(0xFFFFFF);
                ((k)this.a).a.fillRect(14, 6, (data.k.a() / 3 - 16) * n3 / 100, 4);
                this.a.I = 0L;
            }
        } else {
            this.b = true;
            n2 = 0;
            while ((float)n2 < this.a.a) {
                this.a.a.a(((k)this.a).a, 655366, 14 + 1 * n2, 6);
                ++n2;
            }
        }
        ((k)this.a).a.setColor(0);
        this.a.a.a(((k)this.a).a, 655364, data.k.a() / 3 + 4 - 2, 4);
        ((k)this.a).a.fillRect(data.k.a() / 3 + 16 - 2, 6, data.k.a() / 3 - 16, 4);
        if (this.c && n4 <= 10) {
            this.a.I += this.a.f;
            if (this.a.I > 300L) {
                this.c = false;
                ((k)this.a).a.setColor(0xFFFFFF);
                ((k)this.a).a.fillRect(data.k.a() / 3 + 16 - 2, 6, (data.k.a() / 3 - 16) * n4 / 100, 4);
                this.a.I = 0L;
            }
        } else {
            this.c = true;
            n2 = 0;
            while ((float)n2 < this.a.b) {
                this.a.a.a(((k)this.a).a, 655367, data.k.a() / 3 + 16 - 2 + 1 * n2, 6);
                ++n2;
            }
        }
        ((k)this.a).a.setColor(0);
        this.a.a.a(((k)this.a).a, 655365, data.k.a() / 3 * 2 + 4 - 2, 4);
        ((k)this.a).a.fillRect(data.k.a() / 3 * 2 + 16 - 2, 6, data.k.a() / 3 - 16, 4);
        if (this.d && n5 <= 10) {
            this.a.I += this.a.f;
            if (this.a.I > 300L) {
                ((k)this.a).a.setColor(0xFFFFFF);
                ((k)this.a).a.fillRect(data.k.a() / 3 * 2 + 16 - 2, 6, (data.k.a() / 3 - 16) * n5 / 100, 4);
                this.d = false;
                this.a.I = 0L;
            }
        } else {
            this.d = true;
            n2 = 0;
            while ((float)n2 < this.a.c) {
                this.a.a.a(((k)this.a).a, 655368, data.k.a() / 3 * 2 + 16 - 2 + 1 * n2, 6);
                ++n2;
            }
        }
        if (this.a.m != 7) {
            this.c();
        }
    }

    public final void c() {
        if (this.a.m == 6) {
            if (this.a.ak == 1 && this.a.am == 1) {
                if (this.a.G && this.a.M) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 2 && this.a.am == 1) {
                if (this.a.G && this.a.H && this.a.M) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 3 && this.a.am == 1) {
                if (this.a.G && this.a.H && this.a.I && this.a.M) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 4 && this.a.am == 1) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.M) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 5 && this.a.am == 1) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.K && this.a.M) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 6 && this.a.am == 1) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.K && this.a.L && this.a.M) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 1 && this.a.am == 2) {
                if (this.a.G && this.a.M && this.a.N) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 2 && this.a.am == 2) {
                if (this.a.G && this.a.H && this.a.M && this.a.N) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 3 && this.a.am == 2) {
                if (this.a.G && this.a.H && this.a.I && this.a.M && this.a.N) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 4 && this.a.am == 2) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.M && this.a.N) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 5 && this.a.am == 2) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.K && this.a.M && this.a.N) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 6 && this.a.am == 2) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.K && this.a.L && this.a.M && this.a.N) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 1 && this.a.am == 3) {
                if (this.a.G && this.a.M && this.a.N && this.a.O) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 2 && this.a.am == 3) {
                if (this.a.G && this.a.H && this.a.M && this.a.N && this.a.O) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 3 && this.a.am == 3) {
                if (this.a.G && this.a.H && this.a.I && this.a.M && this.a.N && this.a.O) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 4 && this.a.am == 3) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.M && this.a.N && this.a.O) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 5 && this.a.am == 3) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.K && this.a.M && this.a.N && this.a.O) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 6 && this.a.am == 3) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.K && this.a.L && this.a.M && this.a.N && this.a.O) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 1 && this.a.am == 4) {
                if (this.a.G && this.a.M && this.a.N && this.a.O && this.a.P) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 2 && this.a.am == 4) {
                if (this.a.G && this.a.H && this.a.M && this.a.N && this.a.O && this.a.P) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 3 && this.a.am == 4) {
                if (this.a.G && this.a.H && this.a.I && this.a.M && this.a.N && this.a.O && this.a.P) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 4 && this.a.am == 4) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.M && this.a.N && this.a.O && this.a.P) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 5 && this.a.am == 4) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.K && this.a.M && this.a.N && this.a.O && this.a.P) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 6 && this.a.am == 4) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.K && this.a.L && this.a.M && this.a.N && this.a.O && this.a.P) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 1 && this.a.am == 5) {
                if (this.a.G && this.a.M && this.a.N && this.a.O && this.a.P && this.a.Q) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 2 && this.a.am == 5) {
                if (this.a.G && this.a.H && this.a.M && this.a.N && this.a.O && this.a.P && this.a.Q) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 3 && this.a.am == 5) {
                if (this.a.G && this.a.H && this.a.I && this.a.M && this.a.N && this.a.O && this.a.P && this.a.Q) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 4 && this.a.am == 5) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.M && this.a.N && this.a.O && this.a.P && this.a.Q) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 5 && this.a.am == 5) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.L && this.a.M && this.a.N && this.a.O && this.a.P && this.a.Q) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 6 && this.a.am == 5) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.K && this.a.L && this.a.M && this.a.N && this.a.O && this.a.P && this.a.Q) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 1 && this.a.am == 6) {
                if (this.a.G && this.a.M && this.a.N && this.a.O && this.a.P && this.a.Q && this.a.R) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 2 && this.a.am == 6) {
                if (this.a.G && this.a.H && this.a.M && this.a.N && this.a.O && this.a.P && this.a.Q && this.a.R) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 3 && this.a.am == 6) {
                if (this.a.G && this.a.H && this.a.I && this.a.M && this.a.N && this.a.O && this.a.P && this.a.Q && this.a.R) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 4 && this.a.am == 6) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.M && this.a.N && this.a.O && this.a.P && this.a.Q && this.a.R) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 5 && this.a.am == 6) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.K && this.a.M && this.a.N && this.a.O && this.a.P && this.a.Q && this.a.R) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 6 && this.a.am == 6) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.K && this.a.L && this.a.M && this.a.N && this.a.O && this.a.P && this.a.Q && this.a.R) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 1) {
                if (this.a.G) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 2) {
                if (this.a.G && this.a.H) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 3) {
                if (this.a.G && this.a.H && this.a.I) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 4) {
                if (this.a.G && this.a.H && this.a.I && this.a.J) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 5) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.K) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            } else if (this.a.ak == 6) {
                if (this.a.G && this.a.H && this.a.I && this.a.J && this.a.K && this.a.L) {
                    this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
                } else {
                    this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
                }
            }
        } else if (this.a.m == 8) {
            if (this.a.aw > 0) {
                this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
            } else {
                this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
            }
        } else if (this.a.m == 1 && this.a.s == 1) {
            this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
        } else if (this.a.m == 1 && this.a.s == 2) {
            if (!this.a.v) {
                this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
            } else {
                this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
            }
        } else if (this.a.m == 1 && this.a.s == 3) {
            if (!this.a.S) {
                this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
            } else {
                this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
            }
        } else if (this.a.m == 1 && this.a.s == 4) {
            this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
        } else if (this.a.m == 1 && this.a.s == 0) {
            if (this.a.aB > 0) {
                this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
            } else {
                this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
            }
        } else if (this.a.m == 1 && this.a.s == 5) {
            if (this.a.av > 0) {
                this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
            } else {
                this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
            }
        } else if (this.a.m == 5) {
            if (this.a.ay > 0) {
                this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
            } else {
                this.a.a.a(((k)this.a).a, 655381, this.a.aW, this.a.aX);
            }
        } else {
            this.a.a.a(((k)this.a).a, 655378, this.a.aW, this.a.aX);
        }
        if (this.a.aW != 0) {
            if (this.a.k == 0 && (this.a.l == 2 || this.a.l == 3 || this.a.l == 4 || this.a.l == 10)) {
                if (this.a.hasPointerEvents()) {
                    this.a.aZ = 24;
                    this.a.bl = 24;
                    this.a.bm = 44;
                } else {
                    this.a.aZ = 16;
                    this.a.bl = 16;
                    this.a.bm = 36;
                }
            } else if (this.a.k == 0 && this.a.l == 6 && (this.a.m == 3 || this.a.m == 4 || this.a.m == 5 || this.a.m == 8)) {
                if (this.a.hasPointerEvents()) {
                    this.a.aZ = 40;
                    this.a.bl = 40;
                    this.a.bm = 60;
                } else {
                    this.a.aZ = 32;
                    this.a.bl = 32;
                    this.a.bm = 52;
                }
            } else if (this.a.k == 0 && this.a.l == 6 && this.a.m == 6) {
                if (this.a.ae) {
                    this.a.aZ = 16;
                    this.a.bl = 16;
                    this.a.bm = 36;
                } else if (this.a.hasPointerEvents()) {
                    this.a.aZ = 40;
                    this.a.bl = 40;
                    this.a.bm = 60;
                } else {
                    this.a.aZ = 32;
                    this.a.bl = 32;
                    this.a.bm = 52;
                }
            } else if (this.a.k == 0 && this.a.l == 5) {
                if (this.a.hasPointerEvents()) {
                    this.a.aZ = 40;
                    this.a.bl = 40;
                    this.a.bm = 60;
                } else {
                    this.a.aZ = 32;
                    this.a.bl = 32;
                    this.a.bm = 52;
                }
            } else if (this.a.k == 0 && this.a.l == 6 && this.a.m == 1) {
                if (this.a.hasPointerEvents()) {
                    this.a.aZ = 40;
                    this.a.bl = 40;
                    this.a.bm = 60;
                } else {
                    this.a.aZ = 32;
                    this.a.bl = 32;
                    this.a.bm = 52;
                }
            } else if (this.a.k == 0 && this.a.l == 6 && (this.a.m == 10 || this.a.m == 9 || this.a.m == 7 || this.a.m == 0)) {
                this.a.aZ = 16;
                this.a.bl = 16;
                this.a.bm = 36;
            }
        } else {
            this.a.aZ = data.k.b() - 20;
            this.a.bl = data.k.b() - 20;
            this.a.bm = data.k.b();
        }
        this.a.a.a(((k)this.a).a, 655379, this.a.aY, this.a.aZ);
    }

    public final void d() {
        this.a = new Date();
        this.a = this.a.getTime();
        this.b = this.a / 1000L / 60L / 60L;
        this.c = this.b * 60L * 60L * 1000L;
        this.d = this.a - this.c;
        this.a.J = this.d / 1000L / 60L;
        this.e = 17257200L + this.b;
        this.f = this.e / 24L;
        this.g = this.f * 24L;
        this.a.K = this.a.ah ? this.e - this.g + 2L : this.e - this.g + 1L;
        if (this.a.d) {
            this.a.d = false;
            this.a.a = this.f;
        }
        this.a.aG = (int)(this.f - this.a.a) + 1;
        this.a.a = new StringBuffer().append(this.a.K + ":" + this.a.J);
    }

    public final void e() {
        if (this.a.aM) {
            if (this.a.F < 333L) {
                this.a.aS = 852012;
            } else if (this.a.F < 666L) {
                this.a.aS = 852013;
            } else if (this.a.F < 999L) {
                this.a.aS = 852014;
            } else {
                this.a.af = true;
                this.a.aM = false;
                if (this.a.X) {
                    this.a.a.a(this.a.P + 180);
                    this.a.l[this.a.P + 180] = 0;
                } else if (this.a.Y) {
                    this.a.a.a(this.a.P + 180 + 2);
                    this.a.l[this.a.P + 180 + 2] = 0;
                } else if (this.a.V) {
                    this.a.a.a(this.a.P + 1);
                    this.a.l[this.a.P + 1] = 0;
                } else if (this.a.W) {
                    this.a.a.a(this.a.P + 360 + 1);
                    this.a.l[this.a.P + 360 + 1] = 0;
                }
                this.a.m = 0;
                this.f();
                this.a.aw = true;
                return;
            }
        }
        if ((this.a.j || this.a.k) && this.a.az || this.a.aM) {
            this.a.a.a();
        }
        if (this.e) {
            this.a.aq = this.a.C > 3 ? (this.a.hasPointerEvents() ? this.a.C : 3) : this.a.C;
            this.a.x = this.a.aq / 2;
            if (this.a.C > 0) {
                this.e = false;
            }
        }
        int n2 = this.a.aq * 20 + (this.a.aq - 1) * 6;
        int n3 = 0;
        int n4 = 0;
        int n5 = this.a.aq;
        if (this.a.C > 0) {
            if (!this.a.hasPointerEvents() && this.a.C > 3) {
                n4 = this.a.x - 1;
                n5 = this.a.x + 2;
                if (this.a.x == 0) {
                    n4 = 0;
                    n5 = 3;
                }
                if (this.a.x == this.a.C - 1) {
                    n4 = this.a.x - 2;
                    n5 = this.a.x + 1;
                }
            }
            int n6 = n4;
            while (n6 < n5) {
                if ((data.k.a() >> 1) - (n2 >> 1) + 26 * n3 - 26 * (this.a.x - n5 / 2) <= (data.k.a() >> 1) + (n2 >> 1) && (data.k.a() >> 1) - (n2 >> 1) + 26 * n3 - 26 * (this.a.x - n5 / 2) >= (data.k.a() >> 1) - (n2 >> 1)) {
                    if (this.a.n != 1 && this.a.m == 1 || this.a.p != 1 && this.a.m == 8) {
                        this.a.a.a(((k)this.a).a, 655384, (data.k.a() >> 1) - (n2 >> 1) + 26 * n3 + 10 - 26 * (this.a.x - n5 / 2), this.a.aU - 10);
                    } else {
                        if (n6 == this.a.x) {
                            this.a.a.a(((k)this.a).a, 655383, (data.k.a() >> 1) - (n2 >> 1) + 26 * n3 + 10 - 26 * (this.a.x - n5 / 2), this.a.aU - 10);
                            this.a.a.a(((k)this.a).a, 655382, (data.k.a() >> 1) - (n2 >> 1) + 26 * n3 + 10 - 26 * (this.a.x - n5 / 2), this.a.aU - 10);
                        } else {
                            this.a.a.a(((k)this.a).a, 655384, (data.k.a() >> 1) - (n2 >> 1) + 26 * n3 + 10 - 26 * (this.a.x - n5 / 2), this.a.aU - 10);
                        }
                        this.a.a.a(((k)this.a).a, this.a.t[n6], (data.k.a() >> 1) - (n2 >> 1) + 26 * n3 + 10 - 26 * (this.a.x - n5 / 2), this.a.aU - 10);
                    }
                }
                ++n3;
                ++n6;
            }
        }
        if (!this.a.hasPointerEvents()) {
            if (this.a.C > 0) {
                if (this.a.n != 1 && this.a.m == 1 || this.a.p != 1 && this.a.m == 8) {
                    this.a.a.a(((k)this.a).a, 655403, (data.k.a() >> 1) - (n2 >> 1) - 3 - 8, this.a.aU - 20 - this.J);
                    this.a.a.a(((k)this.a).a, 655404, (data.k.a() >> 1) + (n2 >> 1) + 5, this.a.aU - 20 - this.J);
                } else {
                    if (this.a.j) {
                        this.a.a.a(((k)this.a).a, 655401, (data.k.a() >> 1) - (n2 >> 1) - 3 - 8, this.a.aU - 20 - this.J);
                    } else if (this.a.x == 0) {
                        this.a.a.a(((k)this.a).a, 655403, (data.k.a() >> 1) - (n2 >> 1) - 3 - 8, this.a.aU - 20 - this.J);
                    } else {
                        this.a.a.a(((k)this.a).a, 655399, (data.k.a() >> 1) - (n2 >> 1) - 3 - 8, this.a.aU - 20 - this.J);
                    }
                    if (this.a.k) {
                        this.a.a.a(((k)this.a).a, 655402, (data.k.a() >> 1) + (n2 >> 1) + 5, this.a.aU - 20 - this.J);
                    } else if (this.a.x == this.a.C - 1) {
                        this.a.a.a(((k)this.a).a, 655404, (data.k.a() >> 1) + (n2 >> 1) + 5, this.a.aU - 20 - this.J);
                    } else {
                        this.a.a.a(((k)this.a).a, 655400, (data.k.a() >> 1) + (n2 >> 1) + 5, this.a.aU - 20 - this.J);
                    }
                }
            }
            if (this.a.l > 500L) {
                if (this.a.j) {
                    this.a.j = false;
                }
                if (this.a.k) {
                    this.a.k = false;
                }
            }
        }
    }

    public final void f() {
        this.a.C = 0;
        this.a.x = 1;
        this.e = true;
    }

    public final void g() {
        this.a[0] = this.a.a.a(70);
        this.a[1] = this.a.a.a(71);
        this.a[2] = this.a.a.a(72);
        this.a[3] = this.a.a.a(73);
        this.a[4] = this.a.a.a(74);
        this.a[5] = this.a.a.a(75);
        this.a[0] = 655369;
        this.a[1] = 655370;
        this.a[2] = 655371;
        this.a[3] = 655372;
        this.a[4] = 655373;
        this.a[5] = 655374;
    }

    public final void h() {
        this.a.r = this.a.b ? 0 : 1;
        this.a.n = 0;
        this.a.au = 0;
        this.a.z = 0;
        this.a.x = 1;
        this.a.aw = true;
    }

    public final void i() {
        if (this.a.hasPointerEvents()) {
            if (!(this.a.n == 1 && this.a.m != 2 || this.a.n == 3 && this.a.v || this.a.n == 5 && this.a.S)) {
                if (this.a.f) {
                    this.a.a.a(((k)this.a).a, 655413, 8, 16);
                } else {
                    this.a.a.a(((k)this.a).a, 655411, 8, 16);
                }
                if (this.a.g) {
                    this.a.a.a(((k)this.a).a, 655414, data.k.a() - 8 - 12, 16);
                } else {
                    this.a.a.a(((k)this.a).a, 655412, data.k.a() - 8 - 12, 16);
                }
            }
        } else if (!(this.a.n == 1 && this.a.m != 2 || this.a.n == 3 && this.a.v || this.a.n == 5 && this.a.S)) {
            if (this.a.f) {
                this.a.a.a(((k)this.a).a, 655401, 8, 16);
            } else {
                this.a.a.a(((k)this.a).a, 655399, 8, 16);
            }
            if (this.a.g) {
                this.a.a.a(((k)this.a).a, 655402, data.k.a() - 16, 16);
            } else {
                this.a.a.a(((k)this.a).a, 655400, data.k.a() - 16, 16);
            }
        }
        if (this.a.m > 500L) {
            if (this.a.f) {
                this.a.f = false;
            }
            if (this.a.g) {
                this.a.g = false;
            }
        }
    }

    public final void j() {
        if (this.a.s == 0) {
            this.a(this.a.a.a(57), this.a.m);
            if (this.a.n == 1) {
                this.e();
                return;
            }
        } else {
            if (this.a.s == 1) {
                this.d(this.a.a.a(58));
                return;
            }
            if (this.a.s == 2) {
                this.e(this.a.a.a(60));
                return;
            }
            if (this.a.s == 3) {
                this.a.a.d(this.a.a.a(67));
                return;
            }
            if (this.a.s == 4) {
                this.a.a.e(this.a.a.a(68));
                return;
            }
            if (this.a.s == 5) {
                this.f(this.a.a.a(65));
                if (this.a.n == 1) {
                    this.e();
                }
            }
        }
    }

    private void a(StringBuffer stringBuffer, byte[] byArray) {
        int n2 = this.a.hasPointerEvents() ? 52 + this.a.aI * this.a.as : 40 + this.a.aI * this.a.as;
        if (this.a.aw) {
            int n3;
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
            int n4 = 0;
            while (n4 < this.a.as) {
                if (n4 % 2 == 0) {
                    if (this.a.au % 2 == 0) {
                        ((k)this.a).a.setColor(10061);
                    } else {
                        ((k)this.a).a.setColor(6965);
                    }
                } else if (this.a.au % 2 == 0) {
                    ((k)this.a).a.setColor(6965);
                } else {
                    ((k)this.a).a.setColor(10061);
                }
                if (this.a.hasPointerEvents()) {
                    ((k)this.a).a.fillRect(0, 52 + n4 * this.a.aI, data.k.a(), this.a.aI);
                } else {
                    ((k)this.a).a.fillRect(0, 40 + n4 * this.a.aI, data.k.a(), this.a.aI);
                }
                ++n4;
            }
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655375, 0, 16);
                n3 = 1;
                while (n3 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655376, 2 * n3, 16);
                    ++n3;
                }
                this.a.a.a(((k)this.a).a, 655377, data.k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, 16 + (24 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, 655360, 0, 16);
                n3 = 1;
                while (n3 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n3, 16);
                    ++n3;
                }
                this.a.a.a(((k)this.a).a, 655362, data.k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, 16 + (16 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            }
            this.a.at = this.a.aB;
            if (this.a.at > this.a.as) {
                if (this.a.hasPointerEvents()) {
                    if (this.a.l == 6 && this.a.m == 1 && this.a.n == 0 || this.a.n == 1) {
                        this.a.at = this.a.at - this.a.au < this.a.as ? this.a.aB - this.a.au : this.a.as;
                    }
                } else {
                    this.a.at = this.a.as;
                }
            }
            n3 = this.a.au;
            while (n3 < this.a.at + this.a.au) {
                this.k = n3 == this.a.z ? 0xFFFFFF : 39423;
                StringBuffer stringBuffer2 = new StringBuffer().append(this.a.b[byArray[n3]]).append(".." + this.a.q[n3]);
                if (this.a.hasPointerEvents()) {
                    this.a.a.a(((k)this.a).a, stringBuffer2, data.k.a() >> 1, 52 + this.a.aI * (n3 - this.a.au) + this.a.aI / 2 + 4, this.k, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                } else {
                    this.a.a.a(((k)this.a).a, stringBuffer2, data.k.a() >> 1, 40 + this.a.aI * (n3 - this.a.au) + this.a.aI / 2 + 4, this.k, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                }
                ++n3;
            }
        }
        if (this.a.n != 1 || this.a.m == 2) {
            if (this.a.hasPointerEvents()) {
                if (this.a.h) {
                    this.a.a.a(((k)this.a).a, 655419, (data.k.a() >> 1) - 12, 40);
                } else if (this.a.au > 0) {
                    this.a.a.a(((k)this.a).a, 655417, (data.k.a() >> 1) - 12, 40);
                } else {
                    this.a.a.a(((k)this.a).a, 655421, (data.k.a() >> 1) - 12, 40);
                }
                if (this.a.i) {
                    this.a.a.a(((k)this.a).a, 655420, (data.k.a() >> 1) - 12, n2);
                } else if (this.a.aB > this.a.at && this.a.at + this.a.au < this.a.aB) {
                    this.a.a.a(((k)this.a).a, 655418, (data.k.a() >> 1) - 12, n2);
                } else {
                    this.a.a.a(((k)this.a).a, 655422, (data.k.a() >> 1) - 12, n2);
                }
            } else {
                if (this.a.h) {
                    this.a.a.a(((k)this.a).a, 655407, (data.k.a() >> 1) - 8, 32);
                } else if (this.a.au > 0) {
                    this.a.a.a(((k)this.a).a, 655405, (data.k.a() >> 1) - 8, 32);
                } else {
                    this.a.a.a(((k)this.a).a, 655409, (data.k.a() >> 1) - 8, 32);
                }
                if (this.a.i) {
                    this.a.a.a(((k)this.a).a, 655408, (data.k.a() >> 1) - 8, n2);
                } else if (this.a.aB > this.a.at && this.a.at + this.a.au < this.a.aB) {
                    this.a.a.a(((k)this.a).a, 655406, (data.k.a() >> 1) - 8, n2);
                } else {
                    this.a.a.a(((k)this.a).a, 655410, (data.k.a() >> 1) - 8, n2);
                }
            }
            if (this.a.n > 500L) {
                if (this.a.h) {
                    this.a.h = false;
                }
                if (this.a.i) {
                    this.a.i = false;
                }
            }
        } else if (this.a.hasPointerEvents()) {
            this.a.a.a(((k)this.a).a, 655421, (data.k.a() >> 1) - 12, 40);
            this.a.a.a(((k)this.a).a, 655422, (data.k.a() >> 1) - 12, n2);
        } else {
            this.a.a.a(((k)this.a).a, 655409, (data.k.a() >> 1) - 8, 32);
            this.a.a.a(((k)this.a).a, 655410, (data.k.a() >> 1) - 8, n2);
        }
        this.i();
    }

    private void d(StringBuffer stringBuffer) {
        int n2 = (int)(this.a.a * 100.0f / this.a.d);
        int n3 = (int)(this.a.b * 100.0f / this.a.d);
        int n4 = (int)(this.a.c * 100.0f / this.a.d);
        if (this.a.aw) {
            int n5;
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655375, 0, 16);
                n5 = 1;
                while (n5 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655376, 2 * n5, 16);
                    ++n5;
                }
                this.a.a.a(((k)this.a).a, 655377, data.k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, 16 + (24 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, 655360, 0, 16);
                n5 = 1;
                while (n5 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n5, 16);
                    ++n5;
                }
                this.a.a.a(((k)this.a).a, 655362, data.k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, 16 + (16 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            }
            n5 = 32 + (data.k.b() - 52 - 6 * (this.a.aL * 3 / 2 + 12)) / 2;
            this.a.a.a(((k)this.a).a, 655393, data.k.a() >> 3, n5 + 6);
            this.a.a.a(((k)this.a).a, 655394, data.k.a() >> 3, n5 + (12 + this.a.aL * 1) * 1 + 6);
            this.a.a.a(((k)this.a).a, 655395, data.k.a() >> 3, n5 + (12 + this.a.aL * 1) * 2 + 6);
            this.a.a.a(((k)this.a).a, 655396, data.k.a() >> 3, n5 + (12 + this.a.aL * 1) * 3 + 6);
            this.a.a.a(((k)this.a).a, 655397, data.k.a() >> 3, n5 + (12 + this.a.aL * 1) * 4 + 6);
            this.a.a.a(((k)this.a).a, 655398, data.k.a() >> 3, n5 + (12 + this.a.aL * 1) * 5 + 6);
            int n6 = 0;
            while (n6 < 6) {
                int n7;
                ((k)this.a).a.setColor(0);
                ((k)this.a).a.fillRect((data.k.a() >> 3) + 28, 7 * n6 + n5 + (6 + this.a.aL * 1) * n6, 1 * (int)this.a.d, 8);
                if (n6 == 0) {
                    if (n2 <= 10) {
                        if (this.b && n2 <= 10) {
                            this.a.aw = true;
                            ((k)this.a).a.setColor(0xFFFFFF);
                            ((k)this.a).a.fillRect((data.k.a() >> 3) + 28, 7 * n6 + n5 + (6 + this.a.aL * 1) * n6, 1 * (int)this.a.d * n2 / 100, 8);
                        } else {
                            this.a.aw = true;
                            n7 = 0;
                            while ((float)n7 < this.a.a) {
                                this.a.a.a(((k)this.a).a, this.a[n6], (data.k.a() >> 3) + 28 + 1 * n7, 7 * n6 + n5 + (6 + this.a.aL * 1) * n6);
                                ++n7;
                            }
                        }
                    } else {
                        n7 = 0;
                        while ((float)n7 < this.a.a) {
                            this.a.a.a(((k)this.a).a, this.a[n6], (data.k.a() >> 3) + 28 + 1 * n7, 7 * n6 + n5 + (6 + this.a.aL * 1) * n6);
                            ++n7;
                        }
                    }
                } else if (n6 == 1) {
                    if (n3 <= 10) {
                        if (this.c) {
                            this.a.aw = true;
                            ((k)this.a).a.setColor(0xFFFFFF);
                            ((k)this.a).a.fillRect((data.k.a() >> 3) + 28, 7 * n6 + n5 + (6 + this.a.aL * 1) * n6, 1 * (int)this.a.d * n3 / 100, 8);
                        } else {
                            this.a.aw = true;
                            n7 = 0;
                            while ((float)n7 < this.a.b) {
                                this.a.a.a(((k)this.a).a, this.a[n6], (data.k.a() >> 3) + 28 + 1 * n7, 7 * n6 + n5 + (6 + this.a.aL * 1) * n6);
                                ++n7;
                            }
                        }
                    } else {
                        n7 = 0;
                        while ((float)n7 < this.a.b) {
                            this.a.a.a(((k)this.a).a, this.a[n6], (data.k.a() >> 3) + 28 + 1 * n7, 7 * n6 + n5 + (6 + this.a.aL * 1) * n6);
                            ++n7;
                        }
                    }
                } else if (n6 == 2) {
                    if (n4 <= 10) {
                        if (!this.d) {
                            this.a.aw = true;
                            ((k)this.a).a.setColor(0xFFFFFF);
                            ((k)this.a).a.fillRect((data.k.a() >> 3) + 28, 7 * n6 + n5 + (6 + this.a.aL * 1) * n6, 1 * (int)this.a.d * n4 / 100, 8);
                        } else {
                            this.a.aw = true;
                            n7 = 0;
                            while ((float)n7 < this.a.c) {
                                this.a.a.a(((k)this.a).a, this.a[n6], (data.k.a() >> 3) + 28 + 1 * n7, 7 * n6 + n5 + (6 + this.a.aL * 1) * n6);
                                ++n7;
                            }
                        }
                    } else {
                        n7 = 0;
                        while ((float)n7 < this.a.c) {
                            this.a.a.a(((k)this.a).a, this.a[n6], (data.k.a() >> 3) + 28 + 1 * n7, 7 * n6 + n5 + (6 + this.a.aL * 1) * n6);
                            ++n7;
                        }
                    }
                } else if (n6 == 3) {
                    n7 = 0;
                    while ((float)n7 < this.a.e) {
                        this.a.a.a(((k)this.a).a, this.a[n6], (data.k.a() >> 3) + 28 + 1 * n7, 7 * n6 + n5 + (6 + this.a.aL * 1) * n6);
                        ++n7;
                    }
                } else if (n6 == 4) {
                    n7 = 0;
                    while ((float)n7 < this.a.f) {
                        this.a.a.a(((k)this.a).a, this.a[n6], (data.k.a() >> 3) + 28 + 1 * n7, 7 * n6 + n5 + (6 + this.a.aL * 1) * n6);
                        ++n7;
                    }
                } else if (n6 == 5) {
                    n7 = 0;
                    while ((float)n7 < this.a.g) {
                        this.a.a.a(((k)this.a).a, this.a[n6], (data.k.a() >> 3) + 28 + 1 * n7, 7 * n6 + n5 + (6 + this.a.aL * 1) * n6);
                        ++n7;
                    }
                }
                this.a.a.a(((k)this.a).a, this.a[n6], (data.k.a() >> 3) + 28 + 1 * (int)this.a.d + data.k.a() / 17, 7 + 7 * n6 + n5 + (6 + this.a.aL * 1) * n6, 39423, 68, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                ++n6;
            }
        }
        this.i();
    }

    public final void a(StringBuffer stringBuffer, int n2) {
        int n3;
        if (this.a.c[n2] == null && n2 > 59 && n2 < 120) {
            this.a.a.a(3);
            n3 = 0;
            while (n3 < 60) {
                this.a.c[n3 + 60] = this.a.a.a(196608 + n3).toString();
                ++n3;
            }
            this.a.a.b(3);
            this.a.a();
        }
        if (this.a.l) {
            this.a = new StringBuffer().append(this.a.c[n2]);
            this.a.a.a(this.a, data.k.a() >> 1, 111, data.k.a(), data.k.b() - 106 - 20);
            this.a.l = false;
        }
        if (this.a.n) {
            this.a.n = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655375, 0, 16);
                n3 = 1;
                while (n3 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655376, 2 * n3, 16);
                    ++n3;
                }
                this.a.a.a(((k)this.a).a, 655377, data.k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, 16 + (24 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, 655360, 0, 16);
                n3 = 1;
                while (n3 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n3, 16);
                    ++n3;
                }
                this.a.a.a(((k)this.a).a, 655362, data.k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, 16 + (16 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            }
            StringBuffer stringBuffer2 = new StringBuffer().append(this.a.b[n2]);
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, stringBuffer2, data.k.a() >> 1, 55, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                if (this.a.f[n2] != -1 && n2 != 18 && n2 != 9 && n2 != 10 && n2 != 17) {
                    if (n2 == 4 || n2 == 43 || n2 == 44 || n2 == 51 || n2 == 52 || n2 == 53 || n2 == 62) {
                        this.a.a.a(((k)this.a).a, this.a.f[n2], data.k.a() >> 1, 85);
                    } else {
                        this.a.a.a(((k)this.a).a, this.a.f[n2], data.k.a() >> 1, 75);
                    }
                }
                ac.c(Integer.parseInt(this.a.b.elementAt(this.a.h).toString()));
                this.a.a.a(((k)this.a).a, this.a, data.k.a() >> 1, 107, 0xFFFFFF, 1089, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b() - 106 - 20, 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, stringBuffer2, data.k.a() >> 1, 43, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                if (this.a.f[n2] != -1 && n2 != 18 && n2 != 9 && n2 != 10 && n2 != 17) {
                    if (n2 == 4 || n2 == 43 || n2 == 44 || n2 == 51 || n2 == 52 || n2 == 53 || n2 == 62) {
                        this.a.a.a(((k)this.a).a, this.a.f[n2], data.k.a() >> 1, 73);
                    } else {
                        this.a.a.a(((k)this.a).a, this.a.f[n2], data.k.a() >> 1, 63);
                    }
                }
                ac.c(Integer.parseInt(this.a.b.elementAt(this.a.h).toString()));
                this.a.a.a(((k)this.a).a, this.a, data.k.a() >> 1, 107, 0xFFFFFF, 1089, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b() - 106 - 20, 0, 0);
            }
        }
        if (this.a.m) {
            this.a.a.a((data.k.a() >> 1) - 8, 86, data.k.b() - 12 - 1);
        }
    }

    private void e(StringBuffer stringBuffer) {
        if (this.a.aw) {
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655375, 0, 16);
                int n2 = 1;
                while (n2 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655376, 2 * n2, 16);
                    ++n2;
                }
                this.a.a.a(((k)this.a).a, 655377, data.k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, 16 + (24 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, 655360, 0, 16);
                int n3 = 1;
                while (n3 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n3, 16);
                    ++n3;
                }
                this.a.a.a(((k)this.a).a, 655362, data.k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, 16 + (16 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            }
            ((k)this.a).a.setColor(26265);
            ((k)this.a).a.fillRect((data.k.a() >> 1) - 24, 32 + (data.k.b() - 32 - 20 >> 1) - 24, 48, 51);
            this.a.a.a(((k)this.a).a, 196608, data.k.a() >> 1, 32 + (data.k.b() - 32 - 20 >> 1) - 24 + 37);
            if (this.a.d[this.a.G] != -1) {
                this.a.a.a(((k)this.a).a, this.a.f[this.a.d[this.a.G]], data.k.a() >> 1, 32 + (data.k.b() - 32 - 20 >> 1) - 24 + 37);
            }
            if (this.a.c[this.a.F] != -1) {
                this.a.a.a(((k)this.a).a, this.a.f[this.a.c[this.a.F]], data.k.a() >> 1, 32 + (data.k.b() - 32 - 20 >> 1) - 24 + 37);
            }
            if (this.a.b[this.a.E] != -1) {
                this.a.a.a(((k)this.a).a, this.a.f[this.a.b[this.a.E]], data.k.a() >> 1, 32 + (data.k.b() - 32 - 20 >> 1) - 24 + 37);
            }
            if (this.a.a[this.a.D] != -1) {
                this.a.a.a(((k)this.a).a, this.a.f[this.a.a[this.a.D]], data.k.a() >> 1, 32 + (data.k.b() - 32 - 20 >> 1) - 24 + this.a.a.a[0] + 37 + this.a.a.b[0]);
            }
        }
        if (this.a.v) {
            if (this.a.u == 0) {
                if (this.a.t) {
                    this.a.a.a(((k)this.a).a, 655401, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) - 32 - 3);
                } else {
                    this.a.a.a(((k)this.a).a, 655399, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) - 32 - 3);
                }
            } else {
                this.a.a.a(((k)this.a).a, 655403, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) - 32 - 3);
            }
            if (this.a.u == 1) {
                if (this.a.t) {
                    this.a.a.a(((k)this.a).a, 655401, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) - 16 - 1);
                } else {
                    this.a.a.a(((k)this.a).a, 655399, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) - 16 - 1);
                }
            } else {
                this.a.a.a(((k)this.a).a, 655403, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) - 16 - 1);
            }
            if (this.a.u == 2) {
                if (this.a.t) {
                    this.a.a.a(((k)this.a).a, 655401, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) + 1);
                } else {
                    this.a.a.a(((k)this.a).a, 655399, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) + 1);
                }
            } else {
                this.a.a.a(((k)this.a).a, 655403, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) + 1);
            }
            if (this.a.u == 3) {
                if (this.a.t) {
                    this.a.a.a(((k)this.a).a, 655401, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) + 16 + 3);
                } else {
                    this.a.a.a(((k)this.a).a, 655399, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) + 16 + 3);
                }
            } else {
                this.a.a.a(((k)this.a).a, 655403, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) + 16 + 3);
            }
            if (this.a.u == 0) {
                if (this.a.u) {
                    this.a.a.a(((k)this.a).a, 655402, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) - 32 - 3);
                } else {
                    this.a.a.a(((k)this.a).a, 655400, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) - 32 - 3);
                }
            } else {
                this.a.a.a(((k)this.a).a, 655404, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) - 32 - 3);
            }
            if (this.a.u == 1) {
                if (this.a.u) {
                    this.a.a.a(((k)this.a).a, 655402, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) - 16 - 1);
                } else {
                    this.a.a.a(((k)this.a).a, 655400, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) - 16 - 1);
                }
            } else {
                this.a.a.a(((k)this.a).a, 655404, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) - 16 - 1);
            }
            if (this.a.u == 2) {
                if (this.a.u) {
                    this.a.a.a(((k)this.a).a, 655402, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) + 1);
                } else {
                    this.a.a.a(((k)this.a).a, 655400, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) + 1);
                }
            } else {
                this.a.a.a(((k)this.a).a, 655404, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) + 1);
            }
            if (this.a.u == 3) {
                if (this.a.u) {
                    this.a.a.a(((k)this.a).a, 655402, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) + 16 + 3);
                } else {
                    this.a.a.a(((k)this.a).a, 655400, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) + 16 + 3);
                }
            } else {
                this.a.a.a(((k)this.a).a, 655404, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) + 16 + 3);
            }
        } else {
            this.a.a.a(((k)this.a).a, 655403, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) - 32 - 3);
            this.a.a.a(((k)this.a).a, 655403, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) - 16 - 1);
            this.a.a.a(((k)this.a).a, 655403, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) + 1);
            this.a.a.a(((k)this.a).a, 655403, (data.k.a() >> 1) - 19 - 21, 32 + (data.k.b() - 32 - 20 >> 1) + 16 + 3);
            this.a.a.a(((k)this.a).a, 655404, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) - 32 - 3);
            this.a.a.a(((k)this.a).a, 655404, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) - 16 - 1);
            this.a.a.a(((k)this.a).a, 655404, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) + 1);
            this.a.a.a(((k)this.a).a, 655404, (data.k.a() >> 1) + 19 + 14, 32 + (data.k.b() - 32 - 20 >> 1) + 16 + 3);
        }
        if (this.a.o > 500L) {
            if (this.a.t) {
                this.a.t = false;
            }
            if (this.a.u) {
                this.a.u = false;
            }
        }
        this.i();
    }

    public final void b(StringBuffer stringBuffer) {
        if (this.a.aw) {
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655375, 0, 16);
                int n2 = 1;
                while (n2 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655376, 2 * n2, 16);
                    ++n2;
                }
                this.a.a.a(((k)this.a).a, 655377, data.k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, 16 + (24 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, 655360, 0, 16);
                int n3 = 1;
                while (n3 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n3, 16);
                    ++n3;
                }
                this.a.a.a(((k)this.a).a, 655362, data.k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, 16 + (16 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            }
            if (this.a.t == 0) {
                this.a.a.a(((k)this.a).a, this.a.a.a(77), data.k.a() >> 1, data.k.b() / 2 - this.a.aJ * 2, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, this.a.a.a(77), data.k.a() >> 1, data.k.b() / 2 - this.a.aJ * 2, 26265, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            }
            if (this.a.t == 1) {
                this.a.a.a(((k)this.a).a, this.a.a.a(78), data.k.a() >> 1, data.k.b() / 2 + this.a.aJ, 0xFFFFFF, 17, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                return;
            }
            this.a.a.a(((k)this.a).a, this.a.a.a(78), data.k.a() >> 1, data.k.b() / 2 + this.a.aJ, 26265, 17, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
        }
    }

    private void f(StringBuffer stringBuffer) {
        int n2 = this.a.hasPointerEvents() ? 52 + this.a.aI * this.a.as : 40 + this.a.aI * this.a.as;
        if (this.a.aw) {
            int n3;
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
            int n4 = 0;
            while (n4 < this.a.as) {
                if (n4 % 2 == 0) {
                    if (this.a.au % 2 == 0) {
                        ((k)this.a).a.setColor(10061);
                    } else {
                        ((k)this.a).a.setColor(6965);
                    }
                } else if (this.a.au % 2 == 0) {
                    ((k)this.a).a.setColor(6965);
                } else {
                    ((k)this.a).a.setColor(10061);
                }
                if (this.a.hasPointerEvents()) {
                    ((k)this.a).a.fillRect(0, 52 + n4 * this.a.aI, data.k.a(), this.a.aI);
                } else {
                    ((k)this.a).a.fillRect(0, 40 + n4 * this.a.aI, data.k.a(), this.a.aI);
                }
                ++n4;
            }
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655375, 0, 16);
                n3 = 1;
                while (n3 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655376, 2 * n3, 16);
                    ++n3;
                }
                this.a.a.a(((k)this.a).a, 655377, data.k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, 16 + (24 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, 655360, 0, 16);
                n3 = 1;
                while (n3 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n3, 16);
                    ++n3;
                }
                this.a.a.a(((k)this.a).a, 655362, data.k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, 16 + (16 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            }
            this.a.at = this.a.av;
            if (this.a.at > this.a.as) {
                this.a.at = this.a.as;
            }
            n3 = this.a.au;
            while (n3 < this.a.at + this.a.au) {
                this.k = n3 == this.a.v ? 0xFFFFFF : 39423;
                StringBuffer stringBuffer2 = new StringBuffer().append(this.a.b[this.a.a[n3]]);
                if (this.a.hasPointerEvents()) {
                    this.a.a.a(((k)this.a).a, stringBuffer2, data.k.a() >> 1, 52 + this.a.aI * (n3 - this.a.au) + this.a.aI / 2 + 4, this.k, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                } else {
                    this.a.a.a(((k)this.a).a, stringBuffer2, data.k.a() >> 1, 40 + this.a.aI * (n3 - this.a.au) + this.a.aI / 2 + 4, this.k, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                }
                ++n3;
            }
        }
        if (this.a.n != 1 || this.a.m == 2) {
            if (this.a.hasPointerEvents()) {
                if (this.a.h) {
                    this.a.a.a(((k)this.a).a, 655419, (data.k.a() >> 1) - 12, 40);
                } else if (this.a.au > 0) {
                    this.a.a.a(((k)this.a).a, 655417, (data.k.a() >> 1) - 12, 40);
                } else {
                    this.a.a.a(((k)this.a).a, 655421, (data.k.a() >> 1) - 12, 40);
                }
                if (this.a.i) {
                    this.a.a.a(((k)this.a).a, 655420, (data.k.a() >> 1) - 12, n2);
                } else if (this.a.av > this.a.at && this.a.at + this.a.au < this.a.av) {
                    this.a.a.a(((k)this.a).a, 655418, (data.k.a() >> 1) - 12, n2);
                } else {
                    this.a.a.a(((k)this.a).a, 655422, (data.k.a() >> 1) - 12, n2);
                }
            } else {
                if (this.a.h) {
                    this.a.a.a(((k)this.a).a, 655407, (data.k.a() >> 1) - 8, 32);
                } else if (this.a.au > 0) {
                    this.a.a.a(((k)this.a).a, 655405, (data.k.a() >> 1) - 8, 32);
                } else {
                    this.a.a.a(((k)this.a).a, 655409, (data.k.a() >> 1) - 8, 32);
                }
                if (this.a.i) {
                    this.a.a.a(((k)this.a).a, 655408, (data.k.a() >> 1) - 8, n2);
                } else if (this.a.av > this.a.at && this.a.at + this.a.au < this.a.av) {
                    this.a.a.a(((k)this.a).a, 655406, (data.k.a() >> 1) - 8, n2);
                } else {
                    this.a.a.a(((k)this.a).a, 655410, (data.k.a() >> 1) - 8, n2);
                }
            }
            if (this.a.n > 500L) {
                if (this.a.h) {
                    this.a.h = false;
                }
                if (this.a.i) {
                    this.a.i = false;
                }
            }
        } else if (this.a.hasPointerEvents()) {
            this.a.a.a(((k)this.a).a, 655421, (data.k.a() >> 1) - 12, 40);
            this.a.a.a(((k)this.a).a, 655422, (data.k.a() >> 1) - 12, n2);
        } else {
            this.a.a.a(((k)this.a).a, 655409, (data.k.a() >> 1) - 8, 32);
            this.a.a.a(((k)this.a).a, 655410, (data.k.a() >> 1) - 8, n2);
        }
        this.i();
    }

    public final void k() {
        this.a.m = 0;
        this.a.aB = false;
        this.a.aC = false;
        this.a.aD = false;
        int n2 = 0;
        while (n2 < 120) {
            this.a.f[n2] = "";
            this.a.e[n2] = "";
            this.a.d[n2] = "";
            ++n2;
        }
        this.a.az = 0;
        this.a.ay = 0;
        this.a.ax = 0;
        this.a.y = 0;
        this.h();
    }

    public final void c(StringBuffer stringBuffer) {
        int n2 = this.a.hasPointerEvents() ? 52 + this.a.aI * this.a.as : 40 + this.a.aI * this.a.as;
        if (this.a.aw) {
            int n3;
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
            int n4 = 0;
            while (n4 < this.a.as) {
                if (n4 % 2 == 0) {
                    if (this.a.au % 2 == 0) {
                        ((k)this.a).a.setColor(10061);
                    } else {
                        ((k)this.a).a.setColor(6965);
                    }
                } else if (this.a.au % 2 == 0) {
                    ((k)this.a).a.setColor(6965);
                } else {
                    ((k)this.a).a.setColor(10061);
                }
                if (this.a.hasPointerEvents()) {
                    ((k)this.a).a.fillRect(0, 52 + n4 * this.a.aI, data.k.a(), this.a.aI);
                } else {
                    ((k)this.a).a.fillRect(0, 40 + n4 * this.a.aI, data.k.a(), this.a.aI);
                }
                ++n4;
            }
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655375, 0, 16);
                n3 = 1;
                while (n3 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655376, 2 * n3, 16);
                    ++n3;
                }
                this.a.a.a(((k)this.a).a, 655377, data.k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, 16 + (24 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, 655360, 0, 16);
                n3 = 1;
                while (n3 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n3, 16);
                    ++n3;
                }
                this.a.a.a(((k)this.a).a, 655362, data.k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, data.k.a() >> 1, 16 + (16 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            }
            this.n = 0;
            this.o = 0;
            this.p = 0;
            this.q = 0;
            this.r = 0;
            this.s = 0;
            if (this.a.aA) {
                int n5;
                int n6;
                n3 = this.a.b[17].equals(this.a.b[9]) ? 2 : (this.a.b[17].equals(this.a.b[10]) ? 3 : 1);
                int n7 = 0;
                while (n7 < this.a.aB) {
                    n6 = 0;
                    while (n6 < 120) {
                        n5 = 0;
                        while (n5 <= 10) {
                            if (this.a.a[n6][n5] != 0 && this.a.m[n7] == this.a.a[n6][n5]) {
                                int n8;
                                int n9;
                                int n10;
                                int n11;
                                int n12;
                                int n13;
                                int n14;
                                if (n3 == 1 && this.a.e[n6] == -1) {
                                    this.f = true;
                                    n14 = 0;
                                    while (n14 < this.a.av) {
                                        if (this.a.a[n14] == (byte)n6) {
                                            this.f = false;
                                        }
                                        ++n14;
                                    }
                                    n13 = 0;
                                    while (n13 < this.a.aw) {
                                        if (this.a.a[n13] == this.a.b[n6]) {
                                            this.f = false;
                                        }
                                        ++n13;
                                    }
                                    n12 = 0;
                                    while (n12 < this.a.H) {
                                        if (this.a.a[n12] == n6) {
                                            this.f = false;
                                        }
                                        ++n12;
                                    }
                                    n11 = 0;
                                    while (n11 < this.a.I) {
                                        if (this.a.b[n11] == n6) {
                                            this.f = false;
                                        }
                                        ++n11;
                                    }
                                    n10 = 0;
                                    while (n10 < this.a.J) {
                                        if (this.a.c[n10] == n6) {
                                            this.f = false;
                                        }
                                        ++n10;
                                    }
                                    n9 = 0;
                                    while (n9 < this.a.K) {
                                        if (this.a.d[n9] == n6) {
                                            this.f = false;
                                        }
                                        ++n9;
                                    }
                                    n8 = 0;
                                    while (n8 < this.a.az) {
                                        if (this.a.f[n8] == this.a.b[n6]) {
                                            this.f = false;
                                        }
                                        ++n8;
                                    }
                                    if (this.f) {
                                        this.a.f[this.a.az] = this.a.b[n6];
                                        ++this.a.az;
                                    }
                                }
                                if (n3 == 2 && (this.a.e[n6] == 9 || this.a.e[n6] == -1)) {
                                    this.f = true;
                                    n14 = 0;
                                    while (n14 < this.a.av) {
                                        if (this.a.a[n14] == (byte)n6) {
                                            this.f = false;
                                        }
                                        ++n14;
                                    }
                                    n13 = 0;
                                    while (n13 < this.a.aw) {
                                        if (this.a.a[n13] == this.a.b[n6]) {
                                            this.f = false;
                                        }
                                        ++n13;
                                    }
                                    n12 = 0;
                                    while (n12 < this.a.H) {
                                        if (this.a.a[n12] == n6) {
                                            this.f = false;
                                        }
                                        ++n12;
                                    }
                                    n11 = 0;
                                    while (n11 < this.a.I) {
                                        if (this.a.b[n11] == n6) {
                                            this.f = false;
                                        }
                                        ++n11;
                                    }
                                    n10 = 0;
                                    while (n10 < this.a.J) {
                                        if (this.a.c[n10] == n6) {
                                            this.f = false;
                                        }
                                        ++n10;
                                    }
                                    n9 = 0;
                                    while (n9 < this.a.K) {
                                        if (this.a.d[n9] == n6) {
                                            this.f = false;
                                        }
                                        ++n9;
                                    }
                                    n8 = 0;
                                    while (n8 < this.a.az) {
                                        if (this.a.f[n8] == this.a.b[n6]) {
                                            this.f = false;
                                        }
                                        ++n8;
                                    }
                                    if (this.f) {
                                        this.a.f[this.a.az] = this.a.b[n6];
                                        ++this.a.az;
                                    }
                                }
                                if (n3 == 3 && (this.a.e[n6] == 9 || this.a.e[n6] == 10 || this.a.e[n6] == -1)) {
                                    this.f = true;
                                    n14 = 0;
                                    while (n14 < this.a.av) {
                                        if (this.a.a[n14] == (byte)n6) {
                                            this.f = false;
                                        }
                                        ++n14;
                                    }
                                    n13 = 0;
                                    while (n13 < this.a.aw) {
                                        if (this.a.a[n13] == this.a.b[n6]) {
                                            this.f = false;
                                        }
                                        ++n13;
                                    }
                                    n12 = 0;
                                    while (n12 < this.a.H) {
                                        if (this.a.a[n12] == n6) {
                                            this.f = false;
                                        }
                                        ++n12;
                                    }
                                    n11 = 0;
                                    while (n11 < this.a.I) {
                                        if (this.a.b[n11] == n6) {
                                            this.f = false;
                                        }
                                        ++n11;
                                    }
                                    n10 = 0;
                                    while (n10 < this.a.J) {
                                        if (this.a.c[n10] == n6) {
                                            this.f = false;
                                        }
                                        ++n10;
                                    }
                                    n9 = 0;
                                    while (n9 < this.a.K) {
                                        if (this.a.d[n9] == n6) {
                                            this.f = false;
                                        }
                                        ++n9;
                                    }
                                    n8 = 0;
                                    while (n8 < this.a.az) {
                                        if (this.a.f[n8] == this.a.b[n6]) {
                                            this.f = false;
                                        }
                                        ++n8;
                                    }
                                    if (this.f) {
                                        this.a.f[this.a.az] = this.a.b[n6];
                                        ++this.a.az;
                                    }
                                }
                            }
                            ++n5;
                            ++n5;
                        }
                        ++n6;
                    }
                    ++n7;
                }
                n6 = 0;
                while (n6 < this.a.az) {
                    n5 = 0;
                    while (n5 < 120) {
                        if (this.a.f[n6] == this.a.b[n5]) {
                            if (this.a.f[n5] == 3 || this.a.f[n5] == 5 || this.a.f[n5] == 2) {
                                if (n3 == 1 && !this.a.f[n6].equals(this.a.b[17])) {
                                    this.a.e[this.a.ay] = this.a.f[n6];
                                    ++this.a.ay;
                                }
                                if (n3 == 2 && !this.a.f[n6].equals(this.a.b[17]) && !this.a.f[n6].equals(this.a.b[9])) {
                                    this.a.e[this.a.ay] = this.a.f[n6];
                                    ++this.a.ay;
                                }
                                if (!(n3 != 3 || this.a.f[n6].equals(this.a.b[17]) || this.a.f[n6].equals(this.a.b[9]) || this.a.f[n6].equals(this.a.b[10]))) {
                                    this.a.e[this.a.ay] = this.a.f[n6];
                                    ++this.a.ay;
                                }
                            } else {
                                this.a.d[this.a.ax] = this.a.f[n6];
                                ++this.a.ax;
                            }
                        }
                        ++n5;
                    }
                    ++n6;
                }
                this.a.aA = false;
            }
            this.a.at = 0;
            if (this.a.aB) {
                this.a.at = this.a.ay;
                if (this.a.at > this.a.as) {
                    this.a.at = this.a.as;
                }
                n3 = this.a.au;
                while (n3 < this.a.at + this.a.au) {
                    this.t = n3 == this.a.y ? 0xFFFFFF : 39423;
                    StringBuffer stringBuffer2 = new StringBuffer().append(this.a.e[n3]);
                    if (this.a.hasPointerEvents()) {
                        this.a.a.a(((k)this.a).a, stringBuffer2, data.k.a() >> 1, 52 + this.a.aI * (n3 - this.a.au) + this.a.aI / 2 + 4, this.t, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                    } else {
                        this.a.a.a(((k)this.a).a, stringBuffer2, data.k.a() >> 1, 40 + this.a.aI * (n3 - this.a.au) + this.a.aI / 2 + 8, this.t, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                    }
                    ++n3;
                }
            } else {
                this.a.at = this.a.ax;
                if (this.a.at > this.a.as) {
                    this.a.at = this.a.as;
                }
                n3 = this.a.au;
                while (n3 < this.a.at + this.a.au) {
                    this.t = n3 == this.a.y ? 0xFFFFFF : 39423;
                    StringBuffer stringBuffer3 = new StringBuffer(this.a.d[n3]);
                    if (this.a.hasPointerEvents()) {
                        this.a.a.a(((k)this.a).a, stringBuffer3, data.k.a() >> 1, 52 + this.a.aI * (n3 - this.a.au) + this.a.aI / 2 + 4, this.t, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                    } else {
                        this.a.a.a(((k)this.a).a, stringBuffer3, data.k.a() >> 1, 40 + this.a.aI * (n3 - this.a.au) + this.a.aI / 2 + 4, this.t, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                    }
                    ++n3;
                }
            }
        }
        if (this.a.hasPointerEvents()) {
            if (this.a.h) {
                this.a.a.a(((k)this.a).a, 655419, (data.k.a() >> 1) - 12, 40);
            } else if (this.a.au > 0) {
                this.a.a.a(((k)this.a).a, 655417, (data.k.a() >> 1) - 12, 40);
            } else {
                this.a.a.a(((k)this.a).a, 655421, (data.k.a() >> 1) - 12, 40);
            }
            if (this.a.i) {
                this.a.a.a(((k)this.a).a, 655420, (data.k.a() >> 1) - 12, n2);
            } else if (this.a.aB) {
                if (this.a.ay > this.a.as && this.a.au != this.a.ay - this.a.as) {
                    this.a.a.a(((k)this.a).a, 655418, (data.k.a() >> 1) - 12, n2);
                } else {
                    this.a.a.a(((k)this.a).a, 655422, (data.k.a() >> 1) - 12, n2);
                }
            } else if (this.a.ax > this.a.as && this.a.au != this.a.ax - this.a.as) {
                this.a.a.a(((k)this.a).a, 655418, (data.k.a() >> 1) - 12, n2);
            } else {
                this.a.a.a(((k)this.a).a, 655422, (data.k.a() >> 1) - 12, n2);
            }
        } else {
            if (this.a.h) {
                this.a.a.a(((k)this.a).a, 655407, (data.k.a() >> 1) - 8, 32);
            } else if (this.a.au > 0) {
                this.a.a.a(((k)this.a).a, 655405, (data.k.a() >> 1) - 8, 32);
            } else {
                this.a.a.a(((k)this.a).a, 655409, (data.k.a() >> 1) - 8, 32);
            }
            if (this.a.i) {
                this.a.a.a(((k)this.a).a, 655408, (data.k.a() >> 1) - 8, n2);
            } else if (this.a.aB) {
                if (this.a.ay > this.a.as && this.a.au != this.a.ay - this.a.as) {
                    this.a.a.a(((k)this.a).a, 655406, (data.k.a() >> 1) - 8, n2);
                } else {
                    this.a.a.a(((k)this.a).a, 655410, (data.k.a() >> 1) - 8, n2);
                }
            } else if (this.a.ax > this.a.as && this.a.au != this.a.ax - this.a.as) {
                this.a.a.a(((k)this.a).a, 655406, (data.k.a() >> 1) - 8, n2);
            } else {
                this.a.a.a(((k)this.a).a, 655410, (data.k.a() >> 1) - 8, n2);
            }
        }
        if (this.a.n > 500L) {
            if (this.a.h) {
                this.a.h = false;
            }
            if (this.a.i) {
                this.a.i = false;
            }
        }
    }

    public final void b(int n2) {
        if (this.a.aw) {
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
            this.a.a.a(((k)this.a).a, this.a.a.a(n2), data.k.a() / 2, data.k.b() / 3, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            this.a.a.a(((k)this.a).a, this.a.a.a(115), this.a.bb, this.a.bc, 0xFFFFFF, 0x40 | this.a.ba | 0x100, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            this.a.a.a(((k)this.a).a, this.a.a.a(116), this.a.bd, this.a.be, 0xFFFFFF, 328, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
        }
    }
}

