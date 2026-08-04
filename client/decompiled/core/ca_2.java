/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;

/*
 * Renamed from ca
 */
public class ca_2
extends hd_0 {
    public hd_0 a(String string, String string2, String string3, Attributes attributes, KN kN) {
        if (string2.equals("project") && (string.equals("") || string.equals("antlib:org.apache.tools.ant"))) {
            return amj_2.aBE();
        }
        if (string2.equals(string3)) {
            throw new SAXParseException("Unexpected element \"{" + string + "}" + string2 + "\" {" + "antlib:org.apache.tools.ant" + "}" + string2, kN.getLocator());
        }
        throw new SAXParseException("Unexpected element \"" + string3 + "\" " + string2, kN.getLocator());
    }
}

