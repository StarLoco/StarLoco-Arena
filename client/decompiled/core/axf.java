/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class axf
extends aNZ {
    private static Logger a = Logger.getLogger(axf.class);
    public static final String TAG = "Item";
    private String aPH = null;
    private String crg = null;
    private int cvl = 0;
    private jn_2 cri = null;
    public static final int crk = "attribute".hashCode();
    public static final int aPL = "field".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof jn_2) {
            this.cri = (jn_2)((Object)na_12);
        }
        super.a(na_12);
    }

    public String getTag() {
        return TAG;
    }

    public String getField() {
        return this.aPH;
    }

    public void setField(String string) {
        this.aPH = string;
    }

    public int getAttributeHash() {
        return this.cvl;
    }

    public String getAttribute() {
        return this.crg;
    }

    public void setAttribute(String string) {
        this.crg = string;
        this.cvl = this.crg != null ? this.crg.hashCode() : 0;
    }

    public jn_2 getResultProvider() {
        return this.cri;
    }

    public void setCondition(jn_2 jn_22) {
        this.cri = jn_22;
    }

    public void j() {
        super.j();
        this.aPH = null;
        this.crg = null;
        this.cri = null;
    }

    public void b() {
        super.b();
        this.cvl = 0;
    }

    public void a(air_1 air_12) {
        axf axf2 = (axf)air_12;
        super.a((air_1)axf2);
        axf2.crg = this.crg;
        axf2.cvl = this.cvl;
        axf2.aPH = this.aPH;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == crk) {
            this.setAttribute(if_12.eM(string));
        } else if (n2 == aPL) {
            this.setField(if_12.eM(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == crk) {
            this.setAttribute(String.valueOf(object));
        } else if (n2 == aPL) {
            this.setField(String.valueOf(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

