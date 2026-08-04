/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Ta
 */
class ta_1
implements ov_1 {
    final /* synthetic */ yE bML;

    ta_1(yE yE2) {
        this.bML = yE2;
    }

    public boolean a(ke ke2) {
        abd_1 abd_12 = (abd_1)ke2;
        if (yE.d(this.bML)) {
            if (this.bML.cLZ == null) {
                return true;
            }
            if (this.bML.cLZ.aY(abd_12.p(this.bML), abd_12.q(this.bML))) {
                return true;
            }
            on_0 on_02 = yE.e(this.bML).getAppearance();
            if (on_02 == null) {
                return true;
            }
            if (!on_02.aY(abd_12.p(yE.e(this.bML)), abd_12.q(yE.e(this.bML)))) {
                yE.f(this.bML);
                return true;
            }
        }
        return false;
    }
}

