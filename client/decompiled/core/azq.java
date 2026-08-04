/*
 * Decompiled with CFR 0.152.
 */
import java.awt.event.KeyEvent;

public class azq {
    private static azq dnx = new azq();
    private lb_0 dny = new lb_0();

    private azq() {
    }

    public static azq aLT() {
        return dnx;
    }

    public KeyEvent[] aLU() {
        return (KeyEvent[])this.dny.a(new KeyEvent[this.dny.size()]);
    }

    public boolean bY(int n2) {
        return this.dny.bY(n2);
    }

    public void keyPressed(KeyEvent keyEvent) {
        this.dny.c(keyEvent.getKeyCode(), keyEvent);
    }

    public void keyReleased(KeyEvent keyEvent) {
        this.dny.remove(keyEvent.getKeyCode());
    }
}

