/*
 * Decompiled with CFR 0.152.
 */
public enum aDM {
    dyU,
    dyV,
    dyW;


    public static aDM kV(String string) {
        aDM[] aDMArray;
        for (aDM aDM2 : aDMArray = aDM.values()) {
            if (!aDM2.name().equals(string.toUpperCase())) continue;
            return aDM2;
        }
        return aDMArray[0];
    }
}

