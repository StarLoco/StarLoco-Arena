/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.java.games.joal.AL
 */
import net.java.games.joal.AL;

public abstract class aks
implements aEe {
    protected AL cY;
    protected aL bAT;

    public void initialize() {
        this.cY = aL.bH().bI();
        this.bAT = aL.bH();
    }

    public void cleanUp() {
        this.cY = null;
        this.bAT = null;
    }
}

