/*
 * Decompiled with CFR 0.152.
 */
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.HashMap;

/*
 * Renamed from avj
 */
public class avj_0
extends aem_2
implements WU {
    private static final boolean DEBUG = false;
    private ClassLoader ddM = Thread.currentThread().getContextClassLoader();
    private Class[] ddN = null;
    private dg_2 ddO = null;
    private apm_0 avk = null;
    private ClassLoader ddP = null;
    protected boolean aEI;
    protected boolean aEJ = this.aEI = Boolean.getBoolean("org.codehaus.janino.source_debugging.enable");
    protected boolean aEK = this.aEI;
    static Class aIF;
    static Class ddQ;

    public static void main(String[] stringArray) {
        if (stringArray.length >= 1 && stringArray[0].equals("-help")) {
            System.out.println("Usage:");
            System.out.println("    org.codehaus.janino.SimpleCompiler <source-file> <class-name> { <argument> }");
            System.out.println("Reads a compilation unit from the given <source-file> and invokes method");
            System.out.println("\"public static void main(String[])\" of class <class-name>, passing the.");
            System.out.println("given <argument>s.");
            System.exit(1);
        }
        if (stringArray.length < 2) {
            System.err.println("Source file and/or class name missing; try \"-help\".");
            System.exit(1);
        }
        String string = stringArray[0];
        String string2 = stringArray[1];
        String[] stringArray2 = new String[stringArray.length - 2];
        System.arraycopy(stringArray, 2, stringArray2, 0, stringArray2.length);
        ClassLoader classLoader = new avj_0(string, new FileInputStream(string)).getClassLoader();
        Class<?> clazz = classLoader.loadClass(string2);
        Method method = clazz.getMethod("main", aIF == null ? (aIF = avj_0.a("[Ljava.lang.String;")) : aIF);
        method.invoke(null, new Object[]{stringArray2});
    }

    public avj_0(String string, Reader reader) {
        this.a(string, reader);
    }

    public avj_0(String string, InputStream inputStream) {
        this.b(string, inputStream);
    }

    public avj_0(String string) {
        this.gl(string);
    }

    public avj_0(ahr_1 ahr_12, ClassLoader classLoader) {
        this.e(classLoader);
        this.a(ahr_12);
    }

    public avj_0() {
    }

    public void e(ClassLoader classLoader) {
        this.a(classLoader, null);
    }

    public void a(ClassLoader classLoader, Class[] classArray) {
        this.aIo();
        this.ddM = classLoader != null ? classLoader : Thread.currentThread().getContextClassLoader();
        this.ddN = classArray;
    }

    public void c(boolean bl2, boolean bl3, boolean bl4) {
        this.aEI = bl2;
        this.aEJ = bl3;
        this.aEK = bl4;
    }

    public final void a(String string, Reader reader) {
        this.a(new ahr_1(string, reader));
    }

    public void a(ahr_1 ahr_12) {
        this.aIn();
        kh_1 kh_12 = new GN(ahr_12).QM();
        this.d(kh_12);
    }

    public void c(kh_1 kh_12) {
        this.aIn();
        this.d(kh_12);
    }

    protected final void aIn() {
        this.aIo();
        this.ddO = (dg_2)AccessController.doPrivileged(new jm_1(this));
        if (this.ddN != null) {
            for (int j = 0; j < this.ddN.length; ++j) {
                dg_2.a(this.ddO, this.ddN[j]);
            }
        }
        this.avk = new alc_0(this.ddO);
    }

    public ClassLoader getClassLoader() {
        if (this.getClass() != (ddQ == null ? (ddQ = avj_0.a("avj")) : ddQ)) {
            throw new IllegalStateException("Must not be called on derived instances");
        }
        if (this.ddP == null) {
            throw new IllegalStateException("Must only be called after \"cook()\"");
        }
        return this.ddP;
    }

    public boolean equals(Object object) {
        if (!(object instanceof avj_0)) {
            return false;
        }
        avj_0 avj_02 = (avj_0)object;
        if (this.getClass() != avj_02.getClass()) {
            return false;
        }
        if (this.ddP == null || avj_02.ddP == null) {
            throw new IllegalStateException("Equality can only be checked after cooking");
        }
        return this.ddP.equals(avj_02.ddP);
    }

    public int hashCode() {
        return this.ddO.hashCode();
    }

    protected atu_0 a(lc_0 lc_02, Class clazz) {
        asn asn2;
        if (clazz == null) {
            return null;
        }
        dg_2.a(this.ddO, clazz);
        try {
            asn2 = this.avk.lT(sA.cb(clazz.getName()));
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new aHY("Loading IClass \"" + clazz.getName() + "\": " + classNotFoundException);
        }
        if (asn2 == null) {
            throw new aHY("Cannot load class \"" + clazz.getName() + "\" through the given ClassLoader");
        }
        return new vq_1(lc_02, asn2);
    }

    protected atu_0[] a(lc_0 lc_02, Class[] classArray) {
        atu_0[] atu_0Array = new atu_0[classArray.length];
        for (int j = 0; j < classArray.length; ++j) {
            atu_0Array[j] = this.a(lc_02, classArray[j]);
        }
        return atu_0Array;
    }

    protected final ClassLoader d(kh_1 kh_12) {
        nw_2[] nw_2Array = new zh_2(kh_12, this.avk).b(this.aEI, this.aEJ, this.aEK);
        HashMap<String, byte[]> hashMap = new HashMap<String, byte[]>();
        for (int j = 0; j < nw_2Array.length; ++j) {
            nw_2 nw_22 = nw_2Array[j];
            hashMap.put(nw_22.aaB(), nw_22.toByteArray());
        }
        this.ddP = (ClassLoader)AccessController.doPrivileged(new jk_0(this, hashMap));
        return this.ddP;
    }

    protected void aIo() {
        if (this.ddO != null) {
            throw new IllegalStateException("Already cooked");
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

    static ClassLoader a(avj_0 avj_02) {
        return avj_02.ddM;
    }

    static dg_2 b(avj_0 avj_02) {
        return avj_02.ddO;
    }
}

