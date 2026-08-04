/*
 * Decompiled with CFR 0.152.
 */
public class qw
extends abd_1 {
    private kn_1 aeH = null;
    private Object It = null;
    private Object dE = null;
    private kn_1 aeI = null;
    private Object aeJ = null;
    private static final acl_0 uG = new ym_0(new ap_0());

    public qw() {
    }

    public qw(kn_1 kn_12, kn_1 kn_13, Object object) {
        this.a(kn_12);
        this.b(kn_13);
        this.DK = kn_12;
        this.dE = object;
    }

    public static qw a(abd_1 abd_12, kn_1 kn_12, kn_1 kn_13, Object object) {
        qw qw2;
        try {
            qw2 = (qw)uG.adr();
            qw2.DG = uG;
        }
        catch (Exception exception) {
            qw2 = new qw();
            qw2.b();
        }
        qw2.ng(abd_12.bTl);
        qw2.nh(abd_12.bTm);
        qw2.setModifiers(abd_12.jH);
        qw2.ai(abd_12.oI);
        qw2.aj(abd_12.oJ);
        qw2.e(kn_12);
        qw2.a(qe_1.bFe);
        qw2.a(kn_12);
        qw2.b(kn_13);
        qw2.dE = object;
        return qw2;
    }

    public kn_1 vE() {
        return this.aeH;
    }

    public void a(kn_1 kn_12) {
        qa_1 qa_12;
        if (kn_12 instanceof na_1) {
            this.aeH = kn_12;
        }
        if (kn_12 != null && (qa_12 = kn_12.getRenderableParent()) != null) {
            this.It = qa_12.getItemValue();
        }
    }

    public kn_1 vF() {
        return this.aeI;
    }

    public void b(kn_1 kn_12) {
        qa_1 qa_12;
        if (kn_12 instanceof na_1) {
            this.aeI = kn_12;
        }
        if (kn_12 != null && (qa_12 = kn_12.getRenderableParent()) != null) {
            this.aeJ = qa_12.getItemValue();
        }
    }

    public Object qJ() {
        return this.It;
    }

    public Object vG() {
        return this.aeJ;
    }

    public qe_1 aV() {
        return qe_1.bFe;
    }
}

