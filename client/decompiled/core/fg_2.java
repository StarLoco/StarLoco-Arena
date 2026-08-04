/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from fG
 */
public class fg_2
extends Cs {
    private static ym_0 rI = new ym_0(new abv_0());

    public do_1 jm() {
        zs_1 zs_12;
        try {
            zs_12 = (zs_1)rI.adr();
            zs_1.a(zs_12, rI);
        }
        catch (Exception exception) {
            zs_1.dT().error((Object)"Erreur lors de l'extraction d'un BreedMaster du pool", (Throwable)exception);
            zs_12 = new zs_1();
        }
        return zs_12;
    }
}

