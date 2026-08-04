/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Tn
 */
public enum tn_0 {
    bNr(0.21f, 0.12f, 0.03f),
    bNs(0.32f, 0.8f, 0.68f),
    bNt(1.0f, 0.88f, 0.3f),
    bNu(1.0f, 0.47f, 0.06f),
    bNv(0.83f, 0.85f, 0.14f),
    bNw(1.0f, 0.9f, 0.65f),
    bNx(0.74f, 0.65f, 0.51f),
    bNy(0.25f, 0.23f, 0.2f),
    bNz(1.0f, 0.86f, 0.78f),
    bNA(1.0f, 0.8f, 0.74f),
    bNB(1.0f, 0.94f, 0.73f),
    bNC(1.0f, 0.87f, 0.62f),
    bND(1.0f, 0.77f, 0.55f),
    bNE(0.91f, 0.66f, 0.56f),
    bNF(0.91f, 0.74f, 0.07f),
    bNG(0.77f, 0.62f, 0.39f),
    bNH(0.69f, 0.44f, 0.28f),
    bNI(0.51f, 0.3f, 0.16f),
    bNJ(0.34f, 0.16f, 0.04f),
    bNK(0.54f, 0.52f, 0.27f),
    bNL(0.49f, 0.43f, 0.26f),
    bNM(0.37f, 0.31f, 0.18f),
    bNN(0.27f, 0.44f, 0.56f),
    bNO(0.12f, 0.16f, 0.22f),
    bNP(0.0f, 0.0f, 0.0f),
    bNQ(0.0f, 0.59f, 0.84f),
    bNR(0.5f, 0.72f, 0.78f),
    bNS(0.59f, 0.84f, 0.0f),
    bNT(0.92f, 0.82f, 0.07f),
    bNU(0.71f, 0.51f, 0.0f),
    bNV(0.39f, 0.16f, 0.0f),
    bNW(0.25f, 0.14f, 0.04f),
    bNX(0.69f, 0.32f, 1.0f),
    bNY(0.13f, 0.79f, 1.0f),
    bNZ(0.0f, 0.0f, 0.0f),
    bOa(0.72f, 0.07f, 0.02f),
    bOb(1.0f, 0.32f, 0.58f),
    bOc(0.61f, 0.94f, 0.19f),
    bOd(0.74f, 0.47f, 0.1f),
    bOe(1.0f, 1.0f, 1.0f),
    bOf(1.0f, 0.9f, 0.48f),
    bOg(0.0f, 0.05f, 0.3f),
    bOh(1.0f, 0.85f, 0.88f),
    bOi(0.83f, 1.0f, 0.97f),
    bOj(0.81f, 0.31f, 1.0f),
    bOk(1.0f, 0.75f, 0.12f),
    bOl(1.0f, 0.06f, 0.0f);

    private final float[] aaV;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private tn_0(float f3) {
        void var5_3;
        void var4_2;
        this.aaV = new float[]{f3 * 1.25f, var4_2 * 1.25f, var5_3 * 1.25f, 1.0f};
    }

    public float[] Aa() {
        return this.aaV;
    }

    public static tn_0 hT(int n2) {
        tn_0[] tn_0Array = tn_0.values();
        if (n2 >= 0 && n2 < tn_0Array.length) {
            return tn_0Array[n2];
        }
        return null;
    }
}

