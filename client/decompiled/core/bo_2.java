/*
 * Decompiled with CFR 0.152.
 */
import java.util.Hashtable;

/*
 * Renamed from bO
 */
class bo_2
extends Hashtable {
    bo_2() {
    }

    private Object d(Object object) {
        return super.get(object);
    }

    public Object get(Object object) {
        Object object2 = this.d(object);
        if (object2 instanceof rs_0) {
            rs_0 rs_02 = (rs_0)object2;
            rs_02.LH();
            object2 = rs_02.adO();
        }
        return object2;
    }

    static Object a(bo_2 bo_22, Object object) {
        return bo_22.d(object);
    }
}

