/*
 * Decompiled with CFR 0.152.
 */
import java.util.regex.Pattern;

/*
 * Renamed from rc
 */
public class rc_1 {
    public static boolean h(String string, String string2) {
        return aie_2.cxi.matcher(string).matches() && Pattern.compile(rc_1.bG(string)).matcher(string2).matches();
    }

    static String bG(String string) {
        String string2 = string;
        for (awZ awZ2 : awZ.values()) {
            string2 = string2.replaceAll(String.valueOf(awZ.diD.getChar()) + awZ2.getChar(), awZ2.getPattern());
        }
        return string2;
    }
}

