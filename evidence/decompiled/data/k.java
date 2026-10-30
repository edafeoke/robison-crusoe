/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Font
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package data;

import com.hellomoto.fullscreen.game.GAMECANVAS;
import data.ac;
import data.f;
import data.w;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class k
extends GAMECANVAS
implements Runnable {
    public Graphics a;
    private boolean aQ = true;
    private boolean aR = false;
    private long L;
    private long M;
    private long N = 0L;
    private int bw = 0;
    private int bx = 0;
    private boolean aS = false;
    private boolean aT = true;
    private static k a = null;
    private static int by = -1;
    private long O = 60L;
    private boolean aU = false;
    private int bz = 0;
    private byte a = 0;
    private static Image a = null;
    private static int bA = 0;
    private static int bB = 0;
    private static int bC = 0;
    private static int bD = 0xFFFFFF;
    private static int bE = 20;
    private static int bF = 169;
    private static int bG = 200;
    private static int bH = 8;
    private static int bI = 0;
    private static int bJ = 0;
    private int bK = 0;
    private int bL = 0;
    private boolean aV = true;
    private static int bM = -1;
    private static int bN = -1;
    private static Font a;
    private StringBuffer d = null;
    private StringBuffer e = null;
    private StringBuffer f = null;
    private StringBuffer g = null;
    public static final int[] x;
    private int bO;
    private int bP;
    private int bQ;
    private int bR;
    private int[] z;
    private int[] A;
    private int bS;
    private int bT;
    private int bU = 0;
    private int bV = 0;
    private int[] B;
    public int bo = 0;
    public int bp = 0;
    public int bq = 0;
    private int bW = 0;
    public int br;
    public int bs;
    public int bt;
    public int bu;
    public int[] y;
    public int bv;

    public k() {
        super(false);
        char[][] cArrayArray = new char[][]{{' ', '0'}, {'.', ',', '-', '?', '!', '\'', ':', '1', '@', '_', '(', ')', ';', '&', '/', '%', '<', '=', '>', '\"', '\u20ac', '$', '\u00a3', '\u00a7', '\u00b0', '[', ']', '{', '}', '~', '^', '|'}, {'a', 'b', 'c', '2', '\u00e0', '\u00e2', '\u00e7', '\u00e4', 'A', 'B', 'C'}, {'d', 'e', 'f', '3', '\u00e9', '\u00e8', '\u00ea', '\u00eb', 'D', 'E', 'F'}, {'g', 'h', 'i', '4', '\u00ef', '\u00ee', 'G', 'H', 'I'}, {'j', 'k', 'l', '5', 'J', 'K', 'L'}, {'m', 'n', 'o', '6', '\u00f4', '\u00f6', 'M', 'N', 'O'}, {'p', 'q', 'r', 's', '7', 'P', 'Q', 'R', 'S'}, {'t', 'u', 'v', '8', '\u00f9', '\u00fb', '\u00fc', 'T', 'U', 'V'}, {'w', 'x', 'y', 'z', '9', 'W', 'X', 'Y', 'Z'}, {'+', '*'}, {'#', '-', '+'}};
        int[] nArray = new int[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 42, 35};
        int[] nArray2 = new int[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57};
        this.bv = 0;
        this.setFullScreenMode(true);
        a = this;
        k.b();
        k.a();
        this.f();
        this.a = 1;
    }

    public final void a(int n2) {
        System.gc();
        try {
            Thread.sleep(n2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void b(int n2) {
        this.O = 1000 / n2;
    }

    public static final boolean a() {
        if (by == -1) {
            if ("SunMicrosystems_wtk".equals(System.getProperty("microedition.platform")) && "wtk-emulator".equals(System.getProperty("device.model"))) {
                by = 1;
                return true;
            }
            by = 1;
            return true;
        }
        return by != 0;
    }

    public final Graphics a() {
        return this.a;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        int n10 = n4 - 4;
        graphics.setColor(n8);
        graphics.drawRect(n2, n3, n4 - 1, n5 - 1);
        if (n7 == 0) {
            n7 = 1;
        }
        n10 = n6 * n10 / n7;
        graphics.setColor(n9);
        graphics.fillRect(n2 + 2, n3 + 2, n10, n5 - 4);
    }

    public final void b(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        this.a(graphics, n2, n3, n4, n5, n6, n7, n8, n9);
    }

    public static final void a(Image image, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11) {
        a = image;
        bA = n2;
        bB = n3;
        bC = n4;
        bD = n5;
        bE = n6;
        bF = n7;
        bG = n8;
        bH = n9;
        bI = n10;
        bJ = n11;
    }

    public void a(Graphics graphics) {
    }

    public void a(int n2, long l2) {
    }

    public final synchronized void sizeChanged(int n2, int n3) {
        this.a = this.getGraphics();
        if (!this.aQ) {
            try {
                bM = k.a.aV ? n3 : n3 - 22;
                bN = n2;
                w.a().a(0, 0, bN, bM);
                if (this.a != null) {
                    this.a.setClip(0, 0, bN, bM);
                    this.a.setColor(bD);
                    this.a.fillRect(0, 0, n2, n3);
                }
                this.f = null;
                this.g = null;
                this.a(10, System.currentTimeMillis());
                this.a(1, System.currentTimeMillis());
                return;
            }
            catch (Exception exception) {
                System.out.println("Canvas : Exception" + exception);
            }
        }
    }

    public static final int a() {
        if (bN == -1) {
            bN = 240;
        }
        return bN;
    }

    public static final int b() {
        if (bM == -1) {
            bM = k.a.aV ? 320 : 298;
        }
        return bM;
    }

    public static final int c() {
        return 8;
    }

    public static final int d() {
        return 22;
    }

    public static final int e() {
        return 8;
    }

    public static final int f() {
        return 18;
    }

    public static final int g() {
        return 0;
    }

    public final void a(boolean bl) {
        this.aV = bl;
        bM = -1;
        bN = -1;
    }

    private String a(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            return "";
        }
        return stringBuffer.toString();
    }

    public final void a(StringBuffer stringBuffer, StringBuffer stringBuffer2) {
        if (stringBuffer == null && stringBuffer2 == null) {
            this.a(true);
        } else {
            this.a(false);
        }
        this.e = stringBuffer;
        this.d = stringBuffer2;
        if (this.a(this.d).equals(this.a(this.f))) {
            this.a(this.e).equals(this.a(this.g));
        }
    }

    private int a(int n2) {
        if (n2 == 48) {
            return 0;
        }
        if (n2 == 49) {
            return 1;
        }
        if (n2 == 50) {
            return 2;
        }
        if (n2 == 51) {
            return 3;
        }
        if (n2 == 52) {
            return 4;
        }
        if (n2 == 53) {
            return 5;
        }
        if (n2 == 54) {
            return 6;
        }
        if (n2 == 55) {
            return 7;
        }
        if (n2 == 56) {
            return 8;
        }
        if (n2 == 57) {
            return 9;
        }
        if (n2 == 42) {
            return 10;
        }
        if (n2 == 35) {
            return 11;
        }
        if (n2 == 32768) {
            return 32;
        }
        if (n2 == 32769) {
            return 33;
        }
        if (n2 == -3) {
            return 20;
        }
        if (n2 == -4) {
            return 21;
        }
        if (n2 == -1) {
            return 18;
        }
        if (n2 == -2) {
            return 19;
        }
        if (n2 == -6) {
            return 15;
        }
        if (n2 == -7) {
            return 16;
        }
        if (n2 == -10) {
            return 12;
        }
        if (n2 == 8) {
            return 14;
        }
        if (n2 == -5) {
            return 22;
        }
        int n3 = this.getGameAction(n2);
        if (n3 == 1) {
            return 23;
        }
        if (n3 == 6) {
            return 24;
        }
        if (n3 == 2) {
            return 25;
        }
        if (n3 == 5) {
            return 26;
        }
        if (n3 == 8) {
            return 27;
        }
        if (n3 == 9) {
            return 28;
        }
        if (n3 == 10) {
            return 29;
        }
        if (n3 == 11) {
            return 30;
        }
        if (n3 == 12) {
            return 31;
        }
        return 34;
    }

    private int b(int n2) {
        int n3 = 34;
        try {
            n3 = this.a(n2);
        }
        catch (Exception exception) {}
        if (n3 < 34) {
            return n3;
        }
        return 34;
    }

    public final void KEYRELEASED(int n2) {
        int n3 = this.y[this.b(n2)];
        this.d(n3);
        this.bo = 1;
        this.bv = 0;
    }

    public final void KEYPRESSED(int n2) {
        this.bo = 3;
        this.br = this.bz;
        this.bs = this.bT == n2 ? ++this.bs : 1;
        this.bT = n2;
        this.e(n2);
        int n3 = this.y[this.b(n2)];
        this.c(n3);
        this.bv = this.bT;
    }

    public final void KEYREPEATED(int n2) {
    }

    public void pointerDragged(int n2, int n3) {
    }

    public void pointerPressed(int n2, int n3) {
        this.bW = 3;
        if (n2 < k.a() >> 1 && n2 > 0 && n3 < 320 && n3 > 298 && !this.aV && this.e != null) {
            this.br = this.bz;
            this.bs = this.bT == -6 ? ++this.bs : 1;
            this.bT = -6;
            this.bv = -6;
            this.c(this.y[15]);
        }
        if (n2 < k.a() && n2 > k.a() >> 1 && n3 < 320 && n3 > 298 && !this.aV && this.d != null) {
            this.br = this.bz;
            this.bs = this.bT == -7 ? ++this.bs : 1;
            this.bT = -7;
            this.bv = -7;
            this.c(this.y[16]);
        }
    }

    public void pointerReleased(int n2, int n3) {
        this.bW = 1;
        if (n2 < k.a() >> 1 && n2 > 0 && n3 < 320 && n3 > 298 && !this.aV) {
            this.bv = 0;
            this.d(this.y[15]);
        }
        if (n2 < k.a() && n2 > k.a() >> 1 && n3 < 320 && n3 > 298 && !this.aV) {
            this.bv = 0;
            this.d(this.y[15]);
        }
    }

    private void c(int n2) {
        this.bQ |= n2;
        int n3 = this.bP++;
        this.z[n3] = this.z[n3] | n2;
        this.bP &= 0xFF;
    }

    private void d(int n2) {
        this.bQ &= -1 - n2;
        int n3 = this.bP++;
        this.A[n3] = this.A[n3] | n2;
        this.bP &= 0xFF;
    }

    private void e(int n2) {
        this.bp = n2;
        this.B[this.bU++] = n2;
        this.bU &= 0xF;
    }

    private void c() {
        if (this.bo == 1) {
            this.bo = 0;
        }
        if (this.bo == 3) {
            this.bo = 2;
        }
        if (this.bo == 0) {
            this.bp = 0;
        }
        if (this.bU != this.bV) {
            ++this.bV;
            this.bV &= 0xF;
        }
    }

    private void d() {
        if (this.bq == 1) {
            this.bq = 0;
        }
        if (this.bq == 3) {
            this.bq = 2;
        }
        if (this.bW != 0) {
            this.bq = this.bW;
            this.bW = 0;
        }
    }

    private void e() {
        int n2 = this.bP;
        boolean bl = false;
        do {
            this.bR &= -1 - this.A[this.bO];
            this.bR |= this.z[this.bO];
            this.z[this.bO] = 0;
            this.A[this.bO] = 0;
            if (this.bO != n2) {
                this.bO = this.bO + 1 & 0xFF;
            } else {
                bl = true;
            }
            this.bt = this.bR;
            this.bu = (this.bt ^ this.bS) & this.bt;
            this.bS = this.bt;
        } while (this.bu == 0 && this.bt == 0 && !bl);
    }

    public final void b() {
        this.bz = 0;
        while (this.bz < 256) {
            this.A[this.bz] = 0;
            this.z[this.bz] = 0;
            ++this.bz;
        }
        this.bs = 0;
        this.br = 0;
        this.bT = 0;
        this.bu = 0;
        this.bS = 0;
        this.bt = 0;
        this.bR = 0;
        this.bQ = 0;
        this.bP = 0;
        this.bO = 255;
    }

    public final void run() {
        this.setFullScreenMode(true);
        Graphics graphics = this.getGraphics();
        while (this.a != 0) {
            Object object;
            if (this.aS) {
                try {
                    Thread.sleep(100L);
                }
                catch (Exception exception) {
                    object = exception;
                    exception.printStackTrace();
                }
                continue;
            }
            if (!this.isShown()) continue;
            this.M = System.currentTimeMillis();
            this.b(graphics);
            if (this.aT) {
                this.flushGraphics();
            }
            this.L = System.currentTimeMillis() - this.M;
            if (this.L >= this.O) continue;
            try {
                object = this;
                synchronized (object) {
                    this.wait(this.O - this.L);
                }
                this.L = System.currentTimeMillis() - this.M;
            }
            catch (Exception exception) {}
        }
    }

    public final synchronized void hideNotify() {
        this.bL = 0;
        this.aU = true;
        this.bz = -1;
        ++this.bx;
        this.a(0, System.currentTimeMillis());
    }

    public final synchronized void showNotify() {
        this.bL = 0;
        this.bz = -1;
    }

    private void f() {
        this.bK |= 1;
        this.bK |= 1;
        this.y = x;
        this.z = new int[256];
        this.A = new int[256];
        this.b();
        this.B = new int[16];
    }

    public final synchronized void b(Graphics graphics) {
        try {
            if (k.a()) {
                if (!this.aR) {
                    ++this.bz;
                    this.a = graphics;
                    this.a.setClip(0, 0, k.a(), k.b());
                    if (this.bK != 7) {
                        if (this.bz == 0) {
                            this.a(null, null);
                        }
                        this.a.setColor(bD);
                        this.a.setClip(0, 0, k.a(), k.b());
                        this.a.fillRect(0, 0, k.a(), k.b());
                        if (this.O != 0L) {
                            this.N = this.O;
                        }
                        this.O = 0L;
                        this.bw += w.a().a();
                        this.bw += ac.a().a();
                        if ((this.bK & 2) == 0 && w.a().a()) {
                            this.bL = w.a().a() - 1;
                            this.bK |= 2;
                        }
                        if ((this.bK & 4) == 0 && ac.a().a()) {
                            this.bL = ac.a().a() - 1;
                            this.bK |= 4;
                        }
                        if (this.bK == 7) {
                            this.bL = this.bw - 1;
                            this.bz = -1;
                            this.O = this.N;
                        }
                        ++this.bL;
                        if (a != null) {
                            this.a.drawImage(a, bA, bB, bC);
                        }
                        this.b(this.a, bE, bF, bG, bH, this.bL, this.bw, bI, bJ);
                    } else {
                        int n2 = this.bx;
                        if (this.aQ) {
                            this.a(3, System.currentTimeMillis());
                            this.aQ = false;
                        }
                        if (this.aU) {
                            this.b();
                            this.a(null, null);
                            this.a.setColor(bD);
                            this.a.fillRect(0, 0, k.a(), k.b());
                            this.bz = 0;
                            this.a(1, System.currentTimeMillis());
                        }
                        this.e();
                        this.a(this.a);
                        if (!this.aV) {
                            if (this.d == null && this.e == null) {
                                this.a.setClip(0, k.b(), k.a(), k.d());
                                this.a.setColor(0x404040);
                                this.a.drawLine(0, k.b(), k.a(), k.b());
                                this.a.setColor(0xBFBFBF);
                                this.a.fillRect(0, k.b() + 1, k.a(), k.d());
                                this.a.setColor(0x404040);
                                this.a.drawLine(k.a() / 2, k.b() + 3, k.a() / 2, k.b() + k.d());
                                this.a.setColor(0);
                                a = Font.getFont((int)0, (int)0, (int)k.c());
                                this.a.setFont(a);
                            }
                            if (this.d != this.f || this.e != this.g) {
                                if (this.d != null || this.e != null) {
                                    this.a.setClip(0, k.b(), k.a(), k.d());
                                    this.a.setColor(0x404040);
                                    this.a.drawLine(0, k.b(), k.a(), k.b());
                                    this.a.setColor(0xBFBFBF);
                                    this.a.fillRect(0, k.b() + 1, k.a(), k.d());
                                    this.a.setColor(0x404040);
                                    this.a.drawLine(k.a() / 2, k.b() + 3, k.a() / 2, k.b() + k.d());
                                    this.a.setColor(0);
                                    a = Font.getFont((int)0, (int)0, (int)k.c());
                                    this.a.setFont(a);
                                }
                                if (this.d != null) {
                                    this.a.drawString(this.d.toString(), k.a() - 2, k.b() + 2, 24);
                                }
                                if (this.e != null) {
                                    this.a.drawString(this.e.toString(), 2, k.b() + 2, 20);
                                }
                                this.f = this.d;
                                this.g = this.e;
                            }
                            this.a.setClip(0, 0, k.a(), k.b());
                        }
                        this.c();
                        this.d();
                        if (n2 == this.bx) {
                            this.aU = false;
                        }
                    }
                }
            } else {
                graphics.setColor(0xFFFFFF);
                graphics.fillRect(0, 0, k.a(), k.b());
                graphics.setColor(0xFF0000);
                graphics.drawString("*UNAUTHORIZED*", 0, 2 * k.f(), 20);
            }
            return;
        }
        catch (f f2) {
            this.bK = 7;
            this.a(9, System.currentTimeMillis());
            System.gc();
            return;
        }
        catch (Exception exception) {
            this.aR = true;
            this.a(6, System.currentTimeMillis());
            System.out.println("Canvas : Uncaught Exception : " + exception + "\n --message : " + exception.getMessage());
            exception.printStackTrace();
            return;
        }
    }

    static {
        x = new int[]{32, 5, 4, 6, 1, 16, 2, 9, 8, 10, 16, 128, 16, 128, 128, 16, 128, 80, 4, 8, 1, 2, 16, 4, 8, 1, 2, 80, 16, 16, 16, 16, 16, 128, 256};
    }
}

