/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ru
 */
public class ru_0
implements alx_0 {
    private adg_2 DD;
    private long agL;

    public ru_0(adg_2 adg_22, int n2) {
        this.DD = adg_22;
        this.agL = aam_1.aMF().a(this, n2, 0, 1);
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }

    public void stop() {
        aam_1.aMF().en(this.agL);
        this.agL = 0L;
    }

    public boolean a(pr_0 pr_02) {
        axe_0 axe_02;
        if (pr_02 instanceof axe_0 && (axe_02 = (axe_0)pr_02).aKD() == this.agL) {
            if (this.DD.isElementMapRoot() && this.DD.getElementMap() != null) {
                add_1.aOG().kO(this.DD.getElementMap().getId());
            } else {
                this.DD.aab();
            }
        }
        return false;
    }
}

