/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from vu
 */
public final class vu_2
extends akE {
    public final jy_2 al;
    public final TK Pj;
    short asR = (short)-1;

    public vu_2(lc_0 lc_02, jy_2 jy_22, TK tK) {
        super(lc_02);
        this.al = jy_22;
        this.al.a(this);
        this.Pj = tK;
        this.Pj.a(this);
    }

    public String toString() {
        return "synchronized(" + this.al + ") " + this.Pj;
    }

    public void a(awv_0 awv_02) {
        awv_02.c(this);
    }
}

