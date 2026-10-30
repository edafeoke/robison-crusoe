/*
 * Decompiled with CFR 0.152.
 */
package data;

import data.k;
import data.p;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class v {
    private p a;
    public boolean a;
    public int a;
    private boolean b;
    private boolean c = false;
    public long a = 0L;
    private boolean d = true;
    private boolean e = true;
    private int b = 39423;

    public v(p p2) {
        this.a = p2;
    }

    public final void a() {
        this.a.a.s();
        this.a.a.h();
        if (this.a.z) {
            this.a.a.g();
        }
        if (this.a.y) {
            this.a.a.f();
        }
        if (this.a.x) {
            this.a.a.e();
        }
        if (this.a.B) {
            this.a.a.g();
        }
        this.a.a.i();
        this.a.a.f();
        this.a.a.b();
        this.a.a.c();
        this.a.a.a();
        this.a.a.b();
        if (this.a) {
            this.a.a.d();
        }
        if (this.a.B) {
            this.a.a.e();
        }
        if (this.a.C) {
            this.a.a.f();
        }
    }

    public final void b() {
        switch (this.a.l) {
            case 0: {
                return;
            }
            case 1: {
                if (!this.a.c) {
                    this.a.r = this.a.b ? this.a.a.a(this.a.r, 0, this.a.b - 1) : this.a.a.a(this.a.r, 1, this.a.b - 1);
                }
                this.a.a.e();
                return;
            }
            case 11: {
                if (this.a.a == 1) {
                    if ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1) {
                        this.a.ax = true;
                        this.a.a.a(1);
                        this.a.a.e();
                        this.a.a.a(false);
                        this.a.l = 1;
                        return;
                    }
                    if ((this.a.bu & 0x8000) == 0 && this.a.a.e != 3) break;
                    this.a.l = 1;
                    this.a.aw = true;
                    return;
                }
                if ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1) {
                    this.a.a.destroyApp(true);
                    return;
                }
                if ((this.a.bu & 0x8000) == 0 && this.a.a.e != 3) break;
                this.a.l = 1;
                this.a.aw = true;
                return;
            }
            case 2: {
                this.a.A = this.a.a.a(this.a.A, 0, 1);
                this.a.a.g();
                return;
            }
            case 3: {
                this.a.d = this.a.a.a(this.a.d, 0, this.a.e - 1);
                if (this.a.hasPointerEvents() && this.a.d > this.a.e - 1) {
                    if (this.a.a.b) {
                        this.a.d = (Integer)this.a.a.elementAt(this.a.f);
                        this.a.a.b = false;
                    } else {
                        this.a.d = (Integer)this.a.a.elementAt(this.a.g);
                    }
                }
                this.a.a.a();
                this.a.a.f();
                return;
            }
            case 10: {
                this.a.a.q();
                return;
            }
            case 4: {
                this.a.a.q();
                return;
            }
            case 5: {
                if ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1) {
                    this.a.l = 6;
                    this.a.a();
                }
                if ((this.a.bu & 0x8000) == 0 && this.a.a.e != 3) break;
                this.a.a.r();
                this.a.a.a();
                this.a.a.b(this.a.aG);
                this.g();
                this.a.l = 1;
                this.a.a.h();
                this.a.a();
                return;
            }
            case 6: {
                this.a.a.f();
                switch (this.a.m) {
                    case 2: {
                        if (!this.a.aM) {
                            this.a.a.h();
                            this.a.a.i();
                            if ((this.a.bu & 0x8000) != 0 || (this.a.bu & 4) != 0 || (this.a.bu & 8) != 0 || this.a.a.e == 3 || this.a.a.e == 2 || this.a.a.e == 8) {
                                this.a.m = 0;
                                this.a.a.f();
                                this.a.aK = true;
                                this.a.aw = true;
                            }
                        }
                        this.a.o = true;
                        break;
                    }
                    case 1: {
                        if (this.a.n != 1 && !this.a.S && !this.a.v) {
                            this.a.s = this.a.a.b(this.a.s, 0, 5);
                        }
                        switch (this.a.n) {
                            case 0: {
                                this.b = false;
                                this.a.z = this.a.a.a(this.a.z, 0, this.a.aB - 1);
                                this.a.a.b();
                                this.a.a.a(0);
                                if ((this.a.bu & 0x8000) == 0 && this.a.a.e != 3) break;
                                this.a.m = 0;
                                this.a.a.f();
                                this.a.z = 0;
                                this.a.au = 0;
                                break;
                            }
                            case 1: {
                                this.a.a.h();
                                this.a.a.i();
                                if ((this.a.bu & 0x8000) == 0 && (this.a.bu & 4) == 0 && (this.a.bu & 8) == 0 && this.a.a.e != 3 && this.a.a.e != 2 && this.a.a.e != 8) break;
                                this.a.n = this.a.o;
                                this.a.aw = true;
                                this.a.aK = true;
                                break;
                            }
                            case 2: {
                                if ((this.a.bu & 0x8000) == 0 && this.a.a.e != 3) break;
                                this.a.m = 0;
                                this.a.n = 0;
                                this.a.a.f();
                                break;
                            }
                            case 3: {
                                if (this.a.v) {
                                    this.a.u = this.a.a.a(this.a.u, 0, 3);
                                    this.a.a.p();
                                    if ((this.a.bu & 0x8000) == 0 && this.a.a.e != 3) break;
                                    this.a.v = false;
                                    this.a.u = -1;
                                    this.a.aw = true;
                                    break;
                                }
                                if ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1) {
                                    this.a.v = true;
                                    this.a.u = 0;
                                    this.a.aw = true;
                                }
                                if ((this.a.bu & 0x8000) == 0 && this.a.a.e != 3) break;
                                this.a.m = 0;
                                this.a.n = 0;
                                this.a.a.f();
                                break;
                            }
                            case 4: {
                                this.b = false;
                                this.a.v = this.a.a.a(this.a.v, 0, this.a.av - 1);
                                this.a.a.c();
                                this.a.a.a(4);
                                if ((this.a.bu & 0x8000) == 0 && this.a.a.e != 3) break;
                                this.a.m = 0;
                                this.a.n = 0;
                                this.a.a.f();
                                this.a.v = 0;
                                this.a.au = 0;
                                break;
                            }
                            case 5: {
                                if (this.a.S) {
                                    if ((this.a.bu & 4) != 0) {
                                        if (this.a.R > 0) {
                                            this.a.R -= 60;
                                        }
                                        this.a.aw = true;
                                        this.a.o = 0L;
                                        this.a.aI = true;
                                        if (this.a.aO - 3 > 0) {
                                            this.a.aO -= 3;
                                        }
                                    }
                                    if ((this.a.bu & 8) != 0) {
                                        if (this.a.R < 120) {
                                            this.a.R += 60;
                                        }
                                        this.a.aw = true;
                                        this.a.o = 0L;
                                        this.a.aJ = true;
                                        if (this.a.aO + 3 <= 9) {
                                            this.a.aO += 3;
                                        }
                                    }
                                    if ((this.a.bu & 1) != 0) {
                                        if (this.a.Q > 0) {
                                            this.a.Q -= 60;
                                        }
                                        this.a.aw = true;
                                        this.a.o = 0L;
                                        this.a.aG = true;
                                        if (this.a.aO - 1 == 2 || this.a.aO - 1 == 1 || this.a.aO - 1 == 4 || this.a.aO - 1 == 5 || this.a.aO - 1 == 7 || this.a.aO - 1 == 8) {
                                            --this.a.aO;
                                        }
                                    }
                                    if ((this.a.bu & 2) != 0) {
                                        if (this.a.Q < 120) {
                                            this.a.Q += 60;
                                        }
                                        this.a.aw = true;
                                        this.a.o = 0L;
                                        this.a.aH = true;
                                        if (this.a.aO + 1 == 2 || this.a.aO + 1 == 3 || this.a.aO + 1 == 5 || this.a.aO + 1 == 6 || this.a.aO + 1 == 8 || this.a.aO + 1 == 9) {
                                            ++this.a.aO;
                                        }
                                    }
                                    if ((this.a.bu & 0x8000) == 0 && this.a.a.e != 3) break;
                                    this.a.S = false;
                                    this.a.aw = true;
                                    break;
                                }
                                if ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1) {
                                    this.a.S = true;
                                    this.a.aw = true;
                                }
                                if ((this.a.bu & 0x8000) == 0 && this.a.a.e != 3) break;
                                this.a.m = 0;
                                this.a.n = 0;
                                this.a.a.f();
                                break;
                            }
                            case 6: {
                                if ((this.a.bu & 0x8000) == 0 && this.a.a.e != 3) break;
                                this.a.m = 0;
                                this.a.n = 0;
                                this.a.a.f();
                            }
                        }
                        this.a.o = true;
                        break;
                    }
                    case 0: {
                        this.a.o = false;
                        this.b = true;
                        this.a.az = true;
                        if (!this.a.aK && !this.a.aF) {
                            if (!(this.a || this.a.U || this.a.aN)) {
                                this.a.a.c();
                            }
                            if (this.a.U) {
                                this.a.a.d();
                            }
                            if (this.a.aN) {
                                this.a.a.e();
                            }
                            if ((this.a.bu & 0x8000) != 0 || this.a.a.e == 3) {
                                this.a.l = 5;
                                this.a.aL = true;
                                this.a.aw = true;
                            }
                            if (this.a && ((this.a.bu & 0x4000) != 0 || (this.a.bu & 0x10) != 0 || this.a.a.e == 1)) {
                                this.a.c = 0;
                                this.a.a.a();
                                this.a.a.b(this.a.aG);
                                this.g();
                                this.a.l = 1;
                            }
                            if (!(this.a || this.a.U || (this.a.bu & 0x10) == 0 && (this.a.bu & 0x4000) == 0 && this.a.a.e != 1)) {
                                this.a.m = 2;
                            }
                        }
                        if (this.a.aF && this.a.H > 8000L) {
                            this.a.l = 9;
                            this.a.y = 0L;
                            this.a.aw = true;
                        }
                        this.a.aK = false;
                        break;
                    }
                    case 3: {
                        if ((this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || (this.a.bu & 0x4000) != 0 || this.a.a.e == 1 || this.a.a.e == 3) {
                            if (this.b) {
                                if (this.c) {
                                    this.a.p = 0;
                                    this.a.m = 8;
                                    this.a.w = 0;
                                    this.a.a.f();
                                    this.c = false;
                                } else {
                                    this.a.n = 0;
                                    this.a.m = 0;
                                    this.a.a.f();
                                    this.a.aB = false;
                                    this.a.aC = false;
                                    this.a.aD = false;
                                }
                                this.b = false;
                            } else {
                                this.a.m = 1;
                                this.a.n = this.a.o;
                                if (this.a.o == 0) {
                                    this.a.s = 0;
                                }
                                if (this.a.o == 4) {
                                    this.a.s = 5;
                                }
                                this.a.a.f();
                            }
                            this.a.aw = true;
                            this.a.a();
                        }
                        this.a.a.q();
                        this.a.o = true;
                        break;
                    }
                    case 4: {
                        this.a.t = this.a.a.a(this.a.t, 0, 1);
                        this.a.a.m();
                        if ((this.a.bu & 0x8000) != 0 || this.a.a.e == 3) {
                            this.a.m = 0;
                            this.a.a.f();
                        }
                        this.a.o = true;
                        break;
                    }
                    case 5: {
                        this.a.y = this.a.aB ? this.a.a.a(this.a.y, 0, this.a.ay - 1) : this.a.a.a(this.a.y, 0, this.a.ax - 1);
                        this.a.a.d();
                        this.a.a.n();
                        if ((this.a.bu & 0x8000) != 0 || this.a.a.e == 3) {
                            this.a.a.k();
                            this.a.a.f();
                        }
                        this.a.o = true;
                        break;
                    }
                    case 6: {
                        this.a.a.o();
                        if ((this.a.bu & 8) != 0 || this.a.a.e == 8) {
                            if (this.a.B < this.a.aA - this.a.as) {
                                ++this.a.B;
                            }
                            this.a.aw = true;
                            this.a.n = 0L;
                            this.a.i = true;
                            this.a.aA = 0;
                        }
                        if ((this.a.bu & 4) != 0 || this.a.a.e == 2) {
                            if (this.a.B > 0) {
                                --this.a.B;
                            }
                            this.a.aw = true;
                            this.a.n = 0L;
                            this.a.h = true;
                            this.a.aA = 0;
                        }
                        if (!(this.a.ae || (this.a.bu & 0x8000) == 0 && this.a.a.e != 3)) {
                            if (this.a.aC || this.a.aD) {
                                this.a.m = 0;
                                this.a.aD = false;
                                this.a.aC = false;
                                this.a.a.f();
                            } else {
                                this.a.m = 5;
                            }
                            this.a.aw = true;
                            this.a.B = 0;
                            this.a.a.f();
                        }
                        this.a.o = true;
                        break;
                    }
                    case 7: {
                        switch (this.a.q) {
                            case 0: {
                                this.a.a.b();
                                if (!this.a.a.a) break;
                                this.a.a.a = false;
                                if ((this.a.bu & 0x8000) == 0 && (this.a.bu & 0x4000) == 0 && this.a.a.e != 1 && this.a.a.e != 3) break;
                                if (this.a.a.a > 0) {
                                    int n2 = this.a.a.b;
                                    this.a.q[n2] = this.a.q[n2] - 1;
                                    if (this.a.q[this.a.a.b] == 0) {
                                        int n3 = this.a.a.b;
                                        while (n3 < this.a.aB - 1) {
                                            this.a.m[n3] = this.a.m[n3 + 1];
                                            this.a.q[n3] = this.a.q[n3 + 1];
                                            ++n3;
                                        }
                                        --this.a.aB;
                                    }
                                }
                                this.a.m = 0;
                                this.a.a.f();
                                break;
                            }
                            case 1: {
                                this.a.a.e();
                                if (!this.a.a.a) break;
                                this.a.a.a = false;
                                if ((this.a.bu & 0x8000) == 0 && (this.a.bu & 0x4000) == 0 && (this.a.bu & 0x10) == 0 && this.a.a.e != 1 && this.a.a.e != 3) break;
                                if (this.a.a.a > 0) {
                                    int n4 = this.a.a.b;
                                    this.a.q[n4] = this.a.q[n4] - 1;
                                    if (this.a.q[this.a.a.b] == 0) {
                                        int n5 = this.a.a.b;
                                        while (n5 < this.a.aB - 1) {
                                            this.a.m[n5] = this.a.m[n5 + 1];
                                            this.a.q[n5] = this.a.q[n5 + 1];
                                            ++n5;
                                        }
                                        --this.a.aB;
                                    }
                                }
                                this.a.m = 0;
                                this.a.a.f();
                                break;
                            }
                            case 2: {
                                this.a.a.d();
                                if (!this.a.a.a) break;
                                this.a.a.a = false;
                                if ((this.a.bu & 0x8000) == 0 && (this.a.bu & 0x4000) == 0 && (this.a.bu & 0x10) == 0 && this.a.a.e != 1 && this.a.a.e != 3) break;
                                if (this.a.a.a > 0) {
                                    int n6 = this.a.a.b;
                                    this.a.q[n6] = this.a.q[n6] - 1;
                                    if (this.a.q[this.a.a.b] == 0) {
                                        int n7 = this.a.a.b;
                                        while (n7 < this.a.aB - 1) {
                                            this.a.m[n7] = this.a.m[n7 + 1];
                                            this.a.q[n7] = this.a.q[n7 + 1];
                                            ++n7;
                                        }
                                        --this.a.aB;
                                    }
                                }
                                this.a.m = 0;
                                this.a.a.f();
                                break;
                            }
                            case 3: {
                                this.a.a.d();
                                if (!this.a.a.a) break;
                                this.a.a.a = false;
                                if ((this.a.bu & 0x8000) == 0 && (this.a.bu & 0x4000) == 0 && (this.a.bu & 0x10) == 0 && this.a.a.e != 1 && this.a.a.e != 3) break;
                                if (this.a.a.a > 0) {
                                    int n8 = this.a.a.b;
                                    this.a.q[n8] = this.a.q[n8] - 1;
                                    if (this.a.q[this.a.a.b] == 0) {
                                        int n9 = this.a.a.b;
                                        while (n9 < this.a.aB - 1) {
                                            this.a.m[n9] = this.a.m[n9 + 1];
                                            this.a.q[n9] = this.a.q[n9 + 1];
                                            ++n9;
                                        }
                                        --this.a.aB;
                                    }
                                }
                                this.a.m = 0;
                                this.a.a.f();
                            }
                        }
                        this.a.aw = true;
                        this.a.o = true;
                        this.a.aK = false;
                        break;
                    }
                    case 8: {
                        switch (this.a.p) {
                            case 0: {
                                this.c = true;
                                this.b = true;
                                this.a.w = this.a.a.a(this.a.w, 0, this.a.aw - 1);
                                this.a.a.l();
                                if ((this.a.bu & 0x8000) == 0 && this.a.a.e != 3) break;
                                this.a.m = 0;
                                this.c = false;
                                this.b = false;
                                this.a.aB = false;
                                this.a.a.f();
                                break;
                            }
                            case 1: {
                                this.a.a.h();
                                this.a.a.i();
                                if ((this.a.bu & 0x8000) == 0 && (this.a.bu & 4) == 0 && (this.a.bu & 8) == 0 && this.a.a.e != 3 && this.a.a.e != 2 && this.a.a.e != 8) break;
                                this.a.p = 0;
                                this.c = false;
                                this.a.aw = true;
                            }
                        }
                        this.a.o = true;
                        break;
                    }
                    case 9: {
                        this.a.a.q();
                        break;
                    }
                    case 10: {
                        if (this.a.p) {
                            this.a.aw = true;
                            if ((this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || (this.a.bu & 0x4000) != 0 || this.a.a.e == 1 || this.a.a.e == 3) {
                                this.a.g = 4001L;
                            }
                            if (this.a.g > 4000L) {
                                this.a.aK = false;
                                this.a.g = 0L;
                                this.a.p = false;
                                this.a.n = true;
                                this.a.m = true;
                                this.a.m = this.a.j;
                                this.a.b();
                            }
                        }
                        if (!this.a.q) break;
                        this.a.aw = true;
                        if ((this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || (this.a.bu & 0x4000) != 0 || this.a.a.e == 1 || this.a.a.e == 3) {
                            this.a.g = 8001L;
                        }
                        if (this.a.g <= 8000L) break;
                        this.a.aK = false;
                        this.a.g = 0L;
                        this.a.q = false;
                        this.a.n = true;
                        this.a.m = true;
                        this.a.m = this.a.j;
                        this.a.b();
                    }
                }
                return;
            }
            case 7: {
                return;
            }
            case 8: {
                if ((this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || (this.a.bu & 0x4000) != 0 || this.a.a.e == 1 || this.a.a.e == 3) {
                    this.a.y = 8001L;
                }
                if (this.a.y > 8000L) {
                    this.g();
                }
                this.a.a.h();
                return;
            }
            case 9: {
                if ((this.a.bu & 0x8000) != 0 || (this.a.bu & 0x10) != 0 || (this.a.bu & 0x4000) != 0 || this.a.a.e == 1 || this.a.a.e == 3) {
                    this.a.y = 8001L;
                }
                if (this.a.y > 8000L) {
                    this.g();
                }
                this.a.a.h();
            }
        }
    }

    public final void c() {
        if (this.a.aw) {
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, k.a(), k.b());
            this.a.a.a(((k)this.a).a, this.a.a.a(94), k.a() >> 1, (k.b() >> 1) - 30, 0xFFFFFF, 17, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
            if (this.a.aO) {
                this.a.a.a(((k)this.a).a, this.a.a.a(95), k.a() >> 1, k.b() >> 1, 0xFFFFFF, 17, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
                return;
            }
            if (this.a.aP) {
                this.a.a.a(((k)this.a).a, this.a.a.a(110), k.a() >> 1, k.b() >> 1, 0xFFFFFF, 17, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
                return;
            }
            this.a.a.a(((k)this.a).a, this.a.a.a(96), k.a() >> 1, k.b() >> 1, 0xFFFFFF, 17, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
        }
    }

    public final void d() {
        if (this.a.aw) {
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, k.a(), k.b());
            this.a.a.a(((k)this.a).a, this.a.a.a(97), k.a() >> 1, k.b() / 4, 0xFFFFFF, 17, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
            this.a.a.a(((k)this.a).a, this.a.a.a(98), k.a() >> 1, k.b() / 4 + 20, 0xFFFFFF, 17, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
        }
    }

    public final void a(boolean bl) {
        this.b(bl);
        if (bl) {
            if (this.a == 70) {
                if (this.a.c == 0) {
                    this.c(false);
                    return;
                }
                this.c(true);
                return;
            }
            if (this.a == 80) {
                return;
            }
            if (this.a == 100) {
                this.a.a.a(true, true);
                this.a = this.a.a.b();
                this.a.a.q();
                this.a.a.d();
                this.a.a.p();
                this.a.a.t();
                return;
            }
        } else {
            if (this.a == 80) {
                this.a.a.d();
                this.a.a.b();
                return;
            }
            if (this.a == 100) {
                this.a.Z = 8;
                this.a.aa = 148;
                this.a.ab = this.a.Z - this.a.N / 2;
                this.a.ac = this.a.aa - this.a.O / 2;
                this.a.a.q();
                this.a.d = true;
            }
        }
    }

    private void b(boolean bl) {
        this.a.p = false;
        this.a.c = true;
        if (this.a == 0) {
            this.a.aj = false;
            this.a.ak = false;
            this.a.al = false;
            this.a.am = false;
            this.a.an = false;
            this.a.ao = false;
            this.a.ap = false;
            this.a.aq = false;
            this.a.ar = false;
            this.a.as = false;
            this.a.at = false;
            this.a.au = false;
            this.a.av = false;
            this.a.a();
            if (!bl) {
                this.a.ad = true;
            } else if (this.a.ag) {
                int n2 = 0;
                while (n2 < 120) {
                    if (this.a.c[n2] != null) {
                        this.a.c[n2] = null;
                    }
                    ++n2;
                }
                this.a.a.d();
            }
            if (!this.d) {
                this.a.a.a("BIGislandObjects", this.a.l);
            }
            this.a.a.d();
            this.a.a.a();
            this.a.aj = this.a.M / 2;
            this.a.a();
            return;
        }
        if (this.a == 20) {
            if (this.d) {
                this.a.a.e();
                this.d = false;
                this.e = false;
                this.a.a();
                return;
            }
            if (!bl && this.e) {
                this.e = false;
                this.a.a.a = 0;
                this.a.a.b = 0;
                this.a.a.c = 0;
                this.a.a.d = 0;
                this.a.a.e = 0;
                this.a.a.f = 0;
                this.a.a.g = 0;
                this.a.a.h = 0;
                this.a.a.i = 0;
                this.a.a.j = 0;
                this.a.a.k = 0;
                this.a.a.l = 0;
                this.a.a.m = 0;
                this.a.a.n = 0;
                this.a.a.o = 0;
                this.a.a.p = 0;
                this.a.a.q = 0;
                this.a.a.r = 0;
                this.a.a.s = 0;
                this.a.a.t = 0;
                this.a.a.u = 0;
                this.a.a.v = 0;
                this.a.a.w = 0;
                this.a.a.x = 0;
                this.a.a.e();
                this.a.a();
                return;
            }
        } else {
            if (this.a == 40) {
                this.a.a.c();
                this.a.a.f();
                this.a.a.g();
                this.a.a();
                return;
            }
            if (this.a == 60) {
                if (!bl) {
                    this.a.a.d();
                    this.a.a.p();
                }
                this.a.m = 0;
                this.a.n = 0;
                this.a.b = this.a.a = (float)((k.a() / 3 - 16) / 1);
                this.a.c = this.a.a;
                this.a.e = 0.0f;
                this.a.f = 0.0f;
                this.a.g = 0.0f;
                this.a.L = 0;
                this.a.a();
            }
        }
    }

    public final void e() {
        this.a += 2;
        if (this.a <= 100) {
            if (this.a.r == 0) {
                this.a.a.a(0);
            } else if (this.a.r == 1) {
                this.a.a.a(1);
            }
            this.a.a(((k)this.a).a, 20, k.b() / 3 * 2, k.a() - 40, 12, this.a, 100, 0, this.b);
            return;
        }
        this.a = -2;
        this.a.a.h();
        if (this.a.e) {
            this.a.aw = true;
            this.a.C = 0L;
            this.a.c = false;
            this.a.e = false;
        } else {
            this.a.l = 6;
        }
        this.a.a();
    }

    private void c(boolean bl) {
        long l2;
        long l3;
        long l4 = (long)this.a.aG - this.a;
        int n2 = 0;
        if (this.a.K < this.a.c) {
            if (l4 > 0L) {
                --l4;
            }
            l3 = 24L - this.a.c + this.a.K;
        } else {
            l3 = this.a.K - this.a.c;
        }
        if (this.a.J < this.a.d) {
            if (l3 > 0L) {
                --l3;
            }
            l2 = 0L;
        }
        l2 = 0L;
        long l5 = 24L * l4 + l3;
        if (l5 >= 1L && l5 <= 15L) {
            n2 = 10;
        } else if (l5 >= 16L && l5 <= 35L) {
            n2 = 20;
        } else if (l5 >= 36L && l5 <= 72L) {
            n2 = 30;
        } else if (l5 >= 73L && l5 <= 119L) {
            n2 = 40;
        } else if (l5 > 119L) {
            n2 = 50;
        }
        if (bl) {
            this.a.a += this.a.d / 100.0f * (float)n2;
            this.a.b += this.a.d / 100.0f * (float)n2;
            this.a.c += this.a.d / 100.0f * (float)n2;
            if (this.a.a >= this.a.d) {
                this.a.a = this.a.d;
            }
            if (this.a.b >= this.a.d) {
                this.a.b = this.a.d;
            }
            if (this.a.c >= this.a.d) {
                this.a.c = this.a.d;
                return;
            }
        } else {
            if (this.a.a > 10.0f) {
                this.a.a -= this.a.d / 100.0f * (float)n2;
                if (this.a.a < 10.0f) {
                    this.a.a = 10.0f;
                }
            }
            if (this.a.b > 10.0f) {
                this.a.b -= this.a.d / 100.0f * (float)n2;
                if (this.a.b < 0.0f) {
                    this.a.b = 10.0f;
                }
            }
            if (this.a.c > 10.0f) {
                this.a.c -= this.a.d / 100.0f * (float)n2;
                if (this.a.c < 0.0f) {
                    this.a.c = 10.0f;
                }
            }
        }
    }

    public final void a(int n2) {
        this.a.L += n2;
        if (this.a.L >= 50) {
            this.a.L -= 50;
            if (this.a.g < this.a.d) {
                this.a.g += 1.0f;
            }
        }
    }

    public final void f() {
        if (!this.a.D) {
            if (this.a.k >= 60000L) {
                this.a.a.d();
                this.a.a = this.a.J < 10L ? new StringBuffer().append(this.a.K + ":0" + this.a.J) : new StringBuffer().append(this.a.K + ":" + this.a.J);
                this.a.k = 0L;
                this.a.aw = true;
                return;
            }
        } else if (this.a.e > 4000L) {
            if (this.a.J < 60L) {
                ++this.a.J;
            }
            if (this.a.J == 60L) {
                this.a.J = 0L;
                ++this.a.K;
                if (this.a.K == 24L) {
                    this.a.K = 0L;
                }
            }
            this.a.e = 0L;
            this.a.aw = true;
            if (this.a.J < 10L) {
                this.a.a = new StringBuffer().append(this.a.K + ":0" + this.a.J);
                return;
            }
            this.a.a = new StringBuffer().append(this.a.K + ":" + this.a.J);
        }
    }

    public final void g() {
        this.a.a.a(14);
        this.a.l = 1;
        this.a.ap = 0;
        this.a.b = 0L;
        this.a.aO = false;
        this.a.aP = false;
        this.a.r = this.a.b ? 0 : 1;
        this.a = false;
        this.a.aF = false;
        this.a.c = false;
        this.a.aw = true;
        this.a.T = false;
        this.a.aB = 0;
        int n2 = 0;
        while (n2 < this.a.m.length) {
            this.a.q[n2] = 0;
            ++n2;
        }
        this.a.av = 0;
        int n3 = 0;
        while (n3 < this.a.a.length) {
            this.a.g[n3] = 0;
            ++n3;
        }
        this.a.aw = 0;
        int n4 = 0;
        while (n4 < this.a.a.length) {
            this.a.a[n4] = null;
            this.a.h[n4] = 0;
            ++n4;
        }
        int n5 = 0;
        while (n5 < this.a.r.length) {
            this.a.r[n5] = 0;
            ++n5;
        }
        this.a.aC = 0;
        int n6 = 0;
        while (n6 < this.a.s.length) {
            this.a.s[n6] = 0;
            ++n6;
        }
        this.a.aD = 0;
        this.a.a = new int[5];
        this.a.b = new int[5];
        this.a.c = new int[5];
        this.a.d = new int[5];
        this.a.H = 1;
        this.a.I = 1;
        this.a.J = 1;
        this.a.K = 1;
        this.a.D = 0;
        this.a.E = 0;
        this.a.F = 0;
        this.a.G = 0;
    }
}

