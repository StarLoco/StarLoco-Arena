/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aox
 */
public class aox_1
extends ZT {
    private boolean rM = true;
    private ry cKZ;
    private ry bWn;
    private static final acl_0 aU = new ym_0(new axT());
    public aea_0 nC = new axQ(this, 20);

    public aox_1 aCM() {
        aox_1 aox_12;
        try {
            aox_12 = (aox_1)aU.adr();
            aox_12.uG = aU;
        }
        catch (Exception exception) {
            aox_12 = new aox_1();
            aox_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un Push : " + exception.getMessage()));
        }
        aox_12.rM = this.rM;
        aox_12.bWn = this.bWn;
        aox_12.cKZ = this.cKZ;
        return aox_12;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.rM) {
            if (this.bWm instanceof gn_0 && this.bWl instanceof gn_0) {
                gn_0 gn_02 = (gn_0)this.bWm;
                gn_0 gn_03 = (gn_0)this.bWl;
                gn_0 gn_04 = gn_03.PZ();
                gn_0 gn_05 = gn_02.PZ();
                if (gn_04 != null) {
                    gn_04.bm(true);
                    gn_04.i(gn_02);
                }
                if (gn_05 != null) {
                    gn_05.bm(true);
                    gn_05.i(gn_03);
                }
            }
            this.bWl.m(this.bWn);
            this.bWm.m(this.cKZ);
            if (xb_22 != null) {
                xb_22.h(this.bWl);
            }
            this.b(xb_22, bl2);
            if (this.bdv != null && this.bdv.gX() != null) {
                this.bdv.gX().a(this.cKZ.getX(), this.cKZ.getY(), this.cKZ.wk(), this.bWn.getX(), this.bWn.getY(), this.bWn.wk(), this.bWl);
            }
            if (this.bdv != null && this.bdv.gX() != null) {
                this.bdv.gX().a(this.bWn.getX(), this.bWn.getY(), this.bWn.wk(), this.cKZ.getX(), this.cKZ.getY(), this.cKZ.wk(), this.bWm);
            }
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        this.rM = true;
        if (xb_22 != null && xb_22.ajO() != null && ((xj_0)xb_22.ajO()).alo() || this.bWl == null || this.bWl.PR() || this.bWl.PT()) {
            this.rM = false;
            return;
        }
        if (this.bWm instanceof gn_0) {
            this.bWn = new ry(this.bWm.gn(), this.bWm.go(), this.bWm.gp());
            if (this.bWm.b(avx_0.deB)) {
                this.rM = false;
            }
        }
        if (this.bWl instanceof gn_0) {
            this.cKZ = new ry(this.bWl.gn(), this.bWl.go(), this.bWl.gp());
            if (this.bWl.b(avx_0.deB)) {
                this.rM = false;
            }
            if (((gn_0)this.bWl).rD() && this.bWm.b(avx_0.deA) || this.bWm instanceof gn_0 && ((gn_0)this.bWm).rD() && this.bWl.b(avx_0.deA)) {
                this.rM = false;
            }
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

    public aea_0 gN() {
        return this.nC;
    }

    static /* synthetic */ ry a(aox_1 aox_12) {
        return aox_12.cKZ;
    }

    static /* synthetic */ ry b(aox_1 aox_12) {
        return aox_12.bWn;
    }

    static /* synthetic */ ry a(aox_1 aox_12, ry ry2) {
        aox_12.cKZ = ry2;
        return aox_12.cKZ;
    }

    static /* synthetic */ ry b(aox_1 aox_12, ry ry2) {
        aox_12.bWn = ry2;
        return aox_12.bWn;
    }
}

