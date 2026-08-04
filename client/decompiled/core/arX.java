/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public abstract class arX
extends Enum {
    public static final /* enum */ arX cQT = new fo_1();
    public static final /* enum */ arX cQU = new fr_0();
    public static final /* enum */ arX cQV = new fs_1();
    protected static final Logger a;
    private static final /* synthetic */ arX[] cQW;

    public static final arX[] values() {
        return (arX[])cQW.clone();
    }

    public static arX valueOf(String string) {
        return Enum.valueOf(arX.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private arX() {
        void var2_-1;
        void var1_-1;
    }

    public abstract db_2 iE();

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    /* synthetic */ arX(fo_1 fo_12) {
        this((String)var1_-1, (int)var2_1);
        void var2_1;
        void var1_-1;
    }

    static {
        cQW = new arX[]{cQT, cQU, cQV};
        a = Logger.getLogger(arX.class);
    }
}

