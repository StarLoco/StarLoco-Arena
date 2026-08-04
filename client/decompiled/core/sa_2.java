/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Sa
 */
public class sa_2
extends ZT {
    private static final acl_0 aU = new ym_0(new dv_2());
    private int bzV = 0;
    private ry bzW;
    private boolean rM = true;
    public aea_0 nC = new dx(this, 10);

    public void b() {
        super.b();
        this.bzV = 0;
        this.rM = true;
    }

    public sa_2 aeF() {
        sa_2 sa_22;
        try {
            sa_22 = (sa_2)aU.adr();
            sa_22.uG = aU;
        }
        catch (Exception exception) {
            sa_22 = new sa_2();
            sa_22.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un Pull : " + exception.getMessage()));
        }
        sa_22.rM = this.rM;
        return sa_22;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (!this.rM) {
            this.aoy();
            super.a(xb_22, bl2);
            return;
        }
        if (this.bWm != null && this.bWm instanceof gn_0) {
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
            }
            if (this.bdv.gX() != null) {
                this.bdv.gX().a(ry2.getX(), ry2.getY(), ry2.wk(), this.bWm.gn(), this.bWm.go(), this.bWm.gp(), this.bWm);
            }
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        switch (((xj_0)this.bWj).Tb().length) {
            case 1: {
                this.r = (int)((xj_0)this.bWj).Tb()[0];
                break;
            }
            default: {
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un Pull : " + ((xj_0)this.bWj).Tb().length));
                this.r = 0;
            }
        }
        if (this.bWm instanceof gn_0) {
            gn_0 gn_02 = (gn_0)this.bWm;
            if (gn_02.PL().b((aak_2)avx_0.dev)) {
                this.rM = false;
                return;
            }
            this.aaH();
        }
    }

    private void aaH() {
        int n2;
        int n3;
        qc_0 qc_02 = new aby_2(this.bWm.gn(), this.bWm.go(), this.bWm.gp(), this.bWl.gn(), this.bWl.go(), this.bWl.gp()).aqB();
        int[] nArray = qc_02.acJ();
        aoq_0 aoq_02 = this.bdv.gV();
        gn_0 gn_02 = (gn_0)this.bWl;
        ry ry2 = gn_02.gg();
        akd_0 akd_02 = null;
        int n4 = this.bWm.gn();
        int n5 = this.bWm.go();
        short s = this.bWm.gp();
        int n6 = 0;
        for (n3 = 0; n3 < this.r; ++n3) {
            int n7;
            n2 = n4 + nArray[0];
            int n8 = n5 + nArray[1];
            short s2 = auU.e(aoq_02.Em(), n2, n8, aoq_02.YF());
            if (s2 == Short.MIN_VALUE || (akd_02 = aoq_02.F(n2, n8, s2)) == null || akd_02.aG == ry2.getX() && akd_02.aH == ry2.getY()) break;
            short s3 = akd_02.wp;
            if (aoq_02.bD(n2, n8) || (n7 = s3 - s) > 2) break;
            if (n7 < 0) {
                n6 -= n7;
            }
            n4 = akd_02.aG;
            n5 = akd_02.aH;
            s = s3;
        }
        this.bzW = new ry(n4, n5, s);
        this.bzV = n6;
        if ((akd_02 == null || akd_02 != null && (akd_02.aG != ry2.getX() || akd_02.aH != ry2.getY())) && (n2 = this.r - n3) > 0) {
            this.bzV += n2 * (akd_02 == null ? 3 : 3);
        }
        this.r = n3;
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

    static /* synthetic */ ry a(sa_2 sa_22) {
        return sa_22.bzW;
    }

    static /* synthetic */ ry a(sa_2 sa_22, ry ry2) {
        sa_22.bzW = ry2;
        return sa_22.bzW;
    }
}

