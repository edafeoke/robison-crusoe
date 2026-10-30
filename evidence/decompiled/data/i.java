/*
 * Decompiled with CFR 0.152.
 */
package data;

import data.p;
import java.util.Random;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class i {
    public byte a;
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int[] a;
    public int[] b;
    public int f;
    public int g;
    public int h;
    public boolean a;
    public boolean b = true;
    private Random a;
    private p a = true;

    public i(p p2, byte by, int n2, int n3, int n4, int[] nArray, int[] nArray2) {
        this.b = new int[3];
        this.a = new Random();
        this.a = p2;
        this.a = by;
        this.a = n2;
        this.b = n3;
        this.c = p2.M >> 1;
        this.d = p2.M >> 1;
        switch (n4) {
            case 1: {
                this.e = p2.aj / 6;
                break;
            }
            case 2: {
                this.e = p2.aj / 5;
                break;
            }
            case 3: {
                this.e = p2.aj / 4;
                break;
            }
            case 4: {
                this.e = p2.aj / 3;
                break;
            }
            case 5: {
                this.e = p2.aj / 2;
            }
        }
        this.a = nArray;
        this.b = nArray2;
        this.f = 0;
        this.b();
    }

    public final void a() {
        if (Math.abs(this.a - this.a.Z) <= 2 && Math.abs(this.b - this.a.aa) <= 2) {
            this.g = this.a.Z - this.a;
            this.h = this.a.aa - this.b;
            this.a = true;
            this.b = true;
        }
        if (this.a) {
            if (this.g > 0) {
                if (this.c < this.a.M - this.e) {
                    this.g -= this.e;
                    this.c += this.e;
                } else {
                    boolean bl = this.a = this.a + 1 < 180 && this.a.k[this.b * 180 + this.a + 1] >= 5 && this.a.k[this.b * 180 + this.a + 1] <= 6 && this.a.a.e(this.b * 180 + this.a + 1, 1) && this.a.a.d(this.b * 180 + this.a + 1, 1) && this.a.a.c(this.b * 180 + this.a + 1, 1) && (this.a + 1 != this.a.Z || this.b != this.a.aa);
                    if (this.a) {
                        this.g -= this.e;
                        this.c = this.c + this.e - this.a.M;
                        this.a.l[this.b * 180 + this.a] = 0;
                        if (this.a < 179) {
                            ++this.a;
                        }
                        this.a.l[this.b * 180 + this.a] = this.a;
                    }
                }
                if (this.g <= 0) {
                    this.a = false;
                }
            } else {
                if (this.c > 0) {
                    this.g += this.e;
                    this.c -= this.e;
                } else {
                    boolean bl = this.a = this.a - 2 > 0 && this.a.k[this.b * 180 + this.a - 1] >= 5 && this.a.k[this.b * 180 + this.a - 1] <= 6 && this.a.a.e(this.b * 180 + this.a - 1, 0) && this.a.a.d(this.b * 180 + this.a - 1, 0) && this.a.a.c(this.b * 180 + this.a - 1, 0) && (this.a - 1 != this.a.Z || this.b != this.a.aa);
                    if (this.a) {
                        this.g += this.e;
                        this.c = this.c - this.e + this.a.M;
                        this.a.l[this.b * 180 + this.a] = 0;
                        if (this.a > 0) {
                            --this.a;
                        }
                        this.a.l[this.b * 180 + this.a] = this.a;
                    }
                }
                if (this.g > 0) {
                    this.a = false;
                }
            }
        }
        if (this.b) {
            if (this.h > 0) {
                if (this.d < this.a.M - this.e) {
                    this.h -= this.e;
                    this.d += this.e;
                } else {
                    boolean bl = this.b = this.b + 1 < 180 && this.a.k[this.b * 180 + this.a + 180] >= 5 && this.a.k[this.b * 180 + this.a + 180] <= 6 && this.a.a.e(this.b * 180 + this.a + 180, 3) && this.a.a.d(this.b * 180 + this.a + 180, 3) && this.a.a.c(this.b * 180 + this.a + 180, 3) && (this.a != this.a.Z || this.b + 1 != this.a.aa);
                    if (this.b) {
                        this.h -= this.e;
                        this.d = this.d + this.e - this.a.M;
                        this.a.l[this.b * 180 + this.a] = 0;
                        if (this.b < 179) {
                            ++this.b;
                        }
                        this.a.l[this.b * 180 + this.a] = this.a;
                    }
                }
                if (this.h <= 0) {
                    this.b = false;
                }
            } else {
                if (this.d > 0) {
                    this.h += this.e;
                    this.d -= this.e;
                } else {
                    boolean bl = this.b = this.b - 1 > 0 && this.a.k[this.b * 180 + this.a - 180] >= 5 && this.a.k[this.b * 180 + this.a - 180] <= 6 && this.a.a.e(this.b * 180 + this.a - 180, 2) && this.a.a.d(this.b * 180 + this.a - 180, 2) && this.a.a.c(this.b * 180 + this.a - 180, 2) && (this.a != this.a.Z || this.b - 1 != this.a.aa);
                    if (this.b) {
                        this.h += this.e;
                        this.d = this.d - this.e + this.a.M;
                        this.a.l[this.b * 180 + this.a] = 0;
                        if (this.b > 0) {
                            --this.b;
                        }
                        this.a.l[this.b * 180 + this.a] = this.a;
                    }
                }
                if (this.h > 0) {
                    this.b = false;
                }
            }
        }
        if (!this.a && !this.b) {
            this.b();
            this.a = true;
            this.b = true;
        }
        if (Math.abs(this.a - this.a.Z) <= 1 && Math.abs(this.b - this.a.aa) <= 1) {
            this.a.c = 0.0f;
            this.a.aO = true;
        }
    }

    public final void a(int n2, int n3) {
        if (this.g > 0 && this.a || this.g <= 0 && !this.a) {
            if (this.f < 2) {
                this.a.a.a(this.a.a(), this.b[0], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.c, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.d);
            } else if (this.f < 4) {
                this.a.a.a(this.a.a(), this.b[1], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.c, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.d);
            } else {
                this.a.a.a(this.a.a(), this.b[2], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.c, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.d);
            }
            if (this.f < 3) {
                this.a.a.a(this.a.a(), 589833, this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.c, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.d);
            } else {
                this.a.a.a(this.a.a(), 589834, this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.c, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.d);
            }
        } else {
            if (this.f < 2) {
                this.a.a.a(this.a.a(), this.a[0], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.c, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.d);
            } else if (this.f < 4) {
                this.a.a.a(this.a.a(), this.a[1], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.c, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.d);
            } else {
                this.a.a.a(this.a.a(), this.a[2], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.c, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.d);
            }
            if (this.f < 3) {
                this.a.a.a(this.a.a(), 589831, this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.c, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.d);
            } else {
                this.a.a.a(this.a.a(), 589832, this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.c, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.d);
            }
        }
        if (++this.f == 6) {
            this.f = 0;
        }
    }

    public final void b() {
        this.g = this.a.M * 10 - this.a.M * (Math.abs(this.a.nextInt()) % 20);
        this.h = this.a.M * 10 - this.a.M * (Math.abs(this.a.nextInt()) % 20);
    }
}

