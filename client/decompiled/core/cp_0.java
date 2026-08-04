/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from CP
 */
public class cp_0
implements atG {
    private static cp_0 aMP = new cp_0();
    private static apk_0 auy;

    public static cp_0 Lj() {
        return aMP;
    }

    public static void a(apk_0 apk_02) {
        auy = apk_02;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            // empty if block
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }

    private static void cE(String string) {
        if (auy != null) {
            auy.log(string);
            auy = null;
        }
    }

    public boolean a(pr_0 pr_02) {
        boolean bl2 = true;
        switch (pr_02.getId()) {
            case 5491: {
                agr_2 agr_22 = (agr_2)pr_02;
                byte by = agr_22.an();
                int n2 = agr_22.aSo();
                int n3 = agr_22.aSp();
                int n4 = agr_22.aSq();
                ajt_1 ajt_12 = (ajt_1)azs_0.aLV().getProperty("fusionTrade").getValue();
                if (by == 0) {
                    if (n2 != 0) {
                        ajt_12.lc(n2);
                        ajt_12.iu(aon_0.aYc().getString("fusionSuccess"));
                        ajt_12.dE(false);
                    } else if (n3 != 0) {
                        ajt_12.dE(true);
                        ajt_12.lc(n3);
                        ajt_12.iu(aon_0.aYc().getString("fusionRecipeFailed"));
                    } else if (n4 != 0) {
                        ajt_12.lc(n4);
                        ajt_12.iu(aon_0.aYc().getString("fusionLeftovers"));
                        ajt_12.dE(false);
                    } else {
                        ajt_12.iu(aon_0.aYc().getString("fusionFailed"));
                        ajt_12.dE(false);
                    }
                }
                ajt_12.clear();
                azs_0.aLV().a((aho_0)ajt_12, ajt_1.ce);
                cp_0.cE("R\u00e9sultat de la fusion : \n\n - errorCode = " + by + "." + "\n - obtainedReferenceCoachCardId = " + n2 + "." + "\n - notObtainedReferenceCoachCardId = " + n3 + "." + "\n - recorveredReferenceCoachCardId = " + n4 + ".\n");
                bl2 = false;
                break;
            }
        }
        return bl2;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }
}

