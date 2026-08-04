/*
 * Decompiled with CFR 0.152.
 */
public class awK {
    public static final String dio = "ant.reuse.loader";
    static Class cjN;

    public static ClassLoader a(UI uI, awq_0 awq_02) {
        return awK.a(uI, awq_02, false);
    }

    public static ClassLoader a(UI uI, awq_0 awq_02, boolean bl2) {
        String string = awq_02.aJC();
        Object object = uI.gi(string);
        if (!(object instanceof bk_2)) {
            throw new eq_2("The specified classpathref " + string + " does not reference a Path.");
        }
        String string2 = "ant.loader." + string;
        return awK.a(uI, (bk_2)object, string2, bl2);
    }

    public static ClassLoader a(UI uI, bk_2 bk_22, String string) {
        return awK.a(uI, bk_22, string, false);
    }

    public static ClassLoader a(UI uI, bk_2 bk_22, String string, boolean bl2) {
        return awK.a(uI, bk_22, string, bl2, awK.Q(uI));
    }

    public static ClassLoader a(UI uI, bk_2 bk_22, String string, boolean bl2, boolean bl3) {
        ClassLoader classLoader = null;
        if (string != null && bl3) {
            Object object = uI.gi(string);
            if (object != null && !(object instanceof ClassLoader)) {
                throw new eq_2("The specified loader id " + string + " does not reference a class loader");
            }
            classLoader = (ClassLoader)object;
        }
        if (classLoader == null) {
            classLoader = awK.a(uI, bk_22, bl2);
            if (string != null && bl3) {
                uI.o(string, classLoader);
            }
        }
        return classLoader;
    }

    public static ClassLoader a(UI uI, bk_2 bk_22, boolean bl2) {
        ny_1 ny_12 = uI.g(bk_22);
        if (bl2) {
            ny_12.ac(false);
            ny_12.sx();
        }
        return ny_12;
    }

    public static Object a(String string, ClassLoader classLoader) {
        return awK.a(string, classLoader, cjN == null ? (cjN = awK.a("java.lang.Object")) : cjN);
    }

    public static Object a(String string, ClassLoader classLoader, Class clazz) {
        try {
            Class<?> clazz2 = Class.forName(string, true, classLoader);
            Object obj = clazz2.newInstance();
            if (!clazz.isInstance(obj)) {
                throw new eq_2("Class of unexpected Type: " + string + " expected :" + clazz);
            }
            return obj;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new eq_2("Class not found: " + string, classNotFoundException);
        }
        catch (InstantiationException instantiationException) {
            throw new eq_2("Could not instantiate " + string + ". Specified class should have a no " + "argument constructor.", instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new eq_2("Could not instantiate " + string + ". Specified class should have a " + "public constructor.", illegalAccessException);
        }
        catch (LinkageError linkageError) {
            throw new eq_2("Class " + string + " could not be loaded because of an invalid dependency.", linkageError);
        }
    }

    public static gn_2 a(aat_0 aat_02) {
        return new gn_2(aat_02);
    }

    private static boolean Q(UI uI) {
        return uI.getProperty(dio) != null;
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    static boolean R(UI uI) {
        return awK.Q(uI);
    }
}

