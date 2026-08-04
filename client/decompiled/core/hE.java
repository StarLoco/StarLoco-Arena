/*
 * Decompiled with CFR 0.152.
 */
public class hE
extends Cs {
    private static ym_0 rI = new ym_0(new xf_1());

    public hh_2 kO() {
        hh_2 hh_22;
        try {
            hh_22 = (hh_2)rI.adr();
            hh_2.a(hh_22, rI);
        }
        catch (Exception exception) {
            hh_2.dT().error((Object)"Erreur lors de l'extraction d'un DofusArenaClientInteractiveAnimatedElementSceneView du pool", (Throwable)exception);
            hh_22 = new hh_2();
        }
        return hh_22;
    }
}

