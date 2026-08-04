/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;

/*
 * Renamed from avu
 */
public class avu_0
implements atG {
    private static avu_0 dem = new avu_0();
    private r_0 den = null;
    private boolean deo = false;

    public static avu_0 aIB() {
        return dem;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 18000: {
                if (this.den == null) {
                    this.den = add_1.aOG().a(aon_0.aYc().getString("question.giveUpFight"), 1176L, 102, 1);
                    this.den.a(new abl_1(this));
                }
                return false;
            }
            case 18018: {
                apN.aDK().b(S.as());
                apN.aDK().b(agd_1.awz());
                apN.aDK().b(alx_2.aWN());
                apN.aDK().b(abt_1.aNp());
                apN.aDK().a(ae_2.az());
                ((xu_2)DofusArenaClientInstance.yl().YP()).cD(true);
                return false;
            }
            case 18012: {
                ayd_0 ayd_02 = (ayd_0)pr_02;
                ee_2 ee_22 = ayd_02.tG();
                if (ee_22 != null) {
                    afl_0 afl_02 = azs_0.aLV().getProperty("fight.timeline.selectedFighter");
                    if (afl_02 != null && ee_22.equals(afl_02.getValue()) && add_1.aOG().kR("fighterInformationsDialog")) {
                        add_1.aOG().kO("fighterInformationsDialog");
                    } else {
                        boolean bl2;
                        adu_0 adu_02 = (adu_0)ee_22.Oc();
                        boolean bl3 = bl2 = adu_02 != null && adu_02.asC() && ((aez_0)ee_22.LQ()).getId() != ((aez_0)azs_0.aLV().getProperty("localCoach").getValue()).getId();
                        if (!bl2) {
                            azs_0.aLV().g("fight.timeline.selectedFighter", ee_22);
                            if (!add_1.aOG().kR("fighterInformationsDialog")) {
                                add_1.aOG().a("fighterInformationsDialog", oh_2.bq("fighterInformationsDialog"), 0L, (short)10000);
                            }
                        }
                    }
                }
                return false;
            }
            case 18015: {
                kq kq2 = (kq)pr_02;
                tO tO2 = kq2.oQ();
                if (tO2 != null) {
                    afl_0 afl_03 = azs_0.aLV().getProperty("singleCardData");
                    if (afl_03 != null && afl_03.getValue() != null && afl_03.getValue().equals(tO2) && add_1.aOG().kR("singleCardDialog")) {
                        add_1.aOG().kO("singleCardDialog");
                    } else {
                        azs_0.aLV().g("singleCardData", tO2);
                        if (!add_1.aOG().kR("singleCardDialog")) {
                            add_1.aOG().a("singleCardDialog", oh_2.bq("singleCardDialog"), 0L, (short)10100);
                        }
                    }
                }
                return false;
            }
            case 16624: {
                da_1 da_12 = (da_1)pr_02;
                yp_2 yp_22 = da_12.fw();
                if (yp_22 != null) {
                    azs_0.aLV().g("singleCardData", yp_22);
                }
                return false;
            }
            case 16625: {
                azs_0.aLV().g("singleCardData", (Object)null);
                return false;
            }
            case 16622: {
                pd_2 pd_22 = (pd_2)pr_02;
                ve_0 ve_02 = pd_22.abQ();
                if (ve_02 != null) {
                    azs_0.aLV().g("singleCardData", ve_02);
                }
                return false;
            }
            case 16623: {
                azs_0.aLV().g("singleCardData", (Object)null);
                return false;
            }
            case 18016: {
                ayd_0 ayd_03 = (ayd_0)pr_02;
                ee_2 ee_23 = ayd_03.tG();
                if (ee_23 != null) {
                    ee_23.NW().BX();
                }
                return false;
            }
            case 18017: {
                ayd_0 ayd_04 = (ayd_0)pr_02;
                ee_2 ee_24 = ayd_04.tG();
                if (ee_24 != null) {
                    ee_24.NW().BY();
                }
                return false;
            }
            case 18019: {
                adu_0 adu_03 = apN.aDK().aDL();
                if (adu_03 != null) {
                    boolean bl4 = this.deo = !this.deo;
                    if (this.deo) {
                        adu_03.d(new abJ(this));
                    } else {
                        adu_03.d(new abO(this));
                    }
                }
                return false;
            }
            case 5301: {
                add_1 add_12 = add_1.aOG();
                if (add_12 != null) {
                    if (add_12.kR("consoleDialog")) {
                        add_12.kO("consoleDialog");
                    } else {
                        add_12.a("consoleDialog", oh_2.bq("consoleDialog"), 1025L, (short)30000);
                    }
                }
                return false;
            }
        }
        return true;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public boolean aIC() {
        return this.deo;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().l("dofusarena.fight", aek_2.class);
            apN.aDK().a(bq_2.cF());
            azs_0.aLV().g("fight.timeline.display", true);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().aPa();
            apN.aDK().b(bq_2.cF());
            add_1.aOG().kO("fightEventCardsDialog");
            add_1.aOG().kO("timelineDialog");
            add_1.aOG().kO("fighterInformationsDialog");
            add_1.aOG().kO("singleCardDialog");
            add_1.aOG().aPa();
            azs_0.aLV().kb("fight.status");
            if (this.den != null) {
                this.den.D();
                this.den = null;
            }
        }
    }

    static /* synthetic */ r_0 a(avu_0 avu_02, r_0 r_02) {
        avu_02.den = r_02;
        return avu_02.den;
    }
}

