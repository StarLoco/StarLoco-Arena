/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from eh
 */
public class eh_2
implements hR {
    private static final eh_2 nZ = new eh_2();
    private static final aPp oa = new aPp();
    private static final uh_0 ob = new uh_0();

    public static eh_2 he() {
        return nZ;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.card");
    }

    public void a(mk_1 mk_12) {
        Object object;
        int n2;
        boolean bl2;
        boolean bl3;
        int n3;
        int n4;
        int n5;
        int n6;
        lJ lJ2;
        lJ[] lJArray;
        for (lJ lJ3 : lJArray = aly_1.aAQ().a(oa)) {
            lJ2 = (aPp)lJ3;
            n6 = ((aPp)lJ2).aZb();
            int n7 = ((aPp)lJ2).getValue();
            n5 = ((aPp)lJ2).tm();
            n4 = ((aPp)lJ2).tr();
            boolean bl6 = ((aPp)lJ2).isUnique();
            boolean bl4 = ((aPp)lJ2).to();
            n3 = ((aPp)lJ2).ts();
            bl3 = ((aPp)lJ2).tp();
            boolean bl5 = ((aPp)lJ2).tq();
            bl2 = ((aPp)lJ2).tt();
            akw_0[] akw_0Array = ((aPp)lJ2).tu();
            float[] fArray = ((aPp)lJ2).tk();
            byte by = ((aPp)lJ2).tl();
            n2 = ((aPp)lJ2).getRank();
            object = aMK.pq(((aPp)lJ2).aZc());
            np_1[] np_1Array = ((aPp)lJ2).tv();
            short s = ((aPp)lJ2).tw();
            short s2 = ((aPp)lJ2).tx();
            short s3 = ((aPp)lJ2).tz();
            byte by2 = ((aPp)lJ2).tA();
            int n8 = ((aPp)lJ2).tB();
            aim_1 aim_12 = ((aPp)lJ2).aZf();
            byte by3 = ((aPp)lJ2).tD();
            int n9 = ((aPp)lJ2).tE();
            xj xj2 = new xj(n6, (aMK)object, n5, n7, n4, bl6, bl4, n3, bl3, bl5, bl2, akw_0Array, by, fArray, n2, np_1Array, s, s2, s3, by2, n8, aim_12, by3, n9);
            la_0.XJ().a(xj2);
            if (xj2.tj() == aMK.dYz) {
                cF.a(xj2);
            }
            if (n5 != 0) {
                aqy_0 aqy_02 = ayc_0.aLE().mS(n5);
                aqy_02.b(xj2);
            }
            if (!bl3) {
                xj2 = new xj(-n6, (aMK)object, n5, n7, n4, bl6, false, n3, true, bl5, bl2, akw_0Array, by, fArray, n2, np_1Array, s, s2, s3, by2, n8, aim_12, by3, n9);
                la_0.XJ().a(xj2);
            }
            for (int j = 0; j < np_1Array.length; ++j) {
                Object object2 = np_1Array[j];
                if (!(object2 instanceof agp)) continue;
                WF.ajj().b(((agp)object2).awq());
            }
        }
        jk_1.mf().initialize();
        for (lJ lJ3 : lJArray = aly_1.aAQ().a(ob)) {
            lJ2 = (uh_0)lJ3;
            n6 = ((uh_0)lJ2).getId();
            vi_1 vi_12 = vi_1.ap((byte)((uh_0)lJ2).getType());
            n5 = ((uh_0)lJ2).eo();
            n4 = ((uh_0)lJ2).ex() ? 1 : 0;
            int n10 = ((uh_0)lJ2).AA();
            int n11 = ((uh_0)lJ2).Az();
            n3 = ((uh_0)lJ2).ew() ? 1 : 0;
            bl3 = ((uh_0)lJ2).ey();
            int n12 = ((uh_0)lJ2).getValue();
            bl2 = ((uh_0)lJ2).AB();
            boolean bl6 = ((uh_0)lJ2).AC();
            int n13 = ((uh_0)lJ2).eA();
            int n14 = ((uh_0)lJ2).AE();
            n2 = ((uh_0)lJ2).AD() ? 1 : 0;
            object = new ve_0(n6, vi_12, n14, n5, n4 != 0, n10, n11, n3 != 0, bl3, n12, bl2, bl6, n13, n2 != 0);
            for (Ht ht : ((uh_0)lJ2).eC()) {
                xj_0 xj_02 = abw_2.c(ht);
                ((jb_2)object).a(xj_02);
                WF.ajj().b(xj_02);
            }
            aca_0.aOq().a((jb_2)object);
        }
        mk_12.b(this);
    }
}

