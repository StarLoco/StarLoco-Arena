/*
 * Decompiled with CFR 0.152.
 */
import java.util.Locale;
import org.xml.sax.AttributeList;
import org.xml.sax.DocumentHandler;
import org.xml.sax.SAXParseException;

/*
 * Renamed from Ww
 */
class ww_0
extends amq_2 {
    private Object parent;
    private Object bUe;
    private fy_2 afs;
    private fy_2 bUf = null;
    private id_2 afp;

    public ww_0(zv_0 zv_02, DocumentHandler documentHandler, Object object, fy_2 fy_22, id_2 id_22) {
        super(zv_02, documentHandler);
        this.parent = object instanceof akm ? ((akm)object).OV() : object;
        this.afs = fy_22;
        this.afp = id_22;
    }

    public void a(String string, AttributeList attributeList) {
        Class<?> clazz = this.parent.getClass();
        hm_2 hm_22 = hm_2.a(zv_0.c(this.dXM), clazz);
        try {
            String string2 = string.toLowerCase(Locale.US);
            if (this.parent instanceof rs_0) {
                rs_0 rs_02 = new rs_0(string2);
                rs_02.l(zv_0.c(this.dXM));
                ((rs_0)this.parent).a(rs_02);
                this.bUe = rs_02;
            } else {
                this.bUe = hm_22.d(zv_0.c(this.dXM), this.parent, string2);
            }
            zv_0.a(this.dXM, this.bUe, attributeList);
            this.bUf = new fy_2(this.bUe, string);
            this.bUf.a(attributeList);
            this.afs.b(this.bUf);
        }
        catch (eq_2 eq_22) {
            throw new SAXParseException(eq_22.getMessage(), zv_0.b(this.dXM), eq_22);
        }
    }

    public void characters(char[] cArray, int n2, int n3) {
        this.bUf.b(cArray, n2, n3);
    }

    public void startElement(String string, AttributeList attributeList) {
        if (this.bUe instanceof cf_1) {
            new qm_2(this.dXM, this, (cf_1)this.bUe, this.bUf, this.afp).a(string, attributeList);
        } else {
            new ww_0(this.dXM, this, this.bUe, this.bUf, this.afp).a(string, attributeList);
        }
    }
}

