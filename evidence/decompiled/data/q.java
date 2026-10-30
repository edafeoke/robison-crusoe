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
public final class q {
    public byte a;
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public int[] a;
    public int[] b;
    public int[] c;
    public int k;
    public int l;
    public long a;
    public long b;
    public int m;
    public int n;
    public boolean a;
    public boolean b = true;
    public boolean c = false;
    public int o;
    private Random a;
    private p a = true;

    public q(p p2, byte by, int n2, int n3, int n4, int n5, int n6, int[] nArray, int[] nArray2, int[] nArray3) {
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
                this.i = p2.aj / 6;
                this.j = p2.aj / 5;
                break;
            }
            case 2: {
                this.i = p2.aj / 5;
                this.j = p2.aj / 4;
                break;
            }
            case 3: {
                this.i = p2.aj / 4;
                this.j = p2.aj / 3;
                break;
            }
            case 4: {
                this.i = p2.aj / 3;
                this.j = p2.aj / 2;
                break;
            }
            case 5: {
                this.i = p2.aj / 2;
            }
        }
        this.l = n6;
        this.a = nArray;
        this.b = nArray2;
        this.c = nArray3;
        this.k = 0;
        this.a = 0L;
        this.b();
        this.o = 0;
    }

    public final void a() {
        if (Math.abs(this.c - this.a.Z) <= 2 && Math.abs(this.d - this.a.aa) <= 2) {
            this.c = true;
            this.h = this.j;
            this.m = this.c > this.a.Z ? (this.c - this.a.Z + this.c - this.a < this.g ? (this.c - this.a.Z) * this.a.M : (this.a + this.g - this.c) * this.a.M) : (this.c - this.a.Z + this.c - this.a > -this.g ? (this.c - this.a.Z) * this.a.M : (this.a - this.g - this.c) * this.a.M);
            this.n = this.d > this.a.aa ? (this.d - this.a.aa + this.d - this.b < this.g ? (this.d - this.a.aa) * this.a.M : (this.b + this.g - this.d) * this.a.M) : (this.d - this.a.aa + this.d - this.b > -this.g ? (this.d - this.a.aa) * this.a.M : (this.b - this.g - this.d) * this.a.M);
            if (this.o == 1) {
                this.o = 0;
            }
            this.a = true;
            this.b = true;
        } else {
            this.c = false;
            this.h = this.i;
        }
        switch (this.o) {
            case 0: {
                if (this.a) {
                    if (this.m > 0) {
                        if (this.e < this.a.M - this.h) {
                            this.m -= this.h;
                            this.e += this.h;
                        } else {
                            boolean bl = this.a = this.a.a.a(this.d * 180 + this.c + 1, 1) && this.a.a.e(this.d * 180 + this.c + 1, 1) && this.a.a.d(this.d * 180 + this.c + 1, 1) && this.a.a.c(this.d * 180 + this.c + 1, 1) && (this.c + 1 != this.a.Z || this.d != this.a.aa);
                            if (this.a) {
                                this.m -= this.h;
                                this.e = this.e + this.h - this.a.M;
                                this.a.l[this.d * 180 + this.c] = 0;
                                ++this.c;
                                this.a.l[this.d * 180 + this.c] = this.a;
                            }
                        }
                        if (this.m <= 0) {
                            this.a = false;
                        }
                    } else {
                        if (this.e > 0) {
                            this.m += this.h;
                            this.e -= this.h;
                        } else {
                            boolean bl = this.a = this.a.a.a(this.d * 180 + this.c - 1, 0) && this.a.a.e(this.d * 180 + this.c - 1, 0) && this.a.a.d(this.d * 180 + this.c - 1, 0) && this.a.a.c(this.d * 180 + this.c - 1, 0) && (this.c - 1 != this.a.Z || this.d != this.a.aa);
                            if (this.a) {
                                this.m += this.h;
                                this.e = this.e - this.h + this.a.M;
                                this.a.l[this.d * 180 + this.c] = 0;
                                --this.c;
                                this.a.l[this.d * 180 + this.c] = this.a;
                            }
                        }
                        if (this.m > 0) {
                            this.a = false;
                        }
                    }
                }
                if (this.b) {
                    if (this.n > 0) {
                        if (this.f < this.a.M - this.h) {
                            this.n -= this.h;
                            this.f += this.h;
                        } else {
                            boolean bl = this.b = this.a.a.a(this.d * 180 + this.c + 180, 3) && this.a.a.e(this.d * 180 + this.c + 180, 3) && this.a.a.d(this.d * 180 + this.c + 180, 3) && this.a.a.c(this.d * 180 + this.c + 180, 3) && (this.c != this.a.Z || this.d + 1 != this.a.aa);
                            if (this.b) {
                                this.n -= this.h;
                                this.f = this.f + this.h - this.a.M;
                                this.a.l[this.d * 180 + this.c] = 0;
                                ++this.d;
                                this.a.l[this.d * 180 + this.c] = this.a;
                            }
                        }
                        if (this.n <= 0) {
                            this.b = false;
                        }
                    } else {
                        if (this.f > 0) {
                            this.n += this.h;
                            this.f -= this.h;
                        } else {
                            boolean bl = this.b = this.a.a.a(this.d * 180 + this.c - 180, 2) && this.a.a.e(this.d * 180 + this.c - 180, 2) && this.a.a.d(this.d * 180 + this.c - 180, 2) && this.a.a.c(this.d * 180 + this.c - 180, 2) && (this.c != this.a.Z || this.d - 1 != this.a.aa);
                            if (this.b) {
                                this.n += this.h;
                                this.f = this.f - this.h + this.a.M;
                                this.a.l[this.d * 180 + this.c] = 0;
                                --this.d;
                                this.a.l[this.d * 180 + this.c] = this.a;
                            }
                        }
                        if (this.n > 0) {
                            this.b = false;
                        }
                    }
                }
                if (this.a || this.b || this.c) break;
                int n2 = Math.abs(this.a.nextInt()) % 5;
                if (n2 == 0) {
                    this.b = (this.l + Math.abs(this.a.nextInt()) % 50) * 100;
                    this.a = 0L;
                    this.o = 1;
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
                    this.b = (this.l + Math.abs(this.a.nextInt()) % 50) * 100;
                    this.a = 0L;
                    break;
                }
                this.b();
                this.a = true;
                this.b = true;
                this.o = 0;
            }
        }
        this.a += this.a.f;
    }

    public final void a(int n2, int n3) {
        switch (this.o) {
            case 0: {
                if (this.a.k[this.d * 180 + this.c] != this.a.e[4]) {
                    this.a.a.a(((k)this.a).a, 589827, this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
                }
                if (this.m > 0 && this.a || this.m <= 0 && !this.a) {
                    if (this.k < 2) {
                        this.a.a.a(((k)this.a).a, this.b[0], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
                        break;
                    }
                    this.a.a.a(((k)this.a).a, this.b[1], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
                    break;
                }
                if (this.k < 2) {
                    this.a.a.a(((k)this.a).a, this.a[0], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
                    break;
                }
                this.a.a.a(((k)this.a).a, this.a[1], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
                break;
            }
            case 1: {
                if (this.a.k[this.d * 180 + this.c] != this.a.e[4]) {
                    this.a.a.a(((k)this.a).a, 589827, this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
                }
                if (this.k < 2) {
                    this.a.a.a(((k)this.a).a, this.c[0], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
                    break;
                }
                this.a.a.a(((k)this.a).a, this.c[1], this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
            }
        }
        if (this.a.k[this.d * 180 + this.c] == this.a.e[4]) {
            if (this.k < 2) {
                this.a.a.a(((k)this.a).a, 589826, this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
            } else {
                this.a.a.a(((k)this.a).a, 589830, this.a.aM + this.a.M * n2 - this.a.M + this.a.U + this.e, this.a.aN + this.a.M * n3 - this.a.M + this.a.V + this.f);
            }
        }
        if (++this.k == 4) {
            this.k = 0;
        }
    }

    public final void b() {
        if (Math.abs(this.a - this.c) < this.g) {
            this.m = Math.abs(this.a.nextInt()) % (2 * this.a.M * Math.abs(this.g - Math.abs(this.a - this.c))) - this.a.M * Math.abs(this.g - Math.abs(this.a - this.c));
        } else {
            this.m = Math.abs(this.a.nextInt()) % (this.a.M * this.g);
            if (this.c > this.a) {
                this.m = -this.m;
            }
        }
        if (Math.abs(this.b - this.d) < this.g) {
            this.n = Math.abs(this.a.nextInt()) % (2 * this.a.M * Math.abs(this.g - Math.abs(this.b - this.d))) - this.a.M * Math.abs(this.g - Math.abs(this.b - this.d));
            return;
        }
        this.n = Math.abs(this.a.nextInt()) % (this.a.M * this.g);
        if (this.d > this.b) {
            this.n = -this.n;
        }
    }
}

