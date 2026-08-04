/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from GC
 */
public class gc_1
implements hR {
    private static final gc_1 bbu = new gc_1();
    private static final mw_0 bbv = new mw_0();

    public static gc_1 Qr() {
        return bbu;
    }

    private gc_1() {
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.map");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(bbv)) {
            mw_0 mw_02 = (mw_0)lJ2;
            afh_1.aRG().a(mw_02);
        }
        mk_12.b(this);
    }
}

