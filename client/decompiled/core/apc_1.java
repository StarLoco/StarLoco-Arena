/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aPc
 */
public class apc_1
extends aht_1
implements ayi {
    public static final String TAG = "progressBar";
    private vz_1 enn;
    private boolean ba = true;
    private float cOk = 1.0f;
    private float cOj = 1.5707964f;
    public static final float mb = 0.0f;
    public static final float mc = 1.0f;
    private float md = 0.0f;
    private float me = 1.0f;
    private float mf = 0.0f;
    private float eno = 0.0f;
    private float enp = 0.0f;
    private boolean enq = true;
    private boolean enr = true;
    private aao_2 ens;
    private boolean ent = false;
    private float[] enu = null;
    private ArrayList env = new ArrayList();
    private boolean enw = false;
    private ur_1 aHT = null;
    private long enx = -1L;
    public static final int ej = "horizontal".hashCode();
    public static final int eny = "fullCirclePercentage".hashCode();
    public static final int enz = "deltaAngle".hashCode();
    public static final int mi = "maxBound".hashCode();
    public static final int mj = "minBound".hashCode();
    public static final int enA = "displayType".hashCode();
    public static final int enB = "useIncreaseProgressTween".hashCode();
    public static final int enC = "useDecreaseProgressTween".hashCode();
    public static final int enD = "tweenType".hashCode();
    public static final int dL = "value".hashCode();
    public static final int enE = "initialValue".hashCode();
    public static final int enF = "inversed".hashCode();
    public static final int enG = "markers".hashCode();
    public static final int enH = "tweenDuration".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof ur_1) {
            this.setPixmap((ur_1)na_12);
        }
        super.a(na_12);
    }

    public void setPixmap(ur_1 ur_12) {
        if (ur_12 != this.aHT) {
            this.aHT = ur_12;
            this.setNeedsToPreProcess();
        }
    }

    public void invalidate() {
        this.enw = true;
        super.invalidate();
    }

    protected void pX() {
        super.pX();
        this.arC.i(this.enn.getEntity());
    }

    public String getTag() {
        return TAG;
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return zb instanceof aLu;
    }

    public aLu getAppearance() {
        return (aLu)this.cLZ;
    }

    public boolean getUseIncreaseProgressTween() {
        return this.enq;
    }

    public void setUseIncreaseProgressTween(boolean bl2) {
        this.enq = bl2;
    }

    public boolean getUseDecreaseProgressTween() {
        return this.enr;
    }

    public void setUseDecreaseProgressTween(boolean bl2) {
        this.enr = bl2;
    }

    public void setPixmaps(ur_1 ur_12, ur_1 ur_13, ur_1 ur_14, ur_1 ur_15, ur_1 ur_16, ur_1 ur_17, ur_1 ur_18, ur_1 ur_19, ur_1 ur_110) {
        if (this.ens == aao_2.dpf) {
            this.setDisplayType(aao_2.dpg);
        }
        this.enn.setPixmaps(ur_12.getPixmap(), ur_13.getPixmap(), ur_14.getPixmap(), ur_15.getPixmap(), ur_16.getPixmap(), ur_17.getPixmap(), ur_18.getPixmap(), ur_19.getPixmap(), ur_110.getPixmap());
        int n2 = ur_12.getPixmap().getWidth() + ur_14.getPixmap().getWidth();
        int n3 = ur_12.getPixmap().getHeight() + ur_18.getPixmap().getHeight();
        this.setMinSize(new agj_1(n2, n3));
    }

    public void setModulationColor(vP vP2) {
        if (this.enn != null) {
            this.enn.setModulationColor(vP2);
        }
        for (int j = this.env.size() - 1; j >= 0; --j) {
            ((azc_0)this.env.get(j)).setModulationColor(vP2);
        }
    }

    public vP getModulationColor() {
        if (this.enn != null) {
            return this.enn.getModulationColor();
        }
        return null;
    }

    public void setColor(vP vP2, String string) {
        if (string == null || string.equalsIgnoreCase(TAG)) {
            this.enn.setColor(vP2);
            this.setMinSize(new agj_1(0, 0));
        }
    }

    public float getMinBound() {
        return this.md;
    }

    public void setMinBound(float f) {
        this.md = f;
        if (this.mf < this.md) {
            this.mf = this.md;
        }
        this.aYO();
    }

    public float getMaxBound() {
        return this.me;
    }

    public void setMaxBound(float f) {
        this.me = f;
        if (this.mf > this.me) {
            this.mf = this.me;
        }
        this.aYO();
    }

    public float getPercentage() {
        return Math.max(0.0f, Math.min(1.0f, (this.mf - this.md) / (this.me - this.md)));
    }

    private float getVisualPercentage() {
        return Math.max(0.0f, Math.min(1.0f, (this.enp - this.md) / (this.me - this.md)));
    }

    public float getValue() {
        return this.mf;
    }

    public void setValue(float f) {
        if (this.mf == (f = ej_0.b(f, this.md, this.me))) {
            return;
        }
        boolean bl2 = this.enq && f > this.mf || this.enr && f < this.mf;
        this.q(yu_0.class);
        yu_0 yu_02 = new yu_0(this, this.ent ? this.me - this.mf : this.mf, this.ent ? this.me - f : f, this, 0, bl2 ? (int)(this.enx != -1L ? this.enx : 500L) : 0, ys.aCp);
        this.a(yu_02);
        this.mf = f;
    }

    public float getInitialValue() {
        return this.eno;
    }

    public void setInitialValue(float f) {
        this.mf = this.eno = f;
    }

    public float getFullCirclePercentage() {
        return this.cOk;
    }

    public void setFullCirclePercentage(float f) {
        this.cOk = f;
        if (this.enn != null) {
            this.enn.setFullCirclePercentage(f);
        }
    }

    public float getDeltaAngle() {
        return this.cOj;
    }

    public void setDeltaAngle(float f) {
        this.cOj = f;
        if (this.enn != null) {
            this.enn.setDeltaAngle(f);
        }
    }

    public aao_2 getDisplayType() {
        return this.ens;
    }

    public void setDisplayType(aao_2 aao_22) {
        if (this.ens != aao_22 || this.enn == null) {
            this.ens = aao_22;
            vP vP2 = null;
            vP vP3 = null;
            if (this.enn != null) {
                vP2 = this.enn.getColor();
                vP3 = this.enn.getModulationColor();
                this.enn.j();
            }
            this.enn = this.ens.aoG();
            this.enn.b();
            this.enn.setHorizontal(this.ba);
            this.enn.setColor(vP2);
            this.enn.setModulationColor(vP3);
            this.enn.setFullCirclePercentage(this.cOk);
            this.enn.setDeltaAngle(this.cOj);
        }
    }

    public boolean isHorizontal() {
        return this.ba;
    }

    public void setHorizontal(boolean bl2) {
        this.ba = bl2;
        if (this.enn != null) {
            this.enn.setHorizontal(bl2);
        }
    }

    public boolean isInversed() {
        return this.ent;
    }

    public void setInversed(boolean bl2) {
        this.ent = bl2;
    }

    public void setMarkers(float[] fArray) {
        if (this.enu != null && this.enu.equals(fArray)) {
            return;
        }
        this.enu = fArray;
        this.invalidate();
    }

    public void setTweenDuration(long l2) {
        this.enx = l2;
    }

    public void validate() {
        super.validate();
        this.aYO();
    }

    private void aYO() {
        if (this.enn != null) {
            int n2 = this.cLZ.getContentWidth();
            int n3 = this.cLZ.getContentHeight();
            int n4 = 0;
            float f = this.getVisualPercentage();
            if (this.ent) {
                n4 = this.cLZ.getLeftInset() + (int)((float)n2 * f) + 1;
                f = 1.0f - f;
            } else {
                n4 = this.cLZ.getLeftInset();
            }
            int n5 = this.cLZ.getBottomInset();
            if ("test".equals(this.rE)) {
                a.warn((Object)Float.valueOf(f));
            }
            this.enn.a(n4, n5, n2, n3, f);
        }
    }

    public boolean gU(int n2) {
        boolean bl2 = super.gU(n2);
        if (this.enw) {
            this.invalidate();
        }
        return bl2;
    }

    public void a(air_1 air_12) {
        apc_1 apc_12 = (apc_1)air_12;
        super.a(air_12);
        apc_12.setDisplayType(this.getDisplayType());
        apc_12.setUseIncreaseProgressTween(this.enq);
        apc_12.setUseDecreaseProgressTween(this.enr);
        apc_12.setHorizontal(this.ba);
        apc_12.setValue(this.mf);
        apc_12.setInitialValue(this.eno);
        apc_12.setMaxBound(this.me);
        apc_12.setMinBound(this.md);
        apc_12.setFullCirclePercentage(this.cOk);
        apc_12.setDeltaAngle(this.cOj);
        apc_12.aHT = this.aHT;
        apc_12.setInversed(this.ent);
        apc_12.setTweenDuration(this.enx);
        apc_12.setNeedsToPreProcess();
    }

    public void j() {
        super.j();
        this.enn.j();
        this.enn = null;
        this.env.clear();
        this.enw = false;
        this.aHT = null;
        this.enu = null;
        this.enx = -1L;
    }

    public void b() {
        super.b();
        aLu aLu2 = new aLu();
        aLu2.b();
        aLu2.setWidget(this);
        this.a(aLu2);
        abp_1 abp_12 = new abp_1(this, null);
        abp_12.b();
        this.a(abp_12);
        this.cOk = 1.0f;
        this.cOj = 1.5707964f;
        this.ba = true;
        this.enr = true;
        this.enq = true;
        this.ent = false;
        this.setDisplayType(aao_2.dpf);
        this.setNonBlocking(false);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(string));
        } else if (n2 == enz) {
            this.setDeltaAngle(Gr.getFloat(string));
        } else if (n2 == mi) {
            this.setMaxBound(Gr.getFloat(string));
        } else if (n2 == eny) {
            this.setFullCirclePercentage(Gr.getFloat(string));
        } else if (n2 == mj) {
            this.setMinBound(Gr.getFloat(string));
        } else if (n2 == enB) {
            this.setUseIncreaseProgressTween(Gr.getBoolean(string));
        } else if (n2 == enC) {
            this.setUseDecreaseProgressTween(Gr.getBoolean(string));
        } else if (n2 == dL) {
            this.setValue(Gr.getFloat(string));
        } else if (n2 == enE) {
            this.setInitialValue(Gr.getFloat(string));
        } else if (n2 == enA) {
            this.setDisplayType(aao_2.ke(string));
        } else if (n2 == enF) {
            this.setInversed(Gr.getBoolean(string));
        } else if (n2 == enH) {
            this.setTweenDuration(Gr.getLong(string));
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
        if (n2 == mi) {
            this.setMaxBound(Gr.getFloat(object));
            return true;
        } else if (n2 == mj) {
            this.setMinBound(Gr.getFloat(object));
            return true;
        } else if (n2 == dL) {
            this.setValue(Gr.getFloat(object));
            return true;
        } else if (n2 == enE) {
            this.setInitialValue(Gr.getFloat(object));
            return true;
        } else if (n2 == enF) {
            this.setInversed(Gr.getBoolean(object));
            return true;
        } else if (n2 == enG) {
            if (object != null && !(object instanceof float[])) return false;
            this.setMarkers((float[])object);
            return true;
        } else {
            if (n2 != enH) return super.setPropertyAttribute(n2, object);
            this.setTweenDuration(Gr.getLong(object));
        }
        return true;
    }

    static /* synthetic */ float a(apc_1 apc_12, float f) {
        apc_12.enp = f;
        return apc_12.enp;
    }

    static /* synthetic */ void a(apc_1 apc_12) {
        apc_12.aYO();
    }

    static /* synthetic */ boolean b(apc_1 apc_12) {
        return apc_12.enw;
    }

    static /* synthetic */ float[] c(apc_1 apc_12) {
        return apc_12.enu;
    }

    static /* synthetic */ ArrayList d(apc_1 apc_12) {
        return apc_12.env;
    }

    static /* synthetic */ ur_1 e(apc_1 apc_12) {
        return apc_12.aHT;
    }

    static /* synthetic */ boolean a(apc_1 apc_12, boolean bl2) {
        apc_12.enw = bl2;
        return apc_12.enw;
    }

    static /* synthetic */ boolean f(apc_1 apc_12) {
        return apc_12.ba;
    }
}

