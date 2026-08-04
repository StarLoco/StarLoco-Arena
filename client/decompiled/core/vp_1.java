/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from VP
 */
class vp_1
implements alx_0 {
    int bTl;
    int bTm;
    int jH;
    int oI;
    int oJ;
    final /* synthetic */ aqq_0 aLv;

    public vp_1(aqq_0 aqq_02, int n2, int n3, int n4, int n5, int n6) {
        this.aLv = aqq_02;
        this.bTl = n2;
        this.bTm = n3;
        this.jH = n4;
        this.oI = n5;
        this.oJ = n6;
    }

    public boolean a(pr_0 pr_02) {
        this.aLv.getAppearance().aCl();
        abd_1 abd_12 = abd_1.aNc();
        abd_12.ng(this.bTl);
        abd_12.nh(this.bTm);
        abd_12.setModifiers(this.jH);
        abd_12.ai(this.oI);
        abd_12.aj(this.oJ);
        abd_12.e(this.aLv);
        abd_12.a(qe_1.bFB);
        this.aLv.f(abd_12);
        return false;
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }
}

