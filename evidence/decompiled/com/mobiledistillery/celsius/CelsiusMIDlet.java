/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Display
 *  javax.microedition.midlet.MIDlet
 */
package com.mobiledistillery.celsius;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public abstract class CelsiusMIDlet
extends MIDlet {
    public CelsiusMIDlet() {
        Display.getDisplay((MIDlet)this);
    }

    public void startApp() {
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean bl) {
        this.notifyDestroyed();
    }
}

