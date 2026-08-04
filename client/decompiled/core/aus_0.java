/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from auS
 */
public class aus_0
extends ZT {
    private static final acl_0 aU = new ym_0(new aof_2());

    public aus_0() {
        this.aG();
    }

    public aus_0 aHI() {
        aus_0 aus_02;
        try {
            aus_02 = (aus_0)aU.adr();
            aus_02.uG = aU;
        }
        catch (Exception exception) {
            aus_02 = new aus_0();
            aus_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return aus_02;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWl != null && this.bWl instanceof gn_0) {
            short s = this.bdv.gU().JI();
            ((gn_0)this.bWl).a((fv)this.bWk, s, (short)this.r);
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
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un ReduceCooldown : " + ((xj_0)this.bWj).Tb().length));
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
}

