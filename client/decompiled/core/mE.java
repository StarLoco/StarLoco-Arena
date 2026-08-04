/*
 * Decompiled with CFR 0.152.
 */
public class mE
extends aNZ {
    public static final String TAG = "data";
    private Object dE;
    public static final int dL = "value".hashCode();

    public String getTag() {
        return TAG;
    }

    public Object getValue() {
        return this.dE;
    }

    public void setValue(Object object) {
        this.dE = object;
    }

    public Object getElementValue() {
        return this.dE;
    }

    public void a(air_1 air_12) {
        mE mE2 = (mE)air_12;
        super.a(air_12);
        mE2.dE = this.dE;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != dL) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setValue(if_12.eM(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != dL) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setValue(object);
        return true;
    }
}

