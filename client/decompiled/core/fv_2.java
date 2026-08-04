/*
 * Decompiled with CFR 0.152.
 */
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/*
 * Renamed from Fv
 */
abstract class fv_2
extends aiz_1
implements Iterator {
    private final adf_2 aUO;

    public fv_2(adf_2 adf_22) {
        super(adf_22);
        this.aUO = adf_22;
    }

    public Object next() {
        this.ays();
        return this.fB(this._index);
    }

    protected final int nextIndex() {
        if (this.cyP != this.cyO.size()) {
            throw new ConcurrentModificationException();
        }
        Object[] objectArray = this.aUO.dxM;
        int n2 = this._index;
        while (n2-- > 0 && (objectArray[n2] == adf_2.dxP || objectArray[n2] == adf_2.dxO)) {
        }
        return n2;
    }

    protected abstract Object fB(int var1);
}

