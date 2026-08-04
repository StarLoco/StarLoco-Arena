/*
 * Decompiled with CFR 0.152.
 */
import java.util.List;

public class apS
implements hR {
    private static final apS cNH = new apS();
    private static final co_1 cNI = new co_1();

    public static apS aDW() {
        return cNH;
    }

    private apS() {
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.spell");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(cNI)) {
            co_1 co_12 = (co_1)lJ2;
            int n2 = co_12.el();
            byte by = co_12.eo();
            byte by2 = co_12.ep();
            byte by3 = co_12.er();
            byte by4 = co_12.eq();
            byte by5 = co_12.es();
            boolean bl2 = co_12.ew();
            boolean bl3 = co_12.ex();
            byte by6 = co_12.ev();
            byte by7 = co_12.eu();
            int n3 = co_12.getValue();
            int n4 = co_12.en();
            boolean bl4 = co_12.ey();
            int n5 = co_12.eA();
            int n6 = co_12.em();
            String string = co_12.ez();
            List list = ahp_1.a(null, string);
            boolean bl5 = co_12.eB();
            byte by8 = co_12.et();
            boolean bl6 = co_12.eD();
            boolean bl7 = co_12.eF();
            long[] lArray = co_12.eE();
            fv fv2 = je_1.Wa().el(co_12.eG());
            yp_2 yp_22 = new yp_2(n2, n6, by, by2, by4, by3, by5, by8, bl2, bl3, by6, by7, n3, n4, bl4, n5, list, bl5, bl6, lArray, bl7, fv2);
            for (Ht ht : co_12.eC()) {
                xj_0 xj_02 = abw_2.c(ht);
                yp_22.a(xj_02);
                WF.ajj().b(xj_02);
            }
            je_1.Wa().g(yp_22);
            if (yp_22.jd() == null) continue;
            yp_22.jd().a(new zd_2(yp_22));
        }
        mk_12.b(this);
    }
}

