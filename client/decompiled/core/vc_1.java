/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from VC
 */
public class vc_1
extends abd_1 {
    private kn_1 aeH = null;
    private Object It = null;
    private Object dE = null;
    private kn_1 bSS = null;
    private Object bST = null;
    private static final acl_0 uG = new ym_0(new ahw_0());

    public vc_1() {
    }

    public vc_1(kn_1 kn_12, kn_1 kn_13, Object object) {
        this.a(kn_12);
        this.d(kn_13);
        this.DK = kn_12;
        this.dE = object;
    }

    public static vc_1 b(abd_1 abd_12, kn_1 kn_12, kn_1 kn_13, Object object) {
        vc_1 vc_12;
        try {
            vc_12 = (vc_1)uG.adr();
            vc_12.DG = uG;
        }
        catch (Exception exception) {
            vc_12 = new vc_1();
            vc_12.b();
        }
        vc_12.ng(abd_12.bTl);
        vc_12.nh(abd_12.bTm);
        vc_12.setModifiers(abd_12.jH);
        vc_12.ai(abd_12.oI);
        vc_12.aj(abd_12.oJ);
        vc_12.e(kn_12);
        vc_12.a(qe_1.bFd);
        vc_12.a(kn_12);
        vc_12.d(kn_13);
        vc_12.dE = object;
        return vc_12;
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

    public kn_1 aiG() {
        return this.bSS;
    }

    public void d(kn_1 kn_12) {
        qa_1 qa_12;
        if (kn_12 instanceof na_1) {
            this.bSS = kn_12;
        }
        if (kn_12 != null && (qa_12 = kn_12.getRenderableParent()) != null) {
            this.bST = qa_12.getItemValue();
        }
    }

    public Object qJ() {
        return this.It;
    }

    public Object aiH() {
        return this.bST;
    }

    public Object getValue() {
        return this.dE;
    }

    public qe_1 aV() {
        return qe_1.bFd;
    }
}

