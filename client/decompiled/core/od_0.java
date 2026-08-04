/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from od
 */
class od_0
implements ov_1 {
    final /* synthetic */ int[] yP;
    final /* synthetic */ aac_2 Qu;

    od_0(aac_2 aac_22, int[] nArray) {
        this.Qu = aac_22;
        this.yP = nArray;
    }

    public boolean a(ke ke2) {
        this.yP[0] = this.yP[0] + 1;
        int n2 = this.yP[0];
        do_1.nd.setText(aon_0.aYc().a(29, aac_2.a(this.Qu)[n2], new Object[0]));
        if (n2 == aac_2.a(this.Qu).length - 1) {
            do_1.nd.a(0, (ov_1)this, new ahd(this));
        }
        return false;
    }
}

