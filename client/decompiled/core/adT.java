/*
 * Decompiled with CFR 0.152.
 */
public class adT
extends ee_2 {
    private String cnA;
    private int DB;

    public adT(ee_2 ee_22, aJt aJt2) {
        this.aTv = ee_22;
        this.baJ = xq.axE;
        this.setName(aJt2.getName());
        if (this.aTv != null) {
            this.setName(this.getName() + " (" + ee_22.getName() + ")");
        }
        this.hA(String.valueOf(aJt2.aFw()));
        ll_0 ll_02 = this.baQ.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            alm_0 alm_02 = (alm_0)ll_02.value();
            alm_02.atS();
        }
        this.a(Lr.bqx).at(aJt2.aFu());
        this.a(Lr.bqz).at(aJt2.aFv());
        this.a(Lr.bqy).at(aJt2.Vo());
        this.a(Lr.bqy).aAF();
        this.a(Lr.bqx).aAF();
        this.a(Lr.bqz).aAF();
        if (this.aTv != null) {
            this.a(Lr.bqA).at(this.aTv.d(Lr.bqA));
        }
        this.a(Lr.bqA).aAF();
        this.a(Lr.brd).set(aJt2.ot());
        this.a(Lr.bre).set(aJt2.ou());
        this.DB = aJt2.oz();
        if (aJt2.Qx().length > 0) {
            try {
                for (int j = 0; j < aJt2.Qx().length; ++j) {
                    this.Oh().a(je_1.Wa().el(aJt2.Qx()[j]));
                }
            }
            catch (Exception exception) {
                a.error((Object)"Erreur lors de l'ajout d'un sort \u00e0 un SummonedFighter :", (Throwable)exception);
            }
        }
        if (aJt2.op()) {
            this.PL().a(avx_0.deA);
        }
        if (aJt2.aFx()) {
            this.PL().a(avx_0.deB);
        }
        if (aJt2.or()) {
            this.PL().a(avx_0.dev);
        }
        if (aJt2.os()) {
            this.PL().a(avx_0.dex);
        }
        if (aJt2.ov()) {
            this.PL().a(avx_0.deD);
        }
        if (aJt2.oy()) {
            this.PL().a(avx_0.deF);
        }
    }

    public void hA(String string) {
        this.cnA = string;
        this.Of();
    }

    protected String Oe() {
        return this.cnA;
    }

    public int oz() {
        return this.DB;
    }

    public boolean Dk() {
        return true;
    }
}

