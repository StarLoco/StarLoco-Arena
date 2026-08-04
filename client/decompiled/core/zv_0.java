/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.xml.sax.AttributeList;
import org.xml.sax.DocumentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.XMLReaderAdapter;

/*
 * Renamed from zV
 */
public class zv_0
extends es_2 {
    private static final ga_2 xa = ga_2.Qo();
    private Parser aGG;
    private UI hL;
    private File K;
    private File aGH;
    private Locator awt;
    private id_2 aGI = new id_2();

    public zv_0() {
        this.aGI.setName("");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(UI uI, Object object) {
        if (!(object instanceof File)) {
            throw new eq_2("Only File source supported by default plugin");
        }
        File file = (File)object;
        FileInputStream fileInputStream = null;
        InputSource inputSource = null;
        this.hL = uI;
        this.K = new File(file.getAbsolutePath());
        this.aGH = new File(this.K.getParent());
        try {
            try {
                this.aGG = abj_1.getParser();
            }
            catch (eq_2 eq_22) {
                this.aGG = new XMLReaderAdapter(abj_1.getXMLReader());
            }
            String string = xa.eb(file.getAbsolutePath());
            fileInputStream = new FileInputStream(file);
            inputSource = new InputSource(fileInputStream);
            inputSource.setSystemId(string);
            uI.l("parsing buildfile " + file + " with URI = " + string, 3);
            amy_1 amy_12 = new amy_1(this);
            this.aGG.setDocumentHandler(amy_12);
            this.aGG.setEntityResolver(amy_12);
            this.aGG.setErrorHandler(amy_12);
            this.aGG.setDTDHandler(amy_12);
            this.aGG.parse(inputSource);
        }
        catch (SAXParseException sAXParseException) {
            try {
                axc_0 axc_02 = new axc_0(sAXParseException.getSystemId(), sAXParseException.getLineNumber(), sAXParseException.getColumnNumber());
                Exception exception = sAXParseException.getException();
                if (!(exception instanceof eq_2)) throw new eq_2(sAXParseException.getMessage(), exception, axc_02);
                eq_2 eq_23 = (eq_2)exception;
                if (eq_23.hW() != axc_0.diY) throw eq_23;
                eq_23.a(axc_02);
                throw eq_23;
                catch (SAXException sAXException) {
                    Exception exception2 = sAXException.getException();
                    if (!(exception2 instanceof eq_2)) throw new eq_2(sAXException.getMessage(), exception2);
                    throw (eq_2)exception2;
                }
                catch (FileNotFoundException fileNotFoundException) {
                    throw new eq_2(fileNotFoundException);
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    throw new eq_2("Encoding of project file is invalid.", unsupportedEncodingException);
                }
                catch (IOException iOException) {
                    throw new eq_2("Error reading project file: " + iOException.getMessage(), iOException);
                }
            }
            catch (Throwable throwable) {
                ga_2.h(fileInputStream);
                throw throwable;
            }
        }
        ga_2.h(fileInputStream);
    }

    private static void a(zv_0 zv_02, DocumentHandler documentHandler, id_2 id_22, String string, AttributeList attributeList) {
        if (string.equals("description")) {
            new aIy(zv_02, documentHandler);
        } else if (zv_02.hL.ahm().get(string) != null) {
            new ajv_0(zv_02, documentHandler, id_22).a(string, attributeList);
        } else {
            new qm_2(zv_02, documentHandler, id_22, null, id_22).a(string, attributeList);
        }
    }

    private void a(Object object, AttributeList attributeList) {
        String string = attributeList.getValue("id");
        if (string != null) {
            this.hL.o(string, object);
        }
    }

    static Parser a(zv_0 zv_02) {
        return zv_02.aGG;
    }

    static Locator b(zv_0 zv_02) {
        return zv_02.awt;
    }

    static UI c(zv_0 zv_02) {
        return zv_02.hL;
    }

    static ga_2 Ha() {
        return xa;
    }

    static File d(zv_0 zv_02) {
        return zv_02.aGH;
    }

    static File e(zv_0 zv_02) {
        return zv_02.K;
    }

    static Locator a(zv_0 zv_02, Locator locator) {
        zv_02.awt = locator;
        return zv_02.awt;
    }

    static id_2 f(zv_0 zv_02) {
        return zv_02.aGI;
    }

    static void b(zv_0 zv_02, DocumentHandler documentHandler, id_2 id_22, String string, AttributeList attributeList) {
        zv_0.a(zv_02, documentHandler, id_22, string, attributeList);
    }

    static void a(zv_0 zv_02, Object object, AttributeList attributeList) {
        zv_02.a(object, attributeList);
    }
}

