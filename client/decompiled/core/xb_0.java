/*
 * Decompiled with CFR 0.152.
 */
import javax.swing.JFrame;

/*
 * Renamed from XB
 */
class xb_0
implements Runnable {
    final /* synthetic */ amo_2 bYR;

    xb_0(amo_2 amo_22) {
        this.bYR = amo_22;
    }

    public void run() {
        amo_2.a(this.bYR, new JFrame("RenderTree Debug"));
        amo_2.a(this.bYR, new kn_0());
        amo_2.b(this.bYR).setContentPane(amo_2.a(this.bYR).iA());
        amo_2.b(this.bYR).setDefaultCloseOperation(3);
        amo_2.b(this.bYR).setSize(300, 600);
        amo_2.b(this.bYR).setVisible(true);
        ip_2.Un().a(new hj_1(this), 2000L, -1);
    }
}

