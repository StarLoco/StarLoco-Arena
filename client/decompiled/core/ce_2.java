/*
 * Decompiled with CFR 0.152.
 */
import javax.swing.JFrame;

/*
 * Renamed from CE
 */
class ce_2
implements Runnable {
    final /* synthetic */ akK aMf;

    ce_2(akK akK2) {
        this.aMf = akK2;
    }

    public void run() {
        akK.a(this.aMf, new JFrame("PSys Debug"));
        akK.a(this.aMf, new fm_2());
        akK.b(this.aMf).setContentPane(akK.a(this.aMf).iA());
        akK.b(this.aMf).setDefaultCloseOperation(3);
        akK.b(this.aMf).setSize(300, 600);
        akK.b(this.aMf).setVisible(true);
        ip_2.Un().a(new asm_0(this), 1000L, -1);
    }
}

