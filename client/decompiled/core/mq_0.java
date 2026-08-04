/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Mq
 */
public class mq_0
implements atG {
    protected static final Logger a = Logger.getLogger(mq_0.class);
    private static mq_0 btQ = new mq_0();

    public static mq_0 Ys() {
        return btQ;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 15001: {
                ayV ayV2 = (ayV)pr_02;
                ayg_0.aKP().C(ayV2.aLH());
                apN.aDK().a(ayf_0.aKO());
                return false;
            }
            case 15003: {
                Eh eh = (Eh)pr_02;
                if (eh.MP() > 0L) {
                    ayg_0.aKP().a((aLb)eh.MO());
                } else if (eh.MP() == (long)ua_1.bPk) {
                    add_1.aOG().a(aon_0.aYc().getString("error.mail.mailboxFull"), 1090L, 102, 1);
                }
                add_1.aOG().kO("newMailDialog");
                return false;
            }
            case 15507: {
                afj_2 afj_22 = (afj_2)pr_02;
                ho_0 ho_02 = (ho_0)azs_0.aLV().getProperty("mailbox.newMail").getValue();
                ho_02.eH(afj_22.mb());
                azs_0.aLV().a((aho_0)ho_02, "receiverId");
                return false;
            }
            case 15007: {
                cb cb2 = (cb)pr_02;
                ug ug2 = (ug)ayg_0.aKP().cR(cb2.ec());
                if (ug2 != null) {
                    sj_1 sj_12 = apN.aDK().Ln();
                    try {
                        for (wy_2 wy_22 : cb2.ed()) {
                            if (sj_12.getId() == cb2.ee()) {
                                sj_12.aQn().f(wy_22);
                            }
                            for (int j = ug2.Ax().size() - 1; j >= 0; --j) {
                                if (ug2.Ax().get(j) != wy_22.jf()) continue;
                                ug2.Ax().bv(j);
                            }
                        }
                    }
                    catch (xR xR2) {
                        xR2.printStackTrace();
                    }
                    catch (gg gg2) {
                        gg2.printStackTrace();
                    }
                    ayg_0.aKP().c(ug2);
                    azs_0.aLV().g("mailbox.mail", ug2);
                    azs_0.aLV().a((aho_0)((ho_0)ug2), "cards");
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
    }
}

