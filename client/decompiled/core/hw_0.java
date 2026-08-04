/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Hw
 */
public abstract class hw_0 {
    public static final int beP = 200;
    private long beQ = 200L;
    private long beR = 0L;
    private yc_2 beS;

    public yc_2 Tl() {
        return this.bD(false);
    }

    public yc_2 bD(boolean bl2) {
        if (this.beS == null) {
            this.beS = this.us();
        }
        if (bl2 || this.beR != 0L && System.currentTimeMillis() - this.beR > this.beQ) {
            this.a(this.beS);
        }
        if (this.beR == 0L) {
            this.beR = System.currentTimeMillis();
        }
        return this.beS;
    }

    public void done() {
        this.beR = 0L;
        if (this.beS != null) {
            this.beS.done();
            this.beS.cW("");
            this.beS.cX("");
            this.b(this.beS);
        }
    }

    public void bL(long l2) {
        this.beQ = l2;
    }

    protected abstract yc_2 us();

    protected abstract void a(yc_2 var1);

    protected abstract void b(yc_2 var1);

    public long Tm() {
        return this.beR;
    }
}

