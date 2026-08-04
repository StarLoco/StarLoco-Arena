/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class aqY
extends rK {
    private final Method aVm;
    private final Class cPc;
    private final hm_2 aVn;

    aqY(hm_2 hm_22, Method method, Method method2, Class clazz) {
        super(method);
        this.aVn = hm_22;
        this.aVm = method2;
        this.cPc = clazz;
    }

    public void a(UI uI, Object object, String string) {
        try {
            this.aVm.invoke(object, this.cPc.getMethod("valueOf", hm_2.avl == null ? (hm_2.avl = hm_2.a("java.lang.String")) : hm_2.avl).invoke(null, string));
        }
        catch (InvocationTargetException invocationTargetException) {
            if (invocationTargetException.getTargetException() instanceof IllegalArgumentException) {
                throw new eq_2("'" + string + "' is not a permitted value for " + this.cPc.getName());
            }
            throw hm_2.b(invocationTargetException);
        }
        catch (Exception exception) {
            throw new eq_2(exception);
        }
    }
}

