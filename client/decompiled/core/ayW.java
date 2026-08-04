/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.AttributeList;
import org.xml.sax.DocumentHandler;
import org.xml.sax.SAXParseException;

class ayW
extends amq_2 {
    private id_2 afp;

    public ayW(zv_0 zv_02, DocumentHandler documentHandler) {
        super(zv_02, documentHandler);
    }

    public void a(String string, AttributeList attributeList) {
        String string2 = null;
        String string3 = "";
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        for (int j = 0; j < attributeList.getLength(); ++j) {
            String string8 = attributeList.getName(j);
            String string9 = attributeList.getValue(j);
            if (string8.equals("name")) {
                string2 = string9;
                if (!string2.equals("")) continue;
                throw new eq_2("name attribute must not be empty", new axc_0(zv_0.b(this.dXM)));
            }
            if (string8.equals("depends")) {
                string3 = string9;
                continue;
            }
            if (string8.equals("if")) {
                string4 = string9;
                continue;
            }
            if (string8.equals("unless")) {
                string5 = string9;
                continue;
            }
            if (string8.equals("id")) {
                string6 = string9;
                continue;
            }
            if (string8.equals("description")) {
                string7 = string9;
                continue;
            }
            throw new SAXParseException("Unexpected attribute \"" + string8 + "\"", zv_0.b(this.dXM));
        }
        if (string2 == null) {
            throw new SAXParseException("target element appears without a name attribute", zv_0.b(this.dXM));
        }
        this.afp = new id_2();
        this.afp.eF("");
        this.afp.setName(string2);
        this.afp.w(string4);
        this.afp.x(string5);
        this.afp.setDescription(string7);
        zv_0.c(this.dXM).a(string2, this.afp);
        if (string6 != null && !string6.equals("")) {
            zv_0.c(this.dXM).o(string6, this.afp);
        }
        if (string3.length() > 0) {
            this.afp.eE(string3);
        }
    }

    public void startElement(String string, AttributeList attributeList) {
        zv_0.b(this.dXM, this, this.afp, string, attributeList);
    }
}

