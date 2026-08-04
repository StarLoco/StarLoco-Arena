/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/*
 * Renamed from aBw
 */
public class abw_1 {
    public static final boolean dru = true;
    private static HashMap aPt = new HashMap();
    private static String drv = "";
    private static final Logger a = Logger.getLogger(abw_1.class);

    public static void kg(String string) {
        drv = string;
    }

    public static ma_1 kh(String string) {
        ma_1 ma_12 = abw_1.kk(string);
        if (ma_12 == null) {
            return abw_1.kl(string);
        }
        return ma_12;
    }

    public static ma_1 e(String string, int n2, int n3) {
        String string2 = string;
        string2 = string2 + '-';
        if ((n2 & 1) != 0) {
            string2 = string2 + "bold";
        }
        if ((n2 & 2) != 0) {
            string2 = string2 + "italic";
        }
        if ((n2 & 4) != 0) {
            string2 = string2 + "bordered";
        }
        if (n2 == 0) {
            string2 = string2 + "plain";
        }
        string2 = string2 + "-";
        ma_1 ma_12 = abw_1.kk(string2 = string2 + n3);
        if (ma_12 == null) {
            ma_12 = abw_1.kl(string2);
        }
        return ma_12;
    }

    public static int ki(String string) {
        int n2 = 0;
        if (string == null) {
            return n2;
        }
        String string2 = string.toLowerCase();
        if (string2.contains("bold")) {
            n2 |= 1;
        }
        if (string2.contains("italic")) {
            n2 |= 2;
        }
        if (string2.contains("bordered")) {
            n2 |= 4;
        }
        return n2;
    }

    public static int kj(String string) {
        int n2 = string.lastIndexOf(45) + 1;
        if (n2 == 0) {
            return 0;
        }
        String string2 = string.substring(n2);
        try {
            return Integer.parseInt(string2);
        }
        catch (Exception exception) {
            return 0;
        }
    }

    public static String getType(String string) {
        int n2 = string.indexOf(45);
        if (n2 <= 0) {
            return string;
        }
        return string.substring(0, n2);
    }

    private static ma_1 kk(String string) {
        return (ma_1)aPt.get(string);
    }

    private static ma_1 kl(String string) {
        ma_1 ma_12 = aFM.au(string, drv);
        if (ma_12 == null) {
            a.error((Object)("Unable to load the font " + string + " from path " + drv));
            ma_12 = abw_1.km(string);
        }
        aPt.put(string, ma_12);
        return ma_12;
    }

    private static ma_1 km(String string) {
        ma_1 ma_12 = null;
        float f = Float.MAX_VALUE;
        for (Map.Entry entry : aPt.entrySet()) {
            String string2 = (String)entry.getKey();
            float f2 = abw_1.al(string, string2);
            if (!(f2 < f)) continue;
            f = f2;
            ma_12 = (ma_1)entry.getValue();
        }
        return ma_12;
    }

    private static float al(String string, String string2) {
        String string3 = abw_1.getType(string);
        int n2 = abw_1.kj(string);
        int n3 = abw_1.ki(string);
        String string4 = abw_1.getType(string2);
        int n4 = abw_1.kj(string2);
        int n5 = abw_1.ki(string2);
        float f = 0.0f;
        if (!string4.equals(string3)) {
            f += 2.0f;
        }
        f += (float)Math.abs(n2 - n4);
        if (n5 != n3) {
            f += 4.0f;
        }
        return f;
    }
}

