/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/*
 * Renamed from aLZ
 */
class alz_0
extends amw_2 {
    static final int ADD = 1;
    static final int dXk = 2;
    private Constructor cRa;
    private int dXl;

    alz_0(Method method, Constructor constructor, int n2) {
        super(method);
        this.cRa = constructor;
        this.dXl = n2;
    }

    boolean aBL() {
        return true;
    }

    Object a(UI uI, Object object, Object object2) {
        if (object2 == null) {
            Object[] objectArray;
            if (this.cRa.getParameterTypes().length == 0) {
                objectArray = new Object[]{};
            } else {
                Object[] objectArray2 = new Object[1];
                objectArray = objectArray2;
                objectArray2[0] = uI;
            }
            object2 = this.cRa.newInstance(objectArray);
        }
        if (object2 instanceof cc_0) {
            object2 = ((cc_0)object2).i(uI);
        }
        if (this.dXl == 1) {
            this.m(object, object2);
        }
        return object2;
    }

    void j(Object object, Object object2) {
        if (this.dXl == 2) {
            this.m(object, object2);
        }
    }

    private void m(Object object, Object object2) {
        this.getMethod().invoke(object, object2);
    }
}

