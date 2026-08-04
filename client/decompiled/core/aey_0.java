/*
 * Decompiled with CFR 0.152.
 */
import java.io.UnsupportedEncodingException;
import java.text.StringCharacterIterator;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Renamed from aey
 */
public abstract class aey_0 {
    private static final String coW = "0123456789abcdef";

    public static byte[] hH(String string) {
        if (string == null) {
            return new byte[0];
        }
        try {
            return string.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return string.getBytes();
        }
    }

    public static String V(byte[] byArray) {
        if (byArray == null) {
            return null;
        }
        if (byArray.length == 0) {
            return "";
        }
        try {
            return new String(byArray, "UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return new String(byArray);
        }
    }

    public static String capitalize(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static String hI(String string) {
        char[] cArray = string.trim().toLowerCase().toCharArray();
        boolean bl2 = false;
        int n2 = cArray.length;
        for (int j = 0; j < n2; ++j) {
            if (Character.isLetter(cArray[j])) {
                if (bl2) continue;
                cArray[j] = Character.toUpperCase(cArray[j]);
                bl2 = true;
                continue;
            }
            bl2 = false;
        }
        return String.valueOf(cArray);
    }

    public static String hJ(String string) {
        if (string == null) {
            return null;
        }
        String string2 = string.toLowerCase();
        string2 = string2.replaceAll("(\u00e3|\u00e1|\u00e0|\u00e2|\u00e4)", "a");
        string2 = string2.replaceAll("(\u00e9|\u00e8|\u00ea|\u00eb)", "e");
        string2 = string2.replaceAll("(\u00ed|\u00ec|\u00ef|\u00ee)", "i");
        string2 = string2.replaceAll("(\u00f5|\u00f3|\u00f2|\u00f6|\u00f4)", "o");
        string2 = string2.replaceAll("(\u00fa|\u00f9|\u00fc|\u00fb)", "u");
        string2 = string2.replaceAll("(\u00f1)", "n");
        string2 = string2.replaceAll("(\u00e7)", "c");
        string2 = string2.trim();
        return string2;
    }

    public static String bm(int n2, int n3) {
        return aey_0.t(String.valueOf(n2), n3);
    }

    public static String t(String string, int n2) {
        StringBuffer stringBuffer = new StringBuffer(n2);
        for (int j = 0; j < n2 - string.length(); ++j) {
            stringBuffer.append("0");
        }
        stringBuffer.append(string);
        return stringBuffer.toString();
    }

    public static String dA(long l2) {
        return aey_0.c(l2, true);
    }

    public static String c(long l2, boolean bl2) {
        Date date = new Date(l2);
        String string = date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900);
        if (!bl2) {
            return string;
        }
        String string2 = (date.getHours() < 10 ? "0" + date.getHours() : Integer.valueOf(date.getHours())) + ":" + (date.getMinutes() < 10 ? "0" + date.getMinutes() : Integer.valueOf(date.getMinutes()));
        return string + " \u00e0 " + string2;
    }

    public static boolean hK(String string) {
        return string == null || string.trim().length() == 0 || string.equals("null");
    }

    public static boolean hL(String string) {
        return aey_0.hK(string) ? false : Pattern.matches("^[-]?\\d+$", string);
    }

    public static int a(char c, String string) {
        int n2 = 0;
        int n3 = string.length();
        for (int j = 0; j < n3; ++j) {
            if (string.charAt(j) != c) continue;
            ++n2;
        }
        return n2;
    }

    public static int a(char c, char[] cArray) {
        int n2 = 0;
        int n3 = cArray.length;
        for (int j = 0; j < n3; ++j) {
            if (cArray[j] != c) continue;
            ++n2;
        }
        return n2;
    }

    public static String hM(String string) {
        Pattern pattern = Pattern.compile("(.*)([0-9]+)");
        Matcher matcher = pattern.matcher(string);
        if (!matcher.matches()) {
            return string + " 0";
        }
        String string2 = matcher.group(2);
        int n2 = Integer.parseInt(string2);
        return matcher.group(1) + Integer.toString(n2 + 1);
    }

    public static boolean g(String string, String string2, boolean bl2) {
        if (bl2) {
            if (aey_0.hK(string)) {
                return aey_0.hK(string2);
            }
        } else {
            if (string == null) {
                return string2 == null;
            }
            if (string2 == null) {
                return false;
            }
        }
        return string.equals(string2);
    }

    public static int compare(String string, String string2) {
        if (string == null) {
            return string2 == null ? 0 : "".compareTo(string2);
        }
        return string.compareTo(string2 == null ? "" : string2);
    }

    public static String hN(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(string);
        char c = stringCharacterIterator.current();
        while (c != '\uffff') {
            if (c == '<') {
                stringBuilder.append("&lt;");
            } else if (c == '>') {
                stringBuilder.append("&gt;");
            } else if (c == '\"') {
                stringBuilder.append("&quot;");
            } else if (c == '\'') {
                stringBuilder.append("&#039;");
            } else if (c == '&') {
                stringBuilder.append("&amp;");
            } else {
                stringBuilder.append(c);
            }
            c = stringCharacterIterator.next();
        }
        return stringBuilder.toString();
    }

    public static String W(byte[] byArray) {
        StringBuffer stringBuffer = new StringBuffer(byArray.length * 2);
        for (int j = 0; j < byArray.length; ++j) {
            int n2 = byArray[j] & 0xFF;
            stringBuffer.append(coW.charAt(n2 >>> 4)).append(coW.charAt(n2 & 0xF));
        }
        return stringBuffer.toString();
    }
}

