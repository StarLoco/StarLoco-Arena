/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Sn
 */
public final class sn_2
extends Zb {
    public static final String TAG = "AnimatedElementViewerAppearance";
    public static final int GJ = "direction".hashCode();
    public static final int GK = "filePath".hashCode();
    public static final int GM = "animName".hashCode();
    public static final int GN = "offsetX".hashCode();
    public static final int GO = "offsetY".hashCode();
    public static final int GP = "scale".hashCode();
    public static final int GQ = "useDefaultMaterial".hashCode();
    public static final int GR = "blendPremult".hashCode();
    private String Gu;
    private String tF;
    private float Gv;
    private float Gw;
    private float Gx;
    private int Gz;
    private boolean bLi = true;

    public String getTag() {
        return TAG;
    }

    public final String getFilePath() {
        return this.Gu;
    }

    public final void setFilePath(String string) {
        this.Gu = string;
        if (this.DD != null) {
            this.getAnimatedElementViewer().setFilePath(string);
        }
    }

    public String getAnimName() {
        return this.tF;
    }

    public void setAnimName(String string) {
        this.tF = string;
        if (this.DD != null) {
            this.getAnimatedElementViewer().setAnimName(this.tF);
        }
    }

    public float getOffsetX() {
        return this.Gv;
    }

    public void setOffsetX(float f) {
        this.Gv = f;
        if (this.DD != null) {
            this.getAnimatedElementViewer().setOffsetX(this.Gv);
        }
    }

    public float getOffsetY() {
        return this.Gw;
    }

    public void setOffsetY(float f) {
        this.Gw = f;
        if (this.DD != null) {
            this.getAnimatedElementViewer().setOffsetY(this.Gw);
        }
    }

    public float getScale() {
        return this.Gx;
    }

    public void setScale(float f) {
        this.Gx = f;
        if (this.DD != null) {
            this.getAnimatedElementViewer().setScale(this.Gx);
        }
    }

    public int getDirection() {
        return this.Gz;
    }

    public void setDirection(int n2) {
        this.Gz = n2;
        if (this.DD != null) {
            this.getAnimatedElementViewer().setDirection(this.Gz);
        }
    }

    public void setUseBlendPremult(boolean bl2) {
        this.bLi = bl2;
        if (this.DD != null) {
            this.getAnimatedElementViewer().setUseBlendPremult(this.bLi);
        }
    }

    public final lj_1 getAnimatedElementViewer() {
        return (lj_1)this.DD;
    }

    public final void setWidget(adg_2 adg_22) {
        super.setWidget(adg_22);
        lj_1 lj_12 = this.getAnimatedElementViewer();
        lj_12.setFilePath(this.Gu);
        lj_12.setAnimName(this.tF);
        lj_12.setOffsetX(this.Gv);
        lj_12.setOffsetY(this.Gw);
        lj_12.setScale(this.Gx);
        lj_12.setDirection(this.Gz);
        lj_12.setUseBlendPremult(this.bLi);
    }

    public final void a(air_1 air_12) {
        sn_2 sn_22 = (sn_2)air_12;
        super.a((air_1)sn_22);
        if (this.Gu != null) {
            sn_22.setFilePath(this.Gu);
            sn_22.setAnimName(this.tF);
            sn_22.setOffsetX(this.Gv);
            sn_22.setOffsetY(this.Gw);
            sn_22.setScale(this.Gx);
            sn_22.setDirection(this.Gz);
            sn_22.setUseBlendPremult(this.bLi);
        }
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == GK) {
            this.setFilePath(string);
        } else if (n2 == GM) {
            this.setAnimName(string);
        } else if (n2 == GJ) {
            this.setDirection(Gr.R(string));
        } else if (n2 == GN) {
            this.setOffsetX(Gr.getFloat(string));
        } else if (n2 == GO) {
            this.setOffsetY(Gr.getFloat(string));
        } else if (n2 == GP) {
            this.setScale(Gr.getFloat(string));
        } else if (n2 == GR) {
            this.setUseBlendPremult(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == GK) {
            this.setFilePath((String)object);
        } else if (n2 == GM) {
            this.setAnimName((String)object);
        } else if (n2 == GJ) {
            this.setDirection(Gr.R(object));
        } else if (n2 == GN) {
            this.setOffsetX(Gr.getFloat(object));
        } else if (n2 == GO) {
            this.setOffsetY(Gr.getFloat(object));
        } else if (n2 == GP) {
            this.setScale(Gr.getFloat(object));
        } else if (n2 == GR) {
            this.setUseBlendPremult(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

