/*
 * Decompiled with CFR 0.152.
 */
class Fq
implements ov_1 {
    final /* synthetic */ li_1 aCU;

    Fq(li_1 li_12) {
        this.aCU = li_12;
    }

    public boolean a(ke ke2) {
        qa_1 qa_12 = (qa_1)ke2.oE();
        if (qa_12.getItemValue() != null) {
            rf_0.b(this.aCU.aaC, qa_12);
            if (rf_0.w(this.aCU.aaC) != null) {
                rf_0.w(this.aCU.aaC).a(rf_0.x(this.aCU.aaC).getPosition(), rf_0.x(this.aCU.aaC).getSize(), this.aCU.aaC.cLZ.getTotalInsets());
                this.aCU.aaC.setNeedsToResetMeshes();
            }
        }
        return false;
    }
}

