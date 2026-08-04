/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from azc
 */
public class azc_0
extends adg_2
implements oc_0,
ie_1 {
    public static final String TAG = "Image";
    protected zo_1 dnd;
    protected boolean aVo = false;
    protected boolean agr = false;
    protected boolean cJn = true;
    protected ajn_1 dne = ajn_1.dSu;
    protected agj_1 dnf;
    public static final int cJ = "align".hashCode();
    public static final int dng = "displayShape".hashCode();
    public static final int dnh = "displaySize".hashCode();
    public static final int ary = "flipHorizontaly".hashCode();
    public static final int arz = "flipVerticaly".hashCode();
    public static final int dni = "keepAspectRatio".hashCode();
    public static final int aHX = "modulationColor".hashCode();
    public static final int dnj = "pixmap".hashCode();
    public static final int aVq = "scaled".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof ur_1) {
            this.setPixmap((ur_1)na_12);
        }
        super.a(na_12);
    }

    protected void pX() {
        super.pX();
        if (this.arC != null && this.dnd.getEntity() != null) {
            this.arC.i(this.dnd.getEntity());
        }
    }

    public String getTag() {
        return TAG;
    }

    public akq_1 getPixmap() {
        return this.dnd != null ? this.dnd.getPixmap() : null;
    }

    public void setPixmap(akq_1 akq_12) {
        if (this.dnd != null) {
            akq_1 akq_13 = this.dnd.getPixmap();
            if (akq_13 != null) {
                akq_13.b(this);
            }
            this.dnd.setPixmap(akq_12);
            akq_12.a(this);
            this.cJn = true;
            this.setNeedsToPreProcess();
        }
    }

    public void setPixmap(ur_1 ur_12) {
        if (this.dnd != null) {
            akq_1 akq_12 = this.dnd.getPixmap();
            if (akq_12 != null) {
                akq_12.b(this);
            }
            if ((akq_12 = ur_12.getPixmap()) != null) {
                akq_12.a(this);
            }
            this.dnd.setPixmap(ur_12.getPixmap());
            this.cJn = true;
            this.setNeedsToPreProcess();
        }
    }

    public boolean getScaled() {
        return this.aVo;
    }

    public void setScaled(boolean bl2) {
        if (this.aVo != bl2) {
            this.aVo = bl2;
            this.cJn = true;
            this.setNeedsToPreProcess();
        }
    }

    public void setDisplayShape(kx_1 kx_12) {
        if (this.dnd != null) {
            this.dnd.setShape(kx_12);
        }
    }

    public kx_1 getDisplayShape() {
        return this.dnd != null ? this.dnd.getShape() : null;
    }

    public boolean getKeepAspectRatio() {
        return this.agr;
    }

    public void setKeepAspectRatio(boolean bl2) {
        if (this.agr != bl2) {
            this.agr = bl2;
            this.cJn = true;
            this.setNeedsToPreProcess();
        }
    }

    public ajn_1 getAlign() {
        return this.dne;
    }

    public void setAlign(ajn_1 ajn_12) {
        if (!ajn_12.equals((Object)this.dne)) {
            this.dne = ajn_12;
            this.cJn = true;
            this.setNeedsToPreProcess();
        }
    }

    public void setDisplaySize(agj_1 agj_12) {
        this.dnf = agj_12;
        this.cJn = true;
        this.setNeedsToPreProcess();
    }

    public agj_1 getDisplaySize() {
        return this.dnf;
    }

    public void setModulationColor(vP vP2) {
        if (this.dnd != null) {
            this.dnd.setModulationColor(vP2);
        }
    }

    public vP getModulationColor() {
        return this.dnd != null ? this.dnd.getModulationColor() : null;
    }

    public void setFlipHorizontaly(boolean bl2) {
        if (this.dnd != null) {
            this.dnd.setFlipHorizontaly(bl2);
            this.cJn = true;
            this.setNeedsToPreProcess();
        }
    }

    public boolean getFlipHorizontaly() {
        return this.dnd != null && this.dnd.Gl();
    }

    public void setFlipVerticaly(boolean bl2) {
        if (this.dnd != null) {
            this.dnd.setFlipVerticaly(bl2);
            this.cJn = true;
            this.setNeedsToPreProcess();
        }
    }

    public boolean getFlipVerticaly() {
        return this.dnd != null && this.dnd.Gm();
    }

    public zo_1 getImageMesh() {
        return this.dnd;
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return zb instanceof fm_0;
    }

    public void j() {
        super.j();
        this.dne = null;
        this.dnf = null;
        if (this.dnd != null) {
            akq_1 akq_12 = this.dnd.getPixmap();
            if (akq_12 != null) {
                akq_12.b(this);
            }
            this.dnd.j();
            this.dnd = null;
        }
    }

    public void b() {
        this.dne = ajn_1.dSu;
        super.b();
        fm_0 fm_02 = fm_0.checkOut();
        fm_02.setWidget(this);
        this.a(fm_02);
        this.setNeedsToPreProcess();
        this.dnd = new zo_1();
        this.dnd.b();
    }

    public void validate() {
        if (this.dnd != null) {
            this.aLJ();
            this.dnd.a(this.aLd, this.cLZ.getMargin(), this.cLZ.getBorder(), this.cLZ.getPadding());
        }
        super.validate();
    }

    private void aLJ() {
        if (this.dnd == null) {
            return;
        }
        akq_1 akq_12 = this.dnd.getPixmap();
        if (akq_12 != null) {
            int n2 = this.cLZ.getContentWidth();
            int n3 = this.cLZ.getContentHeight();
            if (this.dnf != null) {
                int n4 = this.dne.ag((int)this.dnf.getWidth(), n2);
                int n5 = this.dne.ah((int)this.dnf.getHeight(), n3);
                this.dnd.setX(n4);
                this.dnd.setY(n5);
                this.dnd.setHeight((int)this.dnf.getHeight());
                this.dnd.setWidth((int)this.dnf.getWidth());
            } else if (this.aVo) {
                if (this.agr) {
                    int n6;
                    int n7;
                    if (n2 != 0 && akq_12.getWidth() != 0 && n3 != 0 && akq_12.getHeight() != 0) {
                        float f;
                        float f2 = (float)akq_12.getWidth() / (float)akq_12.getHeight();
                        if (f2 == (f = (float)n2 / (float)n3)) {
                            n7 = n2;
                            n6 = n3;
                        } else if (f2 > f) {
                            n7 = n2;
                            n6 = (int)((float)n7 / f2);
                        } else {
                            n6 = n3;
                            n7 = (int)((float)n6 * f2);
                        }
                    } else {
                        n7 = n2;
                        n6 = n3;
                    }
                    int n8 = this.dne.ag(n7, n2);
                    int n9 = this.dne.ah(n6, n3);
                    this.dnd.setX(n8);
                    this.dnd.setY(n9);
                    this.dnd.setHeight(n6);
                    this.dnd.setWidth(n7);
                } else {
                    this.dnd.setX(0);
                    this.dnd.setY(0);
                    this.dnd.setHeight(n3);
                    this.dnd.setWidth(n2);
                }
            } else {
                int n10 = this.dne.ag(akq_12.getWidth(), n2);
                int n11 = this.dne.ah(akq_12.getHeight(), n3);
                this.dnd.setX(n10);
                this.dnd.setY(n11);
                this.dnd.setHeight(akq_12.getHeight());
                this.dnd.setWidth(akq_12.getWidth());
            }
        }
    }

    public boolean zs() {
        boolean bl2 = false;
        if (this.dnd == null) {
            return false;
        }
        if (this.dnd.getPixmap() != null) {
            int n2;
            int n3;
            if (this.dnf != null) {
                n3 = this.dnf.width;
                n2 = this.dnf.height;
            } else {
                n3 = this.dnd.getPixmap().getWidth();
                n2 = this.dnd.getPixmap().getHeight();
            }
            if (this.aLb == null || n3 != this.aLb.width || n2 != this.aLb.height) {
                this.setMinSize(new agj_1(n3, n2));
                bl2 = true;
            }
        } else if (this.aLb == null || this.aLb.width != 0 || this.aLb.height != 0) {
            this.setMinSize(new agj_1(0, 0));
            bl2 = true;
        }
        return bl2;
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.dnd != null && (this.cJn || this.dnd.getPixmap() != null && this.dnd.getPixmap().azP())) {
            boolean bl3 = this.zs();
            this.aLJ();
            try {
                if (this.cLZ != null) {
                    this.dnd.a(this.aLd, this.cLZ.getMargin(), this.cLZ.getBorder(), this.cLZ.getPadding());
                }
            }
            catch (NullPointerException nullPointerException) {
                a.error((Object)("imageMesh = " + this.dnd + ", appearance = " + this.cLZ), (Throwable)nullPointerException);
            }
            if (bl3 && this.dxR != null) {
                this.dxR.Am();
            }
            this.cJn = false;
        }
        return bl2;
    }

    public void a(air_1 air_12) {
        azc_0 azc_02 = (azc_0)air_12;
        super.a(air_12);
        azc_02.setAlign(this.dne);
        azc_02.setDisplaySize(this.dnf != null ? (agj_1)this.dnf.clone() : null);
        azc_02.setKeepAspectRatio(this.agr);
        azc_02.setScaled(this.aVo);
        azc_02.setModulationColor(azc_02.getModulationColor());
        azc_02.setFlipHorizontaly(this.getFlipHorizontaly());
        azc_02.setFlipVerticaly(this.getFlipVerticaly());
        azc_02.setDisplayShape(this.getDisplayShape());
    }

    public void a(akq_1 akq_12) {
        this.cJn = true;
        this.setNeedsToPreProcess();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == cJ) {
            this.setAlign(ajn_1.lz(string));
        } else if (n2 == dng) {
            this.setDisplayShape(kx_1.aP(string));
        } else if (n2 == dnh) {
            this.setDisplaySize(if_12.eL(string));
        } else if (n2 == ary) {
            this.setFlipHorizontaly(Gr.getBoolean(string));
        } else if (n2 == arz) {
            this.setFlipVerticaly(Gr.getBoolean(string));
        } else if (n2 == dni) {
            this.setKeepAspectRatio(Gr.getBoolean(string));
        } else if (n2 == aHX) {
            this.setModulationColor(if_12.eK(string));
        } else if (n2 == aVq) {
            this.setScaled(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == cJ) {
            this.setAlign((ajn_1)((Object)object));
        } else if (n2 == dng) {
            this.setDisplayShape((kx_1)((Object)object));
        } else if (n2 == dnh) {
            this.setDisplaySize((agj_1)object);
        } else if (n2 == ary) {
            this.setFlipHorizontaly(Gr.getBoolean(object));
        } else if (n2 == arz) {
            this.setFlipVerticaly(Gr.getBoolean(object));
        } else if (n2 == dni) {
            this.setKeepAspectRatio(Gr.getBoolean(object));
        } else if (n2 == aHX) {
            this.setModulationColor((vP)object);
        } else if (n2 == dnj) {
            this.setPixmap((akq_1)object);
        } else if (n2 == aVq) {
            this.setScaled(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

