/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from qE
 */
public class qe_2
extends cp_1 {
    public static final String TAG = "And";

    public String getTag() {
        return TAG;
    }

    public boolean isValid(Object object) {
        if (this.cFn) {
            object = this.cFm;
        }
        for (alt_0 alt_02 : this.jG) {
            if (alt_02.isValid(object)) continue;
            return false;
        }
        return true;
    }

    public alt_0 bg() {
        qe_2 qe_22 = new qe_2();
        this.a((air_1)qe_22);
        return qe_22;
    }

    public Object getEncapsulatedObject() {
        return null;
    }
}

