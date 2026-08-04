/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from lV
 */
class lv_2
implements Runnable {
    final /* synthetic */ rt_2 Iu;

    lv_2(rt_2 rt_22) {
        this.Iu = rt_22;
    }

    public void run() {
        apN apN2 = apN.aDK();
        if (apN2.vL() == 15) {
            apN2.w((byte)15);
            apN2.vJ().closeConnection();
        } else {
            pm_0.ur().done();
            byte[] byArray = apN2.vK();
            if (byArray != null) {
                tL tL2 = new tL(byArray);
                apN2.vJ().b(tL2);
            } else {
                apN2.vJ().closeConnection();
            }
        }
    }
}

