/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Renamed from VT
 */
public class vt_0 {
    private static int bTw = 1;
    private static String bTx = "STATIC_EFFECT";
    private static final int bTy = 2;
    private static final String bTz = "ACTIVATE_LAYER";
    private boolean Va;
    private static final float[] bTA = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    private static final float[] bTB = new float[]{0.0f, 0.0f, 0.0f, 0.4f};
    private static final vt_0 bTC = new vt_0();
    protected su_1 bsp = new ev_2();
    private HashMap bTD = new HashMap();

    public static vt_0 aiU() {
        return bTC;
    }

    private static int aiV() {
        if (bTw == Integer.MAX_VALUE) {
            return 1;
        }
        return bTw++;
    }

    public boolean aiW() {
        return this.Va;
    }

    public void activate() {
        if (!this.Va) {
            try {
                for (int j = 0; j < 2; ++j) {
                    aaR aaR2 = wn_2.Dj().cH(this.iu(j));
                    aaR2.q(mx_0.Kz[j]);
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            this.Va = true;
        }
    }

    public void deactivate() {
        if (this.Va) {
            this.clear();
            for (int j = 0; j < 2; ++j) {
                wn_2.Dj().cI(this.iu(j));
            }
            this.Va = false;
        }
    }

    public void clear() {
        for (ahz_0 ahz_02 : this.bTD.values()) {
            ahz_02.clear();
        }
        this.bTD.clear();
    }

    public void k(ack_1 ack_12) {
        if (this.aiW()) {
            ahz_0 ahz_02;
            Object object;
            Object object2;
            String string = null;
            zj_2 zj_22 = (zj_2)((Object)ack_12);
            if (zj_22.ht()) {
                try {
                    string = mu_1.rM().getString("highLightGfxFile") + zj_22.hu() + ".tga";
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                object2 = new adz_1();
                object = ahz_0.a(string, (adz_1)object2);
                ahz_02 = new ahz_0(bTx + vt_0.aiV(), bTA, (ef_1)object, (adz_1)object2, fa_0.ry);
            } else {
                ahz_02 = new ahz_0(bTx + vt_0.aiV(), bTB);
            }
            ahz_02.y(ack_12.gn(), ack_12.go(), ack_12.gp());
            object2 = ack_12.aqO().fg();
            object = Uj.a(ack_12.gn(), ack_12.go(), qc_0.bET, this.bsp, (List)object2);
            int n2 = ((ArrayList)object).size();
            for (int j = 0; j < n2; ++j) {
                DisplayedScreenElement displayedScreenElement = (DisplayedScreenElement)((ArrayList)object).get(j);
                ry ry2 = displayedScreenElement.atV().avX();
                ahz_02.y(ry2.getX(), ry2.getY(), ry2.wk());
            }
            this.bTD.put(ack_12, ahz_02);
        }
    }

    public void l(ack_1 ack_12) {
        ahz_0 ahz_02;
        if (this.aiW() && (ahz_02 = (ahz_0)this.bTD.get(ack_12)) != null) {
            ahz_02.clear();
            this.bTD.remove(ack_12);
        }
    }

    private String iu(int n2) {
        return bTz + n2;
    }

    public void a(ry ry2, qs_2 qs_22) {
        for (int j = 0; j < 2; ++j) {
            this.c(j, ry2);
        }
    }

    public void w(ry ry2) {
        for (int j = 0; j < 2; ++j) {
            this.d(j, ry2);
        }
    }

    public void c(int n2, ry ry2) {
        if (!this.aiW()) {
            return;
        }
        DisplayedScreenElement displayedScreenElement = aga_0.aSG().b(ry2.getX(), ry2.getY(), ry2.wk(), pq_2.abX);
        if (displayedScreenElement == null) {
            return;
        }
        wn_2.Dj().a(displayedScreenElement.aua(), this.iu(n2));
    }

    public void d(int n2, ry ry2) {
        if (!this.aiW()) {
            return;
        }
        DisplayedScreenElement displayedScreenElement = aga_0.aSG().b(ry2.getX(), ry2.getY(), ry2.wk(), pq_2.abX);
        if (displayedScreenElement == null) {
            return;
        }
        wn_2.Dj().b(displayedScreenElement.aua(), this.iu(n2));
    }
}

