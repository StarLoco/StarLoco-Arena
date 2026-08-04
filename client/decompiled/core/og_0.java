/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from oG
 */
public class og_0
implements Pq {
    private float[] aaB = new float[16];

    public og_0() {
        this.reset();
    }

    public og_0(float f, float f2) {
        this();
        this.k(f, f2);
    }

    public float getX() {
        return -this.aaB[1];
    }

    public void x(float f) {
        this.aaB[1] = -f;
    }

    public float getY() {
        return -this.aaB[4];
    }

    public void y(float f) {
        this.aaB[4] = -f;
    }

    public void k(float f, float f2) {
        this.aaB[1] = -f;
        this.aaB[4] = -f2;
    }

    public void add(float f, float f2) {
        this.aaB[1] = this.aaB[1] - f;
        this.aaB[4] = this.aaB[4] - f2;
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

    public void a(GL gL) {
        if (this.aaB[1] != 0.0f || this.aaB[4] != 0.0f) {
            gL.glMultMatrixf(this.aaB, 0);
        }
    }

    public String toString() {
        return String.format("RotateSkew x=%f y=%f", Float.valueOf(this.getX()), Float.valueOf(this.getY()));
    }
}

