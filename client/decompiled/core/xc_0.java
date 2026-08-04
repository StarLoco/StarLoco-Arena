/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import com.ankamagames.baseImpl.graphics.alea.display.ScreenElement;

/*
 * Renamed from Xc
 */
class xc_0
implements pq_2 {
    final /* synthetic */ hl_0 bWM;

    xc_0(hl_0 hl_02) {
        this.bWM = hl_02;
    }

    public boolean a(DisplayedScreenElement displayedScreenElement) {
        ScreenElement screenElement = displayedScreenElement.coy;
        zl_1 zl_12 = screenElement.avY();
        if (zl_12.aom() && !zl_12.aoo()) {
            return displayedScreenElement.gp() - screenElement.PD() == hl_0.a(this.bWM);
        }
        return displayedScreenElement.gp() == hl_0.a(this.bWM);
    }
}

