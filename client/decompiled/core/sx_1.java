/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from SX
 */
class sx_1
implements ov_1 {
    final /* synthetic */ yE bML;

    sx_1(yE yE2) {
        this.bML = yE2;
    }

    public boolean a(ke ke2) {
        abd_1 abd_12 = (abd_1)ke2;
        if (!yE.g(this.bML) && yE.d(this.bML)) {
            aIg aIg2 = yE.e(this.bML).getScrollBar();
            Zb zb = aIg2.getAppearance();
            if (zb == null) {
                return true;
            }
            if (!zb.aY(abd_12.p(aIg2), abd_12.q(aIg2))) {
                yE.f(this.bML);
                return true;
            }
        }
        if (yE.g(this.bML)) {
            yE.a(this.bML, false);
        }
        return false;
    }
}

