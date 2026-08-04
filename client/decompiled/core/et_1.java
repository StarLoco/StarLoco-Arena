/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from ET
 */
public class et_1
extends a_0 {
    private static Logger a = Logger.getLogger(et_1.class);
    public static final String TAG = "BorderLayout";
    public static final String aTJ = "bl";
    private int aUb = 0;
    private int aUc = 0;
    public static final int aTM = "hgap".hashCode();
    public static final int aTN = "vgap".hashCode();

    public String getTag() {
        return TAG;
    }

    public void setHGap(int n2) {
        this.aUb = n2;
    }

    public void setVGap(int n2) {
        this.aUc = n2;
    }

    private adg_2 getWidgetByConstraint(aht_1 aht_12, ahq_1 ahq_12) {
        for (adg_2 adg_22 : aht_12.getWidgetChildren()) {
            pj_2 pj_22;
            if (!adg_22.getVisible() || !(adg_22.getLayoutData() instanceof pj_2) || !(pj_22 = (pj_2)adg_22.getLayoutData()).getData().equals((Object)ahq_12)) continue;
            return adg_22;
        }
        return null;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        agj_1 agj_12;
        agj_1 agj_13 = new agj_1(0, 0);
        adg_2 adg_22 = this.getWidgetByConstraint(aht_12, ahq_1.dOa);
        if (adg_22 != null && adg_22.getVisible()) {
            agj_12 = adg_22.getMinSize();
            agj_13.width += agj_12.width + this.aUb;
            agj_13.height = Math.max(agj_12.height, agj_13.height);
        }
        if ((adg_22 = this.getWidgetByConstraint(aht_12, ahq_1.dOb)) != null && adg_22.getVisible()) {
            agj_12 = adg_22.getMinSize();
            agj_13.width += agj_12.width + this.aUb;
            agj_13.height = Math.max(agj_12.height, agj_13.height);
        }
        if ((adg_22 = this.getWidgetByConstraint(aht_12, ahq_1.dNX)) != null && adg_22.getVisible()) {
            agj_12 = adg_22.getMinSize();
            agj_13.width += agj_12.width;
            agj_13.height = Math.max(agj_12.height, agj_13.height);
        }
        if ((adg_22 = this.getWidgetByConstraint(aht_12, ahq_1.dNY)) != null && adg_22.getVisible()) {
            agj_12 = adg_22.getMinSize();
            agj_13.width = Math.max(agj_12.width, agj_13.width);
            agj_13.height += agj_12.height + this.aUc;
        }
        if ((adg_22 = this.getWidgetByConstraint(aht_12, ahq_1.dNZ)) != null && adg_22.getVisible()) {
            agj_12 = adg_22.getMinSize();
            agj_13.width = Math.max(agj_12.width, agj_13.width);
            agj_13.height += agj_12.height + this.aUc;
        }
        return agj_13;
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        agj_1 agj_12;
        agj_1 agj_13 = new agj_1(0, 0);
        adg_2 adg_22 = this.getWidgetByConstraint(aht_12, ahq_1.dOa);
        if (adg_22 != null && adg_22.getVisible()) {
            agj_12 = adg_22.getPrefSize();
            agj_13.width += agj_12.width + this.aUb;
            agj_13.height = Math.max(agj_12.height, agj_13.height);
        }
        if ((adg_22 = this.getWidgetByConstraint(aht_12, ahq_1.dOb)) != null && adg_22.getVisible()) {
            agj_12 = adg_22.getPrefSize();
            agj_13.width += agj_12.width + this.aUb;
            agj_13.height = Math.max(agj_12.height, agj_13.height);
        }
        if ((adg_22 = this.getWidgetByConstraint(aht_12, ahq_1.dNX)) != null && adg_22.getVisible()) {
            agj_12 = adg_22.getPrefSize();
            agj_13.width += agj_12.width;
            agj_13.height = Math.max(agj_12.height, agj_13.height);
        }
        if ((adg_22 = this.getWidgetByConstraint(aht_12, ahq_1.dNY)) != null && adg_22.getVisible()) {
            agj_12 = adg_22.getPrefSize();
            agj_13.width = Math.max(agj_12.width, agj_13.width);
            agj_13.height += agj_12.height + this.aUc;
        }
        if ((adg_22 = this.getWidgetByConstraint(aht_12, ahq_1.dNZ)) != null && adg_22.getVisible()) {
            agj_12 = adg_22.getPrefSize();
            agj_13.width = Math.max(agj_12.width, agj_13.width);
            agj_13.height += agj_12.height + this.aUc;
        }
        return agj_13;
    }

    public void a(aht_1 aht_12) {
        int n2;
        int n3;
        agj_1 agj_12;
        int n4 = aht_12.getAppearance().getContentHeight();
        int n5 = 0;
        int n6 = 0;
        int n7 = aht_12.getAppearance().getContentWidth();
        adg_2 adg_22 = this.getWidgetByConstraint(aht_12, ahq_1.dNZ);
        if (adg_22 != null && adg_22.getVisible()) {
            agj_12 = adg_22.getPrefSize();
            n3 = n6;
            if (adg_22.isExpandable()) {
                n2 = n7 - n6;
            } else {
                n2 = (int)agj_12.getWidth();
                n3 += (n7 - n6 - n2) / 2;
            }
            adg_22.setSize(n2, agj_12.height);
            adg_22.setPosition(n3, n5);
            n5 += agj_12.height + this.aUc;
        }
        if ((adg_22 = this.getWidgetByConstraint(aht_12, ahq_1.dNY)) != null && adg_22.getVisible()) {
            agj_12 = adg_22.getPrefSize();
            n3 = n6;
            if (adg_22.isExpandable()) {
                n2 = n7 - n6;
            } else {
                n2 = (int)agj_12.getWidth();
                n3 += (n7 - n6 - n2) / 2;
            }
            adg_22.setSize(n2, agj_12.height);
            adg_22.setPosition(n3, n4 - agj_12.height);
            n4 -= agj_12.height + this.aUc;
        }
        if ((adg_22 = this.getWidgetByConstraint(aht_12, ahq_1.dOa)) != null && adg_22.getVisible()) {
            agj_12 = adg_22.getPrefSize();
            n3 = n5;
            if (adg_22.isExpandable()) {
                n2 = n4 - n5;
            } else {
                n2 = (int)agj_12.getHeight();
                n3 += (n4 - n5 - n2) / 2;
            }
            adg_22.setSize(agj_12.width, n2);
            adg_22.setPosition(n7 - agj_12.width, n3);
            n7 -= agj_12.width + this.aUb;
        }
        if ((adg_22 = this.getWidgetByConstraint(aht_12, ahq_1.dOb)) != null && adg_22.getVisible()) {
            agj_12 = adg_22.getPrefSize();
            n3 = n5;
            if (adg_22.isExpandable()) {
                n2 = n4 - n5;
            } else {
                n2 = agj_12.height;
                n3 += (n4 - n5 - n2) / 2;
            }
            adg_22.setSize(agj_12.width, n2);
            adg_22.setPosition(n6, n3);
            n6 += agj_12.width + this.aUb;
        }
        if ((adg_22 = this.getWidgetByConstraint(aht_12, ahq_1.dNX)) != null && adg_22.getVisible()) {
            if (adg_22.isExpandable()) {
                adg_22.setSize(n7 - n6, n4 - n5);
                adg_22.setPosition(n6, n5);
            } else {
                adg_22.setSizeToPrefSize();
                adg_22.setPosition(n6 + (n7 - n6 - adg_22.getWidth()) / 2, n5 + (n4 - n5 - adg_22.getHeight()) / 2);
            }
        }
    }

    public void j() {
        super.j();
    }

    public void b() {
        super.b();
    }

    public void a(air_1 air_12) {
        et_1 et_12 = (et_1)air_12;
        super.a((air_1)et_12);
        et_12.aUb = this.aUb;
        et_12.aUc = this.aUc;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == aTM) {
            this.setHGap(Gr.R(string));
        } else if (n2 == aTN) {
            this.setVGap(Gr.R(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        return super.setPropertyAttribute(n2, object);
    }

    public et_1 OF() {
        et_1 et_12 = new et_1();
        et_12.b();
        this.a((air_1)et_12);
        return et_12;
    }
}

