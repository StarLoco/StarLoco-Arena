/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aAb
 */
public class aab_0
extends on_1
implements cn_1 {
    private static Logger a = Logger.getLogger(aab_0.class);
    public static final String TAG = "Color";
    public static final String doT = "NamedColor";
    private vP aZ = null;
    private acX doU = null;
    private String m_name = null;
    private static final acl_0 uG = new ym_0(new az_2());
    public static final int j = "color".hashCode();
    public static final int aru = "name".hashCode();
    public static final int arw = "position".hashCode();

    public static aab_0 checkOut() {
        aab_0 aab_02;
        try {
            aab_02 = (aab_0)uG.adr();
            aab_02.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            aab_02 = new aab_0();
            aab_02.b();
        }
        return aab_02;
    }

    public String getTag() {
        return TAG;
    }

    public void setColor(vP vP2) {
        if (this.aZ == vP2) {
            return;
        }
        this.aZ = vP2;
        this.f(new lu_1(this));
    }

    public vP getColor() {
        return this.aZ;
    }

    public acX getPosition() {
        return this.doU;
    }

    public void setPosition(acX acX2) {
        this.doU = acX2;
        this.f(new lu_1(this));
    }

    public String getName() {
        return this.m_name;
    }

    public void setName(String string) {
        this.m_name = string;
        this.f(new lu_1(this));
    }

    public void setup(and_0 and_02) {
        if (and_02 instanceof ajb_0) {
            ((ajb_0)and_02).setColor(this.aZ, this.m_name);
        }
    }

    public void j() {
        super.j();
        this.aZ = null;
        this.m_name = null;
        this.doU = null;
    }

    public void b() {
        super.b();
    }

    public void a(air_1 air_12) {
        aab_0 aab_02 = (aab_0)air_12;
        super.a((air_1)aab_02);
        aab_02.setColor(this.getColor());
        aab_02.m_name = this.m_name;
        aab_02.doU = this.doU;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == j) {
            this.setColor(if_12.eK(string));
        } else if (n2 == aru) {
            this.setName(if_12.eM(string));
        } else if (n2 == arw) {
            this.setPosition(acX.hx(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == j) {
            this.setColor((vP)object);
        } else if (n2 == aru) {
            this.setName(String.valueOf(object));
        } else if (n2 == arw) {
            this.setPosition((acX)((Object)object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

