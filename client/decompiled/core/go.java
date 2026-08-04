/*
 * Decompiled with CFR 0.152.
 */
import java.util.List;

public class go
extends ZT {
    private static final acl_0 aU = new ym_0(new st_2());
    private boolean sO = true;

    public go jE() {
        go go2;
        try {
            go2 = (go)aU.adr();
            go2.uG = aU;
        }
        catch (Exception exception) {
            go2 = new go();
            go2.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un Push : " + exception.getMessage()));
        }
        go2.sO = true;
        return go2;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.sO) {
            ry ry2 = new ry(this.bWl.gn(), this.bWl.go(), this.bWl.gp());
            if (this.bWl instanceof gn_0) {
                gn_0 gn_02 = (gn_0)this.bWl;
                if (gn_02.rD()) {
                    gn_02.PZ().bm(true);
                }
                if (gn_02.Qa()) {
                    gn_02.bm(false);
                }
            }
            this.bWl.m(this.bWn.getX(), this.bWn.getY(), this.bWn.wk());
            this.b(xb_22, bl2);
            if (this.bdv.gX() != null) {
                this.bdv.gX().a(ry2.getX(), ry2.getY(), ry2.wk(), this.bWl.gn(), this.bWl.go(), this.bWl.gp(), this.bWl);
            }
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        akd_0 akd_02;
        if (this.bWl instanceof gn_0 && this.bWn != null && (akd_02 = this.bdv.gV().F(this.bWn.getX(), this.bWn.getY(), this.bWn.wk())) == null) {
            this.sO = false;
        }
    }

    public List a(ea_0 ea_02, int n2, int n3, short s) {
        return null;
    }

    public boolean aH() {
        return true;
    }

    public boolean aI() {
        return false;
    }

    public boolean aJ() {
        return true;
    }
}

