/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Bo
 */
public class bo_1
implements atG {
    private static bo_1 aIP = new bo_1();

    public static bo_1 Ik() {
        return aIP;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 18009: {
                au_2 au_22 = new au_2();
                apN.aDK().vJ().b(au_22);
                return false;
            }
            case 16700: {
                ia_2 ia_22 = (ia_2)pr_02;
                wy_2 wy_22 = ia_22.lm();
                if (wy_22 != null) {
                    azs_0.aLV().g("singleCardData", wy_22);
                }
                return false;
            }
            case 16701: {
                azs_0.aLV().g("singleCardData", (Object)null);
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
            add_1.aOG().a("fightPresentationDialog", oh_2.bq("fightPresentationDialog"), 1L, (short)10100);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().kO("fightPresentationDialog");
            azs_0.aLV().g("singleCardData", (Object)null);
        }
    }
}

