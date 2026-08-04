/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from pQ
 */
public class pq_0 {
    float acx = 1.0f;
    float acy = 0.0f;
    float acz = 0.0f;
    float acA = 1.0f;
    float acB = 0.0f;
    float acC = 0.0f;
    boolean acD = true;
    boolean acE = true;
    float IQ = 1.0f;
    float IR = 1.0f;
    float IS = 1.0f;
    float IT = 1.0f;
    byte acF = 0;

    public final void uu() {
        this.acD = true;
        this.acx = 1.0f;
        this.acy = 0.0f;
        this.acz = 0.0f;
        this.acA = 1.0f;
    }

    public final void f(float f, float f2, float f3, float f4) {
        this.acD = false;
        this.acx = f;
        this.acy = f2;
        this.acz = f3;
        this.acA = f4;
    }

    public final void uv() {
        this.acE = true;
        this.acB = 0.0f;
        this.acC = 0.0f;
    }

    public final void m(float f, float f2) {
        this.acE = false;
        this.acB = f;
        this.acC = f2;
    }
}

