/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aJx
 */
class ajx_0
implements ov_1 {
    final /* synthetic */ YN cHP;

    ajx_0(YN yN) {
        this.cHP = yN;
    }

    public boolean a(ke ke2) {
        if (ke2.oF() != this.cHP) {
            return false;
        }
        aFH aFH2 = YN.a(this.cHP);
        if (aFH2 != null && aFH2.aRY() == nf_2.NN && ((wC)aFH2).CZ() != null && ((wC)aFH2).CZ().getId() != null) {
            this.cHP.setCursorType(xy_0.bYt);
        } else {
            this.cHP.setCursorType(xy_0.bYl);
        }
        return false;
    }
}

