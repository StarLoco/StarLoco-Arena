/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class afg {
    private static final Logger a = Logger.getLogger(afg.class);
    public static final byte cqo = -16;
    public static final byte cqp = 15;
    public static final int cqq = 4;
    public static final int cqr = 0;
    public static final byte cqs = 0;
    public static final byte cqt = 1;
    public static final byte cqu = 2;
    public static final byte cqv = 3;
    public static final byte cqw = 5;
    public static final byte cqx = 6;

    public static acm_1 az(byte by) {
        byte by2 = (byte)((by & 0xF) >> 0);
        switch (by2) {
            case 0: {
                return new ajo_2();
            }
            case 1: {
                return new ajq_2();
            }
            case 2: {
                return new ajg_2();
            }
            case 3: {
                return new aji_2();
            }
            case 5: {
                return new ajj_2();
            }
            case 6: {
                return new ajc_2();
            }
        }
        a.error((Object)("Type de map inconnu " + by2));
        return null;
    }
}

