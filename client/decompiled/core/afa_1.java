/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aFa
 */
public final class afa_1
extends jy_2 {
    public final boolean dES;
    public final String coI;
    public final anw dET;

    public afa_1(lc_0 lc_02, String string, anw anw2) {
        super(lc_02);
        this.dES = true;
        this.coI = string;
        this.dET = anw2;
    }

    public afa_1(lc_0 lc_02, anw anw2, String string) {
        super(lc_02);
        this.dES = false;
        this.coI = string;
        this.dET = anw2;
    }

    public String toString() {
        return this.dES ? this.coI + this.dET : this.dET + this.coI;
    }

    public void a(Ax ax) {
        ax.e(this);
    }

    public void a(EO eO) {
        eO.e(this);
    }
}

