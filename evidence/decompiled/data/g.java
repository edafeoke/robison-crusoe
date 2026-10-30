/*
 * Decompiled with CFR 0.152.
 */
package data;

import data.d;
import data.i;
import data.k;
import data.l;
import data.o;
import data.p;
import data.q;
import data.u;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class g {
    private p a;
    private int[] b;
    private int[] c;
    private int[] d;
    private byte[] a;
    private boolean a;
    private int[] e;
    private int[] f;
    private byte[] b;
    private boolean b;
    private boolean c;
    private byte[] c;
    private byte[] d;
    public u[] a;
    public int a;
    public u[] b;
    public int b;
    public u[] c;
    public int c;
    public u[] d;
    public int d;
    public u[] e;
    public int e = 0;
    public u[] f;
    public int f = 0;
    public u[] g;
    public int g = 0;
    public int[][] a;
    public int h = 0;
    public l[] a;
    public int i = 0;
    public l[] b;
    public int j = 0;
    public l[] c;
    public int k = 0;
    public int[][] b;
    public int l = 0;
    public d[] a;
    public int m = 0;
    public d[] b;
    public int n = 0;
    public d[] c;
    public int o = 0;
    public d[] d;
    public int p = 0;
    public int[][] c;
    public int q = 0;
    public i[] a;
    public int r = 0;
    public int[] a;
    public int s = 0;
    public q[] a = 0;
    public int t = 0;
    public q[] b = 0;
    public int u = 0;
    public q[] c = 0;
    public int v = 0;
    public q[] d;
    public int w = 0;
    public int[][] d = 0;
    public int x = 0;

    public g(p p2) {
        this.a = new byte[49];
        this.e = new int[49];
        this.f = new int[49];
        this.b = new byte[32400];
        this.c = new byte[32400];
        this.d = new byte[32400];
        this.a = p2;
    }

    public final void a() {
        this.o();
        this.p();
        this.q();
        this.r();
        this.a("BIGislandFULL", this.a.k);
        this.a.a();
    }

    public final void b() {
        this.a("BIGislandRocks", this.a.j);
        this.a.a();
        this.a("BIGislandDecor", this.a.g);
        this.a.a();
        int n2 = 0;
        while (n2 < this.a.g.length) {
            if (this.a.g[n2] != 0) {
                if (this.a.g[n2] == this.d[18]) {
                    this.a.h[n2] = (byte)data.o.a(13);
                }
                if (this.a.g[n2] == this.d[1]) {
                    this.a.h[n2] = (byte)data.o.a(8);
                }
                if (this.a.g[n2] == this.d[5]) {
                    this.a.h[n2] = (byte)data.o.a(9);
                }
                if (this.a.g[n2] == this.d[13]) {
                    this.a.h[n2] = (byte)data.o.a(12);
                }
                if (this.a.g[n2] == this.d[21]) {
                    this.a.h[n2] = (byte)data.o.a(4);
                }
                if (this.a.g[n2] == this.d[22]) {
                    this.a.h[n2] = (byte)data.o.a(4);
                }
                if (this.a.g[n2] == this.d[49]) {
                    this.a.h[n2] = (byte)data.o.a(6);
                }
            } else {
                this.a.h[n2] = -1;
            }
            ++n2;
        }
    }

    public final void c() {
        this.a("BIGislandBorders", this.a.i);
        this.a.a();
        this.a("BIGislandObjects", this.a.l);
        this.a.a();
        this.a = false;
        this.a[0] = 6;
        this.a[1] = 10;
        this.a[2] = 11;
        this.a[3] = 15;
        this.a[4] = 16;
        this.a[5] = 17;
        this.a[6] = 18;
        this.a[7] = 19;
        this.a[8] = 20;
        this.a[9] = 23;
        this.a[10] = 24;
        this.a[11] = 77;
        this.a[12] = 78;
        this.a[13] = 79;
        this.a[14] = 80;
        this.a[15] = 81;
        this.a[16] = 82;
        this.a[17] = 83;
        this.a[18] = 84;
        this.a[19] = 85;
        this.a[20] = 86;
        this.a[21] = 87;
        this.a[22] = 88;
        this.a[23] = 89;
        this.a[24] = 90;
        this.a[25] = 91;
        this.a[26] = 93;
        this.a[27] = 95;
        this.a[28] = 97;
        this.a[29] = 100;
        this.a[30] = 101;
        this.a[31] = 102;
        this.a[32] = 103;
        this.a[33] = 104;
        this.a[34] = 105;
        this.a[35] = 106;
        this.a[36] = 107;
        this.a[37] = 108;
        this.a[38] = 109;
        this.a[39] = 110;
        this.a[40] = 111;
        this.a[41] = 112;
        this.a[42] = 113;
        this.a[43] = 114;
        this.a[44] = 115;
        this.a[45] = 118;
        this.a[46] = 98;
        this.a[47] = 121;
        this.a[48] = 124;
        this.a.N = data.k.a() / this.a.M;
        this.a.O = data.k.b() / this.a.M - 1;
        if (this.a.c) {
            this.a.Z = this.a.ab + this.a.N / 2;
            this.a.aa = this.a.ac + this.a.O / 2;
        }
    }

    private void k() {
        int n2 = 1;
        int n3 = 0;
        while (n3 < this.a.l.length) {
            if (this.a.l[n3] != 0 && !this.a.a.a((int)this.a.l[n3])) {
                this.a.a[n3] = (short)n2;
                ++n2;
            } else {
                this.a.a[n3] = -1;
            }
            ++n3;
        }
    }

    private void l() {
        this.b = this.a.l;
        int n2 = 0;
        try {
            n2 = 0;
            while (n2 < this.a.l.length) {
                if (this.b[n2] != 0) {
                    int n3 = 0;
                    while (n3 < this.a.length) {
                        if (this.a[n3] == this.b[n2]) {
                            int n4;
                            int n5 = 0;
                            while (n5 < this.a.aw) {
                                if (this.a.a[n5] == this.a.b[this.b[n2] - 1]) {
                                    this.b[n2] = 0;
                                    break;
                                }
                                ++n5;
                            }
                            if (this.b[n2] != 0) {
                                n4 = 0;
                                while (n4 < this.a.av) {
                                    if (this.a.a[n4] == this.b[n2] - 1) {
                                        this.b[n2] = 0;
                                        break;
                                    }
                                    ++n4;
                                }
                            }
                            if (this.b[n2] != 0) {
                                n4 = 0;
                                while (n4 < this.a.aB) {
                                    if (this.a.m[n4] == this.b[n2] - 1) {
                                        this.b[n2] = 0;
                                        break;
                                    }
                                    ++n4;
                                }
                            }
                        }
                        ++n3;
                    }
                }
                ++n2;
            }
        }
        catch (Exception exception) {
            System.out.println("erreur filterUniqueObjects " + n2 + "objectArrayFull_CPY[i]" + this.b[n2]);
            exception.printStackTrace();
        }
        this.a.l = this.b;
    }

    private void m() {
        int n2 = 0;
        while (n2 < this.a.l.length) {
            int n3 = 0;
            while (n3 < this.a.r.length) {
                if (this.a.r[n3] == this.a.a[n2]) {
                    this.a.l[n2] = 0;
                    this.a.a[n2] = 0;
                    break;
                }
                ++n3;
            }
            ++n2;
        }
    }

    private void n() {
        if (this.a.ap == 1) {
            int n2 = 0;
            while (n2 < this.a.l.length) {
                if (this.a.l[n2] == 85) {
                    this.a.l[n2] = 0;
                    this.a.l[(int)this.a.b] = !this.a.T ? 95 : 0;
                }
                ++n2;
            }
        }
    }

    public final void d() {
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        byte by = 0;
        short s2 = 0;
        boolean bl = false;
        boolean bl2 = false;
        int n5 = 0;
        try {
            this.k();
            boolean bl3 = false;
            ++n5;
            this.l();
            ++n5;
            this.n();
            ++n5;
            this.m();
            ++n5;
            this.b = this.a.l;
        }
        catch (Exception exception) {
            System.out.println("Exception mixUpObjectPos : " + n5);
            exception.printStackTrace();
        }
        int n6 = 0;
        while (n6 < this.b.length) {
            try {
                this.b = true;
                if (this.b[n6] != 0) {
                    int n7 = 0;
                    while (n7 < this.a.a.a.length) {
                        if (this.a.a.a[n7] == this.b[n6]) {
                            this.b = false;
                            break;
                        }
                        ++n7;
                    }
                    if (this.b) {
                        by = this.b[n6];
                        s2 = this.a.a[n6];
                        int n8 = 0;
                        while (n8 < this.a.s.length) {
                            if (this.a.s[n8] == s2) {
                                this.b[n6] = 0;
                                bl = true;
                                break;
                            }
                            bl = false;
                            ++n8;
                        }
                        if (!bl) {
                            n2 = data.o.a(7);
                            n2 = n2 < 4 ? -n2 : (n2 /= 2);
                            n3 = data.o.a(7);
                            n3 = n3 < 4 ? -n3 : (n3 /= 2);
                            n4 = n6 + (n2 + n3 * 180);
                            if (n4 > 31399) {
                                n4 = 31399;
                            }
                            if (!(this.a(n4) && this.e(n4, 1) && this.c(n4, 1) && this.d(n4, 1))) {
                                this.b[n6] = this.a(by) ? by : (byte)0;
                            } else {
                                this.b[n4] = by;
                                this.b[n6] = 0;
                                this.a.a[n4] = s2;
                                this.a.a[n6] = -1;
                            }
                        }
                    }
                }
            }
            catch (Exception exception) {
                System.out.println(n6 + "->" + n4);
            }
            ++n6;
        }
        this.a.l = this.b;
    }

    public final void e() {
        int n2 = 0;
        while (n2 < this.a.l.length) {
            switch (this.a.l[n2]) {
                case 5: {
                    ++this.t;
                    break;
                }
                case 42: {
                    ++this.i;
                    break;
                }
                case 43: {
                    ++this.m;
                    break;
                }
                case 44: {
                    ++this.n;
                    break;
                }
                case 45: {
                    ++this.u;
                    break;
                }
                case 46: {
                    ++this.v;
                    break;
                }
                case 47: {
                    ++this.w;
                    break;
                }
                case 48: {
                    ++this.j;
                    break;
                }
                case 49: {
                    ++this.o;
                    break;
                }
                case 50: {
                    ++this.p;
                    break;
                }
                case 51: {
                    ++this.a;
                    break;
                }
                case 52: {
                    ++this.b;
                    break;
                }
                case 53: {
                    ++this.c;
                    break;
                }
                case 54: {
                    ++this.d;
                    break;
                }
                case 55: {
                    ++this.e;
                    break;
                }
                case 56: {
                    ++this.r;
                    break;
                }
                case 62: {
                    ++this.k;
                    break;
                }
                case 63: {
                    ++this.f;
                    break;
                }
                case 64: {
                    ++this.g;
                }
            }
            ++n2;
        }
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        int n14 = 0;
        int n15 = 0;
        int n16 = 0;
        int n17 = 0;
        int n18 = 0;
        this.a = new u[this.a];
        this.b = new u[this.b];
        this.c = new u[this.c];
        this.d = new u[this.d];
        this.e = new u[this.e];
        this.f = new u[this.f];
        this.g = new u[this.g];
        this.a = new l[this.i];
        this.b = new l[this.j];
        this.c = new l[this.k];
        this.a = new d[this.m];
        this.b = new d[this.n];
        this.c = new d[this.o];
        this.d = new d[this.p];
        this.a = new i[this.r];
        this.a = new q[this.t];
        this.b = new q[this.u];
        this.c = new q[this.v];
        this.d = new q[this.w];
        this.a = new int[this.a + this.b + this.c + this.d + this.e + this.f + this.g][2];
        this.b = new int[this.i + this.j + this.k][2];
        this.c = new int[this.m + this.n + this.o + this.p][2];
        this.a = new int[this.r];
        this.d = new int[this.t + this.u + this.v + this.w][2];
        int n19 = 0;
        while (n19 < this.a.l.length) {
            int[] nArray = new int[2];
            int[] nArray2 = new int[2];
            int[] nArray3 = new int[2];
            int[] nArray4 = new int[2];
            int[] nArray5 = new int[2];
            switch (this.a.l[n19]) {
                case 5: {
                    nArray[0] = 131072;
                    nArray[1] = 131073;
                    nArray2[0] = 131074;
                    nArray2[1] = 131075;
                    nArray5[0] = 131076;
                    nArray5[1] = 131077;
                    this.a[n16] = new q(this.a, this.a.l[n19], n19 % 180, n19 / 180, 7, 4, 50, nArray, nArray2, nArray5);
                    ++n16;
                    break;
                }
                case 42: {
                    nArray[0] = 131112;
                    nArray[1] = 131113;
                    nArray2[0] = 131112;
                    nArray2[1] = 131113;
                    nArray5[0] = 131112;
                    nArray5[1] = 131113;
                    this.a[n8] = new l(this.a, this.a.l[n19], n19 % 180, n19 / 180, 7, 1, 50, nArray, nArray2, nArray5);
                    ++n8;
                    break;
                }
                case 43: {
                    nArray[0] = 131086;
                    nArray[1] = 131087;
                    nArray2[0] = 131088;
                    nArray2[1] = 131089;
                    nArray5[0] = 131086;
                    nArray5[1] = 131087;
                    this.a[n11] = new d(this.a, this.a.l[n19], n19 % 180, n19 / 180, 7, 2, 50, nArray, nArray2, nArray5);
                    ++n11;
                    break;
                }
                case 44: {
                    nArray[0] = 131158;
                    nArray[1] = 131159;
                    nArray2[0] = 131160;
                    nArray2[1] = 131161;
                    nArray5[0] = 131158;
                    nArray5[1] = 131159;
                    this.b[n12] = new d(this.a, this.a.l[n19], n19 % 180, n19 / 180, 7, 4, 50, nArray, nArray2, nArray5);
                    ++n12;
                    break;
                }
                case 45: {
                    nArray[0] = 131082;
                    nArray[1] = 131083;
                    nArray2[0] = 131084;
                    nArray2[1] = 131085;
                    nArray5[0] = 131082;
                    nArray5[1] = 131083;
                    this.b[n17] = new q(this.a, this.a.l[n19], n19 % 180, n19 / 180, 7, 4, 50, nArray, nArray2, nArray5);
                    ++n17;
                    break;
                }
                case 47: {
                    nArray[0] = 131090;
                    nArray[1] = 131091;
                    nArray2[0] = 131092;
                    nArray2[1] = 131093;
                    nArray5[0] = 131090;
                    nArray5[1] = 131091;
                    this.d[n18] = new q(this.a, this.a.l[n19], n19 % 180, n19 / 180, 7, 2, 50, nArray, nArray2, nArray5);
                    ++n18;
                    break;
                }
                case 48: {
                    nArray[0] = 131078;
                    nArray[1] = 131079;
                    nArray2[0] = 131080;
                    nArray2[1] = 131081;
                    nArray5[0] = 131078;
                    nArray5[1] = 131078;
                    this.b[n9] = new l(this.a, this.a.l[n19], n19 % 180, n19 / 180, 7, 1, 50, nArray, nArray2, nArray5);
                    ++n9;
                    break;
                }
                case 49: {
                    nArray[0] = 131124;
                    nArray[1] = 131125;
                    nArray2[0] = 131126;
                    nArray2[1] = 131127;
                    nArray5[0] = 131128;
                    nArray5[1] = 131129;
                    this.c[n13] = new d(this.a, this.a.l[n19], n19 % 180, n19 / 180, 7, 2, 50, nArray, nArray2, nArray5);
                    ++n13;
                    break;
                }
                case 50: {
                    nArray[0] = 131118;
                    nArray[1] = 131119;
                    nArray2[0] = 131120;
                    nArray2[1] = 131121;
                    nArray5[0] = 131122;
                    nArray5[1] = 131123;
                    this.d[n14] = new d(this.a, this.a.l[n19], n19 % 180, n19 / 180, 7, 1, 50, nArray, nArray2, nArray5);
                    ++n14;
                    break;
                }
                case 52: {
                    nArray[0] = 131174;
                    nArray[1] = 131175;
                    nArray2[0] = 131176;
                    nArray2[1] = 131177;
                    nArray3[0] = 131178;
                    nArray3[1] = 131179;
                    nArray4[0] = 131180;
                    nArray4[1] = 131181;
                    nArray5[0] = 131176;
                    nArray5[1] = 131177;
                    this.b[n3] = new u(this.a, this.a.l[n19], n19 % 180, n19 / 180, 7, 4, 50, nArray, nArray2, nArray3, nArray4, nArray5);
                    ++n3;
                    break;
                }
                case 53: {
                    nArray[0] = 131142;
                    nArray[1] = 131143;
                    nArray2[0] = 131144;
                    nArray2[1] = 131145;
                    nArray3[0] = 131146;
                    nArray3[1] = 131147;
                    nArray4[0] = 131148;
                    nArray4[1] = 131149;
                    nArray5[0] = 131144;
                    nArray5[1] = 131145;
                    this.c[n4] = new u(this.a, this.a.l[n19], n19 % 180, n19 / 180, 7, 4, 50, nArray, nArray2, nArray3, nArray4, nArray5);
                    ++n4;
                    break;
                }
                case 54: {
                    nArray[0] = 131150;
                    nArray[1] = 131151;
                    nArray2[0] = 131152;
                    nArray2[1] = 131153;
                    nArray3[0] = 131154;
                    nArray3[1] = 131155;
                    nArray4[0] = 131156;
                    nArray4[1] = 131157;
                    nArray5[0] = 131152;
                    nArray5[1] = 131153;
                    this.d[n5] = new u(this.a, this.a.l[n19], n19 % 180, n19 / 180, 7, 3, 50, nArray, nArray2, nArray3, nArray4, nArray5);
                    ++n5;
                    break;
                }
                case 56: {
                    int[] nArray6 = new int[]{131162, 131163, 131164};
                    int[] nArray7 = new int[]{131165, 131166, 131167};
                    this.a[n15] = new i(this.a, this.a.l[n19], n19 % 180, n19 / 180, 5, nArray6, nArray7);
                    ++n15;
                    break;
                }
                case 62: {
                    nArray[0] = 131114;
                    nArray[1] = 131115;
                    nArray2[0] = 131116;
                    nArray2[1] = 131117;
                    nArray5[0] = 131114;
                    nArray5[1] = 131114;
                    this.c[n10] = new l(this.a, this.a.l[n19], n19 % 180, n19 / 180, 7, 1, 50, nArray, nArray2, nArray5);
                    ++n10;
                    break;
                }
                case 63: {
                    nArray[0] = 131190;
                    nArray[1] = 131191;
                    nArray2[0] = 131192;
                    nArray2[1] = 131193;
                    nArray3[0] = 131194;
                    nArray3[1] = 131195;
                    nArray4[0] = 131196;
                    nArray4[1] = 131197;
                    nArray5[0] = 131192;
                    nArray5[1] = 131193;
                    this.f[n6] = new u(this.a, this.a.l[n19], n19 % 180, n19 / 180, 7, 3, 50, nArray, nArray2, nArray3, nArray4, nArray5);
                    ++n6;
                    break;
                }
                case 64: {
                    nArray[0] = 131182;
                    nArray[1] = 131183;
                    nArray2[0] = 131184;
                    nArray2[1] = 131185;
                    nArray3[0] = 131186;
                    nArray3[1] = 131187;
                    nArray4[0] = 131188;
                    nArray4[1] = 131189;
                    nArray5[0] = 131184;
                    nArray5[1] = 131185;
                    this.g[n7] = new u(this.a, this.a.l[n19], n19 % 180, n19 / 180, 7, 4, 50, nArray, nArray2, nArray3, nArray4, nArray5);
                    ++n7;
                }
            }
            ++n19;
        }
    }

    private void o() {
        this.b[0] = 65536;
        this.b[1] = 65537;
        this.b[2] = 65538;
        this.b[3] = 65539;
        this.b[4] = 65540;
        this.b[5] = 65541;
        this.b[6] = 65542;
        this.b[7] = 65543;
        this.b[8] = 65544;
        this.b[9] = 65545;
        this.b[10] = 65546;
        this.b[11] = 65547;
        this.b[12] = 65548;
        this.b[13] = 65549;
        this.b[14] = 65550;
        this.b[15] = 65551;
        this.a.e[0] = 0;
        this.a.e[1] = 1;
        this.a.e[2] = 2;
        this.a.e[3] = 3;
        this.a.e[4] = 4;
        this.a.e[5] = 5;
        this.a.e[6] = 6;
        this.a.e[7] = 7;
        this.a.e[8] = 8;
        this.a.e[9] = 9;
        this.a.e[10] = 10;
        this.a.e[11] = 11;
        this.a.e[12] = 12;
        this.a.e[13] = 13;
        this.a.e[14] = 14;
        this.a.e[15] = 15;
    }

    private void p() {
        this.e[0] = 0;
        this.e[1] = 262144;
        this.e[2] = 262145;
        this.e[3] = 262146;
        this.e[4] = 262147;
        this.e[5] = 262148;
        this.e[6] = 262149;
        this.e[7] = 262150;
        this.e[8] = 262151;
        this.e[9] = 262152;
        this.e[10] = 262153;
        this.e[11] = 262154;
        this.e[12] = 262155;
        this.e[13] = 262156;
        this.e[14] = 262157;
        this.e[15] = 262158;
        this.e[16] = 262159;
        this.e[17] = 262160;
        this.e[18] = 262161;
        this.e[19] = 262162;
        this.e[20] = 262163;
        this.e[21] = 262164;
        this.e[22] = 262165;
        this.e[23] = 262166;
        this.e[24] = 262167;
        this.e[25] = 262168;
        this.e[26] = 262169;
        this.e[27] = 262170;
        this.e[28] = 262171;
        this.e[29] = 262172;
        this.e[30] = 262173;
        this.e[31] = 262174;
        this.e[32] = 262175;
        this.e[33] = 262176;
        this.e[34] = 262177;
        this.e[35] = 262178;
        this.e[36] = 262179;
        this.e[37] = 262180;
        this.e[38] = 262181;
        this.e[39] = 262182;
        this.e[40] = 262183;
        this.e[41] = 262184;
        this.e[42] = 262185;
        this.e[43] = 262186;
        this.e[44] = 262187;
        this.e[45] = 262188;
        this.e[46] = 262189;
        this.e[47] = 262190;
        this.e[48] = 262191;
        this.f[0] = 0;
        this.f[1] = 1;
        this.f[2] = 2;
        this.f[3] = 3;
        this.f[4] = 4;
        this.f[5] = 5;
        this.f[6] = 6;
        this.f[7] = 7;
        this.f[8] = 8;
        this.f[9] = 9;
        this.f[10] = 10;
        this.f[11] = 11;
        this.f[12] = 12;
        this.f[13] = 13;
        this.f[14] = 14;
        this.f[15] = 15;
        this.f[16] = 16;
        this.f[17] = 17;
        this.f[18] = 18;
        this.f[19] = 19;
        this.f[20] = 20;
        this.f[21] = 21;
        this.f[22] = 22;
        this.f[23] = 23;
        this.f[24] = 24;
        this.f[25] = 25;
        this.f[26] = 26;
        this.f[27] = 27;
        this.f[28] = 28;
        this.f[29] = 29;
        this.f[30] = 30;
        this.f[31] = 31;
        this.f[32] = 32;
        this.f[33] = 33;
        this.f[34] = 34;
        this.f[35] = 35;
        this.f[36] = 36;
        this.f[37] = 37;
        this.f[38] = 38;
        this.f[39] = 39;
        this.f[40] = 40;
        this.f[41] = 41;
        this.f[42] = 42;
        this.f[43] = 43;
        this.f[44] = 44;
        this.f[45] = 45;
        this.f[46] = 46;
        this.f[47] = 47;
        this.f[48] = 48;
    }

    private void q() {
        this.a.p[0] = 0;
        this.a.p[1] = 458752;
        this.a.p[2] = 458753;
        this.a.p[3] = 458754;
        this.a.p[4] = 458756;
        this.a.p[5] = 458836;
        this.a.p[6] = 458837;
        this.a.p[7] = 458758;
        this.a.p[8] = 458760;
        this.a.p[9] = 458762;
        this.a.p[10] = 458764;
        this.a.p[11] = 458766;
        this.a.p[12] = 458767;
        this.a.p[13] = 458769;
        this.a.p[14] = 458770;
        this.a.p[15] = 458771;
        this.a.p[16] = 458772;
        this.a.p[17] = 458774;
        this.a.p[18] = 458776;
        this.a.p[19] = 458777;
        this.a.p[20] = 458778;
        this.a.p[21] = 458779;
        this.a.p[22] = 458780;
        this.a.p[23] = 458781;
        this.a.p[24] = 458782;
        this.a.p[25] = 458783;
        this.a.p[26] = 458784;
        this.a.p[27] = 458785;
        this.a.p[28] = 458786;
        this.a.p[29] = 458787;
        this.a.p[30] = 458788;
        this.a.p[31] = 458789;
        this.a.p[32] = 458790;
        this.a.p[33] = 458791;
        this.a.p[34] = 458792;
        this.a.p[35] = 458793;
        this.a.p[36] = 458794;
        this.a.p[37] = 458795;
        this.a.p[38] = 458796;
        this.a.p[39] = 458797;
        this.a.p[40] = 458798;
        this.a.p[41] = 458799;
        this.a.p[42] = 458800;
        this.a.p[43] = 458802;
        this.a.p[44] = 458804;
        this.a.p[45] = 458805;
        this.a.p[46] = 458806;
        this.a.p[47] = 458807;
        this.a.p[48] = 458812;
        this.a.p[49] = 458814;
        this.a.p[50] = 458815;
        this.a.p[51] = 458817;
        this.a.p[52] = 458818;
        this.a.p[53] = 458819;
        this.a.p[54] = 458820;
        this.a.p[55] = 458821;
        this.a.p[56] = 458822;
        this.a.p[57] = 458823;
        this.a.p[58] = 458824;
        this.a.p[59] = 458825;
        this.a.p[60] = 458826;
        this.a.p[61] = 458827;
        this.a.p[62] = 458828;
        this.a.p[63] = 458829;
        this.a.p[64] = 458830;
        this.a.p[65] = 458831;
        this.a.p[66] = 458832;
        this.a.p[67] = 458833;
        this.a.p[68] = 458834;
        this.a.p[69] = 458835;
        this.a.p[70] = 458808;
        this.a.p[71] = 458809;
        this.a.p[72] = 458810;
        this.a.p[73] = 458811;
        this.a.p[74] = 458803;
        this.a.p[75] = 458757;
        this.a.p[76] = 458755;
        this.a.p[77] = 458761;
        this.a.p[78] = 458759;
        this.a.p[79] = 458763;
        this.a.p[80] = 458765;
        this.a.p[81] = 458768;
        this.a.p[82] = 458775;
        this.a.p[83] = 458801;
        this.a.p[84] = 458816;
        this.a.p[85] = 458813;
        this.a.p[86] = 458773;
        this.d[0] = 0;
        this.d[1] = 1;
        this.d[2] = 2;
        this.d[3] = 3;
        this.d[4] = 4;
        this.d[5] = 5;
        this.d[6] = 6;
        this.d[7] = 7;
        this.d[8] = 8;
        this.d[9] = 9;
        this.d[10] = 10;
        this.d[11] = 11;
        this.d[12] = 12;
        this.d[13] = 13;
        this.d[14] = 14;
        this.d[15] = 15;
        this.d[16] = 16;
        this.d[17] = 17;
        this.d[18] = 18;
        this.d[19] = 19;
        this.d[20] = 20;
        this.d[21] = 21;
        this.d[22] = 22;
        this.d[23] = 23;
        this.d[24] = 24;
        this.d[25] = 25;
        this.d[26] = 26;
        this.d[27] = 27;
        this.d[28] = 28;
        this.d[29] = 29;
        this.d[30] = 30;
        this.d[31] = 31;
        this.d[32] = 32;
        this.d[33] = 33;
        this.d[34] = 34;
        this.d[35] = 35;
        this.d[36] = 36;
        this.d[37] = 37;
        this.d[38] = 38;
        this.d[39] = 39;
        this.d[40] = 40;
        this.d[41] = 41;
        this.d[42] = 42;
        this.d[43] = 43;
        this.d[44] = 44;
        this.d[45] = 45;
        this.d[46] = 46;
        this.d[47] = 47;
        this.d[48] = 48;
        this.d[49] = 49;
        this.d[50] = 50;
        this.d[51] = 51;
        this.d[52] = 52;
        this.d[53] = 53;
        this.d[54] = 54;
        this.d[55] = 55;
        this.d[56] = 56;
        this.d[57] = 57;
        this.d[58] = 58;
        this.d[59] = 59;
        this.d[60] = 60;
        this.d[61] = 61;
        this.d[62] = 62;
        this.d[63] = 63;
        this.d[64] = 64;
        this.d[65] = 65;
        this.d[66] = 66;
        this.d[67] = 67;
        this.d[68] = 68;
        this.d[69] = 69;
        this.d[70] = 70;
        this.d[71] = 71;
        this.d[72] = 72;
        this.d[73] = 73;
        this.d[74] = 74;
        this.d[75] = 75;
        this.d[76] = 76;
        this.d[77] = 77;
        this.d[78] = 78;
        this.d[79] = 79;
        this.d[80] = 80;
        this.d[81] = 81;
        this.d[82] = 82;
        this.d[83] = 83;
        this.d[84] = 84;
        this.d[85] = 85;
        this.d[86] = 86;
    }

    private void r() {
        this.a.o[0] = 524288;
        this.a.o[1] = 524289;
        this.a.o[2] = 524290;
        this.a.o[3] = 524291;
        this.a.o[4] = 720896;
        this.a.o[5] = 720897;
        this.a.o[6] = 720898;
        this.a.o[7] = 524292;
        this.a.o[8] = 524293;
        this.a.o[9] = 524294;
        this.a.o[10] = 524295;
        this.a.o[11] = 524296;
        this.a.o[12] = 524297;
        this.a.o[13] = 524298;
        this.a.o[14] = 524299;
        this.a.o[15] = 524300;
        this.a.o[16] = 720899;
        this.a.o[17] = 720900;
        this.a.o[18] = 720901;
        this.a.o[19] = 720902;
        this.a.o[20] = 720903;
        this.a.o[21] = 720904;
        this.c[0] = 1;
        this.c[1] = 2;
        this.c[2] = 3;
        this.c[3] = 4;
        this.c[4] = 5;
        this.c[5] = 6;
        this.c[6] = 7;
        this.c[7] = 8;
        this.c[8] = 9;
        this.c[9] = 10;
        this.c[10] = 11;
        this.c[11] = 12;
        this.c[12] = 13;
        this.c[13] = 14;
        this.c[14] = 15;
        this.c[15] = 16;
        this.c[16] = 17;
        this.c[17] = 18;
        this.c[18] = 19;
        this.c[19] = 20;
        this.c[20] = 21;
        this.c[21] = 22;
    }

    public final boolean a(String string, byte[] byArray) {
        boolean bl = false;
        try {
            int n2 = 0;
            int n3 = 0;
            try {
                InputStream inputStream = this.getClass().getResourceAsStream("/" + string + ".byt");
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                n2 = dataInputStream.read();
                while (n2 != -1) {
                    byArray[n3] = (byte)n2;
                    n2 = dataInputStream.read();
                    if ((string.equals("BIGislandObjects") || string.equals("islandObjects")) && !this.a && this.a.a.a((int)byArray[n3])) {
                        byArray[n3] = 0;
                    }
                    ++n3;
                }
                inputStream.close();
                bl = true;
                if (this.a.a.a == 0L && string.equals("BIGislandObjects")) {
                    int n4 = 0;
                    while (n4 < this.a.l.length) {
                        if (this.a.l[n4] == 85 || this.a.l[n4] == 95) {
                            this.a.b = n4;
                        }
                        ++n4;
                    }
                }
            }
            catch (IOException iOException) {
                IOException iOException2 = iOException;
                iOException.printStackTrace();
            }
        }
        catch (Exception exception) {}
        return bl;
    }

    public final void f() {
        int n2 = 0;
        int n3 = 0;
        try {
            int n4 = this.a.ac - 1;
            while (n4 <= this.a.ac + this.a.O + 2) {
                int n5 = this.a.ab - 1;
                while (n5 < this.a.ab + this.a.N + 2) {
                    if (n4 >= 0 && n5 >= 0 && n4 < 180 && n5 < 180 && this.a.j[n5 + n4 * 180] != 0 && this.a.j[n5 + n4 * 180] < 50) {
                        this.a.a.a(this.a.a(), this.e[this.a.j[n5 + n4 * 180]], this.a.aM + this.a.M * n2 - this.a.M + this.a.U, this.a.aN + this.a.M * n3 - this.a.M + this.a.V);
                    }
                    ++n2;
                    ++n5;
                }
                n2 = 0;
                ++n3;
                ++n4;
            }
            return;
        }
        catch (Exception exception) {
            System.out.println("Erreur Decor " + n2 + " " + n3);
            exception.printStackTrace();
            return;
        }
    }

    public final void g() {
        int n2 = 0;
        int n3 = 0;
        try {
            int n4 = this.a.ac - 2;
            while (n4 <= this.a.ac + this.a.O + 2) {
                int n5 = this.a.ab - 2;
                while (n5 < this.a.ab + this.a.N + 2) {
                    if (n4 >= 0 && n5 >= 0 && n4 < 180 && n5 < 180 && this.a.i[n5 + n4 * 180] != 0) {
                        this.a.a.a(this.a.a(), this.a.o[this.a.i[n5 + n4 * 180] - 1], this.a.aM + (this.a.M * n2 - this.a.M * 2 + this.a.U) + this.a.M / 2, this.a.aN + (this.a.M * n3 - this.a.M * 2 + this.a.V) + this.a.M / 2);
                    }
                    ++n2;
                    ++n5;
                }
                n2 = 0;
                ++n3;
                ++n4;
            }
            return;
        }
        catch (Exception exception) {
            System.out.println("Erreur Border " + n2 + " " + n3);
            exception.printStackTrace();
            return;
        }
    }

    public final void h() {
        int n2 = 0;
        int n3 = 0;
        try {
            int n4 = this.a.ac - 1;
            while (n4 <= this.a.ac + this.a.O + 2) {
                int n5 = this.a.ab - 1;
                while (n5 <= this.a.ab + this.a.N + 2) {
                    if (n4 >= 0 && n5 >= 0 && n4 < 180 && n5 < 180) {
                        this.a.a.a(this.a.a(), this.b[this.a.k[n5 + n4 * 180]], this.a.aM + this.a.M * n2 - this.a.M + this.a.U, this.a.aN + this.a.M * n3 - this.a.M + this.a.V);
                    }
                    ++n2;
                    ++n5;
                }
                n2 = 0;
                ++n3;
                ++n4;
            }
            return;
        }
        catch (Exception exception) {
            System.out.println("Erreur Tile " + n2 + " " + n3);
            exception.printStackTrace();
            return;
        }
    }

    public final int a(int n2, int n3) {
        int n4 = n2 + n3 * 180;
        return n4;
    }

    public final byte a(int n2, int n3) {
        int n4 = n2 + n3 * 180;
        byte by = this.a.k[n4];
        return by;
    }

    public final byte b(int n2, int n3) {
        int n4 = n2 + n3 * 180;
        byte by = this.a.j[n4];
        return by;
    }

    public final byte c(int n2, int n3) {
        int n4 = n2 + n3 * 180;
        byte by = this.a.g[n4];
        return by;
    }

    public final byte d(int n2, int n3) {
        int n4 = n2 + n3 * 180;
        byte by = this.a.l[n4];
        return by;
    }

    public final boolean a(int n2, int n3) {
        this.c = true;
        switch (n3) {
            case 0: 
            case 1: 
            case 2: 
            case 3: {
                this.c = this.a.k[n2] == this.a.e[5] ? false : (this.a.k[n2] == this.a.e[6] ? false : (this.a.k[n2] == this.a.e[12] ? false : (this.a.k[n2] == this.a.e[13] ? false : this.a.k[n2] != this.a.e[14])));
            }
        }
        return this.c;
    }

    public final boolean a(int n2) {
        this.c = true;
        this.c = this.a.k[n2] == this.a.e[5] ? false : (this.a.k[n2] == this.a.e[6] ? false : (this.a.k[n2] == this.a.e[4] ? false : (this.a.k[n2] == this.a.e[12] ? false : (this.a.k[n2] == this.a.e[13] ? false : this.a.k[n2] != this.a.e[14]))));
        return this.c;
    }

    public final boolean b(int n2, int n3) {
        this.c = true;
        switch (n3) {
            case 0: 
            case 1: 
            case 2: 
            case 3: {
                if (this.a.k[n2] == this.a.e[12] || this.a.k[n2] == this.a.e[13] || this.a.k[n2] == this.a.e[14]) break;
                this.c = false;
            }
        }
        return this.c;
    }

    public final boolean c(int n2, int n3) {
        this.c = true;
        switch (n3) {
            case 0: 
            case 1: 
            case 2: 
            case 3: {
                if (this.a.g[n2] == 0) break;
                return false;
            }
        }
        return this.c;
    }

    public final boolean d(int n2, int n3) {
        this.c = true;
        switch (n3) {
            case 0: 
            case 1: 
            case 2: 
            case 3: {
                if (this.a.l[n2] == 0) break;
                return false;
            }
        }
        return this.c;
    }

    public final boolean e(int n2, int n3) {
        this.c = true;
        switch (n3) {
            case 0: 
            case 1: 
            case 2: 
            case 3: {
                this.c = this.a.j[n2] == this.f[0] ? true : (this.a.j[n2] == this.f[10] ? true : (this.a.j[n2] == this.f[11] ? true : (this.a.j[n2] == this.f[12] ? true : (this.a.j[n2] == this.f[18] ? true : (this.a.j[n2] == this.f[20] ? true : (this.a.j[n2] == this.f[22] ? true : (this.a.j[n2] == this.f[23] ? true : (this.a.j[n2] == this.f[24] ? true : (this.a.j[n2] == this.f[30] ? true : (this.a.j[n2] == this.f[31] ? true : (this.a.j[n2] == this.f[32] ? true : (this.a.j[n2] == this.f[38] ? true : (this.a.j[n2] == this.f[41] ? true : (this.a.j[n2] == this.f[42] ? true : (this.a.j[n2] == this.f[43] ? true : (this.a.j[n2] == this.f[44] ? true : (this.a.j[n2] == this.f[45] ? true : (this.a.j[n2] == this.f[46] ? true : (this.a.j[n2] == this.f[47] ? true : this.a.j[n2] >= 50)))))))))))))))))));
            }
        }
        return this.c;
    }

    public final void i() {
        int n2 = 0;
        int n3 = 0;
        try {
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            int n9 = this.a.ac - 2;
            while (n9 <= this.a.ac + this.a.O + 4) {
                n8 = this.a.ab - 2;
                while (n8 < this.a.ab + this.a.N + 4) {
                    if (n9 >= 0 && n8 >= 0 && n9 < 180 && n8 < 180 && this.a.A) {
                        if (this.a.g[n8 + n9 * 180] == 44) {
                            this.a.a.a(this.a.a(), 589827, this.a.aM + this.a.M * n2 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n3 - this.a.M * 2 + this.a.V + this.a.M / 2);
                        }
                        if (this.a.g[n8 + n9 * 180] == 0 || this.a.g[n8 + n9 * 180] == 1 || this.a.g[n8 + n9 * 180] == 5 || this.a.g[n8 + n9 * 180] == 18 || this.a.g[n8 + n9 * 180] == 13 || this.a.g[n8 + n9 * 180] == 21 || this.a.g[n8 + n9 * 180] == 22 || this.a.g[n8 + n9 * 180] == 49) {
                            this.a(n8, n9, n2, n3);
                        } else if (this.a.g[n8 + n9 * 180] != 0) {
                            this.a.a.a(this.a.a(), this.a.p[this.a.g[n8 + n9 * 180]], this.a.aM + this.a.M * n2 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n3 - this.a.M * 2 + this.a.V + this.a.M / 2);
                        }
                    }
                    if (n9 == this.a.aa && n8 == this.a.Z) {
                        n7 = n2;
                        n6 = n3;
                        n5 = this.a.aa;
                        while (n5 < this.a.aa + 10) {
                            n4 = this.a.Z - 5;
                            while (n4 < this.a.Z + 5) {
                                if (n5 >= 0 && n4 >= 0 && n5 < 180 && n4 < 180) {
                                    if (this.a.g[n4 + n5 * 180] != 0) {
                                        if (this.a.g[n4 + n5 * 180] == 0 || this.a.g[n4 + n5 * 180] == 1 || this.a.g[n4 + n5 * 180] == 5 || this.a.g[n4 + n5 * 180] == 18 || this.a.g[n4 + n5 * 180] == 13 || this.a.g[n4 + n5 * 180] == 21 || this.a.g[n4 + n5 * 180] == 22 || this.a.g[n4 + n5 * 180] == 49) {
                                            this.a(n8, n9, n7, n6);
                                            if (!this.a.T) {
                                                this.a.a.a();
                                            } else {
                                                this.a.a.b();
                                            }
                                        } else if (!this.a.T) {
                                            this.a.a.a();
                                        } else {
                                            this.a.a.b();
                                        }
                                    } else if (!this.a.T) {
                                        this.a.a.a();
                                    } else {
                                        this.a.a.b();
                                    }
                                }
                                ++n7;
                                ++n4;
                            }
                            n7 = 0;
                            ++n6;
                            ++n5;
                        }
                    }
                    if (n9 >= 0 && n8 >= 0 && n9 < 180 && n8 < 180) {
                        if (this.a.l[n8 + n9 * 180] == 51) {
                            n7 = 0;
                            while (n7 < this.a) {
                                if (this.a[n7].c == n8 && this.a[n7].d == n9) {
                                    if (this.a[n7].m == 1) break;
                                    this.a[n7].a(n2 - 1, n3 - 1);
                                    this.a[this.h][0] = 51;
                                    this.a[this.h][1] = n7;
                                    ++this.h;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 52) {
                            n7 = 0;
                            while (n7 < this.b) {
                                if (this.b[n7].c == n8 && this.b[n7].d == n9) {
                                    if (this.b[n7].m == 1) break;
                                    this.b[n7].a(n2 - 1, n3 - 1);
                                    this.a[this.h][0] = 52;
                                    this.a[this.h][1] = n7;
                                    ++this.h;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 53) {
                            n7 = 0;
                            while (n7 < this.c) {
                                if (this.c[n7].c == n8 && this.c[n7].d == n9) {
                                    if (this.c[n7].m == 1) break;
                                    this.c[n7].a(n2 - 1, n3 - 1);
                                    this.a[this.h][0] = 53;
                                    this.a[this.h][1] = n7;
                                    ++this.h;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 54) {
                            n7 = 0;
                            while (n7 < this.d) {
                                if (this.d[n7].c == n8 && this.d[n7].d == n9) {
                                    if (this.d[n7].m == 1) break;
                                    this.d[n7].a(n2 - 1, n3 - 1);
                                    this.a[this.h][0] = 54;
                                    this.a[this.h][1] = n7;
                                    ++this.h;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 55) {
                            n7 = 0;
                            while (n7 < this.e) {
                                if (this.e[n7].c == n8 && this.e[n7].d == n9) {
                                    if (this.e[n7].m == 1) break;
                                    this.e[n7].a(n2 - 1, n3 - 1);
                                    this.a[this.h][0] = 55;
                                    this.a[this.h][1] = n7;
                                    ++this.h;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 63) {
                            n7 = 0;
                            while (n7 < this.f) {
                                if (this.f[n7].c == n8 && this.f[n7].d == n9) {
                                    if (this.f[n7].m == 1) break;
                                    this.f[n7].a(n2 - 1, n3 - 1);
                                    this.a[this.h][0] = 63;
                                    this.a[this.h][1] = n7;
                                    ++this.h;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 64) {
                            n7 = 0;
                            while (n7 < this.g) {
                                if (this.g[n7].c == n8 && this.g[n7].d == n9) {
                                    if (this.g[n7].m == 1) break;
                                    this.g[n7].a(n2 - 1, n3 - 1);
                                    this.a[this.h][0] = 64;
                                    this.a[this.h][1] = n7;
                                    ++this.h;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 42) {
                            n7 = 0;
                            while (n7 < this.i) {
                                if (this.a[n7].c == n8 && this.a[n7].d == n9) {
                                    this.a[n7].a(n2 - 1, n3 - 1);
                                    this.b[this.l][0] = 42;
                                    this.b[this.l][1] = n7;
                                    ++this.l;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 48) {
                            n7 = 0;
                            while (n7 < this.j) {
                                if (this.b[n7].c == n8 && this.b[n7].d == n9) {
                                    this.b[n7].a(n2 - 1, n3 - 1);
                                    this.b[this.l][0] = 48;
                                    this.b[this.l][1] = n7;
                                    ++this.l;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 62) {
                            n7 = 0;
                            while (n7 < this.k) {
                                if (this.c[n7].c == n8 && this.c[n7].d == n9) {
                                    this.c[n7].a(n2 - 1, n3 - 1);
                                    this.b[this.l][0] = 62;
                                    this.b[this.l][1] = n7;
                                    ++this.l;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 43) {
                            n7 = 0;
                            while (n7 < this.m) {
                                if (this.a[n7].c == n8 && this.a[n7].d == n9) {
                                    this.a[n7].a(n2 - 1, n3 - 1);
                                    this.c[this.q][0] = 43;
                                    this.c[this.q][1] = n7;
                                    ++this.q;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 44) {
                            n7 = 0;
                            while (n7 < this.n) {
                                if (this.b[n7].c == n8 && this.b[n7].d == n9) {
                                    this.b[n7].a(n2 - 1, n3 - 1);
                                    this.c[this.q][0] = 44;
                                    this.c[this.q][1] = n7;
                                    ++this.q;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 49) {
                            n7 = 0;
                            while (n7 < this.o) {
                                if (this.c[n7].c == n8 && this.c[n7].d == n9) {
                                    this.c[n7].a(n2 - 1, n3 - 1);
                                    this.c[this.q][0] = 49;
                                    this.c[this.q][1] = n7;
                                    ++this.q;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 50) {
                            n7 = 0;
                            while (n7 < this.p) {
                                if (this.d[n7].c == n8 && this.d[n7].d == n9) {
                                    this.d[n7].a(n2 - 1, n3 - 1);
                                    this.c[this.q][0] = 50;
                                    this.c[this.q][1] = n7;
                                    ++this.q;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 5) {
                            n7 = 0;
                            while (n7 < this.t) {
                                if (this.a[n7].c == n8 && this.a[n7].d == n9) {
                                    this.a[n7].a(n2 - 1, n3 - 1);
                                    this.d[this.x][0] = 5;
                                    this.d[this.x][1] = n7;
                                    ++this.x;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 45) {
                            n7 = 0;
                            while (n7 < this.t) {
                                if (this.b[n7].c == n8 && this.b[n7].d == n9) {
                                    this.b[n7].a(n2 - 1, n3 - 1);
                                    this.d[this.x][0] = 45;
                                    this.d[this.x][1] = n7;
                                    ++this.x;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 46) {
                            n7 = 0;
                            while (n7 < this.v) {
                                if (this.c[n7].c == n8 && this.c[n7].d == n9) {
                                    this.c[n7].a(n2 - 1, n3 - 1);
                                    this.d[this.x][0] = 46;
                                    this.d[this.x][1] = n7;
                                    ++this.x;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 47) {
                            n7 = 0;
                            while (n7 < this.w) {
                                if (this.d[n7].c == n8 && this.d[n7].d == n9) {
                                    this.d[n7].a(n2 - 1, n3 - 1);
                                    this.d[this.x][0] = 47;
                                    this.d[this.x][1] = n7;
                                    ++this.x;
                                    break;
                                }
                                ++n7;
                            }
                        } else if (this.a.l[n8 + n9 * 180] == 56) {
                            n7 = 0;
                            while (n7 < this.r) {
                                if (this.a[n7].a == n8 && this.a[n7].b == n9) {
                                    this.a[n7].a(n2 - 1, n3 - 1);
                                    this.a[this.s] = n7;
                                    ++this.s;
                                    break;
                                }
                                ++n7;
                            }
                        }
                    }
                    ++n2;
                    ++n8;
                }
                n2 = 0;
                ++n3;
                ++n9;
            }
            if (this.a.m != 2) {
                n8 = 0;
                while (n8 < this.h) {
                    switch (this.a[n8][0]) {
                        case 51: {
                            this.a[this.a[n8][1]].a();
                            break;
                        }
                        case 52: {
                            this.b[this.a[n8][1]].a();
                            break;
                        }
                        case 53: {
                            this.c[this.a[n8][1]].a();
                            break;
                        }
                        case 54: {
                            this.d[this.a[n8][1]].a();
                            break;
                        }
                        case 55: {
                            this.e[this.a[n8][1]].a();
                            break;
                        }
                        case 63: {
                            this.f[this.a[n8][1]].a();
                            break;
                        }
                        case 64: {
                            this.g[this.a[n8][1]].a();
                        }
                    }
                    ++n8;
                }
                n7 = 0;
                while (n7 < this.l) {
                    switch (this.b[n7][0]) {
                        case 42: {
                            this.a[this.b[n7][1]].a();
                            break;
                        }
                        case 48: {
                            this.b[this.b[n7][1]].a();
                            break;
                        }
                        case 62: {
                            this.c[this.b[n7][1]].a();
                        }
                    }
                    ++n7;
                }
                n6 = 0;
                while (n6 < this.q) {
                    switch (this.c[n6][0]) {
                        case 43: {
                            this.a[this.c[n6][1]].a();
                            break;
                        }
                        case 44: {
                            this.b[this.c[n6][1]].a();
                            break;
                        }
                        case 49: {
                            this.c[this.c[n6][1]].a();
                            break;
                        }
                        case 50: {
                            this.d[this.c[n6][1]].a();
                        }
                    }
                    ++n6;
                }
                n5 = 0;
                while (n5 < this.x) {
                    switch (this.d[n5][0]) {
                        case 5: {
                            this.a[this.d[n5][1]].a();
                            break;
                        }
                        case 45: {
                            this.b[this.d[n5][1]].a();
                            break;
                        }
                        case 46: {
                            this.c[this.d[n5][1]].a();
                            break;
                        }
                        case 47: {
                            this.d[this.d[n5][1]].a();
                        }
                    }
                    ++n5;
                }
                n4 = 0;
                while (n4 < this.s) {
                    this.a[this.a[n4]].a();
                    ++n4;
                }
            }
            this.h = 0;
            this.l = 0;
            this.q = 0;
            this.x = 0;
            this.s = 0;
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    private void a(int n2, int n3, int n4, int n5) {
        if (this.a.g[n2 + n3 * 180] == this.d[18]) {
            this.a.a.a(this.a.a(), 589827, this.a.aM + this.a.M * n4 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n5 - this.a.M * 2 + this.a.V + this.a.M / 2);
            this.a.a.a(this.a.a(), this.a.p[18], this.a.aM + this.a.M * n4 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n5 - this.a.M * 2 + this.a.V + this.a.M / 2);
            if (this.a.h[n2 + n3 * 180] == 0) {
                this.a.a.a(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 1) {
                this.a.a.b(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 2) {
                this.a.a.c(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 3) {
                this.a.a.d(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 4) {
                this.a.a.e(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 5) {
                this.a.a.f(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 6) {
                this.a.a.g(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 7) {
                this.a.a.h(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 8) {
                this.a.a.i(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 9) {
                this.a.a.j(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 10) {
                this.a.a.k(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 11) {
                this.a.a.l(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 12) {
                this.a.a.m(n4, n5);
            }
        }
        if (this.a.g[n2 + n3 * 180] == this.d[1]) {
            this.a.a.a(this.a.a(), 589827, this.a.aM + this.a.M * n4 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n5 - this.a.M * 2 + this.a.V + this.a.M / 2);
            this.a.a.a(this.a.a(), this.a.p[1], this.a.aM + this.a.M * n4 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n5 - this.a.M * 2 + this.a.V + this.a.M / 2);
            if (this.a.h[n2 + n3 * 180] == 0) {
                this.a.a.n(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 1) {
                this.a.a.o(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 2) {
                this.a.a.p(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 3) {
                this.a.a.q(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 4) {
                this.a.a.r(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 5) {
                this.a.a.s(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 6) {
                this.a.a.t(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 7) {
                this.a.a.u(n4, n5);
            }
        }
        if (this.a.g[n2 + n3 * 180] == this.d[5]) {
            this.a.a.a(this.a.a(), 589827, this.a.aM + this.a.M * n4 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n5 - this.a.M * 2 + this.a.V + this.a.M / 2);
            this.a.a.a(this.a.a(), this.a.p[5], this.a.aM + this.a.M * n4 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n5 - this.a.M * 2 + this.a.V + this.a.M / 2);
            if (this.a.h[n2 + n3 * 180] == 0) {
                this.a.a.v(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 1) {
                this.a.a.w(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 2) {
                this.a.a.x(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 3) {
                this.a.a.y(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 4) {
                this.a.a.z(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 5) {
                this.a.a.A(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 6) {
                this.a.a.B(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 7) {
                this.a.a.C(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 8) {
                this.a.a.D(n4, n5);
            }
        }
        if (this.a.g[n2 + n3 * 180] == this.d[13]) {
            this.a.a.a(this.a.a(), 589827, this.a.aM + this.a.M * n4 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n5 - this.a.M * 2 + this.a.V + this.a.M / 2);
            this.a.a.a(this.a.a(), this.a.p[13], this.a.aM + this.a.M * n4 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n5 - this.a.M * 2 + this.a.V + this.a.M / 2);
            if (this.a.h[n2 + n3 * 180] == 0) {
                this.a.a.E(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 1) {
                this.a.a.F(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 2) {
                this.a.a.G(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 3) {
                this.a.a.H(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 4) {
                this.a.a.I(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 5) {
                this.a.a.J(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 6) {
                this.a.a.K(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 7) {
                this.a.a.L(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 8) {
                this.a.a.M(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 9) {
                this.a.a.N(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 10) {
                this.a.a.O(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 11) {
                this.a.a.P(n4, n5);
            }
        }
        if (this.a.g[n2 + n3 * 180] == this.d[21]) {
            this.a.a.a(this.a.a(), 589827, this.a.aM + this.a.M * n4 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n5 - this.a.M * 2 + this.a.V + this.a.M / 2);
            this.a.a.a(this.a.a(), this.a.p[21], this.a.aM + this.a.M * n4 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n5 - this.a.M * 2 + this.a.V + this.a.M / 2);
            if (this.a.h[n2 + n3 * 180] == 0) {
                this.a.a.Q(n4, n5);
            }
            if (this.a.h[n2 + n3 * 180] == 1) {
                this.a.a.R(n4, n5);
            }
            if (this.a.h[n2 + n3 * 180] == 2) {
                this.a.a.S(n4, n5);
            }
            if (this.a.h[n2 + n3 * 180] == 3) {
                this.a.a.T(n4, n5);
            }
        }
        if (this.a.g[n2 + n3 * 180] == this.d[22]) {
            this.a.a.a(this.a.a(), 589827, this.a.aM + this.a.M * n4 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n5 - this.a.M * 2 + this.a.V + this.a.M / 2);
            this.a.a.a(this.a.a(), this.a.p[22], this.a.aM + this.a.M * n4 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n5 - this.a.M * 2 + this.a.V + this.a.M / 2);
            if (this.a.h[n2 + n3 * 180] == 0) {
                this.a.a.U(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 1) {
                this.a.a.V(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 2) {
                this.a.a.V(n4, n5);
            } else if (this.a.h[n2 + n3 * 180] == 3) {
                this.a.a.V(n4, n5);
            }
        }
        if (this.a.g[n2 + n3 * 180] == this.d[49]) {
            this.a.a.a(this.a.a(), 589827, this.a.aM + this.a.M * n4 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n5 - this.a.M * 2 + this.a.V + this.a.M / 2);
            this.a.a.a(this.a.a(), this.a.p[49], this.a.aM + this.a.M * n4 - this.a.M * 2 + this.a.U + this.a.M / 2, this.a.aN + this.a.M * n5 - this.a.M * 2 + this.a.V + this.a.M / 2);
            if (this.a.h[n2 + n3 * 180] == 0) {
                this.a.a.W(n4, n5);
                return;
            }
            if (this.a.h[n2 + n3 * 180] == 1) {
                this.a.a.X(n4, n5);
                return;
            }
            if (this.a.h[n2 + n3 * 180] == 2) {
                this.a.a.Y(n4, n5);
                return;
            }
            if (this.a.h[n2 + n3 * 180] == 3) {
                this.a.a.Z(n4, n5);
                return;
            }
            if (this.a.h[n2 + n3 * 180] == 4) {
                this.a.a.aa(n4, n5);
                return;
            }
            if (this.a.h[n2 + n3 * 180] == 5) {
                this.a.a.ab(n4, n5);
            }
        }
    }

    public final void a(boolean bl) {
        if (bl) {
            int n2 = 0;
            while (n2 < 32400) {
                this.c[n2] = this.a.k[n2];
                this.d[n2] = this.a.i[n2];
                if (this.a.k[n2] == 4) {
                    this.a.k[n2] = 0;
                }
                if (this.a.k[n2] == 5) {
                    this.a.k[n2] = 4;
                }
                if (this.a.k[n2] == 6) {
                    this.a.k[n2] = 5;
                }
                if (this.a.i[n2] == 7) {
                    this.a.i[n2] = 0;
                }
                if (this.a.i[n2] == 19) {
                    this.a.i[n2] = 7;
                }
                if (this.a.i[n2] == 22) {
                    this.a.i[n2] = 19;
                }
                if (this.a.i[n2] == 6) {
                    this.a.i[n2] = 0;
                }
                if (this.a.i[n2] == 18) {
                    this.a.i[n2] = 6;
                }
                if (this.a.i[n2] == 21) {
                    this.a.i[n2] = 18;
                }
                if (this.a.i[n2] == 5) {
                    this.a.i[n2] = 0;
                }
                if (this.a.i[n2] == 17) {
                    this.a.i[n2] = 5;
                }
                if (this.a.i[n2] == 20) {
                    this.a.i[n2] = 17;
                }
                ++n2;
            }
        } else {
            int n3 = 0;
            while (n3 < 32400) {
                this.a.k[n3] = this.c[n3];
                this.a.i[n3] = this.d[n3];
                ++n3;
            }
        }
        if (!this.a.ax) {
            this.a("BIGislandObjects", this.a.l);
            this.a.a();
            this.d();
            this.a.a();
            this.a.a.b();
            this.a.a();
        }
    }

    public final void j() {
        this.a.v = null;
        this.a.aH = -1;
        this.a.b = null;
        switch (this.a.j[this.a(this.a.Z - 1, this.a.aa - 1)]) {
            case 50: {
                if (this.a.aj) break;
                this.a.c = this.a.a.a(0);
                this.a.aj = true;
                this.a.m = 9;
                this.a.aw = true;
                return;
            }
            case 51: {
                if (this.a.ak) break;
                this.a.c = this.a.a.a(1);
                this.a.ak = true;
                this.a.m = 9;
                this.a.aw = true;
                return;
            }
            case 52: {
                if (this.a.al) break;
                this.a.c = this.a.hasPointerEvents() ? new StringBuffer(this.a.a.a(2).toString() + "\\n \\n" + this.a.a.a(4).toString()) : new StringBuffer(this.a.a.a(2).toString() + "\\n \\n" + this.a.a.a(3).toString());
                this.a.al = true;
                this.a.m = 9;
                this.a.aw = true;
                this.a.v = new int[]{3, 6, 5};
                return;
            }
            case 53: {
                if (this.a.am) break;
                this.a.c = this.a.hasPointerEvents() ? new StringBuffer(this.a.a.a(5).toString() + "\\n \\n" + this.a.a.a(7).toString()) : new StringBuffer(this.a.a.a(5).toString() + "\\n \\n" + this.a.a.a(6).toString());
                this.a.am = true;
                this.a.m = 9;
                this.a.aw = true;
                this.a.v = new int[]{9, 7, 4};
                this.a.aH = 9;
                return;
            }
            case 54: {
                if (this.a.an) break;
                this.a.c = this.a.hasPointerEvents() ? new StringBuffer(this.a.a.a(8).toString() + "\\n \\n" + this.a.a.a(10).toString()) : new StringBuffer(this.a.a.a(8).toString() + "\\n \\n" + this.a.a.a(9).toString());
                this.a.an = true;
                this.a.m = 9;
                this.a.aw = true;
                this.a.v = new int[]{9, 7, 4};
                this.a.aH = 4;
                return;
            }
            case 55: {
                if (this.a.ao) break;
                this.a.c = this.a.hasPointerEvents() ? new StringBuffer(this.a.a.a(11).toString() + "\\n \\n" + this.a.a.a(13).toString()) : new StringBuffer(this.a.a.a(11).toString() + "\\n \\n" + this.a.a.a(12).toString());
                this.a.ao = true;
                this.a.m = 9;
                this.a.aw = true;
                this.a.v = new int[]{13};
                this.a.aH = 13;
                return;
            }
            case 56: {
                if (this.a.ap) break;
                this.a.c = this.a.hasPointerEvents() ? new StringBuffer(this.a.a.a(14).toString() + "\\n \\n" + this.a.a.a(16).toString()) : new StringBuffer(this.a.a.a(14).toString() + "\\n \\n" + this.a.a.a(15).toString());
                this.a.ap = true;
                this.a.m = 9;
                this.a.aw = true;
                this.a.v = new int[]{12};
                this.a.aH = 12;
                return;
            }
            case 57: {
                if (this.a.aq) break;
                this.a.c = this.a.hasPointerEvents() ? new StringBuffer(this.a.a.a(17).toString() + "\\n \\n" + this.a.a.a(19).toString()) : new StringBuffer(this.a.a.a(17).toString() + "\\n \\n" + this.a.a.a(18).toString());
                this.a.aq = true;
                this.a.m = 9;
                this.a.aw = true;
                this.a.v = new int[]{11};
                this.a.aH = 11;
                return;
            }
            case 58: {
                if (this.a.ar) break;
                this.a.c = this.a.hasPointerEvents() ? new StringBuffer(this.a.a.a(20).toString() + "\\n \\n" + this.a.a.a(22).toString()) : new StringBuffer(this.a.a.a(20).toString() + "\\n \\n" + this.a.a.a(21).toString());
                this.a.ar = true;
                this.a.m = 9;
                this.a.aw = true;
                this.a.v = new int[]{14};
                this.a.aH = 14;
                return;
            }
            case 59: {
                if (this.a.as) break;
                this.a.c = this.a.hasPointerEvents() ? new StringBuffer(this.a.a.a(23).toString() + "\\n \\n" + this.a.a.a(25).toString()) : new StringBuffer(this.a.a.a(23).toString() + "\\n \\n" + this.a.a.a(24).toString());
                this.a.as = true;
                this.a.m = 9;
                this.a.aw = true;
                this.a.v = new int[]{10, 9, 6};
                this.a.aH = 9;
                return;
            }
            case 60: {
                if (this.a.at) break;
                this.a.c = this.a.hasPointerEvents() ? new StringBuffer(this.a.a.a(26).toString() + "\\n \\n" + this.a.a.a(28).toString()) : new StringBuffer(this.a.a.a(26).toString() + "\\n \\n" + this.a.a.a(27).toString());
                this.a.at = true;
                this.a.m = 9;
                this.a.aw = true;
                this.a.v = new int[]{9, 7, 4};
                this.a.aH = 7;
                this.a.b = this.a.a.a(60);
                return;
            }
            case 61: {
                if (this.a.au) break;
                this.a.c = this.a.a.a(29);
                this.a.au = true;
                this.a.m = 9;
                this.a.aw = true;
                return;
            }
            case 62: {
                if (this.a.av) break;
                this.a.c = this.a.hasPointerEvents() ? new StringBuffer(this.a.a.a(30).toString() + "\\n \\n" + this.a.a.a(32).toString()) : new StringBuffer(this.a.a.a(30).toString() + "\\n \\n" + this.a.a.a(31).toString());
                this.a.av = true;
                this.a.m = 9;
                this.a.aw = true;
                this.a.v = new int[]{9, 7, 4};
                this.a.aH = 7;
                this.a.b = this.a.a.a(68);
            }
        }
    }
}

