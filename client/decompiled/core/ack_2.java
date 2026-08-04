/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aCK
 */
public class ack_2
implements hR {
    private static final ack_2 duF = new ack_2();
    private static final aub duG = new aub();

    public static ack_2 aOv() {
        return duF;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.tournament");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(duG)) {
            aub aub2 = (aub)lJ2;
            LS.Yf().a(aub2.Bw(), aub2);
            for (int j = 0; j < aub2.tv().length; ++j) {
                np_1 np_12 = aub2.tv()[j];
                if (!(np_12 instanceof agp)) continue;
                WF.ajj().b(((agp)np_12).awq());
            }
        }
        mk_12.b(this);
    }
}

