/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;

public class Cp
extends a_0 {
    final /* synthetic */ aqq_0 aLv;

    public Cp(aqq_0 aqq_02) {
        this.aLv = aqq_02;
    }

    public boolean aO() {
        return false;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        agj_1 agj_12;
        agj_1 agj_13 = this.aLv.cNW != null ? this.aLv.cNW.getMinSize() : new agj_1();
        agj_1 agj_14 = agj_12 = this.aLv.lU != null ? this.aLv.lU.getMinSize() : new agj_1();
        if (this.aLv.aoV == bo_0.aJv || this.aLv.aoV == bo_0.aJu) {
            agj_13.height = Math.max(agj_13.height, agj_12.height);
            agj_13.width += agj_12.width;
            if (this.aLv.cNW != null && this.aLv.lU != null) {
                agj_13.width += this.aLv.getAppearance().getGap();
            }
        } else {
            agj_13.height += agj_12.height;
            agj_13.width = Math.max(agj_13.width, agj_12.width);
            if (this.aLv.cNW != null && this.aLv.lU != null) {
                agj_13.height += this.aLv.getAppearance().getGap();
            }
        }
        return agj_13;
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        agj_1 agj_12;
        agj_1 agj_13 = this.aLv.cNW != null ? this.aLv.cNW.getPrefSize() : new agj_1();
        agj_1 agj_14 = agj_12 = this.aLv.lU != null ? this.aLv.lU.getPrefSize() : new agj_1();
        if (this.aLv.aoV == bo_0.aJv || this.aLv.aoV == bo_0.aJu) {
            agj_13.height = Math.max(agj_13.height, agj_12.height);
            agj_13.width += agj_12.width;
            if (this.aLv.cNW != null && this.aLv.lU != null) {
                agj_13.width += this.aLv.getAppearance().getGap();
            }
        } else {
            agj_13.height += agj_12.height;
            agj_13.width = Math.max(agj_13.width, agj_12.width);
            if (this.aLv.cNW != null && this.aLv.lU != null) {
                agj_13.height += this.aLv.getAppearance().getGap();
            }
        }
        return agj_13;
    }

    public void a(aht_1 aht_12) {
        agj_1 agj_12 = new agj_1(0, 0);
        Point point = new Point(0, 0);
        agj_1 agj_13 = new agj_1(0, 0);
        Point point2 = new Point(0, 0);
        if (this.aLv.lU != null && this.aLv.lU.getVisible()) {
            agj_12 = this.aLv.lU.getPrefSize();
        }
        if (this.aLv.cNW != null && this.aLv.cNW.getVisible()) {
            agj_13 = this.aLv.cNW.getPrefSize();
            switch (this.aLv.aoV) {
                case aJs: {
                    point2.y += agj_12.height;
                    if (this.aLv.lU == null || this.aLv.cNW == null) break;
                    point2.y += this.aLv.getAppearance().getGap();
                    break;
                }
                case aJt: {
                    point.y += agj_13.height;
                    if (this.aLv.lU == null || this.aLv.cNW == null) break;
                    point.y += this.aLv.getAppearance().getGap();
                    break;
                }
                case aJu: {
                    point2.x += agj_12.width;
                    if (this.aLv.lU == null || this.aLv.cNW == null) break;
                    point2.x += this.aLv.getAppearance().getGap();
                    break;
                }
                case aJv: {
                    point.x += agj_13.width;
                    if (this.aLv.lU == null || this.aLv.cNW == null) break;
                    point.x += this.aLv.getAppearance().getGap();
                }
            }
        }
        int n2 = 0;
        int n3 = 0;
        switch (this.aLv.aoV) {
            case aJs: 
            case aJt: {
                if (agj_12.width > agj_13.width) {
                    point2.x += (agj_12.width - agj_13.width) / 2;
                } else {
                    point.x += (agj_13.width - agj_12.width) / 2;
                }
                n2 = agj_12.height + agj_13.height;
                n3 = Math.max(agj_12.width, agj_13.width);
                if (this.aLv.lU == null || this.aLv.cNW == null) break;
                n2 += this.aLv.getAppearance().getGap();
                break;
            }
            case aJu: 
            case aJv: {
                if (agj_12.height > agj_13.height) {
                    point2.y += (agj_12.height - agj_13.height) / 2;
                } else {
                    point.y += (agj_13.height - agj_12.height) / 2;
                }
                n2 = Math.max(agj_12.height, agj_13.height);
                n3 = agj_12.width + agj_13.width;
                if (this.aLv.lU == null || this.aLv.cNW == null) break;
                n3 += this.aLv.getAppearance().getGap();
            }
        }
        point.x += this.aLv.cG.ag(n3, this.aLv.cLZ.getContentWidth());
        point2.x += this.aLv.cG.ag(n3, this.aLv.cLZ.getContentWidth());
        point.y += this.aLv.cG.ah(n2, this.aLv.cLZ.getContentHeight());
        point2.y += this.aLv.cG.ah(n2, this.aLv.cLZ.getContentHeight());
        if (this.aLv.cNW != null) {
            this.aLv.cNW.setSize(agj_13);
            this.aLv.cNW.setPosition(point2);
        }
        if (this.aLv.lU != null) {
            this.aLv.lU.setSize(agj_12);
            this.aLv.lU.setPosition(point);
        }
    }
}

