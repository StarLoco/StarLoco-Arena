/*
 * Decompiled with CFR 0.152.
 */
public class apd
extends aan_1
implements alx_0 {
    private long lc;
    private int cLU;
    private int wg;
    private long agL;
    public static final String cLV = "timeLeft";

    public apd(int n2) {
        super(aon_0.aYc().getString("tournamentCountdown", n2));
        this.ef(n2);
    }

    public boolean jt() {
        return false;
    }

    public byte getType() {
        return 8;
    }

    public long fx() {
        return this.lc;
    }

    public void ad(long l2) {
        this.lc = l2;
    }

    public void lK(int n2) {
        this.cLU = n2;
    }

    public int aDl() {
        return this.cLU;
    }

    public Object getFieldValue(String string) {
        if (string.equals(cLV)) {
            return this.cLU;
        }
        return super.getFieldValue(string);
    }

    public void ef(int n2) {
        this.stop();
        this.setDuration(n2);
        this.agL = aam_1.aMF().a(this, 60000L, 0, n2);
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
        this.lK(this.wg);
        azs_0.aLV().a((aho_0)this, cLV);
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

