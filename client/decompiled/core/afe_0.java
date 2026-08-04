/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from afE
 */
public class afe_0
implements ahs_2 {
    public void a(String string, StringBuffer stringBuffer, char c, int n2) {
        if (string.indexOf(c) >= 0) {
            stringBuffer.append(c);
        } else {
            switch (c) {
                case '_': {
                    break;
                }
                case '\\': {
                    stringBuffer.append(c);
                    break;
                }
                case 't': {
                    stringBuffer.append('\t');
                    break;
                }
                case 'r': {
                    stringBuffer.append('\r');
                    break;
                }
                case 'n': {
                    stringBuffer.append('\n');
                    break;
                }
                default: {
                    String string2 = this.hS(string);
                    new IllegalArgumentException("Illegal char '" + c + " at column " + n2 + ". Only \\\\, \\_" + string2 + ", \\t, \\n, \\r combinations are allowed as escape characters.");
                }
            }
        }
    }

    String hS(String string) {
        String string2 = "";
        for (int j = 0; j < string.length(); ++j) {
            string2 = string2 + ", \\" + string.charAt(j);
        }
        return string2;
    }

    public static String hT(String string) {
        int n2 = string.length();
        StringBuffer stringBuffer = new StringBuffer(n2);
        int n3 = 0;
        while (n3 < n2) {
            int n4;
            if ((n4 = string.charAt(n3++)) == 92) {
                if ((n4 = string.charAt(n3++)) == 110) {
                    n4 = 10;
                } else if (n4 == 114) {
                    n4 = 13;
                } else if (n4 == 116) {
                    n4 = 9;
                } else if (n4 == 102) {
                    n4 = 12;
                } else if (n4 == 8) {
                    n4 = 8;
                } else if (n4 == 34) {
                    n4 = 34;
                } else if (n4 == 39) {
                    n4 = 39;
                } else if (n4 == 92) {
                    n4 = 92;
                }
            }
            stringBuffer.append((char)n4);
        }
        return stringBuffer.toString();
    }
}

