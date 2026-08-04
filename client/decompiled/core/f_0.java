/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import org.apache.log4j.Logger;

/*
 * Renamed from f
 */
public class f_0
extends pD
implements ayi {
    private static Logger a = Logger.getLogger(f_0.class);
    public static final String TAG = "PlainBorder";
    private wk_2 h = new wk_2();
    public static final int j = "color".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof aab_0) {
            na_12.a(qe_1.bFa, new axB(this), false);
            this.setColor((aab_0)na_12);
            this.a((aab_0)na_12);
        }
        super.a(na_12);
    }

    public String getTag() {
        return TAG;
    }

    public boolean isValidAdd(air_1 air_12) {
        if (air_12 instanceof aab_0 && ((aab_0)air_12).getColor() == null) {
            a.error((Object)"Tentative d'ajout d'un ColorElement sans couleur");
            return false;
        }
        return super.isValidAdd(air_12);
    }

    public wk_2 getMesh() {
        return this.h;
    }

    public Entity getEntity() {
        return this.h.getEntity();
    }

    public void setColor(aab_0 aab_02) {
        this.setColor(aab_02.getColor());
    }

    public void setColor(vP vP2) {
        this.h.setColor(vP2);
    }

    public vP getColor() {
        return this.h.getColor();
    }

    public void setModulationColor(vP vP2) {
        this.h.setModulationColor(vP2);
    }

    public vP getModulationColor() {
        return this.h.getModulationColor();
    }

    protected void a(aab_0 aab_02) {
        if (this.uA == null) {
            return;
        }
        for (int j = this.uA.size() - 1; j >= 0; --j) {
            na_1 na_12 = (na_1)this.uA.get(j);
            if (!(na_12 instanceof aab_0)) continue;
            this.k(na_12);
        }
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        f_0 f_02 = (f_0)air_12;
        f_02.setColor(this.h.getColor());
        f_02.setModulationColor(this.getModulationColor());
    }

    public void j() {
        super.j();
        this.h.j();
    }

    public void b() {
        super.b();
        this.h.b();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != j) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setColor(if_12.eK(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == j) {
            if (object != null) {
                this.setColor((vP)object);
            }
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

