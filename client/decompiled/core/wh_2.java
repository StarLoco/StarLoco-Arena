/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from wH
 */
public class wh_2
implements alx_0 {
    private int wg;
    private long agL;

    public void ef(int n2) {
        this.stop();
        this.setDuration(n2);
        this.agL = aam_1.aMF().a(this, 1000L, 0, n2);
    }

    public void stop() {
        this.setDuration(0);
        if (this.agL > 0L) {
            aam_1.aMF().en(this.agL);
        }
        this.agL = 0L;
    }

    private void setDuration(int n2) {
        this.wg = Math.max(n2, 0);
        azs_0.aLV().g("countdown", this.wg);
    }

    public boolean a(pr_0 pr_02) {
        this.setDuration(this.wg - 1);
        if (this.wg == 0) {
            this.stop();
        }
        return false;
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }
}

