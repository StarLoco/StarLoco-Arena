/*
 * Decompiled with CFR 0.152.
 */
public class azB
extends cp_1 {
    public static final String TAG = "Or";

    public String getTag() {
        return TAG;
    }

    public boolean isValid(Object object) {
        if (this.cFn) {
            object = this.cFm;
        }
        for (alt_0 alt_02 : this.jG) {
            if (!alt_02.isValid(object)) continue;
            return true;
        }
        return false;
    }

    public alt_0 bg() {
        azB azB2 = new azB();
        this.a((air_1)azB2);
        return azB2;
    }
}

