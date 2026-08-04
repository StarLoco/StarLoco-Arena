/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from au
 */
public class au_1
implements atG {
    private static final Logger a = Logger.getLogger(au_1.class);
    private static au_1 cj = new au_1();

    public static au_1 aX() {
        return cj;
    }

    public void a(fh_2 fh_22, boolean bl2) {
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 5403: {
                mj_1 mj_12 = (mj_1)pr_02;
                if (mj_12.rr() == 0) {
                    KI kI = (KI)azs_0.aLV().getProperty("guild").getValue();
                    azs_0.aLV().g("guildCanAffiliate", false);
                    azs_0.aLV().a((aho_0)kI, KI.ce);
                    String string = "";
                    try {
                        string = afg_1.kn(afl_1.aRM());
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        string = "" + afl_1.aRM();
                    }
                    string = aon_0.aYc().getString("ladderInformation.demon") + " " + string;
                    add_1.aOG().a(aon_0.aYc().getString("demonAffiliationSuccessfull", string), 1058L, 102, 1);
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("cardmaster.exchange.error"), 1090L, 102, 1);
                }
                apN.aDK().b(arb_0.aED());
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

