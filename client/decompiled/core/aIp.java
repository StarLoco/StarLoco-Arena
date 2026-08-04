/*
 * Decompiled with CFR 0.152.
 */
public class aIp
implements hR {
    private static final aIp dPL = new aIp();
    private static final cb_2 dPM = new cb_2();

    public static aIp aUT() {
        return dPL;
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray = aly_1.aAQ().a(dPM);
        if (lJArray != null) {
            for (int j = lJArray.length - 1; 0 <= j; --j) {
                int n2 = ((cb_2)lJArray[j]).getId();
                vk_1.BZ().Cb().c(n2, new atk_0(n2));
            }
        }
        mk_12.b(this);
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.proLeagueDefinition");
    }
}

