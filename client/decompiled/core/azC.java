/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class azC
extends a_0 {
    private static Logger a = Logger.getLogger(azC.class);
    public static final String TAG = "StaticLayout";
    public static final String aTJ = "sl";
    private boolean dnU = false;
    private static final HashMap dnV = new HashMap();
    public static final int dnW = "adaptToContentSize".hashCode();

    public String getTag() {
        return TAG;
    }

    public boolean a() {
        return this.dnU;
    }

    public boolean getAdaptToContentSize() {
        return this.dnU;
    }

    public void setAdaptToContentSize(boolean bl2) {
        this.dnU = bl2;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        if (this.dnU) {
            int n2 = 0;
            int n3 = 0;
            ArrayList arrayList = aht_12.getWidgetChildren();
            for (int j = 0; j < arrayList.size(); ++j) {
                agj_1 agj_12;
                adg_2 adg_22 = (adg_2)arrayList.get(j);
                if (!adg_22.getVisible()) continue;
                auW auW2 = (auW)adg_22.getLayoutData();
                int n4 = 0;
                int n5 = 0;
                if (auW2.isXInit()) {
                    n4 = auW2.getX();
                }
                if (auW2.isYInit()) {
                    n5 = auW2.getY();
                }
                if (auW2.getSize() != null) {
                    int n6;
                    agj_12 = auW2.getSize();
                    agj_1 agj_13 = adg_22.getMinSize();
                    if (agj_12.awi() == -1.0f) {
                        n6 = (int)agj_12.getWidth();
                        n2 = Math.max(agj_12.width + n4, n2);
                        if (n6 == -1) {
                            n6 = adg_22.aLd.width;
                        } else if (n6 == -2) {
                            n6 = (int)agj_13.getWidth();
                        }
                        n2 = Math.max(n6 + n4, n2);
                    } else {
                        n2 = Math.max(Math.round((float)agj_13.width / agj_12.awi() * 100.0f) + n4, n2);
                    }
                    if (agj_12.awj() == -1.0f) {
                        n6 = (int)agj_12.getHeight();
                        n2 = Math.max(agj_12.height + n5, n2);
                        if (n6 == -1) {
                            n6 = adg_22.aLd.height;
                        } else if (n6 == -2) {
                            n6 = (int)agj_13.getHeight();
                        }
                        n2 = Math.max(n6 + n5, n2);
                        continue;
                    }
                    n2 = Math.max(Math.round((float)agj_13.height / agj_12.awj() * 100.0f) + n5, n2);
                    continue;
                }
                agj_12 = adg_22.getMinSize();
                n2 = Math.max(agj_12.width + n4, n2);
                n3 = Math.max(agj_12.height + n5, n3);
            }
            return new agj_1(n2, n3);
        }
        return new agj_1(0, 0);
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        if (this.dnU) {
            int n2 = 0;
            int n3 = 0;
            ArrayList arrayList = aht_12.getWidgetChildren();
            for (int j = 0; j < arrayList.size(); ++j) {
                agj_1 agj_12;
                auW auW2;
                adg_2 adg_22 = (adg_2)arrayList.get(j);
                if (!adg_22.getVisible() || !(adg_22.getLayoutData() instanceof auW) || (auW2 = (auW)adg_22.getLayoutData()) == null) continue;
                int n4 = 0;
                int n5 = 0;
                if (auW2.isXInit()) {
                    n4 = auW2.getX();
                }
                if (auW2.isYInit()) {
                    n5 = auW2.getY();
                }
                if (auW2.getSize() != null) {
                    int n6;
                    agj_12 = auW2.getSize();
                    agj_1 agj_13 = adg_22.getPrefSize();
                    if (agj_12.awi() == -1.0f) {
                        n6 = (int)agj_12.getWidth();
                        n2 = Math.max(agj_12.width + n4, n2);
                        if (n6 == -1) {
                            n6 = adg_22.aLd.width;
                        } else if (n6 == -2) {
                            n6 = (int)agj_13.getWidth();
                        }
                        n2 = Math.max(n6 + n4, n2);
                    } else {
                        n2 = Math.max(Math.round((float)agj_13.width / agj_12.awi() * 100.0f) + n4, n2);
                    }
                    if (agj_12.awj() == -1.0f) {
                        n6 = (int)agj_12.getHeight();
                        n3 = Math.max(agj_12.height + n5, n3);
                        if (n6 == -1) {
                            n6 = adg_22.aLd.height;
                        } else if (n6 == -2) {
                            n6 = (int)agj_13.getHeight();
                        }
                        n3 = Math.max(n6 + n5, n3);
                        continue;
                    }
                    n3 = Math.max(Math.round((float)agj_13.height / agj_12.awj() * 100.0f) + n5, n3);
                    continue;
                }
                agj_12 = adg_22.getPrefSize();
                n2 = Math.max(agj_12.width + n4, n2);
                n3 = Math.max(agj_12.height + n5, n3);
            }
            return new agj_1(n2, n3);
        }
        return new agj_1(0, 0);
    }

    public agj_1 getContentGreedySize(aht_1 aht_12, adg_2 adg_22, agj_1 agj_12) {
        int n2;
        int n3;
        agj_1 agj_13;
        if (!this.dnU || aht_12 == null || adg_22 == null) {
            return new agj_1(0, 0);
        }
        if (!(adg_22.getLayoutData() instanceof auW)) {
            return new agj_1(0, 0);
        }
        int n4 = agj_12.width;
        int n5 = agj_12.height;
        auW auW2 = (auW)adg_22.getLayoutData();
        int n6 = 0;
        int n7 = 0;
        if (auW2.isXInit()) {
            n6 = auW2.getX();
        }
        if (auW2.isYInit()) {
            n7 = auW2.getY();
        }
        if ((agj_13 = auW2.getSize()) != null) {
            n3 = agj_13.awi() == -1.0f ? n4 - n6 : (int)((float)n4 * agj_13.awi() / 100.0f) - n6;
            n2 = agj_13.awj() == -1.0f ? n5 - n7 : (int)((float)n5 * agj_13.awj() / 100.0f) - n7;
        } else {
            n3 = n4 - n6;
            n2 = n5 - n7;
        }
        return new agj_1(n3, n2);
    }

    public static boolean b(aht_1 aht_12, adg_2 adg_22) {
        int n2;
        Object object;
        Object object2;
        if (aht_12 == null || adg_22 == null) {
            return false;
        }
        if (adg_22 == null || adg_22.getLayoutData() == null || !adg_22.getVisible() || !(adg_22.getLayoutData() instanceof auW)) {
            return false;
        }
        auW auW2 = (auW)adg_22.getLayoutData();
        if (!auW2.isUsable()) {
            return false;
        }
        auW2.setUsable(false);
        if (!auW2.isInitValue() || !adg_22.isSizeInitByUserDefinition()) {
            if (auW2.getSize() != null) {
                object2 = auW2.getSize();
                object = adg_22.getPrefSize();
                n2 = ((agj_1)object2).awi() != -1.0f ? (int)Math.round((double)((float)aht_12.getAppearance().getContentWidth() * ((agj_1)object2).awi()) / 100.0) : ((agj_1)object2).width;
                int n3 = ((agj_1)object2).awj() != -1.0f ? (int)Math.round((double)((float)aht_12.getAppearance().getContentHeight() * ((agj_1)object2).awj()) / 100.0) : ((agj_1)object2).height;
                if (n3 == -1) {
                    n3 = adg_22.aLd.height;
                } else if (n3 == -2) {
                    n3 = (int)((Dimension)object).getHeight();
                }
                if (n2 == -1) {
                    n2 = adg_22.aLd.width;
                } else if (n2 == -2) {
                    n2 = (int)((Dimension)object).getWidth();
                }
                adg_22.setSize(n2, n3);
            } else {
                adg_22.setSizeToPrefSize();
            }
        }
        if (!auW2.isInitValue() || !adg_22.isPositionInitByUserDefinition()) {
            if (auW2.isXInit()) {
                adg_22.setX(auW2.getX());
            }
            if (auW2.isYInit()) {
                adg_22.setY(auW2.getY());
            }
            if (auW2.getAlignment() != null) {
                object2 = auW2.getAlignment();
                int n4 = auW2.isXOffsetInit() ? auW2.getXOffset() : 0;
                n2 = auW2.isYOffsetInit() ? auW2.getYOffset() : 0;
                adg_22.setX(((ajn_1)((Object)object2)).ag(adg_22.getSize().width, aht_12.getAppearance().getContentWidth()) + n4);
                adg_22.setY(((ajn_1)((Object)object2)).ah(adg_22.getSize().height, aht_12.getAppearance().getContentHeight()) + n2);
            }
            if (auW2.getXPerc() != null) {
                adg_22.setX((int)(auW2.getXPerc().getValue() / 100.0 * (double)(aht_12.getAppearance().getContentWidth() - adg_22.getSize().width)));
            }
            if (auW2.getYPerc() != null) {
                adg_22.setY((int)(auW2.getYPerc().getValue() / 100.0 * (double)(aht_12.getAppearance().getContentHeight() - adg_22.getSize().height)));
            }
            if (auW2.isAutoPositionable()) {
                object2 = auW2.getReferentWidget();
                object = (ex_2)((adg_2)object2).getWidgetParentOfType(ex_2.class);
                ((ex_2)object).getWindowManager().b(auW2.getControlGroup(), (adg_2)object2);
                auW2.setControlGroup(null);
                auW2.setReferentWidget(null);
                auW2.setCascadeMethodEnabled(false);
            }
        }
        if (auW2.isInitValue() && adg_22.isPositionInitByUserDefinition()) {
            object2 = (ex_2)adg_22.getWidgetParentOfType(ex_2.class);
            adg_22.setX(Math.max(0, Math.min(adg_22.getX((aht_1)object2), ((adg_2)object2).getWidth() - adg_22.getWidth())));
            adg_22.setY(Math.max(0, Math.min(adg_22.getY((aht_1)object2), ((adg_2)object2).getHeight() - adg_22.getHeight())));
        }
        return true;
    }

    public static void a(aht_1 aht_12, ArrayList arrayList) {
        adg_2 adg_22;
        if (aht_12 == null || arrayList == null) {
            return;
        }
        dnV.clear();
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            int n3;
            Object object;
            auW auW2;
            adg_22 = (adg_2)arrayList.get(j);
            if (adg_22 == null || adg_22.getLayoutData() == null || !adg_22.getVisible() || !(adg_22.getLayoutData() instanceof auW) || !(auW2 = (auW)adg_22.getLayoutData()).isUsable()) continue;
            auW2.setUsable(false);
            if (!auW2.isInitValue() || !adg_22.isSizeInitByUserDefinition()) {
                if (auW2.getSize() != null) {
                    object = auW2.getSize();
                    agj_1 agj_12 = adg_22.getPrefSize();
                    n3 = ((agj_1)object).awi() != -1.0f ? (int)Math.round((double)((float)aht_12.getAppearance().getContentWidth() * ((agj_1)object).awi()) / 100.0) : ((agj_1)object).width;
                    int n4 = ((agj_1)object).awj() != -1.0f ? (int)Math.round((double)((float)aht_12.getAppearance().getContentHeight() * ((agj_1)object).awj()) / 100.0) : ((agj_1)object).height;
                    if (n4 == -1) {
                        n4 = adg_22.aLd.height;
                    } else if (n4 == -2) {
                        n4 = (int)agj_12.getHeight();
                    }
                    if (n3 == -1) {
                        n3 = adg_22.aLd.width;
                    } else if (n3 == -2) {
                        n3 = (int)agj_12.getWidth();
                    }
                    adg_22.setSize(n3, n4);
                } else {
                    adg_22.setSizeToPrefSize();
                }
            }
            if (!auW2.isInitValue() || !adg_22.isPositionInitByUserDefinition()) {
                if (auW2.isXInit()) {
                    adg_22.setX(auW2.getX());
                }
                if (auW2.isYInit()) {
                    adg_22.setY(auW2.getY());
                }
                if (auW2.getAlignment() != null) {
                    object = auW2.getAlignment();
                    int n5 = auW2.isXOffsetInit() ? auW2.getXOffset() : 0;
                    n3 = auW2.isYOffsetInit() ? auW2.getYOffset() : 0;
                    adg_22.setX(((ajn_1)((Object)object)).ag(adg_22.getSize().width, aht_12.getAppearance().getContentWidth()) + n5);
                    adg_22.setY(((ajn_1)((Object)object)).ah(adg_22.getSize().height, aht_12.getAppearance().getContentHeight()) + n3);
                }
                if (auW2.getXPerc() != null) {
                    int n6 = auW2.isXOffsetInit() ? auW2.getXOffset() : 0;
                    adg_22.setX((int)Math.round(auW2.getXPerc().getValue() / 100.0 * (double)(aht_12.getAppearance().getContentWidth() - adg_22.getSize().width)) + n6);
                }
                if (auW2.getYPerc() != null) {
                    int n7 = auW2.isYOffsetInit() ? auW2.getYOffset() : 0;
                    adg_22.setY((int)Math.round(auW2.getYPerc().getValue() / 100.0 * (double)(aht_12.getAppearance().getContentHeight() - adg_22.getSize().height)) + n7);
                }
                if (auW2.isAutoPositionable()) {
                    dnV.put(auW2.getControlGroup(), auW2.getReferentWidget());
                    auW2.setReferentWidget(null);
                }
            }
            if (!auW2.isInitValue() || !adg_22.isPositionInitByUserDefinition()) continue;
            object = (ex_2)adg_22.getWidgetParentOfType(ex_2.class);
            adg_22.setX(Math.max(0, Math.min(adg_22.getX((aht_1)object), ((adg_2)object).getWidth() - adg_22.getWidth())));
            adg_22.setY(Math.max(0, Math.min(adg_22.getY((aht_1)object), ((adg_2)object).getHeight() - adg_22.getHeight())));
        }
        if (dnV.size() != 0) {
            for (Map.Entry entry : dnV.entrySet()) {
                adg_22 = (ex_2)((adg_2)entry.getValue()).getWidgetParentOfType(ex_2.class);
                ((ex_2)adg_22).getWindowManager().b((String)entry.getKey(), (adg_2)entry.getValue());
            }
        }
    }

    public void a(aht_1 aht_12) {
        azC.a(aht_12, aht_12.getWidgetChildren());
    }

    public void a(aht_1 aht_12, adg_2 adg_22) {
        azC.b(aht_12, adg_22);
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        ((azC)air_12).setAdaptToContentSize(this.dnU);
    }

    public azC aMa() {
        azC azC2 = new azC();
        azC2.b();
        this.a((air_1)azC2);
        return azC2;
    }

    public void j() {
        super.j();
    }

    public void b() {
        super.b();
        this.b = true;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != dnW) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setAdaptToContentSize(Gr.getBoolean(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        return super.setPropertyAttribute(n2, object);
    }
}

