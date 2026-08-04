/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.Insets;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class sx
extends aht_1
implements Fc,
ajb_0 {
    public static final String TAG = "tree";
    public static final String ajq = "button";
    public static final String ajr = "cell";
    public static final String ajs = "openedCell";
    public static final String ajt = "leafCell";
    public static final String aju = "selectedCell";
    public static final String ajv = "oddCell";
    public static final String ajw = "evenCell";
    public static final String ajx = "scrollBar";
    private sn_0 dR;
    private EV ajy;
    private ArrayList ajz;
    private ArrayList dS;
    private aIg ajA;
    private boolean ajB = false;
    private boolean ajC;
    private int ajD;
    private String ed = null;
    private aji_1 ee = null;
    private boolean ajE;
    private boolean ajF;
    private boolean ajG;
    private ArrayList ajH;
    private boolean ajI = true;
    private boolean ajJ = true;
    private EV ajK = null;
    private ArrayList ajL;
    private boolean ajM = false;
    private boolean ajN = false;
    private boolean ajO = false;
    private int ajP;
    private int ajQ;
    private int ajR;
    private boolean dW;
    private vP ajS;
    private vP ajT;
    public static final int ei = "content".hashCode();
    public static final int ajU = "cellHeight".hashCode();
    public static final int add = "enableDND".hashCode();
    public static final int ajV = "minRows".hashCode();
    public static final int ajW = "maxRows".hashCode();
    public static final int ajX = "openOnlyOne".hashCode();
    public static final int ajY = "selectOnlyOne".hashCode();
    public static final int ajZ = "displayRoot".hashCode();
    public static final int aka = "noClosingOnClick".hashCode();
    public static final int akb = "noUnselectingOnClick".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof ie) {
            this.dR.a((ie)na_12);
        }
        super.a(na_12);
    }

    protected void pX() {
        for (int j = this.ajH.size() - 1; j >= 0; --j) {
            this.arC.i(((apd_0)this.ajH.get(j)).apq());
        }
        super.pX();
    }

    public String getTag() {
        return TAG;
    }

    public adg_2 getWidgetByThemeElementName(String string, boolean bl2) {
        if (string.equals(ajx)) {
            return this.ajA;
        }
        return super.getWidgetByThemeElementName(string, bl2);
    }

    public void setColor(vP vP2, String string) {
        if (string == null || string.equals(ajw)) {
            this.ajS = vP2;
        } else if (string.equals(ajv)) {
            this.ajT = vP2;
        }
    }

    public void setContentProperty(String string, aji_1 aji_12) {
        this.ed = string;
        this.ee = aji_12;
    }

    public int getCellHeight() {
        return this.ajP;
    }

    public void setCellHeight(int n2) {
        this.ajP = n2;
    }

    public int getMinRows() {
        return this.ajQ;
    }

    public void setMinRows(int n2) {
        this.ajQ = n2;
    }

    public int getMaxRows() {
        return this.ajR;
    }

    public void setMaxRows(int n2) {
        this.ajR = n2;
    }

    public boolean isEnableDND() {
        return this.dW;
    }

    public void setEnableDND(boolean bl2) {
        this.dW = bl2;
    }

    public boolean getSelectOnlyOne() {
        return this.ajI;
    }

    public void setSelectOnlyOne(boolean bl2) {
        this.ajI = bl2;
    }

    public EV getSelected() {
        return this.ajK;
    }

    public boolean getOpenOnlyOne() {
        return this.ajJ;
    }

    public void setOpenOnlyOne(boolean bl2) {
        this.ajJ = bl2;
    }

    public EV getTopOpened() {
        if (!this.ajL.isEmpty()) {
            return (EV)this.ajL.get(this.ajL.size() - 1);
        }
        return null;
    }

    public void setContent(EV eV) {
        this.ajy = eV;
        if (!this.ajM) {
            this.ajy.bo(true);
        }
        this.setContentDirty();
    }

    private void setContentDirty() {
        this.ajG = true;
        this.setNeedsToPreProcess();
    }

    private void setTreeDirty() {
        this.ajF = true;
        this.setNeedsToPreProcess();
    }

    private void setValuesDirty() {
        this.ajE = true;
        this.setNeedsToPostProcess();
    }

    public void setOffset(int n2) {
        if (this.ajD == n2) {
            return;
        }
        float f = this.dA(n2);
        this.ajA.getSlider().setValue(f);
    }

    private void setListOffset(int n2) {
        if (this.ajD == n2) {
            return;
        }
        this.ajD = n2;
        this.setValuesDirty();
    }

    public void setDisplayRoot(boolean bl2) {
        this.ajM = bl2;
    }

    public void setNoClosingOnClick(boolean bl2) {
        this.ajN = bl2;
    }

    public void setNoUnselectingOnClick(boolean bl2) {
        this.ajO = bl2;
    }

    private void yv() {
        boolean bl2;
        EV eV = this.getTopOpened();
        this.ajz.clear();
        akd_2 akd_22 = new akd_2(this.ajy);
        boolean bl3 = bl2 = !this.ajM;
        while (akd_22.hasNext()) {
            if (bl2) {
                akd_22.aVy();
                bl2 = false;
                continue;
            }
            this.ajz.add(akd_22.aVy());
        }
        int n2 = this.ajD;
        int n3 = this.ajz.indexOf(eV);
        if (this.ajz.size() <= this.dS.size()) {
            n2 = 0;
        } else if ((n3 == -1 || n3 < this.ajD || n3 >= this.ajD + this.dS.size()) && n3 != -1 && n3 + this.dS.size() <= this.ajz.size()) {
            n2 = n3;
        }
        n2 = ej_0.e(n2, 0, this.ajz.size() - this.dS.size());
        if (n2 != this.ajD) {
            this.ajD = n2;
            this.setOffset(n2);
        }
    }

    private int yw() {
        return this.a(this.ajy) - (this.ajM ? 0 : 1);
    }

    private int a(EV eV) {
        if (eV == null) {
            return 0;
        }
        int n2 = 1;
        if (eV.hasChildren() && eV.OL()) {
            ArrayList arrayList = eV.getChildren();
            int n3 = arrayList.size();
            for (int j = 0; j < n3; ++j) {
                n2 += this.a((EV)arrayList.get(j));
            }
        }
        return n2;
    }

    private float dA(int n2) {
        int n3;
        int n4;
        if (n2 < 0) {
            n2 = 0;
        }
        if (n2 > (n4 = (n3 = this.yw()) - this.dS.size()) + 1) {
            n2 = n4 + 1;
        }
        return 1.0f - (float)n2 / (float)n4;
    }

    private int R(float f) {
        int n2 = this.yw();
        float f2 = n2 - this.dS.size();
        float f3 = f2 - (float)Math.round(f2 * f);
        if (f3 < 0.0f) {
            f3 = 0.0f;
        } else if (f3 > f2 + 1.0f) {
            f3 = f2 + 1.0f;
        }
        return Math.round(f3);
    }

    public void ca() {
        Object object;
        Object object2;
        int n2;
        if (this.ajC || this.dS == null) {
            return;
        }
        int n3 = this.ajD;
        int n4 = 0;
        int n5 = this.ajz.size();
        for (n2 = 0; n2 < n5; ++n2) {
            object2 = (EV)this.ajz.get(n2);
            if (n3 != 0) {
                --n3;
                continue;
            }
            if (n4 == this.dS.size()) break;
            object = (qa_1)this.dS.get(n4);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.ed);
            stringBuilder.append("#").append(n4 + this.ajD);
            ((qa_1)object).setContentProperty(stringBuilder.toString(), this.ee);
            ((qa_1)object).setContent(object2.getValue());
            String string = this.getStyle();
            StringBuilder stringBuilder2 = new StringBuilder(TAG);
            if (string != null) {
                stringBuilder2.append(string);
            }
            stringBuilder2.append("$");
            if (object2.OL()) {
                stringBuilder2.append(ajs);
            } else if (object2.isSelected()) {
                stringBuilder2.append(aju);
            } else if (object2.hasChildren()) {
                stringBuilder2.append(ajr);
            } else {
                stringBuilder2.append(ajt);
            }
            ((aht_1)object).getAppearance().setMargin(new Insets(0, (object2.getDepth() - (this.ajM ? 0 : 1)) * 10 + 5, 0, 0));
            ((adg_2)object).setStyle(stringBuilder2.toString(), true);
            ++n4;
        }
        n5 = this.dS.size();
        for (n2 = n4; n2 < n5; ++n2) {
            object2 = (qa_1)this.dS.get(n4);
            object = new StringBuilder();
            ((StringBuilder)object).append(this.ed);
            ((StringBuilder)object).append("#").append(n2 + this.ajD);
            ((qa_1)object2).setContentProperty(((StringBuilder)object).toString(), this.ee);
            ((qa_1)object2).setContent(null);
        }
    }

    public void yx() {
        this.ajA.yx();
        super.yx();
        this.a(qe_1.bFH, new amb_2(this), false);
        this.a(qe_1.bFD, new amc_1(this), false);
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.ajG) {
            this.yv();
            this.setValuesDirty();
            this.ajF = true;
            this.ajG = false;
        }
        if (this.ajF) {
            super.Am();
            this.invalidate();
            this.ajF = false;
        }
        return bl2;
    }

    public boolean cb(int n2) {
        boolean bl2 = super.cb(n2);
        if (this.ajE) {
            this.ca();
            this.ajE = false;
        }
        return bl2;
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        sx sx2 = (sx)air_12;
        sx2.setCellHeight(this.ajP);
        sx2.setMinRows(this.ajQ);
        sx2.setMaxRows(this.ajR);
        sx2.setEnableDND(this.dW);
        sx2.setSelectOnlyOne(this.ajI);
        sx2.setOpenOnlyOne(this.ajJ);
        for (int j = sx2.dMc.size() - 1; j >= 0; --j) {
            adg_2 adg_22 = (adg_2)sx2.dMc.get(j);
            if (adg_22 == sx2.ajA) continue;
            adg_22.aab();
        }
    }

    public void j() {
        super.j();
        this.ajS = null;
        this.ajT = null;
        this.ajA = null;
        this.ajy = null;
        this.ajz = null;
        this.ajH = null;
        this.dS = null;
        this.ajK = null;
        this.ajL = null;
    }

    public void b() {
        super.b();
        kk_2 kk_22 = new kk_2(this);
        kk_22.b();
        this.a(kk_22);
        this.ajA = new aIg();
        this.ajA.b();
        this.ajA.setHorizontal(false);
        this.ajA.setValue(1.0f);
        this.a(this.ajA);
        this.ajQ = -1;
        this.ajR = -1;
        this.ajP = 30;
        this.dW = true;
        this.ajD = 0;
        this.ajC = false;
        this.ajF = false;
        this.dR = new sn_0();
        this.dS = new ArrayList();
        this.ajH = new ArrayList();
        this.ajz = new ArrayList();
        this.ajL = new ArrayList();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == ajU) {
            this.setCellHeight(Gr.R(string));
        } else if (n2 == add) {
            this.setEnableDND(Gr.getBoolean(string));
        } else if (n2 == ajV) {
            this.setMinRows(Gr.R(string));
        } else if (n2 == ajW) {
            this.setMaxRows(Gr.R(string));
        } else if (n2 == ajX) {
            this.setOpenOnlyOne(Gr.getBoolean(string));
        } else if (n2 == ajY) {
            this.setSelectOnlyOne(Gr.getBoolean(string));
        } else if (n2 == ajZ) {
            this.setDisplayRoot(Gr.getBoolean(string));
        } else if (n2 == aka) {
            this.setNoClosingOnClick(Gr.getBoolean(string));
        } else if (n2 == akb) {
            this.setNoUnselectingOnClick(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != ei) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setContent((EV)object);
        return true;
    }

    static /* synthetic */ int a(sx sx2) {
        return sx2.yw();
    }

    static /* synthetic */ int b(sx sx2) {
        return sx2.ajR;
    }

    static /* synthetic */ int c(sx sx2) {
        return sx2.ajQ;
    }

    static /* synthetic */ aIg d(sx sx2) {
        return sx2.ajA;
    }

    static /* synthetic */ int e(sx sx2) {
        return sx2.ajP;
    }

    static /* synthetic */ boolean a(sx sx2, boolean bl2) {
        sx2.ajC = bl2;
        return sx2.ajC;
    }

    static /* synthetic */ ArrayList f(sx sx2) {
        return sx2.ajz;
    }

    static /* synthetic */ ArrayList g(sx sx2) {
        return sx2.dS;
    }

    static /* synthetic */ ArrayList h(sx sx2) {
        return sx2.ajH;
    }

    static /* synthetic */ sn_0 i(sx sx2) {
        return sx2.dR;
    }

    static /* synthetic */ boolean j(sx sx2) {
        return sx2.dW;
    }

    static /* synthetic */ int k(sx sx2) {
        return sx2.ajD;
    }

    static /* synthetic */ boolean l(sx sx2) {
        return sx2.ajN;
    }

    static /* synthetic */ boolean m(sx sx2) {
        return sx2.ajJ;
    }

    static /* synthetic */ ArrayList n(sx sx2) {
        return sx2.ajL;
    }

    static /* synthetic */ boolean o(sx sx2) {
        return sx2.ajO;
    }

    static /* synthetic */ boolean p(sx sx2) {
        return sx2.ajI;
    }

    static /* synthetic */ EV q(sx sx2) {
        return sx2.ajK;
    }

    static /* synthetic */ EV a(sx sx2, EV eV) {
        sx2.ajK = eV;
        return sx2.ajK;
    }

    static /* synthetic */ void r(sx sx2) {
        sx2.setContentDirty();
    }

    static /* synthetic */ boolean b(sx sx2, boolean bl2) {
        sx2.ajB = bl2;
        return sx2.ajB;
    }

    static /* synthetic */ boolean s(sx sx2) {
        return sx2.ajB;
    }

    static /* synthetic */ Logger yy() {
        return a;
    }

    static /* synthetic */ int a(sx sx2, float f) {
        return sx2.R(f);
    }

    static /* synthetic */ void a(sx sx2, int n2) {
        sx2.setListOffset(n2);
    }
}

