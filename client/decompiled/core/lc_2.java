/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Lc
 */
public class lc_2
implements hR {
    private static final lc_2 bpP = new lc_2();
    private static final wr_0 bpQ = new wr_0();

    public static lc_2 Xs() {
        return bpP;
    }

    private lc_2() {
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.achievementSubtype");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(bpQ)) {
            wr_0 wr_02 = (wr_0)lJ2;
            li_2 li_22 = new li_2(wr_02.CJ(), wr_02.pV());
            qy_2.ady().a(li_22);
            qy_2.ady().aV(wr_02.CJ()).b(li_22);
        }
        mk_12.b(this);
    }
}

