/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from yK
 */
public class yk_0
extends ZT {
    private static final acl_0 aU = new ym_0(new ra_1());

    public yk_0() {
        this.aG();
    }

    public yk_0 FE() {
        yk_0 yk_02;
        try {
            yk_02 = (yk_0)aU.adr();
            yk_02.uG = aU;
        }
        catch (Exception exception) {
            yk_02 = new yk_0();
            yk_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un MPLoss : " + exception.getMessage()));
        }
        return yk_02;
    }

    public static yk_0 b(ea_0 ea_02, int n2, kc_2 kc_22) {
        yk_0 yk_02;
        try {
            yk_02 = (yk_0)aU.adr();
            yk_02.uG = aU;
        }
        catch (Exception exception) {
            yk_02 = new yk_0();
            yk_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un " + yk_0.class.getSimpleName() + " : " + exception.getMessage()));
        }
        yk_02.bWm = kc_22;
        yk_02.r = n2;
        yk_02.ahI = -1;
        yk_02.bdv = ea_02;
        yk_02.aW = mh_2.buG.getId();
        yk_02.bWr = ((ZT)mh_2.buG.getObject()).Oz();
        yk_02.aG();
        return yk_02;
    }

    public void aG() {
        super.aG();
        this.bWt.set(62);
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWm != null && this.bWm.b(Lr.bqz)) {
            this.bWm.a(Lr.bqz).ka(this.r);
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        int n2;
        switch (((xj_0)this.bWj).Tb().length) {
            case 1: {
                this.r = (int)((xj_0)this.bWj).Tb()[0];
                break;
            }
            case 3: {
                this.r = ou_1.aE((int)((xj_0)this.bWj).iY(0), (int)((xj_0)this.bWj).iY(1)) + (int)((xj_0)this.bWj).iY(2);
                break;
            }
            default: {
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un MPLoss : " + ((xj_0)this.bWj).Tb().length));
                this.r = 0;
            }
        }
        int n3 = Math.min(this.r, this.bWm.d(Lr.bqz));
        if (this.bWm.b(Lr.bqZ) && this.r > 0 && (n2 = this.bWm.d(Lr.bqZ)) > 0) {
            n3 = 0;
            for (int j = 0; j < this.r; ++j) {
                if (ou_1.he(100) <= n2) continue;
                ++n3;
            }
        }
        this.r = n3;
        if (this.bWm != null && this.bWm.b(Lr.bqz) && this.r < 0) {
            this.r = this.bWm.d(Lr.bqz);
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

