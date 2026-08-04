/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.AttributeList;
import org.xml.sax.DocumentHandler;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXParseException;

/*
 * Renamed from aMq
 */
class amq_2
extends HandlerBase {
    protected DocumentHandler dXL;
    zv_0 dXM;

    public amq_2(zv_0 zv_02, DocumentHandler documentHandler) {
        this.dXL = documentHandler;
        this.dXM = zv_02;
        zv_0.a(zv_02).setDocumentHandler(this);
    }

    public void startElement(String string, AttributeList attributeList) {
        throw new SAXParseException("Unexpected element \"" + string + "\"", zv_0.b(this.dXM));
    }

    public void characters(char[] cArray, int n2, int n3) {
        String string = new String(cArray, n2, n3).trim();
        if (string.length() > 0) {
            throw new SAXParseException("Unexpected text \"" + string + "\"", zv_0.b(this.dXM));
        }
    }

    public void endElement(String string) {
        zv_0.a(this.dXM).setDocumentHandler(this.dXL);
    }
}

