/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from azW
 */
public class azw_0
extends ZT {
    private static final acl_0 aU = new ym_0(new hh_1());
    private int bzV = 0;
    private ry bzW;
    private int bzX;
    private int bzY;
    private boolean bzZ;
    private boolean rM = true;
    private tl_2 bAa = null;
    public aea_0 nC = new hk_0(this, 18);

    public void b() {
        super.b();
        this.bzV = 0;
        this.rM = true;
        this.bAa = null;
    }

    public void j() {
        super.j();
        this.bAa = null;
    }

    public azw_0 aMy() {
        azw_0 azw_02;
        try {
            azw_02 = (azw_0)aU.adr();
            azw_02.uG = aU;
        }
        catch (Exception exception) {
            azw_02 = new azw_0();
            azw_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un Push : " + exception.getMessage()));
        }
        azw_02.rM = this.rM;
        return azw_02;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (!this.rM) {
            this.aoy();
            super.a(xb_22, bl2);
            return;
        }
        ry ry2 = new ry(this.bWl.gn(), this.bWl.go(), this.bWl.gp());
        this.b(xb_22, bl2);
        if (((gn_0)this.bWl).Qa() && !this.bzW.equals(ry2)) {
            ((gn_0)this.bWl).bm(false);
        }
        this.bWl.m(this.bzW);
        if (this.bWv && this.bzV > 0) {
            ig_1 ig_12 = ig_1.a(this.bdv, fv_1.bal, this.bzV, this.bWl);
            ig_12.akd();
            ig_12.i(this.bWl);
            ig_12.release();
            if (this.bAa != null && this.bAa instanceof kc_2) {
                if (this.bAa instanceof gn_0 && ((gn_0)this.bAa).rD()) {
                    this.bAa = ((gn_0)this.bAa).PZ();
                }
                ig_1 ig_13 = ig_1.a(this.bdv, fv_1.bal, this.bzV, (kc_2)((Object)this.bAa));
                ig_13.akd();
                ig_13.i((kc_2)((Object)this.bAa));
                ig_13.release();
            }
        }
        if (this.bdv.gX() != null) {
            this.bdv.gX().a(ry2.getX(), ry2.getY(), ry2.wk(), this.bWl.gn(), this.bWl.go(), this.bWl.gp(), this.bWl);
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        this.rM = true;
        switch (((xj_0)this.bWj).Tb().length) {
            case 1: {
                this.r = (int)((xj_0)this.bWj).Tb()[0];
                break;
            }
            default: {
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un Push : " + ((xj_0)this.bWj).Tb().length));
                this.r = 0;
            }
        }
        if (this.bWl != null && this.bWl instanceof gn_0) {
            gn_0 gn_02 = (gn_0)this.bWl;
            if (gn_02.PL().b((aak_2)avx_0.dev)) {
                this.rM = false;
                return;
            }
            this.aaH();
            this.bzV = this.bzY;
            int n2 = this.r - this.bzX;
            if (n2 > 0) {
                this.bzV += n2 * (this.bzZ ? 3 : 3);
            }
            this.r = this.bzX;
        } else {
            this.rM = false;
        }
    }

    private void aaH() {
        short s;
        int n2;
        short s2;
        int n3;
        int n4;
        qc_0 qc_02 = new aby_2(this.bWm.gn(), this.bWm.go(), this.bWm.gp(), this.bWl.gn(), this.bWl.go(), this.bWl.gp()).aqB();
        int[] nArray = qc_02.acJ();
        int n5 = this.bWl.gn();
        int n6 = this.bWl.go();
        short s3 = this.bWl.gp();
        this.bzY = 0;
        akd_0 akd_02 = null;
        aoq_0 aoq_02 = this.bdv.gV();
        this.bzX = 0;
        while (this.bzX < this.r && aoq_02.F(n4 = n5 + nArray[0], n3 = n6 + nArray[1]) && (s2 = auU.e(aoq_02.Em(), n4, n3, aoq_02.YF())) != Short.MIN_VALUE && (akd_02 = aoq_02.F(n4, n3, s2)) != null && (n2 = (s = akd_02.wp) - s3) <= 2) {
            if (aoq_02.bD(n4, n3)) {
                this.bAa = aoq_02.bK(n4, n3);
                break;
            }
            if (n2 < 0) {
                this.bzY -= n2;
            }
            n5 = akd_02.aG;
            n6 = akd_02.aH;
            s3 = s;
            ++this.bzX;
        }
        this.bzW = new ry(n5, n6, s3);
        this.bzZ = akd_02 == null;
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

    static /* synthetic */ ry a(azw_0 azw_02) {
        return azw_02.bzW;
    }

    static /* synthetic */ tl_2 b(azw_0 azw_02) {
        return azw_02.bAa;
    }

    static /* synthetic */ ry a(azw_0 azw_02, ry ry2) {
        azw_02.bzW = ry2;
        return azw_02.bzW;
    }

    static /* synthetic */ tl_2 a(azw_0 azw_02, tl_2 tl_22) {
        azw_02.bAa = tl_22;
        return azw_02.bAa;
    }
}

