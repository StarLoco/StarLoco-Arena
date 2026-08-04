/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from PG
 */
public class pg_1
implements atG {
    protected static final Logger a = Logger.getLogger(pg_1.class);
    private static pg_1 bEh = new pg_1();

    public static pg_1 acm() {
        return bEh;
    }

    public boolean a(pr_0 pr_02) {
        sj_1 sj_12 = apN.aDK().Ln();
        switch (pr_02.getId()) {
            case 5110: {
                asH asH2 = (asH)pr_02;
                nk nk2 = sj_12.aQt();
                wy_2 wy_22 = asH2.apc();
                short s = wy_22.hG();
                wy_2 wy_23 = (wy_2)apN.aDK().Ln().yD().bW(wy_22.jf());
                wy_2 wy_24 = (wy_2)nk2.b(asH2.fY(), wy_22.jf());
                if (wy_24 != null) {
                    wy_22 = wy_24;
                } else if (wy_23 != null) {
                    wy_22.aS(wy_23.je());
                }
                nk2.a(asH2.fY(), wy_22, s);
                amq_1.aXh().eQ(System.currentTimeMillis());
                amq_1.aXh().aXj();
                return false;
            }
            case 5112: {
                aaz_1 aaz_12 = (aaz_1)pr_02;
                nk nk3 = sj_12.aQt();
                wy_2 wy_25 = aaz_12.apc();
                nk3.a(aaz_12.fY(), wy_25.jf(), wy_25.hG());
                amq_1.aXh().eQ(System.currentTimeMillis());
                amq_1.aXh().aXj();
                return false;
            }
            case 5113: {
                Or or = (Or)pr_02;
                if (or.abt() == 1) {
                    add_1.aOG().a(aon_0.aYc().getString("error.exchange.uniqueCardAlreadyExists"), 66L, 102, 1);
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("error.exchange.linkedCard"), 66L, 102, 1);
                }
                return false;
            }
            case 5116: {
                dl_0 dl_02 = (dl_0)pr_02;
                nk nk4 = sj_12.aQt();
                nk4.g(nk4.ck(dl_02.fY()));
                return false;
            }
            case 5114: {
                aqX aqX2 = (aqX)pr_02;
                nk nk5 = sj_12.aQt();
                switch (aqX2.aEh()) {
                    case 0: {
                        nk5.f(sj_12);
                        break;
                    }
                    case 1: {
                        nk5.e(sj_12);
                    }
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
        if (!bl2) {
            apN.aDK().b(ug_1.AL());
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            apN.aDK().a(ug_1.AL());
        }
    }
}

