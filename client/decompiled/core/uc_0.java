/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from uc
 */
public class uc_0
extends ZT {
    private static final acl_0 aU = new ym_0(new aho_2());

    public static uc_0 a(ea_0 ea_02, int n2, kc_2 kc_22, xj_0 xj_02, kc_2 kc_23, Pi pi) {
        uc_0 uc_02;
        try {
            uc_02 = (uc_0)aU.adr();
            uc_02.uG = aU;
        }
        catch (Exception exception) {
            uc_02 = new uc_0();
            uc_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un AdaptLook : " + exception.getMessage()));
        }
        uc_02.aW = mh_2.bwg.getId();
        uc_02.bWr = ((ZT)mh_2.bwg.getObject()).Oz();
        uc_02.aG();
        uc_02.bWl = kc_23;
        uc_02.bWm = kc_22;
        uc_02.r = n2;
        uc_02.ahI = -1;
        uc_02.bWk = pi;
        uc_02.bdv = ea_02;
        uc_02.bWj = xj_02;
        return uc_02;
    }

    public uc_0 Ai() {
        uc_0 uc_02;
        try {
            uc_02 = (uc_0)aU.adr();
            uc_02.uG = aU;
        }
        catch (Exception exception) {
            uc_02 = new uc_0();
            uc_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return uc_02;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).fz(this.r);
        }
        super.a(xb_22, bl2);
    }

    public void aK() {
        if (this.bWm instanceof gn_0) {
            gn_0 gn_02 = (gn_0)this.bWm;
            gn_02.fA(this.r);
        }
        super.aK();
    }

    public void a(xb_2 xb_22) {
        switch (((xj_0)this.bWj).Tb().length) {
            case 1: {
                this.r = (int)((xj_0)this.bWj).Tb()[0];
                break;
            }
            default: {
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un AdaptLook : " + ((xj_0)this.bWj).Tb().length));
                this.r = 0;
            }
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

    public boolean gM() {
        return false;
    }
}

