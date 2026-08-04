/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aIk
 */
class aik_2
extends a_0 {
    final /* synthetic */ afQ aFM;

    private aik_2(afQ afQ2) {
        this.aFM = afQ2;
    }

    public boolean aO() {
        return false;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        if (!afQ.b(this.aFM)) {
            return new agj_1(0, 0);
        }
        int n2 = 0;
        int n3 = 0;
        agj_1 agj_12 = afQ.c(this.aFM).getMinSize();
        agj_1 agj_13 = afQ.d(this.aFM).getMinSize();
        agj_1 agj_14 = afQ.e(this.aFM).getMinSize();
        if (!afQ.f(this.aFM).equals((Object)aDM.dyW)) {
            n2 += agj_13.height;
            n3 += agj_13.width;
        } else {
            n3 += agj_14.width;
        }
        if (!afQ.g(this.aFM).equals((Object)aDM.dyW)) {
            n3 += agj_12.width;
            n2 += agj_12.height;
        } else {
            n2 += agj_14.height;
        }
        return new agj_1(n3, n2);
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        if (!afQ.b(this.aFM)) {
            return new agj_1(0, 0);
        }
        int n2 = 0;
        int n3 = 0;
        agj_1 agj_12 = afQ.c(this.aFM).getPrefSize();
        agj_1 agj_13 = afQ.d(this.aFM).getPrefSize();
        agj_1 agj_14 = afQ.e(this.aFM).getPrefSize();
        if (!afQ.f(this.aFM).equals((Object)aDM.dyW)) {
            if (this.aFM.dxX != null) {
                if (agj_14.width <= this.aFM.dxX.width) {
                    n3 += agj_14.width;
                } else {
                    n3 += Math.max(this.aFM.dxX.width, agj_13.width);
                    n2 += agj_13.height;
                }
            } else {
                n2 += agj_13.height;
                n3 += agj_13.width;
            }
        } else {
            n3 += agj_14.width;
        }
        if (!afQ.g(this.aFM).equals((Object)aDM.dyW)) {
            if (this.aFM.dxX != null) {
                if (agj_14.height <= this.aFM.dxX.height) {
                    n2 += agj_14.height;
                } else {
                    n2 += Math.max(this.aFM.dxX.height, agj_12.height);
                    n3 += agj_12.width;
                }
            } else {
                n3 += agj_12.width;
                n2 += agj_12.height;
            }
        } else {
            n2 += agj_14.height;
        }
        return new agj_1(n3, n2);
    }

    public void a(aht_1 aht_12) {
        if (afQ.e(this.aFM) != null) {
            int n2;
            int n3;
            Zb zb = this.aFM.getAppearance();
            int n4 = zb.getContentHeight();
            int n5 = zb.getContentWidth();
            if (afQ.e(this.aFM) instanceof yt_1) {
                ((yt_1)afQ.e(this.aFM)).setTextWidgetSize(n5, n4, true);
                ((yt_1)afQ.e(this.aFM)).getTextBuilder().Jy();
            }
            if (!afQ.b(this.aFM) || (double)n5 >= afQ.e(this.aFM).getPrefSize().getWidth() && !afQ.f(this.aFM).equals((Object)aDM.dyV) || afQ.f(this.aFM).equals((Object)aDM.dyW)) {
                afQ.a(this.aFM, false);
            } else {
                afQ.a(this.aFM, true);
                n4 = (int)((double)n4 - afQ.d(this.aFM).getPrefSize().getHeight());
            }
            if (!afQ.b(this.aFM) || (double)n4 >= afQ.e(this.aFM).getPrefSize().getHeight() && !afQ.g(this.aFM).equals((Object)aDM.dyV) || afQ.g(this.aFM).equals((Object)aDM.dyW)) {
                afQ.b(this.aFM, false);
            } else {
                n5 = (int)((double)n5 - afQ.c(this.aFM).getPrefSize().getWidth());
                if (afQ.e(this.aFM) instanceof yt_1) {
                    ((yt_1)afQ.e(this.aFM)).setTextWidgetSize(n5, afQ.e(this.aFM).getHeight(), true);
                    ((yt_1)afQ.e(this.aFM)).getTextBuilder().Jy();
                } else if (!afQ.h(this.aFM)) {
                    afQ.c(this.aFM).setValue(1.0f);
                }
                afQ.b(this.aFM, true);
            }
            if (afQ.h(this.aFM) && !afQ.i(this.aFM)) {
                if ((double)n5 >= afQ.e(this.aFM).getPrefSize().getWidth() && !afQ.f(this.aFM).equals((Object)aDM.dyV) || afQ.f(this.aFM).equals((Object)aDM.dyW)) {
                    afQ.a(this.aFM, false);
                } else {
                    afQ.a(this.aFM, true);
                    n4 = (int)((double)n4 - afQ.d(this.aFM).getPrefSize().getHeight());
                }
            }
            int n6 = afQ.j(this.aFM) != null && afQ.f(this.aFM) != aDM.dyW ? afQ.e((afQ)this.aFM).getPrefSize().width : (int)Math.max((double)n5, afQ.e(this.aFM).getPrefSize().getWidth());
            int n7 = afQ.j(this.aFM) != null && afQ.g(this.aFM) != aDM.dyW ? afQ.e((afQ)this.aFM).getPrefSize().height : (int)Math.max((double)n4, afQ.e(this.aFM).getPrefSize().getHeight());
            afQ.e(this.aFM).setSize(n6, n7);
            afQ.k(this.aFM);
            if (afQ.i(this.aFM)) {
                afQ.d(this.aFM).setSize(n5, (int)afQ.d(this.aFM).getPrefSize().getHeight());
            }
            if (afQ.h(this.aFM)) {
                afQ.c(this.aFM).setSize((int)afQ.c(this.aFM).getPrefSize().getWidth(), n4);
            }
            if (afQ.i(this.aFM)) {
                n3 = 0;
                n2 = 0;
                if (afQ.h(this.aFM) && afQ.l(this.aFM).equals((Object)bo_0.aJv)) {
                    n3 += afQ.c(this.aFM).getWidth();
                }
                if (afQ.m(this.aFM).equals((Object)bo_0.aJs)) {
                    n2 += n4;
                }
                afQ.d(this.aFM).setPosition(n3, n2);
                afQ.d(this.aFM).getSlider().setSliderSize((float)n5 / (float)n6);
                afQ.d(this.aFM).setButtonJump(1.0f / Math.max(1.0f, (float)n6 / (float)n5 - 1.0f));
            }
            if (afQ.h(this.aFM)) {
                n3 = 0;
                n2 = 0;
                if (afQ.i(this.aFM) && afQ.m(this.aFM).equals((Object)bo_0.aJt)) {
                    n2 += afQ.d(this.aFM).getHeight();
                }
                if (afQ.l(this.aFM).equals((Object)bo_0.aJu)) {
                    n3 += n5;
                }
                afQ.c(this.aFM).setPosition(n3, n2);
                afQ.c(this.aFM).getSlider().setSliderSize((float)n4 / (float)n7);
                afQ.c(this.aFM).setButtonJump(1.0f / Math.max(1.0f, (float)n7 / (float)n4 - 1.0f));
            }
            afQ.d(this.aFM).setVisible(afQ.i(this.aFM));
            afQ.c(this.aFM).setVisible(afQ.h(this.aFM));
        }
    }

    /* synthetic */ aik_2(afQ afQ2, ao_1 ao_12) {
        this(afQ2);
    }
}

