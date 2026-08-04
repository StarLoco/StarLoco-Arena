/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Df
 */
public class df_2
extends ZT {
    private static final acl_0 aU = new ym_0(new Wi());

    public df_2() {
        this.aG();
    }

    public df_2 Lx() {
        df_2 df_22;
        try {
            df_22 = (df_2)aU.adr();
            df_22.uG = aU;
        }
        catch (Exception exception) {
            df_22 = new df_2();
            df_22.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un APLoss : " + exception.getMessage()));
        }
        return df_22;
    }

    public static df_2 c(ea_0 ea_02, int n2, kc_2 kc_22) {
        df_2 df_22;
        try {
            df_22 = (df_2)aU.adr();
            df_22.uG = aU;
        }
        catch (Exception exception) {
            df_22 = new df_2();
            df_22.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un HPLoss : " + exception.getMessage()));
        }
        df_22.bWm = kc_22;
        df_22.r = n2;
        df_22.ahI = -1;
        df_22.bdv = ea_02;
        df_22.aW = mh_2.buC.getId();
        df_22.bWr = ((ZT)mh_2.buC.getObject()).Oz();
        df_22.aG();
        return df_22;
    }

    public void aG() {
        super.aG();
        this.bWt.set(52);
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWm != null && this.bWm.b(Lr.bqy)) {
            this.bWm.a(Lr.bqy).ka(this.r);
            if (this.bWm instanceof gn_0 && !((gn_0)this.bWm).Dk() && this.bWl instanceof gn_0) {
                ((gn_0)this.bWl).LQ().m(or_0.Wc.tI(), (short)(this.bWm.a(Lr.bqy).max() - this.bWm.d(Lr.bqy)));
            }
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
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un APLoss : " + ((xj_0)this.bWj).Tb().length));
            }
        }
        int n3 = Math.min(this.r, this.bWm.d(Lr.bqy));
        if (this.bWm.b(Lr.bqY) && n3 > 0 && (n2 = this.bWm.d(Lr.bqY)) > 0) {
            n3 = 0;
            for (int j = 0; j < this.r; ++j) {
                if (ou_1.he(100) <= n2) continue;
                ++n3;
            }
        }
        this.r = n3;
        if (this.bWm != null && this.bWm.b(Lr.bqy) && this.r < 0) {
            this.r = this.bWm.d(Lr.bqy);
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

