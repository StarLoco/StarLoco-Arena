/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;

/*
 * Renamed from hX
 */
public class hx_2 {
    private static final ga_2 xa = ga_2.Qo();
    static Class xb;

    public static void setContextClassLoader(ClassLoader classLoader) {
        Thread thread = Thread.currentThread();
        thread.setContextClassLoader(classLoader);
    }

    public static ClassLoader getContextClassLoader() {
        Thread thread = Thread.currentThread();
        return thread.getContextClassLoader();
    }

    public static boolean ll() {
        return true;
    }

    private static File g(File file) {
        if (file != null) {
            try {
                file = xa.dZ(file.getAbsolutePath());
            }
            catch (eq_2 eq_22) {
                // empty catch block
            }
        }
        return file;
    }

    public static File e(Class clazz) {
        return hx_2.g(Hs.e(clazz));
    }

    public static File a(ClassLoader classLoader, String string) {
        if (classLoader == null) {
            classLoader = (xb == null ? (xb = hx_2.a("hX")) : xb).getClassLoader();
        }
        return hx_2.g(Hs.a(classLoader, string));
    }

    public static String az(String string) {
        return string.replace('.', '/') + ".class";
    }

    public static boolean b(ClassLoader classLoader, String string) {
        return classLoader.getResource(hx_2.az(string)) != null;
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

