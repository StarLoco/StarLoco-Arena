/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from avR
 */
public enum avr_0 implements rk_0
{
    dgg,
    dgh,
    dgi,
    dgj,
    dgk,
    dgl,
    dgm,
    dgn,
    dgo,
    dgp,
    dgq,
    dgr,
    dgs,
    dgt,
    dgu,
    dgv,
    dgw,
    dgx,
    dgy,
    dgz,
    dgA,
    dgB,
    dgC,
    dgD,
    dgE,
    dgF,
    dgG,
    dgH,
    dgI,
    dgJ,
    dgK,
    dgL,
    dgM,
    dgN,
    dgO,
    dgP,
    dgQ,
    dgR;

    public static avr_0[] dgS;

    public static avr_0 cf(short s) {
        avr_0[] avr_0Array = avr_0.values();
        if (s < 0 || s >= avr_0Array.length) {
            return null;
        }
        return avr_0Array[s];
    }

    public short aJg() {
        return (short)this.ordinal();
    }

    public String cE() {
        return null;
    }

    public String cC() {
        return Short.toString(this.aJg());
    }

    public String cD() {
        return this.toString();
    }

    static {
        dgS = new avr_0[]{dgg, dgL, dgM, dgN, dgO, dgP, dgQ, dgR};
    }
}

