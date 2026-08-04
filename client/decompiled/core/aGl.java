/*
 * Decompiled with CFR 0.152.
 */
public class aGl
implements hR {
    private static final aGl dIh = new aGl();
    private static final ama_1 dIi = new ama_1();

    public static aGl aSl() {
        return dIh;
    }

    private aGl() {
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.event");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(dIi)) {
            ama_1 ama_12 = (ama_1)lJ2;
            int n2 = ama_12.aXe();
            boolean bl2 = ama_12.eB();
            tO tO2 = new tO(n2, bl2);
            for (Ht ht : ama_12.eC()) {
                xj_0 xj_02 = abw_2.c(ht);
                tO2.a(xj_02);
                WF.ajj().b(xj_02);
            }
            cw_1.eO().a(tO2);
        }
        mk_12.b(this);
    }
}

