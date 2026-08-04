/*
 * Decompiled with CFR 0.152.
 */
public final class ayN
extends jy_2 {
    public final anw dmN;
    public final String coI;
    public final jy_2 ail;

    public ayN(lc_0 lc_02, anw anw2, String string, jy_2 jy_22) {
        super(lc_02);
        this.dmN = anw2;
        this.coI = string;
        this.ail = jy_22;
    }

    public String toString() {
        return this.dmN.toString() + ' ' + this.coI + ' ' + this.ail.toString();
    }

    public void a(Ax ax) {
        ax.d(this);
    }

    public void a(EO eO) {
        eO.d(this);
    }
}

