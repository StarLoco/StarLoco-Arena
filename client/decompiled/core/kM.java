/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.Cursor;
import org.apache.log4j.Logger;

public class kM
implements ass_0 {
    private static Logger a = Logger.getLogger(kM.class);
    private Cursor FF;

    public kM(Cursor cursor) {
        this.FF = cursor;
    }

    public void show() {
        add_1.aOG().YN().setCursor(this.FF);
    }

    public void hide() {
    }
}

