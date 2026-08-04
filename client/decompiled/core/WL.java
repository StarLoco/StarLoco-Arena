/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import org.apache.log4j.Logger;

public final class WL {
    private static final Logger a = Logger.getLogger(WL.class);
    private final cp_2 bVi = new cp_2(8192);

    public final DisplayedScreenElement dg(long l2) {
        assert (l2 != 0L);
        DisplayedScreenElement displayedScreenElement = (DisplayedScreenElement)this.bVi.t(l2);
        if (displayedScreenElement != null) {
            displayedScreenElement.HE();
            return displayedScreenElement;
        }
        displayedScreenElement = (DisplayedScreenElement)yW.FL().a(DisplayedScreenElement.it(), DisplayedScreenElement.class);
        this.bVi.a(l2, displayedScreenElement);
        return displayedScreenElement;
    }

    public final void c(DisplayedScreenElement displayedScreenElement) {
        if (displayedScreenElement.avb() > 0) {
            displayedScreenElement.HF();
        } else {
            long l2 = displayedScreenElement.coy.ctu;
            assert (l2 != 0L);
            this.bVi.u(l2);
            displayedScreenElement.HF();
        }
    }

    public final void aq(byte by) {
        akz_0 akz_02 = this.bVi.eI();
        while (akz_02.hasNext()) {
            akz_02.fK();
            DisplayedScreenElement displayedScreenElement = (DisplayedScreenElement)akz_02.value();
            if (displayedScreenElement.coA != null || !displayedScreenElement.aw(by)) continue;
            displayedScreenElement.atU();
        }
    }

    public void clear() {
        if (this.bVi.size() != 0) {
            a.error((Object)"la factory contient encore des \u00e9l\u00e9m\u00e9ents!!!");
        }
        this.bVi.clear();
    }
}

