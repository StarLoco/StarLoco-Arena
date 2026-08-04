/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from SY
 */
class sy_2
implements ov_1 {
    final /* synthetic */ yE bML;

    sy_2(yE yE2) {
        this.bML = yE2;
    }

    public boolean a(ke ke2) {
        hf_0 hf_02 = (hf_0)ke2;
        if (hf_02.getSelected()) {
            this.bML.setSelectedValue(hf_02.getValue());
            yE.f(this.bML);
        }
        hf_0 hf_03 = new hf_0(this.bML, hf_02.kC(), hf_02.getValue(), hf_02.getSelected());
        this.bML.f(hf_03);
        return false;
    }
}

