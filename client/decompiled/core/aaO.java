/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

public class aaO
extends qg_1
implements Iterable {
    private Object[] cgI;

    public aaO() {
        this(10);
    }

    public aaO(int n2) {
        super(n2);
        this.cgI = new Object[n2];
    }

    public boolean cM(int n2) {
        int n3 = this.adv.length;
        if (!super.cM(n2)) {
            return false;
        }
        Object[] objectArray = this.cgI;
        this.cgI = new Object[n2];
        System.arraycopy(objectArray, 0, this.cgI, 0, n3);
        return true;
    }

    public void c(long l2, Object object) {
        this.ve();
        int n2 = this.aA(l2);
        if (n2 < 0) {
            n2 = -n2 - 1;
        } else {
            ++this.m_size;
            this.adv[n2] = l2;
        }
        this.cgI[n2] = object;
    }

    public Object u(long l2) {
        if (this.m_size == 0) {
            return null;
        }
        int n2 = this.az(l2);
        if (n2 < 0) {
            return null;
        }
        Object object = this.cgI[n2];
        if (n2 < this.m_size - 1) {
            this.adv[n2] = this.adv[this.m_size - 1];
            this.cgI[n2] = this.cgI[this.m_size - 1];
            this.adv[this.m_size - 1] = 0L;
            this.cgI[this.m_size - 1] = null;
        } else {
            this.adv[n2] = 0L;
            this.cgI[n2] = null;
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

    public Object t(long l2) {
        int n2 = this.az(l2);
        if (n2 < 0) {
            return null;
        }
        return this.cgI[n2];
    }

    public Object jx(int n2) {
        return this.cgI[n2];
    }

    public Iterator iterator() {
        return new dl_2(this.cgI, false);
    }
}

