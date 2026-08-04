/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/*
 * Renamed from wE
 */
class we_1
extends asn {
    private Class clazz;
    private apm_0 avk;
    static Class avl;

    public we_1(Class clazz, apm_0 apm_02) {
        this.clazz = clazz;
        this.avk = apm_02;
    }

    protected adi_0[] lK() {
        Constructor<?>[] constructorArray = this.clazz.getDeclaredConstructors();
        adi_0[] adi_0Array = new adi_0[constructorArray.length];
        for (int j = 0; j < constructorArray.length; ++j) {
            adi_0Array[j] = new asd_0(this, constructorArray[j]);
        }
        return adi_0Array;
    }

    protected ff_2[] lL() {
        Method[] methodArray = this.clazz.getDeclaredMethods();
        ArrayList<ff_2> arrayList = new ArrayList<ff_2>();
        for (int j = 0; j < methodArray.length; ++j) {
            Method method = methodArray[j];
            arrayList.add(new oe_0(this, method));
        }
        if (methodArray.length == 0 && this.clazz.isArray()) {
            arrayList.add(new wn_1(this));
        }
        return arrayList.toArray(new ff_2[arrayList.size()]);
    }

    protected jz_0[] lM() {
        Field[] fieldArray = this.clazz.getDeclaredFields();
        jz_0[] jz_0Array = new jz_0[fieldArray.length];
        for (int j = 0; j < fieldArray.length; ++j) {
            jz_0Array[j] = new xf_0(this, fieldArray[j]);
        }
        return jz_0Array;
    }

    protected asn[] lN() {
        return this.b(this.clazz.getDeclaredClasses());
    }

    protected asn lO() {
        Class<?> clazz = this.clazz.getDeclaringClass();
        if (clazz == null) {
            return null;
        }
        return this.i(clazz);
    }

    protected asn lP() {
        if (Modifier.isStatic(this.clazz.getModifiers())) {
            return null;
        }
        return this.ic();
    }

    protected asn lQ() {
        Class clazz = this.clazz.getSuperclass();
        return clazz == null ? null : this.i(clazz);
    }

    protected asn[] lR() {
        return this.b(this.clazz.getInterfaces());
    }

    protected String lS() {
        return sA.cb(this.clazz.getName());
    }

    public amf ib() {
        return we_1.ed(this.clazz.getModifiers());
    }

    public boolean isFinal() {
        return Modifier.isFinal(this.clazz.getModifiers());
    }

    public boolean isInterface() {
        return this.clazz.isInterface();
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(this.clazz.getModifiers());
    }

    public boolean isArray() {
        return this.clazz.isArray();
    }

    protected asn lU() {
        Class<?> clazz = this.clazz.getComponentType();
        return clazz == null ? null : this.i(clazz);
    }

    public boolean isPrimitive() {
        return this.clazz.isPrimitive();
    }

    public boolean lT() {
        return this.clazz == Byte.TYPE || this.clazz == Short.TYPE || this.clazz == Integer.TYPE || this.clazz == Long.TYPE || this.clazz == Character.TYPE || this.clazz == Float.TYPE || this.clazz == Double.TYPE;
    }

    public String toString() {
        int n2 = 0;
        Class<?> clazz = this.clazz;
        while (clazz.isArray()) {
            ++n2;
            clazz = clazz.getComponentType();
        }
        String string = clazz.getName();
        while (n2-- > 0) {
            string = string + "[]";
        }
        return string;
    }

    private asn i(Class clazz) {
        asn asn2;
        try {
            asn2 = this.avk.lT(sA.cb(clazz.getName()));
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new aHY("Loading IClass \"" + clazz.getName() + "\": " + classNotFoundException);
        }
        if (asn2 == null) {
            throw new aHY("Cannot load class \"" + clazz.getName() + "\" through the given ClassLoader");
        }
        return asn2;
    }

    private asn[] b(Class[] classArray) {
        asn[] asnArray = new asn[classArray.length];
        for (int j = 0; j < classArray.length; ++j) {
            asnArray[j] = this.i(classArray[j]);
        }
        return asnArray;
    }

    private static amf ed(int n2) {
        return Modifier.isPrivate(n2) ? amf.cGq : (Modifier.isProtected(n2) ? amf.cGr : (Modifier.isPublic(n2) ? amf.cGt : amf.cGs));
    }

    static apm_0 a(we_1 we_12) {
        return we_12.avk;
    }

    static amf ee(int n2) {
        return we_1.ed(n2);
    }

    static asn[] a(we_1 we_12, Class[] classArray) {
        return we_12.b(classArray);
    }

    static asn a(we_1 we_12, Class clazz) {
        return we_12.i(clazz);
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

