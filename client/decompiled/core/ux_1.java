/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ux
 */
public class ux_1
implements hR {
    private static final ux_1 aqB = new ux_1();
    private static final yp_0 aqC = new yp_0();

    public static ux_1 AG() {
        return aqB;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.cardSet");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(aqC)) {
            yp_0 yp_02 = (yp_0)lJ2;
            fe_1 fe_12 = new fe_1(yp_02.tm(), yp_02.tu());
            ayc_0.aLE().a(fe_12);
        }
        mk_12.b(this);
    }
}

