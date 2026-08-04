/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import org.apache.log4j.Logger;

public class auU {
    private static boolean cXx = false;
    private static boolean cXy = false;
    private static int ug;
    private static String arj;
    private static final akd_0[] aNf;
    private static final pc_0 bHl;
    private static final Logger a;
    private static final cp_2 cXz;
    private static final cp_2 cXA;
    private static final Object cXB;

    private auU() {
    }

    public static void aHJ() {
        cXx = true;
    }

    public static void el(boolean bl2) {
        cXy = bl2;
    }

    public static boolean aHK() {
        return cXx;
    }

    public static void setPath(String string) {
        try {
            arj = vq_2.getURL(string).toString();
            if (!arj.endsWith("/")) {
                arj = arj + "/";
            }
        }
        catch (IOException iOException) {
            a.error((Object)("Invalid path : " + string), (Throwable)iOException);
        }
    }

    public static void aH(int n2) {
        assert (cXx) : "Can't set worldId if not using constant world. See TopologyMapManager.enableConstantWorld for more informations";
        ug = n2;
    }

    public static int AW() {
        assert (cXx) : "Can't get worldId if not using constant world. See TopologyMapManager.enableConstantWorld for more informations";
        return ug;
    }

    private static String a(String string, int n2, short s, short s2) {
        assert (string != null && string.contains("%d") && string.endsWith("/"));
        return String.format(arj, n2) + s + '_' + s2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void d(int n2, short s, short s2) {
        long l2 = auU.a(n2, (long)s, (long)s2, 0);
        Object object = cXB;
        synchronized (object) {
            if (cXA.m(l2)) {
                return;
            }
            String string = auU.a(arj, n2, s, s2);
            acf acf2 = new acf(vq_2.gm(string));
            byte by = acf2.readByte();
            acm_1 acm_12 = afg.az(by);
            if (acm_12 == null) {
                a.error((Object)("Unable to create map (" + s + "; " + s2 + ";" + n2 + ")"));
                return;
            }
            acm_12.b(acf2);
            assert (hy_2.aO(acm_12.aG) == s && hy_2.aP(acm_12.aH) == s2);
            cXA.a(l2, acm_12);
        }
    }

    public static void b(short s, short s2) {
        assert (cXx) : "Can't loadMap without giving worldId if not using constant world. See TopologyMapManager.enableConstantWorld for more informations";
        auU.d(ug, s, s2);
        auU.b(ug, s, s2, (short)0);
    }

    public static void w(short s, short s2) {
        assert (cXx) : "Can't loadMap without giving worldId if not using constant world. See TopologyMapManager.enableConstantWorld for more informations";
        long l2 = auU.a(ug, (long)s, (long)s2, 0);
        cXA.u(l2);
        cXz.u(l2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static dc_0 a(int n2, short s, short s2, short s3) {
        long l2 = auU.a(n2, (long)s, (long)s2, (int)s3);
        Object object = cXB;
        synchronized (object) {
            return (dc_0)cXz.t(l2);
        }
    }

    public static dc_0 x(short s, short s2) {
        assert (cXx) : "Can't getMap without giving worldId if not using constant world. See TopologyMapManager.enableConstantWorld for more informations";
        return auU.a(ug, s, s2, (short)0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static dc_0 c(int n2, int n3, int n4, short s) {
        long l2 = hy_2.aO(n3);
        long l3 = hy_2.aP(n4);
        Object object = cXB;
        synchronized (object) {
            return (dc_0)cXz.t(auU.a(n2, l2, l3, (int)s));
        }
    }

    public static dc_0 bW(int n2, int n3) {
        assert (cXx) : "Can't getMapFromCell without giving worldId if not using constant world. See TopologyMapManager.enableConstantWorld for more informations";
        return auU.c(ug, n2, n3, (short)0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void reset() {
        Object object = cXB;
        synchronized (object) {
            cXz.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static dc_0 b(int n2, short s, short s2, short s3) {
        long l2 = auU.a(n2, (long)s, (long)s2, (int)s3);
        Object object = cXB;
        synchronized (object) {
            dc_0 dc_02 = (dc_0)cXz.t(l2);
            if (dc_02 != null) {
                return dc_02;
            }
            long l3 = l2 & 0xFFFFFFFFFFFF0000L;
            acm_1 acm_12 = (acm_1)cXA.t(l3);
            assert (acm_12 != null) : "The cell (" + s + "; " + s2 + ") in world " + n2 + " belongs to a map not loaded";
            dc_02 = new dc_0(acm_12);
            cXz.a(l2, dc_02);
            return dc_02;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void d(int n2, int n3, int n4, short s) {
        long l2 = auU.a(n2, (long)n3, n4, (int)s);
        Object object = cXB;
        synchronized (object) {
            cXz.u(l2);
        }
    }

    public static void a(int n2, short s, int n3, int n4, int n5, int n6, int n7, aja_1 aja_12) {
        int n8;
        aja_12.reset();
        if (n3 > n5) {
            n8 = n3;
            n3 = n5;
            n5 = n8;
        }
        if (n4 > n6) {
            n8 = n4;
            n4 = n6;
            n6 = n8;
        }
        n8 = hy_2.aO(n3 -= n7);
        int n9 = hy_2.aP(n4 -= n7);
        int n10 = hy_2.aO(n5 += n7);
        int n11 = hy_2.aP(n6 += n7);
        aja_12.reset();
        for (int j = n9; j <= n11; ++j) {
            for (int i2 = n8; i2 <= n10; ++i2) {
                dc_0 dc_02 = auU.a(n2, (short)i2, (short)j, s);
                aja_12.a(dc_02, i2, j);
            }
        }
    }

    public static void a(int n2, short s, int n3, int n4, int n5, aja_1 aja_12) {
        auU.a(n2, s, n3, n4, n3, n4, n5, aja_12);
    }

    public static void a(int n2, int n3, int n4, int n5, int n6, aja_1 aja_12) {
        assert (cXx) : "Can't getTopologyMapInstances without giving worldId if not using constant world. See TopologyMapManager.enableConstantWorld for more informations";
        auU.a(ug, (short)0, n2, n3, n4, n5, n6, aja_12);
    }

    public static void a(short s, byte by, short s2) {
        bHl.a(s, by, s2);
    }

    public static short bX(int n2, int n3) {
        assert (!cXy) : "Can't call getHighestWalkableZ in a 'thread safe aware' environnement. See TopologyMapManager.setLimitToThreadSafeMethods for more informations";
        dc_0 dc_02 = auU.bW(n2, n3);
        assert (dc_02 != null) : "The cell (" + n2 + "; " + n3 + ") belongs to a map not loaded";
        int n4 = dc_02.Ls().a(n2, n3, aNf, 0);
        return mo_1.a(0, n4, aNf, (short)Short.MAX_VALUE, bHl.abN());
    }

    public static short H(int n2, int n3, short s) {
        assert (cXx) : "Can't getTopologyMapInstances without giving worldId if not using constant world. See TopologyMapManager.enableConstantWorld for more informations";
        return auU.a(ug, n2, n3, s, (short)0);
    }

    public static short I(int n2, int n3, short s) {
        assert (!cXy) : "Can't call getPossibleNearestWalkableZ in a 'thread safe aware' environnement. See TopologyMapManager.setLimitToThreadSafeMethods for more informations";
        dc_0 dc_02 = auU.bW(n2, n3);
        if (dc_02 == null || dc_02.ak(n2, n3)) {
            return Short.MIN_VALUE;
        }
        int n4 = dc_02.Ls().a(n2, n3, aNf, 0);
        return mo_1.a(0, n4, aNf, (short)(s + bHl.abO()), bHl.abN());
    }

    public static short e(int n2, int n3, int n4, short s) {
        assert (!cXy) : "Can't call getHighestWalkableZ in a 'thread safe aware' environnement. See TopologyMapManager.setLimitToThreadSafeMethods for more informations";
        dc_0 dc_02 = auU.c(n2, n3, n4, s);
        assert (dc_02 != null) : "The cell (" + n3 + "; " + n4 + ", instance " + n2 + ") belongs to a map not loaded";
        if (dc_02.ak(n3, n4)) {
            return Short.MIN_VALUE;
        }
        int n5 = dc_02.Ls().a(n3, n4, aNf, 0);
        return mo_1.a(0, n5, aNf, (short)Short.MAX_VALUE, bHl.abN());
    }

    public static short a(int n2, int n3, int n4, short s, short s2) {
        assert (!cXy) : "Can't call getNearestWalkableZ in a 'thread safe aware' environnement. See TopologyMapManager.setLimitToThreadSafeMethods for more informations";
        dc_0 dc_02 = auU.c(n2, n3, n4, s2);
        if (dc_02 == null) {
            a.error((Object)("MapInstance is null for parameters : worldId=" + n2 + ", pos=[" + n3 + ";" + n4 + "], InstanceUid=" + s2));
            return Short.MIN_VALUE;
        }
        if (dc_02.ak(n3, n4)) {
            return Short.MIN_VALUE;
        }
        int n5 = dc_02.Ls().a(n3, n4, aNf, 0);
        int n6 = Short.MAX_VALUE;
        short s3 = Short.MIN_VALUE;
        for (int j = 0; j < n5; ++j) {
            int n7;
            akd_0 akd_02 = aNf[j];
            if (akd_02.cCJ == -1 || akd_02.wp == Short.MIN_VALUE || akd_02.cCI || (n7 = Math.abs(akd_02.wp - s)) >= n6) continue;
            n6 = (short)n7;
            s3 = akd_02.wp;
        }
        return s3;
    }

    public static ry a(int n2, short s, ry ry2, int n3) {
        return auU.a(n2, s, ry2.getX(), ry2.getY(), ry2.wk(), n3);
    }

    public static ry a(int n2, short s, int n3, int n4, short s2, int n5) {
        assert (!cXy) : "Can't call getNearestWalkableZ in a 'thread safe aware' environnement. See TopologyMapManager.setLimitToThreadSafeMethods for more informations";
        dc_0 dc_02 = auU.c(n2, n3, n4, s);
        if (dc_02 == null) {
            return null;
        }
        for (int j = 1; j <= n5; ++j) {
            short s3;
            int n6;
            int n7;
            int n8;
            for (n8 = 0; n8 < j; ++n8) {
                n7 = n3 + j - n8;
                n6 = n4 - n8;
                s3 = auU.a(n2, n7, n6, s2, s);
                if (s3 == Short.MIN_VALUE) continue;
                return new ry(n7, n6, s3);
            }
            for (n8 = 0; n8 < j; ++n8) {
                n7 = n3 - n8;
                n6 = n4 - j + n8;
                s3 = auU.a(n2, n7, n6, s2, s);
                if (s3 == Short.MIN_VALUE) continue;
                return new ry(n7, n6, s3);
            }
            for (n8 = 0; n8 < j; ++n8) {
                n7 = n3 - j + n8;
                n6 = n4 + n8;
                s3 = auU.a(n2, n7, n6, s2, s);
                if (s3 == Short.MIN_VALUE) continue;
                return new ry(n7, n6, s3);
            }
            for (n8 = 0; n8 < j; ++n8) {
                n7 = n3 + n8;
                n6 = n4 + j - n8;
                s3 = auU.a(n2, n7, n6, s2, s);
                if (s3 == Short.MIN_VALUE) continue;
                return new ry(n7, n6, s3);
            }
        }
        return null;
    }

    public static short J(int n2, int n3, short s) {
        assert (!cXy) : "Can't call getNearestZ in a 'thread safe aware' environnement. See TopologyMapManager.setLimitToThreadSafeMethods for more informations";
        dc_0 dc_02 = auU.bW(n2, n3);
        if (dc_02 == null) {
            return Short.MIN_VALUE;
        }
        int n4 = dc_02.Ls().a(n2, n3, aNf, 0);
        int n5 = Short.MAX_VALUE;
        short s2 = s;
        for (int j = 0; j < n4; ++j) {
            int n6 = Math.abs(auU.aNf[j].wp - s);
            if (n6 >= n5) continue;
            n5 = (short)n6;
            s2 = auU.aNf[j].wp;
        }
        return s2;
    }

    public static boolean B(ry ry2) {
        if (ry2 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/world/topology/TopologyMapManager.isIndoor must not be null");
        }
        return auU.K(ry2.getX(), ry2.getY(), ry2.wk());
    }

    public static boolean K(int n2, int n3, short s) {
        assert (cXx) : "Can't isIndoor without giving worldId if not using constant world. See TopologyMapManager.enableConstantWorld for more informations";
        return auU.b(ug, n2, n3, s, (short)0);
    }

    public static boolean b(int n2, int n3, int n4, short s, short s2) {
        assert (!cXy) : "Can't call isIndoor in a 'thread safe aware' environnement. See TopologyMapManager.setLimitToThreadSafeMethods for more informations";
        dc_0 dc_02 = auU.c(n2, n3, n4, s2);
        assert (dc_02 != null) : "The cell (" + n3 + "; " + n4 + ") belongs to a map not loaded";
        return dc_02.t(n3, n4, s);
    }

    public static boolean G(int n2, int n3, short s) {
        assert (cXx) : "Can't isWalkable without giving worldId if not using constant world. See TopologyMapManager.enableConstantWorld for more informations";
        return auU.c(ug, n2, n3, s, (short)0);
    }

    public static boolean c(int n2, int n3, int n4, short s, short s2) {
        assert (!cXy) : "Can't call isWalkable in a 'thread safe aware' environnement. See TopologyMapManager.setLimitToThreadSafeMethods for more informations";
        dc_0 dc_02 = auU.c(n2, n3, n4, s2);
        assert (dc_02 != null) : "The cell (" + n3 + "; " + n4 + ") belongs to a map not loaded";
        if (dc_02.ak(n3, n4)) {
            return false;
        }
        int n5 = dc_02.Ls().a(n3, n4, aNf, 0);
        for (int j = 0; j < n5; ++j) {
            if (auU.aNf[j].wp != s) continue;
            return auU.aNf[j].cCJ != -1;
        }
        return false;
    }

    public static short[] bY(int n2, int n3) {
        assert (cXx) : "Can't getWalkableZ without giving worldId if not using constant world. See TopologyMapManager.enableConstantWorld for more informations";
        assert (!cXy) : "Can't call getNearestZ in a 'thread safe aware' environnement. See TopologyMapManager.setLimitToThreadSafeMethods for more informations";
        dc_0 dc_02 = auU.bW(n2, n3);
        if (dc_02 == null || dc_02.ak(n2, n3)) {
            return new short[0];
        }
        int n4 = dc_02.Ls().a(n2, n3, aNf, 0);
        mm_0 mm_02 = new mm_0();
        for (int j = 0; j < n4; ++j) {
            if (auU.aNf[j].cCJ == -1 || auU.aNf[j].cCI) continue;
            mm_02.add(auU.aNf[j].wp);
        }
        mm_02.reverse();
        return mm_02.ru();
    }

    public static boolean a(int n2, int n3, int n4, int n5, boolean bl2) {
        int n6 = Math.abs(n4 - n2);
        if (n6 > 1) {
            return false;
        }
        int n7 = Math.abs(n5 - n3);
        if (n7 > 1) {
            return false;
        }
        return n6 != n7 || bl2 && n6 != 0;
    }

    private static long a(int n2, long l2, long l3, int n3) {
        return (l2 += 32767L) << 48 | (l3 += 32767L) << 32 | (long)((n2 & 0xFFFF) << 16) | (long)(n3 & 0xFFFF);
    }

    public static void main(String[] stringArray) {
        try {
            int n2 = 30;
            arj = "file:/F:/Work/IntelliJ/Adamai_Trunk/prebuild/maps/common/%d/tplg/30/";
            auU.d(30, (short)12, (short)-12);
            dc_0 dc_02 = auU.b(30, (short)12, (short)-12, (short)0);
            System.out.println("");
        }
        catch (IOException iOException) {
            a.error((Object)"", (Throwable)iOException);
        }
    }

    static {
        aNf = new akd_0[32];
        bHl = new pc_0(-1, 0, -1);
        a = Logger.getLogger(auU.class);
        cXz = new cp_2();
        cXA = new cp_2();
        cXB = new Object();
        for (int j = 0; j < aNf.length; ++j) {
            auU.aNf[j] = new akd_0();
        }
    }
}

