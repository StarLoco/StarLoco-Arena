/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;

public class zN
implements atG {
    private static boolean aGf = true;
    protected static final Logger a = Logger.getLogger(zN.class);
    private static final add_1 auz = add_1.aOG();
    private static zN aGg = new zN();

    public static void aY(boolean bl2) {
        aGf = bl2;
    }

    public static zN GN() {
        return aGg;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 22000: {
                ade_0 ade_02 = (ade_0)pr_02;
                aau_1 aau_12 = avq_0.ce(ade_02.adP());
                if (aau_12 != null && !aau_12.isHidden()) {
                    String string = aon_0.aYc().a(37, ade_02.adP(), new Object[0]);
                    String string2 = asf_0.b(aau_12);
                    gc_2 gc_22 = new gc_2(aon_0.aYc().getString("achievementUnlocked", string), string2);
                    if (aGf) {
                        iz_1.Vg().b(gc_22);
                        azs_0.aLV().a((aho_0)iz_1.Vg(), iz_1.ce);
                    }
                    short[] sArray = aau_12.aoW().Gj();
                    aGz aGz2 = apN.aDK().Ln().qI();
                    for (int j = 0; j < sArray.length; ++j) {
                        aGz2.A(sArray[j], aau_12.aoW().cp(sArray[j]));
                    }
                    if (aGf && aau_12.eA() != 0 && anr_0.aXN().j(aau_12.eA(), true) != null) {
                        anr_0.aXN().a(aau_12.eA(), 0, ug_2.bQg, false);
                    }
                }
                return false;
            }
            case 15005: {
                wt_2 wt_22 = (wt_2)pr_02;
                jI jI2 = new jI(aon_0.aYc().getString("infos.newMails", wt_22.ajK()));
                iz_1.Vg().b(jI2);
                azs_0.aLV().a((aho_0)iz_1.Vg(), iz_1.ce);
                return false;
            }
            case 17005: {
                aef_1 aef_12 = (aef_1)pr_02;
                iz_0 iz_02 = aef_12.aPT();
                aan_1 aan_12 = null;
                if (iz_02.getType() == 2) {
                    aan_12 = new hf_2(aon_0.aYc().a(35, iz_02.Bo(), new Object[0]));
                } else if (iz_02.getType() == 1) {
                    aan_12 = new yM(((wk_1)iz_02).getMessage());
                    System.out.println(((wk_1)iz_02).getMessage());
                }
                iz_1.Vg().b(aan_12);
                azs_0.aLV().a((aho_0)iz_1.Vg(), iz_1.ce);
                return false;
            }
            case 558: {
                ahU ahU2 = (ahU)pr_02;
                aff_1 aff_12 = new aff_1(aon_0.aYc().getString("infos.guildCreated", ahU2.hd()));
                iz_1.Vg().b(aff_12);
                azs_0.aLV().a((aho_0)iz_1.Vg(), iz_1.ce);
                return false;
            }
            case 560: {
                ry_1 ry_12 = (ry_1)pr_02;
                String string = "";
                string = ry_12.xX() ? aon_0.aYc().getString("infos.guildMemberRemoved", ry_12.xW()) : aon_0.aYc().getString("infos.guildMemberAdded", ry_12.xW());
                aff_1 aff_13 = new aff_1(string);
                iz_1.Vg().b(aff_13);
                azs_0.aLV().a((aho_0)iz_1.Vg(), iz_1.ce);
                return false;
            }
            case 28620: {
                Yq yq = (Yq)pr_02;
                if (yq.amw()) {
                    String string = aon_0.aYc().getString("tournamentFinale", yq.BC()) + "\n";
                    string = string + yq.agb()[0] + " VS " + yq.agb()[1];
                    tu_2 tu_22 = new tu_2(string);
                    tu_22.cK(yq.cA());
                    tu_22.j(yq.aga());
                    tu_22.q(yq.agb());
                    iz_1.Vg().b(tu_22);
                    azs_0.aLV().a((aho_0)iz_1.Vg(), iz_1.ce);
                } else if (yq.amx()) {
                    ArrayList arrayList = iz_1.Vg().Vh();
                    int n2 = 0;
                    boolean bl2 = false;
                    for (int j = 0; j < arrayList.size(); ++j) {
                        if (arrayList.get(j) instanceof tu_2 && ((tu_2)arrayList.get(j)).cA() == yq.cA()) {
                            bl2 = true;
                            break;
                        }
                        ++n2;
                    }
                    if (bl2) {
                        iz_1.Vg().c((aan_1)arrayList.get(n2));
                    }
                }
                return false;
            }
            case 28630: {
                dg_0 dg_02 = (dg_0)pr_02;
                long l2 = dg_02.fx();
                boolean bl3 = dg_02.Mh();
                if (bl3) {
                    boolean bl4 = false;
                    for (aan_1 aan_13 : iz_1.Vg().Vh()) {
                        if (!(aan_13 instanceof td_0) || ((td_0)aan_13).fx() != l2) continue;
                        bl4 = true;
                        break;
                    }
                    if (!bl4) {
                        aan_1 aan_13;
                        vg vg2 = vk_1.BZ().aQ(l2);
                        aan_13 = new td_0(vg2.BC());
                        ((td_0)aan_13).ad(l2);
                        iz_1.Vg().b(aan_13);
                    } else {
                        a.error((Object)("on essaye d'ajouter une info de tournoi qui est d\u00e9j\u00e0 pr\u00e9sente !" + new Exception()));
                    }
                } else {
                    aan_1 aan_14 = null;
                    for (aan_1 aan_15 : iz_1.Vg().Vh()) {
                        if (!(aan_15 instanceof td_0) || ((td_0)aan_15).fx() != l2) continue;
                        aan_14 = aan_15;
                        break;
                    }
                    iz_1.Vg().c(aan_14);
                }
                azs_0.aLV().a((aho_0)iz_1.Vg(), iz_1.ce);
                a.info((Object)("info tournoi : le tournoi d'id " + l2 + " change d'\u00e9tat : p\u00e9riode de recherche " + dg_02.Mh()));
                return false;
            }
            case 28644: {
                aaj_0 aaj_02 = (aaj_0)pr_02;
                long l3 = aaj_02.fx();
                long l4 = aaj_02.apr();
                apd apd2 = new apd((int)(1L + (l4 - new Date().getTime()) / 60000L));
                iz_1.Vg().b(apd2);
                azs_0.aLV().a((aho_0)iz_1.Vg(), iz_1.ce);
                a.info((Object)("Information du tournoi d'id " + l3 + " : La date de la prochaine p\u00e9riode de recherche d'opposant est " + new Date(l4) + "."));
                return false;
            }
            case 28646: {
                aNq aNq2 = (aNq)pr_02;
                long l5 = aNq2.fx();
                long l6 = aNq2.aXu() / 60L / 1000L;
                vg vg3 = vk_1.BZ().aQ(l5);
                if (vg3 != null) {
                    add_1.aOG().f(aon_0.aYc().getString("tournamentAnnouncement", l6, vg3.BC()), 102, 1);
                    td_0 td_02 = new td_0(vg3.BC());
                    td_02.ad(l5);
                    iz_1.Vg().b(td_02);
                    aan_1 aan_16 = null;
                    for (aan_1 aan_17 : iz_1.Vg().Vh()) {
                        if (!(aan_17 instanceof apd) || ((apd)aan_17).aDl() != 0) continue;
                        aan_16 = aan_17;
                        break;
                    }
                    iz_1.Vg().c(aan_16);
                }
                a.info((Object)("Information du tournoi d'id " + l5 + " : La p\u00e9riode de recherche d'opposant commence maintenant et est valable pour " + l6 + " minutes."));
                return false;
            }
            case 28648: {
                df_1 df_12 = (df_1)pr_02;
                long l7 = df_12.fx();
                vg vg4 = vk_1.BZ().aQ(l7);
                if (vg4 != null) {
                    boolean bl5 = df_12.fy();
                    add_1.aOG().f(aon_0.aYc().getString(bl5 ? "tournamentForfeit" : "tournamentWinner", vg4.BC()), 102, 1);
                    if (!bl5 && auz.kR("tournamentsSearchStatusDialog")) {
                        auz.kO("tournamentsSearchStatusDialog");
                        auz.kG("dofusarena.tournamentsSearchStatus");
                        apN.aDK().b(ds_2.LP());
                    }
                    aan_1 aan_18 = null;
                    for (aan_1 aan_19 : iz_1.Vg().Vh()) {
                        if (!(aan_19 instanceof td_0) || ((td_0)aan_19).fx() != l7) continue;
                        aan_18 = aan_19;
                        break;
                    }
                    iz_1.Vg().c(aan_18);
                    a.info((Object)("Information du tournoi d'id " + l7 + " : La p\u00e9riode de recherche d'opposant a pris fin maintenant et le coach est d\u00e9clar\u00e9 " + (bl5 ? "" : "gagnant par") + " forfait."));
                }
                return false;
            }
            case 25000: {
                az az2 = (az)pr_02;
                switch (az2.an()) {
                    default: 
                }
                zN.M(az2.an());
                return false;
            }
        }
        return true;
    }

    public static void M(byte by) {
        String string = "";
        switch (by) {
            case 69: {
                string = aon_0.aYc().getString("error.teamManagement.matchfinderOccuring");
                break;
            }
            case 72: {
                string = aon_0.aYc().getString("error.guild.noIsland");
                break;
            }
            case 34: {
                string = aon_0.aYc().getString("error.fight.creation.unableToCreateFight");
                break;
            }
            case 35: {
                string = aon_0.aYc().getString("error.fight.creation.targetDisconnected");
                break;
            }
            case 38: {
                string = aon_0.aYc().getString("error.fight.creation.internalErrorDuringCreation");
                break;
            }
            case 39: {
                string = aon_0.aYc().getString("error.fight.creation.noInstanceServer");
                break;
            }
            case 40: {
                string = aon_0.aYc().getString("error.fight.creation.canceledByOpponent");
                break;
            }
            case 45: {
                string = aon_0.aYc().getString("error.fight.creation.invalidFightersCount");
                break;
            }
            case 46: {
                string = aon_0.aYc().getString("error.fight.creation.invalidTeamBudget");
                break;
            }
            case 63: {
                string = aon_0.aYc().getString("error.fight.creation.tooMuchSameBreedFighters");
                break;
            }
            case 68: {
                string = aon_0.aYc().getString("error.fight.creation.fightFinished");
                break;
            }
            case 61: {
                string = aon_0.aYc().getString("error.fight.creation.spellForbidden");
                break;
            }
            case 62: {
                string = aon_0.aYc().getString("error.fight.creation.equipmentForbidden");
                break;
            }
            case 66: {
                string = aon_0.aYc().getString("error.fight.creation.badCoachCardQuantity");
                break;
            }
            case 67: {
                string = aon_0.aYc().getString("error.fight.creation.noInvitation");
                break;
            }
            case 75: {
                string = aon_0.aYc().getString("error.fight.creation.tooMuchDifferentBreed");
                break;
            }
            case 71: {
                string = aon_0.aYc().getString("error.fight.creation.fighterTooHighLeague");
                break;
            }
            case 77: {
                string = aon_0.aYc().getString("error.fight.creation.fighterTooOldSeason");
                break;
            }
            case 30: {
                string = aon_0.aYc().getString("error.fight.creation.targetNotFound");
                break;
            }
            case 31: {
                string = aon_0.aYc().getString("error.fight.creation.targetBusy");
                break;
            }
            case 32: {
                string = aon_0.aYc().getString("error.fight.creation.youreBusy");
                break;
            }
            case 33: {
                string = aon_0.aYc().getString("error.fight.creation.targetIsYourself");
                break;
            }
            case 74: {
                string = aon_0.aYc().getString("error.roublardNotActivated");
                break;
            }
            case 76: {
                string = aon_0.aYc().getString("error.invalidDatesForTournament");
                break;
            }
            case 78: {
                string = aon_0.aYc().getString("error.fight.invalidMinimalEvolutionTeamBudget", 5000);
                break;
            }
            default: {
                string = aon_0.aYc().getString("error", by);
            }
        }
        add_1.aOG().a(string, 1090L, 102, 1);
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

