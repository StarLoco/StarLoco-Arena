/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Am
 */
class am_0
implements ov_1 {
    final /* synthetic */ afQ aFM;

    am_0(afQ afQ2) {
        this.aFM = afQ2;
    }

    public boolean a(ke ke2) {
        abd_1 abd_12 = (abd_1)ke2;
        if (afQ.h(this.aFM)) {
            afQ.c(this.aFM).setValue(afQ.c(this.aFM).getValue() - afQ.c(this.aFM).getButtonJump() * (float)abd_12.aNb());
        }
        return true;
    }
}

