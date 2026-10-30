/*
 * Decompiled with CFR 0.152.
 */
package data;

import data.k;
import data.p;
import java.util.Random;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class x {
    private p a;
    public boolean a;
    public int a;
    public int b;
    public boolean b;
    public int c;
    public int[] a;
    public int[] b;
    public int d;
    public int e;
    public boolean c;
    private boolean d;
    private boolean e;
    public int[] c = false;
    public long a;
    private boolean f;
    private boolean g;
    private boolean h;
    private boolean i;
    private boolean j;
    private boolean k;
    private int i;
    private int j;
    public int f;
    public int g;
    private int[] d = false;
    private int k;
    private int l;
    private int m;
    private int n;
    private int[] e = false;
    private int[] f = false;
    private int o = 0;
    private int p = 0;
    private int q = 0;
    private int r = 0;
    private boolean l = true;
    private boolean m = true;
    private int s = 0;
    private int t = 0;
    private int u = 0;
    private int[] g = true;
    private boolean n = false;
    private int v = 0;
    private int w;
    private int x;
    private int y;
    private int z;
    private int A;
    private int B = 7;
    private int C = 0;
    private int D = 0;
    private int E;
    private int[] h;
    private int[] i = 0;
    private int[] j = 0;
    private int[][] a;
    private int[][] b = false;
    public int h = 0;
    private int[] k = 0;
    public Random a = 0L;

    public x(p p2) {
        this.b = new int[]{97, 94, 91, 87, 81, 77, 71, 64, 57, 50, 42, 34, 26, 17, 9, 0};
        this.c = new int[9];
        this.d = new int[5];
        this.e = new int[3];
        this.f = new int[3];
        this.g = new int[]{131171, 0, 0};
        this.a = new Random();
        this.a = p2;
    }

    public final void a() {
        int n2;
        int n3;
        int n4;
        if (this.a == 0) {
            if (this.a.aw) {
                this.a.aw = false;
                this.a.v = 0L;
                this.i();
                this.a.a.c();
            }
            if (this.b && ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1 || this.a.a.e == 3)) {
                this.a.v = 2001L;
                this.a.aK = true;
            }
            if (this.a.v > 2000L) {
                this.c = false;
                this.a.m = 0;
                this.a.a.f();
            }
            this.b = true;
            return;
        }
        if (this.a.aw) {
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
            ((k)this.a).a.setColor(26265);
            ((k)this.a).a.fillRect(20, data.k.b() / 11 + 30, data.k.a() - 40, data.k.b() - 60 - data.k.b() / 11 * 2);
        }
        ((k)this.a).a.setColor(0xFFFFFF);
        ((k)this.a).a.drawLine((data.k.a() >> 1) + this.i, (data.k.b() >> 1) - 3 + this.j, (data.k.a() >> 1) + 4 + this.i / 3 * 2, (data.k.b() >> 1) - 3 + this.j / 3 * 2 - 20);
        ((k)this.a).a.drawLine((data.k.a() >> 1) + 4 + this.i / 3 * 2, (data.k.b() >> 1) - 3 - 20 + this.j / 3 * 2, (data.k.a() >> 1) + 4 + 3 + this.i / 3, (data.k.b() >> 1) - 3 - 20 - 40 + this.j / 3);
        ((k)this.a).a.drawLine((data.k.a() >> 1) + 4 + 3 + this.i / 3, (data.k.b() >> 1) - 3 - 20 - 40 + this.j / 3, (data.k.a() >> 1) + 4 + 3 + 2, (data.k.b() >> 1) - 3 - 20 - 40 - 60 - 20);
        ((k)this.a).a.drawLine((data.k.a() >> 1) + this.i, (data.k.b() >> 1) + 3 + this.j, (data.k.a() >> 1) + this.f + 1, (data.k.b() >> 1) + this.g - 7);
        this.a.a.a(((k)this.a).a, 851979, (data.k.a() >> 1) + this.i, (data.k.b() >> 1) + this.j);
        if (this.k < 3) {
            this.a.a.a(((k)this.a).a, 851980, (data.k.a() >> 1) + this.f, (data.k.b() >> 1) + this.g);
            ++this.k;
        } else {
            this.a.a.a(((k)this.a).a, 851981, (data.k.a() >> 1) + this.f, (data.k.b() >> 1) + this.g);
            ++this.k;
            if (this.k == 6) {
                this.k = 0;
            }
        }
        this.g();
        this.h();
        if (this.c) {
            this.a = true;
            this.a(851976);
            if (this.b && ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1 || this.a.a.e == 3)) {
                this.a.v = 2001L;
                this.a.aK = true;
            }
            if (this.a.v > 2000L) {
                this.c = false;
                int n5 = this.b;
                this.a.q[n5] = this.a.q[n5] - 1;
                if (this.a.q[this.b] == 0) {
                    n4 = this.b;
                    while (n4 < this.a.aB - 1) {
                        this.a.m[n4] = this.a.m[n4 + 1];
                        this.a.q[n4] = this.a.q[n4 + 1];
                        ++n4;
                    }
                    --this.a.aB;
                }
                n4 = 0;
                while (n4 < this.a.n[14]) {
                    this.a.e += this.a.d / 100.0f;
                    if (this.a.e >= this.a.d) {
                        this.a.e = this.a.d;
                        break;
                    }
                    ++n4;
                }
                n3 = this.a.m[14];
                if (n3 < 0) {
                    n3 = -n3;
                    n2 = 0;
                    while (n2 < n3) {
                        this.a.c -= this.a.d / 100.0f;
                        if (this.a.c <= 0.0f) {
                            this.a.a.b();
                            break;
                        }
                        ++n2;
                    }
                } else {
                    n2 = 0;
                    while (n2 < this.a.m[14]) {
                        this.a.c += this.a.d / 100.0f;
                        if (this.a.c >= this.a.d) {
                            this.a.c = this.a.d;
                            break;
                        }
                        ++n2;
                    }
                }
                this.a.m = 0;
                this.a.a.f();
            }
            this.b = true;
            this.a.a.c();
        }
        if (this.e) {
            this.a = true;
            this.b(851976);
            if (this.j > -data.k.a()) {
                this.l = this.i;
                this.m = this.j;
                this.j -= 12;
                this.a.v = 0L;
            }
            if (this.b && ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1 || this.a.a.e == 3)) {
                this.a.v = 2001L;
                this.a.aK = true;
            }
            if (this.a.v > 2000L) {
                this.e = false;
                this.a.m = 0;
                this.a.a.f();
                this.a.a.a = true;
                n4 = 0;
                while (n4 < this.a.aB) {
                    if (this.a.m[n4] == this.n) {
                        this.a.q[n4] = this.a.q[n4] + 1;
                        this.a.a.a = false;
                    }
                    ++n4;
                }
                if (this.a.a.a) {
                    this.a.m[this.a.aB] = (byte)this.n;
                    this.a.q[this.a.aB] = 1;
                    ++this.a.aB;
                }
                int n6 = this.b;
                this.a.q[n6] = this.a.q[n6] - 1;
                if (this.a.q[this.b] == 0) {
                    n3 = this.b;
                    while (n3 < this.a.aB - 1) {
                        this.a.m[n3] = this.a.m[n3 + 1];
                        this.a.q[n3] = this.a.q[n3 + 1];
                        ++n3;
                    }
                    --this.a.aB;
                }
                n3 = 0;
                while (n3 < this.a.n[14]) {
                    this.a.e += this.a.d / 100.0f;
                    if (this.a.e >= this.a.d) {
                        this.a.e = this.a.d;
                        break;
                    }
                    ++n3;
                }
                n2 = this.a.m[14];
                if (n2 < 0) {
                    n2 = -n2;
                    int n7 = 0;
                    while (n7 < n2) {
                        this.a.c -= this.a.d / 100.0f;
                        if (this.a.c <= 0.0f) {
                            this.a.a.b();
                            break;
                        }
                        ++n7;
                    }
                } else {
                    int n8 = 0;
                    while (n8 < this.a.m[14]) {
                        this.a.c += this.a.d / 100.0f;
                        if (this.a.c >= this.a.d) {
                            this.a.c = this.a.d;
                            break;
                        }
                        ++n8;
                    }
                }
            }
            this.b = true;
            this.a.a.c();
        }
    }

    public final void b() {
        if (!this.e) {
            if ((this.a.bt & 1) != 0 || this.a.a.e == 4) {
                if (this.i > -((data.k.a() >> 1) - 23)) {
                    this.i -= 3;
                    this.a.aw = true;
                }
            } else if ((this.a.bt & 2) != 0 || this.a.a.e == 6) {
                if (this.i < (data.k.a() >> 1) - 23) {
                    this.i += 3;
                    this.a.aw = true;
                }
            } else if ((this.a.bt & 4) != 0 || this.a.a.e == 2) {
                if (!this.d) {
                    if (this.g > -((data.k.b() >> 1) - 37 - data.k.b() / 11)) {
                        this.j -= 3;
                    }
                } else {
                    this.j -= 3;
                }
                this.a.aw = true;
            } else if (((this.a.bt & 8) != 0 || this.a.a.e == 8) && this.g < (data.k.b() >> 1) - 37 - data.k.b() / 11) {
                this.j += 3;
                this.a.aw = true;
            }
            if (!this.d) {
                if (this.s == 3) {
                    this.j();
                    this.s = 0;
                }
                ++this.s;
            }
        }
        this.g = this.j + 3 + 17;
        this.f = this.a(this.i);
        if (this.d) {
            this.l = this.f;
            this.m = this.g;
            if (this.g < -((data.k.b() >> 1) - 37 - data.k.b() / 11)) {
                this.e = true;
                this.a.v = 0L;
                this.d = false;
            }
        }
    }

    private int a(int n2) {
        int n3 = 0;
        while (n3 < 4) {
            this.d[n3] = this.d[n3 + 1];
            ++n3;
        }
        this.d[4] = n2;
        return this.d[0];
    }

    private void g() {
        this.a.a.a(((k)this.a).a, this.g[this.v], (data.k.a() >> 1) + this.l, (data.k.b() >> 1) + this.m);
        if (this.d) {
            if (this.f >= 0) {
                this.g[0] = this.e[0];
                this.g[1] = this.e[1];
                this.g[2] = this.e[2];
                ++this.l;
                this.f = this.l;
                ++this.i;
                if (this.l >= (data.k.a() >> 1) - 23) {
                    this.c = true;
                    this.a.v = 0L;
                    this.d = false;
                }
            } else {
                this.g[0] = this.f[0];
                this.g[1] = this.f[1];
                this.g[2] = this.f[2];
                --this.l;
                this.f = this.l;
                --this.i;
                if (this.l <= -((data.k.a() >> 1) - 23)) {
                    this.c = true;
                    this.a.v = 0L;
                    this.d = false;
                }
            }
            if (this.g >= -((data.k.b() >> 1) - 37 - data.k.b() / 11)) {
                ++this.m;
                this.g = this.m;
                this.j = this.g - 3 - 17;
                if (this.m >= (data.k.b() >> 1) - 37 - data.k.b() / 11) {
                    this.c = true;
                    this.a.v = 0L;
                    this.d = false;
                }
            }
        }
        this.v = this.v == 2 ? 0 : ++this.v;
        if (!this.n) {
            if (!this.c && !this.d) {
                this.g[0] = this.e[0];
                this.g[1] = this.e[1];
                this.g[2] = this.e[2];
            }
            if (!this.c) {
                if (!this.d) {
                    if (this.l < data.k.a() / 2 && !this.e) {
                        ++this.l;
                    }
                    if (this.l == data.k.a() / 2) {
                        this.n = true;
                    }
                }
            } else {
                this.l += 4;
            }
        } else {
            if (!this.c && !this.d) {
                this.g[0] = this.f[0];
                this.g[1] = this.f[1];
                this.g[2] = this.f[2];
            }
            if (!this.c) {
                if (!this.d) {
                    if (this.l > -(data.k.a() / 2) && !this.e) {
                        --this.l;
                    }
                    if (this.l == -(data.k.a() / 2)) {
                        this.n = false;
                    }
                }
            } else {
                this.l -= 4;
            }
        }
        if (!this.d) {
            if (this.t == 0) {
                this.u = Math.abs(this.a.nextInt()) % 20;
                this.t = 5 + Math.abs(this.a.nextInt()) % (data.k.a() / 10);
            }
            if (this.u < 10) {
                if (this.m > -((data.k.b() >> 1) - 50 - data.k.b() / 11)) {
                    --this.m;
                }
            } else if (this.m < (data.k.b() >> 1) - 50 - data.k.b() / 11) {
                ++this.m;
            }
            --this.t;
        }
        this.a.aw = true;
    }

    private void h() {
        if ((this.a.bu & 0x10) != 0 || this.a.a.e == 5) {
            if (this.f < this.l + (this.o >> 1) && this.f > this.l - (this.o >> 1)) {
                if (this.g < this.m + (this.p >> 1) && this.g > this.m - (this.p >> 1)) {
                    this.d = true;
                    return;
                }
                this.c = true;
                this.a.v = 0L;
                return;
            }
            this.c = true;
            this.a.v = 0L;
        }
    }

    public final void a(int n2) {
        ((k)this.a).a.setColor(13158);
        ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
        this.a.a.a(((k)this.a).a, n2, data.k.a() >> 1, data.k.b() / 3);
        if (n2 == 851990) {
            this.a.a.a(((k)this.a).a, 851991, data.k.a() >> 1, data.k.b() / 3);
        }
        this.a.a.a(((k)this.a).a, this.a.a.a(83), data.k.a() >> 1, (data.k.b() >> 1) + 4, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
        this.a.a.c();
    }

    private void i() {
        ((k)this.a).a.setColor(13158);
        ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
        this.a.a.a(((k)this.a).a, this.a.a.a(85), data.k.a() >> 1, (data.k.b() >> 1) + 4, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
    }

    private void b(int n2) {
        ((k)this.a).a.setColor(13158);
        ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
        this.a.a.a(((k)this.a).a, n2, data.k.a() >> 1, data.k.b() / 3);
        switch (this.a.q) {
            case 0: {
                this.a.a.a(((k)this.a).a, this.a.a.a(84), data.k.a() >> 1, (data.k.b() >> 1) + 4, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                return;
            }
            case 1: {
                this.a.a.a(((k)this.a).a, this.a.a.a(86), data.k.a() >> 1, (data.k.b() >> 1) + 4, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            }
        }
    }

    private void j() {
        if (this.m) {
            if (this.r < 5) {
                ++this.i;
            }
            if (this.r == 5) {
                this.m = false;
            }
            ++this.r;
        } else {
            if (this.r > 0) {
                --this.i;
            }
            if (this.r == 0) {
                this.m = true;
            }
            --this.r;
        }
        if (this.l) {
            if (this.q < 3) {
                ++this.j;
            }
            if (this.q == 3) {
                this.l = false;
            }
            ++this.q;
            return;
        }
        if (this.q > 0) {
            --this.j;
        }
        if (this.q == 0) {
            this.l = true;
        }
        --this.q;
    }

    public final void c() {
        this.a = 0;
        this.b = -1;
        int n2 = 0;
        while (n2 < this.a.aB) {
            if (this.a.m[n2] == 90) {
                this.b = n2;
                this.a = this.a.q[n2];
            }
            ++n2;
        }
        if (this.a > 0) {
            this.c = false;
            this.a.v = 0L;
            this.d = false;
            this.l = -data.k.a() / 2 + 1;
            this.m = 0;
            this.f = 0;
            this.g = 0;
            this.i = 0;
            this.j = this.g - 3 - 17;
            int n3 = Math.abs(this.a.nextInt()) % 4;
            switch (n3) {
                case 0: {
                    this.n = 40;
                    this.e[0] = 131103;
                    this.e[1] = 131104;
                    this.e[2] = 131105;
                    this.f[0] = 131100;
                    this.f[1] = 131101;
                    this.f[2] = 131102;
                    this.o = 16;
                    this.p = 10;
                    break;
                }
                case 1: {
                    this.n = 56;
                    this.e[0] = 131109;
                    this.e[1] = 131110;
                    this.e[2] = 131111;
                    this.f[0] = 131106;
                    this.f[1] = 131107;
                    this.f[2] = 131108;
                    this.o = 16;
                    this.p = 9;
                    break;
                }
                case 2: {
                    this.n = 57;
                    this.e[0] = 131097;
                    this.e[1] = 131098;
                    this.e[2] = 131099;
                    this.f[0] = 131094;
                    this.f[1] = 131095;
                    this.f[2] = 131096;
                    this.o = 16;
                    this.p = 12;
                    break;
                }
                case 3: {
                    this.n = 58;
                    this.e[0] = 131171;
                    this.e[1] = 131172;
                    this.e[2] = 131173;
                    this.f[0] = 131168;
                    this.f[1] = 131169;
                    this.f[2] = 131170;
                    this.o = 20;
                    this.p = 14;
                }
            }
            int n4 = 0;
            while (n4 < 5) {
                this.d[n4] = this.i;
                ++n4;
            }
            this.b = false;
            return;
        }
        this.a.aw = true;
    }

    public final void d() {
        int n2;
        if (this.a == 0) {
            if (this.a.aw) {
                this.a.aw = false;
                this.a.v = 0L;
                this.o();
                this.a.a.c();
            }
            if (this.b && ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1 || this.a.a.e == 3)) {
                this.a.v = 2001L;
                this.a.aK = true;
            }
            if (this.a.v > 2000L) {
                this.c = false;
                this.a.m = 0;
                this.a.a.f();
            }
            this.b = true;
            return;
        }
        ((k)this.a).a.setColor(13158);
        ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b() - 44);
        ((k)this.a).a.setColor(26265);
        ((k)this.a).a.fillRect((data.k.a() >> 1) - (this.A >> 1), 35, this.A, data.k.b() - 80);
        if (this.g) {
            this.a.v = 0L;
            this.h = true;
            this.g = false;
        }
        if (this.h) {
            if (this.b && ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1 || this.a.a.e == 3)) {
                this.a.v = 4001L;
                this.a.aK = true;
            }
            if (this.a.v > 4000L) {
                this.h = false;
            }
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
            this.a.a.a(((k)this.a).a, 851986, data.k.a() >> 1, data.k.b() / 3);
            this.a.a.a(((k)this.a).a, this.a.a.a(87), data.k.a() >> 1, (data.k.b() >> 1) - 6, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            this.a.a.c();
        } else {
            this.l();
            this.k();
            if (!this.k || this.a.v <= 2000L) {
                this.a.a.a(((k)this.a).a, this.y, (data.k.a() >> 1) + this.w - 1, (data.k.b() >> 1) + this.x - 1);
            }
            this.m();
            if (this.a.aw) {
                ((k)this.a).a.setColor(13158);
                ((k)this.a).a.fillRect(0, data.k.b() - 44, data.k.a(), 44);
                String string = new Integer(this.D).toString() + "/10";
                String string2 = new Integer(this.B).toString() + "/5";
                this.a.a.a(((k)this.a).a, 131171, data.k.a() / 5, data.k.b() - 28);
                this.a.a.a(((k)this.a).a, new StringBuffer(string), data.k.a() / 5 + 32, data.k.b() - 24, 0xFFFF00, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                this.a.a.a(((k)this.a).a, 851984, data.k.a() / 5 * 3 + data.k.a() / 5 / 2, data.k.b() - 28);
                this.a.a.a(((k)this.a).a, new StringBuffer(string2), data.k.a() / 5 * 3 + data.k.a() / 5 / 2 + 32, data.k.b() - 24, 0xFFFF00, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                this.a.aw = false;
            }
            if (!this.f) {
                if (this.B == 5) {
                    ((k)this.a).a.setColor(13158);
                    ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
                    if (this.D >= 10) {
                        this.e = true;
                    } else {
                        ++this.B;
                        this.c = true;
                    }
                } else if (this.D >= 10) {
                    ((k)this.a).a.setColor(13158);
                    ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
                    this.e = true;
                }
            }
        }
        if (this.c) {
            this.a = true;
            this.a(851986);
            if (this.j) {
                this.a.v = 0L;
                this.j = false;
            }
            if (this.b && ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1 || this.a.a.e == 3)) {
                this.a.v = 4001L;
                this.a.aK = true;
            }
            if (this.a.v > 4000L) {
                this.c = false;
                int n3 = this.b;
                this.a.q[n3] = this.a.q[n3] - 1;
                if (this.a.q[this.b] == 0) {
                    int n4 = this.b;
                    while (n4 < this.a.aB - 1) {
                        this.a.m[n4] = this.a.m[n4 + 1];
                        this.a.q[n4] = this.a.q[n4 + 1];
                        ++n4;
                    }
                    --this.a.aB;
                }
                int n5 = 0;
                while (n5 < this.a.n[83]) {
                    this.a.e += this.a.d / 100.0f;
                    if (this.a.e >= this.a.d) {
                        this.a.e = this.a.d;
                        break;
                    }
                    ++n5;
                }
                int n6 = this.a.m[83];
                if (n6 < 0) {
                    n6 = -n6;
                    n2 = 0;
                    while (n2 < n6) {
                        this.a.c -= this.a.d / 100.0f;
                        if (this.a.c <= 0.0f) {
                            this.a.a.b();
                            break;
                        }
                        ++n2;
                    }
                } else {
                    n2 = 0;
                    while (n2 < this.a.m[83]) {
                        this.a.c += this.a.d / 100.0f;
                        if (this.a.c >= this.a.d) {
                            this.a.c = this.a.d;
                            break;
                        }
                        ++n2;
                    }
                }
                this.a.m = 0;
                this.a.a.f();
            }
            this.b = true;
            this.a.a.c();
        }
        if (this.e) {
            this.a = true;
            this.b(851986);
            if (this.j) {
                this.a.v = 0L;
                this.j = false;
            }
            if (this.b && ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1 || this.a.a.e == 3)) {
                this.a.v = 2001L;
                this.a.aK = true;
            }
            if (this.a.v > 2000L) {
                int n7;
                this.e = false;
                this.a.m = 0;
                this.a.a.f();
                this.a.a.a = true;
                byte by = 0;
                int n8 = Math.abs(this.a.nextInt()) % 3;
                switch (n8) {
                    case 0: {
                        by = 59;
                        break;
                    }
                    case 1: {
                        by = 60;
                        break;
                    }
                    case 2: {
                        by = 2;
                    }
                }
                n2 = 0;
                while (n2 < this.a.aB) {
                    if (this.a.m[n2] == by) {
                        this.a.q[n2] = this.a.q[n2] + 1;
                        this.a.a.a = false;
                    }
                    ++n2;
                }
                if (this.a.a.a) {
                    this.a.m[this.a.aB] = by;
                    this.a.q[this.a.aB] = 1;
                    ++this.a.aB;
                }
                int n9 = this.b;
                this.a.q[n9] = this.a.q[n9] - 1;
                if (this.a.q[this.b] == 0) {
                    n7 = this.b;
                    while (n7 < this.a.aB - 1) {
                        this.a.m[n7] = this.a.m[n7 + 1];
                        this.a.q[n7] = this.a.q[n7 + 1];
                        ++n7;
                    }
                    --this.a.aB;
                }
                n7 = 0;
                while (n7 < this.a.n[83]) {
                    this.a.e += this.a.d / 100.0f;
                    if (this.a.e >= this.a.d) {
                        this.a.e = this.a.d;
                        break;
                    }
                    ++n7;
                }
                int n10 = this.a.m[83];
                if (n10 < 0) {
                    n10 = -n10;
                    int n11 = 0;
                    while (n11 < n10) {
                        this.a.c -= this.a.d / 100.0f;
                        if (this.a.c <= 0.0f) {
                            this.a.a.b();
                            break;
                        }
                        ++n11;
                    }
                } else {
                    int n12 = 0;
                    while (n12 < this.a.m[83]) {
                        this.a.c += this.a.d / 100.0f;
                        if (this.a.c >= this.a.d) {
                            this.a.c = this.a.d;
                            break;
                        }
                        ++n12;
                    }
                }
            }
            this.a.a.c();
        }
        this.b = true;
    }

    public final void e() {
        if (!(this.a.aK || this.e || this.f || this.k || (this.a.bu & 0x10) == 0 && this.a.a.e != 5)) {
            ++this.B;
            this.f = true;
            this.a.aw = true;
        }
    }

    private void k() {
        if (this.f) {
            if (this.x + (this.z >> 1) >= (data.k.b() >> 1) - 45) {
                this.a.aw = true;
                if (this.i) {
                    int n2 = 0;
                    while (n2 < this.E) {
                        if (this.k[n2] == 1) {
                            ++this.C;
                            this.k[n2] = 2;
                        }
                        ++n2;
                    }
                    this.D += this.C;
                    this.a.v = 0L;
                    this.k = true;
                    this.i = false;
                }
                if (this.k) {
                    if (this.a.v > 2000L) {
                        this.k = false;
                        this.a.aw = true;
                        return;
                    }
                    if (this.a.v > 0L) {
                        this.a.aw = false;
                        return;
                    }
                } else {
                    this.x = -(data.k.b() >> 1) + 35 - (this.z >> 1);
                    this.f = false;
                    this.C = 0;
                    if (this.D < 10 && this.B < 5) {
                        this.n();
                        return;
                    }
                }
            } else {
                this.i = true;
                this.x += 5;
            }
        }
    }

    private void l() {
        if (this.D < 10 && this.B < 6) {
            int n2 = 0;
            while (n2 < this.E) {
                if (this.k[n2] != 2) {
                    this.a.a.a(((k)this.a).a, this.a[n2][this.h], this.h[n2], this.i[n2]);
                    if (!this.k) {
                        if (this.k[n2] != 1) {
                            if (this.j[n2] > 0) {
                                if (this.h[n2] + 8 + this.j[n2] >= data.k.a() + 70) {
                                    this.c(n2);
                                } else {
                                    int n3 = n2;
                                    this.h[n3] = this.h[n3] + this.j[n2];
                                }
                            } else if (this.h[n2] - 8 + this.j[n2] <= -70) {
                                this.c(n2);
                            } else {
                                int n4 = n2;
                                this.h[n4] = this.h[n4] + this.j[n2];
                            }
                        } else if (this.f) {
                            int n5 = n2;
                            this.i[n5] = this.i[n5] + 5;
                        }
                    } else if (this.k[n2] != 1) {
                        int n6;
                        if (this.h[n2] > data.k.a() >> 1) {
                            if (this.j[n2] < 0) {
                                n6 = 0;
                                while (n6 < 3) {
                                    this.a[n2][n6] = this.b[n2][n6];
                                    ++n6;
                                }
                            }
                            this.j[n2] = 10;
                        } else {
                            if (this.j[n2] > 0) {
                                n6 = 0;
                                while (n6 < 3) {
                                    this.a[n2][n6] = this.b[n2][n6];
                                    ++n6;
                                }
                            }
                            this.j[n2] = -10;
                        }
                        int n7 = n2;
                        this.h[n7] = this.h[n7] + this.j[n2];
                    }
                }
                ++n2;
            }
            ++this.h;
            if (this.h > 2) {
                this.h = 0;
            }
        }
    }

    private void m() {
        int n2 = 0;
        while (n2 < this.E) {
            if (this.k[n2] == 0 && this.h[n2] > (data.k.a() >> 1) + this.w - (this.A >> 1) && this.h[n2] < (data.k.a() >> 1) + this.w + (this.A >> 1) && this.i[n2] > (data.k.b() >> 1) + this.x - (this.z >> 1) && this.i[n2] < (data.k.b() >> 1) + this.x + (this.z >> 1)) {
                this.k[n2] = 1;
            }
            ++n2;
        }
    }

    public final void f() {
        this.a = 0;
        this.b = -1;
        int n2 = 0;
        while (n2 < this.a.aB) {
            if (this.a.m[n2] == 26) {
                this.b = n2;
                this.a = this.a.q[n2];
            }
            ++n2;
        }
        if (this.a > 0) {
            this.c = false;
            this.a.v = 0L;
            this.d = false;
            this.y = 851984;
            this.z = 16;
            this.A = 32;
            this.n();
            this.w = 0;
            this.x = -(data.k.b() >> 1) + 35 - (this.z >> 1);
            this.B = 0;
            this.C = 0;
            this.D = 0;
            this.b = true;
            this.g = true;
            this.i = true;
            this.j = true;
            this.f = false;
            this.a.aK = true;
            this.k = false;
            this.a.aw = false;
            return;
        }
        this.a.aw = true;
    }

    private void n() {
        this.E = 3 + Math.abs(this.a.nextInt()) % 5;
        this.h = new int[this.E];
        this.i = new int[this.E];
        this.j = new int[this.E];
        this.k = new int[this.E];
        this.a = new int[this.E][3];
        this.b = new int[this.E][3];
        int n2 = 0;
        while (n2 < this.E) {
            this.c(n2);
            ++n2;
        }
    }

    private void c(int n2) {
        int n3 = Math.abs(this.a.nextInt()) % 2;
        this.h[n2] = n3 == 0 ? -Math.abs(this.a.nextInt()) % 60 : data.k.a() + Math.abs(this.a.nextInt()) % 60;
        this.i[n2] = 42 + Math.abs(this.a.nextInt()) % (data.k.b() - 80 - 14);
        this.j[n2] = 2 + Math.abs(this.a.nextInt()) % 3;
        if (n3 != 0) {
            this.j[n2] = -this.j[n2];
        }
        this.k[n2] = 0;
        int n4 = Math.abs(this.a.nextInt()) % 100;
        if (n4 < 45) {
            if (n3 == 0) {
                this.a[n2][0] = 131133;
                this.a[n2][1] = 131134;
                this.a[n2][2] = 131135;
                this.b[n2][0] = 131130;
                this.b[n2][1] = 131131;
                this.b[n2][2] = 131132;
                return;
            }
            this.a[n2][0] = 131130;
            this.a[n2][1] = 131131;
            this.a[n2][2] = 131132;
            this.b[n2][0] = 131133;
            this.b[n2][1] = 131134;
            this.b[n2][2] = 131135;
            return;
        }
        if (n4 < 65) {
            if (n3 == 0) {
                this.a[n2][0] = 131139;
                this.a[n2][1] = 131140;
                this.a[n2][2] = 131141;
                this.b[n2][0] = 131136;
                this.b[n2][1] = 131137;
                this.b[n2][2] = 131138;
                return;
            }
            this.a[n2][0] = 131136;
            this.a[n2][1] = 131137;
            this.a[n2][2] = 131138;
            this.b[n2][0] = 131139;
            this.b[n2][1] = 131140;
            this.b[n2][2] = 131141;
            return;
        }
        if (n4 < 75) {
            if (n3 == 0) {
                this.a[n2][0] = 131097;
                this.a[n2][1] = 131098;
                this.a[n2][2] = 131099;
                this.b[n2][0] = 131094;
                this.b[n2][1] = 131095;
                this.b[n2][2] = 131096;
                return;
            }
            this.a[n2][0] = 131094;
            this.a[n2][1] = 131095;
            this.a[n2][2] = 131096;
            this.b[n2][0] = 131097;
            this.b[n2][1] = 131098;
            this.b[n2][2] = 131099;
            return;
        }
        if (n4 < 85) {
            if (n3 == 0) {
                this.a[n2][0] = 131103;
                this.a[n2][1] = 131104;
                this.a[n2][2] = 131105;
                this.b[n2][0] = 131100;
                this.b[n2][1] = 131101;
                this.b[n2][2] = 131102;
                return;
            }
            this.a[n2][0] = 131100;
            this.a[n2][1] = 131101;
            this.a[n2][2] = 131102;
            this.b[n2][0] = 131103;
            this.b[n2][1] = 131104;
            this.b[n2][2] = 131105;
            return;
        }
        if (n4 < 100) {
            if (n3 == 0) {
                this.a[n2][0] = 131109;
                this.a[n2][1] = 131110;
                this.a[n2][2] = 131111;
                this.b[n2][0] = 131106;
                this.b[n2][1] = 131107;
                this.b[n2][2] = 131108;
                return;
            }
            this.a[n2][0] = 131106;
            this.a[n2][1] = 131107;
            this.a[n2][2] = 131108;
            this.b[n2][0] = 131109;
            this.b[n2][1] = 131110;
            this.b[n2][2] = 131111;
        }
    }

    private void o() {
        ((k)this.a).a.setColor(13158);
        ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
        this.a.a.a(((k)this.a).a, this.a.a.a(88), data.k.a() >> 1, (data.k.b() >> 1) + 4, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
    }
}

