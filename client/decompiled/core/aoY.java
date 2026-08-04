/*
 * Decompiled with CFR 0.152.
 */
public class aoY
implements hR {
    private static final aoY cLS = new aoY();
    private static final ru_1 cLT = new ru_1();

    public static aoY aDk() {
        return cLS;
    }

    private aoY() {
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.achievement");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(cLT)) {
            ru_1 ru_12 = (ru_1)lJ2;
            aau_1 aau_12 = new aau_1(ru_12.adP(), ru_12.adQ(), ru_12.adU(), ru_12.adR(), ru_12.adT(), ru_12.adV(), ru_12.adS(), ru_12.adX(), ru_12.adW(), ru_12.adY(), ru_12.eA(), ru_12.isHidden());
            qy_2.a(aau_12);
            qy_2.ady().aW(ru_12.adT()).a(aau_12);
        }
        mk_12.b(this);
    }
}

