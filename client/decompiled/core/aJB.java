/*
 * Decompiled with CFR 0.152.
 */
class aJB
implements ov_1 {
    final /* synthetic */ YN cHP;

    aJB(YN yN) {
        this.cHP = yN;
    }

    public boolean a(ke ke2) {
        if (ke2.oF() == this.cHP && this.cHP.getTextBuilder().gs()) {
            this.cHP.setCursorType(this.cHP.getTextBuilder().gs() ? xy_0.bYo : xy_0.bYl);
        }
        return false;
    }
}

