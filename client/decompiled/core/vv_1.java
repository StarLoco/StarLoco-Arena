/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from vV
 */
public class vv_1
implements atG {
    protected static final Logger a = Logger.getLogger(vv_1.class);
    private static vv_1 aua = new vv_1();

    public static vv_1 Cx() {
        return aua;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 8032: {
                aez_0 aez_02;
                aMC aMC2 = (aMC)pr_02;
                sj_1 sj_12 = apN.aDK().Ln();
                if (sj_12 != null && (aez_02 = sj_12.yC()) != null && aez_02.getId() == aMC2.mb()) {
                    pm_0.ur().bD(true).m(aon_0.aYc().getString("waitingForOpponents"), 0);
                    apN.aDK().b(fk_0.jo());
                }
                return false;
            }
            case 8038: {
                try {
                    apN.aDK().aDL().ass().nW();
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

