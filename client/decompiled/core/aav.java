/*
 * Decompiled with CFR 0.152.
 */
class aav
extends a_0 {
    final /* synthetic */ ajc cfS;

    private aav(ajc ajc2) {
        this.cfS = ajc2;
    }

    private int getFirstConstraint() {
        if (ajc.a(this.cfS) <= 0) {
            return (int)Math.round(Math.sqrt(ajc.b(this.cfS).size()));
        }
        return ajc.a(this.cfS);
    }

    private int getSecondConstraint(int n2) {
        return (int)Math.ceil((double)ajc.b(this.cfS).size() / (double)n2);
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        int n2;
        int n3;
        if (ajc.b(this.cfS).size() == 0) {
            return new agj_1(0, 0);
        }
        if (ajc.c(this.cfS)) {
            n3 = this.getFirstConstraint();
            n2 = this.getSecondConstraint(n3);
        } else {
            n2 = this.getFirstConstraint();
            n3 = this.getSecondConstraint(n2);
        }
        agj_1 agj_12 = ((aht_1)ajc.b(this.cfS).get(0)).getMinSize();
        agj_12.setWidth(agj_12.width * n3);
        agj_12.setHeight(agj_12.height * n2);
        return agj_12;
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        int n2;
        int n3;
        if (ajc.b(this.cfS).size() == 0) {
            return new agj_1(0, 0);
        }
        if (ajc.c(this.cfS)) {
            n3 = this.getFirstConstraint();
            n2 = this.getSecondConstraint(n3);
        } else {
            n2 = this.getFirstConstraint();
            n3 = this.getSecondConstraint(n2);
        }
        agj_1 agj_12 = ((aht_1)ajc.b(this.cfS).get(0)).getPrefSize();
        agj_12.setWidth(agj_12.width * n3);
        agj_12.setHeight(agj_12.height * n2);
        return agj_12;
    }

    public void a(aht_1 aht_12) {
        block12: {
            int n2;
            int n3;
            int n4;
            if (ajc.b(this.cfS).size() == 0) {
                return;
            }
            if (ajc.c(this.cfS)) {
                n4 = this.getFirstConstraint();
                n3 = this.getSecondConstraint(n4);
            } else {
                n3 = this.getFirstConstraint();
                n4 = this.getSecondConstraint(n3);
            }
            int n5 = ajc.b(this.cfS).size();
            for (n2 = 0; n2 < n5; ++n2) {
                ((aht_1)ajc.b(this.cfS).get(n2)).setSizeToPrefSize();
            }
            n2 = ajc.b(this.cfS).size();
            n5 = ((aht_1)ajc.b(this.cfS).get(0)).getWidth();
            int n6 = ((aht_1)ajc.b(this.cfS).get(0)).getHeight();
            int n7 = 0;
            int n8 = aht_12.getAppearance().getContentHeight() - n6;
            if (ajc.c(this.cfS)) {
                for (int j = 0; j < n3; ++j) {
                    for (int i2 = 0; i2 < n4; ++i2) {
                        int n9 = i2 + j * n4;
                        if (n9 < n2) {
                            aht_1 aht_13 = (aht_1)ajc.b(this.cfS).get(n9);
                            aht_13.setPosition(n7, n8);
                            n7 += n5;
                            continue;
                        }
                        break block12;
                    }
                    n7 = 0;
                    n8 -= n6;
                }
            } else {
                for (int j = 0; j < n4; ++j) {
                    for (int i3 = 0; i3 < n3; ++i3) {
                        int n10 = j + i3 * n4;
                        if (n10 < n2) {
                            aht_1 aht_14 = (aht_1)ajc.b(this.cfS).get(i3 + j * n3);
                            aht_14.setPosition(n7, n8);
                            n8 -= n6;
                            continue;
                        }
                        break block12;
                    }
                    n8 = aht_12.getAppearance().getContentHeight() - n6;
                    n7 += n5;
                }
            }
        }
    }

    /* synthetic */ aav(ajc ajc2, azr_0 azr_02) {
        this(ajc2);
    }
}

