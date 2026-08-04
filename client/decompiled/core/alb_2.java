/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from alB
 */
class alb_2
extends a_0 {
    final /* synthetic */ yE bML;

    private alb_2(yE yE2) {
        this.bML = yE2;
    }

    public boolean aO() {
        return false;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        agj_1 agj_12 = yE.a(this.bML) != null ? yE.a(this.bML).getMinSize() : new agj_1();
        agj_1 agj_13 = yE.b(this.bML) != null ? yE.b(this.bML).getMinSize() : new agj_1();
        agj_12.height = Math.max(agj_12.height, agj_13.height);
        agj_12.width += agj_13.width;
        return agj_12;
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        agj_1 agj_12 = yE.a(this.bML) != null ? yE.a(this.bML).getPrefSize() : new agj_1();
        agj_1 agj_13 = yE.b(this.bML) != null ? yE.b(this.bML).getPrefSize() : new agj_1();
        agj_12.height = Math.max(agj_12.height, agj_13.height);
        agj_12.width += agj_13.width;
        return agj_12;
    }

    public void a(aht_1 aht_12) {
        if (yE.b(this.bML) != null && yE.b(this.bML).getVisible()) {
            yE.b(this.bML).setSizeToPrefSize();
            yE.b(this.bML).setPosition(this.bML.cLZ.getContentWidth() - (int)yE.b(this.bML).getSize().getWidth(), (this.bML.cLZ.getContentHeight() - yE.b(this.bML).getHeight()) / 2);
        }
        if (yE.a(this.bML) != null && yE.a(this.bML).getVisible()) {
            yE.a(this.bML).setPosition(0, 0);
            yE.a(this.bML).setSize(new agj_1(this.bML.cLZ.getContentWidth() - (int)yE.b(this.bML).getSize().getWidth(), this.bML.cLZ.getContentHeight()));
        }
    }

    /* synthetic */ alb_2(yE yE2, sz_1 sz_12) {
        this(yE2);
    }
}

