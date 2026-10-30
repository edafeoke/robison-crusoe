/*
 * Decompiled with CFR 0.152.
 */
package data;

import data.p;
import java.util.Random;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class l {
    public byte a;
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int[] a;
    public int[] b;
    public int[] c;
    public int i;
    public int j;
    public long a;
    public long b;
    public int k;
    public int l;
    public boolean a;
    public boolean b = true;
    public int m;
    private Random a;
    private p a = true;

    public l(p p2, byte by, int n2, int n3, int n4, int n5, int n6, int[] nArray, int[] nArray2, int[] nArray3) {
        this.b = new int[2];
        this.c = new int[2];
        this.a = new Random();
        this.a = p2;
        this.a = by;
        this.a = n2;
        this.b = n3;
        this.c = n2;
        this.d = n3;
        this.e = p2.M >> 1;
        this.f = p2.M >> 1;
        this.g = n4;
        switch (n5) {
            case 1: {
                this.h = p2.aj / 6;
                break;
            }
            case 2: {
                this.h = p2.aj / 5;
                break;
            }
            case 3: {
                this.h = p2.aj / 4;
                break;
            }
            case 4: {
                this.h = p2.aj / 3;
                break;
            }
            case 5: {
                this.h = p2.aj / 2;
            }
        }
        this.j = n6;
        this.a = nArray;
        this.b = nArray2;
        this.c = nArray3;
        this.i = 0;
        this.a = 0L;
        this.b();
        this.m = 0;
    }

    public final void a() {
        switch (this.m) {
            case 0: {
                if (this.a) {
                    if (this.k > 0) {
                        if (this.e < this.a.M - this.h) {
                            this.k -= this.h;
                            this.e += this.h;
                        } else {
                            boolean bl = this.a = this.a.a.a(this.d * 180 + this.c + 1, 1) && this.a.a.e(this.d * 180 + this.c + 1, 1) && this.a.a.d(this.d * 180 + this.c + 1, 1) && this.a.a.c(this.d * 180 + this.c + 1, 1) && (this.c + 1 != this.a.Z || this.d != this.a.aa);
                            if (this.a) {
                                this.k -= this.h;
                                this.e = this.e + this.h - this.a.M;
                                this.a.l[this.d * 180 + this.c] = 0;
                                ++this.c;
                                this.a.l[this.d * 180 + this.c] = this.a;
                            }
                        }
                        if (this.k <= 0) {
                            this.a = false;
                        }
                    } else {
                        if (this.e > 0) {
                            this.k += this.h;
                            this.e -= this.h;
                        } else {
                            boolean bl = this.a = this.a.a.a(this.d * 180 + this.c - 1, 0) && this.a.a.e(this.d * 180 + this.c - 1, 0) && this.a.a.d(this.d * 180 + this.c - 1, 0) && this.a.a.c(this.d * 180 + this.c - 1, 0) && (this.c - 1 != this.a.Z || this.d != this.a.aa);
                            if (this.a) {
                                this.k += this.h;
                                this.e = this.e - this.h + this.a.M;
                                this.a.l[this.d * 180 + this.c] = 0;
                                --this.c;
                                this.a.l[this.d * 180 + this.c] = this.a;
                            }
                        }
                        if (this.k > 0) {
                            this.a = false;
                        }
                    }
                }
                if (this.b) {
                    if (this.l > 0) {
                        if (this.f < this.a.M - this.h) {
                            this.l -= this.h;
                            this.f += this.h;
                        } else {
                            boolean bl = this.b = this.a.a.a(this.d * 180 + this.c + 180, 3) && this.a.a.e(this.d * 180 + this.c + 180, 3) && this.a.a.d(this.d * 180 + this.c + 180, 3) && this.a.a.c(this.d * 180 + this.c + 180, 3) && (this.c != this.a.Z || this.d + 1 != this.a.aa);
                            if (this.b) {
                                this.l -= this.h;
                                this.f = this.f + this.h - this.a.M;
                                this.a.l[this.d * 180 + this.c] = 0;
                                ++this.d;
                                this.a.l[this.d * 180 + this.c] = this.a;
                            }
                        }
                        if (this.l <= 0) {
                            this.b = false;
                        }
                    } else {
                        if (this.f > 0) {
                            this.l += this.h;
                            this.f -= this.h;
                        } else {
                            boolean bl = this.b = this.a.a.a(this.d * 180 + this.c - 180, 2) && this.a.a.e(this.d * 180 + this.c - 180, 2) && this.a.a.d(this.d * 180 + this.c - 180, 2) && this.a.a.c(this.d * 180 + this.c - 180, 2) && (this.c != this.a.Z || this.d - 1 != this.a.aa);
                            if (this.b) {
                                this.l += this.h;
                                this.f = this.f - this.h + this.a.M;
                                this.a.l[this.d * 180 + this.c] = 0;
                                --this.d;
                                this.a.l[this.d * 180 + this.c] = this.a;
                            }
                        }
                        if (this.l > 0) {
                            this.b = false;
                        }
                    }
                }
                if (this.a || this.b) break;
                int n2 = Math.abs(this.a.nextInt()) % 5;
                if (n2 == 0) {
                    if (this.a != 48 || this.a.k[this.d * 180 + this.c] != this.a.e[4]) {
                        this.b = (this.j + Math.abs(this.a.nextInt()) % 50) * 100;
                        this.a = 0L;
                        this.m = 1;
                        break;
                    }
                    this.b();
                    this.a = true;
                    this.b = true;
                    break;
                }
                this.b();
                this.a = true;
                this.b = true;
                break;
            }
            case 1: {
                if (this.a <= this.b) break;
                int n3 = Math.abs(this.a.nextInt()) % 5;
                if (n3 == 0) {
                    this.b = (this.j + Math.abs(this.a.nextInt()) % 50) * 100;
                    this.a = 0L;
                    break;
                }
                this.b();
                this.a = true;
                this.b = true;
                this.m = 0;
            }
        }
        this.a += this.a.f;
    }

    public final void a(int n2, int n3) {
        switch (this.m) {
            case 0: {
                if (this.a.k[this.d * 180 + this.c] != this.a.e[4]) {
                    this.a.a.a(this.a.a(), 589827, this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
                }
                if (this.k > 0 && this.a || this.k <= 0 && !this.a) {
                    if (this.i < 2) {
                        this.a.a.a(this.a.a(), this.b[0], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
                        break;
                    }
                    this.a.a.a(this.a.a(), this.b[1], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
                    break;
                }
                if (this.i < 2) {
                    this.a.a.a(this.a.a(), this.a[0], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
                    break;
                }
                this.a.a.a(this.a.a(), this.a[1], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
                break;
            }
            case 1: {
                if (this.a.k[this.d * 180 + this.c] != this.a.e[4]) {
                    this.a.a.a(this.a.a(), 589827, this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
                }
                if (this.i < 2) {
                    this.a.a.a(this.a.a(), this.c[0], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
                    break;
                }
                this.a.a.a(this.a.a(), this.c[1], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
            }
        }
        if (this.a.k[this.d * 180 + this.c] == this.a.e[4]) {
            if (this.i < 2) {
                this.a.a.a(this.a.a(), 589826, this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
            } else {
                this.a.a.a(this.a.a(), 589830, this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
            }
        }
        if (++this.i == 4) {
            this.i = 0;
        }
    }

    public final void b() {
        if (Math.abs(this.a - this.c) < this.g) {
            this.k = Math.abs(this.a.nextInt()) % (2 * this.a.M * Math.abs(this.g - Math.abs(this.a - this.c))) - this.a.M * Math.abs(this.g - Math.abs(this.a - this.c));
        } else {
            this.k = Math.abs(this.a.nextInt()) % (this.a.M * this.g);
            if (this.c > this.a) {
                this.k = -this.k;
            }
        }
        if (Math.abs(this.b - this.d) < this.g) {
            this.l = Math.abs(this.a.nextInt()) % (2 * this.a.M * Math.abs(this.g - Math.abs(this.b - this.d))) - this.a.M * Math.abs(this.g - Math.abs(this.b - this.d));
            return;
        }
        this.l = Math.abs(this.a.nextInt()) % (this.a.M * this.g);
        if (this.d > this.b) {
            this.l = -this.l;
        }
    }
}

