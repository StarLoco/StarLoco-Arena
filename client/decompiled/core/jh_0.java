/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from JH
 */
class jh_0
implements ov_1 {
    final /* synthetic */ ana_0 yj;

    jh_0(ana_0 ana_02) {
        this.yj = ana_02;
    }

    public boolean a(ke ke2) {
        ana_0.a(this.yj, null);
        if (ana_0.c(this.yj) && ana_0.d(this.yj) != Integer.MIN_VALUE && ana_0.e(this.yj) != Integer.MIN_VALUE) {
            int n2 = this.yj.cwV + ana_0.d(this.yj) * 20;
            int n3 = this.yj.cwW + ana_0.e(this.yj) * 20;
            this.yj.a(new iq(this.yj, n2, n3, 1000));
            ana_0.a(this.yj, Integer.MIN_VALUE);
            ana_0.b(this.yj, Integer.MIN_VALUE);
        }
        return false;
    }
}

