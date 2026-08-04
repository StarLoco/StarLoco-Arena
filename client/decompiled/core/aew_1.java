/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aEW
 */
public class aew_1
extends ZT {
    private static final acl_0 aU = new ym_0(new xe_1());
    private ack_1 dEJ;

    public aew_1 aRh() {
        aew_1 aew_12;
        try {
            aew_12 = (aew_1)aU.adr();
            aew_12.uG = aU;
        }
        catch (Exception exception) {
            aew_12 = new aew_1();
            aew_12.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un " + this.getClass().getSimpleName() + " : " + exception.getMessage()));
        }
        aew_12.dEJ = this.dEJ;
        return aew_12;
    }

    public void b() {
        this.r = 0;
        this.dEJ = null;
        super.b();
    }

    public void j() {
        this.r = 0;
        this.dEJ = null;
        super.j();
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.bWm instanceof gn_0) {
            yl_1 yl_12 = ame_1.aWP().eN(this.r);
            if (yl_12 == null) {
                a.error((Object)("Impossible d'executer l'effet " + this.getClass().getSimpleName() + " : AbstractEffectArea \u00e9gal \u00e0 null."));
            } else {
                this.dEJ = yl_12.a(new akh_0(this.bWm.getId(), this.bWn.getX(), this.bWn.getY(), this.bWn.wk(), this.bdv, this.bWl));
                this.b(xb_22, bl2);
                ((gn_0)this.bWm).e(this.dEJ);
                this.bWm.PJ().o(this);
                if (this.bWl != null && this.bWl != this.bWm) {
                    this.bWl.PJ().o(this);
                }
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
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un " + this.getClass().getSimpleName() + " : " + ((xj_0)this.bWj).Tb().length + "."));
                this.r = 0;
            }
        }
    }

    public void aK() {
        if (this.dEJ != null && this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).d(this.dEJ);
        }
        super.aK();
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

