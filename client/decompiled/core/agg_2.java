/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from agG
 */
class agg_2
implements ov_1 {
    final /* synthetic */ atg_0 kF;

    agg_2(atg_0 atg_02) {
        this.kF = atg_02;
    }

    public boolean a(ke ke2) {
        if (atg_0.k(this.kF) || ke2.oF() != atg_0.g(this.kF)) {
            return false;
        }
        abd_1 abd_12 = (abd_1)ke2;
        if (atg_0.n(this.kF)) {
            int n2 = abd_12.p((adg_2)abd_12.oE());
            float f = (int)Math.floor((double)atg_0.g(this.kF).getWidth() / 2.0);
            float f2 = (int)Math.ceil((double)atg_0.g(this.kF).getWidth() / 2.0);
            if ((float)n2 < f) {
                n2 = (int)f;
            } else if ((float)n2 > (float)this.kF.aLd.width - f2) {
                n2 = this.kF.aLd.width - (int)f2;
            }
            float f3 = ((float)n2 - f) / ((float)this.kF.aLd.width - (float)atg_0.g(this.kF).getWidth());
            this.kF.setValue(atg_0.b(this.kF, f3));
        } else {
            int n3 = abd_12.q((adg_2)abd_12.oE());
            float f = (float)Math.floor((double)atg_0.g(this.kF).getHeight() / 2.0);
            float f4 = (float)Math.ceil((double)atg_0.g(this.kF).getHeight() / 2.0);
            if ((float)n3 < f) {
                n3 = (int)f;
            } else if ((float)n3 > (float)this.kF.aLd.height - f4) {
                n3 = this.kF.aLd.height - (int)f4;
            }
            float f5 = ((float)n3 - f) / ((float)this.kF.aLd.height - (float)atg_0.g(this.kF).getHeight());
            this.kF.setValue(atg_0.b(this.kF, f5));
        }
        return true;
    }
}

