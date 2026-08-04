/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import org.xml.sax.AttributeList;
import org.xml.sax.DocumentHandler;
import org.xml.sax.SAXParseException;

/*
 * Renamed from LT
 */
class lt_1
extends amq_2 {
    public lt_1(zv_0 zv_02, DocumentHandler documentHandler) {
        super(zv_02, documentHandler);
    }

    public void a(String string, AttributeList attributeList) {
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        for (int j = 0; j < attributeList.getLength(); ++j) {
            String string6 = attributeList.getName(j);
            String string7 = attributeList.getValue(j);
            if (string6.equals("default")) {
                string2 = string7;
                continue;
            }
            if (string6.equals("name")) {
                string3 = string7;
                continue;
            }
            if (string6.equals("id")) {
                string4 = string7;
                continue;
            }
            if (string6.equals("basedir")) {
                string5 = string7;
                continue;
            }
            throw new SAXParseException("Unexpected attribute \"" + attributeList.getName(j) + "\"", zv_0.b(this.dXM));
        }
        if (string2 == null || string2.equals("")) {
            throw new eq_2("The default attribute is required");
        }
        zv_0.c(this.dXM).gb(string2);
        if (string3 != null) {
            zv_0.c(this.dXM).setName(string3);
            zv_0.c(this.dXM).o(string3, zv_0.c(this.dXM));
        }
        if (string4 != null) {
            zv_0.c(this.dXM).o(string4, zv_0.c(this.dXM));
        }
        if (zv_0.c(this.dXM).getProperty("basedir") != null) {
            zv_0.c(this.dXM).ar(zv_0.c(this.dXM).getProperty("basedir"));
        } else if (string5 == null) {
            zv_0.c(this.dXM).ar(zv_0.d(this.dXM).getAbsolutePath());
        } else if (new File(string5).isAbsolute()) {
            zv_0.c(this.dXM).ar(string5);
        } else {
            File file = zv_0.Ha().d(zv_0.d(this.dXM), string5);
            zv_0.c(this.dXM).A(file);
        }
        zv_0.c(this.dXM).a("", zv_0.f(this.dXM));
    }

    public void startElement(String string, AttributeList attributeList) {
        if (string.equals("target")) {
            this.b(string, attributeList);
        } else {
            zv_0.b(this.dXM, this, zv_0.f(this.dXM), string, attributeList);
        }
    }

    private void b(String string, AttributeList attributeList) {
        new ayW(this.dXM, this).a(string, attributeList);
    }
}

