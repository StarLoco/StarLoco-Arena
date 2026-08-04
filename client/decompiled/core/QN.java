/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public abstract class QN {
    protected static final Logger a = Logger.getLogger(QN.class);
    private static QN bHN;

    protected QN() {
        bHN = this;
    }

    public static boolean K(byte[] byArray) {
        if (bHN != null) {
            return bHN.i(byArray);
        }
        a.error((Object)"Le v\u00e9rificateur de version n'a pas \u00e9t\u00e9 d\u00e9finit");
        return false;
    }

    public static byte[] tR() {
        if (bHN != null) {
            return bHN.pC();
        }
        a.error((Object)"Le v\u00e9rificateur de version n'a pas \u00e9t\u00e9 d\u00e9finit");
        return new byte[0];
    }

    protected abstract boolean i(byte[] var1);

    protected abstract byte[] pC();
}

