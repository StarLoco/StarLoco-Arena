/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from hQ
 */
public class hq_2
extends a_0 {
    final /* synthetic */ aJS wG;

    public hq_2(aJS aJS2) {
        this.wG = aJS2;
    }

    public boolean aO() {
        return false;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        agj_1 agj_12 = aJS.a(this.wG).getVisible() ? aJS.a(this.wG).getMinSize() : new agj_1(0, 0);
        switch (aJS.b(this.wG)) {
            case aJs: 
            case aJt: {
                agj_12.height += aJS.c((aJS)this.wG).getMinSize().height;
                agj_12.width = Math.max(agj_12.width, aJS.c((aJS)this.wG).getMinSize().width);
                break;
            }
            case aJu: 
            case aJv: {
                agj_12.width += aJS.c((aJS)this.wG).getMinSize().width;
                agj_12.height = Math.max(agj_12.height, aJS.c((aJS)this.wG).getMinSize().height);
            }
        }
        return agj_12;
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        agj_1 agj_12 = aJS.a(this.wG).getVisible() ? aJS.a(this.wG).getPrefSize() : new agj_1(0, 0);
        switch (aJS.b(this.wG)) {
            case aJs: 
            case aJt: {
                agj_12.height += aJS.c((aJS)this.wG).getPrefSize().height;
                agj_12.width = Math.max(agj_12.width, aJS.c((aJS)this.wG).getPrefSize().width);
                break;
            }
            case aJu: 
            case aJv: {
                agj_12.width += aJS.c((aJS)this.wG).getPrefSize().width;
                agj_12.height = Math.max(agj_12.height, aJS.c((aJS)this.wG).getPrefSize().height);
            }
        }
        return agj_12;
    }

    public void a(aht_1 aht_12) {
        int n2 = aht_12.cLZ.getContentHeight();
        int n3 = aht_12.cLZ.getContentWidth();
        switch (aJS.b(this.wG)) {
            case aJs: {
                aJS.c(this.wG).setSize(n3, aJS.c((aJS)this.wG).getPrefSize().height);
                aJS.c(this.wG).setPosition(0, n2 - aJS.c(this.wG).getHeight());
                if (!aJS.a(this.wG).getVisible()) break;
                aJS.a(this.wG).setSize(n3, n2 - aJS.c(this.wG).getHeight());
                aJS.a(this.wG).setPosition(0, 0);
                break;
            }
            case aJt: {
                aJS.c(this.wG).setSize(n3, aJS.c((aJS)this.wG).getPrefSize().height);
                aJS.c(this.wG).setPosition(0, 0);
                if (!aJS.a(this.wG).getVisible()) break;
                aJS.a(this.wG).setSize(n3, n2 - aJS.c(this.wG).getHeight());
                aJS.a(this.wG).setPosition(0, aJS.c(this.wG).getHeight());
                break;
            }
            case aJv: {
                aJS.c(this.wG).setSize(aJS.c((aJS)this.wG).getPrefSize().width, n2);
                aJS.c(this.wG).setPosition(0, 0);
                if (!aJS.a(this.wG).getVisible()) break;
                aJS.a(this.wG).setSize(n3 - aJS.c(this.wG).getWidth(), n2);
                aJS.a(this.wG).setPosition(aJS.c(this.wG).getWidth(), 0);
                break;
            }
            case aJu: {
                aJS.c(this.wG).setSize(aJS.c((aJS)this.wG).getPrefSize().width, n2);
                aJS.c(this.wG).setPosition(n3 - aJS.c(this.wG).getWidth(), 0);
                if (!aJS.a(this.wG).getVisible()) break;
                aJS.a(this.wG).setSize(n3 - aJS.c(this.wG).getWidth(), n2);
                aJS.a(this.wG).setPosition(0, 0);
            }
        }
    }
}

