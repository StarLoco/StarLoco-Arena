/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import org.apache.log4j.Logger;

/*
 * Renamed from Yz
 */
public class yz_0
extends rj_2
implements oc_0 {
    private static Logger a = Logger.getLogger(yz_0.class);
    public static final String TAG = "TiledPixmapBackground";
    private awO cbh = new awO();
    private static final acl_0 uG = new ym_0(new zY());
    public static final int aHX = "modulationColor".hashCode();

    public static yz_0 checkOut() {
        yz_0 yz_02;
        try {
            yz_02 = (yz_0)uG.adr();
            yz_02.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            yz_02 = new yz_0();
            yz_02.b();
        }
        return yz_02;
    }

    public void a(na_1 na_12) {
        if (na_12 instanceof ur_1) {
            this.setPixmap((ur_1)na_12);
        }
        super.a(na_12);
    }

    public void aaf() {
        super.aaf();
        if (this.cLZ == null) {
            a.warn((Object)"Appearance null !?");
            return;
        }
        adg_2 adg_22 = this.cLZ.getWidget();
        if (adg_22 == null) {
            a.warn((Object)"Widget null !?");
            return;
        }
        aht_1 aht_12 = adg_22.getContainer();
        if (aht_12 != null) {
            aht_12.setNeedsScissor(true);
        }
    }

    public String getTag() {
        return TAG;
    }

    public void setModulationColor(vP vP2) {
        this.cbh.setModulationColor(vP2);
    }

    public vP getModulationColor() {
        return this.cbh.getModulationColor();
    }

    public void setPixmap(ur_1 ur_12) {
        this.cbh.setPixmap(ur_12.getPixmap());
    }

    public awO getMesh() {
        return this.cbh;
    }

    public Entity getEntity() {
        return this.cbh.getEntity();
    }

    public void j() {
        super.j();
        this.cbh.j();
        this.cbh = null;
    }

    public void b() {
        super.b();
        this.cbh = new awO();
        this.cbh.b();
        na_1 na_12 = this.getParent();
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        yz_0 yz_02 = (yz_0)air_12;
        yz_02.setScaled(this.isScaled());
        yz_02.setModulationColor(this.getModulationColor());
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

