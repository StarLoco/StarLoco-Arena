/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Method;

class ard
extends rK {
    private final Method aVm;
    private final hm_2 aVn;

    ard(hm_2 hm_22, Method method, Method method2) {
        super(method);
        this.aVn = hm_22;
        this.aVm = method2;
    }

    public void a(UI uI, Object object, String string) {
        try {
            this.aVm.invoke(object, Class.forName(string));
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new eq_2(classNotFoundException);
        }
    }
}

