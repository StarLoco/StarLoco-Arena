/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from tK
 */
public abstract class tk_1
extends aL {
    protected int anZ = 0;

    protected void dJ(int n2) {
        this.anZ = n2;
        for (int j = this.do.size() - 1; j >= 0; --j) {
            aag_1 aag_12 = (aag_1)this.do.jx(j);
            aag_12.eF(this.dK(aag_12.zU()));
        }
    }

    protected final boolean dK(int n2) {
        return qi_1.vV().R(this.anZ, n2);
    }
}

