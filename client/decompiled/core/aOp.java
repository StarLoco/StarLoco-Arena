/*
 * Decompiled with CFR 0.152.
 */
public class aOp
extends ZT {
    private static final acl_0 aU = new ym_0(new akw_1());

    public aOp aYd() {
        aOp aOp2;
        try {
            aOp2 = (aOp)aU.adr();
            aOp2.uG = aU;
        }
        catch (Exception exception) {
            aOp2 = new aOp();
            aOp2.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return aOp2;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().a(avx_0.deC);
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
    }

    public void aK() {
        if (this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().b(avx_0.deC);
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

    public boolean gM() {
        return false;
    }
}

