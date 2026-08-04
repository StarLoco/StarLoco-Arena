/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import org.apache.log4j.Logger;

/*
 * Renamed from pH
 */
public class ph_0
extends rj_2
implements ayi {
    private static Logger a = Logger.getLogger(ph_0.class);
    public static final String TAG = "PlainBackground";
    private aaH acn;
    public static final int j = "color".hashCode();

    public void f(na_1 na_12) {
        super.f(na_12);
    }

    public void a(na_1 na_12) {
        if (na_12 instanceof aab_0) {
            na_12.a(qe_1.bFa, new hq_0(this), false);
            this.setColor((aab_0)na_12);
            this.a((aab_0)na_12);
        }
        super.a(na_12);
    }

    public String getTag() {
        return TAG;
    }

    public void a(air_1 air_12) {
        ph_0 ph_02 = (ph_0)air_12;
        ph_02.setColor(this.getColor());
        super.a(air_12);
    }

    public void setColor(aab_0 aab_02) {
        this.setColor(aab_02.getColor());
    }

    public void setColor(vP vP2) {
        if (vP2 != null) {
            this.getMesh().setColor(vP2);
        }
    }

    public vP getColor() {
        return this.getMesh().getColor();
    }

    public aaH getMesh() {
        return this.acn;
    }

    public Entity getEntity() {
        return this.getMesh().apq();
    }

    public boolean isValidAdd(air_1 air_12) {
        if (air_12 instanceof aab_0 && ((aab_0)air_12).getColor() == null) {
            a.error((Object)"Tentative d'ajout d'un ColorElement sans couleur");
            return false;
        }
        return super.isValidAdd(air_12);
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

    public void b() {
        super.b();
        this.acn = new aaH();
        this.acn.b();
    }

    public void j() {
        super.j();
        this.acn.j();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != j) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setColor(if_12.eK(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != j) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setColor((vP)object);
        return true;
    }

    public void setModulationColor(vP vP2) {
        this.acn.setModulationColor(vP2);
    }

    public vP getModulationColor() {
        return this.acn.getModulationColor();
    }
}

