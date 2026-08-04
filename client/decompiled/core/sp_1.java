/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Sp
 */
public abstract class sp_1 {
    protected int[] bLj;
    protected int m_size;

    public sp_1(int n2) {
        this.bLj = new int[n2];
        this.m_size = 0;
    }

    public boolean cM(int n2) {
        if (n2 > this.bLj.length) {
            int n3 = this.bLj.length;
            int[] nArray = this.bLj;
            this.bLj = new int[n2];
            System.arraycopy(nArray, 0, this.bLj, 0, n3);
            return true;
        }
        return false;
    }

    protected int hJ(int n2) {
        for (int j = this.m_size - 1; j >= 0; --j) {
            if (n2 != this.bLj[j]) continue;
            return j;
        }
        return -1;
    }

    protected int hK(int n2) {
        for (int j = this.m_size - 1; j >= 0; --j) {
            if (n2 != this.bLj[j]) continue;
            return -j - 1;
        }
        return this.m_size;
    }

    protected void ve() {
        if (this.m_size == this.bLj.length) {
            this.cM(this.bLj.length * 2);
        }
    }

    public final boolean contains(int n2) {
        return this.hJ(n2) != -1;
    }

    public void reset() {
        this.m_size = 0;
    }

    public void clear() {
        this.m_size = 0;
    }

    public int size() {
        return this.m_size;
    }

    public int hL(int n2) {
        return this.bLj[n2];
    }
}

