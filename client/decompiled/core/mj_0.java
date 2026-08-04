/*
 * Decompiled with CFR 0.152.
 */
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * Renamed from Mj
 */
class mj_0
extends WindowAdapter {
    final /* synthetic */ hs_1 btk;

    mj_0(hs_1 hs_12) {
        this.btk = hs_12;
    }

    public void windowIconified(WindowEvent windowEvent) {
        if (hs_1.a(this.btk) != null) {
            hs_1.a(this.btk).bS(true);
        }
    }

    public void windowDeiconified(WindowEvent windowEvent) {
        if (hs_1.a(this.btk) != null) {
            hs_1.a(this.btk).bS(false);
        }
    }

    public void windowClosed(WindowEvent windowEvent) {
        if (hs_1.a(this.btk) != null) {
            hs_1.a(this.btk).Zg();
        }
        hs_1.a.info((Object)"UI closed. Leaving application");
        System.exit(0);
    }
}

