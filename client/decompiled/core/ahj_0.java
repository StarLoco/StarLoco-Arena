/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

/*
 * Renamed from aHj
 */
public abstract class ahj_0
implements Iterator {
    protected final Iterator dLW;

    public ahj_0(Iterator iterator) {
        this.dLW = iterator;
    }

    public boolean hasNext() {
        return this.dLW.hasNext();
    }

    public Object next() {
        return this.dLW.next();
    }

    public void remove() {
        this.dLW.remove();
    }
}

