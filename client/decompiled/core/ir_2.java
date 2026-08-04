/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from iR
 */
class ir_2
implements ov_1 {
    final /* synthetic */ int[] yP;
    final /* synthetic */ aod_2 yQ;
    final /* synthetic */ String[] yR;
    final /* synthetic */ String yS;
    final /* synthetic */ JX iS;
    final /* synthetic */ jJ[] iU;
    final /* synthetic */ int yT;
    final /* synthetic */ ajx_2 yU;

    ir_2(ajx_2 ajx_22, int[] nArray, aod_2 aod_22, String[] stringArray, String string, JX jX, jJ[] jJArray, int n2) {
        this.yU = ajx_22;
        this.yP = nArray;
        this.yQ = aod_22;
        this.yR = stringArray;
        this.yS = string;
        this.iS = jX;
        this.iU = jJArray;
        this.yT = n2;
    }

    public boolean a(ke ke2) {
        this.yP[0] = this.yP[0] + 1;
        int n2 = this.yP[0];
        this.yQ.setBubbleText(this.yR[n2]);
        if (n2 == this.yR.length - 1) {
            this.yQ.a(0, (ov_1)this, new agy_1(this));
        }
        return false;
    }
}

