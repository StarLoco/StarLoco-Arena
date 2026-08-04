/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from qH
 */
public class qh_0
extends ZT {
    private static final acl_0 aU = new ym_0(new al_2());

    public qh_0() {
        this.aG();
    }

    public qh_0 vU() {
        qh_0 qh_02;
        try {
            qh_02 = (qh_0)aU.adr();
            qh_02.uG = aU;
        }
        catch (Exception exception) {
            qh_02 = new qh_0();
            qh_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un StrikeBack : " + exception.getMessage()));
        }
        return qh_02;
    }

    public void aG() {
        super.aG();
        this.bWt.set(2);
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWm != null && this.bWm.b(Lr.bqx)) {
            if (this.r > 0) {
                this.bWm.a(Lr.bqx).ka(this.r);
            }
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        this.r = us_0.U(((xj_0)this.bWj).Tb()[0] * (float)xb_22.getValue() / 100.0f);
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

