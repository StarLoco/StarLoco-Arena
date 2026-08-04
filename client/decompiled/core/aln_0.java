/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aLn
 */
public class aln_0
extends aMn {
    private agv_0 dic = new agv_0();
    private agv_0 did = new agv_0();
    private agv_0 die = new agv_0();
    private agv_0 dif = new agv_0();
    private float dVA;
    private float bIL;
    private float dVB;
    private float dVC;
    private float dVD;
    private float cOS;
    private float bIK;
    private float cOR;
    private float dVE;
    private float dVF;
    private float dVG;
    private float dVH;
    private boolean dVI = true;
    private long dVJ;
    private long dVK;

    public aln_0(Du du) {
        super(du);
    }

    public void dN(long l2) {
        this.dVJ = l2;
    }

    public agv_0 u() {
        return this.dic;
    }

    public void f(agv_0 agv_02) {
        this.dic = agv_02;
        this.dVI = true;
    }

    public agv_0 aJA() {
        return this.did;
    }

    public void g(agv_0 agv_02) {
        this.did = agv_02;
        this.dVI = true;
    }

    public agv_0 v() {
        return this.die;
    }

    public void h(agv_0 agv_02) {
        this.die = agv_02;
        this.dVI = true;
    }

    public agv_0 aJB() {
        return this.dif;
    }

    public void i(agv_0 agv_02) {
        this.dif = agv_02;
        this.dVI = true;
    }

    private void aWn() {
        float f = 1.0f;
        float f2 = this.dic.getX();
        float f3 = this.dic.getY();
        float f4 = this.dic.id();
        float f5 = f2 + this.did.getX() * 1.0f;
        float f6 = f3 + this.did.getY() * 1.0f;
        float f7 = f4 + this.did.id() * 1.0f;
        float f8 = this.die.getX();
        float f9 = this.die.getY();
        float f10 = this.die.id();
        float f11 = f8 - this.dif.getX() * 1.0f;
        float f12 = f9 - this.dif.getY() * 1.0f;
        float f13 = f10 - this.dif.id() * 1.0f;
        this.dVA = f8 - 3.0f * f11 + 3.0f * f5 - f2;
        this.bIL = 3.0f * f11 - 6.0f * f5 + 3.0f * f2;
        this.dVB = 3.0f * f5 - 3.0f * f2;
        this.dVC = f2;
        this.dVD = f9 - 3.0f * f12 + 3.0f * f6 - f3;
        this.cOS = 3.0f * f12 - 6.0f * f6 + 3.0f * f3;
        this.bIK = 3.0f * f6 - 3.0f * f3;
        this.cOR = f3;
        this.dVE = f10 - 3.0f * f13 + 3.0f * f7 - f4;
        this.dVF = 3.0f * f13 - 6.0f * f7 + 3.0f * f4;
        this.dVG = 3.0f * f7 - 3.0f * f4;
        this.dVH = f4;
        this.dVI = false;
    }

    public agv_0 a(long l2) {
        if (this.dVI) {
            this.aWn();
        }
        assert (l2 >= 0L) : "Le temps ne doit pas etre inferieur a 0";
        if (l2 > this.dVJ) {
            l2 = this.dVJ;
        }
        float f = (float)l2 / (float)this.dVJ;
        float f2 = f * f;
        float f3 = f2 * f;
        return new agv_0(this.dVA * f3 + this.bIL * f2 + this.dVB * f + this.dVC, this.dVD * f3 + this.cOS * f2 + this.bIK * f + this.cOR, this.dVE * f3 + this.dVF * f2 + this.dVG * f + this.dVH);
    }

    public long getDuration() {
        return this.dVJ;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(this.getClass().getSimpleName());
        stringBuffer.append(" (duration:").append(this.dVJ).append(") > from=").append(this.dic).append(", to=").append(this.die).append(", initVel=").append(this.did).append(", finalVel=").append(this.dif).append(".");
        return stringBuffer.toString();
    }

    public double aJk() {
        return this.dVJ;
    }

    public void bI(int n2) {
        this.dVK += (long)n2;
        if (this.dVK > this.dVJ) {
            this.aWV();
        }
        if (this.Ie == null) {
            return;
        }
        agv_0 agv_02 = this.a(this.dVK);
        this.Ie.a(agv_02.getX(), agv_02.getY(), agv_02.id());
    }
}

