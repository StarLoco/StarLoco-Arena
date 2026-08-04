/*
 * Decompiled with CFR 0.152.
 */
public enum aaM {
    cgC,
    cgD,
    cgE,
    cgF;


    public static aaM hf(String string) {
        aaM[] aaMArray;
        for (aaM aaM2 : aaMArray = aaM.values()) {
            if (!aaM2.name().equals(string.toUpperCase())) continue;
            return aaM2;
        }
        return aaMArray[0];
    }

    public static aaM jw(int n2) {
        aaM[] aaMArray = aaM.values();
        if (aaMArray.length > n2 && n2 >= 0) {
            return aaMArray[n2];
        }
        return null;
    }
}

