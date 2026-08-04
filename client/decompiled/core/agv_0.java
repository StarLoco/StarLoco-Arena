/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aGv
 */
public class agv_0 {
    private static final float pr = (float)Math.PI;
    private static final float dIC = 0.3926991f;
    private static final float dID = 1.1780972f;
    private static final float dIE = 1.9634955f;
    private static final float dIF = 2.7488937f;
    private static final float dIG = 0.7853982f;
    private static final float dIH = 2.3561945f;
    public float Hk;
    public float Hl;
    public float Hm;
    public static final agv_0 dII = new agv_0(1.0f, 0.0f, 0.0f);
    public static final agv_0 dIJ = new agv_0(0.0f, 1.0f, 0.0f);
    public static final agv_0 dIK = new agv_0(0.0f, 0.0f, 1.0f);
    public static final agv_0 dIL = new agv_0();

    public agv_0() {
        this(0.0f, 0.0f, 0.0f);
    }

    public agv_0(agv_0 agv_02) {
        this(agv_02.Hk, agv_02.Hl, agv_02.Hm);
    }

    public agv_0(ry ry2, ry ry3) {
        this(ry3.getX() - ry2.getX(), ry3.getY() - ry2.getY(), ry3.wk() - ry2.wk());
    }

    public agv_0(float[] fArray) {
        this(fArray[0], fArray[1], fArray[2]);
    }

    public agv_0(int[] nArray) {
        this(nArray[0], nArray[1], nArray[2]);
    }

    public agv_0(float f, float f2, float f3) {
        this.Hk = f;
        this.Hl = f2;
        this.Hm = f3;
    }

