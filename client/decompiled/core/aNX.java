/*
 * Decompiled with CFR 0.152.
 */
public class aNX
extends aNZ {
    public static final String TAG = "tooltip";
    public static vP dZX = vP.atM;
    public static vP dZY = new vP(ava_0.cYs[0], ava_0.cYs[1], ava_0.cYs[2], ava_0.cYs[3]);
    public static vP dZZ = new vP(ava_0.cYu[0], ava_0.cYu[1], ava_0.cYu[2], ava_0.cYu[3]);
    public static ma_1 eaa;
    public static float eab;
    public String IJ = "";
    public int bsW = 0;
    public int bsX = 0;
    public float fh = eab;
    public int wg = oz_0.aby();
    public float dHS = -1.0f;
    public vP CR = dZX;
    public vP aet = dZY;
    public vP fi = dZZ;
    public ma_1 Jj = eaa;
    public aea_2 ahY = rM.ahU;
    public BT eac = BT.aJT;
    public static final int caS;
    public static final int ead;
    public static final int eae;
    public static final int eaf;
    public static final int avT;
    public static final int eag;
    public static final int caO;
    public static final int arw;
    public static final int cWQ;
    public static final int ciR;
    public static final int ciS;

    public void a(air_1 air_12) {
        aNX aNX2 = (aNX)air_12;
        super.a(air_12);
        aNX2.IJ = this.IJ;
        aNX2.bsW = this.bsW;
        aNX2.bsX = this.bsX;
        aNX2.wg = this.wg;
        aNX2.dHS = this.dHS;
        aNX2.setTextColor(this.CR);
        aNX2.setBackgroundColor(this.aet);
        aNX2.setBorderColor(this.fi);
        aNX2.Jj = this.Jj;
        aNX2.ahY = this.ahY;
        aNX2.eac = this.eac;
        aNX2.fh = this.fh;
    }

    public void s(adg_2 adg_22) {
        oz_0 oz_02 = add_1.aOG().aON().awF();
        oz_02.setText(this.IJ);
        oz_02.a(this.aet.Cp(), this.aet.Cq(), this.aet.Cr(), this.aet.getAlpha());
        oz_02.b(this.fi.Cp(), this.fi.Cq(), this.fi.Cr(), this.fi.getAlpha());
        oz_02.setDuration(this.wg);
        oz_02.setMaxWidth((int)this.dHS);
        oz_02.setColor(this.CR.Cp(), this.CR.Cq(), this.CR.Cr(), this.CR.getAlpha());
        oz_02.a(this.Jj);
        oz_02.setOffset(this.bsW, this.bsX);
        oz_02.r((float)(adg_22.getDisplayX() + this.eac.eL(adg_22.getWidth())) - add_1.aOG().aON().adF() / 2.0f, (float)(adg_22.getDisplayY() + this.eac.eM(adg_22.getHeight())) + add_1.aOG().aON().adG() / 2.0f);
        oz_02.setBorderWidth(this.fh);
    }

    public String getTag() {
        return TAG;
    }

    public void setDuration(int n2) {
        this.wg = n2 * 1000;
    }

    public vP getBackgroundColor() {
        return this.aet;
    }

    public vP getBorderColor() {
        return this.fi;
    }

    public float getBorderWidth() {
        return this.fh;
    }

    public int getDuration() {
        return this.wg;
    }

    public ma_1 getFont() {
        return this.Jj;
    }

    public aea_2 getHotPointPosition() {
        return this.ahY;
    }

    public float getMaxWidth() {
        return this.dHS;
    }

    public BT getPosition() {
        return this.eac;
    }

    public String getText() {
        return this.IJ;
    }

    public vP getTextColor() {
        return this.CR;
    }

    public Integer getXOffset() {
        return this.bsW;
    }

    public int getYOffset() {
        return this.bsX;
    }

    public void setBackgroundColor(vP vP2) {
        if (this.aet == vP2) {
            return;
        }
        this.aet = vP2;
    }

    public void setBorderColor(vP vP2) {
        if (this.fi == vP2) {
            return;
        }
        this.fi = vP2;
    }

    public void setBorderWidth(float f) {
        this.fh = f;
    }

    public void setHotPointPosition(aea_2 aea_22) {
        this.ahY = aea_22;
    }

    public void setMaxWidth(float f) {
        this.dHS = f;
    }

    public void setPosition(BT bT) {
        this.eac = bT;
    }

    public void setText(String string) {
        this.IJ = string;
    }

    public void setTextColor(vP vP2) {
        if (this.CR == vP2) {
            return;
        }
        this.CR = vP2;
    }

    public void setXOffset(int n2) {
        this.bsW = n2;
    }

    public void setYOffset(int n2) {
        this.bsX = n2;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == caS) {
            this.setText(if_12.eM(string));
        } else if (n2 == ead) {
            this.setBackgroundColor(if_12.eK(string));
        } else if (n2 == eae) {
            this.setBorderColor(if_12.eK(string));
        } else if (n2 == avT) {
            this.setDuration(Gr.R(string));
        } else if (n2 == eag) {
            this.setHotPointPosition(aea_2.kW(string));
        } else if (n2 == caO) {
            this.setMaxWidth(Gr.getFloat(string));
        } else if (n2 == arw) {
            this.setPosition(BT.dv(string));
        } else if (n2 == ciR) {
            this.setXOffset(Gr.R(string));
        } else if (n2 == ciS) {
            this.setYOffset(Gr.R(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == caS) {
            this.setText(String.valueOf(object));
        } else if (n2 == ead) {
            this.setBackgroundColor((vP)object);
        } else if (n2 == eae) {
            this.setBorderColor((vP)object);
        } else if (n2 == avT) {
            this.setDuration(Gr.R(object));
        } else if (n2 == eag) {
            this.setHotPointPosition((aea_2)((Object)object));
        } else if (n2 == caO) {
            this.setMaxWidth(Gr.getFloat(object));
        } else if (n2 == arw) {
            this.setPosition((BT)((Object)object));
        } else if (n2 == ciR) {
            this.setXOffset(Gr.R(object));
        } else if (n2 == ciS) {
            this.setYOffset(Gr.R(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    public void j() {
        super.j();
        this.CR = null;
        this.aet = null;
        this.fi = null;
    }

    static {
        eab = 1.5f;
        caS = "text".hashCode();
        ead = "backgroundColor".hashCode();
        eae = "borderColor".hashCode();
        eaf = "borderWidth".hashCode();
        avT = "duration".hashCode();
        eag = "hotPointPosition".hashCode();
        caO = "maxWidth".hashCode();
        arw = "position".hashCode();
        cWQ = "textColor".hashCode();
        ciR = "xOffset".hashCode();
        ciS = "yOffset".hashCode();
    }
}

