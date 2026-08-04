/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import org.apache.log4j.Logger;

/*
 * Renamed from aCF
 */
public class acf_2
extends rj_2
implements oc_0 {
    private static Logger a = Logger.getLogger(acf_2.class);
    public static final String TAG = "PixmapBackground";
    private xm_1 duC = new xm_1();
    private static final acl_0 uG = new ym_0(new my_2());
    public static final int aHX = "modulationColor".hashCode();

    public static acf_2 checkOut() {
        acf_2 acf_22;
        try {
            acf_22 = (acf_2)uG.adr();
            acf_22.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            acf_22 = new acf_2();
            acf_22.b();
        }
        return acf_22;
    }

    public void a(na_1 na_12) {
        if (na_12 instanceof ur_1) {
            this.setPixmap((ur_1)na_12);
        }
        super.a(na_12);
    }

    public String getTag() {
        return TAG;
    }

    public void setModulationColor(vP vP2) {
        this.duC.setModulationColor(vP2);
    }

    public vP getModulationColor() {
        return this.duC.getModulationColor();
    }

    public void setPixmap(ur_1 ur_12) {
        switch (ur_12.getPosition()) {
            case dSm: {
                this.duC.e(ur_12.getPixmap());
                break;
            }
            case dSo: {
                this.duC.c(ur_12.getPixmap());
                break;
            }
            case dSq: {
                this.duC.d(ur_12.getPixmap());
                break;
            }
            case dSt: {
                this.duC.i(ur_12.getPixmap());
                break;
            }
            case dSu: {
                this.duC.j(ur_12.getPixmap());
                break;
            }
            case dSv: {
                this.duC.b(ur_12.getPixmap());
                break;
            }
            case dSy: {
                this.duC.h(ur_12.getPixmap());
                break;
            }
            case dSA: {
                this.duC.f(ur_12.getPixmap());
                break;
            }
            case dSC: {
                this.duC.g(ur_12.getPixmap());
            }
        }
    }

    public void setPixmaps(akq_1 akq_12, akq_1 akq_13, akq_1 akq_14, akq_1 akq_15, akq_1 akq_16, akq_1 akq_17, akq_1 akq_18, akq_1 akq_19, akq_1 akq_110) {
        this.duC.setPixmaps(akq_12, akq_13, akq_14, akq_15, akq_16, akq_17, akq_18, akq_19, akq_110);
        if (akq_12 == null || akq_13 == null || akq_14 == null || akq_15 == null || akq_16 == null || akq_17 == null || akq_18 == null || akq_19 == null || akq_110 == null) {
            a.error((Object)"Une des pixmaps pass\u00e9e est nulle !");
        }
    }

    public void setPixmaps(akq_1[] akq_1Array) {
        if (akq_1Array.length == 9) {
            this.duC.setPixmaps(akq_1Array);
        } else {
            a.error((Object)"La taille du tableau pass\u00e9 en parametre ne correspond pas au nombre de pixmap donc on a besoin!");
        }
        if (akq_1Array[0] == null || akq_1Array[1] == null || akq_1Array[2] == null || akq_1Array[3] == null || akq_1Array[4] == null || akq_1Array[5] == null || akq_1Array[6] == null || akq_1Array[7] == null || akq_1Array[8] == null) {
            a.error((Object)"Une des pixmaps pass\u00e9e est nulle !");
        }
    }

    public void setPixmaps(akq_1 akq_12) {
        this.duC.setPixmaps(akq_12);
        if (akq_12 == null) {
            a.error((Object)"Une des pixmaps pass\u00e9e est nulle !");
        }
    }

    public xm_1 getMesh() {
        return this.duC;
    }

    public Entity getEntity() {
        return this.duC.getEntity();
    }

    public void setScaled(boolean bl2) {
        super.setScaled(bl2);
        this.duC.setScaled(bl2);
    }

    public void j() {
        super.j();
        this.duC.j();
        this.duC = null;
    }

    public void b() {
        super.b();
        this.duC = new xm_1();
        this.duC.b();
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        acf_2 acf_22 = (acf_2)air_12;
        acf_22.setScaled(this.isScaled());
        acf_22.setModulationColor(this.getModulationColor());
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != aHX) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setModulationColor(if_12.eK(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != aHX) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setModulationColor((vP)object);
        return true;
    }
}

