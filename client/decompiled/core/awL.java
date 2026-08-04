/*
 * Decompiled with CFR 0.152.
 */
public class awL {
    public static final int dip = 25;
    protected final YU diq;
    protected int dir;
    protected int m_size;

    public awL(awL awL2) {
        this.diq = new YU(awL2.diq);
        this.dir = awL2.dir;
        this.m_size = awL2.m_size;
    }

    public awL(int n2, int n3) {
        this.diq = new YU(n2);
        this.dir = n3;
        this.m_size = n2;
    }

    public awL(kf_0 kf_02, int n2) {
        this.m_size = kf_02.getWidth() * kf_02.getHeight();
        this.diq = new YU(this.m_size);
        int n3 = kf_02.getWidth();
        int n4 = kf_02.getHeight();
        for (int j = 0; j < n3; ++j) {
            for (int i2 = 0; i2 < n4; ++i2) {
                this.diq.set(i2 * n3 + j, kf_02.G(j, i2) >= n2);
            }
        }
        this.dir = n3;
    }

    public awL(byte[] byArray, int n2, int n3, int n4) {
        this.m_size = n4;
        this.dir = n3;
        this.diq = YU.f(byArray, n2, this.m_size);
    }

    public void i(int n2, boolean bl2) {
        this.diq.set(n2, bl2);
    }

    public void e(int n2, int n3, boolean bl2) {
        this.diq.set(n3 * this.dir + n2, bl2);
    }

    public boolean ca(int n2, int n3) {
        return this.diq.get(n3 * this.dir + n2);
    }

    public int getSize() {
        return this.m_size;
    }
}

