/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

public class aGs
implements Pq {
    protected float dIB;

    public aGs() {
    }

    public aGs(float f) {
        this.dIB = f;
    }

    public float aSr() {
        return this.dIB;
    }

    public void bG(float f) {
        this.dIB = f;
    }

    public void add(float f) {
        this.dIB += f;
    }

    public void a(GL gL) {
        if (this.dIB != 0.0f) {
            gL.glRotatef(this.dIB, 0.0f, 0.0f, 1.0f);
        }
    }

    public void reset() {
        this.dIB = 0.0f;
    }

    public String toString() {
        return String.format("Rotation angle=%f", Float.valueOf(this.dIB));
    }
}

