/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aZ
 */
public class az_1 {
    private static final Logger a = Logger.getLogger(az_1.class);

    public static rd_1 b(String string, String string2) {
        if (string == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/time/calendar/util/GameDateFormatter.parse must not be null");
        }
        if (string2 == null) {
            throw new IllegalArgumentException("Argument 1 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/time/calendar/util/GameDateFormatter.parse must not be null");
        }
        if (!rc_1.h(string, string2)) {
            throw new pw_0(string, string2);
        }
        int n2 = 0;
        int n3 = 1;
        int n4 = 1;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        char[] cArray = string.toCharArray();
        int n8 = 0;
        int n9 = 0;
        while (n8 < cArray.length) {
            String string3;
            char c = cArray[n8++];
            int n10 = n9++;
            if (c != awZ.diD.getChar()) continue;
            c = cArray[n8++];
            if (n8 < cArray.length) {
                int n11 = string2.indexOf(cArray[n8], n9);
                string3 = string2.substring(n10, n11);
            } else {
                string3 = string2.substring(n10);
            }
            n9 += string3.length();
            awZ awZ2 = (awZ)((Object)awZ.diL.get(Character.valueOf(c)));
            switch (awZ2) {
                case diE: {
                    String string4 = IP.getInstance().get(1) / 100 + string3;
                    n2 = Integer.parseInt(string4);
                    break;
                }
                case diF: {
                    n2 = Integer.parseInt(string3);
                    break;
                }
                case diG: {
                    n3 = Integer.parseInt(string3);
                    break;
                }
                case diH: {
                    n4 = Integer.parseInt(string3);
                    break;
                }
                case diI: {
                    n5 = Integer.parseInt(string3);
                    break;
                }
                case diJ: {
                    n6 = Integer.parseInt(string3);
                    break;
                }
                case diK: {
                    n7 = Integer.parseInt(string3);
                }
            }
            ++n8;
        }
        return new rd_1(n7, n6, n5, n4, n3, n2);
    }

    public static String a(String string, acx_1 acx_12) {
        if (string == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/time/calendar/util/GameDateFormatter.format must not be null");
        }
        if (acx_12 == null) {
            throw new IllegalArgumentException("Argument 1 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/time/calendar/util/GameDateFormatter.format must not be null");
        }
        StringBuilder stringBuilder = new StringBuilder();
        char[] cArray = string.toCharArray();
        int n2 = 0;
        for (int j = 0; j < cArray.length; ++j) {
            if (cArray[j] != '%') continue;
            if (j != n2) {
                stringBuilder.append(cArray, n2, j - n2);
            }
            if (++j == cArray.length) {
                n2 = cArray.length;
                break;
            }
            char c = cArray[j];
            awZ awZ2 = (awZ)((Object)awZ.diL.get(Character.valueOf(c)));
            switch (awZ2) {
                case diE: {
                    stringBuilder.append(acx_12.getYear() % 100);
                    break;
                }
                case diF: {
                    stringBuilder.append(acx_12.getYear());
                    break;
                }
                case diG: {
                    int n3 = acx_12.getMonth();
                    if (n3 < 10) {
                        stringBuilder.append('0');
                    }
                    stringBuilder.append(n3);
                    break;
                }
                case diH: {
                    int n3 = acx_12.getDay();
                    if (n3 < 10) {
                        stringBuilder.append('0');
                    }
                    stringBuilder.append(n3);
                    break;
                }
                case diI: {
                    int n3 = acx_12.getHours();
                    if (n3 < 10) {
                        stringBuilder.append('0');
                    }
                    stringBuilder.append(n3);
                    break;
                }
                case diJ: {
                    int n3 = acx_12.getMinutes();
                    if (n3 < 10) {
                        stringBuilder.append('0');
                    }
                    stringBuilder.append(n3);
                    break;
                }
                case diK: {
                    int n3 = acx_12.getSeconds();
                    if (n3 < 10) {
                        stringBuilder.append('0');
                    }
                    stringBuilder.append(n3);
                    break;
                }
                case diD: {
                    stringBuilder.append(awZ.diD.getChar());
                    break;
                }
                default: {
                    a.warn((Object)("Caract\u00e8re de formattage inconnu '" + c + "' dans la chaine " + string));
                }
            }
            n2 = j + 1;
        }
        stringBuilder.append(cArray, n2, cArray.length - n2);
        return stringBuilder.toString();
    }
}

