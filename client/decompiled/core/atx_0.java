/*
 * Decompiled with CFR 0.152.
 */
import java.util.ConcurrentModificationException;

/*
 * Renamed from atX
 */
public class atx_0
extends aiz_1 {
    private final sa_1 cVv;

    public atx_0(sa_1 sa_12) {
        super(sa_12);
        this.cVv = sa_12;
    }

    protected final int nextIndex() {
        if (this.cyP != this.cyO.size()) {
            throw new ConcurrentModificationException();
        }
        Object[] objectArray = this.cVv.dxM;
        int n2 = this._index;
        while (n2-- > 0 && (objectArray[n2] == null || objectArray[n2] == adf_2.dxO || objectArray[n2] == adf_2.dxP)) {
        }
        return n2;
    }

    public void fK() {
        this.ays();
    }

    public Object awg() {
        return this.cVv.dxM[this._index];
    }

    public int value() {
        return this.cVv.aiN[this._index];
    }

    public int aR(int n2) {
        int n3 = this.value();
        this.cVv.aiN[this._index] = n2;
        return n3;
    }
}

