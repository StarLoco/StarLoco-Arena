/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;
import java.util.ArrayList;

/*
 * Renamed from ahR
 */
public class ahr_2
extends adg_2 {
    public static final String TAG = "sphereBoard";
    public static final int auN = 18;
    private agg cwN;
    Ei coi;
    private static final ob_1[] cwO = new ob_1[18];
    private ov_1 aDb;
    private ov_1 cwP;
    private ov_1 cwQ;
    private ov_1 jk;
    private ov_1 cwR;
    private boolean cwS;
    private Point cwT;
    private boolean cwU = false;
    int cwV;
    int cwW;
    int cwX;
    int cwY;
    private int cwZ;
    private int cxa;
    private ayr_0 cxb = null;
    int aQg;
    int aQh;
    private float aaw = 1.0f;
    private float cxc = 1.0f;
    private boolean cxd = false;
    private aeg_1 cxe = null;
    public static final int cxf = "clickEnabled".hashCode();

    protected void pX() {
        super.pX();
        this.arC.i(this.cwN.getEntity());
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return true;
    }

    public void setSphereBoard(Ei ei) {
        this.coi = ei;
        this.cxd = true;
        this.setNeedsToPostProcess();
    }

    public void setZoom(float f) {
        this.setZoom(f, true);
    }

    public void setZoom(float f, boolean bl2) {
        if (bl2) {
            this.q(bb_1.class);
            this.a(new bb_1(this, this.aaw, f, this, 0, 500, ys.aCq));
        } else {
            this.cwN.setZoom(f);
        }
        this.aaw = f;
    }

    public void setTokenPixelPosition(int n2, int n3) {
        this.aQg = n2;
        this.aQh = n3;
        this.cwN.bo(n2, n3);
    }

    private void setDeltaPosition(int n2, int n3) {
        int n4 = this.getVisualWidth() - this.cLZ.getContentWidth();
        int n5 = this.getVisualHeight() - this.cLZ.getContentHeight();
        int n6 = n4 < 0 ? -n4 / 2 : -n4;
        int n7 = n4 < 0 ? -n4 / 2 : 0;
        int n8 = n5 < 0 ? -n5 / 2 : -n5;
        int n9 = n5 < 0 ? -n5 / 2 : 0;
        this.setDeltaPositionNoCheck(ej_0.e(n2, n6, n7), ej_0.e(n3, n8, n9));
    }

    public void setDeltaPositionNoCheck(int n2, int n3) {
        this.cwV = n2;
        this.cwW = n3;
        this.cwN.setScreenPosition(this.cwV, this.cwW);
    }

    private int getVisualWidth() {
        return (int)((float)(this.coi.getCellWidth() * this.coi.getWidth()) * this.aaw);
    }

    private int getVisualHeight() {
        return (int)((float)(this.coi.getCellHeight() * this.coi.getHeight()) * this.aaw);
    }

    private ayr_0 getSphereAt(int n2, int n3) {
        ArrayList arrayList = this.coi.Xn();
        int n4 = arrayList.size();
        for (int j = 0; j < n4; ++j) {
            ayr_0 ayr_02 = (ayr_0)arrayList.get(j);
            if (ayr_02.aLe() != n2 || ayr_02.aLf() != n3) continue;
            return ayr_02;
        }
        return null;
    }

    public float getZoom() {
        return this.aaw;
    }

    public agg getMesh() {
        return this.cwN;
    }

    public void setZoomBack() {
        this.setZoom(1.0f, true);
        this.axw();
        this.a(new iT(this, this.cwV, this.cwW, this.cwX, this.cwY, this, 0, 500, ys.aCq, false));
    }

    public void setZoomToOne(int n2, int n3) {
        this.setZoom(1.0f, true);
        this.axw();
        this.a(new iT(this, this.cwV, this.cwW, this.getSphereXToDeltaX(n2), this.getSphereYToDeltaY(n3), this, 0, 500, ys.aCq, false));
    }

    public void axw() {
        for (int j = 0; j < 18; ++j) {
            if (cwO[j] == null) continue;
            this.getAppearance().b(cwO[j]);
            cwO[j].j();
            ahr_2.cwO[j] = null;
        }
    }

    public void setZoomToFullView() {
        float f = (float)this.cLZ.getContentWidth() / (float)(this.coi.getWidth() * this.coi.getCellWidth());
        f = Math.min(f, (float)this.cLZ.getContentHeight() / (float)(this.coi.getHeight() * this.coi.getCellHeight()));
        this.setZoom(Math.min(f, 1.0f), true);
        int n2 = this.getVisualWidth() - this.cLZ.getContentWidth();
        int n3 = this.getVisualHeight() - this.cLZ.getContentHeight();
        int n4 = n2 < 0 ? -n2 / 2 : 0;
        int n5 = n3 < 0 ? -n3 / 2 : 0;
        this.cwX = this.cwV;
        this.cwY = this.cwW;
        this.a(new iT(this, this.cwV, this.cwW, n4, n5, this, 0, 500, ys.aCq, true));
    }

    private void axx() {
        int n2 = 0;
        int n3 = Math.max(this.coi.getWidth(), this.coi.getHeight());
        for (ayr_0 ayr_02 : this.coi.Xn()) {
            if (!ayr_02.aLk() || n2 >= 18) continue;
            ahr_2.cwO[n2] = new ob_1();
            cwO[n2].b();
            cwO[n2].setFile("100.xps");
            cwO[n2].setX(-this.getWidth() / 2 + (ayr_02.aLe() + 1 + (n3 - this.coi.getWidth()) / 2) * this.getWidth() / n3);
            cwO[n2].setY(-this.getHeight() / 2 + (ayr_02.aLf() + 1 + (n3 - this.coi.getHeight()) / 2) * this.getHeight() / n3);
            cwO[n2].setLevel(0);
            cwO[n2].setAlignment(BT.aJX);
            this.getAppearance().a(cwO[n2]);
            ++n2;
        }
    }

    public void axy() {
        this.bv(this.coi.MS(), this.coi.MT());
    }

    private void bv(int n2, int n3) {
        this.setDeltaPosition(this.getSphereXToDeltaX(n2), this.getSphereYToDeltaY(n3));
    }

    private int getSphereXToDeltaX(int n2) {
        return ej_0.e(-(n2 * this.coi.getCellWidth() - BT.aJX.ag((int)((float)this.coi.getCellWidth() * this.aaw), this.cLZ.getContentWidth())), -Math.max(0, this.getVisualWidth() - this.cLZ.getContentWidth()), 0);
    }

    private int getSphereYToDeltaY(int n2) {
        return ej_0.e(-(n2 * this.coi.getCellHeight() - BT.aJX.ah((int)((float)this.coi.getCellHeight() * this.aaw), this.cLZ.getContentHeight())), -Math.max(0, this.getVisualHeight() - this.cLZ.getContentHeight()), 0);
    }

    private void ade() {
        this.aDb = new qy_1(this);
        this.a(qe_1.bFz, this.aDb, false);
        this.cwQ = new qx_1(this);
        this.a(qe_1.bFv, this.cwQ, false);
        this.cwP = new qw_2(this);
        this.a(qe_1.bFt, this.cwP, false);
        this.jk = new qv_1(this);
        this.a(qe_1.bFB, this.jk, false);
        this.cwR = new qu_1(this);
        this.a(qe_1.bFC, this.cwR, false);
    }

    public boolean cb(int n2) {
        boolean bl2 = super.cb(n2);
        if (this.cxd) {
            this.setTokenPixelPosition(this.coi.MS() * this.coi.getCellWidth(), this.coi.MT() * this.coi.getCellHeight());
            this.cwN.a(this.aLd, this.cLZ.getMargin(), this.cLZ.getBorder(), this.cLZ.getPadding());
            this.axy();
            this.cxd = false;
        }
        return bl2;
    }

    public void a(air_1 air_12) {
        ahr_2 ahr_22 = (ahr_2)air_12;
        super.a(air_12);
        ahr_22.b(qe_1.bFv, this.cwQ, false);
        ahr_22.b(qe_1.bFt, this.cwP, false);
        ahr_22.b(qe_1.bFz, this.aDb, false);
        ahr_22.b(qe_1.bFB, this.jk, false);
        ahr_22.b(qe_1.bFC, this.cwR, false);
        ahr_22.cwS = this.cwS;
    }

    public void yx() {
        super.yx();
        if (this.dxR != null) {
            this.dxR.setNeedsScissor(true);
        }
    }

    public void Aj() {
        super.Aj();
        this.ade();
    }

    public void j() {
        super.j();
        this.cwN.j();
        this.cwN = null;
        if (this.coi != null) {
            this.coi.MX();
            this.coi = null;
        }
        this.cwR = null;
        this.aDb = null;
        this.cwP = null;
        this.cwQ = null;
        this.jk = null;
        this.cxb = null;
    }

    public void b() {
        super.b();
        Zb zb = Zb.checkOut();
        zb.setWidget(this);
        this.a(zb);
        this.cwN = new agg();
        this.cwN.b();
        this.cwN.b(this);
        this.cwW = 0;
        this.cwV = 0;
        this.cxe = null;
        this.cwS = true;
    }

    public void x(ArrayList arrayList) {
        if (this.cxe == null || this.cxe.wv()) {
            this.cxe = new aeg_1(this, 0, (arrayList.size() - 1) * 500, ys.aCp, arrayList, this.coi);
            super.a(this.cxe);
        } else {
            this.cxe.atA().addAll(arrayList);
        }
    }

    public void a(aPk aPk2) {
        if (aPk2 instanceof aeg_1) {
            this.cxe = (aeg_1)aPk2;
        }
        super.a(aPk2);
    }

    public void setClickEnabled(boolean bl2) {
        this.cwS = bl2;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != cxf) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setClickEnabled(Gr.getBoolean(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != cxf) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setClickEnabled(Gr.getBoolean(object));
        return true;
    }

    static /* synthetic */ void c(ahr_2 ahr_22) {
        ahr_22.axx();
    }

    static /* synthetic */ float a(ahr_2 ahr_22, float f) {
        ahr_22.cxc = f;
        return ahr_22.cxc;
    }

    static /* synthetic */ float d(ahr_2 ahr_22) {
        return ahr_22.cxc;
    }

    static /* synthetic */ agg e(ahr_2 ahr_22) {
        return ahr_22.cwN;
    }

    static /* synthetic */ Point a(ahr_2 ahr_22, Point point) {
        ahr_22.cwT = point;
        return ahr_22.cwT;
    }

    static /* synthetic */ boolean a(ahr_2 ahr_22, boolean bl2) {
        ahr_22.cwU = bl2;
        return ahr_22.cwU;
    }

    static /* synthetic */ Point f(ahr_2 ahr_22) {
        return ahr_22.cwT;
    }

    static /* synthetic */ boolean g(ahr_2 ahr_22) {
        return ahr_22.cwU;
    }

    static /* synthetic */ void a(ahr_2 ahr_22, int n2, int n3) {
        ahr_22.setDeltaPosition(n2, n3);
    }

    static /* synthetic */ int a(ahr_2 ahr_22, int n2) {
        ahr_22.cwZ = n2;
        return ahr_22.cwZ;
    }

    static /* synthetic */ float h(ahr_2 ahr_22) {
        return ahr_22.aaw;
    }

    static /* synthetic */ int b(ahr_2 ahr_22, int n2) {
        ahr_22.cxa = n2;
        return ahr_22.cxa;
    }

    static /* synthetic */ ayr_0 i(ahr_2 ahr_22) {
        return ahr_22.cxb;
    }

    static /* synthetic */ int j(ahr_2 ahr_22) {
        return ahr_22.cwZ;
    }

    static /* synthetic */ int k(ahr_2 ahr_22) {
        return ahr_22.cxa;
    }

    static /* synthetic */ ayr_0 a(ahr_2 ahr_22, ayr_0 ayr_02) {
        ahr_22.cxb = ayr_02;
        return ahr_22.cxb;
    }

    static /* synthetic */ ayr_0 b(ahr_2 ahr_22, int n2, int n3) {
        return ahr_22.getSphereAt(n2, n3);
    }

    static /* synthetic */ boolean l(ahr_2 ahr_22) {
        return ahr_22.cwS;
    }
}

