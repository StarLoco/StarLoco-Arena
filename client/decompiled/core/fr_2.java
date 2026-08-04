/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Method;

/*
 * Renamed from FR
 */
class fr_2
extends amw_2 {
    private final Object aVx;
    private final Object aVy;
    private final hm_2 aVn;

    fr_2(hm_2 hm_22, Method method, Object object, Object object2) {
        super(method);
        this.aVn = hm_22;
        this.aVx = object;
        this.aVy = object2;
    }

    Object a(UI uI, Object object, Object object2) {
        if (!this.getMethod().getName().endsWith("Configured")) {
            this.getMethod().invoke(object, this.aVx);
        }
        return this.aVy;
    }

    Object AI() {
        return this.aVx;
    }

    void j(Object object, Object object2) {
        if (this.getMethod().getName().endsWith("Configured")) {
            this.getMethod().invoke(object, this.aVx);
        }
    }
}

