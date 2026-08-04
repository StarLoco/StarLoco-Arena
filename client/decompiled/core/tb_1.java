/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Tb
 */
public final class tb_1
extends wl_0 {
    public final TK Pj;
    public final jy_2 bMM;

    public tb_1(lc_0 lc_02, TK tK, jy_2 jy_22) {
        super(lc_02);
        this.Pj = tK;
        this.Pj.a(this);
        this.bMM = jy_22;
        this.bMM.a(this);
    }

    public String toString() {
        return "do " + this.Pj + " while(" + this.bMM + ");";
    }

    public void a(awv_0 awv_02) {
        awv_02.c(this);
    }
}

