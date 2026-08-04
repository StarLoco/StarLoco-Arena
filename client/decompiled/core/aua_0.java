/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aua
 */
public final class aua_0 {
    public final int cVC = -1;
    private int m_firstFree;
    private int[] cVD;
    public int cVE;
    public int cVF;

    public aua_0(int n2) {
        this.cVD = new int[n2];
        this.aHd();
    }

    public final int aHc() {
        if (this.m_firstFree >= this.cVD.length) {
            return -1;
        }
        int n2 = this.m_firstFree;
        this.m_firstFree = this.cVD[this.m_firstFree];
        --this.cVE;
        ++this.cVF;
        return n2;
    }

    public final void mm(int n2) {
        this.cVD[n2] = this.m_firstFree;
        this.m_firstFree = n2;
        ++this.cVE;
        --this.cVF;
    }

    public final void aHd() {
        this.m_firstFree = 0;
        for (int j = 0; j < this.cVD.length; ++j) {
            this.cVD[j] = j + 1;
        }
        this.cVE = this.cVD.length;
        this.cVF = 0;
    }

    public final int pz() {
        return this.cVE;
    }

    public final int pA() {
        return this.cVF;
    }

    public final int getSize() {
        return this.cVD.length;
    }

    public final void resize(int n2) {
        assert (n2 > this.cVD.length);
        int[] nArray = new int[n2];
        for (int j = this.cVD.length; j < nArray.length; ++j) {
            nArray[j] = j + 1;
        }
        System.arraycopy(this.cVD, 0, nArray, 0, this.cVD.length);
        this.cVD = nArray;
        this.cVE = n2 - this.cVF;
    }
}

