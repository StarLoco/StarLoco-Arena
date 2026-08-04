/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import com.ankamagames.baseImpl.graphics.alea.display.ScreenElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * Renamed from abb
 */
public class abb_0 {
    private static final int chh = 16;
    private static final ArrayList bPF = new ArrayList(64);
    private boolean aPS = false;
    private boolean chi;
    private kC chj;
    private DisplayedScreenElement[] chk;
    private final ArrayList chl = new ArrayList();
    private static final Comparator chm = new qm_0();

    public void a(kC kC2, WL wL, boolean bl2) {
        this.a(wL);
        this.chj = kC2;
        if (this.chj == null) {
            return;
        }
        int n2 = this.chj.q(aga_0.aoq());
        this.chk = new DisplayedScreenElement[n2];
        int n3 = 0;
        int n4 = this.chj.EK.size();
        for (int j = 0; j < n4; ++j) {
            ScreenElement screenElement = (ScreenElement)this.chj.EK.get(j);
            byte by = screenElement.avY().aoq();
            if ((by & aga_0.aoq()) != by) continue;
            DisplayedScreenElement displayedScreenElement = wL.dg(screenElement.ctu);
            if (bl2 || displayedScreenElement.coy == null) {
                displayedScreenElement.b(screenElement);
            }
            this.chk[n3] = displayedScreenElement;
            ++n3;
            if (!screenElement.aos()) continue;
            this.chl.add(displayedScreenElement);
        }
    }

    public void b(ari_0 ari_02) {
        this.chi = this.chj == null ? false : ari_02.y(this.chj.EQ, this.chj.EN, this.chj.EO, this.chj.EP);
    }

    public boolean isVisible() {
        return this.chi;
    }

    ArrayList apH() {
        return this.chl;
    }

    public void update() {
        if (this.aPS) {
            return;
        }
        if (this.chk == null) {
            this.aPS = true;
            return;
        }
        for (int j = 0; j < this.chk.length; ++j) {
            DisplayedScreenElement displayedScreenElement = this.chk[j];
            if (displayedScreenElement.coA == null || displayedScreenElement.coA.jI() == null || displayedScreenElement.coA.jI().is()) continue;
            return;
        }
        this.aPS = true;
    }

    public boolean a(aba_2 aba_22, cp_2 cp_22, ari_0 ari_02) {
        this.b(ari_02);
        if (!this.isVisible()) {
            return false;
        }
        boolean bl2 = false;
        for (int j = 0; j < this.chk.length; ++j) {
            DisplayedScreenElement displayedScreenElement = this.chk[j];
            if (displayedScreenElement == null) continue;
            boolean bl3 = displayedScreenElement.a(aba_22, cp_22, ari_02);
            bl2 |= bl3;
        }
        return bl2;
    }

    public final kC apI() {
        return this.chj;
    }

    public final DisplayedScreenElement[] apJ() {
        return this.chk;
    }

    public boolean is() {
        return this.aPS;
    }

    public final void a(int n2, int n3, ArrayList arrayList, pq_2 pq_22) {
        ScreenElement screenElement;
        if (this.chk == null) {
            return;
        }
        if (!this.bi(n2, n3)) {
            return;
        }
        if (this.chk.length <= 16) {
            for (int j = 0; j < this.chk.length; ++j) {
                DisplayedScreenElement displayedScreenElement = this.chk[j];
                ScreenElement screenElement2 = displayedScreenElement.coy;
                if (screenElement2.ctp != n2 || screenElement2.ctq != n3 || !pq_22.a(displayedScreenElement)) continue;
                arrayList.add(displayedScreenElement);
            }
            return;
        }
        int n4 = 0;
        int n5 = this.chk.length - 1;
        int n6 = -1;
        do {
            ScreenElement screenElement3;
            int n7 = n5 + n4 >>> 1;
            if (n4 + 1 == n5) {
                screenElement3 = this.chk[n4].coy;
                if (screenElement3.ctp == n2 && screenElement3.ctq == n3) {
                    n6 = n4;
                    break;
                }
                screenElement3 = this.chk[n5].coy;
                if (screenElement3.ctp == n2 && screenElement3.ctq == n3) {
                    n6 = n5;
                    break;
                }
                return;
            }
            screenElement3 = this.chk[n7].coy;
            if (screenElement3.ctq > n3) {
                n5 = n7;
                continue;
            }
            if (screenElement3.ctq < n3) {
                n4 = n7;
                continue;
            }
            if (screenElement3.ctp > n2) {
                n5 = n7;
                continue;
            }
            if (screenElement3.ctp < n2) {
                n4 = n7;
                continue;
            }
            n6 = n7;
        } while (n6 == -1);
        n4 = n6;
        int n8 = 1;
        while (n4 - n8 >= 0) {
            screenElement = this.chk[n4 - n8].coy;
            if (screenElement.ctp != n2 || screenElement.ctq != n3) break;
            ++n8;
        }
        n4 = n4 + 1 - n8;
        while (n6 + 1 < this.chk.length) {
            screenElement = this.chk[++n6].coy;
            if (screenElement.ctp != n2 || screenElement.ctq != n3) break;
            ++n8;
        }
        for (int j = 0; j < n8; ++j) {
            DisplayedScreenElement displayedScreenElement = this.chk[n4 + j];
            if (!pq_22.a(displayedScreenElement)) continue;
            arrayList.add(displayedScreenElement);
        }
    }

