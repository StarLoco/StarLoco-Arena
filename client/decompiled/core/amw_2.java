/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Method;

/*
 * Renamed from amw
 */
abstract class amw_2 {
    private Method method;

    protected amw_2(Method method) {
        this.method = method;
    }

    Method getMethod() {
        return this.method;
    }

    boolean aBL() {
        return false;
    }

    Object AI() {
        return null;
    }

    abstract Object a(UI var1, Object var2, Object var3);

    void j(Object object, Object object2) {
    }

    static Method a(amw_2 amw_22) {
        return amw_22.method;
    }
}

