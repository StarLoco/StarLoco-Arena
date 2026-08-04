/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Method;

abstract class rK {
    private Method method;

    protected rK(Method method) {
        this.method = method;
    }

    abstract void a(UI var1, Object var2, String var3);

    static Method a(rK rK2) {
        return rK2.method;
    }
}

