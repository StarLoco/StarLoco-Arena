/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Kv
 */
public class kv_2
implements atG {
    private static final Logger a = Logger.getLogger(kv_2.class);
    private static kv_2 bnL = new kv_2();

    public static kv_2 WF() {
        return bnL;
    }

    public void a(fh_2 fh_22, boolean bl2) {
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 5401: {
                NN nN = (NN)pr_02;
                azs_0.aLV().g("cardMasterTrade", new aJd(nN.aaV(), nN.aaW()));
                if (!(apN.aDK().c(vu_1.aip()) || apN.aDK().c(wp_0.CH()) || apN.aDK().c(ds_2.LP()))) {
                    apN.aDK().a(ku_2.oU());
                    if (nN.aaX()) {
                        ku_2.oU().bH(1);
                    } else {
                        ku_2.oU().bH(0);
                    }
                } else {
                    apN.aDK().b(kv_2.WF());
                    add_1.aOG().a(aon_0.aYc().getString("cannotOpenUIWhenSearchingFight"), 1058L, 102, 1);
                }
                return false;
            }
            case 5403: {
                mj_1 mj_12 = (mj_1)pr_02;
                if (mj_12.rr() == 0) {
                    add_1.aOG().a(aon_0.aYc().getString("cardmaster.exchange.successfull"), 1058L, 102, 1);
                    aim_1 aim_12 = apN.aDK().Ln().rs();
                    aim_12.clear();
                    mj_12.rs().a(new il_2(this, aim_12));
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("cardmaster.exchange.error"), 1090L, 102, 1);
                }
                azs_0.aLV().g("cardMasterTrade", (Object)null);
                apN.aDK().b(ku_2.oU());
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

