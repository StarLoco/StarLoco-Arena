/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;

/*
 * Renamed from Hd
 */
public class hd_0 {
    public void b(String string, String string2, String string3, Attributes attributes, KN kN) {
    }

    public hd_0 a(String string, String string2, String string3, Attributes attributes, KN kN) {
        throw new SAXParseException("Unexpected element \"" + string3 + " \"", kN.getLocator());
    }

    public void a(String string, String string2, String string3, KN kN) {
    }

    public void a(String string, String string2, KN kN) {
    }

    public void a(char[] cArray, int n2, int n3, KN kN) {
        String string = new String(cArray, n2, n3).trim();
        if (string.length() > 0) {
            throw new SAXParseException("Unexpected text \"" + string + "\"", kN.getLocator());
        }
    }

    protected void eo(String string) {
    }
}

