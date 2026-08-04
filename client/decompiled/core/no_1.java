/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from nO
 */
public final class no_1
extends wl_0 {
    public final TK Pg;
    public final jy_2 Ph;
    public final jy_2[] Pi;
    public final TK Pj;

    public no_1(lc_0 lc_02, TK tK, jy_2 jy_22, jy_2[] jy_2Array, TK tK2) {
        super(lc_02);
        this.Pg = tK;
        if (tK != null) {
            tK.a(this);
        }
        this.Ph = jy_22;
        if (jy_22 != null) {
            jy_22.a(this);
        }
        this.Pi = jy_2Array;
        if (jy_2Array != null) {
            for (int j = 0; j < jy_2Array.length; ++j) {
                jy_2Array[j].a(this);
            }
        }
        this.Pj = tK2;
        this.Pj.a(this);
    }

    public String toString() {
        return "for (...; ...; ...) ...";
    }

    public void a(awv_0 awv_02) {
        awv_02.c(this);
    }
}

