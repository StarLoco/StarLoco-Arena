/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from oW
 */
class ow_1
implements ov_1 {
    final /* synthetic */ aIg il;

    ow_1(aIg aIg2) {
        this.il = aIg2;
    }

    public boolean a(ke ke2) {
        if (ke2.oF() == aIg.b(this.il)) {
            aIg.d(this.il).setValue(aIg.d(this.il).getValue() + aIg.g(this.il));
        }
        if (ke2.oF() == aIg.c(this.il)) {
            aIg.d(this.il).setValue(aIg.d(this.il).getValue() - aIg.g(this.il));
        }
        return false;
    }
}

