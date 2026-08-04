/*
 * Decompiled with CFR 0.152.
 */
import java.util.ConcurrentModificationException;

/*
 * Renamed from hs
 */
abstract class hs_2
extends aiz_1 {
    protected final OI vA;

    public hs_2(OI oI) {
        super(oI);
        this.vA = oI;
    }

    protected final int nextIndex() {
        if (this.cyP != this.vA.size()) {
            throw new ConcurrentModificationException();
        }
        byte[] byArray = this.vA.bCp;
        int n2 = this._index;
        while (n2-- > 0 && byArray[n2] != 1) {
        }
        return n2;
    }
}

