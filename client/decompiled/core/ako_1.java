/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;

/*
 * Renamed from akO
 */
public final class ako_1 {
    private static final boolean cDX = xk_1.cO("dos");
    private static final boolean cDY = xk_1.isName("netware");
    private static final boolean cDZ = xk_1.isName("aix");
    private static final String JAVA_HOME = System.getProperty("java.home");
    private static final ga_2 xa = ga_2.Qo();
    private static String cEa;
    private static int cEb;
    public static final String bQA = "1.0";
    public static final int VERSION_1_0 = 10;
    public static final String bQB = "1.1";
    public static final int VERSION_1_1 = 11;
    public static final String bQC = "1.2";
    public static final int VERSION_1_2 = 12;
    public static final String bQD = "1.3";
    public static final int VERSION_1_3 = 13;
    public static final String bQE = "1.4";
    public static final int VERSION_1_4 = 14;
    public static final String cEc = "1.5";
    public static final int VERSION_1_5 = 15;
    public static final String cEd = "1.6";
    public static final int VERSION_1_6 = 16;
    private static boolean cEe;
    private static Vector cEf;

    private ako_1() {
    }

    public static String ahi() {
        return cEa;
    }

    public static int azZ() {
        return cEb;
    }

    public static boolean iy(String string) {
        return cEa.equals(string);
    }

    public static boolean iz(String string) {
        return cEa.compareTo(string) >= 0;
    }

    public static boolean aAa() {
        return cEe;
    }

    public static String iA(String string) {
        if (cDY) {
            return string;
        }
        File file = null;
        if (cDZ) {
            file = ako_1.Y(JAVA_HOME + "/sh", string);
        }
        if (file == null) {
            file = ako_1.Y(JAVA_HOME + "/bin", string);
        }
        if (file != null) {
            return file.getAbsolutePath();
        }
        return ako_1.iC(string);
    }

    public static String iB(String string) {
        if (cDY) {
            return string;
        }
        File file = null;
        if (cDZ) {
            file = ako_1.Y(JAVA_HOME + "/../sh", string);
        }
        if (file == null) {
            file = ako_1.Y(JAVA_HOME + "/../bin", string);
        }
        if (file != null) {
            return file.getAbsolutePath();
        }
        return ako_1.iA(string);
    }

    private static String iC(String string) {
        return string + (cDX ? ".exe" : "");
    }

    private static File Y(String string, String string2) {
        File file = xa.dZ(string);
        File file2 = null;
        if (file.exists() && !(file2 = new File(file, ako_1.iC(string2))).exists()) {
            file2 = null;
        }
        return file2;
    }

    private static void aAb() {
        cEf = new Vector();
        switch (cEb) {
            case 15: 
            case 16: {
                cEf.addElement("com.sun.org.apache");
            }
            case 14: {
                if (cEb == 14) {
                    cEf.addElement("org.apache.crimson");
                    cEf.addElement("org.apache.xalan");
                    cEf.addElement("org.apache.xml");
                    cEf.addElement("org.apache.xpath");
                }
                cEf.addElement("org.ietf.jgss");
                cEf.addElement("org.w3c.dom");
                cEf.addElement("org.xml.sax");
            }
            case 13: {
                cEf.addElement("org.omg");
                cEf.addElement("com.sun.corba");
                cEf.addElement("com.sun.jndi");
                cEf.addElement("com.sun.media");
                cEf.addElement("com.sun.naming");
                cEf.addElement("com.sun.org.omg");
                cEf.addElement("com.sun.rmi");
                cEf.addElement("sunw.io");
                cEf.addElement("sunw.util");
            }
            case 12: {
                cEf.addElement("com.sun.java");
                cEf.addElement("com.sun.image");
            }
        }
        cEf.addElement("sun");
        cEf.addElement("java");
        cEf.addElement("javax");
    }

    public static Vector aAc() {
        Vector<String> vector = new Vector<String>();
        vector.addElement("java.lang.Object");
        switch (cEb) {
            case 15: 
            case 16: {
                vector.addElement("com.sun.org.apache.xerces.internal.jaxp.datatype.DatatypeFactoryImpl ");
            }
            case 14: {
                vector.addElement("sun.audio.AudioPlayer");
                if (cEb == 14) {
                    vector.addElement("org.apache.crimson.parser.ContentModel");
                    vector.addElement("org.apache.xalan.processor.ProcessorImport");
                    vector.addElement("org.apache.xml.utils.URI");
                    vector.addElement("org.apache.xpath.XPathFactory");
                }
                vector.addElement("org.ietf.jgss.Oid");
                vector.addElement("org.w3c.dom.Attr");
                vector.addElement("org.xml.sax.XMLReader");
            }
            case 13: {
                vector.addElement("org.omg.CORBA.Any");
                vector.addElement("com.sun.corba.se.internal.corba.AnyImpl");
                vector.addElement("com.sun.jndi.ldap.LdapURL");
                vector.addElement("com.sun.media.sound.Printer");
                vector.addElement("com.sun.naming.internal.VersionHelper");
                vector.addElement("com.sun.org.omg.CORBA.Initializer");
                vector.addElement("sunw.io.Serializable");
                vector.addElement("sunw.util.EventListener");
            }
            case 12: {
                vector.addElement("javax.accessibility.Accessible");
                vector.addElement("sun.misc.BASE64Encoder");
                vector.addElement("com.sun.image.codec.jpeg.JPEGCodec");
            }
        }
        vector.addElement("sun.reflect.SerializationConstructorAccessorImpl");
        vector.addElement("sun.net.www.http.HttpClient");
        vector.addElement("sun.audio.AudioPlayer");
        return vector;
    }

    public static Vector aAd() {
        if (cEf == null) {
            ako_1.aAb();
        }
        return cEf;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static File t(String[] stringArray) {
        File file = xa.a("ANT", ".JAVA_OPTS", (File)null, false, true);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (int j = 0; j < stringArray.length; ++j) {
                printWriter.println(stringArray[j]);
            }
            Object var5_4 = null;
        }
        catch (Throwable throwable) {
            Object var5_5 = null;
            ga_2.a(printWriter);
            throw throwable;
        }
        ga_2.a(printWriter);
        return file;
    }

    public static String aAe() {
        return JAVA_HOME;
    }

    static {
        try {
            cEa = bQA;
            cEb = 10;
            Class.forName("java.lang.Void");
            cEa = bQB;
            ++cEb;
            Class.forName("java.lang.ThreadLocal");
            cEa = bQC;
            ++cEb;
            Class.forName("java.lang.StrictMath");
            cEa = bQD;
            ++cEb;
            Class.forName("java.lang.CharSequence");
            cEa = bQE;
            ++cEb;
            Class.forName("java.net.Proxy");
            cEa = cEc;
            ++cEb;
            Class.forName("java.util.ServiceLoader");
            cEa = cEd;
            ++cEb;
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        cEe = false;
        try {
            Class.forName("kaffe.util.NotImplemented");
            cEe = true;
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }
}

