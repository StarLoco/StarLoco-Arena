/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Method;

class arb
extends rK {
    private final Class cPc;
    private final Method aVm;
    private final hm_2 aVn;

    arb(hm_2 hm_22, Method method, Class clazz, Method method2) {
        super(method);
        this.aVn = hm_22;
        this.cPc = clazz;
        this.aVm = method2;
    }

    public void a(UI uI, Object object, String string) {
        try {
            aNk aNk2 = (aNk)this.cPc.newInstance();
            aNk2.setValue(string);
            this.aVm.invoke(object, aNk2);
        }
        catch (InstantiationException instantiationException) {
            throw new eq_2(instantiationException);
        }
    }
}

