/*
 * Decompiled with CFR 0.152.
 */
public class Pr
extends ig_1 {
    private static final acl_0 aU = new ym_0(new vw_0());

    private Pr() {
    }

    public Pr(fv_1 fv_12, boolean bl2) {
        super(fv_12, bl2);
    }

    public Pr abZ() {
        Pr pr;
        try {
            pr = (Pr)aU.adr();
            pr.uG = aU;
        }
        catch (Exception exception) {
            pr = new Pr();
            pr.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un RunningEffect : " + exception.getMessage()));
        }
        pr.a(this);
        return pr;
    }

    public void a(xb_2 xb_22) {
        super.a(xb_22);
        if (this.bWl != null && !this.bWl.PR() && !this.bWl.PT()) {
            this.r *= this.bWl.d(Lr.bqz);
        } else {
            this.aoy();
        }
    }

    /* synthetic */ Pr(vw_0 vw_02) {
        this();
    }
}

