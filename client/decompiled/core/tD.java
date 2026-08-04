/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;
import java.util.Enumeration;
import java.util.Vector;

public class tD
extends and_1
implements gx_2 {
    private Vector ano = new Vector();
    private abe_0 anp = null;
    private String anq = null;
    private String DV = null;
    private int anr = 0;

    public tD() {
    }

    public tD(Reader reader) {
        super(reader);
    }

    public int read() {
        if (this.anp == null) {
            this.anp = new ia_0();
        }
        while (this.DV == null || this.DV.length() == 0) {
            this.DV = this.anp.a(this.in);
            if (this.DV == null) {
                return -1;
            }
            Enumeration enumeration = this.ano.elements();
            while (enumeration.hasMoreElements()) {
                aDa aDa2 = (aDa)enumeration.nextElement();
                this.DV = aDa2.dV(this.DV);
                if (this.DV != null) continue;
                break;
            }
            this.anr = 0;
            if (this.DV == null || this.anp.aR().length() == 0) continue;
            if (this.anq != null) {
                this.DV = this.DV + this.anq;
                continue;
            }
            this.DV = this.DV + this.anp.aR();
        }
        char c = this.DV.charAt(this.anr);
        ++this.anr;
        if (this.anr == this.DV.length()) {
            this.DV = null;
        }
        return c;
    }

    public final Reader b(Reader reader) {
        tD tD2 = new tD(reader);
        tD2.ano = this.ano;
        tD2.anp = this.anp;
        tD2.anq = this.anq;
        tD2.l(this.TP());
        return tD2;
    }

    public void cr(String string) {
        this.anq = tD.cs(string);
    }

    public void a(ia_0 ia_02) {
        this.a((abe_0)ia_02);
    }

    public void a(aOa aOa2) {
        this.a((abe_0)aOa2);
    }

    public void a(anl_0 anl_02) {
        this.a((abe_0)anl_02);
    }

    public void a(abe_0 abe_02) {
        if (this.anp != null) {
            throw new eq_2("Only one tokenizer allowed");
        }
        this.anp = abe_02;
    }

    public void a(aax_1 aax_12) {
        this.ano.addElement(aax_12);
    }

    public void a(aty_0 aty_02) {
        this.ano.addElement(aty_02);
    }

    public void a(arM arM2) {
        this.ano.addElement(arM2);
    }

    public void a(ie_0 ie_02) {
        this.ano.addElement(ie_02);
    }

    public void a(acr_1 acr_12) {
        this.ano.addElement(acr_12);
    }

    public void a(agh_0 agh_02) {
        this.ano.addElement(agh_02);
    }

    public void a(gi_1 gi_12) {
        this.ano.addElement(gi_12);
    }

    public void a(aDa aDa2) {
        this.ano.addElement(aDa2);
    }

    public static String cs(String string) {
        return ayM.cs(string);
    }

    public static int ct(String string) {
        if (string == null) {
            return 0;
        }
        int n2 = 0;
        if (string.indexOf(103) != -1) {
            n2 |= 0x10;
        }
        if (string.indexOf(105) != -1) {
            n2 |= 0x100;
        }
        if (string.indexOf(109) != -1) {
            n2 |= 0x1000;
        }
        if (string.indexOf(115) != -1) {
            n2 |= 0x10000;
        }
        return n2;
    }
}

