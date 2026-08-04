/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aFW
 */
class afw_1
implements ov_1 {
    final /* synthetic */ rf_0 aaC;

    afw_1(rf_0 rf_02) {
        this.aaC = rf_02;
    }

    public boolean a(ke ke2) {
        Kf kf = (Kf)ke2;
        atg_0 atg_02 = rf_0.b(this.aaC).getSlider();
        if (kf.oE() == atg_02 || kf.oF() == atg_02) {
            this.aaC.setListOffset(rf_0.a(this.aaC, kf.getValue()));
        }
        return false;
    }
}

