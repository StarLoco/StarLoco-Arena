/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from anj
 */
public class anj_2
extends df_2 {
    private static final acl_0 aU = new ym_0(new ahw_2());

    public anj_2 aCp() {
        anj_2 anj_22;
        try {
            anj_22 = (anj_2)aU.adr();
            anj_22.uG = aU;
        }
        catch (Exception exception) {
            anj_22 = new anj_2();
            anj_22.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un APLeech : " + exception.getMessage()));
        }
        return anj_22;
    }

    public void aG() {
        super.aG();
        this.bWt.set(56);
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWl != null && this.bWl.b(Lr.bqy)) {
            if (this.bWl.d(Lr.bqy) < this.bWl.a(Lr.bqy).max()) {
                this.bWl.a(Lr.bqy).jZ(this.r);
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

