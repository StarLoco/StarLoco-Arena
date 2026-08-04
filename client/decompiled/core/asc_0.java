/*
 * Decompiled with CFR 0.152.
 */
import java.awt.geom.Line2D;
import java.util.ArrayList;

/*
 * Renamed from asC
 */
public class asc_0
extends aht_1
implements Fc {
    public static final String TAG = "tournament";
    private ArrayList bfK;
    private ArrayList c = new ArrayList();
    private ArrayList dS = new ArrayList();
    private sn_0 dR = new sn_0();
    private int bfS = -1;
    private oV cSn;
    private int aUc = 5;
    private int aUb = 10;
    private int bfO;
    private int bfN;
    private agj_1 bfP;
    private String ed = null;
    private aji_1 ee = null;
    private boolean cSo = true;
    public static final int bfZ = "cellSize".hashCode();
    public static final int ei = "content".hashCode();
    public static final int aTM = "hgap".hashCode();
    public static final int aTN = "vgap".hashCode();

    public void a(na_1 na_12) {
        super.a(na_12);
        if (na_12 instanceof ie) {
            this.dR.a((ie)na_12);
        }
    }

    protected void pX() {
        super.pX();
    }

    public String getTag() {
        return TAG;
    }

    public void setCellSize(agj_1 agj_12) {
        this.bfP = agj_12;
        this.cSo = true;
        this.setNeedsToPreProcess();
    }

    public agj_1 getCellSize() {
        return this.bfP;
    }

    public int getVgap() {
        return this.aUc;
    }

    public void setVgap(int n2) {
        this.aUc = n2;
        this.cSo = true;
        this.setNeedsToPreProcess();
    }

    public int getHgap() {
        return this.aUb;
    }

    public void setHgap(int n2) {
        this.aUb = n2;
        this.cSo = true;
        this.setNeedsToPreProcess();
    }

    public void setContentProperty(String string, aji_1 aji_12) {
        this.ed = string;
        this.ee = aji_12;
    }

    public void setContent(nj_2 nj_22) {
        int n2 = 0;
        this.bfK = (ArrayList)nj_22.getItems().clone();
        this.bfO = nj_22.getRowCount();
        this.bfN = nj_22.getColumnCount();
        this.c = nj_22.d() != null ? (ArrayList)nj_22.d().clone() : this.c;
        n2 = this.bfK.size();
        int n3 = this.dS.size();
        if (n3 < n2) {
            for (int j = n3; j < n2; ++j) {
                qa_1 qa_12 = new qa_1();
                qa_12.b();
                qa_12.setNonBlocking(this.dyc);
                qa_12.setRendererManager(this.dR);
                qa_12.a(qe_1.bFx, new agk_0(this), false);
                qa_12.a(qe_1.bFy, new agm_0(this), false);
                this.dS.add(qa_12);
                this.a(qa_12);
            }
        } else if (n3 > n2) {
            for (int j = n2; j < n3; ++j) {
                qa_1 qa_13 = (qa_1)this.dS.remove(this.dS.size() - 1);
                qa_13.aab();
            }
            if (this.bfS >= this.dS.size()) {
                this.bfS = -1;
            }
        }
        this.ca();
    }

    public void ca() {
        for (int j = 0; j < this.bfK.size(); ++j) {
            qa_1 qa_12 = (qa_1)this.dS.get(j);
            agc_0 agc_02 = (agc_0)this.bfK.get(j);
            qa_12.setContentProperty(this.ed + "#" + j, this.ee);
            qa_12.setContent(agc_02.getValue());
        }
    }

    public void validate() {
        super.validate();
        this.aFC();
    }

    private void aFC() {
        ArrayList<Line2D.Float> arrayList = new ArrayList<Line2D.Float>();
        int n2 = this.bfP.width + this.aUb;
        int n3 = this.bfP.height + this.aUc;
        for (Line2D line2D : this.c) {
            float f = (float)line2D.getX1();
            float f2 = (float)line2D.getY1();
            float f3 = (float)line2D.getX2();
            float f4 = (float)line2D.getY2();
            float f5 = (float)n2 * f + (float)this.bfP.width;
            float f6 = (float)n2 * (f + 1.0f) - (float)(this.aUb / 2);
            float f7 = (float)this.bfO - f2 - 1.0f;
            float f8 = (float)n3 * f7 + (float)(this.bfP.height / 2);
            f7 = (float)this.bfO - f4 - 1.0f;
            float f9 = (float)n3 * f7 + (float)(this.bfP.height / 2);
            float f10 = (float)n2 * f3;
            arrayList.add(new Line2D.Float(f5, f8, f6, f8));
            arrayList.add(new Line2D.Float(f6, f8, f6, f9));
            arrayList.add(new Line2D.Float(f6, f9, f10, f9));
        }
        this.cSn.e(arrayList);
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.cSo) {
            this.Am();
        }
        this.cSo = false;
        return bl2;
    }

    private void aFD() {
        ArrayList<agc_0> arrayList = new ArrayList<agc_0>();
        ArrayList<Line2D.Float> arrayList2 = new ArrayList<Line2D.Float>();
        agc_0 agc_02 = new agc_0("Arone", 1.0f, 0.0f);
        arrayList.add(agc_02);
        agc_02 = new agc_0("?", 1.0f, 7.0f);
        arrayList.add(agc_02);
        agc_02 = new agc_0("Khalim", 1.0f, 1.0f);
        arrayList.add(agc_02);
        arrayList2.add(new Line2D.Float(1.0f, 0.0f, 2.0f, 0.5f));
        arrayList2.add(new Line2D.Float(1.0f, 1.0f, 2.0f, 0.5f));
        agc_02 = new agc_0("Tessaran", 1.0f, 2.0f);
        arrayList.add(agc_02);
        agc_02 = new agc_0("Noreen", 1.0f, 3.0f);
        arrayList.add(agc_02);
        arrayList2.add(new Line2D.Float(1.0f, 2.0f, 2.0f, 2.5f));
        arrayList2.add(new Line2D.Float(1.0f, 3.0f, 2.0f, 2.5f));
        agc_02 = new agc_0("Arone", 2.0f, 0.5f);
        arrayList.add(agc_02);
        arrayList2.add(new Line2D.Float(2.0f, 0.5f, 3.0f, 1.5f));
        agc_02 = new agc_0("Noreen", 2.0f, 2.5f);
        arrayList.add(agc_02);
        agc_02 = new agc_0("Khalim", 0.0f, 4.0f);
        arrayList.add(agc_02);
        agc_02 = new agc_0("Tessaran", 0.0f, 5.0f);
        arrayList.add(agc_02);
        agc_02 = new agc_0("Khalim", 2.0f, 4.5f);
        arrayList.add(agc_02);
        agc_02 = new agc_0("Noreen", 2.0f, 5.5f);
        arrayList.add(agc_02);
        agc_02 = new agc_0("Khalim", 3.0f, 5.0f);
        arrayList.add(agc_02);
        agc_02 = new agc_0("Arone", 3.0f, 1.5f);
        arrayList.add(agc_02);
        agc_02 = new agc_0("?", 4.0f, 3.25f);
        arrayList.add(agc_02);
        nj_2 nj_22 = new nj_2(arrayList, arrayList2, 8, 5);
        this.setContent(nj_22);
    }

    public void b() {
        super.b();
        this.cSn = new oV();
        ape_0 ape_02 = new ape_0(this);
        ape_02.b();
        this.a(ape_02);
    }

    public void j() {
        super.j();
        this.bfK.clear();
        this.bfK = null;
        this.dS.clear();
        this.dS = null;
        this.c.clear();
        this.c = null;
        this.bfP = null;
        this.dR = null;
        this.cSn.j();
        this.cSn = null;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == bfZ) {
            this.setCellSize(if_12.eL(string));
        } else if (n2 == aTM) {
            this.setHgap(Gr.R(string));
        } else if (n2 == aTN) {
            this.setVgap(Gr.R(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != ei) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setContent((nj_2)object);
        return true;
    }

    static /* synthetic */ agj_1 a(asc_0 asc_02) {
        return asc_02.bfP;
    }

    static /* synthetic */ int b(asc_0 asc_02) {
        return asc_02.bfN;
    }

    static /* synthetic */ int c(asc_0 asc_02) {
        return asc_02.aUb;
    }

    static /* synthetic */ int d(asc_0 asc_02) {
        return asc_02.bfO;
    }

    static /* synthetic */ int e(asc_0 asc_02) {
        return asc_02.aUc;
    }

    static /* synthetic */ ArrayList f(asc_0 asc_02) {
        return asc_02.bfK;
    }

    static /* synthetic */ ArrayList g(asc_0 asc_02) {
        return asc_02.dS;
    }

    static /* synthetic */ int a(asc_0 asc_02, int n2) {
        asc_02.bfS = n2;
        return asc_02.bfS;
    }
}

