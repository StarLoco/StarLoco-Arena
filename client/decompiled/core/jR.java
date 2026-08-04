/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class jR
implements atG {
    protected static final Logger a = Logger.getLogger(jR.class);
    private static jR CS = new jR();

    public static jR oc() {
        return CS;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 8012: {
                aez_0 aez_02;
                aio_0 aio_02 = (aio_0)pr_02;
                sj_1 sj_12 = apN.aDK().Ln();
                if (sj_12 != null && (aez_02 = sj_12.yC()) != null && aez_02.getId() == aio_02.mb()) {
                    pm_0.ur().bD(true).m(aon_0.aYc().getString("waitingForOpponents"), 0);
                    apN.aDK().b(bo_1.Ik());
                }
                return false;
            }
            case 8018: {
                try {
                    apN.aDK().aDL().ass().nU();
                }
                catch (Exception exception) {
                    a.error((Object)"Error : ", (Throwable)exception);
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
        if (!bl2) {
            pm_0.ur().done();
        }
    }
}

