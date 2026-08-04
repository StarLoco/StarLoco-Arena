/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from xx
 */
public class xx_1 {
    private static final Logger a = Logger.getLogger(xx_1.class);
    private static jg_0 ayE = new jg_0(25);
    private static jg_0 ayF = new jg_0(25);
    private static jg_0 ayG = new jg_0(25);
    private static int ayH = Integer.MIN_VALUE;
    private static int ayI = Integer.MIN_VALUE;
    private static int ayJ = Integer.MIN_VALUE;
    private static short ayK = Short.MIN_VALUE;
    private static String ayL;
    private static String ayM;
    private static boolean ayN;
    private static final aBp ayO;
    private static final aBp ayP;
    private static final gC ayQ;
    private static final aga_0 ayR;
    private static final uQ ayS;

    public static void q(String string, String string2) {
        if (string == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/MapManagerHelper.setValidMapsCoordFile must not be null");
        }
        if (string2 == null) {
            throw new IllegalArgumentException("Argument 1 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/MapManagerHelper.setValidMapsCoordFile must not be null");
        }
        ayL = string;
        ayM = string2;
        ayR.a(ayP);
    }

    public static boolean k(short s, short s2) {
        int n2 = ej_0.a(s, s2);
        return ayO.contains(n2);
    }

    public static void a(qs_2 qs_22, int n2, int n3, short s) {
        YR yR = qs_22.vn();
        Du du = yR.Fx();
        xx_1.p(n2, n3, 2);
        du.a(n2, n3, s);
        yR.Fs();
        qi_1.vV().a(qs_22, 500);
        qs_22.vA();
        ayR.b(yR.aEO());
    }

    public static void p(int n2, int n3, int n4) {
        short s;
        short s2;
        int n5;
        int n6;
        assert (ayK != Short.MIN_VALUE) : "Il faut d'abord appeler setWorldId";
        int n7 = hy_2.aO(n2);
        int n8 = hy_2.aO(n3);
        int n9 = n4 * 2 + 1;
        if (n7 == ayH && n8 == ayI && ayJ >= n9) {
            return;
        }
        ayH = n7;
        ayI = n8;
        ayJ = n9;
        n7 -= n4;
        n8 -= n4;
        jg_0 jg_02 = ayE;
        ayE = ayG;
        ayF = jg_02;
        ayF.clear();
        for (n6 = 0; n6 < n9; ++n6) {
            for (n5 = 0; n5 < n9; ++n5) {
                s2 = (short)(n7 + n6);
                s = (short)(n8 + n5);
                int n10 = ej_0.a(s2, s);
                if (!ayO.contains(n10)) continue;
                ayF.add(n10);
                if (ayE.contains(n10)) continue;
                try {
                    auU.b(s2, s);
                }
                catch (IOException iOException) {
                    a.warn((Object)("Topology map " + s2 + " " + s));
                }
                try {
                    ayS.b(s2, s);
                }
                catch (IOException iOException) {
                    a.warn((Object)("Light map " + s2 + " " + s));
                }
                try {
                    ayQ.b(s2, s);
                    continue;
                }
                catch (IOException iOException) {
                    a.warn((Object)("Environnement map doesn't exists " + s2 + " " + s));
                }
            }
        }
        for (n6 = 0; n6 < ayE.size(); ++n6) {
            n5 = ayE.bu(n6);
            if (ayF.contains(n5)) continue;
            s2 = ej_0.an(n5);
            s = ej_0.ao(n5);
            ayQ.c(s2, s);
            ayS.j(s2, s);
            auU.w(s2, s);
        }
        jg_02 = ayG;
        ayG = ayF;
        ayF = jg_02;
    }

    public static void ai(short s) {
        if (ayK != s) {
            xx_1.clear();
            ayK = s;
            assert (ayL != null) : "D'abord appler setValidMapsCoordFile";
            xx_1.a(ayL, s, ayO);
            xx_1.a(ayM, s, ayP);
            auU.aH(s);
            ayQ.aH(s);
            ayR.ot(s);
            acg_1.arw().jJ(s);
            ayS.aH(s);
            if (ayN) {
                ke_0.pk().aH(s);
                ke_0.pk().load();
            }
        }
    }

    private static void a(String string, int n2, aBp aBp2) {
        try {
            byte[] byArray = vq_2.readFile(String.format(string, n2));
            ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
            int n3 = byArray.length / 4;
            for (int j = 0; j < n3; ++j) {
                aBp2.nk(byteBuffer.getInt());
            }
        }
        catch (IOException iOException) {
            a.error((Object)"probl\u00e8me de lecture des coordonn\u00e9es de maps valides", (Throwable)iOException);
        }
    }

    public static short Em() {
        return ayK;
    }

    public static void clear() {
        ayH = Integer.MIN_VALUE;
        ayI = Integer.MIN_VALUE;
        ayJ = Integer.MIN_VALUE;
        ayK = Short.MIN_VALUE;
        ayE.clear();
        ayF.clear();
        ayG.clear();
        ayO.clear();
        ayP.clear();
        ayQ.kf();
        ayQ.reset();
        ayS.clean();
    }

    public static void aS(boolean bl2) {
        ayN = bl2;
    }

    public static aBp el(int n2) {
        aBp aBp2 = new aBp();
        xx_1.a(ayM, n2, aBp2);
        return aBp2;
    }

    static {
        ayN = true;
        ayO = new aBp();
        ayP = new aBp();
        ayQ = gC.kg();
        ayR = aga_0.aSG();
        ayS = uQ.AV();
    }
}

