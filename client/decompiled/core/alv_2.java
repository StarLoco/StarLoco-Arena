/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/*
 * Renamed from aLv
 */
public class alv_2 {
    private String name;
    private Class clazz;
    private Class lr;
    private Class ls;
    private String className;
    private ClassLoader civ;
    static Class OR;

    public void setName(String string) {
        this.name = string;
    }

    public String getName() {
        return this.name;
    }

    public void b(Class clazz) {
        this.clazz = clazz;
        if (clazz == null) {
            return;
        }
        this.civ = this.civ == null ? clazz.getClassLoader() : this.civ;
        this.className = this.className == null ? clazz.getName() : this.className;
    }

    public void setClassName(String string) {
        this.className = string;
    }

    public String getClassName() {
        return this.className;
    }

    public void c(Class clazz) {
        this.lr = clazz;
    }

    public void d(Class clazz) {
        this.ls = clazz;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.civ = classLoader;
    }

    public ClassLoader getClassLoader() {
        return this.civ;
    }

    public Class f(UI uI) {
        Class clazz;
        if (this.ls != null && ((clazz = this.g(uI)) == null || this.ls.isAssignableFrom(clazz))) {
            return clazz;
        }
        return this.lr == null ? this.g(uI) : this.lr;
    }

    public Class g(UI uI) {
        try {
            return this.aWr();
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            uI.l("Could not load a dependent class (" + noClassDefFoundError.getMessage() + ") for type " + this.name, 4);
        }
        catch (ClassNotFoundException classNotFoundException) {
            uI.l("Could not load class (" + this.className + ") for type " + this.name, 4);
        }
        return null;
    }

    public Class aWr() {
        if (this.clazz != null) {
            return this.clazz;
        }
        this.clazz = this.civ == null ? Class.forName(this.className) : this.civ.loadClass(this.className);
        return this.clazz;
    }

    public Object j(UI uI) {
        return this.X(uI);
    }

    private Object X(UI uI) {
        Class clazz = this.g(uI);
        if (clazz == null) {
            return null;
        }
        Object object = this.b(uI, clazz);
        if (object == null || this.lr == null) {
            return object;
        }
        if (this.ls != null && this.ls.isAssignableFrom(object.getClass())) {
            return object;
        }
        akm akm2 = (akm)this.b(uI, this.lr);
        if (akm2 == null) {
            return null;
        }
        akm2.P(object);
        return akm2;
    }

    public void h(UI uI) {
        if (this.clazz == null) {
            this.clazz = this.g(uI);
            if (this.clazz == null) {
                throw new eq_2("Unable to create class for " + this.getName());
            }
        }
        if (!(this.lr == null || this.ls != null && this.ls.isAssignableFrom(this.clazz))) {
            akm akm2 = (akm)this.b(uI, this.lr);
            if (akm2 == null) {
                throw new eq_2("Unable to create adapter object");
            }
            akm2.z(this.clazz);
        }
    }

    private Object b(UI uI, Class clazz) {
        try {
            Object object = this.b(clazz, uI);
            return object;
        }
        catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getTargetException();
            throw new eq_2("Could not create type " + this.name + " due to " + throwable, throwable);
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            String string = "Type " + this.name + ": A class needed by class " + clazz + " cannot be found: " + noClassDefFoundError.getMessage();
            throw new eq_2(string, noClassDefFoundError);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new eq_2("Could not create type " + this.name + " as the class " + clazz + " has no compatible constructor");
        }
        catch (InstantiationException instantiationException) {
            throw new eq_2("Could not create type " + this.name + " as the class " + clazz + " is abstract");
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new eq_2("Could not create type " + this.name + " as the constructor " + clazz + " is not accessible");
        }
        catch (Throwable throwable) {
            throw new eq_2("Could not create type " + this.name + " due to " + throwable, throwable);
        }
    }

    public Object b(Class clazz, UI uI) {
        Object[] objectArray;
        Constructor constructor = null;
        boolean bl2 = false;
        try {
            constructor = clazz.getConstructor(new Class[0]);
            bl2 = true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            constructor = clazz.getConstructor(OR == null ? (OR = alv_2.a("UI")) : OR);
            bl2 = false;
        }
        if (bl2) {
            objectArray = new Object[]{};
        } else {
            Object[] objectArray2 = new Object[1];
            objectArray = objectArray2;
            objectArray2[0] = uI;
        }
        Object t = constructor.newInstance(objectArray);
        uI.at(t);
        return t;
    }

    public boolean a(alv_2 alv_22, UI uI) {
        return alv_22 != null && alv_22.getClass() == this.getClass() && alv_22.g(uI).equals(this.g(uI)) && alv_22.f(uI).equals(this.f(uI)) && alv_22.lr == this.lr && alv_22.ls == this.ls;
    }

    public boolean b(alv_2 alv_22, UI uI) {
        ClassLoader classLoader;
        if (!(alv_22 != null && this.getClass() == alv_22.getClass() && this.getClassName().equals(alv_22.getClassName()) && this.K(this.lr).equals(this.K(alv_22.lr)) && this.K(this.ls).equals(this.K(alv_22.ls)))) {
            return false;
        }
        ClassLoader classLoader2 = alv_22.getClassLoader();
        return classLoader2 == (classLoader = this.getClassLoader()) || classLoader2 instanceof ny_1 && classLoader instanceof ny_1 && ((ny_1)classLoader2).sv().equals(((ny_1)classLoader).sv());
    }

    private String K(Class clazz) {
        return clazz == null ? "<null>" : clazz.getClass().getName();
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

