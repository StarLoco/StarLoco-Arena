/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from apT
 */
class apt_0
implements apx {
    final /* synthetic */ float cNJ;
    final /* synthetic */ long cNK;
    final /* synthetic */ ahn_0 cNL;

    apt_0(ahn_0 ahn_02, float f, long l2) {
        this.cNL = ahn_02;
        this.cNJ = f;
        this.cNK = l2;
    }

    public boolean a(lP lP2) {
        if (lP2.aXA()) {
            lP2.V(this.cNJ < 0.0f ? -this.cNJ : 0.0f);
        }
        lP2.ay(this.cNK);
        if (lP2.isShutdown()) {
            ahn_0.a(this.cNL).add(lP2);
        }
        return true;
    }
}

