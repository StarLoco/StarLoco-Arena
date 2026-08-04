/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.AttributeList;
import org.xml.sax.DocumentHandler;
import org.xml.sax.SAXParseException;

/*
 * Renamed from aJV
 */
class ajv_0
extends amq_2 {
    private id_2 afp;
    private Object dTb;
    private fy_2 aft = null;

    public ajv_0(zv_0 zv_02, DocumentHandler documentHandler, id_2 id_22) {
        super(zv_02, documentHandler);
        this.afp = id_22;
    }

    public void a(String string, AttributeList attributeList) {
        try {
            this.dTb = zv_0.c(this.dXM).ge(string);
            if (this.dTb == null) {
                throw new eq_2("Unknown data type " + string);
            }
            this.aft = new fy_2(this.dTb, string);
            this.aft.a(attributeList);
            this.afp.d(this.aft);
        }
        catch (eq_2 eq_22) {
            throw new SAXParseException(eq_22.getMessage(), zv_0.b(this.dXM), eq_22);
        }
    }

    public void characters(char[] cArray, int n2, int n3) {
        this.aft.b(cArray, n2, n3);
    }

    public void startElement(String string, AttributeList attributeList) {
        new ww_0(this.dXM, this, this.dTb, this.aft, this.afp).a(string, attributeList);
    }
}

