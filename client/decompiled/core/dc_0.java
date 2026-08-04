/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Dc
 */
public class dc_0 {
    private final YU aNb = new YU(324);
    private final YU aNc = new YU(324);
    private acm_1 aNd;
    private int aNe;
    private static final akd_0[] aNf = new akd_0[32];

    public dc_0(acm_1 acm_12) {
        this.a(acm_12);
    }

    public boolean ak(int n2, int n3) {
        return this.aNb.get((n3 -= this.aNd.aH) * 18 + (n2 -= this.aNd.aG));
    }

    public final int s(int n2, int n3, int n4) {
        assert (this.aNd.F(n2, n3));
        int n5 = this.aNd.a(n2, n3, aNf, 0);
        if (n5 == 0) {
            return 0;
        }
        for (int j = 0; j < n5; ++j) {
            if (dc_0.aNf[j].wp != n4) continue;
            return aNf[j].azG();
        }
        return 0;
    }

    public boolean t(int n2, int n3, int n4) {
        return akd_0.lg(this.s(n2, n3, n4));
    }

    public boolean al(int n2, int n3) {
        return this.aNc.get((n3 -= this.aNd.aH) * 18 + (n2 -= this.aNd.aG));
    }

    public final acm_1 Ls() {
        return this.aNd;
    }

    public void b(int n2, int n3, boolean bl2) {
        if (this.ak(n2, n3) == bl2) {
            return;
        }
        if (!bl2) {
            if (!this.am(n2, n3)) {
                this.aNb.set((n3 -= this.aNd.aH) * 18 + (n2 -= this.aNd.aG), false);
                ++this.aNe;
            }
        } else {
            this.aNb.set((n3 -= this.aNd.aH) * 18 + (n2 -= this.aNd.aG), true);
            --this.aNe;
        }
    }

    public void c(int n2, int n3, boolean bl2) {
        if (this.al(n2, n3) == bl2) {
            return;
        }
        if (bl2) {
            if (!this.ak(n2, n3)) {
                this.aNc.set((n3 -= this.aNd.aH) * 18 + (n2 -= this.aNd.aG), true);
            }
        } else {
            this.aNc.set((n3 -= this.aNd.aH) * 18 + (n2 -= this.aNd.aG), false);
        }
    }

    public void a(acm_1 acm_12) {
        this.aNd = acm_12;
        this.aNb.cG(false);
        this.aNe = 324;
        int n2 = acm_12.aG;
        int n3 = acm_12.aH;
        int n4 = 0;
        for (int j = 0; j < 18; ++j) {
            for (int i2 = 0; i2 < 18; ++i2) {
                if (this.am(n2 + i2, n3 + j)) {
                    this.aNb.set(n4, true);
                    --this.aNe;
                }
                ++n4;
            }
        }
    }

    public final int Lt() {
        return this.aNe;
    }

    public boolean am(int n2, int n3) {
        int n4 = this.aNd.a(n2, n3, aNf, 0);
        if (n4 == 1) {
            return dc_0.aNf[0].cCJ == -1;
        }
        for (int j = 0; j < n4; ++j) {
            if (dc_0.aNf[j].cCJ == -1) continue;
            return false;
        }
        return true;
    }

    static {
        for (int j = 0; j < aNf.length; ++j) {
            dc_0.aNf[j] = new akd_0();
        }
    }
}

