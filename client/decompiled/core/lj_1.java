/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import java.io.IOException;

/*
 * Renamed from lj
 */
public final class lj_1
extends adg_2
implements ayi {
    public static final String TAG = "AnimatedElementViewer";
    private gq_2 oB;
    private String Gu;
    private String tF;
    private float Gv;
    private float Gw;
    private float Gx;
    private boolean Gy;
    private int Gz;
    private aPb tJ;
    private boolean GA = true;
    private lc_1 tB = lc_1.Gf;
    private xM tC = xM.azv;
    private air GB = air.cya;
    private air GC = air.cye;
    private boolean GD = false;
    private boolean GE;
    private boolean GF;
    private boolean GG = false;
    private boolean GH;
    public static final int GI = "animatedElement".hashCode();
    public static final int GJ = "direction".hashCode();
    public static final int GK = "filePath".hashCode();
    public static final int GL = "equipment".hashCode();
    public static final int GM = "animName".hashCode();
    public static final int GN = "offsetX".hashCode();
    public static final int GO = "offsetY".hashCode();
    public static final int GP = "scale".hashCode();
    public static final int GQ = "useDefaultMaterial".hashCode();
    public static final int GR = "blendPremult".hashCode();

    public String getTag() {
        return TAG;
    }

    public final String getFilePath() {
        return this.Gu;
    }

    public final void setFilePath(String string) {
        if (string == null) {
            return;
        }
        this.Gu = string;
        String string2 = add_1.aOG().dwz;
        if (string2 == null) {
            return;
        }
        this.oB.lq(vq_2.gs(string));
        String string3 = string.startsWith("jar:") || string.startsWith("file:") ? string : string2 + string;
        try {
            this.oB.b(string3, false);
        }
        catch (IOException iOException) {
            a.error((Object)"Unable to load anm file", (Throwable)iOException);
        }
        this.oB.a(this.GB, this.GC);
    }

    public final String getAnimName() {
        return this.tF;
    }

    public final void setAnimName(String string) {
        if (string != null) {
            this.tF = string;
            this.Gy = true;
        }
    }

    public ahh_1 getAnimatedElement() {
        return this.oB;
    }

    public void setAnimatedElement(ahh_1 ahh_12) {
        if (this.oB == null) {
            return;
        }
        this.oB.b(ahh_12);
        if (this.GA) {
            this.oB.aTp();
        }
        this.GE = true;
    }

    public float getOffsetX() {
        return this.Gv;
    }

    public final void setOffsetX(float f) {
        this.Gv = f;
        this.GE = true;
    }

    public final float getOffsetY() {
        return this.Gw;
    }

    public final void setOffsetY(float f) {
        this.Gw = f;
        this.GE = true;
    }

    public float getScale() {
        return this.Gx;
    }

    public void setScale(float f) {
        this.Gx = f;
        this.GE = true;
    }

    public int getDirection() {
        return this.Gz;
    }

    public void setDirection(int n2) {
        this.Gz = n2;
        this.GF = true;
    }

    public final aPb getMaterial() {
        return this.tJ;
    }

    public final void setMaterial(aPb aPb2) {
        if (aPb2 == null) {
            return;
        }
        this.tJ = aPb2;
        this.GD = true;
    }

    public void setEquipment(gw_2 gw_22) {
        if (gw_22 == null) {
            return;
        }
        this.tB = gw_22.kc();
        this.tC = gw_22.kd();
        this.GG = true;
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return true;
    }

    public boolean isUseDefaultMaterial() {
        return this.GA;
    }

    public void setUseDefaultMaterial(boolean bl2) {
        this.GA = bl2;
    }

    public void setUseBlendPremult(boolean bl2) {
        this.GB = bl2 ? air.cya : air.cyd;
        this.GH = true;
    }

    public final void j() {
        super.j();
        if (this.oB != null) {
            this.oB.dispose();
            this.oB.getMaterial().release();
            this.oB = null;
        }
        this.tB.clear();
        this.tC.clear();
    }

    public final void b() {
        super.b();
        assert (this.oB == null);
        this.oB = new gq_2(0L);
        this.GE = true;
        sn_2 sn_22 = new sn_2();
        sn_22.b();
        sn_22.setWidget(this);
        this.a(sn_22);
        this.setNeedsToPostProcess();
        this.GB = air.cya;
        this.GC = air.cye;
    }

    public boolean cb(int n2) {
        super.cb(n2);
        if (!this.aQv) {
            return true;
        }
        this.pY();
        if (this.Gy) {
            this.oB.aY(this.tF);
            this.Gy = false;
        }
        if (this.GF) {
            this.oB.b(qc_0.hf(this.Gz));
            this.GF = false;
        }
        if (this.GG) {
            gw_2 gw_22 = this.oB.aTF();
            gw_22.a(this.tB);
            gw_22.a(this.tC);
            this.oB.aTq();
            this.GG = false;
        }
        if (this.GD) {
            this.oB.setMaterial(this.tJ);
            this.GD = false;
        }
        if (this.GH) {
            this.oB.a(this.GB, this.GC);
        }
        this.oB.b(null, n2);
        return true;
    }

    public final void a(air_1 air_12) {
        lj_1 lj_12 = (lj_1)air_12;
        super.a((air_1)lj_12);
        lj_12.setFilePath(this.Gu);
        lj_12.setOffsetX(this.Gv);
        lj_12.setOffsetY(this.Gw);
        lj_12.setScale(this.Gx);
        lj_12.setDirection(this.Gz);
        lj_12.setMaterial(this.tJ);
        lj_12.setAnimName(this.tF);
        lj_12.GA = this.GA;
        lj_12.setNeedsToPostProcess();
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
        } else if (n2 == GQ) {
            this.setUseDefaultMaterial(Gr.getBoolean(string));
        } else if (n2 == GR) {
            this.setUseDefaultMaterial(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == GI) {
            this.setAnimatedElement((ahh_1)object);
        } else if (n2 == GK) {
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
        } else if (n2 == GL) {
            this.setEquipment((gw_2)object);
        } else if (n2 == GQ) {
            this.setUseDefaultMaterial(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    protected void pX() {
        super.pX();
        this.arC.i(this.oB.getEntity());
    }

    private void pY() {
        Entity entity = this.oB.getEntity();
        avz avz2 = (avz)entity.aUM().aI(0);
        avz2.e((float)this.aLd.getWidth() / 2.0f + this.Gv, (float)this.aLd.getHeight() / 2.0f + this.Gw, 0.0f);
        float f = 1.0f;
        if (this.oB.aTF() != null) {
            f = this.oB.aTF().getScale();
        }
        avz2.m(this.Gx / f, this.Gx / f, 0.0f);
        entity.aUM().b(0, avz2);
        this.GE = false;
    }

    public void setModulationColor(vP vP2) {
        if (this.oB == null || vP2 == null || this.oB.aTm() == null) {
            return;
        }
        float[] fArray = new float[]{1.0f - vP2.Cp(), 1.0f - vP2.Cq(), 1.0f - vP2.Cr(), 0.0f};
        float[] fArray2 = new float[]{vP2.Cp() * vP2.getAlpha(), vP2.Cq() * vP2.getAlpha(), vP2.Cr() * vP2.getAlpha(), vP2.getAlpha()};
        this.oB.f(fArray2, fArray);
    }

    public vP getModulationColor() {
        float[] fArray = this.oB.getMaterial().aYK();
        return new vP(fArray[0], fArray[1], fArray[2], fArray[3]);
    }
}

