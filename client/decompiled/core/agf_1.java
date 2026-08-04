/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from agF
 */
class agf_1
implements ov_1 {
    final /* synthetic */ atg_0 kF;

    agf_1(atg_0 atg_02) {
        this.kF = atg_02;
    }

    public boolean a(ke ke2) {
        if (!atg_0.k(this.kF) && ke2.oF() == ke2.oE()) {
            float f;
            abd_1 abd_12 = (abd_1)ke2;
            float f2 = f = atg_0.l(this.kF) ? 1.0f / (float)atg_0.c(this.kF) + 0.001f : atg_0.m(this.kF);
            if (atg_0.n(this.kF) && abd_12.p((adg_2)abd_12.oF()) < atg_0.g(this.kF).getX() || !atg_0.n(this.kF) && abd_12.q((adg_2)abd_12.oF()) < atg_0.g(this.kF).getY()) {
                this.kF.setValue(atg_0.b(this.kF, atg_0.a(this.kF, atg_0.o(this.kF)) - f));
            } else {
                this.kF.setValue(atg_0.b(this.kF, atg_0.a(this.kF, atg_0.o(this.kF)) + f));
            }
        }
        return false;
    }
}

