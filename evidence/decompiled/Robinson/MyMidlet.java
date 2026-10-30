/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.midlet.MIDlet
 */
package Robinson;

import com.mobiledistillery.celsius.CelsiusMIDlet;
import data.p;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

public class MyMidlet
extends CelsiusMIDlet {
    private p a = new p(this);

    public MyMidlet() {
        new Thread(this.a).start();
    }

    public final void startApp() {
        Display.getDisplay((MIDlet)this).setCurrent((Displayable)this.a);
    }

    public final void pauseApp() {
    }

    public final void destroyApp(boolean bl) {
        this.notifyDestroyed();
    }
}

