/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ake
 */
class ake_2
implements Runnable {
    final /* synthetic */ acu_1 bsR;

    private ake_2(acu_1 acu_12) {
        this.bsR = acu_12;
    }

    public void run() {
        ip_2.Un().Ur();
        ip_2.Un().update();
        while (!this.bsR.cjX.isEmpty()) {
            pr_0 pr_02 = (pr_0)this.bsR.cjX.poll();
            this.bsR.ckc.decrementAndGet();
            acu_1.a(this.bsR, pr_02);
        }
    }

    /* synthetic */ ake_2(acu_1 acu_12, mc_0 mc_02) {
        this(acu_12);
    }
}

