/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/*
 * Renamed from aHA
 */
public class aha_2 {
    public static final String dMH = "logback.xml";
    public static final String dMI = "logback-test.xml";
    public static final String dMJ = "logback.configurationFile";
    public static final String dMK = "logback.statusListenerClass";
    public static final String dML = "SYSOUT";
    final ahu_0 cFW;

    public aha_2(ahu_0 ahu_02) {
        this.cFW = ahu_02;
    }

    public void i(URL uRL) {
        if (uRL == null) {
            throw new IllegalArgumentException("URL argument cannot be null");
        }
        aip_1 aip_12 = new aip_1();
        aip_12.a(this.cFW);
        aip_12.b(uRL);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private URL a(ClassLoader classLoader, boolean bl2) {
        String string = dh_2.getSystemProperty(dMJ);
        if (string != null) {
            URL uRL = null;
            try {
                URL uRL2 = uRL = new URL(string);
                return uRL2;
            }
            catch (MalformedURLException malformedURLException) {
                uRL = agw_0.c(string, classLoader);
                if (uRL != null) {
                    URL uRL3 = uRL;
                    return uRL3;
                }
                File file = new File(string);
                if (file.exists() && file.isFile()) {
                    try {
                        URL uRL4 = uRL = file.toURI().toURL();
                        return uRL4;
                    }
                    catch (MalformedURLException malformedURLException2) {}
                }
            }
            finally {
                if (bl2) {
                    this.a(string, classLoader, uRL);
                }
            }
        }
        return null;
    }

    public URL fb(boolean bl2) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL uRL = this.a(classLoader, bl2);
        if (uRL != null) {
            return uRL;
        }
        uRL = agw_0.c(dMI, classLoader);
        if (bl2) {
            this.a(dMI, classLoader, uRL);
        }
        if (uRL != null) {
            return uRL;
        }
        uRL = agw_0.c(dMH, classLoader);
        if (bl2) {
            this.a(dMH, classLoader, uRL);
        }
        return uRL;
    }

    public void aUd() {
        adt_1.f(this.cFW);
        URL uRL = this.fb(true);
        if (uRL != null) {
            this.i(uRL);
        } else {
            eN.d(this.cFW);
        }
    }

    private void d(String string, ClassLoader classLoader) {
        List list = null;
        Ju ju = this.cFW.ea();
        try {
            list = agw_0.b(string, classLoader);
        }
        catch (IOException iOException) {
            ju.c(new aIX("Failed to get url list for resource [" + string + "]", this.cFW, iOException));
        }
        if (list != null && list.size() > 1) {
            ju.c(new apQ("Resource [" + string + "] occurs multiple times on the classpath.", this.cFW));
            for (URL uRL : list) {
                ju.c(new apQ("Resource [" + string + "] occurs at [" + uRL.toString() + "]", this.cFW));
            }
        }
    }

    private void a(String string, ClassLoader classLoader, URL uRL) {
        Ju ju = this.cFW.ea();
        if (uRL == null) {
            ju.c(new jP("Could NOT find resource [" + string + "]", this.cFW));
        } else {
            ju.c(new jP("Found resource [" + string + "] at [" + uRL.toString() + "]", this.cFW));
            this.d(string, classLoader);
        }
    }
}

