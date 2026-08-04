/*
 * Decompiled with CFR 0.152.
 */
public class WO
implements hR {
    private static final WO bVu = new WO();
    private static final ek_2 bVv = new ek_2();

    public static WO ajs() {
        return bVu;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.tournament");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray = aly_1.aAQ().a(bVv);
        aim_1 aim_12 = LS.Yf().Yh();
        for (lJ lJ2 : lJArray) {
            ek_2 ek_22 = (ek_2)lJ2;
            aim_12.c(ek_22.Oy(), 0);
        }
        mk_12.b(this);
    }
}

