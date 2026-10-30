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
public final class s {
    private p a;
    private int a;
    private boolean a;
    private boolean b;
    private boolean c;
    private boolean d;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int[] a;
    private int h;
    private int i;
    private int j;
    private int[] b;
    private int[] c;
    private int[] d;
    private boolean[] a;
    private int[] e;
    private int[] f;
    private boolean[] b;
    private boolean[] c;
    private int[] g = 0;
    private int[] h;
    private int[] i;
    private int[] j;
    private boolean[] d = 0;
    private int[][] a;
    private int[][] b = false;
    private int[][] c = true;
    private int[] k;
    private int[] l;
    private boolean[] e;
    private boolean e = 0;
    private int k;
    private long a;
    private boolean f = true;
    private int l;
    public Random a = new int[]{851993, 851995, 851991, 851996, 851994, 851992};

    public s(p p2) {
        this.b = new int[10];
        this.c = new int[10];
        this.d = new int[10];
        this.a = new boolean[10];
        this.e = new int[9];
        this.f = new int[9];
        this.b = new boolean[10];
        this.c = new boolean[10];
        this.g = new int[10];
        this.h = new int[10];
        this.i = new int[10];
        this.j = new int[10];
        this.d = new boolean[10];
        this.a = new int[10][2];
        this.b = new int[10][2];
        this.c = new int[10][2];
        this.k = new int[10];
        this.l = new int[10];
        this.e = new boolean[10];
        this.a = new Random();
        this.a = p2;
    }

