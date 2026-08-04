/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aNN
 */
public class ann_2
extends Cs {
    public static ym_0 rI = new ym_0(new ww_1());

    public do_1 jm() {
        xx_2 xx_22;
        try {
            xx_22 = (xx_2)rI.adr();
            xx_2.a(xx_22, rI);
        }
        catch (Exception exception) {
            xx_2.a.error((Object)"Erreur lors de l'extraction d'un FusionLaboratory du pool", (Throwable)exception);
            xx_22 = new xx_2();
        }
        return xx_22;
    }
}

