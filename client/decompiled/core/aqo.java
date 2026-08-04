/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class aqo
extends ke {
    private static Logger a = Logger.getLogger(aqo.class);
    private boolean cNV = false;

    public aqo(adg_2 adg_22, boolean bl2) {
        this.DK = adg_22;
        this.cNV = bl2;
    }

    public boolean isFull() {
        return this.cNV;
    }

    public qe_1 aV() {
        return qe_1.bFb;
    }
}

