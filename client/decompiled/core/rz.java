/*
 * Decompiled with CFR 0.152.
 */
public class rz {
    private float Hk;
    private float Hl;

    public rz() {
    }

    public rz(rz rz2) {
        this.b(rz2);
    }

    public rz(float f, float f2) {
        this.k(f, f2);
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        rz rz2 = (rz)object;
        return this.Hk == rz2.Hk && this.Hl == rz2.Hl;
    }

    public final int hashCode() {
        long l2 = 31L * (31L + (long)this.Hk) + (long)this.Hl;
        return (int)(l2 ^ l2 >> 32);
    }

    public String toString() {
        return "{Point2i : (" + this.Hk + ", " + this.Hl + ") @" + Integer.toHexString(this.hashCode()) + "}";
    }

    public final boolean q(float f, float f2) {
        return this.Hk == f && this.Hl == f2;
    }

    public final boolean a(rz rz2) {
        return this.Hk == rz2.Hk && this.Hl == rz2.Hl;
    }

    public final void k(float f, float f2) {
        this.Hk = f;
        this.Hl = f2;
    }

    public final void b(rz rz2) {
        this.Hk = rz2.Hk;
        this.Hl = rz2.Hl;
    }

    public final float getX() {
        return this.Hk;
    }

    public final float getY() {
        return this.Hl;
    }

    public final void x(float f) {
        this.Hk = f;
    }

    public final void y(float f) {
        this.Hl = f;
    }
}

