/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from NA
 */
public class na_2
extends ZT {
    private static final acl_0 aU = new ym_0(new aaB());
    private int bzV = 0;
    private ry bzW;
    private int bzX;
    private int bzY;
    private boolean bzZ;
    private boolean rM = true;
    private tl_2 bAa = null;
    public aea_0 nC = new aaa_0(this, 18);

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

    public na_2 aaG() {
        na_2 na_22;
        try {
            na_22 = (na_2)aU.adr();
            na_22.uG = aU;
        }
        catch (Exception exception) {
            na_22 = new na_2();
            na_22.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un Push : " + exception.getMessage()));
        }
        na_22.rM = this.rM;
        return na_22;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (!this.rM) {
            this.aoy();
            super.a(xb_22, bl2);
            return;
        }
        ry ry2 = new ry(this.bWm.gn(), this.bWm.go(), this.bWm.gp());
        this.b(xb_22, bl2);
        if (((gn_0)this.bWm).Qa() && !this.bzW.equals(ry2)) {
            ((gn_0)this.bWm).bm(false);
        }
        this.bWm.m(this.bzW);
        if (this.bWv && this.bzV > 0) {
            ig_1 ig_12 = ig_1.a(this.bdv, fv_1.bal, this.bzV, this.bWm);
            ig_12.akd();
            ig_12.i(this.bWm);
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
            this.bdv.gX().a(ry2.getX(), ry2.getY(), ry2.wk(), this.bWm.gn(), this.bWm.go(), this.bWm.gp(), this.bWm);
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
        if (this.bWm != null && this.bWm instanceof gn_0) {
            gn_0 gn_02 = (gn_0)this.bWm;
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
        qc_0 qc_02 = new aby_2(this.bWl.gn(), this.bWl.go(), this.bWl.gp(), this.bWm.gn(), this.bWm.go(), this.bWm.gp()).aqB();
        int[] nArray = qc_02.acJ();
        int n5 = this.bWm.gn();
        int n6 = this.bWm.go();
        short s3 = this.bWm.gp();
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

    static /* synthetic */ ry a(na_2 na_22) {
        return na_22.bzW;
    }

    static /* synthetic */ tl_2 b(na_2 na_22) {
        return na_22.bAa;
    }

    static /* synthetic */ ry a(na_2 na_22, ry ry2) {
        na_22.bzW = ry2;
        return na_22.bzW;
    }

    static /* synthetic */ tl_2 a(na_2 na_22, tl_2 tl_22) {
        na_22.bAa = tl_22;
        return na_22.bAa;
    }
}

