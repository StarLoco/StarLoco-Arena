/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Renamed from RF
 */
public class rf_0
extends aht_1
implements Fc,
px_2 {
    public static final String TAG = "List";
    public static final String bJx = "horizontalScrollBar";
    public static final String bJy = "verticalScrollBar";
    private rH bJz;
    private aIg ajA = null;
    private float bJA = -1.0f;
    private float bJB = -1.0f;
    private agj_1 bfP = new agj_1();
    private boolean bJC = false;
    private boolean dW = true;
    protected float bJD = 0.0f;
    private int bJE = 1;
    private boolean ajC = false;
    private boolean ba;
    private boolean bJF = false;
    private int bJG = 0;
    private boolean ajB = false;
    private boolean bJH = false;
    private int bJI = -1;
    private int bJJ = -1;
    private int bJK = -1;
    private int bJL = -1;
    private eF bJM = eF.pa;
    private int bJN = 1;
    private boolean bJO = true;
    private boolean dY = false;
    private boolean dX = true;
    private aDM bJP = aDM.dyU;
    private ArrayList bJQ;
    protected ArrayList dS;
    private qa_1 bJR = null;
    private qa_1 dT = null;
    private int dV = -1;
    private sn_0 dR = new sn_0();
    protected ArrayList ec = null;
    private ArrayList bJS = null;
    private BT bBx;
    private String ed = null;
    private aji_1 ee = null;
    private boolean bJT = false;
    private boolean bJU = false;
    private apd_0 bJV;
    private apd_0 bfY;
    public static long bJW = 0L;
    public static final int cJ = "align".hashCode();
    public static final int bJX = "adaptCellSizeToContentSize".hashCode();
    public static final int bJY = "autoIdealSize".hashCode();
    public static final int bfZ = "cellSize".hashCode();
    public static final int ei = "content".hashCode();
    public static final int ej = "horizontal".hashCode();
    public static final int bJZ = "idealSizeMaxColumns".hashCode();
    public static final int bKa = "idealSizeMaxRows".hashCode();
    public static final int bKb = "idealSizeMinColumns".hashCode();
    public static final int bKc = "idealSizeMinRows".hashCode();
    public static final int bKd = "listFilter".hashCode();
    public static final int bKe = "listOffset".hashCode();
    public static final int bKf = "minDisplayedCells".hashCode();
    public static final int bKg = "mouseOverColor".hashCode();
    public static final int bKh = "selectedColor".hashCode();
    public static final int bKi = "offset".hashCode();
    public static final int bKj = "oppositeScrollBarPosition".hashCode();
    public static final int bKk = "scrollBar".hashCode();
    public static final int bKl = "scrollBarBehaviour".hashCode();
    public static final int ek = "selected".hashCode();
    public static final int el = "selectedValue".hashCode();
    public static final int em = "selectionable".hashCode();
    public static final int en = "selectionTogglable".hashCode();
    public static final int bKm = "showOneMore".hashCode();
    public static final int bKn = "wishedMinSize".hashCode();
    public static final int add = "enableDND".hashCode();
    public static final int bKo = "listLayoutMode".hashCode();
    public static final int bKp = "isoColumnCount".hashCode();
    public static final int bKq = "isoPositiveFactor".hashCode();

    public rf_0() {
        this(false);
    }

    public rf_0(boolean bl2) {
        this.ba = bl2;
    }

    public void a(na_1 na_12) {
        super.a(na_12);
        if (na_12 instanceof ie) {
            this.dR.a((ie)na_12);
        }
    }

    protected void pX() {
        if (this.bfY != null && this.dT != null) {
            this.arC.i(this.bfY.apq());
        }
        if (this.bJR != null && this.bJV != null) {
            this.arC.i(this.bJV.apq());
        }
        super.pX();
    }

    public void yx() {
        this.ajA.yx();
        super.yx();
        this.a(qe_1.bFH, new afw_1(this), false);
        this.a(qe_1.bFD, new afu_0(this), false);
    }

    public void EO() {
        for (qa_1 qa_12 : this.dS) {
            qa_12.EO();
        }
        this.ajA.EO();
        super.EO();
    }

    public boolean getEnableDND() {
        return this.dW;
    }

    public void setEnableDND(boolean bl2) {
        if (this.dW != bl2) {
            this.dW = bl2;
            for (int j = this.dS.size() - 1; j >= 0; --j) {
                ((qa_1)this.dS.get(j)).setEnableDND(bl2);
            }
        }
    }

    public void setEnabled(boolean bl2) {
        super.setEnabled(bl2);
        for (int j = this.dS.size() - 1; j >= 0; --j) {
            ((qa_1)this.dS.get(j)).setEnabled(bl2);
        }
    }

    public adg_2 getWidget(int n2, int n3) {
        if (this.czc || !this.aQv || !this.getAppearance().aY(n2, n3) || ago_2.getInstance().isMovePointMode()) {
            return null;
        }
        adg_2 adg_22 = null;
        adg_2 adg_23 = null;
        n2 -= this.getAppearance().getLeftInset();
        n3 -= this.getAppearance().getBottomInset();
        for (qa_1 qa_12 : this.dS) {
            if (qa_12.isUnloading() || (adg_22 = ((adg_2)qa_12).getWidget(n2 - qa_12.getX(), n3 - qa_12.getY())) == null) continue;
            adg_23 = adg_22;
        }
        if (this.ajB && !this.ajA.isUnloading()) {
            adg_22 = this.ajA.getWidget(n2 - this.ajA.getX(), n3 - this.ajA.getY());
        }
        if (adg_22 != null) {
            adg_23 = adg_22;
        }
        return adg_23;
    }

    public void e(abd_1 abd_12) {
        this.setOffset(this.bJD + (float)abd_12.aNb());
    }

    public void setOffset(float f) {
        this.ajA.getSlider().setValue(this.ap(f));
    }

    public float getOffset() {
        return this.bJD;
    }

    private float ap(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (this.ba) {
            float f2 = (float)this.getPotentialColumnCount(this.bJB) - this.bJA + (float)this.bJG;
            if (f > f2 + 1.0f) {
                f = f2 + 1.0f;
            }
            return f / f2;
        }
        float f3 = (float)this.getPotentialRowCount(this.bJA) - this.bJB + (float)this.bJG;
        if (f > f3 + 1.0f) {
            f = f3 + 1.0f;
        }
        return 1.0f - f / f3;
    }

    private float aq(float f) {
        float f2;
        float f3;
        if (this.ba) {
            f3 = (float)this.getPotentialColumnCount(this.bJB) - this.bJA + (float)this.bJG;
            f2 = f3 * f;
        } else {
            f3 = (float)this.getPotentialRowCount(this.bJA) - this.bJB + (float)this.bJG;
            f2 = f3 * (1.0f - f);
        }
        if (f2 < 0.0f) {
            f2 = 0.0f;
        } else if (f2 > f3 + 1.0f) {
            f2 = f3 + 1.0f;
        }
        return f2;
    }

    private int getPotentialRowCount(float f) {
        ArrayList arrayList = this.getItems();
        if (arrayList == null) {
            return 0;
        }
        return (int)Math.ceil((float)arrayList.size() / f);
    }

    private int getPotentialColumnCount(float f) {
        ArrayList arrayList = this.getItems();
        if (arrayList == null) {
            return 0;
        }
        return (int)Math.ceil((float)arrayList.size() / f);
    }

    public void setListOffset(float f) {
        this.setListOffset(f, true);
    }

    public void setListOffset(float f, boolean bl2) {
        float f2 = this.bJD;
        this.bJD = f;
        int n2 = (int)((Math.floor(this.bJD) - Math.floor(f2)) * (double)(this.ba ? this.bJB : this.bJA));
        boolean bl3 = n2 != 0;
        float f3 = f - f2;
        if (bl3) {
            if (f3 > 0.0f) {
                for (int j = 0; j < n2; ++j) {
                    this.dS.add(this.dS.remove(0));
                }
            } else if (f3 < 0.0f) {
                for (int j = 0; j < -n2; ++j) {
                    this.dS.add(0, this.dS.remove(this.dS.size() - 1));
                }
            }
        }
        if (bl2) {
            this.getListLayout().ah(false);
        }
        if (bl3 && bl2) {
            this.hy(n2);
        }
    }

    protected void ca() {
        this.hy(0);
    }

    private void hy(int n2) {
        int n3;
        int n4;
        if (this.ajC || this.dS == null) {
            return;
        }
        this.dT = null;
        boolean bl2 = false;
        int n5 = this.ba ? (int)(Math.floor(this.bJD) * Math.ceil(this.bJB)) : (int)(Math.floor(this.bJD) * Math.ceil(this.bJA));
        for (n4 = 0; n4 < this.dS.size(); ++n4) {
            n3 = n4 + n5;
            qa_1 qa_12 = (qa_1)this.dS.get(n4);
            if (n4 < 0 || n4 >= this.dS.size() || this.ec == null || n3 < 0 || n3 >= this.ec.size() || n3 != this.dV || bl2) continue;
            bl2 = true;
            this.dT = qa_12;
            break;
        }
        n4 = 0;
        n3 = this.dS.size();
        if (n2 > 0) {
            n4 = n3 - n2;
        } else if (n2 < 0) {
            n3 = -n2;
        }
        while (n4 < n3) {
            int n6 = n4 + n5;
            if (n4 >= 0 && n4 < this.dS.size()) {
                qa_1 qa_13 = (qa_1)this.dS.get(n4);
                qa_13.setContentProperty(this.ed + "#" + n6, this.ee);
                if (this.ec != null && n6 >= 0 && n6 < this.ec.size()) {
                    qa_13.setContent(this.ec.get(n6));
                } else {
                    qa_13.setContent(null);
                }
            }
            ++n4;
        }
        if (!bl2) {
            this.dT = null;
        }
        this.cb();
    }

    public eF getListLayoutMode() {
        return this.bJM;
    }

    public void setListLayoutMode(eF eF2) {
        if (this.bJM == eF2) {
            return;
        }
        this.bJM = eF2;
        a_0 a_02 = null;
        switch (this.bJM) {
            case pa: {
                a_02 = new li_1(this);
                break;
            }
            case pb: {
                a_02 = new oi_1(this);
            }
        }
        a_02.b();
        this.a(a_02);
    }

    public int getIsoColumnCount() {
        return this.bJN;
    }

    public void setIsoColumnCount(int n2) {
        this.bJN = n2;
    }

    public boolean getIsoPositiveFactor() {
        return this.bJO;
    }

    public void setIsoPositiveFactor(boolean bl2) {
        this.bJO = bl2;
    }

    public void setListFilter(rH rH2) {
        this.bJz = rH2;
    }

    public rH getListFilter() {
        return this.bJz;
    }

    public void setSelectionTogglable(boolean bl2) {
        this.dY = bl2;
    }

    public boolean getSelectionTogglable() {
        return this.dY;
    }

    public boolean isUsePositionTween() {
        return this.dyr;
    }

    public void setSelectionable(boolean bl2) {
        this.dX = bl2;
    }

    public boolean getSelectionable() {
        return this.dX;
    }

    public void setHorizontal(boolean bl2) {
        this.ba = bl2;
        this.ajA.setHorizontal(bl2);
        this.setOffset(this.bJD);
        this.bJT = true;
        this.setNeedsToPreProcess();
    }

    public boolean getHorizontal() {
        return this.ba;
    }

    public void setCellSize(agj_1 agj_12) {
        this.bfP = agj_12;
        this.bJT = true;
        this.setNeedsToPreProcess();
    }

    public String getTag() {
        return TAG;
    }

    public boolean getAdaptCellSizeToContentSize() {
        return this.bJC;
    }

    public void setAdaptCellSizeToContentSize(boolean bl2) {
        this.bJC = bl2;
    }

    public void setNeedsScissor(boolean bl2) {
        super.setNeedsScissor(bl2);
    }

    public aDM getScrollbarBehaviour() {
        return this.bJP;
    }

    public void setScrollBar(boolean bl2) {
        this.bJP = bl2 ? aDM.dyV : aDM.dyW;
    }

    public boolean isScrollBarDisplayed() {
        switch (this.bJP) {
            case dyV: {
                return true;
            }
            case dyW: {
                return false;
            }
        }
        return this.ajB;
    }

    public void setScrollBarBehaviour(aDM aDM2) {
        this.bJP = aDM2;
    }

    public vP getSelectedColor() {
        if (this.bfY == null) {
            return null;
        }
        return this.bfY.getColor();
    }

    public void setSelectedColor(vP vP2) {
        if (vP2 != null) {
            if (this.bfY == null) {
                this.bfY = new apd_0();
                this.bfY.b();
                this.setNeedsToResetMeshes();
            }
            this.bfY.setColor(vP2);
        } else {
            if (this.bfY != null) {
                this.bfY.j();
            }
            this.bfY = null;
            this.setNeedsToResetMeshes();
        }
    }

    public vP getMouseOverColor() {
        if (this.bJV == null) {
            return null;
        }
        return this.bJV.getColor();
    }

    public void setMouseOverColor(vP vP2) {
        if (vP2 != null) {
            if (this.bJV == null) {
                this.bJV = new apd_0();
                this.bJV.b();
                this.setNeedsToResetMeshes();
            }
            this.bJV.setColor(vP2);
        } else {
            if (this.bJV != null) {
                this.bJV.j();
            }
            this.bJV = null;
            this.setNeedsToResetMeshes();
        }
    }

    public int getMinDisplayedCells() {
        return this.bJE;
    }

    public void setMinDisplayedCells(int n2) {
        this.bJE = n2;
        this.bJT = true;
        this.setNeedsToPreProcess();
    }

    public boolean isAutoIdealSize() {
        return this.bJH;
    }

    public void setAutoIdealSize(boolean bl2) {
        this.bJH = bl2;
        this.bJT = true;
        this.setNeedsToPreProcess();
    }

    public void setAutoIdealSize(boolean bl2, int n2, int n3) {
        this.bJH = bl2;
        this.bJI = n2;
        this.bJJ = n3;
        this.bJT = true;
        this.setNeedsToPreProcess();
    }

    public void setAutoIdealSize(boolean bl2, int n2, int n3, int n4, int n5) {
        this.bJH = bl2;
        this.bJI = n2;
        this.bJJ = n3;
        this.bJK = n4;
        this.bJL = n5;
        this.bJT = true;
        this.setNeedsToPreProcess();
    }

    public int getIdealSizeMaxColumns() {
        return this.bJJ;
    }

    public void setIdealSizeMaxColumns(int n2) {
        this.bJJ = n2;
        this.bJT = true;
        this.setNeedsToPreProcess();
    }

    public int getIdealSizeMaxRows() {
        return this.bJI;
    }

    public void setIdealSizeMaxRows(int n2) {
        this.bJI = n2;
        this.bJT = true;
        this.setNeedsToPreProcess();
    }

    public int getIdealSizeMinColumns() {
        return this.bJL;
    }

    public void setIdealSizeMinColumns(int n2) {
        this.bJL = n2;
        this.bJT = true;
        this.setNeedsToPreProcess();
    }

    public int getIdealSizeMinRows() {
        return this.bJK;
    }

    public void setIdealSizeMinRows(int n2) {
        this.bJK = n2;
        this.bJT = true;
        this.setNeedsToPreProcess();
    }

    public void setShowOneMore(boolean bl2) {
        this.bJG = bl2 ? 1 : 0;
        this.bJT = true;
        this.setNeedsToPreProcess();
    }

    public BT getAlign() {
        return this.bBx;
    }

    public void setAlign(BT bT) {
        if (this.ba) {
            if (bT.equals((Object)BT.aJU) || bT.equals((Object)BT.aJX) || bT.equals((Object)BT.aKa)) {
                this.bBx = bT;
            }
        } else if (bT.equals((Object)BT.aJW) || bT.equals((Object)BT.aJX) || bT.equals((Object)BT.aJY)) {
            this.bBx = bT;
        }
    }

    public boolean isOppositeScrollBarPosition() {
        return this.bJF;
    }

    public void setOppositeScrollBarPosition(boolean bl2) {
        this.bJF = bl2;
    }

    public agj_1 getWishedMinSize() {
        return this.getPrefSize();
    }

    public void setWishedMinSize(agj_1 agj_12) {
        this.setPrefSize(agj_12);
    }

    public agj_1 getIdealSize() {
        return this.getIdealSize(-1, -1);
    }

    public agj_1 getIdealSize(int n2, int n3) {
        agj_1 agj_12 = this.getContentIdealSize(n2, n3, -1, -1);
        int n4 = agj_12.width;
        int n5 = agj_12.height;
        return new agj_1(n4 += this.cLZ.getLeftInset() + this.cLZ.getRightInset(), n5 += this.cLZ.getTopInset() + this.cLZ.getBottomInset());
    }

    public agj_1 getContentIdealSize(int n2, int n3, int n4, int n5) {
        int n6 = 10;
        int n7 = 10;
        int n8 = this.ec == null ? 0 : this.ec.size();
        int n9 = n5 == -1 ? Integer.MIN_VALUE : n5;
        int n10 = n3 == -1 ? Integer.MAX_VALUE : n3;
        int n11 = n4 == -1 ? Integer.MIN_VALUE : n4;
        int n12 = n2 == -1 ? Integer.MAX_VALUE : n2;
        int n13 = 0;
        int n14 = 0;
        if (this.bfP.awj() == -1.0f || this.bfP.awi() == -1.0f) {
            int n15;
            if (this.bfP.awi() != -1.0f) {
                n15 = (int)(1.0f / this.bfP.awi() * 100.0f);
                n14 = Math.max(n9, Math.min(n10, n15));
                n13 = Math.max(n11, Math.min(n12, this.getPotentialRowCount(n14)));
            } else if (this.bfP.awj() != -1.0f) {
                n15 = (int)(1.0f / this.bfP.awj() * 100.0f);
                n13 = Math.max(n11, Math.min(n12, n15));
                n14 = Math.max(n9, Math.min(n10, this.getPotentialColumnCount(n13)));
            } else {
                boolean bl2;
                n15 = n3 >= 0 || n5 >= 0 ? 1 : 0;
                boolean bl3 = bl2 = n2 >= 0 || n4 >= 0;
                if (n15 != 0 && !bl2) {
                    n14 = Math.min(Math.max(n3, n5), n8);
                    n13 = this.getPotentialRowCount(n14);
                } else if (n15 == 0 && bl2) {
                    n13 = Math.min(Math.max(n2, n4), n8);
                    n14 = this.getPotentialColumnCount(n13);
                } else if (n15 != 0 && bl2) {
                    if (this.ba) {
                        n13 = Math.max(n11, Math.min(n2, this.getPotentialRowCount(Math.max(1, n9))));
                        n14 = Math.max(n9, Math.min(n3, this.getPotentialColumnCount(n13)));
                    } else {
                        n14 = Math.max(n9, Math.min(n3, this.getPotentialColumnCount(Math.max(1, n11))));
                        n13 = Math.max(n11, Math.min(n2, this.getPotentialRowCount(n14)));
                    }
                } else if (this.ba) {
                    n13 = 1;
                    n14 = n8;
                } else {
                    n14 = 1;
                    n13 = n8;
                }
            }
            n7 = this.bfP.height * n13;
            n6 = this.bfP.width * n14;
        }
        if (n14 * n13 < n8) {
            agj_1 agj_12 = this.ajA.getPrefSize();
            if (this.ba) {
                n7 += agj_12.height;
            } else {
                n6 += agj_12.width;
            }
        }
        return new agj_1(n6, n7);
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return zb instanceof on_0;
    }

    public on_0 getAppearance() {
        return (on_0)this.cLZ;
    }

    public aIg getScrollBar() {
        return this.ajA;
    }

    public adg_2 getWidgetByThemeElementName(String string, boolean bl2) {
        if (this.ba && bJx.equalsIgnoreCase(string) || !this.ba && bJy.equalsIgnoreCase(string) || bl2) {
            return this.ajA;
        }
        return null;
    }

    public void setSelected(sm_0 sm_02) {
        this.setSelectedOffset(this.ec.indexOf(sm_02));
    }

    public int getSelectedOffset() {
        return this.dV;
    }

    public void setSelectedOffset(int n2) {
        if (n2 == this.dV) {
            return;
        }
        ArrayList arrayList = this.getItems();
        int n3 = this.dV;
        this.dV = n2;
        qa_1 qa_12 = this.dT;
        this.dT = this.getRenderableByOffset(n2);
        if (this.ec != null && n3 != -1) {
            this.f(new hf_0(this, qa_12, arrayList.get(n3), false));
        }
        if (this.ec != null && this.dV != -1) {
            this.f(new hf_0(this, this.dT, arrayList.get(this.dV), true));
        }
        this.cb();
    }

    public void setSelectedValue(Object object) {
        ArrayList arrayList = this.getItems();
        if (arrayList == null) {
            return;
        }
        int n2 = -1;
        for (int j = 0; j < arrayList.size(); ++j) {
            Object e = arrayList.get(j);
            if (e == null || e != object && !e.equals(object)) continue;
            n2 = j;
            break;
        }
        this.setSelectedOffset(n2);
    }

    public Object getSelectedValue() {
        return this.getSelectedValue(this.getItems());
    }

    public Object getSelectedValue(ArrayList arrayList) {
        if (this.dV < 0 || arrayList == null || this.dV >= arrayList.size()) {
            return null;
        }
        return arrayList.get(this.dV);
    }

    public qa_1 getSelected() {
        return this.dT;
    }

    public int getSelectedOffsetByValue(Object object) {
        int n2;
        for (n2 = 0; n2 < this.getItems().size() && this.getItems().get(n2) != object; ++n2) {
        }
        if (n2 == this.getItems().size()) {
            return -1;
        }
        return n2;
    }

    public qa_1 getRenderableByOffset(int n2) {
        int n3 = this.ba ? n2 - (int)(Math.floor(this.bJD) * Math.ceil(this.bJB)) : n2 - (int)(Math.floor(this.bJD) * Math.ceil(this.bJA));
        if (n3 < 0 || n3 >= this.dS.size()) {
            return null;
        }
        return (qa_1)this.dS.get(n3);
    }

    public int getOffsetByRenderable(qa_1 qa_12) {
        if (qa_12 == null || this.ec == null) {
            return -1;
        }
        int n2 = this.ba ? (int)(Math.floor(this.bJD) * Math.ceil(this.bJB)) + this.dS.indexOf(qa_12) : (int)(Math.floor(this.bJD) * Math.ceil(this.bJA)) + this.dS.indexOf(qa_12);
        if (n2 >= this.ec.size()) {
            n2 = -1;
        }
        return n2;
    }

    public adg_2 getWidget(String string, int n2) {
        if (n2 >= 0 && n2 < this.dS.size()) {
            return (adg_2)this.dS.get(n2);
        }
        return null;
    }

    private void cb() {
        if (this.dT != null && this.bfY != null) {
            this.bfY.a(this.dT.getPosition(), this.dT.getSize(), this.cLZ.getTotalInsets());
        }
        this.setNeedsToResetMeshes();
    }

    private void a(qa_1 qa_12) {
        if (qa_12 == this.dT) {
            return;
        }
        if (qa_12 != null) {
            this.setSelectedOffset(this.getOffsetByRenderable(qa_12));
        } else {
            this.dV = -1;
        }
    }

    private float aT(int n2, int n3) {
        float f;
        if (this.ba) {
            if (!this.bBx.equals((Object)BT.aJX)) {
                f = (float)Math.floor((float)n2 / (float)n3);
            } else if (this.ec != null && this.ec.size() > 0) {
                int n4 = (int)Math.floor((double)n2 / (double)n3);
                f = Math.min(this.ec.size(), n4);
            } else {
                f = 0.0f;
            }
        } else {
            f = (float)n2 / (float)n3;
        }
        return f;
    }

    private float aU(int n2, int n3) {
        float f;
        if (this.ba) {
            f = (float)n2 / (float)n3;
        } else if (!this.bBx.equals((Object)BT.aJX)) {
            f = (int)Math.floor((double)n2 / (double)n3);
        } else if (this.ec != null && this.ec.size() > 0) {
            int n4 = (int)Math.floor((double)n2 / (double)n3);
            f = Math.min(this.ec.size(), n4);
        } else {
            f = 0.0f;
        }
        return f;
    }

    private void Ox() {
        if (this.ajB) {
            if (this.ba) {
                float f = this.getPotentialColumnCount(this.bJB);
                if (this.bJB + (float)this.bJG > 0.0f && f - this.bJA + (float)this.bJG > 0.0f) {
                    if (!this.ajA.getEnabled()) {
                        this.ajA.setEnabled(true);
                    }
                    this.ajA.setButtonJump(1.0f / (f - this.bJA + (float)this.bJG));
                    this.ajA.getSlider().setSliderSize(this.bJA / (f + (float)this.bJG));
                } else {
                    this.ajA.setButtonJump(0.0f);
                    this.ajA.setEnabled(false);
                }
            } else {
                float f = this.getPotentialRowCount(this.bJA);
                if (this.bJA + (float)this.bJG > 0.0f && f - this.bJB + (float)this.bJG > 0.0f) {
                    if (!this.ajA.getEnabled()) {
                        this.ajA.setEnabled(true);
                    }
                    this.ajA.setButtonJump(1.0f / (f - this.bJB + (float)this.bJG));
                    this.ajA.getSlider().setSliderSize(this.bJB / (f + (float)this.bJG));
                } else {
                    this.ajA.setButtonJump(0.0f);
                    this.ajA.setEnabled(false);
                }
            }
        }
    }

    public void yv() {
        this.ec = this.bJS;
        this.bJS = null;
        this.aep();
        this.bJT = true;
        this.setNeedsToPreProcess();
    }

    public qn_1 getListLayout() {
        return (qn_1)((Object)this.dMe);
    }

    public boolean isHorizontal() {
        return this.ba;
    }

    public ArrayList getItems() {
        if (this.bJS != null) {
            return this.bJS;
        }
        return this.ec;
    }

    public void setContentProperty(String string, aji_1 aji_12) {
        this.ed = string;
        this.ee = aji_12;
    }

    public void setContent(Object[] objectArray) {
        if (this.czn) {
            return;
        }
        int n2 = this.dV;
        Object object = this.getSelectedValue();
        this.bJS = new ArrayList();
        if (objectArray != null) {
            for (int j = 0; j < objectArray.length; ++j) {
                if (this.bJz != null && !this.bJz.F(objectArray[j])) continue;
                this.bJS.add(objectArray[j]);
            }
        }
        this.dV = this.getSelectedOffsetByValue(object);
        if (this.dV == -1 && n2 != -1) {
            this.f(new hf_0(this, null, object, false));
            this.f(new hf_0(this, null, null, true));
        }
        this.bJR = null;
        this.setNeedsToPreProcess();
        this.setNeedsToResetMeshes();
    }

    public void setContent(Iterable iterable) {
        if (this.czn) {
            return;
        }
        int n2 = this.dV;
        Object object = this.getSelectedValue();
        this.bJS = new ArrayList();
        if (iterable != null) {
            Iterator iterator = iterable.iterator();
            while (iterator != null && iterator.hasNext()) {
                Object t = iterator.next();
                if (this.bJz != null && !this.bJz.F(t)) continue;
                this.bJS.add(t);
            }
        }
        this.dV = this.getSelectedOffsetByValue(object);
        if (this.dV == -1 && n2 != -1) {
            this.f(new hf_0(this, null, object, false));
            this.f(new hf_0(this, null, null, true));
        }
        this.bJR = null;
        this.setNeedsToPreProcess();
        this.setNeedsToResetMeshes();
    }

    private void aep() {
        float f = (this.ba ? this.bJA : this.bJB) - (float)this.bJG;
        int n2 = Math.max(1, (int)Math.floor(this.ba ? (double)this.bJB : (double)this.bJA));
        if (this.ec != null && (float)(this.ec.size() / n2) < f) {
            this.bJD = 0.0f;
        } else if (this.ec != null && (float)((int)Math.ceil((float)this.ec.size() / (float)n2)) - this.bJD < f || this.bJD < 0.0f) {
            this.bJD = (float)Math.ceil((float)this.ec.size() / (float)n2) - f;
        }
    }

    public int getTableIndex(qa_1 qa_12) {
        return this.dS.indexOf(qa_12) + (int)Math.floor(this.bJD);
    }

    public int getItemIndex(Object object) {
        int n2 = 0;
        if (this.ec != null) {
            for (Object e : this.ec) {
                if (e == object) {
                    return n2;
                }
                ++n2;
            }
        }
        return -1;
    }

    public ArrayList getRenderables() {
        return this.dS;
    }

    public agj_1 getCellSize() {
        return this.bfP;
    }

    private void e(Object object, int n2) {
        this.ec.add(n2, object);
    }

    public void c(Object object) {
        if (this.ec == null) {
            this.ec = new ArrayList();
        }
        this.e(object, this.ec.size());
        this.dV = this.ec.size() - 1;
        this.bJT = true;
        this.setNeedsToPreProcess();
        this.cb();
    }

    public boolean a(int n2, Object object) {
        if (this.ec == null) {
            this.ec = new ArrayList();
        }
        if (n2 >= 0 || n2 <= this.ec.size()) {
            this.e(object, n2);
            this.dV = n2;
            this.bJT = true;
            this.setNeedsToPreProcess();
            this.cb();
            return true;
        }
        return false;
    }

    public void a(Object object, Object object2) {
        boolean bl2 = false;
        if (this.ec != null) {
            for (int j = 0; j < this.ec.size(); ++j) {
                if (object != this.ec.get(j)) continue;
                this.e(object2, j);
                this.dV = j;
                bl2 = true;
                break;
            }
            if (bl2) {
                this.bJT = true;
                this.setNeedsToPreProcess();
                this.cb();
            }
        }
    }

    public void c(ArrayList arrayList, ArrayList arrayList2) {
        Point point;
        int n2;
        int n3;
        qa_1 qa_12;
        int n4;
        int n5;
        Object object;
        int n6;
        ArrayList<qa_1> arrayList3 = new ArrayList<qa_1>(this.dS.size());
        ArrayList<Object> arrayList4 = new ArrayList<Object>(this.ec.size());
        arrayList3.addAll(this.dS);
        arrayList4.addAll(this.ec);
        int n7 = 400;
        for (n6 = 0; n6 < arrayList.size(); ++n6) {
            object = ((pf_0)arrayList.get(n6)).getFirst();
            n5 = (Integer)((pf_0)arrayList.get(n6)).acl();
            n4 = this.ec.indexOf(object);
            qa_12 = this.getRenderableByOffset(n4);
            if (qa_12 != null) {
                n3 = this.dS.indexOf(qa_12);
                if (n3 != (n2 = n3 - n4 + n5)) {
                    qa_12.q(ho_2.class);
                    qa_12.q(ard_0.class);
                    qa_12.a(new ho_2(null, new Rectangle(0, this.bfP.height, this.bfP.width, 0), qa_12, false, 0, 400, ys.aCq));
                    point = (Point)this.bJQ.get(n2);
                    qa_12.a(new ard_0(qa_12.getX(), qa_12.getY(), point.x, point.y, qa_12, 400, 0, ys.aCp));
                    qa_12.a(new ho_2(new Rectangle(0, this.bfP.height, this.bfP.width, 0), new Rectangle(0, 0, this.bfP.width, this.bfP.height), qa_12, true, 400, 400, ys.aCq));
                }
                if (n2 >= 0 && n2 < this.dS.size()) {
                    arrayList3.set(n2, qa_12);
                }
            }
            arrayList4.set(n5, object);
        }
        for (n6 = 0; n6 < arrayList2.size(); ++n6) {
            object = ((pf_0)arrayList2.get(n6)).getFirst();
            n5 = (Integer)((pf_0)arrayList2.get(n6)).acl();
            n4 = this.ec.indexOf(object);
            if (n4 == n5) continue;
            qa_12 = this.getRenderableByOffset(n4);
            if (qa_12 != null) {
                n3 = this.dS.indexOf(qa_12);
                n2 = n3 - n4 + n5;
                qa_12.q(ard_0.class);
                point = (Point)this.bJQ.get(n2);
                qa_12.a(new ard_0(qa_12.getX(), qa_12.getY(), point.x, point.y, qa_12, 400, 400, ys.aCq));
                if (n2 >= 0 && n2 < this.dS.size()) {
                    arrayList3.set(n2, qa_12);
                }
            }
            arrayList4.set(n5, object);
        }
        this.dS = arrayList3;
        this.ec = arrayList4;
    }

    public void f(Object object, int n2) {
        this.a(object, n2, false);
    }

    public void a(Object object, int n2, boolean bl2) {
        qa_1 qa_12;
        int n3 = this.ec.indexOf(object);
        if (n2 < 0 || n2 >= this.ec.size() || n3 == n2) {
            return;
        }
        if (bl2 && (qa_12 = this.getRenderableByOffset(n3)) != null) {
            int n4 = this.dS.indexOf(qa_12);
            Point point = qa_12.dxS;
            int n5 = Math.max(0, n4 - n3 + n2);
            if (n3 < n2) {
                for (int j = n4 + 1; j <= n5; ++j) {
                    qa_1 qa_13 = (qa_1)this.dS.get(j);
                    qa_13.a(new ard_0(qa_13.getX(), qa_13.getY(), point.x, point.y, qa_13, 700, 300, ys.aCq));
                    point = qa_13.dxS;
                }
            } else {
                for (int j = n4 - 1; j >= n5; --j) {
                    qa_1 qa_14 = (qa_1)this.dS.get(j);
                    qa_14.a(new ard_0(qa_14.getX(), qa_14.getY(), point.x, point.y, qa_14, 700, 300, ys.aCq));
                    point = qa_14.dxS;
                }
            }
            qa_12.a(new ho_2(null, new Rectangle(0, this.bfP.height, this.bfP.width, 0), qa_12, false, 0, 700, ys.aCq));
            qa_12.a(new ard_0(qa_12.getX(), qa_12.getY(), point.x, point.y, qa_12, 700, 0, ys.aCp));
            qa_12.a(new ho_2(new Rectangle(0, this.bfP.height, this.bfP.width, 0), new Rectangle(0, 0, this.bfP.width, this.bfP.height), qa_12, true, 700, 700, ys.aCq));
            this.dS.add(n5, this.dS.remove(n4));
        }
        this.ec.add(n2, this.ec.remove(n3));
    }

    public void k(Object object, Object object2) {
        boolean bl2;
        int n2 = this.ec.indexOf(object);
        int n3 = this.ec.indexOf(object2);
        if (n2 == -1 || n3 == -1) {
            return;
        }
        int n4 = (int)(Math.floor(this.bJD) * (double)(this.ba ? this.bJB : this.bJA));
        int n5 = (int)(Math.floor(this.bJD + (this.ba ? this.bJA : this.bJB)) * (double)(this.ba ? this.bJB : this.bJA));
        boolean bl3 = n2 >= n4 && n2 <= n5;
        boolean bl4 = bl2 = n3 >= n4 && n3 <= n5;
        if (bl3 && bl2) {
            qa_1 qa_12 = this.getRenderableByOffset(n2);
            qa_1 qa_13 = this.getRenderableByOffset(n3);
            qa_12.a(new ho_2(null, new Rectangle(qa_12.getX(), this.bfP.height + qa_12.getY(), this.bfP.width, 0), qa_12, false, 0, 250, ys.aCp));
            qa_12.a(new ard_0(qa_12.getX(), qa_12.getY(), qa_13.getX(), qa_13.getY(), qa_12, 250, 0, ys.aCp));
            qa_12.a(new ho_2(new Rectangle(qa_12.getX(), this.bfP.height + qa_12.getY(), this.bfP.width, 0), new Rectangle(qa_12.getX(), qa_12.getY(), this.bfP.width, this.bfP.height), qa_12, true, 250, 250, ys.aCp));
            qa_13.a(new ho_2(null, new Rectangle(qa_13.getX(), this.bfP.height + qa_13.getY(), this.bfP.width, 0), qa_13, false, 0, 250, ys.aCp));
            qa_13.a(new ard_0(qa_13.getX(), qa_13.getY(), qa_12.getX(), qa_12.getY(), qa_13, 250, 0, ys.aCp));
            qa_13.a(new ho_2(new Rectangle(qa_13.getX(), this.bfP.height + qa_13.getY(), this.bfP.width, 0), new Rectangle(qa_13.getX(), qa_13.getY(), this.bfP.width, this.bfP.height), qa_13, true, 250, 250, ys.aCp));
        } else if (bl3 || bl2) {
            if (!bl3) {
                qa_1 qa_14 = this.getRenderableByOffset(n3);
                aam_1.aMF().a(new aft_1(this, qa_14, object), 250L, 0, 1);
                qa_14.a(new ho_2(null, new Rectangle(qa_14.getX(), this.bfP.height + qa_14.getY(), this.bfP.width, 0), qa_14, false, 0, 250, ys.aCq));
                qa_14.a(new ho_2(new Rectangle(qa_14.getX(), this.bfP.height + qa_14.getY(), this.bfP.width, 0), new Rectangle(qa_14.getX(), qa_14.getY(), this.bfP.width, this.bfP.height), qa_14, true, 250, 250, ys.aCq));
            } else {
                qa_1 qa_15 = this.getRenderableByOffset(n2);
                aam_1.aMF().a(new afs_2(this, qa_15, object2), 250L, 0, 1);
                qa_15.a(new ho_2(null, new Rectangle(qa_15.getX(), this.bfP.height + qa_15.getY(), this.bfP.width, 0), qa_15, false, 0, 250, ys.aCq));
                qa_15.a(new ho_2(new Rectangle(qa_15.getX(), this.bfP.height + qa_15.getY(), this.bfP.width, 0), new Rectangle(qa_15.getX(), qa_15.getY(), this.bfP.width, this.bfP.height), qa_15, true, 250, 250, ys.aCq));
            }
        }
        this.ec.set(n2, object2);
        this.ec.set(n3, object);
    }

    public void b(Object object) {
    }

    public boolean b(Object object, Object object2) {
        boolean bl2 = false;
        if (this.ec != null) {
            int n2;
            for (n2 = 0; n2 < this.ec.size(); ++n2) {
                if (object != this.ec.get(n2)) continue;
                this.ec.set(n2, object2);
                bl2 = true;
                break;
            }
            if (bl2) {
                this.dV = n2;
                this.bJT = true;
                this.setNeedsToPreProcess();
                this.cb();
            }
            return bl2;
        }
        return false;
    }

    public Object getValue(int n2) {
        if (this.ec != null) {
            return this.ec.get(n2);
        }
        return null;
    }

    public int size() {
        if (this.ec != null) {
            return this.ec.size();
        }
        return 0;
    }

    protected void a(ke ke2, boolean bl2) {
        if (!(ke2.oH() || ke2.aV() != qe_1.bFi && ke2.aV() != qe_1.bFj)) {
            ke2.X(true);
            aek.atD().click();
        }
    }

    public void j() {
        super.j();
        this.dT = null;
        this.dS.clear();
        this.bJQ.clear();
        this.ec = null;
        this.bJS = null;
        this.bJz = null;
        this.ed = null;
        this.ee = null;
        this.dR = null;
        if (this.bJV != null) {
            this.bJV.j();
            this.bJV = null;
        }
        if (this.bfY != null) {
            this.bfY.j();
            this.bfY = null;
        }
    }

    public void b() {
        super.b();
        this.dyc = false;
        on_0 on_02 = new on_0();
        on_02.b();
        on_02.setWidget(this);
        this.a(on_02);
        li_1 li_12 = new li_1(this);
        li_12.b();
        this.a(li_12);
        this.ajA = new aIg();
        this.ajA.b();
        this.ajA.setCanBeCloned(false);
        this.ajA.setHorizontal(this.ba);
        this.a(this.ajA);
        if (!this.ba) {
            this.ajA.getSlider().setValue(1.0f);
            this.bBx = BT.aJU;
        } else {
            this.bBx = BT.aJW;
        }
        this.dV = -1;
        this.dS = new ArrayList();
        this.bJQ = new ArrayList();
        this.dW = true;
        this.dyu = true;
    }

    public void invalidate() {
        super.invalidate();
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.bJS != null) {
            this.yv();
            this.bJU = true;
            this.setNeedsToPostProcess();
        }
        if (this.bJT) {
            super.Am();
            this.invalidate();
            this.bJT = false;
        }
        return bl2;
    }

    public void Am() {
    }

    public boolean cb(int n2) {
        boolean bl2 = super.cb(n2);
        if (this.bJU) {
            this.ca();
            this.bJU = false;
        }
        return bl2;
    }

    public void a(air_1 air_12) {
        rf_0 rf_02 = (rf_0)air_12;
        super.a(air_12);
        rf_02.setAlign(this.bBx);
        rf_02.setAutoIdealSize(this.bJH, this.bJI, this.bJJ, this.bJK, this.bJL);
        rf_02.setCellSize((agj_1)this.bfP.clone());
        rf_02.setHorizontal(this.ba);
        rf_02.setMinDisplayedCells(this.bJE);
        rf_02.setMouseOverColor(this.getMouseOverColor());
        rf_02.setSelectedColor(this.getSelectedColor());
        rf_02.setOffset(this.bJD);
        rf_02.setOppositeScrollBarPosition(this.bJF);
        rf_02.setShowOneMore(this.bJG > 0);
        rf_02.setScrollBarBehaviour(this.bJP);
        rf_02.setSelectionTogglable(this.dY);
        rf_02.setSelectionable(this.dX);
        rf_02.setEnableDND(this.dW);
        rf_02.setListFilter(this.bJz);
        rf_02.setListLayoutMode(this.bJM);
        rf_02.setIsoColumnCount(this.bJN);
        rf_02.setIsoPositiveFactor(true);
        rf_02.dyg = true;
        rf_02.setNeedsToPreProcess();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == cJ) {
            this.setAlign(BT.dv(string));
        } else if (n2 == bJX) {
            this.setAdaptCellSizeToContentSize(Gr.getBoolean(string));
        } else if (n2 == bJY) {
            this.setAutoIdealSize(Gr.getBoolean(string));
        } else if (n2 == bfZ) {
            this.setCellSize(if_12.eL(string));
        } else if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(string));
        } else if (n2 == bJZ) {
            this.setIdealSizeMaxColumns(Gr.R(string));
        } else if (n2 == bKa) {
            this.setIdealSizeMaxRows(Gr.R(string));
        } else if (n2 == bKb) {
            this.setIdealSizeMinColumns(Gr.R(string));
        } else if (n2 == bKc) {
            this.setIdealSizeMinRows(Gr.R(string));
        } else if (n2 == bKe) {
            this.setListOffset(Gr.R(string));
        } else if (n2 == bKf) {
            this.setMinDisplayedCells(Gr.R(string));
        } else if (n2 == bKh) {
            this.setSelectedColor(if_12.eK(string));
        } else if (n2 == bKg) {
            this.setMouseOverColor(if_12.eK(string));
        } else if (n2 == bKi) {
            this.setOffset(Gr.R(string));
        } else if (n2 == bKj) {
            this.setOppositeScrollBarPosition(Gr.getBoolean(string));
        } else if (n2 == bKk) {
            this.setScrollBar(Gr.getBoolean(string));
        } else if (n2 == bKl) {
            this.setScrollBarBehaviour(aDM.kV(string));
        } else if (n2 == em) {
            this.setSelectionable(Gr.getBoolean(string));
        } else if (n2 == en) {
            this.setSelectionTogglable(Gr.getBoolean(string));
        } else if (n2 == bKm) {
            this.setShowOneMore(Gr.getBoolean(string));
        } else if (n2 == bKn) {
            this.setPrefSize(if_12.eL(string));
        } else if (n2 == add) {
            this.setEnableDND(Gr.getBoolean(string));
        } else if (n2 == bKq) {
            this.setIsoPositiveFactor(Gr.getBoolean(string));
        } else if (n2 == bKp) {
            this.setIsoColumnCount(Gr.R(string));
        } else if (n2 == bKo) {
            this.setListLayoutMode((eF)((Object)if_12.c(eF.class, string)));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == bKe) {
            this.setListOffset(Gr.R(object));
            return true;
        } else if (n2 == bKi) {
            this.setOffset(Gr.R(object));
            return true;
        } else if (n2 == bKk) {
            this.setScrollBar(Gr.getBoolean(object));
            return true;
        } else if (n2 == ei) {
            if (object == null || object.getClass().isArray()) {
                this.setContent((Object[])object);
                return true;
            } else {
                if (!(object instanceof Iterable)) return false;
                this.setContent((Iterable)object);
            }
            return true;
        } else if (n2 == ek) {
            this.setSelected((sm_0)object);
            return true;
        } else if (n2 == el) {
            this.setSelectedValue(object);
            return true;
        } else if (n2 == bJZ) {
            this.setIdealSizeMaxColumns(Gr.R(object));
            return true;
        } else if (n2 == bKa) {
            this.setIdealSizeMaxRows(Gr.R(object));
            return true;
        } else if (n2 == bKb) {
            this.setIdealSizeMinColumns(Gr.R(object));
            return true;
        } else if (n2 == bKc) {
            this.setIdealSizeMinRows(Gr.R(object));
            return true;
        } else if (n2 == bKd) {
            this.setListFilter((rH)object);
            return true;
        } else if (n2 == add) {
            this.setEnableDND(Gr.getBoolean(object));
            return true;
        } else {
            if (n2 != bKq) return super.setPropertyAttribute(n2, object);
            this.setIsoPositiveFactor(Gr.getBoolean(object));
        }
        return true;
    }

    static /* synthetic */ aIg b(rf_0 rf_02) {
        return rf_02.ajA;
    }

    static /* synthetic */ float a(rf_0 rf_02, float f) {
        return rf_02.aq(f);
    }

    static /* synthetic */ boolean c(rf_0 rf_02) {
        return rf_02.bJH;
    }

    static /* synthetic */ int d(rf_0 rf_02) {
        return rf_02.bJI;
    }

    static /* synthetic */ int e(rf_0 rf_02) {
        return rf_02.bJJ;
    }

    static /* synthetic */ int f(rf_0 rf_02) {
        return rf_02.bJK;
    }

    static /* synthetic */ int g(rf_0 rf_02) {
        return rf_02.bJL;
    }

    static /* synthetic */ boolean h(rf_0 rf_02) {
        return rf_02.ba;
    }

    static /* synthetic */ int i(rf_0 rf_02) {
        return rf_02.bJE;
    }

    static /* synthetic */ agj_1 j(rf_0 rf_02) {
        return rf_02.bfP;
    }

    static /* synthetic */ aDM k(rf_0 rf_02) {
        return rf_02.bJP;
    }

    static /* synthetic */ boolean l(rf_0 rf_02) {
        return rf_02.ajB;
    }

    static /* synthetic */ sn_0 m(rf_0 rf_02) {
        return rf_02.dR;
    }

    static /* synthetic */ boolean a(rf_0 rf_02, boolean bl2) {
        rf_02.ajC = bl2;
        return rf_02.ajC;
    }

    static /* synthetic */ boolean n(rf_0 rf_02) {
        return rf_02.bJC;
    }

    static /* synthetic */ float a(rf_0 rf_02, int n2, int n3) {
        return rf_02.aT(n2, n3);
    }

    static /* synthetic */ float b(rf_0 rf_02, int n2, int n3) {
        return rf_02.aU(n2, n3);
    }

    static /* synthetic */ boolean b(rf_0 rf_02, boolean bl2) {
        rf_02.ajB = bl2;
        return rf_02.ajB;
    }

    static /* synthetic */ int b(rf_0 rf_02, float f) {
        return rf_02.getPotentialColumnCount(f);
    }

    static /* synthetic */ int o(rf_0 rf_02) {
        return rf_02.bJG;
    }

    static /* synthetic */ int c(rf_0 rf_02, float f) {
        return rf_02.getPotentialRowCount(f);
    }

    static /* synthetic */ boolean p(rf_0 rf_02) {
        return rf_02.bJF;
    }

    static /* synthetic */ BT q(rf_0 rf_02) {
        return rf_02.bBx;
    }

    static /* synthetic */ ArrayList r(rf_0 rf_02) {
        return rf_02.bJQ;
    }

    static /* synthetic */ boolean s(rf_0 rf_02) {
        return rf_02.dW;
    }

    static /* synthetic */ boolean t(rf_0 rf_02) {
        return rf_02.dX;
    }

    static /* synthetic */ boolean u(rf_0 rf_02) {
        return rf_02.dY;
    }

    static /* synthetic */ qa_1 v(rf_0 rf_02) {
        return rf_02.dT;
    }

    static /* synthetic */ void a(rf_0 rf_02, qa_1 qa_12) {
        rf_02.a(qa_12);
    }

    static /* synthetic */ qa_1 b(rf_0 rf_02, qa_1 qa_12) {
        rf_02.bJR = qa_12;
        return rf_02.bJR;
    }

    static /* synthetic */ apd_0 w(rf_0 rf_02) {
        return rf_02.bJV;
    }

    static /* synthetic */ qa_1 x(rf_0 rf_02) {
        return rf_02.bJR;
    }

    static /* synthetic */ float y(rf_0 rf_02) {
        return rf_02.bJB;
    }

    static /* synthetic */ float z(rf_0 rf_02) {
        return rf_02.bJA;
    }

    static /* synthetic */ float d(rf_0 rf_02, float f) {
        rf_02.bJA = f;
        return rf_02.bJA;
    }

    static /* synthetic */ float e(rf_0 rf_02, float f) {
        rf_02.bJB = f;
        return rf_02.bJB;
    }

    static /* synthetic */ void A(rf_0 rf_02) {
        rf_02.Ox();
    }

    static /* synthetic */ void B(rf_0 rf_02) {
        rf_02.aep();
    }

    static /* synthetic */ boolean a(rf_0 rf_02, int n2) {
        rf_02.bJU = (byte)(rf_02.bJU | n2);
        return rf_02.bJU;
    }

    static /* synthetic */ int C(rf_0 rf_02) {
        return rf_02.bJN;
    }

    static /* synthetic */ boolean D(rf_0 rf_02) {
        return rf_02.bJO;
    }
}

