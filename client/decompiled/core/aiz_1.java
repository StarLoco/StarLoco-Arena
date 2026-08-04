/*
 * Decompiled with CFR 0.152.
 */
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/*
 * Renamed from aiz
 */
abstract class aiz_1 {
    protected final kB cyO;
    protected int cyP;
    protected int _index;

    public aiz_1(kB kB2) {
        this.cyO = kB2;
        this.cyP = this.cyO.size();
        this._index = this.cyO.capacity();
    }

    public boolean hasNext() {
        return this.nextIndex() >= 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void remove() {
        if (this.cyP != this.cyO.size()) {
            throw new ConcurrentModificationException();
        }
        try {
            this.cyO.pf();
            this.cyO.O(this._index);
        }
        finally {
            this.cyO.Y(false);
        }
        --this.cyP;
    }

    protected final void ays() {
        this._index = this.nextIndex();
        if (this._index < 0) {
            throw new NoSuchElementException();
        }
    }

    protected abstract int nextIndex();
}

