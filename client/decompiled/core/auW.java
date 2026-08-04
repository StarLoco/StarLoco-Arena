/*
 * Decompiled with CFR 0.152.
 */
public class auW
extends dz_2 {
    public static final String TAG = "StaticLayoutData";
    public static final String aTJ = "sld";
    private agj_1 cXC;
    private ajn_1 cXD;
    private int aG = 0;
    private int aH = 0;
    private auC cXE;
    private auC cXF;
    private int bsW = 0;
    private int bsX = 0;
    private boolean cXG = false;
    private boolean cXH = false;
    private boolean cXI = false;
    private boolean cXJ = false;
    private boolean agi = false;
    private boolean agj = true;
    private adg_2 cXK = null;
    private String cXL = null;
    private boolean cXM = false;
    public static final int cJ = "align".hashCode();
    public static final int bBo = "alignment".hashCode();
    public static final int cXN = "cascadeMethodEnabled".hashCode();
    public static final int cXO = "resizeOnce".hashCode();
    public static final int agl = "initValue".hashCode();
    public static final int cXP = "size".hashCode();
    public static final int cXQ = "usable".hashCode();
    public static final int ars = "x".hashCode();
    public static final int ciR = "xOffset".hashCode();
    public static final int cXR = "xPerc".hashCode();
    public static final int art = "y".hashCode();
    public static final int ciS = "yOffset".hashCode();
    public static final int cXS = "yPerc".hashCode();

    public String getTag() {
        return TAG;
    }

    public boolean isInitValue() {
        return this.agi;
    }

    public void setInitValue(boolean bl2) {
        this.agi = bl2;
        this.agj = true;
    }

    public void setResizeOnce(boolean bl2) {
        this.setInitValue(bl2);
    }

    public boolean isAutoPositionable() {
        return this.cXK != null;
    }

    public boolean isCascadePositionable() {
        return this.cXM;
    }

    public adg_2 getReferentWidget() {
        return this.cXK;
    }

    public void setReferentWidget(adg_2 adg_22) {
        this.cXK = adg_22;
    }

    public void setCascadeMethodEnabled(boolean bl2) {
        this.cXM = bl2;
    }

    public String getControlGroup() {
        return this.cXL;
    }

    public void setControlGroup(String string) {
        this.cXL = string;
    }

    public int getX() {
        return this.aG;
    }

    public auC getXPerc() {
        return this.cXE;
    }

    public void setXPerc(auC auC2) {
        this.cXE = auC2;
    }

    public auC getYPerc() {
        return this.cXF;
    }

    public void setYPerc(auC auC2) {
        this.cXF = auC2;
    }

    public void setX(int n2) {
        this.cXG = true;
        this.aG = n2;
    }

    public boolean isXInit() {
        return this.cXG;
    }

    public int getY() {
        return this.aH;
    }

    public void setY(int n2) {
        this.cXH = true;
        this.aH = n2;
    }

    public boolean isYInit() {
        return this.cXH;
    }

    public int getXOffset() {
        return this.bsW;
    }

    public void setXOffset(int n2) {
        this.cXI = true;
        this.bsW = n2;
    }

    public int getYOffset() {
        return this.bsX;
    }

    public void setYOffset(int n2) {
        this.cXJ = true;
        this.bsX = n2;
    }

    public boolean isXOffsetInit() {
        return this.cXI;
    }

    public boolean isYOffsetInit() {
        return this.cXJ;
    }

    public agj_1 getSize() {
        return this.cXC;
    }

    public void setSize(agj_1 agj_12) {
        this.cXC = agj_12;
    }

    public ajn_1 getAlignment() {
        return this.cXD;
    }

    public void setAlignment(ajn_1 ajn_12) {
        this.cXD = ajn_12;
    }

    public void setAlign(ajn_1 ajn_12) {
        this.cXD = ajn_12;
    }

    public void setUsable(boolean bl2) {
        this.agj = bl2;
    }

    public boolean isUsable() {
        return !this.agi || this.agj;
    }

    public void b() {
        super.b();
        this.aG = 0;
        this.aH = 0;
        this.bsW = 0;
        this.bsX = 0;
        this.cXG = false;
        this.cXH = false;
        this.cXI = false;
        this.cXJ = false;
        this.agi = false;
        this.cXM = false;
        this.agj = true;
    }

    public void j() {
        super.j();
        this.cXK = null;
        this.cXC = null;
        this.cXD = null;
        this.cXL = null;
        this.cXE = null;
        this.cXF = null;
    }

    public void a(air_1 air_12) {
        auW auW2 = (auW)air_12;
        super.a((air_1)auW2);
        auW2.cXD = this.cXD;
        if (this.cXC != null) {
            auW2.cXC = (agj_1)this.cXC.clone();
        }
        if (this.cXG) {
            auW2.setX(this.aG);
        }
        if (this.cXH) {
            auW2.setY(this.aH);
        }
        if (this.cXI) {
            auW2.setXOffset(this.bsW);
        }
        if (this.cXJ) {
            auW2.setYOffset(this.bsX);
        }
        if (this.cXE != null) {
            auW2.setXPerc((auC)this.cXE.clone());
        }
        if (this.cXF != null) {
            auW2.setYPerc((auC)this.cXF.clone());
        }
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == cJ || n2 == bBo) {
            this.setAlign(ajn_1.lz(string));
        } else if (n2 == cXN) {
            this.setCascadeMethodEnabled(Gr.getBoolean(string));
        } else if (n2 == cXO || n2 == agl) {
            this.setInitValue(Gr.getBoolean(string));
        } else if (n2 == cXP) {
            this.setSize(if_12.eL(string));
        } else if (n2 == cXQ) {
            this.setUsable(Gr.getBoolean(string));
        } else if (n2 == ars) {
            this.setX(Gr.R(string));
        } else if (n2 == ciR) {
            this.setXOffset(Gr.R(string));
        } else if (n2 == cXR) {
            this.setXPerc(if_12.eQ(string));
        } else if (n2 == art) {
            this.setY(Gr.R(string));
        } else if (n2 == ciS) {
            this.setYOffset(Gr.R(string));
        } else if (n2 == cXS) {
            this.setYPerc(if_12.eQ(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == cXR) {
            this.setXPerc((auC)object);
        } else if (n2 == cXS) {
            this.setYPerc((auC)object);
        } else if (n2 == cXP) {
            this.setSize((agj_1)object);
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

