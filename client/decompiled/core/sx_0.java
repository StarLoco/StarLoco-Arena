/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Sx
 */
public class sx_0 {
    private static final Logger a = Logger.getLogger(sx_0.class);
    private static final boolean DEBUG = true;
    private static final String bLy = "\\$";
    private static final sx_0 bLz = new sx_0();

    private sx_0() {
    }

    public static sx_0 afl() {
        return bLz;
    }

    private static String fP(String string) {
        assert (string != null);
        try {
            return new rw_2().wE().bJ(ax_0.valueOf(string).getValue()).wF().wR();
        }
        catch (Exception exception) {
            a.error((Object)("la variable est inconnu " + string), (Throwable)exception);
            return string;
        }
    }

    public static String replace(String string) {
        String[] stringArray = string.split(bLy);
        StringBuilder stringBuilder = new StringBuilder(string.length());
        boolean bl2 = string.startsWith(bLy);
        for (int j = 0; j < stringArray.length; ++j) {
            if (bl2) {
                stringBuilder.append(sx_0.fP(stringArray[j]));
            } else {
                stringBuilder.append(stringArray[j]);
            }
            bl2 = !bl2;
        }
        return stringBuilder.toString();
    }
}

