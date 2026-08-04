/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from atE
 */
public class ate_0
extends ZT {
    private static final acl_0 aU = new ym_0(new rk_1());

    public ate_0 aGI() {
        ate_0 ate_02;
        try {
            ate_02 = (ate_0)aU.adr();
            ate_02.uG = aU;
        }
        catch (Exception exception) {
            ate_02 = new ate_0();
            ate_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return ate_02;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().a(avx_0.deE);
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
    }

    public void aK() {
        if (this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().b(avx_0.deE);
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

