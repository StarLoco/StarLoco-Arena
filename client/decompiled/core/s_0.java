/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

/*
 * Renamed from s
 */
public class s_0
implements ain_0 {
    private static final Set H = new HashSet();
    public static final String I = "build.xml";
    private int J = 2;
    private File K;
    private static PrintStream out;
    private static PrintStream err;
    private Vector L = new Vector();
    private Properties M = new Properties();
    private Vector listeners = new Vector(1);
    private Vector N = new Vector(1);
    private boolean O = true;
    private boolean P = false;
    private String Q = null;
    private String R = null;
    private boolean S = false;
    private boolean T = false;
    private boolean U = false;
    private static boolean V;
    private Integer W = null;
    private boolean X = false;
    private static String Y;
    static Class Z;
    static Class aa;
    static Class ab;
    static Class ac;

    private static void a(Throwable throwable) {
        String string = throwable.getMessage();
        if (string != null) {
            System.err.println(string);
        }
    }

    public static void a(String[] stringArray, Properties properties, ClassLoader classLoader) {
        s_0 s_02 = new s_0();
        s_02.b(stringArray, properties, classLoader);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void b(String[] stringArray, Properties properties, ClassLoader classLoader) {
        try {
            Bc.HZ();
            this.a(stringArray);
        }
        catch (Throwable throwable) {
            s_0.E();
            s_0.a(throwable);
            this.exit(1);
            return;
        }
        if (properties != null) {
            Enumeration enumeration = properties.keys();
            while (enumeration.hasMoreElements()) {
                String string = (String)enumeration.nextElement();
                String string2 = properties.getProperty(string);
                this.M.put(string, string2);
            }
        }
        int n2 = 1;
        try {
            try {
                this.a(classLoader);
                n2 = 0;
            }
            catch (agq_0 agq_02) {
                n2 = agq_02.getStatus();
                if (n2 != 0) {
                    throw agq_02;
                }
            }
        }
        catch (eq_2 eq_22) {
            if (err != System.err) {
                s_0.a(eq_22);
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            s_0.a(throwable);
        }
        finally {
            s_0.E();
        }
        this.exit(n2);
    }

    protected void exit(int n2) {
        System.exit(n2);
    }

    private static void E() {
        if (V) {
            ga_2.a(out);
            ga_2.a(err);
        }
    }

    public static void main(String[] stringArray) {
        s_0.a(stringArray, null, null);
    }

    public s_0() {
    }

    protected s_0(String[] stringArray) {
        this.a(stringArray);
    }

    private void a(String[] stringArray) {
        String string = null;
        PrintStream printStream = null;
        for (int j = 0; j < stringArray.length; ++j) {
            Object object;
            String string2 = stringArray[j];
            if (string2.equals("-help") || string2.equals("-h")) {
                s_0.printUsage();
                return;
            }
            if (string2.equals("-version")) {
                s_0.H();
                return;
            }
            if (string2.equals("-diagnostics")) {
                Bc.a(System.out);
                return;
            }
            if (string2.equals("-quiet") || string2.equals("-q")) {
                this.J = 1;
                continue;
            }
            if (string2.equals("-verbose") || string2.equals("-v")) {
                s_0.H();
                this.J = 3;
                continue;
            }
            if (string2.equals("-debug") || string2.equals("-d")) {
                s_0.H();
                this.J = 4;
                continue;
            }
            if (string2.equals("-noinput")) {
                this.O = false;
                continue;
            }
            if (string2.equals("-logfile") || string2.equals("-l")) {
                try {
                    object = new File(stringArray[j + 1]);
                    ++j;
                    printStream = new PrintStream(new FileOutputStream((File)object));
                    V = true;
                    continue;
                }
                catch (IOException iOException) {
                    String string3 = "Cannot write on the specified log file. Make sure the path exists and you have write permissions.";
                    throw new eq_2(string3);
                }
                catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                    String string4 = "You must specify a log file when using the -log argument";
                    throw new eq_2(string4);
                }
            }
            if (string2.equals("-buildfile") || string2.equals("-file") || string2.equals("-f")) {
                j = this.a(stringArray, j);
                continue;
            }
            if (string2.equals("-listener")) {
                j = this.b(stringArray, j);
                continue;
            }
            if (string2.startsWith("-D")) {
                j = this.c(stringArray, j);
                continue;
            }
            if (string2.equals("-logger")) {
                j = this.d(stringArray, j);
                continue;
            }
            if (string2.equals("-inputhandler")) {
                j = this.e(stringArray, j);
                continue;
            }
            if (string2.equals("-emacs") || string2.equals("-e")) {
                this.S = true;
                continue;
            }
            if (string2.equals("-projecthelp") || string2.equals("-p")) {
                this.U = true;
                continue;
            }
            if (string2.equals("-find") || string2.equals("-s")) {
                if (j < stringArray.length - 1) {
                    string = stringArray[++j];
                    continue;
                }
                string = I;
                continue;
            }
            if (string2.startsWith("-propertyfile")) {
                j = this.f(stringArray, j);
                continue;
            }
            if (string2.equals("-k") || string2.equals("-keep-going")) {
                this.P = true;
                continue;
            }
            if (string2.equals("-nice")) {
                j = this.g(stringArray, j);
                continue;
            }
            if (H.contains(string2)) {
                object = "Ant's Main method is being handed an option " + string2 + " that is only for the launcher class." + "\nThis can be caused by a version mismatch between " + "the ant script/.bat file and Ant itself.";
                throw new eq_2((String)object);
            }
            if (string2.equals("-autoproxy")) {
                this.X = true;
                continue;
            }
            if (string2.startsWith("-")) {
                object = "Unknown argument: " + string2;
                System.err.println((String)object);
                s_0.printUsage();
                throw new eq_2("");
            }
            this.L.addElement(string2);
        }
        if (this.K == null) {
            this.K = string != null ? this.a(System.getProperty("user.dir"), string) : new File(I);
        }
        if (!this.K.exists()) {
            System.out.println("Buildfile: " + this.K + " does not exist!");
            throw new eq_2("Build failed");
        }
        if (this.K.isDirectory()) {
            System.out.println("What? Buildfile: " + this.K + " is a dir!");
            throw new eq_2("Build failed");
        }
        this.F();
        if (this.J >= 2) {
            System.out.println("Buildfile: " + this.K);
        }
        if (printStream != null) {
            out = printStream;
            err = printStream;
            System.setOut(out);
            System.setErr(err);
        }
        this.T = true;
    }

    private int a(String[] stringArray, int n2) {
        try {
            this.K = new File(stringArray[++n2].replace('/', File.separatorChar));
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new eq_2("You must specify a buildfile when using the -buildfile argument");
        }
        return n2;
    }

    private int b(String[] stringArray, int n2) {
        try {
            this.listeners.addElement(stringArray[n2 + 1]);
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            String string = "You must specify a classname when using the -listener argument";
            throw new eq_2(string);
        }
        return ++n2;
    }

    private int c(String[] stringArray, int n2) {
        String string = stringArray[n2];
        String string2 = string.substring(2, string.length());
        String string3 = null;
        int n3 = string2.indexOf("=");
        if (n3 > 0) {
            string3 = string2.substring(n3 + 1);
            string2 = string2.substring(0, n3);
        } else if (n2 < stringArray.length - 1) {
            string3 = stringArray[++n2];
        } else {
            throw new eq_2("Missing value for property " + string2);
        }
        this.M.put(string2, string3);
        return n2;
    }

    private int d(String[] stringArray, int n2) {
        if (this.Q != null) {
            throw new eq_2("Only one logger class may be specified.");
        }
        try {
            this.Q = stringArray[++n2];
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new eq_2("You must specify a classname when using the -logger argument");
        }
        return n2;
    }

    private int e(String[] stringArray, int n2) {
        if (this.R != null) {
            throw new eq_2("Only one input handler class may be specified.");
        }
        try {
            this.R = stringArray[++n2];
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new eq_2("You must specify a classname when using the -inputhandler argument");
        }
        return n2;
    }

    private int f(String[] stringArray, int n2) {
        try {
            this.N.addElement(stringArray[++n2]);
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            String string = "You must specify a property filename when using the -propertyfile argument";
            throw new eq_2(string);
        }
        return n2;
    }

    private int g(String[] stringArray, int n2) {
        try {
            this.W = Integer.decode(stringArray[++n2]);
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new eq_2("You must supply a niceness value (1-10) after the -nice option");
        }
        catch (NumberFormatException numberFormatException) {
            throw new eq_2("Unrecognized niceness value: " + stringArray[n2]);
        }
        if (this.W < 1 || this.W > 10) {
            throw new eq_2("Niceness value is out of the range 1-10");
        }
        return n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void F() {
        for (int j = 0; j < this.N.size(); ++j) {
            String string = (String)this.N.elementAt(j);
            Properties properties = new Properties();
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(string);
                properties.load(fileInputStream);
            }
            catch (IOException iOException) {
                try {
                    System.out.println("Could not load property file " + string + ": " + iOException.getMessage());
                }
                catch (Throwable throwable) {
                    ga_2.h(fileInputStream);
                    throw throwable;
                }
                ga_2.h(fileInputStream);
            }
            ga_2.h(fileInputStream);
            Enumeration<?> enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String string2 = (String)enumeration.nextElement();
                if (this.M.getProperty(string2) != null) continue;
                this.M.put(string2, properties.getProperty(string2));
            }
        }
    }

    private File a(File file) {
        File file2 = file.getParentFile();
        if (file2 != null && this.J >= 3) {
            System.out.println("Searching in " + file2.getAbsolutePath());
        }
        return file2;
    }

    private File a(String string, String string2) {
        if (this.J >= 2) {
            System.out.println("Searching for " + string2 + " ...");
        }
        File file = new File(new File(string).getAbsolutePath());
        File file2 = new File(file, string2);
        while (!file2.exists()) {
            if ((file = this.a(file)) == null) {
                throw new eq_2("Could not locate a build file!");
            }
            file2 = new File(file, string2);
        }
        return file2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(ClassLoader classLoader) {
        if (!this.T) {
            return;
        }
        UI uI = new UI();
        uI.d(classLoader);
        Throwable throwable = null;
        try {
            this.a(uI);
            this.b(uI);
            PrintStream printStream = System.err;
            PrintStream printStream2 = System.out;
            InputStream inputStream = System.in;
            SecurityManager securityManager = null;
            securityManager = System.getSecurityManager();
            try {
                Object object;
                if (this.O) {
                    uI.j(System.in);
                }
                System.setIn(new ady_0(uI));
                System.setOut(new PrintStream(new bU(uI, false)));
                System.setErr(new PrintStream(new bU(uI, true)));
                if (!this.U) {
                    uI.ahq();
                }
                if (this.W != null) {
                    try {
                        uI.l("Setting Ant's thread priority to " + this.W, 3);
                        Thread.currentThread().setPriority(this.W);
                    }
                    catch (SecurityException securityException) {
                        uI.log("A security manager refused to set the -nice value");
                    }
                }
                uI.init();
                Enumeration enumeration = this.M.keys();
                while (enumeration.hasMoreElements()) {
                    object = (String)enumeration.nextElement();
                    String string = (String)this.M.get(object);
                    uI.E((String)object, string);
                }
                uI.E("ant.file", this.K.getAbsolutePath());
                uI.cq(this.P);
                if (this.X) {
                    object = new zc_1(uI);
                    ((zc_1)object).anl();
                }
                es_2.a(uI, this.K);
                if (this.U) {
                    s_0.c(uI);
                    s_0.a(uI, this.J > 2);
                    return;
                }
                if (this.L.size() == 0 && uI.ahe() != null) {
                    this.L.addElement(uI.ahe());
                }
                uI.e(this.L);
            }
            finally {
                if (securityManager != null) {
                    System.setSecurityManager(securityManager);
                }
                System.setOut(printStream2);
                System.setErr(printStream);
                System.setIn(inputStream);
            }
        }
        catch (RuntimeException runtimeException) {
            throwable = runtimeException;
            throw runtimeException;
        }
        catch (Error error) {
            throwable = error;
            throw error;
        }
        finally {
            if (!this.U) {
                uI.e(throwable);
            } else if (throwable != null) {
                uI.l(throwable.toString(), 0);
            }
        }
    }

    protected void a(UI uI) {
        uI.a(this.G());
        for (int j = 0; j < this.listeners.size(); ++j) {
            String string = (String)this.listeners.elementAt(j);
            kd_1 kd_12 = (kd_1)awK.a(string, (Z == null ? s_0.a("s") : Z).getClassLoader(), aa == null ? s_0.a("Kd") : aa);
            uI.at(kd_12);
            uI.a(kd_12);
        }
    }

    private void b(UI uI) {
        hu_1 hu_12 = null;
        if (this.R == null) {
            hu_12 = new vm_1();
        } else {
            hu_12 = (hu_1)awK.a(this.R, (Z == null ? (Z = s_0.a("s")) : Z).getClassLoader(), ab == null ? (ab = s_0.a("HU")) : ab);
            uI.at(hu_12);
        }
        uI.a(hu_12);
    }

    private kp_2 G() {
        kp_2 kp_22 = null;
        if (this.Q != null) {
            try {
                kp_22 = (kp_2)awK.a(this.Q, (Z == null ? (Z = s_0.a("s")) : Z).getClassLoader(), ac == null ? (ac = s_0.a("Kp")) : ac);
            }
            catch (eq_2 eq_22) {
                System.err.println("The specified logger class " + this.Q + " could not be used because " + eq_22.getMessage());
                throw new RuntimeException();
            }
        } else {
            kp_22 = new bz_2();
        }
        kp_22.eN(this.J);
        kp_22.l(out);
        kp_22.m(err);
        kp_22.aZ(this.S);
        return kp_22;
    }

    private static void printUsage() {
        String string = System.getProperty("line.separator");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ant [options] [target [target2 [target3] ...]]" + string);
        stringBuffer.append("Options: " + string);
        stringBuffer.append("  -help, -h              print this message" + string);
        stringBuffer.append("  -projecthelp, -p       print project help information" + string);
        stringBuffer.append("  -version               print the version information and exit" + string);
        stringBuffer.append("  -diagnostics           print information that might be helpful to" + string);
        stringBuffer.append("                         diagnose or report problems." + string);
        stringBuffer.append("  -quiet, -q             be extra quiet" + string);
        stringBuffer.append("  -verbose, -v           be extra verbose" + string);
        stringBuffer.append("  -debug, -d             print debugging information" + string);
        stringBuffer.append("  -emacs, -e             produce logging information without adornments" + string);
        stringBuffer.append("  -lib <path>            specifies a path to search for jars and classes" + string);
        stringBuffer.append("  -logfile <file>        use given file for log" + string);
        stringBuffer.append("    -l     <file>                ''" + string);
        stringBuffer.append("  -logger <classname>    the class which is to perform logging" + string);
        stringBuffer.append("  -listener <classname>  add an instance of class as a project listener" + string);
        stringBuffer.append("  -noinput               do not allow interactive input" + string);
        stringBuffer.append("  -buildfile <file>      use given buildfile" + string);
        stringBuffer.append("    -file    <file>              ''" + string);
        stringBuffer.append("    -f       <file>              ''" + string);
        stringBuffer.append("  -D<property>=<value>   use value for given property" + string);
        stringBuffer.append("  -keep-going, -k        execute all targets that do not depend" + string);
        stringBuffer.append("                         on failed target(s)" + string);
        stringBuffer.append("  -propertyfile <name>   load all properties from file with -D" + string);
        stringBuffer.append("                         properties taking precedence" + string);
        stringBuffer.append("  -inputhandler <class>  the class which will handle input requests" + string);
        stringBuffer.append("  -find <file>           (s)earch for buildfile towards the root of" + string);
        stringBuffer.append("    -s  <file>           the filesystem and use it" + string);
        stringBuffer.append("  -nice  number          A niceness value for the main thread:" + string + "                         1 (lowest) to 10 (highest); 5 is the default" + string);
        stringBuffer.append("  -nouserlib             Run ant without using the jar files from" + string + "                         ${user.home}/.ant/lib" + string);
        stringBuffer.append("  -noclasspath           Run ant without using CLASSPATH" + string);
        stringBuffer.append("  -autoproxy             Java1.5+: use the OS proxy settings" + string);
        stringBuffer.append("  -main <class>          override Ant's normal entry point");
        System.out.println(stringBuffer.toString());
    }

    private static void H() {
        System.out.println(s_0.I());
    }

    public static synchronized String I() {
        if (Y == null) {
            try {
                Properties properties = new Properties();
                InputStream inputStream = (Z == null ? (Z = s_0.a("s")) : Z).getResourceAsStream("/org/apache/tools/ant/version.txt");
                properties.load(inputStream);
                inputStream.close();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Apache Ant version ");
                stringBuffer.append(properties.getProperty("VERSION"));
                stringBuffer.append(" compiled on ");
                stringBuffer.append(properties.getProperty("DATE"));
                Y = stringBuffer.toString();
            }
            catch (IOException iOException) {
                throw new eq_2("Could not load the version information:" + iOException.getMessage());
            }
            catch (NullPointerException nullPointerException) {
                throw new eq_2("Could not load the version information.");
            }
        }
        return Y;
    }

    private static void c(UI uI) {
        if (uI.getDescription() != null) {
            uI.log(uI.getDescription());
        }
    }

    private static Map a(Map map) {
        Object object;
        Object object2;
        HashMap<axc_0, id_2> hashMap = new HashMap<axc_0, id_2>();
        Object object3 = map.entrySet().iterator();
        while (object3.hasNext()) {
            object2 = object3.next();
            object = (String)object2.getKey();
            id_2 id_22 = (id_2)object2.getValue();
            id_2 id_23 = (id_2)hashMap.get(id_22.hW());
            if (id_23 != null && id_23.getName().length() <= ((String)object).length()) continue;
            hashMap.put(id_22.hW(), id_22);
        }
        object3 = new HashMap();
        object2 = hashMap.values().iterator();
        while (object2.hasNext()) {
            object = (id_2)object2.next();
            object3.put(((id_2)object).getName(), object);
        }
        return object3;
    }

    private static void a(UI uI, boolean bl2) {
        int n2 = 0;
        Map map = s_0.a(uI.ahn());
        Vector<String> vector = new Vector<String>();
        Vector<String> vector2 = new Vector<String>();
        Vector<String> vector3 = new Vector<String>();
        Object object = map.values().iterator();
        while (object.hasNext()) {
            int n3;
            id_2 id_22 = (id_2)object.next();
            String string = id_22.getName();
            if (string.equals("")) continue;
            String string2 = id_22.getDescription();
            if (string2 == null) {
                n3 = s_0.a(vector3, string);
                vector3.insertElementAt(string, n3);
                continue;
            }
            n3 = s_0.a(vector, string);
            vector.insertElementAt(string, n3);
            vector2.insertElementAt(string2, n3);
            if (string.length() <= n2) continue;
            n2 = string.length();
        }
        s_0.a(uI, vector, vector2, "Main targets:", n2);
        if (vector.size() == 0) {
            bl2 = true;
        }
        if (bl2) {
            s_0.a(uI, vector3, null, "Other targets:", 0);
        }
        if ((object = uI.ahe()) != null && !"".equals(object)) {
            uI.log("Default target: " + (String)object);
        }
    }

    private static int a(Vector vector, String string) {
        int n2 = vector.size();
        for (int j = 0; j < vector.size() && n2 == vector.size(); ++j) {
            if (string.compareTo((String)vector.elementAt(j)) >= 0) continue;
            n2 = j;
        }
        return n2;
    }

    private static void a(UI uI, Vector vector, Vector vector2, String string, int n2) {
        String string2 = System.getProperty("line.separator");
        String string3 = "    ";
        while (string3.length() <= n2) {
            string3 = string3 + string3;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(string + string2 + string2);
        for (int j = 0; j < vector.size(); ++j) {
            stringBuffer.append(" ");
            stringBuffer.append(vector.elementAt(j));
            if (vector2 != null) {
                stringBuffer.append(string3.substring(0, n2 - ((String)vector.elementAt(j)).length() + 2));
                stringBuffer.append(vector2.elementAt(j));
            }
            stringBuffer.append(string2);
        }
        uI.l(stringBuffer.toString(), 1);
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    static {
        H.add("-lib");
        H.add("-cp");
        H.add("-noclasspath");
        H.add("--noclasspath");
        H.add("-nouserlib");
        H.add("-main");
        out = System.out;
        err = System.err;
        V = false;
        Y = null;
    }
}

