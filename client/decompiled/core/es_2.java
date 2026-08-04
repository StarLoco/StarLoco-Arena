/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;
import org.xml.sax.AttributeList;

/*
 * Renamed from ES
 */
public class es_2 {
    public static final String aTT = "antlib:org.apache.tools.ant";
    public static final String aTU = "ant:current";
    public static final String aTV = "antlib:";
    public static final String aTW = "ant-type";
    public static final String aTX = "org.apache.tools.ant.ProjectHelper";
    public static final String aTY = "META-INF/services/org.apache.tools.ant.ProjectHelper";
    public static final String aTZ = "ant.projectHelper";
    private Vector aUa = new Vector();

    public static void a(UI uI, File file) {
        es_2 es_22 = es_2.OE();
        uI.o(aTZ, es_22);
        es_22.a(uI, file);
    }

    public Vector OD() {
        return this.aUa;
    }

    public void a(UI uI, Object object) {
        throw new eq_2("ProjectHelper.parse() must be implemented in a helper plugin " + this.getClass().getName());
    }

    public static es_2 OE() {
        es_2 es_22;
        block11: {
            es_22 = null;
            String string = System.getProperty(aTX);
            try {
                if (string != null) {
                    es_22 = es_2.dO(string);
                }
            }
            catch (SecurityException securityException) {
                System.out.println("Unable to load ProjectHelper class \"" + string + " specified in system property " + aTX);
            }
            if (es_22 == null) {
                try {
                    InputStreamReader inputStreamReader;
                    ClassLoader classLoader = hx_2.getContextClassLoader();
                    InputStream inputStream = null;
                    if (classLoader != null) {
                        inputStream = classLoader.getResourceAsStream(aTY);
                    }
                    if (inputStream == null) {
                        inputStream = ClassLoader.getSystemResourceAsStream(aTY);
                    }
                    if (inputStream == null) break block11;
                    try {
                        inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    }
                    catch (UnsupportedEncodingException unsupportedEncodingException) {
                        inputStreamReader = new InputStreamReader(inputStream);
                    }
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String string2 = bufferedReader.readLine();
                    bufferedReader.close();
                    if (string2 != null && !"".equals(string2)) {
                        es_22 = es_2.dO(string2);
                    }
                }
                catch (Exception exception) {
                    System.out.println("Unable to load ProjectHelper from service META-INF/services/org.apache.tools.ant.ProjectHelper");
                }
            }
        }
        return es_22 == null ? new amj_2() : es_22;
    }

    private static es_2 dO(String string) {
        ClassLoader classLoader = hx_2.getContextClassLoader();
        try {
            Class<?> clazz = null;
            if (classLoader != null) {
                try {
                    clazz = classLoader.loadClass(string);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    // empty catch block
                }
            }
            if (clazz == null) {
                clazz = Class.forName(string);
            }
            return (es_2)clazz.newInstance();
        }
        catch (Exception exception) {
            throw new eq_2(exception);
        }
    }

    public static ClassLoader getContextClassLoader() {
        return hx_2.ll() ? hx_2.getContextClassLoader() : null;
    }

    public static void a(Object object, AttributeList attributeList, UI uI) {
        if (object instanceof akm) {
            object = ((akm)object).OV();
        }
        hm_2 hm_22 = hm_2.a(uI, object.getClass());
        int n2 = attributeList.getLength();
        for (int j = 0; j < n2; ++j) {
            String string = es_2.a(uI, attributeList.getValue(j), uI.ahc());
            try {
                hm_22.a(uI, object, attributeList.getName(j).toLowerCase(Locale.US), string);
                continue;
            }
            catch (eq_2 eq_22) {
                if (attributeList.getName(j).equals("id")) continue;
                throw eq_22;
            }
        }
    }

    public static void a(UI uI, Object object, char[] cArray, int n2, int n3) {
        es_2.b(uI, object, new String(cArray, n2, n3));
    }

    public static void b(UI uI, Object object, String string) {
        if (string == null) {
            return;
        }
        if (object instanceof akm) {
            object = ((akm)object).OV();
        }
        hm_2.a(uI, object.getClass()).b(uI, object, string);
    }

    public static void a(UI uI, Object object, Object object2, String string) {
        hm_2 hm_22 = hm_2.a(uI, object.getClass());
        hm_22.b(uI, object, object2, string);
    }

    public static String c(UI uI, String string) {
        return uI.fZ(string);
    }

    public static String a(UI uI, String string, Hashtable hashtable) {
        afc_2 afc_22 = afc_2.W(uI);
        return afc_22.a(null, string, hashtable);
    }

    public static void a(String string, Vector vector, Vector vector2) {
        afc_2.b(string, vector, vector2);
    }

    public static String s(String string, String string2) {
        if (string == null || string.equals("") || string.equals(aTT)) {
            return string2;
        }
        return string + ":" + string2;
    }

    public static String dP(String string) {
        if (string == null) {
            return "";
        }
        int n2 = string.lastIndexOf(58);
        if (n2 == -1) {
            return "";
        }
        return string.substring(0, n2);
    }

    public static String dQ(String string) {
        int n2 = string.lastIndexOf(58);
        if (n2 == -1) {
            return string;
        }
        return string.substring(n2 + 1);
    }

    public static eq_2 a(eq_2 eq_22, axc_0 axc_02) {
        if (eq_22.hW() == null || eq_22.getMessage() == null) {
            return eq_22;
        }
        String string = "The following error occurred while executing this line:" + System.getProperty("line.separator") + eq_22.hW().toString() + eq_22.getMessage();
        if (axc_02 == null) {
            return new eq_2(string, eq_22);
        }
        return new eq_2(string, eq_22, axc_02);
    }
}

