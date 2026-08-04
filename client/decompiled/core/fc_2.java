/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from fc
 */
public class fc_2
implements atG {
    private static fc_2 qc = new fc_2();

    public static fc_2 ia() {
        return qc;
    }

    public boolean a(pr_0 pr_02) {
        pr_02.getId();
        return true;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            if (add_1.aOG().kR("fightMenuBarDialog")) {
                add_1.aOG().a("fightMenuDialog", oh_2.bq("fightMenuDialog"), 257L, (short)19500);
            } else {
                add_1.aOG().a("menuDialog", oh_2.bq("menuDialog"), 257L, (short)19500);
            }
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            if (add_1.aOG().kR("fightMenuBarDialog")) {
                add_1.aOG().kO("fightMenuDialog");
            } else {
                add_1.aOG().kO("menuDialog");
            }
        }
    }
}

