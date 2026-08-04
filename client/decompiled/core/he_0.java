/*
 * Decompiled with CFR 0.152.
 */
import java.util.Collection;

/*
 * Renamed from HE
 */
public class he_0
extends aNZ
implements jn_2 {
    public static final String TAG = "ValueReplacer";
    public static final String kW = "size";
    public static final String bfl = "concat";
    protected yw_1 bfm;
    private String fZ = null;
    private String ayx = null;
    public static final int dL = "value".hashCode();
    public static final int bfn = "key".hashCode();

    public String getTag() {
        return TAG;
    }

    public String getKey() {
        return this.fZ;
    }

    public void setKey(String string) {
        this.fZ = string;
    }

    public String getValue() {
        return this.ayx;
    }

    public void setValue(String string) {
        this.ayx = string;
    }

    public void setResultProviderParent(yw_1 yw_12) {
        this.bfm = yw_12;
    }

    public Object getResult(Object object) {
        if (this.fZ == null) {
            return null;
        }
        if (this.fZ.equalsIgnoreCase(kW)) {
            if (object instanceof Collection) {
                return ((Collection)object).size();
            }
            if (object instanceof Object[]) {
                return ((Object[])object).length;
            }
            return 0;
        }
        if (this.fZ.equalsIgnoreCase(bfl)) {
            return this.ayx + (object != null ? object.toString() : "");
        }
        return null;
    }

    public void a(air_1 air_12) {
        he_0 he_02 = (he_0)air_12;
        he_02.setKey(this.fZ);
        he_02.setValue(this.ayx);
        super.a((air_1)he_02);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == dL) {
            this.setValue(if_12.eM(string));
        } else if (n2 == bfn) {
            this.setKey(if_12.eM(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == dL) {
            this.setValue(String.valueOf(object));
        } else if (n2 == bfn) {
            this.setKey(String.valueOf(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

