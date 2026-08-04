/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from auv
 */
public class auv_0 {
    protected static Logger a = Logger.getLogger(auv_0.class);
    private static long cWm = 0L;
    private static long cWn = 5000L;
    private static long cWo = cWm;

    public static boolean aHC() {
        return cWo != cWm;
    }

    public static void ek(boolean bl2) {
        cWo = bl2 ? System.currentTimeMillis() : cWm;
    }
}

