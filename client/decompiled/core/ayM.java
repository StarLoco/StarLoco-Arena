/*
 * Decompiled with CFR 0.152.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Vector;

public final class ayM {
    private static final long dmI = 1024L;
    private static final long dmJ = 0x100000L;
    private static final long dmK = 0x40000000L;
    private static final long dmL = 0x10000000000L;
    private static final long dmM = 0x4000000000000L;
    public static final String LINE_SEP = System.getProperty("line.separator");

    private ayM() {
    }

    public static Vector jZ(String string) {
        return ayM.E(string, 10);
    }

    public static Vector E(String string, int n2) {
        Vector<String> vector = new Vector<String>();
        int n3 = -1;
        int n4 = 0;
        while ((n3 = string.indexOf(n2, n4)) != -1) {
            String string2 = string.substring(n4, n3);
            vector.addElement(string2);
            n4 = n3 + 1;
        }
        vector.addElement(string.substring(n4));
        return vector;
    }

    public static String replace(String string, String string2, String string3) {
        StringBuffer stringBuffer = new StringBuffer(string.length());
        int n2 = -1;
        int n3 = 0;
        while ((n2 = string.indexOf(string2, n3)) != -1) {
            stringBuffer.append(string.substring(n3, n2)).append(string3);
            n3 = n2 + string2.length();
        }
        stringBuffer.append(string.substring(n3));
        return stringBuffer.toString();
    }

    public static String i(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter, true);
        throwable.printStackTrace(printWriter);
        printWriter.flush();
        printWriter.close();
        return stringWriter.toString();
    }

    public static boolean a(StringBuffer stringBuffer, String string) {
        if (string.length() > stringBuffer.length()) {
            return false;
        }
        int n2 = stringBuffer.length() - 1;
        for (int j = string.length() - 1; j >= 0; --j) {
            if (stringBuffer.charAt(n2) != string.charAt(j)) {
                return false;
            }
            --n2;
        }
        return true;
    }

    public static String cs(String string) {
        StringBuffer stringBuffer = new StringBuffer();
        boolean bl2 = false;
        for (int j = 0; j < string.length(); ++j) {
            char c = string.charAt(j);
            if (!bl2) {
                if (c == '\\') {
                    bl2 = true;
                    continue;
                }
                stringBuffer.append(c);
                continue;
            }
            switch (c) {
                case '\\': {
                    stringBuffer.append('\\');
                    break;
                }
                case 'n': {
                    stringBuffer.append('\n');
                    break;
                }
                case 'r': {
                    stringBuffer.append('\r');
                    break;
                }
                case 't': {
                    stringBuffer.append('\t');
                    break;
                }
                case 'f': {
                    stringBuffer.append('\f');
                    break;
                }
                case 's': {
                    stringBuffer.append(" \t\n\r\f");
                    break;
                }
                default: {
                    stringBuffer.append(c);
                }
            }
            bl2 = false;
        }
        return stringBuffer.toString();
    }

    public static long ka(String string) {
        long l2 = 1L;
        char c = string.charAt(0);
        switch (c) {
            case '+': {
                string = string.substring(1);
                break;
            }
            case '-': {
                l2 = -1L;
                string = string.substring(1);
                break;
            }
        }
        char c2 = string.charAt(string.length() - 1);
        if (!Character.isDigit(c2)) {
            int n2 = 1;
            switch (c2) {
                case 'K': {
                    l2 *= 1024L;
                    break;
                }
                case 'M': {
                    l2 *= 0x100000L;
                    break;
                }
                case 'G': {
                    l2 *= 0x40000000L;
                    break;
                }
                case 'T': {
                    l2 *= 0x10000000000L;
                    break;
                }
                case 'P': {
                    l2 *= 0x4000000000000L;
                    break;
                }
                default: {
                    n2 = 0;
                }
            }
            string = string.substring(0, string.length() - n2);
        }
        return l2 * Long.parseLong(string);
    }

    public static String aa(String string, String string2) {
        if (string.endsWith(string2)) {
            return string.substring(0, string.length() - string2.length());
        }
        return string;
    }

    public static String ab(String string, String string2) {
        if (string.startsWith(string2)) {
            return string.substring(string2.length());
        }
        return string;
    }
}

