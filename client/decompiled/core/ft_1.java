/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from fT
 */
public class ft_1
implements atG {
    protected static final Logger a = Logger.getLogger(ft_1.class);
    private static ft_1 rY = new ft_1();
    private r_0 rZ = null;

    public static ft_1 jr() {
        return rY;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 26314: {
                ahV ahV2 = (ahV)pr_02;
                this.rZ = add_1.aOG().a(aon_0.aYc().getString("xvsxFightInvitation.confirmation", ahV2.axz(), ahV2.hX()), 1176L, 102, 1);
                this.rZ.a(new aqr_0(this, ahV2));
                return false;
            }
            case 26300: {
                wu_2 wu_22 = (wu_2)pr_02;
                pw_1.acG().a(wu_22.Y(), wu_22.Ds(), wu_22.Dt(), wu_22.uK());
                return false;
            }
            case 26302: {
                pu_1 pu_12 = (pu_1)pr_02;
                pw_1.acG().clear();
                po_0.abV().abW();
                apN.aDK().a(wg_2.CC());
                if (pu_12.uK()) {
                    apN.aDK().a(do_2.Mm());
                } else {
                    apN.aDK().a(B.V());
                }
                B.V().d(pu_12.Y());
                return false;
            }
            case 26304: {
                gz_0 gz_02 = (gz_0)pr_02;
                pw_1.acG().cr(gz_02.Y());
                if (this.rZ != null) {
                    this.rZ.b(2048);
                    this.rZ = null;
                }
                add_1.aOG().a(aon_0.aYc().getString("error.fight.creation.canceledByOpponent"), 1090L, 102, 1);
                return false;
            }
            case 26312: {
                axr_0 axr_02 = (axr_0)pr_02;
                apN.aDK().b(wg_2.CC());
                apN.aDK().b(hu_2.li());
                apN.aDK().b(B.V());
                po_0.abV().abW();
                zN.M(axr_02.an());
                return false;
            }
            case 4309: {
                pw_1.acG().clear();
                apN.aDK().b(this);
                po_0.abV().abW();
                apN.aDK().a(wg_2.CC());
                apN.aDK().a(do_2.Mm());
                adu_0.dc(true);
                return false;
            }
            case 2307: {
                bx_1 bx_12 = (bx_1)pr_02;
                r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("opponentSearchConfirmation.request", bx_12.xW(), bx_12.IP()), 1176L, 102, 1);
                r_02.a(new aqQ(this, bx_12));
                avn_0.d(r_02);
                return false;
            }
            case 2309: {
                cJ cJ2 = (cJ)pr_02;
                avn_0.close();
                if (!cJ2.eY()) {
                    add_1.aOG().a(aon_0.aYc().getString("opponentSearchConfirmation.resultIsNo"), 1026L, 102, 1);
                } else {
                    apN.aDK().a(lg_0.pU());
                }
                return false;
            }
            case 23112: {
                aku_1 aku_12 = (aku_1)pr_02;
                avn_0.close();
                if (!aku_12.eY()) {
                    add_1.aOG().a(aon_0.aYc().getString("opponentSearchConfirmation.resultIsNo"), 1026L, 102, 1);
                }
                return false;
            }
            case 23110: {
                tb_2 tb_22 = (tb_2)pr_02;
                r_0 r_03 = add_1.aOG().a(aon_0.aYc().getString("opponentSearchConfirmation.request", tb_22.xW(), tb_22.zJ()), 1176L, 102, 1);
                r_03.a(new aqo_0(this, tb_22));
                avn_0.d(r_03);
                return false;
            }
            case 8300: {
                aiw_1 aiw_12 = new aiw_1();
                aiw_12.cb(false);
                apN.aDK().vJ().b(aiw_12);
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

    static /* synthetic */ r_0 a(ft_1 ft_12, r_0 r_02) {
        ft_12.rZ = r_02;
        return ft_12.rZ;
    }

    static /* synthetic */ r_0 a(ft_1 ft_12) {
        return ft_12.rZ;
    }
}

