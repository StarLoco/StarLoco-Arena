/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from qI
 */
public class qi_1
implements aHq {
    private static final Logger a = Logger.getLogger(qi_1.class);
    public static final int afi = 500;
    private static final qi_1 afj = new qi_1();
    private static final int afk = 0;
    public static final int afl = 0;
    private boolean afm = true;
    private int afn = 0;
    private final pd_1 afo = new pd_1(this, null);

    private qi_1() {
    }

    public static qi_1 vV() {
        return afj;
    }

    public static boolean cW(int n2) {
        return n2 == 0;
    }

    public float[] a(xw_0 xw_02) {
        if (xw_02 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/display/GroupLayerManager.getLayerColor must not be null");
        }
        int n2 = xw_02.Ge();
        float[] fArray = this.afo.cD(n2);
        if (fArray != null) {
            return fArray;
        }
        fArray = this.R(this.afn, n2) ? new float[]{1.0f, 1.0f, 1.0f, 1.0f} : new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        this.afo.c(n2, fArray);
        return fArray;
    }

    public boolean R(int n2, int n3) {
        return acg_1.arw().bj(n2, n3);
    }

    public void a(qs_2 qs_22, float f, float f2) {
        if (qs_22 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/display/GroupLayerManager.prepareBeforeRendering must not be null");
        }
    }

    public void a(qs_2 qs_22, int n2) {
        if (qs_22 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/display/GroupLayerManager.process must not be null");
        }
        YR yR = qs_22.vn();
        int n3 = yR.Ge();
        boolean bl2 = false;
        if (n3 != this.afn) {
            pd_1.a(this.afo).clear();
            pd_1.b(this.afo).clear();
            boolean bl3 = this.cX(n3);
            bl2 = this.afm != bl3;
            this.afm = bl3;
            this.afn = n3;
        }
        this.afo.a(n2, bl2);
    }

    public boolean vW() {
        return this.cX(this.afn);
    }

    private boolean cX(int n2) {
        return this.R(n2, 0);
    }

    public void clear() {
        this.afm = true;
        this.afo.clear();
    }

    static /* synthetic */ int a(qi_1 qi_12) {
        return qi_12.afn;
    }
}

