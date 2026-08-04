/*
 * Decompiled with CFR 0.152.
 */
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/*
 * Renamed from xn
 */
class xn_0
implements ItemListener {
    final /* synthetic */ cf_0 axB;

    xn_0(cf_0 cf_02) {
        this.axB = cf_02;
    }

    public void itemStateChanged(ItemEvent itemEvent) {
        if (this.axB.ii == null) {
            return;
        }
        boolean bl2 = itemEvent.getStateChange() == 1;
        this.axB.ii.kW().j(bl2);
    }
}

