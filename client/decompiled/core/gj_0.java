/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from GJ
 */
public class gj_0
extends Cs {
    private static ym_0 rI = new ym_0(new qy());

    public do_1 jm() {
        aac_2 aac_22;
        try {
            aac_22 = (aac_2)rI.adr();
            aac_2.a(aac_22, rI);
        }
        catch (Exception exception) {
            aac_2.dT().error((Object)"Erreur lors de l'extraction d'un DemonI du pool", (Throwable)exception);
            aac_22 = new aac_2();
        }
        return aac_22;
    }
}

