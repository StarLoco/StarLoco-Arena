/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aqq
 */
public class aqq_0
extends aht_1
implements oc_0,
aac,
ajb_0,
wS {
    public static final String TAG = "Button";
    protected azc_0 cNW;
    protected OE lU;
    protected BT cG;
    protected bo_0 aoV;
    protected aiq_0 lX;
    protected af_1 lY;
    protected boolean apf = true;
    protected ov_1 cNX;
    protected ov_1 aDc;
    protected ov_1 aDb;
    protected ov_1 cNY;
    protected ov_1 cNZ;
    protected ov_1 cOa;
    protected int eb = -1;
    public static final int caS = "text".hashCode();
    public static final int cJ = "align".hashCode();
    public static final int mh = "font".hashCode();
    public static final int aHX = "modulationColor".hashCode();
    public static final int ml = "textOrientation".hashCode();
    public static final int arv = "texture".hashCode();
    public static final int cOb = "pixmapAlign".hashCode();
    public static final int eg = "clickSoundId".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof ur_1) {
            this.setPixmap((ur_1)na_12);
        } else if (na_12 instanceof OE) {
            this.setLabel((OE)na_12);
        }
        super.a(na_12);
    }

    public void f(na_1 na_12) {
        if (na_12 instanceof azc_0) {
            if (this.cNW != null && this.cNW != na_12) {
                this.cNW.aab();
            }
            this.cNW = (azc_0)na_12;
        }
        super.f(na_12);
    }

    public String getTag() {
        return TAG;
    }

    public bo_0 getPixmapAlign() {
        return this.aoV;
    }

    public void setPixmapAlign(bo_0 bo_02) {
        this.aoV = bo_02;
        this.Am();
    }

    public aiq_0 getTextOrientation() {
        return this.lX;
    }

    public void setTextOrientation(aiq_0 aiq_02) {
        this.lX = aiq_02;
        if (this.lU != null) {
            this.lU.setOrientation(aiq_02);
        }
    }

    public void setAlign(BT bT) {
        this.cG = bT;
        this.apf = true;
        this.setNeedsToPreProcess();
    }

    public void setLabel(OE oE) {
        if (oE != this.lU && this.lU != null) {
            this.lU.aab();
            this.lU = oE;
        } else if (this.lU == null) {
            this.lU = oE;
        }
        if (this.lU != null) {
            this.lU.setOrientation(this.lX);
            this.lU.setColor(this.getAppearance().getTextColor(), null);
            this.lU.setFont(this.getAppearance().getFont());
            this.lU.setAlign(BT.aJX);
        }
    }

    public void setText(String string) {
        if (string == null || string.equals("")) {
            if (this.lU != null) {
                this.k(this.lU);
            }
            return;
        }
        if (this.lU == null) {
            this.lU = new OE();
            this.lU.b();
            this.a(this.lU);
        }
        this.lU.setText(string);
    }

    public String getText() {
        if (this.lU != null) {
            return this.lU.getText();
        }
        return "";
    }

    public void setEnabled(boolean bl2) {
        super.setEnabled(bl2);
        if (this.OD) {
            this.getAppearance().aCn();
        } else {
            this.getAppearance().aCm();
        }
    }

    public adg_2 getWidget(int n2, int n3) {
        if (this.czc) {
            return null;
        }
        if (this.aQv && !this.dyc && this.getAppearance().aY(n2, n3) && !ago_2.getInstance().isMovePointMode()) {
            return this;
        }
        return null;
    }

    public ani_2 getAppearance() {
        return (ani_2)this.cLZ;
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return zb instanceof ani_2;
    }

    public void setTexture(ef_1 ef_12) {
        ur_1 ur_12 = null;
        if (ef_12 != null) {
            ur_12 = ur_1.checkOut();
            ur_12.setTexture(ef_12);
        }
        this.setPixmap(ur_12);
    }

    public void setModulationColor(vP vP2) {
        if (this.cNW != null) {
            this.cNW.setModulationColor(vP2);
        }
    }

    public vP getModulationColor() {
        if (this.cNW != null) {
            return this.cNW.getModulationColor();
        }
        return null;
    }

    public void setPixmap(ur_1 ur_12) {
        if (ur_12 != null) {
            if (this.cNW == null) {
                this.cNW = new azc_0();
                this.cNW.b();
                this.a(this.cNW);
            }
            this.cNW.setPixmap(ur_12);
        } else if (this.cNW != null) {
            this.cNW.aab();
            this.cNW = null;
        }
    }

    public void setFocusable(boolean bl2) {
        super.setFocusable(bl2);
        if (this.dye && this.cNX == null) {
            this.cNX = new agy_2(this);
            this.a(qe_1.bFm, this.cNX, false);
        } else if (!this.dye && this.cNX != null) {
            this.b(qe_1.bFm, this.cNX, false);
            this.cNX = null;
        }
    }

    public void setFont(af_1 af_12) {
        if (this.lU != null) {
            this.lU.setFont(af_12);
        }
        this.lY = af_12;
    }

    public void setColor(vP vP2, String string) {
        if (this.lU != null && (string == null || string.equalsIgnoreCase("text"))) {
            this.lU.setColor(vP2, null);
        }
    }

    public void setClickSoundId(int n2) {
        this.eb = n2;
    }

    public int getClickSoundId() {
        return this.eb;
    }

    protected void a(ke ke2, boolean bl2) {
        if (!ke2.oH()) {
            block0 : switch (ke2.aV()) {
                case bFB: 
                case bFC: 
                case bFi: 
                case bFj: {
                    ke2.X(true);
                    switch (this.eb) {
                        case -1: {
                            aek.atD().click();
                            break block0;
                        }
                        case -2: {
                            aek.atD().atH();
                            break block0;
                        }
                    }
                    aek.atD().jY(this.eb);
                    break;
                }
                case bFx: {
                    aek.atD().rollOver();
                    ke2.X(true);
                }
            }
        }
    }

    public void aDY() {
        this.L(1, 1, 0);
    }

    public void L(int n2, int n3, int n4) {
        this.getAppearance().aCk();
        aam_1.aMF().a(new vp_1(this, n2, n3, n4, this.getScreenX() + this.aLd.width / 2, this.getScreenY() + this.aLd.height / 2), 200L, 0, 1);
    }

    public void yx() {
        super.yx();
    }

    public void eT() {
        this.aDc = new agu_2(this);
        ago_2.getInstance().a(qe_1.bFA, this.aDc, false);
        this.aDb = new agv_2(this);
        this.a(qe_1.bFz, this.aDb, false);
        this.cNY = new agl_2(this);
        this.a(qe_1.bFx, this.cNY, false);
        this.cNZ = new agk_2(this);
        this.a(qe_1.bFy, this.cNZ, false);
    }

    public void j() {
        super.j();
        this.cNW = null;
        this.lU = null;
        ago_2.getInstance().b(qe_1.bFA, this.aDc, false);
        this.cNY = null;
        this.cNZ = null;
        this.aDb = null;
        this.aDc = null;
        this.cNX = null;
    }

    public void b() {
        super.b();
        ani_2 ani_22 = ani_2.checkOut();
        ani_22.setWidget(this);
        this.a(ani_22);
        Cp cp = new Cp(this);
        cp.b();
        this.a(cp);
        this.dyc = false;
        this.cG = BT.aJX;
        this.aoV = bo_0.aJv;
        this.lX = aiq_0.cxW;
        this.eb = -1;
        this.eT();
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.apf) {
            this.invalidate();
            this.apf = false;
        }
        return bl2;
    }

    public void a(air_1 air_12) {
        aqq_0 aqq_02 = (aqq_0)air_12;
        super.a((air_1)aqq_02);
        aqq_02.cG = this.cG;
        aqq_02.aoV = this.aoV;
        aqq_02.lX = this.lX;
        aqq_02.eb = this.eb;
        aqq_02.b(qe_1.bFz, this.aDb, false);
        aqq_02.b(qe_1.bFx, this.cNY, false);
        aqq_02.b(qe_1.bFy, this.cNZ, false);
        if (this.cNX != null) {
            aqq_02.b(qe_1.bFm, this.cNX, false);
        }
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == caS) {
            this.setText(if_12.eM(string));
        } else if (n2 == cJ) {
            this.setAlign((BT)((Object)if_12.c(BT.class, string)));
        } else if (n2 == aHX) {
            this.setModulationColor(if_12.eK(string));
        } else if (n2 == mh) {
            this.setFont(if_12.eP(string));
        } else if (n2 == ml) {
            this.setTextOrientation((aiq_0)((Object)if_12.c(aiq_0.class, string)));
        } else if (n2 == arv) {
            this.setTexture(if_12.eO(string));
        } else if (n2 == cOb) {
            this.setPixmapAlign((bo_0)((Object)if_12.c(bo_0.class, string)));
        } else if (n2 == eg) {
            this.setClickSoundId(Gr.R(string));
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
        } else if (n2 == aHX) {
            this.setModulationColor((vP)object);
        } else if (n2 == mh) {
            this.setFont((af_1)object);
        } else if (n2 == ml) {
            this.setTextOrientation((aiq_0)((Object)object));
        } else if (n2 == arv) {
            this.setTexture((ef_1)object);
        } else if (n2 == cOb) {
            this.setPixmapAlign((bo_0)((Object)object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    public xy_0 getCursorType() {
        return xy_0.bYm;
    }
}

