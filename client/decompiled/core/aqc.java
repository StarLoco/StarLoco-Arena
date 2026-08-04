/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import java.util.Map;

public final class aqc {
    static final char cNN = '{';
    static final char mn = '}';
    static final String cNO = "{}";
    private static final char If = '\\';

    public static final String format(String string, Object object) {
        return aqc.d(string, new Object[]{object});
    }

    public static final String format(String string, Object object, Object object2) {
        return aqc.d(string, new Object[]{object, object2});
    }

    public static final String d(String string, Object[] objectArray) {
        if (string == null) {
            return null;
        }
        if (objectArray == null) {
            return string;
        }
        int n2 = 0;
        StringBuffer stringBuffer = new StringBuffer(string.length() + 50);
        for (int j = 0; j < objectArray.length; ++j) {
            int n3 = string.indexOf(cNO, n2);
            if (n3 == -1) {
                if (n2 == 0) {
                    return string;
                }
                stringBuffer.append(string.substring(n2, string.length()));
                return stringBuffer.toString();
            }
            if (aqc.y(string, n3)) {
                if (!aqc.z(string, n3)) {
                    --j;
                    stringBuffer.append(string.substring(n2, n3 - 1));
                    stringBuffer.append('{');
                    n2 = n3 + 1;
                    continue;
                }
                stringBuffer.append(string.substring(n2, n3 - 1));
                aqc.a(stringBuffer, objectArray[j], new HashMap());
                n2 = n3 + 2;
                continue;
            }
            stringBuffer.append(string.substring(n2, n3));
            aqc.a(stringBuffer, objectArray[j], new HashMap());
            n2 = n3 + 2;
        }
        stringBuffer.append(string.substring(n2, string.length()));
        return stringBuffer.toString();
    }

    static final boolean y(String string, int n2) {
        if (n2 == 0) {
            return false;
        }
        char c = string.charAt(n2 - 1);
        return c == '\\';
    }

    static final boolean z(String string, int n2) {
        return n2 >= 2 && string.charAt(n2 - 2) == '\\';
    }

    private static void a(StringBuffer stringBuffer, Object object, Map map) {
        if (object == null) {
            stringBuffer.append("null");
            return;
        }
        if (!object.getClass().isArray()) {
            stringBuffer.append(object);
        } else if (object instanceof boolean[]) {
            aqc.a(stringBuffer, (boolean[])object);
        } else if (object instanceof byte[]) {
            aqc.a(stringBuffer, (byte[])object);
        } else if (object instanceof char[]) {
            aqc.a(stringBuffer, (char[])object);
        } else if (object instanceof short[]) {
            aqc.a(stringBuffer, (short[])object);
        } else if (object instanceof int[]) {
            aqc.a(stringBuffer, (int[])object);
        } else if (object instanceof long[]) {
            aqc.a(stringBuffer, (long[])object);
        } else if (object instanceof float[]) {
            aqc.a(stringBuffer, (float[])object);
        } else if (object instanceof double[]) {
            aqc.a(stringBuffer, (double[])object);
        } else {
            aqc.a(stringBuffer, (Object[])object, map);
        }
    }

    private static void a(StringBuffer stringBuffer, Object[] objectArray, Map map) {
        stringBuffer.append('[');
        if (!map.containsKey(objectArray)) {
            map.put(objectArray, null);
            int n2 = objectArray.length;
            for (int j = 0; j < n2; ++j) {
                aqc.a(stringBuffer, objectArray[j], map);
                if (j == n2 - 1) continue;
                stringBuffer.append(", ");
            }
            map.remove(objectArray);
        } else {
            stringBuffer.append("...");
        }
        stringBuffer.append(']');
    }

    private static void a(StringBuffer stringBuffer, boolean[] blArray) {
        stringBuffer.append('[');
        int n2 = blArray.length;
        for (int j = 0; j < n2; ++j) {
            stringBuffer.append(blArray[j]);
            if (j == n2 - 1) continue;
            stringBuffer.append(", ");
        }
        stringBuffer.append(']');
    }

    private static void a(StringBuffer stringBuffer, byte[] byArray) {
        stringBuffer.append('[');
        int n2 = byArray.length;
        for (int j = 0; j < n2; ++j) {
            stringBuffer.append(byArray[j]);
            if (j == n2 - 1) continue;
            stringBuffer.append(", ");
        }
        stringBuffer.append(']');
    }

    private static void a(StringBuffer stringBuffer, char[] cArray) {
        stringBuffer.append('[');
        int n2 = cArray.length;
        for (int j = 0; j < n2; ++j) {
            stringBuffer.append(cArray[j]);
            if (j == n2 - 1) continue;
            stringBuffer.append(", ");
        }
        stringBuffer.append(']');
    }

    private static void a(StringBuffer stringBuffer, short[] sArray) {
        stringBuffer.append('[');
        int n2 = sArray.length;
        for (int j = 0; j < n2; ++j) {
            stringBuffer.append(sArray[j]);
            if (j == n2 - 1) continue;
            stringBuffer.append(", ");
        }
        stringBuffer.append(']');
    }

    private static void a(StringBuffer stringBuffer, int[] nArray) {
        stringBuffer.append('[');
        int n2 = nArray.length;
        for (int j = 0; j < n2; ++j) {
            stringBuffer.append(nArray[j]);
            if (j == n2 - 1) continue;
            stringBuffer.append(", ");
        }
        stringBuffer.append(']');
    }

    private static void a(StringBuffer stringBuffer, long[] lArray) {
        stringBuffer.append('[');
        int n2 = lArray.length;
        for (int j = 0; j < n2; ++j) {
            stringBuffer.append(lArray[j]);
            if (j == n2 - 1) continue;
            stringBuffer.append(", ");
        }
        stringBuffer.append(']');
    }

    private static void a(StringBuffer stringBuffer, float[] fArray) {
        stringBuffer.append('[');
        int n2 = fArray.length;
        for (int j = 0; j < n2; ++j) {
            stringBuffer.append(fArray[j]);
            if (j == n2 - 1) continue;
            stringBuffer.append(", ");
        }
        stringBuffer.append(']');
    }

    private static void a(StringBuffer stringBuffer, double[] dArray) {
        stringBuffer.append('[');
        int n2 = dArray.length;
        for (int j = 0; j < n2; ++j) {
            stringBuffer.append(dArray[j]);
            if (j == n2 - 1) continue;
            stringBuffer.append(", ");
        }
        stringBuffer.append(']');
    }
}

