/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import org.apache.log4j.Logger;

/*
 * Renamed from aIA
 */
public class aia_2
extends pD
implements oc_0 {
    private static Logger a = Logger.getLogger(aia_2.class);
    private ahi_0 dQd;
    public static final String TAG = "pixmapBorder16";
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

    public void setModulationColor(vP vP2) {
        this.dQd.setModulationColor(vP2);
    }

    public vP getModulationColor() {
        return this.dQd != null ? this.dQd.getModulationColor() : null;
    }

    public void setPixmap(ur_1 ur_12) {
        this.dQd.a(ur_12.getPixmap(), ur_12.getPosition());
        this.apf = true;
        if (this.dQd.Gk()) {
            this.dQd.a(this.CB);
            Zb zb = (Zb)this.getParentOfType(Zb.class);
            if (zb != null) {
                zb.setBorder(this.CB);
            }
        }
    }

    public void setPixmaps(akq_1 akq_12, akq_1 akq_13, akq_1 akq_14, akq_1 akq_15, akq_1 akq_16, akq_1 akq_17, akq_1 akq_18, akq_1 akq_19, akq_1 akq_110, akq_1 akq_111, akq_1 akq_112, akq_1 akq_113, akq_1 akq_114, akq_1 akq_115, akq_1 akq_116, akq_1 akq_117) {
        this.dQd.setPixmaps(akq_12, akq_13, akq_14, akq_15, akq_16, akq_17, akq_18, akq_19, akq_110, akq_111, akq_112, akq_113, akq_114, akq_115, akq_116, akq_117);
        this.apf = true;
        if (this.dQd.Gk()) {
            this.dQd.a(this.CB);
            Zb zb = (Zb)this.getParentOfType(Zb.class);
            zb.setBorder(this.CB);
        }
    }

    public void setPixmaps(akq_1[] akq_1Array) {
        if (akq_1Array.length == 16) {
            this.dQd.setPixmaps(akq_1Array);
        } else {
            a.error((Object)"La taille du tableau pass\u00e9 en parametre ne correspond pas au nombre de pixmap donc on a besoin!");
        }
        this.apf = true;
        if (this.dQd.Gk()) {
            this.dQd.a(this.CB);
            Zb zb = (Zb)this.getParentOfType(Zb.class);
            zb.setBorder(this.CB);
        }
    }

    public ahi_0 getMesh() {
        return this.dQd;
    }

    public Entity getEntity() {
        return this.dQd.getEntity();
    }

    public void b() {
        super.b();
        this.dQd = new ahi_0();
        this.dQd.b();
    }

    public void j() {
        super.j();
        this.dQd.j();
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

