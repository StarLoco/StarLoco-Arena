/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;

/*
 * Renamed from Zr
 */
public final class zr_1 {
    private static zr_1 cdb = new zr_1();
    private static final ga_2 xa = ga_2.Qo();

    private zr_1() {
    }

    public static zr_1 anx() {
        return cdb;
    }

    public static boolean N(String string, String string2) {
        return zr_1.d(string, string2, true);
    }

    public static boolean d(String string, String string2, boolean bl2) {
        String string3;
        int n2;
        if (string2.startsWith(File.separator) != string.startsWith(File.separator)) {
            return false;
        }
        String[] stringArray = zr_1.gX(string);
        String[] stringArray2 = zr_1.gX(string2);
        int n3 = 0;
        int n4 = stringArray.length - 1;
        int n5 = stringArray2.length - 1;
        for (n2 = 0; n3 <= n4 && n2 <= n5 && !(string3 = stringArray[n3]).equals("**"); ++n3, ++n2) {
            if (zr_1.f(string3, stringArray2[n2], bl2)) continue;
            return false;
        }
        if (n2 > n5) {
            return true;
        }
        return n3 <= n4;
    }

    public static boolean O(String string, String string2) {
        return zr_1.e(string, string2, true);
    }

    public static boolean e(String string, String string2, boolean bl2) {
        String string3;
        int n2;
        String[] stringArray = zr_1.gX(string);
        String[] stringArray2 = zr_1.gX(string2);
        int n3 = 0;
        int n4 = stringArray.length - 1;
        int n5 = stringArray2.length - 1;
        for (n2 = 0; n3 <= n4 && n2 <= n5 && !(string3 = stringArray[n3]).equals("**"); ++n3, ++n2) {
            if (zr_1.f(string3, stringArray2[n2], bl2)) continue;
            stringArray = null;
            stringArray2 = null;
            return false;
        }
        if (n2 > n5) {
            for (int j = n3; j <= n4; ++j) {
                if (stringArray[j].equals("**")) continue;
                stringArray = null;
                stringArray2 = null;
                return false;
            }
            return true;
        }
        if (n3 > n4) {
            stringArray = null;
            stringArray2 = null;
            return false;
        }
        while (n3 <= n4 && n2 <= n5 && !(string3 = stringArray[n4]).equals("**")) {
            if (!zr_1.f(string3, stringArray2[n5], bl2)) {
                stringArray = null;
                stringArray2 = null;
                return false;
            }
            --n4;
            --n5;
        }
        if (n2 > n5) {
            for (int j = n3; j <= n4; ++j) {
                if (stringArray[j].equals("**")) continue;
                stringArray = null;
                stringArray2 = null;
                return false;
            }
            return true;
        }
        while (n3 != n4 && n2 <= n5) {
            int n6;
            int n7 = -1;
            for (n6 = n3 + 1; n6 <= n4; ++n6) {
                if (!stringArray[n6].equals("**")) continue;
                n7 = n6;
                break;
            }
            if (n7 == n3 + 1) {
                ++n3;
                continue;
            }
            n6 = n7 - n3 - 1;
            int n8 = n5 - n2 + 1;
            int n9 = -1;
            block6: for (int j = 0; j <= n8 - n6; ++j) {
                for (int i2 = 0; i2 < n6; ++i2) {
                    String string4 = stringArray[n3 + i2 + 1];
                    String string5 = stringArray2[n2 + j + i2];
                    if (!zr_1.f(string4, string5, bl2)) continue block6;
                }
                n9 = n2 + j;
                break;
            }
            if (n9 == -1) {
                stringArray = null;
                stringArray2 = null;
                return false;
            }
            n3 = n7;
            n2 = n9 + n6;
        }
        for (int j = n3; j <= n4; ++j) {
            if (stringArray[j].equals("**")) continue;
            stringArray = null;
            stringArray2 = null;
            return false;
        }
        return true;
    }

    public static boolean P(String string, String string2) {
        return zr_1.f(string, string2, true);
    }

