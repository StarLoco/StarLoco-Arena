/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from agl
 */
class agl_1
implements ov_1 {
    final /* synthetic */ akz_2 ctL;

    agl_1(akz_2 akz_22) {
        this.ctL = akz_22;
    }

    public boolean a(ke ke2) {
        if (akz_2.c(this.ctL) == akz_2.b(this.ctL)) {
            this.ctL.setValue(akz_2.d(this.ctL));
        } else {
            this.ctL.setValue(akz_2.c(this.ctL) - 1);
        }
        return true;
    }
}

