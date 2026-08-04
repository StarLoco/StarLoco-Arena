/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;

public enum awZ {
    diD('%', ""),
    diE('y', "0*[0-9]{2}"),
    diF('Y', "0*[0-9]{4}"),
    diG('M', "0*[0-9]{1,2}"),
    diH('d', "0*[0-9]{1,2}"),
    diI('h', "0*[0-9]{1,2}"),
    diJ('m', "0*[0-9]{1,2}"),
    diK('s', "0*[0-9]{1,2}");

    public static final HashMap diL;
    public static String diM;
    private final char diN;
    private final String diO;

    /*
     * WARNING - void declaration
     */
    private awZ() {
        void var4_2;
        void var3_1;
        this.diN = var3_1;
        this.diO = var4_2;
    }

    public char getChar() {
        return this.diN;
    }

    public String getPattern() {
        return this.diO;
    }

    static {
        diL = new HashMap();
        diM = "";
        for (int j = 0; j < awZ.values().length; ++j) {
            awZ awZ2 = awZ.values()[j];
            char c = awZ2.diN;
            diL.put(Character.valueOf(c), awZ2);
            if (awZ2 == diD) continue;
            diM = diM + c;
        }
    }
}

