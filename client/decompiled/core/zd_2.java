/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Zd
 */
public class zd_2
extends yp_2 {
    public static final String UV = "usable";
    public static final String UW = "smallDescription";
    public static final String ccD = "cooldownInFight";
    public static final String ccE = "linkedSpells";
    public static final String[] ce = new String[]{"usable", "smallDescription", "cooldown", "linkedSpells"};
    public static final String[] oT = new String[ce.length + yp_2.ce.length];
    private ee_2 bN;

    public zd_2(yp_2 yp_22) {
        super(yp_22.getId(), yp_22.iQ(), yp_22.iR(), yp_22.iS(), yp_22.iT(), yp_22.iU(), yp_22.ja(), yp_22.et(), yp_22.iW(), yp_22.iN(), yp_22.iY(), yp_22.iZ(), yp_22.getValue(), yp_22.getTarget(), yp_22.iX(), yp_22.eA(), yp_22.iL(), yp_22.adW(), yp_22.eD(), yp_22.jb(), yp_22.eF(), yp_22.jd());
        this.rd = yp_22.iK();
        this.rt = yp_22.jc();
    }

    public ee_2 tG() {
        return this.bN;
    }

    public void b(ee_2 ee_22) {
        this.bN = ee_22;
    }

    public jv_1 anm() {
        if (this.bN != null) {
            return apN.aDK().aDL().a((gn_0)this.bN, (fv)this, (ry)null);
        }
        ee_2 ee_22 = (ee_2)apN.aDK().aDL().ass().nP();
        if (ee_22 != null) {
            return apN.aDK().aDL().a((gn_0)ee_22, (fv)this, (ry)null);
        }
        return jv_1.blp;
    }

    public String[] getFields() {
        return oT;
    }

    public Object getFieldValue(String string) {
        if (string.equals(UV)) {
            return this.anm() == jv_1.blp;
        }
        if (string.equals(UW)) {
            StringBuilder stringBuilder = new StringBuilder(this.getName());
            stringBuilder.append(" (").append(this.iR()).append(' ').append(aon_0.aYc().getString("AP")).append(")");
            jv_1 jv_12 = this.anm();
            if (jv_12 != jv_1.blp) {
                stringBuilder.append('\n').append(aon_0.aYc().getString(jv_12.toString()));
            }
            return stringBuilder.toString();
        }
        if (string.equals(ccD)) {
            adu_0 adu_02 = apN.aDK().aDL();
            if (adu_02 != null && adu_02.Zy() == ko_2.bpv) {
                azg_0 azg_02 = adu_02.ass();
                ee_2 ee_22 = this.tG();
                if (ee_22 != null) {
                    short s = azg_02.JI();
                    int n2 = ee_22.PN().c(this, s);
                    akv_0 akv_02 = (akv_0)((axD)ee_22.PH()).aKB().t(this.getId());
                    int n3 = 0;
                    if (akv_02 != null) {
                        n3 = akv_02.aVC() - s;
                        if (akv_02.getPosition() > azg_02.bj(ee_22.getId())) {
                            ++n3;
                        }
                    }
                    return n2 != -1 ? Math.max(n2, n3) : 0;
                }
            }
            return 0;
        }
        if (string.equals(ccE)) {
            return this.jc();
        }
        return super.getFieldValue(string);
    }

    static {
        System.arraycopy(ce, 0, oT, 0, ce.length);
        System.arraycopy(yp_2.ce, 0, oT, ce.length, yp_2.ce.length);
    }
}

