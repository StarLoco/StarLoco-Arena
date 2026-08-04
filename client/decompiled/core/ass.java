/*
 * Decompiled with CFR 0.152.
 */
class ass
implements ov_1 {
    final /* synthetic */ oi_1 cRY;

    ass(oi_1 oi_12) {
        this.cRY = oi_12;
    }

    public boolean a(ke ke2) {
        qa_1 qa_12 = (qa_1)ke2.oE();
        if (qa_12.getItemValue() != null) {
            rf_0.b(this.cRY.aaC, qa_12);
            if (rf_0.w(this.cRY.aaC) != null) {
                rf_0.w(this.cRY.aaC).a(rf_0.x(this.cRY.aaC).getPosition(), rf_0.x(this.cRY.aaC).getSize(), this.cRY.aaC.cLZ.getTotalInsets());
                this.cRY.aaC.setNeedsToResetMeshes();
            }
        }
        return false;
    }
}

