/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import java.util.ArrayList;

public class asF
extends adt_2 {
    private static final ArrayList wn = new ArrayList();

    public asF(adu_0 adu_02) {
        this.b();
        this.bsq = adu_02;
    }

    public void bV(int n2, int n3) {
        if (this.bsq.gV().F(n2, n3)) {
            this.bsq.gV().bC(n2, n3);
        }
        aga_0.aSG().a(n2, n3, wn, pq_2.abV);
        for (int j = 0; j < wn.size(); ++j) {
            ((DisplayedScreenElement)wn.get(j)).setVisible(false);
        }
        wn.clear();
        qd_1.uW().O(n2, n3);
    }

    public aav_2 gR() {
        return WF.ajj();
    }

    public azk aFF() {
        return je_1.Wa();
    }

    public bs_1 aFG() {
        return aca_0.aOq();
    }
}

