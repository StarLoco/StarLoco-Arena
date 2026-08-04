/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from pp
 */
public final class pp_0 {
    public static final short abC = 0;
    public static final short abD = 1;
    public static final short abE = 2;
    public static final short abF = 4;
    public static final short abG = 0;
    public static final short abH = 7;
    public static final short abI = 8;
    public static final short abJ = 16;
    public static final short abK = 32;
    public static final short abL = 32;
    public static final short abM = 64;
    public static final short abN = 128;
    public static final short abO = 256;
    public static final short abP = 512;
    public static final short abQ = 1024;
    public static final short abR = 2048;
    public static final short abS = 4096;
    public static final short ANNOTATION = 8192;
    public static final short abT = 16384;
    private static final Object[] abU = new Object[]{"public", new Short(1), "private", new Short(2), "protected", new Short(4), "static", new Short(8), "final", new Short(16), "synchronized", new Short(32), "volatile", new Short(64), "transient", new Short(128), "native", new Short(256), "interface", new Short(512), "abstract", new Short(1024), "strictfp", new Short(2048)};

    private pp_0() {
    }

    public static boolean N(short s) {
        return (s & 7) == 1;
    }

    public static boolean O(short s) {
        return (s & 7) == 2;
    }

    public static boolean P(short s) {
        return (s & 7) == 4;
    }

    public static boolean Q(short s) {
        return (s & 7) == 0;
    }

    public static short h(short s, short s2) {
        return (short)(s & 0xFFFFFFF8 | s2);
    }

    public static String R(short s) {
        if (s == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int j = 0; j < abU.length; j += 2) {
            if ((s & (Short)abU[j + 1]) == 0) continue;
            if (stringBuffer.length() > 0) {
                stringBuffer.append(' ');
            }
            stringBuffer.append((String)abU[j]);
        }
        return stringBuffer.toString();
    }
}

