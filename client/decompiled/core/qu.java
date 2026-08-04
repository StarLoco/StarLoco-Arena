/*
 * Decompiled with CFR 0.152.
 */
class qu
implements ov_1 {
    final /* synthetic */ ej_1 adU;

    qu(ej_1 ej_12) {
        this.adU = ej_12;
    }

    public boolean a(ke ke2) {
        qa_1 qa_12 = (qa_1)ke2.oE();
        if (qa_12.getItemValue() != null) {
            aaz_2.b(this.adU.aTO, qa_12);
            if (aaz_2.o(this.adU.aTO) != null) {
                aaz_2.o(this.adU.aTO).f(0, aaz_2.p(this.adU.aTO).getY(), this.adU.aTO.cLZ.getContentWidth() - aaz_2.f(this.adU.aTO).getWidth(), aaz_2.p(this.adU.aTO).getHeight(), this.adU.aTO.cLZ.getTopInset(), this.adU.aTO.cLZ.getBottomInset(), this.adU.aTO.cLZ.getLeftInset(), this.adU.aTO.cLZ.getRightInset());
                this.adU.aTO.setNeedsToResetMeshes();
            }
            if (aaz_2.q(this.adU.aTO) != null) {
                aaz_2.q(this.adU.aTO).a(aaz_2.p(this.adU.aTO).getPosition(), aaz_2.p(this.adU.aTO).getSize(), this.adU.aTO.cLZ.getTotalInsets());
            }
        }
        return false;
    }
}

