/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.AttributeList;
import org.xml.sax.DocumentHandler;

/*
 * Renamed from qM
 */
class qm_2
extends amq_2 {
    private id_2 afp;
    private cf_1 afq;
    private dm_1 afr;
    private fy_2 afs;
    private fy_2 aft = null;

    public qm_2(zv_0 zv_02, DocumentHandler documentHandler, cf_1 cf_12, fy_2 fy_22, id_2 id_22) {
        super(zv_02, documentHandler);
        this.afq = cf_12;
        this.afs = fy_22;
        this.afp = id_22;
    }

    public void a(String string, AttributeList attributeList) {
        try {
            this.afr = zv_0.c(this.dXM).gd(string);
        }
        catch (eq_2 eq_22) {
            // empty catch block
        }
        if (this.afr == null) {
            this.afr = new rs_0(string);
            this.afr.l(zv_0.c(this.dXM));
            this.afr.cW(string);
        }
        this.afr.a(new axc_0(zv_0.b(this.dXM)));
        zv_0.a(this.dXM, this.afr, attributeList);
        this.afr.a(this.afp);
        this.afq.a(this.afr);
        this.afr.init();
        this.aft = this.afr.LG();
        this.aft.a(attributeList);
        if (this.afs != null) {
            this.afs.b(this.aft);
        }
    }

    public void characters(char[] cArray, int n2, int n3) {
        this.aft.b(cArray, n2, n3);
    }

    public void startElement(String string, AttributeList attributeList) {
        if (this.afr instanceof cf_1) {
            new qm_2(this.dXM, this, (cf_1)((Object)this.afr), this.aft, this.afp).a(string, attributeList);
        } else {
            new ww_0(this.dXM, this, this.afr, this.aft, this.afp).a(string, attributeList);
        }
    }
}

