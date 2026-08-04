/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from DA
 */
class da_2
implements ov_1 {
    final /* synthetic */ ud_1 Dg;

    da_2(ud_1 ud_12) {
        this.Dg = ud_12;
    }

    public boolean a(ke ke2) {
        if (this.Dg.OD) {
            int n2 = ud_1.b(this.Dg);
            ud_1.a(this.Dg, Math.min(ud_1.c(this.Dg).size() - 1, ud_1.b(this.Dg) + 1));
            if (ud_1.b(this.Dg) != n2) {
                ud_1.a(this.Dg, true);
                this.Dg.setNeedsToPreProcess();
            }
        }
        return false;
    }
}

