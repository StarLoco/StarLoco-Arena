/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TimeZone;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;

public final class Bc {
    private static final int aIx = 10000;
    private static final int aIy = 32;
    private static final int aIz = 1024;
    private static final int aIA = 1000;
    private static final int aIB = 60;
    private static final int aIC = 60;
    private static final String aID = "org.apache.tools.ant.taskdefs.optional.Test";
    protected static final String aIE = "Access to this property blocked by a security manager";
    static Class Z;
    static Class aIF;

    private Bc() {
    }

    public static boolean HY() {
        try {
            Class.forName(aID);
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
        return true;
    }

    public static void HZ() {
        try {
            Class<?> clazz = Class.forName(aID);
            String string = Bc.k(Z == null ? (Z = Bc.a("s")) : Z);
            String string2 = Bc.k(clazz);
            if (string != null && !string.equals(string2)) {
                throw new eq_2("Invalid implementation version between Ant core and Ant optional tasks.\n core    : " + string + "\n" + " optional: " + string2);
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            Bc.c(classNotFoundException);
        }
    }

    public static File[] Ia() {
        String string = System.getProperty("ant.home");
        if (string == null) {
            return null;
        }
        File file = new File(string, "lib");
        return Bc.n(file);
    }

    private static File[] n(File file) {
        ey_1 ey_12 = new ey_1();
        File[] fileArray = file.listFiles(ey_12);
        return fileArray;
    }

    public static void main(String[] stringArray) {
        Bc.a(System.out);
    }

    private static String k(Class clazz) {
        Package package_ = clazz.getPackage();
        return package_.getImplementationVersion();
    }

    private static String Ib() {
        SAXParser sAXParser = Bc.Ic();
        if (sAXParser == null) {
            return "Could not create an XML Parser";
        }
        String string = sAXParser.getClass().getName();
        return string;
    }

    private static SAXParser Ic() {
        SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
        if (sAXParserFactory == null) {
            return null;
        }
        SAXParser sAXParser = null;
        try {
            sAXParser = sAXParserFactory.newSAXParser();
        }
        catch (Exception exception) {
            Bc.c(exception);
        }
        return sAXParser;
    }

    private static String Id() {
        SAXParser sAXParser = Bc.Ic();
        if (sAXParser == null) {
            return null;
        }
        String string = Bc.l(sAXParser.getClass());
        return string;
    }

    private static String Ie() {
        try {
            XMLReader xMLReader = abj_1.aNh();
            return xMLReader.getClass().getName();
        }
        catch (eq_2 eq_22) {
            Bc.c(eq_22);
            return null;
        }
    }

    private static String If() {
        try {
            XMLReader xMLReader = abj_1.aNh();
            return Bc.l(xMLReader.getClass());
        }
        catch (eq_2 eq_22) {
            Bc.c(eq_22);
            return null;
        }
    }

    private static void c(Throwable throwable) {
    }

    private static String l(Class clazz) {
        File file = hx_2.e(clazz);
        return file == null ? null : file.getAbsolutePath();
    }

    public static void a(PrintStream printStream) {
        printStream.println("------- Ant diagnostics report -------");
        printStream.println(s_0.I());
        Bc.a(printStream, "Implementation Version");
        printStream.println("core tasks     : " + Bc.k(Z == null ? (Z = Bc.a("s")) : Z));
        Class<?> clazz = null;
        try {
            clazz = Class.forName(aID);
            printStream.println("optional tasks : " + Bc.k(clazz));
        }
        catch (ClassNotFoundException classNotFoundException) {
            Bc.c(classNotFoundException);
            printStream.println("optional tasks : not available");
        }
        Bc.a(printStream, "ANT PROPERTIES");
        Bc.c(printStream);
        Bc.a(printStream, "ANT_HOME/lib jar listing");
        Bc.d(printStream);
        Bc.a(printStream, "USER_HOME/.ant/lib jar listing");
        Bc.e(printStream);
        Bc.a(printStream, "Tasks availability");
        Bc.g(printStream);
        Bc.a(printStream, "org.apache.env.Which diagnostics");
        Bc.f(printStream);
        Bc.a(printStream, "XML Parser information");
        Bc.h(printStream);
        Bc.a(printStream, "System properties");
        Bc.b(printStream);
        Bc.a(printStream, "Temp dir");
        Bc.i(printStream);
        Bc.a(printStream, "Locale information");
        Bc.j(printStream);
        Bc.a(printStream, "Proxy information");
        Bc.k(printStream);
        printStream.println();
    }

    private static void a(PrintStream printStream, String string) {
        printStream.println();
        printStream.println("-------------------------------------------");
        printStream.print(" ");
        printStream.println(string);
        printStream.println("-------------------------------------------");
    }

    private static void b(PrintStream printStream) {
        Properties properties = null;
        try {
            properties = System.getProperties();
        }
        catch (SecurityException securityException) {
            Bc.c(securityException);
            printStream.println("Access to System.getProperties() blocked by a security manager");
        }
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            String string2 = Bc.getProperty(string);
            printStream.println(string + " : " + string2);
        }
    }

    private static String getProperty(String string) {
        String string2;
        try {
            string2 = System.getProperty(string);
        }
        catch (SecurityException securityException) {
            string2 = aIE;
        }
        return string2;
    }

    private static void c(PrintStream printStream) {
        UI uI = new UI();
        uI.agX();
        printStream.println("ant.version: " + uI.getProperty("ant.version"));
        printStream.println("ant.java.version: " + uI.getProperty("ant.java.version"));
        printStream.println("ant.core.lib: " + uI.getProperty("ant.core.lib"));
        printStream.println("ant.home: " + uI.getProperty("ant.home"));
    }

    private static void d(PrintStream printStream) {
        printStream.println("ant.home: " + System.getProperty("ant.home"));
        File[] fileArray = Bc.Ia();
        Bc.a(fileArray, printStream);
    }

    private static void e(PrintStream printStream) {
        String string = System.getProperty("user.home");
        printStream.println("user.home: " + string);
        File file = new File(string, Iw.bht);
        File[] fileArray = Bc.n(file);
        Bc.a(fileArray, printStream);
    }

    private static void a(File[] fileArray, PrintStream printStream) {
        if (fileArray == null) {
            printStream.println("No such directory.");
            return;
        }
        for (int j = 0; j < fileArray.length; ++j) {
            printStream.println(fileArray[j].getName() + " (" + fileArray[j].length() + " bytes)");
        }
    }

    private static void f(PrintStream printStream) {
        Throwable throwable = null;
        try {
            Class<?> clazz = Class.forName("org.apache.env.Which");
            Method method = clazz.getMethod("main", aIF == null ? (aIF = Bc.a("[Ljava.lang.String;")) : aIF);
            method.invoke(null, new Object[]{new String[0]});
        }
        catch (ClassNotFoundException classNotFoundException) {
            printStream.println("Not available.");
            printStream.println("Download it at http://xml.apache.org/commons/");
        }
        catch (InvocationTargetException invocationTargetException) {
            throwable = invocationTargetException.getTargetException() == null ? invocationTargetException : invocationTargetException.getTargetException();
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
        }
        if (throwable != null) {
            printStream.println("Error while running org.apache.env.Which");
            throwable.printStackTrace();
        }
    }

    private static void g(PrintStream printStream) {
        InputStream inputStream = (Z == null ? (Z = Bc.a("s")) : Z).getResourceAsStream("/org/apache/tools/ant/taskdefs/defaults.properties");
        if (inputStream == null) {
            printStream.println("None available");
        } else {
            Properties properties = new Properties();
            try {
                properties.load(inputStream);
                Enumeration enumeration = properties.keys();
                while (enumeration.hasMoreElements()) {
                    String string = (String)enumeration.nextElement();
                    String string2 = properties.getProperty(string);
                    try {
                        Class.forName(string2);
                        properties.remove(string);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        printStream.println(string + " : Not Available " + "(the implementation class is not present)");
                    }
                    catch (NoClassDefFoundError noClassDefFoundError) {
                        String string3 = noClassDefFoundError.getMessage().replace('/', '.');
                        printStream.println(string + " : Missing dependency " + string3);
                    }
                    catch (LinkageError linkageError) {
                        printStream.println(string + " : Initialization error");
                    }
                }
                if (properties.size() == 0) {
                    printStream.println("All defined tasks are available");
                } else {
                    printStream.println("A task being missing/unavailable should only matter if you are trying to use it");
                }
            }
            catch (IOException iOException) {
                printStream.println(iOException.getMessage());
            }
        }
    }

    private static void h(PrintStream printStream) {
        String string = Bc.Ib();
        String string2 = Bc.Id();
        Bc.a(printStream, "XML Parser", string, string2);
        Bc.a(printStream, "Namespace-aware parser", Bc.Ie(), Bc.If());
    }

    private static void a(PrintStream printStream, String string, String string2, String string3) {
        if (string2 == null) {
            string2 = "unknown";
        }
        if (string3 == null) {
            string3 = "unknown";
        }
        printStream.println(string + " : " + string2);
        printStream.println(string + " Location: " + string3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void i(PrintStream printStream) {
        FileOutputStream fileOutputStream;
        File file;
        block10: {
            String string = System.getProperty("java.io.tmpdir");
            if (string == null) {
                printStream.println("Warning: java.io.tmpdir is undefined");
                return;
            }
            printStream.println("Temp dir is " + string);
            File file2 = new File(string);
            if (!file2.exists()) {
                printStream.println("Warning, java.io.tmpdir directory does not exist: " + string);
                return;
            }
            long l2 = System.currentTimeMillis();
            file = null;
            fileOutputStream = null;
            try {
                file = File.createTempFile("diag", "txt", file2);
                fileOutputStream = new FileOutputStream(file);
                byte[] byArray = new byte[1024];
                for (int j = 0; j < 32; ++j) {
                    fileOutputStream.write(byArray);
                }
                fileOutputStream.close();
                fileOutputStream = null;
                long l3 = file.lastModified();
                file.delete();
                printStream.println("Temp dir is writeable");
                long l4 = l3 - l2;
                printStream.println("Temp dir alignment with system clock is " + l4 + " ms");
                if (Math.abs(l4) <= 10000L) break block10;
                printStream.println("Warning: big clock drift -maybe a network filesystem");
            }
            catch (IOException iOException) {
                try {
                    Bc.c(iOException);
                    printStream.println("Failed to create a temporary file in the temp dir " + string);
                    printStream.println("File  " + file + " could not be created/written to");
                }
                catch (Throwable throwable) {
                    ga_2.a(fileOutputStream);
                    if (file != null && file.exists()) {
                        file.delete();
                    }
                    throw throwable;
                }
                ga_2.a(fileOutputStream);
                if (file != null && file.exists()) {
                    file.delete();
                }
            }
        }
        ga_2.a(fileOutputStream);
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    private static void j(PrintStream printStream) {
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = calendar.getTimeZone();
        printStream.println("Timezone " + timeZone.getDisplayName() + " offset=" + timeZone.getOffset(calendar.get(0), calendar.get(1), calendar.get(2), calendar.get(5), calendar.get(7), ((calendar.get(11) * 60 + calendar.get(12)) * 60 + calendar.get(13)) * 1000 + calendar.get(14)));
    }

    private static void b(PrintStream printStream, String string) {
        String string2 = Bc.getProperty(string);
        if (string2 != null) {
            printStream.print(string);
            printStream.print(" = ");
            printStream.print('\"');
            printStream.print(string2);
            printStream.println('\"');
        }
    }

    private static void k(PrintStream printStream) {
        Bc.b(printStream, "http.proxyHost");
        Bc.b(printStream, "http.proxyPort");
        Bc.b(printStream, "http.proxyUser");
        Bc.b(printStream, "http.proxyPassword");
        Bc.b(printStream, "http.nonProxyHosts");
        Bc.b(printStream, "https.proxyHost");
        Bc.b(printStream, "https.proxyPort");
        Bc.b(printStream, "https.nonProxyHosts");
        Bc.b(printStream, "ftp.proxyHost");
        Bc.b(printStream, "ftp.proxyPort");
        Bc.b(printStream, "ftp.nonProxyHosts");
        Bc.b(printStream, "socksProxyHost");
        Bc.b(printStream, "socksProxyPort");
        Bc.b(printStream, "java.net.socks.username");
        Bc.b(printStream, "java.net.socks.password");
        if (ako_1.azZ() < 15) {
            return;
        }
        Bc.b(printStream, "java.net.useSystemProxies");
        String string = "org.apache.tools.ant.util.java15.ProxyDiagnostics";
        try {
            Class<?> clazz = Class.forName("org.apache.tools.ant.util.java15.ProxyDiagnostics");
            Object obj = clazz.newInstance();
            printStream.println("Java1.5+ proxy settings:");
            printStream.println(obj.toString());
        }
        catch (ClassNotFoundException classNotFoundException) {
        }
        catch (IllegalAccessException illegalAccessException) {
        }
        catch (InstantiationException instantiationException) {
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            // empty catch block
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

