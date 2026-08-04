/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ajj
 */
class ajj_0
implements Runnable {
    final /* synthetic */ amc_0 cAh;

    ajj_0(amc_0 amc_02) {
        this.cAh = amc_02;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        pu_2 pu_22 = null;
        try {
            while (true) {
                if ((pu_22 = (pu_2)amc_0.a(this.cAh).remove()) == null) {
                    continue;
                }
                pu_22.delete();
            }
        }
        catch (InterruptedException interruptedException) {
            amc_0 amc_02 = this.cAh;
            synchronized (amc_02) {
                amc_0.a(this.cAh, null);
                Thread.currentThread().interrupt();
            }
            return;
        }
    }
}

