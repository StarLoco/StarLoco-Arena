/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aNK
 */
public class ank_1
implements hR {
    private final ajd_0 dZR = new ajd_0();
    private static final ank_1 dZS = new ank_1();

    public static ank_1 aXL() {
        return dZS;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.fusionLaboratoryDefinition");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray = aly_1.aAQ().a(this.dZR);
        if (lJArray != null) {
            for (int j = lJArray.length - 1; 0 <= j; --j) {
                CN.a(new abe_1((ajd_0)lJArray[j]));
            }
        }
        mk_12.b(this);
    }
}

