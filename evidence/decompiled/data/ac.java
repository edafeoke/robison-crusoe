/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Font
 *  javax.microedition.lcdui.Graphics
 */
package data;

import data.b;
import data.k;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ac {
    private static ac a = null;
    private byte a;
    private byte[] a;
    private StringBuffer[][] a;
    private int a;
    public boolean a;
    private boolean b;
    public int[] a;
    private Vector a = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private Vector b;
    private Vector c;
    private Vector d;
    private int b = true;
    private int c;
    private int d;
    private boolean c;
    private static int e;
    public static StringBuffer[] a;

    private ac() {
        this.b();
    }

    public static final ac a() {
        if (k.a()) {
            if (a == null) {
                a = new ac();
            }
            return a;
        }
        throw new b("Unauthorised operation");
    }

    public final void a(int n2) {
        if (this.a != n2) {
            this.a(true);
            this.a = (byte)n2;
            this.a();
        }
    }

    public final int a() {
        return 4;
    }

    public final boolean a() {
        if (this.a[this.a] != -1) {
            this.a(this.a, (int)this.a[this.a]);
        }
        ++this.a;
        if (this.a == 4) {
            this.a = 0;
            return true;
        }
        return false;
    }

    private void a() {
        int n2 = 0;
        while (n2 < 4) {
            if (this.a[n2] != -1) {
                this.a(n2, (int)this.a[n2]);
            }
            ++n2;
        }
    }

    public final void a(boolean bl) {
        if (this.a == null) {
            return;
        }
        int n2 = 0;
        while (n2 < 4) {
            if (!bl) {
                this.a[n2] = -1;
            }
            if (this.a != null && this.a[n2] != null) {
                int n3 = 0;
                while (n3 < this.a[n2].length) {
                    this.a[n2][n3] = null;
                    ++n3;
                }
                this.a[n2] = null;
            }
            ++n2;
        }
        this.a = null;
        System.gc();
    }

    public final void b(int n2) {
        byte by = this.a[n2];
        if (by != -1) {
            int n3 = 0;
            while (n3 < this.a[by].length) {
                this.a[by][n3] = null;
                ++n3;
            }
            this.a[by] = null;
            this.a[n2] = -1;
        }
        System.gc();
    }

    public final StringBuffer a(int n2) {
        int n3 = n2 >> 16;
        int n4 = this.a[n3];
        if (n4 < 0) {
            n4 = this.a ? this.a(n3) : this.a(n3, 0);
        }
        return this.a[n4][n2 & 0xFFFF];
    }

    public final int a(int n2) {
        if (this.a[n2] > 0) {
            return this.a[n2];
        }
        int n3 = 1;
        while (n3 < 4) {
            if (this.a == null || this.a[n3] == null) {
                return this.a(n2, n3);
            }
            ++n3;
        }
        return this.a(n2, 0);
    }

    private int a(int n2, int n3) {
        if (this.a == null) {
            this.a = new StringBuffer[4][];
        }
        int n4 = 0;
        while (n4 < 4) {
            if (this.a[n4] == n3) {
                this.a[n4] = -1;
            }
            ++n4;
        }
        try {
            int n5 = 9366;
            int n6 = 0;
            int n7 = 5;
            InputStream inputStream = this.getClass().getResourceAsStream("/data/D0");
            inputStream.skip(80048L);
            if (n2 == -2) {
                n6 = inputStream.read();
            } else {
                inputStream.skip(1 + 27 * n2);
                n7 = inputStream.read() | inputStream.read() << 8;
                inputStream.skip(5 * this.a);
                n6 = inputStream.read();
                n5 = inputStream.read() | inputStream.read() << 8 | inputStream.read() << 16 | inputStream.read() << 24;
            }
            inputStream.close();
            inputStream = this.getClass().getResourceAsStream("/data/D" + Integer.toString(n6));
            inputStream.skip(n5);
            this.a[n3] = new StringBuffer[n7];
            n4 = 0;
            while (n4 < n7) {
                int n8;
                StringBuffer stringBuffer = new StringBuffer();
                while ((n8 = inputStream.read()) != 0) {
                    int n9 = n8 & 0x7F;
                    if ((n8 & 0x80) != 0) {
                        n8 = inputStream.read();
                        n9 |= (n8 & 0x7F) << 7;
                        if ((n8 & 0x80) != 0) {
                            n9 |= inputStream.read() << 14;
                        }
                    }
                    stringBuffer.append((char)n9);
                }
                this.a[n3][n4] = stringBuffer;
                ++n4;
            }
            inputStream.close();
        }
        catch (Exception exception) {}
        if (n2 >= 0) {
            this.a[n2] = (byte)n3;
        }
        return n3;
    }

    private String a(Vector vector, int n2) {
        return (String)vector.elementAt(n2);
    }

    private int a(Vector vector, int n2) {
        return (Integer)vector.elementAt(n2);
    }

    private void a(Vector vector, String string, int n2) {
        vector.setElementAt(string, n2);
    }

    private void a(Vector vector, int n2, int n3) {
        vector.setElementAt(new Integer(n2), n3);
    }

    private void a(String string, Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        if (graphics == null) {
            return;
        }
        if ((n4 & 0x100) != 0) {
            graphics.setColor(n6);
            this.a(string, graphics, n2, n3, n4 & 0x7F);
            graphics.setColor(n5);
        }
        graphics.drawString(string, n2, n3, n4 & 0x7F);
    }

    private void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        if (graphics == null) {
            return;
        }
        graphics.setColor(n5);
        graphics.setFont(Font.getFont((int)n2, (int)n3, (int)n4));
    }

    private boolean a(String string, int n2, int n3, int n4, int n5) {
        this.c = false;
        if (string == null) {
            return false;
        }
        if (string.length() == 0) {
            return false;
        }
        int n6 = this.a(string, n3, n4, n5);
        boolean bl = false;
        if (n6 + this.d > this.c) {
            int n7;
            String string2;
            bl = true;
            if (string.endsWith(" ") && this.d + this.a(string.substring(0, string.length() - 1), n3, n4, n5) <= this.c) {
                if (string.length() > 1) {
                    string = string.substring(0, string.length() - 1);
                    bl = false;
                } else {
                    return false;
                }
            }
            if (bl && this.a.size() > 0 && (string2 = this.a(this.a, n7 = this.a.size() - 1)).endsWith(" ") && this.a(this.d, n7) <= this.c) {
                string2 = string2.substring(0, string2.length() - 1);
                this.d -= this.a(this.d, n7);
                this.a(this.a, string2, n7);
                this.a(this.d, this.a(string2, n3, this.a(this.c, n7), n5), n7);
                this.d += this.a(this.d, n7);
                --this.b;
                this.c = true;
            }
        }
        if (string.endsWith(" ")) {
            ++this.b;
        }
        this.a.addElement(string);
        this.b.addElement(new Integer(n2));
        this.c.addElement(new Integer(n4));
        this.d.addElement(new Integer(this.a(string, n3, n4, n5)));
        return bl;
    }

    private int a(String string, int n2, int n3, int n4) {
        int n5 = Font.getFont((int)n2, (int)n3, (int)n4).stringWidth(string) + string.length() * k.g();
        return n5;
    }

    private int a(char c2, char c3) {
        if (c3 == '%') {
            if (c2 == 'c') {
                return -1;
            }
            if (c2 == 'u') {
                return -4;
            }
            if (c2 == 'b') {
                return -1;
            }
            if (c2 == 'i') {
                return -2;
            }
        } else {
            if (c2 == 'c' && c3 >= '0' && c3 <= '9') {
                return this.a[c3 - 48];
            }
            if (c2 == 'u') {
                return 4;
            }
            if (c2 == 'b') {
                return 1;
            }
            if (c2 == 'i') {
                return 2;
            }
        }
        return -999;
    }

    public static final int b() {
        return e;
    }

    public static final void c(int n2) {
        e = n2;
    }

    private void a(String string, Graphics graphics, int n2, int n3, int n4) {
        int n5 = -1;
        while (n5 < 2) {
            int n6 = -1;
            while (n6 < 2) {
                graphics.drawString(string, n2 + n5, n3 + n6, n4);
                ++n6;
            }
            ++n5;
        }
    }

    public final int a(Graphics graphics, StringBuffer stringBuffer, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12, int n13) {
        int n14;
        int n15;
        int n16;
        int n17;
        if (stringBuffer == null) {
            return n3;
        }
        if ((n5 & 0x7F) == 0) {
            n5 = n5 | 0x10 | 4;
        }
        if ((n5 & 2) != 0) {
            if ((n5 & 0x200) == 0) {
                n5 |= 0x200;
            }
            n5 &= 0xFFFFFFFD;
            n5 |= 0x10;
        }
        if ((n5 & 0x200) != 0) {
            if ((n5 & 1) != 0) {
                n5 = n5 & 0xFFFFFF80 | 0x10 | 1;
            } else if ((n5 & 8) != 0) {
                n5 = n5 & 0xFFFFFF80 | 0x10 | 8;
            }
        }
        if ((n5 & 0x800) != 0) {
            if ((n5 & 0x200) != 0) {
                n3 -= n9 / 2;
            }
            this.a(graphics, n6, n7, n8, n4);
            this.a(stringBuffer.toString(), graphics, n2, n3, n5, n4, n12);
            return n3 + n9;
        }
        this.a = new Vector();
        this.b = new Vector();
        this.c = new Vector();
        this.d = new Vector();
        this.d = 0;
        this.c = n10;
        this.b = 0;
        String string = stringBuffer.toString();
        String string2 = "";
        int n18 = n4;
        int n19 = n7;
        int n20 = n3;
        int n21 = 1;
        int n22 = 0;
        int n23 = 0;
        int n24 = 0;
        int n25 = 0;
        int n26 = 0;
        int n27 = 0;
        int n28 = 0;
        Vector<Integer> vector = new Vector<Integer>();
        n24 = string.indexOf("\\&");
        while (n24 != -1) {
            int n29 = 0;
            n17 = string.charAt(n24 + 2);
            if (n17 >= 48 && n17 <= 57 && (n29 = n17 - 48) < a.length) {
                string = a[n29] != null ? string.substring(0, n24) + a[n29].toString() + string.substring(n24 + 3) : string.substring(0, n24) + "***ISSUE WITH TEXT PARAM***" + string.substring(n24 + 3);
            }
            n24 = string.indexOf("\\&", n24 + 2);
        }
        if ((n5 & 0x400) != 0 && e > 0) {
            if (graphics != null) {
                String string3 = e < 0 || e > string.length() ? "***ISSUE WITH DRAWSTRINGOFFSET***" : string.substring(0, e);
                n17 = string3.indexOf("\\");
                while (n17 > -1) {
                    char c2 = string3.charAt(n17 + 1);
                    n16 = c2;
                    if ((c2 == 'c' || n16 == 117 || n16 == 105 || n16 == 98) && (n15 = this.a((char)n16, string3.charAt(n17 + 2))) != -999) {
                        if (n16 == 99) {
                            n18 = n15 == -1 ? n4 : n15;
                        } else if (n15 < 0 && (n19 & -n15) != 0) {
                            n19 &= ~(-n15);
                        } else if (n15 >= 0 && (n19 & n15) == 0) {
                            n19 |= n15;
                        }
                    }
                    n17 = string3.indexOf("\\", n17 + 3);
                }
            }
            string = e < 0 || e > string.length() ? "***ISSUE WITH DRAWSTRINGOFFSET***" : string.substring(e);
        }
        this.a(graphics, n6, n19, n8, n18);
        if ((n5 & 0x1000) != 0 && (n5 & 0x7F) != 0) {
            if ((n5 & 1) != 0) {
                n2 -= n10 / 2;
                n5 &= 0xFFFFFFFE;
                n5 |= 4;
            } else if ((n5 & 8) != 0) {
                n2 -= n10;
                n5 &= 0xFFFFFFF7;
                n5 |= 4;
            }
        }
        while (n23 < string.length() || this.a.size() > n28) {
            int n30;
            int n31;
            char c3 = 'x';
            n17 = 0;
            n16 = -1;
            n15 = 0;
            n14 = 0;
            if (n23 < string.length()) {
                n22 = string.indexOf(" ", n23);
                if (n22 == -1) {
                    n14 = 1;
                    n22 = string.length() - 1;
                }
                if ((n24 = (string2 = string.substring(n23, n22 + 1)).indexOf("\\")) + 1 >= string2.length()) {
                    n24 = -1;
                    n14 = 1;
                }
            } else {
                n24 = -2;
                n14 = 1;
            }
            if (n24 == -1) {
                n25 = 0;
                n23 += string2.length();
                boolean bl = this.a(string2, n18, n6, n19, n8);
                n15 = bl ? 1 : 0;
                if (!bl) {
                    this.d += this.a(this.d, this.d.size() - 1);
                }
            } else {
                n25 = 0;
                n26 = 0;
                while (n24 > -1) {
                    c3 = string2.charAt(n24 + 1);
                    n31 = this.a.size();
                    if (c3 == 'c' || c3 == 'u' || c3 == 'i' || c3 == 'b') {
                        n30 = this.a(c3, string2.charAt(n24 + 2));
                        if (n30 != -999) {
                            if (n24 > 0) {
                                n15 = this.a(string2.substring(n17, n24), n18, n6, n19, n8) ? 1 : 0;
                            }
                            if (c3 == 'c') {
                                n18 = n30 == -1 ? n4 : n30;
                                n25 = 3;
                            } else {
                                if (n30 < 0 && (n19 & -n30) != 0) {
                                    n19 &= ~(-n30);
                                } else if (n30 >= 0 && (n19 & n30) == 0) {
                                    n19 |= n30;
                                }
                                n25 = n30 < 0 ? 3 : 2;
                            }
                        }
                    } else if (c3 == '@' || c3 == 'n' || c3 == 'p') {
                        boolean bl = this.a(string2.substring(n17, n24), n18, n6, n19, n8);
                        n15 = bl ? 1 : 0;
                        if (!bl) {
                            n16 = n24;
                            n25 = 2;
                        } else {
                            n16 = -1;
                            n25 = 0;
                        }
                    } else if (c3 == '\u00a7') {
                        if (string2.substring(n24 + 2).indexOf("\\") == -1) {
                            n15 = this.a(string2.substring(n17, n24) + " " + string2.substring(n24 + 2), n18, n6, n19, n8) ? 1 : 0;
                            n25 = 2;
                            --n23;
                        } else {
                            string2 = string2.substring(n17, n24) + " " + string2.substring(n24 + 2);
                            n25 = 1;
                            n24 = -1;
                        }
                        ++n26;
                    } else if (c3 == 't') {
                        n15 = this.a(string2.substring(n17, n24) + "   ", n18, n6, n19, n8) ? 1 : 0;
                        n25 = 2;
                        n23 -= "   ".length();
                    } else if (string2.substring(n24 + 1).indexOf("\\") == -1) {
                        n15 = this.a(string2.substring(n17), n18, n6, n19, n8) ? 1 : 0;
                        n25 = 0;
                        ++n24;
                    } else {
                        n25 = 0;
                        ++n24;
                    }
                    n17 = n24 + n25;
                    n24 += n25;
                    n23 += n25;
                    n24 = string2.indexOf("\\", n24);
                    if (n14 != 0 && n24 == -1 && n17 < string2.length()) {
                        n14 = 0;
                    }
                    if (this.a.size() > n31) {
                        n23 += ((String)this.a.lastElement()).length();
                        if (n15 == 0) {
                            this.d += ((Integer)this.d.lastElement()).intValue();
                        }
                    }
                    if (n15 != 0 || n16 > -1) break;
                }
            }
            if (n15 == 0 && n14 == 0 && n16 <= -1) continue;
            if (this.a.size() > n28) {
                int n32;
                int n33;
                if (this.a(this.d, n28) > n10) {
                    String string4 = this.a(this.a, n28);
                    n30 = this.a(this.d, n28);
                    n33 = this.a(this.c, n28);
                    n32 = string4.length();
                    while (n30 > n10) {
                        if (n32 > 1) {
                            --n32;
                        }
                        n30 = this.a(string4.substring(0, n32), n6, n33, n8);
                    }
                    this.a(this.a, string4.substring(0, n32), n28);
                    this.a(this.d, n30, n28);
                    this.a.insertElementAt(string4.substring(n32), n28 + 1);
                    this.b.insertElementAt(new Integer(this.a(this.b, n28)), n28 + 1);
                    this.c.insertElementAt(new Integer(n33), n28 + 1);
                    this.d.insertElementAt(new Integer(this.a(string4.substring(n32), n6, n33, n8)), n28 + 1);
                    this.d = n30;
                    n27 = n28 + 1;
                    if (n16 > -1) {
                        n23 -= 2;
                    }
                } else if (n15 != 0) {
                    n27 = this.a.size() - 1;
                    if ((n5 & 0x1000) != 0 && this.a(this.a, this.a.size() - 1).endsWith(" ")) {
                        --this.b;
                    }
                } else {
                    n27 = this.a.size();
                }
                n31 = n2;
                n30 = n5;
                if (n5 != 0) {
                    if (this.b) {
                        if ((n5 & 1) != 0) {
                            n31 = n2 - this.d / 2;
                            n30 = n5 & 0xFFFFFFFE;
                            n30 |= 4;
                        } else if ((n5 & 8) != 0) {
                            n31 = n2 - this.d;
                            n30 = n5 & 0xFFFFFFF7;
                            n30 |= 4;
                        }
                    } else if ((n5 & 1) != 0) {
                        n31 = n2 + this.d / 2;
                        n30 = n5 & 0xFFFFFFFE;
                        n30 |= 8;
                    } else if ((n5 & 4) != 0) {
                        n31 = n2 + this.d;
                        n30 = n5 & 0xFFFFFFFB;
                        n30 |= 8;
                    } else if ((n5 & 8) != 0) {
                        n31 = n2 + this.d;
                    }
                }
                n33 = n10 - this.d;
                n32 = 0;
                int n34 = 0;
                int n35 = 0;
                if ((n5 & 0x1000) != 0 && n16 == -1 && n33 < n10 / 3 && this.b > 0) {
                    n32 = n33 / this.b;
                    n34 = n33 - n32 * this.b;
                    n35 = n34 / this.b + 1;
                }
                int n36 = n28;
                int n37 = n28;
                while (n37 < n27) {
                    if ((n5 & 0x200) == 0) {
                        int n38 = this.a(this.b, n28);
                        this.a(graphics, n6, this.a(this.c, n28), n8, n38);
                        this.a(this.a(this.a, n28), graphics, n31, n20, n30, n38, n12);
                    } else {
                        vector.addElement(new Integer(n31));
                        n36 = n37;
                    }
                    n31 = this.b ? (n31 += this.a(this.d, n36)) : (n31 -= this.a(this.d, n36));
                    if ((n5 & 0x1000) != 0 && this.a(this.a, n36).endsWith(" ")) {
                        n31 += n32;
                        if (n34 > 0) {
                            n31 += n35;
                            if ((n34 -= n35) < 0) {
                                n34 = 0;
                                n35 = 0;
                            }
                        }
                    }
                    if ((n5 & 0x200) == 0) {
                        this.a.removeElementAt(n28);
                        this.d.removeElementAt(n28);
                        this.b.removeElementAt(n28);
                        this.c.removeElementAt(n28);
                    }
                    ++n37;
                }
                n28 = (n5 & 0x200) == 0 ? 0 : n27;
                this.d = 0;
                this.b = 0;
                if (this.a.size() > n28) {
                    int n39 = n28;
                    while (n39 < this.a.size()) {
                        this.d += this.a(this.d, n39);
                        if ((n5 & 0x1000) != 0 && this.a(this.a, n39).endsWith(" ")) {
                            ++this.b;
                        }
                        ++n39;
                    }
                }
            }
            n20 += n9;
            if (c3 == 'p' && n16 < n27 && n16 > -1) {
                while (n20 < n3 + n11 * n21) {
                    n20 += n9;
                }
                ++n21;
            }
            if ((n5 & 0x400) != 0 && n20 + n9 > n3 + n11 && graphics != null) break;
        }
        if ((n5 & 0x200) != 0 && vector.size() > 0) {
            int n40 = 0;
            n17 = this.a(vector, 0);
            n15 = n3 - (n20 - n3) / 2;
            n14 = 0;
            while (n14 < vector.size()) {
                n16 = this.a(this.b, n14);
                this.a(graphics, n6, this.a(this.c, n14), n8, n16);
                this.a(this.a(this.a, n14), graphics, n17, n15, n5 & 0xFFFFFD80, n16, n12);
                n40 = n17;
                if (n14 + 1 < vector.size()) {
                    n17 = this.a(vector, n14 + 1);
                }
                if (n17 < n40 + this.a(this.d, n14)) {
                    n15 += n9;
                }
                ++n14;
            }
            n20 = n15;
        }
        if ((n5 & 0x400) != 0) {
            if (this.a.size() > n28) {
                int n41 = n28;
                while (n41 < this.a.size()) {
                    n23 -= this.a(this.a, n41).length();
                    ++n41;
                }
                n23 -= n25;
                n23 -= n26;
            }
            e = n23 < string.length() ? (e += n23) : 0;
        }
        this.a = null;
        this.c = null;
        this.b = null;
        this.d = null;
        return n20;
    }

    public final int a(Graphics graphics, StringBuffer stringBuffer, int n2, int n3, int n4) {
        return this.a(graphics, stringBuffer, n2, n3, n4, 20, 0, 0, k.e(), k.f(), k.a(), k.b(), 0, 0);
    }

    private void b() {
        this.a = new byte[4];
        this.a = 0;
        int n2 = 0;
        while (n2 < 4) {
            this.a[n2++] = -1;
        }
        a = new StringBuffer[10];
    }
}

