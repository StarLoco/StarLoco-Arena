/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Kk
 */
public class kk_2
extends a_0 {
    final /* synthetic */ sx bno;

    public kk_2(sx sx2) {
        this.bno = sx2;
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        int n2 = 0;
        int n3 = 0;
        int n4 = sx.a(this.bno);
        int n5 = 1;
        if (sx.b(this.bno) >= 0 || sx.c(this.bno) >= 0) {
            n5 = Math.min(Math.max(sx.b(this.bno), sx.c(this.bno)), n4);
        }
        if (n5 < n4) {
            agj_1 agj_12 = sx.d(this.bno).getPrefSize();
            n3 += agj_12.width;
        }
        return new agj_1(n3, n2 += sx.e(this.bno) * n5);
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        return this.getContentPreferedSize(aht_12);
    }

    public void a(aht_1 aht_12) {
        Object object;
        Object object2;
        int n2;
        sx.a(this.bno, true);
        int n3 = aht_12.getAppearance().getContentHeight();
        int n4 = aht_12.getAppearance().getContentWidth();
        int n5 = Math.min(sx.f(this.bno).size(), n3 / sx.e(this.bno));
        int n6 = sx.g(this.bno).size();
        if (n5 > n6) {
            sx.g(this.bno).ensureCapacity(n5);
            sx.h(this.bno).ensureCapacity(n5);
            for (n2 = n6; n2 < n5; ++n2) {
                apd_0 apd_02 = new apd_0();
                apd_02.b();
                sx.h(this.bno).add(apd_02);
                qa_1 qa_12 = new qa_1();
                qa_12.b();
                qa_12.setNonBlocking(this.bno.dyc);
                qa_12.setRendererManager(sx.i(this.bno));
                qa_12.setEnableDND(sx.j(this.bno));
                qa_12.setEnabled(this.bno.OD);
                qa_12.a(qe_1.bFB, new ayo_0(this, qa_12), false);
                sx.g(this.bno).add(qa_12);
                this.a((na_1)qa_12);
                qa_12.setChildrenAdded(true);
                object2 = this.bno.getStyle();
                object = new StringBuilder("tree");
                if (object2 != null) {
                    ((StringBuilder)object).append((String)object2);
                }
                ((StringBuilder)object).append("$").append("cell");
                qa_12.setStyle(((StringBuilder)object).toString(), true);
            }
        } else if (n5 < n6) {
            n2 = n6 - n5;
            for (int j = n2 - 1; j >= 0; --j) {
                int n7 = sx.g(this.bno).size() - 1;
                object2 = (qa_1)sx.g(this.bno).remove(n7);
                this.bno.k((na_1)object2);
                object = (apd_0)sx.h(this.bno).remove(n7);
                ((aaH)object).j();
            }
        }
        sx.b(this.bno, (n2 = sx.a(this.bno)) > sx.g(this.bno).size());
        if (sx.s(this.bno)) {
            sx.d(this.bno).setVisible(true);
            int n8 = sx.d((sx)this.bno).getPrefSize().width;
            sx.d(this.bno).setSize(n8, n3);
            sx.d(this.bno).setPosition(n4 -= n8, 0);
        } else {
            sx.d(this.bno).setVisible(false);
        }
        int n9 = 0;
        int n10 = n3 - sx.e(this.bno);
        for (int j = 0; j < n5; ++j) {
            object = (qa_1)sx.g(this.bno).get(j);
            if (object == null) {
                sx.yy().warn((Object)("Impossible de trouver un renderableContainer \u00e0 la ligne " + j));
                continue;
            }
            ((adg_2)object).setSize(n4, sx.e(this.bno));
            ((adg_2)object).setPosition(n9, n10);
            ((apd_0)sx.h(this.bno).get(j)).f(0, n10, n9, sx.e(this.bno), 0, 0, 0, 0);
            n10 -= sx.e(this.bno);
        }
        this.Ox();
        sx.a(this.bno, false);
    }

    private void Ox() {
        if (sx.s(this.bno)) {
            int n2 = sx.a(this.bno);
            int n3 = n2 - sx.g(this.bno).size();
            if (n3 > 0) {
                if (!sx.d(this.bno).getEnabled()) {
                    sx.d(this.bno).setEnabled(true);
                }
                sx.d(this.bno).setButtonJump(1.0f / (float)n3);
                sx.d(this.bno).getSlider().setSliderSize((float)sx.g(this.bno).size() / (float)n2);
            } else {
                sx.d(this.bno).setButtonJump(0.0f);
                sx.d(this.bno).setEnabled(false);
            }
        }
    }
}

