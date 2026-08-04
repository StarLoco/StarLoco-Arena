/*
 * Decompiled with CFR 0.152.
 */
public final class aFz
extends jy_2 {
    public final ahe_1 dHl;
    public final ln_2 dHm;

    public aFz(lc_0 lc_02, ahe_1 ahe_12, ln_2 ln_22) {
        super(lc_02);
        this.dHl = ahe_12;
        this.dHm = ln_22;
    }

    public String toString() {
        return "new " + this.dHl.toString() + " { ... }";
    }

    public void a(Ax ax) {
        ax.c(this);
    }

    public void a(EO eO) {
        eO.c(this);
    }
}