    public final DisplayedScreenElement a(int n2, int n3, pq_2 pq_22) {
        if (!this.bi(n2, n3)) {
            return null;
        }
        bPF.clear();
        this.a(n2, n3, bPF, pq_22);
        DisplayedScreenElement displayedScreenElement = null;
        int n4 = bPF.size();
        for (int j = 0; j < n4; ++j) {
            DisplayedScreenElement displayedScreenElement2 = (DisplayedScreenElement)bPF.get(j);
            if (displayedScreenElement2.coy.ctp != n2 || displayedScreenElement2.coy.ctq != n3 || displayedScreenElement != null && displayedScreenElement.coy.cts > displayedScreenElement2.coy.cts) continue;
            displayedScreenElement = displayedScreenElement2;
        }
        return displayedScreenElement;
    }

    public final DisplayedScreenElement a(int n2, int n3, int n4, pq_2 pq_22) {
        if (!this.bi(n3, n4)) {
            return null;
        }
        bPF.clear();
        this.a(n3, n4, bPF, pq_22);
        for (int j = bPF.size() - 1; j >= 0; --j) {
            DisplayedScreenElement displayedScreenElement = (DisplayedScreenElement)bPF.get(j);
            if (displayedScreenElement.coy.ctp == n3 && displayedScreenElement.coy.ctq == n4) continue;
            bPF.remove(j);
        }
        if (bPF.size() <= n2) {
            return null;
        }
        Collections.sort(bPF, chm);
        return (DisplayedScreenElement)bPF.get(bPF.size() - 1 - n2);
    }

    DisplayedScreenElement b(int n2, int n3, int n4, pq_2 pq_22) {
        if (!this.A(n2, n3, (short)n4)) {
            return null;
        }
        bPF.clear();
        this.a(n2, n3, bPF, pq_22);
        DisplayedScreenElement displayedScreenElement = null;
        int n5 = bPF.size();
        for (int j = 0; j < n5; ++j) {
            DisplayedScreenElement displayedScreenElement2 = (DisplayedScreenElement)bPF.get(j);
            ScreenElement screenElement = displayedScreenElement2.coy;
            if (screenElement.ctp != n2 || screenElement.ctq != n3 || screenElement.cto != n4 && (!screenElement.ctr.aom() || screenElement.cto - screenElement.aba != n4) || displayedScreenElement != null && displayedScreenElement.coy.cts > screenElement.cts) continue;
            displayedScreenElement = displayedScreenElement2;
        }
        return displayedScreenElement;
    }

    public final DisplayedScreenElement c(int n2, int n3, int n4, pq_2 pq_22) {
        if (!this.A(n2, n3, (short)n4)) {
            return null;
        }
        bPF.clear();
        this.a(n2, n3, bPF, pq_22);
        DisplayedScreenElement displayedScreenElement = null;
        int n5 = bPF.size();
        for (int j = 0; j < n5; ++j) {
            DisplayedScreenElement displayedScreenElement2 = (DisplayedScreenElement)bPF.get(j);
            assert (displayedScreenElement2.coy.ctp == n2);
            assert (displayedScreenElement2.coy.ctq == n3);
            if (displayedScreenElement2.coy.cto != n4 || displayedScreenElement != null && displayedScreenElement.coy.cts > displayedScreenElement2.coy.cts || !displayedScreenElement2.coy.ctr.aop()) continue;
            displayedScreenElement = displayedScreenElement2;
        }
        return displayedScreenElement;
    }

    public void a(int n2, int n3, ArrayList arrayList) {
        if (!this.contains(n2, n3)) {
            return;
        }
        for (int j = 0; j < this.chk.length; ++j) {
            DisplayedScreenElement displayedScreenElement = this.chk[j];
            if (displayedScreenElement.coy.ctr.aom() || n3 < displayedScreenElement.coA.EO || n3 >= displayedScreenElement.coA.EQ || n2 < displayedScreenElement.coA.EN || n2 >= displayedScreenElement.coA.EP || !displayedScreenElement.bl(n2, n3)) continue;
            arrayList.add(displayedScreenElement);
        }
    }

    private boolean bi(int n2, int n3) {
        return this.chj == null || this.chj.F(n2, n3);
    }

    private boolean A(int n2, int n3, short s) {
        return this.chj == null || this.chj.g(n2, n3, s);
    }

    private boolean contains(int n2, int n3) {
        if (this.chj == null) {
            return false;
        }
        if (n2 > this.chj.EP) {
            return false;
        }
        if (n2 < this.chj.EN) {
            return false;
        }
        if (n3 > this.chj.EQ) {
            return false;
        }
        return n3 >= this.chj.EO;
    }

    public void a(WL wL) {
        if (this.chj == null) {
            return;
        }
        ahn_0.dNL.b(this.chj);
        for (int j = 0; j < this.chk.length; ++j) {
            DisplayedScreenElement displayedScreenElement = this.chk[j];
            displayedScreenElement.a(wL);
        }
        this.chl.clear();
        this.chk = null;
        this.chj = null;
        this.aPS = false;
    }

    public final String toString() {
        return "DisplayedScreenMap {" + this.chj + "}";
    }
}

