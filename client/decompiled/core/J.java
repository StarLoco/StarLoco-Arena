/*
 * Decompiled with CFR 0.152.
 */
class J
implements Runnable {
    final /* synthetic */ WP aT;

    J(WP wP) {
        this.aT = wP;
    }

    public void run() {
        try {
            this.aT.update();
        }
        catch (Exception exception) {
            WP.a.error((Object)"Exception", (Throwable)exception);
        }
    }
}

