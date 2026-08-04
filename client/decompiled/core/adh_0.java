/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aDh
 */
public final class adh_0
extends wl_0 {
    public final jy_2 bMM;
    public final TK Pj;

    public adh_0(lc_0 lc_02, jy_2 jy_22, TK tK) {
        super(lc_02);
        this.bMM = jy_22;
        this.bMM.a(this);
        this.Pj = tK;
        this.Pj.a(this);
    }

    public String toString() {
        return "while (" + this.bMM + ") " + this.Pj + ';';
    }

    public void a(awv_0 awv_02) {
        awv_02.c(this);
    }
}

