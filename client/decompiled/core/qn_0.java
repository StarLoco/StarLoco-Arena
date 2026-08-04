/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Qn
 */
public class qn_0
implements atG {
    protected static final Logger a = Logger.getLogger(qn_0.class);
    private static qn_0 bGd = new qn_0();

    public static qn_0 adc() {
        return bGd;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 8022: {
                lk_2 lk_22 = (lk_2)pr_02;
                adu_0 adu_02 = apN.aDK().aDL();
                if (adu_02 != null) {
                    ee_2 ee_22 = (ee_2)adu_02.eg(lk_22.K());
                    if (ee_22 != null) {
                        ee_22.m(lk_22.gO(), lk_22.gP(), lk_22.gQ());
                    } else {
                        a.error((Object)("Le fighter " + lk_22.K() + " est inconnu !"));
                    }
                }
                return false;
            }
            case 8024: {
                aez_0 aez_02;
                dw_1 dw_12 = (dw_1)pr_02;
                sj_1 sj_12 = apN.aDK().Ln();
                if (sj_12 != null && (aez_02 = sj_12.yC()) != null && aez_02.getId() == dw_12.mb()) {
                    pm_0.ur().bD(true).m(aon_0.aYc().getString("waitingForOpponents"), 0);
                    apN.aDK().b(azL.aMm());
                }
                return false;
            }
            case 8028: {
                try {
                    apN.aDK().aDL().ass().nV();
                }
                catch (Exception exception) {
                    a.error((Object)"Error : ", (Throwable)exception);
                }
                return false;
            }
            case 4522: {
                u_0 u_02 = (u_0)pr_02;
                byte by = u_02.N().lV();
                wl_2 wl_22 = new wl_2(u_02.Ao(), by, u_02.M(), u_02.L());
                wl_22.bB(u_02.K());
                vr_0.aiM().b(wl_22);
                vr_0.aiM().aiQ();
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

