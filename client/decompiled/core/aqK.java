/*
 * Decompiled with CFR 0.152.
 */
public class aqK {
    private static final int UNINITIALIZED = -1;
    private static int cOx = -1;

    public static boolean aEg() {
        if (cOx == -1) {
            try {
                Class.forName("javax.management.ObjectName");
                cOx = 1;
            }
            catch (Throwable throwable) {
                cOx = 0;
            }
        }
        return cOx == 1;
    }
}

