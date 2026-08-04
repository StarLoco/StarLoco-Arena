/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;

/*
 * Renamed from zq
 */
class zq_2
extends aez_2 {
    zq_2() {
    }

    public final int a(DisplayedScreenElement displayedScreenElement, DisplayedScreenElement displayedScreenElement2) {
        long l2 = displayedScreenElement.atW().dPx;
        long l3 = displayedScreenElement2.atW().dPx;
        if (l2 < l3) {
            return 1;
        }
        if (l2 > l3) {
            return -1;
        }
        return 0;
    }
}

