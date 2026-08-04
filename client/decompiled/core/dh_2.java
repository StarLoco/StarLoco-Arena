/*
 * Decompiled with CFR 0.152.
 */
import java.util.Properties;

/*
 * Renamed from dH
 */
public class dh_2 {
    static final String mm = "${";
    static final char mn = '}';
    static final int mo = 2;
    static final int mp = 1;
    static final String mq = "_IS_UNDEFINED";

    public static Object a(String string, Class clazz, vU vU2) {
        ClassLoader classLoader = vU2.getClass().getClassLoader();
        return dh_2.a(string, clazz, classLoader);
    }

    public static Object a(String string, Class clazz, ClassLoader classLoader) {
        if (string == null) {
            throw new NullPointerException();
        }
        try {
            Class<?> clazz2 = null;
            clazz2 = classLoader.loadClass(string);
            if (!clazz.isAssignableFrom(clazz2)) {
                throw new aKg(clazz, clazz2);
            }
            return clazz2.newInstance();
        }
        catch (aKg aKg2) {
            throw aKg2;
        }
        catch (Throwable throwable) {
            throw new ajl_1("Failed to instantiate type " + string, throwable);
        }
    }

    public static String a(String string, ms_1 ms_12) {
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = 0;
        while (true) {
            int n3;
            if ((n3 = string.indexOf(mm, n2)) == -1) {
                if (n2 == 0) {
                    return string;
                }
                stringBuffer.append(string.substring(n2, string.length()));
                return stringBuffer.toString();
            }
            stringBuffer.append(string.substring(n2, n3));
            int n4 = string.indexOf(125, n3);
            if (n4 == -1) {
                throw new IllegalArgumentException('\"' + string + "\" has no closing brace. Opening brace at position " + n3 + '.');
            }
            String string2 = string.substring(n3 += 2, n4);
            String[] stringArray = dh_2.O(string2);
            String string3 = stringArray[0];
            String string4 = stringArray[1];
            String string5 = null;
            string5 = ms_12.getProperty(string3);
            if (string5 == null) {
                string5 = dh_2.getSystemProperty(string3, null);
            }
            if (string5 == null) {
                string5 = string4;
            }
            if (string5 != null) {
                String string6 = dh_2.a(string5, ms_12);
                stringBuffer.append(string6);
            } else {
                stringBuffer.append(string3 + mq);
            }
            n2 = n4 + 1;
        }
    }

    public static String getSystemProperty(String string, String string2) {
        try {
            return System.getProperty(string, string2);
        }
        catch (SecurityException securityException) {
            return string2;
        }
    }

    public static String getSystemProperty(String string) {
        try {
            return System.getProperty(string);
        }
        catch (SecurityException securityException) {
            return null;
        }
    }

    public static Properties fW() {
        try {
            return System.getProperties();
        }
        catch (SecurityException securityException) {
            return new Properties();
        }
    }

    public static String[] O(String string) {
        String[] stringArray = new String[2];
        stringArray[0] = string;
        int n2 = string.indexOf(":-");
        if (n2 != -1) {
            stringArray[0] = string.substring(0, n2);
            stringArray[1] = string.substring(n2 + 2);
        }
        return stringArray;
    }

    public static boolean toBoolean(String string, boolean bl2) {
        if (string == null) {
            return bl2;
        }
        String string2 = string.trim();
        if ("true".equalsIgnoreCase(string2)) {
            return true;
        }
        if ("false".equalsIgnoreCase(string2)) {
            return false;
        }
        return bl2;
    }

    public static boolean isEmpty(String string) {
        return string == null || "".equals(string);
    }
}

