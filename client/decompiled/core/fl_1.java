/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * Renamed from FL
 */
class fl_1
extends rK {
    private final Method aVm;
    private final hm_2 aVn;

    fl_1(hm_2 hm_22, Method method, Method method2) {
        super(method);
        this.aVn = hm_22;
        this.aVm = method2;
    }

    public void a(UI uI, Object object, String string) {
        try {
            this.aVm.invoke(object, new Long(ayM.ka(string)));
        }
        catch (InvocationTargetException invocationTargetException) {
            throw invocationTargetException;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw illegalAccessException;
        }
        catch (Exception exception) {
            throw new eq_2(exception);
        }
    }
}

