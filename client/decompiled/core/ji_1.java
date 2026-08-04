/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from JI
 */
public class ji_1 {
    public static final ji_1 bmb = new ji_1(1.0f, 1.0f, 1.0f);
    public static final ji_1 bmc = new ji_1(0.0f, 0.0f, 0.0f);
    protected float IQ;
    protected float IR;
    protected float IS;
    protected float IU;
    protected float IV;
    protected float IW;
    protected long bmd;
    protected long bme;

    public ji_1(ji_1 ji_12) {
        this.IU = this.IQ = ji_12.IQ;
        this.IV = this.IR = ji_12.IR;
        this.IW = this.IS = ji_12.IS;
        this.bmd = 0L;
        this.bme = 0L;
    }

    public ji_1(float f, float f2, float f3) {
        this.IU = this.IQ = f;
        this.IV = this.IR = f2;
        this.IW = this.IS = f3;
        this.bmd = 0L;
        this.bme = 0L;
    }

    public float Cp() {
        return this.IQ;
    }

    public float Cq() {
        return this.IR;
    }

    public float Cr() {
        return this.IS;
    }

    public void a(float f, float f2, float f3, long l2, long l3) {
        this.IU = f;
        this.IV = f2;
        this.IW = f3;
        this.bmd = l2;
        this.bme = l3;
    }

    public void d(float f, float f2, float f3) {
        this.IQ = this.IU = f;
        this.IR = this.IV = f2;
        this.IS = this.IW = f3;
        this.bme = 0L;
        this.bmd = 0L;
    }

    public void ay(long l2) {
        if (this.bme == 0L) {
            return;
        }
        long l3 = l2 - this.bmd;
        if (l3 < this.bme) {
            float f = (float)l3 / (float)this.bme;
            this.IQ = ej_0.a(this.IQ, this.IU, f);
            this.IR = ej_0.a(this.IR, this.IV, f);
            this.IS = ej_0.a(this.IS, this.IW, f);
        } else {
            this.IQ = this.IU;
            this.IR = this.IV;
            this.IS = this.IW;
            this.bme = 0L;
        }
    }

    public boolean jU() {
        return this.bme != 0L;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{");
        stringBuffer.append(this.IQ).append(" ; ");
        stringBuffer.append(this.IR).append(" ; ");
        stringBuffer.append(this.IS).append("}");
        return stringBuffer.toString();
    }
}

