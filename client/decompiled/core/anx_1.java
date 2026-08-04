/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;

/*
 * Renamed from aNx
 */
public class anx_1
implements atG {
    private static anx_1 dZq = new anx_1();
    private static final long dZr = 1000L;
    private static final int dZs = 0;
    private static final int dZt = 1;
    private long agL = 0L;

    public static anx_1 aXx() {
        return dZq;
    }

    public boolean a(pr_0 pr_02) {
        if (pr_02 instanceof axe_0) {
            axe_0 axe_02 = (axe_0)pr_02;
            if (this.agL == axe_02.aKD()) {
                azs_0.aLV().g("fight.endTurnState", true);
            }
            return false;
        }
        switch (pr_02.getId()) {
            case 18001: {
                ayd_0 ayd_02 = (ayd_0)pr_02;
                ee_2 ee_22 = ayd_02.tG();
                if (ee_22 != null) {
                    long l2 = ee_22.getId();
                    ee_2 ee_23 = (ee_2)apN.aDK().aDL().ass().nP();
                    if (ee_23 != null && ee_23.getId() == l2) {
                        rC rC2 = new rC();
                        rC2.j(l2);
                        apN.aDK().vJ().b(rC2);
                        azs_0.aLV().g("fight.endTurnState", false);
                    }
                }
                return false;
            }
            case 18003: {
                ayd_0 ayd_03 = (ayd_0)pr_02;
                ee_2 ee_24 = ayd_03.tG();
                if (ee_24 != null) {
                    lr_2 lr_22 = new lr_2();
                    lr_22.j(ee_24.getId());
                    lr_22.a(qc_0.bEM);
                    apN.aDK().vJ().b(lr_22);
                }
                return false;
            }
            case 18002: {
                ayd_0 ayd_04 = (ayd_0)pr_02;
                ee_2 ee_25 = ayd_04.tG();
                if (ee_25 != null) {
                    lr_2 lr_23 = new lr_2();
                    lr_23.j(ee_25.getId());
                    lr_23.a(qc_0.bEK);
                    apN.aDK().vJ().b(lr_23);
                }
                return false;
            }
            case 18004: {
                ayd_0 ayd_05 = (ayd_0)pr_02;
                ee_2 ee_26 = ayd_05.tG();
                if (ee_26 != null) {
                    lr_2 lr_24 = new lr_2();
                    lr_24.j(ee_26.getId());
                    lr_24.a(qc_0.bEO);
                    apN.aDK().vJ().b(lr_24);
                }
                return false;
            }
            case 18005: {
                ayd_0 ayd_06 = (ayd_0)pr_02;
                ee_2 ee_27 = ayd_06.tG();
                if (ee_27 != null) {
                    lr_2 lr_25 = new lr_2();
                    lr_25.j(ee_27.getId());
                    lr_25.a(qc_0.bEQ);
                    apN.aDK().vJ().b(lr_25);
                }
                return false;
            }
            case 18006: {
                da_1 da_12 = (da_1)pr_02;
                ee_2 ee_28 = da_12.tG();
                yp_2 yp_22 = da_12.fw();
                if (ee_28 != null && yp_22 != null) {
                    yp_2 yp_23;
                    boolean bl2 = true;
                    if (yp_22 instanceof zd_2) {
                        yp_23 = (zd_2)yp_22;
                        bl2 = ((zd_2)yp_23).anm().isValid();
                    }
                    if (bl2) {
                        apN.aDK().a(S.as());
                        apN.aDK().b(ae_2.az());
                        apN.aDK().b(agd_1.awz());
                        apN.aDK().b(abt_1.aNp());
                        yp_23 = alx_2.aWN().aWO();
                        if (apN.aDK().c(alx_2.aWN()) && yp_23 != null && yp_23.equals(yp_22)) {
                            apN.aDK().b(alx_2.aWN());
                            mb_0.Yl().hide();
                        } else {
                            alx_2.aWN().e(yp_22);
                            alx_2.aWN().b(ee_28);
                            alx_2.aWN().mj();
                            alx_2.aWN().ml();
                            apN.aDK().a(alx_2.aWN());
                        }
                    } else {
                        this.aXy();
                    }
                }
                return false;
            }
            case 18007: {
                lf_0 lf_02 = (lf_0)pr_02;
                ee_2 ee_29 = lf_02.tG();
                ve_0 ve_02 = lf_02.qu();
                if (ee_29 != null && ve_02 != null) {
                    ve_0 ve_03;
                    boolean bl3 = true;
                    if (ve_02 instanceof on_2) {
                        ve_03 = (on_2)ve_02;
                        bl3 = ((on_2)ve_03).tH().isValid();
                    }
                    if (bl3) {
                        apN.aDK().a(S.as());
                        apN.aDK().b(ae_2.az());
                        apN.aDK().b(agd_1.awz());
                        apN.aDK().b(alx_2.aWN());
                        ve_03 = abt_1.aNp().aNq();
                        if (apN.aDK().c(abt_1.aNp()) && ve_03 != null && ve_03.equals(ve_02)) {
                            apN.aDK().b(abt_1.aNp());
                            mb_0.Yl().hide();
                        } else {
                            abt_1.aNp().f(ve_02);
                            abt_1.aNp().b(ee_29);
                            abt_1.aNp().mj();
                            apN.aDK().a(abt_1.aNp());
                        }
                    } else {
                        this.aXy();
                    }
                }
                return false;
            }
            case 18008: {
                ayd_0 ayd_07 = (ayd_0)pr_02;
                ee_2 ee_210 = ayd_07.tG();
                if (ee_210 != null) {
                    if (ee_210.Oc().a(ee_210, null).isValid()) {
                        apN.aDK().a(S.as());
                        apN.aDK().b(ae_2.az());
                        apN.aDK().b(abt_1.aNp());
                        apN.aDK().b(alx_2.aWN());
                        if (apN.aDK().c(agd_1.awz())) {
                            apN.aDK().b(agd_1.awz());
                            mb_0.Yl().hide();
                        } else {
                            agd_1.awz().b(ee_210);
                            agd_1.awz().mj();
                            apN.aDK().a(agd_1.awz());
                        }
                    } else {
                        this.aXy();
                    }
                }
                return false;
            }
            case 18013: {
                da_1 da_13 = (da_1)pr_02;
                afl_0 afl_02 = azs_0.aLV().getProperty("singleCardData");
                if (afl_02 != null && da_13.fw().equals(afl_02.getValue()) && add_1.aOG().kR("singleCardDialog")) {
                    add_1.aOG().kO("singleCardDialog");
                } else {
                    azs_0.aLV().g("singleCardData", da_13.fw());
                    add_1.aOG().kO("fighterInformationsDialog");
                    add_1.aOG().a("singleCardDialog", oh_2.bq("singleCardDialog"), 1L, (short)10100);
                }
                return false;
            }
            case 18014: {
                lf_0 lf_03 = (lf_0)pr_02;
                azs_0.aLV().g("singleCardData", lf_03.qu());
                add_1.aOG().kO("fighterInformationsDialog");
                add_1.aOG().a("singleCardDialog", oh_2.bq("singleCardDialog"), 1L, (short)10100);
                return false;
            }
        }
        return true;
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            aam_1 aam_12 = aam_1.aMF();
            if (!aam_12.isRunning()) {
                aam_12.start();
            }
            if (aam_12.isRunning()) {
                this.agL = aam_12.a(this, 1000L, 0, 1);
            }
            azs_0.aLV().g("fight.endTurnState", false);
            ((xu_2)DofusArenaClientInstance.yl().YP()).cD(true);
            add_1.aOG().a("fighterControlsDialog", oh_2.bq("fighterControlsDialog"), (short)10100);
            if (!apN.aDK().c(ae_2.az())) {
                apN.aDK().a(S.as());
            }
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            aam_1 aam_12 = aam_1.aMF();
            if (aam_12.isRunning()) {
                aam_12.en(this.agL);
            }
            azs_0.aLV().kb("fight.endTurnState");
            if (!apN.aDK().c(ae_2.az())) {
                ((xu_2)DofusArenaClientInstance.yl().YP()).cD(false);
            }
            add_1.aOG().kO("fighterControlsDialog");
            add_1.aOG().kO("singleCardDialog");
            add_1.aOG().aPa();
            mb_0.Yl().hide();
            apN.aDK().b(S.as());
            apN.aDK().b(alx_2.aWN());
            apN.aDK().b(agd_1.awz());
            apN.aDK().b(abt_1.aNp());
        }
    }

    private void aXy() {
        if (apN.aDK().c(agd_1.awz())) {
            apN.aDK().b(agd_1.awz());
        }
        if (apN.aDK().c(abt_1.aNp())) {
            apN.aDK().b(abt_1.aNp());
        }
        if (apN.aDK().c(alx_2.aWN())) {
            apN.aDK().b(alx_2.aWN());
        }
        mb_0.Yl().hide();
    }
}

