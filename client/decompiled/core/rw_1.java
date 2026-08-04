/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Rw
 */
class rw_1
implements axq_0 {
    private final iN baD;
    final /* synthetic */ JX iS;
    final /* synthetic */ String yS;
    final /* synthetic */ jJ[] iU;
    final /* synthetic */ String baE;
    final /* synthetic */ aDI bJi;

    rw_1(aDI aDI2, JX jX, String string, jJ[] jJArray, String string2) {
        this.bJi = aDI2;
        this.iS = jX;
        this.yS = string;
        this.iU = jJArray;
        this.baE = string2;
        this.baD = new iN(this.iS, this.yS, this.iU);
    }

    public void aL(String string) {
        if (string.equals(this.baE)) {
            this.baD.lG();
            aMi.aWT().lG(this.baE);
        }
    }
}

