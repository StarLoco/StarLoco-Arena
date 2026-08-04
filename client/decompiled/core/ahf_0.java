/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aHf
 */
public class ahf_0
extends yk_0 {
    private static final acl_0 aU = new ym_0(new yo_2());

    public ahf_0 aTj() {
        ahf_0 ahf_02;
        try {
            ahf_02 = (ahf_0)aU.adr();
            ahf_02.uG = aU;
        }
        catch (Exception exception) {
            ahf_02 = new ahf_0();
            ahf_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un APLeech : " + exception.getMessage()));
        }
        return ahf_02;
    }

    public void aG() {
        super.aG();
        this.bWt.set(67);
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWl != null && this.bWl.b(Lr.bqz)) {
            if (this.bWl.d(Lr.bqz) < this.bWl.a(Lr.bqz).max()) {
                this.bWl.a(Lr.bqz).jZ(this.r);
            }
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public boolean aH() {
        return true;
    }

    public boolean aI() {
        return true;
    }

    public boolean aJ() {
        return false;
    }
}

