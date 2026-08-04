/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aEa
 */
public enum aea_2 {
    dzs,
    dzt,
    dzu,
    dzv,
    dzw,
    dzx,
    dzy,
    dzz;


    public static aea_2 kW(String string) {
        aea_2[] aea_2Array;
        for (aea_2 aea_22 : aea_2Array = aea_2.values()) {
            if (!aea_22.name().equals(string.toUpperCase())) continue;
            return aea_22;
        }
        return aea_2Array[0];
    }
}

