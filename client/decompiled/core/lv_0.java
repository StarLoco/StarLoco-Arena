/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from LV
 */
class lv_0
extends apc {
    final /* synthetic */ iN bsJ;
    final /* synthetic */ JX iS;
    final /* synthetic */ qe_1 bsK;
    final /* synthetic */ String yS;
    final /* synthetic */ aab_1 bsL;

    lv_0(aab_1 aab_12, iN iN2, JX jX, qe_1 qe_12, String string) {
        this.bsL = aab_12;
        this.bsJ = iN2;
        this.iS = jX;
        this.bsK = qe_12;
        this.yS = string;
    }

    public boolean a(ke ke2) {
        ayr_0 ayr_02 = (ayr_0)azs_0.aLV().getProperty("sphereboard.selectedSphere").getValue();
        if (ayr_02 != null) {
            this.bsJ.lG();
            aMi.aWT().b(this.iS, "sphereBoardDialog", "sphereBoard", this.bsK.name(), this.yS);
        }
        return false;
    }
}

