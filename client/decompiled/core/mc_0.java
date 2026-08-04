/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Mc
 */
class mc_0
extends nj_0 {
    final /* synthetic */ acu_1 bsY;
    final /* synthetic */ acu_1 bsR;

    mc_0(acu_1 acu_12, acu_1 acu_13) {
        this.bsR = acu_12;
        this.bsY = acu_13;
        super((mc_0)null);
    }

    public void run() {
        this.setName("Worker");
        acu_1.sP().info((Object)"Worker running");
        this.f(true);
        while (this.isRunning()) {
            this.bsY.run();
        }
        acu_1.sP().info((Object)"Worker stopped");
        acu_1.a(this.bsR, null);
    }
}

