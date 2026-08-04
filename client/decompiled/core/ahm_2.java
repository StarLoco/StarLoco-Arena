/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aHm
 */
public class ahm_2
extends pt_2 {
    public static final String TAG = "StringCondition";

    public String getTag() {
        return TAG;
    }

    public boolean isValid(Object object) {
        if (this.cFn) {
            object = this.cFm;
        }
        if (!(this.dE instanceof String) || !(object instanceof String) || this.dD == null || this.fZ == null) {
            return false;
        }
        String string = (String)object;
        if (this.fZ.equalsIgnoreCase("length")) {
            return this.dD.isValid(string.length());
        }
        if (this.fZ.equalsIgnoreCase("startsWith")) {
            return this.dD.isValid(string.startsWith((String)this.dE));
        }
        return false;
    }

    public alt_0 bg() {
        ahm_2 ahm_22 = new ahm_2();
        this.a((air_1)ahm_22);
        return ahm_22;
    }
}

