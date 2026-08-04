/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aGL
 */
public enum agl_0 {
    dJr(0.2f, 0.2f, 0.2f),
    dJs(0.4f, 0.21f, 0.1f),
    dJt(0.7f, 0.34f, 0.0f),
    dJu(1.0f, 0.47f, 0.0f),
    dJv(1.0f, 0.7f, 0.4f),
    dJw(1.0f, 0.73f, 0.23f),
    dJx(1.0f, 0.23f, 0.35f),
    dJy(1.0f, 0.2f, 0.2f),
    dJz(0.35f, 0.36f, 0.0f),
    dJA(0.83f, 0.87f, 0.1f),
    dJB(0.5f, 1.0f, 0.5f),
    dJC(0.8f, 0.8f, 1.0f),
    dJD(0.47f, 0.56f, 1.0f),
    dJE(0.2f, 0.2f, 0.4f),
    dJF(0.29f, 0.47f, 0.41f),
    dJG(1.0f, 1.0f, 0.75f);

    private final float[] aaV;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private agl_0(float f3) {
        void var5_3;
        void var4_2;
        this.aaV = new float[]{f3 * 1.25f, var4_2 * 1.25f, var5_3 * 1.25f, 1.0f};
    }

    public float[] Aa() {
        return this.aaV;
    }

    public static agl_0 oA(int n2) {
        agl_0[] agl_0Array = agl_0.values();
        if (n2 >= 0 && n2 < agl_0Array.length) {
            return agl_0Array[n2];
        }
        return null;
    }
}

