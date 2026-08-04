/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from akP
 */
public class akp_2
extends ays {
    private static final acl_0 aU = new ym_0(new aev_2());

    public akp_2() {
        super(avx_0.deJ);
    }

    public akp_2 aAf() {
        akp_2 akp_22;
        try {
            akp_22 = (akp_2)aU.adr();
            akp_22.uG = aU;
        }
        catch (Exception exception) {
            akp_22 = new akp_2();
            akp_22.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un " + akp_22.getClass().getSimpleName() + " : " + exception.getMessage()));
        }
        return akp_22;
    }
}

