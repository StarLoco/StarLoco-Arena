/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from atf
 */
public class atf_0
implements up_1 {
    public static final float[][] ahp = new float[][]{{1.0f, -11.0f}, {2.0f, -11.0f}, {0.0f, -11.0f}, {0.0f, -8.0f}, {2.0f, -8.0f}, {2.0f, -7.0f}, {1.0f, -7.0f}, {1.0f, -4.0f}, {4.0f, -4.0f}, {4.0f, -6.0f}, {6.0f, -6.0f}, {6.0f, -4.0f}, {4.0f, -4.0f}, {4.0f, -1.0f}, {7.0f, -1.0f}, {7.0f, -2.0f}, {8.0f, -2.0f}, {8.0f, -0.0f}, {11.0f, -0.0f}, {11.0f, -2.0f}, {11.0f, -1.0f}, {-11.0f, -1.0f}, {-11.0f, -2.0f}, {-11.0f, -0.0f}, {-8.0f, -0.0f}, {-8.0f, -2.0f}, {-7.0f, -2.0f}, {-7.0f, -1.0f}, {-4.0f, -1.0f}, {-4.0f, -4.0f}, {-6.0f, -4.0f}, {-6.0f, -6.0f}, {-4.0f, -6.0f}, {-4.0f, -4.0f}, {-1.0f, -4.0f}, {-1.0f, -7.0f}, {-2.0f, -7.0f}, {-2.0f, -8.0f}, {0.0f, -8.0f}, {0.0f, -11.0f}, {-2.0f, -11.0f}, {-1.0f, -11.0f}, {-1.0f, 31.0f}, {-2.0f, 31.0f}, {-0.0f, 31.0f}, {-0.0f, 28.0f}, {-2.0f, 28.0f}, {-2.0f, 27.0f}, {-1.0f, 27.0f}, {-1.0f, 24.0f}, {-4.0f, 24.0f}, {-4.0f, 26.0f}, {-6.0f, 26.0f}, {-6.0f, 24.0f}, {-4.0f, 24.0f}, {-4.0f, 21.0f}, {-7.0f, 21.0f}, {-7.0f, 22.0f}, {-8.0f, 22.0f}, {-8.0f, 20.0f}, {-11.0f, 20.0f}, {-11.0f, 22.0f}, {-11.0f, 21.0f}, {-11.0f, 21.0f}, {-11.0f, 21.0f}, {-11.0f, 21.0f}, {-11.0f, 21.0f}, {-11.0f, 21.0f}, {-11.0f, 21.0f}, {-11.0f, 21.0f}, {-11.0f, 21.0f}, {27.0f, 21.0f}, {17.0f, 0.0f}, {19.0f, 21.0f}, {11.0f, 21.0f}, {11.0f, 22.0f}, {11.0f, 20.0f}, {8.0f, 20.0f}, {8.0f, 22.0f}, {7.0f, 22.0f}, {7.0f, 21.0f}, {4.0f, 21.0f}, {4.0f, 24.0f}, {6.0f, 24.0f}, {6.0f, 26.0f}, {4.0f, 26.0f}, {4.0f, 24.0f}, {1.0f, 24.0f}, {1.0f, 27.0f}, {2.0f, 27.0f}, {2.0f, 28.0f}, {0.0f, 28.0f}, {0.0f, 31.0f}, {2.0f, 31.0f}, {1.0f, 31.0f}};
    public static final float[][] ahq = new float[][]{{0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f}};
    public static final short[] ahr = new short[]{0, 41, 42, 94, 2, 3, 38, 39, 92, 91, 45, 44, 4, 5, 36, 37, 90, 89, 47, 46, 6, 7, 8, 9, 35, 34, 33, 32, 88, 87, 86, 85, 48, 49, 50, 51, 6, 9, 32, 35, 88, 85, 51, 48, 10, 11, 30, 31, 84, 83, 53, 52, 11, 12, 13, 14, 30, 29, 28, 27, 83, 82, 81, 80, 53, 54, 55, 56, 11, 15, 26, 30, 83, 79, 57, 53, 11, 14, 15, 11, 30, 27, 26, 30, 83, 80, 79, 83, 53, 56, 57, 53, 16, 17, 18, 19, 25, 24, 23, 22, 78, 77, 76, 75, 58, 59, 60, 61, 19, 20, 21, 22, 75, 74, 62, 61, 71, 72, 73, 71};
    public static final short[] ahs = new short[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 0};
    private float[][] cSQ = new float[ahp.length][2];
    private float cSR;
    private float cSS;
    private boolean cST;
    public static final int cSU = 72;
    public static final int cSV = 20;

    public int wV() {
        return 32;
    }

    public int wW() {
        return 12;
    }

    public int wX() {
        return 12;
    }

    public int wY() {
        return 12;
    }

    public float[][] wZ() {
        return this.cSQ;
    }

    public float[][] xa() {
        return ahq;
    }

    public short[] xb() {
        return ahr;
    }

    public short[] xc() {
        return ahs;
    }

    public atf_0() {
        System.arraycopy(ahp, 0, this.cSQ, 0, ahp.length);
        this.cSR = 0.0f;
        this.cSS = 0.0f;
    }

    public final float aGa() {
        return this.cSR;
    }

    public final void bc(float f) {
        this.cSR = f;
    }

    public final float aGb() {
        return this.cSS;
    }

    public final void bd(float f) {
        this.cSS = f;
    }

    public final void dW(boolean bl2) {
        int n2 = bl2 ? 20 : 0;
        float f = this.cSQ[73][0] + (this.cSQ[71][0] - this.cSQ[73][0]) / 2.0f;
        float f2 = this.cSQ[73][1] + (this.cSQ[71][1] - this.cSQ[73][1]) / 2.0f;
        float[] fArray = new float[]{(float)(Math.cos(-2.0943951023931953) * (double)n2 + (double)f), (float)(Math.sin(-2.0943951023931953) * (double)n2 + (double)f2)};
        this.cSQ[72] = fArray;
    }

    public void setSparkAngle(float f) {
        if ((f %= (float)Math.PI) > 0.0f) {
            f = (float)((double)f - Math.PI);
        }
        float f2 = this.cSQ[73][0] + (this.cSQ[71][0] - this.cSQ[73][0]) / 2.0f;
        float f3 = this.cSQ[73][1] + (this.cSQ[71][1] - this.cSQ[73][1]) / 2.0f;
        float[] fArray = new float[]{(float)(Math.cos(f) * 20.0 + (double)f2), (float)(Math.sin(f) * 20.0 + (double)f3)};
        this.cSQ[72] = fArray;
    }
}

