/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package data;

import data.b;
import data.k;
import data.m;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class w {
    private static w a = null;
    private byte[] a;
    private short[][] a;
    private short[][] b;
    private short[][] c;
    private short[][] d;
    private short[][] e;
    private short[][] f;
    private Image[] a;
    private int[] a;
    private int a;
    private int b;
    private int c;
    private int d;
    private static int e = 0;
    public boolean a = false;

    private w() {
        this.b(0, 0, k.a(), k.b());
    }

    public static final w a() {
        if (k.a()) {
            if (a == null) {
                a = new w();
            }
            return a;
        }
        throw new b("Unauthorised operation");
    }

    public final int a() {
        return 15;
    }

    public final boolean a() {
        if (this.a[e] >= 0) {
            this.a(e, this.a[e], "");
        }
        if (++e == 15) {
            e = 0;
            return true;
        }
        return false;
    }

    public final void a(int n2) {
        byte by = this.a[n2];
        if (by >= 0) {
            this.a[by] = null;
            this.f[by] = null;
            this.e[by] = null;
            this.d[by] = null;
            this.c[by] = null;
            this.b[by] = null;
            this.a[by] = null;
            this.a[n2] = -1;
            System.gc();
        }
    }

    public final int a(int n2) {
        return this.a(n2, "");
    }

    public final int a(int n2, String string) {
        if (this.a[n2] > 0) {
            return this.a[n2];
        }
        int n3 = 1;
        while (n3 < 15) {
            if (this.a[n3] == null) {
                return this.a(n2, n3, string);
            }
            ++n3;
        }
        return this.a(n2, 0, string);
    }

    private int a(int n2, int n3, String string) {
        boolean bl = false;
        if (this.a[n2] == (byte)n3 && this.a[n3] != null) {
            return n3;
        }
        while (true) {
            int n4 = 0;
            while (n4 < 15) {
                if (this.a[n4] == n3) {
                    this.a[n4] = -1;
                }
                ++n4;
            }
            if (n2 >= 15) {
                return n3;
            }
            try {
                if (n3 == 0 || this.a[n3] == null) {
                    int n5;
                    InputStream inputStream = this.getClass().getResourceAsStream("/data/D0");
                    inputStream.skip(0L);
                    n4 = 0;
                    int n6 = 0;
                    while (n4 < n2) {
                        n6 += inputStream.read() | inputStream.read() << 8;
                        ++n4;
                    }
                    this.a[n2] = n5 = inputStream.read() | inputStream.read() << 8;
                    inputStream.skip((15 - n2 - 1) * 2 + n6 * 12);
                    this.a[n3] = new short[n5];
                    this.b[n3] = new short[n5];
                    this.c[n3] = new short[n5];
                    this.d[n3] = new short[n5];
                    this.e[n3] = new short[n5];
                    this.f[n3] = new short[n5];
                    n4 = 0;
                    while (n4 < n5) {
                        this.a[n3][n4] = (short)(inputStream.read() | inputStream.read() << 8);
                        this.b[n3][n4] = (short)(inputStream.read() | inputStream.read() << 8);
                        this.c[n3][n4] = (short)(inputStream.read() | inputStream.read() << 8);
                        this.d[n3][n4] = (short)(inputStream.read() | inputStream.read() << 8);
                        this.e[n3][n4] = (short)(inputStream.read() | inputStream.read() << 8);
                        this.f[n3][n4] = (short)(inputStream.read() | inputStream.read() << 8);
                        ++n4;
                    }
                    inputStream.close();
                }
                if (!string.equals("")) {
                    Object var9_9 = null;
                    int n7 = 0;
                    n7 = this.a(n2, (byte[])null);
                    byte[] byArray = m.a().a(string);
                    int n8 = w.a(null);
                    if (n8 != -1) {
                        n8 += 4;
                        try {
                            System.arraycopy(byArray, 0, null, n8, byArray.length);
                            this.a[n3] = Image.createImage(null, (int)0, (int)n7);
                        }
                        catch (Exception exception) {
                            this.a[n3] = Image.createImage((String)("/data/BS" + n2 + ".png"));
                        }
                    } else {
                        this.a[n3] = Image.createImage((String)("/data/BS" + n2 + ".png"));
                    }
                } else {
                    this.a[n3] = Image.createImage((String)("/data/BS" + n2 + ".png"));
                }
                this.a[n2] = (byte)n3;
            }
            catch (Exception exception) {
                continue;
            }
            break;
        }
        return n3;
    }

    public static final int a(byte[] byArray) {
        int n2 = 0;
        int n3 = 0;
        while (n3 == 0) {
            if (byArray[n2] == 80 && byArray[n2 + 1] == 76 && byArray[n2 + 2] == 84 && byArray[n2 + 3] == 69) {
                n3 = 1;
            }
            if (++n2 <= byArray.length - 4) continue;
            n3 = 2;
        }
        if (n3 == 1) {
            return n2 - 1;
        }
        return -1;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void a() {
        block16: {
            var6_1 = null;
            try {
                var6_1 = this.getClass().getResourceAsStream("/data/D0");
                var6_1.skip(0L);
                var1_2 = 0;
                if (true) ** GOTO lbl32
            }
            catch (Exception v0) {
                var9_7 = v0;
                v0.printStackTrace();
                try {
                    if (var6_1 != null) {
                        var6_1.close();
                    }
                }
                catch (IOException v1) {
                    var12_6 = v1;
                    v1.printStackTrace();
                }
                try {
                    break block16;
                }
                catch (Throwable var10_8) {
                    try {
                        if (var6_1 == null) throw var10_8;
                        var6_1.close();
                        throw var10_8;
                    }
                    catch (IOException v2) {
                        var12_6 = v2;
                        v2.printStackTrace();
                    }
                    throw var10_8;
                }
            }
            do {
                this.a[var1_2] = var6_1.read() | var6_1.read() << 8;
                ++var1_2;
lbl32:
                // 2 sources

            } while (var1_2 < 15);
            var8_3 = 0;
            var7_4 = 0;
            while (var7_4 < 15) {
                this.a[var8_3] = null;
                var4_5 = this.a[var8_3];
                this.a[var8_3] = new short[var4_5];
                this.b[var8_3] = new short[var4_5];
                this.c[var8_3] = new short[var4_5];
                this.d[var8_3] = new short[var4_5];
                this.e[var8_3] = new short[var4_5];
                this.f[var8_3] = new short[var4_5];
                var1_2 = 0;
                while (var1_2 < var4_5) {
                    this.a[var8_3][var1_2] = (short)(var6_1.read() | var6_1.read() << 8);
                    this.b[var8_3][var1_2] = (short)(var6_1.read() | var6_1.read() << 8);
                    this.c[var8_3][var1_2] = (short)(var6_1.read() | var6_1.read() << 8);
                    this.d[var8_3][var1_2] = (short)(var6_1.read() | var6_1.read() << 8);
                    this.e[var8_3][var1_2] = (short)(var6_1.read() | var6_1.read() << 8);
                    this.f[var8_3][var1_2] = (short)(var6_1.read() | var6_1.read() << 8);
                    ++var1_2;
                }
                this.a[var7_4] = (byte)var8_3;
                ++var7_4;
                ++var8_3;
            }
            try {
                if (var6_1 != null) {
                    var6_1.close();
                }
            }
            catch (IOException v3) {
                var12_6 = v3;
                v3.printStackTrace();
            }
        }
        System.gc();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final int a(int var1_1, byte[] var2_2) {
        var3_3 = null;
        var4_4 = 0;
        try {
            var3_3 = this.getClass().getResourceAsStream("/data/BS" + var1_1 + ".png");
            if (var2_2 == null) {
                var5_5 = var3_3.available();
                var2_2 = new byte[var5_5];
            }
            var5_5 = 0;
            var4_4 = 0;
            var6_7 = var3_3.read();
            if (true) ** GOTO lbl37
        }
        catch (Exception v0) {
            var5_6 = v0;
            v0.printStackTrace();
            var4_4 = 0;
            try {
                if (var3_3 != null) {
                    var3_3.close();
                }
            }
            catch (IOException v1) {
                var9_8 = v1;
                v1.printStackTrace();
            }
            try {
                return var4_4;
            }
            catch (Throwable var7_9) {
                try {
                    if (var3_3 == null) throw var7_9;
                    var3_3.close();
                    throw var7_9;
                }
                catch (IOException v2) {
                    var9_8 = v2;
                    v2.printStackTrace();
                }
                throw var7_9;
            }
        }
        do {
            var2_2[var4_4++] = (byte)var6_7;
            var6_7 = var3_3.read();
lbl37:
            // 2 sources

        } while (var6_7 >= 0);
        try {
            if (var3_3 == null) return var4_4;
            var3_3.close();
            return var4_4;
        }
        catch (IOException v3) {
            var9_8 = v3;
            v3.printStackTrace();
        }
        return var4_4;
    }

    public final void a(int n2, int n3, int n4, int n5) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
        this.d = n5;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4) {
        this.a(graphics, n2, n3, n4, -1);
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        int n6 = n2 >> 16;
        n2 &= 0xFFFF;
        int n7 = this.a[n6];
        if (n7 < 0) {
            n7 = this.a ? this.a(n6, "") : this.a(n6, 0, "");
        } else if (this.a[n7] == null) {
            try {
                this.a[n7] = Image.createImage((String)("/data/BS" + n6 + ".png"));
            }
            catch (IOException iOException) {
                IOException iOException2 = iOException;
                iOException.printStackTrace();
            }
        }
        int n8 = 0;
        if (n5 == -1) {
            if ((this.c[n7][n2] & 0x8000) != 0) {
                n8 = 2;
            }
        } else if (n5 >= 0) {
            n8 = n5;
        }
        int n9 = n3 -= this.e[n7][n2];
        int n10 = n4 -= this.f[n7][n2];
        int n11 = this.c[n7][n2] & Short.MAX_VALUE;
        int n12 = this.d[n7][n2] & Short.MAX_VALUE;
        if (n5 == 5 || n5 == 6 || n5 == 4 || n5 == 7) {
            int n13 = this.d;
            this.d = this.c;
            this.c = n13;
        }
        if (n9 + n11 > this.a + this.c) {
            n11 = this.a + this.c - n9;
        }
        if (n10 + n12 > this.b + this.d) {
            n12 = this.b + this.d - n10;
        }
        if (n9 < this.a) {
            n11 -= this.a - n9;
            n9 = this.a;
        }
        if (n10 < this.b) {
            n12 -= this.b - n10;
            n10 = this.b;
        }
        if (n11 > 0 && n12 > 0) {
            if (n8 != 0) {
                graphics.drawRegion(this.a[n7], this.a[n7][n2] + ((this.c[n7][n2] & Short.MAX_VALUE) - n9 + n3) - n11, this.b[n7][n2] + n10 - n4, n11, n12, n8, n9, n10, 0);
                return;
            }
            graphics.drawRegion(this.a[n7], this.a[n7][n2] + n9 - n3, this.b[n7][n2] + n10 - n4, n11, n12, n8, n9, n10, 0);
        }
    }

    public final Image a(int n2) {
        int n3 = n2 >> 16;
        byte by = this.a[n3];
        n2 &= 0xFFFF;
        if (this.a[by] != null) {
            return Image.createImage((Image)this.a[by], (int)this.a[by][n2], (int)this.b[by][n2], (int)(this.c[by][n2] & Short.MAX_VALUE), (int)(this.d[by][n2] & Short.MAX_VALUE), (int)0);
        }
        return null;
    }

    public final void a(Graphics graphics, Image image, int n2, int n3) {
        if (graphics != null && image != null) {
            this.a(graphics, image, n2, n3, -1);
        }
    }

    public final void a(Graphics graphics, Image image, int n2, int n3, int n4) {
        int n5 = 0;
        if (n4 >= 0 && n4 >= 0) {
            n5 = n4;
        }
        graphics.drawRegion(image, 0, 0, image.getWidth(), image.getHeight(), n5, n2, n3, 0);
    }

    private void b(int n2, int n3, int n4, int n5) {
        int n6;
        this.a(n2, n3, n4, n5);
        this.a = new byte[15];
        this.a = new short[15][];
        this.b = new short[15][];
        this.c = new short[15][];
        this.d = new short[15][];
        this.e = new short[15][];
        this.f = new short[15][];
        this.a = new Image[15];
        this.a = new int[15];
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/data/D0");
            inputStream.skip(0L);
            n6 = 0;
            while (n6 < 15) {
                this.a[n6] = inputStream.read();
                int n7 = n6++;
                this.a[n7] = this.a[n7] | inputStream.read() << 8;
            }
            inputStream.close();
        }
        catch (Exception exception) {}
        n6 = 0;
        while (n6 < 15) {
            this.a[n6++] = -1;
        }
    }
}

