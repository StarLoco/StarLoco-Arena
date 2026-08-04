/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aky
 */
public class aky_1
extends pt_2 {
    public static final String TAG = "Not";

    public String getTag() {
        return TAG;
    }

    public boolean isValid(Object object) {
        if (this.cFn) {
            object = this.cFm;
        }
        if (this.dD != null) {
            return !this.dD.isValid(object);
        }
        return false;
    }

    public alt_0 bg() {
        aky_1 aky_12 = new aky_1();
        this.a((air_1)aky_12);
        return aky_12;
    }
}

