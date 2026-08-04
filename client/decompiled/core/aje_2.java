/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aJe
 */
class aje_2
extends a_0 {
    static final /* synthetic */ boolean bb;
    final /* synthetic */ ahz UX;

    private aje_2(ahz ahz2) {
        this.UX = ahz2;
    }

    public boolean aO() {
        return false;
    }

    private nm_0 isRadiusValid(int n2, boolean bl2, ArrayList arrayList) {
        int n3 = arrayList.size();
        if (n3 == 1) {
            agj_1 agj_12 = ((aqq_0)arrayList.get(0)).getPrefSize();
            return nm_0.k(0, 0, agj_12.width, agj_12.height);
        }
        if (!bb && n3 <= 1) {
            throw new AssertionError((Object)"Le nombre de boutons est inf\u00e9rieur \u00e0 2");
        }
        float f = ahz.kJ(n3);
        float f2 = n3 == 8 ? 4.712389f : 1.5707964f + (float)(n3 - 1) * f / 2.0f;
        nm_0 nm_02 = nm_0.sl();
        nm_0 nm_03 = nm_0.sl();
        nm_0 nm_04 = nm_0.sl();
        nm_0 nm_05 = null;
        for (int j = 0; j < n3; ++j) {
            adg_2 adg_22 = (adg_2)arrayList.get(j);
            agj_1 agj_13 = bl2 ? adg_22.getMinSize() : adg_22.getPrefSize();
            boolean bl3 = false;
            boolean bl4 = false;
            nm_03.setWidth(agj_13.width);
            nm_03.setHeight(agj_13.height);
            nm_03.setX((int)((double)n2 * Math.cos(f2)) + n2);
            nm_03.setY((int)((double)n2 * Math.sin(f2)) + n2);
            nm_04.b(nm_03);
            if (nm_05 != null) {
                if (nm_03.d(nm_05)) {
                    nm_04.release();
                    nm_03.release();
                    nm_02.release();
                    nm_05.release();
                    return null;
                }
            } else {
                nm_05 = nm_0.sl();
            }
            nm_05.e(nm_03);
            if (j == 0) {
                nm_02.e(nm_03);
            }
            f2 -= f;
        }
        if (nm_03.d(nm_02)) {
            nm_04.release();
            nm_03.release();
            nm_02.release();
            if (nm_05 != null) {
                nm_05.release();
            }
            return null;
        }
        nm_03.release();
        nm_02.release();
        if (nm_05 != null) {
            nm_05.release();
        }
        return nm_04;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        agj_1 agj_12;
        ahz ahz2 = (ahz)aht_12;
        if (ahz.a(ahz2).size() == 0) {
            agj_12 = new agj_1(0, 0);
        } else {
            ArrayList arrayList = ((Oc)ahz.a((ahz)ahz2).get((int)ahz.b((ahz)ahz2))).bBG;
            int n2 = 40;
            nm_0 nm_02 = this.isRadiusValid(n2, true, arrayList);
            while (nm_02 == null) {
                nm_02 = this.isRadiusValid(n2 += 5, true, arrayList);
            }
            ahz.a(this.UX, n2);
            agj_12 = new agj_1(ahz.c(this.UX) * 2, ahz.c(this.UX) * 2);
            if (ahz.a(this.UX).size() > 1) {
                if (arrayList.size() > 3) {
                    agj_12.width += ahz.d((ahz)this.UX).getMinSize().width + ahz.e((ahz)this.UX).getMinSize().width;
                    agj_12.height += ahz.d((ahz)this.UX).getMinSize().height + ahz.e((ahz)this.UX).getMinSize().height;
                }
                agj_12.width = Math.max(ahz.d((ahz)this.UX).getMinSize().width + ahz.e((ahz)this.UX).getMinSize().width, agj_12.width);
                agj_12.height = Math.max(ahz.d((ahz)this.UX).getMinSize().height + ahz.e((ahz)this.UX).getMinSize().height, agj_12.height);
            }
        }
        return agj_12;
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        agj_1 agj_12;
        ahz ahz2 = (ahz)aht_12;
        if (ahz.a(ahz2).size() == 0) {
            agj_12 = new agj_1(0, 0);
        } else {
            agj_1 agj_13 = ((aqq_0)((Oc)ahz.a((ahz)ahz2).get((int)0)).bBG.get(0)).getPrefSize();
            int n2 = agj_13.width;
            int n3 = agj_13.height;
            ahz.a(this.UX, 40);
            int n4 = ahz.a(ahz2).size();
            for (int j = 0; j < n4; ++j) {
                Oc oc = (Oc)ahz.a(ahz2).get(j);
                ArrayList arrayList = oc.bBG;
                oc.bBc = 40;
                nm_0 nm_02 = this.isRadiusValid(oc.bBc, false, arrayList);
                while (nm_02 == null) {
                    oc.bBc += 5;
                    nm_02 = this.isRadiusValid(oc.bBc, false, arrayList);
                }
                oc.bBc = Math.max(oc.bBc, Math.max(nm_02.getHeight(), nm_02.getWidth()) / 2);
                nm_02.release();
                ahz.a(this.UX, Math.max(ahz.c(this.UX), oc.bBc));
            }
            agj_12 = new agj_1(n2 + ahz.c(this.UX) * 2, n3 + ahz.c(this.UX) * 2);
        }
        return agj_12;
    }

    public void a(aht_1 aht_12) {
        int n2;
        int n3;
        if (ahz.a(this.UX).size() == 0) {
            return;
        }
        Oc oc = (Oc)ahz.a(this.UX).get(ahz.b(this.UX));
        ArrayList arrayList = oc.bBG;
        int n4 = arrayList.size();
        float f = ahz.kJ(n4);
        float f2 = n4 == 8 ? 4.712389f : 1.5707964f + (float)(n4 - 1) * f / 2.0f;
        for (n3 = 0; n3 < n4; ++n3) {
            ((aqq_0)arrayList.get(n3)).setSizeToPrefSize();
        }
        n3 = ahz.c(this.UX) - oc.bBc;
        if (ahz.a(this.UX).size() > 1) {
            ahz.e(this.UX).setVisible(true);
            ahz.e(this.UX).setSizeToPrefSize();
            n2 = BP.aJB.ag(ahz.e(this.UX).getWidth(), aht_12.getAppearance().getContentWidth()) - ahz.e(this.UX).getWidth();
            int n5 = BP.aJB.ah(ahz.e(this.UX).getHeight(), aht_12.getAppearance().getContentHeight());
            ahz.e(this.UX).setPosition(ahz.c(this.UX), ahz.c(this.UX));
            ahz.e(this.UX).setUsePositionTween(true);
            ahz.e(this.UX).setPosition(n2, n5);
            ahz.e(this.UX).setUsePositionTween(false);
            ahz.d(this.UX).setVisible(true);
            ahz.d(this.UX).setSizeToPrefSize();
            n2 = BP.aJB.ag(ahz.d(this.UX).getWidth(), aht_12.getAppearance().getContentWidth()) + ahz.d(this.UX).getWidth();
            n5 = BP.aJB.ah(ahz.d(this.UX).getHeight(), aht_12.getAppearance().getContentHeight());
            ahz.d(this.UX).setPosition(ahz.c(this.UX), ahz.c(this.UX));
            ahz.d(this.UX).setUsePositionTween(true);
            ahz.d(this.UX).setPosition(n2, n5);
            ahz.d(this.UX).setUsePositionTween(false);
        } else {
            ahz.e(this.UX).setVisible(false);
            ahz.d(this.UX).setVisible(false);
        }
        for (n2 = 0; n2 < n4; ++n2) {
            adg_2 adg_22 = (adg_2)arrayList.get(n2);
            adg_22.setVisible(true);
            int n6 = (int)Math.round(Math.toDegrees(f2)) % 360;
            switch (n6) {
                case 0: {
                    adg_22.setStyle("MRU" + this.UX.getStyle() + "$buttonEast", true);
                    break;
                }
                case -315: 
                case -300: 
                case 45: 
                case 60: {
                    adg_22.setStyle("MRU" + this.UX.getStyle() + "$buttonNorthEast", true);
                    break;
                }
                case -270: 
                case 90: {
                    adg_22.setStyle("MRU" + this.UX.getStyle() + "$buttonNorth", true);
                    break;
                }
                case -240: 
                case -225: 
                case 120: 
                case 135: {
                    adg_22.setStyle("MRU" + this.UX.getStyle() + "$buttonNorthWest", true);
                    break;
                }
                case -180: 
                case 180: {
                    adg_22.setStyle("MRU" + this.UX.getStyle() + "$buttonWest", true);
                    break;
                }
                case -135: 
                case -120: 
                case 225: 
                case 240: {
                    adg_22.setStyle("MRU" + this.UX.getStyle() + "$buttonSouthWest", true);
                    break;
                }
                case -90: 
                case 270: {
                    adg_22.setStyle("MRU" + this.UX.getStyle() + "$buttonSouth", true);
                    break;
                }
                case -60: 
                case -45: 
                case 300: 
                case 315: {
                    adg_22.setStyle("MRU" + this.UX.getStyle() + "$buttonSouthEast", true);
                }
            }
            if (!adg_22.r(uk_2.class)) {
                adg_22.setPosition(oc.bBc + n3, oc.bBc + n3);
                uk_2 uk_22 = new uk_2(1.5707964f, f2, 0, oc.bBc, oc.bBc + n3, oc.bBc + n3, adg_22, 0, 300, ys.aCq);
                adg_22.a(uk_22);
            }
            f2 -= f;
        }
    }

    /* synthetic */ aje_2(ahz ahz2, op_1 op_12) {
        this(ahz2);
    }

    static {
        bb = !ahz.class.desiredAssertionStatus();
    }
}

