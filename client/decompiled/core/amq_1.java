/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aMQ
 */
public class amq_1
extends avp_0 {
    private static amq_1 dYJ = new amq_1();
    private long dYK = 0L;
    private long agL = 0L;

    public static amq_1 aXh() {
        return dYJ;
    }

    public boolean a(pr_0 pr_02) {
        if (pr_02 instanceof axe_0) {
            axe_0 axe_02 = (axe_0)pr_02;
            if (this.agL == axe_02.aKD()) {
                this.aXk();
            }
            return false;
        }
        switch (pr_02.getId()) {
            case 16807: {
                rg_0 rg_02 = (rg_0)pr_02;
                wy_2 wy_22 = rg_02.hF();
                sj_1 sj_12 = apN.aDK().Ln();
                CG cG = (CG)sj_12.aQt();
                ky_2 ky_22 = sj_12.yD();
                if (cG.bw(wy_22.je())) {
                    if (!((xj)wy_22.NR()).tp() && ky_22.bU(wy_22.jf())) {
                        ua_2 ua_22 = new ua_2();
                        ua_22.cm(rg_02.fX());
                        ua_22.i(wy_22.jf());
                        ua_22.aR((short)1);
                        apN.aDK().vJ().b(ua_22);
                        afl_0 afl_02 = azs_0.aLV().getProperty("coachManagement.currentSet");
                        azs_0.aLV().a((aho_0)((fe_1)afl_02.getValue()), fe_1.aVa);
                    } else {
                        add_1.aOG().a(aon_0.aYc().getString("error.exchange.linkedCard"), 66L, 102, 1);
                    }
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("error.exchange.tooMuchCard", (byte)5), 66L, 102, 1);
                }
                return false;
            }
            case 16702: {
                ia_2 ia_22 = (ia_2)pr_02;
                sj_1 sj_13 = ia_22.Ln();
                wy_2 wy_23 = ia_22.lm();
                if (sj_13 != null && wy_23 != null) {
                    ua_2 ua_23 = new ua_2();
                    CG cG = (CG)sj_13.aQt();
                    if (cG != null) {
                        ua_23.cm((Long)cG.getFieldValue("exchangeId"));
                        ua_23.i(wy_23.jf());
                        ua_23.aR((short)1);
                        apN.aDK().vJ().b(ua_23);
                    }
                }
                return false;
            }
            case 16808: {
                rg_0 rg_03 = (rg_0)pr_02;
                wd_0 wd_02 = new wd_0();
                wd_02.cm(rg_03.fX());
                wd_02.i(rg_03.hF().jf());
                wd_02.aR((short)1);
                apN.aDK().vJ().b(wd_02);
                return false;
            }
            case 16809: {
                sb_0 sb_02 = (sb_0)pr_02;
                long l2 = sb_02.getLongValue();
                any any2 = new any();
                any2.cm(l2);
                apN.aDK().vJ().b(any2);
                return false;
            }
            case 16821: {
                if (this.dYK == 0L || System.currentTimeMillis() - this.dYK > 3000L) {
                    sb_0 sb_03 = (sb_0)pr_02;
                    long l3 = sb_03.getLongValue();
                    ahJ ahJ2 = new ahJ();
                    ahJ2.cm(l3);
                    apN.aDK().vJ().b(ahJ2);
                }
                return false;
            }
        }
        return super.a(pr_02);
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            if (apN.aDK().c(agn_0.awo())) {
                apN.aDK().b(agn_0.awo());
            }
            add_1.aOG().l("dofusarena.exchange", ado_2.class);
        }
        apN.aDK().a(pg_1.acm());
        super.a(fh_22, bl2);
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().kG("dofusarena.exchange");
            aam_1 aam_12 = aam_1.aMF();
            if (aam_12.isRunning()) {
                aam_12.en(this.agL);
            }
        }
        apN.aDK().b(pg_1.acm());
        super.b(fh_22, bl2);
    }

    protected void W() {
        add_1.aOG().kO("exchangeDialog");
    }

    protected void X() {
        add_1.aOG().a("exchangeDialog", oh_2.bq("exchangeDialog"), 1L, (short)10001);
    }

    public long aXi() {
        return this.dYK;
    }

    public void eQ(long l2) {
        this.dYK = l2;
    }

    public void aXj() {
        aam_1 aam_12 = aam_1.aMF();
        if (!aam_12.isRunning()) {
            aam_12.start();
        }
        if (aam_12.isRunning()) {
            this.agL = aam_12.a(this, 3000L, 0, 1);
        }
        CG cG = (CG)apN.aDK().Ln().aQt();
        cG.bd(false);
        azs_0.aLV().a((aho_0)cG, "readyButtonEnabled");
    }

    public void aXk() {
        CG cG = (CG)apN.aDK().Ln().aQt();
        cG.bd(true);
        azs_0.aLV().a((aho_0)cG, "readyButtonEnabled");
    }
}

