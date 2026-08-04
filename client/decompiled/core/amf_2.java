/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from aMF
 */
public class amf_2
implements Pq {
    protected float Hk;
    protected float Hl;
    protected float Hm;

    public amf_2() {
    }

    public amf_2(float f, float f2, float f3) {
        this.d(f, f2, f3);
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

    public float id() {
        return this.Hm;
    }

    public void X(float f) {
        this.Hm = f;
    }

    public void d(float f, float f2, float f3) {
        this.Hk = f;
        this.Hl = f2;
        this.Hm = f3;
    }

    public void p(float f, float f2, float f3) {
        this.Hk += f;
        this.Hl += f2;
        this.Hm += f3;
    }

    public void a(GL gL) {
        if (this.Hk != 0.0f || this.Hl != 0.0f || this.Hm != 0.0f) {
            gL.glTranslatef(this.Hk, this.Hl, this.Hm);
        }
    }

    public void reset() {
        this.Hm = 0.0f;
        this.Hl = 0.0f;
        this.Hk = 0.0f;
    }

    public String toString() {
        return String.format("Position x=%f y=%f z=%f", Float.valueOf(this.Hk), Float.valueOf(this.Hl), Float.valueOf(this.Hm));
    }
}

