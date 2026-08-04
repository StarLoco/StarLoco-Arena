/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;

/*
 * Renamed from abm
 */
public class abm_1 {
    private hj_2 chC;
    private Hashtable chD = new Hashtable();
    private boolean chE = true;
    private Hashtable chF = new Hashtable();
    private boolean chG = true;
    private Set chH = new HashSet();
    private Stack chI = new Stack();
    private String chJ = null;
    private abm_1 chK;
    private UI hL;
    private static final String chL = "Can't load default task list";
    private static final String chM = "Can't load default type list";
    public static final String chN = "ant.ComponentHelper";
    private static final String chO = "only";
    private static final String chP = "property";
    private static Properties[] chQ = new Properties[2];
    static Class chR;
    static Class bdZ;
    static Class chS;
    static Class chT;

    public static abm_1 D(UI uI) {
        if (uI == null) {
            return null;
        }
        abm_1 abm_12 = (abm_1)uI.gi(chN);
        if (abm_12 != null) {
            return abm_12;
        }
        abm_12 = new abm_1();
        abm_12.l(uI);
        uI.o(chN, abm_12);
        return abm_12;
    }

    protected abm_1() {
    }

    public void a(abm_1 abm_12) {
        this.chK = abm_12;
    }

    public abm_1 apR() {
        return this.chK;
    }

    public void l(UI uI) {
        this.hL = uI;
        this.chC = new hj_2(uI);
    }

    public void b(abm_1 abm_12) {
        hj_2 hj_22 = abm_12.chC;
        Iterator<Object> iterator = hj_22.values().iterator();
        while (iterator.hasNext()) {
            alv_2 alv_22 = (alv_2)iterator.next();
            this.chC.put(alv_22.getName(), alv_22);
        }
        iterator = abm_12.chH.iterator();
        while (iterator.hasNext()) {
            this.chH.add(iterator.next());
        }
    }

    public Object a(rs_0 rs_02, String string, String string2) {
        Object object = this.hp(string2);
        if (object instanceof dm_1) {
            dm_1 dm_12 = (dm_1)object;
            dm_12.a(rs_02.hW());
            dm_12.dE(string2);
            dm_12.cW(rs_02.LF());
            dm_12.a(rs_02.LE());
            dm_12.init();
        }
        return object;
    }

    public Object hp(String string) {
        alv_2 alv_22 = this.at(string);
        return alv_22 == null ? null : alv_22.j(this.hL);
    }

    public Class hq(String string) {
        alv_2 alv_22 = this.at(string);
        return alv_22 == null ? null : alv_22.f(this.hL);
    }

    public alv_2 at(String string) {
        this.eo(string);
        return this.chC.at(string);
    }

    public void apS() {
        this.apW();
        this.apX();
    }

    public void c(String string, Class clazz) {
        this.v(clazz);
        alv_2 alv_22 = new alv_2();
        alv_22.setName(string);
        alv_22.setClassLoader(clazz.getClassLoader());
        alv_22.b(clazz);
        alv_22.c(chR == null ? (chR = abm_1.a("aLe")) : chR);
        alv_22.setClassName(clazz.getName());
        alv_22.d(bdZ == null ? (bdZ = abm_1.a("Dm")) : bdZ);
        this.c(alv_22);
    }

