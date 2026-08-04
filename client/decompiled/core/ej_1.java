/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from EJ
 */
public class ej_1
extends a_0 {
    final /* synthetic */ aaz_2 aTO;

    public ej_1(aaz_2 aaz_22) {
        this.aTO = aaz_22;
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        int n2 = 0;
        int n3 = 0;
        int n4 = aaz_2.a(this.aTO) == null ? 0 : aaz_2.a(this.aTO).size();
        int n5 = 1;
        if (aaz_2.b(this.aTO) >= 0 || aaz_2.c(this.aTO) >= 0) {
            n5 = ej_0.e(n4, aaz_2.c(this.aTO), aaz_2.b(this.aTO));
        }
        for (int j = aaz_2.d(this.aTO).size() - 1; j >= 0; --j) {
            n3 += ((ee_0)aaz_2.d(this.aTO).get(j)).getCellWidth();
            aqq_0 aqq_02 = (aqq_0)aaz_2.e(this.aTO).get(j);
            if (aqq_02 != null) {
                n2 = Math.max(n2, aqq_02.getPrefSize().height);
                continue;
            }
            aaz_2.XF().warn((Object)"Un bouton de colonne n'a pas \u00e9t\u00e9 initialis\u00e9 correctement");
        }
        agj_1 agj_12 = aaz_2.f(this.aTO).getPrefSize();
        switch (aaz_2.g(this.aTO)) {
            case dyW: {
                break;
            }
            case dyV: {
                n3 += agj_12.width;
                break;
            }
            case dyU: {
                if (n5 >= n4) break;
                n3 += agj_12.width;
            }
        }
        return new agj_1(n3, n2 += aaz_2.h(this.aTO) * n5);
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        return this.getContentPreferedSize(aht_12);
    }

    public void a(aht_1 aht_12) {
        int n2;
        int n3;
        int n4;
        int n5;
        ee_0 ee_02;
        int n6;
        int n7;
        aaz_2.a(this.aTO, true);
        int n8 = aht_12.getAppearance().getContentHeight();
        int n9 = aaz_2.d(this.aTO).size();
        if (n9 == 0) {
            return;
        }
        int n10 = n8 / aaz_2.h(this.aTO);
        if (n10 > (n7 = aaz_2.i(this.aTO).size() / n9)) {
            aaz_2.i(this.aTO).ensureCapacity(n9 * n10);
            aaz_2.j(this.aTO).ensureCapacity(n10);
            for (n6 = n7; n6 < n10; ++n6) {
                apd_0 apd_02 = new apd_0();
                apd_02.b();
                aaz_2.j(this.aTO).add(apd_02);
                for (int j = 0; j < n9; ++j) {
                    ee_02 = (ee_0)aaz_2.d(this.aTO).get(j);
                    qa_1 qa_12 = new qa_1();
                    qa_12.b();
                    qa_12.setCollection(this.aTO);
                    qa_12.setNonBlocking(this.aTO.dyc);
                    qa_12.setRendererManager(ee_02.getRendererManager());
                    qa_12.setEnableDND(aaz_2.k(this.aTO));
                    qa_12.setEnabled(this.aTO.OD);
                    qa_12.setCanBeCloned(false);
                    qa_12.a(qe_1.bFB, new qo_0(this), false);
                    qa_12.a(qe_1.bFx, new qu(this), false);
                    qa_12.a(qe_1.bFy, new qq(this), false);
                    aaz_2.i(this.aTO).add(qa_12);
                    this.a((na_1)qa_12);
                }
            }
        } else if (n10 < n7) {
            Object object;
            int n11;
            n6 = n9 * (n7 - n10);
            for (n11 = n6 - 1; n11 >= 0; --n11) {
                object = (qa_1)aaz_2.i(this.aTO).remove(aaz_2.i(this.aTO).size() - 1);
                this.aTO.k((na_1)object);
            }
            for (n11 = n7 - n10 - 1; n11 >= 0; --n11) {
                object = (apd_0)aaz_2.j(this.aTO).remove(aaz_2.j(this.aTO).size() - 1);
                ((aaH)object).j();
            }
        }
        n6 = 0;
        for (n5 = aaz_2.d(this.aTO).size() - 1; n5 >= 0; --n5) {
            aqq_0 aqq_02 = (aqq_0)aaz_2.e(this.aTO).get(n5);
            if (aqq_02 != null) {
                n6 = Math.max(n6, aqq_02.getPrefSize().height);
                continue;
            }
            aaz_2.yy().warn((Object)"Un bouton de colonne n'a pas \u00e9t\u00e9 initialis\u00e9 correctement");
        }
        n5 = 0;
        for (n4 = 0; n4 < n9; ++n4) {
            ee_02 = (ee_0)aaz_2.d(this.aTO).get(n4);
            int n12 = n8 - n6;
            aqq_0 aqq_03 = (aqq_0)aaz_2.e(this.aTO).get(n4);
            aqq_03.setSize(ee_02.getCellWidth(), n6);
            aqq_03.setPosition(n5, n12);
            n12 -= aaz_2.h(this.aTO);
            for (int j = 0; j < n10; ++j) {
                qa_1 qa_13 = aaz_2.a(this.aTO, j, n4);
                if (qa_13 == null) {
                    aaz_2.apG().warn((Object)("Impossible de trouver un renderableContainer \u00e0 la ligne " + j + " et \u00e0 la colonne " + n4));
                    continue;
                }
                qa_13.setSize(ee_02.getCellWidth(), aaz_2.h(this.aTO));
                qa_13.setPosition(n5, n12);
                n12 -= aaz_2.h(this.aTO);
            }
            n5 += ee_02.getCellWidth();
        }
        n4 = n8 - n6 - aaz_2.h(this.aTO);
        for (n3 = 0; n3 < n10; ++n3) {
            ((apd_0)aaz_2.j(this.aTO).get(n3)).f(0, n4, n5, aaz_2.h(this.aTO), 0, 0, 0, 0);
            n4 -= aaz_2.h(this.aTO);
        }
        switch (aaz_2.g(this.aTO)) {
            case dyW: {
                aaz_2.b(this.aTO, false);
                break;
            }
            case dyV: {
                aaz_2.b(this.aTO, true);
                break;
            }
            case dyU: {
                aaz_2.b(this.aTO, aaz_2.a(this.aTO).size() > aaz_2.i(this.aTO).size() / aaz_2.d(this.aTO).size());
            }
        }
        if (aaz_2.r(this.aTO)) {
            aaz_2.f(this.aTO).setVisible(true);
            n3 = aaz_2.f((aaz_2)this.aTO).getPrefSize().width;
            aaz_2.f(this.aTO).setSize(n3, n8);
            aaz_2.f(this.aTO).setPosition(n5, 0);
        } else {
            aaz_2.f(this.aTO).setVisible(false);
        }
        this.Ox();
        n3 = aaz_2.a(this.aTO).size() - n10;
        int n13 = n2 = n3 < 0 ? 0 : ej_0.e(aaz_2.s(this.aTO), 0, n3);
        if (n2 != aaz_2.s(this.aTO)) {
            this.aTO.setOffset(n2);
        }
        if (n10 != n7) {
            aaz_2.t(this.aTO);
        }
        aaz_2.a(this.aTO, false);
    }

    private void Ox() {
        if (aaz_2.r(this.aTO)) {
            int n2 = aaz_2.a(this.aTO).size() - aaz_2.i(this.aTO).size() / aaz_2.d(this.aTO).size();
            if (n2 > 0) {
                if (!aaz_2.f(this.aTO).getEnabled()) {
                    aaz_2.f(this.aTO).setEnabled(true);
                }
                aaz_2.f(this.aTO).setButtonJump(1.0f / (float)n2);
                aaz_2.f(this.aTO).getSlider().setSliderSize((float)(aaz_2.i(this.aTO).size() / aaz_2.d(this.aTO).size()) / (float)aaz_2.a(this.aTO).size());
            } else {
                aaz_2.f(this.aTO).setButtonJump(0.0f);
                aaz_2.f(this.aTO).setEnabled(false);
            }
        }
    }
}

