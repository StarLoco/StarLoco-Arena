/*
 * Decompiled with CFR 0.152.
 */
public class Jk
extends ZT {
    private boolean rM = true;
    private static final acl_0 aU = new ym_0(new vl_2());

    public Jk VB() {
        Jk jk;
        try {
            jk = (Jk)aU.adr();
            jk.uG = aU;
        }
        catch (Exception exception) {
            jk = new Jk();
            jk.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        jk.rM = this.rM;
        return jk;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.rM && !this.bWl.PR() && !this.bWl.PT()) {
            ry ry2 = new ry(this.bWm.gn(), this.bWm.go(), this.bWm.gp());
            gn_0 gn_02 = (gn_0)this.bWm;
            if (((gn_0)this.bWl).i(gn_02)) {
                this.b(xb_22, bl2);
                if (this.bdv.gX() != null) {
                    this.bdv.gX().a(ry2.getX(), ry2.getY(), ry2.wk(), this.bWm.gn(), this.bWm.go(), this.bWm.gp(), this.bWm);
                }
            }
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        if (!(this.bWl instanceof gn_0) || !(this.bWm instanceof gn_0) || this.bWl == this.bWm || ((gn_0)this.bWm).PL().b((aak_2)avx_0.deA) || ((gn_0)this.bWm).rD() || this.bWl.PD() == Short.MAX_VALUE || ((gn_0)this.bWl).Qa() || ((gn_0)this.bWl).rD()) {
            this.rM = false;
        }
    }

    public boolean aH() {
        return true;
    }

    public boolean aI() {
        return true;
    }

    public boolean aJ() {
        return false;
    }
}

