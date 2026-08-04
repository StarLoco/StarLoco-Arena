/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aNQ
 */
public class anq_0
extends pt_2 {
    public static final String TAG = "ItemCondition";
    public static final int aPL = "field".hashCode();

    public String getTag() {
        return TAG;
    }

    public boolean isValid(Object object) {
        if (this.cFn) {
            object = this.cFm;
        }
        if (!(object instanceof qa_1) && !(object instanceof aho_0)) {
            return false;
        }
        if (object instanceof aho_0) {
            if (this.fZ != null) {
                return this.dD.isValid(((aho_0)object).getFieldValue(this.fZ));
            }
            return this.dD.isValid(object);
        }
        qa_1 qa_12 = (qa_1)object;
        sm_0 sm_02 = qa_12.getItem();
        if (sm_02 != null && sm_02.getValue() instanceof aho_0 && this.fZ != null) {
            return this.dD.isValid(sm_02.getFieldValue(this.fZ));
        }
        Object object2 = sm_02 == null ? null : sm_02.getValue();
        return this.dD.isValid(object2);
    }

    public void setField(String string) {
        this.setKey(string);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != aPL) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setField(if_12.eM(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != aPL) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setField(String.valueOf(object));
        return true;
    }
}

