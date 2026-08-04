/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from my
 */
class my_1
implements Runnable {
    final /* synthetic */ int KG;
    final /* synthetic */ int KH;
    final /* synthetic */ aLE KI;

    my_1(aLE aLE2, int n2, int n3) {
        this.KI = aLE2;
        this.KG = n2;
        this.KH = n3;
    }

    public void run() {
        aLE.a(this.KI).setSize(this.KG, this.KH);
    }
}

