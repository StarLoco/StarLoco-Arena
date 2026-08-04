/*
 * Decompiled with CFR 0.152.
 */
import java.util.Enumeration;
import java.util.Iterator;

/*
 * Renamed from aGR
 */
final class agr_1
implements Iterator {
    private final Enumeration dJJ;

    agr_1(Enumeration enumeration) {
        this.dJJ = enumeration;
    }

    public boolean hasNext() {
        return this.dJJ.hasMoreElements();
    }

    public Object next() {
        return this.dJJ.nextElement();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}

