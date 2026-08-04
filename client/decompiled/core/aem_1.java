/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.Cursor;
import org.apache.log4j.Logger;

/*
 * Renamed from aeM
 */
public class aem_1
implements ass_0,
Runnable {
    private static Logger a = Logger.getLogger(aem_1.class);
    private Cursor[] cpG;
    private int HX;
    private int ajD;

    public aem_1(Cursor[] cursorArray, int n2) {
        assert (n2 > 0) : "delay <= 0 !";
        assert (cursorArray != null && cursorArray.length > 0) : "Invalid cursor array !";
        this.cpG = cursorArray;
        this.HX = n2;
        this.ajD = 0;
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }

    public void show() {
        this.ajD = 0;
        this.run();
        ip_2.Un().a(this, this.HX, -1);
    }

    public void hide() {
        ip_2.Un().b(this);
    }

    public void run() {
        add_1.aOG().YN().setCursor(this.cpG[this.ajD]);
        this.ajD = (this.ajD + 1) % this.cpG.length;
    }
}

