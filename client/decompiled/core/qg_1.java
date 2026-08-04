/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from qg
 */
public abstract class qg_1 {
    protected long[] adv;
    protected int m_size;

    public qg_1(int n2) {
        this.adv = new long[n2];
        this.m_size = 0;
    }

    public boolean cM(int n2) {
        if (n2 > this.adv.length) {
            int n3 = this.adv.length;
            long[] lArray = this.adv;
            this.adv = new long[n2];
            System.arraycopy(lArray, 0, this.adv, 0, n3);
            return true;
        }
        return false;
    }

    protected int az(long l2) {
        for (int j = this.m_size - 1; j >= 0; --j) {
            if (l2 != this.adv[j]) continue;
            return j;
        }
        return -1;
    }

    protected int aA(long l2) {
        for (int j = this.m_size - 1; j >= 0; --j) {
            if (l2 != this.adv[j]) continue;
            return -j - 1;
        }
        return this.m_size;
    }

    protected void ve() {
        if (this.m_size == this.adv.length) {
            this.cM(this.adv.length * 2);
        }
    }

    public final boolean m(long l2) {
        return this.az(l2) != -1;
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

    public long cN(int n2) {
        return this.adv[n2];
    }
}

