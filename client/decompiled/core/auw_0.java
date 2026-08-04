/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from auw
 */
public abstract class auw_0
extends UC {
    private static final Logger a = Logger.getLogger(auw_0.class);
    protected static final byte cWp = 1;
    protected static final byte cWq = 2;

    public static adp_0 t(acf acf2, float f) {
        byte by = acf2.readByte();
        switch (by) {
            case 1: {
                return um_0.bRD.m(acf2, f);
            }
            case 2: {
                return tw_1.amY.f(acf2, f);
            }
        }
        a.error((Object)("type de condition inconnu " + by));
        return null;
    }
}

