/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Q
 */
public class q_0
implements hR {
    private static final q_0 bc = new q_0();
    private static final GE bd = new GE();

    public static q_0 ar() {
        return bc;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.challenge");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(bd)) {
            GE gE = (GE)lJ2;
            ahy_1.axg().a(gE);
            for (int j = 0; j < gE.tv().length; ++j) {
                np_1 np_12 = gE.tv()[j];
                if (!(np_12 instanceof agp)) continue;
                WF.ajj().b(((agp)np_12).awq());
            }
        }
        mk_12.b(this);
    }
}

