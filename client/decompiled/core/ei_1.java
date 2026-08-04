/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from EI
 */
public class ei_1
extends a_0
implements cn_1 {
    private static Logger a;
    public static final String TAG = "RowLayout";
    public static final String aTJ = "rl";
    private static final acl_0 uG;
    private boolean ba = true;
    private short aTK = 0;
    private short aTL = 0;
    private BT cG = BT.aJX;
    public static final int aTM;
    public static final int aTN;
    public static final int ej;
    public static final int cJ;

    public static ei_1 checkOut() {
        ei_1 ei_12;
        try {
            ei_12 = (ei_1)uG.adr();
            ei_12.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            ei_12 = new ei_1();
            ei_12.b();
        }
        return ei_12;
    }

    public String getTag() {
        return TAG;
    }

    public BT getAlign() {
        return this.cG;
    }

    public void setAlign(BT bT) {
        this.cG = bT;
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
                agj_1 agj_12 = adg_22.getMinSize();
                n2 += agj_12.width;
                n3 = Math.max(n3, agj_12.height);
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
                agj_1 agj_13 = adg_23.getMinSize();
                n3 += agj_13.height;
                n2 = Math.max(n2, agj_13.width);
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
                agj_1 agj_12 = adg_22.getPrefSize();
                n2 += agj_12.width;
                n3 = Math.max(n3, agj_12.height);
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
                agj_1 agj_13 = adg_23.getPrefSize();
                n3 += agj_13.height;
                n2 = Math.max(n2, agj_13.width);
            }
            n2 += 2 * this.aTK;
            n3 += 2 * this.aTL;
        }
        return new agj_1(n2, n3);
    }

    public agj_1 getContentGreedySize(aht_1 aht_12, adg_2 adg_22, agj_1 agj_12) {
        int n2 = agj_12.width;
        int n3 = agj_12.height;
        boolean bl2 = true;
        if (this.ba) {
            for (int j = aht_12.getWidgetChildren().size() - 1; j >= 0; --j) {
                adg_2 adg_23 = aht_12.getWidget(j);
                if (!adg_23.getVisible()) continue;
                if (bl2) {
                    bl2 = false;
                } else {
                    n2 -= this.aTK;
                }
                if (adg_23 == adg_22) continue;
                n2 -= adg_23.getPrefSize().width;
            }
            n2 -= 2 * this.aTK;
        } else {
            for (int j = aht_12.getWidgetChildren().size() - 1; j >= 0; --j) {
                adg_2 adg_24 = aht_12.getWidget(j);
                if (!adg_24.getVisible()) continue;
                if (bl2) {
                    bl2 = false;
                } else {
                    n3 -= this.aTL;
                }
                if (adg_24 == adg_22) continue;
                n3 -= adg_24.getPrefSize().height;
            }
            n3 -= 2 * this.aTL;
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

    private ArrayList a(ArrayList arrayList, int n2) {
        int n3;
        int n4;
        ArrayList<pf_0> arrayList2 = new ArrayList<pf_0>();
        ArrayList<adg_2> arrayList3 = new ArrayList<adg_2>();
        if (arrayList != null) {
            for (adg_2 adg_22 : arrayList) {
                if (!adg_22.isShrinkable()) continue;
                arrayList3.add(adg_22);
            }
        }
        if (arrayList3 == null || arrayList3.size() == 0) {
            return arrayList2;
        }
        for (adg_2 adg_22 : arrayList3) {
            pf_0 pf_02;
            agj_1 agj_12 = adg_22.getPrefSize();
            n4 = this.ba ? agj_12.width : agj_12.height;
            for (n3 = arrayList2.size() - 1; n3 >= 0 && (Integer)(pf_02 = (pf_0)arrayList2.get(n3)).acl() <= n4; --n3) {
            }
            arrayList2.add(n3 + 1, new pf_0(adg_22, n4));
        }
        int n5 = n2;
        while (n5 > 0) {
            int n6 = (Integer)((pf_0)arrayList2.get(0)).acl();
            int n7 = 0;
            n4 = 0;
            for (pf_0 pf_03 : arrayList2) {
                if ((Integer)pf_03.acl() == n6) {
                    ++n7;
                    continue;
                }
                n4 = (Integer)pf_03.acl();
                break;
            }
            if (n5 < n7) {
                for (int j = 0; j < n7 && n5 > 0; --n5, ++j) {
                    ((pf_0)arrayList2.get(j)).ad((Integer)((pf_0)arrayList2.get(j)).acl() - 1);
                }
                break;
            }
            n3 = n6 - n4;
            if (n3 * n7 > n5 || n3 <= 0) {
                n3 = (int)Math.floor((double)n5 / (double)n7);
            }
            n5 -= n3 * n7;
            for (int j = 0; j < n7; ++j) {
                ((pf_0)arrayList2.get(j)).ad(n6 - n3);
            }
        }
        return arrayList2;
    }

    public void a(aht_1 aht_12) {
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        boolean bl2 = false;
        ArrayList arrayList = null;
        ArrayList arrayList2 = aht_12.getWidgetChildren();
        if (this.ba) {
            int n8;
            int n9 = aht_12.getAppearance().getContentHeight() - 2 * this.aTL;
            int n10 = aht_12.getAppearance().getContentWidth() - 2 * this.aTK;
            int n11 = arrayList2.size();
            for (n8 = 0; n8 < n11; ++n8) {
                adg_2 adg_22 = (adg_2)arrayList2.get(n8);
                if (!adg_22.getVisible()) continue;
                n10 -= adg_22.getPrefSize().width;
                ++n4;
                if (adg_22.isExpandable()) {
                    ++n5;
                }
                if (!adg_22.isShrinkable()) continue;
                ++n6;
            }
            if ((n10 -= (n4 - 1) * this.aTK) < 0) {
                bl2 = true;
                n7 = -n10;
                n10 = 0;
                arrayList = this.a(arrayList2, n7);
            }
            n8 = 0;
            if (n5 > 0) {
                n8 = (int)Math.floor((double)n10 / (double)n5);
            }
            n11 = 0;
            if (n5 > 0) {
                n11 = n10 - n8 * n5;
            }
            n2 = this.aTK + (n5 == 0 ? this.cG.eL(n10) : 0);
            n3 = this.aTL;
            for (int j = 0; j < arrayList2.size(); ++j) {
                int n12;
                adg_2 adg_23 = (adg_2)arrayList2.get(j);
                if (!adg_23.getVisible()) continue;
                agj_1 agj_12 = adg_23.getPrefSize();
                int n13 = agj_12.width;
                int n14 = n3;
                if (bl2 && adg_23.isShrinkable() && arrayList != null) {
                    for (pf_0 pf_02 : arrayList) {
                        if (pf_02.getFirst() != adg_23) continue;
                        n13 = (Integer)pf_02.acl();
                        break;
                    }
                }
                if (adg_23.isExpandable()) {
                    n13 += n8;
                    if (n11 > 0) {
                        ++n13;
                        --n11;
                    }
                }
                if (adg_23.getLayoutData() instanceof Pm) {
                    n12 = agj_12.height;
                    n14 += ((Pm)adg_23.getLayoutData()).getAlign().ah(n12, n9);
                } else {
                    n12 = n9;
                }
                adg_23.setSize(n13, n12);
                adg_23.setPosition(n2, n14);
                n2 += n13 + this.aTK;
            }
        } else {
            int n15;
            int n16 = aht_12.getAppearance().getContentWidth() - 2 * this.aTK;
            int n17 = aht_12.getAppearance().getContentHeight() - 2 * this.aTL;
            int n18 = arrayList2.size();
            for (n15 = 0; n15 < n18; ++n15) {
                adg_2 adg_24 = (adg_2)arrayList2.get(n15);
                if (!adg_24.getVisible()) continue;
                n17 -= adg_24.getPrefSize().height;
                ++n4;
                if (adg_24.isExpandable()) {
                    ++n5;
                }
                if (!adg_24.isShrinkable()) continue;
                ++n6;
            }
            if ((n17 -= (n4 - 1) * this.aTL) < 0) {
                bl2 = true;
                n7 = -n17;
                n17 = 0;
                arrayList = this.a(arrayList2, n7);
            }
            n15 = 0;
            if (n5 > 0) {
                n15 = (int)Math.floor((double)n17 / (double)n5);
            }
            n18 = 0;
            if (n5 > 0) {
                n18 = n17 - n15 * n5;
            }
            n2 = this.aTK;
            n3 = aht_12.getAppearance().getContentHeight() - (n5 == 0 ? n17 - this.cG.eM(n17) : 0);
            for (int j = 0; j < arrayList2.size(); ++j) {
                int n19;
                adg_2 adg_25 = (adg_2)arrayList2.get(j);
                if (!adg_25.getVisible()) continue;
                agj_1 agj_13 = adg_25.getPrefSize();
                int n20 = agj_13.height;
                int n21 = n2;
                if (bl2 && adg_25.isShrinkable() && arrayList != null) {
                    for (pf_0 pf_03 : arrayList) {
                        if (pf_03.getFirst() != adg_25) continue;
                        n20 = (Integer)pf_03.acl();
                        break;
                    }
                }
                if (adg_25.isExpandable()) {
                    n20 += n15;
                    if (n18 > 0) {
                        ++n20;
                        --n18;
                    }
                }
                if (adg_25.getLayoutData() instanceof Pm) {
                    n19 = agj_13.width;
                    n21 += ((Pm)adg_25.getLayoutData()).getAlign().ag(n19, n16);
                } else {
                    n19 = n16;
                }
                adg_25.setSize(n19, n20);
                adg_25.setPosition(n21, n3 -= n20 + this.aTL);
            }
        }
    }

    public void a(air_1 air_12) {
        ei_1 ei_12 = (ei_1)air_12;
        super.a((air_1)ei_12);
        ei_12.aTK = this.aTK;
        ei_12.aTL = this.aTL;
        ei_12.ba = this.ba;
        ei_12.cG = this.cG;
    }

    public ei_1 Ow() {
        ei_1 ei_12 = ei_1.checkOut();
        this.a((air_1)ei_12);
        return ei_12;
    }

    public void b() {
        super.b();
        this.ba = true;
        this.aTK = 0;
        this.aTL = 0;
        this.cG = BT.aJX;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == aTM) {
            this.setHgap(Gr.getShort(string));
        } else if (n2 == aTN) {
            this.setVgap(Gr.getShort(string));
        } else if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(string));
        } else if (n2 == cJ) {
            this.setAlign(BT.dv(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        return super.setPropertyAttribute(n2, object);
    }

    static {
        ym_0 ym_02;
        a = Logger.getLogger(ei_1.class);
        try {
            ym_02 = new ym_0(new aay_0(), 600);
        }
        catch (Exception exception) {
            ym_02 = new ym_0(new aaX());
        }
        uG = ym_02;
        aTM = "hgap".hashCode();
        aTN = "vgap".hashCode();
        ej = "horizontal".hashCode();
        cJ = "align".hashCode();
    }
}

