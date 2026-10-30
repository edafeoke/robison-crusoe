/*
 * Decompiled with CFR 0.152.
 */
package data;

import data.k;
import data.p;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class j {
    private p a;
    private int f = 0;
    public int a;
    public int b;
    public int c;
    public int d;
    public boolean a = false;
    public int e = -1;
    public boolean b = false;
    private boolean c = false;
    private boolean d = false;
    private int g;

    public j(p p2) {
        this.a = p2;
    }

    private void a() {
        this.a = this.a.Z < this.a.N / 2 ? (this.a.N / 2 + 1) * this.a.M - this.a.M / 2 - (this.a.N / 2 - this.a.Z) * this.a.M : (this.a.Z > 179 - this.a.N / 2 ? (this.a.N / 2 + 1) * this.a.M - this.a.M / 2 + (this.a.Z - (179 - this.a.N / 2)) * this.a.M : (this.a.N / 2 + 1) * this.a.M - this.a.M / 2);
        if (this.a.aa < this.a.O / 2) {
            this.b = (this.a.O / 2 + 1) * this.a.M + this.a.M / 2 - (this.a.O / 2 - this.a.aa) * this.a.M;
            return;
        }
        if (this.a.aa > 179 - this.a.O / 2) {
            this.b = (this.a.O / 2 + 1) * this.a.M + this.a.M / 2 + (this.a.aa - (179 - this.a.O / 2)) * this.a.M;
            return;
        }
        this.b = (this.a.O / 2 + 1) * this.a.M + this.a.M / 2;
    }

    public final void a(int n2, int n3) {
        if (this.a.k == 2 || this.a.k == 0 && this.a.a.a == -2 && !this.a.ax) {
            this.c = n2;
            this.d = n3;
            this.b();
        }
    }

    public final void b(int n2, int n3) {
        this.c = -1;
        this.d = -1;
        this.a = false;
        this.c = false;
        if (this.d && this.a.m == 7 && this.a.q == 0) {
            this.e = 5;
            this.d = false;
        }
    }

    public final void c(int n2, int n3) {
        if ((this.a.k == 2 || this.a.k == 0 && this.a.a.a == -2 && !this.a.ax) && this.a && this.a.m == 7 && this.a.q == 0) {
            this.c = true;
            this.d = true;
            this.c = n2;
            this.d = n3;
            this.b();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void b() {
        if (!this.c && this.c > this.a.bf && this.c < this.a.bg && this.d > this.a.bh && this.d < this.a.bi) {
            this.e = 1;
            return;
        }
        if (!this.c && this.c > this.a.bj && this.c < this.a.bk && this.d > this.a.bl && this.d < this.a.bm) {
            this.e = 3;
            return;
        }
        switch (this.a.l) {
            case 0: {
                return;
            }
            case 1: {
                int n2 = 2;
                if (this.c <= k.a() / 6) return;
                if (this.c >= k.a() / 6 + 2 * this.a.aK) return;
                if (this.a.b) {
                    n2 = 1;
                }
                int n3 = n2;
                while (n3 < this.a.b + 1) {
                    if (this.d > k.b() / this.a.b + 1 + (this.a.aP + this.a.aL - this.a.aQ) * n3 - this.a.aR && this.d < k.b() / this.a.b + 1 + (this.a.aP + this.a.aL - this.a.aQ) * n3 - this.a.aR + this.a.aP) {
                        this.a.r = n3 - 1;
                        this.e = 1;
                        return;
                    }
                    ++n3;
                }
                return;
            }
            case 2: {
                if (this.c <= k.a() / 3 || this.c >= k.a() / 3 * 2) return;
                if (this.d < k.b() >> 1) {
                    this.a.A = 0;
                    this.e = 5;
                    return;
                }
                if (this.d <= k.b() >> 1) return;
                this.a.A = 1;
                this.e = 5;
                return;
            }
            case 3: {
                boolean bl = false;
                boolean bl2 = false;
                int n4 = 38;
                int n5 = 0;
                if (this.c >= (k.a() >> 1) - 24 && this.c <= (k.a() >> 1) + 24) {
                    if (this.d >= 21 && this.d <= 45) {
                        this.e = 2;
                    } else if (this.a.g < this.a.a.size() - 1 && this.d >= k.b() - 20 && this.d <= k.b() - 20 + 24) {
                        this.e = 8;
                    }
                }
                if (this.c <= 20 || this.c >= k.a() - 40) return;
                int n6 = (Integer)this.a.a.elementAt(this.a.f);
                while (n6 < (Integer)this.a.a.elementAt(this.a.g)) {
                    n5 = this.a.a.a(null, this.a.a.a(33 + n6), 20, n4 + 5, 0, 20, 0, 1, 8, this.a.aJ, k.a() - 40, k.b(), 0, 0);
                    this.f = (n5 - n4) / this.a.aJ;
                    if (this.d >= n5 - this.a.aJ * this.f - 2 + 4 && this.d <= n5 - 2 + 4) {
                        this.a.d = n6;
                        this.e = 1;
                        return;
                    }
                    n4 = n5;
                    ++n6;
                }
                return;
            }
            case 10: {
                if (this.c < (k.a() >> 1) - 16 - 5 || this.c > (k.a() >> 1) + 24 + 5) return;
                if (this.d > this.a.a.j && this.d < this.a.a.j + 24) {
                    this.e = 2;
                    return;
                }
                if (this.d < k.b() - 20 || this.d > k.b() - 20 + 24) return;
                this.e = 8;
                return;
            }
            case 4: {
                if (this.c < (k.a() >> 1) - 24 || this.c > (k.a() >> 1) + 24) return;
                if (this.d > 21 && this.d < 45) {
                    this.e = 2;
                    return;
                }
                if (this.d < k.b() - 20 || this.d > k.b() - 20 + 24) return;
                this.e = 8;
                return;
            }
            case 5: {
                return;
            }
            case 6: {
                switch (this.a.m) {
                    case 2: {
                        int n7 = this.a.C * 20 + (this.a.C - 1) * 6;
                        int n8 = 0;
                        while (n8 < this.a.C) {
                            if (this.c > (k.a() >> 1) - (n7 >> 1) + 26 * n8 + 10 - 26 * (this.a.x - this.a.aq / 2) - 10 && this.c < (k.a() >> 1) - (n7 >> 1) + 26 * n8 + 10 - 26 * (this.a.x - this.a.aq / 2) + 10 && this.d > this.a.aU - 20) {
                                this.e = 5;
                                this.a.x = n8;
                                this.a.C = 0;
                                return;
                            }
                            ++n8;
                        }
                        this.e = 3;
                        return;
                    }
                    case 1: {
                        if (!this.a.v && this.d > 16 && this.d < 40) {
                            if (this.c < 20) {
                                this.e = 4;
                            } else if (this.c > k.a() - 8 - 12) {
                                this.e = 6;
                            }
                        }
                        switch (this.a.n) {
                            case 0: {
                                this.g = 52 + this.a.aI * this.a.as;
                                if (this.c <= k.a() / 3 || this.c >= k.a() / 3 * 2) return;
                                if (this.c >= (k.a() >> 1) - 16 && this.c <= (k.a() >> 1) + 16 && this.d > 40 && this.d < 52) {
                                    this.e = 2;
                                    return;
                                }
                                if (this.c >= (k.a() >> 1) - 16 && this.c <= (k.a() >> 1) + 24 && this.d > this.g && this.d < this.g + 12) {
                                    this.e = 8;
                                    return;
                                }
                                int n9 = this.a.au;
                                while (n9 < this.a.at + this.a.au) {
                                    if (this.d >= 52 + this.a.aI * (n9 - this.a.au) + 4 && this.d < 52 + this.a.aI * (n9 - this.a.au) + 4 + this.a.aJ) {
                                        this.e = 1;
                                        this.a.z = n9;
                                        this.a.aw = true;
                                        return;
                                    }
                                    ++n9;
                                }
                                return;
                            }
                            case 1: {
                                int n10 = this.a.C * 20 + (this.a.C - 1) * 6;
                                int n11 = 0;
                                while (n11 < this.a.C) {
                                    if (this.c > (k.a() >> 1) - (n10 >> 1) + 26 * n11 + 10 - 26 * (this.a.x - this.a.aq / 2) - 10 && this.c < (k.a() >> 1) - (n10 >> 1) + 26 * n11 + 10 - 26 * (this.a.x - this.a.aq / 2) + 10 && this.d > this.a.aU - 10 - 10 && this.d < this.a.aU - 10 + 10) {
                                        this.e = 5;
                                        this.a.x = n11;
                                        this.a.C = 0;
                                        return;
                                    }
                                    ++n11;
                                }
                                this.e = 3;
                                return;
                            }
                            case 2: {
                                return;
                            }
                            case 3: {
                                if (this.a.v) {
                                    if (this.d > 32 + (k.b() - 32 - 20 >> 1) - 32 - 3 && this.d < 32 + (k.b() - 32 - 20 >> 1) - 32 - 3 + 16) {
                                        this.a.u = 0;
                                        if (this.c > (k.a() >> 1) - 19 - 21 - 15 && this.c < (k.a() >> 1) - 19 - 21 + 8) {
                                            this.e = 4;
                                            return;
                                        }
                                        if (this.c <= (k.a() >> 1) + 19 + 14 || this.c >= (k.a() >> 1) + 19 + 14 + 8 + 15) return;
                                        this.e = 6;
                                        return;
                                    }
                                    if (this.d > 32 + (k.b() - 32 - 20 >> 1) - 16 - 1 && this.d < 32 + (k.b() - 32 - 20 >> 1) - 16 - 1 + 16) {
                                        this.a.u = 1;
                                        if (this.c > (k.a() >> 1) - 19 - 21 - 15 && this.c < (k.a() >> 1) - 19 - 21 + 8) {
                                            this.e = 4;
                                            return;
                                        }
                                        if (this.c <= (k.a() >> 1) + 19 + 14 || this.c >= (k.a() >> 1) + 19 + 14 + 8 + 15) return;
                                        this.e = 6;
                                        return;
                                    }
                                    if (this.d > 32 + (k.b() - 32 - 20 >> 1) + 1 && this.d < 32 + (k.b() - 32 - 20 >> 1) + 1 + 16) {
                                        this.a.u = 2;
                                        if (this.c > (k.a() >> 1) - 19 - 21 - 15 && this.c < (k.a() >> 1) - 19 - 21 + 8) {
                                            this.e = 4;
                                            return;
                                        }
                                        if (this.c <= (k.a() >> 1) + 19 + 14 || this.c >= (k.a() >> 1) + 19 + 14 + 8 + 15) return;
                                        this.e = 6;
                                        return;
                                    }
                                    if (this.d <= 32 + (k.b() - 32 - 20 >> 1) + 16 + 3 || this.d >= 32 + (k.b() - 32 - 20 >> 1) + 16 + 3 + 16) return;
                                    this.a.u = 3;
                                    if (this.c > (k.a() >> 1) - 19 - 21 - 15 && this.c < (k.a() >> 1) - 19 - 21 + 8) {
                                        this.e = 4;
                                        return;
                                    }
                                    if (this.c <= (k.a() >> 1) + 19 + 14 || this.c >= (k.a() >> 1) + 19 + 14 + 12 + 15) return;
                                    this.e = 6;
                                    return;
                                }
                                if (this.d <= 32 || this.d >= k.b() - 20) return;
                                this.e = 1;
                                return;
                            }
                            case 4: {
                                this.g = 52 + this.a.aI * this.a.as;
                                if (this.c <= k.a() / 3 || this.c >= k.a() / 3 * 2) return;
                                if (this.c >= (k.a() >> 1) - 24 && this.c <= (k.a() >> 1) + 24 && this.d > 40 && this.d < 52) {
                                    this.e = 2;
                                    return;
                                }
                                if (this.c >= (k.a() >> 1) - 24 && this.c <= (k.a() >> 1) + 24 && this.d > this.g && this.d < this.g + 12) {
                                    this.e = 8;
                                    return;
                                }
                                int n12 = this.a.au;
                                while (n12 < this.a.at + this.a.au) {
                                    if (this.d >= 52 + this.a.aI * (n12 - this.a.au) + 4 && this.d < 52 + this.a.aI * (n12 - this.a.au) + 4 + this.a.aJ) {
                                        this.e = 1;
                                        this.a.v = n12;
                                        this.a.aw = true;
                                        return;
                                    }
                                    ++n12;
                                }
                                return;
                            }
                            case 5: {
                                if (this.a.S) {
                                    if (this.d > 140 - this.a.a.f - this.a.a.e - 8 && this.d < 140 - this.a.a.f - this.a.a.e + 8) {
                                        this.a.R = 0;
                                        if (this.c > k.a() / 2 - 16 - 1 - 8 && this.c < k.a() / 2 - 16 - 1 + 8) {
                                            this.a.Q = 0;
                                            this.a.aO = 1;
                                        } else if (this.c > k.a() / 2 - 8 && this.c < k.a() / 2 + 8) {
                                            this.a.Q = 60;
                                            this.a.aO = 2;
                                        } else if (this.c > k.a() / 2 + 16 + 1 - 8 && this.c < k.a() / 2 + 16 + 1 + 8) {
                                            this.a.Q = 120;
                                            this.a.aO = 3;
                                        }
                                    } else if (this.d > 157 - this.a.a.f - this.a.a.e - 8 && this.d < 157 - this.a.a.f - this.a.a.e + 8) {
                                        this.a.R = 60;
                                        if (this.c > k.a() / 2 - 16 - 1 - 8 && this.c < k.a() / 2 - 16 - 1 + 8) {
                                            this.a.Q = 0;
                                            this.a.aO = 4;
                                        } else if (this.c > k.a() / 2 - 8 && this.c < k.a() / 2 + 8) {
                                            this.a.Q = 60;
                                            this.a.aO = 5;
                                        } else if (this.c > k.a() / 2 + 16 + 1 - 8 && this.c < k.a() / 2 + 16 + 1 + 8) {
                                            this.a.Q = 120;
                                            this.a.aO = 6;
                                        }
                                    } else if (this.d > 174 - this.a.a.f - this.a.a.e - 8 && this.d < 174 - this.a.a.f - this.a.a.e + 8) {
                                        this.a.R = 120;
                                        if (this.c > k.a() / 2 - 16 - 1 - 8 && this.c < k.a() / 2 - 16 - 1 + 8) {
                                            this.a.Q = 0;
                                            this.a.aO = 7;
                                        } else if (this.c > k.a() / 2 - 8 && this.c < k.a() / 2 + 8) {
                                            this.a.Q = 60;
                                            this.a.aO = 8;
                                        } else if (this.c > k.a() / 2 + 16 + 1 - 8 && this.c < k.a() / 2 + 16 + 1 + 8) {
                                            this.a.Q = 120;
                                            this.a.aO = 9;
                                        }
                                    }
                                } else if (this.d > 32 && this.d < k.b() - 20) {
                                    this.e = 1;
                                }
                                this.a.aw = true;
                            }
                        }
                        return;
                    }
                    case 0: {
                        this.a();
                        if (this.c < this.a + 3 * this.a.M / 2 && this.c > this.a - 3 * this.a.M / 2 && this.d < this.b + 3 * this.a.M / 2 && this.d > this.b - 3 * this.a.M / 2) {
                            this.e = 1;
                            return;
                        }
                        if (this.c > this.a && this.d < this.b + Math.abs(this.c - this.a) && this.d > this.b - Math.abs(this.c - this.a)) {
                            this.e = 6;
                            this.a = true;
                            return;
                        }
                        if (this.c < this.a && this.d < this.b + Math.abs(this.c - this.a) && this.d > this.b - Math.abs(this.c - this.a)) {
                            this.e = 4;
                            this.a = true;
                            return;
                        }
                        if (this.d > this.b && this.c < this.a + Math.abs(this.d - this.b) && this.c > this.a - Math.abs(this.d - this.b)) {
                            this.e = 8;
                            this.a = true;
                            return;
                        }
                        if (this.d >= this.b || this.c >= this.a + Math.abs(this.d - this.b) || this.c <= this.a - Math.abs(this.d - this.b)) return;
                        this.e = 2;
                        this.a = true;
                        return;
                    }
                    case 3: {
                        if (this.c >= (k.a() >> 1) - 24 && this.c <= (k.a() >> 1) + 24) {
                            if (this.d > 86 && this.d < 110) {
                                this.e = 2;
                                return;
                            }
                            if (this.d > k.b() - 20 - 9 && this.d < k.b() - 20 - 9 + 24) {
                                this.e = 8;
                                return;
                            }
                        }
                        this.e = 3;
                        return;
                    }
                    case 4: {
                        if (this.c <= k.a() / 3 || this.c >= k.a() / 3 * 2) return;
                        if (this.d < k.b() >> 1) {
                            this.a.t = 0;
                            this.e = 1;
                            return;
                        }
                        if (this.d <= k.b() >> 1) return;
                        this.a.t = 1;
                        this.e = 1;
                        return;
                    }
                    case 5: {
                        this.g = 52 + this.a.aI * this.a.as;
                        if (this.c <= k.a() / 3 || this.c >= k.a() / 3 * 2) return;
                        if (this.c >= (k.a() >> 1) - 24 && this.c <= (k.a() >> 1) + 24 && this.d > 40 && this.d < 52) {
                            this.e = 2;
                            return;
                        }
                        if (this.c >= (k.a() >> 1) - 24 && this.c <= (k.a() >> 1) + 24 && this.d > this.g && this.d < this.g + 12) {
                            this.e = 8;
                            return;
                        }
                        int n13 = this.a.au;
                        while (n13 < this.a.at + this.a.au) {
                            if (this.d >= 52 + this.a.aI * (n13 - this.a.au) + 4 && this.d < 52 + this.a.aI * (n13 - this.a.au) + 4 + this.a.aJ) {
                                this.e = 1;
                                this.a.y = n13;
                                this.a.aw = true;
                                return;
                            }
                            ++n13;
                        }
                        return;
                    }
                    case 6: {
                        this.g = 52 + this.a.aI * this.a.as;
                        if (this.c <= k.a() / 3 || this.c >= k.a() / 3 * 2) return;
                        if (this.c >= (k.a() >> 1) - 24 && this.c <= (k.a() >> 1) + 24 && this.d > 40 && this.d < 52) {
                            this.e = 2;
                            return;
                        }
                        if (this.c >= (k.a() >> 1) - 24 && this.c <= (k.a() >> 1) + 24 && this.d > this.g && this.d < this.g + 12) {
                            this.e = 8;
                            return;
                        }
                        this.e = 5;
                        return;
                    }
                    case 7: {
                        switch (this.a.q) {
                            case 0: {
                                if (this.c > (k.a() >> 1) + this.a.a.f && this.d < (k.b() >> 1) + this.a.a.g + Math.abs(this.c - ((k.a() >> 1) + this.a.a.f)) && this.d > (k.b() >> 1) + this.a.a.g - Math.abs(this.c - ((k.a() >> 1) + this.a.a.f))) {
                                    this.e = 6;
                                    this.a = true;
                                    return;
                                }
                                if (this.c < (k.a() >> 1) + this.a.a.f && this.d < (k.b() >> 1) + this.a.a.g + Math.abs(this.c - ((k.a() >> 1) + this.a.a.f)) && this.d > (k.b() >> 1) + this.a.a.g - Math.abs(this.c - ((k.a() >> 1) + this.a.a.f))) {
                                    this.e = 4;
                                    this.a = true;
                                    return;
                                }
                                if (this.d > (k.b() >> 1) + this.a.a.g && this.c < (k.a() >> 1) + this.a.a.f + Math.abs(this.d - ((k.b() >> 1) + this.a.a.g)) && this.c > (k.a() >> 1) + this.a.a.f - Math.abs(this.d - ((k.b() >> 1) + this.a.a.g))) {
                                    this.e = 8;
                                    this.a = true;
                                    return;
                                }
                                if (this.d >= (k.b() >> 1) + this.a.a.g || this.c >= (k.a() >> 1) + this.a.a.f + Math.abs(this.d - ((k.b() >> 1) + this.a.a.g)) || this.c <= (k.a() >> 1) + this.a.a.f - Math.abs(this.d - ((k.b() >> 1) + this.a.a.g))) return;
                                this.e = 2;
                                this.a = true;
                                return;
                            }
                            case 1: {
                                if (this.c <= 0) return;
                                this.e = 5;
                                return;
                            }
                            case 2: {
                                if (this.c >= k.a() / 2 - 12 && this.c <= k.a() / 2 + 12) {
                                    if (this.d >= k.b() - 2 - 12 - 2 - 24 - 36 && this.d <= k.b() - 2 - 12 - 2 - 24 - 12 + 24) {
                                        this.e = 2;
                                        this.a = true;
                                    } else if (this.d >= k.b() - 2 - 24) {
                                        this.e = 8;
                                        this.a = true;
                                    }
                                } else if (this.d >= k.b() - 2 - 12 - 2 - 48) {
                                    if (this.c >= k.a() / 2 - 72 - 2 && this.c <= k.a() / 2 - 24 - 2 + 12) {
                                        this.e = 4;
                                        this.a = true;
                                    } else if (this.c >= k.a() / 2 + 12 + 2 && this.c <= k.a() / 2 + 12 + 2 + 36) {
                                        this.e = 6;
                                        this.a = true;
                                    }
                                }
                                if (this.c < 0 || this.c > 27 || this.d < k.b() - 32 - 48 || this.d > k.b() - 32 + 32) return;
                                this.e = 5;
                                return;
                            }
                            case 3: {
                                if (this.c >= k.a() / 2 - 12 && this.c <= k.a() / 2 + 12) {
                                    if (this.d >= k.b() - 2 - 12 - 2 - 24 - 36 && this.d <= k.b() - 2 - 12 - 2 - 24 - 12 + 24) {
                                        this.e = 2;
                                        this.a = true;
                                    } else if (this.d >= k.b() - 2 - 24) {
                                        this.e = 8;
                                        this.a = true;
                                    }
                                } else if (this.d >= k.b() - 2 - 12 - 2 - 48) {
                                    if (this.c >= k.a() / 2 - 72 - 2 && this.c <= k.a() / 2 - 24 - 2 + 12) {
                                        this.e = 4;
                                        this.a = true;
                                    } else if (this.c >= k.a() / 2 + 12 + 2 && this.c <= k.a() / 2 + 12 + 2 + 36) {
                                        this.e = 6;
                                        this.a = true;
                                    }
                                }
                                if (this.c < 0 || this.c > 27 || this.d < k.b() - 32 - 48 || this.d > k.b() - 32 + 32) return;
                                this.e = 5;
                            }
                        }
                        return;
                    }
                    case 8: {
                        switch (this.a.p) {
                            case 0: {
                                this.g = 52 + this.a.aI * this.a.as;
                                if (this.c <= k.a() / 3 || this.c >= k.a() / 3 * 2) break;
                                if (this.c >= (k.a() >> 1) - 24 && this.c <= (k.a() >> 1) + 24 && this.d > 40 && this.d < 52) {
                                    this.e = 2;
                                    break;
                                }
                                if (this.c >= (k.a() >> 1) - 24 && this.c <= (k.a() >> 1) + 24 && this.d > this.g - 12 && this.d < this.g) {
                                    this.e = 8;
                                    break;
                                }
                                int n14 = this.a.au;
                                while (n14 < this.a.at + this.a.au) {
                                    if (this.d >= 52 + this.a.aI * (n14 - this.a.au) + 4 && this.d < 52 + this.a.aI * (n14 - this.a.au) + 4 + this.a.aJ) {
                                        this.e = 1;
                                        this.a.w = n14;
                                        this.a.aw = true;
                                        return;
                                    }
                                    ++n14;
                                }
                                break;
                            }
                            case 1: {
                                int n15 = this.a.C * 20 + (this.a.C - 1) * 6;
                                int n16 = 0;
                                while (n16 < this.a.C) {
                                    if (this.c > (k.a() >> 1) - (n15 >> 1) + 26 * n16 + 10 - 26 * (this.a.x - this.a.aq / 2) - 10 && this.c < (k.a() >> 1) - (n15 >> 1) + 26 * n16 + 10 - 26 * (this.a.x - this.a.aq / 2) + 10 && this.d > this.a.aU - 10 - 10 && this.d < this.a.aU - 10 + 10) {
                                        this.e = 5;
                                        this.a.x = n16;
                                        return;
                                    }
                                    ++n16;
                                }
                                this.e = 3;
                            }
                        }
                        this.a.o = true;
                        return;
                    }
                    case 9: {
                        if (this.c >= (k.a() >> 1) - 24 && this.c <= (k.a() >> 1) + 24) {
                            if (this.d > this.a.a.j && this.d < this.a.a.j + 24) {
                                this.e = 2;
                                return;
                            }
                            if (this.d >= k.b() - 20 && this.d <= k.b() - 20 + 24) {
                                this.e = 8;
                                return;
                            }
                        }
                        this.e = 1;
                        return;
                    }
                    case 10: {
                        if (this.c <= 0) return;
                        this.e = 1;
                    }
                }
            }
        }
    }
}

