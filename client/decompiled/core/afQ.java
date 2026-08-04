/*
 * Decompiled with CFR 0.152.
 */
public class afQ
extends aht_1 {
    public static final String TAG = "ScrollContainer";
    public static final String bJx = "horizontalScrollBar";
    public static final String bJy = "verticalScrollBar";
    private aIg csd;
    private aIg cse;
    private aDM csf = aDM.dyU;
    private aDM csg = aDM.dyU;
    private boolean csh = false;
    private boolean csi = false;
    private BT csj = null;
    private boolean csk = true;
    private float csl = 1.0f;
    private bo_0 csm = bo_0.aJu;
    private bo_0 csn = bo_0.aJt;
    private boolean cso = false;
    private adg_2 csp;
    public static final int csq = "enableScrollBar".hashCode();
    public static final int csr = "horizontalScrollBarBehaviour".hashCode();
    public static final int css = "verticalScrollBarBehaviour".hashCode();
    public static final int cst = "horizontalScrollBarPosition".hashCode();
    public static final int csu = "verticalScrollBarPosition".hashCode();
    public static final int csv = "horizontalScrollBarAlignment".hashCode();
    public static final int csw = "verticalScrollBarAlignment".hashCode();
    public static final int csx = "innerWidgetAlign".hashCode();

    public void h(adg_2 adg_22) {
        if (adg_22 != this.csd && adg_22 != this.cse) {
            if (this.csp != null) {
                this.k(this.csp);
            }
            this.csp = adg_22;
            this.csp.setSize(this.csp.getPrefSize());
        }
        super.h(adg_22);
    }

    public String getTag() {
        return TAG;
    }

    public bo_0 getVerticalScrollBarAlignment() {
        return this.csm;
    }

    public void setVerticalScrollBarAlignment(bo_0 bo_02) {
        this.csm = bo_02;
    }

    public bo_0 getHorizontalScrollBarAlignment() {
        return this.csn;
    }

    public void setHorizontalScrollBarAlignment(bo_0 bo_02) {
        this.csn = bo_02;
    }

    public aDM getVerticalScrollBarBehaviour() {
        return this.csf;
    }

    public void setVerticalScrollBarBehaviour(aDM aDM2) {
        if (this.csf == null || !this.csf.equals((Object)aDM2)) {
            this.csf = aDM2;
            this.cso = true;
            this.setNeedsToPreProcess();
        }
    }

    public void setVerticalScrollBarPosition(float f) {
        this.csd.setValue(f);
    }

    public aDM getHorizontalScrollBarBehaviour() {
        return this.csg;
    }

    public void setHorizontalScrollBarBehaviour(aDM aDM2) {
        if (this.csg == null || !this.csg.equals((Object)aDM2)) {
            this.csg = aDM2;
            this.cso = true;
            this.setNeedsToPreProcess();
        }
    }

    public void setHorizontalScrollBarPosition(float f) {
        this.cse.setValue(f);
    }

    public boolean isEnableScrollBar() {
        return this.csk;
    }

    public void setEnableScrollBar(boolean bl2) {
        this.csk = bl2;
        this.cso = true;
        this.setNeedsToPreProcess();
    }

    public BT getInnerWidgetAlign() {
        return this.csj;
    }

    public void setInnerWidgetAlign(BT bT) {
        this.csj = bT;
    }

    public aIg getVerticalScrollBar() {
        return this.csd;
    }

    public aIg getHorizontalScrollBar() {
        return this.cse;
    }

    public adg_2 getWidgetByThemeElementName(String string, boolean bl2) {
        if (bJx.equalsIgnoreCase(string)) {
            return this.cse;
        }
        if (bJy.equalsIgnoreCase(string)) {
            return this.csd;
        }
        return null;
    }

    public nm_0 getScissor(adg_2 adg_22) {
        if (adg_22 == this.csd || adg_22 == this.cse) {
            return nm_0.k(this.getScreenX() + this.cLZ.getLeftInset(), this.getScreenY() + this.cLZ.getBottomInset(), this.aLd.width - this.cLZ.getLeftInset() - this.cLZ.getRightInset(), this.aLd.height - this.cLZ.getBottomInset() - this.cLZ.getTopInset());
        }
        return nm_0.k(this.getScreenX() + this.cLZ.getLeftInset() + (this.csh && this.csm.equals((Object)bo_0.aJv) ? this.csd.getWidth() : 0), this.getScreenY() + this.cLZ.getBottomInset() + (this.csi && this.csn.equals((Object)bo_0.aJt) ? this.cse.getHeight() : 0), this.aLd.width - this.cLZ.getLeftInset() - this.cLZ.getRightInset() - (this.csh ? this.csd.getWidth() : 0), (int)(this.csl * (float)(this.aLd.height - this.cLZ.getBottomInset() - this.cLZ.getTopInset() - (this.csi ? this.cse.getHeight() : 0))));
    }

    public adg_2 getWidget(int n2, int n3) {
        if (this.czc || !this.aQv || !this.getAppearance().aY(n2, n3) || ago_2.getInstance().isMovePointMode()) {
            return null;
        }
        adg_2 adg_22 = null;
        if (this.csi && !this.cse.isUnloading() && (adg_22 = this.cse.getWidget((n2 -= this.getAppearance().getLeftInset()) - this.cse.dxS.x, (n3 -= this.getAppearance().getBottomInset()) - this.cse.dxS.y)) != null) {
            return adg_22;
        }
        if (this.csh && !this.csd.isUnloading() && (adg_22 = this.csd.getWidget(n2 - this.csd.dxS.x, n3 - this.csd.dxS.y)) != null) {
            return adg_22;
        }
        if (this.csh && this.csi && n3 < this.csd.getY()) {
            return this.dyc ? null : this;
        }
        if (this.csp != null && !this.csp.isUnloading()) {
            adg_22 = this.csp.getWidget(n2 - this.csp.dxS.x, n3 - this.csp.dxS.y);
        }
        return adg_22 != null ? adg_22 : (this.dyc ? null : this);
    }

    public void aQ(float f) {
        this.csd.setValue(f);
    }

    public void aR(float f) {
        this.cse.setValue(f);
    }

    public void invalidate() {
        super.invalidate();
    }

    public void j() {
        super.j();
        this.csd = null;
        this.cse = null;
    }

    public void b() {
        super.b();
        aik_2 aik_22 = new aik_2(this, null);
        aik_22.b();
        this.a(aik_22);
        this.dyc = false;
        this.a(qe_1.bFz, false);
        this.a(qe_1.bFA, false);
        this.a(qe_1.bFB, false);
        this.a(qe_1.bFC, false);
        this.a(qe_1.bFv, false);
        this.a(qe_1.bFu, false);
        this.a(qe_1.bFw, false);
        this.csl = 1.0f;
        this.csd = new aIg();
        this.csd.b();
        this.csd.setCanBeCloned(false);
        this.csd.setHorizontal(false);
        this.a(this.csd);
        this.csd.a(qe_1.bFH, new ao_1(this), false);
        this.cse = new aIg();
        this.cse.b();
        this.cse.setCanBeCloned(false);
        this.cse.setHorizontal(true);
        this.a(this.cse);
        this.cse.a(qe_1.bFH, new An(this), false);
        this.a(qe_1.bFD, new am_0(this), false);
        this.csk = true;
        this.dyu = true;
    }

    public void avC() {
        this.q(zH.class);
        this.csl = 1.0f;
    }

    public void avD() {
        this.a(new zH(this, this.csl, 0.0f, this, 10000, 5000, ys.aCq));
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.cso) {
            this.Am();
            this.cso = false;
        }
        return bl2;
    }

    public void a(air_1 air_12) {
        afQ afQ2 = (afQ)air_12;
        super.a((air_1)afQ2);
        afQ2.csg = this.csg;
        afQ2.csf = this.csf;
        afQ2.csj = this.csj;
        afQ2.csk = this.csk;
        afQ2.dyg = true;
        afQ2.setNeedsToPreProcess();
    }

    private void avE() {
        this.z(this.cse.getSlider().getValue());
        this.A(this.csd.getSlider().getValue());
    }

    private void z(double d) {
        int n2 = this.csp.getSize().width;
        int n3 = this.cLZ.getContentWidth();
        if (this.csh) {
            n3 = (int)((double)n3 - this.csd.getSize().getWidth());
        }
        int n4 = this.csj == null || n2 - n3 > 0 ? -((int)((double)(n2 - n3) * d)) : this.csj.ag(n2, n3);
        if (this.csh && this.csm.equals((Object)bo_0.aJv)) {
            n4 = (int)((double)n4 + this.csd.getSize().getWidth());
        }
        this.csp.setX(n4);
    }

    private void A(double d) {
        int n2 = this.csp.getSize().height;
        int n3 = this.cLZ.getContentHeight();
        if (this.csi) {
            n3 = (int)((double)n3 - this.cse.getSize().getHeight());
        }
        int n4 = this.csj == null || n2 - n3 > 0 ? -((int)((double)(n2 - n3) * d)) : this.csj.ah(n2, n3);
        if (this.csi && this.csn.equals((Object)bo_0.aJt)) {
            n4 = (int)((double)n4 + this.cse.getSize().getHeight());
        }
        this.csp.setY(n4);
    }

    public boolean avF() {
        return (double)this.getAppearance().getContentHeight() < this.csp.getPrefSize().getHeight();
    }

    public boolean avG() {
        return (double)this.getAppearance().getContentWidth() < this.csp.getPrefSize().getWidth();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == csq) {
            this.setEnableScrollBar(Gr.getBoolean(string));
        } else if (n2 == csr) {
            this.setHorizontalScrollBarBehaviour(aDM.kV(string));
        } else if (n2 == css) {
            this.setVerticalScrollBarBehaviour(aDM.kV(string));
        } else if (n2 == csx) {
            this.setInnerWidgetAlign(BT.dv(string));
        } else if (n2 == cst) {
            this.setHorizontalScrollBarPosition(Gr.getFloat(string));
        } else if (n2 == csu) {
            this.setVerticalScrollBarPosition(Gr.getFloat(string));
        } else if (n2 == csv) {
            this.setHorizontalScrollBarAlignment(bo_0.ds(string));
        } else if (n2 == csw) {
            this.setVerticalScrollBarAlignment(bo_0.ds(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == csq) {
            this.setEnableScrollBar(Gr.getBoolean(object));
        } else if (n2 == csr) {
            this.setHorizontalScrollBarBehaviour((aDM)((Object)object));
        } else if (n2 == css) {
            this.setVerticalScrollBarBehaviour((aDM)((Object)object));
        } else if (n2 == cst) {
            this.setHorizontalScrollBarPosition(Gr.getFloat(object));
        } else if (n2 == csu) {
            this.setVerticalScrollBarPosition(Gr.getFloat(object));
        } else if (n2 == csx) {
            this.setInnerWidgetAlign((BT)((Object)object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ float a(afQ afQ2) {
        return afQ2.csl;
    }

    static /* synthetic */ float a(afQ afQ2, float f) {
        afQ2.csl = f;
        return afQ2.csl;
    }

    static /* synthetic */ boolean b(afQ afQ2) {
        return afQ2.csk;
    }

    static /* synthetic */ aIg c(afQ afQ2) {
        return afQ2.csd;
    }

    static /* synthetic */ aIg d(afQ afQ2) {
        return afQ2.cse;
    }

    static /* synthetic */ adg_2 e(afQ afQ2) {
        return afQ2.csp;
    }

    static /* synthetic */ aDM f(afQ afQ2) {
        return afQ2.csg;
    }

    static /* synthetic */ aDM g(afQ afQ2) {
        return afQ2.csf;
    }

    static /* synthetic */ boolean a(afQ afQ2, boolean bl2) {
        afQ2.csi = bl2;
        return afQ2.csi;
    }

    static /* synthetic */ boolean b(afQ afQ2, boolean bl2) {
        afQ2.csh = bl2;
        return afQ2.csh;
    }

    static /* synthetic */ boolean h(afQ afQ2) {
        return afQ2.csh;
    }

    static /* synthetic */ boolean i(afQ afQ2) {
        return afQ2.csi;
    }

    static /* synthetic */ BT j(afQ afQ2) {
        return afQ2.csj;
    }

    static /* synthetic */ void k(afQ afQ2) {
        afQ2.avE();
    }

    static /* synthetic */ bo_0 l(afQ afQ2) {
        return afQ2.csm;
    }

    static /* synthetic */ bo_0 m(afQ afQ2) {
        return afQ2.csn;
    }

    static /* synthetic */ void a(afQ afQ2, double d) {
        afQ2.A(d);
    }

    static /* synthetic */ void b(afQ afQ2, double d) {
        afQ2.z(d);
    }
}