    public void v(Class clazz) {
        if (!Modifier.isPublic(clazz.getModifiers())) {
            String string = clazz + " is not public";
            this.hL.l(string, 0);
            throw new eq_2(string);
        }
        if (Modifier.isAbstract(clazz.getModifiers())) {
            String string = clazz + " is abstract";
            this.hL.l(string, 0);
            throw new eq_2(string);
        }
        try {
            clazz.getConstructor(null);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            String string = "No public no-arg constructor in " + clazz;
            this.hL.l(string, 0);
            throw new eq_2(string);
        }
        if (!(bdZ == null ? (bdZ = abm_1.a("Dm")) : bdZ).isAssignableFrom(clazz)) {
            ale_1.a(clazz, this.hL);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Hashtable ahl() {
        Hashtable hashtable = this.chD;
        synchronized (hashtable) {
            hj_2 hj_22 = this.chC;
            synchronized (hj_22) {
                if (this.chE) {
                    this.chD.clear();
                    Iterator iterator = this.chC.keySet().iterator();
                    while (iterator.hasNext()) {
                        String string = (String)iterator.next();
                        Class clazz = this.chC.aw(string);
                        if (clazz == null || !(bdZ == null ? abm_1.a("Dm") : bdZ).isAssignableFrom(clazz)) continue;
                        this.chD.put(string, this.chC.av(string));
                    }
                    this.chE = false;
                }
            }
        }
        return this.chD;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Hashtable ahm() {
        Hashtable hashtable = this.chF;
        synchronized (hashtable) {
            hj_2 hj_22 = this.chC;
            synchronized (hj_22) {
                if (this.chG) {
                    this.chF.clear();
                    Iterator iterator = this.chC.keySet().iterator();
                    while (iterator.hasNext()) {
                        String string = (String)iterator.next();
                        Class clazz = this.chC.aw(string);
                        if (clazz == null || (bdZ == null ? abm_1.a("Dm") : bdZ).isAssignableFrom(clazz)) continue;
                        this.chF.put(string, this.chC.av(string));
                    }
                    this.chG = false;
                }
            }
        }
        return this.chF;
    }

    public void d(String string, Class clazz) {
        alv_2 alv_22 = new alv_2();
        alv_22.setName(string);
        alv_22.b(clazz);
        this.c(alv_22);
        this.hL.l(" +User datatype: " + string + "     " + clazz.getName(), 4);
    }

    public void a(alv_2 alv_22) {
        this.c(alv_22);
    }

    public Hashtable apT() {
        return this.chC;
    }

    public dm_1 gd(String string) {
        dm_1 dm_12 = this.hr(string);
        if (dm_12 == null && string.equals(chP)) {
            this.c(chP, chS == null ? (chS = abm_1.a("org.apache.tools.ant.taskdefs.Property")) : chS);
            dm_12 = this.hr(string);
        }
        return dm_12;
    }

    private dm_1 hr(String string) {
        Class clazz = this.hq(string);
        if (clazz == null || !(bdZ == null ? (bdZ = abm_1.a("Dm")) : bdZ).isAssignableFrom(clazz)) {
            return null;
        }
        Object object = this.hp(string);
        if (object == null) {
            return null;
        }
        if (!(object instanceof dm_1)) {
            throw new eq_2("Expected a Task from '" + string + "' but got an instance of " + object.getClass().getName() + " instead");
        }
        dm_1 dm_12 = (dm_1)object;
        dm_12.dE(string);
        dm_12.cW(string);
        this.hL.l("   +Task: " + string, 4);
        return dm_12;
    }

    public Object ge(String string) {
        return this.hp(string);
    }

    public String as(Object object) {
        return this.a(object, false);
    }

    public String a(Object object, boolean bl2) {
        Class<?> clazz = object.getClass();
        String string = clazz.getName();
        Iterator iterator = this.chC.values().iterator();
        while (iterator.hasNext()) {
            alv_2 alv_22 = (alv_2)iterator.next();
            if (!string.equals(alv_22.getClassName()) || clazz != alv_22.f(this.hL)) continue;
            String string2 = alv_22.getName();
            return bl2 ? string2 : "The <" + string2 + "> type";
        }
        return abm_1.a(object.getClass(), bl2);
    }

    public static String a(UI uI, Object object, boolean bl2) {
        if (uI == null) {
            uI = UI.ar(object);
        }
        return uI == null ? abm_1.a(object.getClass(), bl2) : abm_1.D(uI).a(object, bl2);
    }

    private static String a(Class clazz, boolean bl2) {
        if (bl2) {
            String string = clazz.getName();
            return string.substring(string.lastIndexOf(46) + 1);
        }
        return clazz.toString();
    }

    private boolean b(alv_2 alv_22) {
        return alv_22.g(this.hL) != null && alv_22.f(this.hL) != null;
    }

    private boolean a(alv_2 alv_22, alv_2 alv_23) {
        boolean bl2 = this.b(alv_22);
        boolean bl3 = bl2 == this.b(alv_23);
        return bl3 && (!bl2 || alv_22.a(alv_23, this.hL));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void c(alv_2 alv_22) {
        String string = alv_22.getName();
        hj_2 hj_22 = this.chC;
        synchronized (hj_22) {
            this.chE = true;
            this.chG = true;
            alv_2 alv_23 = this.chC.at(string);
            if (alv_23 != null) {
                if (this.a(alv_22, alv_23)) {
                    return;
                }
                Class clazz = this.chC.aw(string);
                boolean bl2 = clazz != null && (bdZ == null ? (bdZ = abm_1.a("Dm")) : bdZ).isAssignableFrom(clazz);
                this.hL.l("Trying to override old definition of " + (bl2 ? "task " : "datatype ") + string, alv_22.b(alv_23, this.hL) ? 3 : 1);
            }
            this.hL.l(" +Datatype " + string + " " + alv_22.getClassName(), 4);
            this.chC.put(string, alv_22);
        }
    }

    public void hs(String string) {
        this.chJ = string;
        this.chI.push(string);
    }

    public String apU() {
        return this.chJ;
    }

    public void apV() {
        this.chI.pop();
        this.chJ = this.chI.size() == 0 ? null : (String)this.chI.peek();
    }

    private void apW() {
        ClassLoader classLoader = this.f(null);
        Properties properties = abm_1.cU(false);
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            String string2 = properties.getProperty(string);
            alv_2 alv_22 = new alv_2();
            alv_22.setName(string);
            alv_22.setClassName(string2);
            alv_22.setClassLoader(classLoader);
            alv_22.d(bdZ == null ? abm_1.a("Dm") : bdZ);
            alv_22.c(chR == null ? abm_1.a("aLe") : chR);
            this.chC.put(string, alv_22);
        }
    }

    private ClassLoader f(ClassLoader classLoader) {
        String string = this.hL.getProperty("build.sysclasspath");
        if (this.hL.agZ() != null && !chO.equals(string)) {
            classLoader = this.hL.agZ();
        }
        return classLoader;
    }

    private static synchronized Properties cU(boolean bl2) {
        int n2;
        int n3 = n2 = bl2 ? 1 : 0;
        if (chQ[n2] == null) {
            String string = bl2 ? "/org/apache/tools/ant/types/defaults.properties" : "/org/apache/tools/ant/taskdefs/defaults.properties";
            String string2 = bl2 ? chM : chL;
            InputStream inputStream = null;
            try {
                inputStream = (chT == null ? (chT = abm_1.a("abm")) : chT).getResourceAsStream(string);
                if (inputStream == null) {
                    throw new eq_2(string2);
                }
                Properties properties = new Properties();
                properties.load(inputStream);
                abm_1.chQ[n2] = properties;
            }
            catch (IOException iOException) {
                try {
                    throw new eq_2(string2, iOException);
                }
                catch (Throwable throwable) {
                    ga_2.h(inputStream);
                    throw throwable;
                }
            }
            ga_2.h(inputStream);
        }
        return chQ[n2];
    }

    private void apX() {
        ClassLoader classLoader = this.f(null);
        Properties properties = abm_1.cU(true);
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            String string2 = properties.getProperty(string);
            alv_2 alv_22 = new alv_2();
            alv_22.setName(string);
            alv_22.setClassName(string2);
            alv_22.setClassLoader(classLoader);
            this.chC.put(string, alv_22);
        }
    }

    private synchronized void eo(String string) {
        String string2 = es_2.dP(string);
        if ("".equals(string2)) {
            string2 = "antlib:org.apache.tools.ant";
        }
        if (!string2.startsWith("antlib:")) {
            return;
        }
        if (this.chH.contains(string2)) {
            return;
        }
        this.chH.add(string2);
        xt_2 xt_22 = new xt_2();
        xt_22.l(this.hL);
        xt_22.init();
        xt_22.setURI(string2);
        xt_22.cW(string2);
        xt_22.K(dj_2.J(string2));
        xt_22.a(new akV("ignore"));
        xt_22.execute();
    }

    public String U(String string, String string2) {
        String string3;
        Serializable serializable;
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println("Problem: failed to create " + string2 + " " + string);
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        String string4 = System.getProperty("user.home");
        File file = new File(string4, Iw.bht);
        boolean bl5 = false;
        String string5 = System.getProperty("ant.home");
        if (string5 != null) {
            serializable = new File(string5, "lib");
            string3 = ((File)serializable).getAbsolutePath();
        } else {
            bl5 = true;
            string3 = "ANT_HOME" + File.separatorChar + "lib";
        }
        serializable = new StringBuffer();
        String string6 = "        -";
        ((StringBuffer)serializable).append("        -");
        ((StringBuffer)serializable).append(string3);
        ((StringBuffer)serializable).append('\n');
        if (bl5) {
            ((StringBuffer)serializable).append("        -");
            ((StringBuffer)serializable).append("the IDE Ant configuration dialogs");
        } else {
            ((StringBuffer)serializable).append("        -");
            ((StringBuffer)serializable).append(file);
            ((StringBuffer)serializable).append('\n');
            ((StringBuffer)serializable).append("        -");
            ((StringBuffer)serializable).append("a directory added on the command line with the -lib argument");
        }
        String string7 = ((StringBuffer)serializable).toString();
        alv_2 alv_22 = this.at(string);
        if (alv_22 == null) {
            this.a(printWriter, string, string7);
            bl4 = true;
        } else {
            String string8 = alv_22.getClassName();
            boolean bl6 = string8.startsWith("org.apache.tools.ant.");
            boolean bl7 = string8.startsWith("org.apache.tools.ant.taskdefs.optional");
            bl7 |= string8.startsWith("org.apache.tools.ant.types.optional");
            Class clazz = null;
            try {
                clazz = alv_22.aWr();
            }
            catch (ClassNotFoundException classNotFoundException) {
                bl3 = true;
                if (!bl7) {
                    bl4 = true;
                }
                this.a(printWriter, string8, bl7, string7);
            }
            catch (NoClassDefFoundError noClassDefFoundError) {
                bl3 = true;
                this.a(printWriter, bl7, noClassDefFoundError, string7);
            }
            if (clazz != null) {
                try {
                    alv_22.b(clazz, this.hL);
                    printWriter.println("The component could be instantiated.");
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    bl2 = true;
                    printWriter.println("Cause: The class " + string8 + " has no compatible constructor.");
                }
                catch (InstantiationException instantiationException) {
                    bl2 = true;
                    printWriter.println("Cause: The class " + string8 + " is abstract and cannot be instantiated.");
                }
                catch (IllegalAccessException illegalAccessException) {
                    bl2 = true;
                    printWriter.println("Cause: The constructor for " + string8 + " is private and cannot be invoked.");
                }
                catch (InvocationTargetException invocationTargetException) {
                    bl2 = true;
                    Throwable throwable = invocationTargetException.getTargetException();
                    printWriter.println("Cause: The constructor threw the exception");
                    printWriter.println(throwable.toString());
                    throwable.printStackTrace(printWriter);
                }
                catch (NoClassDefFoundError noClassDefFoundError) {
                    bl3 = true;
                    printWriter.println("Cause:  A class needed by class " + string8 + " cannot be found: ");
                    printWriter.println("       " + noClassDefFoundError.getMessage());
                    printWriter.println("Action: Determine what extra JAR files are needed, and place them in:");
                    printWriter.println(string7);
                }
            }
            printWriter.println();
            printWriter.println("Do not panic, this is a common problem.");
            if (bl4) {
                printWriter.println("It may just be a typographical error in the build file or the task/type declaration.");
            }
            if (bl3) {
                printWriter.println("The commonest cause is a missing JAR.");
            }
            if (bl2) {
                printWriter.println("This is quite a low level problem, which may need consultation with the author of the task.");
                if (bl6) {
                    printWriter.println("This may be the Ant team. Please file a defect or contact the developer team.");
                } else {
                    printWriter.println("This does not appear to be a task bundled with Ant.");
                    printWriter.println("Please take it up with the supplier of the third-party " + string2 + ".");
                    printWriter.println("If you have written it yourself, you probably have a bug to fix.");
                }
            } else {
                printWriter.println();
                printWriter.println("This is not a bug; it is a configuration problem");
            }
        }
        printWriter.flush();
        printWriter.close();
        return stringWriter.toString();
    }

    private void a(PrintWriter printWriter, String string, String string2) {
        boolean bl2 = string.indexOf("antlib:") == 0;
        String string3 = es_2.dP(string);
        printWriter.println("Cause: The name is undefined.");
        printWriter.println("Action: Check the spelling.");
        printWriter.println("Action: Check that any custom tasks/types have been declared.");
        printWriter.println("Action: Check that any <presetdef>/<macrodef> declarations have taken place.");
        if (string3.length() > 0) {
            List list = this.chC.ax(string3);
            if (list.size() > 0) {
                printWriter.println();
                printWriter.println("The definitions in the namespace " + string3 + " are:");
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    alv_2 alv_22 = (alv_2)iterator.next();
                    String string4 = es_2.dQ(alv_22.getName());
                    printWriter.println("    " + string4);
                }
            } else {
                printWriter.println("No types or tasks have been defined in this namespace yet");
                if (bl2) {
                    printWriter.println();
                    printWriter.println("This appears to be an antlib declaration. ");
                    printWriter.println("Action: Check that the implementing library exists in one of:");
                    printWriter.println(string2);
                }
            }
        }
    }

    private void a(PrintWriter printWriter, String string, boolean bl2, String string2) {
        printWriter.println("Cause: the class " + string + " was not found.");
        if (bl2) {
            printWriter.println("        This looks like one of Ant's optional components.");
            printWriter.println("Action: Check that the appropriate optional JAR exists in");
            printWriter.println(string2);
        } else {
            printWriter.println("Action: Check that the component has been correctly declared");
            printWriter.println("        and that the implementing JAR is in one of:");
            printWriter.println(string2);
        }
    }

    private void a(PrintWriter printWriter, boolean bl2, NoClassDefFoundError noClassDefFoundError, String string) {
        printWriter.println("Cause: Could not load a dependent class " + noClassDefFoundError.getMessage());
        if (bl2) {
            printWriter.println("       It is not enough to have Ant's optional JARs");
            printWriter.println("       you need the JAR files that the optional tasks depend upon.");
            printWriter.println("       Ant's optional task dependencies are listed in the manual.");
        } else {
            printWriter.println("       This class may be in a separate JAR that is not installed.");
        }
        printWriter.println("Action: Determine what extra JAR files are needed, and place them in one of:");
        printWriter.println(string);
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

