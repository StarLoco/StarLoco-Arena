/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from LH
 */
public class lh_1
implements atG {
    protected static final Logger a = Logger.getLogger(lh_1.class);
    private static lh_1 bsf = new lh_1();

    public static lh_1 XX() {
        return bsf;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 552: {
                sj_1 sj_12 = apN.aDK().Ln();
                kf_1 kf_12 = (kf_1)pr_02;
                ca_0 ca_02 = sj_12.aPY();
                ca_0 ca_03 = kf_12.WT()[0];
                if (ca_03.hd() != null && !ca_03.hd().equals("")) {
                    azs_0.aLV().g("guildCanAffiliate", ca_03.Kg().aRe() == 1 && ca_03.Kk() == 0);
                    sj_12.a(ca_03);
                } else {
                    sj_12.a((ca_0)null);
                    azs_0.aLV().g("guildCanAffiliate", false);
                }
                if (ca_02 == null || ca_02.Kk() != ca_03.Kk()) {
                    // empty if block
                }
                return false;
            }
            case 504: {
                mD mD2 = (mD)pr_02;
                KI kI = (KI)azs_0.aLV().getProperty("guild").getValue();
                switch (mD2.getResult()) {
                    case 403: {
                        add_1.aOG().a(aon_0.aYc().getString("guild.creationSuccessfull"), 1058L, 102, 1);
                        break;
                    }
                    case 401: {
                        add_1.aOG().a(aon_0.aYc().getString("guild.destroyed"), 1058L, 102, 1);
                        kI.clean();
                        break;
                    }
                    case 402: {
                        add_1.aOG().a(aon_0.aYc().getString("guild.kicked"), 1058L, 102, 1);
                        kI.clean();
                        break;
                    }
                    case 400: {
                        add_1.aOG().a(aon_0.aYc().getString("guild.left"), 1058L, 102, 1);
                        kI.clean();
                        break;
                    }
                    case 404: {
                        add_1.aOG().a(aon_0.aYc().getString("guild.invitationAccepted"), 1058L, 102, 1);
                        sj_1 sj_13 = apN.aDK().Ln();
                        ca_0 ca_04 = sj_13.aPY();
                        add_2 add_22 = new add_2(ca_04.Kd());
                        apN.aDK().vJ().b(add_22);
                        break;
                    }
                    case 11: {
                        add_1.aOG().a(aon_0.aYc().getString("guild.error.invalidName"), 1090L, 102, 1);
                        break;
                    }
                    case 40: {
                        add_1.aOG().a(aon_0.aYc().getString("error.inviationDenied"), 1090L, 102, 1);
                        break;
                    }
                    case 20: {
                        add_1.aOG().a(aon_0.aYc().getString("errorNoMoreRoom"), 1090L, 102, 1);
                        break;
                    }
                    case 35: {
                        add_1.aOG().a(aon_0.aYc().getString("errorGuildUserNotFound"), 1090L, 102, 1);
                        break;
                    }
                    default: {
                        add_1.aOG().a(aon_0.aYc().getString("guild.error.creationError"), 1090L, 102, 1);
                    }
                }
                return false;
            }
            case 510: {
                arl_0 arl_02 = (arl_0)pr_02;
                if (arl_02.aEj().length > 0) {
                    KI kI = (KI)azs_0.aLV().getProperty("guild").getValue();
                    kI.b(arl_02.aEj());
                    ArrayList arrayList = kI.WX();
                    arrayList.clear();
                    for (vd_2 vd_22 : kI.WW()) {
                        arrayList.add((vd_2)vd_22.clone());
                    }
                    azs_0.aLV().g("guildSelectedRank", arrayList.get(0));
                    azs_0.aLV().a((aho_0)kI, KI.ce);
                }
                return false;
            }
            case 512: {
                kf_1 kf_13 = (kf_1)pr_02;
                ca_0 ca_05 = apN.aDK().Ln().aPY();
                if (ca_05 != null) {
                    KI kI = (KI)azs_0.aLV().getProperty("guild").getValue();
                    kI.a("guild.name", ca_05.hd());
                    kI.a("guild.members", kf_13.WT());
                    azs_0.aLV().getProperty("guild").avr();
                    azs_0.aLV().g("guildInviter", ca_05.Kg().aQY());
                    azs_0.aLV().g("guildExcluder", ca_05.Kg().aQZ());
                    azs_0.aLV().g("guildMaster", ca_05.Kg().aRe() == 1);
                }
                return false;
            }
            case 2601: {
                kq_2 kq_22 = (kq_2)pr_02;
                aez_0 aez_02 = new aez_0();
                aez_02.a(kq_22.WC());
                aez_02.c(kq_22.mb());
                aez_02.setName(kq_22.xW());
                KI kI = (KI)azs_0.aLV().getProperty("guild").getValue();
                long l2 = kq_22.mb();
                Object object = kI.WV();
                int n2 = ((ca_0[])object).length;
                for (int j = 0; j < n2; ++j) {
                    ca_0 ca_06 = object[j];
                    if (ca_06.Ke() != l2) continue;
                    aez_02.bf((byte)ca_06.Kg().aRe());
                    break;
                }
                if ((object = bd_1.Is().bb(l2)) != null && object instanceof aez_0) {
                    aez_0 aez_03 = (aez_0)object;
                    aez_02.kX(aez_03.aQf());
                    aez_02.S(aez_03.lZ());
                    aez_02.bh(aez_03.aQe());
                    aez_02.bg(aez_03.aQd());
                }
                azs_0.aLV().g("guildCoachStats", aez_02);
                add_1.aOG().a("guildCoachStatsDialog", oh_2.bq("guildCoachStatsDialog"), 1L, (short)10001);
                return false;
            }
            case 554: {
                kf_1 kf_14 = (kf_1)pr_02;
                for (ca_0 ca_07 : kf_14.WT()) {
                    mT mT2 = bd_1.Is().bb(ca_07.Ke());
                    if (mT2 == null || !(mT2 instanceof aez_0)) continue;
                    ca_0 ca_08 = ((aez_0)mT2).aPY();
                    if (ca_08 == null) {
                        ((aez_0)mT2).a(ca_07);
                        continue;
                    }
                    ca_08.T(ca_07.hd());
                    ca_08.bv(ca_07.Kf());
                }
                return false;
            }
            case 556: {
                h_0 h_02 = (h_0)pr_02;
                mT mT3 = bd_1.Is().bb(h_02.ai());
                if (mT3 != null) {
                    if (mT3 instanceof aez_0) {
                        ((aez_0)mT3).a((ca_0)null);
                    }
                    if (mT3.getId() == apN.aDK().Ln().getId()) {
                        KI kI = (KI)azs_0.aLV().getProperty("guild").getValue();
                        kI.clean();
                    }
                }
                return false;
            }
            case 502: {
                auf_0 auf_02 = (auf_0)pr_02;
                if (apN.aDK().aDL() == null && !apN.aDK().Ln().yP() && !mc_1.qM().qO().containsKey(auf_02.MG().toLowerCase())) {
                    r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("guild.groupInvitationRequest", auf_02.MG(), auf_02.CL()), 1176L, 102, 1);
                    r_02.a(new aex_2(this, auf_02));
                } else {
                    cg_0 cg_02 = new cg_0();
                    cg_02.C(auf_02.CL());
                    cg_02.t(false);
                    cg_02.B(auf_02.MG());
                    cg_02.l(auf_02.rp());
                    cg_02.u(false);
                    apN.aDK().vJ().b(cg_02);
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
        azs_0.aLV().g("guildCanAffiliate", false);
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }
}

