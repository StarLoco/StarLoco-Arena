/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Renamed from aeG
 */
class aeg_2
implements Iterator {
    private Iterator cpi;
    private boolean cpj;
    final /* synthetic */ alh_1 bzc;

    aeg_2(alh_1 alh_12, Iterable iterable) {
        this.bzc = alh_12;
        this.cpi = iterable.iterator();
    }

    public boolean hasNext() {
        boolean bl2;
        boolean bl3 = bl2 = !this.cpj && this.cpi.hasNext();
        if (!bl2) {
            this.bzc.cEX = null;
        }
        return bl2;
    }

    public Object next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return this.cpi.next();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public void stop() {
        this.cpj = true;
        this.cpi = null;
    }
}