    public static boolean f(String string, String string2, boolean bl2) {
        char c;
        int n2;
        char[] cArray = string.toCharArray();
        char[] cArray2 = string2.toCharArray();
        int n3 = 0;
        int n4 = cArray.length - 1;
        int n5 = 0;
        int n6 = cArray2.length - 1;
        boolean bl3 = false;
        for (n2 = 0; n2 < cArray.length; ++n2) {
            if (cArray[n2] != '*') continue;
            bl3 = true;
            break;
        }
        if (!bl3) {
            if (n4 != n6) {
                return false;
            }
            for (n2 = 0; n2 <= n4; ++n2) {
                char c2 = cArray[n2];
                if (c2 == '?') continue;
                if (bl2 && c2 != cArray2[n2]) {
                    return false;
                }
                if (bl2 || Character.toUpperCase(c2) == Character.toUpperCase(cArray2[n2])) continue;
                return false;
            }
            return true;
        }
        if (n4 == 0) {
            return true;
        }
        while ((c = cArray[n3]) != '*' && n5 <= n6) {
            if (c != '?') {
                if (bl2 && c != cArray2[n5]) {
                    return false;
                }
                if (!bl2 && Character.toUpperCase(c) != Character.toUpperCase(cArray2[n5])) {
                    return false;
                }
            }
            ++n3;
            ++n5;
        }
        if (n5 > n6) {
            for (n2 = n3; n2 <= n4; ++n2) {
                if (cArray[n2] == '*') continue;
                return false;
            }
            return true;
        }
        while ((c = cArray[n4]) != '*' && n5 <= n6) {
            if (c != '?') {
                if (bl2 && c != cArray2[n6]) {
                    return false;
                }
                if (!bl2 && Character.toUpperCase(c) != Character.toUpperCase(cArray2[n6])) {
                    return false;
                }
            }
            --n4;
            --n6;
        }
        if (n5 > n6) {
            for (n2 = n3; n2 <= n4; ++n2) {
                if (cArray[n2] == '*') continue;
                return false;
            }
            return true;
        }
        while (n3 != n4 && n5 <= n6) {
            int n7;
            n2 = -1;
            for (n7 = n3 + 1; n7 <= n4; ++n7) {
                if (cArray[n7] != '*') continue;
                n2 = n7;
                break;
            }
            if (n2 == n3 + 1) {
                ++n3;
                continue;
            }
            n7 = n2 - n3 - 1;
            int n8 = n6 - n5 + 1;
            int n9 = -1;
            block8: for (int j = 0; j <= n8 - n7; ++j) {
                for (int i2 = 0; i2 < n7; ++i2) {
                    c = cArray[n3 + i2 + 1];
                    if (c != '?' && (bl2 && c != cArray2[n5 + j + i2] || !bl2 && Character.toUpperCase(c) != Character.toUpperCase(cArray2[n5 + j + i2]))) continue block8;
                }
                n9 = n5 + j;
                break;
            }
            if (n9 == -1) {
                return false;
            }
            n3 = n2;
            n5 = n9 + n7;
        }
        for (n2 = n3; n2 <= n4; ++n2) {
            if (cArray[n2] == '*') continue;
            return false;
        }
        return true;
    }

    public static Vector gW(String string) {
        return zr_1.Q(string, File.separator);
    }

    public static Vector Q(String object, String string) {
        Object object2;
        Vector<String> vector = new Vector<String>();
        if (ga_2.isAbsolutePath((String)object)) {
            object2 = xa.ea((String)object);
            vector.add(object2[0]);
            object = object2[1];
        }
        object2 = new StringTokenizer((String)object, string);
        while (((StringTokenizer)object2).hasMoreTokens()) {
            vector.addElement(((StringTokenizer)object2).nextToken());
        }
        return vector;
    }

    private static String[] gX(String string) {
        String string2 = null;
        if (ga_2.isAbsolutePath(string)) {
            String[] stringArray = xa.ea(string);
            string2 = stringArray[0];
            string = stringArray[1];
        }
        char c = File.separatorChar;
        int n2 = 0;
        int n3 = string.length();
        int n4 = 0;
        for (int j = 0; j < n3; ++j) {
            if (string.charAt(j) != c) continue;
            if (j != n2) {
                ++n4;
            }
            n2 = j + 1;
        }
        if (n3 != n2) {
            ++n4;
        }
        String[] stringArray = new String[n4 + (string2 == null ? 0 : 1)];
        if (string2 != null) {
            stringArray[0] = string2;
            n4 = 1;
        } else {
            n4 = 0;
        }
        n2 = 0;
        for (int j = 0; j < n3; ++j) {
            if (string.charAt(j) != c) continue;
            if (j != n2) {
                String string3 = string.substring(n2, j);
                stringArray[n4++] = string3;
            }
            n2 = j + 1;
        }
        if (n3 != n2) {
            String string4;
            stringArray[n4] = string4 = string.substring(n2);
        }
        return stringArray;
    }

    public static boolean a(File file, File file2, int n2) {
        if (!file.exists()) {
            return false;
        }
        if (!file2.exists()) {
            return true;
        }
        return file.lastModified() - (long)n2 > file2.lastModified();
    }

    public static boolean a(iv_1 iv_12, iv_1 iv_13, int n2) {
        return zr_1.a(iv_12, iv_13, (long)n2);
    }

    public static boolean a(iv_1 iv_12, iv_1 iv_13, long l2) {
        long l3 = iv_12.getLastModified();
        boolean bl2 = iv_12 instanceof ash_0 ? l3 != 0L : iv_12.lI();
        long l4 = iv_13.getLastModified();
        if (l4 == 0L) {
            return true;
        }
        return l3 - l2 > l4;
    }

    public static String gY(String string) {
        StringBuffer stringBuffer = new StringBuffer();
        if (string != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(string);
            while (stringTokenizer.hasMoreTokens()) {
                stringBuffer.append(stringTokenizer.nextToken());
            }
        }
        return stringBuffer.toString();
    }

    public static boolean gZ(String string) {
        return string.indexOf(42) != -1 || string.indexOf(63) != -1;
    }

    public static String ha(String string) {
        String[] stringArray = zr_1.gX(string);
        StringBuffer stringBuffer = new StringBuffer();
        for (int j = 0; j < stringArray.length && !zr_1.gZ(stringArray[j]); ++j) {
            if (j > 0 && stringBuffer.charAt(stringBuffer.length() - 1) != File.separatorChar) {
                stringBuffer.append(File.separator);
            }
            stringBuffer.append(stringArray[j]);
        }
        return stringBuffer.toString();
    }
}

