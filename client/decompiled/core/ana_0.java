/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;
import java.io.BufferedInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/*
 * Renamed from anA
 */
public class ana_0
extends aht_1
implements oc_0,
ie_1 {
    public static final String TAG = "multipleImage";
    public static final String cJj = "internalPopup";
    public static final String cJk = "internalPopupTextView";
    protected vo_2 cJl;
    private boolean cwU = false;
    private Point cwT = null;
    protected boolean cJm = false;
    protected boolean cJn = true;
    protected boolean cJo = false;
    private boolean cJp = false;
    private boolean cJq = false;
    protected int cwV;
    protected int cwW;
    private int cJr;
    private int cJs;
    protected int bTj = 0;
    protected int bTk = 0;
    private int cJt = 0;
    private int cJu = 0;
    private boolean cJv = false;
    private boolean cJw = false;
    private final ArrayList bTh = new ArrayList();
    private final ArrayList bTi = new ArrayList();
    private akq_1 arn = null;
    private bt_2 cJx = null;
    private ov_1 aDb;
    private ov_1 aDc;
    private ov_1 cwQ;
    private ov_1 cwP;
    private adg_2 cJy;
    private ps cJz;
    private lf_1 cJA;
    public static final int aHX = "modulationColor".hashCode();
    public static final int cJB = "imagePath".hashCode();
    public static final int cJC = "manualInnerMove".hashCode();
    public static final int cJD = "useInnerMoveTween".hashCode();
    public static final int cJE = "shrinkToImageWidth".hashCode();
    public static final int cJF = "shrinkToImageHeight".hashCode();
    public static final int cJG = "toggleInnerMoveOnClick".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof ur_1) {
            this.setPixmap((ur_1)na_12);
        } else if (na_12 instanceof adg_2 && na_12.getId().equals(cJj)) {
            this.cJy = (adg_2)na_12;
            this.cJy.setVisible(false);
        }
        super.a(na_12);
    }

    protected void pX() {
        if (this.arC != null && this.cJl.getEntity() != null) {
            this.arC.i(this.cJl.getEntity());
        }
        super.pX();
    }

    public String getTag() {
        return TAG;
    }

    public void setModulationColor(vP vP2) {
        if (this.cJl != null) {
            this.cJl.setModulationColor(vP2);
        }
    }

    public boolean getManualInnerMove() {
        return this.cJv;
    }

    public void setManualInnerMove(boolean bl2) {
        this.cJv = bl2;
        this.aCv();
    }

    private void aCv() {
        xy_0 xy_02 = xy_0.bYl;
        if (this.cJv) {
            xy_02 = this.cJp && !this.cJq ? xy_0.bYp : (this.cJq && !this.cJp ? xy_0.bYq : xy_0.bYn);
        }
        this.setCursorType(xy_02);
    }

    public void setUseInnerMoveTween(boolean bl2) {
        this.aCw();
        if (bl2) {
            this.b(0.0f, 1.0f, 0.0f, 1.0f, 5000);
        }
    }

    public boolean getShrinkToImageWidth() {
        return this.cJq;
    }

    public void setShrinkToImageWidth(boolean bl2) {
        this.cJq = bl2;
        this.aCv();
    }

    public boolean getShrinkToImageHeight() {
        return this.cJp;
    }

    public void setShrinkToImageHeight(boolean bl2) {
        this.cJp = bl2;
        this.aCv();
    }

    public boolean getToggleInnerMoveOnClick() {
        return this.cJw;
    }

    public void setToggleInnerMoveOnClick(boolean bl2) {
        this.cJw = bl2;
    }

    public void setPixmap(ur_1 ur_12) {
        this.bTh.clear();
        if (this.cJl != null) {
            this.cJl.clear();
        }
        this.arn = ur_12.getPixmap();
        this.arn.a(this);
        xt_1 xt_12 = new xt_1(this.arn, 0, 0);
        this.bTh.add(xt_12);
    }

    public vP getModulationColor() {
        return this.cJl != null ? this.cJl.getModulationColor() : null;
    }

    public int getDeltaX() {
        return this.cwV;
    }

    public void setDeltaX(int n2) {
        this.cwV = ej_0.e(n2, -(this.cJt - this.cLZ.getContentWidth()), 0);
        this.cJn = true;
        this.setNeedsToPreProcess();
    }

    public int getDeltaY() {
        return this.cwW;
    }

    public void setDeltaY(int n2) {
        this.cwW = ej_0.e(n2, 0, this.cJu - this.cLZ.getContentHeight());
        this.cJn = true;
        this.setNeedsToPreProcess();
    }

    public int getChunkWidth() {
        return this.bTj;
    }

    public void setChunkWidth(int n2) {
        this.bTj = n2;
    }

    public int getChunkHeight() {
        return this.bTk;
    }

    public void setChunkHeight(int n2) {
        this.bTk = n2;
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return true;
    }

    public void setImagePath(String string) {
        URL uRL;
        if (string == null) {
            return;
        }
        try {
            uRL = new URL(string);
        }
        catch (MalformedURLException malformedURLException) {
            a.error((Object)("URL invalide : " + string));
            return;
        }
        aAN aAN2 = new aAN();
        aNe aNe2 = new aNe();
        try {
            aAN2.q(new BufferedInputStream(uRL.openStream()));
            aAN2.a(aNe2, new tf_2[0]);
            aAN2.close();
        }
        catch (Exception exception) {
            a.error((Object)("Probl\u00e8me lors de la lecture du fichier de map d'url : " + uRL));
            return;
        }
        this.bTh.clear();
        if (this.cJl != null) {
            this.cJl.clear();
        }
        this.cwV = 0;
        this.cwW = 0;
        this.bTj = 0;
        this.bTk = 0;
        ArrayList arrayList = aNe2.aXo().getChildren();
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            Object object;
            Object object2;
            int n3;
            k_0 k_02 = (k_0)arrayList.get(j);
            if (k_02.getName().equals("#text") || k_02.getName().equals("#comment")) continue;
            if (k_02.getName().equalsIgnoreCase("parameters")) {
                k_0 k_03 = k_02.f("maxWidth");
                if (k_03 != null) {
                    this.bTj = k_03.getIntValue();
                }
                if ((k_03 = k_02.f("maxHeight")) != null) {
                    this.bTk = k_03.getIntValue();
                }
                if ((k_03 = k_02.f("totalWidth")) != null) {
                    this.cJt = k_03.getIntValue();
                }
                if ((k_03 = k_02.f("totalHeight")) == null) continue;
                this.cJu = k_03.getIntValue();
                continue;
            }
            if (k_02.getName().equalsIgnoreCase("image")) {
                int n4 = 0;
                n3 = 0;
                ef_1 ef_12 = null;
                k_0 k_04 = k_02.f("x");
                if (k_04 != null) {
                    n4 = k_04.getIntValue();
                }
                if ((k_04 = k_02.f("y")) != null) {
                    n3 = k_04.getIntValue();
                }
                if ((k_04 = k_02.f("texture")) != null) {
                    object2 = k_04.getStringValue();
                    try {
                        object = an_2.a(uRL, (String)object2);
                        String string2 = ((URL)object).toString();
                        ef_12 = this.iL(string2);
                    }
                    catch (Exception exception) {
                        a.error((Object)"Probl\u00e8me lors de la r\u00e9cup\u00e9ration de la texture de la map");
                    }
                }
                object2 = new xt_1(new akq_1(ef_12), n4, n3);
                this.bTh.add(object2);
                continue;
            }
            if (!k_02.getName().equalsIgnoreCase("text")) continue;
            int n5 = 0;
            n3 = 0;
            int n6 = 40;
            int n7 = 40;
            object2 = null;
            object = k_02.f("x");
            if (object != null) {
                n5 = object.getIntValue();
            }
            if ((object = k_02.f("y")) != null) {
                n3 = object.getIntValue();
            }
            if ((object = k_02.f("width")) != null) {
                n6 = object.getIntValue();
            }
            if ((object = k_02.f("height")) != null) {
                n7 = object.getIntValue();
            }
            if ((object = k_02.f("key")) != null) {
                object2 = object.getStringValue();
            }
            this.bTi.add(new bt_2((String)object2, n5, n3, n6, n7));
        }
        this.cJo = true;
        this.cJn = true;
        this.setNeedsToPreProcess();
    }

    private boolean zs() {
        boolean bl2 = false;
        int n2 = 0;
        int n3 = 0;
        if (this.cJp) {
            n3 = this.cJu;
        }
        if (this.cJq) {
            n2 = this.cJt;
        }
        if (this.aLb == null || n2 != this.aLb.width || n3 != this.aLb.height) {
            this.setMinSize(new agj_1(n2, n3));
            bl2 = true;
        }
        return bl2;
    }

    public void b(float f, float f2, float f3, float f4, int n2) {
        this.q(lf_1.class);
        this.cJA = new lf_1(this, f, f2, f3, f4, this, 0, n2, ys.aCq);
        this.cJA.pT(-1);
        this.a(this.cJA);
    }

    public void aCw() {
        this.q(lf_1.class);
    }

    public void setTweenPaused(boolean bl2) {
        if (this.cJA != null) {
            this.cJA.setPaused(bl2);
        }
    }

    public boolean aCx() {
        return this.cJA != null;
    }

    private ef_1 iL(String string) {
        return cx_0.JY().a(arX.cQT.iE(), ej_0.aa(string), string, new adz_1(), false);
    }

    public void Aj() {
        super.Aj();
        this.cJz = (ps)this.getElementMap().R(cJk);
    }

    public void j() {
        super.j();
        this.cJA = null;
        ago_2.getInstance().b(qe_1.bFA, this.aDc, false);
        this.cwQ = null;
        this.cwP = null;
        this.aDc = null;
        this.aDb = null;
        if (this.cJl != null) {
            this.cJl.j();
            this.cJl = null;
        }
        if (this.arn != null) {
            this.arn.b(this);
            this.arn = null;
        }
        this.bTh.clear();
    }

    public void b() {
        super.b();
        this.setNonBlocking(false);
        this.setLayoutManager(null);
        Zb zb = Zb.checkOut();
        zb.setWidget(this);
        this.a(zb);
        this.setNeedsToPreProcess();
        this.cJl = new vo_2();
        this.cJl.b();
        this.aCy();
    }

    public void yx() {
        super.yx();
        this.dxR.setNeedsScissor(true);
    }

    public void validate() {
        if (this.cJl != null) {
            this.cJl.a(this.aLd, this.cLZ.getMargin(), this.cLZ.getBorder(), this.cLZ.getPadding());
        }
        if (this.cJy != null) {
            if (this.cJx != null) {
                this.cJy.setVisible(true);
                this.cJy.setSizeToPrefSize();
                int n2 = -BT.aJX.ag(this.cJx.getWidth(), this.cJy.getWidth()) + this.cJx.getX() + this.cwV;
                int n3 = this.cJx.getY() + this.cwW + this.cJx.getHeight();
                this.cJy.setPosition(n2, n3);
            } else {
                this.cJy.setVisible(false);
            }
        }
        super.validate();
    }

    private void aCy() {
        this.aDb = new jj_0(this);
        this.a(qe_1.bFz, this.aDb, false);
        this.aDc = new jh_0(this);
        ago_2.getInstance().a(qe_1.bFw, this.aDc, false);
        this.cwQ = new jb_0(this);
        this.a(qe_1.bFv, this.cwQ, false);
        this.cwP = new jz_1(this);
        this.a(qe_1.bFt, this.cwP, false);
        this.a(qe_1.bFB, new jd_1(this), false);
    }

    private boolean isInside(bt_2 bt_22, int n2, int n3) {
        return bt_22.getX() <= n2 && bt_22.getX() + bt_22.getWidth() >= n2 && bt_22.getY() <= n3 && bt_22.getY() + bt_22.getHeight() >= n3;
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        boolean bl3 = false;
        boolean bl4 = this.zs();
        if (this.cJm) {
            this.cJu = this.bTk = this.arn.getHeight();
            this.cJt = this.bTj = this.arn.getWidth();
            this.setDeltaX(this.cwV);
            this.setDeltaY(this.cwW);
            this.cJm = false;
            this.cJn = true;
            this.cJo = true;
        }
        if (this.cJl != null && this.cJn) {
            this.cJl.setX(this.cwV);
            this.cJl.setY(this.cwW);
            this.cJl.setHeight(this.bTk);
            this.cJl.setWidth(this.bTj);
            this.cJn = false;
            bl3 = true;
        }
        if (this.cJl != null && this.cJo) {
            this.cJl.clear();
            int n3 = this.bTh.size();
            for (int j = 0; j < n3; ++j) {
                this.cJl.a((xt_1)this.bTh.get(j));
            }
            this.cJo = false;
            bl3 = true;
        }
        if (bl3) {
            try {
                if (this.cLZ != null) {
                    this.cJl.a(this.aLd, this.cLZ.getMargin(), this.cLZ.getBorder(), this.cLZ.getPadding());
                }
            }
            catch (NullPointerException nullPointerException) {
                a.error((Object)("imageMesh = " + this.cJl + ", appearance = " + this.cLZ), (Throwable)nullPointerException);
            }
        }
        if (bl4) {
            this.Am();
        }
        return bl2;
    }

    public void a(air_1 air_12) {
        ana_0 ana_02 = (ana_0)air_12;
        super.a(air_12);
        ana_02.b(qe_1.bFw, this.aDc, false);
        ana_02.b(qe_1.bFv, this.cwQ, false);
        ana_02.b(qe_1.bFt, this.cwP, false);
        ana_02.b(qe_1.bFz, this.aDb, false);
        ana_02.setModulationColor(ana_02.getModulationColor());
        ana_02.setManualInnerMove(this.cJv);
        ana_02.setShrinkToImageWidth(this.cJq);
        ana_02.setShrinkToImageHeight(this.cJp);
        ana_02.setToggleInnerMoveOnClick(this.cJw);
    }

    public void a(akq_1 akq_12) {
        this.cJm = true;
        this.setNeedsToPreProcess();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == cJE) {
            this.setShrinkToImageWidth(Gr.getBoolean(string));
        } else if (n2 == cJF) {
            this.setShrinkToImageHeight(Gr.getBoolean(string));
        } else if (n2 == cJG) {
            this.setToggleInnerMoveOnClick(Gr.getBoolean(string));
        } else if (n2 == cJD) {
            this.setUseInnerMoveTween(Gr.getBoolean(string));
        } else if (n2 == cJC) {
            this.setManualInnerMove(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == cJE) {
            this.setShrinkToImageWidth(Gr.getBoolean(object));
        } else if (n2 == cJF) {
            this.setShrinkToImageHeight(Gr.getBoolean(object));
        } else if (n2 == aHX) {
            this.setModulationColor((vP)object);
        } else if (n2 == cJB) {
            this.setImagePath((String)object);
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ int a(ana_0 ana_02) {
        return ana_02.cJt;
    }

    static /* synthetic */ int b(ana_0 ana_02) {
        return ana_02.cJu;
    }

    static /* synthetic */ lf_1 a(ana_0 ana_02, lf_1 lf_12) {
        ana_02.cJA = lf_12;
        return ana_02.cJA;
    }

    static /* synthetic */ Point a(ana_0 ana_02, Point point) {
        ana_02.cwT = point;
        return ana_02.cwT;
    }

    static /* synthetic */ boolean a(ana_0 ana_02, boolean bl2) {
        ana_02.cwU = bl2;
        return ana_02.cwU;
    }

    static /* synthetic */ boolean c(ana_0 ana_02) {
        return ana_02.cwU;
    }

    static /* synthetic */ int d(ana_0 ana_02) {
        return ana_02.cJr;
    }

    static /* synthetic */ int e(ana_0 ana_02) {
        return ana_02.cJs;
    }

    static /* synthetic */ int a(ana_0 ana_02, int n2) {
        ana_02.cJr = n2;
        return ana_02.cJr;
    }

    static /* synthetic */ int b(ana_0 ana_02, int n2) {
        ana_02.cJs = n2;
        return ana_02.cJs;
    }

    static /* synthetic */ boolean f(ana_0 ana_02) {
        return ana_02.cJv;
    }

    static /* synthetic */ Point g(ana_0 ana_02) {
        return ana_02.cwT;
    }

    static /* synthetic */ ArrayList h(ana_0 ana_02) {
        return ana_02.bTi;
    }

    static /* synthetic */ boolean a(ana_0 ana_02, bt_2 bt_22, int n2, int n3) {
        return ana_02.isInside(bt_22, n2, n3);
    }

    static /* synthetic */ bt_2 i(ana_0 ana_02) {
        return ana_02.cJx;
    }

    static /* synthetic */ bt_2 a(ana_0 ana_02, bt_2 bt_22) {
        ana_02.cJx = bt_22;
        return ana_02.cJx;
    }

    static /* synthetic */ adg_2 j(ana_0 ana_02) {
        return ana_02.cJy;
    }

    static /* synthetic */ ps k(ana_0 ana_02) {
        return ana_02.cJz;
    }

    static /* synthetic */ boolean l(ana_0 ana_02) {
        return ana_02.cJw;
    }
}

