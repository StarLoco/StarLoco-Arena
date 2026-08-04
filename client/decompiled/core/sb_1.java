/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from sb
 */
class sb_1
implements ov_1 {
    final /* synthetic */ cb_0 aiO;

    sb_1(cb_0 cb_02) {
        this.aiO = cb_02;
    }

    public boolean a(ke ke2) {
        this.aiO.getAppearance().abR();
        if (ke2.oH()) {
            return false;
        }
        if (this.aiO.getAppearance().isChecked()) {
            aek.atD().atI();
        } else {
            aek.atD().atJ();
        }
        ke2.X(true);
        return false;
    }
}

