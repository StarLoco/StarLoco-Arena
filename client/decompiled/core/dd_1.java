/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from dD
 */
public class dd_1
extends aht_1
implements ajb_0,
ayi,
wS {
    public static final String TAG = "progressText";
    public static final String lR = " / ";
    public static final String lS = "\u00af";
    public static final String lT = "%";
    private OE lU;
    private OE lV;
    private OE lW;
    private aiq_0 lX = aiq_0.cxW;
    private vP aZ = null;
    private af_1 lY = null;
    private boolean ba = true;
    private aaM lZ = aaM.cgF;
    private boolean ma = true;
    public static final float mb = 0.0f;
    public static final float mc = 1.0f;
    private float md = 0.0f;
    private float me = 1.0f;
    private float mf = 0.0f;
    public static final int mg = "displayValue".hashCode();
    public static final int mh = "font".hashCode();
    public static final int ej = "horizontal".hashCode();
    public static final int mi = "maxBound".hashCode();
    public static final int mj = "minBound".hashCode();
    public static final int mk = "splitText".hashCode();
    public static final int ml = "textOrientation".hashCode();
    public static final int dL = "value".hashCode();

    public String getTag() {
        return TAG;
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return zb instanceof auL;
    }

    public auL getAppearance() {
        return (auL)this.cLZ;
    }

    public void setTextOrientation(aiq_0 aiq_02) {
        this.lX = aiq_02;
        if (this.lU != null) {
            this.lU.setOrientation(aiq_02);
        }
        if (this.lV != null) {
            this.lV.setOrientation(aiq_02);
        }
        if (this.lW != null) {
            this.lW.setOrientation(aiq_02);
        }
    }

    public void setFont(af_1 af_12) {
        if (this.lU != null) {
            this.lU.setFont(af_12);
        }
        if (this.lV != null) {
            this.lV.setFont(af_12);
        }
        if (this.lW != null) {
            this.lW.setFont(af_12);
        }
        this.lY = af_12;
    }

    public void setModulationColor(vP vP2) {
        if (this.lU != null) {
            this.lU.setModulationColor(vP2);
        }
        if (this.lV != null) {
            this.lV.setModulationColor(vP2);
        }
        if (this.lW != null) {
            this.lW.setModulationColor(vP2);
        }
    }

    public vP getModulationColor() {
        if (this.lU != null) {
            return this.lU.getModulationColor();
        }
        return null;
    }

    public void setColor(vP vP2, String string) {
        if (string == null || string.equalsIgnoreCase("text")) {
            if (this.aZ == vP2) {
                return;
            }
            this.aZ = vP2;
            if (this.lU != null) {
                this.lU.setColor(this.aZ, null);
            }
            if (this.lV != null) {
                this.lV.setColor(this.aZ, null);
            }
            if (this.lW != null) {
                this.lW.setColor(this.aZ, null);
            }
        }
    }

    private void setText(String string) {
        this.setText(string, null);
    }

    private void setText(String string, String string2) {
        if (!(this.ma || string == null || string.equals("") || string2 == null || string2.equals(""))) {
            string = string + lR + string2;
            if (this.lV != null) {
                this.k(this.lV);
                this.lV = null;
            }
            if (this.lW != null) {
                this.k(this.lW);
                this.lW = null;
            }
        }
        if (string == null || string.equals("")) {
            if (this.lU != null) {
                this.k(this.lU);
                this.lU = null;
            }
            if (this.lV != null) {
                this.k(this.lV);
                this.lV = null;
            }
            if (this.lW != null) {
                this.k(this.lW);
                this.lW = null;
            }
            return;
        }
        if (this.lU == null) {
            this.lU = new OE();
            this.lU.b();
            this.a(this.lU);
            this.a((na_1)this.lU);
        }
        this.lU.setText(string);
        if (!this.ma) {
            return;
        }
        if (string2 != null && !string2.equals("")) {
            if (this.lV == null) {
                this.lV = new OE();
                this.lV.b();
                this.a(this.lV);
                this.a((na_1)this.lV);
                this.lV.setText(lS);
            }
            if (this.lW == null) {
                this.lW = new OE();
                this.lW.b();
                this.a(this.lW);
                this.a((na_1)this.lW);
            }
            this.lW.setText(string2);
        } else {
            if (this.lV != null) {
                this.k(this.lV);
                this.lV = null;
            }
            if (this.lW != null) {
                this.k(this.lW);
                this.lW = null;
            }
        }
    }

    private String getText() {
        if (this.lU != null) {
            return this.lU.getText();
        }
        return "";
    }

    public float getMinBound() {
        return this.md;
    }

    public void setMinBound(float f) {
        this.md = f;
        if (this.mf < this.md) {
            this.mf = this.md;
        }
        this.fT();
    }

    public float getMaxBound() {
        return this.me;
    }

    public void setMaxBound(float f) {
        this.me = f;
        if (this.mf > this.me) {
            this.mf = this.me;
        }
        this.fT();
    }

    public float getPercentage() {
        return (this.mf - this.md) / (this.me - this.md);
    }

    public float getValue() {
        return this.mf;
    }

    public void setValue(float f) {
        if (f < this.md) {
            f = this.md;
        }
        if (f > this.me) {
            f = this.me;
        }
        this.mf = f;
        this.fT();
    }

    public boolean isHorizontal() {
        return this.ba;
    }

    public void setHorizontal(boolean bl2) {
        if (this.ba != bl2) {
            this.ba = bl2;
        }
        this.Am();
    }

    public boolean getSplitText() {
        return this.ma;
    }

    public void setSplitText(boolean bl2) {
        this.ma = bl2;
    }

    public aaM getDisplayValue() {
        return this.lZ;
    }

    public void setDisplayValue(aaM aaM2) {
        this.lZ = aaM2;
        this.fT();
    }

    private void a(OE oE) {
        oE.setExpandable(false);
        oE.setAlign(BT.aJX);
        oE.setFont(this.lY);
        oE.setOrientation(this.lX);
        oE.setColor(this.aZ, null);
    }

    public void validate() {
        super.validate();
    }

    private void fT() {
        switch (this.lZ) {
            case cgC: {
                if ((double)this.mf - Math.floor(this.mf) != 0.0) {
                    this.setText(Float.toString(this.mf));
                    break;
                }
                this.setText(Integer.toString((int)this.mf));
                break;
            }
            case cgD: {
                this.setText(Integer.toString((int)(this.getPercentage() * 100.0f)) + lT);
                break;
            }
            case cgE: {
                String string = (double)this.mf - Math.floor(this.mf) != 0.0 ? Float.toString(this.mf) : Integer.toString((int)this.mf);
                String string2 = (double)this.me - Math.floor(this.me) != 0.0 ? Float.toString(this.me) : Integer.toString((int)this.me);
                this.setText(string, string2);
                break;
            }
            default: {
                this.setText("");
            }
        }
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        dd_1 dd_12 = (dd_1)air_12;
        dd_12.setHorizontal(this.ba);
        dd_12.setValue(this.mf);
        dd_12.setMaxBound(this.me);
        dd_12.setMinBound(this.md);
        dd_12.setSplitText(this.ma);
        dd_12.setTextOrientation(this.lX);
        dd_12.setText(this.getText(), this.lW != null ? this.lW.getText() : "");
    }

    public void j() {
        super.j();
        this.lX = null;
        this.lY = null;
        this.lU = null;
        this.lV = null;
        this.lW = null;
        this.aZ = null;
    }

    public void b() {
        super.b();
        auL auL2 = auL.checkOut();
        auL2.setWidget(this);
        this.a(auL2);
        xi_1 xi_12 = new xi_1(this);
        xi_12.b();
        this.a(xi_12);
        this.setNonBlocking(false);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == mg) {
            this.setDisplayValue(aaM.hf(string));
        } else if (n2 == mi) {
            this.setMaxBound(Gr.getFloat(string));
        } else if (n2 == mj) {
            this.setMinBound(Gr.getFloat(string));
        } else if (n2 == mh) {
            this.setFont(if_12.eP(string));
        } else if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(string));
        } else if (n2 == mk) {
            this.setSplitText(Gr.getBoolean(string));
        } else if (n2 == ml) {
            this.setTextOrientation(aiq_0.il(string));
        } else if (n2 == dL) {
            this.setValue(Gr.getFloat(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == mg) {
            this.setDisplayValue((aaM)((Object)object));
        } else if (n2 == mi) {
            this.setMaxBound(Gr.getFloat(object));
        } else if (n2 == mj) {
            this.setMinBound(Gr.getFloat(object));
        } else if (n2 == mh) {
            this.setFont((vg_2)object);
        } else if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(object));
        } else if (n2 == mk) {
            this.setSplitText(Gr.getBoolean(object));
        } else if (n2 == ml) {
            this.setTextOrientation((aiq_0)((Object)object));
        } else if (n2 == dL) {
            this.setValue(Gr.getFloat(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ boolean a(dd_1 dd_12) {
        return dd_12.ba;
    }

    static /* synthetic */ OE b(dd_1 dd_12) {
        return dd_12.lU;
    }

    static /* synthetic */ OE c(dd_1 dd_12) {
        return dd_12.lV;
    }

    static /* synthetic */ OE d(dd_1 dd_12) {
        return dd_12.lW;
    }
}

