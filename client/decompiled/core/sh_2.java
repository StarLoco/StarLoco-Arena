/*
 * Decompiled with CFR 0.152.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Renamed from Sh
 */
class sh_2
implements ActionListener {
    final /* synthetic */ Hu bLe;

    sh_2(Hu hu) {
        this.bLe = hu;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (Hu.a(this.bLe) != null) {
            Hu.a(this.bLe).aJb();
        } else {
            System.exit(0);
        }
    }
}

