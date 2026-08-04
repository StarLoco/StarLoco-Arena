/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from cC
 */
public class cc_0
extends alv_2 {
    private alv_2 jm;
    private rs_0 jn;

    public cc_0(alv_2 alv_22, rs_0 rs_02) {
        if (alv_22 instanceof cc_0) {
            cc_0 cc_02 = (cc_0)alv_22;
            rs_02.b(cc_02.jn);
            alv_22 = cc_02.jm;
        }
        this.jm = alv_22;
        this.jn = rs_02;
    }

    public void b(Class clazz) {
        throw new eq_2("Not supported");
    }

    public void setClassName(String string) {
        throw new eq_2("Not supported");
    }

    public String getClassName() {
        return this.jm.getClassName();
    }

    public void c(Class clazz) {
        throw new eq_2("Not supported");
    }

    public void d(Class clazz) {
        throw new eq_2("Not supported");
    }

    public void setClassLoader(ClassLoader classLoader) {
        throw new eq_2("Not supported");
    }

    public ClassLoader getClassLoader() {
        return this.jm.getClassLoader();
    }

    public Class f(UI uI) {
        return this.jm.f(uI);
    }

    public Class g(UI uI) {
        return this.jm.g(uI);
    }

    public void h(UI uI) {
        this.jm.h(uI);
    }

    public Object i(UI uI) {
        return this.jm.j(uI);
    }

    public rs_0 eU() {
        return this.jn;
    }

    public Object j(UI uI) {
        return this;
    }

    public boolean a(alv_2 alv_22, UI uI) {
        return alv_22 != null && alv_22.getClass() == this.getClass() && this.jm != null && this.jm.a(((cc_0)alv_22).jm, uI) && this.jn.aj(((cc_0)alv_22).jn);
    }

    public boolean b(alv_2 alv_22, UI uI) {
        return alv_22 != null && alv_22.getClass().getName().equals(this.getClass().getName()) && this.jm != null && this.jm.b(((cc_0)alv_22).jm, uI) && this.jn.aj(((cc_0)alv_22).jn);
    }
}

