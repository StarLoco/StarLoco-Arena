/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aID
 */
public class aid_0
extends ZT {
    private static final acl_0 aU = new ym_0(new aDf());

    public aid_0 aVg() {
        aid_0 aid_02;
        try {
            aid_02 = (aid_0)aU.adr();
            aid_02.uG = aU;
        }
        catch (Exception exception) {
            aid_02 = new aid_0();
            aid_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return aid_02;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().a(avx_0.deG);
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
    }

    public void aK() {
        if (this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().b(avx_0.deG);
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

