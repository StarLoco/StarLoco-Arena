/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class ie
extends aNZ {
    public static final String TAG = "ItemRenderer";
    private static Logger a = Logger.getLogger(ie.class);
    private sn_0 xf = null;
    private ArrayList xg = new ArrayList();
    private kn_1 xh = null;
    private jn_2 xi;
    private ArrayList xj = new ArrayList();
    private ArrayList G = new ArrayList();
    private static ArrayList xk = new ArrayList();
    public static final int xl = "onActivation".hashCode();
    public static final int xm = "onClick".hashCode();
    public static final int xn = "onDoubleClick".hashCode();
    public static final int xo = "onFocusChange".hashCode();
    public static final int xp = "onItemClick".hashCode();
    public static final int xq = "onItemDoubleClick".hashCode();
    public static final int xr = "onItemOut".hashCode();
    public static final int xs = "onItemOver".hashCode();
    public static final int xt = "onKeyPress".hashCode();
    public static final int xu = "onKeyRelease".hashCode();
    public static final int xv = "onKeyType".hashCode();
    public static final int xw = "onListSelectionChange".hashCode();
    public static final int xx = "onMouseDrag".hashCode();
    public static final int xy = "onMouseDragIn".hashCode();
    public static final int xz = "onMouseDragOut".hashCode();
    public static final int xA = "onMouseEnter".hashCode();
    public static final int xB = "onMouseExit".hashCode();
    public static final int xC = "onMouseMove".hashCode();
    public static final int xD = "onMousePress".hashCode();
    public static final int xE = "onMouseRelease".hashCode();
    public static final int xF = "onMouseWheel".hashCode();
    public static final int xG = "onSelectionChange".hashCode();
    public static final int xH = "onSliderMove".hashCode();
    public static final int xI = "onDrag".hashCode();
    public static final int xJ = "onDrop".hashCode();
    public static final int xK = "onDragOut".hashCode();
    public static final int xL = "onDropOut".hashCode();
    public static final int xM = "onDragOver".hashCode();
    public static final int xN = "onPopupDisplay".hashCode();
    public static final int xO = "onPopupHide".hashCode();

    public void c(qa_1 qa_12) {
        for (atn atn2 : this.G) {
            qa_12.a(atn2.aV(), atn2, false);
        }
    }

    public void d(qa_1 qa_12) {
        for (atn atn2 : this.G) {
            qa_12.b(atn2.aV(), atn2, false);
        }
    }

    public String getTag() {
        return TAG;
    }

    public sn_0 getManager() {
        return this.xf;
    }

    public void setManager(sn_0 sn_02) {
        this.xf = sn_02;
    }

    public void setOnActivation(fa_2 fa_22) {
        this.G.add(fa_22);
    }

    public void setOnClick(apc apc2) {
        this.G.add(apc2);
    }

    public void setOnDoubleClick(auh_0 auh_02) {
        this.G.add(auh_02);
    }

    public void setOnFocusChange(awX awX2) {
        this.G.add(awX2);
    }

    public void setOnKeyPress(nh_0 nh_02) {
        this.G.add(nh_02);
    }

    public void setOnKeyRelease(amv_2 amv_22) {
        this.G.add(amv_22);
    }

    public void setOnKeyType(wf_1 wf_12) {
        this.G.add(wf_12);
    }

    public void setOnListSelectionChange(alw_0 alw_02) {
        this.G.add(alw_02);
    }

    public void setOnMouseDrag(Tg tg) {
        this.G.add(tg);
    }

    public void setOnMouseDragIn(to_0 to_02) {
        this.G.add(to_02);
    }

    public void setOnMouseDragOut(aqz aqz2) {
        this.G.add(aqz2);
    }

    public void setOnMouseEnter(gb_0 gb_02) {
        this.G.add(gb_02);
    }

    public void setOnMouseExit(Se se) {
        this.G.add(se);
    }

    public void setOnMouseMove(yV yV2) {
        this.G.add(yV2);
    }

    public void setOnMousePress(Lw lw) {
        this.G.add(lw);
    }

    public void setOnMouseRelease(aCb aCb2) {
        this.G.add(aCb2);
    }

    public void setOnMouseWheel(fk_1 fk_12) {
        this.G.add(fk_12);
    }

    public void setOnItemOut(nX nX2) {
        this.G.add(nX2);
    }

    public void setOnItemOver(aq_0 aq_02) {
        this.G.add(aq_02);
    }

    public void setOnItemClick(fk fk2) {
        this.G.add(fk2);
    }

    public void setOnItemDoubleClick(aBn aBn2) {
        this.G.add(aBn2);
    }

    public void setOnDrag(anb_0 anb_02) {
        this.G.add(anb_02);
    }

    public void setOnDrop(av_2 av_22) {
        this.G.add(av_22);
    }

    public void setOnDropOut(jd_2 jd_22) {
        this.G.add(jd_22);
    }

    public void setOnDragOut(aza_0 aza_02) {
        this.G.add(aza_02);
    }

    public void setOnDragOver(nf_0 nf_02) {
        this.G.add(nf_02);
    }

    public void setOnSliderMove(fu_1 fu_12) {
        this.G.add(fu_12);
    }

    public void setOnSelectionChange(ala_0 ala_02) {
        this.G.add(ala_02);
    }

    public void setOnPopupDisplay(adz_0 adz_02) {
        this.G.add(adz_02);
    }

    public void setOnPopupHide(pf_1 pf_12) {
        this.G.add(pf_12);
    }

    public void a(na_1 na_12) {
        boolean bl2 = true;
        if (na_12 instanceof jn_2) {
            this.xi = (jn_2)((Object)na_12);
        } else if (na_12 instanceof afz_1) {
            this.xj.add((afz_1)na_12);
        } else {
            if (na_12 instanceof adg_2) {
                ((adg_2)na_12).setVisible(false);
            }
            this.xg.add(na_12);
            na_12.setIsATemplate(true);
            bl2 = false;
        }
        if (bl2) {
            super.a(na_12, false);
        }
    }

    public boolean isRenderableCompatible(qa_1 qa_12) {
        Object object;
        if (this.xi != null && (object = this.xi.getResult(qa_12)) instanceof Boolean) {
            return (Boolean)object;
        }
        return true;
    }

    public void e(qa_1 qa_12) {
        Object object;
        this.xh = null;
        ArrayList arrayList = new ArrayList();
        aji_1 aji_12 = new aji_1(null, this.blb.azj());
        aji_12.c(this.blb);
        for (afz_1 afz_12 : this.xj) {
            afz_12.ava();
            object = afz_12.getProperty();
            if (object != null) {
                ((afl_0)object).i(qa_12);
            }
            afz_12.h((air_1)qa_12);
        }
        this.c(qa_12);
        int n2 = this.xg.size();
        for (int j = 0; j < n2; ++j) {
            object = ((na_1)this.xg.get(j)).aah();
            if (object instanceof adg_2) {
                ((adg_2)object).setVisible(true);
            }
            this.a((na_1)object, xk, arrayList, qa_12, null, aji_12);
            qa_12.g((na_1)object);
        }
        if (this.xh != null) {
            qa_12.setDragNDropable(this.xh);
        }
        qa_12.setInnerElementMap(aji_12);
        qa_12.setRenderableChildren(xk.toArray(new na_1[xk.size()]));
        qa_12.setItemElements(arrayList);
        xk.clear();
    }

    private void a(na_1 na_12, ArrayList arrayList, ArrayList arrayList2, qa_1 qa_12, kn_1 kn_12, aji_1 aji_12) {
        na_12.setElementMap(aji_12);
        if (na_12.getId() != null) {
            aji_12.a(na_12.getId(), na_12);
        }
        if (na_12 instanceof axf && na_12.getParentOfType(ie.class) == null) {
            arrayList2.add((axf)na_12);
            na_1 na_13 = na_12.getParent() != null ? na_12.getParent() : qa_12;
            if (!arrayList.contains(na_13)) {
                arrayList.add(na_13);
            }
        }
        if (na_12 instanceof kn_1) {
            na_12.setRenderableParent(qa_12);
            this.xh = kn_12 = (kn_1)na_12;
        }
        if (na_12 instanceof adg_2 && kn_12 != null) {
            ((adg_2)na_12).setDragAndDropParent(kn_12);
        }
        if (na_12 instanceof adg_2) {
            na_12.setRenderableParent(qa_12);
        }
        for (na_1 na_14 : na_12.getChildren()) {
            this.a(na_14, arrayList, arrayList2, qa_12, kn_12, aji_12);
        }
    }

    private void a(na_1 na_12, String string, int n2, sm_0 sm_02, String string2, jn_2 jn_22) {
        if (na_12 == null || string == null) {
            return;
        }
        try {
            bz_1.a(string, na_12, sm_02, n2, string2, jn_22);
        }
        catch (Exception exception) {
            a.error((Object)("Erreur \u00e0 l'invoke method=" + string), (Throwable)exception);
        }
    }

    public void a(na_1[] na_1Array, sm_0 sm_02) {
        if (na_1Array != null) {
            for (na_1 na_12 : na_1Array) {
                na_1[] na_1Array2;
                ArrayList arrayList = na_12.getChildren();
                for (na_1 na_13 : na_1Array2 = arrayList.toArray(new na_1[arrayList.size()])) {
                    if (!(na_13 instanceof axf)) continue;
                    axf axf2 = (axf)na_13;
                    if (sm_02 != null) {
                        this.a(na_12, axf2.getAttribute(), axf2.getAttributeHash(), sm_02, axf2.getField(), axf2.getResultProvider());
                        continue;
                    }
                    this.a(na_12, axf2.getAttribute(), axf2.getAttributeHash(), null, null, axf2.getResultProvider());
                }
            }
        }
    }

    public boolean b(ke ke2) {
        return false;
    }

    public boolean c(ke ke2) {
        return false;
    }

    public void j() {
        super.j();
        this.xi = null;
        this.xh = null;
        this.G.clear();
        this.G = null;
        this.xj.clear();
        this.xj = null;
        if (this.xg != null) {
            for (int j = this.xg.size() - 1; j >= 0; --j) {
                ((na_1)this.xg.get(j)).release();
            }
            this.xg.clear();
            this.xg = null;
        }
        this.xf = null;
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        ie ie2 = (ie)air_12;
        ie2.G.addAll(this.G);
        for (int j = 0; j < this.xg.size(); ++j) {
            ie2.xg.add(((na_1)this.xg.get(j)).aah());
        }
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == xl) {
            this.setOnActivation((fa_2)if_12.c(fa_2.class, string));
        } else if (n2 == xm) {
            this.setOnClick((apc)if_12.c(apc.class, string));
        } else if (n2 == xn) {
            this.setOnDoubleClick((auh_0)if_12.c(auh_0.class, string));
        } else if (n2 == xo) {
            this.setOnFocusChange((awX)if_12.c(awX.class, string));
        } else if (n2 == xp) {
            this.setOnItemClick((fk)if_12.c(fk.class, string));
        } else if (n2 == xq) {
            this.setOnItemDoubleClick((aBn)if_12.c(aBn.class, string));
        } else if (n2 == xr) {
            this.setOnItemOut((nX)if_12.c(nX.class, string));
        } else if (n2 == xs) {
            this.setOnItemOver((aq_0)if_12.c(aq_0.class, string));
        } else if (n2 == xI) {
            this.setOnDrag((anb_0)if_12.c(anb_0.class, string));
        } else if (n2 == xJ) {
            this.setOnDrop((av_2)if_12.c(av_2.class, string));
        } else if (n2 == xK) {
            this.setOnDragOut((aza_0)if_12.c(aza_0.class, string));
        } else if (n2 == xL) {
            this.setOnDropOut((jd_2)if_12.c(jd_2.class, string));
        } else if (n2 == xM) {
            this.setOnDragOver((nf_0)if_12.c(nf_0.class, string));
        } else if (n2 == xt) {
            this.setOnKeyPress((nh_0)if_12.c(nh_0.class, string));
        } else if (n2 == xu) {
            this.setOnKeyRelease((amv_2)if_12.c(amv_2.class, string));
        } else if (n2 == xv) {
            this.setOnKeyType((wf_1)if_12.c(wf_1.class, string));
        } else if (n2 == xw) {
            this.setOnListSelectionChange((alw_0)if_12.c(alw_0.class, string));
        } else if (n2 == xx) {
            this.setOnMouseDrag((Tg)if_12.c(Tg.class, string));
        } else if (n2 == xy) {
            this.setOnMouseDragIn((to_0)if_12.c(to_0.class, string));
        } else if (n2 == xz) {
            this.setOnMouseDragOut((aqz)if_12.c(aqz.class, string));
        } else if (n2 == xA) {
            this.setOnMouseEnter((gb_0)if_12.c(gb_0.class, string));
        } else if (n2 == xB) {
            this.setOnMouseExit((Se)if_12.c(Se.class, string));
        } else if (n2 == xC) {
            this.setOnMouseMove((yV)if_12.c(yV.class, string));
        } else if (n2 == xD) {
            this.setOnMousePress((Lw)if_12.c(Lw.class, string));
        } else if (n2 == xE) {
            this.setOnMouseRelease((aCb)if_12.c(aCb.class, string));
        } else if (n2 == xF) {
            this.setOnMouseWheel((fk_1)if_12.c(fk_1.class, string));
        } else if (n2 == xG) {
            this.setOnSelectionChange((ala_0)if_12.c(ala_0.class, string));
        } else if (n2 == xH) {
            this.setOnSliderMove((fu_1)if_12.c(fu_1.class, string));
        } else if (n2 == xN) {
            this.setOnPopupDisplay((adz_0)if_12.c(adz_0.class, string));
        } else if (n2 == xO) {
            this.setOnPopupHide((pf_1)if_12.c(pf_1.class, string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }
}

