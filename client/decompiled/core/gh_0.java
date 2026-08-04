/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Gh
 */
public final class gh_0 {
    private float[] baG;
    private int m_size;

    public gh_0(int n2) {
        this.baG = new float[n2 * n2];
        this.m_size = n2;
    }

    public gh_0(float[] fArray) {
        this.m(fArray);
    }

    public final void m(float[] fArray) {
        assert (Math.sqrt(fArray.length) == (double)((int)Math.sqrt(fArray.length)));
        this.m_size = (int)Math.sqrt(fArray.length);
        this.baG = new float[fArray.length];
        System.arraycopy(fArray, 0, this.baG, 0, this.baG.length);
    }

    public final float[] Pn() {
        return this.baG;
    }

    public final int getSize() {
        return this.m_size;
    }
}

