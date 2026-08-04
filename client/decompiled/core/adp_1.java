/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aDP
 */
public class adp_1
implements hR {
    private static final adp_1 dzg = new adp_1();
    private static final jz_2 dzh = new jz_2();

    public static adp_1 aPI() {
        return dzg;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.summoning");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(dzh)) {
            jz_2 jz_22 = (jz_2)lJ2;
            int n2 = jz_22.getId();
            int n3 = jz_22.ok();
            int n4 = jz_22.ol();
            int n5 = jz_22.om();
            int n6 = jz_22.oo();
            int[] nArray = jz_22.on().nm();
            int n7 = jz_22.ot();
            int n8 = jz_22.ou();
            boolean bl2 = jz_22.op();
            boolean bl3 = jz_22.oq();
            boolean bl4 = jz_22.or();
            boolean bl5 = jz_22.os();
            boolean bl6 = jz_22.ov();
            boolean bl7 = jz_22.oy();
            byte by = jz_22.ox();
            int n9 = jz_22.oz();
            aJt aJt2 = new aJt(n2, n3, n4, n5, n6, nArray, n7, n8, bl2, bl3, bl4, bl5, bl6, bl7, by, n9);
            ER.OC().a(aJt2);
        }
        mk_12.b(this);
    }
}

