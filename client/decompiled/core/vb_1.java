/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from VB
 */
public class vb_1
implements Comparable {
    private short EL;
    private short EM;
    float bSG;
    float bSH;
    float bSI;
    float bSJ;
    float bSK;
    float bSL;
    float bSM;
    float bSN;
    private vb_1 bSO;
    private vb_1 bSP;
    private vb_1 bSQ;
    private vb_1 bSR;

    public vb_1(short s, short s2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.EL = s;
        this.EM = s2;
        this.bSG = f;
        this.bSH = f2;
        this.bSI = f3;
        this.bSJ = f4;
        this.bSK = f5;
        this.bSL = f6;
        this.bSM = f7;
        this.bSN = f8;
    }

    public short pi() {
        return this.EL;
    }

    public short pj() {
        return this.EM;
    }

    public float ait() {
        return this.bSG;
    }

    public void av(float f) {
        this.bSG = f;
    }

    public float aiu() {
        return this.bSH;
    }

    public void aw(float f) {
        this.bSH = f;
    }

    public float aiv() {
        return this.bSI;
    }

    public void ax(float f) {
        this.bSI = f;
    }

    public float aiw() {
        return this.bSJ;
    }

    public void ay(float f) {
        this.bSJ = f;
    }

    public float aix() {
        return this.bSK;
    }

    public void az(float f) {
        this.bSK = f;
    }

    public float aiy() {
        return this.bSL;
    }

    public void aA(float f) {
        this.bSL = f;
    }

    public float aiz() {
        return this.bSM;
    }

    public void aB(float f) {
        this.bSM = f;
    }

    public float aiA() {
        return this.bSN;
    }

    public void aC(float f) {
        this.bSN = f;
    }

    public int a(vb_1 vb_12) {
        if (vb_12.EM != this.EM) {
            return this.EM - vb_12.EM;
        }
        return this.EL - vb_12.EL;
    }

    public vb_1 aiB() {
        return this.bSO;
    }

    public void b(vb_1 vb_12) {
        this.bSO = vb_12;
    }

    public vb_1 aiC() {
        return this.bSP;
    }

    public void c(vb_1 vb_12) {
        this.bSP = vb_12;
    }

    public vb_1 aiD() {
        return this.bSQ;
    }

    public void d(vb_1 vb_12) {
        this.bSQ = vb_12;
    }

    public vb_1 aiE() {
        return this.bSR;
    }

    public void e(vb_1 vb_12) {
        this.bSR = vb_12;
    }

    public float[] aiF() {
        return new float[]{this.bSG, this.bSH, this.bSI, this.bSJ, this.bSK, this.bSL, this.bSM, this.bSN};
    }

    static /* synthetic */ short f(vb_1 vb_12) {
        return vb_12.EL;
    }

    static /* synthetic */ short g(vb_1 vb_12) {
        return vb_12.EM;
    }
}

