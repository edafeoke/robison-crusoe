/*
 * Decompiled with CFR 0.152.
 */
package data;

import data.b;
import data.k;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public final class m {
    private static m a = null;

    private m() {
    }

    public static final m a() {
        if (k.a()) {
            if (a == null) {
                a = new m();
            }
            return a;
        }
        throw new b("Unauthorised operation");
    }

    /*
     * Loose catch block
     */
    public final byte[] a(String string) {
        byte[] byArray;
        block23: {
            boolean bl = false;
            byte[] byArray2 = new byte[1024];
            byArray = null;
            InputStream inputStream = null;
            ByteArrayOutputStream byteArrayOutputStream = null;
            inputStream = this.getClass().getResourceAsStream("/" + string);
            byteArrayOutputStream = new ByteArrayOutputStream();
            int n2 = 0;
            while (n2 >= 0) {
                n2 = inputStream.read(byArray2, 0, 1024);
                if (n2 <= 0) continue;
                byteArrayOutputStream.write(byArray2, 0, n2);
            }
            byArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            }
            catch (Exception exception) {}
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            catch (Exception exception) {}
            break block23;
            catch (Exception exception) {}
            {
                try {
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                }
                catch (Exception exception) {}
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
                catch (Exception exception) {}
                try {
                }
                catch (Throwable throwable) {
                    try {
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                    }
                    catch (Exception exception) {}
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                    catch (Exception exception) {}
                    throw throwable;
                }
            }
        }
        return byArray;
    }
}

