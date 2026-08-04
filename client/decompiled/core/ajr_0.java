/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ajr
 */
public class ajr_0
extends Cs {
    private static ym_0 rI = new ym_0(new ql_2());

    public do_1 jm() {
        br_0 br_02;
        try {
            br_02 = (br_0)rI.adr();
            br_0.a(br_02, rI);
        }
        catch (Exception exception) {
            br_0.dT().error((Object)"Erreur lors de l'extraction d'une graveyard du pool", (Throwable)exception);
            br_02 = new br_0();
        }
        return br_02;
    }
}

