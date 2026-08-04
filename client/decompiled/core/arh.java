/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Method;

class arh
extends rK {
    private final String cPi;
    private final Method aVm;
    private final hm_2 aVn;

    arh(hm_2 hm_22, Method method, String string, Method method2) {
        super(method);
        this.aVn = hm_22;
        this.cPi = string;
        this.aVm = method2;
    }

    public void a(UI uI, Object object, String string) {
        if (string.length() == 0) {
            throw new eq_2("The value \"\" is not a legal value for attribute \"" + this.cPi + "\"");
        }
        this.aVm.invoke(object, new Character(string.charAt(0)));
    }
}

