/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from agi
 */
class agi_2
implements ov_1 {
    final /* synthetic */ akz_2 ctL;

    agi_2(akz_2 akz_22) {
        this.ctL = akz_22;
    }

    public boolean a(ke ke2) {
        if (akz_2.c(this.ctL) == akz_2.d(this.ctL)) {
            this.ctL.setValue(akz_2.b(this.ctL));
        } else {
            this.ctL.setValue(akz_2.c(this.ctL) + 1);
        }
        return true;
    }
}

