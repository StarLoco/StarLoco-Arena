/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Ge
 */
class ge_2
implements zh_0 {
    private final iN baD;
    final /* synthetic */ JX iS;
    final /* synthetic */ String yS;
    final /* synthetic */ jJ[] iU;
    final /* synthetic */ String baE;
    final /* synthetic */ aaL baF;

    ge_2(aaL aaL2, JX jX, String string, jJ[] jJArray, String string2) {
        this.baF = aaL2;
        this.iS = jX;
        this.yS = string;
        this.iU = jJArray;
        this.baE = string2;
        this.baD = new iN(this.iS, this.yS, this.iU);
    }

    public void cZ(String string) {
        if (string.equals(this.baE)) {
            this.baD.lG();
            aMi.aWT().lG(this.baE);
        }
    }
}

