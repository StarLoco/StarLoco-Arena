/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ib
 */
public class ib_1
extends ZT {
    private static final acl_0 aU = new ym_0(new azp_0());

    public ib_1() {
        this.aG();
    }

    public ib_1 ln() {
        ib_1 ib_12;
        try {
            ib_12 = (ib_1)aU.adr();
            ib_12.uG = aU;
        }
        catch (Exception exception) {
            ib_12 = new ib_1();
            ib_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return ib_12;
    }

    public void aG() {
        super.aG();
        this.bWt.set(15);
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.bWm != null && this.bWm.b(Lr.bqx) && !this.bWm.b(avx_0.dey)) {
            this.bWm.a(Lr.bqx).ka(this.r);
            if (this.bWk.iO() == 134L && (this.bWm.PR() || this.bWm.d(Lr.bqx) <= 0)) {
                ((gn_0)this.bWl).LQ().l(or_0.Xs.tI(), (short)1);
            }
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        switch (((xj_0)this.bWj).Tb().length) {
            case 1: {
                this.r = (int)((xj_0)this.bWj).Tb()[0];
                break;
            }
            default: {
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un CharacPoison : " + ((xj_0)this.bWj).Tb().length));
                this.r = 0;
            }
        }
        if (xb_22 != null) {
            this.r *= xb_22.getValue();
        }
    }

    public boolean aH() {
        return false;
    }

    public boolean aI() {
        return true;
    }

    public boolean aJ() {
        return false;
    }
}

