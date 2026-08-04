/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Array;
import java.util.Iterator;

public class acy
implements Iterable {
    private Object[] cku = (Object[])Array.newInstance(Object.class, 0);

    public void add(Object object) {
        if (object == null) {
            return;
        }
        int n2 = this.cku.length;
        Object[] objectArray = (Object[])Array.newInstance(Object.class, n2 + 1);
        System.arraycopy(this.cku, 0, objectArray, 0, n2);
        objectArray[n2] = object;
        this.cku = objectArray;
    }

    public void add(Object[] objectArray) {
        if (objectArray == null || objectArray.length == 0) {
            return;
        }
        int n2 = this.cku.length;
        Object[] objectArray2 = (Object[])Array.newInstance(Object.class, n2 + objectArray.length);
        if (n2 > 0) {
            System.arraycopy(this.cku, 0, objectArray2, 0, n2);
        }
        System.arraycopy(objectArray, 0, objectArray2, n2, objectArray.length);
        this.cku = objectArray2;
    }

    public void g(Object[] objectArray) {
        if (objectArray == null) {
            this.cku = (Object[])Array.newInstance(Object.class, 0);
            return;
        }
        this.cku = objectArray;
    }

    public void set(int n2, Object object) {
        if (n2 < 0) {
            return;
        }
        if (n2 >= this.cku.length) {
            Object[] objectArray = (Object[])Array.newInstance(Object.class, n2 + 1);
            System.arraycopy(this.cku, 0, objectArray, 0, this.cku.length);
            this.cku = objectArray;
        }
        this.cku[n2] = object;
    }

    public Object get(int n2) {
        if (n2 < 0 || n2 >= this.cku.length) {
            return null;
        }
        return this.cku[n2];
    }

    public int size() {
        return this.cku.length;
    }

    public void clear() {
        this.cku = (Object[])Array.newInstance(Object.class, 0);
    }

    public Iterator iterator() {
        return new dl_2(this.cku, false);
    }

    public Object[] arm() {
        return this.cku;
    }
}

