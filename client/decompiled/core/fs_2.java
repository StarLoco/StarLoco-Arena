/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Fs
 */
public class fs_2
implements hR {
    private static final fs_2 aUL = new fs_2();
    private static final aet_1 aUM = new aet_1();

    public static fs_2 OU() {
        return aUL;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.standardFightParameters");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(aUM)) {
            aet_1 aet_12 = (aet_1)lJ2;
            for (int j = 0; j < aet_12.tv().length; ++j) {
                np_1 np_12 = aet_12.tv()[j];
                if (!(np_12 instanceof agp)) continue;
                WF.ajj().b(((agp)np_12).awq());
            }
        }
        mk_12.b(this);
    }
}

