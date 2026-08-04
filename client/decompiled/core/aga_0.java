/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from aGA
 */
public class aga_0
implements aht_2 {
    private static final int cKu = 25;
    private static final int dIN = 0;
    private static final int dIO = 1;
    private static final int dIP = 2;
    private static final int dIQ = 3;
    public static final int dIR = 0;
    public static final int dIS = 1;
    public static final int dIT = 2;
    private final cp_2 dIU = new cp_2(2048);
    private anx_0 dIV;
    private ArrayList ari;
    private String dIW;
    private String dIX;
    private aBp dIY;
    private int dIZ = Integer.MIN_VALUE;
    private byte dJa;
    private static byte cdY = (byte)-1;
    private static final aga_0 dJb = new aga_0();
    private static final Logger a = Logger.getLogger(aga_0.class);
    private final ArrayList atg = new ArrayList();
    private final WL dJc = new WL();
    private final ArrayList dJd = new ArrayList();
    private final ala_2 dJe;
    private final ahf_1 dJf = new ahf_1();
    private final me_0 dJg = new me_0();
    private int dJh;

    private aga_0() {
        this.ari = new ArrayList(25);
        this.dJe = new ala_2(this.dJc);
        this.dIV = new anx_0();
        this.clear();
    }

    public static aga_0 aSF() {
        return new aga_0();
    }

    public static aga_0 aSG() {
        return dJb;
    }

    public Iterator aSH() {
        return this.ari.iterator();
    }

    public void clear() {
        this.dJa = 0;
        this.ari.clear();
        this.dIV.clear();
        this.dJe.clear();
        this.dJf.clear();
        this.dJc.clear();
    }

    private static String G(String string, int n2) {
        if (string == null) {
            return null;
        }
        assert (string.contains("%d") && string.endsWith("/"));
        return String.format(string, n2);
    }

    public void ot(int n2) {
        if (this.dIZ == n2) {
            return;
        }
        this.dIZ = n2;
        if (this.dIX != null) {
            this.dIW = aga_0.G(this.dIX, this.dIZ);
        }
        this.dJa = (byte)(this.dJa & 0xFFFFFFFD);
    }

    public void setPath(String string) {
        this.dIX = string;
        this.dIW = aga_0.G(this.dIX, this.dIZ);
        this.dJa = (byte)(this.dJa & 0xFFFFFFFD);
    }

    public void b(qs_2 qs_22, int n2) {
        this.dJf.clear();
        ari_0 ari_02 = qs_22.aNB();
        for (int j = 0; j < this.ari.size(); ++j) {
            abb_0 abb_02 = (abb_0)this.ari.get(j);
            ArrayList arrayList = abb_02.apH();
            if (arrayList.size() == 0) continue;
            abb_02.b(ari_02);
            if (!abb_02.isVisible()) continue;
            for (int i2 = arrayList.size() - 1; i2 >= 0; --i2) {
                DisplayedScreenElement displayedScreenElement = (DisplayedScreenElement)arrayList.get(i2);
                if (!displayedScreenElement.coy.aos() || displayedScreenElement.coA == null || this.dJf.contains(displayedScreenElement)) continue;
                displayedScreenElement.bz((short)n2);
                this.dJf.add(displayedScreenElement);
            }
        }
    }

    public final void b(agf_0 agf_02) {
        this.z(agf_02.bAE, agf_02.bAB, agf_02.bAD, agf_02.bAC);
    }

    private void z(int n2, int n3, int n4, int n5) {
        assert (this.dIW != null) : "You must call setPath before";
        this.dIV.a(this.dIW, n2, n3, n4, n5, this.dIY);
        if (this.dIV.aCD()) {
            this.aSI();
        } else {
            this.dJe.a(this.dIV.HC(), this.dIV.HD(), 25, this.ari);
        }
        int n6 = this.ari.size();
        if (n6 != 0) {
            boolean bl2 = true;
            for (int j = 0; j < n6; ++j) {
                abb_0 abb_02 = (abb_0)this.ari.get(j);
                if (abb_02 == null) continue;
                abb_02.update();
                boolean bl3 = abb_02.is();
                bl2 &= bl3;
            }
            this.dJa = bl2 ? (byte)(this.dJa | 1) : (byte)(this.dJa & 0xFFFFFFFE);
        }
    }

    private void aSI() {
        asz asz2 = this.dIV.cKv;
        int n2 = asz2.size();
        this.ari.clear();
        for (int j = 0; j < n2; ++j) {
            int n3 = asz2.hL(j);
            kC kC2 = (kC)asz2.jx(j);
            abb_0 abb_02 = (abb_0)this.dJe.ea(n3);
            if (abb_02 == null) {
                abb_02 = new abb_0();
                abb_02.a(kC2, this.dJc, false);
                this.dJe.put(n3, abb_02);
            }
            this.ari.add(abb_02);
        }
    }

    public boolean a(EA eA) {
        return this.dJd.add(eA);
    }

    public boolean b(EA eA) {
        return this.dJd.remove(eA);
    }

    public void aSJ() {
        int n2;
        assert (this.ari.size() == this.dIV.cKv.size());
        for (n2 = 0; n2 < this.ari.size(); ++n2) {
            abb_0 abb_02 = (abb_0)this.ari.get(n2);
            abb_02.a(abb_02.apI(), this.dJc, true);
        }
        for (n2 = 0; n2 < this.dJd.size(); ++n2) {
            ((EA)this.dJd.get(n2)).NQ();
        }
    }

    public boolean c(aba_2 aba_22) {
        this.dIU.clear();
        boolean bl2 = false;
        int n2 = this.ari.size();
        ari_0 ari_02 = aba_22.aNB();
        for (int j = 0; j < n2; ++j) {
            abb_0 abb_02 = (abb_0)this.ari.get(j);
            bl2 |= abb_02.a(aba_22, this.dIU, ari_02);
        }
        akz_0 akz_02 = this.dIU.eI();
        while (akz_02.hasNext()) {
            akz_02.fK();
            aba_22.b(((DisplayedScreenElement)akz_02.value()).atW(), true);
        }
        this.dJa = (byte)(this.dJa | 2);
        return bl2;
    }

    public final void a(int n2, int n3, ArrayList arrayList, pq_2 pq_22) {
        for (int j = 0; j < this.ari.size(); ++j) {
            abb_0 abb_02 = (abb_0)this.ari.get(j);
            abb_02.a(n2, n3, arrayList, pq_22);
        }
    }

    public final DisplayedScreenElement a(int n2, int n3, pq_2 pq_22) {
        DisplayedScreenElement displayedScreenElement = null;
        for (int j = 0; j < this.ari.size(); ++j) {
            abb_0 abb_02 = (abb_0)this.ari.get(j);
            DisplayedScreenElement displayedScreenElement2 = abb_02.a(n2, n3, pq_22);
            if (displayedScreenElement2 == null || displayedScreenElement != null && displayedScreenElement.coy.cts > displayedScreenElement2.coy.cts) continue;
            displayedScreenElement = displayedScreenElement2;
        }
        return displayedScreenElement;
    }

    public final DisplayedScreenElement a(int n2, int n3, int n4, pq_2 pq_22) {
        DisplayedScreenElement displayedScreenElement = null;
        for (int j = 0; j < this.ari.size(); ++j) {
            abb_0 abb_02 = (abb_0)this.ari.get(j);
            DisplayedScreenElement displayedScreenElement2 = abb_02.a(n2, n3, n4, pq_22);
            if (displayedScreenElement2 == null || displayedScreenElement != null && displayedScreenElement.coy.cts > displayedScreenElement2.coy.cts) continue;
            displayedScreenElement = displayedScreenElement2;
        }
        return displayedScreenElement;
    }

    public final DisplayedScreenElement b(int n2, int n3, int n4, pq_2 pq_22) {
        DisplayedScreenElement displayedScreenElement = null;
        for (int j = 0; j < this.ari.size(); ++j) {
            abb_0 abb_02 = (abb_0)this.ari.get(j);
            DisplayedScreenElement displayedScreenElement2 = abb_02.b(n2, n3, n4, pq_22);
            if (displayedScreenElement2 == null || displayedScreenElement != null && displayedScreenElement.coy.cts > displayedScreenElement2.coy.cts) continue;
            displayedScreenElement = displayedScreenElement2;
        }
        return displayedScreenElement;
    }

    public final DisplayedScreenElement c(int n2, int n3, int n4, pq_2 pq_22) {
        DisplayedScreenElement displayedScreenElement = null;
        for (int j = 0; j < this.ari.size(); ++j) {
            abb_0 abb_02 = (abb_0)this.ari.get(j);
            DisplayedScreenElement displayedScreenElement2 = abb_02.c(n2, n3, n4, pq_22);
            if (displayedScreenElement2 == null || displayedScreenElement != null && displayedScreenElement.coy.cts > displayedScreenElement2.coy.cts) continue;
            displayedScreenElement = displayedScreenElement2;
        }
        return displayedScreenElement;
    }

    public final DisplayedScreenElement d(int n2, int n3, int n4, pq_2 pq_22) {
        short s = auU.H(n2, n3, (short)n4);
        if (s == Short.MIN_VALUE) {
            return null;
        }
        return this.c(n2, n3, s, pq_22);
    }

    public final DisplayedScreenElement e(int n2, int n3, int n4, pq_2 pq_22) {
        short s = auU.J(n2, n3, (short)n4);
        if (s == Short.MIN_VALUE) {
            return null;
        }
        return this.b(n2, n3, s, pq_22);
    }

    public void a(int n2, int n3, ArrayList arrayList) {
        for (int j = 0; j < this.ari.size(); ++j) {
            abb_0 abb_02 = (abb_0)this.ari.get(j);
            kC kC2 = abb_02.apI();
            if (kC2 == null || n3 < kC2.EO || n3 >= kC2.EQ || n2 < kC2.EN || n2 >= kC2.EP) continue;
            abb_02.a(n2, n3, arrayList);
        }
    }

    public final ArrayList aSK() {
        return this.ari;
    }

    public final boolean is() {
        return (this.dJa & 1) == 1;
    }

    final boolean aSL() {
        return this.dJa == 3;
    }

    public void a(aBp aBp2) {
        this.dIY = aBp2;
    }

    public void ou(int n2) {
        this.dJh = n2;
        byte by = (byte)(cdY & 0xFFFFFFF8 | aga_0.ov(n2));
        this.bo(by);
        this.aSJ();
    }

    public int aSM() {
        return this.dJh;
    }

    public void a(byte by, boolean bl2) {
        byte by2 = cdY;
        by2 = bl2 ? (byte)(by2 | by) : (byte)(by2 & ~by);
        this.bo(by2);
    }

    public static byte ov(int n2) {
        switch (n2) {
            case 2: {
                return 7;
            }
            case 1: {
                return 3;
            }
            case 0: {
                return 1;
            }
        }
        return 1;
    }

    private void bo(byte by) {
        if (by > cdY) {
            this.dJc.aq(by);
            ahn_0.dNL.jW();
        }
        cdY = by;
    }

    public static byte aoq() {
        return cdY;
    }

    public ArrayList a(ari_0 ari_02) {
        this.atg.clear();
        this.dIU.a(new Ac(this));
        return this.atg;
    }

    static /* synthetic */ ArrayList a(aga_0 aga_02) {
        return aga_02.atg;
    }
}

