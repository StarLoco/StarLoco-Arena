/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/*
 * Renamed from FN
 */
class fn_2
extends rK {
    private final boolean aVr;
    private final Constructor aVs;
    private final Method aVm;
    private final hm_2 aVn;

    fn_2(hm_2 hm_22, Method method, boolean bl2, Constructor constructor, Method method2) {
        super(method);
        this.aVn = hm_22;
        this.aVr = bl2;
        this.aVs = constructor;
        this.aVm = method2;
    }

    public void a(UI uI, Object object, String string) {
        try {
            Object[] objectArray;
            if (this.aVr) {
                Object[] objectArray2 = new Object[2];
                objectArray2[0] = uI;
                objectArray = objectArray2;
                objectArray2[1] = string;
            } else {
                Object[] objectArray3 = new Object[1];
                objectArray = objectArray3;
                objectArray3[0] = string;
            }
            Object[] objectArray4 = objectArray;
            Object t = this.aVs.newInstance(objectArray4);
            if (uI != null) {
                uI.at(t);
            }
            this.aVm.invoke(object, t);
        }
        catch (InstantiationException instantiationException) {
            throw new eq_2(instantiationException);
        }
    }
}

