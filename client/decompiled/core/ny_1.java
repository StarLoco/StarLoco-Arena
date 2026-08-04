/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/*
 * Renamed from ny
 */
public class ny_1
extends ClassLoader
implements afK {
    private static final ga_2 xa = ga_2.Qo();
    private static final int cq = 8192;
    private static final int OE = 256;
    private Vector OF = new Vector();
    private UI hL;
    private boolean OG = true;
    private Vector OH = new Vector();
    private Vector OI = new Vector();
    private boolean OJ = false;
    private ClassLoader OL = null;
    private Hashtable OM = new Hashtable();
    private static Map ON = Collections.synchronizedMap(new HashMap());
    private ClassLoader OO = null;
    private boolean OP = false;
    static Class OQ;
    static Class OR;

    public ny_1(ClassLoader classLoader, UI uI, bk_2 bk_22) {
        this.c(classLoader);
        this.f(bk_22);
        this.l(uI);
    }

    public ny_1() {
        this.c((ClassLoader)null);
    }

    public ny_1(UI uI, bk_2 bk_22) {
        this.c((ClassLoader)null);
        this.l(uI);
        this.f(bk_22);
    }

    public ny_1(ClassLoader classLoader, UI uI, bk_2 bk_22, boolean bl2) {
        this(uI, bk_22);
        if (classLoader != null) {
            this.c(classLoader);
        }
        this.ac(bl2);
        this.sx();
    }

    public ny_1(UI uI, bk_2 bk_22, boolean bl2) {
        this(null, uI, bk_22, bl2);
    }

    public ny_1(ClassLoader classLoader, boolean bl2) {
        this.c(classLoader);
        this.hL = null;
        this.OG = bl2;
    }

    public void l(UI uI) {
        this.hL = uI;
        if (uI != null) {
            uI.a(this);
        }
    }

    public void f(bk_2 bk_22) {
        this.OF.removeAllElements();
        if (bk_22 != null) {
            bk_2 bk_23 = bk_22.s("ignore");
            String[] stringArray = bk_23.list();
            for (int j = 0; j < stringArray.length; ++j) {
                try {
                    this.aZ(stringArray[j]);
                    continue;
                }
                catch (eq_2 eq_22) {
                    // empty catch block
                }
            }
        }
    }

    public void c(ClassLoader classLoader) {
        this.OL = classLoader == null ? (OQ == null ? (OQ = ny_1.a("ny")) : OQ).getClassLoader() : classLoader;
    }

    public void ac(boolean bl2) {
        this.OG = bl2;
    }

    protected void l(String string, int n2) {
        if (this.hL != null) {
            this.hL.l(string, n2);
        }
    }

    public void st() {
        if (this.OP) {
            throw new eq_2("Context loader has not been reset");
        }
        if (hx_2.ll()) {
            this.OO = hx_2.getContextClassLoader();
            ClassLoader classLoader = this;
            if (this.hL != null && "only".equals(this.hL.getProperty("build.sysclasspath"))) {
                classLoader = this.getClass().getClassLoader();
            }
            hx_2.setContextClassLoader(classLoader);
            this.OP = true;
        }
    }

    public void su() {
        if (hx_2.ll() && this.OP) {
            hx_2.setContextClassLoader(this.OO);
            this.OO = null;
            this.OP = false;
        }
    }

    public void aZ(String string) {
        File file = this.hL != null ? this.hL.gg(string) : new File(string);
        try {
            this.i(file);
        }
        catch (IOException iOException) {
            throw new eq_2(iOException);
        }
    }

    public void h(File file) {
        if (this.OF.contains(file)) {
            return;
        }
        this.OF.addElement(file);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    protected void i(File file) {
        Object object;
        Object object2;
        Object object3;
        Object object4;
        String string;
        block16: {
            String string2;
            block15: {
                block14: {
                    this.OF.addElement(file);
                    if (file.isDirectory()) {
                        return;
                    }
                    string2 = file.getAbsolutePath() + file.lastModified() + "-" + file.length();
                    string = (String)ON.get(string2);
                    if (string != null) break block16;
                    object4 = null;
                    object3 = null;
                    object4 = new ZipFile(file);
                    object3 = ((ZipFile)object4).getInputStream(new ZipEntry("META-INF/MANIFEST.MF"));
                    if (object3 != null) break block14;
                    ga_2.h((InputStream)object3);
                    if (object4 != null) {
                        ((ZipFile)object4).close();
                    }
                    return;
                }
                try {
                    object2 = new InputStreamReader((InputStream)object3, "UTF-8");
                    object = new xd_2((Reader)object2);
                    string = ((xd_2)object).DB().getAttributeValue("Class-Path");
                }
                catch (id_0 id_02) {
                    ga_2.h(object3);
                    if (object4 != null) {
                        ((ZipFile)object4).close();
                    }
                    break block15;
                    catch (Throwable throwable) {
                        ga_2.h(object3);
                        if (object4 != null) {
                            ((ZipFile)object4).close();
                        }
                        throw throwable;
                    }
                }
                ga_2.h((InputStream)object3);
                if (object4 != null) {
                    ((ZipFile)object4).close();
                }
            }
            if (string == null) {
                string = "";
            }
            ON.put(string2, string);
        }
        if (!"".equals(string)) {
            object4 = xa.o(file);
            object3 = new StringTokenizer(string);
            while (((StringTokenizer)object3).hasMoreTokens()) {
                object2 = ((StringTokenizer)object3).nextToken();
                object = new URL((URL)object4, (String)object2);
                if (!((URL)object).getProtocol().equals("file")) {
                    this.l("Skipping jar library " + (String)object2 + " since only relative URLs are supported by this" + " loader", 3);
                    continue;
                }
                String string3 = Hs.ez(((URL)object).getFile());
                File file2 = new File(string3);
                if (!file2.exists() || this.k(file2)) continue;
                this.i(file2);
            }
        }
    }

    public String sv() {
        StringBuffer stringBuffer = new StringBuffer();
        boolean bl2 = true;
        Enumeration enumeration = this.OF.elements();
        while (enumeration.hasMoreElements()) {
            if (!bl2) {
                stringBuffer.append(System.getProperty("path.separator"));
            } else {
                bl2 = false;
            }
            stringBuffer.append(((File)enumeration.nextElement()).getAbsolutePath());
        }
        return stringBuffer.toString();
    }

    public synchronized void ad(boolean bl2) {
        this.OJ = bl2;
    }

    public static void f(Class clazz) {
        Constructor<?>[] constructorArray = clazz.getDeclaredConstructors();
        if (constructorArray != null && constructorArray.length > 0 && constructorArray[0] != null) {
            String[] stringArray = new String[256];
            try {
                constructorArray[0].newInstance(stringArray);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void ba(String string) {
        this.OH.addElement(string + (string.endsWith(".") ? "" : "."));
    }

    public void bb(String string) {
        this.OI.addElement(string + (string.endsWith(".") ? "" : "."));
    }

    public Class bc(String string) {
        this.l("force loading " + string, 4);
        Class clazz = this.findLoadedClass(string);
        if (clazz == null) {
            clazz = this.findClass(string);
        }
        return clazz;
    }

    public Class bd(String string) {
        this.l("force system loading " + string, 4);
        Class clazz = this.findLoadedClass(string);
        if (clazz == null) {
            clazz = this.bj(string);
        }
        return clazz;
    }

    public InputStream getResourceAsStream(String string) {
        InputStream inputStream = null;
        if (this.bg(string)) {
            inputStream = this.bf(string);
            if (inputStream != null) {
                this.l("ResourceStream for " + string + " loaded from parent loader", 4);
            } else {
                inputStream = this.be(string);
                if (inputStream != null) {
                    this.l("ResourceStream for " + string + " loaded from ant loader", 4);
                }
            }
        } else {
            inputStream = this.be(string);
            if (inputStream != null) {
                this.l("ResourceStream for " + string + " loaded from ant loader", 4);
            } else {
                inputStream = this.bf(string);
                if (inputStream != null) {
                    this.l("ResourceStream for " + string + " loaded from parent loader", 4);
                }
            }
        }
        if (inputStream == null) {
            this.l("Couldn't load ResourceStream for " + string, 4);
        }
        return inputStream;
    }

    private InputStream be(String string) {
        InputStream inputStream = null;
        Enumeration enumeration = this.OF.elements();
        while (enumeration.hasMoreElements() && inputStream == null) {
            File file = (File)enumeration.nextElement();
            inputStream = this.a(file, string);
        }
        return inputStream;
    }

    private InputStream bf(String string) {
        if (this.OL == null) {
            return ny_1.getSystemResourceAsStream(string);
        }
        return this.OL.getResourceAsStream(string);
    }

    private InputStream a(File file, String string) {
        try {
            ZipFile zipFile = (ZipFile)this.OM.get(file);
            if (zipFile == null && file.isDirectory()) {
                File file2 = new File(file, string);
                if (file2.exists()) {
                    return new FileInputStream(file2);
                }
            } else {
                ZipEntry zipEntry;
                if (zipFile == null) {
                    if (!file.exists()) {
                        return null;
                    }
                    zipFile = new ZipFile(file);
                    this.OM.put(file, zipFile);
                    zipFile = (ZipFile)this.OM.get(file);
                }
                if ((zipEntry = zipFile.getEntry(string)) != null) {
                    return zipFile.getInputStream(zipEntry);
                }
            }
        }
        catch (Exception exception) {
            this.l("Ignoring Exception " + exception.getClass().getName() + ": " + exception.getMessage() + " reading resource " + string + " from " + file, 3);
        }
        return null;
    }

    private boolean bg(String string) {
        String string2;
        boolean bl2 = this.OG;
        Enumeration enumeration = this.OH.elements();
        while (enumeration.hasMoreElements()) {
            string2 = (String)enumeration.nextElement();
            if (!string.startsWith(string2)) continue;
            bl2 = true;
            break;
        }
        enumeration = this.OI.elements();
        while (enumeration.hasMoreElements()) {
            string2 = (String)enumeration.nextElement();
            if (!string.startsWith(string2)) continue;
            bl2 = false;
            break;
        }
        return bl2;
    }

    private ClassLoader sw() {
        ClassLoader classLoader;
        for (classLoader = this.getClass().getClassLoader(); classLoader != null && classLoader.getParent() != null; classLoader = classLoader.getParent()) {
        }
        return classLoader;
    }

    public URL getResource(String string) {
        URL uRL = null;
        if (this.bg(string)) {
            URL uRL2 = uRL = this.OL == null ? super.getResource(string) : this.OL.getResource(string);
        }
        if (uRL != null) {
            this.l("Resource " + string + " loaded from parent loader", 4);
        } else {
            Enumeration enumeration = this.OF.elements();
            while (enumeration.hasMoreElements() && uRL == null) {
                File file = (File)enumeration.nextElement();
                uRL = this.b(file, string);
                if (uRL == null) continue;
                this.l("Resource " + string + " loaded from ant loader", 4);
            }
        }
        if (uRL == null && !this.bg(string)) {
            if (this.OJ) {
                uRL = this.sw() == null ? null : this.sw().getResource(string);
            } else {
                URL uRL3 = uRL = this.OL == null ? super.getResource(string) : this.OL.getResource(string);
            }
            if (uRL != null) {
                this.l("Resource " + string + " loaded from parent loader", 4);
            }
        }
        if (uRL == null) {
            this.l("Couldn't load Resource " + string, 4);
        }
        return uRL;
    }

    protected Enumeration findResources(String string) {
        adf_1 adf_12 = new adf_1(this, string);
        Enumeration enumeration = this.OL != null && this.OL != this.getParent() ? this.OL.getResources(string) : new jj_2();
        if (this.bg(string)) {
            return ux_0.a(enumeration, adf_12);
        }
        if (this.OJ) {
            return this.sw() == null ? adf_12 : ux_0.a(adf_12, this.sw().getResources(string));
        }
        return ux_0.a(adf_12, enumeration);
    }

    protected URL b(File file, String string) {
        try {
            ZipFile zipFile = (ZipFile)this.OM.get(file);
            if (zipFile == null && file.isDirectory()) {
                File file2 = new File(file, string);
                if (file2.exists()) {
                    try {
                        return xa.o(file2);
                    }
                    catch (MalformedURLException malformedURLException) {
                        return null;
                    }
                }
            } else {
                ZipEntry zipEntry;
                if (zipFile == null) {
                    if (file.exists()) {
                        zipFile = new ZipFile(file);
                        this.OM.put(file, zipFile);
                    } else {
                        return null;
                    }
                }
                if ((zipEntry = zipFile.getEntry(string)) != null) {
                    try {
                        return new URL("jar:" + xa.o(file) + "!/" + zipEntry);
                    }
                    catch (MalformedURLException malformedURLException) {
                        return null;
                    }
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    protected synchronized Class loadClass(String string, boolean bl2) {
        Class clazz = this.findLoadedClass(string);
        if (clazz != null) {
            return clazz;
        }
        if (this.bg(string)) {
            try {
                clazz = this.bj(string);
                this.l("Class " + string + " loaded from parent loader " + "(parentFirst)", 4);
            }
            catch (ClassNotFoundException classNotFoundException) {
                clazz = this.findClass(string);
                this.l("Class " + string + " loaded from ant loader " + "(parentFirst)", 4);
            }
        } else {
            try {
                clazz = this.findClass(string);
                this.l("Class " + string + " loaded from ant loader", 4);
            }
            catch (ClassNotFoundException classNotFoundException) {
                if (this.OJ) {
                    throw classNotFoundException;
                }
                clazz = this.bj(string);
                this.l("Class " + string + " loaded from parent loader", 4);
            }
        }
        if (bl2) {
            this.resolveClass(clazz);
        }
        return clazz;
    }

    private String bh(String string) {
        return string.replace('.', '/') + ".class";
    }

    protected Class a(File file, byte[] byArray, String string) {
        this.c(file, string);
        return this.defineClass(string, byArray, 0, byArray.length, (OR == null ? (OR = ny_1.a("UI")) : OR).getProtectionDomain());
    }

    protected void c(File file, String string) {
        int n2 = string.lastIndexOf(46);
        if (n2 == -1) {
            return;
        }
        String string2 = string.substring(0, n2);
        if (this.getPackage(string2) != null) {
            return;
        }
        Manifest manifest = this.j(file);
        if (manifest == null) {
            this.definePackage(string2, null, null, null, null, null, null, null);
        } else {
            this.a(file, string2, manifest);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Manifest j(File file) {
        if (file.isDirectory()) {
            return null;
        }
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);
            Manifest manifest = jarFile.getManifest();
            return manifest;
        }
        finally {
            if (jarFile != null) {
                jarFile.close();
            }
        }
    }

    protected void a(File file, String string, Manifest manifest) {
        Attributes attributes;
        String string2 = string.replace('.', '/') + "/";
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        String string8 = null;
        String string9 = null;
        URL uRL = null;
        Attributes attributes2 = manifest.getAttributes(string2);
        if (attributes2 != null) {
            string3 = attributes2.getValue(Attributes.Name.SPECIFICATION_TITLE);
            string4 = attributes2.getValue(Attributes.Name.SPECIFICATION_VENDOR);
            string5 = attributes2.getValue(Attributes.Name.SPECIFICATION_VERSION);
            string6 = attributes2.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
            string7 = attributes2.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
            string8 = attributes2.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
            string9 = attributes2.getValue(Attributes.Name.SEALED);
        }
        if ((attributes = manifest.getMainAttributes()) != null) {
            if (string3 == null) {
                string3 = attributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
            }
            if (string4 == null) {
                string4 = attributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
            }
            if (string5 == null) {
                string5 = attributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
            }
            if (string6 == null) {
                string6 = attributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
            }
            if (string7 == null) {
                string7 = attributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
            }
            if (string8 == null) {
                string8 = attributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
            }
            if (string9 == null) {
                string9 = attributes.getValue(Attributes.Name.SEALED);
            }
        }
        if (string9 != null && string9.toLowerCase(Locale.ENGLISH).equals("true")) {
            try {
                uRL = new URL(ga_2.Qo().eb(file.getAbsolutePath()));
            }
            catch (MalformedURLException malformedURLException) {
                // empty catch block
            }
        }
        this.definePackage(string, string3, string5, string4, string6, string8, string7, uRL);
    }

    private Class a(InputStream inputStream, String string, File file) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n2 = -1;
        byte[] byArray = new byte[8192];
        while ((n2 = inputStream.read(byArray, 0, 8192)) != -1) {
            byteArrayOutputStream.write(byArray, 0, n2);
        }
        byte[] byArray2 = byteArrayOutputStream.toByteArray();
        return this.a(file, byArray2, string);
    }

    public Class findClass(String string) {
        this.l("Finding class " + string, 4);
        return this.bi(string);
    }

    protected boolean k(File file) {
        Enumeration enumeration = this.OF.elements();
        while (enumeration.hasMoreElements()) {
            File file2 = (File)enumeration.nextElement();
            if (!file2.equals(file)) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Class bi(String string) {
        Class clazz;
        InputStream inputStream;
        block7: {
            inputStream = null;
            String string2 = this.bh(string);
            try {
                Enumeration enumeration = this.OF.elements();
                while (enumeration.hasMoreElements()) {
                    File file = (File)enumeration.nextElement();
                    try {
                        inputStream = this.a(file, string2);
                        if (inputStream == null) continue;
                        this.l("Loaded from " + file + " " + string2, 4);
                        clazz = this.a(inputStream, string, file);
                        break block7;
                    }
                    catch (SecurityException securityException) {
                        throw securityException;
                    }
                    catch (IOException iOException) {
                        this.l("Exception reading component " + file + " (reason: " + iOException.getMessage() + ")", 3);
                    }
                }
                throw new ClassNotFoundException(string);
            }
            catch (Throwable throwable) {
                ga_2.h(inputStream);
                throw throwable;
            }
        }
        ga_2.h(inputStream);
        return clazz;
    }

    private Class bj(String string) {
        if (this.OL == null) {
            return this.findSystemClass(string);
        }
        return this.OL.loadClass(string);
    }

    public synchronized void cleanup() {
        Enumeration enumeration = this.OM.elements();
        while (enumeration.hasMoreElements()) {
            ZipFile zipFile = (ZipFile)enumeration.nextElement();
            try {
                zipFile.close();
            }
            catch (IOException iOException) {}
        }
        this.OM = new Hashtable();
        if (this.hL != null) {
            this.hL.b(this);
        }
        this.hL = null;
    }

    public void a(axv_0 axv_02) {
    }

    public void b(axv_0 axv_02) {
        this.cleanup();
    }

    public void c(axv_0 axv_02) {
        if (axv_02.TP() == this.hL) {
            this.cleanup();
        }
    }

    public void d(axv_0 axv_02) {
    }

    public void e(axv_0 axv_02) {
    }

    public void f(axv_0 axv_02) {
    }

    public void g(axv_0 axv_02) {
    }

    public void h(axv_0 axv_02) {
    }

    public void i(axv_0 axv_02) {
    }

    public void sx() {
        Vector vector = ako_1.aAd();
        Enumeration enumeration = vector.elements();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            this.ba(string);
        }
    }

    public String toString() {
        return "AntClassLoader[" + this.sv() + "]";
    }

    static Vector a(ny_1 ny_12) {
        return ny_12.OF;
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

