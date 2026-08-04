/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from CJ
 */
public class cj_0
implements atG {
    private static final Logger a = Logger.getLogger(cj_0.class);
    private static final cj_0 aMJ = new cj_0();

    public static cj_0 La() {
        return aMJ;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 16441: {
                apN.aDK().a(do_2.Mm());
                apN.aDK().a(wg_2.CC());
                afz_0 afz_02 = (afz_0)azs_0.aLV().getProperty("selectedChallenge").getValue();
                alv_1 alv_12 = new alv_1();
                alv_12.fH(afz_02.getId());
                alv_12.bM((short)99);
                apN.aDK().vJ().b(alv_12);
                apN.aDK().b(this);
                return false;
            }
            case 16440: {
                azs_0.aLV().g("selectedChallenge", ahy_1.axg().dC(((sb_0)pr_02).getLongValue()));
                azs_0.aLV().a((aho_0)ahy_1.axg(), ahy_1.ce);
                return false;
            }
        }
        return true;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            po_0.abV().abW();
            add_1.aOG().a("challengeDialog", oh_2.bq("challengeDialog"), (short)10000);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        add_1.aOG().kO("challengeDialog");
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }
}

