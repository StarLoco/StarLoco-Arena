/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from auO
 */
public class auo_0 {
    private int aG;
    private int aH;

    public auo_0(int n2, int n3) {
        this.aG = n2;
        this.aH = n3;
    }

    public auo_0(adz_1 adz_12) {
        this.aG = adz_12.getX();
        this.aH = adz_12.getY();
    }

    public final int getX() {
        return this.aG;
    }

    public final int getY() {
        return this.aH;
    }

    public final void add(int n2, int n3) {
        this.aG += n2;
        this.aH += n3;
    }

    public final void d(auo_0 auo_02) {
        this.aG += auo_02.aG;
        this.aH += auo_02.aH;
    }

    public final void e(auo_0 auo_02) {
        this.aG -= auo_02.aG;
        this.aH -= auo_02.aH;
    }

    public final void set(int n2, int n3) {
        this.aG = n2;
        this.aH = n3;
    }

    public String toString() {
        return "Coord(" + this.aG + ";" + this.aH + ")";
    }

    public final int hashCode() {
        long l2 = 31L * (31L + (long)this.aG) + (long)this.aH;
        return (int)(l2 ^ l2 >> 32);
    }

    public final boolean S(int n2, int n3) {
        return this.aG == n2 && this.aH == n3;
    }

    public final boolean f(auo_0 auo_02) {
        return this.aG == auo_02.aG && this.aH == auo_02.aH;
    }
}

