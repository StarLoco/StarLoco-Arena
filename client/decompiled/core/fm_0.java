/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from FM
 */
public class fm_0
extends Zb
implements cn_1 {
    private static Logger a = Logger.getLogger(fm_0.class);
    public static final String TAG = "ImageAppearance";
    private boolean aVo = false;
    private boolean aVp = false;
    private ur_1 aHT = null;
    private vP AC = null;
    private static final acl_0 uG = new ym_0(new cY());
    public static final int aHX = "modulationColor".hashCode();
    public static final int aVq = "scaled".hashCode();

    public static fm_0 checkOut() {
        fm_0 fm_02;
        try {
            fm_02 = (fm_0)uG.adr();
            fm_02.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            fm_02 = new fm_0();
            fm_02.b();
        }
        return fm_02;
    }

    public void a(na_1 na_12) {
        if (na_12 instanceof ur_1) {
            this.aHT = (ur_1)na_12;
            if (this.DD instanceof azc_0) {
                ((azc_0)this.DD).setPixmap((ur_1)na_12);
                if (this.aVp) {
                    ((azc_0)this.DD).setScaled(this.aVo);
                }
            } else if (this.DD != null) {
                a.error((Object)("Un " + this.DD.getClass() + " poss\u00e8de une ImageAppearance et ne peut pas recevoir de Pixmap"));
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
        if (this.DD instanceof azc_0) {
            ((azc_0)this.DD).setModulationColor(vP2);
        }
    }

    public boolean isScaled() {
        return this.aVo;
    }

    public void setScaled(boolean bl2) {
        this.aVo = bl2;
        this.aVp = true;
        if (this.DD instanceof azc_0) {
            ((azc_0)this.DD).setScaled(this.aVo);
        }
    }

    public void setWidget(adg_2 adg_22) {
        super.setWidget(adg_22);
        if (adg_22 instanceof azc_0) {
            azc_0 azc_02 = (azc_0)adg_22;
            if (this.aVp) {
                azc_02.setScaled(this.aVo);
            }
            if (this.AC != null) {
                azc_02.setModulationColor(this.AC);
            }
        }
    }

    public boolean cc(int n2) {
        if (this.bPA && this.DD instanceof azc_0) {
            azc_0 azc_02 = (azc_0)this.DD;
            if (this.aHT != null) {
                azc_02.setPixmap(this.aHT);
            }
            if (this.aVp) {
                azc_02.setScaled(this.aVo);
            }
        }
        return super.cc(n2);
    }

    public void Pj() {
        for (int j = this.cch.size() - 1; j >= 0; --j) {
            ve_2 ve_22 = (ve_2)this.cch.get(j);
            if (!(ve_22 instanceof ur_1)) continue;
            this.DD.getEntity().removeAllChildren();
        }
        super.Pj();
    }

    public void a(air_1 air_12) {
        fm_0 fm_02 = (fm_0)air_12;
        super.a((air_1)fm_02);
        if (this.aVp) {
            fm_02.setScaled(this.aVo);
        }
        fm_02.setModulationColor(this.AC);
    }

    public void j() {
        super.j();
        this.aHT = null;
        this.AC = null;
    }

    public void b() {
        super.b();
        this.aVo = false;
        this.aVp = false;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == aHX) {
            this.setModulationColor(if_12.eK(string));
        } else if (n2 == aVq) {
            this.setScaled(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == aHX) {
            this.setModulationColor((vP)object);
        } else if (n2 == aVq) {
            this.setScaled(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

