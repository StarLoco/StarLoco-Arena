/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from aaZ
 */
public class aaz_2
extends aht_1
implements Fc,
ajb_0,
px_2 {
    public static final String TAG = "table";
    public static final String ajq = "button";
    public static final String cgX = "directSortButton";
    public static final String cgY = "indirectSortButton";
    public static final String ajv = "oddCell";
    public static final String ajw = "evenCell";
    public static final String ajx = "scrollBar";
    public static final String cgZ = "mouseOver";
    public static final String cha = "mouseOverCell";
    public static final String chb = "selected";
    private ArrayList bBG;
    private ArrayList chc;
    private ArrayList ec;
    private ArrayList dS;
    private aIg ajA;
    private boolean ajB = false;
    private boolean ajC;
    private int ajD;
    private int dV = -1;
    private int chd = -1;
    private qa_1 dT;
    private qa_1 bJR;
    private String ed = null;
    private aji_1 ee = null;
    private boolean ajE;
    private boolean che;
    private int[] bLj = null;
    private ArrayList ajH;
    private apd_0 bJV;
    private apd_0 chf;
    private apd_0 bfY;
    private aDM bJP;
    private int ajP;
    private int ajQ;
    private int ajR;
    private boolean dW;
    private cn_2 chg;
    private vP ajS;
    private vP ajT;
    private boolean dX;
    private boolean dY;
    public static final int ei = "content".hashCode();
    public static final int ajU = "cellHeight".hashCode();
    public static final int add = "enableDND".hashCode();
    public static final int ajV = "minRows".hashCode();
    public static final int ajW = "maxRows".hashCode();
    public static final int bKl = "scrollBarBehaviour".hashCode();

    public void a(na_1 na_12) {
        super.a(na_12);
        if (na_12 instanceof ee_0) {
            this.a((ee_0)na_12);
        }
    }

    private void a(ee_0 ee_02) {
        this.chc.add(ee_02);
        aqq_0 aqq_02 = new aqq_0();
        aqq_02.b();
        this.a(aqq_02);
        aqq_02.setElementMap(this.blb);
        aqq_02.setChildrenAdded(true);
        aqq_02.setCanBeCloned(false);
        String string = this.getStyle();
        StringBuilder stringBuilder = new StringBuilder(TAG);
        if (string != null) {
            stringBuilder.append(string);
        }
        stringBuilder.append("$").append(ajq);
        String string2 = stringBuilder.toString();
        aqq_02.setStyle(string2, true);
        aqq_02.setText(ee_02.getName());
        aqq_02.a(qe_1.bFB, new axN(this, aqq_02, string2, ee_02, string), false);
        this.bBG.add(aqq_02);
        this.setTableDirty();
    }

    protected void pX() {
        for (int j = this.ajH.size() - 1; j >= 0; --j) {
            this.arC.i(((apd_0)this.ajH.get(j)).apq());
        }
        if (this.bfY != null && this.dT != null) {
            this.arC.i(this.bfY.apq());
        }
        if (this.bJV != null && this.bJR != null) {
            this.arC.i(this.bJV.apq());
        }
        if (this.chf != null && this.bJR != null) {
            this.arC.i(this.chf.apq());
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
        if (string == null || string.equals(cgZ)) {
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
        } else if (string.equals(cha)) {
            if (vP2 != null) {
                if (this.chf == null) {
                    this.chf = new apd_0();
                    this.chf.b();
                    this.setNeedsToResetMeshes();
                }
                this.chf.setColor(vP2);
            } else {
                if (this.chf != null) {
                    this.chf.j();
                }
                this.chf = null;
                this.setNeedsToResetMeshes();
            }
        } else if (string.equals(chb)) {
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
        } else if (string.equals(ajw)) {
            this.ajS = vP2;
        } else if (string.equals(ajv)) {
            this.ajT = vP2;
        }
    }

    public void setContentProperty(String string, aji_1 aji_12) {
        this.ed = string;
        this.ee = aji_12;
    }

    public aDM getScrollBarBehaviour() {
        return this.bJP;
    }

    public void setScrollBarBehaviour(aDM aDM2) {
        this.bJP = aDM2;
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

    public void setContent(Iterable iterable) {
        assert (this.ec != null);
        Object var2_2 = null;
        if (this.dV != -1) {
            var2_2 = this.ec.get(this.bLj[this.dV]);
        }
        this.ec.clear();
        if (iterable != null) {
            Iterator iterator = iterable.iterator();
            while (iterator.hasNext()) {
                this.ec.add(iterator.next());
            }
        }
        this.bLj = null;
        this.setOffset(0);
        int n2 = this.getSelectedOffsetByValue(var2_2);
        if (n2 != -1) {
            this.setSelectedOffset(n2, false);
        } else {
            this.dV = ej_0.e(this.dV, -1, this.ec.size() - 1);
            this.dT = this.getRenderableByOffset(this.dV);
            if (this.bLj == null) {
                this.apF();
            }
            Object var4_5 = null;
            if (this.dV != -1) {
                var4_5 = this.ec.get(this.bLj[this.dV]);
            }
            if (var2_2 != var4_5) {
                gm_0 gm_02 = new gm_0(this);
                gm_02.S(var2_2);
                gm_02.setValue(var4_5);
                this.f(gm_02);
            }
            this.cb();
        }
        this.bJR = null;
        this.setTableDirty();
        this.setValuesDirty();
    }

    public void setContent(Object[] objectArray) {
        assert (this.ec != null);
        Object var2_2 = null;
        if (this.dV != -1) {
            var2_2 = this.ec.get(this.bLj[this.dV]);
        }
        this.ec.clear();
        if (objectArray != null) {
            for (Object object : objectArray) {
                this.ec.add((aho_0)object);
            }
        }
        this.bLj = null;
        this.setOffset(0);
        int n2 = this.getSelectedOffsetByValue(var2_2);
        if (n2 != -1) {
            this.setSelectedOffset(n2, false);
        } else {
            this.dV = ej_0.e(this.dV, -1, this.ec.size() - 1);
            this.dT = this.getRenderableByOffset(this.dV);
            if (this.bLj == null) {
                this.apF();
            }
            Object var4_6 = null;
            if (this.dV != -1) {
                var4_6 = this.ec.get(this.bLj[this.dV]);
            }
            if (var2_2 != var4_6) {
                gm_0 gm_02 = new gm_0(this);
                gm_02.S(var2_2);
                gm_02.setValue(var4_6);
                this.f(gm_02);
            }
            this.cb();
        }
        this.bJR = null;
        this.setTableDirty();
        this.setValuesDirty();
    }

    private qa_1 getRenderableByPosition(int n2, int n3) {
        int n4 = n2 * this.chc.size() + n3;
        if (n4 < 0 || n4 >= this.dS.size()) {
            return null;
        }
        return (qa_1)this.dS.get(n4);
    }

    private void setTableDirty() {
        this.che = true;
        this.setNeedsToPreProcess();
    }

    private void setValuesDirty() {
        this.ajE = true;
        this.setNeedsToPostProcess();
    }

    public Object getValue(int n2) {
        if (n2 < 0 || n2 >= this.ec.size()) {
            return null;
        }
        return this.ec.get(n2);
    }

    public qa_1 getSelected() {
        return null;
    }

    public int getTableIndex(qa_1 qa_12) {
        return this.dS.indexOf(qa_12);
    }

    public int getItemIndex(Object object) {
        return this.ec.indexOf(object);
    }

    public adg_2 getWidget(String string, int n2) {
        if (n2 >= 0 && n2 < this.dS.size()) {
            return (adg_2)this.dS.get(n2);
        }
        return null;
    }

    public ArrayList getRenderables() {
        return this.dS;
    }

    public void setTableModel(cn_2 cn_22) {
        this.chg = cn_22;
    }

    public cn_2 getTableModel() {
        return this.chg;
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

    public int getSelectedOffsetByValue(Object object) {
        if (this.ec == null) {
            return -1;
        }
        int n2 = this.ec.indexOf(object);
        if (n2 == -1) {
            return -1;
        }
        if (this.bLj == null) {
            this.apF();
        }
        for (int j = 0; j < this.bLj.length; ++j) {
            if (this.bLj[j] != n2) continue;
            return j;
        }
        return -1;
    }

    public int getOffsetByRenderable(qa_1 qa_12) {
        if (qa_12 == null || this.ec == null) {
            return -1;
        }
        int n2 = this.ajD + this.dS.indexOf(qa_12) / this.chc.size();
        if (n2 >= this.ec.size()) {
            n2 = -1;
        }
        return n2;
    }

    public qa_1 getRenderableByOffset(int n2) {
        if (n2 == -1 || this.ec == null) {
            return null;
        }
        int n3 = (n2 - this.ajD) * this.chc.size();
        if (n3 < 0 || n3 >= this.dS.size()) {
            return null;
        }
        return (qa_1)this.dS.get(n3);
    }

    public void setSelectedOffset(int n2, boolean bl2) {
        if (n2 == this.dV && !bl2) {
            return;
        }
        int n3 = this.dV;
        this.dV = n2;
        this.dT = this.getRenderableByOffset(n2);
        if (this.bLj == null) {
            this.apF();
        }
        Object var4_4 = null;
        Object var5_5 = null;
        if (n3 != -1) {
            var4_4 = this.ec.get(this.bLj[n3]);
        }
        if (this.dV != -1) {
            var5_5 = this.ec.get(this.bLj[this.dV]);
        }
        if (var4_4 != var5_5) {
            gm_0 gm_02 = new gm_0(this);
            gm_02.S(var4_4);
            gm_02.setValue(var5_5);
            this.f(gm_02);
        }
        this.cb();
    }

    private void cb() {
        if (this.dT != null && this.bfY != null) {
            this.bfY.f(0, this.dT.getY(), this.cLZ.getContentWidth() - this.ajA.getWidth(), this.dT.getHeight(), this.cLZ.getTopInset(), this.cLZ.getBottomInset(), this.cLZ.getLeftInset(), this.cLZ.getRightInset());
        }
        this.setNeedsToResetMeshes();
    }

    private void a(qa_1 qa_12) {
        if (qa_12 == this.dT) {
            return;
        }
        if (qa_12 != null) {
            this.setSelectedOffset(this.getOffsetByRenderable(qa_12), false);
        } else {
            this.dV = -1;
        }
    }

    private float dA(int n2) {
        int n3;
        if (n2 < 0) {
            n2 = 0;
        }
        if ((n3 = this.ec.size() - this.dS.size() / this.chc.size()) == 0) {
            return 1.0f;
        }
        if (n3 > 0 && n2 > n3 + 1) {
            n2 = n3 + 1;
        }
        return 1.0f - (float)n2 / (float)n3;
    }

    private int R(float f) {
        float f2 = this.ec.size() - this.dS.size() / this.chc.size();
        float f3 = f2 - (float)Math.round(f2 * f);
        if (f2 < 0.0f || f3 < 0.0f) {
            f3 = 0.0f;
        } else if (f3 > f2 + 1.0f) {
            f3 = f2 + 1.0f;
        }
        return Math.round(f3);
    }

    public void ca() {
        int n2;
        int n3;
        if (this.ajC || this.dS == null) {
            return;
        }
        this.apF();
        this.dT = null;
        for (n3 = 0; n3 < this.dS.size(); n3 += this.chc.size()) {
            n2 = n3 / this.chc.size() + this.ajD;
            qa_1 qa_12 = (qa_1)this.dS.get(n3);
            if (n3 < 0 || n3 >= this.dS.size() || this.ec == null || n2 < 0 || n2 >= this.ec.size() || n2 != this.dV) continue;
            this.dT = qa_12;
            break;
        }
        n3 = this.dS.size() / this.chc.size();
        for (n2 = 0; n2 < n3; ++n2) {
            int n4 = n2 + this.ajD;
            int n5 = n4 >= this.bLj.length ? n4 : this.bLj[n4];
            ((apd_0)this.ajH.get(n2)).setColor(n4 % 2 == 0 ? this.ajS : this.ajT);
            for (int j = this.chc.size() - 1; j >= 0; --j) {
                ee_0 ee_02 = (ee_0)this.chc.get(j);
                qa_1 qa_13 = this.getRenderableByPosition(n2, j);
                String string = ee_02.getField();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.ed);
                stringBuilder.append("#").append(n5);
                if (string != null) {
                    stringBuilder.append("/").append(ee_02.getField());
                }
                qa_13.setContentProperty(stringBuilder.toString(), this.ee);
                if (this.ec != null && n5 >= 0 && n5 < this.ec.size()) {
                    aho_0 aho_02 = (aho_0)this.ec.get(n5);
                    if (aho_02 != null) {
                        if (string != null) {
                            qa_13.setContent(aho_02.getFieldValue(ee_02.getField()));
                            continue;
                        }
                        qa_13.setContent(aho_02);
                        continue;
                    }
                    qa_13.setContent(null);
                    continue;
                }
                qa_13.setContent(null);
            }
        }
        this.cb();
    }

    private void apF() {
        if (this.bLj == null && this.chg != null) {
            this.bLj = this.chg.a(this.ec);
        }
        if (this.bLj == null) {
            this.bLj = cn_2.R(this.ec.size());
        }
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.che) {
            super.Am();
            this.invalidate();
            this.che = false;
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
        aaz_2 aaz_22 = (aaz_2)air_12;
        aaz_22.setCellHeight(this.ajP);
        aaz_22.setMinRows(this.ajQ);
        aaz_22.setMaxRows(this.ajR);
        aaz_22.setEnableDND(this.dW);
        aaz_22.setTableModel(this.chg);
        aaz_22.setScrollBarBehaviour(this.bJP);
    }

    public void yx() {
        this.ajA.yx();
        super.yx();
        this.a(qe_1.bFH, new axk_0(this), false);
        this.a(qe_1.bFD, new axi_0(this), false);
    }

    public void j() {
        super.j();
        this.ajS = null;
        this.ajT = null;
        if (this.bJV != null) {
            this.bJV.j();
            this.bJV = null;
        }
        if (this.chf != null) {
            this.chf.j();
            this.chf = null;
        }
        if (this.bfY != null) {
            this.bfY.j();
            this.bfY = null;
        }
        if (this.ajH != null) {
            for (int j = this.ajH.size() - 1; j >= 0; --j) {
                ((apd_0)this.ajH.get(j)).j();
            }
            this.ajH = null;
        }
        this.dT = null;
        this.bJR = null;
        this.bBG = null;
        this.ajA = null;
        this.chc = null;
        this.ec = null;
        this.chg = null;
        this.dS = null;
    }

    public void b() {
        super.b();
        ej_1 ej_12 = new ej_1(this);
        ej_12.b();
        this.a(ej_12);
        this.ajA = new aIg();
        this.ajA.b();
        this.ajA.setHorizontal(false);
        this.ajA.setValue(1.0f);
        this.ajA.setCanBeCloned(false);
        this.a(this.ajA);
        this.ajQ = -1;
        this.ajR = -1;
        this.ajP = 30;
        this.dW = true;
        this.ajD = 0;
        this.ajC = false;
        this.che = false;
        this.dyc = false;
        this.dX = true;
        this.dY = false;
        this.dV = -1;
        this.chd = -1;
        this.dS = new ArrayList();
        this.bBG = new ArrayList();
        this.chc = new ArrayList();
        this.ec = new ArrayList();
        this.ajH = new ArrayList();
        this.bJP = aDM.dyU;
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
        } else if (n2 == bKl) {
            this.setScrollBarBehaviour(aDM.kV(string));
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
        if (n2 != ei) return super.setPropertyAttribute(n2, object);
        if (object == null || object.getClass().isArray()) {
            this.setContent((Object[])object);
            return true;
        } else {
            if (!(object instanceof Iterable)) return false;
            this.setContent((Iterable)object);
        }
        return true;
    }

    public void b(Object object) {
    }

    public void c(Object object) {
    }

    public boolean a(int n2, Object object) {
        return false;
    }

    public void a(Object object, Object object2) {
    }

    public boolean b(Object object, Object object2) {
        return false;
    }

    public int size() {
        return this.ec.size();
    }

    static /* synthetic */ ArrayList a(aaz_2 aaz_22) {
        return aaz_22.ec;
    }

    static /* synthetic */ int b(aaz_2 aaz_22) {
        return aaz_22.ajR;
    }

    static /* synthetic */ int c(aaz_2 aaz_22) {
        return aaz_22.ajQ;
    }

    static /* synthetic */ ArrayList d(aaz_2 aaz_22) {
        return aaz_22.chc;
    }

    static /* synthetic */ ArrayList e(aaz_2 aaz_22) {
        return aaz_22.bBG;
    }

    static /* synthetic */ Logger XF() {
        return a;
    }

    static /* synthetic */ aIg f(aaz_2 aaz_22) {
        return aaz_22.ajA;
    }

    static /* synthetic */ aDM g(aaz_2 aaz_22) {
        return aaz_22.bJP;
    }

    static /* synthetic */ int h(aaz_2 aaz_22) {
        return aaz_22.ajP;
    }

    static /* synthetic */ boolean a(aaz_2 aaz_22, boolean bl2) {
        aaz_22.ajC = bl2;
        return aaz_22.ajC;
    }

    static /* synthetic */ ArrayList i(aaz_2 aaz_22) {
        return aaz_22.dS;
    }

    static /* synthetic */ ArrayList j(aaz_2 aaz_22) {
        return aaz_22.ajH;
    }

    static /* synthetic */ boolean k(aaz_2 aaz_22) {
        return aaz_22.dW;
    }

    static /* synthetic */ boolean l(aaz_2 aaz_22) {
        return aaz_22.dX;
    }

    static /* synthetic */ boolean m(aaz_2 aaz_22) {
        return aaz_22.dY;
    }

    static /* synthetic */ qa_1 n(aaz_2 aaz_22) {
        return aaz_22.dT;
    }

    static /* synthetic */ void a(aaz_2 aaz_22, qa_1 qa_12) {
        aaz_22.a(qa_12);
    }

    static /* synthetic */ qa_1 b(aaz_2 aaz_22, qa_1 qa_12) {
        aaz_22.bJR = qa_12;
        return aaz_22.bJR;
    }

    static /* synthetic */ apd_0 o(aaz_2 aaz_22) {
        return aaz_22.bJV;
    }

    static /* synthetic */ qa_1 p(aaz_2 aaz_22) {
        return aaz_22.bJR;
    }

    static /* synthetic */ apd_0 q(aaz_2 aaz_22) {
        return aaz_22.chf;
    }

    static /* synthetic */ Logger yy() {
        return a;
    }

    static /* synthetic */ qa_1 a(aaz_2 aaz_22, int n2, int n3) {
        return aaz_22.getRenderableByPosition(n2, n3);
    }

    static /* synthetic */ Logger apG() {
        return a;
    }

    static /* synthetic */ boolean b(aaz_2 aaz_22, boolean bl2) {
        aaz_22.ajB = bl2;
        return aaz_22.ajB;
    }

    static /* synthetic */ boolean r(aaz_2 aaz_22) {
        return aaz_22.ajB;
    }

    static /* synthetic */ int s(aaz_2 aaz_22) {
        return aaz_22.ajD;
    }

    static /* synthetic */ void t(aaz_2 aaz_22) {
        aaz_22.setValuesDirty();
    }

    static /* synthetic */ cn_2 u(aaz_2 aaz_22) {
        return aaz_22.chg;
    }

    static /* synthetic */ int[] a(aaz_2 aaz_22, int[] nArray) {
        aaz_22.bLj = nArray;
        return nArray;
    }

    static /* synthetic */ int a(aaz_2 aaz_22, float f) {
        return aaz_22.R(f);
    }

    static /* synthetic */ void a(aaz_2 aaz_22, int n2) {
        aaz_22.setListOffset(n2);
    }
}

