/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/*
 * Renamed from aBj
 */
public class abj_1 {
    private static final ga_2 xa = ga_2.Qo();
    private static SAXParserFactory parserFactory = null;
    private static SAXParserFactory drc = null;
    private static DocumentBuilderFactory drd = null;

    public static synchronized SAXParserFactory getParserFactory() {
        if (parserFactory == null) {
            parserFactory = abj_1.aNg();
        }
        return parserFactory;
    }

    public static synchronized SAXParserFactory aNf() {
        if (drc == null) {
            drc = abj_1.aNg();
            drc.setNamespaceAware(true);
        }
        return drc;
    }

    public static SAXParserFactory aNg() {
        try {
            return SAXParserFactory.newInstance();
        }
        catch (FactoryConfigurationError factoryConfigurationError) {
            throw new eq_2("XML parser factory has not been configured correctly: " + factoryConfigurationError.getMessage(), factoryConfigurationError);
        }
    }

    public static Parser getParser() {
        try {
            return abj_1.a(abj_1.getParserFactory()).getParser();
        }
        catch (SAXException sAXException) {
            throw abj_1.a(sAXException);
        }
    }

    public static XMLReader getXMLReader() {
        try {
            return abj_1.a(abj_1.getParserFactory()).getXMLReader();
        }
        catch (SAXException sAXException) {
            throw abj_1.a(sAXException);
        }
    }

    public static XMLReader aNh() {
        try {
            return abj_1.a(abj_1.aNf()).getXMLReader();
        }
        catch (SAXException sAXException) {
            throw abj_1.a(sAXException);
        }
    }

    public static String H(File file) {
        return xa.eb(file.getAbsolutePath());
    }

    public static DocumentBuilder aNi() {
        try {
            return abj_1.aNj().newDocumentBuilder();
        }
        catch (ParserConfigurationException parserConfigurationException) {
            throw new eq_2(parserConfigurationException);
        }
    }

    private static SAXParser a(SAXParserFactory sAXParserFactory) {
        try {
            return sAXParserFactory.newSAXParser();
        }
        catch (ParserConfigurationException parserConfigurationException) {
            throw new eq_2("Cannot create parser for the given configuration: " + parserConfigurationException.getMessage(), parserConfigurationException);
        }
        catch (SAXException sAXException) {
            throw abj_1.a(sAXException);
        }
    }

    private static eq_2 a(SAXException sAXException) {
        Exception exception = sAXException.getException();
        if (exception != null) {
            return new eq_2(exception);
        }
        return new eq_2(sAXException);
    }

    private static synchronized DocumentBuilderFactory aNj() {
        if (drd == null) {
            try {
                drd = DocumentBuilderFactory.newInstance();
            }
            catch (FactoryConfigurationError factoryConfigurationError) {
                throw new eq_2("Document builder factory has not been configured correctly: " + factoryConfigurationError.getMessage(), factoryConfigurationError);
            }
        }
        return drd;
    }
}

