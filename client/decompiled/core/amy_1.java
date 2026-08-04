/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

/*
 * Renamed from aMy
 */
class amy_1
extends HandlerBase {
    zv_0 dXM;

    public amy_1(zv_0 zv_02) {
        this.dXM = zv_02;
    }

    public InputSource resolveEntity(String string, String string2) {
        zv_0.c(this.dXM).l("resolving systemId: " + string2, 3);
        if (string2.startsWith("file:")) {
            String string3 = zv_0.Ha().ec(string2);
            File file = new File(string3);
            if (!file.isAbsolute()) {
                file = zv_0.Ha().d(zv_0.d(this.dXM), string3);
                zv_0.c(this.dXM).l("Warning: '" + string2 + "' in " + zv_0.e(this.dXM) + " should be expressed simply as '" + string3.replace('\\', '/') + "' for compliance with other XML tools", 1);
            }
            try {
                InputSource inputSource = new InputSource(new FileInputStream(file));
                inputSource.setSystemId(zv_0.Ha().eb(file.getAbsolutePath()));
                return inputSource;
            }
            catch (FileNotFoundException fileNotFoundException) {
                zv_0.c(this.dXM).l(file.getAbsolutePath() + " could not be found", 1);
            }
        }
        return null;
    }

    public void startElement(String string, AttributeList attributeList) {
        if (!string.equals("project")) {
            throw new SAXParseException("Config file is not of expected XML type", zv_0.b(this.dXM));
        }
        new lt_1(this.dXM, this).a(string, attributeList);
    }

    public void setDocumentLocator(Locator locator) {
        zv_0.a(this.dXM, locator);
    }
}

