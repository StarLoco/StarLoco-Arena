/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class nW {
    private static Logger a = Logger.getLogger(nW.class);
    private static final long PS = 60000L;
    private static final long PT = Long.MAX_VALUE;
    private static final long PU = 60000L;
    private static final int PV = 0;
    private static final int PW = 0;
    private static final byte[] PX = new byte[]{1, 2};
    private static final int PY = PX.length;
    private static long PZ = Long.MAX_VALUE;
    private static int Qa = PY;
    private static final Runnable Qb = new kl_0();

    public static void start() {
        Qa = PY;
        PZ = System.currentTimeMillis() + 60000L;
        apN.aDK().a(pl_2.acq());
    }

    public static void stop() {
        Qa = PY;
        PZ = Long.MAX_VALUE;
    }

    public static void sL() {
        ++Qa;
    }

    static /* synthetic */ long sM() {
        return PZ;
    }

    static /* synthetic */ int sN() {
        return Qa;
    }

    static /* synthetic */ int sO() {
        return PY;
    }

    static /* synthetic */ Logger sP() {
        return a;
    }

    static /* synthetic */ int bF(int n2) {
        Qa = n2;
        return Qa;
    }

    static /* synthetic */ byte[] sQ() {
        return PX;
    }

    static {
        ip_2.Un().a(Qb, 60000L);
    }
}

