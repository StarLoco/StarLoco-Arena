/*
 * Decompiled with CFR 0.152.
 */
class ayG
implements Runnable {
    final /* synthetic */ byte[] dmA;
    final /* synthetic */ ml_2 dmB;

    ayG(ml_2 ml_22, byte[] byArray) {
        this.dmB = ml_22;
        this.dmA = byArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        Object object = ml_2.a(this.dmB);
        synchronized (object) {
            int n2 = ml_2.b(this.dmB).size();
            for (int j = 0; j < n2; ++j) {
                ait_0 ait_02 = (ait_0)ml_2.b(this.dmB).get(j);
                ait_02.X(this.dmA);
            }
        }
    }
}

