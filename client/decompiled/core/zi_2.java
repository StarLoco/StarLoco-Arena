/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from zi
 */
class zi_2
implements yx_0 {
    final /* synthetic */ afm_2 aEP;
    final /* synthetic */ yf_0 aEQ;

    zi_2(yf_0 yf_02, afm_2 afm_22) {
        this.aEQ = yf_02;
        this.aEP = afm_22;
    }

    public void a(aPk aPk2, d_0 d_02) {
        switch (d_02) {
            case f: {
                aji_1 aji_12 = this.aEQ.getElementMap();
                adg_2 adg_22 = (adg_2)aji_12.R("image");
                if (adg_22 != null) {
                    adg_22.getAppearance().setModulationColor(vP.atL);
                }
                if ((adg_22 = (adg_2)aji_12.R("container")) != null) {
                    adg_22.getAppearance().setModulationColor(vP.atL);
                }
                if ((adg_22 = (adg_2)aji_12.R("text")) != null) {
                    adg_22.getAppearance().setModulationColor(vP.atL);
                }
                this.aEQ.cleanUp();
                this.aEP.b(this);
                return;
            }
        }
    }
}

