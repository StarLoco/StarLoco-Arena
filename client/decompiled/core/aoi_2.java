/*
 * Decompiled with CFR 0.152.
 */
import java.util.Collection;

/*
 * Renamed from aoI
 */
public class aoi_2
extends pt_2 {
    public static final String TAG = "CollectionCondition";
    public static final String kW = "size";

    public String getTag() {
        return TAG;
    }

    public boolean isValid(Object object) {
        if (this.cFn) {
            object = this.cFm;
        }
        if (this.fZ != null && this.fZ.equalsIgnoreCase(kW)) {
            if (object instanceof Collection) {
                return this.dD.isValid(((Collection)object).size());
            }
            if (object instanceof Object[]) {
                return this.dD.isValid(((Object[])object).length);
            }
            return false;
        }
        return false;
    }
}

