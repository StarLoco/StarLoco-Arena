/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/*
 * Renamed from amj
 */
public class amj_2
extends es_2 {
    public static final String cGy = "ant.targets";
    private static hd_0 cGz = new pz_0();
    private static hd_0 cGA = new aOH();
    private static hd_0 cGB = new ca_2();
    private static hd_0 cGC = new ags_2();
    private static final String cGD = "ant.parsing.context";
    private static final ga_2 xa = ga_2.Qo();

    public rs_0 a(UI uI, URL uRL) {
        id_2 id_22 = new id_2();
        id_22.l(uI);
        KN kN = new KN(uI);
        kN.b(id_22);
        kN.d(id_22);
        this.a(kN.TP(), uRL, new lN(kN, cGz));
        dm_1[] dm_1Array = id_22.TQ();
        if (dm_1Array.length != 1) {
            throw new eq_2("No tasks defined");
        }
        return (rs_0)dm_1Array[0];
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(UI uI, Object object) {
        this.OD().addElement(object);
        KN kN = null;
        kN = (KN)uI.gi(cGD);
        if (kN == null) {
            kN = new KN(uI);
            uI.o(cGD, kN);
            uI.o(cGy, kN.Xj());
        }
        if (this.OD().size() > 1) {
            kN.bN(true);
            id_2 id_22 = kN.Xh();
            id_2 id_23 = kN.Xi();
            Map map = kN.Xl();
            try {
                id_2 id_24 = new id_2();
                id_24.l(uI);
                id_24.setName("");
                kN.c(id_24);
                kN.h(new HashMap());
                kN.d(id_24);
                this.a(uI, object, new lN(kN, cGB));
                id_24.execute();
            }
            finally {
                kN.c(id_22);
                kN.d(id_23);
                kN.h(map);
            }
        } else {
            kN.h(new HashMap());
            this.a(uI, object, new lN(kN, cGB));
            kN.Xi().execute();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(UI uI, Object object, lN lN2) {
        KN kN = lN.a(lN2);
        File file = null;
        URL uRL = null;
        String string = null;
        if (object instanceof File) {
            file = (File)object;
            file = xa.dZ(file.getAbsolutePath());
            kN.w(file);
            string = file.toString();
        } else {
            if (!(object instanceof URL)) throw new eq_2("Source " + object.getClass().getName() + " not supported by this plugin");
            uRL = (URL)object;
            string = uRL.toString();
        }
        InputStream inputStream = null;
        InputSource inputSource = null;
        try {
            XMLReader xMLReader = abj_1.aNh();
            String string2 = null;
            if (file != null) {
                string2 = xa.eb(file.getAbsolutePath());
                inputStream = new FileInputStream(file);
            } else {
                inputStream = uRL.openStream();
                string2 = uRL.toString();
            }
            inputSource = new InputSource(inputStream);
            if (string2 != null) {
                inputSource.setSystemId(string2);
            }
            uI.l("parsing buildfile " + string + " with URI = " + string2, 3);
            lN lN3 = lN2;
            xMLReader.setContentHandler(lN3);
            xMLReader.setEntityResolver(lN3);
            xMLReader.setErrorHandler(lN3);
            xMLReader.setDTDHandler(lN3);
            xMLReader.parse(inputSource);
        }
        catch (SAXParseException sAXParseException) {
            try {
                Exception exception;
                axc_0 axc_02 = new axc_0(sAXParseException.getSystemId(), sAXParseException.getLineNumber(), sAXParseException.getColumnNumber());
                Exception exception2 = sAXParseException.getException();
                if (exception2 instanceof eq_2) {
                    eq_2 eq_22 = (eq_2)exception2;
                    if (eq_22.hW() != axc_0.diY) throw eq_22;
                    eq_22.a(axc_02);
                    throw eq_22;
                }
                String string3 = sAXParseException.getMessage();
                if (exception2 == null) {
                    exception = sAXParseException;
                    throw new eq_2(string3, exception, axc_02);
                }
                exception = exception2;
                throw new eq_2(string3, exception, axc_02);
                catch (SAXException sAXException) {
                    Exception exception3;
                    Exception exception4 = sAXException.getException();
                    if (exception4 instanceof eq_2) {
                        throw (eq_2)exception4;
                    }
                    String string4 = sAXException.getMessage();
                    if (exception4 == null) {
                        exception3 = sAXException;
                        throw new eq_2(string4, exception3);
                    }
                    exception3 = exception4;
                    throw new eq_2(string4, exception3);
                }
                catch (FileNotFoundException fileNotFoundException) {
                    throw new eq_2(fileNotFoundException);
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    throw new eq_2("Encoding of project file " + string + " is invalid.", unsupportedEncodingException);
                }
                catch (IOException iOException) {
                    throw new eq_2("Error reading project file " + string + ": " + iOException.getMessage(), iOException);
                }
            }
            catch (Throwable throwable) {
                ga_2.h(inputStream);
                throw throwable;
            }
        }
        ga_2.h(inputStream);
    }

    protected static hd_0 aBz() {
        return cGB;
    }

    protected static void a(hd_0 hd_02) {
        cGB = hd_02;
    }

    protected static hd_0 aBA() {
        return cGC;
    }

    protected static void b(hd_0 hd_02) {
        cGC = hd_02;
    }

    protected static hd_0 aBB() {
        return cGA;
    }

    protected static void c(hd_0 hd_02) {
        cGA = hd_02;
    }

    protected static hd_0 aBC() {
        return cGz;
    }

    protected static void d(hd_0 hd_02) {
        cGz = hd_02;
    }

    static ga_2 aBD() {
        return xa;
    }

    static hd_0 aBE() {
        return cGC;
    }

    static hd_0 aBF() {
        return cGA;
    }

    static hd_0 aBG() {
        return cGz;
    }
}

