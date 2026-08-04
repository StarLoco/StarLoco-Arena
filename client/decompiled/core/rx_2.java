/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from rx
 */
public class rx_2
implements up_1 {
    private static final float[][] ahp = new float[][]{{0.0f, 2.0f}, {0.0f, -2.0f}, {2.0f, 0.0f}, {-2.0f, 0.0f}, {0.0f, -2.0f}, {0.0f, 2.0f}, {-2.0f, 0.0f}, {2.0f, 0.0f}};
    private static final float[][] ahq = new float[][]{{0.0f, 0.0f}, {0.0f, 1.0f}, {0.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 1.0f}, {1.0f, 0.0f}, {1.0f, 0.0f}, {0.0f, 0.0f}};
    private static final short[] ahr = new short[]{0, 1, 2, 7, 7, 2, 3, 6, 3, 6, 5, 4};
    private static final short[] ahs = new short[]{0, 1, 2, 3, 4, 5, 6, 7, 0};

    public int wV() {
        return 9;
    }

    public int wW() {
        return 5;
    }

    public int wX() {
        return 5;
    }

    public int wY() {
        return 1;
    }

    public float[][] wZ() {
        return ahp;
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
}

