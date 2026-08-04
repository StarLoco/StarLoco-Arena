/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from abh
 */
public final class abh_1
extends gk_0
implements eb_0 {
    public abh_1(lc_0 lc_02, String string, short s, String string2, atu_0 atu_02, atu_0[] atu_0Array) {
        super(lc_02, string, s, string2, atu_02, atu_0Array);
    }

    protected asn lP() {
        aim_2 aim_22 = this.Dw();
        while (!(aim_22 instanceof xN)) {
            aim_22 = aim_22.Dw();
        }
        if (aim_22 instanceof kc_0 && (((kc_0)aim_22).HC & 8) != 0) {
            return null;
        }
        while (!(aim_22 instanceof el_1)) {
            aim_22 = aim_22.Dw();
        }
        return ((DM)aim_22).aOl;
    }

    public String getClassName() {
        aim_2 aim_22 = this.Dw();
        while (!(aim_22 instanceof el_1)) {
            aim_22 = aim_22.Dw();
        }
        return ((el_1)aim_22).getClassName() + '$' + this.name;
    }

    public void a(qo_1 qo_12) {
        qo_12.c(this);
    }
}

