/*
 * Decompiled with CFR 0.152.
 */
import java.util.Map;

/*
 * Renamed from jc
 */
final class jc_1
implements nm_1 {
    private final Map zp;

    jc_1(Map map) {
        this.zp = map;
    }

    public final boolean i(Object object, Object object2) {
        if (object2 == null && !this.zp.containsKey(object)) {
            return false;
        }
        Object v = this.zp.get(object);
        return v == object2 || v != null && v.equals(object2);
    }
}

