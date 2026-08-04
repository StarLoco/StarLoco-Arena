/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from axt
 */
public class axt_0
extends Cs {
    private static ym_0 rI = new ym_0(new adz_2());

    public do_1 jm() {
        uk_0 uk_02;
        try {
            uk_02 = (uk_0)rI.adr();
            uk_0.a(uk_02, rI);
        }
        catch (Exception exception) {
            uk_0.dT().error((Object)"Erreur lors de l'extraction d'un Challenge du pool", (Throwable)exception);
            uk_02 = new uk_0();
        }
        return uk_02;
    }
}

