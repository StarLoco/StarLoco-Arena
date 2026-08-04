/*
 * Decompiled with CFR 0.152.
 */
public enum acX {
    clm,
    cln,
    clo,
    clp;


    public static acX hx(String string) {
        acX[] acXArray;
        for (acX acX2 : acXArray = acX.values()) {
            if (!acX2.name().equals(string.toUpperCase())) continue;
            return acX2;
        }
        return acXArray[0];
    }
}

