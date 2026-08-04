/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Method;

/*
 * Renamed from aLe
 */
public class ale_1
extends dm_1
implements akm {
    private Object dVr;
    static Class dVs;
    static Class bea;
    static Class OR;

    public ale_1() {
    }

    public ale_1(Object object) {
        this();
        this.P(object);
    }

    public static void a(Class clazz, UI uI) {
        if (!(dVs == null ? (dVs = ale_1.a("zp")) : dVs).isAssignableFrom(clazz)) {
            try {
                Method method = clazz.getMethod("execute", null);
                if (!Void.TYPE.equals(method.getReturnType())) {
                    String string = "return type of execute() should be void but was \"" + method.getReturnType() + "\" in " + clazz;
                    uI.l(string, 1);
                }
            }
            catch (NoSuchMethodException noSuchMethodException) {
                String string = "No public execute() in " + clazz;
                uI.l(string, 0);
                throw new eq_2(string);
            }
            catch (LinkageError linkageError) {
                String string = "Could not load " + clazz + ": " + linkageError;
                uI.l(string, 0);
                throw new eq_2(string, linkageError);
            }
        }
    }

    public void z(Class clazz) {
        ale_1.a(clazz, this.TP());
    }

    public void execute() {
        Method method;
        try {
            method = this.dVr.getClass().getMethod("setLocation", bea == null ? (bea = ale_1.a("axc")) : bea);
            if (method != null) {
                method.invoke(this.dVr, this.hW());
            }
        }
        catch (NoSuchMethodException noSuchMethodException) {
        }
        catch (Exception exception) {
            this.l("Error setting location in " + this.dVr.getClass(), 0);
            throw new eq_2(exception);
        }
        try {
            method = this.dVr.getClass().getMethod("setProject", OR == null ? (OR = ale_1.a("UI")) : OR);
            if (method != null) {
                method.invoke(this.dVr, this.TP());
            }
        }
        catch (NoSuchMethodException noSuchMethodException) {
        }
        catch (Exception exception) {
            this.l("Error setting project in " + this.dVr.getClass(), 0);
            throw new eq_2(exception);
        }
        try {
            fi_0.g(this.dVr);
        }
        catch (eq_2 eq_22) {
            throw eq_22;
        }
        catch (Exception exception) {
            this.l("Error in " + this.dVr.getClass(), 3);
            throw new eq_2(exception);
        }
    }

    public void P(Object object) {
        this.dVr = object;
    }

    public Object OV() {
        return this.dVr;
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }
}

