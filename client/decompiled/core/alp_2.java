/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.EntityGroup;
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Renamed from aLP
 */
public abstract class alp_2
extends kn_1 {
    private static final int dWq = 200;
    private static final int dWr = 20;
    protected EntityGroup cFH;
    private aea_2 dWs = aea_2.dzt;
    private aNX dWt = null;
    private int dWu = 1000;
    protected boolean dWv = true;
    protected float dWw = Float.NaN;
    protected float dWx = Float.NaN;
    protected float dWy;
    protected float dWz = Float.NaN;
    protected float dWA = Float.NaN;
    protected float dWB = Float.NaN;
    protected float dWC;
    protected float dWD;
    private boolean dWE;
    protected float aaw = 1.0f;
    protected float drG = 1.0f;
    protected float drF = 1.0f;
    protected float Er = 1.0f;
    private boolean dWF = false;
    protected aaj dWG = null;
    protected EntitySprite dWH = null;
    private final ArrayList dWI = new ArrayList();
    private final ArrayList ec = new ArrayList();
    private aaj dWJ = null;
    private final ArrayList dWK = new ArrayList();
    private EntitySprite dWL = null;
    private final ArrayList dWM = new ArrayList();
    private final HashMap aPu = new HashMap();
    private boolean dWN = false;
    protected aba_2 cuD;
    private float dWO;
    private boolean dWP = false;
    public static final int ei = "content".hashCode();
    public static final int dWQ = "compassContent".hashCode();
    public static final int dWR = "landMarkContent".hashCode();
    public static final int dWS = "isoCenterX".hashCode();
    public static final int dWT = "isoCenterY".hashCode();
    public static final int dWU = "isoCenterZ".hashCode();
    public static final int dWV = "isoMap".hashCode();
    public static final int dWW = "maxZoom".hashCode();
    public static final int dWX = "minZoom".hashCode();
    public static final int dWY = "tooltipHotPoint".hashCode();
    public static final int dWZ = "zoomScale".hashCode();
    public static final int dXa = "onMapClick".hashCode();
    public static final int dXb = "onMapDoubleClick".hashCode();
    public static final int dXc = "onMapMove".hashCode();
    public static final int dXd = "enableTooltip".hashCode();
    public static final int dXe = "landmarkZoom".hashCode();

    private void a(aba_2 aba_22, aaj aaj2, EntitySprite entitySprite, float f, float f2, float f3, float f4, boolean bl2, boolean bl3) {
        int n2;
        int n3;
        if (!aaj2.isVisible()) {
            return;
        }
        if (aaj2.aH(0.05f)) {
            aaj2.z(bl3 ? aaj2.aoM() : this.b(aaj2.tU(), aaj2.aoM(), 20));
            aaj2.A(bl3 ? aaj2.aoL() : this.b(aaj2.tV(), aaj2.aoL(), 20));
            aaj2.aD(bl3 ? aaj2.aoN() : this.b(aaj2.aoJ(), aaj2.aoN(), 20));
        }
        boolean bl4 = this.dWH == entitySprite;
        int n4 = 3;
        int n5 = 3;
        boolean bl5 = this.dWP && aaj2.aoK() != null;
        ef_1 ef_12 = this.getMeshTexture(bl5 ? aaj2.aoK() : aaj2.iu());
        if (ef_12 != null) {
            if (ef_12.isEmpty()) {
                return;
            }
            kf_0 kf_02 = ef_12.lB(0);
            adz_1 adz_12 = cx_0.JY().c(ef_12);
            n4 = adz_12.getX();
            n5 = adz_12.getY();
            entitySprite.setTexture(ef_12);
            float f5 = kf_02.getWidth();
            entitySprite.k(0.0f, 0.0f, (float)n5 / f5, (float)n4 / (float)kf_02.getWidth());
        }
        float f6 = (float)(aba_22.i(aaj2.tU(), aaj2.tV()) - (double)f3);
        float f7 = (float)(aba_22.j(aaj2.tU(), aaj2.tV()) - (double)f4 + (double)aaj2.aoJ() * aba_22.aNA() * (double)this.aaw);
        int n6 = (int)(f6 + (float)this.getAppearance().getContentWidth() / 2.0f);
        int n7 = (int)(f7 + (float)this.getAppearance().getContentHeight() / 2.0f);
        boolean bl6 = this.getAppearance().aY(n6, n7);
        if (!bl6 && bl2) {
            n3 = this.getAppearance().getOnScreenX(n6, n7) - (int)((float)this.getAppearance().getContentWidth() / 2.0f);
            n2 = this.getAppearance().getOnScreenY(n6, n7) - (int)((float)this.getAppearance().getContentHeight() / 2.0f);
        } else {
            n3 = (int)f6;
            n2 = (int)f7;
        }
        if (bl4 && aaj2.aoQ()) {
            n4 = (int)((double)n4 * 1.2);
            n5 = (int)((double)n5 * 1.2);
        }
        if (!bl5) {
            n4 = (int)((float)n4 * this.dWO);
            n5 = (int)((float)n5 * this.dWO);
        }
        entitySprite.setSize(n4, n5);
        entitySprite.x((int)(f2 + (float)(n2 += (int)((float)n5 / 2.0f))), (int)(f + (float)(n3 -= (int)((float)n4 / 2.0f))));
        float[] fArray = aaj2.Aa();
        if (fArray != null && fArray.length == 4) {
            float f8 = fArray[3] * (bl2 && !bl6 ? 0.5f : 1.0f);
            entitySprite.setColor(fArray[0], fArray[1], fArray[2], f8);
        }
        if (bl2 || bl6) {
            if (aaj2.aoR()) {
                entitySprite.b(ahA.axi().ih("transform"));
                entitySprite.oM(1801198125);
            } else if (entitySprite.aUP() != null) {
                entitySprite.b((asr_0)null);
            }
            this.cFH.i(entitySprite);
        }
    }

    public void a(na_1 na_12) {
        super.a(na_12);
    }

    protected void pX() {
        this.arC.i(this.cFH);
        super.pX();
    }

    public float getIsoCenterX() {
        return this.dWw;
    }

    public void setIsoCenterX(float f) {
        if (Double.isNaN(this.dWz)) {
            this.dWw = f;
            this.dWz = f;
        } else {
            this.dWz = f;
        }
        assert (!Double.isNaN(this.dWw));
    }

    public float getIsoCenterY() {
        return this.dWx;
    }

    public void setIsoCenterY(float f) {
        if (Double.isNaN(this.dWA)) {
            this.dWx = f;
            this.dWA = f;
        } else {
            this.dWA = f;
        }
    }

    public float getIsoCenterZ() {
        return this.dWy;
    }

    public void setIsoCenterZ(float f) {
        if (Double.isNaN(this.dWB)) {
            this.dWy = f;
            this.dWB = f;
        } else {
            this.dWB = f;
        }
    }

    public boolean getEnableTooltip() {
        return this.dWv;
    }

    public void setEnableTooltip(boolean bl2) {
        this.dWv = bl2;
    }

    public float getZoom() {
        return this.aaw;
    }

    public float getMinZoom() {
        return this.drF;
    }

    public void setMinZoom(float f) {
        if (f > 0.0f && f <= 1.0f) {
            this.drF = f;
            this.setZoom();
        }
    }

    public float getMaxZoom() {
        return this.drG;
    }

    public void setMaxZoom(float f) {
        if (f > 0.0f && f <= 1.0f) {
            this.drG = f;
            this.setZoom();
        }
    }

    public float getZoomScale() {
        return this.Er;
    }

    public void setZoomScale(float f) {
        if (f >= 0.0f && f <= 1.0f) {
            this.Er = f;
            this.setZoom();
        }
    }

    public aea_2 getTooltipHotPoint() {
        return this.dWs;
    }

    public void setTooltipHotPoint(aea_2 aea_22) {
        this.dWs = aea_22;
    }

    protected void setZoom() {
        this.aaw = this.drF + (this.drG - this.drF) * this.Er;
        assert (this.aaw > 0.0f && this.aaw <= 1.0f);
        this.cuD.L(86.0 * (double)this.aaw);
        this.cuD.M(43.0 * (double)this.aaw * (double)(this.dWE ? 1 : 2));
    }

    public boolean isIsoMap() {
        return this.dWE;
    }

    public void setIsoMap(boolean bl2) {
        this.dWE = bl2;
        this.setZoom();
    }

    public ArrayList getItems() {
        return this.ec;
    }

    public ArrayList getLandMarks() {
        return this.dWI;
    }

    public aaj getCompassFollowedItem() {
        return this.dWJ;
    }

    public ArrayList getMeshes() {
        return this.dWK;
    }

    public EntitySprite getCompassMesh() {
        return this.dWL;
    }

    public ArrayList getLandMarkMeshes() {
        return this.dWM;
    }

    public void setContent(ArrayList arrayList) {
        this.ec.clear();
        this.ec.addAll(arrayList);
        this.aWG();
    }

    public void setCompassContent(aaj aaj2) {
        this.dWJ = aaj2;
        this.aWG();
    }

    public void setLandMarkContent(ArrayList arrayList) {
        this.dWI.clear();
        this.dWI.addAll(arrayList);
        this.aWG();
    }

    public void a(aaj aaj2) {
        this.dWI.add(aaj2);
        EntitySprite entitySprite = (EntitySprite)yW.FL().a(EntitySprite.it(), EntitySprite.class);
        GLGeometrySprite gLGeometrySprite = new GLGeometrySprite();
        entitySprite.a(gLGeometrySprite);
        gLGeometrySprite.HF();
        this.dWM.add(entitySprite);
    }

    public void b(aaj aaj2) {
        int n2 = this.dWI.indexOf(aaj2);
        if (n2 != -1) {
            this.dWI.remove(n2);
            ((EntitySprite)this.dWM.remove(n2)).HF();
        }
        if (this.dWG == aaj2) {
            this.dWH = null;
            this.dWG = null;
        }
    }

    public void aWF() {
        this.dWI.clear();
        for (int j = this.dWM.size() - 1; j >= 0; --j) {
            ((EntitySprite)this.dWM.remove(j)).HF();
        }
        this.dWH = null;
        this.dWG = null;
    }

    private void aWG() {
        GLGeometrySprite gLGeometrySprite;
        EntitySprite entitySprite;
        int n2;
        int n3;
        int n4 = n3 = this.ec != null ? this.ec.size() : 0;
        while (this.dWK.size() > n3) {
            ((EntitySprite)this.dWK.remove(this.dWK.size() - 1)).HF();
        }
        for (n2 = this.dWK.size(); n2 < n3; ++n2) {
            entitySprite = (EntitySprite)yW.FL().a(EntitySprite.it(), EntitySprite.class);
            gLGeometrySprite = new GLGeometrySprite();
            entitySprite.a(gLGeometrySprite);
            gLGeometrySprite.HF();
            this.dWK.add(entitySprite);
        }
        if (this.dWJ == null && this.dWL != null) {
            this.dWL.HF();
            this.dWL = null;
        } else if (this.dWJ != null && this.dWL == null) {
            this.dWL = (EntitySprite)yW.FL().a(EntitySprite.it(), EntitySprite.class);
            GLGeometrySprite gLGeometrySprite2 = new GLGeometrySprite();
            this.dWL.a(gLGeometrySprite2);
            gLGeometrySprite2.HF();
        }
        int n5 = n3 = this.dWI != null ? this.dWI.size() : 0;
        while (this.dWM.size() > n3) {
            ((EntitySprite)this.dWM.remove(this.dWM.size() - 1)).HF();
        }
        for (n2 = this.dWM.size(); n2 < n3; ++n2) {
            entitySprite = (EntitySprite)yW.FL().a(EntitySprite.it(), EntitySprite.class);
            gLGeometrySprite = new GLGeometrySprite();
            entitySprite.a(gLGeometrySprite);
            gLGeometrySprite.HF();
            this.dWM.add(entitySprite);
        }
    }

    private ef_1 getMeshTexture(String string) {
        ef_1 ef_12 = (ef_1)this.aPu.get(string);
        if (ef_12 != null) {
            return ef_12;
        }
        ef_12 = this.iL(string);
        this.aPu.put(string, ef_12);
        return ef_12;
    }

    private ef_1 iL(String string) {
        return cx_0.JY().a(arX.cQT.iE(), ej_0.aa(string), string, new adz_1(), false);
    }

    public adg_2 getWidget(int n2, int n3) {
        if (this.czc || ago_2.getInstance().isMovePointMode()) {
            return null;
        }
        return super.getWidget(n2, n3);
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return zb != null;
    }

    public void setOnMapClick(aue_0 aue_02) {
        this.a(qe_1.bFq, aue_02, true);
    }

    public void setOnMapDoubleClick(ahF ahF2) {
        this.a(qe_1.bFr, ahF2, true);
    }

    public void setOnMapMove(aky_2 aky_22) {
        this.dWF = true;
        this.a(qe_1.bFs, aky_22, true);
    }

    public aaj getOverItem() {
        return this.dWG;
    }

    public EntitySprite getOverMesh() {
        return this.dWH;
    }

    public float getLandMarkZoom() {
        return this.dWO;
    }

    public void setLandMarkZoom(float f) {
        this.dWO = f;
    }

    public boolean isUseAlternateTexture() {
        return this.dWP;
    }

    public void setUseAlternateTexture(boolean bl2) {
        this.dWP = bl2;
    }

    protected void aWH() {
        this.dWN = true;
    }

    private boolean a(EntitySprite entitySprite, int n2, int n3) {
        if (entitySprite == null) {
            return false;
        }
        int n4 = n2 - this.cLZ.getLeftInset();
        int n5 = n3 - this.cLZ.getBottomInset();
        return this.getAppearance().aY(n4, n5) && this.isHit(entitySprite, n2, n3);
    }

    public aaj getItemUnderMouse(ArrayList arrayList, ArrayList arrayList2, int n2, int n3) {
        int n4 = this.b(arrayList, n2, n3);
        if (n4 < 0 || n4 >= arrayList2.size()) {
            return null;
        }
        return (aaj)arrayList2.get(n4);
    }

    private int b(ArrayList arrayList, int n2, int n3) {
        for (int j = arrayList.size() - 1; j >= 0; --j) {
            EntitySprite entitySprite = (EntitySprite)arrayList.get(j);
            if (!this.a(entitySprite, n2, n3)) continue;
            return j;
        }
        return -1;
    }

    private boolean a(EntitySprite entitySprite, aaj aaj2, int n2, int n3) {
        String string = aaj2.getName();
        if (string == null) {
            return false;
        }
        int n4 = (int)entitySprite.HC() + this.getDisplayX();
        int n5 = (int)entitySprite.HD() + this.getDisplayY();
        this.dWt.s(this);
        int n6 = 0;
        int n7 = 0;
        switch (this.dWs) {
            case dzv: {
                n7 = -((int)entitySprite.HB()) * 2;
                break;
            }
            case dzw: {
                n6 = (int)entitySprite.HA();
                n7 = -((int)entitySprite.HB()) * 2;
                break;
            }
            case dzx: {
                n6 = (int)entitySprite.HA() * 2;
                n7 = -((int)entitySprite.HB()) * 2;
                break;
            }
            case dzz: {
                n7 = -((int)entitySprite.HB());
                break;
            }
            case dzt: {
                n6 = (int)entitySprite.HA();
                break;
            }
            case dzs: {
                n6 = (int)entitySprite.HA() * 2;
                break;
            }
            case dzy: {
                n7 = -((int)entitySprite.HB());
            }
        }
        add_1.aOG().a(this.dWs);
        add_1.aOG().a(string, n4, n5, Integer.MAX_VALUE, n6, n7);
        return true;
    }

    protected void a(aaj aaj2, EntitySprite entitySprite) {
    }

    protected void b(aaj aaj2, EntitySprite entitySprite) {
    }

    protected void a(int n2, aaj aaj2, EntitySprite entitySprite) {
    }

    protected void cb(int n2, int n3) {
    }

    public Point getIsoMousePosition() {
        int n2 = awS.aJG().getX() - this.getScreenX();
        int n3 = awS.aJG().getY() - this.getScreenY();
        double d = this.cuD.i(this.dWw, this.dWx) + (double)n2 - (double)this.cLZ.getLeftInset() - (double)(this.cLZ.getContentWidth() / 2);
        double d2 = this.cuD.j(this.dWw, this.dWx) + (double)n3 - (double)this.cLZ.getBottomInset() - (double)(this.cLZ.getContentHeight() / 2);
        double d3 = this.cuD.k(d, d2);
        double d4 = this.cuD.l(d, d2);
        return new Point((int)d3, (int)d4);
    }

    public void aWI() {
        int n2;
        int n3 = awS.aJG().getX() - this.getScreenX();
        int n4 = this.b(this.dWK, n3, n2 = awS.aJG().getY() - this.getScreenY());
        if (n4 >= 0 && n4 < this.ec.size()) {
            this.dWH = (EntitySprite)this.dWK.get(n4);
            this.dWG = (aaj)this.ec.get(n4);
        }
        if (this.dWH == null && this.a(this.dWL, n3, n2)) {
            this.dWH = this.dWL;
            this.dWG = this.dWJ;
        }
        if (this.dWH == null && (n4 = this.b(this.dWM, n3, n2)) >= 0 && n4 < this.dWI.size()) {
            this.dWH = (EntitySprite)this.dWM.get(n4);
            this.dWG = (aaj)this.dWI.get(n4);
        }
    }

    void g(abd_1 abd_12) {
        int n2 = abd_12.p(this);
        int n3 = abd_12.q(this);
        EntitySprite entitySprite = null;
        aaj aaj2 = null;
        int n4 = this.b(this.dWK, n2, n3);
        if (n4 >= 0 && n4 < this.ec.size()) {
            entitySprite = (EntitySprite)this.dWK.get(n4);
            aaj2 = (aaj)this.ec.get(n4);
        }
        if (entitySprite == null && this.a(this.dWL, n2, n3)) {
            entitySprite = this.dWL;
            aaj2 = this.dWJ;
        }
        if (entitySprite == null && (n4 = this.b(this.dWM, n2, n3)) >= 0 && n4 < this.dWI.size()) {
            entitySprite = (EntitySprite)this.dWM.get(n4);
            aaj2 = (aaj)this.dWI.get(n4);
        }
        if (this.dWv && entitySprite != null && aaj2 != null) {
            this.a(entitySprite, aaj2, n2, n3);
        } else if (this.dWG != aaj2) {
            add_1.aOG().aPa();
        }
        if (this.dWG != aaj2) {
            if (this.dWG != null && this.dWH != null) {
                this.b(this.dWG, this.dWH);
                this.f(bx_0.a(abd_12, this, qe_1.bFk, this.dWG, this.dWH));
                this.dWG = null;
                this.dWH.HF();
                this.dWH = null;
            }
            if (aaj2 != null && entitySprite != null) {
                this.a(aaj2, entitySprite);
                this.f(bx_0.a(abd_12, this, qe_1.bFl, aaj2, entitySprite));
                this.dWG = aaj2;
                this.dWH = entitySprite;
                this.dWH.HE();
            }
        }
        double d = this.cuD.i(this.dWw, this.dWx) + (double)n2 - (double)this.cLZ.getLeftInset() - (double)(this.cLZ.getContentWidth() / 2);
        double d2 = this.cuD.j(this.dWw, this.dWx) + (double)n3 - (double)this.cLZ.getBottomInset() - (double)(this.cLZ.getContentHeight() / 2);
        double d3 = this.cuD.k(d, d2);
        double d4 = this.cuD.l(d, d2);
        this.cb((int)d3, (int)d4);
        if (this.dWF) {
            pb_2 pb_22 = pb_2.a(abd_12, (float)d3, (float)d4, this.dWG != null ? this.dWG.getValue() : null);
            pb_22.a(qe_1.bFs);
            this.f(pb_22);
        }
    }

    boolean h(abd_1 abd_12) {
        qe_1 qe_12;
        qe_1 qe_13;
        if (abd_12.aV() != qe_1.bFB && abd_12.aV() != qe_1.bFC) {
            return false;
        }
        int n2 = abd_12.p(this);
        int n3 = abd_12.q(this);
        if (abd_12.aV() == qe_1.bFB) {
            qe_13 = qe_1.bFi;
            qe_12 = qe_1.bFq;
        } else {
            qe_13 = qe_1.bFj;
            qe_12 = qe_1.bFr;
        }
        if (this.dWH != null) {
            bx_0 bx_02 = bx_0.a(abd_12, this, qe_13, this.dWG, this.dWH);
            this.f(bx_02);
            this.a(abd_12.getButton(), this.dWG, this.dWH);
        }
        double d = this.cuD.i(this.dWw, this.dWx) + (double)n2 - (double)this.cLZ.getLeftInset() - (double)(this.cLZ.getContentWidth() / 2);
        double d2 = this.cuD.j(this.dWw, this.dWx) + (double)n3 - (double)this.cLZ.getBottomInset() - (double)(this.cLZ.getContentHeight() / 2);
        double d3 = this.cuD.k(d, d2);
        double d4 = this.cuD.l(d, d2);
        pb_2 pb_22 = pb_2.a(abd_12, (float)d3, (float)d4, this.dWG != null ? this.dWG.getValue() : null);
        pb_22.a(qe_12);
        this.f(pb_22);
        return false;
    }

    private void ade() {
        acp_0 acp_02 = new acp_0(this);
        this.a(qe_1.bFt, acp_02, false);
        acJ acJ2 = new acJ(this);
        this.a(qe_1.bFB, acJ2, false);
        this.a(qe_1.bFC, acJ2, false);
    }

    private boolean isHit(EntitySprite entitySprite, int n2, int n3) {
        return entitySprite.Hw() <= (float)n2 && entitySprite.Hx() >= (float)n2 && entitySprite.Hz() <= (float)n3 && entitySprite.Hy() >= (float)n3;
    }

    protected float isoToScreenX(int n2, int n3, boolean bl2) {
        double d = this.cuD.i(bl2 ? (double)this.dWz : (double)this.dWw, bl2 ? (double)this.dWA : (double)this.dWx);
        double d2 = this.cuD.i(n2, n3);
        return (float)(d2 - d);
    }

    protected float isoToScreenY(int n2, int n3, boolean bl2) {
        double d = this.cuD.j(bl2 ? (double)this.dWz : (double)this.dWw, bl2 ? (double)this.dWA : (double)this.dWx);
        double d2 = this.cuD.j(n2, n3);
        return (float)(d2 - d);
    }

    private float b(float f, float f2, int n2) {
        assert (!Double.isNaN(f)) : "position is NaN";
        assert (!Double.isNaN(f2)) : "BadMoFo !!!";
        assert (n2 != 0) : "BadMoFo !!!";
        if (f == f2) {
            return f;
        }
        float f3 = f2 - f;
        float f4 = Math.abs(f3);
        if (f4 > (float)this.dWu) {
            return f2;
        }
        if (f4 > this.aaw) {
            return f + f3 / (float)n2;
        }
        return f;
    }

    public void j() {
        super.j();
        this.cFH.HF();
        this.cFH = null;
        this.ec.clear();
        this.dWI.clear();
        this.dWJ = null;
        this.dWt.j();
        this.dWt = null;
        this.dWG = null;
        if (this.dWH != null) {
            this.dWH.HF();
            this.dWH = null;
        }
        this.cuD = null;
        add_1.aOG().aPa();
    }

    public void b() {
        super.b();
        Zb zb = Zb.checkOut();
        zb.setWidget(this);
        this.a(zb);
        ei_1 ei_12 = ei_1.checkOut();
        this.a(ei_12);
        this.cFH = (EntityGroup)yW.FL().a(EntityGroup.it(), EntityGroup.class);
        this.cFH.aUM().a(new avz());
        this.dWt = new aNX();
        this.dWt.b();
        this.dWw = 0.0f;
        this.dWx = 0.0f;
        this.dWy = 0.0f;
        this.dWv = true;
        this.dWN = false;
        this.dWO = 1.0f;
        this.dWP = false;
        this.ade();
        this.aWJ();
        this.setNonBlocking(false);
    }

    public void invalidate() {
        super.invalidate();
    }

    public void validate() {
        super.validate();
        this.dWu = (int)((double)this.cLZ.getContentWidth() / this.cuD.aNy());
    }

    public boolean cb(int n2) {
        super.cb(n2);
        this.cuD.setVisible(false);
        this.cuD.P(this.cLZ.getContentWidth(), this.cLZ.getContentHeight());
        this.cuD.Ni().p(this.getDisplayX() + this.cLZ.getLeftInset());
        this.cuD.Ni().q(this.getDisplayY() + this.cLZ.getBottomInset());
        this.cuD.bI(n2);
        return true;
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        alp_2 alp_22 = (alp_2)air_12;
        alp_22.setIsoCenterX(this.getIsoCenterX());
        alp_22.setIsoCenterY(this.getIsoCenterY());
        alp_22.setIsoCenterZ(this.getIsoCenterZ());
        alp_22.setIsoMap(this.dWE);
        alp_22.setMaxZoom(this.getMaxZoom());
        alp_22.setMinSize(this.getMinSize());
        alp_22.setTooltipHotPoint(this.getTooltipHotPoint());
        alp_22.setZoomScale(this.getZoomScale());
        alp_22.setEnableTooltip(this.getEnableTooltip());
    }

    private void aWJ() {
        this.cuD = new aci_1(this);
        this.cuD.L(86.0 * (double)this.aaw);
        this.cuD.M(43.0 * (double)this.aaw);
        this.cuD.cg(true);
        this.cuD.ch(true);
        this.cuD.a(new amh_1());
        this.cuD.d(false);
        this.cuD.setVisible(false);
    }

    public void yx() {
        super.yx();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == dWS) {
            this.setIsoCenterX(Gr.getFloat(string));
        } else if (n2 == dWT) {
            this.setIsoCenterY(Gr.getFloat(string));
        } else if (n2 == dWU) {
            this.setIsoCenterZ(Gr.getFloat(string));
        } else if (n2 == dWV) {
            this.setIsoMap(Gr.getBoolean(string));
        } else if (n2 == dXa) {
            this.setOnMapClick((aue_0)if_12.c(aue_0.class, string));
        } else if (n2 == dXb) {
            this.setOnMapDoubleClick((ahF)if_12.c(ahF.class, string));
        } else if (n2 == dXc) {
            this.setOnMapMove((aky_2)if_12.c(aky_2.class, string));
        } else if (n2 == dWW) {
            this.setMaxZoom(Gr.getFloat(string));
        } else if (n2 == dWX) {
            this.setMinZoom(Gr.getFloat(string));
        } else if (n2 == dWY) {
            this.setTooltipHotPoint(aea_2.kW(string));
        } else if (n2 == dXd) {
            this.setEnableTooltip(Gr.getBoolean(string));
        } else if (n2 == dXe) {
            this.setLandMarkZoom(Gr.getFloat(string));
        } else if (n2 == dWZ) {
            this.setZoomScale(Gr.getFloat(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == ei) {
            this.setContent((ArrayList)object);
        } else if (n2 == dWQ) {
            this.setCompassContent((aaj)object);
        } else if (n2 == dWR) {
            this.setLandMarkContent((ArrayList)object);
        } else if (n2 == dWS) {
            this.setIsoCenterX(Gr.getFloat(object));
        } else if (n2 == dWT) {
            this.setIsoCenterY(Gr.getFloat(object));
        } else if (n2 == dWU) {
            this.setIsoCenterZ(Gr.getFloat(object));
        } else if (n2 == dWV) {
            this.setIsoMap(Gr.getBoolean(object));
        } else if (n2 == dWW) {
            this.setMaxZoom(Gr.getFloat(object));
        } else if (n2 == dWX) {
            this.setMinZoom(Gr.getFloat(object));
        } else if (n2 == dWY) {
            this.setTooltipHotPoint((aea_2)((Object)object));
        } else if (n2 == dXd) {
            this.setEnableTooltip(Gr.getBoolean(object));
        } else if (n2 == dWZ) {
            this.setZoomScale(Gr.getFloat(object));
        } else if (n2 == dXe) {
            this.setLandMarkZoom(Gr.getFloat(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ boolean a(alp_2 alp_22) {
        return alp_22.dWN;
    }

    static /* synthetic */ ArrayList b(alp_2 alp_22) {
        return alp_22.ec;
    }

    static /* synthetic */ float a(alp_2 alp_22, float f, float f2, int n2) {
        return alp_22.b(f, f2, n2);
    }

    static /* synthetic */ ArrayList c(alp_2 alp_22) {
        return alp_22.dWK;
    }

    static /* synthetic */ EntitySprite d(alp_2 alp_22) {
        return alp_22.dWL;
    }

    static /* synthetic */ ArrayList e(alp_2 alp_22) {
        return alp_22.dWI;
    }

    static /* synthetic */ ArrayList f(alp_2 alp_22) {
        return alp_22.dWM;
    }

    static /* synthetic */ void a(alp_2 alp_22, aba_2 aba_22, aaj aaj2, EntitySprite entitySprite, float f, float f2, float f3, float f4, boolean bl2, boolean bl3) {
        alp_22.a(aba_22, aaj2, entitySprite, f, f2, f3, f4, bl2, bl3);
    }

    static /* synthetic */ aaj g(alp_2 alp_22) {
        return alp_22.dWJ;
    }

    static /* synthetic */ boolean a(alp_2 alp_22, boolean bl2) {
        alp_22.dWN = bl2;
        return alp_22.dWN;
    }
}

