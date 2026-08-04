/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from pu
 */
public class pu_0
extends ZT {
    private static final acl_0 aU = new ym_0(new ix_2());

    public pu_0 uh() {
        pu_0 pu_02;
        try {
            pu_02 = (pu_0)aU.adr();
            pu_02.uG = aU;
        }
        catch (Exception exception) {
            pu_02 = new pu_0();
            pu_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un AutomaticEndTurn : " + exception.getMessage()));
        }
        return pu_02;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().a(avx_0.dew);
        }
        super.a(xb_22, bl2);
    }

    public void aK() {
        if (this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().b(avx_0.dew);
        }
        super.aK();
    }

    public void a(xb_2 xb_22) {
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

