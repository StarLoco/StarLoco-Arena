/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public abstract class ZT
extends xb_2 {
    private int cem;
    private int cen;
    private boolean ceo = false;
    protected akv_0 bKL;
    public aea_0 cep = new jf_2(this, 12);

    protected ZT() {
        this.bWL = new jj_1(this, 8);
        this.nw = new ji_2(this, 8);
    }

    private static String k(ry ry2) {
        return ry2 == null ? "null" : "(" + ry2.getX() + ", " + ry2.getY() + ", " + ry2.wk() + ")";
    }

    public String toString() {
        return "(" + this.aW + ", " + this.bWl + ", " + this.bWm + " / " + ZT.k(this.bWn) + ")";
    }

    protected void a(EnumSet enumSet) {
        this.bWs = enumSet;
    }

    private void a(ArrayList arrayList, kc_2 kc_22) {
        if (arrayList.isEmpty()) {
            arrayList.add(kc_22);
        } else {
            int n2 = arrayList.size();
            if (this.bWl == kc_22) {
                arrayList.add(n2, kc_22);
            } else {
                arrayList.add(n2 - 1, kc_22);
            }
        }
    }

    public List a(xj_0 xj_02, aOf aOf2, ea_0 ea_02, int n2, int n3, short s) {
        se_2 se_22 = new se_2();
        ArrayList arrayList = new ArrayList();
        if (vv_0.aiq() != null && ea_02 != null) {
            for (agf_2 agf_22 : xj_02.alM().fm()) {
                ArrayList arrayList2 = new ArrayList();
                for (kc_2 kc_22 : vv_0.aiq().a(aOf2, ea_02.gT(), agf_22, n2, n3, s, xj_02.alI())) {
                    if (kc_22 instanceof gn_0 && (this.mi() == null || this.mi().iP() == 3 || this.mi().iP() == 2 || this.mi().iP() == 13 || this.mi().iP() == 12)) {
                        boolean bl2;
                        gn_0 gn_02 = (gn_0)kc_22;
                        if (gn_02.Qa() || !gn_02.rD()) {
                            if (!se_22.add(gn_02.getId())) continue;
                            this.a(arrayList2, gn_02);
                            continue;
                        }
                        if (!gn_02.rD() || agf_22.fj() == zg_1.cdv || (bl2 = this.mi() != null && this.mi().iP() == 3 && ame_1.aWP().eO(((ack_1)this.mi()).aqM()) != null) || !se_22.add(gn_02.getId())) continue;
                        this.a(arrayList2, gn_02);
                        continue;
                    }
                    if (!se_22.add(kc_22.getId())) continue;
                    this.a(arrayList2, kc_22);
                }
                arrayList.add(arrayList2);
            }
        }
        return arrayList;
    }

    public void b() {
        super.b();
        this.cem = ry_0.adZ();
        this.cen = -1;
        this.r = 0;
        this.ceo = false;
        this.bKL = akv_0.aVB();
    }

    public void j() {
        super.j();
        this.r = 0;
        this.bKL = null;
        this.bWA = null;
    }

    public void b(xb_2 xb_22, boolean bl2) {
        this.ceo = true;
        if (this.bdv != null && this.bdv.gS() != null && !bl2) {
            this.bdv.gS().l(this);
        }
        this.cen = xb_22 != null ? ((ZT)xb_22).aoz() : -1;
        if (this.bdv != null && this.bdv.gS() != null && bl2) {
            this.bdv.gS().m(this);
        }
    }

    public boolean aku() {
        return true;
    }

    protected void aoy() {
        this.ceo = true;
    }

    protected void a(xb_2 xb_22, boolean bl2) {
        if (!this.ceo) {
            this.b(xb_22, bl2);
        }
        this.ceo = false;
        super.a(xb_22, bl2);
    }

    public void akA() {
    }

    public void akB() {
        this.jt(0);
    }

    public void jt(int n2) {
        cn_0 cn_02;
        if (this.bWj != null && (cn_02 = this.bdv.gU()) != null) {
            int[] nArray = ((xj_0)this.bWj).aln();
            if (nArray == null || nArray.length != 2) {
                a.error((Object)("On veut pousser dans la timeline le arenaRunningEffect " + this + " de fa\u00e7on instantan\u00e9e : EffectId = " + ((xj_0)this.bWj).ST() + ", ActionId = " + ((xj_0)this.bWj).M() + "."));
            } else if (this.mi() != null && this.mi().iP() == 14) {
                asj_0 asj_02 = new asj_0(this, this.ajR().getId());
                ((jm_0)cn_02).a(asj_02);
                this.bKL = null;
            } else {
                int n3 = nArray[0] - n2;
                arm_0 arm_02 = arm_0.lQ(n3).dS(0 < nArray[1]);
                this.bKL = cn_02.a(new asj_0(this, cn_02.JG()), arm_02.bS((short)(n3 + 1)));
                if (this.bKL.aVC() == 0) {
                    a.info((Object)this.bKL, (Throwable)new Exception());
                }
            }
        }
    }

    public nc_2 akC() {
        return this.bWA;
    }

    public akv_0 aex() {
        return this.bKL;
    }

    public void b(akv_0 akv_02) {
        this.bKL = akv_02;
    }

    public boolean akF() {
        if (this.bWE) {
            return false;
        }
        return super.akF() || this.bWj != null && ((xj_0)this.bWj).aln() != null && ((xj_0)this.bWj).aln().length == 2 && (((xj_0)this.bWj).aln()[0] > 0 || ((xj_0)this.bWj).aln()[1] > 0);
    }

    public boolean isInfinite() {
        return this.bWj != null && ((xj_0)this.bWj).aln() != null && ((xj_0)this.bWj).aln().length == 2 && (((xj_0)this.bWj).aln()[0] == 63 || ((xj_0)this.bWj).aln()[1] == 63);
    }

    public boolean akD() {
        return this.bWj != null && ((xj_0)this.bWj).alp() != null && ((xj_0)this.bWj).alp().length == 2 && (((xj_0)this.bWj).alp()[0] > 0 || ((xj_0)this.bWj).alp()[1] > 0);
    }

    public int aoz() {
        return this.cem;
    }

    public int aoA() {
        return this.cen;
    }

    public xX ff() {
        return new jh_2(this, new gp_0[0]);
    }

    public XV akO() {
        return als_2.aWK();
    }

    public aea_0 akM() {
        return this.cep;
    }

    public fv_1 aL() {
        return null;
    }

    public Lr aM() {
        return null;
    }

    public boolean gM() {
        return true;
    }

    static /* synthetic */ kc_2 a(ZT zT) {
        return zT.bWl;
    }

    static /* synthetic */ kc_2 b(ZT zT) {
        return zT.bWl;
    }

    static /* synthetic */ kc_2 a(ZT zT, kc_2 kc_22) {
        zT.bWl = kc_22;
        return zT.bWl;
    }

    static /* synthetic */ ea_0 c(ZT zT) {
        return zT.bdv;
    }

    static /* synthetic */ ea_0 d(ZT zT) {
        return zT.bdv;
    }

    static /* synthetic */ ea_0 e(ZT zT) {
        return zT.bdv;
    }

    static /* synthetic */ int f(ZT zT) {
        return zT.aW;
    }

    static /* synthetic */ kc_2 g(ZT zT) {
        return zT.bWm;
    }

    static /* synthetic */ kc_2 h(ZT zT) {
        return zT.bWm;
    }

    static /* synthetic */ kc_2 b(ZT zT, kc_2 kc_22) {
        zT.bWm = kc_22;
        return zT.bWm;
    }

    static /* synthetic */ ea_0 i(ZT zT) {
        return zT.bdv;
    }

    static /* synthetic */ ea_0 j(ZT zT) {
        return zT.bdv;
    }

    static /* synthetic */ kc_2 c(ZT zT, kc_2 kc_22) {
        zT.bWm = kc_22;
        return zT.bWm;
    }

    static /* synthetic */ ea_0 k(ZT zT) {
        return zT.bdv;
    }

    static /* synthetic */ Pi l(ZT zT) {
        return zT.bWk;
    }

    static /* synthetic */ Pi m(ZT zT) {
        return zT.bWk;
    }

    static /* synthetic */ Pi n(ZT zT) {
        return zT.bWk;
    }

    static /* synthetic */ ea_0 o(ZT zT) {
        return zT.bdv;
    }

    static /* synthetic */ ea_0 p(ZT zT) {
        return zT.bdv;
    }
}

