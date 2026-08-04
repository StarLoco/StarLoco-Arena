/*
 * Decompiled with CFR 0.152.
 */
public abstract class kH
extends dn_2
implements Bk {
    public boolean a(axe axe2) {
        return this.isTraceEnabled();
    }

    public void a(axe axe2, String string) {
        this.trace(string);
    }

    public void a(axe axe2, String string, Object object) {
        this.i(string, object);
    }

    public void a(axe axe2, String string, Object object, Object object2) {
        this.a(string, object, object2);
    }

    public void a(axe axe2, String string, Object[] objectArray) {
        this.a(string, objectArray);
    }

    public void a(axe axe2, String string, Throwable throwable) {
        this.trace(string, throwable);
    }

    public boolean b(axe axe2) {
        return this.isDebugEnabled();
    }

    public void b(axe axe2, String string) {
        this.debug(string);
    }

    public void b(axe axe2, String string, Object object) {
        this.j(string, object);
    }

    public void b(axe axe2, String string, Object object, Object object2) {
        this.b(string, object, object2);
    }

    public void b(axe axe2, String string, Object[] objectArray) {
        this.b(string, objectArray);
    }

    public void b(axe axe2, String string, Throwable throwable) {
        this.debug(string, throwable);
    }

    public boolean c(axe axe2) {
        return this.isInfoEnabled();
    }

    public void c(axe axe2, String string) {
        this.info(string);
    }

    public void c(axe axe2, String string, Object object) {
        this.k(string, object);
    }

    public void c(axe axe2, String string, Object object, Object object2) {
        this.c(string, object, object2);
    }

    public void c(axe axe2, String string, Object[] objectArray) {
        this.c(string, objectArray);
    }

    public void c(axe axe2, String string, Throwable throwable) {
        this.info(string, throwable);
    }

    public boolean d(axe axe2) {
        return this.Ii();
    }

    public void d(axe axe2, String string) {
        this.warn(string);
    }

    public void d(axe axe2, String string, Object object) {
        this.l(string, object);
    }

    public void d(axe axe2, String string, Object object, Object object2) {
        this.d(string, object, object2);
    }

    public void d(axe axe2, String string, Object[] objectArray) {
        this.warn(string, objectArray);
    }

    public void d(axe axe2, String string, Throwable throwable) {
        this.warn(string, throwable);
    }

    public boolean e(axe axe2) {
        return this.Ij();
    }

    public void e(axe axe2, String string) {
        this.error(string);
    }

    public void e(axe axe2, String string, Object object) {
        this.m(string, object);
    }

    public void e(axe axe2, String string, Object object, Object object2) {
        this.e(string, object, object2);
    }

    public void e(axe axe2, String string, Object[] objectArray) {
        this.error(string, objectArray);
    }

    public void e(axe axe2, String string, Throwable throwable) {
        this.error(string, throwable);
    }

    public String toString() {
        return this.getClass().getName() + "(" + this.getName() + ")";
    }
}

