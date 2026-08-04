/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Dx
 */
class dx_0
implements ov_1 {
    final /* synthetic */ ud_1 Dg;

    dx_0(ud_1 ud_12) {
        this.Dg = ud_12;
    }

    public boolean a(ke ke2) {
        int n2 = ud_1.b(this.Dg);
        ud_1.a(this.Dg, Math.max(0, ud_1.b(this.Dg) - 1));
        if (ud_1.b(this.Dg) != n2) {
            ud_1.a(this.Dg, true);
            this.Dg.setNeedsToPreProcess();
        }
        return false;
    }
}

