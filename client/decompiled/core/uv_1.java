/*
 * Decompiled with CFR 0.152.
 */
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import sun.reflect.Reflection;

/*
 * Renamed from Uv
 */
public class uv_1 {
    static final pj_1[] bPS = new pj_1[0];
    HashMap bPT = new HashMap();
    private static boolean bPU = false;

    public void a(un_1[] un_1Array) {
        int n2 = 0;
        pj_1[] pj_1Array = new pj_1[]{};
        do {
            n2 = this.a(un_1Array, n2 + pj_1Array.length);
            pj_1Array = this.b(un_1Array, n2);
            this.a(pj_1Array);
        } while (n2 != -1);
    }

    void a(pj_1[] pj_1Array) {
        Throwable throwable = new Throwable("local stack reference");
        StackTraceElement[] stackTraceElementArray = throwable.getStackTrace();
        int n2 = tk_2.a(stackTraceElementArray, pj_1Array);
        int n3 = stackTraceElementArray.length - n2;
        int n4 = pj_1Array.length - n2;
        ClassLoader classLoader = null;
        ClassLoader classLoader2 = null;
        int n5 = 0;
        for (int j = 0; j < n2; ++j) {
            abl_0 abl_02;
            Class<?> clazz = null;
            if (bPU) {
                clazz = Reflection.getCallerClass(n3 + j - n5 + 1);
            }
            pj_1 pj_12 = pj_1Array[n4 + j];
            String string = pj_12.acp.getClassName();
            if (clazz != null && string.equals(clazz.getName())) {
                classLoader = clazz.getClassLoader();
                if (classLoader2 == null) {
                    classLoader2 = clazz.getClassLoader();
                }
                abl_02 = this.t(clazz);
                pj_12.a(abl_02);
                continue;
            }
            ++n5;
            abl_02 = this.a(pj_12, classLoader);
            pj_12.a(abl_02);
        }
        this.a(n2, pj_1Array, classLoader2);
    }

    int a(un_1[] un_1Array, int n2) {
        int n3 = un_1Array.length;
        if (n2 < 0 || n2 >= n3) {
            return -1;
        }
        for (int j = n2; j < n3; ++j) {
            if (un_1Array[j].aqU != ama_0.dXn) continue;
            return j;
        }
        return -1;
    }

    private pj_1[] b(un_1[] un_1Array, int n2) {
        LinkedList<pj_1> linkedList = new LinkedList<pj_1>();
        int n3 = un_1Array.length;
        if (n2 < 0 || n2 >= n3) {
            return linkedList.toArray(bPS);
        }
        for (int j = n2; j < n3; ++j) {
            un_1 un_12 = un_1Array[j];
            if (un_12.aqU != ama_0.dXn) break;
            linkedList.add(un_12.AT());
        }
        return linkedList.toArray(bPS);
    }

    void a(int n2, pj_1[] pj_1Array, ClassLoader classLoader) {
        int n3 = pj_1Array.length - n2;
        for (int j = 0; j < n3; ++j) {
            pj_1 pj_12 = pj_1Array[j];
            abl_0 abl_02 = this.a(pj_12, classLoader);
            pj_12.a(abl_02);
        }
    }

    private abl_0 t(Class clazz) {
        String string = clazz.getName();
        abl_0 abl_02 = (abl_0)this.bPT.get(string);
        if (abl_02 != null) {
            return abl_02;
        }
        String string2 = this.k(clazz);
        String string3 = this.u(clazz);
        abl_02 = new abl_0(string3, string2);
        this.bPT.put(string, abl_02);
        return abl_02;
    }

    private abl_0 a(pj_1 pj_12, ClassLoader classLoader) {
        String string = pj_12.acp.getClassName();
        abl_0 abl_02 = (abl_0)this.bPT.get(string);
        if (abl_02 != null) {
            return abl_02;
        }
        Class clazz = this.d(classLoader, string);
        String string2 = this.k(clazz);
        String string3 = this.u(clazz);
        abl_02 = new abl_0(string3, string2, false);
        this.bPT.put(string, abl_02);
        return abl_02;
    }

    String k(Class clazz) {
        if (clazz == null) {
            return "na";
        }
        Package package_ = clazz.getPackage();
        if (package_ != null) {
            String string = package_.getImplementationVersion();
            if (string == null) {
                return "na";
            }
            return string;
        }
        return "na";
    }

    String u(Class clazz) {
        try {
            URL uRL;
            if (clazz != null && (uRL = clazz.getProtectionDomain().getCodeSource().getLocation()) != null) {
                String string = uRL.toString();
                String string2 = this.a(string, '/');
                if (string2 != null) {
                    return string2;
                }
                return this.a(string, '\\');
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return "na";
    }

    private String a(String string, char c) {
        int n2 = string.lastIndexOf(c);
        if (this.b(n2, string)) {
            n2 = string.lastIndexOf(c, n2 - 1);
            return string.substring(n2 + 1);
        }
        if (n2 > 0) {
            return string.substring(n2 + 1);
        }
        return null;
    }

    private boolean b(int n2, String string) {
        return n2 != -1 && n2 + 1 == string.length();
    }

    private Class c(ClassLoader classLoader, String string) {
        if (classLoader == null) {
            return null;
        }
        try {
            return classLoader.loadClass(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private Class d(ClassLoader classLoader, String string) {
        Class clazz = this.c(classLoader, string);
        if (clazz != null) {
            return clazz;
        }
        ClassLoader classLoader2 = Thread.currentThread().getContextClassLoader();
        if (classLoader2 != classLoader) {
            clazz = this.c(classLoader2, string);
        }
        if (clazz != null) {
            return clazz;
        }
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    static {
        try {
            Reflection.getCallerClass(2);
            bPU = true;
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
        }
        catch (NoSuchMethodError noSuchMethodError) {
        }
        catch (Throwable throwable) {
            System.err.println("Unexpected exception");
            throwable.printStackTrace();
        }
    }
}

