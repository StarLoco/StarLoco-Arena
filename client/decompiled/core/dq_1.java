/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from dq
 */
public class dq_1
implements hR {
    private static final dq_1 lw = new dq_1();
    private static final bg_0 lx = new bg_0();

    public static dq_1 fO() {
        return lw;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.sphereBoard");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(lx)) {
            bg_0 bg_02 = (bg_0)lJ2;
            Ei ei = new Ei(bg_02.getId(), bg_02.cu(), bg_02.ct(), bg_02.cw(), bg_02.cv(), bg_02.cx(), bg_02.cy());
            akp_1.aVO().a(ei);
        }
        mk_12.b(this);
    }
}

