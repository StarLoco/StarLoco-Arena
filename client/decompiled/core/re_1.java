/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Re
 */
class re_1
extends jq {
    private int bIp;
    private float bIq;
    private float bIr;

    re_1() {
    }

    void a(float f, float f2, int n2, int n3) {
        this.bIp = n2;
        this.bIr = f * f / (float)(n2 * n2);
        this.bIq = 1.0f - this.bIr;
    }

    float a(int n2, int n3, float f, float[] fArray, int n4) {
        int n5 = n2 + this.bIp;
        float f2 = 0.0f;
        if (n5 >= 0 && n5 < n4) {
            f2 = fArray[n5 + n3 * n4];
        }
        return f * this.bIq + f2 * this.bIr;
    }
}

