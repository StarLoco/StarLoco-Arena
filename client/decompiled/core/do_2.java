/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from DO
 */
public class do_2
implements atG {
    protected static final Logger a = Logger.getLogger(do_2.class);
    private static do_2 aOo = new do_2();

    public static do_2 Mm() {
        return aOo;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 8000: {
                aat_2 aat_22 = (aat_2)pr_02;
                if (aat_22.an() == 0) {
                    adu_0 adu_02 = aat_2.ac(aat_22.aMK());
                    if (adu_02 != null) {
                        afz_0 afz_02 = ahy_1.axg().dC(adu_02.asy());
                        if (afz_02 != null && afz_02.QE() > 0) {
                            add_1.aOG().f(aon_0.aYc().getString("enteringTimeChallenge"), 102, 1);
                        }
                        apN.aDK().a(adu_02);
                        apN.aDK().b(wg_2.CC());
                        apN.aDK().b(hu_2.li());
                        apN.aDK().b(B.V());
                        this.a(adu_02, aat_22.aMK());
                        adu_02.ZF();
                        return false;
                    } else {
                        a.error((Object)"Fight est null dans le StartFightMessage !");
                    }
                    return false;
                } else {
                    add_1.aOG().a("Erreur \u00e0 la cr\u00e9ation du combat (" + aat_22.an() + ") !", 1090L, 102, 1);
                    apN.aDK().b(this);
                }
                return false;
            }
            case 8010: {
                try {
                    apN.aDK().aDL().ZG();
                    return false;
                }
                catch (Exception exception) {
                    a.error((Object)"Error START_PRESENTATION_MESSAGE : ", (Throwable)exception);
                    a.error((Object)("Fight : " + apN.aDK().aDL()));
                    if (apN.aDK().aDL() == null) return false;
                    a.error((Object)("FightTimeline : " + apN.aDK().aDL().ass()));
                }
                return false;
            }
            case 8020: {
                try {
                    apN.aDK().aDL().ZH();
                    return false;
                }
                catch (Exception exception) {
                    a.error((Object)"Error START_PLACEMENT_MESSAGE : ", (Throwable)exception);
                }
                return false;
            }
            case 8030: {
                afj_0 afj_02 = new afj_0();
                adu_0 adu_03 = apN.aDK().aDL();
                if (adu_03 != null) {
                    byte by = 0;
                    Iterator iterator = adu_03.aKj();
                    while (iterator.hasNext()) {
                        cl_1 cl_12 = (cl_1)iterator.next();
                        afj_02.b(by, ((aez_0)cl_12).aTI());
                        for (ee_2 ee_22 : cl_12.Lg()) {
                            ny_2.sR().println("300|" + ny_2.cu(ny_2.Qn) + "|" + ee_22.getId() + "|" + ee_22.NW().gn() + "|" + ee_22.NW().go() + "|" + ee_22.NW().getAltitude() + "|" + ee_22.NW().L() + "|");
                        }
                        by = (byte)(by + 1);
                    }
                    adu_03.a(afj_02);
                }
                try {
                    apN.aDK().aDL().ZK();
                    return false;
                }
                catch (Exception exception) {
                    a.error((Object)"Error START_OBSERVATION_MESSAGE : ", (Throwable)exception);
                }
                return false;
            }
            case 8040: {
                try {
                    apN.aDK().aDL().ZM();
                    return false;
                }
                catch (Exception exception) {
                    a.error((Object)"Error START_ACTION_MESSAGE : ", (Throwable)exception);
                }
                return false;
            }
            case 4311: {
                ee_2 ee_23;
                adu_0 adu_04 = apN.aDK().aDL();
                if (adu_04 == null) return false;
                Iterator iterator = adu_04.aKq();
                while (iterator.hasNext()) {
                    ee_23 = (ee_2)iterator.next();
                    ee_23.a(Lr.brn).set(0);
                    ee_23.a(Lr.brm).set(0);
                }
                adu_0.dc(false);
                ee_23 = (ee_2)adu_04.ass().nP();
                if (!adu_04.i(ee_23) || ee_23.Dk()) return false;
                apN.aDK().a(anx_1.aXx());
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
        if (!bl2) {
            // empty if block
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            pm_0.ur().done();
            add_1.aOG().kO("fightEventCardsDialog");
            add_1.aOG().kO("timelineDialog");
            azs_0.aLV().kb("fight.timeline");
            azs_0.aLV().kb("fight.timeline.selectedFighter");
            azs_0.aLV().kb("singleCardData");
            azs_0.aLV().kb("fight.eventCards");
            apN.aDK().b(wg_2.CC());
            apN.aDK().b(of_1.th());
            apN.aDK().b(qg_2.acV());
            apN.aDK().b(bo_1.Ik());
            apN.aDK().b(azL.aMm());
            apN.aDK().b(fk_0.jo());
            apN.aDK().b(avu_0.aIB());
            apN.aDK().b(anx_1.aXx());
            apN.aDK().a((adu_0)null);
        }
    }

    public void a(adu_0 adu_02, byte[] byArray) {
        Object object;
        cl_1 cl_12;
        if (adu_02.aKl() != 5) {
            ny_2.sR().ag(DofusArenaClientInstance.yl().aod().a(adc_0.clN));
        } else {
            ny_2.sR().ag(false);
        }
        File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "replays");
        if (!file.exists()) {
            file.mkdir();
        }
        String string = System.getProperty("user.dir") + System.getProperty("file.separator") + "replays" + System.getProperty("file.separator");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmm");
        Date date = new Date();
        string = string + simpleDateFormat.format(date) + "_";
        String string2 = null;
        String string3 = null;
        Object object2 = adu_02.aKj();
        while (object2.hasNext()) {
            cl_12 = (cl_1)object2.next();
            if (cl_12.Le() == 0) {
                if (string2 == null) {
                    string2 = ((aez_0)cl_12).getName();
                    continue;
                }
                string2 = string2 + "-" + ((aez_0)cl_12).getName();
                continue;
            }
            if (string3 == null) {
                string3 = ((aez_0)cl_12).getName();
                continue;
            }
            string3 = string3 + "-" + ((aez_0)cl_12).getName();
        }
        string = string + string2 + "_VS_" + string3 + ".rda";
        ny_2.sR().setFileName(string);
        ny_2.a(adu_02.aKk(), adu_02.aKj());
        ny_2.sR().print("0|" + adu_02.YF() + "|" + 70 + "|" + adu_02.aKk() + "|");
        object2 = adu_02.aKj();
        while (object2.hasNext()) {
            cl_12 = (cl_1)object2.next();
            ny_2.sR().print(((aez_0)cl_12).getName() + "|" + ((aez_0)cl_12).getLevel() + "|" + cl_12.Le() + "|");
        }
        object2 = new Date();
        ny_2.sR().println(String.valueOf(((Date)object2).getTime()) + "|");
        ny_2.sR().print("100|");
        for (int j = 0; j < byArray.length; ++j) {
            ny_2.sR().print(byArray[j] + "/");
        }
        ny_2.sR().println("|");
        ny_2.sR().print("102|" + adu_02.amr() + "|");
        Object object3 = adu_02.aKj();
        while (object3.hasNext()) {
            object = (cl_1)object3.next();
            for (ee_2 ee_22 : object.Lg()) {
                ny_2.sR().print(ee_22.getId() + "/" + object.Ld() + "/" + ee_22.NY().lV() + "|");
            }
        }
        ny_2.sR().println("");
        object3 = DofusArenaClientInstance.yl().YP();
        object = ((qs_2)object3).vn();
        Du du = ((yg_1)object).Fx();
        ny_2.sR().println("200|" + adu_02.YF() + "|" + du.getWorldX() + "|" + du.getWorldY() + "|" + (short)du.getAltitude() + "|");
        ny_2.sR().sU();
    }
}

