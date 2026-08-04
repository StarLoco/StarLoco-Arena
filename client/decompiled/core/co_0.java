/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from CO
 */
public class co_0
extends ZT {
    private static final acl_0 aU = new ym_0(new mn_0());

    public co_0 Li() {
        co_0 co_02;
        try {
            co_02 = (co_0)aU.adr();
            co_02.uG = aU;
        }
        catch (Exception exception) {
            co_02 = new co_0();
            co_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return co_02;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.bWm instanceof gn_0) {
            gn_0 gn_02 = (gn_0)this.bWm;
            gn_02.a(Lr.bre).jZ(300);
            gn_02.a(Lr.brd).jZ(-300);
            gn_02.PL().a(avx_0.deu);
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
    }

    public void aK() {
        if (this.bWm instanceof gn_0) {
            gn_0 gn_02 = (gn_0)this.bWm;
            gn_02.PL().b(avx_0.deu);
            gn_02.a(Lr.bre).jZ(-300);
            gn_02.a(Lr.brd).jZ(300);
            if (this.bWm.c(avx_0.deu) <= 0) {
                gn_02.Oc().k(gn_02);
            }
        } else {
            this.aoy();
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

