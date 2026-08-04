/*
 * Decompiled with CFR 0.152.
 */
import java.util.ListIterator;

/*
 * Renamed from aOv
 */
public class aov_1
extends gv_1 {
    public aov_1(ListIterator listIterator) {
        super(listIterator);
    }

    public boolean hasNext() {
        return super.hasPrevious();
    }

    public boolean hasPrevious() {
        return super.hasNext();
    }

    public Object next() {
        return super.previous();
    }

    public Object previous() {
        return super.next();
    }

    public int nextIndex() {
        throw new UnsupportedOperationException();
    }

    public int previousIndex() {
        throw new UnsupportedOperationException();
    }
}

