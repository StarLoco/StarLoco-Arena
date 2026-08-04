/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class Vz
extends ke {
    private static Logger a = Logger.getLogger(Vz.class);
    private boolean bSC;
    private na_1 bSD;

    public Vz(adg_2 adg_22, boolean bl2) {
        this.DK = adg_22;
        this.bSC = bl2;
    }

    public void setFocused(boolean bl2) {
        this.bSC = bl2;
    }

    public boolean air() {
        return this.bSC;
    }

    public na_1 ais() {
        return this.bSD;
    }

    public qe_1 aV() {
        return qe_1.bFh;
    }
}

