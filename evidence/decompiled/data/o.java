/*
 * Decompiled with CFR 0.152.
 */
package data;

import java.util.Random;

public final class o {
    public static Random a = new Random();

    public static final int a(int n2) {
        try {
            return (a.nextInt() << 1 >>> 1) % n2;
        }
        catch (Exception exception) {
            return 0;
        }
    }
}

