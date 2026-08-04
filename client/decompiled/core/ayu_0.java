/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ayu
 */
public class ayu_0
extends aht_1 {
    public static final String TAG = "Look";
    private String m_name;
    public static final int aru = "name".hashCode();

    public String getTag() {
        return TAG;
    }

    public String getName() {
        return this.m_name;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public void validate() {
        super.validate();
    }

    public void yx() {
        super.yx();
        aab_2 aab_22 = (aab_2)this.getParentOfType(aab_2.class);
        aab_22.a(this);
    }

    public void EO() {
        aab_2 aab_22 = (aab_2)this.getParentOfType(aab_2.class);
        if (aab_22 != null) {
            aab_22.b(this);
        }
        super.EO();
    }

    public void j() {
        super.j();
        this.m_name = null;
    }

    public void a(air_1 air_12) {
        ayu_0 ayu_02 = (ayu_0)air_12;
        super.a(air_12);
        ayu_02.setName(this.m_name);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != aru) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setName(if_12.eM(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != aru) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setName((String)object);
        return true;
    }
}

