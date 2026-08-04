/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aHb
 */
public class ahb_1 {
    private final float bCM;
    private final float dJX;
    private final float dJY;
    private final float dJZ;
    private final int wg;
    private long dKa = -1L;
    final /* synthetic */ aag_1 dKb;

    public ahb_1(aag_1 aag_12, float f, float f2, int n2) {
        this.dKb = aag_12;
        this.bCM = aag_1.a(aag_12);
        this.dJX = aag_1.b(aag_12);
        this.dJY = f;
        this.dJZ = f2;
        this.wg = n2;
    }

    public boolean ev(long l2) {
        if (this.dKa == -1L) {
            this.dKa = l2;
            return true;
        }
        int n2 = (int)(l2 - this.dKa);
        int n3 = ej_0.e(n2, 0, this.wg);
        aag_1.a(this.dKb, this.b(this.bCM, this.dJY, n3, this.wg));
        aag_1.b(this.dKb, this.b(this.dJX, this.dJZ, n3, this.wg));
        aag_1.a(this.dKb, aag_1.a(this.dKb), aag_1.b(this.dKb));
        return n2 < this.wg;
    }

    private float b(float f, float f2, int n2, int n3) {
        float f3 = (float)n2 / (float)n3;
        float f4 = (0.5f - f3) * (1.0f - 2.0f * Math.abs(0.5f - f3));
        return f + (f2 - f) * (f3 -= f4);
    }
}

