/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from xs
 */
public class xs_0
implements hR {
    private static final xs_0 ayC = new xs_0();
    private static final atF ayD = new atF();

    public static xs_0 Ei() {
        return ayC;
    }

    private xs_0() {
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.dialogReply");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(ayD)) {
            hp_2 hp_22;
            atF atF2 = (atF)lJ2;
            ed_0 ed_02 = Rq.aX(atF2.aGJ());
            if (ed_02 == null) {
                ed_02 = new ed_0(atF2.aGJ());
                Rq.a(ed_02);
            }
            if ((hp_22 = hp_2.aC(atF2.aGK())) != null) {
                hp_22.r(atF2.aGL());
            }
            ana_2 ana_22 = new ana_2(atF2.aBY(), atF2.aGJ(), atF2.aCa(), atF2.aCb(), hp_22);
            ed_02.b(ana_22);
        }
        mk_12.b(this);
    }
}

