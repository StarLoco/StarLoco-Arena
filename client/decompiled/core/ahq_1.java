/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aHQ
 */
public enum ahq_1 {
    dNX,
    dNY,
    dNZ,
    dOa,
    dOb;


    public static ahq_1 lu(String string) {
        ahq_1[] ahq_1Array;
        for (ahq_1 ahq_12 : ahq_1Array = ahq_1.values()) {
            if (!ahq_12.name().equals(string.toUpperCase())) continue;
            return ahq_12;
        }
        return ahq_1Array[0];
    }
}

