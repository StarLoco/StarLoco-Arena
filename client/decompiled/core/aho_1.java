/*
 * Decompiled with CFR 0.152.
 */
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/*
 * Renamed from aHo
 */
class aho_1
implements ItemListener {
    final /* synthetic */ aph_1 dLY;

    aho_1(aph_1 aph_12) {
        this.dLY = aph_12;
    }

    public void itemStateChanged(ItemEvent itemEvent) {
        ahn_0.dNL.fc(itemEvent.getStateChange() == 1);
    }
}

