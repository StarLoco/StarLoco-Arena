/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from ane
 */
public class ane_2
extends a_0 {
    private static Logger a = Logger.getLogger(ane_2.class);
    public static final String TAG = "GlidingLayout";
    public static final String aTJ = "gl";
    private boolean ba = true;
    private short aTK = 0;
    private short aTL = 0;
    public static final int aTM = "hgap".hashCode();
    public static final int aTN = "vgap".hashCode();
    public static final int ej = "horizontal".hashCode();

    public String getTag() {
        return TAG;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        int n2 = 0;
        int n3 = 0;
        boolean bl2 = true;
        if (this.ba) {
            for (adg_2 adg_22 : aht_12.getWidgetChildren()) {
                if (!adg_22.getVisible()) continue;
                if (bl2) {
                    bl2 = false;
                } else {
                    n2 += this.aTK;
                }
                n2 += adg_22.getMinSize().width;
                n3 = Math.max(n3, adg_22.getMinSize().height);
            }
            n2 += 2 * this.aTK;
            n3 += 2 * this.aTL;
        } else {
            for (adg_2 adg_23 : aht_12.getWidgetChildren()) {
                if (!adg_23.getVisible()) continue;
                if (bl2) {
                    bl2 = false;
                } else {
                    n3 += this.aTL;
                }
                n3 += adg_23.getMinSize().height;
                n2 = Math.max(n2, adg_23.getMinSize().width);
            }
            n2 += 2 * this.aTK;
            n3 += 2 * this.aTL;
        }
        return new agj_1(n2, n3);
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        int n2 = 0;
        int n3 = 0;
        boolean bl2 = true;
        if (this.ba) {
            for (int j = aht_12.getWidgetChildren().size() - 1; j >= 0; --j) {
                adg_2 adg_22 = aht_12.getWidget(j);
                if (!adg_22.getVisible()) continue;
                if (bl2) {
                    bl2 = false;
                } else {
                    n2 += this.aTK;
                }
                n2 += adg_22.getPrefSize().width;
                n3 = Math.max(n3, adg_22.getPrefSize().height);
            }
            n2 += 2 * this.aTK;
            n3 += 2 * this.aTL;
        } else {
            for (int j = aht_12.getWidgetChildren().size() - 1; j >= 0; --j) {
                adg_2 adg_23 = aht_12.getWidget(j);
                if (!adg_23.getVisible()) continue;
                if (bl2) {
                    bl2 = false;
                } else {
                    n3 += this.aTL;
                }
                n3 += adg_23.getPrefSize().height;
                n2 = Math.max(n2, adg_23.getPrefSize().width);
            }
            n2 += 2 * this.aTK;
            n3 += 2 * this.aTL;
        }
        return new agj_1(n2, n3);
    }

    public short getHgap() {
        return this.aTK;
    }

    public void setHgap(short s) {
        this.aTK = s;
    }

    public boolean isHorizontal() {
        return this.ba;
    }

    public void setHorizontal(boolean bl2) {
        this.ba = bl2;
    }

    public short getVgap() {
        return this.aTL;
    }

    public void setVgap(short s) {
        this.aTL = s;
    }

    public void a(aht_1 aht_12) {
        int n2 = 0;
        int n3 = 0;
        ArrayList arrayList = aht_12.getWidgetChildren();
        if (this.ba) {
            int n4 = aht_12.getAppearance().getContentHeight();
            int n5 = aht_12.getAppearance().getContentWidth();
            for (int j = 0; j < arrayList.size(); ++j) {
                adg_2 adg_22 = (adg_2)arrayList.get(j);
                rp_2 rp_22 = null;
                if (adg_22.getLayoutData() instanceof rp_2) {
                    rp_22 = (rp_2)adg_22.getLayoutData();
                }
                if (!adg_22.getVisible()) continue;
                int n6 = adg_22.getPrefSize().width;
                int n7 = n3;
                int n8 = adg_22.getX();
                int n9 = rp_22 != null ? adg_22.getPrefSize().height : n4;
                if (rp_22 != null) {
                    BT bT = rp_22.getInitAlign();
                    if ((!rp_22.isInitValue() || !adg_22.isPositionInitByUserDefinition() || ago_2.getInstance().isResized()) && rp_22.isUsable() && bT != null) {
                        n8 = bT.ag(n6, n5);
                        rp_22.setUsable(false);
                    }
                    n7 += rp_22.getAlign().ah(n9, n4);
                }
                adg_22.setSize(n6, n9);
                adg_22.setPosition(n8, n7);
            }
        } else {
            int n10 = aht_12.getAppearance().getContentWidth();
            int n11 = aht_12.getAppearance().getContentHeight();
            for (int j = 0; j < arrayList.size(); ++j) {
                adg_2 adg_23 = (adg_2)arrayList.get(j);
                rp_2 rp_23 = null;
                if (adg_23.getLayoutData() instanceof rp_2) {
                    rp_23 = (rp_2)adg_23.getLayoutData();
                }
                if (!adg_23.getVisible()) continue;
                int n12 = adg_23.getPrefSize().height;
                int n13 = n2;
                int n14 = adg_23.getY();
                int n15 = rp_23 != null ? adg_23.getPrefSize().width : n10;
                if (rp_23 != null) {
                    BT bT = rp_23.getInitAlign();
                    if ((!rp_23.isInitValue() || !adg_23.isPositionInitByUserDefinition() || ago_2.getInstance().isResized()) && rp_23.isUsable() && bT != null) {
                        n14 = bT.ah(n12, n11);
                        rp_23.setUsable(false);
                    }
                    n13 += rp_23.getAlign().ag(n15, n10);
                }
                adg_23.setSize(n15, n12);
                adg_23.setPosition(n13, adg_23.getY());
            }
        }
    }

    public void a(air_1 air_12) {
        ane_2 ane_22 = (ane_2)air_12;
        super.a((air_1)ane_22);
        ane_22.aTK = this.aTK;
        ane_22.aTL = this.aTL;
        ane_22.ba = this.ba;
    }

    public ane_2 aCi() {
        ane_2 ane_22 = new ane_2();
        ane_22.b();
        this.a((air_1)ane_22);
        return ane_22;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == aTM) {
            this.setHgap(Gr.getShort(string));
        } else if (n2 == aTN) {
            this.setVgap(Gr.getShort(string));
        } else if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        return super.setPropertyAttribute(n2, object);
    }
}

