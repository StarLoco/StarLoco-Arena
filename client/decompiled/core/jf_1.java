/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from JF
 */
public final class jf_1 {
    private jf_1() {
    }

    private static void a(fd_2 fd_22, TK tK) {
        if (fd_22 instanceof jy_2) {
            ((jy_2)fd_22).a(tK);
        } else if (fd_22 instanceof ln_2) {
            fd_2[] fd_2Array = ((ln_2)fd_22).bsi;
            for (int j = 0; j < fd_2Array.length; ++j) {
                jf_1.a(fd_2Array[j], tK);
            }
        } else {
            throw new aHY("Unexpected array or initializer class " + fd_22.getClass().getName());
        }
    }

    public static String a(Object[] objectArray, String string) {
        return jf_1.a(objectArray, string, 0, objectArray.length);
    }

    public static String a(Object[] objectArray, String string, int n2, int n3) {
        if (objectArray == null) {
            return "(null)";
        }
        if (n2 >= n3) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(objectArray[n2].toString());
        ++n2;
        while (n2 < n3) {
            stringBuffer.append(string);
            stringBuffer.append(objectArray[n2]);
            ++n2;
        }
        return stringBuffer.toString();
    }

    static void b(fd_2 fd_22, TK tK) {
        jf_1.a(fd_22, tK);
    }
}

