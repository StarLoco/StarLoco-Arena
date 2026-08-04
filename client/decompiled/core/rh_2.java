/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Rh
 */
class rh_2
extends jq {
    private int bIs;
    private float bIr;
    private float bIq;

    rh_2() {
    }

    void a(float f, float f2, int n2, int n3) {
        this.bIs = n3;
        this.bIr = f2 * f2 / (float)(n3 * n3);
        this.bIq = 1.0f - this.bIr;
    }

    float a(int n2, int n3, float f, float[] fArray, int n4) {
        int n5 = n3 + this.bIs;
        float f2 = 0.0f;
        if (n5 >= 0 && n5 < n4) {
            f2 = fArray[n2 + n5 * n4];
        }
        return f * this.bIq + f2 * this.bIr;
    }
}

