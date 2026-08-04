/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from aoC
 */
public class aoc_0
implements Pq {
    private float[] aaB = new float[16];

    public aoc_0() {
        this.reset();
    }

    public void reset() {
        this.aaB[0] = 1.0f;
        this.aaB[1] = 0.0f;
        this.aaB[2] = 0.0f;
        this.aaB[3] = 0.0f;
        this.aaB[4] = 0.0f;
        this.aaB[5] = 1.0f;
        this.aaB[6] = 0.0f;
        this.aaB[7] = 0.0f;
        this.aaB[8] = 0.0f;
        this.aaB[9] = 0.0f;
        this.aaB[10] = 1.0f;
        this.aaB[11] = 0.0f;
        this.aaB[12] = 0.0f;
        this.aaB[13] = 0.0f;
        this.aaB[14] = 0.0f;
        this.aaB[15] = 1.0f;
    }

    public void b(float f, float f2, float f3, float f4, float f5, float f6) {
        this.aaB[0] = f;
        this.aaB[1] = -f3;
        this.aaB[2] = 0.0f;
        this.aaB[3] = 0.0f;
        this.aaB[4] = -f2;
        this.aaB[5] = f4;
        this.aaB[6] = 0.0f;
        this.aaB[7] = 0.0f;
        this.aaB[8] = 0.0f;
        this.aaB[9] = 0.0f;
        this.aaB[10] = 1.0f;
        this.aaB[11] = 0.0f;
        this.aaB[12] = f5;
        this.aaB[13] = f6;
        this.aaB[14] = 0.0f;
        this.aaB[15] = 1.0f;
    }

    public void j(float f, float f2, float f3) {
        this.aaB[0] = (float)Math.cos(f) * f2;
        this.aaB[5] = (float)Math.cos(f) * f3;
        this.aaB[1] = (float)Math.sin(f) * f2;
        this.aaB[4] = -((float)Math.sin(f)) * f3;
    }

    public void aX(float f) {
        this.aaB[0] = (float)Math.cos(f);
        this.aaB[4] = -((float)Math.sin(f));
        this.aaB[1] = (float)Math.sin(f);
        this.aaB[5] = (float)Math.cos(f);
    }

    public void O(float f, float f2) {
        this.aaB[0] = this.aaB[0] * f;
        this.aaB[5] = this.aaB[5] * f2;
    }

    public void k(float f, float f2, float f3) {
        this.aaB[12] = f;
        this.aaB[13] = f2;
        this.aaB[14] = f3;
    }

    public void a(aoc_0 aoc_02, aoc_0 aoc_03) {
        this.aaB[0] = aoc_02.aaB[0] * aoc_03.aaB[0] + aoc_02.aaB[4] * aoc_03.aaB[1] + aoc_02.aaB[8] * aoc_03.aaB[2] + aoc_02.aaB[12] * aoc_03.aaB[3];
        this.aaB[1] = aoc_02.aaB[1] * aoc_03.aaB[0] + aoc_02.aaB[5] * aoc_03.aaB[1] + aoc_02.aaB[9] * aoc_03.aaB[2] + aoc_02.aaB[13] * aoc_03.aaB[3];
        this.aaB[2] = aoc_02.aaB[2] * aoc_03.aaB[0] + aoc_02.aaB[6] * aoc_03.aaB[1] + aoc_02.aaB[10] * aoc_03.aaB[2] + aoc_02.aaB[14] * aoc_03.aaB[3];
        this.aaB[3] = aoc_02.aaB[3] * aoc_03.aaB[0] + aoc_02.aaB[7] * aoc_03.aaB[1] + aoc_02.aaB[11] * aoc_03.aaB[2] + aoc_02.aaB[15] * aoc_03.aaB[3];
        this.aaB[4] = aoc_02.aaB[0] * aoc_03.aaB[4] + aoc_02.aaB[4] * aoc_03.aaB[5] + aoc_02.aaB[8] * aoc_03.aaB[6] + aoc_02.aaB[12] * aoc_03.aaB[7];
        this.aaB[5] = aoc_02.aaB[1] * aoc_03.aaB[4] + aoc_02.aaB[5] * aoc_03.aaB[5] + aoc_02.aaB[9] * aoc_03.aaB[6] + aoc_02.aaB[13] * aoc_03.aaB[7];
        this.aaB[6] = aoc_02.aaB[2] * aoc_03.aaB[4] + aoc_02.aaB[6] * aoc_03.aaB[5] + aoc_02.aaB[10] * aoc_03.aaB[6] + aoc_02.aaB[14] * aoc_03.aaB[7];
        this.aaB[7] = aoc_02.aaB[3] * aoc_03.aaB[4] + aoc_02.aaB[7] * aoc_03.aaB[5] + aoc_02.aaB[11] * aoc_03.aaB[6] + aoc_02.aaB[15] * aoc_03.aaB[7];
        this.aaB[8] = aoc_02.aaB[0] * aoc_03.aaB[8] + aoc_02.aaB[4] * aoc_03.aaB[9] + aoc_02.aaB[8] * aoc_03.aaB[10] + aoc_02.aaB[12] * aoc_03.aaB[11];
        this.aaB[9] = aoc_02.aaB[1] * aoc_03.aaB[8] + aoc_02.aaB[5] * aoc_03.aaB[9] + aoc_02.aaB[9] * aoc_03.aaB[10] + aoc_02.aaB[13] * aoc_03.aaB[11];
        this.aaB[10] = aoc_02.aaB[2] * aoc_03.aaB[8] + aoc_02.aaB[6] * aoc_03.aaB[9] + aoc_02.aaB[10] * aoc_03.aaB[10] + aoc_02.aaB[14] * aoc_03.aaB[11];
        this.aaB[11] = aoc_02.aaB[3] * aoc_03.aaB[8] + aoc_02.aaB[7] * aoc_03.aaB[9] + aoc_02.aaB[11] * aoc_03.aaB[10] + aoc_02.aaB[15] * aoc_03.aaB[11];
        this.aaB[12] = aoc_02.aaB[0] * aoc_03.aaB[12] + aoc_02.aaB[4] * aoc_03.aaB[13] + aoc_02.aaB[8] * aoc_03.aaB[14] + aoc_02.aaB[12] * aoc_03.aaB[15];
        this.aaB[13] = aoc_02.aaB[1] * aoc_03.aaB[12] + aoc_02.aaB[5] * aoc_03.aaB[13] + aoc_02.aaB[9] * aoc_03.aaB[14] + aoc_02.aaB[13] * aoc_03.aaB[15];
        this.aaB[14] = aoc_02.aaB[2] * aoc_03.aaB[12] + aoc_02.aaB[6] * aoc_03.aaB[13] + aoc_02.aaB[10] * aoc_03.aaB[14] + aoc_02.aaB[14] * aoc_03.aaB[15];
        this.aaB[15] = aoc_02.aaB[3] * aoc_03.aaB[12] + aoc_02.aaB[7] * aoc_03.aaB[13] + aoc_02.aaB[11] * aoc_03.aaB[14] + aoc_02.aaB[15] * aoc_03.aaB[15];
    }

    public final void aY(float f) {
        this.aaB[0] = this.aaB[0] * f;
        this.aaB[4] = this.aaB[4] * f;
        this.aaB[8] = this.aaB[8] * f;
        this.aaB[12] = this.aaB[12] * f;
        this.aaB[1] = this.aaB[1] * f;
        this.aaB[5] = this.aaB[5] * f;
        this.aaB[9] = this.aaB[9] * f;
        this.aaB[13] = this.aaB[13] * f;
        this.aaB[2] = this.aaB[2] * f;
        this.aaB[6] = this.aaB[6] * f;
        this.aaB[10] = this.aaB[10] * f;
        this.aaB[14] = this.aaB[14] * f;
        this.aaB[3] = this.aaB[3] * f;
        this.aaB[7] = this.aaB[7] * f;
        this.aaB[11] = this.aaB[11] * f;
        this.aaB[15] = this.aaB[15] * f;
    }

    public final void a(aoc_0 aoc_02, aoc_0 aoc_03, aoc_0 aoc_04) {
        aoc_0 aoc_05 = new aoc_0();
        aoc_05.a(aoc_03, aoc_04);
        this.a(aoc_02, aoc_05);
    }

    public final void a(aoc_0 aoc_02, aoc_0 aoc_03, aoc_0 aoc_04, aoc_0 aoc_05) {
        aoc_0 aoc_06 = new aoc_0();
        aoc_06.a(aoc_03, aoc_04, aoc_05);
        this.a(aoc_02, aoc_06);
    }

    public final float aCN() {
        float f = this.aaB[0] * this.aaB[5] - this.aaB[4] * this.aaB[1];
        float f2 = this.aaB[0] * this.aaB[9] - this.aaB[8] * this.aaB[1];
        float f3 = this.aaB[0] * this.aaB[13] - this.aaB[12] * this.aaB[1];
        float f4 = this.aaB[4] * this.aaB[9] - this.aaB[8] * this.aaB[5];
        float f5 = this.aaB[4] * this.aaB[13] - this.aaB[12] * this.aaB[5];
        float f6 = this.aaB[8] * this.aaB[13] - this.aaB[12] * this.aaB[9];
        float f7 = this.aaB[2] * this.aaB[7] - this.aaB[6] * this.aaB[3];
        float f8 = this.aaB[2] * this.aaB[11] - this.aaB[10] * this.aaB[3];
        float f9 = this.aaB[2] * this.aaB[15] - this.aaB[14] * this.aaB[3];
        float f10 = this.aaB[6] * this.aaB[11] - this.aaB[10] * this.aaB[7];
        float f11 = this.aaB[6] * this.aaB[15] - this.aaB[14] * this.aaB[7];
        float f12 = this.aaB[10] * this.aaB[15] - this.aaB[14] * this.aaB[11];
        return f * f12 - f2 * f11 + f3 * f10 + f4 * f9 - f5 * f8 + f6 * f7;
    }

    public final boolean b(aoc_0 aoc_02) {
        assert (aoc_02 != null);
        float f = this.aaB[0] * this.aaB[5] - this.aaB[4] * this.aaB[1];
        float f2 = this.aaB[10] * this.aaB[15] - this.aaB[14] * this.aaB[11];
        float f3 = this.aaB[0] * this.aaB[9] - this.aaB[8] * this.aaB[1];
        float f4 = this.aaB[6] * this.aaB[15] - this.aaB[14] * this.aaB[7];
        float f5 = this.aaB[0] * this.aaB[13] - this.aaB[12] * this.aaB[1];
        float f6 = this.aaB[6] * this.aaB[11] - this.aaB[10] * this.aaB[7];
        float f7 = this.aaB[4] * this.aaB[9] - this.aaB[8] * this.aaB[5];
        float f8 = this.aaB[2] * this.aaB[15] - this.aaB[14] * this.aaB[3];
        float f9 = this.aaB[4] * this.aaB[13] - this.aaB[12] * this.aaB[5];
        float f10 = this.aaB[2] * this.aaB[11] - this.aaB[10] * this.aaB[3];
        float f11 = this.aaB[8] * this.aaB[13] - this.aaB[12] * this.aaB[9];
        float f12 = this.aaB[2] * this.aaB[7] - this.aaB[6] * this.aaB[3];
        float f13 = f * f2 - f3 * f4 + f5 * f6 + f7 * f8 - f9 * f10 + f11 * f12;
        if ((double)(-f13) > 1.0E-4 || (double)f13 < 1.0E-4) {
            return false;
        }
        aoc_02.aaB[0] = this.aaB[5] * f2 - this.aaB[9] * f4 + this.aaB[13] * f6;
        aoc_02.aaB[1] = -this.aaB[1] * f2 + this.aaB[9] * f8 - this.aaB[13] * f10;
        aoc_02.aaB[2] = this.aaB[1] * f4 - this.aaB[5] * f8 + this.aaB[13] * f12;
        aoc_02.aaB[3] = -this.aaB[1] * f6 + this.aaB[5] * f10 - this.aaB[9] * f12;
        aoc_02.aaB[4] = -this.aaB[4] * f2 + this.aaB[8] * f4 - this.aaB[12] * f6;
        aoc_02.aaB[5] = this.aaB[0] * f2 - this.aaB[8] * f8 + this.aaB[12] * f10;
        aoc_02.aaB[6] = -this.aaB[0] * f4 + this.aaB[4] * f8 - this.aaB[12] * f12;
        aoc_02.aaB[7] = this.aaB[0] * f6 - this.aaB[4] * f10 + this.aaB[8] * f12;
        aoc_02.aaB[8] = this.aaB[7] * f11 - this.aaB[11] * f9 + this.aaB[15] * f7;
        aoc_02.aaB[9] = -this.aaB[3] * f11 + this.aaB[11] * f5 - this.aaB[15] * f3;
        aoc_02.aaB[10] = this.aaB[3] * f9 - this.aaB[7] * f5 + this.aaB[15] * f;
        aoc_02.aaB[11] = -this.aaB[3] * f7 + this.aaB[7] * f3 - this.aaB[11] * f;
        aoc_02.aaB[12] = -this.aaB[6] * f11 + this.aaB[10] * f9 - this.aaB[14] * f7;
        aoc_02.aaB[13] = this.aaB[2] * f11 - this.aaB[10] * f5 + this.aaB[14] * f3;
        aoc_02.aaB[14] = -this.aaB[2] * f9 + this.aaB[6] * f5 - this.aaB[14] * f;
        aoc_02.aaB[15] = this.aaB[2] * f7 - this.aaB[6] * f3 + this.aaB[10] * f;
        aoc_02.aY(1.0f / f13);
        return true;
    }

    public final float[] Pn() {
        return this.aaB;
    }

    public void a(GL gL) {
        gL.glMultMatrixf(this.aaB, 0);
    }

    public String toString() {
        return String.format("Matrix2D", new Object[0]);
    }
}

