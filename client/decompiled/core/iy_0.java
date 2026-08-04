/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from iy
 */
public class iy_0
extends ZT {
    private static final acl_0 aU = new ym_0(new aes_0());

    public iy_0 lA() {
        iy_0 iy_02;
        try {
            iy_02 = (iy_0)aU.adr();
            iy_02.uG = aU;
        }
        catch (Exception exception) {
            iy_02 = new iy_0();
            iy_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return iy_02;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        boolean bl3 = false;
        if (this.bWl instanceof gn_0 && ((gn_0)this.bWl).PY() != null) {
            gn_0 gn_02 = (gn_0)this.bWl;
            gn_0 gn_03 = gn_02.PY();
            ry ry2 = new ry(gn_02.PY().gg());
            if (gn_02.a(this.bWn, gn_02.L())) {
                bl3 = true;
                this.b(xb_22, bl2);
                if (this.bdv.gX() != null) {
                    this.bdv.gX().a(ry2.getX(), ry2.getY(), ry2.wk(), gn_03.gn(), gn_03.go(), gn_03.gp(), gn_03);
                }
            }
        }
        if (!bl3) {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
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

