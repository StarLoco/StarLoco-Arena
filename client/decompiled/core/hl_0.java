/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import java.util.ArrayList;

/*
 * Renamed from hL
 */
class hl_0 {
    private final ArrayList wn = new ArrayList();
    private final pq_2 wo = new xc_0(this);
    private short wp;
    private DisplayedScreenElement wq;
    private DisplayedScreenElement wr;

    private hl_0() {
    }

    public void a(aga_0 aga_02, int n2, int n3, int n4) {
        this.wn.clear();
        this.wr = null;
        this.wq = null;
        short s = auU.J(n2, n3, (short)n4);
        if (s == Short.MIN_VALUE) {
            return;
        }
        this.wp = s;
        aga_02.a(n2, n3, this.wn, this.wo);
        if (this.wn.isEmpty()) {
            return;
        }
        for (int j = this.wn.size() - 1; j >= 0; --j) {
            DisplayedScreenElement displayedScreenElement = (DisplayedScreenElement)this.wn.get(j);
            byte by = displayedScreenElement.coy.cts;
            if (this.wq == null || by > this.wq.coy.cts) {
                this.wq = displayedScreenElement;
            }
            if (!pq_2.abX.a(displayedScreenElement) || this.wr != null && by <= this.wr.coy.cts) continue;
            this.wr = displayedScreenElement;
        }
    }

    static /* synthetic */ short a(hl_0 hl_02) {
        return hl_02.wp;
    }

    /* synthetic */ hl_0(aw_0 aw_02) {
        this();
    }

    static /* synthetic */ DisplayedScreenElement b(hl_0 hl_02) {
        return hl_02.wq;
    }

    static /* synthetic */ DisplayedScreenElement c(hl_0 hl_02) {
        return hl_02.wr;
    }
}

