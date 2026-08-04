/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class aDe
extends alt_0 {
    private static Logger a = Logger.getLogger(du_0.class);
    public static final String TAG = "isFalse";

    public String getTag() {
        return TAG;
    }

    public boolean isValid(Object object) {
        if (this.cFn) {
            object = this.cFm;
        }
        if (object instanceof Boolean) {
            return (Boolean)object == false;
        }
        return false;
    }
}

