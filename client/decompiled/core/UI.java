/*
 * Decompiled with CFR 0.152.
 */
import java.io.EOFException;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import java.util.Vector;
import java.util.WeakHashMap;

public class UI
implements axm_0 {
    private static final String LINE_SEP = System.getProperty("line.separator");
    public static final int bQt = 0;
    public static final int bQu = 1;
    public static final int bQv = 2;
    public static final int bQw = 3;
    public static final int bQx = 4;
    private static final String bQy = "VISITING";
    private static final String bQz = "VISITED";
    public static final String bQA = "1.0";
    public static final String bQB = "1.1";
    public static final String bQC = "1.2";
    public static final String bQD = "1.3";
    public static final String bQE = "1.4";
    public static final String bQF = "@";
    public static final String bQG = "@";
    private static final ga_2 xa = ga_2.Qo();
    private String name;
    private String description;
    private Hashtable bQH = new bo_2();
    private HashMap bQI = new HashMap();
    private UI bQJ = null;
    private String bQK;
    private Hashtable bQL = new Hashtable();
    private Zq bQM = new Zq();
    private agd_2 bQN;
    private File bQO;
    private Vector listeners;
    private ClassLoader bQP;
    private Map bQQ;
    private Map bQR;
    private hu_1 bQS;
    private InputStream bQT;
    private boolean P;
    private boolean bQU;
    static Class OR;
    static Class bdZ;
    static Class bQV;

    public void a(hu_1 hu_12) {
        this.bQS = hu_12;
    }

    public void j(InputStream inputStream) {
        this.bQT = inputStream;
    }

    public InputStream agU() {
        return this.bQT;
    }

    public hu_1 agV() {
        return this.bQS;
    }

    public UI() {
        this.bQM.l(this);
        this.bQN = new agd_2(this.bQM);
        this.listeners = new Vector();
        this.bQP = null;
        this.bQQ = Collections.synchronizedMap(new WeakHashMap());
        this.bQR = Collections.synchronizedMap(new WeakHashMap());
        this.bQS = null;
        this.bQT = null;
        this.P = false;
        this.bQU = false;
        this.bQS = new vm_1();
    }

    public UI agW() {
        UI uI = null;
        try {
            uI = (UI)this.getClass().newInstance();
        }
        catch (Exception exception) {
            uI = new UI();
        }
        this.x(uI);
        return uI;
    }

    public void x(UI uI) {
        abm_1.D(uI).b(abm_1.D(this));
        uI.j(this.agU());
        uI.cq(this.ahh());
        uI.a(this.aho().qn());
    }

    public void init() {
        this.agX();
        abm_1.D(this).apS();
    }

    public void agX() {
        this.ahj();
        this.ahk();
        this.G("ant.version", s_0.I());
        this.agY();
    }

    private void agY() {
        File file = Hs.e(OR == null ? (OR = UI.a("UI")) : OR);
        if (file != null) {
            this.G("ant.core.lib", file.getAbsolutePath());
        }
    }

    public ny_1 g(bk_2 bk_22) {
        return new ny_1(this.getClass().getClassLoader(), this, bk_22);
    }

    public ny_1 a(ClassLoader classLoader, bk_2 bk_22) {
        return new ny_1(classLoader, this, bk_22);
    }

    public void d(ClassLoader classLoader) {
        this.bQP = classLoader;
    }

    public ClassLoader agZ() {
        return this.bQP;
    }

    public synchronized void a(kd_1 kd_12) {
        if (this.listeners.contains(kd_12)) {
            return;
        }
        Vector vector = this.aha();
        vector.addElement(kd_12);
        this.listeners = vector;
    }

    public synchronized void b(kd_1 kd_12) {
        Vector vector = this.aha();
        vector.removeElement(kd_12);
        this.listeners = vector;
    }

    public Vector aha() {
        return (Vector)this.listeners.clone();
    }

    public void log(String string) {
        this.l(string, 2);
    }

    public void l(String string, int n2) {
        this.a(string, null, n2);
    }

    public void a(String string, Throwable throwable, int n2) {
        this.a(this, string, throwable, n2);
    }

    public void a(dm_1 dm_12, String string, int n2) {
        this.b(dm_12, string, null, n2);
    }

    public void a(dm_1 dm_12, String string, Throwable throwable, int n2) {
        this.b(dm_12, string, throwable, n2);
    }

    public void a(id_2 id_22, String string, int n2) {
        this.a(id_22, string, null, n2);
    }

    public void a(id_2 id_22, String string, Throwable throwable, int n2) {
        this.b(id_22, string, throwable, n2);
    }

    public Zq ahb() {
        return this.bQM;
    }

    public void setProperty(String string, String string2) {
        afc_2.W(this).a(null, string, string2, true);
    }

    public void D(String string, String string2) {
        afc_2.W(this).d(null, string, string2);
    }

    public void E(String string, String string2) {
        afc_2.W(this).e(null, string, string2);
    }

    public void F(String string, String string2) {
        afc_2 afc_22 = afc_2.W(this);
        afc_22.f(null, string, string2);
    }

    private void G(String string, String string2) {
        afc_2 afc_22 = afc_2.W(this);
        afc_22.a(null, string, string2, false);
    }

    public String getProperty(String string) {
        afc_2 afc_22 = afc_2.W(this);
        return (String)afc_22.getProperty(null, string);
    }

    public String fZ(String string) {
        afc_2 afc_22 = afc_2.W(this);
        return afc_22.a(null, string, null);
    }

    public String ga(String string) {
        afc_2 afc_22 = afc_2.W(this);
        return (String)afc_22.as(null, string);
    }

    public Hashtable ahc() {
        afc_2 afc_22 = afc_2.W(this);
        return afc_22.ahc();
    }

    public Hashtable ahd() {
        afc_2 afc_22 = afc_2.W(this);
        return afc_22.ahd();
    }

    public void y(UI uI) {
        afc_2 afc_22 = afc_2.W(this);
        afc_22.y(uI);
    }

    public void z(UI uI) {
        afc_2 afc_22 = afc_2.W(this);
        afc_22.z(uI);
    }

    public void gb(String string) {
        this.bQK = string;
    }

    public String ahe() {
        return this.bQK;
    }

    public void gc(String string) {
        this.bQK = string;
    }

    public void setName(String string) {
        this.E("ant.project.name", string);
        this.name = string;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String string) {
        this.description = string;
    }

    public String getDescription() {
        if (this.description == null) {
            this.description = arF.E(this);
        }
        return this.description;
    }

    public void H(String string, String string2) {
        if (string == null) {
            return;
        }
        this.bQM.a(new fj(string, string2));
    }

    public Hashtable ahf() {
        return this.bQM.anq();
    }

    public void ar(String string) {
        this.A(new File(string));
    }

    public void A(File file) {
        if (!(file = xa.dZ(file.getAbsolutePath())).exists()) {
            throw new eq_2("Basedir " + file.getAbsolutePath() + " does not exist");
        }
        if (!file.isDirectory()) {
            throw new eq_2("Basedir " + file.getAbsolutePath() + " is not a directory");
        }
        this.bQO = file;
        this.G("basedir", this.bQO.getPath());
        String string = "Project base dir set to: " + this.bQO;
        this.l(string, 3);
    }

    public File ahg() {
        if (this.bQO == null) {
            try {
                this.ar(".");
            }
            catch (eq_2 eq_22) {
                eq_22.printStackTrace();
            }
        }
        return this.bQO;
    }

    public void cq(boolean bl2) {
        this.P = bl2;
    }

    public boolean ahh() {
        return this.P;
    }

    public static String ahi() {
        return ako_1.ahi();
    }

    public void ahj() {
        String string = ako_1.ahi();
        this.G("ant.java.version", string);
        if (ako_1.iy(bQA) || ako_1.iy(bQB)) {
            throw new eq_2("Ant cannot work on Java 1.0 / 1.1");
        }
        this.l("Detected Java version: " + string + " in: " + System.getProperty("java.home"), 3);
        this.l("Detected OS: " + System.getProperty("os.name"), 3);
    }

    public void ahk() {
        Properties properties = System.getProperties();
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            String string2 = properties.getProperty(string);
            if (string2 == null) continue;
            this.G(string, string2);
        }
    }

    public void c(String string, Class clazz) {
        abm_1.D(this).c(string, clazz);
    }

    public void v(Class clazz) {
        abm_1.D(this).v(clazz);
        if (!Modifier.isPublic(clazz.getModifiers())) {
            String string = clazz + " is not public";
            this.l(string, 0);
            throw new eq_2(string);
        }
        if (Modifier.isAbstract(clazz.getModifiers())) {
            String string = clazz + " is abstract";
            this.l(string, 0);
            throw new eq_2(string);
        }
        try {
            clazz.getConstructor(null);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            String string = "No public no-arg constructor in " + clazz;
            this.l(string, 0);
            throw new eq_2(string);
        }
        catch (LinkageError linkageError) {
            String string = "Could not load " + clazz + ": " + linkageError;
            this.l(string, 0);
            throw new eq_2(string, linkageError);
        }
        if (!(bdZ == null ? (bdZ = UI.a("Dm")) : bdZ).isAssignableFrom(clazz)) {
            ale_1.a(clazz, this);
        }
    }

    public Hashtable ahl() {
        return abm_1.D(this).ahl();
    }

    public void d(String string, Class clazz) {
        abm_1.D(this).d(string, clazz);
    }

    public Hashtable ahm() {
        return abm_1.D(this).ahm();
    }

    public void b(id_2 id_22) {
        this.a(id_22.getName(), id_22);
    }

    public void a(String string, id_2 id_22) {
        if (this.bQL.get(string) != null) {
            throw new eq_2("Duplicate target: `" + string + "'");
        }
        this.b(string, id_22);
    }

    public void e(id_2 id_22) {
        this.b(id_22.getName(), id_22);
    }

    public void b(String string, id_2 id_22) {
        String string2 = " +Target: " + string;
        this.l(string2, 4);
        id_22.l(this);
        this.bQL.put(string, id_22);
    }

    public Hashtable ahn() {
        return this.bQL;
    }

    public dm_1 gd(String string) {
        return abm_1.D(this).gd(string);
    }

    public Object ge(String string) {
        return abm_1.D(this).ge(string);
    }

    public void a(lw_1 lw_12) {
        this.o("ant.executor", lw_12);
    }

    public lw_1 aho() {
        Object object = this.gi("ant.executor");
        if (object == null) {
            String string = this.getProperty("ant.executor.class");
            if (string == null) {
                string = (bQV == null ? (bQV = UI.a("org.apache.tools.ant.helper.DefaultExecutor")) : bQV).getName();
            }
            this.l("Attempting to create object of type " + string, 4);
            try {
                object = Class.forName(string, true, this.bQP).newInstance();
            }
            catch (ClassNotFoundException classNotFoundException) {
                try {
                    object = Class.forName(string).newInstance();
                }
                catch (Exception exception) {
                    this.l(exception.toString(), 0);
                }
            }
            catch (Exception exception) {
                this.l(exception.toString(), 0);
            }
            if (object == null) {
                throw new eq_2("Unable to obtain a Target Executor instance.");
            }
            this.a((lw_1)object);
        }
        return (lw_1)object;
    }

    public void e(Vector vector) {
        this.aho().a(this, vector.toArray(new String[vector.size()]));
    }

    public void n(String string, boolean bl2) {
        dm_1 dm_12 = this.a(Thread.currentThread());
        if (dm_12 == null) {
            this.l(string, bl2 ? 1 : 2);
        } else if (bl2) {
            dm_12.dH(string);
        } else {
            dm_12.dF(string);
        }
    }

    public int d(byte[] byArray, int n2, int n3) {
        if (this.bQT != null) {
            System.out.flush();
            return this.bQT.read(byArray, n2, n3);
        }
        throw new EOFException("No input provided for project");
    }

    public int e(byte[] byArray, int n2, int n3) {
        dm_1 dm_12 = this.a(Thread.currentThread());
        if (dm_12 == null) {
            return this.d(byArray, n2, n3);
        }
        return dm_12.c(byArray, n2, n3);
    }

    public void o(String string, boolean bl2) {
        dm_1 dm_12 = this.a(Thread.currentThread());
        if (dm_12 == null) {
            this.a(this, string, bl2 ? 0 : 2);
        } else if (bl2) {
            dm_12.dI(string);
        } else {
            dm_12.dG(string);
        }
    }

    public void gf(String string) {
        if (string == null) {
            String string2 = "No target specified";
            throw new eq_2(string2);
        }
        this.f(this.a(string, this.bQL, false));
    }

    public void f(Vector vector) {
        HashSet<String> hashSet = new HashSet<String>();
        eq_2 eq_22 = null;
        Enumeration enumeration = vector.elements();
        while (enumeration.hasMoreElements()) {
            id_2 id_22 = (id_2)enumeration.nextElement();
            boolean bl2 = true;
            Object object = id_22.TR();
            while (object.hasMoreElements()) {
                String string = (String)object.nextElement();
                if (hashSet.contains(string)) continue;
                bl2 = false;
                this.a(id_22, "Cannot execute '" + id_22.getName() + "' - '" + string + "' failed or was not executed.", 0);
                break;
            }
            if (!bl2) continue;
            object = null;
            try {
                id_22.TU();
                hashSet.add(id_22.getName());
            }
            catch (RuntimeException runtimeException) {
                if (!this.P) {
                    throw runtimeException;
                }
                object = runtimeException;
            }
            catch (Throwable throwable) {
                if (!this.P) {
                    throw new eq_2(throwable);
                }
                object = throwable;
            }
            if (object == null) continue;
            if (object instanceof eq_2) {
                this.a(id_22, "Target '" + id_22.getName() + "' failed with message '" + ((Throwable)object).getMessage() + "'.", 0);
                if (eq_22 != null) continue;
                eq_22 = (eq_2)object;
                continue;
            }
            this.a(id_22, "Target '" + id_22.getName() + "' failed with message '" + ((Throwable)object).getMessage() + "'.", 0);
            ((Throwable)object).printStackTrace(System.err);
            if (eq_22 != null) continue;
            eq_22 = new eq_2((Throwable)object);
        }
        if (eq_22 != null) {
            throw eq_22;
        }
    }

    public File a(String string, File file) {
        return xa.d(file, string);
    }

    public File gg(String string) {
        return xa.d(this.bQO, string);
    }

    public static String dY(String string) {
        return ga_2.dY(string);
    }

    public void t(String string, String string2) {
        xa.t(string, string2);
    }

    public void c(String string, String string2, boolean bl2) {
        xa.a(string, string2, bl2 ? this.bQN : null);
    }

    public void a(String string, String string2, boolean bl2, boolean bl3) {
        xa.a(string, string2, bl2 ? this.bQN : null, bl3);
    }

    public void a(String string, String string2, boolean bl2, boolean bl3, boolean bl4) {
        xa.a(string, string2, bl2 ? this.bQN : null, bl3, bl4);
    }

    public void a(File file, File file2) {
        xa.a(file, file2);
    }

    public void b(File file, File file2, boolean bl2) {
        xa.a(file, file2, bl2 ? this.bQN : null);
    }

    public void a(File file, File file2, boolean bl2, boolean bl3) {
        xa.a(file, file2, bl2 ? this.bQN : null, bl3);
    }

    public void a(File file, File file2, boolean bl2, boolean bl3, boolean bl4) {
        xa.a(file, file2, bl2 ? this.bQN : null, bl3, bl4);
    }

    public void a(File file, long l2) {
        xa.a(file, l2);
        this.l("Setting modification time for " + file, 3);
    }

    public static boolean gh(String string) {
        return "on".equalsIgnoreCase(string) || "true".equalsIgnoreCase(string) || "yes".equalsIgnoreCase(string);
    }

    public static UI ar(Object object) {
        if (object instanceof aat_0) {
            return ((aat_0)object).TP();
        }
        try {
            Method method = object.getClass().getMethod("getProject", null);
            if ((OR == null ? (OR = UI.a("UI")) : OR) == method.getReturnType()) {
                return (UI)method.invoke(object, null);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    public final Vector a(String string, Hashtable hashtable) {
        return this.a(new String[]{string}, hashtable, true);
    }

    public final Vector a(String string, Hashtable hashtable, boolean bl2) {
        return this.a(new String[]{string}, hashtable, bl2);
    }

    public final Vector a(String[] stringArray, Hashtable hashtable, boolean bl2) {
        Vector vector = new Vector();
        Hashtable hashtable2 = new Hashtable();
        Stack stack = new Stack();
        for (int j = 0; j < stringArray.length; ++j) {
            String string = (String)hashtable2.get(stringArray[j]);
            if (string == null) {
                this.a(stringArray[j], hashtable, hashtable2, stack, vector);
                continue;
            }
            if (string != bQy) continue;
            throw new RuntimeException("Unexpected node in visiting state: " + stringArray[j]);
        }
        StringBuffer stringBuffer = new StringBuffer("Build sequence for target(s)");
        for (int j = 0; j < stringArray.length; ++j) {
            stringBuffer.append(j == 0 ? " `" : ", `").append(stringArray[j]).append('\'');
        }
        stringBuffer.append(" is " + vector);
        this.l(stringBuffer.toString(), 3);
        Vector vector2 = bl2 ? vector : new Vector(vector);
        Enumeration enumeration = hashtable.keys();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            String string2 = (String)hashtable2.get(string);
            if (string2 == null) {
                this.a(string, hashtable, hashtable2, stack, vector2);
                continue;
            }
            if (string2 != bQy) continue;
            throw new RuntimeException("Unexpected node in visiting state: " + string);
        }
        this.l("Complete build sequence is " + vector2, 3);
        return vector;
    }

    private void a(String string, Hashtable hashtable, Hashtable hashtable2, Stack stack, Vector vector) {
        hashtable2.put(string, bQy);
        stack.push(string);
        id_2 id_22 = (id_2)hashtable.get(string);
        if (id_22 == null) {
            StringBuffer stringBuffer = new StringBuffer("Target \"");
            stringBuffer.append(string);
            stringBuffer.append("\" does not exist in the project \"");
            stringBuffer.append(this.name);
            stringBuffer.append("\". ");
            stack.pop();
            if (!stack.empty()) {
                String string2 = (String)stack.peek();
                stringBuffer.append("It is used from target \"");
                stringBuffer.append(string2);
                stringBuffer.append("\".");
            }
            throw new eq_2(new String(stringBuffer));
        }
        Object object = id_22.TR();
        while (object.hasMoreElements()) {
            String string3 = (String)object.nextElement();
            String string4 = (String)hashtable2.get(string3);
            if (string4 == null) {
                this.a(string3, hashtable, hashtable2, stack, vector);
                continue;
            }
            if (string4 != bQy) continue;
            throw UI.a(string3, stack);
        }
        object = (String)stack.pop();
        if (string != object) {
            throw new RuntimeException("Unexpected internal error: expected to pop " + string + " but got " + (String)object);
        }
        hashtable2.put(string, bQz);
        vector.addElement(id_22);
    }

    private static eq_2 a(String string, Stack stack) {
        String string2;
        StringBuffer stringBuffer = new StringBuffer("Circular dependency: ");
        stringBuffer.append(string);
        do {
            string2 = (String)stack.pop();
            stringBuffer.append(" <- ");
            stringBuffer.append(string2);
        } while (!string2.equals(string));
        return new eq_2(new String(stringBuffer));
    }

    public void A(UI uI) {
        this.bQJ = uI;
    }

    private Object a(String string, UI uI) {
        rs_0 rs_02 = (rs_0)this.bQI.get(string);
        if (rs_02 == null) {
            return this.bQJ == null ? null : this.bQJ.a(string, uI);
        }
        uI.l("Warning: Reference " + string + " has not been set at runtime," + " but was found during" + LINE_SEP + "build file parsing, attempting to resolve." + " Future versions of Ant may support" + LINE_SEP + " referencing ids defined in non-executed targets.", 1);
        rs_0 rs_03 = rs_02.r(uI);
        rs_03.LH();
        return rs_03.adO();
    }

    public void n(String string, Object object) {
        this.bQI.put(string, object);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void o(String string, Object object) {
        Hashtable hashtable = this.bQH;
        synchronized (hashtable) {
            Object object2 = bo_2.a((bo_2)this.bQH, string);
            if (object2 == object) {
                return;
            }
            if (object2 != null && !(object2 instanceof rs_0)) {
                this.l("Overriding previous definition of reference to " + string, 3);
            }
            this.l("Adding reference: " + string, 4);
            this.bQH.put(string, object);
        }
    }

    public Hashtable ahp() {
        return this.bQH;
    }

    public Object gi(String string) {
        Object object = this.bQH.get(string);
        if (object != null) {
            return object;
        }
        object = this.a(string, this);
        if (object == null && !string.equals("ant.PropertyHelper")) {
            Vector vector = new Vector();
            afc_2.W(this).a(string, new Vector(), vector);
            if (vector.size() == 1) {
                this.l("Unresolvable reference " + string + " might be a misuse of property expansion syntax.", 1);
            }
        }
        return object;
    }

    public String as(Object object) {
        return abm_1.D(this).as(object);
    }

    public void ahq() {
        axv_0 axv_02 = new axv_0(this);
        Iterator iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            kd_1 kd_12 = (kd_1)iterator.next();
            kd_12.a(axv_02);
        }
    }

    public void e(Throwable throwable) {
        axv_0 axv_02 = new axv_0(this);
        axv_02.setException(throwable);
        Iterator iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            kd_1 kd_12 = (kd_1)iterator.next();
            kd_12.b(axv_02);
        }
        hm_2.clearCache();
    }

    public void ahr() {
        axv_0 axv_02 = new axv_0(this);
        Iterator iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            Object e = iterator.next();
            if (!(e instanceof afK)) continue;
            ((afK)e).d(axv_02);
        }
    }

    public void f(Throwable throwable) {
        axv_0 axv_02 = new axv_0(this);
        axv_02.setException(throwable);
        Iterator iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            Object e = iterator.next();
            if (!(e instanceof afK)) continue;
            ((afK)e).c(axv_02);
        }
    }

    protected void f(id_2 id_22) {
        axv_0 axv_02 = new axv_0(id_22);
        Iterator iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            kd_1 kd_12 = (kd_1)iterator.next();
            kd_12.e(axv_02);
        }
    }

    protected void a(id_2 id_22, Throwable throwable) {
        axv_0 axv_02 = new axv_0(id_22);
        axv_02.setException(throwable);
        Iterator iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            kd_1 kd_12 = (kd_1)iterator.next();
            kd_12.f(axv_02);
        }
    }

    protected void c(dm_1 dm_12) {
        this.a(Thread.currentThread(), dm_12);
        axv_0 axv_02 = new axv_0(dm_12);
        Iterator iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            kd_1 kd_12 = (kd_1)iterator.next();
            kd_12.g(axv_02);
        }
    }

    protected void a(dm_1 dm_12, Throwable throwable) {
        this.a(Thread.currentThread(), null);
        System.out.flush();
        System.err.flush();
        axv_0 axv_02 = new axv_0(dm_12);
        axv_02.setException(throwable);
        Iterator iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            kd_1 kd_12 = (kd_1)iterator.next();
            kd_12.h(axv_02);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(axv_0 axv_02, String string, int n2) {
        if (string.endsWith(ayM.LINE_SEP)) {
            int n3 = string.length() - ayM.LINE_SEP.length();
            axv_02.D(string.substring(0, n3), n2);
        } else {
            axv_02.D(string, n2);
        }
        UI uI = this;
        synchronized (uI) {
            if (this.bQU) {
                return;
            }
            try {
                this.bQU = true;
                Iterator iterator = this.listeners.iterator();
                while (iterator.hasNext()) {
                    kd_1 kd_12 = (kd_1)iterator.next();
                    kd_12.i(axv_02);
                }
            }
            finally {
                this.bQU = false;
            }
        }
    }

    protected void a(UI uI, String string, int n2) {
        this.a(uI, string, null, n2);
    }

    protected void a(UI uI, String string, Throwable throwable, int n2) {
        axv_0 axv_02 = new axv_0(uI);
        axv_02.setException(throwable);
        this.a(axv_02, string, n2);
    }

    protected void b(id_2 id_22, String string, int n2) {
        this.b(id_22, string, null, n2);
    }

    protected void b(id_2 id_22, String string, Throwable throwable, int n2) {
        axv_0 axv_02 = new axv_0(id_22);
        axv_02.setException(throwable);
        this.a(axv_02, string, n2);
    }

    protected void b(dm_1 dm_12, String string, int n2) {
        this.b(dm_12, string, null, n2);
    }

    protected void b(dm_1 dm_12, String string, Throwable throwable, int n2) {
        axv_0 axv_02 = new axv_0(dm_12);
        axv_02.setException(throwable);
        this.a(axv_02, string, n2);
    }

    public synchronized void a(Thread thread, dm_1 dm_12) {
        if (dm_12 != null) {
            this.bQQ.put(thread, dm_12);
            this.bQR.put(thread.getThreadGroup(), dm_12);
        } else {
            this.bQQ.remove(thread);
            this.bQR.remove(thread.getThreadGroup());
        }
    }

    public dm_1 a(Thread thread) {
        dm_1 dm_12 = (dm_1)this.bQQ.get(thread);
        if (dm_12 == null) {
            for (ThreadGroup threadGroup = thread.getThreadGroup(); dm_12 == null && threadGroup != null; threadGroup = threadGroup.getParent()) {
                dm_12 = (dm_1)this.bQR.get(threadGroup);
            }
        }
        return dm_12;
    }

    public final void at(Object object) {
        if (object instanceof aat_0) {
            ((aat_0)object).l(this);
            return;
        }
        try {
            Method method = object.getClass().getMethod("setProject", OR == null ? (OR = UI.a("UI")) : OR);
            if (method != null) {
                method.invoke(object, this);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public iv_1 gj(String string) {
        return new ash_0(this.ahg(), string);
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

