/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from xI
 */
class xi_1
extends a_0 {
    final /* synthetic */ dd_1 azr;

    xi_1(dd_1 dd_12) {
        this.azr = dd_12;
    }

    public boolean aO() {
        return false;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        int n2 = 0;
        int n3 = 0;
        if (dd_1.a(this.azr)) {
            agj_1 agj_12;
            if (dd_1.b(this.azr) != null) {
                agj_12 = dd_1.b(this.azr).getMinSize();
                n2 = Math.max(n2, agj_12.height);
                n3 += agj_12.width;
            }
            if (dd_1.c(this.azr) != null) {
                agj_12 = dd_1.c(this.azr).getMinSize();
                n2 = Math.max(n2, agj_12.height);
                n3 += agj_12.width;
            }
            if (dd_1.d(this.azr) != null) {
                agj_12 = dd_1.d(this.azr).getMinSize();
                n2 = Math.max(n2, agj_12.height);
                n3 += agj_12.width;
            }
        } else {
            agj_1 agj_13;
            if (dd_1.b(this.azr) != null) {
                agj_13 = dd_1.b(this.azr).getMinSize();
                n3 = Math.max(n3, agj_13.width);
                n2 += agj_13.height;
            }
            if (dd_1.d(this.azr) != null) {
                agj_13 = dd_1.d(this.azr).getMinSize();
                n3 = Math.max(n3, agj_13.width);
                n2 += agj_13.height;
            }
        }
        return new agj_1(n3, n2);
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        int n2 = 0;
        int n3 = 0;
        if (dd_1.a(this.azr)) {
            agj_1 agj_12;
            if (dd_1.b(this.azr) != null) {
                agj_12 = dd_1.b(this.azr).getPrefSize();
                n2 = Math.max(n2, agj_12.height);
                n3 += agj_12.width;
            }
            if (dd_1.c(this.azr) != null) {
                agj_12 = dd_1.c(this.azr).getPrefSize();
                n2 = Math.max(n2, agj_12.height);
                n3 += agj_12.width;
            }
            if (dd_1.d(this.azr) != null) {
                agj_12 = dd_1.d(this.azr).getPrefSize();
                n2 = Math.max(n2, agj_12.height);
                n3 += agj_12.width;
            }
        } else {
            agj_1 agj_13;
            if (dd_1.b(this.azr) != null) {
                agj_13 = dd_1.b(this.azr).getPrefSize();
                n3 = Math.max(n3, agj_13.width);
                n2 += agj_13.height;
            }
            if (dd_1.d(this.azr) != null) {
                agj_13 = dd_1.d(this.azr).getPrefSize();
                n3 = Math.max(n3, agj_13.width);
                n2 += agj_13.height;
            }
        }
        return new agj_1(n3, n2);
    }

    public void a(aht_1 aht_12) {
        int n2 = aht_12.getAppearance().getContentHeight();
        int n3 = aht_12.getAppearance().getContentWidth();
        if (dd_1.a(this.azr)) {
            int n4;
            agj_1 agj_12;
            int n5 = 0;
            if (dd_1.b(this.azr) != null) {
                n5 += dd_1.b((dd_1)this.azr).getPrefSize().width;
            }
            if (dd_1.c(this.azr) != null) {
                n5 += dd_1.c((dd_1)this.azr).getPrefSize().width;
            }
            if (dd_1.d(this.azr) != null) {
                n5 += dd_1.d((dd_1)this.azr).getPrefSize().width;
            }
            int n6 = BT.aJX.ag(n5, n3);
            if (dd_1.b(this.azr) != null) {
                agj_12 = dd_1.b(this.azr).getPrefSize();
                n4 = BT.aJX.ah(agj_12.height, n2);
                dd_1.b(this.azr).setPosition(n6, n4);
                dd_1.b(this.azr).setSizeToPrefSize();
                n6 += agj_12.width;
            }
            if (dd_1.c(this.azr) != null) {
                agj_12 = dd_1.c(this.azr).getPrefSize();
                n4 = BT.aJX.ah(agj_12.height, n2);
                dd_1.c(this.azr).setPosition(n6, n4);
                dd_1.c(this.azr).setSizeToPrefSize();
                n6 += agj_12.width;
            }
            if (dd_1.d(this.azr) != null) {
                agj_12 = dd_1.d(this.azr).getPrefSize();
                n4 = BT.aJX.ah(agj_12.height, n2);
                dd_1.d(this.azr).setPosition(n6, n4);
                dd_1.d(this.azr).setSizeToPrefSize();
            }
        } else {
            int n7;
            agj_1 agj_13;
            int n8 = 0;
            if (dd_1.b(this.azr) != null) {
                n8 += dd_1.b((dd_1)this.azr).getPrefSize().height;
            }
            if (dd_1.d(this.azr) != null) {
                n8 += dd_1.d((dd_1)this.azr).getPrefSize().height;
            }
            int n9 = BT.aJX.ah(n8, n2);
            if (dd_1.d(this.azr) != null) {
                agj_13 = dd_1.d(this.azr).getPrefSize();
                n7 = BT.aJX.ag(agj_13.width, n3);
                dd_1.d(this.azr).setPosition(n7, n9);
                dd_1.d(this.azr).setSizeToPrefSize();
                n9 += agj_13.height;
            }
            if (dd_1.b(this.azr) != null) {
                agj_13 = dd_1.b(this.azr).getPrefSize();
                n7 = BT.aJX.ag(agj_13.width, n3);
                dd_1.b(this.azr).setPosition(n7, n9);
                dd_1.b(this.azr).setSizeToPrefSize();
            }
            if (dd_1.c(this.azr) != null) {
                agj_13 = dd_1.c(this.azr).getPrefSize();
                n7 = BT.aJX.ag(agj_13.width, n3);
                dd_1.c(this.azr).setPosition(n7, dd_1.b(this.azr).getY() - 2);
                dd_1.c(this.azr).setSizeToPrefSize();
            }
        }
    }
}

