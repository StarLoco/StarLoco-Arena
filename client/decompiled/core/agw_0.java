/*
 * Decompiled with CFR 0.152.
 */
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/*
 * Renamed from aGW
 */
public class agw_0 {
    static final String dJP = "Caught Exception while in Loader.getResource. This may be innocuous.";
    private static boolean dJQ = false;
    public static final String dJR = "logback.ignoreTCL";

    public static List b(String string, ClassLoader classLoader) {
        ArrayList<URL> arrayList = new ArrayList<URL>();
        Enumeration<URL> enumeration = classLoader.getResources(string);
        while (enumeration.hasMoreElements()) {
            URL uRL = enumeration.nextElement();
            arrayList.add(uRL);
        }
        return arrayList;
    }

    public static URL c(String string, ClassLoader classLoader) {
        try {
            return classLoader.getResource(string);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static URL ln(String string) {
        return agw_0.c(string, agw_0.class.getClassLoader());
    }

    public static ClassLoader aTa() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class a(String string, vU vU2) {
        ClassLoader classLoader = vU2.getClass().getClassLoader();
        return classLoader.loadClass(string);
    }

    public static Class loadClass(String string) {
        if (dJQ) {
            return Class.forName(string);
        }
        try {
            return agw_0.aTa().loadClass(string);
        }
        catch (Throwable throwable) {
            return Class.forName(string);
        }
    }

    static {
        String string = dh_2.getSystemProperty(dJR, null);
        if (string != null) {
            dJQ = dh_2.toBoolean(string, true);
        }
    }
}

