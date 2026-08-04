/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aKA
 */
public class aka_2
implements dE {
    private amg_1 oO;
    private boolean chi;
    private eu_0 dTG;

    public void a(amg_1 amg_12, String string) {
        this.chi = true;
        this.oO = new cu_0(this, amg_12);
        this.oO.setVisible(this.chi);
        amg_12.a(this);
        this.oO.iG(string);
        this.oO.b(amg_12.L());
        this.oO.aY("AnimStatique");
        this.oO.a(amh_2.aBP());
        this.oO.bR(0.4375f);
        this.oO.be((byte)8);
        this.oO.setVisible(this.chi);
        bd_1.Is().g(this.oO);
        this.oO.aTt();
        this.dTG = new eu_0(this.oO, amg_12);
        int[] nArray = this.dTG.e(amg_12.aNU(), amg_12.aNV(), amg_12.aNW());
        this.oO.a(nArray[0], (double)nArray[1], (double)((short)nArray[2]));
    }

    public void aPO() {
        this.oO.setVisible(false);
    }

    public void Ok() {
        this.oO.setVisible(this.chi);
        if (this.dTG != null) {
            this.dTG.reset();
        }
    }

    public void reset() {
        if (this.dTG != null) {
            this.dTG.reset();
        }
        if (bd_1.Is().bb(this.oO.getId()) == null) {
            bd_1.Is().g(this.oO);
        }
    }

    public void cleanUp() {
        if (this.oO != null) {
            mT mT2 = this.oO.rI();
            if (mT2 != null) {
                mT2.f(this.oO);
            }
            bd_1.Is().ba(this.oO.getId());
        }
        this.oO = null;
    }

    public amg_1 aVF() {
        return this.oO;
    }

    public eu_0 aVG() {
        return this.dTG;
    }

    public void fj(boolean bl2) {
        if (this.oO != null) {
            this.oO.setVisible(this.chi && bl2);
        }
    }

    public boolean isVisible() {
        return this.chi;
    }

    public void setVisible(boolean bl2) {
        this.chi = bl2;
    }

    public void a(boolean bl2, ns_1 ns_12) {
        if (this.oO != null && ns_12 == ns_1.bzw) {
            this.oO.setVisible(this.chi && bl2);
        }
    }
}

