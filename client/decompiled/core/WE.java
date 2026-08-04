/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class WE
implements atG {
    protected static final Logger a = Logger.getLogger(WE.class);
    private static WE bUL = new WE();

    public static WE aji() {
        return bUL;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 4520: {
                cd_2 cd_22 = (cd_2)pr_02;
                byte by = cd_22.N().lV();
                lC lC2 = new lC(cd_22.Ao(), by, cd_22.M());
                lC2.bC(cd_22.K());
                vr_0.aiM().b(lC2);
                ny_2.sR().println("403|" + ny_2.cu(ny_2.Qo) + "|" + cd_22.Ao() + "|" + by + "|" + cd_22.M() + "|" + cd_22.K() + "|");
                return false;
            }
            case 8200: {
                akb_2 akb_22 = vr_0.aiM().aiO();
                if (akb_22 == null) {
                    return false;
                }
                vr_0.aiM().aiQ();
                return false;
            }
            case 8300: {
                YP yP = (YP)pr_02;
                adu_0 adu_02 = apN.aDK().aDL();
                if (adu_02 != null) {
                    ArrayList<al_1> arrayList;
                    byte by = yP.N().lV();
                    apN.aDK().b(anx_1.aXx());
                    y_0 y_02 = new y_0(yP.Ao(), by, yP.M(), adu_02, yP.amP(), yP.amQ(), yP.amR(), yP.amS(), yP.amT(), yP.amU(), yP.amV());
                    azs_0.aLV().g("fight.standingWon", "+ " + yP.amW());
                    sj_1 sj_12 = apN.aDK().Ln();
                    int n2 = sj_12.afQ() + yP.amW();
                    boolean bl2 = aet_0.nJ(sj_12.afQ()) < aet_0.nJ(n2);
                    sj_12.hR(n2);
                    if (bl2) {
                        add_1.aOG().a("coachLevelUpDialog", oh_2.bq("coachLevelUpDialog"), 257L, (short)10010);
                    }
                    if (adu_02.aKl() == 6) {
                        arrayList = new ArrayList<al_1>();
                        arrayList.add(new al_1("Death", yP.amY()));
                        arrayList.add(new al_1("Injury", yP.amX()));
                        azs_0.aLV().g("selectedEndFightAchievement", arrayList.get(0));
                        azs_0.aLV().g("fight.endFightAchievements", arrayList.toArray());
                    }
                    ArrayList<al_1> arrayList2 = arrayList = adu_02.aKl() == 5 ? ahy_1.axg().dC(adu_02.asy()) : null;
                    if (arrayList != null) {
                        azs_0.aLV().g("endFightChallenge", arrayList);
                        if (((afz_0)((Object)arrayList)).QE() > 0) {
                            azs_0.aLV().g("numberOfTurnsInFight", adu_02.ass().JI());
                            int n3 = adu_02.ass().JI() / 10;
                            if (n3 > 3) {
                                n3 = 3;
                            }
                            azs_0.aLV().g("endFightTurnAchievementName", aon_0.aYc().getString("endFightTurnAchievementName" + n3));
                            azs_0.aLV().g("endFightTurnAchievementDescription", aon_0.aYc().getString("endFightTurnAchievementDescription" + n3));
                            int n4 = (adu_02.ass().JI() + 1) / 2 * (adu_02.ass().JI() / 2 + 1) * ((afz_0)((Object)arrayList)).QE();
                            azs_0.aLV().g("endFightGainedXp", n4);
                            Object object = xz_0.amc().afE();
                            for (long l2 : ((aba_0)object).eJ()) {
                                ee_2 ee_22 = adY.atu().dz(l2);
                                if (ee_22 == null || ee_22.NB() != 0) continue;
                                ee_22.ft(n4);
                            }
                        }
                    }
                    if (adu_02.aKl() == 6) {
                        en_1 en_12 = sj_12.aQn().pH();
                        for (Object object : en_12) {
                            if (((xj)((eb_1)object).NR()).tr() >= sj_12.getLevel() || sj_12.aQm().contains(Math.abs(((eb_1)object).jf()))) continue;
                            sj_12.aQm().d(((eb_1)object).jf(), (byte)1);
                        }
                    }
                    vr_0.aiM().b(y_02);
                    vr_0.aiM().aiQ();
                    ny_2.sR().print("500|" + ny_2.cu(ny_2.Qp) + "|" + yP.amP().size() + "|");
                    for (anp_2 anp_22 : yP.amP()) {
                        ny_2.sR().print(ny_2.au(anp_22.getId()) + "|");
                    }
                    ny_2.sR().print(yP.amQ().size() + "|");
                    for (anp_2 anp_23 : yP.amQ()) {
                        ny_2.sR().print(ny_2.au(anp_23.getId()) + "|");
                    }
                    ny_2.sR().println(adu_02.getDuration() + "|");
                    ny_2.sR().close();
                    a.info((Object)("fin d'un combat de type " + adu_02.aKl()));
                    sj_1 sj_13 = apN.aDK().Ln();
                    if (sj_13.yQ()) {
                        sj_13.ax(false);
                        ajw_0 ajw_02 = new ajw_0();
                        ajw_02.aj(sj_13.getId());
                        ajw_02.C((short)99);
                        apN.aDK().vJ().b(ajw_02);
                        apN.aDK().a(wp_0.CH());
                    }
                } else {
                    a.error((Object)"Il n'existe aucun combat \u00e0 terminer !");
                }
                return false;
            }
            case 26332: {
                apN.aDK().aDL().aKt();
                return false;
            }
            case 8400: {
                adu_0 adu_03 = apN.aDK().aDL();
                if (adu_03 != null) {
                    r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("leaveFightAndLaunchMatchFinderQuestion"), 1176L, 102, 1);
                    r_02.a(new xo_0(this));
                } else {
                    a.error((Object)"Il n'existe aucun combat en cours !");
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

    public void a(fh_2 fh_22, boolean bl2) {
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }
}

