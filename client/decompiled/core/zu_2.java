/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ZU
 */
public class zu_2 {
    public static String i(String string, String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string).append(".").append(string2).append(".").append(string3);
        return stringBuilder.toString();
    }

    public static String p(float[] fArray) {
        int n2 = (int)(fArray[0] * 255.0f);
        int n3 = (int)(fArray[1] * 255.0f);
        int n4 = (int)(fArray[2] * 255.0f);
        StringBuilder stringBuilder = new StringBuilder();
        if (n2 < 16) {
            stringBuilder.append("0");
        }
        stringBuilder.append(Integer.toHexString(n2));
        if (n3 < 16) {
            stringBuilder.append("0");
        }
        stringBuilder.append(Integer.toHexString(n3));
        if (n4 < 16) {
            stringBuilder.append("0");
        }
        stringBuilder.append(Integer.toHexString(n4));
        return stringBuilder.toString();
    }
}

