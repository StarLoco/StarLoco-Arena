/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.log4j.Logger;

/*
 * Renamed from oz
 */
public class oz_1
implements atG {
    private static final Logger a = Logger.getLogger(oz_1.class);
    private static final oz_1 aaq = new oz_1();

    public static oz_1 tJ() {
        return aaq;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            ArrayList arrayList = vk_1.BZ().Cd();
            if (arrayList.size() > 0) {
                azs_0.aLV().g("selectedTournamentEvent", arrayList.get(0));
            }
            add_1.aOG().a("totemTournamentDialog", oh_2.bq("totemTournamentDialog"), (short)10000);
            de_2.Mc().Mf().setTime(new Date());
            add_1.aOG().l("dofusarena.tournaments", fn_0.class);
            apN.aDK().Ln().yH();
            aob_2 aob_22 = new aob_2();
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            rd_1 rd_12 = new rd_1(gregorianCalendar.get(13), gregorianCalendar.get(12), gregorianCalendar.get(11), gregorianCalendar.get(5), gregorianCalendar.get(2) + 1, gregorianCalendar.get(1));
            aob_22.a(rd_12, 0);
            azs_0.aLV().g("defaultCalendar", aob_22);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().kO("totemTournamentDialog");
            add_1.aOG().kG("dofusarena.tournaments");
            apN.aDK().Ln().yI();
        }
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 20071: {
                qr_0 qr_02 = (qr_0)azs_0.aLV().getProperty("selectedTournamentEvent").getValue();
                vg vg2 = vk_1.BZ().aQ(qr_02.fx());
                if (vg2 != null) {
                    aub aub2 = LS.Yf().gG(vg2.Bw());
                    int n2 = aub2.qo();
                    if (n2 != 0) {
                        Object object;
                        int n3;
                        int n4 = 0;
                        ky_2 ky_22 = apN.aDK().Ln().yD();
                        wy_2 wy_22 = (wy_2)ky_22.pI().ac(Math.abs(n2));
                        if (wy_22 != null && ky_22.bU(Math.abs(n2))) {
                            n4 += wy_22.hG();
                            n3 = Math.abs(n2);
                        } else {
                            object = (wy_2)ky_22.pI().ac(-Math.abs(n2));
                            if (object != null) {
                                n4 += ((eb_1)object).hG();
                                n3 = -Math.abs(n2);
                            } else {
                                n3 = 0;
                            }
                        }
                        if (n4 > 0) {
                            object = add_1.aOG().a(aon_0.aYc().getString("registerToTournament", wy_22.getName()), 24L, 102, 0);
                            ((r_0)object).a(new ox_0(this, qr_02, n3, vg2));
                        } else {
                            object = aon_0.aYc().getString("cardNeededToRegister", ((xj)la_0.XJ().pj(n2)).getName());
                            add_1.aOG().a((String)object, 2L, 102, 0);
                        }
                    } else {
                        aik_0 aik_02 = new aik_0();
                        aik_02.ad(qr_02.fx());
                        aik_02.aj(apN.aDK().Ln().getId());
                        aik_02.C((short)-1);
                        apN.aDK().vJ().b(aik_02);
                        if (vg2.By().length > 0) {
                            r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("saveProfileQuestion"), 24L, 102, 0);
                            r_02.a(new oy_1(this, vg2));
                        }
                    }
                }
                return false;
            }
            case 16700: {
                ia_2 ia_22 = (ia_2)pr_02;
                wy_2 wy_23 = ia_22.lm();
                if (wy_23 != null) {
                    azs_0.aLV().g("coachManagement.selectedCard", wy_23);
                }
                return false;
            }
            case 16701: {
                azs_0.aLV().g("coachManagement.selectedCard", (Object)null);
                return false;
            }
            case 20160: {
                if (apN.aDK().c(lr_1.qk())) {
                    apN.aDK().b(lr_1.qk());
                } else {
                    apN.aDK().a(lr_1.qk());
                }
                return false;
            }
            case 20077: {
                if (add_1.aOG().kR("tournamentCreationDialog")) {
                    add_1.aOG().kO("tournamentCreationDialog");
                    add_1.aOG().a("totemTournamentDialog", oh_2.bq("totemTournamentDialog"), (short)10000);
                } else {
                    add_1.aOG().kO("totemTournamentDialog");
                    add_1.aOG().a("tournamentCreationDialog", oh_2.bq("tournamentCreationDialog"), (short)10000);
                }
                return false;
            }
            case 20067: {
                if (add_1.aOG().kR("tournamentAdminCreationDialog")) {
                    sb_0 sb_02 = (sb_0)pr_02;
                    aob_2 aob_22 = (aob_2)azs_0.aLV().getProperty("defaultCalendar").getValue();
                    int n5 = aob_22.aXS();
                    aob_22.pF(sb_02.getIntValue());
                    azs_0.aLV().g("tournamentCreationName", aon_0.aYc().a(41, n5, new Object[0]));
                    azs_0.aLV().g("tournamentCreationDescription", aon_0.aYc().a(42, n5, new Object[0]));
                    add_1.aOG().kO("tournamentAdminCreationDialog");
                    add_1.aOG().a("tournamentCreationDialog", oh_2.bq("tournamentCreationDialog"), (short)10000);
                } else {
                    add_1.aOG().kO("tournamentCreationDialog");
                    add_1.aOG().a("tournamentAdminCreationDialog", oh_2.bq("tournamentAdminCreationDialog"), (short)10000);
                }
                return false;
            }
            case 20072: {
                rd_1 rd_12;
                JG jG;
                wy_2 wy_24 = (wy_2)apN.aDK().Ln().yD().bW(807);
                if (!(wy_24 != null && wy_24.hG() != 0 || (jG = (wy_2)apN.aDK().Ln().yD().bW(-807)) != null && ((eb_1)jG).hG() != 0)) {
                    add_1.aOG().a(aon_0.aYc().getString("errorNoTournamentCreationCard"), 1090L, 102, 1);
                    return false;
                }
                jG = (asE)pr_02;
                aob_2 aob_23 = (aob_2)azs_0.aLV().getProperty("defaultCalendar").getValue();
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime(new Date());
                if (gregorianCalendar.get(1) == aob_23.py(0) && gregorianCalendar.get(2) == aob_23.px(0) && aob_23.pw(0) < gregorianCalendar.get(5)) {
                    add_1.aOG().a(aon_0.aYc().getString("error.cantSelectDay"), 1090L, 102, 1);
                }
                if ((rd_12 = rd_1.aF(System.currentTimeMillis())).f(new rd_1(0, aob_23.pD(0), aob_23.pz(0), aob_23.pw(0), aob_23.px(0) + 1, aob_23.py(0))) > 0) {
                    add_1.aOG().a(aon_0.aYc().getString("errorInTournamentDate"), 1090L, 102, 1);
                    return false;
                }
                aFu aFu2 = new aFu();
                aFu2.ll(((asE)jG).getName());
                aFu2.lm(((asE)jG).getDescription());
                aFu2.or(aob_23.aXV());
                aFu2.d(aob_23.aXU());
                if (aob_23.pz(0) > aob_23.pA(0)) {
                    aFu2.b(new rd_1(0, aob_23.pD(0), aob_23.pz(0), aob_23.pw(0), aob_23.px(0) + 1, aob_23.py(0)), new rd_1(0, aob_23.pE(0), aob_23.pA(0), aob_23.pw(0) + 1, aob_23.px(0) + 1, aob_23.py(0)));
                } else {
                    aFu2.b(new rd_1(0, aob_23.pD(0), aob_23.pz(0), aob_23.pw(0), aob_23.px(0) + 1, aob_23.py(0)), new rd_1(0, aob_23.pE(0), aob_23.pA(0), aob_23.pw(0), aob_23.px(0) + 1, aob_23.py(0)));
                }
                if (aob_23.pC(0)) {
                    aFu2.b(new rd_1(0, aob_23.pD(1), aob_23.pz(1), aob_23.pw(1), aob_23.px(1) + 1, aob_23.py(1)), new rd_1(0, aob_23.pE(1), aob_23.pA(1), aob_23.pw(1), aob_23.px(1) + 1, aob_23.py(1)));
                }
                if (aob_23.pC(1)) {
                    aFu2.b(new rd_1(0, aob_23.pD(2), aob_23.pz(2), aob_23.pw(2), aob_23.px(2) + 1, aob_23.py(2)), new rd_1(0, aob_23.pE(2), aob_23.pA(2), aob_23.pw(2), aob_23.px(2) + 1, aob_23.py(2)));
                }
                if (aob_23.pC(2)) {
                    aFu2.b(new rd_1(0, aob_23.pD(3), aob_23.pz(3), aob_23.pw(3), aob_23.px(3) + 1, aob_23.py(3)), new rd_1(0, aob_23.pE(3), aob_23.pA(3), aob_23.pw(3), aob_23.px(3) + 1, aob_23.py(3)));
                }
                aFu2.d(new rd_1(0, aob_23.pD(4), aob_23.pz(4), aob_23.pw(4), aob_23.px(4) + 1, aob_23.py(4)));
                aFu2.e(new rd_1(0, aob_23.pD(5), aob_23.pz(5), aob_23.pw(5), aob_23.px(5) + 1, aob_23.py(5)));
                if (aob_23.pC(2)) {
                    aFu2.f(new rd_1(0, 59, 23, aob_23.pw(3), aob_23.px(3) + 1, aob_23.py(3)));
                } else if (aob_23.pC(1)) {
                    aFu2.f(new rd_1(0, 59, 23, aob_23.pw(2), aob_23.px(2) + 1, aob_23.py(2)));
                } else if (aob_23.pC(0)) {
                    aFu2.f(new rd_1(0, 59, 23, aob_23.pw(1), aob_23.px(1) + 1, aob_23.py(1)));
                } else {
                    aFu2.f(new rd_1(0, 59, 23, aob_23.pw(0), aob_23.px(0) + 1, aob_23.py(0)));
                }
                aFu2.K(aob_23.aXT().nm());
                aFu2.bm((byte)aob_23.aXS());
                ArrayList arrayList = jk_1.mf().me();
                for (int j = 0; j < arrayList.size(); ++j) {
                    WN wN = (WN)arrayList.get(j);
                    for (int i2 = 0; i2 < wN.ajp().size(); ++i2) {
                        aFu2.oq(((np_1)wN.ajp().get(i2)).sn());
                    }
                }
                apN.aDK().vJ().b(aFu2);
                add_1.aOG().kO("tournamentCreationDialog");
                add_1.aOG().a("totemTournamentDialog", oh_2.bq("totemTournamentDialog"), (short)10000);
                add_1.aOG().a(aon_0.aYc().getString("tournamentCreated"), 1090L, 102, 1);
                return false;
            }
            case 20079: {
                if (!add_1.aOG().kR("tournamentTreeDialog")) {
                    sb_0 sb_03 = (sb_0)pr_02;
                    sj_1 sj_12 = apN.aDK().Ln();
                    ah_1 ah_12 = new ah_1();
                    ah_12.eE(0);
                    ah_12.ad(sb_03.getLongValue());
                    ah_12.dq(sj_12.getName());
                    azs_0.aLV().g("duelTree", ah_12);
                    alf_0 alf_02 = new alf_0();
                    alf_02.lr(0);
                    alf_02.ad(sb_03.getLongValue());
                    alf_02.iE(sj_12.getName());
                    apN.aDK().vJ().b(alf_02);
                }
                return false;
            }
            case 20069: {
                sb_0 sb_04 = (sb_0)pr_02;
                ah_1 ah_13 = (ah_1)azs_0.aLV().getProperty("duelTree").getValue();
                if (ah_13 != null) {
                    alf_0 alf_03 = new alf_0();
                    alf_03.lr(Math.max(0, ah_13.Hr() + sb_04.aj()));
                    alf_03.ad(ah_13.fx());
                    apN.aDK().vJ().b(alf_03);
                }
                return false;
            }
            case 20068: {
                sb_0 sb_05 = (sb_0)pr_02;
                ah_1 ah_14 = (ah_1)azs_0.aLV().getProperty("duelTree").getValue();
                if (ah_14 != null) {
                    ah_14.dq(sb_05.getStringValue());
                    alf_0 alf_04 = new alf_0();
                    alf_04.iE(sb_05.getStringValue());
                    alf_04.ad(ah_14.fx());
                    apN.aDK().vJ().b(alf_04);
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
}

