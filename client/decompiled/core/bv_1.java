/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from bv
 */
public abstract class bv_1
extends apn_1
implements mH {
    protected String gb = null;
    public static final int gc = "label".hashCode();
    public static final int gd = "state".hashCode();

    public void setLabel(String string) {
        this.gb = string;
    }

    public void setState(String string) {
        this.setLabel(string);
    }

    public String getLabel() {
        return this.gb;
    }

    public String getState() {
        return this.getLabel();
    }

    public void j() {
        super.j();
        this.gb = null;
    }

    public void a(air_1 air_12) {
        bv_1 bv_12 = (bv_1)air_12;
        super.a((air_1)bv_12);
        bv_12.gb = this.gb;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != gc && n2 != gd) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setLabel(if_12.eM(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != gc && n2 != gd) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setLabel(String.valueOf(object));
        return true;
    }
}

