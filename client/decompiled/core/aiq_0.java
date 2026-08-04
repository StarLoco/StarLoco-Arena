/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aiq
 */
public enum aiq_0 {
    cxU,
    cxV,
    cxW,
    cxX;


    public boolean isVertical() {
        return this == cxU || this == cxV;
    }

    public boolean isHorizontal() {
        return this == cxX || this == cxW;
    }

    public static aiq_0 il(String string) {
        aiq_0[] aiq_0Array;
        for (aiq_0 aiq_02 : aiq_0Array = aiq_0.values()) {
            if (!aiq_02.name().equals(string.toUpperCase())) continue;
            return aiq_02;
        }
        return aiq_0Array[0];
    }
}

