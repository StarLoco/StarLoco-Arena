/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

public class asz
extends sp_1
implements Iterable {
    private Object[] cgI;

    public asz() {
        this(10);
    }

    public asz(int n2) {
        super(n2);
        this.cgI = new Object[n2];
    }

    public boolean cM(int n2) {
        int n3 = this.bLj.length;
        if (!super.cM(n2)) {
            return false;
        }
        Object[] objectArray = this.cgI;
        this.cgI = new Object[n2];
        System.arraycopy(objectArray, 0, this.cgI, 0, n3);
        return true;
    }

    public void put(int n2, Object object) {
        this.ve();
        int n3 = this.hK(n2);
        if (n3 < 0) {
            n3 = -n3 - 1;
        } else {
            ++this.m_size;
            this.bLj[n3] = n2;
        }
        this.cgI[n3] = object;
    }

    public Object remove(int n2) {
        if (this.m_size == 0) {
            return null;
        }
        int n3 = this.hJ(n2);
        if (n3 < 0) {
            return null;
        }
        Object object = this.cgI[n3];
        if (n3 < this.m_size - 1) {
            this.bLj[n3] = this.bLj[this.m_size - 1];
            this.cgI[n3] = this.cgI[this.m_size - 1];
            this.bLj[this.m_size - 1] = 0;
            this.cgI[this.m_size - 1] = null;
        } else {
            this.bLj[n3] = 0;
            this.cgI[n3] = null;
        }
        --this.m_size;
        return object;
    }

    public void clear() {
        super.clear();
        int n2 = this.cgI.length;
        for (int j = 0; j < n2; ++j) {
            this.cgI[j] = null;
        }
    }

    public Object get(int n2) {
        int n3 = this.hJ(n2);
        if (n3 < 0) {
            return null;
        }
        return this.cgI[n3];
    }

    public Object jx(int n2) {
        return this.cgI[n2];
    }

    public Iterator iterator() {
        return new dl_2(this.cgI, false);
    }
}

