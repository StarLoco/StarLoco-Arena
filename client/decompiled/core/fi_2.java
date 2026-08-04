/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from FI
 */
public class fi_2
extends ys {
    public float b(float f, float f2, int n2, int n3) {
        float f3 = (float)n2 / (float)n3 / 2.0f + 0.5f;
        float f4 = (0.5f - f3) * (1.0f - 2.0f * Math.abs(0.5f - f3));
        f3 = (f3 - f4 - 0.5f) * 2.0f;
        return f + (f2 - f) * f3;
    }
}

