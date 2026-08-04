/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from awk
 */
public class awk_0
extends Cs {
    private static ym_0 rI = new ym_0(new ci_2());

    public do_1 jm() {
        hb_0 hb_02;
        try {
            hb_02 = (hb_0)rI.adr();
            hb_0.a(hb_02, rI);
        }
        catch (Exception exception) {
            hb_0.dT().error((Object)"Erreur lors de l'extraction d'un TournamentTotem du pool", (Throwable)exception);
            hb_02 = new hb_0();
        }
        return hb_02;
    }
}

