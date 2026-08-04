/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

class Pf
implements Iterator {
    private Iterator bDl;
    private final yF bDm;

    Pf(yF yF2) {
        this.bDm = yF2;
        this.bDl = yF.a(this.bDm).iterator();
    }

    public Object next() {
        if (this.bDl != null) {
            if (this.bDl.hasNext()) {
                return this.bDl.next();
            }
            this.bDl = null;
        }
        Object e = yF.b(this.bDm).next();
        yF.a(this.bDm).add(e);
        return e;
    }

    public boolean hasNext() {
        return this.bDl != null && this.bDl.hasNext() || yF.b(this.bDm).hasNext();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}

