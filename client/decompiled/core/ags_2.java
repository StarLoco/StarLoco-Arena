/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;

/*
 * Renamed from ags
 */
public class ags_2
extends hd_0 {
    public void b(String string, String string2, String string3, Attributes attributes, KN kN) {
        Object object;
        String string4;
        String string5 = null;
        boolean bl2 = false;
        UI uI = kN.TP();
        kN.Xi().a(new axc_0(kN.getLocator()));
        for (int j = 0; j < attributes.getLength(); ++j) {
            string4 = attributes.getURI(j);
            if (string4 != null && !string4.equals("") && !string4.equals(string)) continue;
            object = attributes.getLocalName(j);
            String string6 = attributes.getValue(j);
            if (((String)object).equals("default")) {
                if (string6 == null || string6.equals("") || kN.Xk()) continue;
                uI.gc(string6);
                continue;
            }
            if (((String)object).equals("name")) {
                if (string6 == null) continue;
                kN.fb(string6);
                bl2 = true;
                if (kN.Xk()) continue;
                uI.setName(string6);
                uI.o(string6, uI);
                continue;
            }
            if (((String)object).equals("id")) {
                if (string6 == null || kN.Xk()) continue;
                uI.o(string6, uI);
                continue;
            }
            if (((String)object).equals("basedir")) {
                if (kN.Xk()) continue;
                string5 = string6;
                continue;
            }
            throw new SAXParseException("Unexpected attribute \"" + attributes.getQName(j) + "\"", kN.getLocator());
        }
        String string7 = "ant.file." + kN.Xc();
        string4 = uI.getProperty(string7);
        if (string4 != null && bl2) {
            object = new File(string4);
            if (kN.Xk() && !((File)object).equals(kN.Xa())) {
                uI.l("Duplicated project name in import. Project " + kN.Xc() + " defined first in " + string4 + " and again in " + kN.Xa(), 1);
            }
        }
        if (kN.Xa() != null && bl2) {
            uI.E("ant.file." + kN.Xc(), kN.Xa().toString());
        }
        if (kN.Xk()) {
            return;
        }
        if (uI.getProperty("basedir") != null) {
            uI.ar(uI.getProperty("basedir"));
        } else if (string5 == null) {
            uI.ar(kN.Xb().getAbsolutePath());
        } else if (new File(string5).isAbsolute()) {
            uI.ar(string5);
        } else {
            uI.A(amj_2.aBD().d(kN.Xb(), string5));
        }
        uI.a("", kN.Xi());
        kN.c(kN.Xi());
    }

    public hd_0 a(String string, String string2, String string3, Attributes attributes, KN kN) {
        return string2.equals("target") && (string.equals("") || string.equals("antlib:org.apache.tools.ant")) ? amj_2.aBF() : amj_2.aBG();
    }
}

