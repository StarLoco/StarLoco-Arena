/*
 * Decompiled with CFR 0.152.
 */
public class axG {
    public static final byte djX = 0;
    public static final byte djY = 1;
    public static final byte djZ = 2;
    public static final byte dka = 3;

    public static byte a(byte by, float[] fArray) {
        switch (by) {
            case 0: {
                mg_1.qV().d(fArray);
                if (mg_1.qV().qW()) break;
                return 1;
            }
            case 2: {
                mg_1.qV().d(fArray);
                if (mg_1.qV().qW()) break;
                return 3;
            }
            case 1: {
                mg_1.qV().d(fArray);
                return by;
            }
        }
        return by;
    }
}

