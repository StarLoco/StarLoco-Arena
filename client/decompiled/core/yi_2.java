/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from yI
 */
public class yi_2 {
    public static void d(akn_1 akn_12) {
        for (akn_1 akn_13 = akn_12; akn_13 != null; akn_13 = akn_13.azY()) {
            acz_0 acz_02;
            if (akn_13 instanceof aci_2) {
                acz_02 = (aci_2)akn_13;
                acz_02.start();
                continue;
            }
            if (!(akn_13 instanceof xz_2)) continue;
            acz_02 = (xz_2)akn_13;
            akn_1 akn_14 = ((xz_2)acz_02).aAc;
            yi_2.d(akn_14);
        }
    }

    public static akn_1 e(akn_1 akn_12) {
        akn_1 akn_13;
        akn_1 akn_14 = akn_12;
        while (akn_14 != null && (akn_13 = akn_14.azY()) != null) {
            akn_14 = akn_13;
        }
        return akn_14;
    }
}

