/*
 * Decompiled with CFR 0.152.
 */
package data;

public final class z
extends Exception {
    public z() {
        System.out.println("CelsiusException: " + this.getMessage());
    }

    public z(String string) {
        super(string);
        System.out.println("CelsiusException: " + string);
    }
}

