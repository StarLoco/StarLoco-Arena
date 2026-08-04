/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ne
 */
public class ne_0
implements hR {
    private static final ne_0 NK = new ne_0();
    private static final alf_2 NL = new alf_2();

    public static ne_0 rZ() {
        return NK;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.sphereBoard");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(NL)) {
            alf_2 alf_22 = (alf_2)lJ2;
            jg_0 jg_02 = alf_22.aWt();
            lb_0 lb_02 = new lb_0();
            for (int j = 0; j < jg_02.size(); ++j) {
                lb_02.c(jg_02.bu(j), aca_0.aOq().E(jg_02.bu(j)));
            }
            aca_0.aOq().a(lb_02, alf_22.getId());
        }
        mk_12.b(this);
    }
}

