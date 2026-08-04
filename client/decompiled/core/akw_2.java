/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from akw
 */
public class akw_2
extends a_0 {
    final /* synthetic */ ex_2 cDx;

    public akw_2(ex_2 ex_22) {
        this.cDx = ex_22;
    }

    public boolean aO() {
        return false;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        return new agj_1(this.cDx.cLZ.getContentWidth(), this.cDx.cLZ.getContentHeight());
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        return new agj_1(this.cDx.cLZ.getContentWidth(), this.cDx.cLZ.getContentHeight());
    }

    public void a(aht_1 aht_12) {
        this.cDx.aRJ.setSize(this.cDx.getAppearance().getContentWidth(), this.cDx.getAppearance().getContentHeight());
        this.cDx.aRJ.setPosition(0, 0);
        this.cDx.aRH.setSize(this.cDx.getAppearance().getContentWidth(), this.cDx.getAppearance().getContentHeight());
        this.cDx.aRH.setPosition(0, 0);
    }
}

