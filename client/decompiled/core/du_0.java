/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from du
 */
public class du_0
extends alt_0 {
    private static Logger a = Logger.getLogger(du_0.class);
    public static final String TAG = "isTrue";

    public String getTag() {
        return TAG;
    }

    public boolean isValid(Object object) {
        if (this.cFn) {
            object = this.cFm;
        }
        if (object instanceof Boolean) {
            return (Boolean)object;
        }
        return false;
    }

    public alt_0 bg() {
        du_0 du_02 = new du_0();
        this.a((air_1)du_02);
        return du_02;
    }
}

