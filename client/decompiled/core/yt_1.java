/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.xulor2.component.mesh.GLTextGeometry;
import java.util.ArrayList;

/*
 * Renamed from Yt
 */
public abstract class yt_1
extends adg_2
implements aac,
ajb_0,
ayi,
wS {
    public static final String caz = "text";
    private ch_2 beX;
    protected Entity3D caA;
    protected boolean caB = true;
    protected String caC = null;
    private static final eu_2 caD = new eu_2();
    private static final agu_0 caE = new agu_0();
    private final ArrayList caF = new ArrayList();
    private float aaw;
    private float caG;
    private boolean caH = false;
    private boolean caI = false;
    public static final int cJ = "align".hashCode();
    public static final int caJ = "justify".hashCode();
    public static final int mh = "font".hashCode();
    public static final int caK = "brightenColor".hashCode();
    public static final int caL = "darkenColor".hashCode();
    public static final int caM = "horizontalAlignment".hashCode();
    public static final int caN = "verticalAlignment".hashCode();
    public static final int caO = "maxWidth".hashCode();
    public static final int caP = "minWidth".hashCode();
    public static final int caQ = "multiline".hashCode();
    public static final int caR = "orientation".hashCode();
    public static final int caS = "text".hashCode();
    public static final int caT = "enableShrinking".hashCode();
    public static final int caU = "useHighContrast".hashCode();
    public static final int caV = "displayCharDelay".hashCode();
    public static final int bBw = "zoom".hashCode();
    public static final int caW = "enableAutoZoomShrink".hashCode();

    public void a(Sm sm) {
        if (!this.caF.contains(sm)) {
            this.caF.add(sm);
        }
    }

    public void b(Sm sm) {
        if (this.caF.contains(sm)) {
            this.caF.remove(sm);
        }
    }

    public void amy() {
        GLTextGeometry gLTextGeometry = (GLTextGeometry)this.caA.ma(0);
        gLTextGeometry.bM(-1L);
    }

    protected void pX() {
        super.pX();
        this.arC.i(this.caA);
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return true;
    }

    public ch_2 getTextBuilder() {
        return this.beX;
    }

    protected void setTextBuilder(ch_2 ch_22) {
        this.beX = ch_22;
        GLTextGeometry gLTextGeometry = (GLTextGeometry)this.caA.ma(0);
        gLTextGeometry.setTextBuilder(ch_22);
    }

    public void setModulationColor(vP vP2) {
        GLTextGeometry gLTextGeometry = (GLTextGeometry)this.caA.ma(0);
        gLTextGeometry.setModulationColor(vP2);
    }

    public vP getModulationColor() {
        GLTextGeometry gLTextGeometry = (GLTextGeometry)this.caA.ma(0);
        return gLTextGeometry.getModulationColor();
    }

    public String getText() {
        return this.beX.mJ();
    }

    public void setText(String string) {
        if (string == null) {
            string = "";
        }
        if (this.caC != null || !string.equals(this.beX.mJ())) {
            this.caC = string;
            this.setNeedsToPreProcess();
        }
    }

    public void setZoomTween(float f, int n2) {
        this.q(aqz_0.class);
        this.a(new aqz_0(Float.valueOf(this.aaw), Float.valueOf(f), this, 0, n2, ys.aCq));
    }

    public void setTextImmediate(String string) {
        this.setText(string);
        this.ahX();
    }

    public void setBrightenColor(boolean bl2) {
        GLTextGeometry gLTextGeometry = (GLTextGeometry)this.caA.ma(0);
        gLTextGeometry.setBrightenColor(bl2);
    }

    public boolean getBrightenColor() {
        GLTextGeometry gLTextGeometry = (GLTextGeometry)this.caA.ma(0);
        return gLTextGeometry.Tn();
    }

    public void setDarkenColor(boolean bl2) {
        GLTextGeometry gLTextGeometry = (GLTextGeometry)this.caA.ma(0);
        gLTextGeometry.setDarkenColor(bl2);
    }

    public boolean getDarkenColor() {
        GLTextGeometry gLTextGeometry = (GLTextGeometry)this.caA.ma(0);
        return gLTextGeometry.To();
    }

    public void setUseHighContrast(boolean bl2) {
        this.beX.setUseHighContrast(bl2);
    }

    public boolean getUseHighContrast() {
        return this.beX.mI();
    }

    public void setColor(vP vP2, String string) {
        if (string == null || string.equalsIgnoreCase(caz)) {
            this.beX.e(vP2);
        }
    }

    public boolean getEnableAutoZoomShrink() {
        return this.caH;
    }

    public void setEnableAutoZoomShrink(boolean bl2) {
        this.caH = bl2;
        this.caI = true;
    }

    public void setAlign(BT bT) {
        if (bT.IB()) {
            this.setVerticalAlignment(BP.aJx);
        } else if (bT.IC()) {
            this.setVerticalAlignment(BP.aJy);
        } else {
            this.setVerticalAlignment(BP.aJB);
        }
        if (bT.ID()) {
            this.setHorizontalAlignment(BP.aJA);
        } else if (bT.IE()) {
            this.setHorizontalAlignment(BP.aJz);
        } else {
            this.setHorizontalAlignment(BP.aJB);
        }
    }

    public void setHorizontalAlignment(BP bP) {
        this.beX.b(bP);
    }

    public void setVerticalAlignment(BP bP) {
        this.beX.setVerticalAlignment(bP);
    }

    public void setOrientation(aiq_0 aiq_02) {
        this.beX.setOrientation(aiq_02);
        this.caB = true;
        this.setNeedsToPostProcess();
    }

    public aiq_0 getOrientation() {
        return this.beX.getOrientation();
    }

    public void setMultiline(boolean bl2) {
        this.beX.setMultiline(bl2);
    }

    public boolean getMultiline() {
        return this.beX.Jh();
    }

    public void setEnableShrinking(boolean bl2) {
        this.beX.setEnableShrinking(bl2);
    }

    public boolean getEnableShrinking() {
        return this.beX.Ji();
    }

    public void setMinWidth(int n2) {
        this.beX.setMinWidth(n2);
    }

    public int getMinWidth() {
        return this.beX.getMinWidth();
    }

    public void setMaxWidth(int n2) {
        this.beX.setMaxWidth(n2);
    }

    public int getMaxWidth() {
        return this.beX.getMaxWidth();
    }

    public void setFont(af_1 af_12) {
        this.beX.a(af_12);
    }

    public void setSize(int n2, int n3) {
        this.setTextWidgetSize(n2, n3, false);
    }

    public void setTextWidgetSize(int n2, int n3, boolean bl2) {
        super.setSize(n2, n3);
        if (this.caH) {
            agj_1 agj_12 = this.beX.Jo();
            float f = Math.min((float)n2 / (float)agj_12.width, (float)n3 / (float)agj_12.height);
            if (f < 1.0f && (double)Math.abs(this.caG - f) > 0.001) {
                this.caG = f;
                this.setNeedsToPostProcess();
            } else if (f >= 1.0f && this.caG != 1.0f) {
                this.caG = 1.0f;
                this.setNeedsToPostProcess();
            }
        }
        float f = this.getAppliedZoom();
        agj_1 agj_13 = this.cLZ.getContentSize();
        if (bl2) {
            this.beX.setMinWidth((int)((float)agj_13.width / f));
        }
        this.beX.setSize((int)Math.ceil((float)agj_13.width / f), (int)Math.ceil((float)agj_13.height / f));
    }

    private float getAppliedZoom() {
        return this.aaw != 1.0f ? this.aaw : this.caG;
    }

    public void setZoom(float f) {
        this.aaw = f;
        if (this.dxR != null) {
            this.dxR.Am();
        }
        this.setNeedsToPostProcess();
    }

    public agj_1 getContentMinSize() {
        agj_1 agj_12 = this.beX.getMinSize();
        agj_12.setWidth((int)((float)agj_12.width * this.aaw));
        agj_12.setHeight((int)((float)agj_12.height * this.aaw));
        return agj_12;
    }

    public void setDisplayCharDelay(long l2) {
        this.beX.bg(l2);
    }

    public void setJustify(boolean bl2) {
        this.beX.setJustify(bl2);
    }

    protected void ahX() {
        if (this.caC != null) {
            this.beX.aD(this.caC);
            this.amz();
            this.caC = null;
        }
    }

    public void amz() {
        ((GLTextGeometry)this.caA.ma(0)).Tp();
    }

    public void validate() {
        super.validate();
        if (this.beX.Jb().booleanValue()) {
            this.beX.Jc();
        }
        if (this.beX.Jq()) {
            this.beX.ba(true);
        }
        if (this.beX.Jr()) {
            this.beX.bb(true);
        }
    }

    public void invalidate() {
        super.invalidate();
        this.caB = true;
        this.setNeedsToPostProcess();
    }

    public boolean cc(int n2) {
        this.ahX();
        if (this.beX.Jp()) {
            this.beX.Jy();
            this.caB = true;
            this.setNeedsToPostProcess();
            if (this.dxR != null) {
                this.dxR.Am();
            }
        }
        return super.cc(n2);
    }

    public boolean gU(int n2) {
        boolean bl2 = super.gU(n2);
        if (this.beX.Jq() || this.beX.Jr()) {
            this.invalidate();
        }
        return bl2;
    }

    public boolean cb(int n2) {
        boolean bl2 = super.cb(n2);
        if (!this.caB) {
            return bl2;
        }
        agj_1 agj_12 = this.beX.getSize();
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        switch (this.getOrientation()) {
            case cxU: {
                f = this.cLZ.getLeftInset() + agj_12.width;
                f2 = this.cLZ.getBottomInset();
                f3 = 1.5707964f;
                break;
            }
            case cxW: {
                f = this.cLZ.getLeftInset();
                f2 = this.cLZ.getBottomInset();
                f3 = 0.0f;
                break;
            }
            case cxV: {
                f = this.cLZ.getLeftInset();
                f2 = this.cLZ.getBottomInset() + agj_12.height;
                f3 = -1.5707964f;
                break;
            }
            case cxX: {
                f = this.cLZ.getLeftInset();
                f2 = this.cLZ.getBottomInset() + agj_12.height;
                f3 = (float)Math.PI;
                break;
            }
            default: {
                assert (false) : "We should never end here";
                break;
            }
        }
        avz avz2 = (avz)this.caA.aUM().aI(0);
        avz2.m(this.getAppliedZoom(), this.getAppliedZoom(), 1.0f);
        this.caA.aUM().b(0, avz2);
        avz2 = (avz)this.caA.aUM().aI(1);
        avz2.e(f, f2, 0.0f);
        caE.d(0.0f, 0.0f, 1.0f);
        caD.a(caE, f3);
        avz2.f(caD);
        this.caA.aUM().b(1, avz2);
        this.caB = false;
        return bl2;
    }

    public void afh() {
        for (int j = this.caF.size() - 1; j >= 0; --j) {
            ((Sm)this.caF.get(j)).afh();
        }
    }

    public void j() {
        super.j();
        if (this.beX != null) {
            this.beX.clean();
            this.beX = null;
        }
        this.caF.clear();
        this.caA.a((ub_0)null);
        this.caA.b((ub_0)null);
        this.caA.HF();
        this.caA = null;
    }

    public void b() {
        super.b();
        assert (this.caA == null);
        this.caA = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
        avz avz2 = new avz();
        avz2.OH();
        this.caA.aUM().a(avz2);
        avz2 = new avz();
        avz2.OH();
        this.caA.aUM().a(avz2);
        this.caA.b(new GLTextGeometry());
        this.aaw = 1.0f;
        this.caG = 1.0f;
        this.caH = false;
        this.caI = false;
    }

    public void a(air_1 air_12) {
        yt_1 yt_12 = (yt_1)air_12;
        super.a((air_1)yt_12);
        yt_12.setText(this.beX.mJ());
        if (this.caC != null) {
            yt_12.caC = this.caC;
        }
        yt_12.setJustify(this.beX.Ja());
        yt_12.setMaxWidth(this.beX.getMaxWidth());
        yt_12.setMinWidth(this.beX.getMinWidth());
        yt_12.setEnableShrinking(this.getEnableShrinking());
        yt_12.setMultiline(this.getMultiline());
        yt_12.setOrientation(this.getOrientation());
        yt_12.setUseHighContrast(this.getUseHighContrast());
        yt_12.setBrightenColor(this.getBrightenColor());
        yt_12.setDarkenColor(this.getDarkenColor());
        yt_12.setZoom(this.aaw);
        if (this.caI) {
            yt_12.setEnableAutoZoomShrink(this.caH);
        }
    }

    public String toString() {
        return super.toString() + (this.beX != null ? " : " + this.getText() : "");
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == cJ) {
            this.setAlign(BT.dv(string));
        } else if (n2 == caJ) {
            this.setJustify(Gr.getBoolean(string));
        } else if (n2 == mh) {
            this.setFont(if_12.eP(string));
        } else if (n2 == caM) {
            this.setHorizontalAlignment(BP.dt(string));
        } else if (n2 == caK) {
            this.setBrightenColor(Gr.getBoolean(string));
        } else if (n2 == caL) {
            this.setDarkenColor(Gr.getBoolean(string));
        } else if (n2 == caN) {
            this.setVerticalAlignment(BP.dt(string));
        } else if (n2 == caO) {
            this.setMaxWidth(Gr.R(string));
        } else if (n2 == caP) {
            this.setMinWidth(Gr.R(string));
        } else if (n2 == caQ) {
            this.setMultiline(Gr.getBoolean(string));
        } else if (n2 == caR) {
            this.setOrientation(aiq_0.il(string));
        } else if (n2 == caS) {
            this.setText(if_12.eM(string));
        } else if (n2 == caU) {
            this.setUseHighContrast(Gr.getBoolean(string));
        } else if (n2 == caT) {
            this.setEnableShrinking(Gr.getBoolean(string));
        } else if (n2 == bBw) {
            this.setZoom(Gr.getFloat(string));
        } else if (n2 == caV) {
            this.setDisplayCharDelay(Gr.getLong(string));
        } else if (n2 == caW) {
            this.setEnableAutoZoomShrink(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == caS) {
            if (object == null) {
                this.setText(null);
            } else {
                this.setText(String.valueOf(object));
            }
        } else if (n2 == cJ) {
            this.setAlign((BT)((Object)object));
        } else if (n2 == caJ) {
            this.setJustify(Gr.getBoolean(object));
        } else if (n2 == mh) {
            this.setFont((vg_2)object);
        } else if (n2 == caK) {
            this.setBrightenColor(Gr.getBoolean(object));
        } else if (n2 == caL) {
            this.setDarkenColor(Gr.getBoolean(object));
        } else if (n2 == caM) {
            this.setHorizontalAlignment((BP)((Object)object));
        } else if (n2 == caN) {
            this.setVerticalAlignment((BP)((Object)object));
        } else if (n2 == caO) {
            this.setMaxWidth(Gr.R(object));
        } else if (n2 == caP) {
            this.setMinWidth(Gr.R(object));
        } else if (n2 == caQ) {
            this.setMultiline(Gr.getBoolean(object));
        } else if (n2 == caR) {
            this.setOrientation((aiq_0)((Object)object));
        } else if (n2 == caT) {
            this.setEnableShrinking(Gr.getBoolean(object));
        } else if (n2 == caU) {
            this.setUseHighContrast(Gr.getBoolean(object));
        } else if (n2 == bBw) {
            this.setZoom(Gr.getFloat(object));
        } else if (n2 == caV) {
            this.setDisplayCharDelay(Gr.getLong(object));
        } else if (n2 == caW) {
            this.setEnableAutoZoomShrink(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

