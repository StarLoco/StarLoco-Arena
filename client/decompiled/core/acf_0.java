/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aCf
 */
public enum acf_0 {
    dtT,
    dtU,
    dtV,
    dtW;


    public static acf_0 kv(String string) {
        acf_0[] acf_0Array;
        for (acf_0 acf_02 : acf_0Array = acf_0.values()) {
            if (!acf_02.name().equals(string.toUpperCase())) continue;
            return acf_02;
        }
        return acf_0Array[0];
    }
}

