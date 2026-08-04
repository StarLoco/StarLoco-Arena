/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ayP
 */
public final class ayp_0
extends gk_0
implements pn_1 {
    public ayp_0(lc_0 lc_02, String string, short s, String string2, atu_0 atu_02, atu_0[] atu_0Array) {
        super(lc_02, string, s, string2, atu_02, atu_0Array);
        if ((s & 0xE) != 0) {
            this.j("Modifiers \"protected\", \"private\" and \"static\" not allowed in package member class declaration");
        }
    }

    public void a(kh_1 kh_12) {
        this.a((aim_2)kh_12);
    }

    public kh_1 ue() {
        return (kh_1)this.Dw();
    }

    protected asn lP() {
        return null;
    }

    public String getClassName() {
        String string = this.getName();
        kh_1 kh_12 = (kh_1)this.Dw();
        if (kh_12.bnk != null) {
            string = kh_12.bnk.doL + '.' + string;
        }
        return string;
    }

    public void a(qo_1 qo_12) {
        qo_12.b(this);
    }
}

