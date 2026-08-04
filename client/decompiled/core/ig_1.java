/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Ig
 */
public class ig_1
extends ZT {
    private static final acl_0 aU = new ym_0(new aqh_0());
    protected fv_1 bS;
    protected int bgm;
    protected boolean bgn = false;
    protected boolean bgo = true;
    protected boolean bgp = false;
    protected boolean bgq = false;

    protected ig_1() {
    }

    public ig_1(fv_1 fv_12) {
        this.bS = fv_12;
        this.aG();
    }

    public ig_1(fv_1 fv_12, boolean bl2) {
        this.bS = fv_12;
        this.bgq = bl2;
        this.aG();
    }

    private static ig_1 a(ea_0 ea_02, fv_1 fv_12, int n2, kc_2 kc_22, boolean bl2) {
        ig_1 ig_12;
        try {
            ig_12 = (ig_1)aU.adr();
            ig_12.uG = aU;
        }
        catch (Exception exception) {
            ig_12 = new ig_1(fv_12);
            ig_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un " + ig_1.class.getSimpleName() + " : " + exception.getMessage()));
        }
        switch (fv_12) {
            case bal: {
                ig_12.aW = mh_2.bun.getId();
                ig_12.bWr = ((ZT)mh_2.bun.getObject()).Oz();
                break;
            }
            case bam: {
                ig_12.aW = mh_2.buo.getId();
                ig_12.bWr = ((ZT)mh_2.buo.getObject()).Oz();
                break;
            }
            case ban: {
                ig_12.aW = mh_2.buq.getId();
                ig_12.bWr = ((ZT)mh_2.buq.getObject()).Oz();
                break;
            }
            case bao: {
                ig_12.aW = mh_2.bur.getId();
                ig_12.bWr = ((ZT)mh_2.bur.getObject()).Oz();
                break;
            }
            case bap: {
                ig_12.aW = mh_2.bup.getId();
                ig_12.bWr = ((ZT)mh_2.bup.getObject()).Oz();
            }
        }
        ig_12.bS = fv_12;
        ig_12.bWm = kc_22;
        ig_12.r = n2;
        ig_12.bgm = 0;
        ig_12.bgp = false;
        ig_12.ahI = -1;
        ig_12.bdv = ea_02;
        ig_12.bgo = true;
        ig_12.bgn = false;
        ig_12.bgq = bl2;
        ig_12.aG();
        return ig_12;
    }

    public static ig_1 a(ea_0 ea_02, fv_1 fv_12, int n2, kc_2 kc_22) {
        return ig_1.a(ea_02, fv_12, n2, kc_22, false);
    }

    public static ig_1 b(ea_0 ea_02, fv_1 fv_12, int n2, kc_2 kc_22) {
        return ig_1.a(ea_02, fv_12, n2, kc_22, true);
    }

    public ig_1 Hl() {
        ig_1 ig_12;
        try {
            ig_12 = (ig_1)aU.adr();
            ig_12.uG = aU;
        }
        catch (Exception exception) {
            ig_12 = new ig_1();
            ig_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un " + ig_1.class.getSimpleName() + " : " + exception.getMessage()));
        }
        ig_12.a(this);
        return ig_12;
    }

    public void a(ig_1 ig_12) {
        super.g(ig_12);
        this.bS = ig_12.bS;
        this.bgp = ig_12.Ua();
        this.bgm = ig_12.Ub();
        this.bgo = ig_12.TZ();
        this.bgn = false;
        this.bgq = ig_12.bgq;
    }

    public void aG() {
        super.aG();
        this.bWt.set(2);
        if (this.bgq) {
            this.bWt.set(14);
        }
        if (this.bS != null) {
            switch (this.bS) {
                case bal: {
                    break;
                }
                case bap: {
                    this.bWt.set(8);
                    break;
                }
                case bam: {
                    this.bWt.set(5);
                    break;
                }
                case ban: {
                    this.bWt.set(6);
                    break;
                }
                case bao: {
                    this.bWt.set(7);
                }
            }
        }
    }

    public void a(int n2, float f, boolean bl2) {
        super.a(n2, f, bl2);
        switch (n2) {
            case 0: {
                if (bl2) break;
                this.r += us_0.U((float)this.r * f / 100.0f);
                break;
            }
            case 1: {
                if (!bl2) {
                    this.r = (int)((float)this.r + f);
                    break;
                }
                this.r = us_0.U(f);
                break;
            }
        }
        this.r = Math.max(0, this.r);
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.r != 0 && this.bWm.b(Lr.bqx) && !this.bWm.b(avx_0.dey)) {
            if (!(this instanceof ang_0)) {
                this.TY();
            }
        } else {
            this.aoy();
        }
        if (this.bgn) {
            this.aoy();
        } else {
            super.a(xb_22, bl2);
        }
    }

    public void TY() {
        if (this.bgm > 0 && !this.bgp && this.bS != fv_1.bal && this.bWl != null && this.bWl != this.bWm) {
            ig_1 ig_12 = ig_1.a(this.bdv, fv_1.bal, this.bgm, this.bWl);
            ig_12.akd();
            ig_12.bF(true);
            ig_12.i(this.bWl);
            ig_12.release();
        }
        if (!this.bWm.PR()) {
            this.bWm.a(Lr.bqx).ka(this.r);
        } else {
            this.bgn = true;
        }
        if (((gn_0)this.bWm).NY() == xq.axQ) {
            ((gn_0)this.bWm).LQ().l(or_0.Xt.tI(), (short)-1);
        }
        if (this.bWl != null && this.bWl instanceof gn_0 && ((gn_0)this.bWl).LQ().Le() != ((gn_0)this.bWm).LQ().Le()) {
            ((gn_0)this.bWl).LQ().l(or_0.VT.tI(), (short)this.r);
            ((gn_0)this.bWl).LQ().l(or_0.aaf.tI(), (short)this.r);
            switch (this.bS) {
                case bap: {
                    ((gn_0)this.bWl).LQ().l(or_0.WH.tI(), (short)this.r);
                    break;
                }
                case bam: {
                    ((gn_0)this.bWl).LQ().l(or_0.WG.tI(), (short)this.r);
                    break;
                }
                case bao: {
                    ((gn_0)this.bWl).LQ().l(or_0.WI.tI(), (short)this.r);
                    break;
                }
                case ban: {
                    ((gn_0)this.bWl).LQ().l(or_0.WJ.tI(), (short)this.r);
                }
            }
            if ((this.bWm.PR() || this.bWm.d(Lr.bqx) <= 0) && this.bWm instanceof gn_0 && !((gn_0)this.bWl).Dk() && ((gn_0)this.bWl).LQ().Le() != ((gn_0)this.bWm).LQ().Le()) {
                ((gn_0)this.bWl).LQ().l(or_0.aae.tI(), (short)1);
            }
        }
    }

    public boolean TZ() {
        return this.bgo;
    }

    public void bE(boolean bl2) {
        this.bgo = bl2;
    }

    public void a(xb_2 xb_22) {
        if (this.bWm == null || !this.bWm.b(Lr.bqx) || this.bWm.b(avx_0.dey)) {
            this.r = 0;
            return;
        }
        if (this.bWl instanceof gn_0 && this.bWm instanceof gn_0 && !mv_1.a((gn_0)this.bWl, this.akO(), (gn_0)this.bWm)) {
            this.r = 0;
            return;
        }
        switch (((xj_0)this.bWj).Tb().length) {
            case 1: {
                this.r = (int)((xj_0)this.bWj).Tb()[0];
                break;
            }
            case 3: {
                this.r = ou_1.A((int)((xj_0)this.bWj).iY(0), (int)((xj_0)this.bWj).iY(1), (int)((xj_0)this.bWj).iY(2));
                break;
            }
            default: {
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un HPLoss : " + ((xj_0)this.bWj).Tb().length));
                this.r = 0;
            }
        }
        boolean bl2 = this.bWj != null && ((xj_0)this.bWj).SX() && this.TZ() && !((gn_0)this.bWm).PL().b((aak_2)avx_0.deF);
        this.r = ig_1.a(this.r, this.bWl, this.bWm, this.bWn, this.bS, bl2, ((xj_0)this.ajO()).alM().fj().aoa());
        if (this.bWm.b(Lr.brc)) {
            int n2 = this.bWm.d(Lr.brc);
            this.bgm = us_0.U((float)(this.r * n2) / 100.0f);
        }
    }

    public static int a(float f, kc_2 kc_22, kc_2 kc_23, ry ry2, fv_1 fv_12, boolean bl2, short s) {
        abf_0 abf_02;
        int n2 = 0;
        int n3 = 0;
        if (fv_12 != fv_1.bal && bl2 && (abf_02 = ry2 != null ? kc_23.Qj().b(new agv_0(kc_22.gn(), kc_22.go(), kc_22.gp(), ry2.getX(), ry2.getY(), ry2.wk())) : kc_23.Qj().c(kc_22.gn(), kc_22.go(), kc_22.gp())) != null) {
            switch (abf_02.aNd()) {
                case 2: {
                    n2 += 40;
                    break;
                }
                case 1: 
                case 3: {
                    n2 += 20;
                    break;
                }
            }
        }
        switch (fv_12) {
            case bal: {
                break;
            }
            case bam: {
                n3 += ig_1.a(Lr.brb, Lr.bqF, Lr.bra, Lr.bqB, kc_22, kc_23);
                f += (float)ig_1.a(Lr.bqO, Lr.bqP, Lr.bqJ, Lr.bqK, kc_22, kc_23);
                break;
            }
            case bap: {
                n3 += ig_1.a(Lr.brb, Lr.bqH, Lr.bra, Lr.bqD, kc_22, kc_23);
                f += (float)ig_1.a(Lr.bqO, Lr.bqR, Lr.bqJ, Lr.bqM, kc_22, kc_23);
                break;
            }
            case ban: {
                n3 += ig_1.a(Lr.brb, Lr.bqG, Lr.bra, Lr.bqC, kc_22, kc_23);
                f += (float)ig_1.a(Lr.bqO, Lr.bqQ, Lr.bqJ, Lr.bqL, kc_22, kc_23);
                break;
            }
            case bao: {
                n3 += ig_1.a(Lr.brb, Lr.bqI, Lr.bra, Lr.bqE, kc_22, kc_23);
                f += (float)ig_1.a(Lr.bqO, Lr.bqS, Lr.bqJ, Lr.bqN, kc_22, kc_23);
            }
        }
        if (kc_23.b(Lr.brl) && s != zg_1.cdv.aoa() && s != zg_1.cdF.aoa()) {
            n3 -= kc_23.a(Lr.brl).atR();
        }
        f = f * (float)(100 + n2 + n3) / 100.0f;
        return Math.max(0, us_0.U(f));
    }

    public static int a(Lr lr, Lr lr2, Lr lr3, Lr lr4, kc_2 kc_22, kc_2 kc_23) {
        int n2 = 0;
        int n3 = 0;
        if (kc_22.b(lr)) {
            n2 += kc_22.a(lr).atR();
        }
        if (kc_22.b(lr2)) {
            n2 += kc_22.a(lr2).atR();
        }
        if (n2 != 0) {
            n3 += gn_0.a(n2, lr2);
        }
        n2 = 0;
        if (kc_23.b(lr3)) {
            n2 += kc_23.a(lr3).atR();
        }
        if (kc_23.b(lr4)) {
            n2 += kc_23.a(lr4).atR();
        }
        if (n2 != 0) {
            n3 -= gn_0.a(n2, lr4);
        }
        return n3;
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

    public fv_1 aL() {
        return this.bS;
    }

    public boolean Ua() {
        return this.bgp;
    }

    public void bF(boolean bl2) {
        this.bgp = bl2;
    }

    public int Ub() {
        return this.bgm;
    }

    public boolean Uc() {
        return this.bgq;
    }
}

