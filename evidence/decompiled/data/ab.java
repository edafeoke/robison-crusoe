/*
 * Decompiled with CFR 0.152.
 */
package data;

import data.k;
import data.p;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ab {
    private p a;
    private int a;
    private int b;
    private int[] c;
    private int[] d;
    private int[] e;
    private int[] f;
    private int[] g;
    private int[] h;
    public int[] a;
    public int[] b;
    private boolean a = false;
    private boolean b = false;
    private boolean c;
    private boolean d;
    private int c = 0;
    private boolean e;
    private boolean f;
    private boolean g = false;
    private boolean h = false;
    private boolean i = true;
    private int d = 0;
    private int e = 0;
    private boolean j = false;
    private int f = 0;
    private boolean k = true;
    private boolean l = true;
    private boolean m = true;
    private boolean n = true;

    public ab(p p2) {
        this.g = new int[]{-3, 0, 0, 0, 0};
        this.h = new int[]{-1, 0, -1, 0, -1};
        this.a = new int[]{0, 0, 0, 0, 0};
        this.b = new int[]{0, -1, 0, -1, 0};
        this.a = p2;
    }

    public final void a(int n2) {
        if (this.a.ab == 180 - this.a.N && this.a.Z <= 180 - this.a.N + this.a.N / 2 || this.a.ab > 0 && this.a.ab < 180 - this.a.N) {
            if (this.a.B >= 25L) {
                this.a = true;
                this.a.U += this.a.aj;
                this.a.B = 0L;
                this.c += this.a.aj;
                if (this.c == this.a.M) {
                    this.a = false;
                    this.c = 0;
                    --this.a.ab;
                    this.a.U = 0;
                }
            }
            this.a.Z = this.a.ab + this.a.N / 2;
            if (this.e) {
                this.e = false;
                return;
            }
        } else if (this.a.B >= 25L) {
            this.a = true;
            this.a.ad -= this.a.aj;
            this.a.B = 0L;
            this.c -= this.a.aj;
            if (this.c == -this.a.M) {
                this.a = false;
                this.c = 0;
                --this.a.Z;
                --this.a.S;
                this.a.ad = 0;
            }
        }
    }

    public final void b(int n2) {
        if (this.a.ab == 0 && this.a.Z >= this.a.N / 2 || this.a.ab > 0 && this.a.ab < 180 - this.a.N) {
            if (this.a.B >= 25L) {
                this.b = true;
                this.a.U -= this.a.aj;
                this.a.B = 0L;
                this.c -= this.a.aj;
                if (this.c == -this.a.M) {
                    this.b = false;
                    this.c = 0;
                    ++this.a.ab;
                    this.a.U = 0;
                }
            }
            this.a.Z = this.a.ab + this.a.N / 2;
            if (this.f) {
                this.f = false;
                return;
            }
        } else if (this.a.B >= 25L) {
            this.b = true;
            this.a.ad += this.a.aj;
            this.a.B = 0L;
            this.c += this.a.aj;
            if (this.c == this.a.M) {
                this.b = false;
                this.c = 0;
                ++this.a.Z;
                ++this.a.S;
                this.a.ad = 0;
            }
        }
    }

    public final void c(int n2) {
        if (this.a.ac == 180 - this.a.O - 1 && this.a.aa <= 180 - this.a.O + (this.a.O - 1) / 2 || this.a.ac > 0 && this.a.ac < 180 - this.a.O - 1) {
            if (this.a.B >= 25L) {
                this.c = true;
                this.a.V += this.a.aj;
                this.a.B = 0L;
                this.c += this.a.aj;
                if (this.c == this.a.M) {
                    this.c = false;
                    this.c = 0;
                    --this.a.ac;
                    this.a.V = 0;
                }
            }
            this.a.aa = this.a.ac + this.a.O / 2;
            if (this.g) {
                this.g = false;
                return;
            }
        } else if (this.a.B >= 25L) {
            this.c = true;
            this.a.ae -= this.a.aj;
            this.a.B = 0L;
            this.c -= this.a.aj;
            if (this.c == -this.a.M) {
                this.c = false;
                this.c = 0;
                --this.a.aa;
                --this.a.T;
                this.a.ae = 0;
            }
        }
    }

    public final void d(int n2) {
        if (this.a.ac == 0 && this.a.aa >= this.a.O / 2 || this.a.ac > 0 && this.a.ac < 180 - this.a.O - 1) {
            if (this.a.B >= 25L) {
                this.d = true;
                this.a.V -= this.a.aj;
                this.a.B = 0L;
                this.c -= this.a.aj;
                if (this.c == -this.a.M) {
                    this.d = false;
                    this.c = 0;
                    ++this.a.ac;
                    this.a.V = 0;
                }
            }
            this.a.aa = this.a.ac + this.a.O / 2;
            if (this.h) {
                this.h = false;
                return;
            }
        } else if (this.a.B >= 25L) {
            this.d = true;
            this.a.ae += this.a.aj;
            this.a.B = 0L;
            this.c += this.a.aj;
            if (this.c == this.a.M) {
                this.d = false;
                this.c = 0;
                ++this.a.aa;
                ++this.a.T;
                this.a.ae = 0;
            }
        }
    }

    private void e() {
        this.a.V = false;
        this.a.W = true;
        if (!this.a.T) {
            this.a.X = false;
            this.a.Y = false;
        }
        this.a.ag = 0;
        this.a.ah = 0;
        this.a.ai = 0;
        this.a.af = this.a.af < 4 ? ++this.a.af : 1;
        if (this.a.T && !this.i && ++this.d > 6) {
            this.d = 0;
        }
    }

    private void f() {
        this.a.V = true;
        this.a.W = false;
        if (!this.a.T) {
            this.a.X = false;
            this.a.Y = false;
        }
        this.a.af = 0;
        this.a.ah = 0;
        this.a.ai = 0;
        this.a.ag = this.a.ag < 4 ? ++this.a.ag : 1;
        if (this.a.T && !this.i && ++this.d > 6) {
            this.d = 0;
        }
    }

    private void g() {
        this.a.V = false;
        this.a.W = false;
        this.a.X = true;
        this.a.Y = false;
        this.a.ag = 0;
        this.a.af = 0;
        this.a.ai = 0;
        this.a.ah = this.a.ah < 4 ? ++this.a.ah : 1;
        if (this.a.T && !this.i && ++this.d > 6) {
            this.d = 0;
        }
    }

    private void h() {
        this.a.V = false;
        this.a.W = false;
        this.a.X = false;
        this.a.Y = true;
        this.a.ag = 0;
        this.a.af = 0;
        this.a.ah = 0;
        this.a.ai = this.a.ai < 4 ? ++this.a.ai : 1;
        if (this.a.T && !this.i && ++this.d > 6) {
            this.d = 0;
        }
    }

    private void i() {
        if (this.a.j > 400L) {
            this.e = this.e == 1 ? 0 : ++this.e;
            this.a.j = 0L;
        }
    }

    public final void a() {
        this.a = (this.a.Z - this.a.ab) * this.a.M + this.a.M / 2 + this.a.ad;
        this.b = (this.a.aa - this.a.ac) * this.a.M + this.a.M + this.a.ae;
        if (this.a.f[18] != this.a.f[19] && !this.j) {
            this.a.a.a(((k)this.a).a, 589827, this.a, this.b);
        }
        if (this.a.U) {
            if (this.f == 2) {
                this.f = 0;
            }
            if (this.f == 0) {
                this.a.a.a(((k)this.a).a, 589824, this.a, this.b);
            }
            if (this.f == 1) {
                this.a.a.a(((k)this.a).a, 589825, this.a, this.b);
            }
            if (this.a.s > 700L) {
                ++this.f;
                this.a.s = 0L;
                return;
            }
        } else {
            if (this.a.aN) {
                this.a.a.a(((k)this.a).a, 589828, this.a, this.b);
                this.a.a.a(((k)this.a).a, 589829, this.a, this.b);
            } else if (this.a.V) {
                this.a.a.a(((k)this.a).a, 196613 + this.a.ag, this.a, this.b);
            } else if (this.a.W) {
                this.a.a.a(((k)this.a).a, 196608 + this.a.af, this.a, this.b);
            } else if (this.a.X) {
                this.a.a.a(((k)this.a).a, 196618 + this.a.ah, this.a, this.b);
            } else if (this.a.Y) {
                this.a.a.a(((k)this.a).a, 196623 + this.a.ai, this.a, this.b);
            }
            this.j();
            if (this.a.f[18] != this.a.f[19] && this.j) {
                this.i();
                if (this.e == 1) {
                    if (this.a.X) {
                        this.a.a.a(((k)this.a).a, 589826, this.a, this.b);
                        return;
                    }
                    if (this.a.Y) {
                        this.a.a.a(((k)this.a).a, 589826, this.a, this.b);
                        return;
                    }
                    this.a.a.a(((k)this.a).a, 589826, this.a, this.b);
                    return;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 589830, this.a, this.b);
                    return;
                }
                if (this.a.Y) {
                    this.a.a.a(((k)this.a).a, 589830, this.a, this.b);
                    return;
                }
                this.a.a.a(((k)this.a).a, 589830, this.a, this.b);
            }
        }
    }

    private void j() {
        switch (this.a.d[this.a.G]) {
            case 107: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327825 + this.a.ag, this.a, this.b);
                    break;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327810 + this.a.af, this.a, this.b);
                    break;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327840 + this.a.ah, this.a, this.b);
                    break;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327855 + this.a.ai, this.a, this.b);
                break;
            }
            case 108: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327875 + this.a.ag, this.a, this.b);
                    break;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327860 + this.a.af, this.a, this.b);
                    break;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327890 + this.a.ah, this.a, this.b);
                    break;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327905 + this.a.ai, this.a, this.b);
                break;
            }
            case 109: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327880 + this.a.ag, this.a, this.b);
                    break;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327865 + this.a.af, this.a, this.b);
                    break;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327895 + this.a.ah, this.a, this.b);
                    break;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327910 + this.a.ai, this.a, this.b);
                break;
            }
            case 110: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327885 + this.a.ag, this.a, this.b);
                    break;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327870 + this.a.af, this.a, this.b);
                    break;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327900 + this.a.ah, this.a, this.b);
                    break;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327915 + this.a.ai, this.a, this.b);
            }
        }
        switch (this.a.c[this.a.F]) {
            case 103: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327760 + this.a.ag, this.a, this.b);
                    break;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327745 + this.a.af, this.a, this.b);
                    break;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327775 + this.a.ah, this.a, this.b);
                    break;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327790 + this.a.ai, this.a, this.b);
                break;
            }
            case 104: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327765 + this.a.ag, this.a, this.b);
                    break;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327750 + this.a.af, this.a, this.b);
                    break;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327780 + this.a.ah, this.a, this.b);
                    break;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327795 + this.a.ai, this.a, this.b);
                break;
            }
            case 105: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327815 + this.a.ag, this.a, this.b);
                    break;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327800 + this.a.af, this.a, this.b);
                    break;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327830 + this.a.ah, this.a, this.b);
                    break;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327845 + this.a.ai, this.a, this.b);
                break;
            }
            case 106: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327820 + this.a.ag, this.a, this.b);
                    break;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327805 + this.a.af, this.a, this.b);
                    break;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327835 + this.a.ah, this.a, this.b);
                    break;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327850 + this.a.ai, this.a, this.b);
            }
        }
        switch (this.a.b[this.a.E]) {
            case 99: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327695 + this.a.ag, this.a, this.b);
                    break;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327680 + this.a.af, this.a, this.b);
                    break;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327710 + this.a.ah, this.a, this.b);
                    break;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327725 + this.a.ai, this.a, this.b);
                break;
            }
            case 100: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327700 + this.a.ag, this.a, this.b);
                    break;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327685 + this.a.af, this.a, this.b);
                    break;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327715 + this.a.ah, this.a, this.b);
                    break;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327730 + this.a.ai, this.a, this.b);
                break;
            }
            case 101: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327705 + this.a.ag, this.a, this.b);
                    break;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327690 + this.a.af, this.a, this.b);
                    break;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327720 + this.a.ah, this.a, this.b);
                    break;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327735 + this.a.ai, this.a, this.b);
                break;
            }
            case 102: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327755 + this.a.ag, this.a, this.b);
                    break;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327740 + this.a.af, this.a, this.b);
                    break;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327770 + this.a.ah, this.a, this.b);
                    break;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327785 + this.a.ai, this.a, this.b);
            }
        }
        switch (this.a.a[this.a.D]) {
            case 111: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327921, this.a + this.c[this.a.ag], this.b + this.d[this.a.ag]);
                    return;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327920, this.a + this.a[this.a.af], this.b + this.b[this.a.af]);
                    return;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327922, this.a + this.e[this.a.ah], this.b + this.f[this.a.ah]);
                    return;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327923, this.a + this.g[this.a.ai], this.b + this.h[this.a.ai]);
                return;
            }
            case 112: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327925, this.a + this.c[this.a.ag], this.b + this.d[this.a.ag]);
                    return;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327924, this.a + this.a[this.a.af], this.b + this.b[this.a.af]);
                    return;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327926, this.a + this.e[this.a.ah], this.b + this.f[this.a.ah]);
                    return;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327927, this.a + this.g[this.a.ai], this.b + this.h[this.a.ai]);
                return;
            }
            case 113: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327929, this.a + this.c[this.a.ag], this.b + this.d[this.a.ag]);
                    return;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327928, this.a + this.a[this.a.af], this.b + this.b[this.a.af]);
                    return;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327930, this.a + this.e[this.a.ah], this.b + this.f[this.a.ah]);
                    return;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327931, this.a + this.g[this.a.ai], this.b + this.h[this.a.ai]);
                return;
            }
            case 114: {
                if (this.a.V) {
                    this.a.a.a(((k)this.a).a, 327933, this.a + this.c[this.a.ag], this.b + this.d[this.a.ag]);
                    return;
                }
                if (this.a.W) {
                    this.a.a.a(((k)this.a).a, 327932, this.a + this.a[this.a.af], this.b + this.b[this.a.af]);
                    return;
                }
                if (this.a.X) {
                    this.a.a.a(((k)this.a).a, 327934, this.a + this.e[this.a.ah], this.b + this.f[this.a.ah]);
                    return;
                }
                if (!this.a.Y) break;
                this.a.a.a(((k)this.a).a, 327935, this.a + this.g[this.a.ai], this.b + this.h[this.a.ai]);
            }
        }
    }

    public final void b() {
        this.a = (this.a.Z - this.a.ab) * this.a.M + this.a.M / 2 + this.a.ad;
        this.b = (this.a.aa - this.a.ac) * this.a.M + this.a.M + this.a.ae;
        if (this.a.X) {
            if (this.d < 3) {
                this.a.a.a(((k)this.a).a, 393224, this.a, this.b);
                this.a.a.a(((k)this.a).a, 196623, this.a, this.b);
                this.k();
                this.a.a.a(((k)this.a).a, 393226, this.a, this.b);
                this.a.a.a(((k)this.a).a, 393230, this.a, this.b);
                this.a.a.a(((k)this.a).a, 393233, this.a, this.b);
            } else {
                this.a.a.a(((k)this.a).a, 393224, this.a, this.b);
                this.a.a.a(((k)this.a).a, 196624, this.a, this.b);
                this.k();
                this.a.a.a(((k)this.a).a, 393226, this.a, this.b);
                this.a.a.a(((k)this.a).a, 393228, this.a, this.b);
                this.a.a.a(((k)this.a).a, 393233, this.a, this.b);
            }
        }
        if (this.a.Y) {
            if (this.d < 3) {
                this.a.a.a(((k)this.a).a, 393225, this.a, this.b);
                this.a.a.a(((k)this.a).a, 196618, this.a, this.b);
                this.k();
                this.a.a.a(((k)this.a).a, 393227, this.a, this.b);
                this.a.a.a(((k)this.a).a, 393231, this.a, this.b);
                this.a.a.a(((k)this.a).a, 393234, this.a, this.b);
                return;
            }
            this.a.a.a(((k)this.a).a, 393225, this.a, this.b);
            this.a.a.a(((k)this.a).a, 196619, this.a, this.b);
            this.k();
            this.a.a.a(((k)this.a).a, 393227, this.a, this.b);
            this.a.a.a(((k)this.a).a, 393229, this.a, this.b);
            this.a.a.a(((k)this.a).a, 393234, this.a, this.b);
        }
    }

    private void k() {
        if (this.a.X) {
            if (this.d < 3) {
                switch (this.a.b[this.a.E]) {
                    case 99: {
                        this.a.a.a(((k)this.a).a, 327725, this.a, this.b);
                        break;
                    }
                    case 100: {
                        this.a.a.a(((k)this.a).a, 327730, this.a, this.b);
                        break;
                    }
                    case 101: {
                        this.a.a.a(((k)this.a).a, 327735, this.a, this.b);
                        break;
                    }
                    case 102: {
                        this.a.a.a(((k)this.a).a, 327785, this.a, this.b);
                    }
                }
                switch (this.a.a[this.a.D]) {
                    case 111: {
                        this.a.a.a(((k)this.a).a, 327923, this.a + this.g[0], this.b + this.h[0]);
                        break;
                    }
                    case 112: {
                        this.a.a.a(((k)this.a).a, 327927, this.a + this.g[0], this.b + this.h[0]);
                        break;
                    }
                    case 113: {
                        this.a.a.a(((k)this.a).a, 327931, this.a + this.g[0], this.b + this.h[0]);
                        break;
                    }
                    case 114: {
                        this.a.a.a(((k)this.a).a, 327935, this.a + this.g[0], this.b + this.h[0]);
                    }
                }
            } else {
                switch (this.a.b[this.a.E]) {
                    case 99: {
                        this.a.a.a(((k)this.a).a, 327726, this.a, this.b);
                        break;
                    }
                    case 100: {
                        this.a.a.a(((k)this.a).a, 327731, this.a, this.b);
                        break;
                    }
                    case 101: {
                        this.a.a.a(((k)this.a).a, 327736, this.a, this.b);
                        break;
                    }
                    case 102: {
                        this.a.a.a(((k)this.a).a, 327786, this.a, this.b);
                    }
                }
                switch (this.a.a[this.a.D]) {
                    case 111: {
                        this.a.a.a(((k)this.a).a, 327923, this.a + this.g[1], this.b + this.h[1]);
                        break;
                    }
                    case 112: {
                        this.a.a.a(((k)this.a).a, 327927, this.a + this.g[1], this.b + this.h[1]);
                        break;
                    }
                    case 113: {
                        this.a.a.a(((k)this.a).a, 327931, this.a + this.g[1], this.b + this.h[1]);
                        break;
                    }
                    case 114: {
                        this.a.a.a(((k)this.a).a, 327935, this.a + this.g[1], this.b + this.h[1]);
                    }
                }
            }
        }
        if (this.a.Y) {
            if (this.d < 3) {
                switch (this.a.b[this.a.E]) {
                    case 99: {
                        this.a.a.a(((k)this.a).a, 327710, this.a, this.b);
                        break;
                    }
                    case 100: {
                        this.a.a.a(((k)this.a).a, 327715, this.a, this.b);
                        break;
                    }
                    case 101: {
                        this.a.a.a(((k)this.a).a, 327720, this.a, this.b);
                        break;
                    }
                    case 102: {
                        this.a.a.a(((k)this.a).a, 327770, this.a, this.b);
                    }
                }
                switch (this.a.a[this.a.D]) {
                    case 111: {
                        this.a.a.a(((k)this.a).a, 327922, this.a + this.e[0], this.b + this.f[0]);
                        break;
                    }
                    case 112: {
                        this.a.a.a(((k)this.a).a, 327926, this.a + this.e[0], this.b + this.f[0]);
                        break;
                    }
                    case 113: {
                        this.a.a.a(((k)this.a).a, 327930, this.a + this.e[0], this.b + this.f[0]);
                        break;
                    }
                    case 114: {
                        this.a.a.a(((k)this.a).a, 327934, this.a + this.e[0], this.b + this.f[0]);
                    }
                }
                return;
            }
            switch (this.a.b[this.a.E]) {
                case 99: {
                    this.a.a.a(((k)this.a).a, 327711, this.a, this.b);
                    break;
                }
                case 100: {
                    this.a.a.a(((k)this.a).a, 327716, this.a, this.b);
                    break;
                }
                case 101: {
                    this.a.a.a(((k)this.a).a, 327721, this.a, this.b);
                    break;
                }
                case 102: {
                    this.a.a.a(((k)this.a).a, 327771, this.a, this.b);
                }
            }
            switch (this.a.a[this.a.D]) {
                case 111: {
                    this.a.a.a(((k)this.a).a, 327922, this.a + this.e[1], this.b + this.f[1]);
                    return;
                }
                case 112: {
                    this.a.a.a(((k)this.a).a, 327926, this.a + this.e[1], this.b + this.f[1]);
                    return;
                }
                case 113: {
                    this.a.a.a(((k)this.a).a, 327930, this.a + this.e[1], this.b + this.f[1]);
                    return;
                }
                case 114: {
                    this.a.a.a(((k)this.a).a, 327934, this.a + this.e[1], this.b + this.f[1]);
                }
            }
        }
    }

    public final void c() {
        this.a.P = this.a.a.a(this.a.Z - 1, this.a.aa - 1);
        this.e(this.a.P + 180 + 1);
        if (!(this.a || this.b || this.d || this.c || this.a.p)) {
            if ((this.a.bt & 1) != 0 || this.a.a.e == 4) {
                this.i = false;
                if (this.a.X) {
                    if (this.a.P > 0) {
                        this.k = !this.a.T ? this.a.a.a(this.a.P + 180, 0) : this.a.a.b(this.a.P + 180, 0);
                        this.l = this.a.a.e(this.a.P + 180, 0);
                        this.m = this.a.a.d(this.a.P + 180, 0);
                        this.n = this.a.a.c(this.a.P + 180, 0);
                    }
                    if (!this.a.w) {
                        this.k = true;
                        this.l = true;
                        this.m = true;
                        this.n = true;
                    }
                    if (this.k && this.l && this.m && this.n) {
                        this.a(4);
                        this.g();
                        this.a.a.j();
                        return;
                    }
                    this.a.ah = 0;
                    return;
                }
                this.a.ah = 0;
                this.a.V = false;
                this.a.W = false;
                this.a.X = true;
                this.a.Y = false;
                return;
            }
            if ((this.a.bt & 2) != 0 || this.a.a.e == 6) {
                this.i = false;
                if (this.a.Y) {
                    if (this.a.P < 32400) {
                        this.k = !this.a.T ? this.a.a.a(this.a.P + 180 + 2, 1) : this.a.a.b(this.a.P + 180 + 2, 1);
                        this.l = this.a.a.e(this.a.P + 180 + 2, 1);
                        this.m = this.a.a.d(this.a.P + 180 + 2, 1);
                        this.n = this.a.a.c(this.a.P + 180 + 2, 1);
                    }
                    if (!this.a.w) {
                        this.k = true;
                        this.l = true;
                        this.m = true;
                        this.n = true;
                    }
                    if (this.k && this.l && this.m && this.n) {
                        this.b(4);
                        this.h();
                        this.a.a.j();
                        return;
                    }
                    this.a.ai = 0;
                    return;
                }
                this.a.ai = 0;
                this.a.V = false;
                this.a.W = false;
                this.a.X = false;
                this.a.Y = true;
                return;
            }
            if ((this.a.bt & 4) != 0 || this.a.a.e == 2) {
                this.i = false;
                if (this.a.V) {
                    if (this.a.P > 0) {
                        this.k = !this.a.T ? this.a.a.a(this.a.P + 1, 2) : this.a.a.b(this.a.P + 1, 2);
                        this.l = this.a.a.e(this.a.P + 1, 2);
                        this.m = this.a.a.d(this.a.P + 1, 2);
                        this.n = this.a.a.c(this.a.P + 1, 2);
                    }
                    if (!this.a.w) {
                        this.k = true;
                        this.l = true;
                        this.m = true;
                        this.n = true;
                    }
                    if (this.k && this.l && this.m && this.n) {
                        this.c(4);
                        this.f();
                        this.a.a.j();
                        return;
                    }
                    this.a.ag = 0;
                    return;
                }
                this.a.ag = 0;
                this.a.V = true;
                this.a.W = false;
                if (!this.a.T) {
                    this.a.X = false;
                    this.a.Y = false;
                    return;
                }
            } else if ((this.a.bt & 8) != 0 || this.a.a.e == 8) {
                this.i = false;
                if (this.a.W) {
                    if (this.a.P < 32400) {
                        this.k = !this.a.T ? this.a.a.a(this.a.P + 360 + 1, 3) : this.a.a.b(this.a.P + 360 + 1, 3);
                        this.l = this.a.a.e(this.a.P + 360 + 1, 3);
                        this.m = this.a.a.d(this.a.P + 360 + 1, 3);
                        this.n = this.a.a.c(this.a.P + 360 + 1, 3);
                    }
                    if (!this.a.w) {
                        this.k = true;
                        this.l = true;
                        this.m = true;
                        this.n = true;
                    }
                    if (this.k && this.l && this.m && this.n) {
                        this.d(4);
                        this.e();
                        this.a.a.j();
                        return;
                    }
                    this.a.af = 0;
                    return;
                }
                this.a.af = 0;
                this.a.V = false;
                this.a.W = true;
                if (!this.a.T) {
                    this.a.X = false;
                    this.a.Y = false;
                    return;
                }
            } else if (!this.i) {
                this.a.ai = 0;
                this.a.ah = 0;
                this.a.ag = 0;
                this.a.af = 0;
                this.i = true;
                return;
            }
        } else {
            if (this.a) {
                if (this.a.P > 0) {
                    this.k = !this.a.T ? this.a.a.a(this.a.P + 180, 0) : this.a.a.b(this.a.P + 360, 0);
                    this.l = this.a.a.e(this.a.P + 180, 0);
                    this.m = this.a.a.d(this.a.P + 180, 0);
                    this.n = this.a.a.c(this.a.P + 180, 0);
                }
                if (this.k && this.l && this.m && this.n) {
                    this.a(4);
                    this.g();
                    this.a.a.j();
                    return;
                }
                this.a = false;
                this.a.ah = 0;
                return;
            }
            if (this.b) {
                if (this.a.P < 32400) {
                    this.k = !this.a.T ? this.a.a.a(this.a.P + 180 + 2, 1) : this.a.a.b(this.a.P + 180 + 2, 1);
                    this.l = this.a.a.e(this.a.P + 180 + 2, 1);
                    this.m = this.a.a.d(this.a.P + 180 + 2, 1);
                    this.n = this.a.a.c(this.a.P + 180 + 2, 1);
                }
                if (this.k && this.l && this.m && this.n) {
                    this.b(4);
                    this.h();
                    this.a.a.j();
                    return;
                }
                this.b = false;
                this.a.ai = 0;
                return;
            }
            if (this.d) {
                if (this.a.P < 32400) {
                    this.k = !this.a.T ? this.a.a.a(this.a.P + 360 + 1, 3) : this.a.a.b(this.a.P + 360 + 1, 3);
                    this.l = this.a.a.e(this.a.P + 360 + 1, 3);
                    this.m = this.a.a.d(this.a.P + 360 + 1, 3);
                    this.n = this.a.a.c(this.a.P + 360 + 1, 3);
                }
                if (this.k && this.l && this.m && this.n) {
                    this.d(4);
                    this.e();
                    this.a.a.j();
                    return;
                }
                this.d = false;
                this.a.af = 0;
                return;
            }
            if (this.c) {
                if (this.a.P > 0) {
                    this.k = !this.a.T ? this.a.a.a(this.a.P + 1, 2) : this.a.a.b(this.a.P + 1, 2);
                    this.l = this.a.a.e(this.a.P + 1, 2);
                    this.m = this.a.a.d(this.a.P + 1, 2);
                    this.n = this.a.a.c(this.a.P + 1, 2);
                }
                if (this.k && this.l && this.m && this.n) {
                    this.c(4);
                    this.f();
                    this.a.a.j();
                    return;
                }
                this.c = false;
                this.a.ag = 0;
            }
        }
    }

    public final void d() {
        this.a.Y = true;
    }

    private void e(int n2) {
        if (this.a.k[n2] == this.a.e[4]) {
            this.j = true;
            return;
        }
        this.j = false;
    }
}

