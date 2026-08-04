/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aIa
 */
public final class aia_1
extends akE {
    public final jy_2 bMM;
    public final TK dOp;
    public final TK dOq;

    public aia_1(lc_0 lc_02, jy_2 jy_22, TK tK, TK tK2) {
        super(lc_02);
        this.bMM = jy_22;
        this.bMM.a(this);
        this.dOp = tK;
        this.dOp.a(this);
        this.dOq = tK2;
        if (tK2 != null) {
            tK2.a(this);
        }
    }

    public String toString() {
        return this.dOq == null ? "if" : "if ... else";
    }

    public void a(awv_0 awv_02) {
        awv_02.c(this);
    }
}