    public final void a() {
        int n2;
        int n3;
        if (this.a.a.a == 0) {
            if (this.a.aw) {
                this.a.aw = false;
                this.a.v = 0L;
                this.q();
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
        if (!this.d) {
            this.e();
            this.f();
            if (this.a.aw) {
                String string = new Integer(this.i).toString() + "/" + new Integer(10).toString();
                if (this.a.hasPointerEvents()) {
                    this.a.a.a(((k)this.a).a, 851991, data.k.a() / 7 - 16, data.k.b() - 32);
                    this.a.a.a(((k)this.a).a, new StringBuffer(string), data.k.a() / 7 + 16, data.k.b() - 30, 0xFFFF00, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                } else {
                    this.a.a.a(((k)this.a).a, 851991, data.k.a() / 2 - 16, data.k.b() - 32);
                    this.a.a.a(((k)this.a).a, new StringBuffer(string), data.k.a() / 2 + 16, data.k.b() - 30, 0xFFFF00, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
                }
                this.a.aw = false;
            }
            this.h();
            this.g();
            this.i();
            this.j();
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655417, data.k.a() / 2 - 12, data.k.b() - 2 - 12 - 2 - 24 - 12);
                this.a.a.a(((k)this.a).a, 655411, data.k.a() / 2 - 24 - 2, data.k.b() - 2 - 12 - 2 - 24);
                this.a.a.a(((k)this.a).a, 655412, data.k.a() / 2 + 12 + 2, data.k.b() - 2 - 12 - 2 - 24);
                this.a.a.a(((k)this.a).a, 655418, data.k.a() / 2 - 12, data.k.b() - 2 - 12);
            }
            if (this.e && this.a.D >= 1500L) {
                int n4 = 0;
                while (n4 < this.a.aT) {
                    if (this.a[n4]) {
                        switch (this.a.a.c[n4]) {
                            case 42: {
                                if (this.a.c[this.a.F] != -1) {
                                    this.a.c -= this.a.d / 100.0f * 25.0f;
                                    break;
                                }
                                this.a.c -= this.a.d / 100.0f * 50.0f;
                                break;
                            }
                            case 43: {
                                if (this.a.b[this.a.E] != -1) {
                                    this.a.c -= this.a.d / 100.0f * 25.0f;
                                    break;
                                }
                                this.a.c -= this.a.d / 100.0f * 50.0f;
                                break;
                            }
                            case 48: {
                                if (this.a.d[this.a.G] != -1) {
                                    this.a.c -= this.a.d / 100.0f * 5.0f;
                                    break;
                                }
                                this.a.c -= this.a.d / 100.0f * 10.0f;
                                break;
                            }
                            case 49: {
                                if (this.a.a[this.a.D] != -1) {
                                    this.a.c -= this.a.d / 100.0f * 10.0f;
                                    break;
                                }
                                this.a.c -= this.a.d / 100.0f * 20.0f;
                            }
                        }
                    }
                    ++n4;
                }
                this.d = true;
                this.a.z = 0L;
            }
        }
        if (this.d) {
            this.a.a.a = true;
            this.a.a.a(851990);
            if (this.a.a.b && ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1 || this.a.a.e == 3)) {
                this.a.z = 8001L;
                this.a.aK = true;
            }
            if (this.a.z > 8000L) {
                this.d = false;
                this.a.m = 0;
                this.a.a.f();
                int n5 = this.a.a.b;
                this.a.q[n5] = this.a.q[n5] - 1;
                if (this.a.q[this.a.a.b] == 0) {
                    int n6 = this.a.a.b;
                    while (n6 < this.a.aB - 1) {
                        this.a.m[n6] = this.a.m[n6 + 1];
                        this.a.q[n6] = this.a.q[n6 + 1];
                        ++n6;
                    }
                    --this.a.aB;
                }
                int n7 = 0;
                while (n7 < this.a.n[81]) {
                    this.a.e += this.a.d / 100.0f;
                    if (this.a.e >= this.a.d) {
                        this.a.e = this.a.d;
                        break;
                    }
                    ++n7;
                }
                n3 = this.a.m[81];
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
                    while (n2 < this.a.m[81]) {
                        this.a.c += this.a.d / 100.0f;
                        if (this.a.c >= this.a.d) {
                            this.a.c = this.a.d;
                            break;
                        }
                        ++n2;
                    }
                }
            }
            this.a.a.b = true;
        }
        if (this.b) {
            this.a.a.a = true;
            this.l();
            if (this.a.a.b && ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1 || this.a.a.e == 3)) {
                this.a.z = 8001L;
                this.a.aK = true;
            }
            if (this.a.z > 8000L) {
                this.b = false;
                this.a.m = 0;
                this.a.a.f();
                this.a.a.a = true;
                int n8 = 0;
                while (n8 < this.a.aB) {
                    if (this.a.m[n8] == this.a.a.c[this.a]) {
                        this.a.q[n8] = this.a.q[n8] + 1;
                        this.a.a.a = false;
                    }
                    ++n8;
                }
                if (this.a.a.a) {
                    this.a.m[this.a.aB] = (byte)this.a.a.c[this.a];
                    this.a.q[this.a.aB] = 1;
                    ++this.a.aB;
                }
                if (this.a.X) {
                    this.m();
                } else if (this.a.Y) {
                    this.n();
                } else if (this.a.V) {
                    this.o();
                } else if (this.a.W) {
                    this.p();
                }
                int n9 = this.a.a.b;
                this.a.q[n9] = this.a.q[n9] - 1;
                if (this.a.q[this.a.a.b] == 0) {
                    n3 = this.a.a.b;
                    while (n3 < this.a.aB - 1) {
                        this.a.m[n3] = this.a.m[n3 + 1];
                        this.a.q[n3] = this.a.q[n3 + 1];
                        ++n3;
                    }
                    --this.a.aB;
                }
                n3 = 0;
                while (n3 < this.a.n[81]) {
                    this.a.e += this.a.d / 100.0f;
                    if (this.a.e >= this.a.d) {
                        this.a.e = this.a.d;
                        break;
                    }
                    ++n3;
                }
                n2 = this.a.m[81];
                if (n2 < 0) {
                    n2 = -n2;
                    int n10 = 0;
                    while (n10 < n2) {
                        this.a.c -= this.a.d / 100.0f;
                        if (this.a.c <= 0.0f) {
                            this.a.a.b();
                            break;
                        }
                        ++n10;
                    }
                } else {
                    int n11 = 0;
                    while (n11 < this.a.m[81]) {
                        this.a.c += this.a.d / 100.0f;
                        if (this.a.c >= this.a.d) {
                            this.a.c = this.a.d;
                            break;
                        }
                        ++n11;
                    }
                }
            }
            this.a.a.b = true;
        }
    }

    private void e() {
        if (!this.a) {
            ((k)this.a).a.setColor(26265);
            ((k)this.a).a.fillArc(this.b - this.a.a.e * (data.k.b() - 50 >> 1) / 100, this.c - this.a.a.e * (data.k.b() - 50 >> 1) / 100, 2 * this.a.a.e * (data.k.b() - 50 >> 1) / 100, 2 * this.a.a.e * (data.k.b() - 50 >> 1) / 100, this.a.a.c, 30);
        }
    }

    private void f() {
        this.a.a.a(((k)this.a).a, 851990, 10, data.k.b() - 50);
    }

    private void g() {
        int n2 = 0;
        while (n2 < this.a.aT) {
            if (!this.b) {
                if (this.e && this.a[n2]) {
                    this.e[n2] = this.h[n2] + this.j[n2] * (int)this.a.D / 1500;
                    this.f[n2] = this.g[n2] + this.i[n2] * (int)this.a.D / 1500;
                    if (this.j[n2] > 0) {
                        if (this.a.r < 600L) {
                            this.a.a.a(((k)this.a).a, this.a[n2][0], this.e[n2], this.f[n2]);
                        } else {
                            this.a.a.a(((k)this.a).a, this.a[n2][1], this.e[n2], this.f[n2]);
                        }
                        if (this.a.r >= 1200L) {
                            this.a.r = 0L;
                        }
                    } else {
                        if (this.a.r < 600L) {
                            this.a.a.a(((k)this.a).a, this.b[n2][0], this.e[n2], this.f[n2]);
                        } else {
                            this.a.a.a(((k)this.a).a, this.b[n2][1], this.e[n2], this.f[n2]);
                        }
                        if (this.a.r >= 1200L) {
                            this.a.r = 0L;
                        }
                    }
                } else {
                    if (this.i[n2] >= 0) {
                        if (this.b[n2] && this.d[n2]) {
                            int n3 = n2;
                            this.f[n3] = this.f[n3] + this.g[n2];
                            int n4 = n2;
                            this.i[n4] = this.i[n4] - this.g[n2];
                            if (this.i[n2] < 0 || this.f[n2] > data.k.b() / 3) {
                                this.b[n2] = false;
                            }
                            if (this.f[n2] > data.k.b() / 3) {
                                this.f[n2] = data.k.b() / 3;
                            }
                        }
                    } else if (this.b[n2] && this.d[n2]) {
                        int n5 = n2;
                        this.f[n5] = this.f[n5] - this.g[n2];
                        int n6 = n2;
                        this.i[n6] = this.i[n6] + this.g[n2];
                        if (this.i[n2] >= 0 || this.f[n2] <= 45) {
                            this.b[n2] = false;
                        }
                        if (this.f[n2] <= 45) {
                            this.f[n2] = 45;
                        }
                    }
                    if (!(this.c[n2] || this.b[n2] || this.e[n2])) {
                        int n7 = Math.abs(this.a.nextInt()) % this.l;
                        if (n7 == 0) {
                            this.e[n2] = true;
                            this.a = (20 + Math.abs(this.a.nextInt()) % 30) * 100;
                            this.a.A = 0L;
                        } else {
                            this.i[n2] = Math.abs(this.a.nextInt()) % (2 * (data.k.b() / 3 - 30)) - (data.k.b() / 3 - 30);
                            this.j[n2] = Math.abs(this.a.nextInt()) % (4 * data.k.a() / 3 - 40) - (2 * data.k.a() / 3 - 20);
                            this.c[n2] = true;
                            this.b[n2] = true;
                        }
                    }
                    if (this.e[n2]) {
                        if (!this.f && this.a.A > this.a) {
                            this.e[n2] = false;
                        }
                        if (this.a.r < 600L) {
                            this.a.a.a(((k)this.a).a, this.c[n2][0], this.e[n2], this.f[n2]);
                        } else {
                            this.a.a.a(((k)this.a).a, this.c[n2][1], this.e[n2], this.f[n2]);
                        }
                        if (this.a.r >= 1200L) {
                            this.a.r = 0L;
                        }
                    } else if (this.j[n2] >= 0) {
                        if (this.c[n2] && this.d[n2]) {
                            int n8 = n2;
                            this.e[n8] = this.e[n8] + this.h[n2];
                            int n9 = n2;
                            this.j[n9] = this.j[n9] - this.h[n2];
                            if (this.j[n2] < 0 || this.e[n2] >= data.k.a() - 24) {
                                this.c[n2] = false;
                            }
                            if (this.e[n2] >= data.k.a() - 24) {
                                this.e[n2] = data.k.a() - 24;
                            }
                            if (this.a.r >= 1200L) {
                                this.a.r = 0L;
                            }
                        }
                        if (this.a.r < 600L) {
                            this.a.a.a(((k)this.a).a, this.a[n2][0], this.e[n2], this.f[n2]);
                        } else {
                            this.a.a.a(((k)this.a).a, this.a[n2][1], this.e[n2], this.f[n2]);
                        }
                    } else {
                        if (this.c[n2] && this.d[n2]) {
                            int n10 = n2;
                            this.e[n10] = this.e[n10] - this.h[n2];
                            int n11 = n2;
                            this.j[n11] = this.j[n11] + this.h[n2];
                            if (this.j[n2] >= 0 || this.e[n2] < 24) {
                                this.c[n2] = false;
                            }
                            if (this.e[n2] < 24) {
                                this.e[n2] = 24;
                            }
                            if (this.a.r >= 1200L) {
                                this.a.r = 0L;
                            }
                        }
                        if (this.a.r < 600L) {
                            this.a.a.a(((k)this.a).a, this.b[n2][0], this.e[n2], this.f[n2]);
                        } else {
                            this.a.a.a(((k)this.a).a, this.b[n2][1], this.e[n2], this.f[n2]);
                        }
                    }
                }
            }
            ++n2;
        }
    }

    private void h() {
        int n2 = 0;
        while (n2 < 10) {
            if (this.d[n2] == 3) {
                this.a.a.a(((k)this.a).a, 851992, this.b[n2], this.c[n2] - 7);
            }
            ++n2;
        }
    }

    private void i() {
        int n2 = 0;
        int n3 = 0;
        if (this.d[this.k] == 1) {
            if (this.b[this.k] < this.g) {
                this.c[this.k] = data.k.b() - 50 - (-(this.d * (this.b[this.k] - 10) * (this.b[this.k] - 10) / this.e) + this.f * (this.b[this.k] - 10) / this.a.a.b[this.a.a.d]);
                this.b[this.k] = 10 + (int)(this.a.G * 2L * (long)this.a.a.b[this.a.a.d] / 100L * (long)this.a.a.e * (long)this.j / 100L / this.a.a.a);
                if (this.a.a.d > 12) {
                    if (this.b[this.k] >= this.g - 1 && this.h < 4) {
                        this.h = 4;
                        n2 = -4;
                    } else if (this.b[this.k] >= this.g - 2 && this.h < 3) {
                        this.h = 3;
                        n2 = -3;
                    } else if (this.b[this.k] >= this.g - 3 && this.h < 2) {
                        this.h = 2;
                        n3 = -4;
                    } else if (this.b[this.k] >= this.g - 4 && this.h < 1) {
                        this.h = 1;
                        n3 = -4;
                    }
                } else if (this.b[this.k] >= this.g - 2 && this.h < 4) {
                    this.h = 4;
                    n2 = -4;
                } else if (this.b[this.k] >= this.g - 4 && this.h < 3) {
                    this.h = 3;
                    n2 = -3;
                } else if (this.b[this.k] >= this.g - 6 && this.h < 2) {
                    this.h = 2;
                    n3 = -4;
                } else if (this.b[this.k] >= this.g - 8 && this.h < 1) {
                    this.h = 1;
                    n3 = -4;
                }
            } else {
                this.d[this.k] = 2;
            }
        }
        if (this.d[this.k] != 0) {
            if (this.d[this.k] == 1) {
                this.a.a.a(((k)this.a).a, this.a[this.h], this.b[this.k] + n2, this.c[this.k] + n3);
                return;
            }
            this.a.a.a(((k)this.a).a, 851992, this.b[this.k], this.c[this.k] - 7);
            return;
        }
        switch (this.h) {
            case 0: {
                n2 = -4;
                n3 = 0;
                break;
            }
            case 1: {
                n2 = -3;
                n3 = 0;
                break;
            }
            case 2: {
                n2 = 0;
                n3 = 0;
                break;
            }
            case 3: {
                n2 = 0;
                n3 = 2;
            }
        }
        this.a.a.a(((k)this.a).a, this.a[this.h], 10 + n2, data.k.b() - 50 + n3);
    }

    private void j() {
        if (!this.b && !this.e && this.d[this.k] == 2) {
            int n2 = 0;
            while (n2 < this.a.aT) {
                if (this.e[n2] - (this.k[n2] >> 1) < this.b[this.k] + 8 && this.e[n2] + (this.k[n2] >> 1) > this.b[this.k]) {
                    if (this.f[n2] - (this.l[n2] >> 1) < this.c[this.k] + 1 && this.f[n2] + (this.l[n2] >> 1) > this.c[this.k] - 7) {
                        this.b = true;
                        this.a = n2;
                        this.d[n2] = false;
                        this.a.z = 0L;
                    } else if (this.k == 0) {
                        this.i[n2] = Math.abs(this.a.nextInt()) % (2 * (data.k.b() / 3 - 30)) - (data.k.b() / 3 - 30);
                        this.j[n2] = Math.abs(this.a.nextInt()) % (4 * data.k.a() / 3) - 2 * data.k.a() / 3;
                        this.b[n2] = true;
                        this.c[n2] = true;
                        this.f = false;
                    }
                } else if (this.k == 0) {
                    this.i[n2] = Math.abs(this.a.nextInt()) % (2 * (data.k.b() / 3 - 30)) - (data.k.b() / 3 - 30);
                    this.j[n2] = Math.abs(this.a.nextInt()) % (4 * data.k.a() / 3) - 2 * data.k.a() / 3;
                    this.b[n2] = true;
                    this.c[n2] = true;
                    this.f = false;
                }
                ++n2;
            }
            if (this.k == 9) {
                int n3 = 0;
                while (n3 < this.a.aT) {
                    if (this.a[n3]) {
                        this.e = true;
                        this.a.D = 0L;
                        this.j[n3] = this.b - this.e[n3];
                        this.i[n3] = this.c - this.f[n3];
                        this.h[n3] = this.e[n3];
                        this.g[n3] = this.f[n3];
                    }
                    ++n3;
                }
                if (!this.e && !this.b) {
                    this.d = !this.b;
                    this.a.z = 0L;
                }
            } else {
                this.d[this.k] = 3;
                ++this.k;
                this.a = false;
                this.a.a.c = 70;
                this.a.a.e = 40;
            }
            this.h = 0;
        }
    }

    public final void b() {
        this.a.a.a = 0;
        this.a.a.b = -1;
        int n2 = 0;
        while (n2 < this.a.aB) {
            if (this.a.m[n2] == 45) {
                this.a.a.b = n2;
                this.a.a.a = this.a.q[n2];
            }
            ++n2;
        }
        if (this.a.a.a > 0) {
            this.b = 10;
            this.c = data.k.b() - 50;
            int n3 = 0;
            while (n3 < 10) {
                this.b[n3] = 10;
                this.c[n3] = data.k.b() - 50;
                this.d[n3] = 0;
                ++n3;
            }
            this.h = 0;
            this.k = 0;
            this.a.a.e = 40;
            this.a.a.c = 70;
            this.i = 0;
            this.e = false;
            this.l = 0;
            this.j = data.k.b() - 50;
            this.c();
            int n4 = 0;
            while (n4 < this.a.aT) {
                this.d[n4] = true;
                this.b[n4] = false;
                this.c[n4] = false;
                this.e[n4] = true;
                this.a[n4] = false;
                ++n4;
            }
            this.k();
            this.a.a.b = true;
            this.a.aK = false;
            this.a = false;
            this.c = true;
            this.b = false;
            this.d = false;
            this.a.aw = true;
            this.f = true;
            return;
        }
        this.a.aw = true;
    }

    public final void c() {
        int n2 = data.k.b() - 50;
        int n3 = data.k.a() - 10 <= n2 ? data.k.a() - 10 : n2;
        while (this.j * this.j < n2 * n2 + n3 * n3) {
            ++this.j;
        }
    }

    private void k() {
        int n2 = 0;
        while (n2 < this.a.aT) {
            switch (this.a.a.c[n2]) {
                case 4: {
                    this.a[n2][0] = 131074;
                    this.a[n2][1] = 131075;
                    this.b[n2][0] = 131072;
                    this.b[n2][1] = 131073;
                    this.c[n2][0] = 131076;
                    this.c[n2][1] = 131077;
                    this.k[n2] = 24;
                    this.l[n2] = 20;
                    this.g[n2] = 2;
                    this.h[n2] = 2;
                    break;
                }
                case 41: {
                    this.a[n2][0] = 131112;
                    this.a[n2][1] = 131113;
                    this.b[n2][0] = 131112;
                    this.b[n2][1] = 131113;
                    this.c[n2][0] = 131112;
                    this.c[n2][1] = 131113;
                    this.k[n2] = 16;
                    this.l[n2] = 15;
                    this.g[n2] = 2;
                    this.h[n2] = 2;
                    break;
                }
                case 42: {
                    this.a[n2][0] = 131088;
                    this.a[n2][1] = 131089;
                    this.b[n2][0] = 131086;
                    this.b[n2][1] = 131087;
                    this.c[n2][0] = 131088;
                    this.c[n2][1] = 131089;
                    this.k[n2] = 48;
                    this.l[n2] = 14;
                    this.g[n2] = 2;
                    this.h[n2] = 2;
                    this.a[n2] = true;
                    break;
                }
                case 43: {
                    this.a[n2][0] = 131160;
                    this.a[n2][1] = 131161;
                    this.b[n2][0] = 131158;
                    this.b[n2][1] = 131159;
                    this.c[n2][0] = 131160;
                    this.c[n2][1] = 131161;
                    this.k[n2] = 32;
                    this.l[n2] = 24;
                    this.g[n2] = 2;
                    this.h[n2] = 2;
                    this.a[n2] = true;
                    break;
                }
                case 44: {
                    this.a[n2][0] = 131084;
                    this.a[n2][1] = 131085;
                    this.b[n2][0] = 131082;
                    this.b[n2][1] = 131083;
                    this.c[n2][0] = 131084;
                    this.c[n2][1] = 131085;
                    this.k[n2] = 24;
                    this.l[n2] = 18;
                    this.g[n2] = 2;
                    this.h[n2] = 2;
                    break;
                }
                case 46: {
                    this.a[n2][0] = 131092;
                    this.a[n2][1] = 131093;
                    this.b[n2][0] = 131090;
                    this.b[n2][1] = 131091;
                    this.c[n2][0] = 131092;
                    this.c[n2][1] = 131093;
                    this.k[n2] = 20;
                    this.l[n2] = 12;
                    this.g[n2] = 2;
                    this.h[n2] = 2;
                    break;
                }
                case 47: {
                    this.a[n2][0] = 131080;
                    this.a[n2][1] = 131081;
                    this.b[n2][0] = 131078;
                    this.b[n2][1] = 131079;
                    this.c[n2][0] = 131080;
                    this.c[n2][1] = 131080;
                    this.k[n2] = 20;
                    this.l[n2] = 16;
                    this.g[n2] = 1;
                    this.h[n2] = 1;
                    break;
                }
                case 48: {
                    this.a[n2][0] = 131126;
                    this.a[n2][1] = 131127;
                    this.b[n2][0] = 131124;
                    this.b[n2][1] = 131125;
                    this.c[n2][0] = 131128;
                    this.c[n2][1] = 131129;
                    this.k[n2] = 24;
                    this.l[n2] = 7;
                    this.g[n2] = 2;
                    this.h[n2] = 2;
                    this.a[n2] = true;
                    break;
                }
                case 49: {
                    this.a[n2][0] = 131120;
                    this.a[n2][1] = 131121;
                    this.b[n2][0] = 131118;
                    this.b[n2][1] = 131119;
                    this.c[n2][0] = 131122;
                    this.c[n2][1] = 131123;
                    this.k[n2] = 28;
                    this.l[n2] = 7;
                    this.g[n2] = 2;
                    this.h[n2] = 2;
                    this.a[n2] = true;
                    break;
                }
                case 51: {
                    this.a[n2][0] = 131180;
                    this.a[n2][1] = 131181;
                    this.b[n2][0] = 131178;
                    this.b[n2][1] = 131179;
                    this.c[n2][0] = 131174;
                    this.c[n2][1] = 131175;
                    this.k[n2] = 19;
                    this.l[n2] = 16;
                    this.g[n2] = 2;
                    this.h[n2] = 2;
                    break;
                }
                case 52: {
                    this.a[n2][0] = 131148;
                    this.a[n2][1] = 131149;
                    this.b[n2][0] = 131146;
                    this.b[n2][1] = 131147;
                    this.c[n2][0] = 131142;
                    this.c[n2][1] = 131143;
                    this.k[n2] = 19;
                    this.l[n2] = 19;
                    this.g[n2] = 2;
                    this.h[n2] = 2;
                    break;
                }
                case 53: {
                    this.a[n2][0] = 131156;
                    this.a[n2][1] = 131157;
                    this.b[n2][0] = 131154;
                    this.b[n2][1] = 131155;
                    this.c[n2][0] = 131150;
                    this.c[n2][1] = 131151;
                    this.k[n2] = 24;
                    this.l[n2] = 21;
                    this.g[n2] = 2;
                    this.h[n2] = 2;
                    break;
                }
                case 61: {
                    this.a[n2][0] = 131116;
                    this.a[n2][1] = 131117;
                    this.b[n2][0] = 131114;
                    this.b[n2][1] = 131115;
                    this.c[n2][0] = 131116;
                    this.c[n2][1] = 131116;
                    this.k[n2] = 32;
                    this.l[n2] = 11;
                    this.g[n2] = 2;
                    this.h[n2] = 2;
                    break;
                }
                case 62: {
                    this.a[n2][0] = 131192;
                    this.a[n2][1] = 131193;
                    this.b[n2][0] = 131190;
                    this.b[n2][1] = 131191;
                    this.c[n2][0] = 131190;
                    this.c[n2][1] = 131191;
                    this.k[n2] = 20;
                    this.l[n2] = 20;
                    this.g[n2] = 2;
                    this.h[n2] = 2;
                    break;
                }
                case 63: {
                    this.a[n2][0] = 131188;
                    this.a[n2][1] = 131189;
                    this.b[n2][0] = 131186;
                    this.b[n2][1] = 131187;
                    this.c[n2][0] = 131184;
                    this.c[n2][1] = 131185;
                    this.k[n2] = 20;
                    this.l[n2] = 30;
                    this.g[n2] = 2;
                    this.h[n2] = 2;
                }
            }
            this.e[n2] = (data.k.a() >> 1) + Math.abs(this.a.nextInt()) % ((data.k.a() >> 1) - 24);
            this.f[n2] = 40 + Math.abs(this.a.nextInt()) % (data.k.b() / 3 - 30 - 10);
            ++n2;
        }
    }

    public final void d() {
        if (!(this.a || this.b || this.a.aK)) {
            if ((this.a.bu & 0x10) != 0 || this.a.a.e == 5) {
                if (!this.c) {
                    this.a = true;
                    this.a.a.d = (this.a.a.c + 15) / 5 - 3;
                    this.d = this.a.a.a[this.a.a.d] * 10000 / (this.a.a.b[this.a.a.d] * this.a.a.b[this.a.a.d]);
                    this.e = this.a.a.e * this.j;
                    this.f = 2 * this.a.a.a[this.a.a.d];
                    this.g = this.a.a.b[this.a.a.d] * this.a.a.e * this.j / 10000 + 10;
                    this.a.a.a = 3000 / data.k.a() * this.a.a.b[this.a.a.d] / 100 * this.a.a.e * this.j / 100 + 2000 / (data.k.b() - 50) * this.a.a.a[this.a.a.d] / 100 * this.a.a.e * this.j / 100;
                    this.a.G = 0L;
                    ++this.i;
                    this.a.aw = true;
                    this.d[this.k] = 1;
                    this.l += 4;
                } else {
                    this.c = false;
                }
            }
            if ((this.a.bt & 4) != 0 || this.a.a.e == 2) {
                if (this.a.a.e < 100) {
                    this.a.a.e += 2;
                }
            } else if ((this.a.bt & 8) != 0 || this.a.a.e == 8) {
                if (this.a.a.e > 40) {
                    this.a.a.e -= 2;
                }
            } else if ((this.a.bt & 1) != 0 || this.a.a.e == 4) {
                if (this.a.a.c + 5 <= 70) {
                    this.a.a.c += 5;
                }
            } else if (((this.a.bt & 2) != 0 || this.a.a.e == 6) && this.a.a.c - 5 >= 10) {
                this.a.a.c -= 5;
            }
            if (this.a.a.c < 25) {
                this.h = 3;
                return;
            }
            if (this.a.a.c < 35) {
                this.h = 2;
                return;
            }
            if (this.a.a.c < 65) {
                this.h = 1;
                return;
            }
            this.h = 0;
            return;
        }
        this.a.aK = false;
    }

    private void l() {
        ((k)this.a).a.setColor(13158);
        ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
        this.a.a.a(((k)this.a).a, 851990, data.k.a() >> 1, data.k.b() / 3);
        this.a.a.a(((k)this.a).a, 851991, data.k.a() >> 1, data.k.b() / 3);
        String string = this.a.a.a(93).toString() + this.a.b[this.a.a.c[this.a]];
        this.a.a.a(((k)this.a).a, new StringBuffer(string), data.k.a() >> 1, (data.k.b() >> 1) + 4, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
        this.a.a.c();
    }

    private void m() {
        if (this.a.l[this.a.P - 180] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 180] = 0;
            return;
        }
        if (this.a.l[this.a.P] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P] = 0;
            return;
        }
        if (this.a.l[this.a.P + 180] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 180] = 0;
            return;
        }
        if (this.a.l[this.a.P + 360] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 360] = 0;
            return;
        }
        if (this.a.l[this.a.P + 540] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 540] = 0;
            return;
        }
        if (this.a.l[this.a.P - 180 - 1] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 180 - 1] = 0;
            return;
        }
        if (this.a.l[this.a.P - 1] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 1] = 0;
            return;
        }
        if (this.a.l[this.a.P + 180 - 1] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 180 - 1] = 0;
            return;
        }
        if (this.a.l[this.a.P + 360 - 1] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 360 - 1] = 0;
            return;
        }
        if (this.a.l[this.a.P + 540 - 1] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 540 - 1] = 0;
            return;
        }
        if (this.a.l[this.a.P - 180 - 2] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 180 - 2] = 0;
            return;
        }
        if (this.a.l[this.a.P - 2] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 2] = 0;
            return;
        }
        if (this.a.l[this.a.P + 180 - 2] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 180 - 2] = 0;
            return;
        }
        if (this.a.l[this.a.P + 360 - 2] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 360 - 2] = 0;
            return;
        }
        if (this.a.l[this.a.P + 540 - 2] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 540 - 2] = 0;
        }
    }

    private void n() {
        if (this.a.l[this.a.P - 180 + 2] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 180 + 2] = 0;
            return;
        }
        if (this.a.l[this.a.P + 2] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 2] = 0;
            return;
        }
        if (this.a.l[this.a.P + 180 + 2] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 180 + 2] = 0;
            return;
        }
        if (this.a.l[this.a.P + 360 + 2] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 360 + 2] = 0;
            return;
        }
        if (this.a.l[this.a.P + 540 + 2] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 540 + 2] = 0;
            return;
        }
        if (this.a.l[this.a.P - 180 + 3] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 180 + 3] = 0;
            return;
        }
        if (this.a.l[this.a.P + 3] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 3] = 0;
            return;
        }
        if (this.a.l[this.a.P + 180 + 3] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 180 + 3] = 0;
            return;
        }
        if (this.a.l[this.a.P + 360 + 3] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 360 + 3] = 0;
            return;
        }
        if (this.a.l[this.a.P + 540 + 3] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 540 + 3] = 0;
            return;
        }
        if (this.a.l[this.a.P - 180 + 4] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 180 + 4] = 0;
            return;
        }
        if (this.a.l[this.a.P + 4] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 4] = 0;
            return;
        }
        if (this.a.l[this.a.P + 180 + 4] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 180 + 4] = 0;
            return;
        }
        if (this.a.l[this.a.P + 360 + 4] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 360 + 4] = 0;
            return;
        }
        if (this.a.l[this.a.P + 540 + 4] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 540 + 4] = 0;
        }
    }

    private void o() {
        if (this.a.l[this.a.P - 1] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 1] = 0;
            return;
        }
        if (this.a.l[this.a.P] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P] = 0;
            return;
        }
        if (this.a.l[this.a.P + 1] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 1] = 0;
            return;
        }
        if (this.a.l[this.a.P + 2] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 2] = 0;
            return;
        }
        if (this.a.l[this.a.P + 3] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 3] = 0;
            return;
        }
        if (this.a.l[this.a.P - 1 - 180] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 1 - 180] = 0;
            return;
        }
        if (this.a.l[this.a.P - 180] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 180] = 0;
            return;
        }
        if (this.a.l[this.a.P + 1 - 180] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 1 - 180] = 0;
            return;
        }
        if (this.a.l[this.a.P + 2 - 180] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 2 - 180] = 0;
            return;
        }
        if (this.a.l[this.a.P + 3 - 180] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 3 - 180] = 0;
            return;
        }
        if (this.a.l[this.a.P - 1 - 360] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 1 - 360] = 0;
            return;
        }
        if (this.a.l[this.a.P - 360] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 360] = 0;
            return;
        }
        if (this.a.l[this.a.P + 1 - 360] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 1 - 360] = 0;
            return;
        }
        if (this.a.l[this.a.P + 2 - 360] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 2 - 360] = 0;
            return;
        }
        if (this.a.l[this.a.P + 3 - 360] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 3 - 360] = 0;
        }
    }

    private void p() {
        if (this.a.l[this.a.P - 1 + 360] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 1 + 360] = 0;
            return;
        }
        if (this.a.l[this.a.P + 360] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 360] = 0;
            return;
        }
        if (this.a.l[this.a.P + 1 + 360] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 1 + 360] = 0;
            return;
        }
        if (this.a.l[this.a.P + 2 + 360] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 2 + 360] = 0;
            return;
        }
        if (this.a.l[this.a.P + 3 + 360] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 3 + 360] = 0;
            return;
        }
        if (this.a.l[this.a.P - 1 + 540] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 1 + 540] = 0;
            return;
        }
        if (this.a.l[this.a.P + 540] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 540] = 0;
            return;
        }
        if (this.a.l[this.a.P + 1 + 540] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 1 + 540] = 0;
            return;
        }
        if (this.a.l[this.a.P + 2 + 540] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 2 + 540] = 0;
            return;
        }
        if (this.a.l[this.a.P + 3 + 540] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 3 + 540] = 0;
            return;
        }
        if (this.a.l[this.a.P - 1 + 720] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P - 1 + 720] = 0;
            return;
        }
        if (this.a.l[this.a.P + 720] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 720] = 0;
            return;
        }
        if (this.a.l[this.a.P + 1 + 720] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 1 + 720] = 0;
            return;
        }
        if (this.a.l[this.a.P + 2 + 720] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 2 + 720] = 0;
            return;
        }
        if (this.a.l[this.a.P + 3 + 720] == this.a.a.c[this.a] + 1) {
            this.a.l[this.a.P + 3 + 720] = 0;
        }
    }

    private void q() {
        ((k)this.a).a.setColor(13158);
        ((k)this.a).a.fillRect(0, 0, data.k.a(), data.k.b());
        this.a.a.a(((k)this.a).a, this.a.a.a(92), data.k.a() >> 1, (data.k.b() >> 1) + 4, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, data.k.a(), data.k.b(), 0, 0);
    }
}

