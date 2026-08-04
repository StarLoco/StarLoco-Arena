/*
 * Decompiled with CFR 0.152.
 */
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.WeakHashMap;

/*
 * Renamed from Sw
 */
class sw_0
implements Iterator {
    private static final WeakHashMap bLw = new WeakHashMap();
    private Object parent;
    private Iterator bLx;

    static synchronized void ao(Object object) {
        Set set = (Set)bLw.get(object);
        if (set != null) {
            set.clear();
        }
    }

    private static synchronized void a(sw_0 sw_02) {
        HashSet<sw_0> hashSet = (HashSet<sw_0>)bLw.get(sw_02.parent);
        if (hashSet == null) {
            hashSet = new HashSet<sw_0>();
            bLw.put(sw_02.parent, hashSet);
        }
        hashSet.add(sw_02);
    }

    private static synchronized void b(sw_0 sw_02) {
        Set set = (Set)bLw.get(sw_02.parent);
        if (set != null) {
            set.remove(sw_02);
        }
    }

    private static synchronized void c(sw_0 sw_02) {
        Set set = (Set)bLw.get(sw_02.parent);
        if (!set.contains(sw_02)) {
            throw new ConcurrentModificationException();
        }
    }

    sw_0(Object object, Iterator iterator) {
        if (object == null) {
            throw new IllegalArgumentException("parent object is null");
        }
        if (iterator == null) {
            throw new IllegalArgumentException("cannot wrap null iterator");
        }
        this.parent = object;
        if (iterator.hasNext()) {
            this.bLx = iterator;
            sw_0.a(this);
        }
    }

    public boolean hasNext() {
        if (this.bLx == null) {
            return false;
        }
        sw_0.c(this);
        return this.bLx.hasNext();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object next() {
        Object e;
        block3: {
            if (this.bLx == null || !this.bLx.hasNext()) {
                throw new NoSuchElementException();
            }
            sw_0.c(this);
            try {
                e = this.bLx.next();
                Object var3_2 = null;
                if (this.bLx.hasNext()) break block3;
                this.bLx = null;
            }
            catch (Throwable throwable) {
                block4: {
                    Object var3_3 = null;
                    if (this.bLx.hasNext()) break block4;
                    this.bLx = null;
                    sw_0.b(this);
                }
                throw throwable;
            }
            sw_0.b(this);
        }
        return e;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}

