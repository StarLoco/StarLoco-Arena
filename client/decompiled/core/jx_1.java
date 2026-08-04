/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from jX
 */
class jx_1
extends a_0 {
    final /* synthetic */ ud_1 Dg;

    private jx_1(ud_1 ud_12) {
        this.Dg = ud_12;
    }

    public boolean aO() {
        return false;
    }

    private int a(boolean bl2, boolean bl3) {
        int n2 = 0;
        if (bl3) {
            for (dl_1 dl_12 : ud_1.c(this.Dg)) {
                n2 = Math.max(bl2 ? dl_12.getMinSize().width : dl_12.getMinSize().height, n2);
            }
        } else {
            for (dl_1 dl_13 : ud_1.c(this.Dg)) {
                n2 = Math.max(bl2 ? dl_13.getPrefSize().width : dl_13.getPrefSize().height, n2);
            }
        }
        return n2;
    }

    private int a(ArrayList arrayList, boolean bl2, boolean bl3) {
        int n2 = 0;
        if (bl3) {
            for (dl_1 dl_12 : arrayList) {
                n2 += bl2 ? dl_12.getMinSize().width : dl_12.getMinSize().height;
            }
        } else {
            for (dl_1 dl_13 : arrayList) {
                n2 += bl2 ? dl_13.getPrefSize().width : dl_13.getPrefSize().height;
            }
        }
        return n2;
    }

    private boolean a(ArrayList arrayList, int n2, int n3, boolean bl2) {
        ud_1.d(this.Dg).Am();
        dl_1 dl_12 = null;
        int n4 = 0;
        int n5 = 0;
        for (dl_1 dl_13 : arrayList) {
            int n6 = bl2 ? dl_13.getPrefSize().width : dl_13.getPrefSize().height;
            ajb_1 ajb_12 = (ajb_1)ud_1.e(this.Dg).get(dl_13);
            if (n4 < n2 || n5 > n3) {
                dl_13.setVisible(false);
            } else {
                dl_13.setVisible(ajb_12.isVisible());
                if ((n5 += n6) > n3) {
                    dl_12 = dl_13;
                }
            }
            ++n4;
        }
        if (ud_1.f(this.Dg) != null) {
            if (dl_12 != null && dl_12 == ud_1.f(this.Dg)) {
                ud_1.a(this.Dg, Math.min(ud_1.c(this.Dg).size() - 1, ud_1.b(this.Dg) + 1));
                return this.a(arrayList, ud_1.b(this.Dg), n3, bl2);
            }
            ud_1.a(this.Dg, null);
        }
        return n5 <= n3;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        agj_1 agj_12 = new agj_1();
        ArrayList arrayList = ud_1.c(this.Dg);
        switch (ud_1.g(this.Dg)) {
            case aJs: 
            case aJt: {
                if (arrayList.size() > 0) {
                    agj_12.setHeight(((dl_1)arrayList.get((int)0)).getMinSize().height);
                }
                agj_12.setWidth(Math.min(this.a(true, true), this.a(arrayList, true, true)));
                break;
            }
            case aJv: 
            case aJu: {
                if (arrayList.size() > 0) {
                    agj_12.setWidth(((dl_1)arrayList.get((int)0)).getMinSize().width);
                }
                agj_12.setHeight(Math.min(this.a(false, true), this.a(arrayList, false, true)));
            }
        }
        return agj_12;
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        agj_1 agj_12 = new agj_1();
        ArrayList arrayList = ud_1.c(this.Dg);
        switch (ud_1.g(this.Dg)) {
            case aJs: 
            case aJt: {
                if (arrayList.size() > 0) {
                    agj_12.setHeight(((dl_1)arrayList.get((int)0)).getPrefSize().height);
                }
                agj_12.setWidth(Math.min(this.a(true, false), this.a(arrayList, true, false)));
                break;
            }
            case aJv: 
            case aJu: {
                if (arrayList.size() > 0) {
                    agj_12.setWidth(((dl_1)arrayList.get((int)0)).getPrefSize().width);
                }
                agj_12.setHeight(Math.min(this.a(false, false), this.a(arrayList, false, false)));
            }
        }
        return agj_12;
    }

    public void a(aht_1 aht_12) {
        ud_1.a(this.Dg, 0.0);
        agj_1 agj_12 = new agj_1();
        agj_12.setWidth(this.Dg.cLZ.getContentWidth());
        agj_12.setHeight(this.Dg.cLZ.getContentHeight());
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        ArrayList arrayList = ud_1.h(this.Dg);
        ud_1.i(this.Dg).setSize(ud_1.i(this.Dg).getPrefSize());
        ud_1.j(this.Dg).setSize(ud_1.j(this.Dg).getPrefSize());
        switch (ud_1.g(this.Dg)) {
            case aJs: 
            case aJt: {
                ud_1.b(this.Dg, agj_12.getWidth() < (double)this.a(arrayList, true, false));
                break;
            }
            case aJv: 
            case aJu: {
                ud_1.b(this.Dg, agj_12.getHeight() < (double)this.a(arrayList, false, false));
            }
        }
        if (ud_1.k(this.Dg)) {
            ud_1.i(this.Dg).setVisible(true);
            ud_1.j(this.Dg).setVisible(true);
        } else {
            ud_1.i(this.Dg).setVisible(false);
            ud_1.j(this.Dg).setVisible(false);
        }
        boolean bl2 = true;
        switch (ud_1.g(this.Dg)) {
            case aJs: 
            case aJt: {
                if (ud_1.k(this.Dg)) {
                    bl2 = this.a(ud_1.h(this.Dg), ud_1.b(this.Dg), (int)agj_12.getWidth() - ud_1.i(this.Dg).getWidth() - ud_1.j(this.Dg).getHeight(), true);
                } else {
                    for (dl_1 dl_12 : ud_1.c(this.Dg)) {
                        ajb_1 ajb_12 = (ajb_1)ud_1.e(this.Dg).get(dl_12);
                        dl_12.setVisible(ajb_12.isVisible());
                    }
                }
                ud_1.d(this.Dg).setSize((int)ud_1.d(this.Dg).getPrefSize().getWidth(), (int)ud_1.d(this.Dg).getPrefSize().getHeight());
                ud_1.l(this.Dg).setSize((int)agj_12.getWidth() - (ud_1.k(this.Dg) ? ud_1.i(this.Dg).getWidth() + ud_1.j(this.Dg).getHeight() : 0), (int)ud_1.d(this.Dg).getPrefSize().getHeight());
                ud_1.m(this.Dg).setSize((int)agj_12.getWidth(), ud_1.d(this.Dg).getHeight());
                ud_1.n(this.Dg).setSize((int)agj_12.getWidth(), (int)ud_1.n(this.Dg).getPrefSize().getHeight());
                if (ud_1.o(this.Dg) == null) break;
                ud_1.o(this.Dg).setSize((int)agj_12.getWidth(), (int)agj_12.getHeight() - ud_1.d(this.Dg).getHeight() - ud_1.n(this.Dg).getHeight());
                break;
            }
            case aJv: 
            case aJu: {
                if (ud_1.k(this.Dg)) {
                    bl2 = this.a(ud_1.c(this.Dg), ud_1.b(this.Dg), (int)agj_12.getHeight() - ud_1.i(this.Dg).getHeight() - ud_1.j(this.Dg).getHeight(), false);
                } else {
                    for (dl_1 dl_13 : ud_1.c(this.Dg)) {
                        ajb_1 ajb_13 = (ajb_1)ud_1.e(this.Dg).get(dl_13);
                        dl_13.setVisible(ajb_13.isVisible());
                    }
                }
                ud_1.d(this.Dg).setSize((int)ud_1.d(this.Dg).getPrefSize().getWidth(), (int)ud_1.d(this.Dg).getPrefSize().getHeight());
                ud_1.l(this.Dg).setSize((int)ud_1.d(this.Dg).getPrefSize().getWidth(), (int)agj_12.getHeight() - (ud_1.k(this.Dg) ? ud_1.i(this.Dg).getHeight() + ud_1.j(this.Dg).getHeight() : 0));
                ud_1.m(this.Dg).setSize(ud_1.d(this.Dg).getWidth(), (int)agj_12.getHeight());
                ud_1.n(this.Dg).setSize((int)ud_1.n(this.Dg).getPrefSize().getWidth(), (int)agj_12.getHeight());
                if (ud_1.o(this.Dg) == null) break;
                ud_1.o(this.Dg).setSize((int)agj_12.getWidth() - ud_1.d(this.Dg).getWidth() - ud_1.n(this.Dg).getWidth(), (int)agj_12.getHeight());
            }
        }
        ud_1.i(this.Dg).setEnabled(!bl2);
        ud_1.j(this.Dg).setEnabled(ud_1.b(this.Dg) != 0);
        switch (ud_1.g(this.Dg)) {
            case aJs: {
                n2 = 0;
                n4 = 0;
                n6 = 0;
                n5 = n3 = this.Dg.cLZ.getContentHeight() - ud_1.d(this.Dg).getHeight();
                n13 = n3;
                n11 = n3;
                n7 = n3 - ud_1.n(this.Dg).getHeight();
                if (ud_1.k(this.Dg)) {
                    if (ud_1.p(this.Dg)) {
                        if (ud_1.q(this.Dg)) {
                            n12 = n2;
                            n10 = n12 + ud_1.j(this.Dg).getWidth();
                            n2 += ud_1.j(this.Dg).getWidth() + ud_1.i(this.Dg).getWidth();
                        } else {
                            n10 = this.Dg.cLZ.getContentWidth() - ud_1.i(this.Dg).getWidth();
                            n12 = n10 - ud_1.j(this.Dg).getWidth();
                        }
                    } else {
                        n12 = n2;
                        n2 += ud_1.j(this.Dg).getWidth();
                        n10 = this.Dg.cLZ.getContentWidth() - ud_1.i(this.Dg).getWidth();
                    }
                } else if (ud_1.r(this.Dg).equals((Object)BP.aJB)) {
                    n2 += (int)((agj_12.getWidth() - (double)ud_1.d(this.Dg).getWidth()) / 2.0);
                } else if (!ud_1.r(this.Dg).equals((Object)BP.aJA)) {
                    n2 += (int)(agj_12.getWidth() - (double)ud_1.d(this.Dg).getWidth());
                }
                n8 = 0;
                n9 = 0;
                break;
            }
            case aJt: {
                n2 = 0;
                n4 = 0;
                n6 = 0;
                n3 = 0;
                n5 = 0;
                n13 = 0;
                n11 = 0;
                n7 = n3 + ud_1.d(this.Dg).getHeight();
                if (ud_1.k(this.Dg)) {
                    if (ud_1.p(this.Dg)) {
                        if (ud_1.q(this.Dg)) {
                            n12 = n2;
                            n10 = n12 + ud_1.j(this.Dg).getWidth();
                            n2 += ud_1.j(this.Dg).getWidth() + ud_1.i(this.Dg).getWidth();
                        } else {
                            n10 = this.Dg.cLZ.getContentWidth() - ud_1.i(this.Dg).getWidth();
                            n12 = n10 - ud_1.j(this.Dg).getWidth();
                        }
                    } else {
                        n12 = n2;
                        n2 += ud_1.j(this.Dg).getWidth();
                        n10 = this.Dg.cLZ.getContentWidth() - ud_1.i(this.Dg).getWidth();
                    }
                } else if (ud_1.r(this.Dg).equals((Object)BP.aJB)) {
                    n2 += (int)((agj_12.getWidth() - (double)ud_1.d(this.Dg).getWidth()) / 2.0);
                } else if (!ud_1.r(this.Dg).equals((Object)BP.aJA)) {
                    n2 += (int)(agj_12.getWidth() - (double)ud_1.d(this.Dg).getWidth());
                }
                n8 = 0;
                n9 = n7 + ud_1.n(this.Dg).getHeight();
                break;
            }
            case aJu: {
                n4 = n2 = this.Dg.cLZ.getContentWidth() - ud_1.d(this.Dg).getWidth();
                n12 = n2;
                n10 = n2;
                n3 = 0;
                n5 = 0;
                n7 = 0;
                n6 = n2 - ud_1.n(this.Dg).getWidth();
                if (ud_1.k(this.Dg)) {
                    if (ud_1.p(this.Dg)) {
                        if (ud_1.q(this.Dg)) {
                            n13 = this.Dg.cLZ.getContentHeight() - ud_1.j(this.Dg).getHeight();
                            n11 = n13 - ud_1.i(this.Dg).getHeight();
                        } else {
                            n11 = n3;
                            n13 = ud_1.i(this.Dg).getWidth();
                            n3 += ud_1.j(this.Dg).getHeight() + ud_1.i(this.Dg).getHeight();
                        }
                    } else {
                        n11 = n3;
                        n3 += ud_1.i(this.Dg).getHeight();
                        n13 = this.Dg.cLZ.getContentHeight() - ud_1.j(this.Dg).getWidth();
                    }
                    ud_1.l(this.Dg).setLocation(n2, n3);
                    ud_1.a(this.Dg, (double)ud_1.d(this.Dg).getHeight() - ud_1.l(this.Dg).getHeight());
                    n3 = (int)((double)n3 - ud_1.s(this.Dg));
                } else if (ud_1.r(this.Dg).equals((Object)BP.aJB)) {
                    n3 += (int)((agj_12.getHeight() - (double)ud_1.d(this.Dg).getHeight()) / 2.0);
                } else if (!ud_1.r(this.Dg).equals((Object)BP.aJy)) {
                    n3 += (int)(agj_12.getHeight() - (double)ud_1.d(this.Dg).getHeight());
                }
                n8 = 0;
                n9 = 0;
                break;
            }
            case aJv: {
                n2 = 0;
                n4 = 0;
                n12 = 0;
                n10 = 0;
                n3 = 0;
                n5 = 0;
                n7 = 0;
                n6 = n2 + ud_1.d(this.Dg).getWidth();
                if (ud_1.k(this.Dg)) {
                    if (ud_1.p(this.Dg)) {
                        if (ud_1.q(this.Dg)) {
                            n13 = this.Dg.cLZ.getContentHeight() - ud_1.j(this.Dg).getHeight();
                            n11 = n13 - ud_1.i(this.Dg).getHeight();
                        } else {
                            n11 = n3;
                            n13 = ud_1.i(this.Dg).getWidth();
                            n3 += ud_1.j(this.Dg).getHeight() + ud_1.i(this.Dg).getHeight();
                        }
                    } else {
                        n11 = n3;
                        n3 += ud_1.i(this.Dg).getHeight();
                        n13 = this.Dg.cLZ.getContentHeight() - ud_1.j(this.Dg).getWidth();
                    }
                    ud_1.l(this.Dg).setLocation(n2, n3);
                    ud_1.a(this.Dg, (double)ud_1.d(this.Dg).getHeight() - ud_1.l(this.Dg).getHeight());
                    n3 = (int)((double)n3 - ud_1.s(this.Dg));
                } else if (ud_1.r(this.Dg).equals((Object)BP.aJB)) {
                    n3 += (int)((agj_12.getHeight() - (double)ud_1.d(this.Dg).getHeight()) / 2.0);
                } else if (!ud_1.r(this.Dg).equals((Object)BP.aJy)) {
                    n3 += (int)(agj_12.getHeight() - (double)ud_1.d(this.Dg).getHeight());
                }
                n8 = n6 + ud_1.n(this.Dg).getWidth();
                n9 = 0;
            }
        }
        ud_1.d(this.Dg).setPosition(n2, n3);
        ud_1.m(this.Dg).setPosition(n4, n5);
        ud_1.n(this.Dg).setPosition(n6, n7);
        ud_1.i(this.Dg).setPosition(n10, n11);
        ud_1.j(this.Dg).setPosition(n12, n13);
        if (ud_1.o(this.Dg) != null) {
            ud_1.o(this.Dg).setPosition(n8, n9);
        }
    }

    /* synthetic */ jx_1(ud_1 ud_12, dz_0 dz_02) {
        this(ud_12);
    }
}

