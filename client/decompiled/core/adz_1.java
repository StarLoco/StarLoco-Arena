/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from adZ
 */
public class adz_1 {
    private int aG;
    private int aH;

    public adz_1() {
    }

    public adz_1(adz_1 adz_12) {
        this.b(adz_12);
    }

    public adz_1(int n2, int n3) {
        this.set(n2, n3);
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        adz_1 adz_12 = (adz_1)object;
        return this.aG == adz_12.aG && this.aH == adz_12.aH;
    }

    public final int hashCode() {
        long l2 = 31L * (31L + (long)this.aG) + (long)this.aH;
        return (int)(l2 ^ l2 >> 32);
    }

    public String toString() {
        return "{Point2i : (" + this.aG + ", " + this.aH + ") @" + Integer.toHexString(this.hashCode()) + "}";
    }

    public final boolean S(int n2, int n3) {
        return this.aG == n2 && this.aH == n3;
    }

    public final boolean a(adz_1 adz_12) {
        return this.aG == adz_12.aG && this.aH == adz_12.aH;
    }

    public final void set(int n2, int n3) {
        this.aG = n2;
        this.aH = n3;
    }

    public final void b(adz_1 adz_12) {
        this.aG = adz_12.aG;
        this.aH = adz_12.aH;
    }

    public final int getX() {
        return this.aG;
    }

    public final int getY() {
        return this.aH;
    }

    public final void setX(int n2) {
        this.aG = n2;
    }

    public final void setY(int n2) {
        this.aH = n2;
    }
}

