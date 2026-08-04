/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * Renamed from Yx
 */
public class yx_1
extends DefaultHandler
implements aaa_1 {
    final agn_1 cbd;
    public List cbe = new ArrayList();
    Locator awt;
    zf_0 cbf = new zf_0();

    public yx_1() {
        this.cbd = new agn_1(this);
    }

    public final void m(InputStream inputStream) {
        this.b(new InputSource(inputStream));
    }

    public List b(InputSource inputSource) {
        SAXParser sAXParser = null;
        try {
            SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
            sAXParserFactory.setValidating(false);
            sAXParserFactory.setNamespaceAware(true);
            sAXParser = sAXParserFactory.newSAXParser();
        }
        catch (Exception exception) {
            String string = "Parser configuration error occured";
            this.e(string, exception);
            throw new azG(string, exception);
        }
        try {
            sAXParser.parse(inputSource, (DefaultHandler)this);
            return this.cbe;
        }
        catch (IOException iOException) {
            String string = "I/O error occurred while parsing xml file";
            this.e(string, iOException);
            throw new azG(string, iOException);
        }
        catch (Exception exception) {
            String string = "Problem parsing XML document. See previously reported errors. Abandoning all further processing.";
            this.e(string, exception);
            throw new azG(string, exception);
        }
    }

    public void startDocument() {
    }

    public Locator getLocator() {
        return this.awt;
    }

    public void setDocumentLocator(Locator locator) {
        this.awt = locator;
    }

    public void startElement(String string, String string2, String string3, Attributes attributes) {
        String string4 = this.w(string2, string3);
        this.cbf.push(string4);
        zf_0 zf_02 = (zf_0)this.cbf.clone();
        this.cbe.add(new auk_0(zf_02, string, string2, string3, attributes, this.getLocator()));
    }

    public void characters(char[] cArray, int n2, int n3) {
        Object object;
        String string = new String(cArray, n2, n3);
        if (string == null) {
            return;
        }
        if (string != null && ((String)(object = string.trim())).length() == 0) {
            return;
        }
        object = this.amG();
        if (object instanceof AJ) {
            AJ aJ = (AJ)object;
            aJ.append(string);
        } else {
            this.cbe.add(new AJ(string, this.getLocator()));
        }
    }

    xg_0 amG() {
        if (this.cbe.isEmpty()) {
            return null;
        }
        int n2 = this.cbe.size();
        return (xg_0)this.cbe.get(n2 - 1);
    }

    public void endElement(String string, String string2, String string3) {
        this.cbe.add(new bi_0(string, string2, string3, this.getLocator()));
        this.cbf.pop();
    }

    String w(String string, String string2) {
        String string3 = string;
        if (string3 == null || string3.length() < 1) {
            string3 = string2;
        }
        return string3;
    }

    public void error(SAXParseException sAXParseException) {
        this.e("Parsing error on line " + sAXParseException.getLineNumber() + " and column " + sAXParseException.getColumnNumber(), sAXParseException);
    }

    public void fatalError(SAXParseException sAXParseException) {
        this.e("Parsing fatal error on line " + sAXParseException.getLineNumber() + " and column " + sAXParseException.getColumnNumber(), sAXParseException);
    }

    public void warning(SAXParseException sAXParseException) {
        this.d("Parsing warning on line " + sAXParseException.getLineNumber() + " and column " + sAXParseException.getColumnNumber(), sAXParseException);
    }

    public void eg(String string) {
        this.cbd.eg(string);
    }

    public void e(String string, Throwable throwable) {
        this.cbd.e(string, throwable);
    }

    public void ee(String string) {
        this.cbd.ee(string);
    }

    public void c(String string, Throwable throwable) {
        this.cbd.c(string, throwable);
    }

    public void b(amb amb2) {
        this.cbd.b(amb2);
    }

    public void ef(String string) {
        this.cbd.ef(string);
    }

    public void d(String string, Throwable throwable) {
        this.cbd.d(string, throwable);
    }

    public vU QK() {
        return this.cbd.QK();
    }

    public void a(vU vU2) {
        this.cbd.a(vU2);
    }

    public List amH() {
        return this.cbe;
    }
}

