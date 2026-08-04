/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aDi
 */
class adi_1
implements ov_1 {
    final /* synthetic */ int[] yP;
    final /* synthetic */ aod_2 yQ;
    final /* synthetic */ String[] yR;
    final /* synthetic */ int yT;
    final /* synthetic */ alx_1 dwN;

    adi_1(alx_1 alx_12, int[] nArray, aod_2 aod_22, String[] stringArray, int n2) {
        this.dwN = alx_12;
        this.yP = nArray;
        this.yQ = aod_22;
        this.yR = stringArray;
        this.yT = n2;
    }

    public boolean a(ke ke2) {
        this.yP[0] = this.yP[0] + 1;
        int n2 = this.yP[0];
        this.yQ.setBubbleText(this.yR[n2]);
        if (n2 == this.yR.length - 1) {
            this.yQ.c(0, rt_0.fN("dialog.terminate"));
            this.yQ.a(0, (ov_1)this, new nn_1(this));
        }
        return false;
    }
}

