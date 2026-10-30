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
public final class n {
    private p a;
    public int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private boolean a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;
    private boolean f;
    private int h;
    private int i;
    private int j = 0;
    private int k = 0;
    private int l;
    private int[] a;
    private int[] b = true;
    private int[] c = false;
    private int m;
    private int[] d = false;
    private int[] e = false;
    private int[] f = true;
    private int[] g = 0;
    private int[] h = 0;
    private boolean[] a;
    public Random a = new Random();

    public n(p p2) {
        this.a = p2;
    }

    public final void a() {
        if (this.a.a.a == 0) {
            if (this.a.aw) {
                this.a.aw = false;
                this.a.v = 0L;
                this.m();
                this.a.a.c();
            }
            if (this.a.a.b && ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1 || this.a.a.e == 3)) {
                this.a.v = 2001L;
                this.a.aK = true;
            }
            if (this.a.v > 2000L) {
                this.a.a.c = false;
                this.a.m = 0;
                this.a.a.f();
            }
            this.a.a.b = true;
            return;
        }
        ((k)this.a).a.setColor(13158);
        ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b() - 44);
        if (this.a.aw) {
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, data.k.b() - 44, data.k.a(), data.k.b());
        }
        if (this.f) {
            this.a.x = 0L;
            this.e = true;
            this.f = false;
        }
        if (this.e) {
            if (this.a.a.b && ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1 || this.a.a.e == 3)) {
                this.a.x = 8001L;
                this.a.aK = true;
            }
            if (this.a.x > 8000L) {
                this.e = false;
            }
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
            this.a.a.a(((k)this.a).a, 851985, data.k.a() >> 1, data.k.b() / 3);
            this.a.a.a(((k)this.a).a, this.a.a.a(90), data.k.a() >> 1, (data.k.b() >> 1) - 6, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
            this.a.a.c();
            return;
        }
        if (!this.c && !this.d) {
            this.e();
            this.f();
            if (this.a.aw) {
                String string = new Integer(this.h).toString() + "/5";
                String string2 = new Integer(this.i).toString() + "/10";
                this.a.a.a(((k)this.a).a, 851970, data.k.a() / 7, data.k.b() - 32);
                this.a.a.a(((k)this.a).a, new StringBuffer(string), data.k.a() / 7 + 28, data.k.b() - 30, 0xFFFF00, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                this.a.a.a(((k)this.a).a, 851985, data.k.a() / 5 * 3 + data.k.a() / 5 / 2, data.k.b() - 32);
                this.a.a.a(((k)this.a).a, new StringBuffer(string2), data.k.a() / 5 * 3 + data.k.a() / 5 / 2 + 28, data.k.b() - 30, 0xFFFF00, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                this.a.aw = false;
            }
            this.g();
            this.h();
            this.i();
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655417, data.k.a() / 2 - 12, data.k.b() - 2 - 12 - 2 - 24 - 12);
                this.a.a.a(((k)this.a).a, 655411, data.k.a() / 2 - 24 - 2, data.k.b() - 2 - 12 - 2 - 24);
                this.a.a.a(((k)this.a).a, 655412, data.k.a() / 2 + 12 + 2, data.k.b() - 2 - 12 - 2 - 24);
                this.a.a.a(((k)this.a).a, 655418, data.k.a() / 2 - 12, data.k.b() - 2 - 12);
            }
        }
        if (this.d) {
            this.a.a.a = true;
            this.a.a.a(851985);
            if (this.a.a.b && ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1 || this.a.a.e == 3)) {
                this.a.x = 8001L;
                this.a.aK = true;
            }
            if (this.a.x > 8000L) {
                int n2;
                this.d = false;
                if (this.a >= 5) {
                    int n3 = this.a.a.b;
                    this.a.q[n3] = this.a.q[n3] - 1;
                    if (this.a.q[this.a.a.b] == 0) {
                        int n4 = this.a.a.b;
                        while (n4 < this.a.aB - 1) {
                            this.a.m[n4] = this.a.m[n4 + 1];
                            this.a.q[n4] = this.a.q[n4 + 1];
                            ++n4;
                        }
                        --this.a.aB;
                    }
                    this.a = 0;
                } else {
                    ++this.a;
                }
                int n5 = this.a.m[3];
                if (n5 < 0) {
                    n5 = -n5;
                    n2 = 0;
                    while (n2 < n5) {
                        this.a.c -= this.a.d / 100.0f;
                        if (this.a.c <= 0.0f) {
                            this.a.a.b();
                            break;
                        }
                        ++n2;
                    }
                } else {
                    n2 = 0;
                    while (n2 < this.a.m[3]) {
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
            this.a.a.b = true;
            this.a.a.c();
        }
        if (this.c) {
            this.a.a.a = true;
            this.l();
            if (this.a.a.b && ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1 || this.a.a.e == 3)) {
                this.a.x = 8001L;
                this.a.aK = true;
            }
            if (this.a.x > 8000L) {
                this.c = false;
                this.a.m = 0;
                this.a.a.f();
                this.a.a.a = true;
                int n6 = 0;
                while (n6 < this.a.aB) {
                    if (this.a.m[n6] == 1) {
                        this.a.q[n6] = this.a.q[n6] + 1;
                        this.a.a.a = false;
                    }
                    ++n6;
                }
                if (this.a.a.a) {
                    this.a.m[this.a.aB] = 1;
                    this.a.q[this.a.aB] = 1;
                    ++this.a.aB;
                }
                if (this.a >= 5) {
                    int n7 = this.a.a.b;
                    this.a.q[n7] = this.a.q[n7] - 1;
                    if (this.a.q[this.a.a.b] == 0) {
                        int n8 = this.a.a.b;
                        while (n8 < this.a.aB - 1) {
                            this.a.m[n8] = this.a.m[n8 + 1];
                            this.a.q[n8] = this.a.q[n8 + 1];
                            ++n8;
                        }
                        --this.a.aB;
                    }
                    this.a = 1;
                } else {
                    ++this.a;
                }
                int n9 = this.a.m[3];
                if (n9 < 0) {
                    n9 = -n9;
                    int n10 = 0;
                    while (n10 < n9) {
                        this.a.c -= this.a.d / 100.0f;
                        if (this.a.c <= 0.0f) {
                            this.a.a.b();
                            break;
                        }
                        ++n10;
                    }
                } else {
                    int n11 = 0;
                    while (n11 < this.a.m[3]) {
                        this.a.c += this.a.d / 100.0f;
                        if (this.a.c >= this.a.d) {
                            this.a.c = this.a.d;
                            break;
                        }
                        ++n11;
                    }
                }
                this.a.q = 1;
                this.a.a.f();
            }
            this.a.a.b = true;
            this.a.a.c();
        }
    }

    private void e() {
        if (!this.a) {
            ((k)this.a).a.setColor(26265);
            ((k)this.a).a.fillArc(this.c - this.a.a.e * (data.k.b() - 50 >> 1) / 100, this.d - this.a.a.e * (data.k.b() - 50 >> 1) / 100, 2 * this.a.a.e * (data.k.b() - 50 >> 1) / 100, 2 * this.a.a.e * (data.k.b() - 50 >> 1) / 100, this.a.a.c, 30);
        }
    }

    private void f() {
        int n2 = 0;
        while (n2 < this.l) {
            this.a.a.a(((k)this.a).a, this.c[n2], this.a[n2], this.b[n2]);
            ++n2;
        }
    }

    private void g() {
        int n2 = 0;
        while (n2 < this.m) {
            switch (this.f[n2]) {
                case 0: {
                    this.a.a.a(((k)this.a).a, 851970, this.d[n2], this.e[n2]);
                    break;
                }
                case 1: {
                    this.a.a.a(((k)this.a).a, 851970, this.d[n2] + this.j, this.e[n2]);
                    if ((long)this.g[n2] > 11000L) {
                        this.f[n2] = 0;
                        this.g[n2] = 0;
                        break;
                    }
                    int n3 = n2;
                    this.g[n3] = (int)((long)this.g[n3] + this.a.f);
                    break;
                }
                case 2: {
                    this.a.a.a(((k)this.a).a, 851970, this.d[n2], this.e[n2]);
                    if (this.e[n2] < data.k.b() - 50) {
                        int n4 = n2;
                        this.e[n4] = this.e[n4] + 5;
                        break;
                    }
                    this.e[n2] = data.k.b() - 50;
                    this.f[n2] = 3;
                    if (this.h < 5) break;
                    this.c = true;
                    this.a.x = 0L;
                    break;
                }
                case 3: {
                    this.a.a.a(((k)this.a).a, 851970, this.d[n2], this.e[n2]);
                }
            }
            ++n2;
        }
        if (this.j == 1) {
            this.k = 0;
        }
        if (this.j == -1) {
            this.k = 1;
        }
        if (this.k == 0) {
            --this.j;
            return;
        }
        ++this.j;
    }

    private void h() {
        this.a.a.a(((k)this.a).a, 851985, this.c, this.d);
        if (this.a) {
            this.d = data.k.b() - 50 - (-(this.e * (this.c - 10) * (this.c - 10) / this.f) + this.g * (this.c - 10) / this.a.a.b[this.a.a.d]);
            this.c = 10 + (int)(this.a.G * 2L * (long)this.a.a.b[this.a.a.d] / 100L * (long)this.a.a.e * (long)this.b / 100L / this.a.a.a);
            if (this.d > data.k.b() - 50) {
                this.a = false;
                this.c = 10;
                this.d = data.k.b() - 50;
                this.a.a.e = 40;
                this.a.a.d = 15;
                this.a.a.c = 70;
                int n2 = 0;
                while (n2 < this.m) {
                    this.h[n2] = 0;
                    ++n2;
                }
                if (this.i == 10 && this.h < 5) {
                    this.d = true;
                    this.a.x = 0L;
                }
            }
        }
    }

    private void i() {
        int n2 = 0;
        while (n2 < this.m) {
            if (this.h[n2] == 0 && this.d[n2] - 7 < this.c + 4 && this.d[n2] + 7 > this.c - 4 && this.e[n2] - 5 < this.d + 4 && this.e[n2] + 5 > this.d - 4) {
                if (this.f[n2] == 0) {
                    this.f[n2] = 1;
                    this.g[n2] = 0;
                    this.h[n2] = 1;
                } else if (this.f[n2] == 1) {
                    ++this.h;
                    this.a.aw = true;
                    this.f[n2] = 2;
                }
            }
            ++n2;
        }
    }

    public final void b() {
        this.a.a.a = 0;
        this.a.a.b = -1;
        int n2 = 0;
        while (n2 < this.a.aB) {
            if (this.a.m[n2] == 3) {
                this.a.a.b = n2;
                this.a.a.a = this.a.q[n2];
            }
            ++n2;
        }
        if (this.a.a.a > 0) {
            this.c = 10;
            this.d = data.k.b() - 50;
            this.a.a.e = 40;
            this.a.a.c = 70;
            this.h = 0;
            this.i = 0;
            this.b = data.k.b() - 50;
            this.c();
            this.j();
            this.k();
            this.a.a.b = true;
            this.a.aK = false;
            this.a = false;
            this.b = true;
            this.c = false;
            this.d = false;
            this.f = true;
            this.a.aw = true;
            return;
        }
        this.a.aw = true;
    }

    public final void c() {
        int n2 = data.k.b() - 50;
        int n3 = data.k.a() - 10 <= n2 ? data.k.a() - 10 : n2;
        while (this.b * this.b < n2 * n2 + n3 * n3) {
            ++this.b;
        }
    }

    private void j() {
        int n2 = (data.k.a() - 15) / 14 * ((data.k.b() / 3 - 30) / 5);
        this.l = 9 + Math.abs(this.a.nextInt()) % (data.k.a() / 12);
        if (this.l > n2) {
            this.l = n2;
        }
        this.a = new int[this.l];
        this.b = new int[this.l];
        this.c = new int[this.l];
        boolean[] blArray = new boolean[n2];
        int n3 = 0;
        while (n3 < n2) {
            blArray[n3] = false;
            ++n3;
        }
        int n4 = 0;
        while (n4 < this.l) {
            int n5 = Math.abs(this.a.nextInt()) % n2;
            while (blArray[n5]) {
                n5 = Math.abs(this.a.nextInt()) % n2;
            }
            blArray[n5] = true;
            this.a[n4] = 15 + 14 * (n5 % ((data.k.a() - 15) / 14));
            this.b[n4] = 30 + 5 * (n5 / ((data.k.a() - 15) / 14));
            int n6 = Math.abs(this.a.nextInt()) % 3;
            switch (n6) {
                case 0: {
                    this.c[n4] = 458805;
                    break;
                }
                case 1: {
                    this.c[n4] = 458806;
                    break;
                }
                case 2: {
                    this.c[n4] = 458807;
                }
            }
            ++n4;
        }
    }

    private void k() {
        this.m = 5 + Math.abs(this.a.nextInt()) % 3;
        this.d = new int[this.m];
        this.e = new int[this.m];
        this.f = new int[this.m];
        this.g = new int[this.m];
        this.h = new int[this.m];
        this.a = new boolean[this.l];
        int n2 = 0;
        while (n2 < this.l) {
            this.a[n2] = false;
            ++n2;
        }
        int n3 = 0;
        while (n3 < this.m) {
            int n4 = 3 + Math.abs(this.a.nextInt()) % (this.l - 3);
            while (this.a[n4] || this.a[n4] < 15 || this.a[n4] > data.k.a() - 7) {
                n4 = 3 + Math.abs(this.a.nextInt()) % (this.l - 3);
            }
            this.a[n4] = true;
            this.d[n3] = this.a[n4];
            this.e[n3] = this.b[n4];
            this.f[n3] = 0;
            this.g[n3] = 0;
            this.h[n3] = 0;
            ++n3;
        }
    }

    public final void d() {
        if (!(this.a || this.c || this.a.aK)) {
            if ((this.a.bu & 0x10) != 0 || this.a.a.e == 5) {
                if (!this.b) {
                    this.a = true;
                    this.a.a.d = (this.a.a.c + 15) / 5 - 3;
                    this.e = this.a.a.a[this.a.a.d] * 10000 / (this.a.a.b[this.a.a.d] * this.a.a.b[this.a.a.d]);
                    this.f = this.a.a.e * this.b;
                    this.g = 2 * this.a.a.a[this.a.a.d];
                    this.a.a.a = 3000 / data.k.a() * this.a.a.b[this.a.a.d] / 100 * this.a.a.e * this.b / 100 + 2000 / (data.k.b() - 50) * this.a.a.a[this.a.a.d] / 100 * this.a.a.e * this.b / 100;
                    this.a.G = 0L;
                    ++this.i;
                    this.a.aw = true;
                } else {
                    this.b = false;
                }
            }
            if ((this.a.bt & 4) != 0 || this.a.a.e == 2) {
                if (this.a.a.e < 100) {
                    this.a.a.e += 2;
                    return;
                }
            } else if ((this.a.bt & 8) != 0 || this.a.a.e == 8) {
                if (this.a.a.e > 40) {
                    this.a.a.e -= 2;
                    return;
                }
            } else if ((this.a.bt & 1) != 0 || this.a.a.e == 4) {
                if (this.a.a.c + 5 <= 70) {
                    this.a.a.c += 5;
                    return;
                }
            } else if (((this.a.bt & 2) != 0 || this.a.a.e == 6) && this.a.a.c - 5 >= 10) {
                this.a.a.c -= 5;
                return;
            }
        } else {
            this.a.aK = false;
        }
    }

    private void l() {
        ((k)this.a).a.setColor(13158);
        ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
        this.a.a.a(((k)this.a).a, 851985, data.k.a() >> 1, data.k.b() / 3);
        this.a.a.a(((k)this.a).a, this.a.a.a(89), data.k.a() >> 1, (data.k.b() >> 1) + 4, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
    }

    private void m() {
        ((k)this.a).a.setColor(13158);
        ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
        this.a.a.a(((k)this.a).a, this.a.a.a(91), data.k.a() >> 1, (data.k.b() >> 1) + 4, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
    }
}

