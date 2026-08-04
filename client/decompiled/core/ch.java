/*
 * Decompiled with CFR 0.152.
 */
class ch
extends a_0 {
    final /* synthetic */ aIg il;

    private ch(aIg aIg2) {
        this.il = aIg2;
    }

    public boolean aO() {
        return false;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        agj_1 agj_12 = aIg.b(this.il).getMinSize();
        agj_1 agj_13 = aIg.c(this.il).getMinSize();
        agj_1 agj_14 = aIg.d(this.il).getMinSize();
        if (aIg.e(this.il)) {
            return new agj_1(agj_12.width + agj_13.width + agj_14.width, Math.max(agj_12.height, Math.max(agj_13.height, agj_14.height)));
        }
        return new agj_1(Math.max(agj_12.width, Math.max(agj_13.width, agj_14.width)), agj_12.height + agj_13.height + agj_14.height);
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        agj_1 agj_12 = aIg.b(this.il).getPrefSize();
        agj_1 agj_13 = aIg.c(this.il).getPrefSize();
        agj_1 agj_14 = aIg.d(this.il).getPrefSize();
        if (aIg.e(this.il)) {
            return new agj_1(agj_12.width + agj_13.width + agj_14.width, Math.max(agj_12.height, Math.max(agj_13.height, agj_14.height)));
        }
        return new agj_1(Math.max(agj_12.width, Math.max(agj_13.width, agj_14.width)), agj_12.height + agj_13.height + agj_14.height);
    }

    public void a(aht_1 aht_12) {
        if (aIg.e(this.il)) {
            int n2 = this.il.getAppearance().getContentHeight();
            aIg.b(this.il).setSize((int)aIg.b(this.il).getPrefSize().getWidth(), n2);
            aIg.c(this.il).setSize((int)aIg.c(this.il).getPrefSize().getWidth(), n2);
            aIg.d(this.il).setSize(this.il.getAppearance().getContentWidth() - aIg.b(this.il).getWidth() - aIg.c(this.il).getWidth(), n2);
        } else {
            int n3 = this.il.getAppearance().getContentWidth();
            aIg.b(this.il).setSize(n3, (int)aIg.b(this.il).getPrefSize().getHeight());
            aIg.c(this.il).setSize(n3, (int)aIg.c(this.il).getPrefSize().getHeight());
            aIg.d(this.il).setSize(n3, this.il.getAppearance().getContentHeight() - aIg.b(this.il).getHeight() - aIg.c(this.il).getHeight());
        }
        this.il.aUC();
    }

    /* synthetic */ ch(aIg aIg2, oz_2 oz_22) {
        this(aIg2);
    }
}

