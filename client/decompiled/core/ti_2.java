/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from TI
 */
class ti_2
implements ov_1 {
    final /* synthetic */ int[] yP;
    final /* synthetic */ aod_2 yQ;
    final /* synthetic */ String[] yR;
    final /* synthetic */ String yS;
    final /* synthetic */ JX iS;
    final /* synthetic */ jJ[] iU;
    final /* synthetic */ int yT;
    final /* synthetic */ ft_2 bOH;

    ti_2(ft_2 ft_22, int[] nArray, aod_2 aod_22, String[] stringArray, String string, JX jX, jJ[] jJArray, int n2) {
        this.bOH = ft_22;
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
            this.yQ.a(0, (ov_1)this, new apq_0(this));
        }
        return false;
    }
}

