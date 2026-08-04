/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from nN
 */
public class nn_2
implements Pq {
    private float Hk = 1.0f;
    private float Hl = 1.0f;

    public nn_2() {
    }

    public nn_2(float f, float f2) {
        this.k(f, f2);
    }

    public float getX() {
        return this.Hk;
    }

    public void x(float f) {
        this.Hk = f;
    }

    public float getY() {
        return this.Hl;
    }

    public void y(float f) {
        this.Hl = f;
    }

    public void k(float f, float f2) {
        this.Hk = f;
        this.Hl = f2;
    }

    public void l(float f, float f2) {
        this.Hk *= f;
        this.Hl *= f2;
    }

    public void a(GL gL) {
        if (this.Hk != 1.0f || this.Hl != 1.0f) {
            gL.glScalef(this.Hk, this.Hl, 1.0f);
        }
    }

    public void reset() {
        this.Hl = 1.0f;
        this.Hk = 1.0f;
    }

    public String toString() {
        return String.format("Scaling : x=%f y=%f", Float.valueOf(this.Hk), Float.valueOf(this.Hl));
    }
}

