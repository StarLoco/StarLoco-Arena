/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from KH
 */
public class kh_2
extends Zb
implements cn_1 {
    private static Logger a = Logger.getLogger(kh_2.class);
    public static final String TAG = "mapAppearance";
    private ur_1 aHT = null;
    private vP AC = null;
    private int aFD;
    private int boR;
    private int aFE;
    private int boS;
    private boolean boT = false;
    private boolean boU = false;
    private boolean boV = false;
    private boolean boW = false;
    public static final int aHX = "modulationColor".hashCode();
    public static final int boX = "startX".hashCode();
    public static final int boY = "startY".hashCode();
    public static final int boZ = "endX".hashCode();
    public static final int bpa = "endY".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof ur_1) {
            this.aHT = (ur_1)na_12;
            if (this.DD instanceof azk_0) {
                ((azk_0)this.DD).setMapBackgroundPixmap(((ur_1)na_12).getPixmap());
            }
        }
        super.a(na_12);
    }

    public String getTag() {
        return TAG;
    }

    public vP getModulationColor() {
        return this.AC;
    }

    public void setModulationColor(vP vP2) {
        if (this.AC == vP2) {
            return;
        }
        this.AC = vP2;
        if (this.DD instanceof azk_0) {
            ((azk_0)this.DD).setModulationColor(vP2);
        }
    }

    public int getStartX() {
        return this.aFD;
    }

    public void setStartX(int n2) {
        this.aFD = n2;
        this.boT = true;
        azk_0 azk_02 = (azk_0)this.DD;
        if (azk_02 != null) {
            azk_02.setMapBackgroundStartX(this.aFD);
        }
    }

    public int getEndX() {
        return this.boR;
    }

    public void setEndX(int n2) {
        this.boR = n2;
        this.boU = true;
        azk_0 azk_02 = (azk_0)this.DD;
        if (azk_02 != null) {
            azk_02.setMapBackgroundEndX(this.boR);
        }
    }

    public int getStartY() {
        return this.aFE;
    }

    public void setStartY(int n2) {
        this.aFE = n2;
        this.boV = true;
        azk_0 azk_02 = (azk_0)this.DD;
        if (azk_02 != null) {
            azk_02.setMapBackgroundStartY(this.aFE);
        }
    }

    public int getEndY() {
        return this.boS;
    }

    public void setEndY(int n2) {
        this.boS = n2;
        this.boW = true;
        azk_0 azk_02 = (azk_0)this.DD;
        if (azk_02 != null) {
            azk_02.setMapBackgroundEndY(this.boS);
        }
    }

    public void setWidget(adg_2 adg_22) {
        super.setWidget(adg_22);
        if (adg_22 instanceof azk_0) {
            azk_0 azk_02 = (azk_0)adg_22;
            if (this.AC != null) {
                azk_02.setModulationColor(this.AC);
            }
            if (this.aHT != null) {
                azk_02.setMapBackgroundPixmap(this.aHT.getPixmap());
            }
            azk_02.setMapBackgroundStartX(this.aFD);
            azk_02.setMapBackgroundStartY(this.aFE);
            azk_02.setMapBackgroundEndX(this.boR);
            azk_02.setMapBackgroundEndY(this.boS);
        }
    }

    public boolean cc(int n2) {
        if (this.bPA && this.DD instanceof azk_0) {
            azk_0 azk_02 = (azk_0)this.DD;
            if (this.aHT != null) {
                azk_02.setMapBackgroundPixmap(this.aHT.getPixmap());
            }
            azk_02.setMapBackgroundStartX(this.aFD);
            azk_02.setMapBackgroundStartY(this.aFE);
            azk_02.setMapBackgroundEndX(this.boR);
            azk_02.setMapBackgroundEndY(this.boS);
        }
        return super.cc(n2);
    }

    public void a(air_1 air_12) {
        kh_2 kh_22 = (kh_2)air_12;
        super.a((air_1)kh_22);
        if (this.boT) {
            kh_22.setStartX(this.aFD);
        }
        if (this.boV) {
            kh_22.setStartY(this.aFE);
        }
        if (this.boU) {
            kh_22.setEndX(this.boR);
        }
        if (this.boW) {
            kh_22.setEndY(this.boS);
        }
        kh_22.setModulationColor(this.AC);
    }

    public void j() {
        super.j();
        this.aHT = null;
        this.AC = null;
    }

    public void b() {
        super.b();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == aHX) {
            this.setModulationColor(if_12.eK(string));
        } else if (n2 == boX) {
            this.setStartX(Gr.R(string));
        } else if (n2 == boY) {
            this.setStartY(Gr.R(string));
        } else if (n2 == boZ) {
            this.setEndX(Gr.R(string));
        } else if (n2 == bpa) {
            this.setEndY(Gr.R(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == aHX) {
            this.setModulationColor((vP)object);
        } else if (n2 == boX) {
            this.setStartX(Gr.R(object));
        } else if (n2 == boY) {
            this.setStartY(Gr.R(object));
        } else if (n2 == boZ) {
            this.setEndX(Gr.R(object));
        } else if (n2 == bpa) {
            this.setEndY(Gr.R(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

