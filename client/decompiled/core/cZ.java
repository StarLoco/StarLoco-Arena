/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public abstract class cZ
extends UC {
    private static final Logger a = Logger.getLogger(gg_0.class);
    protected static final byte kB = 1;
    protected static final byte kC = 2;

    public static agu_1 a(acf acf2, float f) {
        byte by = acf2.readByte();
        switch (by) {
            case 1: {
                return aDk.dwQ.v(acf2, f);
            }
            case 2: {
                return afv_0.crf.p(acf2, f);
            }
        }
        a.error((Object)("type de definition inconnu " + by));
        return null;
    }
}

