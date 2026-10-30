/*
 * Decompiled with CFR 0.152.
 */
package data;

import data.e;
import data.k;
import data.p;
import data.z;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class t {
    private p a;
    private e a;
    private byte[] a;
    private int a = 0;

    public t(p p2) {
        this.a = p2;
    }

    public final void a(int n2) {
        byte[] byArray = new byte[1];
        byte[] byArray2 = byArray;
        byArray[0] = (byte)n2;
        try {
            this.a.a("lang", 0, byArray2.length, byArray2);
            return;
        }
        catch (z z2) {
            return;
        }
    }

    public final int a() {
        byte[] byArray = null;
        try {
            byArray = this.a.a("lang", 0);
        }
        catch (z z2) {}
        byte by = byArray != null ? byArray[0] : (byte)0;
        return by;
    }

    public final void a(boolean bl) {
        byte[] byArray = new byte[1];
        boolean bl2 = bl;
        byArray[0] = (byte)(bl2 ? 1 : 0);
        try {
            this.a.a("daylightSaving", 1, byArray.length, byArray);
            return;
        }
        catch (z z2) {
            return;
        }
    }

    public final boolean a() {
        byte[] byArray = null;
        try {
            byArray = this.a.a("daylightSaving", 1);
        }
        catch (z z2) {}
        byte by = byArray != null ? byArray[0] : (byte)1;
        boolean bl = by == 1;
        return bl;
    }

    public final void b(int n2) {
        byte[] byArray = new byte[1];
        byte[] byArray2 = byArray;
        byArray[0] = (byte)n2;
        try {
            this.a.a("days", 2, byArray2.length, byArray2);
            return;
        }
        catch (z z2) {
            return;
        }
    }

    public final int b() {
        byte[] byArray = null;
        try {
            byArray = this.a.a("days", 2);
        }
        catch (z z2) {}
        byte by = byArray != null ? byArray[0] : (byte)1;
        return by;
    }

    public final void a() {
        int n2;
        int n3;
        int n4;
        this.a = 70 + this.a.aB + this.a.av + this.a.aw + this.a.s.length * 20 + this.a.r.length * 20 + this.a.a.length + this.a.b.length + this.a.c.length + this.a.d.length;
        this.a = new byte[this.a];
        this.a = 0;
        this.a[this.a++] = (byte)this.a.ab;
        this.a[this.a++] = (byte)this.a.ac;
        if (this.a.ad < 0) {
            this.a[this.a++] = 1;
            this.a.ad = -this.a.ad;
            this.a[this.a++] = (byte)this.a.ad;
        } else {
            this.a[this.a++] = 0;
            this.a[this.a++] = (byte)this.a.ad;
        }
        if (this.a.ae < 0) {
            this.a[this.a++] = 1;
            this.a.ae = -this.a.ae;
            this.a[this.a++] = (byte)this.a.ae;
        } else {
            this.a[this.a++] = 0;
            this.a[this.a++] = (byte)this.a.ae;
        }
        this.a[this.a++] = (byte)this.a.Z;
        this.a[this.a++] = (byte)this.a.aa;
        this.a[this.a++] = (byte)this.a.aB;
        int n5 = 0;
        while (n5 < this.a.aB) {
            this.a[this.a++] = (byte)this.a.q[n5];
            ++n5;
        }
        int n6 = 0;
        while (n6 < this.a.aB) {
            n4 = 0;
            while (n4 < this.a.b.length) {
                if (this.a.m[n6] == (byte)n4) {
                    this.a[this.a++] = (byte)n4;
                }
                ++n4;
            }
            ++n6;
        }
        this.a[this.a++] = (byte)this.a.av;
        n4 = 0;
        while (n4 < this.a.av) {
            this.a[this.a++] = (byte)this.a.g[n4];
            ++n4;
        }
        int n7 = 0;
        while (n7 < this.a.av) {
            n3 = 0;
            while (n3 < this.a.b.length) {
                if (this.a.a[n7] == (byte)n3) {
                    this.a[this.a++] = (byte)n3;
                }
                ++n3;
            }
            ++n7;
        }
        this.a[this.a++] = (byte)this.a.aw;
        n3 = 0;
        while (n3 < this.a.aw) {
            this.a[this.a++] = (byte)this.a.h[n3];
            ++n3;
        }
        int n8 = 0;
        while (n8 < this.a.aw) {
            n2 = 0;
            while (n2 < this.a.b.length) {
                if (this.a.a[n8].equals(this.a.b[n2])) {
                    this.a[this.a++] = (byte)n2;
                }
                ++n2;
            }
            ++n8;
        }
        this.a[this.a++] = (byte)this.a.a;
        this.a[this.a++] = (byte)this.a.b;
        this.a[this.a++] = (byte)this.a.c;
        this.a[this.a++] = (byte)this.a.e;
        this.a[this.a++] = (byte)this.a.f;
        this.a[this.a++] = (byte)this.a.g;
        this.a[this.a++] = (byte)this.a.L;
        this.a[this.a++] = (byte)this.a.K;
        this.a[this.a++] = (byte)this.a.J;
        if (this.a.f[17] == 393219) {
            this.a[this.a++] = 17;
        } else if (this.a.f[17] == 393216) {
            this.a[this.a++] = 9;
        } else if (this.a.f[17] == 393217) {
            this.a[this.a++] = 10;
        }
        n2 = 0;
        while (n2 < this.a.s.length) {
            this.a((long)this.a.s[n2]);
            ++n2;
        }
        this.a[this.a++] = (byte)this.a.aD;
        this.a[this.a++] = (byte)this.a.c;
        int n9 = 0;
        while (n9 < this.a.r.length) {
            this.a((long)this.a.r[n9]);
            ++n9;
        }
        this.a[this.a++] = (byte)this.a.aC;
        this.a[this.a++] = (byte)this.a.ap;
        if (this.a.T) {
            this.a.b = this.a.P + 180 + 1;
        } else {
            int n10 = 0;
            while (n10 < this.a.l.length) {
                if (this.a.l[n10] == 85 || this.a.l[n10] == 95) {
                    this.a.b = n10;
                }
                ++n10;
            }
        }
        if (this.a.b == 14925L) {
            this.a.b = 14926L;
        }
        this.a(this.a.b);
        this.a[this.a++] = this.a.T ? (byte)1 : 0;
        int n11 = 0;
        while (n11 < this.a.a.length) {
            this.a[this.a++] = (byte)this.a.a[n11];
            ++n11;
        }
        int n12 = 0;
        while (n12 < this.a.b.length) {
            this.a[this.a++] = (byte)this.a.b[n12];
            ++n12;
        }
        int n13 = 0;
        while (n13 < this.a.c.length) {
            this.a[this.a++] = (byte)this.a.c[n13];
            ++n13;
        }
        int n14 = 0;
        while (n14 < this.a.d.length) {
            this.a[this.a++] = (byte)this.a.d[n14];
            ++n14;
        }
        this.a[this.a++] = (byte)this.a.H;
        this.a[this.a++] = (byte)this.a.I;
        this.a[this.a++] = (byte)this.a.J;
        this.a[this.a++] = (byte)this.a.K;
        this.a[this.a++] = (byte)this.a.D;
        this.a[this.a++] = (byte)this.a.E;
        this.a[this.a++] = (byte)this.a.F;
        this.a[this.a++] = (byte)this.a.G;
        this.a[this.a++] = this.a.aj ? (byte)1 : 0;
        this.a[this.a++] = this.a.ak ? (byte)1 : 0;
        this.a[this.a++] = this.a.al ? (byte)1 : 0;
        this.a[this.a++] = this.a.am ? (byte)1 : 0;
        this.a[this.a++] = this.a.an ? (byte)1 : 0;
        this.a[this.a++] = this.a.ao ? (byte)1 : 0;
        this.a[this.a++] = this.a.ap ? (byte)1 : 0;
        this.a[this.a++] = this.a.aq ? (byte)1 : 0;
        this.a[this.a++] = this.a.ar ? (byte)1 : 0;
        this.a[this.a++] = this.a.as ? (byte)1 : 0;
        this.a[this.a++] = this.a.at ? (byte)1 : 0;
        this.a[this.a++] = this.a.au ? (byte)1 : 0;
        this.a[this.a++] = this.a.av ? (byte)1 : 0;
        this.a[this.a++] = (byte)this.a.a.a;
        this.a(this.a.a);
        try {
            this.a.a("RobinsonSave_1", 3, this.a.length, this.a);
        }
        catch (z z2) {}
        this.a.b = true;
    }

    private void a(long l2) {
        this.a[this.a++] = (byte)(l2 & 0x7FL);
        this.a[this.a++] = (byte)(l2 >> 7 & 0x7FL);
        this.a[this.a++] = (byte)(l2 >> 14 & 0x7FL);
        this.a[this.a++] = (byte)(l2 >> 21 & 0x7FL);
        this.a[this.a++] = (byte)(l2 >> 28 & 0x7FL);
        this.a[this.a++] = (byte)(l2 >> 35 & 0x7FL);
        this.a[this.a++] = (byte)(l2 >> 42 & 0x7FL);
        this.a[this.a++] = (byte)(l2 >> 49 & 0x7FL);
        this.a[this.a++] = (byte)(l2 >> 56 & 0x7FL);
        this.a[this.a++] = (byte)(l2 >> 63 & 1L);
    }

    private long a() {
        long l2 = this.a[this.a++] + (this.a[this.a++] << 7) + (this.a[this.a++] << 14) + (this.a[this.a++] << 21) + (this.a[this.a++] << 28) + (this.a[this.a++] << 35) + (this.a[this.a++] << 42) + (this.a[this.a++] << 49) + (this.a[this.a++] << 56) + (this.a[this.a++] << 63);
        return l2;
    }

    public final void a(boolean bl, boolean bl2) {
        try {
            this.a = this.a.a("RobinsonSave_1", 3);
            bl2 = false;
        }
        catch (z z2) {
            bl2 = true;
        }
        this.a.d = this.a.e = bl2;
        if (!bl2) {
            byte by;
            int n2;
            int n3;
            int n4;
            this.a = 0;
            this.a.ab = this.a[this.a++];
            this.a.ac = this.a[this.a++];
            if (this.a.ab < 0) {
                n4 = 128 + this.a.ab;
                this.a.ab = 128 + n4;
            }
            if (this.a.ac < 0) {
                n4 = 128 + this.a.ac;
                this.a.ac = 128 + n4;
            }
            if (this.a[this.a++] == 1) {
                this.a.ad = this.a[this.a++];
                this.a.ad = -this.a.ad;
            } else {
                this.a.ad = this.a[this.a++];
            }
            if (this.a[this.a++] == 1) {
                this.a.ae = this.a[this.a++];
                this.a.ae = -this.a.ae;
            } else {
                this.a.ae = this.a[this.a++];
            }
            this.a.Z = this.a[this.a++];
            this.a.aa = this.a[this.a++];
            if (this.a.Z < 0) {
                n4 = 128 + this.a.Z;
                this.a.Z = 128 + n4;
            }
            if (this.a.aa < 0) {
                n4 = 128 + this.a.aa;
                this.a.aa = 128 + n4;
            }
            this.a.aB = this.a[this.a++];
            n4 = 0;
            while (n4 < this.a.aB) {
                this.a.q[n4] = this.a[this.a++];
                ++n4;
            }
            int n5 = 0;
            while (n5 < this.a.aB) {
                n3 = this.a[this.a++];
                this.a.m[n5] = (byte)n3;
                ++n5;
            }
            this.a.av = this.a[this.a++];
            n3 = 0;
            while (n3 < this.a.av) {
                this.a.g[n3] = this.a[this.a++];
                ++n3;
            }
            int n6 = 0;
            while (n6 < this.a.av) {
                n2 = this.a[this.a++];
                this.a.a[n6] = (byte)n2;
                ++n6;
            }
            this.a.aw = this.a[this.a++];
            n2 = 0;
            while (n2 < this.a.aw) {
                this.a.h[n2] = this.a[this.a++];
                ++n2;
            }
            int n7 = 0;
            while (n7 < this.a.aw) {
                by = this.a[this.a++];
                this.a.a[n7] = this.a.b[by];
                ++n7;
            }
            this.a.a = this.a[this.a++];
            this.a.b = this.a[this.a++];
            this.a.c = this.a[this.a++];
            this.a.e = this.a[this.a++];
            this.a.f = this.a[this.a++];
            this.a.g = this.a[this.a++];
            this.a.L = this.a[this.a++];
            this.a.d = (k.a() / 3 - 16) / 1;
            this.a.c = this.a[this.a++];
            this.a.d = this.a[this.a++];
            by = this.a[this.a++];
            this.a.f[17] = this.a.f[by];
            this.a.c[17] = this.a.c[by];
            this.a.b[17] = this.a.b[by];
            int n8 = 0;
            while (n8 < this.a.s.length) {
                this.a.s[n8] = (int)this.a();
                ++n8;
            }
            this.a.aD = this.a[this.a++];
            this.a.c = this.a[this.a++];
            int n9 = 0;
            while (n9 < this.a.r.length) {
                this.a.r[n9] = (int)this.a();
                ++n9;
            }
            this.a.aC = this.a[this.a++];
            this.a.ap = this.a[this.a++];
            this.a.b = this.a();
            this.a.T = this.a[this.a++] == 1;
            int n10 = 0;
            while (n10 < this.a.a.length) {
                this.a.a[n10] = this.a[this.a++];
                ++n10;
            }
            int n11 = 0;
            while (n11 < this.a.b.length) {
                this.a.b[n11] = this.a[this.a++];
                ++n11;
            }
            int n12 = 0;
            while (n12 < this.a.c.length) {
                this.a.c[n12] = this.a[this.a++];
                ++n12;
            }
            int n13 = 0;
            while (n13 < this.a.d.length) {
                this.a.d[n13] = this.a[this.a++];
                ++n13;
            }
            this.a.H = this.a[this.a++];
            this.a.I = this.a[this.a++];
            this.a.J = this.a[this.a++];
            this.a.K = this.a[this.a++];
            this.a.D = this.a[this.a++];
            this.a.E = this.a[this.a++];
            this.a.F = this.a[this.a++];
            this.a.G = this.a[this.a++];
            this.a.aj = this.a[this.a++] == 1;
            this.a.ak = this.a[this.a++] == 1;
            this.a.al = this.a[this.a++] == 1;
            this.a.am = this.a[this.a++] == 1;
            this.a.an = this.a[this.a++] == 1;
            this.a.ao = this.a[this.a++] == 1;
            this.a.ap = this.a[this.a++] == 1;
            this.a.aq = this.a[this.a++] == 1;
            this.a.ar = this.a[this.a++] == 1;
            this.a.as = this.a[this.a++] == 1;
            this.a.at = this.a[this.a++] == 1;
            this.a.au = this.a[this.a++] == 1;
            this.a.av = this.a[this.a++] == 1;
            this.a.a.a = this.a[this.a++];
            this.a.a = this.a();
            return;
        }
        this.a.Z = 8;
        this.a.aa = 148;
        this.a.ab = this.a.Z - this.a.N / 2;
        this.a.ac = this.a.aa - this.a.O / 2;
    }
}

