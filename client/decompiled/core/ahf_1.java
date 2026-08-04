/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/*
 * Renamed from ahf
 */
public class ahf_1
extends adf_2
implements Externalizable,
Iterable,
Set {
    static final long serialVersionUID = 1L;

    public ahf_1() {
    }

    public ahf_1(acw_2 acw_22) {
        super(acw_22);
    }

    public ahf_1(int n2) {
        super(n2);
    }

    public ahf_1(int n2, acw_2 acw_22) {
        super(n2, acw_22);
    }

    public ahf_1(int n2, float f) {
        super(n2, f);
    }

    public ahf_1(int n2, float f, acw_2 acw_22) {
        super(n2, f, acw_22);
    }

    public ahf_1(Collection collection) {
        this(collection.size());
        this.addAll(collection);
    }

    public ahf_1(Collection collection, acw_2 acw_22) {
        this(collection.size(), acw_22);
        this.addAll(collection);
    }

    public boolean add(Object object) {
        int n2 = this.aH(object);
        if (n2 < 0) {
            return false;
        }
        Object object2 = this.dxM[n2];
        this.dxM[n2] = object;
        this.Z(object2 == dxP);
        return true;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Set)) {
            return false;
        }
        Set set = (Set)object;
        if (set.size() != this.size()) {
            return false;
        }
        return this.containsAll((Collection)set);
    }

    public int hashCode() {
        iz iz2 = new iz(this, null);
        this.f(iz2);
        return iz2.dY();
    }

    protected void rehash(int n2) {
        int n3 = this.dxM.length;
        Object[] objectArray = this.dxM;
        this.dxM = new Object[n2];
        Arrays.fill(this.dxM, dxP);
        int n4 = n3;
        while (n4-- > 0) {
            if (objectArray[n4] == dxP || objectArray[n4] == dxO) continue;
            Object object = objectArray[n4];
            int n5 = this.aH(object);
            if (n5 < 0) {
                this.l(this.dxM[-n5 - 1], object);
            }
            this.dxM[n5] = object;
        }
    }

    public Object[] toArray() {
        Object[] objectArray = new Object[this.size()];
        this.f(new anq_2(objectArray));
        return objectArray;
    }

    public Object[] toArray(Object[] objectArray) {
        int n2 = this.size();
        if (objectArray.length < n2) {
            objectArray = (Object[])Array.newInstance(objectArray.getClass().getComponentType(), n2);
        }
        this.f(new anq_2(objectArray));
        if (objectArray.length > n2) {
            objectArray[n2] = null;
        }
        return objectArray;
    }

    public void clear() {
        super.clear();
        Object[] objectArray = this.dxM;
        int n2 = objectArray.length;
        while (n2-- > 0) {
            objectArray[n2] = dxP;
        }
    }

    public boolean remove(Object object) {
        int n2 = this.index(object);
        if (n2 >= 0) {
            this.O(n2);
            return true;
        }
        return false;
    }

    public Iterator iterator() {
        return new ami_1(this);
    }

    public boolean containsAll(Collection collection) {
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (this.contains(iterator.next())) continue;
            return false;
        }
        return true;
    }

    public boolean addAll(Collection collection) {
        boolean bl2 = false;
        int n2 = collection.size();
        this.ensureCapacity(n2);
        Iterator iterator = collection.iterator();
        while (n2-- > 0) {
            if (!this.add(iterator.next())) continue;
            bl2 = true;
        }
        return bl2;
    }

    public boolean removeAll(Collection collection) {
        boolean bl2 = false;
        int n2 = collection.size();
        Iterator iterator = collection.iterator();
        while (n2-- > 0) {
            if (!this.remove(iterator.next())) continue;
            bl2 = true;
        }
        return bl2;
    }

    public boolean retainAll(Collection collection) {
        boolean bl2 = false;
        int n2 = this.size();
        Iterator iterator = this.iterator();
        while (n2-- > 0) {
            if (collection.contains(iterator.next())) continue;
            iterator.remove();
            bl2 = true;
        }
        return bl2;
    }

    public void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeByte(0);
        objectOutput.writeInt(this._size);
        atr atr2 = new atr(objectOutput);
        if (!this.f(atr2)) {
            throw atr2.cTR;
        }
    }

    public void readExternal(ObjectInput objectInput) {
        objectInput.readByte();
        int n2 = objectInput.readInt();
        this.N(n2);
        while (n2-- > 0) {
            Object object = objectInput.readObject();
            this.add(object);
        }
    }
}

