/*
 * Decompiled with CFR 0.152.
 */
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

public class auT
extends avj_0
implements akb_1 {
    protected static final Class[] cXr = new Class[0];
    private String[] cXs = null;
    protected String className = "SC";
    private Class cXt = null;
    private Class[] cXu = cXr;
    private Class cXv = null;
    static Class cXw;

    public auT(String string) {
        this.gk(string);
    }

    public auT(String string, InputStream inputStream) {
        this.b(string, inputStream);
    }

    public auT(String string, Reader reader) {
        this.a(string, reader);
    }

    public auT(ahr_1 ahr_12, ClassLoader classLoader) {
        this.e(classLoader);
        this.a(ahr_12);
    }

    public auT(ahr_1 ahr_12, Class clazz, Class[] classArray, ClassLoader classLoader) {
        this.x(clazz);
        this.e(classArray);
        this.e(classLoader);
        this.a(ahr_12);
    }

    public auT(ahr_1 ahr_12, String string, Class clazz, Class[] classArray, ClassLoader classLoader) {
        this.setClassName(string);
        this.x(clazz);
        this.e(classArray);
        this.e(classLoader);
        this.a(ahr_12);
    }

    public auT() {
    }

    public void s(String[] stringArray) {
        this.aIo();
        this.cXs = stringArray;
    }

    public void setClassName(String string) {
        if (string == null) {
            throw new NullPointerException();
        }
        this.aIo();
        this.className = string;
    }

    public void x(Class clazz) {
        this.aIo();
        this.cXt = clazz;
    }

    public void y(Class clazz) {
        this.x(clazz);
    }

    public void e(Class[] classArray) {
        if (classArray == null) {
            throw new NullPointerException("Zero implemented types must be specified as \"new Class[0]\", not \"null\"");
        }
        this.aIo();
        this.cXu = classArray;
    }

    public void f(Class[] classArray) {
        this.e(classArray);
    }

    public void a(ahr_1 ahr_12) {
        this.aIn();
        kh_1 kh_12 = this.f(ahr_12);
        ayp_0 ayp_02 = this.a(ahr_12.RR(), kh_12);
        GN gN = new GN(ahr_12);
        while (!ahr_12.awY().isEOF()) {
            gN.c(ayp_02);
        }
        this.cXv = this.a(kh_12, this.className);
    }

    protected final kh_1 f(ahr_1 ahr_12) {
        kh_1 kh_12 = new kh_1(ahr_12 == null ? null : ahr_12.getFileName());
        if (this.cXs != null) {
            for (int j = 0; j < this.cXs.length; ++j) {
                ahr_1 ahr_13 = new ahr_1(null, new StringReader(this.cXs[j]));
                kh_12.a(new GN(ahr_13).QP());
                if (ahr_13.awY().isEOF()) continue;
                throw new ajy_2("Unexpected token \"" + ahr_13.awY() + "\" in default import", ahr_13.RR());
            }
        }
        if (ahr_12 != null) {
            GN gN = new GN(ahr_12);
            while (ahr_12.awY().dN("import")) {
                kh_12.a(gN.QO());
            }
        }
        return kh_12;
    }

    protected ayp_0 a(lc_0 lc_02, kh_1 kh_12) {
        String string = this.className;
        int n2 = string.lastIndexOf(46);
        if (n2 != -1) {
            kh_12.a(new azm_0(lc_02, string.substring(0, n2)));
            string = string.substring(n2 + 1);
        }
        ayp_0 ayp_02 = new ayp_0(lc_02, null, 1, string, this.a(lc_02, this.cXt), this.a(lc_02, this.cXu));
        kh_12.b(ayp_02);
        return ayp_02;
    }

    protected final Class a(kh_1 kh_12, String string) {
        ClassLoader classLoader = this.d(kh_12);
        try {
            return classLoader.loadClass(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new aHY("SNO: Generated compilation unit does not declare class \"" + string + "\"");
        }
    }

    public Class getClazz() {
        if (this.getClass() != (cXw == null ? (cXw = auT.a("auT")) : cXw)) {
            throw new IllegalStateException("Must not be called on derived instances");
        }
        if (this.cXv == null) {
            throw new IllegalStateException("Must only be called after \"cook()\"");
        }
        return this.cXv;
    }

    public Object g(Reader reader) {
        this.h(reader);
        try {
            return this.getClazz().newInstance();
        }
        catch (InstantiationException instantiationException) {
            ajy_2 ajy_22 = new ajy_2("Class is abstract, an interface, an array class, a primitive type, or void; or has no zero-parameter constructor", null);
            ajy_22.initCause(instantiationException);
            throw ajy_22;
        }
        catch (IllegalAccessException illegalAccessException) {
            ajy_2 ajy_23 = new ajy_2("The class or its zero-parameter constructor is not accessible", null);
            ajy_23.initCause(illegalAccessException);
            throw ajy_23;
        }
    }

    public static Object a(ahr_1 ahr_12, Class clazz, ClassLoader classLoader) {
        Class[] classArray;
        Class clazz2 = clazz != null && !clazz.isInterface() ? clazz : null;
        if (clazz != null && clazz.isInterface()) {
            Class[] classArray2 = new Class[1];
            classArray = classArray2;
            classArray2[0] = clazz;
        } else {
            classArray = new Class[]{};
        }
        return auT.a(ahr_12, "SC", clazz2, classArray, classLoader);
    }

    public static Object a(ahr_1 ahr_12, String string, Class clazz, Class[] classArray, ClassLoader classLoader) {
        auT auT2 = new auT();
        auT2.setClassName(string);
        auT2.x(clazz);
        auT2.e(classArray);
        auT2.e(classLoader);
        auT2.a(ahr_12);
        Class clazz2 = auT2.getClazz();
        try {
            return clazz2.newInstance();
        }
        catch (InstantiationException instantiationException) {
            throw new ajy_2("Cannot instantiate abstract class -- one or more method implementations are missing", null);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new aHY(illegalAccessException.toString());
        }
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

