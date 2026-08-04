/*
 * Decompiled with CFR 0.152.
 */
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * Renamed from Sj
 */
class sj_2
extends MouseAdapter {
    final /* synthetic */ Hu bLe;

    sj_2(Hu hu) {
        this.bLe = hu;
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Hu.a(this.bLe).aJc();
        }
    }
}

