/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class vY
extends ke {
    private static Logger a = Logger.getLogger(vY.class);
    private boolean vd = false;

    public vY(na_1 na_12, boolean bl2) {
        this.vd = bl2;
        this.DK = na_12;
    }

    public boolean isSelected() {
        return this.vd;
    }

    public void setSelected(boolean bl2) {
        this.vd = bl2;
    }

    public qe_1 aV() {
        return qe_1.bFG;
    }

    public void j() {
        super.j();
        this.vd = false;
    }

    public void b() {
        super.b();
    }
}

