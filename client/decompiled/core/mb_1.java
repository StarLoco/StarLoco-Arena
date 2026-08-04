/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from mb
 */
class mb_1
implements Runnable {
    final /* synthetic */ ait_0 Iz;
    final /* synthetic */ rt_2 Iu;

    mb_1(rt_2 rt_22, ait_0 ait_02) {
        this.Iu = rt_22;
        this.Iz = ait_02;
    }

    public void run() {
        if (!this.Iz.isConnected() && !this.Iz.Yu()) {
            apN.aDK().aDO();
        } else if (this.Iz.Yu()) {
            pm_0.ur().bD(true).m(aon_0.aYc().getString("connection.retrying"), this.Iz.YB());
            pm_0.ur().Tl().es(this.Iz.YA());
        }
    }
}

