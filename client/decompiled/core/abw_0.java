/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from abw
 */
public class abw_0
extends ig_1 {
    private static final acl_0 aU = new ym_0(new alu_0());

    private abw_0() {
    }

    public abw_0(fv_1 fv_12, boolean bl2) {
        super(fv_12, bl2);
    }

    public abw_0 aqh() {
        abw_0 abw_02;
        try {
            abw_02 = (abw_0)aU.adr();
            abw_02.uG = aU;
        }
        catch (Exception exception) {
            abw_02 = new abw_0();
            abw_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un RunningEffect : " + exception.getMessage()));
        }
        abw_02.a(this);
        return abw_02;
    }

    public void a(xb_2 xb_22) {
        super.a(xb_22);
        if (this.bWl != null && !this.bWl.PR() && !this.bWl.PT()) {
            this.r *= this.bWl.d(Lr.bqy);
        } else {
            this.aoy();
        }
    }

    /* synthetic */ abw_0(alu_0 alu_02) {
        this();
    }
}

