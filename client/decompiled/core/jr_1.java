/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from JR
 */
public final class jr_1
extends akE {
    public final jy_2 bmK;

    public jr_1(lc_0 lc_02, jy_2 jy_22) {
        super(lc_02);
        this.bmK = jy_22;
        if (jy_22 != null) {
            jy_22.a(this);
        }
    }

    public String toString() {
        return this.bmK == null ? "return;" : "return " + this.bmK + ';';
    }

    public void a(awv_0 awv_02) {
        awv_02.c(this);
    }
}

