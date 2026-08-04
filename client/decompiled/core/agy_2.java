/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from agy
 */
class agy_2
implements ov_1 {
    final /* synthetic */ aqq_0 aLv;

    agy_2(aqq_0 aqq_02) {
        this.aLv = aqq_02;
    }

    public boolean a(ke ke2) {
        aqG aqG2 = (aqG)ke2;
        if (aqG2.getKeyCode() == 10) {
            this.aLv.aDY();
            ago_2.getInstance().setKeyEventConsumed(true);
        }
        return false;
    }
}

