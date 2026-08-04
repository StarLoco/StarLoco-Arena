/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Xr
 */
class xr_2
implements Runnable {
    final /* synthetic */ afe_1 bXO;

    xr_2(afe_1 afe_12) {
        this.bXO = afe_12;
    }

    public void run() {
        this.bXO.setPixmap((ur_1)afe_1.b(this.bXO).get(afe_1.a(this.bXO)));
        afe_1.a(this.bXO, (afe_1.a(this.bXO) + 1) % afe_1.b(this.bXO).size());
    }
}

