/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

public abstract class avw {
    private boolean dep = true;
    private boolean cDF = false;
    private float deq;
    private float der;
    private float des;
    private float fG;

    public abstract int getID();

    public abstract ef_1 a(db_2 var1, GL var2);

    public void a(float f) {
        this.fG += f;
        if (this.fG > this.deq + this.der + this.des) {
            this.en(false);
        }
    }

    public final boolean isEnabled() {
        return this.dep;
    }

    public final void setEnabled(boolean bl2) {
        this.dep = bl2;
    }

    public final boolean aiW() {
        return this.cDF;
    }

    public final void en(boolean bl2) {
        this.cDF = bl2;
    }

    protected void l(float f, float f2, float f3) {
        this.fG = 0.0f;
        this.deq = f * 0.001f;
        this.der = f2 * 0.001f;
        this.des = f3 * 0.001f;
        this.en(true);
    }

    protected float aID() {
        if (this.fG < this.deq) {
            return this.fG / this.deq;
        }
        if (this.fG < this.deq + this.der) {
            return 1.0f;
        }
        if (this.fG < this.deq + this.der + this.des) {
            return (this.deq + this.der + this.des - this.fG) / this.des;
        }
        return 0.0f;
    }
}

