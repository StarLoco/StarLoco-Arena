/*
 * Decompiled with CFR 0.152.
 */
public class aiU
extends abd_1 {
    private kn_1 czv;
    private kn_1 aeH;
    private Object It;
    private Object czw;
    private Object dE;
    private static final acl_0 uG = new ym_0(new lu());

    public aiU() {
    }

    public aiU(kn_1 kn_12, kn_1 kn_13, Object object) {
        this.e(kn_13);
        this.f(kn_12);
        this.DK = kn_12;
        this.dE = object;
    }

    public static aiU c(abd_1 abd_12, kn_1 kn_12, kn_1 kn_13, Object object) {
        aiU aiU2;
        try {
            aiU2 = (aiU)uG.adr();
            aiU2.DG = uG;
        }
        catch (Exception exception) {
            aiU2 = new aiU();
            aiU2.b();
        }
        aiU2.ng(abd_12.bTl);
        aiU2.nh(abd_12.bTm);
        aiU2.setModifiers(abd_12.jH);
        aiU2.ai(abd_12.oI);
        aiU2.aj(abd_12.oJ);
        aiU2.e((na_1)kn_12);
        aiU2.a(qe_1.bFf);
        aiU2.e(kn_13);
        aiU2.f(kn_12);
        aiU2.dE = object;
        return aiU2;
    }

    public kn_1 ayA() {
        return this.aeH;
    }

    public void e(kn_1 kn_12) {
        qa_1 qa_12;
        if (kn_12 instanceof na_1) {
            this.aeH = kn_12;
        }
        if (kn_12 != null && (qa_12 = kn_12.getRenderableParent()) != null) {
            this.It = qa_12.getItemValue();
        }
    }

    public kn_1 ayB() {
        return this.czv;
    }

    public void f(kn_1 kn_12) {
        qa_1 qa_12;
        if (kn_12 instanceof na_1) {
            this.czv = kn_12;
        }
        if (kn_12 != null && (qa_12 = kn_12.getRenderableParent()) != null) {
            this.czw = qa_12.getItemValue();
        }
    }

    public Object ayC() {
        return this.czw;
    }

    public void aB(Object object) {
        this.czw = object;
    }

    public Object qJ() {
        return this.It;
    }

    public void x(Object object) {
        this.It = object;
    }

    public Object getValue() {
        return this.dE;
    }

    public void setValue(Object object) {
        this.dE = object;
    }

    public qe_1 aV() {
        return qe_1.bFf;
    }
}

