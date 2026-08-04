/*
 * Decompiled with CFR 0.152.
 */
final class aCV
implements nm_1 {
    private int hN = 0;
    final /* synthetic */ ano_0 bPX;

    private aCV(ano_0 ano_02) {
        this.bPX = ano_02;
    }

    public int dY() {
        return this.hN;
    }

    public final boolean i(Object object, Object object2) {
        this.hN += this.bPX.dxN.aG(object) ^ (object2 == null ? 0 : object2.hashCode());
        return true;
    }

    /* synthetic */ aCV(ano_0 ano_02, ann_1 ann_12) {
        this(ano_02);
    }
}

