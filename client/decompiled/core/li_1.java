/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;
import java.util.ArrayList;

/*
 * Renamed from LI
 */
public class li_1
extends a_0
implements qn_1 {
    final /* synthetic */ rf_0 aaC;

    public li_1(rf_0 rf_02) {
        this.aaC = rf_02;
    }

    public boolean aO() {
        return false;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        agj_1 agj_12;
        if (rf_0.c(this.aaC)) {
            agj_12 = this.aaC.getContentIdealSize(rf_0.d(this.aaC), rf_0.e(this.aaC), rf_0.f(this.aaC), rf_0.g(this.aaC));
        } else {
            int n2;
            int n3 = 30;
            int n4 = 30;
            int n5 = rf_0.h(this.aaC) ? rf_0.i(this.aaC) : 1;
            int n6 = n2 = rf_0.h(this.aaC) ? 1 : rf_0.i(this.aaC);
            if (rf_0.j(this.aaC) != null) {
                n3 = rf_0.j((rf_0)this.aaC).width * n5;
                n4 = rf_0.j((rf_0)this.aaC).height * n2;
            }
            if (rf_0.k(this.aaC) == aDM.dyV || rf_0.l(this.aaC)) {
                if (rf_0.h(this.aaC)) {
                    n4 += rf_0.b((rf_0)this.aaC).getMinSize().height;
                } else {
                    n3 += rf_0.b((rf_0)this.aaC).getMinSize().width;
                }
            }
            agj_12 = new agj_1(n3, n4);
        }
        return agj_12;
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        agj_1 agj_12;
        if (rf_0.c(this.aaC)) {
            agj_12 = this.aaC.getContentIdealSize(rf_0.d(this.aaC), rf_0.e(this.aaC), rf_0.f(this.aaC), rf_0.g(this.aaC));
        } else {
            int n2;
            int n3 = 30;
            int n4 = 30;
            int n5 = rf_0.h(this.aaC) ? rf_0.i(this.aaC) : 1;
            int n6 = n2 = rf_0.h(this.aaC) ? 1 : rf_0.i(this.aaC);
            if (rf_0.j(this.aaC) != null) {
                n3 = rf_0.j((rf_0)this.aaC).width * n5;
                n4 = rf_0.j((rf_0)this.aaC).height * n2;
            }
            if (rf_0.k(this.aaC) == aDM.dyV || rf_0.l(this.aaC)) {
                if (rf_0.h(this.aaC)) {
                    n4 += rf_0.b((rf_0)this.aaC).getPrefSize().height;
                } else {
                    n3 += rf_0.b((rf_0)this.aaC).getPrefSize().width;
                }
            }
            agj_12 = new agj_1(n3, n4);
        }
        return agj_12;
    }

    public void ah(boolean bl2) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        if (rf_0.m(this.aaC) == null) {
            return;
        }
        rf_0.a(this.aaC, true);
        int n7 = this.aaC.cLZ.getContentWidth();
        int n8 = this.aaC.cLZ.getContentHeight();
        int n9 = 0;
        int n10 = 0;
        int n11 = rf_0.j(this.aaC).awi() != -1.0f ? (int)Math.round((double)((float)n7 * rf_0.j(this.aaC).awi()) / 100.0) : rf_0.j((rf_0)this.aaC).width;
        int n12 = n6 = rf_0.j(this.aaC).awj() != -1.0f ? (int)Math.round((double)((float)n8 * rf_0.j(this.aaC).awj()) / 100.0) : rf_0.j((rf_0)this.aaC).height;
        if (rf_0.n(this.aaC)) {
            ArrayList arrayList = this.aaC.getItems();
            if (arrayList != null && arrayList.size() > 0) {
                if (rf_0.h(this.aaC)) {
                    n11 = n7 / arrayList.size();
                    n6 = n8;
                } else {
                    n6 = n8 / arrayList.size();
                    n11 = n7;
                }
            } else {
                n6 = n8;
                n11 = n7;
            }
        }
        if (n6 == 0 || n11 == 0) {
            return;
        }
        float f = rf_0.a(this.aaC, n8, n6);
        float f2 = rf_0.b(this.aaC, n7, n11);
        int n13 = 0;
        int n14 = 0;
        ArrayList arrayList = this.aaC.getItems();
        int n15 = n5 = arrayList == null ? 0 : arrayList.size();
        if (n5 != 0 && rf_0.c(this.aaC)) {
            if (rf_0.d(this.aaC) == 1) {
                n13 = Math.max(0, (int)Math.floor((float)(n7 - n11 * n5) / (float)(n5 + 1)));
            } else if (rf_0.e(this.aaC) == 1) {
                n14 = Math.max(0, (int)Math.floor((float)(n8 - n6 * n5) / (float)(n5 + 1)));
            }
        }
        switch (rf_0.k(this.aaC)) {
            case dyW: {
                rf_0.b(this.aaC, false);
                break;
            }
            case dyV: {
                rf_0.b(this.aaC, true);
                break;
            }
            case dyU: {
                if (rf_0.h(this.aaC) && (float)(rf_0.b(this.aaC, f) + rf_0.o(this.aaC)) > f2 || !rf_0.h(this.aaC) && (float)(rf_0.c(this.aaC, f2) + rf_0.o(this.aaC)) > f) {
                    rf_0.b(this.aaC, true);
                    break;
                }
                rf_0.b(this.aaC, false);
                break;
            }
        }
        if (rf_0.l(this.aaC)) {
            int n16;
            rf_0.b(this.aaC).setVisible(true);
            if (rf_0.h(this.aaC)) {
                n16 = rf_0.b((rf_0)this.aaC).getPrefSize().height;
                if (bl2) {
                    rf_0.b(this.aaC).setSize(n7, n16);
                }
                n8 -= n16;
                if (!rf_0.p(this.aaC)) {
                    if (bl2) {
                        rf_0.b(this.aaC).setY(n10);
                    }
                    n10 += n16;
                } else if (bl2) {
                    rf_0.b(this.aaC).setY(n10 + n8);
                }
                if (bl2) {
                    rf_0.b(this.aaC).setX(n9);
                }
                n6 = rf_0.j(this.aaC).awj() != -1.0f ? (int)Math.round((double)((float)n8 * rf_0.j(this.aaC).awj()) / 100.0) : rf_0.j((rf_0)this.aaC).height;
                f = rf_0.a(this.aaC, n8, n6);
            } else {
                n16 = rf_0.b((rf_0)this.aaC).getPrefSize().width;
                if (bl2) {
                    rf_0.b(this.aaC).setSize(n16, n8);
                }
                n7 -= n16;
                if (!rf_0.p(this.aaC)) {
                    if (bl2) {
                        rf_0.b(this.aaC).setX(n9 + n7);
                    }
                } else {
                    if (bl2) {
                        rf_0.b(this.aaC).setX(n9);
                    }
                    n9 += n16;
                }
                if (bl2) {
                    rf_0.b(this.aaC).setY(n10);
                }
                n11 = rf_0.j(this.aaC).awi() != -1.0f ? (int)Math.round((double)((float)n7 * rf_0.j(this.aaC).awi()) / 100.0) : rf_0.j((rf_0)this.aaC).width;
                f2 = rf_0.b(this.aaC, n7, n11);
            }
        } else if (bl2) {
            rf_0.b(this.aaC).setVisible(false);
        }
        float f3 = this.aaC.bJD - (float)Math.floor(this.aaC.bJD);
        int n17 = rf_0.h(this.aaC) ? (int)((float)n11 * f3) : 0;
        int n18 = !rf_0.h(this.aaC) ? (int)((float)n6 * f3) : 0;
        int n19 = n9;
        n10 = n14 == 0 ? (rf_0.q(this.aaC).equals((Object)BT.aKa) ? (int)((float)n10 + (float)n6 * (f - 1.0f)) : (rf_0.q(this.aaC).equals((Object)BT.aJX) && rf_0.h(this.aaC) ? (int)((float)n10 + ((float)(n8 - n6) - ((float)n8 - (float)n6 * f) / 2.0f)) : (n10 += n8 - n6 + n18))) : (n10 += n8 - n6 + n18 - n14);
        int n20 = (int)Math.ceil(f) + (rf_0.h(this.aaC) ? 0 : 1);
        int n21 = (int)Math.ceil(f2) + (rf_0.h(this.aaC) ? 1 : 0);
        int n22 = n4 = rf_0.h(this.aaC) ? n20 - 1 + (n21 - 1) * (int)Math.floor(f) + 1 : (n20 - 1) * (int)Math.floor(f2) + (n21 - 1) + 1;
        if (n4 > this.aaC.dS.size()) {
            this.aaC.dS.ensureCapacity(n4);
            rf_0.r(this.aaC).ensureCapacity(n4);
            for (n3 = this.aaC.dS.size(); n3 < n4; ++n3) {
                qa_1 qa_12 = new qa_1();
                qa_12.b();
                qa_12.setCollection(this.aaC);
                qa_12.setNonBlocking(this.aaC.dyc);
                qa_12.setRendererManager(rf_0.m(this.aaC));
                qa_12.setEnableDND(rf_0.s(this.aaC));
                qa_12.setEnabled(this.aaC.OD);
                qa_12.a(qe_1.bFB, new Fn(this), false);
                qa_12.a(qe_1.bFx, new Fq(this), false);
                qa_12.a(qe_1.bFy, new yC(this), false);
                this.aaC.dS.add(qa_12);
                this.a((na_1)qa_12);
                rf_0.r(this.aaC).add(new Point());
            }
        }
        n3 = (int)Math.ceil(rf_0.y(this.aaC)) + (rf_0.h(this.aaC) ? 0 : 1);
        int n23 = (int)Math.ceil(rf_0.z(this.aaC)) + (rf_0.h(this.aaC) ? 1 : 0);
        if (n23 > n21 || n3 > n20) {
            for (n2 = this.aaC.dS.size() - 1; n2 >= 0 && n2 >= n21 * n20; --n2) {
                qa_1 qa_13 = (qa_1)this.aaC.dS.remove(n2);
                if (qa_13 == rf_0.x(this.aaC)) {
                    rf_0.b(this.aaC, null);
                }
                this.aaC.k(qa_13);
            }
        }
        for (n2 = 0; n2 < n20; ++n2) {
            n9 = n13 == 0 ? (rf_0.q(this.aaC).equals((Object)BT.aJY) ? n19 + n7 - (int)((float)n11 * f2) : (rf_0.q(this.aaC).equals((Object)BT.aJX) ? n19 + (n7 - (int)((float)n11 * f2)) / 2 : n19 - n17)) : n19 - n17 + n13;
            for (int j = 0; j < n21; ++j) {
                int n24 = rf_0.h(this.aaC) ? n2 + j * (int)Math.floor(f) : n2 * (int)Math.floor(f2) + j;
                qa_1 qa_14 = (qa_1)this.aaC.dS.get(n24);
                if (qa_14 != null) {
                    qa_14.setSize(n11, n6);
                    ((Point)rf_0.r(this.aaC).get(n24)).setLocation(n9, n10);
                    qa_14.setPosition(n9, n10, !this.aaC.dyr);
                }
                n9 += n11 + n14;
            }
            n10 -= n6 + n14;
        }
        rf_0.d(this.aaC, f2);
        rf_0.e(this.aaC, f);
        if (bl2 && rf_0.l(this.aaC)) {
            rf_0.A(this.aaC);
        }
        rf_0.a(this.aaC, false);
        float f4 = this.aaC.bJD;
        rf_0.B(this.aaC);
        if ((double)Math.abs(f4 - this.aaC.bJD) > 1.0E-4) {
            this.aaC.setOffset(this.aaC.bJD);
        }
        rf_0.a(this.aaC, bl2 ? 1 : 0);
        this.setNeedsToPostProcess();
    }

    public void a(aht_1 aht_12) {
        this.ah(true);
    }
}

