/*
 * Decompiled with CFR 0.152.
 */
public class Cc
extends lp_1 {
    private static final String aKC = "SpellZoneEffect";
    private static final String aKD = "SpellRange";
    private static final String aKE = "SpellRangeWithConstraint";
    private yp_2 aKF;

    public Cc() {
        super(aKD, mx_0.Kv, aKC, mx_0.Ku, aKE, mx_0.Kw);
    }

    public void a(yp_2 yp_22, ee_2 ee_22) {
        this.aKF = yp_22;
        this.d(ee_22);
        this.aKF = null;
    }

    protected YT l(ry ry2) {
        switch (this.bsq.a((gn_0)this.bN, (fv)this.aKF, ry2)) {
            case blp: {
                kc_2 kc_22 = this.bsq.q(ry2);
                if (kc_22 != null && (!kc_22.b(avx_0.deu) || kc_22 instanceof gn_0 && apN.aDK().aDL().p((gn_0)kc_22))) {
                    aLc aLc2 = new aLc(this.aKF.jb());
                    if (aLc2.a((aOf)kc_22, (aOf)this.bN).getFirst() == ahf_2.dMP || aLc2.a((gn_0)kc_22, this.bN) == ahf_2.dMP) {
                        return YT.cbL;
                    }
                    return YT.cbK;
                }
                if (this.aKF.amu()) {
                    return YT.cbL;
                }
                return YT.cbK;
            }
            case blA: {
                kc_2 kc_23 = this.bsq.q(ry2);
                gn_0 gn_02 = null;
                if (kc_23 instanceof gn_0) {
                    gn_02 = (gn_0)kc_23;
                }
                if (gn_02 != null && gn_02.b(avx_0.deu) && !apN.aDK().aDL().p(gn_02)) {
                    return YT.cbK;
                }
                return YT.cbN;
            }
        }
        return YT.cbN;
    }
}

