/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aFi
 */
public class afi_0
extends ZT {
    private static final acl_0 aU = new ym_0(new ta_2());
    protected int bU;

    public static afi_0 b(ea_0 ea_02, int n2, kc_2 kc_22, xj_0 xj_02, kc_2 kc_23, Pi pi) {
        afi_0 afi_02;
        try {
            afi_02 = (afi_0)aU.adr();
            afi_02.uG = aU;
        }
        catch (Exception exception) {
            afi_02 = new afi_0();
            afi_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ChangeLook : " + exception.getMessage()));
        }
        afi_02.aW = mh_2.bvu.getId();
        afi_02.bWr = ((ZT)mh_2.bvu.getObject()).Oz();
        afi_02.aG();
        afi_02.bWm = kc_22;
        afi_02.bWl = kc_23;
        afi_02.r = n2;
        afi_02.bWk = pi;
        afi_02.ahI = -1;
        afi_02.bdv = ea_02;
        afi_02.bWj = xj_02;
        afi_02.bU = 0;
        return afi_02;
    }

    public afi_0 aRH() {
        afi_0 afi_02;
        try {
            afi_02 = (afi_0)aU.adr();
            afi_02.uG = aU;
        }
        catch (Exception exception) {
            afi_02 = new afi_0();
            afi_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return afi_02;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWm instanceof gn_0) {
            boolean bl3 = true;
            if (this.bU > 0) {
                int n2 = 0;
                int n3 = ((xj_0)this.ajO()).ST();
                for (xb_2 xb_23 : this.bWm.PJ()) {
                    if (xb_23.ajO() == null || xb_23.ajO().ST() != n3) continue;
                    ++n2;
                }
                if (n2 != this.bU) {
                    bl3 = false;
                }
            }
            if (bl3) {
                ((gn_0)this.bWm).fy(this.r);
            }
        }
        super.a(xb_22, bl2);
    }

    public void aK() {
        if (this.bWm instanceof gn_0) {
            boolean bl2 = true;
            if (this.bU > 0) {
                int n2 = 0;
                int n3 = ((xj_0)this.ajO()).ST();
                for (xb_2 xb_22 : this.bWm.PJ()) {
                    if (xb_22.ajO() == null || xb_22.ajO().ST() != n3) continue;
                    ++n2;
                }
                if (n2 != this.bU) {
                    bl2 = false;
                }
            }
            if (bl2) {
                gn_0 gn_02 = (gn_0)this.bWm;
                gn_02.fA(this.r);
            }
        }
        super.aK();
    }

    public void a(xb_2 xb_22) {
        switch (((xj_0)this.bWj).Tb().length) {
            case 1: {
                this.r = (int)((xj_0)this.bWj).Tb()[0];
                break;
            }
            case 2: {
                this.r = (int)((xj_0)this.bWj).Tb()[0];
                this.bU = (int)((xj_0)this.bWj).Tb()[1];
                break;
            }
            default: {
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un ChangeLook : " + ((xj_0)this.bWj).Tb().length));
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

