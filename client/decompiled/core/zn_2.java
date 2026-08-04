/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import com.ankamagames.baseImpl.graphics.alea.display.ScreenElement;

/*
 * Renamed from zn
 */
class zn_2
extends aez_2 {
    zn_2() {
    }

    public final int a(DisplayedScreenElement displayedScreenElement, DisplayedScreenElement displayedScreenElement2) {
        double d;
        ayZ ayZ2 = this.aup();
        double d2 = ayZ2.dmX;
        double d3 = ayZ2.dmY;
        qs_2 qs_22 = ayZ2.dmW;
        double d4 = this.a(displayedScreenElement, d2, d3, qs_22);
        if (d4 > (d = this.a(displayedScreenElement2, d2, d3, qs_22))) {
            return 1;
        }
        if (d4 < d) {
            return -1;
        }
        return 0;
    }

    private double a(DisplayedScreenElement displayedScreenElement, double d, double d2, qs_2 qs_22) {
        ScreenElement screenElement = displayedScreenElement.atV();
        double d3 = qs_22.i(screenElement.avV(), screenElement.avW());
        double d4 = qs_22.j(screenElement.avV(), screenElement.avW()) + (double)screenElement.avU() * qs_22.aNA();
        return ej_0.d(d - d3) + ej_0.d(d2 - d4);
    }
}

