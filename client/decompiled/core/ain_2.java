/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Array;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/*
 * Renamed from ain
 */
abstract class ain_2
extends AbstractSet
implements Iterable,
Set {
    final /* synthetic */ ano_0 bPX;

    private ain_2(ano_0 ano_02) {
        this.bPX = ano_02;
    }

    public abstract Iterator iterator();

    public abstract boolean removeElement(Object var1);

    public abstract boolean aq(Object var1);

    public boolean contains(Object object) {
        return this.aq(object);
    }

    public boolean remove(Object object) {
        return this.removeElement(object);
    }

    public boolean containsAll(Collection collection) {
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (this.contains(iterator.next())) continue;
            return false;
        }
        return true;
    }

    public void clear() {
        this.bPX.clear();
    }

    public boolean add(Object object) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        return this.bPX.size();
    }

    public Object[] toArray() {
        Object[] objectArray = new Object[this.size()];
        Iterator iterator = this.iterator();
        int n2 = 0;
        while (iterator.hasNext()) {
            objectArray[n2] = iterator.next();
            ++n2;
        }
        return objectArray;
    }

    public Object[] toArray(Object[] objectArray) {
        int n2 = this.size();
        if (objectArray.length < n2) {
            objectArray = (Object[])Array.newInstance(objectArray.getClass().getComponentType(), n2);
        }
        Iterator iterator = this.iterator();
        Object[] objectArray2 = objectArray;
        for (int j = 0; j < n2; ++j) {
            objectArray2[j] = iterator.next();
        }
        if (objectArray.length > n2) {
            objectArray[n2] = null;
        }
        return objectArray;
    }

    public boolean isEmpty() {
        return this.bPX.isEmpty();
    }

    public boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection collection) {
        boolean bl2 = false;
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (collection.contains(iterator.next())) continue;
            iterator.remove();
            bl2 = true;
        }
        return bl2;
    }

    /* synthetic */ ain_2(ano_0 ano_02, ann_1 ann_12) {
        this(ano_02);
    }
}

