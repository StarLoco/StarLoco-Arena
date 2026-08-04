/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Gd
 */
public class gd_2 {
    protected static Logger a = Logger.getLogger(gd_2.class);
    private yp_1 baz;
    private Cs baA = null;
    private fp_0 baB = null;
    private RE baC = null;

    public void a(fp_0 fp_02) {
        this.baB = fp_02;
    }

    public void c(Cs cs) {
        this.baA = cs;
    }

    public void c(RE rE) {
        this.baC = rE;
    }

    public yp_1 Pl() {
        return this.baz;
    }

    protected void Pm() {
        if (this.baA != null && this.baB != null && this.baC != null) {
            this.baz = new yp_1();
            this.baz.b(this.baA);
            this.baz.b(this.baB);
            this.baz.b(this.baC);
            return;
        }
        a.error((Object)"Impossible de cr\u00e9er le ProxyClient : tous ces param\u00e8tres n'ont pas \u00e9t\u00e9 d\u00e9finis");
    }
}

