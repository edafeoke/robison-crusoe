/*
 * Decompiled with CFR 0.152.
 */
package data;

import data.ac;
import data.k;
import data.p;

public final class r {
    private p a;

    public r(p p2) {
        this.a = p2;
    }

    public final void a(StringBuffer stringBuffer) {
        int n2 = this.a.hasPointerEvents() ? 52 + this.a.aI * this.a.as : 40 + this.a.aI * this.a.as;
        if (this.a.aw) {
            int n3;
            int n4;
            int n5;
            this.a.aA = 0;
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, k.a(), k.b());
            int n6 = 0;
            while (n6 < this.a.as) {
                if (n6 % 2 == 0) {
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
                    ((k)this.a).a.fillRect(0, 52 + n6 * this.a.aI, k.a(), this.a.aI);
                } else {
                    ((k)this.a).a.fillRect(0, 40 + n6 * this.a.aI, k.a(), this.a.aI);
                }
                ++n6;
            }
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655375, 0, 16);
                n5 = 1;
                while (n5 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655376, 2 * n5, 16);
                    ++n5;
                }
                this.a.a.a(((k)this.a).a, 655377, k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, k.a() >> 1, 16 + (24 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, 655360, 0, 16);
                n5 = 1;
                while (n5 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n5, 16);
                    ++n5;
                }
                this.a.a.a(((k)this.a).a, 655362, k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, k.a() >> 1, 16 + (16 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
            }
            n5 = 0;
            while (n5 < 120) {
                if (this.a.aB) {
                    if (this.a.e[this.a.y] == this.a.b[n5]) {
                        this.a.ao = n5;
                    }
                } else if (this.a.aC) {
                    if (n5 == 19) {
                        this.a.ao = n5;
                    }
                } else if (this.a.aD) {
                    if (n5 == 94) {
                        this.a.ao = n5;
                    }
                } else if (this.a.d[this.a.y] == this.a.b[n5]) {
                    this.a.ao = n5;
                }
                ++n5;
            }
            this.a.a.n = 0;
            this.a.a.o = 0;
            this.a.a.p = 0;
            this.a.a.q = 0;
            this.a.a.r = 0;
            this.a.a.s = 0;
            this.a.am = 0;
            this.a.ak = 0;
            if (this.a.a[this.a.ao][0] != 0) {
                this.a.G = false;
                this.a.ak = 1;
                this.a.an = 1;
                n4 = 0;
                while (n4 < this.a.aB) {
                    if (this.a.m[n4] == this.a.a[this.a.ao][0]) {
                        n3 = 1;
                        while (n3 < this.a.n.length) {
                            if (this.a.a[this.a.ao][1] == this.a.n[n3]) {
                                this.a.a.u = n3;
                            }
                            ++n3;
                        }
                        if (this.a.q[n4] >= this.a.a.u) {
                            this.a.G = true;
                        }
                        this.a.a.n = this.a.q[n4];
                    }
                    ++n4;
                }
                this.a.g[this.a.aA] = this.a.a.a(108).toString();
                ++this.a.aA;
                this.a.g[this.a.aA] = this.a.b[this.a.a[this.a.ao][0]] + ".." + this.a.a[this.a.ao][1] + " (" + this.a.a.n + ")";
                ++this.a.aA;
            }
            if (this.a.a[this.a.ao][2] != 0) {
                this.a.H = false;
                this.a.ak = 2;
                this.a.an = 2;
                n4 = 0;
                while (n4 < this.a.aB) {
                    if (this.a.m[n4] == this.a.a[this.a.ao][2]) {
                        n3 = 1;
                        while (n3 < this.a.n.length) {
                            if (this.a.a[this.a.ao][3] == this.a.n[n3]) {
                                this.a.a.v = n3;
                            }
                            ++n3;
                        }
                        if (this.a.q[n4] >= this.a.a.v) {
                            this.a.H = true;
                        }
                        this.a.a.o = this.a.q[n4];
                    }
                    ++n4;
                }
                this.a.g[this.a.aA] = this.a.b[this.a.a[this.a.ao][2]] + ".." + this.a.a[this.a.ao][3] + " (" + this.a.a.o + ")";
                ++this.a.aA;
            }
            if (this.a.a[this.a.ao][4] != 0) {
                this.a.I = false;
                this.a.ak = 3;
                this.a.an = 3;
                n4 = 0;
                while (n4 < this.a.aB) {
                    if (this.a.m[n4] == this.a.a[this.a.ao][4]) {
                        n3 = 1;
                        while (n3 < this.a.n.length) {
                            if (this.a.a[this.a.ao][5] == this.a.n[n3]) {
                                this.a.a.w = n3;
                            }
                            ++n3;
                        }
                        if (this.a.q[n4] >= this.a.a.w) {
                            this.a.I = true;
                        }
                        this.a.a.p = this.a.q[n4];
                    }
                    ++n4;
                }
                this.a.g[this.a.aA] = this.a.b[this.a.a[this.a.ao][4]] + ".." + this.a.a[this.a.ao][5] + " (" + this.a.a.p + ")";
                ++this.a.aA;
            }
            if (this.a.a[this.a.ao][6] != 0) {
                this.a.J = false;
                this.a.ak = 4;
                this.a.an = 4;
                n4 = 0;
                while (n4 < this.a.aB) {
                    if (this.a.m[n4] == this.a.a[this.a.ao][6]) {
                        n3 = 1;
                        while (n3 < this.a.n.length) {
                            if (this.a.a[this.a.ao][7] == this.a.n[n3]) {
                                this.a.a.x = n3;
                            }
                            ++n3;
                        }
                        if (this.a.q[n4] >= this.a.a.x) {
                            this.a.J = true;
                        }
                        this.a.a.q = this.a.q[n4];
                    }
                    ++n4;
                }
                this.a.g[this.a.aA] = this.a.b[this.a.a[this.a.ao][6]] + ".." + this.a.a[this.a.ao][7] + " (" + this.a.a.q + ")";
                ++this.a.aA;
            }
            if (this.a.a[this.a.ao][8] != 0) {
                this.a.K = false;
                this.a.ak = 5;
                this.a.an = 5;
                n4 = 0;
                while (n4 < this.a.aB) {
                    if (this.a.m[n4] == this.a.a[this.a.ao][8]) {
                        n3 = 1;
                        while (n3 < this.a.n.length) {
                            if (this.a.a[this.a.ao][9] == this.a.n[n3]) {
                                this.a.a.y = n3;
                            }
                            ++n3;
                        }
                        if (this.a.q[n4] >= this.a.a.y) {
                            this.a.K = true;
                        }
                        this.a.a.r = this.a.q[n4];
                    }
                    ++n4;
                }
                this.a.g[this.a.aA] = this.a.b[this.a.a[this.a.ao][8]] + ".." + this.a.a[this.a.ao][9] + " (" + this.a.a.r + ")";
                ++this.a.aA;
            }
            if (this.a.a[this.a.ao][10] != 0) {
                this.a.L = false;
                this.a.ak = 6;
                this.a.an = 6;
                n4 = 0;
                while (n4 < this.a.aB) {
                    if (this.a.m[n4] == this.a.a[this.a.ao][10]) {
                        n3 = 1;
                        while (n3 < this.a.n.length) {
                            if (this.a.a[this.a.ao][11] == this.a.n[n3]) {
                                this.a.a.z = n3;
                            }
                            ++n3;
                        }
                        if (this.a.q[n4] >= this.a.a.z) {
                            this.a.L = true;
                        }
                        this.a.a.s = this.a.q[n4];
                    }
                    ++n4;
                }
                this.a.g[this.a.aA] = this.a.b[this.a.a[this.a.ao][10]] + ".." + this.a.a[this.a.ao][11] + " (" + this.a.a.s + ")";
                ++this.a.aA;
            }
            if (this.a.b[this.a.ao][0] != 0 && this.a.b[this.a.ao][0] != -1) {
                this.a.M = false;
                this.a.am = 1;
                n4 = 0;
                while (n4 < this.a.av) {
                    if (this.a.a[n4] == this.a.b[this.a.ao][0]) {
                        n3 = 1;
                        while (n3 < this.a.n.length) {
                            if (this.a.b[this.a.ao][1] == this.a.n[n3]) {
                                this.a.a.A = n3;
                            }
                            ++n3;
                        }
                        if (this.a.g[n4] >= this.a.a.A) {
                            this.a.M = true;
                        }
                    }
                    ++n4;
                }
                this.a.g[this.a.aA] = "";
                ++this.a.aA;
                this.a.g[this.a.aA] = this.a.a.a(109).toString();
                ++this.a.aA;
                this.a.g[this.a.aA] = this.a.b[this.a.b[this.a.ao][0]];
                ++this.a.aA;
            }
            if (this.a.b[this.a.ao][2] != 0) {
                this.a.N = false;
                this.a.am = 2;
                n4 = 0;
                while (n4 < this.a.av) {
                    if (this.a.a[n4] == this.a.b[this.a.ao][2]) {
                        n3 = 1;
                        while (n3 < this.a.n.length) {
                            if (this.a.b[this.a.ao][3] == this.a.n[n3]) {
                                this.a.a.B = n3;
                            }
                            ++n3;
                        }
                        if (this.a.g[n4] >= this.a.a.B) {
                            this.a.N = true;
                        }
                    }
                    ++n4;
                }
                this.a.g[this.a.aA] = this.a.b[this.a.b[this.a.ao][2]];
                ++this.a.aA;
            }
            if (this.a.b[this.a.ao][4] != 0) {
                this.a.O = false;
                this.a.am = 3;
                n4 = 0;
                while (n4 < this.a.av) {
                    if (this.a.a[n4] == this.a.b[this.a.ao][4]) {
                        n3 = 1;
                        while (n3 < this.a.n.length) {
                            if (this.a.b[this.a.ao][5] == this.a.n[n3]) {
                                this.a.a.C = n3;
                            }
                            ++n3;
                        }
                        if (this.a.g[n4] >= this.a.a.C) {
                            this.a.O = true;
                        }
                    }
                    ++n4;
                }
                this.a.g[this.a.aA] = this.a.b[this.a.b[this.a.ao][4]];
                ++this.a.aA;
            }
            if (this.a.b[this.a.ao][6] != 0) {
                this.a.P = false;
                this.a.am = 4;
                n4 = 0;
                while (n4 < this.a.av) {
                    if (this.a.a[n4] == this.a.b[this.a.ao][6]) {
                        n3 = 1;
                        while (n3 < this.a.n.length) {
                            if (this.a.b[this.a.ao][7] == this.a.n[n3]) {
                                this.a.a.D = n3;
                            }
                            ++n3;
                        }
                        if (this.a.g[n4] >= this.a.a.D) {
                            this.a.P = true;
                        }
                    }
                    ++n4;
                }
                this.a.g[this.a.aA] = this.a.b[this.a.b[this.a.ao][6]];
                ++this.a.aA;
            }
            if (this.a.b[this.a.ao][8] != 0) {
                this.a.Q = false;
                this.a.am = 5;
                n4 = 0;
                while (n4 < this.a.av) {
                    if (this.a.a[n4] == this.a.b[this.a.ao][8]) {
                        n3 = 1;
                        while (n3 < this.a.n.length) {
                            if (this.a.b[this.a.ao][9] == this.a.n[n3]) {
                                this.a.a.E = n3;
                            }
                            ++n3;
                        }
                        if (this.a.g[n4] >= this.a.a.E) {
                            this.a.Q = true;
                        }
                    }
                    ++n4;
                }
                this.a.g[this.a.aA] = this.a.b[this.a.b[this.a.ao][8]];
                ++this.a.aA;
            }
            if (this.a.b[this.a.ao][10] != 0) {
                this.a.R = false;
                this.a.am = 6;
                n4 = 0;
                while (n4 < this.a.av) {
                    if (this.a.a[n4] == this.a.b[this.a.ao][10]) {
                        n3 = 1;
                        while (n3 < this.a.n.length) {
                            if (this.a.b[this.a.ao][11] == this.a.n[n3]) {
                                this.a.a.F = n3;
                            }
                            ++n3;
                        }
                        if (this.a.g[n4] >= this.a.a.F) {
                            this.a.R = true;
                        }
                    }
                    ++n4;
                }
                this.a.g[this.a.aA] = this.a.b[this.a.b[this.a.ao][10]];
                ++this.a.aA;
            }
            this.a.at = this.a.aA;
            if (this.a.at > this.a.as) {
                this.a.at = this.a.as;
            }
            n4 = 0;
            while (n4 < this.a.at) {
                this.b(n4 + this.a.B);
                StringBuffer stringBuffer2 = new StringBuffer().append(this.a.g[n4 + this.a.B]);
                if (this.a.hasPointerEvents()) {
                    this.a.a.a(((k)this.a).a, stringBuffer2, k.a() >> 1, 52 + this.a.aI * n4 + this.a.aI / 2 + 4, this.a.a.k, 65, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
                } else {
                    this.a.a.a(((k)this.a).a, stringBuffer2, k.a() >> 1, 40 + this.a.aI * n4 + this.a.aI / 2 + 4, this.a.a.k, 65, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
                }
                ++n4;
            }
        }
        if (!this.a.ae) {
            if (this.a.hasPointerEvents()) {
                if (this.a.h) {
                    this.a.a.a(((k)this.a).a, 655419, (k.a() >> 1) - 12, 40);
                } else if (this.a.B > 0) {
                    this.a.a.a(((k)this.a).a, 655417, (k.a() >> 1) - 12, 40);
                } else {
                    this.a.a.a(((k)this.a).a, 655421, (k.a() >> 1) - 12, 40);
                }
                if (this.a.i) {
                    this.a.a.a(((k)this.a).a, 655420, (k.a() >> 1) - 12, n2);
                } else if (this.a.B < this.a.aA - this.a.as) {
                    this.a.a.a(((k)this.a).a, 655418, (k.a() >> 1) - 12, n2);
                } else {
                    this.a.a.a(((k)this.a).a, 655422, (k.a() >> 1) - 12, n2);
                }
            } else {
                if (this.a.h) {
                    this.a.a.a(((k)this.a).a, 655407, (k.a() >> 1) - 8, 32);
                } else if (this.a.B > 0) {
                    this.a.a.a(((k)this.a).a, 655405, (k.a() >> 1) - 8, 32);
                } else {
                    this.a.a.a(((k)this.a).a, 655409, (k.a() >> 1) - 8, 32);
                }
                if (this.a.i) {
                    this.a.a.a(((k)this.a).a, 655408, (k.a() >> 1) - 8, n2);
                } else if (this.a.B < this.a.aA - this.a.as) {
                    this.a.a.a(((k)this.a).a, 655406, (k.a() >> 1) - 8, n2);
                } else {
                    this.a.a.a(((k)this.a).a, 655410, (k.a() >> 1) - 8, n2);
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
    }

    private void b(int n2) {
        if (this.a.ak == 1 && this.a.am == 1) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2 || n2 == 3) {
                this.a.a.k = 39423;
            }
            if (n2 == 4) {
                if (this.a.M) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 2 && this.a.am == 1) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3 || n2 == 4) {
                this.a.a.k = 39423;
            }
            if (n2 == 5) {
                if (this.a.M) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 3 && this.a.am == 1) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4 || n2 == 5) {
                this.a.a.k = 39423;
            }
            if (n2 == 6) {
                if (this.a.M) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 4 && this.a.am == 1) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5 || n2 == 6) {
                this.a.a.k = 39423;
            }
            if (n2 == 7) {
                if (this.a.M) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 5 && this.a.am == 1) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.K ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6 || n2 == 7) {
                this.a.a.k = 39423;
            }
            if (n2 == 8) {
                if (this.a.M) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 6 && this.a.am == 1) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.K ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.L ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7 || n2 == 8) {
                this.a.a.k = 39423;
            }
            if (n2 == 9) {
                if (this.a.M) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 1 && this.a.am == 2) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2 || n2 == 3) {
                this.a.a.k = 39423;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                if (this.a.N) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 2 && this.a.am == 2) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3 || n2 == 4) {
                this.a.a.k = 39423;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                if (this.a.N) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 3 && this.a.am == 2) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4 || n2 == 5) {
                this.a.a.k = 39423;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7) {
                if (this.a.N) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 4 && this.a.am == 2) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5 || n2 == 6) {
                this.a.a.k = 39423;
            }
            if (n2 == 7) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 8) {
                if (this.a.N) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 5 && this.a.am == 2) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.K ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6 || n2 == 7) {
                this.a.a.k = 39423;
            }
            if (n2 == 8) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 9) {
                if (this.a.N) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 6 && this.a.am == 2) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.K ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.L ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7 || n2 == 8) {
                this.a.a.k = 39423;
            }
            if (n2 == 9) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 10) {
                if (this.a.N) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 1 && this.a.am == 3) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2 || n2 == 3) {
                this.a.a.k = 39423;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                if (this.a.O) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 2 && this.a.am == 3) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3 || n2 == 4) {
                this.a.a.k = 39423;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7) {
                if (this.a.O) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 3 && this.a.am == 3) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4 || n2 == 5) {
                this.a.a.k = 39423;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 8) {
                if (this.a.O) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 4 && this.a.am == 3) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5 || n2 == 6) {
                this.a.a.k = 39423;
            }
            if (n2 == 7) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 8) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 9) {
                if (this.a.O) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 5 && this.a.am == 3) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.K ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6 || n2 == 7) {
                this.a.a.k = 39423;
            }
            if (n2 == 8) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 9) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 10) {
                if (this.a.O) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 6 && this.a.am == 3) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.K ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.L ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7 || n2 == 8) {
                this.a.a.k = 39423;
            }
            if (n2 == 9) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 10) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 11) {
                if (this.a.O) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 1 && this.a.am == 4) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2 || n2 == 3) {
                this.a.a.k = 39423;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7) {
                if (this.a.P) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 2 && this.a.am == 4) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3 || n2 == 4) {
                this.a.a.k = 39423;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 8) {
                if (this.a.P) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 3 && this.a.am == 4) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4 || n2 == 5) {
                this.a.a.k = 39423;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 8) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 9) {
                if (this.a.P) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 4 && this.a.am == 4) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5 || n2 == 6) {
                this.a.a.k = 39423;
            }
            if (n2 == 7) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 8) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 9) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 10) {
                if (this.a.P) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 5 && this.a.am == 4) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.K ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6 || n2 == 7) {
                this.a.a.k = 39423;
            }
            if (n2 == 8) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 9) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 10) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 11) {
                if (this.a.P) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 6 && this.a.am == 4) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.K ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.L ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7 || n2 == 8) {
                this.a.a.k = 39423;
            }
            if (n2 == 9) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 10) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 11) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 12) {
                if (this.a.P) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 1 && this.a.am == 5) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2 || n2 == 3) {
                this.a.a.k = 39423;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7) {
                this.a.a.k = this.a.P ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 8) {
                if (this.a.Q) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 2 && this.a.am == 5) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3 || n2 == 4) {
                this.a.a.k = 39423;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 8) {
                this.a.a.k = this.a.P ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 9) {
                if (this.a.Q) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 3 && this.a.am == 5) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4 || n2 == 5) {
                this.a.a.k = 39423;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 8) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 9) {
                this.a.a.k = this.a.P ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 10) {
                if (this.a.Q) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 4 && this.a.am == 5) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5 || n2 == 6) {
                this.a.a.k = 39423;
            }
            if (n2 == 7) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 8) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 9) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 10) {
                this.a.a.k = this.a.P ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 11) {
                if (this.a.Q) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 5 && this.a.am == 5) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.K ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6 || n2 == 7) {
                this.a.a.k = 39423;
            }
            if (n2 == 8) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 9) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 10) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 11) {
                this.a.a.k = this.a.P ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 12) {
                if (this.a.Q) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 6 && this.a.am == 5) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.K ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.L ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7 || n2 == 8) {
                this.a.a.k = 39423;
            }
            if (n2 == 9) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 10) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 11) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 12) {
                this.a.a.k = this.a.P ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 13) {
                if (this.a.Q) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 1 && this.a.am == 6) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2 || n2 == 3) {
                this.a.a.k = 39423;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7) {
                this.a.a.k = this.a.P ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 8) {
                this.a.a.k = this.a.Q ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 9) {
                if (this.a.R) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 2 && this.a.am == 6) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3 || n2 == 4) {
                this.a.a.k = 39423;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 8) {
                this.a.a.k = this.a.P ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 9) {
                this.a.a.k = this.a.Q ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 10) {
                if (this.a.R) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 3 && this.a.am == 6) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4 || n2 == 5) {
                this.a.a.k = 39423;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 8) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 9) {
                this.a.a.k = this.a.P ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 10) {
                this.a.a.k = this.a.Q ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 11) {
                if (this.a.R) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 4 && this.a.am == 6) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5 || n2 == 6) {
                this.a.a.k = 39423;
            }
            if (n2 == 7) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 8) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 9) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 10) {
                this.a.a.k = this.a.P ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 11) {
                this.a.a.k = this.a.Q ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 12) {
                if (this.a.R) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 5 && this.a.am == 6) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.K ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6 || n2 == 7) {
                this.a.a.k = 39423;
            }
            if (n2 == 8) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 9) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 10) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 11) {
                this.a.a.k = this.a.P ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 12) {
                this.a.a.k = this.a.Q ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 13) {
                if (this.a.R) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 6 && this.a.am == 6) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.K ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                this.a.a.k = this.a.L ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 7 || n2 == 8) {
                this.a.a.k = 39423;
            }
            if (n2 == 9) {
                this.a.a.k = this.a.M ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 10) {
                this.a.a.k = this.a.N ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 11) {
                this.a.a.k = this.a.O ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 12) {
                this.a.a.k = this.a.P ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 13) {
                this.a.a.k = this.a.Q ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 14) {
                if (this.a.R) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 1) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                if (this.a.G) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 2) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                if (this.a.H) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 3) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                if (this.a.I) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 4) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                if (this.a.J) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 5) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                if (this.a.K) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
                return;
            }
        } else if (this.a.ak == 6) {
            if (n2 == 0) {
                this.a.a.k = 39423;
            }
            if (n2 == 1) {
                this.a.a.k = this.a.G ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 2) {
                this.a.a.k = this.a.H ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 3) {
                this.a.a.k = this.a.I ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 4) {
                this.a.a.k = this.a.J ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 5) {
                this.a.a.k = this.a.K ? 0xFFFFFF : 0x949494;
            }
            if (n2 == 6) {
                if (this.a.L) {
                    this.a.a.k = 0xFFFFFF;
                    return;
                }
                this.a.a.k = 0x949494;
            }
        }
    }

    public final void a() {
        if (this.a.aw) {
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, k.a(), k.b());
            this.a.a.d = new StringBuffer().append(this.a.b[this.a.ao] + this.a.a.a(76));
            this.a.a.a(((k)this.a).a, this.a.a.d, k.a() >> 1, (k.b() >> 1) + 4, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
            this.a.a.b();
        }
    }

    public final void b() {
        if (this.a.aw) {
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, k.a(), k.b());
            this.a.a.a(((k)this.a).a, this.a.a.a(81), k.a() >> 1, (k.b() >> 1) - 10, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
        }
    }

    public final void c() {
        if (this.a.aw) {
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, k.a(), k.b());
            this.a.a.a(((k)this.a).a, this.a.a.a(82), k.a() >> 1, (k.b() >> 1) - 10, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, k.a() / 3 * 2, k.b(), 0, 0);
        }
    }

    public final void b(StringBuffer stringBuffer) {
        int n2 = this.a.hasPointerEvents() ? 52 + 16 * this.a.as : 40 + 16 * this.a.as;
        if (this.a.aw) {
            int n3;
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, k.a(), k.b());
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
                    ((k)this.a).a.fillRect(0, 52 + n4 * 16, k.a(), 16);
                } else {
                    ((k)this.a).a.fillRect(0, 40 + n4 * 16, k.a(), 16);
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
                this.a.a.a(((k)this.a).a, 655377, k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, k.a() >> 1, 16 + (24 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, 655360, 0, 16);
                n3 = 1;
                while (n3 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n3, 16);
                    ++n3;
                }
                this.a.a.a(((k)this.a).a, 655362, k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, k.a() >> 1, 16 + (16 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
            }
            this.a.at = 0;
            this.a.at = this.a.aw;
            if (this.a.at > this.a.as) {
                this.a.at = this.a.as;
            }
            n3 = 0;
            while (n3 < this.a.at) {
                this.a.a.k = n3 == this.a.w ? 0xFFFFFF : 39423;
                StringBuffer stringBuffer2 = new StringBuffer().append(this.a.a[n3] + ".." + this.a.h[n3]);
                if (this.a.hasPointerEvents()) {
                    this.a.a.a(((k)this.a).a, stringBuffer2, k.a() >> 1, 52 + this.a.aI * (n3 - this.a.au) + this.a.aI / 2 + 4, this.a.a.k, 65, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
                } else {
                    this.a.a.a(((k)this.a).a, stringBuffer2, k.a() >> 1, 40 + this.a.aI * (n3 - this.a.au) + this.a.aI / 2 + 4, this.a.a.k, 65, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
                }
                ++n3;
            }
        }
        if (this.a.hasPointerEvents()) {
            if (this.a.h) {
                this.a.a.a(((k)this.a).a, 655419, (k.a() >> 1) - 12, 40);
            } else if (this.a.au > this.a.as) {
                this.a.a.a(((k)this.a).a, 655417, (k.a() >> 1) - 12, 40);
            } else {
                this.a.a.a(((k)this.a).a, 655421, (k.a() >> 1) - 12, 40);
            }
            if (this.a.i) {
                this.a.a.a(((k)this.a).a, 655420, (k.a() >> 1) - 12, n2);
            } else if (this.a.at > this.a.as && this.a.au != this.a.at - 1) {
                this.a.a.a(((k)this.a).a, 655418, (k.a() >> 1) - 12, n2);
            } else {
                this.a.a.a(((k)this.a).a, 655422, (k.a() >> 1) - 12, n2);
            }
        } else {
            if (this.a.h) {
                this.a.a.a(((k)this.a).a, 655407, (k.a() >> 1) - 8, 32);
            } else if (this.a.au > this.a.as) {
                this.a.a.a(((k)this.a).a, 655405, (k.a() >> 1) - 8, 32);
            } else {
                this.a.a.a(((k)this.a).a, 655409, (k.a() >> 1) - 8, 32);
            }
            if (this.a.i) {
                this.a.a.a(((k)this.a).a, 655408, (k.a() >> 1) - 8, n2);
            } else if (this.a.at > this.a.as && this.a.au != this.a.at - 1) {
                this.a.a.a(((k)this.a).a, 655406, (k.a() >> 1) - 8, n2);
            } else {
                this.a.a.a(((k)this.a).a, 655410, (k.a() >> 1) - 8, n2);
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

    public final void d() {
        ((k)this.a).a.setColor(13158);
        ((k)this.a).a.fillRect((k.a() >> 1) - this.a.a.a(79).length() * this.a.a.l / 2, (k.b() >> 1) - 10, this.a.a.a(79).length() * this.a.a.l, 20);
        this.a.a.a(((k)this.a).a, this.a.a.a(79), k.a() >> 1, (k.b() >> 1) + 4, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
    }

    public final void c(StringBuffer stringBuffer) {
        if (this.a.aw) {
            int n2;
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, k.a(), k.b());
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655375, 0, 16);
                n2 = 1;
                while (n2 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655376, 2 * n2, 16);
                    ++n2;
                }
                this.a.a.a(((k)this.a).a, 655377, k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, k.a() >> 1, 16 + (24 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, 655360, 0, 16);
                n2 = 1;
                while (n2 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n2, 16);
                    ++n2;
                }
                this.a.a.a(((k)this.a).a, 655362, k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, k.a() >> 1, 16 + (16 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
            }
            String string = this.a.a.a(99).toString() + "\\n" + this.a.a.a(100).toString();
            this.a.a.a(((k)this.a).a, new StringBuffer(string), k.a() >> 1, k.b() >> 1, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
            this.a.a.b();
        }
    }

    public final void e() {
        this.a.a.b = new StringBuffer().append("X:" + this.a.Z);
        this.a.a.c = new StringBuffer().append("Y:" + this.a.aa);
        this.a.a.a(((k)this.a).a, this.a.a.b, 10, 20, 0xFFFFFF, 20, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
        this.a.a.a(((k)this.a).a, this.a.a.c, 10, 30, 0xFFFFFF, 20, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
    }

    public final void f() {
        int n2 = 0;
        int n3 = 0;
        ((k)this.a).a.setColor(0);
        int n4 = this.a.ac - 1;
        while (n4 <= this.a.ac + this.a.O + 2) {
            int n5 = this.a.ab - 1;
            while (n5 <= this.a.ab + this.a.N + 2) {
                if (n4 >= 0 && n5 >= 0) {
                    ((k)this.a).a.drawLine(this.a.aM + 0, this.a.aN + this.a.M * n3 - this.a.M + this.a.V, this.a.aM + k.a(), this.a.aN + this.a.M * n3 - this.a.M + this.a.V);
                    ((k)this.a).a.drawLine(this.a.aM + this.a.M * n2 - this.a.M + this.a.U, this.a.aN, this.a.aM + this.a.M * n2 - this.a.M + this.a.U, this.a.aN + k.b());
                }
                ++n2;
                ++n5;
            }
            n2 = 0;
            ++n3;
            ++n4;
        }
    }

    public final void g() {
        int n2 = 0;
        int n3 = 0;
        ((k)this.a).a.setColor(0);
        int n4 = this.a.Z - 1 - this.a.ab + 1 + this.a.N / 2;
        while (n4 <= this.a.Z - 1 - this.a.ab + 2 + this.a.N / 2) {
            int n5 = this.a.aa - 1 - this.a.ac + 1 + this.a.N / 2;
            while (n5 <= this.a.aa - 1 - this.a.ac + 2 + this.a.N / 2) {
                ((k)this.a).a.drawLine(this.a.aM + 0 + (this.a.N / 2 - 1) * this.a.M + this.a.U, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + (this.a.N / 2 + 2) * this.a.M, this.a.aM + k.a() - (this.a.N / 2 - 1) * this.a.M + this.a.U, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + (this.a.N / 2 + 2) * this.a.M);
                ((k)this.a).a.drawLine(this.a.aM + this.a.M * n2 - this.a.M + this.a.U + (this.a.N / 2 + 1) * this.a.M, this.a.aN + this.a.N / 2 * this.a.M + this.a.V, this.a.aM + this.a.M * n2 - this.a.M + this.a.U + (this.a.N / 2 + 1) * this.a.M, this.a.aN + k.b() + 3 - this.a.N / 2 * this.a.M + this.a.V);
                ++n2;
                ++n5;
            }
            n2 = 0;
            ++n3;
            ++n4;
        }
    }

    public final void d(StringBuffer stringBuffer) {
        if (this.a.aw) {
            int n2;
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, k.a(), k.b());
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655375, 0, 16);
                n2 = 1;
                while (n2 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655376, 2 * n2, 16);
                    ++n2;
                }
                this.a.a.a(((k)this.a).a, 655377, k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, k.a() >> 1, 16 + (24 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, 655360, 0, 16);
                n2 = 1;
                while (n2 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n2, 16);
                    ++n2;
                }
                this.a.a.a(((k)this.a).a, 655362, k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, k.a() >> 1, 16 + (16 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
            }
            n2 = 0;
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            while (n6 < 32400) {
                if (this.a.k[n6] == 0) {
                    this.a.a.G = 0xCCCC99;
                } else if (this.a.k[n6] == 1) {
                    this.a.a.G = 0x669933;
                } else if (this.a.k[n6] == 2) {
                    this.a.a.G = 0xCC6666;
                } else if (this.a.k[n6] == 3) {
                    this.a.a.G = 0x666633;
                } else if (this.a.k[n6] == 4) {
                    this.a.a.G = 0x66FFCC;
                } else if (this.a.k[n6] == 5) {
                    this.a.a.G = 0x33FFFF;
                } else if (this.a.k[n6] == 6) {
                    this.a.a.G = 39372;
                } else if (this.a.k[n6] == 7) {
                    this.a.a.G = 0x333333;
                } else if (this.a.k[n6] == 8) {
                    this.a.a.G = 0xCC9933;
                } else if (this.a.k[n6] == 9) {
                    this.a.a.G = 13056;
                } else if (this.a.k[n6] == 10) {
                    this.a.a.G = 0x996633;
                } else if (this.a.k[n6] == 11) {
                    this.a.a.G = 0x663333;
                } else if (this.a.k[n6] == 12) {
                    this.a.a.G = 0x333300;
                } else if (this.a.k[n6] == 13) {
                    this.a.a.G = 13107;
                } else if (this.a.k[n6] == 14) {
                    this.a.a.G = 0x336633;
                } else if (this.a.k[n6] == 15) {
                    this.a.a.G = 0x330033;
                }
                if (n6 == 180 * n3) {
                    n2 = 0;
                    n4 = 0;
                    ++n3;
                    ++n5;
                }
                if (n2 >= 0 + this.a.Q && n2 < 60 + this.a.Q && n3 >= 0 + this.a.R && n3 < 60 + this.a.R) {
                    ((k)this.a).a.setColor(this.a.a.G);
                    ((k)this.a).a.drawLine(k.a() / 2 - 30 + n4, 48 + n5 - this.a.R - this.a.a.e, k.a() / 2 - 30 + n4, 48 + n5 - this.a.R - this.a.a.e);
                    ++n4;
                }
                ++n2;
                ++n6;
            }
            this.a.a.a(((k)this.a).a, 655423, k.a() / 2 - 16 - 1, 140 - this.a.a.f - this.a.a.e);
            this.a.a.a(((k)this.a).a, 655424, k.a() / 2, 140 - this.a.a.f - this.a.a.e);
            this.a.a.a(((k)this.a).a, 655425, k.a() / 2 + 16 + 1, 140 - this.a.a.f - this.a.a.e);
            this.a.a.a(((k)this.a).a, 655426, k.a() / 2 - 16 - 1, 157 - this.a.a.f - this.a.a.e);
            this.a.a.a(((k)this.a).a, 655427, k.a() / 2, 157 - this.a.a.f - this.a.a.e);
            this.a.a.a(((k)this.a).a, 655428, k.a() / 2 + 16 + 1, 157 - this.a.a.f - this.a.a.e);
            this.a.a.a(((k)this.a).a, 655429, k.a() / 2 - 16 - 1, 174 - this.a.a.f - this.a.a.e);
            this.a.a.a(((k)this.a).a, 655430, k.a() / 2, 174 - this.a.a.f - this.a.a.e);
            this.a.a.a(((k)this.a).a, 655431, k.a() / 2 + 16 + 1, 174 - this.a.a.f - this.a.a.e);
            if (this.a.Q == 0 && this.a.R == 0) {
                this.a.a.a(((k)this.a).a, 655432, k.a() / 2 - 16 - 1, 140 - this.a.a.f - this.a.a.e);
            }
            if (this.a.Q == 60 && this.a.R == 0) {
                this.a.a.a(((k)this.a).a, 655432, k.a() / 2, 140 - this.a.a.f - this.a.a.e);
            }
            if (this.a.Q == 120 && this.a.R == 0) {
                this.a.a.a(((k)this.a).a, 655432, k.a() / 2 + 16 + 1, 140 - this.a.a.f - this.a.a.e);
            }
            if (this.a.Q == 0 && this.a.R == 60) {
                this.a.a.a(((k)this.a).a, 655432, k.a() / 2 - 16 - 1, 157 - this.a.a.f - this.a.a.e);
            }
            if (this.a.Q == 60 && this.a.R == 60) {
                this.a.a.a(((k)this.a).a, 655432, k.a() / 2, 157 - this.a.a.f - this.a.a.e);
            }
            if (this.a.Q == 120 && this.a.R == 60) {
                this.a.a.a(((k)this.a).a, 655432, k.a() / 2 + 16 + 1, 157 - this.a.a.f - this.a.a.e);
            }
            if (this.a.Q == 0 && this.a.R == 120) {
                this.a.a.a(((k)this.a).a, 655432, k.a() / 2 - 16 - 1, 174 - this.a.a.f - this.a.a.e);
            }
            if (this.a.Q == 60 && this.a.R == 120) {
                this.a.a.a(((k)this.a).a, 655432, k.a() / 2, 174 - this.a.a.f - this.a.a.e);
            }
            if (this.a.Q == 120 && this.a.R == 120) {
                this.a.a.a(((k)this.a).a, 655432, k.a() / 2 + 16 + 1, 174 - this.a.a.f - this.a.a.e);
            }
        }
        if (this.a.w > 300L) {
            if (this.a.Z >= 0 && this.a.Z < 60 && this.a.aa >= 0 && this.a.aa < 60) {
                this.a.W = k.a() / 2 - 16 - 1;
                this.a.X = 140 - this.a.a.f - this.a.a.e;
                this.a.a.I = 1;
            }
            if (this.a.Z >= 60 && this.a.Z < 120 && this.a.aa >= 0 && this.a.aa < 60) {
                this.a.W = k.a() / 2;
                this.a.X = 140 - this.a.a.f - this.a.a.e;
                this.a.a.I = 2;
            }
            if (this.a.Z >= 120 && this.a.Z < 180 && this.a.aa >= 0 && this.a.aa < 60) {
                this.a.W = k.a() / 2 + 16 + 1;
                this.a.X = 140 - this.a.a.f - this.a.a.e;
                this.a.a.I = 3;
            }
            if (this.a.Z >= 0 && this.a.Z < 60 && this.a.aa >= 60 && this.a.aa < 120) {
                this.a.W = k.a() / 2 - 16 - 1;
                this.a.X = 157 - this.a.a.f - this.a.a.e;
                this.a.a.I = 4;
            }
            if (this.a.Z >= 60 && this.a.Z < 120 && this.a.aa >= 60 && this.a.aa < 120) {
                this.a.W = k.a() / 2;
                this.a.X = 157 - this.a.a.f - this.a.a.e;
                this.a.a.I = 5;
            }
            if (this.a.Z >= 120 && this.a.Z < 180 && this.a.aa >= 60 && this.a.aa < 120) {
                this.a.W = k.a() / 2 + 16 + 1;
                this.a.X = 157 - this.a.a.f - this.a.a.e;
                this.a.a.I = 6;
            }
            if (this.a.Z >= 0 && this.a.Z < 60 && this.a.aa >= 120 && this.a.aa < 180) {
                this.a.W = k.a() / 2 - 16 - 1;
                this.a.X = 174 - this.a.a.f - this.a.a.e;
                this.a.a.I = 7;
            }
            if (this.a.Z >= 60 && this.a.Z < 120 && this.a.aa >= 120 && this.a.aa < 180) {
                this.a.W = k.a() / 2;
                this.a.X = 174 - this.a.a.f - this.a.a.e;
                this.a.a.I = 8;
            }
            if (this.a.Z >= 120 && this.a.Z < 180 && this.a.aa >= 120 && this.a.aa < 180) {
                this.a.W = k.a() / 2 + 16 + 1;
                this.a.X = 174 - this.a.a.f - this.a.a.e;
                this.a.a.I = 9;
            }
            this.a.a.a(((k)this.a).a, 655389, this.a.W, this.a.X);
        } else {
            if (this.a.Z >= 0 && this.a.Z < 60 && this.a.aa >= 0 && this.a.aa < 60) {
                this.a.W = k.a() / 2 - 16 - 1;
                this.a.X = 140 - this.a.a.f - this.a.a.e;
                this.a.Y = 655423;
                this.a.a.I = 1;
            }
            if (this.a.Z >= 60 && this.a.Z < 120 && this.a.aa >= 0 && this.a.aa < 60) {
                this.a.W = k.a() / 2;
                this.a.X = 140 - this.a.a.f - this.a.a.e;
                this.a.Y = 655424;
                this.a.a.I = 2;
            }
            if (this.a.Z >= 120 && this.a.Z < 180 && this.a.aa >= 0 && this.a.aa < 60) {
                this.a.W = k.a() / 2 + 16 + 1;
                this.a.X = 140 - this.a.a.f - this.a.a.e;
                this.a.Y = 655425;
                this.a.a.I = 3;
            }
            if (this.a.Z >= 0 && this.a.Z < 60 && this.a.aa >= 60 && this.a.aa < 120) {
                this.a.W = k.a() / 2 - 16 - 1;
                this.a.X = 157 - this.a.a.f - this.a.a.e;
                this.a.Y = 655426;
                this.a.a.I = 4;
            }
            if (this.a.Z >= 60 && this.a.Z < 120 && this.a.aa >= 60 && this.a.aa < 120) {
                this.a.W = k.a() / 2;
                this.a.X = 157 - this.a.a.f - this.a.a.e;
                this.a.Y = 655427;
                this.a.a.I = 5;
            }
            if (this.a.Z >= 120 && this.a.Z < 180 && this.a.aa >= 60 && this.a.aa < 120) {
                this.a.W = k.a() / 2 + 16 + 1;
                this.a.X = 157 - this.a.a.f - this.a.a.e;
                this.a.Y = 655428;
                this.a.a.I = 6;
            }
            if (this.a.Z >= 0 && this.a.Z < 60 && this.a.aa >= 120 && this.a.aa < 180) {
                this.a.W = k.a() / 2 - 16 - 1;
                this.a.X = 174 - this.a.a.f - this.a.a.e;
                this.a.Y = 655429;
                this.a.a.I = 7;
            }
            if (this.a.Z >= 60 && this.a.Z < 120 && this.a.aa >= 120 && this.a.aa < 180) {
                this.a.W = k.a() / 2;
                this.a.X = 174 - this.a.a.f - this.a.a.e;
                this.a.Y = 655430;
                this.a.a.I = 8;
            }
            if (this.a.Z >= 120 && this.a.Z < 180 && this.a.aa >= 120 && this.a.aa < 180) {
                this.a.W = k.a() / 2 + 16 + 1;
                this.a.X = 174 - this.a.a.f - this.a.a.e;
                this.a.Y = 655431;
                this.a.a.I = 9;
            }
            this.a.a.a(((k)this.a).a, this.a.Y, this.a.W, this.a.X);
        }
        if (this.a.aE) {
            this.a.aO = this.a.a.I;
            this.a.aE = false;
        }
        if (this.a.a.I == this.a.aO) {
            if (this.a.w > 300L) {
                ((k)this.a).a.setColor(0xFFFF00);
                ((k)this.a).a.drawLine(k.a() / 2 - 30 + this.a.Z - this.a.Q, 48 + this.a.aa - this.a.R - this.a.a.e, k.a() / 2 - 30 + this.a.Z - this.a.Q + 1, 48 + this.a.aa - this.a.R - this.a.a.e);
                ((k)this.a).a.drawLine(k.a() / 2 - 30 + this.a.Z - this.a.Q, 48 + this.a.aa - this.a.R + 1 - this.a.a.e, k.a() / 2 - 30 + this.a.Z - this.a.Q + 1, 48 + this.a.aa - this.a.R + 1 - this.a.a.e);
            } else {
                ((k)this.a).a.setColor(0);
                ((k)this.a).a.drawLine(k.a() / 2 - 30 + this.a.Z - this.a.Q, 48 + this.a.aa - this.a.R - this.a.a.e, k.a() / 2 - 30 + this.a.Z - this.a.Q + 1, 48 + this.a.aa - this.a.R - this.a.a.e);
                ((k)this.a).a.drawLine(k.a() / 2 - 30 + this.a.Z - this.a.Q, 48 + this.a.aa - this.a.R + 1 - this.a.a.e, k.a() / 2 - 30 + this.a.Z - this.a.Q + 1, 48 + this.a.aa - this.a.R + 1 - this.a.a.e);
            }
        }
        if (this.a.w > 600L) {
            this.a.w = 0L;
        }
        if (!this.a.hasPointerEvents()) {
            this.a.a.a(((k)this.a).a, 655403, k.a() / 2 - 30 - 8 - 8 + this.a.a.g, 70 - this.a.a.e);
            this.a.a.a(((k)this.a).a, 655404, k.a() / 2 + 30 + 8 - this.a.a.g, 70 - this.a.a.e);
            this.a.a.a(((k)this.a).a, 655409, k.a() / 2 - 8, 32 + this.a.a.h - this.a.a.e);
            this.a.a.a(((k)this.a).a, 655410, k.a() / 2 - 8, 116 - this.a.a.h - this.a.a.e);
            if (this.a.S) {
                if (this.a.Q > 0) {
                    if (this.a.aG) {
                        this.a.a.a(((k)this.a).a, 655401, k.a() / 2 - 30 - 8 - 8 + this.a.a.g, 70 - this.a.a.e);
                    } else {
                        this.a.a.a(((k)this.a).a, 655399, k.a() / 2 - 30 - 8 - 8 + this.a.a.g, 70 - this.a.a.e);
                    }
                }
                if (this.a.Q < 120) {
                    if (this.a.aH) {
                        this.a.a.a(((k)this.a).a, 655402, k.a() / 2 + 30 + 8 - this.a.a.g, 70 - this.a.a.e);
                    } else {
                        this.a.a.a(((k)this.a).a, 655400, k.a() / 2 + 30 + 8 - this.a.a.g, 70 - this.a.a.e);
                    }
                }
                if (this.a.R > 0) {
                    if (this.a.aI) {
                        this.a.a.a(((k)this.a).a, 655407, k.a() / 2 - 8, 32 + this.a.a.h - this.a.a.e);
                    } else {
                        this.a.a.a(((k)this.a).a, 655405, k.a() / 2 - 8, 32 + this.a.a.h - this.a.a.e);
                    }
                }
                if (this.a.R < 120) {
                    if (this.a.aJ) {
                        this.a.a.a(((k)this.a).a, 655408, k.a() / 2 - 8, 116 - this.a.a.h - this.a.a.e);
                    } else {
                        this.a.a.a(((k)this.a).a, 655406, k.a() / 2 - 8, 116 - this.a.a.h - this.a.a.e);
                    }
                }
                if (this.a.o > 1000L) {
                    if (this.a.aG) {
                        this.a.aG = false;
                    }
                    if (this.a.aH) {
                        this.a.aH = false;
                    }
                    if (this.a.aI) {
                        this.a.aI = false;
                    }
                    if (this.a.aJ) {
                        this.a.aJ = false;
                    }
                }
            }
        }
        this.a.a.i();
    }

    public final void e(StringBuffer stringBuffer) {
        if (this.a.aw) {
            int n2;
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, k.a(), k.b());
            if (this.a.hasPointerEvents()) {
                this.a.a.a(((k)this.a).a, 655375, 0, 16);
                n2 = 1;
                while (n2 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655376, 2 * n2, 16);
                    ++n2;
                }
                this.a.a.a(((k)this.a).a, 655377, k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, k.a() >> 1, 16 + (24 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
            } else {
                this.a.a.a(((k)this.a).a, 655360, 0, 16);
                n2 = 1;
                while (n2 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n2, 16);
                    ++n2;
                }
                this.a.a.a(((k)this.a).a, 655362, k.a() - 2, 16);
                this.a.a.a(((k)this.a).a, stringBuffer, k.a() >> 1, 16 + (16 - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
            }
            if (this.a.J >= 0L && this.a.J <= 5L || this.a.J >= 12L && this.a.J <= 17L || this.a.J >= 24L && this.a.J <= 29L || this.a.J >= 36L && this.a.J <= 41L || this.a.J >= 48L && this.a.J <= 53L) {
                this.a.a.a = this.a.a.a(106).toString();
                if (this.a.J >= 0L && this.a.J <= 5L) {
                    this.a.a.H = 6 - (int)this.a.J;
                } else if (this.a.J >= 12L && this.a.J <= 17L) {
                    this.a.a.H = 18 - (int)this.a.J;
                } else if (this.a.J >= 24L && this.a.J <= 29L) {
                    this.a.a.H = 30 - (int)this.a.J;
                } else if (this.a.J >= 36L && this.a.J <= 41L) {
                    this.a.a.H = 42 - (int)this.a.J;
                } else if (this.a.J >= 48L && this.a.J <= 53L) {
                    this.a.a.H = 54 - (int)this.a.J;
                }
            } else if (this.a.J >= 6L && this.a.J <= 11L || this.a.J >= 18L && this.a.J <= 23L || this.a.J >= 30L && this.a.J <= 35L || this.a.J >= 42L && this.a.J <= 47L || this.a.J >= 54L && this.a.J <= 59L) {
                this.a.a.a = this.a.a.a(107).toString();
                if (this.a.J >= 6L && this.a.J <= 11L) {
                    this.a.a.H = 12 - (int)this.a.J;
                } else if (this.a.J >= 18L && this.a.J <= 23L) {
                    this.a.a.H = 24 - (int)this.a.J;
                } else if (this.a.J >= 30L && this.a.J <= 35L) {
                    this.a.a.H = 36 - (int)this.a.J;
                } else if (this.a.J >= 42L && this.a.J <= 47L) {
                    this.a.a.H = 48 - (int)this.a.J;
                } else if (this.a.J >= 54L && this.a.J <= 59L) {
                    this.a.a.H = 60 - (int)this.a.J;
                }
            }
            String string = this.a.a.a(101).toString() + " " + this.a.a.a + "\\n" + this.a.a.a(102).toString() + " " + this.a.a.H + " " + this.a.a.a(103).toString() + "\\n" + this.a.a.a(104).toString() + " " + this.a.aG + "\\n" + this.a.a.a(105).toString() + " " + this.a.a;
            this.a.a.a(((k)this.a).a, new StringBuffer(string), k.a() >> 1, (k.b() >> 1) - 20, 39423, 65, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
        }
        this.a.a.i();
    }

    public final void h() {
        if (this.a.l) {
            this.a.a.a = this.a.c;
            if (this.a.v == null) {
                this.a.a.j = 21;
                this.a.a.i = k.b() - 37 - 20;
            } else if (this.a.b == null) {
                this.a.a.j = 52;
                this.a.a.i = k.b() - 68 - 20;
            } else {
                this.a.a.j = 72;
                this.a.a.i = k.b() - 88 - 20;
            }
            this.a(this.a.a.a, k.a() >> 1, this.a.a.j + 16, k.a(), this.a.a.i);
            this.a.l = false;
        }
        if (this.a.n) {
            int n2;
            this.a.n = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, k.a(), k.b());
            if (this.a.v != null) {
                n2 = 0;
                while (n2 < this.a.v.length) {
                    this.a.a.a(((k)this.a).a, this.a.u[1], k.a() / 2 - ((this.a.v.length - 1) * 10 + (this.a.v.length - 1) * 3) + n2 * 26, 32);
                    if (this.a.v[n2] == this.a.aH) {
                        this.a.a.a(((k)this.a).a, this.a.u[0], k.a() / 2 - ((this.a.v.length - 1) * 10 + (this.a.v.length - 1) * 3) + n2 * 26, 32);
                    }
                    this.a.a.a(((k)this.a).a, this.a.u[this.a.v[n2]], k.a() / 2 - ((this.a.v.length - 1) * 10 + (this.a.v.length - 1) * 3) + n2 * 26, 32);
                    ++n2;
                }
            }
            if (this.a.b != null) {
                this.a.a.a(((k)this.a).a, 655360, 0, 52);
                n2 = 1;
                while (n2 < this.a.aV + 1) {
                    this.a.a.a(((k)this.a).a, 655361, 2 * n2, 52);
                    ++n2;
                }
                this.a.a.a(((k)this.a).a, 655362, k.a() - 2, 52);
                this.a.a.a(((k)this.a).a, this.a.b, k.a() >> 1, 52 + (16 - this.a.aJ) / 2, 0xFFFFFF, 17, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
                this.a.a.a(((k)this.a).a, 655399, 8, 52);
                this.a.a.a(((k)this.a).a, 655400, k.a() - 16, 52);
            }
            ac.c(Integer.parseInt(this.a.b.elementAt(this.a.h).toString()));
            if (ac.b() != -1) {
                this.a.a.a(((k)this.a).a, this.a.a.a, k.a() >> 1, this.a.a.j + 16, 0xFFFFFF, 1041, 0, 1, 8, this.a.aJ, k.a(), this.a.a.i, 0, 0);
            }
            this.a.a.c();
        }
        if (this.a.m) {
            this.a((k.a() >> 1) - 8, this.a.a.j, k.b() - 16);
        }
    }

    public final void i() {
        if (this.a.aw) {
            this.a.aw = false;
            ((k)this.a).a.setColor(13158);
            ((k)this.a).a.fillRect(0, 0, k.a(), k.b());
            StringBuffer stringBuffer = new StringBuffer().append((Object)this.a.a.a(80));
            this.a.a.a(((k)this.a).a, stringBuffer, k.a() >> 1, this.a.a.a + 20, 0xFFFFFF, 65, 0, 1, 8, this.a.aJ, k.a(), 2 * k.b() / 3, 0, 0);
        }
    }

    public final void a(int n2) {
        ((k)this.a).a.setColor(0xFFFFFF);
        ((k)this.a).a.fillRect(0, 0, k.a(), k.b());
        int n3 = 1;
        while (n3 < this.a.aK + 1) {
            this.a.a.a(((k)this.a).a, this.a.a.c, k.a() / 6 + 2 * n3, k.b() / 6 + (this.a.aP + this.a.aL - this.a.aQ) - this.a.aR);
            ++n3;
        }
        if (n2 == 0) {
            this.a.a.a(((k)this.a).a, this.a.a.a(50), k.a() >> 1, k.b() / 6 + (this.a.aP + this.a.aL - this.a.aQ) - this.a.aR + (this.a.aP - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
            return;
        }
        this.a.a.a(((k)this.a).a, this.a.a.a(51), k.a() >> 1, k.b() / 6 + (this.a.aP + this.a.aL - this.a.aQ) - this.a.aR + (this.a.aP - this.a.aJ) / 2, 0xFFFFFF, 273, 0, 1, 8, this.a.aJ, k.a(), k.b(), 0, 0);
    }

    public final void a(StringBuffer stringBuffer, int n2, int n3, int n4, int n5) {
        this.a.b.removeAllElements();
        ac.c(0);
        this.a.l = true;
        this.a.h = 0;
        this.a.p = 0L;
        this.a.b.addElement(new Integer(0));
        while (this.a.a.a) {
            this.a.a.a(((k)this.a).a, stringBuffer, n2, n3, 0xFFFFFF, 1089, 0, 1, 8, this.a.aJ, n4, n5, 0, 0);
            this.a.b.addElement(new Integer(ac.b()));
            if (ac.b() != 0) continue;
            this.a.a.a = false;
        }
        this.a.b.removeElementAt(this.a.b.size() - 1);
        this.a.n = true;
        this.a.m = true;
        this.a.a.a = true;
    }

    public final void a(int n2, int n3, int n4) {
        if (this.a.hasPointerEvents()) {
            if (this.a.b.size() > 1) {
                if (this.a.h == 0) {
                    this.a.a.a(((k)this.a).a, 655421, n2, n3);
                } else {
                    this.a.a.a(((k)this.a).a, 655417, n2, n3);
                }
                if (this.a.h == this.a.b.size() - 1) {
                    this.a.a.a(((k)this.a).a, 655422, n2, n4);
                } else {
                    this.a.a.a(((k)this.a).a, 655418, n2, n4);
                }
                this.a.m = false;
            }
            switch (this.a.i) {
                case 1: {
                    this.a.p += this.a.f;
                    if (this.a.p < 500L) {
                        this.a.a.a(((k)this.a).a, 655419, n2, n3);
                        this.a.m = true;
                        break;
                    }
                    this.a.p = 0L;
                    this.a.i = 0;
                    if (this.a.h == 0) {
                        this.a.a.a(((k)this.a).a, 655421, n2, n3);
                    }
                    this.a.m = false;
                    break;
                }
                case 2: {
                    this.a.p += this.a.f;
                    if (this.a.p < 500L) {
                        this.a.a.a(((k)this.a).a, 655420, n2, n4);
                        this.a.m = true;
                        break;
                    }
                    this.a.p = 0L;
                    this.a.i = 0;
                    if (this.a.h == this.a.b.size() - 1) {
                        this.a.a.a(((k)this.a).a, 655422, n2, n4);
                    }
                    this.a.m = false;
                }
            }
            return;
        }
        if (this.a.b.size() > 1) {
            if (this.a.h == 0) {
                this.a.a.a(((k)this.a).a, 655409, n2, n3);
            } else {
                this.a.a.a(((k)this.a).a, 655405, n2, n3);
            }
            if (this.a.h == this.a.b.size() - 1) {
                this.a.a.a(((k)this.a).a, 655410, n2, n4);
            } else {
                this.a.a.a(((k)this.a).a, 655406, n2, n4);
            }
            this.a.m = false;
        }
        switch (this.a.i) {
            case 1: {
                this.a.p += this.a.f;
                if (this.a.p < 500L) {
                    this.a.a.a(((k)this.a).a, 655407, n2, n3);
                    this.a.m = true;
                    return;
                }
                this.a.p = 0L;
                this.a.i = 0;
                if (this.a.h == 0) {
                    this.a.a.a(((k)this.a).a, 655409, n2, n3);
                }
                this.a.m = false;
                return;
            }
            case 2: {
                this.a.p += this.a.f;
                if (this.a.p < 500L) {
                    this.a.a.a(((k)this.a).a, 655408, n2, n4);
                    this.a.m = true;
                    return;
                }
                this.a.p = 0L;
                this.a.i = 0;
                if (this.a.h == this.a.b.size() - 1) {
                    this.a.a.a(((k)this.a).a, 655410, n2, n4);
                }
                this.a.m = false;
            }
        }
    }
}

