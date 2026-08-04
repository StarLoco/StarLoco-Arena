/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import java.util.Map;

/*
 * Renamed from dg
 */
final class dg_2
extends ClassLoader {
    private final Map le = new HashMap();

    private dg_2(ClassLoader classLoader) {
        super(classLoader);
    }

    protected Class loadClass(String string, boolean bl2) {
        Class clazz = (Class)this.le.get(string);
        if (clazz != null) {
            return clazz;
        }
        return super.loadClass(string, bl2);
    }

    private void addAuxiliaryClass(Class clazz) {
        Class<?>[] classArray;
        if (this.le.containsKey(clazz.getName())) {
            return;
        }
        try {
            classArray = super.loadClass(clazz.getName(), false);
            if (classArray != clazz) {
                throw new aHY("Trying to add an auxiliary class \"" + clazz.getName() + "\" while another class with the same name is already loaded");
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        this.le.put(clazz.getName(), clazz);
        classArray = clazz.getSuperclass();
        if (classArray != null) {
            this.addAuxiliaryClass((Class)classArray);
        }
        classArray = clazz.getInterfaces();
        for (int j = 0; j < classArray.length; ++j) {
            this.addAuxiliaryClass(classArray[j]);
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof dg_2)) {
            return false;
        }
        dg_2 dg_22 = (dg_2)object;
        ClassLoader classLoader = this.getParent();
        ClassLoader classLoader2 = dg_22.getParent();
        if (classLoader == null ? classLoader2 != null : !classLoader.equals(classLoader2)) {
            return false;
        }
        return ((Object)this.le).equals(dg_22.le);
    }

    public int hashCode() {
        ClassLoader classLoader = this.getParent();
        return (classLoader == null ? 0 : classLoader.hashCode()) ^ ((Object)this.le).hashCode();
    }

    dg_2(ClassLoader classLoader, jm_1 jm_12) {
        this(classLoader);
    }

    static void a(dg_2 dg_22, Class clazz) {
        dg_22.addAuxiliaryClass(clazz);
    }
}

