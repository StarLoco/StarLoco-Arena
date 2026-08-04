/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aBv
 */
public class abv_2
implements atG {
    private static abv_2 drt = new abv_2();

    public static abv_2 aNt() {
        return drt;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 16801: {
                vr_2 vr_22 = (vr_2)pr_02;
                tw_0 tw_02 = new tw_0();
                tw_02.cm(vr_22.Y());
                tw_02.am((byte)0);
                apN.aDK().vJ().b(tw_02);
                return false;
            }
            case 16802: {
                azS azS2 = (azS)pr_02;
                tw_0 tw_03 = new tw_0();
                tw_03.cm(azS2.Y());
                tw_03.am((byte)1);
                apN.aDK().vJ().b(tw_03);
                ug_1.AL().aJ(true);
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

