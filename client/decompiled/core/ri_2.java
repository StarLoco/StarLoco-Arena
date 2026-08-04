/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Ri
 */
class ri_2
extends jq {
    private int bIp;
    private int bIs;
    private float bIt;
    private float bIu;
    private float bIv;
    private float bIw;

    ri_2() {
    }

    void a(float f, float f2, int n2, int n3) {
        this.bIp = n2;
        this.bIs = n3;
        float f3 = f * f / (float)(n2 * n2);
        float f4 = f2 * f2 / (float)(n3 * n3);
        this.bIt = (1.0f - f3) * (1.0f - f4);
        this.bIu = (1.0f - f3) * f4;
        this.bIv = f3 * (1.0f - f4);
        this.bIw = f3 * f4;
    }

    float a(int n2, int n3, float f, float[] fArray, int n4) {
        int n5 = n2 + this.bIp;
        int n6 = n3 + this.bIs;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        if (n6 >= 0 && n6 < n4) {
            f2 = fArray[n2 + n6 * n4];
            if (n5 >= 0 && n5 < n4) {
                f4 = fArray[n5 + n6 * n4];
            }
        }
        if (n5 >= 0 && n5 < n4) {
            f3 = fArray[n5 + n3 * n4];
        }
        return f * this.bIt + f2 * this.bIu + f3 * this.bIv + f4 * this.bIw;
    }
}

