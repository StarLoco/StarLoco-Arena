/*
 * Decompiled with CFR 0.152.
 */
public class MY
implements hR {
    private static final MY byD = new MY();
    private static final fw_0 byE = new fw_0();

    public static MY ZZ() {
        return byD;
    }

    private MY() {
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.achievementType");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(byE)) {
            fw_0 fw_02 = (fw_0)lJ2;
            ajk_1 ajk_12 = new ajk_1(fw_02.CJ());
            qy_2.ady().a(ajk_12);
        }
        mk_12.b(this);
    }
}

