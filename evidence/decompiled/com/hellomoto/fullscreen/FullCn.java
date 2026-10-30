/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.game.GameCanvas
 */
package com.hellomoto.fullscreen;

import javax.microedition.lcdui.game.GameCanvas;

public abstract class FullCn
extends GameCanvas {
    boolean setFullScreen;
    int[] codes;
    int[] CodeFrom;
    int[] CodeTo;
    int n;

    protected FullCn() {
        super(false);
        this.Init();
    }

    protected FullCn(boolean flag) {
        super(flag);
        this.Init();
    }

    private void Init() {
        this.setFullScreen = true;
        this.n = 5;
        this.codes = new int[13];
        this.CodeFrom = new int[]{0, 0, -30, -31, -23, -202, -202, -202, -202, -202, -202, -202, -202, -202, -202, -202, -202, -202, -202, -202, -202};
        this.CodeTo = new int[]{-6, -7, 42, 35, 0, -202, -202, -202, -202, -202, -202, -202, -202, -202, -202, -202, -202, -202, -202, -202, -202};
        this.setFullScreenMode(this.setFullScreen);
        for (int i2 = 0; i2 < 13; ++i2) {
            this.codes[i2] = i2 != 0 && i2 != 3 && i2 != 4 && i2 != 7 ? this.keyCodeConvert(super.getKeyCode(i2)) : 0;
        }
    }

    private int keyCodeConvert(int i2) {
        for (int j2 = 0; j2 < this.n; ++j2) {
            if (this.CodeFrom[j2] != i2) continue;
            return this.CodeTo[j2];
        }
        return i2;
    }

    protected void keyPressed(int i2) {
        this.KEYPRESSED(this.keyCodeConvert(i2));
    }

    protected void keyReleased(int i2) {
        this.KEYRELEASED(this.keyCodeConvert(i2));
    }

    protected void keyRepeated(int i2) {
        this.KEYREPEATED(this.keyCodeConvert(i2));
    }

    protected void KEYPRESSED(int i2) {
    }

    protected void KEYRELEASED(int i2) {
    }

    protected void KEYREPEATED(int i2) {
    }

    protected void pointerPressed(int x2, int y2) {
        int screenW = this.getWidth();
        int screenH = this.getHeight();
        if (y2 < screenH / 4) {
            if (x2 < screenW / 3) {
                this.KEYPRESSED(49);
            } else if (x2 < screenW / 3 * 2) {
                this.KEYPRESSED(50);
            } else {
                this.KEYPRESSED(51);
            }
        } else if (y2 < screenH / 4 * 2) {
            if (x2 < screenW / 3) {
                this.KEYPRESSED(52);
            } else if (x2 < screenW / 3 * 2) {
                this.KEYPRESSED(53);
            } else {
                this.KEYPRESSED(54);
            }
        } else if (y2 < screenH / 4 * 3) {
            if (x2 < screenW / 3) {
                this.KEYPRESSED(55);
            } else if (x2 < screenW / 3 * 2) {
                this.KEYPRESSED(56);
            } else {
                this.KEYPRESSED(57);
            }
        } else if (x2 < screenW / 3) {
            this.KEYPRESSED(this.CodeTo[0]);
        } else if (x2 < screenW / 3 * 2) {
            this.KEYPRESSED(48);
        } else {
            this.KEYPRESSED(this.CodeTo[1]);
        }
    }

    protected void pointerReleased(int x2, int y2) {
        int screenW = this.getWidth();
        int screenH = this.getHeight();
        if (y2 < screenH / 4) {
            if (x2 < screenW / 3) {
                this.KEYRELEASED(49);
            } else if (x2 < screenW / 3 * 2) {
                this.KEYRELEASED(50);
            } else {
                this.KEYRELEASED(51);
            }
        } else if (y2 < screenH / 4 * 2) {
            if (x2 < screenW / 3) {
                this.KEYRELEASED(52);
            } else if (x2 < screenW / 3 * 2) {
                this.KEYRELEASED(53);
            } else {
                this.KEYRELEASED(54);
            }
        } else if (y2 < screenH / 4 * 3) {
            if (x2 < screenW / 3) {
                this.KEYRELEASED(55);
            } else if (x2 < screenW / 3 * 2) {
                this.KEYRELEASED(56);
            } else {
                this.KEYRELEASED(57);
            }
        } else if (x2 < screenW / 3) {
            this.KEYRELEASED(this.CodeTo[0]);
        } else if (x2 < screenW / 3 * 2) {
            this.KEYRELEASED(48);
        } else {
            this.KEYRELEASED(this.CodeTo[1]);
        }
    }

    public int getGameAction(int i2) {
        boolean flag = false;
        for (int k2 = 0; k2 < this.n; ++k2) {
            if (this.CodeTo[k2] != i2) continue;
            flag = true;
            int j2 = super.getGameAction(this.CodeFrom[k2]);
            if (j2 == 0) continue;
            return j2;
        }
        return flag ? 0 : super.getGameAction(i2);
    }

    public int getKeyCode(int i2) {
        return this.codes[i2] == 0 ? super.getKeyCode(i2) : this.codes[i2];
    }

    public String getKeyName(int i2) {
        for (int j2 = 0; j2 < this.n; ++j2) {
            if (this.CodeTo[j2] != i2) continue;
            return super.getKeyName(this.CodeFrom[j2]);
        }
        return super.getKeyName(i2);
    }

    public int getWidth() {
        return 240;
    }

    public int getHeight() {
        return 320;
    }

    protected void sizeChanged(int i2, int j2) {
        this.setFullScreenMode(this.setFullScreen);
    }
}

