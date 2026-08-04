/*
 * Decompiled with CFR 0.152.
 */
public enum atY {
    cVw,
    cVx,
    cVy,
    cVz;

    private static atY cVA;

    public static atY aGZ() {
        return cVA;
    }

    public static boolean aHa() {
        return cVA == cVy;
    }

    public static boolean aHb() {
        return cVA == cVx;
    }

    static {
        String string = System.getProperty("os.name").toLowerCase();
        cVA = string.startsWith("windows") ? cVx : (string.startsWith("mac") ? cVy : (string.startsWith("sunos") ? cVz : cVw));
    }
}

