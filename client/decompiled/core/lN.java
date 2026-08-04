/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.helpers.DefaultHandler;

public class lN
extends DefaultHandler {
    private Stack HU = new Stack();
    private hd_0 HV = null;
    private KN HW;

    public lN(KN kN, hd_0 hd_02) {
        this.HV = hd_02;
        this.HU.push(this.HV);
        this.HW = kN;
    }

    public hd_0 qE() {
        return this.HV;
    }

    public InputSource resolveEntity(String string, String string2) {
        this.HW.TP().l("resolving systemId: " + string2, 3);
        if (string2.startsWith("file:")) {
            String string3 = amj_2.aBD().ec(string2);
            File file = new File(string3);
            if (!file.isAbsolute()) {
                file = amj_2.aBD().d(this.HW.Xb(), string3);
                this.HW.TP().l("Warning: '" + string2 + "' in " + this.HW.Xa() + " should be expressed simply as '" + string3.replace('\\', '/') + "' for compliance with other XML tools", 1);
            }
            this.HW.TP().l("file=" + file, 4);
            try {
                InputSource inputSource = new InputSource(new FileInputStream(file));
                inputSource.setSystemId(amj_2.aBD().eb(file.getAbsolutePath()));
                return inputSource;
            }
            catch (FileNotFoundException fileNotFoundException) {
                this.HW.TP().l(file.getAbsolutePath() + " could not be found", 1);
            }
        }
        this.HW.TP().l("could not resolve systemId", 4);
        return null;
    }

    public void startElement(String string, String string2, String string3, Attributes attributes) {
        hd_0 hd_02 = this.HV.a(string, string2, string3, attributes, this.HW);
        this.HU.push(this.HV);
        this.HV = hd_02;
        this.HV.b(string, string2, string3, attributes, this.HW);
    }

    public void setDocumentLocator(Locator locator) {
        this.HW.setLocator(locator);
    }

    public void endElement(String string, String string2, String string3) {
        hd_0 hd_02;
        this.HV.a(string, string2, this.HW);
        this.HV = hd_02 = (hd_0)this.HU.pop();
        if (this.HV != null) {
            this.HV.a(string, string2, string3, this.HW);
        }
    }

    public void characters(char[] cArray, int n2, int n3) {
        this.HV.a(cArray, n2, n3, this.HW);
    }

    public void startPrefixMapping(String string, String string2) {
        this.HW.startPrefixMapping(string, string2);
    }

    public void endPrefixMapping(String string) {
        this.HW.endPrefixMapping(string);
    }

    static KN a(lN lN2) {
        return lN2.HW;
    }
}

