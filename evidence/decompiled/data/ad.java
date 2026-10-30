/*
 * Decompiled with CFR 0.152.
 */
package data;

import data.l;
import data.p;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ad {
    private p a;
    private boolean b = true;
    private int[] a;
    public boolean a;
    private long a;
    private boolean c = false;
    private int a;
    private boolean d = false;
    private byte[] a = new byte[120];
    private boolean e = true;
    private boolean f = true;

    public ad(p p2) {
        this.a = p2;
    }

    public final void a() {
        int n2;
        int n3 = (int)(this.a.c * 100.0f / this.a.d);
        int n4 = (int)(this.a.f * 100.0f / this.a.d);
        int n5 = (int)(this.a.e * 100.0f / this.a.d);
        this.a.af = true;
        this.a = true;
        if (this.a.f[this.a.al] == 1) {
            n2 = 0;
            while (n2 < this.a.av) {
                if (this.a.a[n2] == (byte)this.a.al) {
                    this.a.g[n2] = this.a.g[n2] + 1;
                    this.a = false;
                }
                ++n2;
            }
            if (this.a) {
                this.a.a[this.a.av] = (byte)this.a.al;
                this.a.g[this.a.av] = 1;
                ++this.a.av;
            }
        } else if (this.a.f[this.a.al] == 0) {
            n2 = 0;
            while (n2 < this.a.aB) {
                if (this.a.m[n2] == (byte)this.a.al) {
                    this.a.q[n2] = this.a.q[n2] + 1;
                    this.a = false;
                }
                ++n2;
            }
            if (this.a) {
                this.a.m[this.a.aB] = (byte)this.a.al;
                this.a.q[this.a.aB] = 1;
                ++this.a.aB;
            }
        } else if (this.a.f[this.a.al] == 3) {
            n2 = 0;
            while (n2 < this.a.aw) {
                if (this.a.a[n2] == new Integer(this.a.al).toString()) {
                    this.a.h[n2] = this.a.h[n2] + 1;
                    this.a = false;
                }
                ++n2;
            }
            if (this.a) {
                this.a.a[this.a.aw] = this.a.b[this.a.al];
                this.a.h[this.a.aw] = 1;
                ++this.a.aw;
            }
        } else if (this.a.f[this.a.al] == 5) {
            n2 = 0;
            while (n2 < this.a.aw) {
                if (this.a.a[n2] == this.a.b[this.a.al]) {
                    this.a.h[n2] = this.a.h[n2] + 1;
                    this.a = false;
                }
                ++n2;
            }
            if (this.a) {
                this.a.a[this.a.aw] = this.a.b[this.a.al];
                this.a.h[this.a.aw] = 1;
                ++this.a.aw;
            }
        } else if (this.a.f[this.a.al] == 10) {
            n2 = 0;
            while (n2 < this.a.H) {
                if (this.a.a[n2] == this.a.al) {
                    this.a = false;
                }
                ++n2;
            }
            if (this.a) {
                this.a.a[this.a.H] = this.a.al;
                ++this.a.H;
            }
        } else if (this.a.f[this.a.al] == 7) {
            n2 = 0;
            while (n2 < this.a.I) {
                if (this.a.b[n2] == this.a.al) {
                    this.a = false;
                }
                ++n2;
            }
            if (this.a) {
                this.a.b[this.a.I] = this.a.al;
                ++this.a.I;
            }
        } else if (this.a.f[this.a.al] == 8) {
            n2 = 0;
            while (n2 < this.a.J) {
                if (this.a.c[n2] == this.a.al) {
                    this.a = false;
                }
                ++n2;
            }
            if (this.a) {
                this.a.c[this.a.J] = this.a.al;
                ++this.a.J;
            }
        } else if (this.a.f[this.a.al] == 9) {
            n2 = 0;
            while (n2 < this.a.K) {
                if (this.a.d[n2] == this.a.al) {
                    this.a = false;
                }
                ++n2;
            }
            if (this.a) {
                this.a.d[this.a.K] = this.a.al;
                ++this.a.K;
            }
        } else {
            n2 = 0;
            while (n2 < this.a.aB) {
                if (this.a.m[n2] == (byte)this.a.al) {
                    this.a.q[n2] = this.a.q[n2] + 1;
                    this.a = false;
                }
                ++n2;
            }
            if (this.a) {
                this.a.m[this.a.aB] = (byte)this.a.al;
                this.a.q[this.a.aB] = 1;
                ++this.a.aB;
            }
        }
        if (this.a.b[this.a.al] != -1 && this.a.c[this.a.al] == -1) {
            if (this.a.l[this.a.al] != 0) {
                if ((n4 += this.a.l[this.a.al]) > 100) {
                    n4 = 100;
                }
                this.a.f = (float)n4 * this.a.d / 100.0f;
            }
            if (this.a.n[this.a.al] != 0) {
                if ((n5 += this.a.n[this.a.al]) > 100) {
                    n5 = 100;
                }
                this.a.e = (float)n5 * this.a.d / 100.0f;
            }
            if (this.a.m[this.a.al] != 0) {
                n2 = this.a.m[this.a.al];
                if ((n3 += n2) > 100) {
                    n3 = 100;
                }
                if (n3 <= 0) {
                    this.b();
                }
                this.a.c = (float)n3 * this.a.d / 100.0f;
            }
        }
        if (this.a.a.b(this.a.al)) {
            this.a.F = 0L;
            this.a.aM = true;
            this.a.af = false;
        }
        if (this.a.af) {
            if (this.a.X) {
                if (this.a.f[this.a.al] == 0) {
                    if (this.a.al == 93 || this.a.al == 21 || this.a.al == 38) {
                        this.a(this.a.P + 180);
                    } else {
                        this.c(this.a.P + 180);
                    }
                }
                this.a.l[this.a.P + 180] = 0;
            } else if (this.a.Y) {
                if (this.a.f[this.a.al] == 0) {
                    if (this.a.al == 93 || this.a.al == 21 || this.a.al == 38) {
                        this.a(this.a.P + 180 + 2);
                    } else {
                        this.c(this.a.P + 180 + 2);
                    }
                }
                this.a.l[this.a.P + 180 + 2] = 0;
            } else if (this.a.V) {
                if (this.a.f[this.a.al] == 0) {
                    if (this.a.al == 93 || this.a.al == 21 || this.a.al == 38) {
                        this.a(this.a.P + 1);
                    } else {
                        this.c(this.a.P + 1);
                    }
                }
                this.a.l[this.a.P + 1] = 0;
            } else if (this.a.W) {
                if (this.a.f[this.a.al] == 0) {
                    if (this.a.al == 93 || this.a.al == 21 || this.a.al == 38) {
                        this.a(this.a.P + 360 + 1);
                    } else {
                        this.c(this.a.P + 360 + 1);
                    }
                }
                this.a.l[this.a.P + 360 + 1] = 0;
            }
            this.a.m = 0;
            this.a.a.f();
            this.a.aw = true;
        }
    }

    public final void b() {
        this.a.l = 8;
        this.a.aw = true;
        this.a.c = false;
        this.a.y = 0L;
        this.a.r = 0;
        if (this.a.b) {
            this.a.r = 0;
            return;
        }
        this.a.r = 1;
    }

    private void c(int n2) {
        this.a.s[this.a.aD] = this.a.a[n2];
        ++this.a.aD;
        if (this.a.aD == 20) {
            this.a.aD = 0;
        }
    }

    private void w() {
        int n2 = 0;
        while (n2 < this.a.s.length) {
            this.a.s[n2] = 0;
            ++n2;
        }
        this.a.aD = 0;
    }

    public final void a(int n2) {
        this.a.r[this.a.aC] = this.a.a[n2];
        ++this.a.aC;
        if (this.a.aC == 70) {
            this.a.aC = 0;
        }
    }

    public final void c() {
        this.a.U = true;
        this.a.m = 0;
        this.a.n = 0;
        this.a.q = 0L;
    }

    public final void d() {
        if (this.a.q > 10000L) {
            this.a.U = false;
            this.a.q = 0L;
            this.b(30);
        }
    }

    public final void e() {
        if (this.a.u > 2000L) {
            this.a.aN = false;
            this.a.u = 0L;
            this.a.a.c();
            this.a.m = 7;
            this.a.q = 0;
            this.a.aw = true;
        }
    }

    public final void f() {
        if (!this.a.F) {
            if (this.a.aL) {
                this.a = this.a.J;
                this.a.aL = false;
            }
            if (this.a.J == this.a + 1L) {
                this.a.a -= this.a.d / 100.0f * 10.0f;
                this.a.b -= this.a.d / 100.0f * 14.0f;
                this.a.c -= this.a.d / 100.0f * 4.0f;
                if (this.a.a < 0.0f) {
                    this.a.a = 0.0f;
                }
                if (this.a.b < 0.0f) {
                    this.a.b = 0.0f;
                }
                if (this.a.c < 0.0f) {
                    this.a.c = 0.0f;
                }
            }
            if (this.a < this.a.J) {
                this.a = this.a.J;
            }
            if (this.a.a == 0.0f || this.a.b == 0.0f || this.a.c == 0.0f) {
                this.a.l = 8;
                this.a.aw = true;
                this.a.c = false;
                this.a.y = 0L;
                this.a.r = 0;
                return;
            }
        } else if (this.a.aw) {
            this.a.a = this.a.d;
            this.a.b = this.a.d;
            this.a.c = this.a.d;
        }
    }

    public final void g() {
        int n2 = (int)(this.a.a * 100.0f / this.a.d);
        int n3 = (int)(this.a.b * 100.0f / this.a.d);
        int n4 = (int)(this.a.c * 100.0f / this.a.d);
        int n5 = (int)(this.a.f * 100.0f / this.a.d);
        int n6 = (int)(this.a.e * 100.0f / this.a.d);
        int n7 = 0;
        while (n7 < 120) {
            if (this.a.m[this.a.z] == (byte)n7) {
                int n8;
                if (this.a.c[n7] != -1 && this.a.c[n7] != 0) {
                    this.a.a.a(this.a.j[n7]);
                    if ((n2 += this.a.j[n7]) > 100) {
                        n2 = 100;
                    }
                    this.a.a = (float)n2 * this.a.d / 100.0f;
                    if ((n3 += this.a.k[n7]) > 100) {
                        n3 = 100;
                    }
                    this.a.b = (float)n3 * this.a.d / 100.0f;
                    n8 = this.a.m[n7];
                    if ((n4 += n8) > 100) {
                        n4 = 100;
                    }
                    if (n4 <= 0) {
                        this.b();
                    }
                    this.a.c = (float)n4 * this.a.d / 100.0f;
                    if (this.a.c[n7] != 0 || this.a.c[n7] != -1) {
                        if (this.a.l[n7] != 0) {
                            if ((n5 += this.a.l[n7]) > 100) {
                                n5 = 100;
                            }
                            this.a.f = (float)n5 * this.a.d / 100.0f;
                        }
                        if (this.a.n[n7] != 0) {
                            if ((n6 += this.a.n[n7]) > 100) {
                                n6 = 100;
                            }
                            this.a.e = (float)n6 * this.a.d / 100.0f;
                        }
                        if (this.a.m[n7] != 0) {
                            int n9 = this.a.m[n7];
                            if ((n4 += n9) > 100) {
                                n4 = 100;
                            }
                            if (n4 <= 0) {
                                this.b();
                            }
                            this.a.c = (float)n4 * this.a.d / 100.0f;
                        }
                    }
                    this.h();
                } else {
                    if (this.a.j[n7] != -1) {
                        this.a.a.a(this.a.j[n7]);
                        if ((n2 += this.a.j[n7]) > 100) {
                            n2 = 100;
                        }
                        this.a.a = (float)n2 * this.a.d / 100.0f;
                        this.h();
                    }
                    if (this.a.k[n7] != -1) {
                        if ((n3 += this.a.k[n7]) > 100) {
                            n3 = 100;
                        }
                        this.a.b = (float)n3 * this.a.d / 100.0f;
                        this.h();
                    }
                    if (this.a.m[n7] != 0) {
                        n8 = this.a.m[n7];
                        if ((n4 += n8) > 100) {
                            n4 = 100;
                        }
                        if (n4 <= 0) {
                            this.b();
                        }
                        this.a.c = (float)n4 * this.a.d / 100.0f;
                        this.h();
                    }
                }
            }
            ++n7;
        }
    }

    public final void h() {
        int n2 = 0;
        int n3 = -1;
        int n4 = -1;
        int n5 = -1;
        boolean bl = true;
        if (!this.a.ay) {
            if (this.a.q[this.a.z] > 1) {
                n2 = this.a.q[this.a.z];
                this.a.q[this.a.z] = --n2;
                int n6 = 0;
                while (n6 <= 120) {
                    if (this.a.m[this.a.z] == n6) {
                        n3 = n6;
                        break;
                    }
                    ++n6;
                }
                if (this.a.c[n3] == 3) {
                    if (this.a.a.a >= 5) {
                        int n7 = 0;
                        while (n7 <= this.a.aB) {
                            if (this.a.m[n7] == 3) {
                                n4 = n7;
                                break;
                            }
                            ++n7;
                        }
                        int n8 = n4;
                        this.a.q[n8] = this.a.q[n8] - 1;
                        if (this.a.q[n4] == 0) {
                            int n9 = 0;
                            while (n9 <= this.a.aB) {
                                this.a[n9] = this.a.m[n9];
                                this.a[n9] = this.a.q[n9];
                                ++n9;
                            }
                            int n10 = n4;
                            while (n10 < this.a.aB - 1) {
                                this.a.m[n10] = this.a[n10 + 1];
                                this.a.q[n10] = this.a[n10 + 1];
                                ++n10;
                            }
                            --this.a.aB;
                            --this.a.z;
                            if (this.a.z < 0) {
                                this.a.z = 0;
                            } else if (this.a.au > 0) {
                                --this.a.au;
                            }
                        }
                        this.a.a.a = 0;
                    } else {
                        ++this.a.a.a;
                    }
                } else if (this.a.c[n3] != 0 && this.a.c[n3] != -1 && this.a.c[n3] != 3) {
                    int n11 = 0;
                    while (n11 <= this.a.aB) {
                        if (this.a.m[n11] == this.a.c[n3]) {
                            n5 = n11;
                            bl = false;
                            break;
                        }
                        ++n11;
                    }
                    if (!bl) {
                        if (this.a.q[this.a.c[n3]] > 1) {
                            n2 = this.a.q[this.a.c[n3]];
                            this.a.q[this.a.c[n3]] = --n2;
                        } else {
                            int n12 = 0;
                            while (n12 <= this.a.aB) {
                                this.a[n12] = this.a.m[n12];
                                this.a[n12] = this.a.q[n12];
                                ++n12;
                            }
                            int n13 = n5;
                            while (n13 < this.a.aB - 1) {
                                this.a.m[n13] = this.a[n13 + 1];
                                this.a.q[n13] = this.a[n13 + 1];
                                ++n13;
                            }
                            --this.a.aB;
                            --this.a.z;
                            if (this.a.z < 0) {
                                this.a.z = 0;
                            } else if (this.a.au > 0) {
                                --this.a.au;
                            }
                        }
                    }
                }
                this.a.ay = true;
                this.a.n = 0;
                this.a.a.f();
                this.a.a.j();
                this.a.aw = true;
                return;
            }
            int n14 = 0;
            while (n14 <= 120) {
                if (this.a.m[this.a.z] == n14) {
                    n3 = n14;
                    break;
                }
                ++n14;
            }
            int n15 = 0;
            while (n15 <= this.a.aB) {
                this.a[n15] = this.a.m[n15];
                this.a[n15] = this.a.q[n15];
                ++n15;
            }
            int n16 = this.a.z;
            while (n16 < this.a.aB - 1) {
                this.a.m[n16] = this.a[n16 + 1];
                this.a.q[n16] = this.a[n16 + 1];
                ++n16;
            }
            --this.a.aB;
            --this.a.z;
            if (this.a.z < 0) {
                this.a.z = 0;
            } else if (this.a.au > 0) {
                --this.a.au;
            }
            if (this.a.c[n3] == 3) {
                if (this.a.a.a >= 5) {
                    int n17 = 0;
                    while (n17 <= this.a.aB) {
                        if (this.a.m[n17] == 3) {
                            n4 = n17;
                            break;
                        }
                        ++n17;
                    }
                    int n18 = n4;
                    this.a.q[n18] = this.a.q[n18] - 1;
                    if (this.a.q[n4] == 0) {
                        int n19 = 0;
                        while (n19 <= this.a.aB) {
                            this.a[n19] = this.a.m[n19];
                            this.a[n19] = this.a.q[n19];
                            ++n19;
                        }
                        int n20 = n4;
                        while (n20 < this.a.aB - 1) {
                            this.a.m[n20] = this.a[n20 + 1];
                            this.a.q[n20] = this.a[n20 + 1];
                            ++n20;
                        }
                        --this.a.aB;
                        --this.a.z;
                        if (this.a.z < 0) {
                            this.a.z = 0;
                        } else if (this.a.au > 0) {
                            --this.a.au;
                        }
                    }
                    this.a.a.a = 0;
                } else {
                    ++this.a.a.a;
                }
            } else if (this.a.c[n3] != 0 && this.a.c[n3] != -1 && this.a.c[n3] != 3) {
                int n21 = 0;
                while (n21 <= this.a.aB) {
                    if (this.a.m[n21] == this.a.c[n3]) {
                        n5 = n21;
                        bl = false;
                        break;
                    }
                    ++n21;
                }
                if (!bl) {
                    if (this.a.q[this.a.c[n3]] > 1) {
                        n2 = this.a.q[this.a.c[n3]];
                        this.a.q[this.a.c[n3]] = --n2;
                    } else {
                        int n22 = 0;
                        while (n22 <= this.a.aB) {
                            this.a[n22] = this.a.m[n22];
                            this.a[n22] = this.a.q[n22];
                            ++n22;
                        }
                        int n23 = n5;
                        while (n23 < this.a.aB - 1) {
                            this.a.m[n23] = this.a[n23 + 1];
                            this.a.q[n23] = this.a[n23 + 1];
                            ++n23;
                        }
                        --this.a.aB;
                        --this.a.z;
                        if (this.a.z < 0) {
                            this.a.z = 0;
                        } else if (this.a.au > 0) {
                            --this.a.au;
                        }
                    }
                }
            }
            this.a.ay = true;
            this.a.n = 0;
            this.a.a.f();
            this.a.a.j();
        }
    }

    public final void i() {
        if (!this.a.ay) {
            if (this.a.h[this.a.w] > 1) {
                int n2 = this.a.h[this.a.w];
                this.a.h[this.a.w] = --n2;
                this.a.p = 0;
                this.a.ay = true;
                return;
            }
            int n3 = 0;
            while (n3 <= this.a.aw) {
                this.a[n3] = (byte)Integer.parseInt(this.a.a[n3]);
                ++n3;
            }
            int n4 = this.a.w;
            while (n4 < this.a.aw - 1) {
                this.a.a[n4] = new Integer(this.a[n4 + 1]).toString();
                ++n4;
            }
            this.a.ay = true;
            this.a.w = 0;
            --this.a.aw;
            this.a.p = 0;
            this.a.a.f();
            this.a.a.j();
        }
    }

    public final void j() {
        int n2 = (int)(this.a.a * 100.0f / this.a.d);
        int n3 = (int)(this.a.b * 100.0f / this.a.d);
        int n4 = (int)(this.a.c * 100.0f / this.a.d);
        int n5 = (int)(this.a.f * 100.0f / this.a.d);
        int n6 = (int)(this.a.e * 100.0f / this.a.d);
        if (this.a.X) {
            this.a = this.a.P + 180;
        } else if (this.a.Y) {
            this.a = this.a.P + 180 + 2;
        } else if (this.a.V) {
            this.a = this.a.P + 1;
        } else if (this.a.W) {
            this.a = this.a.P + 360 + 1;
        }
        int n7 = 0;
        while (n7 < 120) {
            if (this.a.l[this.a] == this.a.i[n7]) {
                int n8;
                if (this.a.j[n7] != -1) {
                    if ((n2 += this.a.j[n7]) > 100) {
                        n2 = 100;
                    }
                    this.a.a = (float)n2 * this.a.d / 100.0f;
                    this.d = true;
                }
                if (this.a.k[n7] != -1) {
                    if ((n3 += this.a.k[n7]) > 100) {
                        n3 = 100;
                    }
                    this.a.b = (float)n3 * this.a.d / 100.0f;
                    this.d = true;
                }
                if (this.a.m[n7] != 0) {
                    n8 = this.a.m[n7];
                    if ((n4 += n8) > 100) {
                        n4 = 100;
                    }
                    if (n4 <= 0) {
                        this.b();
                        return;
                    }
                    this.a.c = (float)n4 * this.a.d / 100.0f;
                    this.d = true;
                }
                if (this.a.c[n7] != 0 || this.a.c[n7] != -1) {
                    if (this.a.l[n7] != 0) {
                        if ((n5 += this.a.l[n7]) > 100) {
                            n5 = 100;
                        }
                        this.a.f = (float)n5 * this.a.d / 100.0f;
                    }
                    if (this.a.n[n7] != 0) {
                        if ((n6 += this.a.n[n7]) > 100) {
                            n6 = 100;
                        }
                        this.a.e = (float)n6 * this.a.d / 100.0f;
                    }
                    if (this.a.m[n7] != 0) {
                        n8 = this.a.m[n7];
                        if ((n4 += n8) > 100) {
                            n4 = 100;
                        }
                        if (n4 <= 0) {
                            this.b();
                            return;
                        }
                        this.a.c = (float)n4 * this.a.d / 100.0f;
                    }
                }
                if (this.d) {
                    this.a.a.a(this.a.j[n7]);
                    if (this.a.X) {
                        this.a.l[this.a.P + 180] = 0;
                    } else if (this.a.Y) {
                        this.a.l[this.a.P + 180 + 2] = 0;
                    } else if (this.a.V) {
                        this.a.l[this.a.P + 1] = 0;
                    } else if (this.a.W) {
                        this.a.l[this.a.P + 360 + 1] = 0;
                    }
                    this.a.m = 0;
                    this.a.a.f();
                    this.d = false;
                    this.a.aw = true;
                }
            }
            ++n7;
        }
    }

    public final void b(int n2) {
        int n3 = (int)(this.a.c * 100.0f / this.a.d);
        if ((n3 += n2) > 100) {
            n3 = 100;
        }
        if (n3 <= 0) {
            this.b();
        }
        this.a.c = (float)n3 * this.a.d / 100.0f;
    }

    public final void k() {
        this.b = true;
        int n2 = 0;
        while (n2 < this.a.av) {
            if (this.a.a[n2] == this.a.ao) {
                this.b = false;
                this.a.z = n2;
                this.a.au = n2 + 1 - this.a.as;
                if (this.a.au >= 0) break;
                this.a.au = 0;
                break;
            }
            ++n2;
        }
        if (this.b) {
            this.a.a[this.a.av] = (byte)this.a.ao;
            this.a.g[this.a.av] = 1;
            ++this.a.av;
            this.a.z = this.a.av - 1;
            this.a.au = this.a.av - this.a.as;
            this.a.v = this.a.av - 1;
            if (this.a.au < 0) {
                this.a.au = 0;
            }
        }
        this.l();
    }

    public final void l() {
        int n2 = (int)(this.a.c * 100.0f / this.a.d);
        int n3 = (int)(this.a.f * 100.0f / this.a.d);
        int n4 = (int)(this.a.e * 100.0f / this.a.d);
        if (this.a.m[this.a.ao] != 0) {
            int n5 = this.a.m[this.a.ao];
            if ((n2 += n5) > 100) {
                n2 = 100;
            }
            if (n2 <= 0) {
                this.b();
            }
            this.a.c = (float)n2 * this.a.d / 100.0f;
        }
        if (this.a.l[this.a.ao] != 0) {
            if ((n3 += this.a.l[this.a.ao]) > 100) {
                n3 = 100;
            }
            this.a.f = (float)n3 * this.a.d / 100.0f;
        }
        if (this.a.n[this.a.ao] != 0) {
            if ((n4 += this.a.n[this.a.ao]) > 100) {
                n4 = 100;
            }
            this.a.e = (float)n4 * this.a.d / 100.0f;
        }
    }

    public final void m() {
        this.b = true;
        int n2 = 0;
        while (n2 < this.a.aw) {
            if (this.a.a[n2].equals(this.a.b[this.a.ao])) {
                this.b = false;
                this.a.z = n2;
                this.a.au = n2 + 1 - this.a.as;
                if (this.a.au >= 0) break;
                this.a.au = 0;
                break;
            }
            ++n2;
        }
        if (this.b) {
            this.a.a[this.a.aw] = this.a.b[this.a.ao];
            this.a.h[this.a.aw] = 1;
            ++this.a.aw;
            this.a.z = this.a.aw - 1;
            this.a.au = this.a.aw - this.a.as;
            this.a.w = this.a.aw - 1;
            if (this.a.au < 0) {
                this.a.au = 0;
            }
        }
        this.l();
    }

    public final void n() {
        this.b = true;
        int n2 = 0;
        while (n2 < this.a.aB) {
            if (this.a.m[n2] == this.a.ao) {
                this.a.q[n2] = this.a.q[n2] + 1;
                this.b = false;
                this.a.z = n2;
                this.a.au = n2 + 1 - this.a.as;
                if (this.a.au >= 0) break;
                this.a.au = 0;
                break;
            }
            ++n2;
        }
        if (this.b) {
            this.a.m[this.a.aB] = (byte)this.a.ao;
            this.a.q[this.a.aB] = 1;
            ++this.a.aB;
            this.a.z = this.a.aB - 1;
            this.a.au = this.a.aB - this.a.as;
            if (this.a.au < 0) {
                this.a.au = 0;
            }
        }
        this.l();
    }

    public final void a(String string) {
        if (string.equals("hat")) {
            this.b = true;
            int n2 = 0;
            while (n2 < this.a.H) {
                if (this.a.a[n2] == this.a.ao) {
                    this.b = false;
                }
                ++n2;
            }
            if (this.b) {
                this.a.a[this.a.H] = this.a.ao;
                ++this.a.H;
            }
        } else if (string.equals("shirt")) {
            this.b = true;
            int n3 = 0;
            while (n3 < this.a.I) {
                if (this.a.b[n3] == this.a.ao) {
                    this.b = false;
                }
                ++n3;
            }
            if (this.b) {
                this.a.b[this.a.I] = this.a.ao;
                ++this.a.I;
            }
        } else if (string.equals("trousers")) {
            this.b = true;
            int n4 = 0;
            while (n4 < this.a.J) {
                if (this.a.c[n4] == this.a.ao) {
                    this.b = false;
                }
                ++n4;
            }
            if (this.b) {
                this.a.c[this.a.J] = this.a.ao;
                ++this.a.J;
            }
        } else if (string.equals("shoes")) {
            this.b = true;
            int n5 = 0;
            while (n5 < this.a.K) {
                if (this.a.d[n5] == this.a.ao) {
                    this.b = false;
                }
                ++n5;
            }
            if (this.b) {
                this.a.d[this.a.K] = this.a.ao;
                ++this.a.K;
            }
        }
        this.l();
    }

    public final void o() {
        if (this.a.an == 1) {
            this.a(this.a.a[this.a.ao][0], this.a.a.u);
            return;
        }
        if (this.a.an == 2) {
            this.a(this.a.a[this.a.ao][0], this.a.a.u);
            this.a(this.a.a[this.a.ao][2], this.a.a.v);
            return;
        }
        if (this.a.an == 3) {
            this.a(this.a.a[this.a.ao][0], this.a.a.u);
            this.a(this.a.a[this.a.ao][2], this.a.a.v);
            this.a(this.a.a[this.a.ao][4], this.a.a.w);
            return;
        }
        if (this.a.an == 4) {
            this.a(this.a.a[this.a.ao][0], this.a.a.u);
            this.a(this.a.a[this.a.ao][2], this.a.a.v);
            this.a(this.a.a[this.a.ao][4], this.a.a.w);
            this.a(this.a.a[this.a.ao][6], this.a.a.x);
            return;
        }
        if (this.a.an == 5) {
            this.a(this.a.a[this.a.ao][0], this.a.a.u);
            this.a(this.a.a[this.a.ao][2], this.a.a.v);
            this.a(this.a.a[this.a.ao][4], this.a.a.w);
            this.a(this.a.a[this.a.ao][6], this.a.a.x);
            this.a(this.a.a[this.a.ao][8], this.a.a.y);
            return;
        }
        if (this.a.an == 6) {
            this.a(this.a.a[this.a.ao][0], this.a.a.u);
            this.a(this.a.a[this.a.ao][2], this.a.a.v);
            this.a(this.a.a[this.a.ao][4], this.a.a.w);
            this.a(this.a.a[this.a.ao][6], this.a.a.x);
            this.a(this.a.a[this.a.ao][8], this.a.a.y);
            this.a(this.a.a[this.a.ao][10], this.a.a.z);
        }
    }

    private void a(byte by, int n2) {
        int n3 = 0;
        int n4 = 0;
        while (n4 <= this.a.aB) {
            this.a[n4] = this.a.m[n4];
            this.a[n4] = this.a.q[n4];
            ++n4;
        }
        int n5 = 0;
        while (n5 < this.a.aB) {
            if (this.a.m[n5] == by) {
                n3 = n5;
            }
            ++n5;
        }
        if (this.a[n3] > n2) {
            int n6 = this.a[n3];
            this.a.q[n3] = n6 -= n2;
            return;
        }
        int n7 = n3;
        while (n7 < this.a.aB - 1) {
            this.a.m[n7] = this.a[n7 + 1];
            this.a.q[n7] = this.a[n7 + 1];
            ++n7;
        }
        --this.a.aB;
        if (this.a.au > 0) {
            --this.a.au;
        }
        --this.a.z;
    }

    public final void p() {
        if (this.a.J >= 0L && this.a.J <= 5L || this.a.J >= 12L && this.a.J <= 17L || this.a.J >= 24L && this.a.J <= 29L || this.a.J >= 36L && this.a.J <= 41L || this.a.J >= 48L && this.a.J <= 53L) {
            if (this.a.r) {
                this.a.s = true;
                if (!this.e) {
                    if (this.a.m == 0) {
                        this.a.aK = true;
                    }
                } else {
                    this.e = false;
                }
                this.a.r = false;
                this.a.a.a(this.a.r);
                this.r();
                this.x();
                this.a(this.a.a.a, this.a.a.i);
                this.a(this.a.a.b, this.a.a.j);
                this.a(this.a.a.c, this.a.a.k);
                this.a.g = 0L;
                return;
            }
        } else if ((this.a.J >= 6L && this.a.J <= 11L || this.a.J >= 18L && this.a.J <= 23L || this.a.J >= 30L && this.a.J <= 35L || this.a.J >= 42L && this.a.J <= 47L || this.a.J >= 54L && this.a.J <= 59L) && !this.a.r) {
            this.a.s = true;
            if (!this.e) {
                if (this.a.m == 0) {
                    this.a.aK = true;
                }
            } else {
                this.e = false;
            }
            this.a.r = true;
            this.a.a.a(this.a.r);
            this.r();
            this.x();
            this.a(this.a.a.a, this.a.a.i);
            this.a(this.a.a.b, this.a.a.j);
            this.a(this.a.a.c, this.a.a.k);
            this.a.a.b();
            this.a.g = 0L;
        }
    }

    public final void q() {
        if (this.a.ab < 0) {
            this.a.ab = 0;
        }
        if (this.a.ab > 180 - this.a.N) {
            this.a.ab = 180 - this.a.N;
        }
        if (this.a.ac < 0) {
            this.a.ac = 0;
        }
        if (this.a.ac > 180 - this.a.O) {
            this.a.ac = 180 - this.a.O - 1;
        }
    }

    public final void r() {
        if (!this.f && this.a.a.a(this.a.Z, this.a.aa) == 5 || this.a.a.a(this.a.Z, this.a.aa) == 6) {
            boolean bl = false;
            boolean[][] blArray = new boolean[31][31];
            int n2 = 0;
            while (n2 < 31) {
                int n3 = 0;
                while (n3 < 31) {
                    blArray[n2][n3] = false;
                    ++n3;
                }
                ++n2;
            }
            boolean bl2 = false;
            blArray[15][15] = true;
            if (this.a.Z > 169 && this.a.Z < 176 && this.a.aa > 104 && this.a.aa < 113) {
                this.a.ae = 0;
                this.a.aa = 101;
                this.a.ad = 0;
                this.a.Z = 166;
                this.a.ac = this.a.aa - this.a.O / 2;
                this.a.ab = this.a.Z - this.a.N / 2;
                return;
            }
            int n4 = 1;
            while (n4 < 15) {
                int n5 = this.a.aa - n4;
                while (n5 <= this.a.aa + n4) {
                    if (n5 > -1 && n5 < 180) {
                        int n6 = this.a.Z - n4;
                        while (n6 <= this.a.Z + n4) {
                            if (n6 > -1 && n6 < 180 && !blArray[15 + (n6 - this.a.Z)][15 + (n5 - this.a.aa)]) {
                                if (this.a.a.b(n6, n5) == 0 && this.a.a.c(n6, n5) == 0 && this.a.a.d(n6, n5) == 0 && this.a.a.a(n6, n5) == 4) {
                                    if ((n5 == this.a.aa - n4 || n5 == this.a.aa + n4) && n6 < this.a.Z && this.a.a.b(this.a.Z, n5) == 0 && this.a.a.c(this.a.Z, n5) == 0 && this.a.a.d(this.a.Z, n5) == 0 && this.a.a.a(this.a.Z, n5) == 4) {
                                        this.a.ae = 0;
                                        this.a.aa = n5;
                                        this.a.ad = 0;
                                        this.a.Z = this.a.Z;
                                        if (this.a.aa > this.a.O / 2 && this.a.aa < 180 - this.a.O / 2) {
                                            this.a.ac = this.a.aa - this.a.O / 2;
                                        }
                                        if (this.a.Z > this.a.N / 2 && this.a.Z < 180 - this.a.N / 2) {
                                            this.a.ab = this.a.Z - this.a.N / 2;
                                        }
                                        return;
                                    }
                                    if ((n6 == this.a.Z - n4 || n6 == this.a.Z + n4) && n5 < this.a.aa && this.a.a.b(n6, this.a.aa) == 0 && this.a.a.c(n6, this.a.aa) == 0 && this.a.a.d(n6, this.a.aa) == 0 && this.a.a.a(n6, this.a.aa) == 4) {
                                        this.a.ae = 0;
                                        this.a.aa = this.a.aa;
                                        this.a.ad = 0;
                                        this.a.Z = n6;
                                        if (this.a.aa > this.a.O / 2 && this.a.aa < 180 - this.a.O / 2) {
                                            this.a.ac = this.a.aa - this.a.O / 2;
                                        }
                                        if (this.a.Z > this.a.N / 2 && this.a.Z < 180 - this.a.N / 2) {
                                            this.a.ab = this.a.Z - this.a.N / 2;
                                        }
                                        return;
                                    }
                                    this.a.ae = 0;
                                    this.a.aa = n5;
                                    this.a.ad = 0;
                                    this.a.Z = n6;
                                    if (this.a.aa > this.a.O / 2 && this.a.aa < 180 - this.a.O / 2) {
                                        this.a.ac = this.a.aa - this.a.O / 2;
                                    }
                                    if (this.a.Z > this.a.N / 2 && this.a.Z < 180 - this.a.N / 2) {
                                        this.a.ab = this.a.Z - this.a.N / 2;
                                    }
                                    return;
                                }
                                blArray[15 + (n6 - this.a.Z)][15 + (n5 - this.a.aa)] = true;
                            }
                            ++n6;
                        }
                    }
                    ++n5;
                }
                ++n4;
            }
        }
        this.f = false;
    }

    private void a(l[] lArray, int n2) {
        int n3 = 0;
        while (n3 < n2) {
            if (this.a.k[lArray[n3].d * 180 + lArray[n3].c] > 4 && this.a.k[lArray[n3].d * 180 + lArray[n3].c] < 7) {
                this.a.l[lArray[n3].d * 180 + lArray[n3].c] = 0;
                lArray[n3].c = lArray[n3].a;
                lArray[n3].d = lArray[n3].b;
            }
            ++n3;
        }
    }

    private void x() {
        int n2 = 0;
        while (n2 < this.a.a.r) {
            boolean bl = false;
            if (this.a.k[this.a.a.a[n2].b * 180 + this.a.a.a[n2].a] < 5) {
                this.a.l[this.a.a.a[n2].b * 180 + this.a.a.a[n2].a] = 0;
                int n3 = 0;
                while (n3 < 10) {
                    if (bl) break;
                    int n4 = 0;
                    while (n4 < 10) {
                        if (this.a.a.a[n2].a - n3 > 0 && this.a.a.a[n2].a - n3 < 180 && this.a.a.a[n2].b - n4 > 0 && this.a.a.a[n2].b - n4 < 180 && this.a.k[(this.a.a.a[n2].b - n4) * 180 + (this.a.a.a[n2].a - n3)] > 4 && this.a.k[(this.a.a.a[n2].b - n4) * 180 + (this.a.a.a[n2].a - n3)] < 7 && this.a.a.e((this.a.a.a[n2].b - n4) * 180 + (this.a.a.a[n2].a - n3), 1) && this.a.a.d((this.a.a.a[n2].b - n4) * 180 + (this.a.a.a[n2].a - n3), 1) && this.a.a.c((this.a.a.a[n2].b - n4) * 180 + (this.a.a.a[n2].a - n3), 1)) {
                            this.a.l[(this.a.a.a[n2].b - n4) * 180 + (this.a.a.a[n2].a - n3)] = 56;
                            this.a.a.a[n2].a -= n3;
                            this.a.a.a[n2].b -= n4;
                            bl = true;
                        }
                        if (bl) break;
                        if (this.a.a.a[n2].a + n3 > 0 && this.a.a.a[n2].a + n3 < 180 && this.a.a.a[n2].b + n4 > 0 && this.a.a.a[n2].b + n4 < 180 && this.a.k[(this.a.a.a[n2].b + n4) * 180 + (this.a.a.a[n2].a + n3)] > 4 && this.a.k[(this.a.a.a[n2].b + n4) * 180 + (this.a.a.a[n2].a + n3)] < 7 && this.a.a.e((this.a.a.a[n2].b + n4) * 180 + (this.a.a.a[n2].a + n3), 1) && this.a.a.d((this.a.a.a[n2].b + n4) * 180 + (this.a.a.a[n2].a + n3), 1) && this.a.a.c((this.a.a.a[n2].b + n4) * 180 + (this.a.a.a[n2].a + n3), 1)) {
                            this.a.l[(this.a.a.a[n2].b + n4) * 180 + (this.a.a.a[n2].a + n3)] = 56;
                            this.a.a.a[n2].a += n3;
                            this.a.a.a[n2].b += n4;
                            bl = true;
                        }
                        if (bl) break;
                        ++n4;
                    }
                    ++n3;
                }
            }
            ++n2;
        }
    }

    public final void s() {
        if (this.a.K == 4L && !this.c) {
            this.w();
            this.a.a.a("BIGislandObjects", this.a.l);
            this.a.a.b();
            this.a.a.d();
            this.c = true;
        }
        if (this.a.K != 4L) {
            this.c = false;
        }
    }

    public final void t() {
        this.a.a.a("BIGislandObjects", this.a.l);
        this.a.a.d();
        this.a.a.b();
    }

    public final void u() {
        if (this.a.X) {
            this.a.a.a(4);
            this.a.l[this.a.P + 180] = 0;
        } else if (this.a.Y) {
            this.a.a.b(4);
            this.a.l[this.a.P + 180 + 2] = 0;
        } else if (this.a.V) {
            this.a.a.c(4);
            this.a.l[this.a.P + 1] = 0;
        } else if (this.a.W) {
            this.a.a.d(4);
            this.a.l[this.a.P + 360 + 1] = 0;
        }
        if (this.a.f[94] == 393224) {
            this.a.X = true;
            this.a.Y = false;
        } else {
            this.a.X = false;
            this.a.Y = true;
        }
        this.a.T = true;
    }

    public final void v() {
        this.a.T = false;
        this.a.f[94] = this.a.X ? 393224 : 393225;
        if (this.a.Z) {
            this.a.l[this.a.P + 180 + 1] = 95;
            this.a.a.a(4);
            this.a.b = this.a.P + 180 + 1;
            return;
        }
        if (this.a.aa) {
            this.a.l[this.a.P + 180 + 1] = 95;
            this.a.a.b(4);
            this.a.b = this.a.P + 180 + 1;
            return;
        }
        if (this.a.ab) {
            this.a.l[this.a.P + 180 + 1] = 95;
            this.a.a.c(4);
            this.a.b = this.a.P + 180 + 1;
            return;
        }
        if (this.a.ac) {
            this.a.l[this.a.P + 180 + 1] = 95;
            this.a.a.d(4);
            this.a.b = this.a.P + 180 + 1;
        }
    }
}

