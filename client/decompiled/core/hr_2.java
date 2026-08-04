/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from hr
 */
public class hr_2
extends Cs {
    private static ym_0 rI = new ym_0(new fu_0());

    public do_1 jm() {
        acn_0 acn_02;
        try {
            acn_02 = (acn_0)rI.adr();
            acn_0.a(acn_02, rI);
        }
        catch (Exception exception) {
            acn_0.dT().error((Object)"Erreur lors de l'extraction d'un DemonIII du pool", (Throwable)exception);
            acn_02 = new acn_0();
        }
        return acn_02;
    }
}

