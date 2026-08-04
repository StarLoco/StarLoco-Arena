/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from atg
 */
public class atg_0
extends aht_1
implements oc_0 {
    public static final String TAG = "Slider";
    public static final String cSW = "horizontalButton";
    public static final String cSX = "verticalButton";
    public static final String cSY = "horizontalSeparator";
    public static final String cSZ = "verticalSeparator";
    private float mf = 0.0f;
    private float cTa = 0.0f;
    private int cTb = 0;
    private int cTc = 1;
    private boolean cTd = true;
    private boolean cTe = true;
    private boolean cTf = false;
    private int cTg = 0;
    private boolean cTh = false;
    private boolean cTi = false;
    private boolean ba = true;
    private float cTj = 0.15f;
    private float cTk = 0.05f;
    private aqq_0 aDf;
    private ov_1 aDb = null;
    private ov_1 cwQ = null;
    private ArrayList cTl = new ArrayList();
    private ur_1 aHT = null;
    private vP AC = null;
    private boolean aHW = false;
    private boolean cTm = false;
    public static final int cTn = "readOnly".hashCode();
    public static final int cTo = "useTween".hashCode();
    public static final int ej = "horizontal".hashCode();
    public static final int mi = "maxBound".hashCode();
    public static final int mj = "minBound".hashCode();
    public static final int cTp = "jump".hashCode();
    public static final int cTq = "sliderSize".hashCode();
    public static final int dL = "value".hashCode();
    public static final int cTr = "numFixedValues".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof aqq_0 && this.aDf != na_12) {
            if (this.aDf != null) {
                this.k(this.aDf);
            }
            this.aDf = (aqq_0)na_12;
            this.aDf.setUsePositionTween(this.cTh && this.cTi);
        } else if (na_12 instanceof ur_1) {
            this.setPixmap((ur_1)na_12);
        }
        super.a(na_12);
    }

    public String getTag() {
        return TAG;
    }

    public float getValue() {
        return this.be(this.mf);
    }

    public void setValue(float f) {
        if (f == this.mf) {
            return;
        }
        int n2 = this.cTf ? 0 : this.cTb;
        int n3 = this.cTf ? this.cTg - 1 : this.cTc;
        this.cTa = f;
        if (f < (float)n2) {
            f = n2;
        } else if (f > (float)n3) {
            f = n3;
        }
        this.mf = f;
        Kf kf = new Kf(this);
        kf.setValue(f);
        this.f(kf);
        this.cTd = true;
        this.setNeedsToMiddleProcess();
    }

    public void setNumFixedValues(int n2) {
        assert (n2 >= 0) : "Invalid value. numFixedValues must be >= 0";
        if (n2 == this.cTg) {
            return;
        }
        this.cTg = n2;
        this.cTf = this.cTg != 0;
        this.cTm = true;
        if (this.cTf) {
            this.setValue(this.cTa);
        }
    }

    public int getMinBound() {
        return this.cTb;
    }

    public void setMinBound(int n2) {
        this.cTb = n2;
    }

    public int getMaxBound() {
        return this.cTc;
    }

    public void setMaxBound(int n2) {
        this.cTc = n2;
    }

    public double getJump() {
        return this.cTk;
    }

    public void setJump(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        } else if (f > 1.0f) {
            f = 1.0f;
        }
        this.cTk = f;
    }

    public boolean isHorizontal() {
        return this.ba;
    }

    public void setHorizontal(boolean bl2) {
        this.ba = bl2;
    }

    public double getSliderSize() {
        return this.cTj;
    }

    public void setSliderSize(float f) {
        this.cTj = f = Math.min(0.99f, Math.max(f, 0.01f));
        this.cTe = true;
        this.setNeedsToMiddleProcess();
    }

    public aqq_0 getButton() {
        return this.aDf;
    }

    public adg_2 getWidgetByThemeElementName(String string, boolean bl2) {
        if ((this.ba || bl2) && cSW.equalsIgnoreCase(string)) {
            return this.aDf;
        }
        if ((!this.ba || bl2) && cSX.equalsIgnoreCase(string)) {
            return this.aDf;
        }
        return null;
    }

    public void setEnabled(boolean bl2) {
        super.setEnabled(bl2);
        this.aDf.setEnabled(bl2);
    }

    public boolean getReadOnly() {
        return this.cTh;
    }

    public void setReadOnly(boolean bl2) {
        this.cTh = bl2;
        this.aDf.setUsePositionTween(this.cTh && this.cTi);
    }

    public void setUseTween(boolean bl2) {
        this.cTi = bl2;
        this.aDf.setUsePositionTween(this.cTh && this.cTi);
    }

    public void setPixmap(ur_1 ur_12) {
        if (ur_12 != this.aHT) {
            this.aHT = ur_12;
            this.aHW = true;
            this.setNeedsToPreProcess();
        }
    }

    public void setModulationColor(vP vP2) {
        if (this.AC == vP2) {
            return;
        }
        this.AC = vP2;
        for (int j = this.cTl.size() - 1; j >= 0; --j) {
            ((azc_0)this.cTl.get(j)).setModulationColor(vP2);
        }
    }

    public vP getModulationColor() {
        return this.AC;
    }

    private void aGc() {
        if (this.cTg <= 1 || !this.cTf || this.cTl.size() <= 0) {
            return;
        }
        if (this.ba) {
            int n2 = ((azc_0)this.cTl.get(0)).getWidth();
            int n3 = this.aDf.getWidth() / 2 - n2 / 2;
            float f = (float)(this.getAppearance().getContentWidth() - this.aDf.getWidth()) / (float)(this.cTg - 1);
            for (int j = 0; j < this.cTl.size(); ++j) {
                azc_0 azc_02 = (azc_0)this.cTl.get(j);
                azc_02.setPosition((int)(f * (float)j + (float)n3), 0);
            }
        } else {
            int n4 = ((azc_0)this.cTl.get(0)).getHeight();
            int n5 = this.aDf.getHeight() / 2 - n4 / 2;
            float f = (float)(this.getAppearance().getContentHeight() - this.aDf.getHeight()) / (float)(this.cTg - 1);
            for (int j = 0; j < this.cTl.size(); ++j) {
                azc_0 azc_03 = (azc_0)this.cTl.get(j);
                azc_03.setPosition(0, (int)(f * (float)j + (float)n5));
            }
        }
        this.cTm = false;
        this.setNeedsToMiddleProcess();
    }

    public void invalidate() {
        this.cTe = true;
        this.cTd = true;
        this.cTm = true;
        this.setNeedsToMiddleProcess();
        super.invalidate();
    }

    private float be(float f) {
        int n2 = this.cTf ? 0 : this.cTb;
        int n3 = this.cTf ? this.cTg - 1 : this.cTc;
        float f2 = f * (float)(n3 - n2) + (float)n2;
        if (this.cTf) {
            f2 = Math.round(f2);
        }
        return f2;
    }

    private float bf(float f) {
        int n2 = this.cTf ? 0 : this.cTb;
        int n3 = this.cTf ? this.cTg - 1 : this.cTc;
        return (f - (float)n2) / (float)(n3 - n2);
    }

    public void aGd() {
        int n2;
        int n3;
        agj_1 agj_12 = this.aDf.getPrefSize();
        if (this.ba) {
            n3 = this.getAppearance().getContentHeight();
            n2 = Math.max((int)((float)this.getAppearance().getContentWidth() * this.cTj), agj_12.width);
        } else {
            n2 = this.getAppearance().getContentWidth();
            n3 = Math.max((int)((float)this.getAppearance().getContentHeight() * this.cTj), agj_12.height);
        }
        this.aDf.setSize(n2, n3);
        if (this.cTl != null && this.cTl.size() > 0) {
            agj_1 agj_13 = ((azc_0)this.cTl.get(0)).getPrefSize();
            int n4 = this.ba ? agj_13.width : n2;
            int n5 = this.ba ? n3 : agj_13.height;
            for (azc_0 azc_02 : this.cTl) {
                azc_02.setSize(n4, n5);
            }
        }
        this.cTe = false;
        this.setNeedsToMiddleProcess();
    }

    public void aGe() {
        int n2;
        int n3;
        if (this.ba) {
            n3 = 0;
            n2 = (int)(this.bf(this.mf) * (float)(this.getAppearance().getContentWidth() - this.aDf.getWidth()));
        } else {
            n2 = 0;
            n3 = (int)(this.bf(this.mf) * (float)(this.getAppearance().getContentHeight() - this.aDf.getHeight()));
        }
        this.aDf.setPosition(n2, n3);
        this.cTd = false;
        this.setNeedsToMiddleProcess();
    }

    public void aGf() {
        this.aDb = new agf_1(this);
        this.a(qe_1.bFz, this.aDb, false);
        this.cwQ = new agg_2(this);
        this.a(qe_1.bFv, this.cwQ, true);
    }

    public void j() {
        super.j();
        this.aDf = null;
        this.cTl.clear();
        this.cTe = false;
        this.aHT = null;
        this.cTm = false;
    }

    public void b() {
        super.b();
        dd dd2 = new dd(this, null);
        dd2.b();
        this.a(dd2);
        this.aDf = new aqq_0();
        this.aDf.b();
        this.aDf.setCanBeCloned(false);
        this.a(this.aDf);
        this.dyc = false;
        this.aGf();
    }

    public boolean gU(int n2) {
        boolean bl2 = super.gU(n2);
        if (this.cTe || this.cTd) {
            this.invalidate();
        }
        return bl2;
    }

    public void a(air_1 air_12) {
        atg_0 atg_02 = (atg_0)air_12;
        super.a((air_1)atg_02);
        atg_02.mf = this.mf;
        atg_02.cTb = this.cTb;
        atg_02.cTc = this.cTc;
        atg_02.cTf = this.cTf;
        atg_02.cTg = this.cTg;
        atg_02.cTa = this.cTa;
        atg_02.ba = this.ba;
        atg_02.cTk = this.cTk;
        atg_02.cTj = this.cTj;
        atg_02.cTl = this.cTl;
        atg_02.setReadOnly(this.cTh);
        atg_02.setUseTween(this.cTi);
        atg_02.setModulationColor(this.AC);
        atg_02.b(qe_1.bFz, this.aDb, true);
        atg_02.b(qe_1.bFv, this.cwQ, true);
        atg_02.dyg = true;
        atg_02.setNeedsToPreProcess();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(string));
        } else if (n2 == cTn) {
            this.setReadOnly(Gr.getBoolean(string));
        } else if (n2 == cTo) {
            this.setUseTween(Gr.getBoolean(string));
        } else if (n2 == mi) {
            this.setMaxBound(Gr.R(string));
        } else if (n2 == mj) {
            this.setMinBound(Gr.R(string));
        } else if (n2 == cTp) {
            this.setJump(Gr.getFloat(string));
        } else if (n2 == cTq) {
            this.setSliderSize(Gr.getFloat(string));
        } else if (n2 == dL) {
            this.setValue(Gr.getFloat(string));
        } else if (n2 == cTr) {
            this.setNumFixedValues(Gr.R(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(object));
        } else if (n2 == cTn) {
            this.setReadOnly(Gr.getBoolean(object));
        } else if (n2 == cTo) {
            this.setUseTween(Gr.getBoolean(object));
        } else if (n2 == mi) {
            this.setMaxBound(Gr.R(object));
        } else if (n2 == mj) {
            this.setMinBound(Gr.R(object));
        } else if (n2 == cTp) {
            this.setJump(Gr.getFloat(object));
        } else if (n2 == cTq) {
            this.setSliderSize(Gr.getFloat(object));
        } else if (n2 == dL) {
            this.setValue(Gr.getFloat(object));
        } else if (n2 == cTr) {
            this.setNumFixedValues(Gr.R(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ boolean a(atg_0 atg_02) {
        return atg_02.aHW;
    }

    static /* synthetic */ boolean a(atg_0 atg_02, boolean bl2) {
        atg_02.aHW = bl2;
        return atg_02.aHW;
    }

    static /* synthetic */ boolean b(atg_0 atg_02) {
        return atg_02.cTm;
    }

    static /* synthetic */ int c(atg_0 atg_02) {
        return atg_02.cTg;
    }

    static /* synthetic */ ArrayList d(atg_0 atg_02) {
        return atg_02.cTl;
    }

    static /* synthetic */ vP e(atg_0 atg_02) {
        return atg_02.AC;
    }

    static /* synthetic */ ur_1 f(atg_0 atg_02) {
        return atg_02.aHT;
    }

    static /* synthetic */ aqq_0 g(atg_0 atg_02) {
        return atg_02.aDf;
    }

    static /* synthetic */ void h(atg_0 atg_02) {
        atg_02.aGc();
    }

    static /* synthetic */ boolean b(atg_0 atg_02, boolean bl2) {
        atg_02.cTm = bl2;
        return atg_02.cTm;
    }

    static /* synthetic */ boolean i(atg_0 atg_02) {
        return atg_02.cTe;
    }

    static /* synthetic */ boolean j(atg_0 atg_02) {
        return atg_02.cTd;
    }

    static /* synthetic */ boolean k(atg_0 atg_02) {
        return atg_02.cTh;
    }

    static /* synthetic */ boolean l(atg_0 atg_02) {
        return atg_02.cTf;
    }

    static /* synthetic */ float m(atg_0 atg_02) {
        return atg_02.cTk;
    }

    static /* synthetic */ boolean n(atg_0 atg_02) {
        return atg_02.ba;
    }

    static /* synthetic */ float o(atg_0 atg_02) {
        return atg_02.mf;
    }

    static /* synthetic */ float a(atg_0 atg_02, float f) {
        return atg_02.bf(f);
    }

    static /* synthetic */ float b(atg_0 atg_02, float f) {
        return atg_02.be(f);
    }
}

