/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Renamed from Fg
 */
class fg_1
implements Iterator {
    private boolean aUu;
    private final iv_1 aUv;

    fg_1(iv_1 iv_12) {
        this.aUv = iv_12;
        this.aUu = false;
    }

    public boolean hasNext() {
        return !this.aUu;
    }

    public Object next() {
        if (this.aUu) {
            throw new NoSuchElementException();
        }
        this.aUu = true;
        return this.aUv;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}

