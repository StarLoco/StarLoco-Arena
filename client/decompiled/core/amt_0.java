/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aMT
 */
public class amt_0
extends Cs {
    private static ym_0 rI = new ym_0(new dn_0());

    public do_1 jm() {
        pn_0 pn_02;
        try {
            pn_02 = (pn_0)rI.adr();
            pn_0.a(pn_02, rI);
        }
        catch (Exception exception) {
            pn_0.dT().error((Object)"Erreur lors de l'extraction d'un DemonChallenge du pool", (Throwable)exception);
            pn_02 = new pn_0();
        }
        return pn_02;
    }
}

