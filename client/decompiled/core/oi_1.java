/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;

/*
 * Renamed from oI
 */
public class oi_1
extends a_0
implements qn_1 {
    final /* synthetic */ rf_0 aaC;

    public oi_1(rf_0 rf_02) {
        this.aaC = rf_02;
    }

    public boolean aO() {
        return false;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        int n2 = (int)Math.ceil((float)this.aaC.ec.size() / (float)rf_0.C(this.aaC));
        int n3 = (int)Math.ceil(rf_0.j(this.aaC).getWidth() / 2.0 * (double)(n2 + rf_0.C(this.aaC)));
        int n4 = (int)Math.ceil(rf_0.j(this.aaC).getHeight() / 2.0 * (double)(n2 + rf_0.C(this.aaC)));
        return new agj_1(n3, n4);
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        int n2 = (int)Math.ceil((float)this.aaC.ec.size() / (float)rf_0.C(this.aaC));
        int n3 = (int)Math.ceil(rf_0.j(this.aaC).getWidth() / 2.0 * (double)(n2 + rf_0.C(this.aaC)));
        int n4 = (int)Math.ceil(rf_0.j(this.aaC).getHeight() / 2.0 * (double)(n2 + rf_0.C(this.aaC)));
        return new agj_1(n3, n4);
    }

    public void ah(boolean bl2) {
        qa_1 qa_12;
        int n2;
        if (rf_0.m(this.aaC) == null) {
            return;
        }
        rf_0.a(this.aaC, true);
        int n3 = this.aaC.cLZ.getContentWidth();
        int n4 = this.aaC.cLZ.getContentHeight();
        int n5 = rf_0.j((rf_0)this.aaC).width;
        int n6 = rf_0.j((rf_0)this.aaC).height;
        rf_0.b(this.aaC).setVisible(false);
        int n7 = (int)Math.min(Math.floor((float)(n3 / n5) * 2.0f), Math.floor((float)(n4 / n6) * 2.0f)) - rf_0.C(this.aaC) + 1;
        int n8 = n7 * rf_0.C(this.aaC);
        if (n8 > this.aaC.dS.size()) {
            this.aaC.dS.ensureCapacity(n8);
            rf_0.r(this.aaC).ensureCapacity(n8);
            for (n2 = this.aaC.dS.size(); n2 < n8; ++n2) {
                qa_12 = new qa_1();
                qa_12.b();
                qa_12.setCollection(this.aaC);
                qa_12.setNonBlocking(this.aaC.dyc);
                qa_12.setRendererManager(rf_0.m(this.aaC));
                qa_12.setEnableDND(rf_0.s(this.aaC));
                qa_12.setEnabled(this.aaC.OD);
                qa_12.getAppearance().setShape(kx_1.FS);
                qa_12.a(qe_1.bFB, new ast_0(this), false);
                qa_12.a(qe_1.bFx, new ass(this), false);
                qa_12.a(qe_1.bFy, new asp_0(this), false);
                this.aaC.dS.add(qa_12);
                this.a((na_1)qa_12);
                rf_0.r(this.aaC).add(new Point());
            }
        }
        for (n2 = this.aaC.dS.size() - 1; n2 >= 0 && n2 >= n8; --n2) {
            qa_12 = (qa_1)this.aaC.dS.remove(n2);
            if (qa_12 == rf_0.x(this.aaC)) {
                rf_0.b(this.aaC, null);
            }
            this.aaC.k(qa_12);
        }
        for (n2 = 0; n2 < rf_0.C(this.aaC); ++n2) {
            int n9;
            int n10;
            if (n2 < rf_0.C(this.aaC) / 2) {
                n10 = 0;
                n9 = rf_0.D(this.aaC) ? -n6 * (2 * n2 - rf_0.C(this.aaC) + 1) : n6 * (2 * n2 - rf_0.C(this.aaC)) + n4;
            } else {
                n9 = 0;
                n10 = rf_0.D(this.aaC) ? (int)((float)n6 / 2.0f * (float)(2 * n2 - rf_0.C(this.aaC) + 1)) : (int)((float)n6 / 2.0f * (float)(2 * n2 - rf_0.C(this.aaC) - 1)) + n4;
            }
            for (int j = 0; j < n7; ++j) {
                int n11 = (rf_0.C(this.aaC) - n2 - 1) * n7 + j;
                qa_1 qa_13 = (qa_1)this.aaC.dS.get(n11);
                qa_13.setPosition(n9, n10);
                qa_13.setSize(n5, n6);
                n9 += n5 / 2;
                n10 += rf_0.D(this.aaC) ? n6 / 2 : -n6 / 2;
            }
        }
        rf_0.a(this.aaC, false);
        float f = this.aaC.bJD;
        rf_0.B(this.aaC);
        if ((double)Math.abs(f - this.aaC.bJD) > 1.0E-4) {
            this.aaC.setOffset(this.aaC.bJD);
        }
        rf_0.a(this.aaC, bl2 ? 1 : 0);
        this.setNeedsToPostProcess();
    }

    public void a(aht_1 aht_12) {
        this.ah(true);
    }
}

