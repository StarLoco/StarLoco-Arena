/*
 * Decompiled with CFR 0.152.
 */
import java.util.ListIterator;

/*
 * Renamed from gV
 */
public abstract class gv_1
implements ListIterator {
    protected final ListIterator uV;

    public gv_1(ListIterator listIterator) {
        this.uV = listIterator;
    }

    public boolean hasNext() {
        return this.uV.hasNext();
    }

    public Object next() {
        return this.uV.next();
    }

    public boolean hasPrevious() {
        return this.uV.hasPrevious();
    }

    public Object previous() {
        return this.uV.previous();
    }

    public int nextIndex() {
        return this.uV.nextIndex();
    }

    public int previousIndex() {
        return this.uV.previousIndex();
    }

    public void remove() {
        this.uV.remove();
    }

    public void set(Object object) {
        this.uV.set(object);
    }

    public void add(Object object) {
        this.uV.add(object);
    }
}

