/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import java.util.Comparator;

/*
 * Renamed from QM
 */
class qm_0
implements Comparator {
    qm_0() {
    }

    public int a(DisplayedScreenElement displayedScreenElement, DisplayedScreenElement displayedScreenElement2) {
        if (displayedScreenElement == displayedScreenElement2) {
            return 0;
        }
        return displayedScreenElement.coy.cts - displayedScreenElement2.coy.cts;
    }
}

