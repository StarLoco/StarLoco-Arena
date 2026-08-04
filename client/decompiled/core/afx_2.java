/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aFX
 */
class afx_2
implements apx {
    final /* synthetic */ ss_0 aVv;

    afx_2(ss_0 ss_02) {
        this.aVv = ss_02;
    }

    public boolean b(FO fO) {
        fO.Pk();
        if (fO.isActive()) {
            ss_0.a(this.aVv).add(FO.a(fO));
        }
        fO.clear();
        return true;
    }
}

