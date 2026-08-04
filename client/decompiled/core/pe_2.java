/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from PE
 */
public class pe_2
implements atG {
    private static pe_2 bEe = new pe_2();

    public static pe_2 ack() {
        return bEe;
    }

    public void a(fh_2 fh_22, boolean bl2) {
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }

    public boolean a(pr_0 pr_02) {
        boolean bl2 = true;
        switch (pr_02.getId()) {
            case 4000: {
                tg_1 tg_12 = (tg_1)pr_02;
                sj_1 sj_12 = apN.aDK().Ln();
                if (sj_12 != null) {
                    sj_12.nu(tg_12.zM());
                }
                bl2 = false;
                break;
            }
            case 4001: {
                tc_2 tc_22 = (tc_2)pr_02;
                aim_1 aim_12 = apN.aDK().Ln().rs();
                aim_12.clear();
                tc_22.rs().a(new RP(this, aim_12));
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

