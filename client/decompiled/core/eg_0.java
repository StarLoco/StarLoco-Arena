/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from eg
 */
public class eg_0 {
    static final String[] nY = new String[]{" ", "  ", "    ", "        ", "                ", "                                "};

    public static final void a(StringBuffer stringBuffer, String string, int n2) {
        int n3 = 0;
        if (string != null) {
            n3 = string.length();
        }
        if (n3 < n2) {
            eg_0.spacePad(stringBuffer, n2 - n3);
        }
        if (string != null) {
            stringBuffer.append(string);
        }
    }

    public static final void b(StringBuffer stringBuffer, String string, int n2) {
        int n3 = 0;
        if (string != null) {
            n3 = string.length();
        }
        if (string != null) {
            stringBuffer.append(string);
        }
        if (n3 < n2) {
            eg_0.spacePad(stringBuffer, n2 - n3);
        }
    }

    public static final void spacePad(StringBuffer stringBuffer, int n2) {
        while (n2 >= 32) {
            stringBuffer.append(nY[5]);
            n2 -= 32;
        }
        for (int j = 4; j >= 0; --j) {
            if ((n2 & 1 << j) == 0) continue;
            stringBuffer.append(nY[j]);
        }
    }
}

