/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;

public class ark
extends rj_2 {
    public static final String TAG = "SwitchingPlainBackground";
    private td_2 cPk = new td_2();
    public static final int avT = "duration".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof aab_0) {
            this.cPk.b(((aab_0)na_12).getColor());
        }
        super.a(na_12);
    }

    public String getTag() {
        return TAG;
    }

    public aqn_0 getMesh() {
        return this.cPk;
    }

    public Entity getEntity() {
        return null;
    }

    public int getDuration() {
        return this.cPk.getDuration();
    }

    public void setDuration(int n2) {
        this.cPk.setDuration(n2);
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        ark ark2 = (ark)air_12;
        ark2.setDuration(this.getDuration());
    }

    public void j() {
        super.j();
        this.cPk.j();
    }

    public void b() {
        super.b();
        this.cPk.b();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != avT) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setDuration(Gr.R(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != avT) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setDuration(Gr.R(object));
        return true;
    }
}

