/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import org.apache.log4j.Logger;

/*
 * Renamed from PM
 */
public class pm_2
extends pD
implements oc_0 {
    private static Logger a = Logger.getLogger(pm_2.class);
    public static final String TAG = "PixmapBorder";
    private att bEl;
    public static final int aHX = "modulationColor".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof ur_1) {
            this.setPixmap((ur_1)na_12);
        }
        super.a(na_12);
    }

    public String getTag() {
        return TAG;
    }

    public void setPixmap(ur_1 ur_12) {
        Zb zb;
        switch (ur_12.getPosition()) {
            case dSm: {
                this.bEl.e(ur_12.getPixmap());
                break;
            }
            case dSo: {
                this.bEl.c(ur_12.getPixmap());
                break;
            }
            case dSq: {
                this.bEl.d(ur_12.getPixmap());
                break;
            }
            case dSt: {
                this.bEl.i(ur_12.getPixmap());
                break;
            }
            case dSv: {
                this.bEl.b(ur_12.getPixmap());
                break;
            }
            case dSy: {
                this.bEl.h(ur_12.getPixmap());
                break;
            }
            case dSA: {
                this.bEl.f(ur_12.getPixmap());
                break;
            }
            case dSC: {
                this.bEl.g(ur_12.getPixmap());
            }
        }
        if (this.bEl.Gk() && (zb = (Zb)this.getParentOfType(Zb.class)) != null) {
            this.bEl.a(this.CB);
            zb.setBorder(this.CB);
        }
    }

    public void setPixmaps(akq_1 akq_12, akq_1 akq_13, akq_1 akq_14, akq_1 akq_15, akq_1 akq_16, akq_1 akq_17, akq_1 akq_18, akq_1 akq_19) {
        this.bEl.setPixmaps(akq_12, akq_13, akq_14, akq_15, akq_16, akq_17, akq_18, akq_19);
        if (this.bEl.Gk()) {
            this.bEl.a(this.CB);
            Zb zb = (Zb)this.getParentOfType(Zb.class);
            zb.setBorder(this.CB);
        }
    }

    public att getMesh() {
        return this.bEl;
    }

    public Entity getEntity() {
        return this.bEl.getEntity();
    }

    public void setModulationColor(vP vP2) {
        this.bEl.setModulationColor(vP2);
    }

    public vP getModulationColor() {
        return this.bEl.getModulationColor();
    }

    public boolean isValidAdd(air_1 air_12) {
        if (air_12 instanceof ur_1 && ((ur_1)air_12).getPosition() == null) {
            a.error((Object)"Tentative d'ajout d'une Pixmap sans position");
            return false;
        }
        return super.isValidAdd(air_12);
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        pm_2 pm_22 = (pm_2)air_12;
        pm_22.setModulationColor(this.getModulationColor());
    }

    public void j() {
        super.j();
        this.bEl.j();
        this.bEl = null;
    }

    public void b() {
        super.b();
        this.bEl = new att();
        this.bEl.b();
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

