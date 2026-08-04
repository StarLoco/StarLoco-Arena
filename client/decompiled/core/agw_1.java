/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aGw
 */
public class agw_1 {
    public float Hk;
    public float Hl;

    public agw_1() {
        this(0.0f, 0.0f);
    }

    public agw_1(agw_1 agw_12) {
        this(agw_12.Hk, agw_12.Hl);
    }

    public agw_1(float[] fArray) {
        this(fArray[0], fArray[1]);
    }

    public agw_1(rz rz2, rz rz3) {
        this.Hk = rz3.getX() - rz2.getX();
        this.Hl = rz3.getY() - rz2.getY();
    }

    public agw_1(float f, float f2) {
        this.Hk = f;
        this.Hl = f2;
    }

    public agw_1(int n2, int n3, int n4, int n5) {
        this.Hk = n4 - n2;
        this.Hl = n5 - n3;
    }

    public void o(float[] fArray) {
        this.Hk = fArray[0];
        this.Hl = fArray[1];
    }

    public void k(float f, float f2) {
        this.Hk = f;
        this.Hl = f2;
    }

    public void a(agw_1 agw_12) {
        this.Hk = agw_12.Hk;
        this.Hl = agw_12.Hl;
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

    public agw_1 b(agw_1 agw_12) {
        return new agw_1(agw_12.Hk + this.Hk, agw_12.Hl + this.Hl);
    }

    public void c(agw_1 agw_12) {
        this.Hk += agw_12.Hk;
        this.Hl += agw_12.Hl;
    }

    public final float d(agw_1 agw_12) {
        float f = this.Hk - agw_12.Hk;
        float f2 = this.Hl - agw_12.Hl;
        return f * f + f2 * f2;
    }

    public agw_1 e(agw_1 agw_12) {
        return new agw_1(this.Hk - agw_12.Hk, this.Hl - agw_12.Hl);
    }

    public void f(agw_1 agw_12) {
        this.Hk -= agw_12.Hk;
        this.Hl -= agw_12.Hl;
    }

    public agw_1 g(agw_1 agw_12) {
        return new agw_1(this.Hk * agw_12.Hk + this.Hk * agw_12.Hl, this.Hl * agw_12.Hk + this.Hl * agw_12.Hl);
    }

    public agw_1 bO(float f) {
        return new agw_1(f * this.Hk, f * this.Hl);
    }

    public void bP(float f) {
        this.Hk *= f;
        this.Hl *= f;
    }

    public agw_1 bQ(float f) {
        return new agw_1((int)(f * this.Hk), (int)(f * this.Hl));
    }

    public float h(agw_1 agw_12) {
        return this.Hk * agw_12.Hl - agw_12.Hk * this.Hl;
    }

    public float i(agw_1 agw_12) {
        return this.Hk * agw_12.Hk + this.Hl * agw_12.Hl;
    }

    public float aSx() {
        return this.Hk * this.Hk + this.Hl * this.Hl;
    }

    public float aSy() {
        float f = this.Hk * this.Hk + this.Hl * this.Hl;
        return (float)Math.sqrt(f);
    }

    public agw_1 aSD() {
        float f = this.aSy();
        return this.bO(1.0f / f);
    }

    public void aSB() {
        float f = this.aSy();
        if (f == 0.0f) {
            f = 0.001f;
        }
        float f2 = 1.0f / f;
        this.Hk *= f2;
        this.Hl *= f2;
    }

    public boolean equals(Object object) {
        if (object == null || !(object instanceof agw_1)) {
            return false;
        }
        agw_1 agw_12 = (agw_1)object;
        return agw_12.Hk == this.Hk && agw_12.Hl == this.Hl;
    }

    public String toString() {
        return "V2 : [" + this.Hk + " ; " + this.Hl + "]";
    }

    public int hashCode() {
        long l2 = 31L * (31L + (long)this.Hk) + (long)this.Hl;
        return (int)(l2 ^ l2 >> 32);
    }
}

