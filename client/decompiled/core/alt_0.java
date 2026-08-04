/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from alt
 */
public abstract class alt_0
extends aNZ {
    protected String fZ = null;
    protected Object dE = null;
    protected Object cFm = null;
    protected boolean cFn = false;
    protected av_1 cFo;
    public static final boolean cFp = false;
    public static final int dL = "value".hashCode();
    public static final int cFq = "comparedValue".hashCode();
    public static final int bfn = "key".hashCode();

    public Object getValue() {
        return this.dE;
    }

    public void setValue(Object object) {
        if (object != null && !object.equals(this.dE) || this.dE != null && !this.dE.equals(object)) {
            this.dE = object;
            this.g(true);
        }
    }

    public Object getComparedValue() {
        return this.cFm;
    }

    public void setComparedValue(Object object) {
        if (object != null && !object.equals(this.cFm) || this.cFm != null && !this.cFm.equals(object) || object == null && this.cFm == null) {
            this.cFm = object;
            this.cFn = true;
            this.g(true);
        }
    }

    public void g(boolean bl2) {
        if (this.adf instanceof alt_0) {
            ((alt_0)this.adf).g(bl2);
        } else if (this.adf instanceof av_1) {
            ((av_1)this.adf).g(bl2);
        }
    }

    public String getKey() {
        return this.fZ;
    }

    public void setKey(String string) {
        this.fZ = string;
    }

    public av_1 getConditionParent() {
        return this.cFo;
    }

    public void setConditionParent(av_1 av_12) {
        this.cFo = av_12;
    }

    public void a(air_1 air_12) {
        alt_0 alt_02 = (alt_0)air_12;
        alt_02.setKey(this.fZ);
        alt_02.setValue(this.dE);
        if (this.cFn) {
            alt_02.setComparedValue(this.cFm);
        }
        super.a((air_1)alt_02);
    }

    public abstract boolean isValid(Object var1);

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == cFq) {
            this.setComparedValue(if_12.eM(string));
        } else if (n2 == dL) {
            this.setValue(if_12.eM(string));
        } else if (n2 == bfn) {
            this.setKey(if_12.eM(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == cFq) {
            this.setComparedValue(object);
        } else if (n2 == dL) {
            this.setValue(object);
        } else if (n2 == bfn) {
            this.setKey(String.valueOf(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

