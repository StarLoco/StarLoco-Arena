/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from ayf
 */
public class ayf_0
extends avp_0
implements atG {
    protected static final Logger a = Logger.getLogger(ayf_0.class);
    private static ayf_0 dkI = new ayf_0();

    public static ayf_0 aKO() {
        return dkI;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 20060: {
                if (!ayg_0.aKP().cS(apN.aDK().Ln().getId())) {
                    apN.aDK().Ln().yH();
                    azs_0.aLV().g("mailbox.newMail", new ho_0());
                    add_1.aOG().a("newMailDialog", oh_2.bq("newMailDialog"), 1L, (short)19501);
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("error.mail.mailboxFull"), 1090L, 102, 1);
                }
                return false;
            }
            case 16700: 
            case 16701: {
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

    public void X() {
        po_0.abV().abW();
        add_1.aOG().a("mailboxDialog", oh_2.bq("mailboxDialog"), 1024L, (short)19501);
        azs_0.aLV().g("mailManager", ayg_0.aKP());
        add_1.aOG().l("dofusarena.mail", ay.class);
    }

    public void W() {
        add_1.aOG().kO("mailboxDialog");
        if (add_1.aOG().kR("newMailDialog")) {
            add_1.aOG().kO("newMailDialog");
        }
        azs_0.aLV().kb("mailManager");
        azs_0.aLV().kb("mailbox.mail");
        add_1.aOG().kG("dofusarena.mail");
        ayg_0.aKP().aKQ();
    }
}

