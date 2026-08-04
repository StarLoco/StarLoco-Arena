/*
 * Decompiled with CFR 0.152.
 */
public enum apH {
    cMq(0.35f, 0.0f, 0.0f),
    cMr(0.35f, 0.2f, 0.0f),
    cMs(0.32f, 0.3f, 0.27f),
    cMt(0.55f, 0.4f, 0.27f),
    cMu(0.95f, 0.65f, 0.35f),
    cMv(1.0f, 0.83f, 0.49f),
    cMw(1.0f, 0.81f, 0.55f),
    cMx(1.0f, 0.89f, 0.75f),
    cMy(0.1f, 0.1f, 0.2f),
    cMz(0.2f, 0.1f, 0.2f),
    cMA(0.3f, 0.3f, 0.1f),
    cMB(0.43f, 0.36f, 0.56f),
    cMC(0.5f, 0.6f, 0.5f),
    cMD(0.8f, 0.9f, 0.45f),
    cME(0.74f, 0.9f, 1.0f),
    cMF(0.8f, 0.8f, 0.8f);

    private final float[] aaV;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private apH(float f3) {
        void var5_3;
        void var4_2;
        this.aaV = new float[]{f3 * 1.25f, var4_2 * 1.25f, var5_3 * 1.25f, 1.0f};
    }

    public float[] Aa() {
        return this.aaV;
    }

    public static apH lL(int n2) {
        apH[] apHArray = apH.values();
        if (n2 >= 0 && n2 < apHArray.length) {
            return apHArray[n2];
        }
        return null;
    }
}