    public agv_0(int n2, int n3, short s, int n4, int n5, short s2) {
        this.Hk = n4 - n2;
        this.Hl = n5 - n3;
        this.Hm = s2 - s;
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

    public void l(int[] nArray) {
        this.Hk = nArray[0];
        this.Hl = nArray[1];
        this.Hm = nArray[2];
    }

    public void j(agv_0 agv_02) {
        if (agv_02 == null) {
            return;
        }
        this.Hk = agv_02.Hk;
        this.Hl = agv_02.Hl;
        this.Hm = agv_02.Hm;
    }

    public void k(agv_0 agv_02) {
        if (agv_02 == null) {
            return;
        }
        this.Hk += agv_02.Hk;
        this.Hl += agv_02.Hl;
        this.Hm += agv_02.Hm;
    }

    public void l(agv_0 agv_02) {
        if (agv_02 == null) {
            return;
        }
        this.Hk -= agv_02.Hk;
        this.Hl -= agv_02.Hl;
        this.Hm -= agv_02.Hm;
    }

    public void O(double d) {
        this.Hk = (float)((double)this.Hk * d);
        this.Hl = (float)((double)this.Hl * d);
        this.Hm = (float)((double)this.Hm * d);
    }

    public boolean o(float f, float f2, float f3) {
        boolean bl2 = this.bK(f);
        boolean bl3 = this.bL(f2);
        boolean bl4 = this.bM(f3);
        return bl2 || bl3 || bl4;
    }

    public boolean bK(float f) {
        if (-f <= this.Hk && this.Hk <= f) {
            return false;
        }
        float f2 = Math.abs(f);
        float f3 = Math.abs(this.Hk);
        this.Hm = this.Hm * f2 / f3;
        this.Hl = this.Hl * f2 / f3;
        this.Hk = this.Hk > f ? f : -f;
        return true;
    }

    public boolean bL(float f) {
        if (-f <= this.Hl && this.Hl <= f) {
            return false;
        }
        float f2 = Math.abs(f);
        float f3 = Math.abs(this.Hl);
        this.Hm = this.Hm * f2 / f3;
        this.Hk = this.Hk * f2 / f3;
        this.Hl = this.Hl > f ? f : -f;
        return true;
    }

    public boolean bM(float f) {
        if (-f <= this.Hm && this.Hm <= f) {
            return false;
        }
        float f2 = Math.abs(f);
        float f3 = Math.abs(this.Hm);
        this.Hl = this.Hl * f2 / f3;
        this.Hk = this.Hk * f2 / f3;
        this.Hm = this.Hm > f ? f : -f;
        return true;
    }

    public agv_0 m(agv_0 agv_02) {
        return new agv_0(agv_02.Hk + this.Hk, agv_02.Hl + this.Hl, agv_02.Hm + this.Hm);
    }

    public agv_0 n(agv_0 agv_02) {
        return new agv_0(this.Hk - agv_02.Hk, this.Hl - agv_02.Hl, this.Hm - agv_02.Hm);
    }

    public agv_0 o(agv_0 agv_02) {
        return new agv_0(this.Hk * agv_02.Hk + this.Hk * agv_02.Hl + this.Hk * agv_02.Hm, this.Hl * agv_02.Hk + this.Hl * agv_02.Hl + this.Hl * agv_02.Hm, this.Hm * agv_02.Hk + this.Hm * agv_02.Hl + this.Hm * agv_02.Hm);
    }

    public agv_0 bN(float f) {
        return new agv_0(f * this.Hk, f * this.Hl, f * this.Hm);
    }

    public float p(agv_0 agv_02) {
        return this.Hk * agv_02.Hk + this.Hl * agv_02.Hl + this.Hm * agv_02.Hm;
    }

    public float e(aby_2 aby_22) {
        return this.Hk * (float)aby_22.getX() + this.Hl * (float)aby_22.getY() + this.Hm * (float)aby_22.Ui();
    }

    public float q(agv_0 agv_02) {
        return this.Hk * agv_02.Hl + this.Hl * agv_02.Hm + this.Hm * agv_02.Hk - agv_02.Hk * this.Hl - agv_02.Hl * this.Hm - agv_02.Hm * this.Hk;
    }

    public float aSx() {
        return this.Hk * this.Hk + this.Hl * this.Hl + this.Hm * this.Hm;
    }

    public float aSy() {
        return (float)Math.sqrt(this.Hk * this.Hk + this.Hl * this.Hl + this.Hm * this.Hm);
    }

    public float aSz() {
        return (float)Math.sqrt(this.Hk * this.Hk + this.Hl * this.Hl);
    }

    public agv_0 aSA() {
        float f = this.aSy();
        if (f == 0.0f) {
            return new agv_0(this.Hk, this.Hl, this.Hm);
        }
        return this.bN(1.0f / f);
    }

    public void aSB() {
        float f = this.aSy();
        if (f == 0.0f) {
            return;
        }
        this.O(1.0f / f);
    }

    public boolean equals(Object object) {
        if (object == null || !(object instanceof agv_0)) {
            return false;
        }
        agv_0 agv_02 = (agv_0)object;
        return agv_02.Hk == this.Hk && agv_02.Hl == this.Hl && agv_02.Hm == this.Hm;
    }

    public String toString() {
        return "[" + this.Hk + " ; " + this.Hl + " ; " + this.Hm + "]";
    }

    public static qc_0 D(float f, float f2) {
        float f3 = -((float)Math.atan2(f2, f));
        qc_0 qc_02 = f3 <= 2.7488937f && f3 >= 1.9634955f ? qc_0.bEP : (f3 <= 1.9634955f && f3 >= 1.1780972f ? qc_0.bEQ : (f3 <= 1.1780972f && f3 >= 0.3926991f ? qc_0.bEJ : (f3 <= 0.3926991f && f3 >= -0.3926991f ? qc_0.bEK : (f3 <= -0.3926991f && f3 >= -1.1780972f ? qc_0.bEL : (f3 <= -1.1780972f && f3 >= -1.9634955f ? qc_0.bEM : (f3 <= -1.9634955f && f3 >= -2.7488937f ? qc_0.bEN : qc_0.bEO))))));
        return qc_02;
    }

    public qc_0 aqA() {
        return agv_0.D(this.Hk, this.Hl);
    }

    public static qc_0 E(float f, float f2) {
        if (f == 0.0f && f2 == 0.0f) {
            return qc_0.bET;
        }
        float f3 = -((float)Math.atan2(f2, f));
        qc_0 qc_02 = f3 <= 2.3561945f && f3 >= 0.7853982f ? qc_0.bEQ : (f3 <= 0.7853982f && f3 >= -0.7853982f ? qc_0.bEK : (f3 <= -0.7853982f && f3 >= -2.3561945f ? qc_0.bEM : qc_0.bEO));
        return qc_02;
    }

    public qc_0 aqB() {
        return agv_0.E(this.Hk, this.Hl);
    }

    public agv_0 aSC() {
        return new agv_0(-this.Hk, -this.Hl, -this.Hm);
    }

    public int hashCode() {
        assert (false) : "Il n'est pas pr\u00e9vu que ces objets comparables servent de clef dans une HashTable/HashMap.";
        return super.hashCode();
    }
}

