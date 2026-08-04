/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.common.game.statistics.PlayerStatisticsReport;
import org.apache.log4j.Logger;

/*
 * Renamed from PQ
 */
public class pq_1
implements atG {
    protected static final Logger a = Logger.getLogger(pq_1.class);
    private static pq_1 bEt = new pq_1();

    public static pq_1 acy() {
        return bEt;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 2048: {
                sj_1 sj_12 = new sj_1();
                sj_12.aQo();
                apN.aDK().b(sj_12);
                apN.aDK().a(rs_1.aez());
                return false;
            }
            case 2050: {
                az_0 az_02 = (az_0)pr_02;
                switch (az_02.an()) {
                    case 0: {
                        apN.aDK().b(rs_1.aez());
                        break;
                    }
                    case 11: 
                    case 12: {
                        add_1.aOG().a(aon_0.aYc().getString("error.coachCreation.invalidName", az_02.an()), 1090L, 5, 2);
                        break;
                    }
                    case 10: 
                    case 13: {
                        add_1.aOG().a(aon_0.aYc().getString("error.coachCreation", az_02.an()), 1090L, 5, 2);
                    }
                }
                return false;
            }
            case 2052: {
                aoh_2 aoh_22 = (aoh_2)pr_02;
                sj_1 sj_13 = aoh_22.Ln();
                apN.aDK().b(sj_13);
                ca_0 ca_02 = sj_13.aPY();
                if (ca_02 != null) {
                    azs_0.aLV().g("guildCanAffiliate", ca_02.Kg().aRe() == 1 && ca_02.Kk() == 0);
                }
                apN.aDK().a(rb_2.aem());
                return false;
            }
            case 2400: {
                sj_1 sj_14 = apN.aDK().Ln();
                if (sj_14 != null) {
                    PlayerStatisticsReport playerStatisticsReport = ((pl_1)pr_02).AK();
                    sj_14.a(playerStatisticsReport);
                    sj_14.a(playerStatisticsReport);
                } else {
                    a.error((Object)"Impossible de sauvegerde les statistiques de coach si aucun coach local n'est d\u00e9fini !");
                }
                return false;
            }
            case 27552: {
                zN.aY(true);
                apN.aDK().b(po_0.abV());
                apN.aDK().a(po_0.abV());
                add_1.aOG().kO("chatDialog");
                add_1.aOG().a("chatDialog", oh_2.bq("chatDialog"), 1025L, (short)19501);
                Gs gs = new Gs();
                gs.fG(202);
                apN.aDK().vJ().b(gs);
                return false;
            }
            case 26333: {
                r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("reconnectionInFightQuestion"), 1176L, 102, 1);
                r_02.a(new aO(this));
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

