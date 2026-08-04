/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Md
 */
public class md_0
extends abb_1 {
    private static md_0 bsZ = new md_0();

    public static md_0 Yp() {
        return bsZ;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 16386: {
                nW.stop();
                apN.aDK().ayJ();
                return false;
            }
            case 16387: {
                apN.aDK().quit();
                return false;
            }
            case 16388: {
                if (apN.aDK().aDL() != null) {
                    add_1.aOG().a(aon_0.aYc().getString("cantDestroyCoachDuringFight"), 2L, 102, 0);
                } else {
                    r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("questionDestroyCoach"), 24L, 102, 0);
                    r_02.a(new jv_2(this));
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

