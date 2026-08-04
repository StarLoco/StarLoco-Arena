/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Mn
 */
public class mn_1
extends Cs {
    private static ym_0 rI = new ym_0(new If());

    public do_1 jm() {
        vR vR2;
        try {
            vR2 = (vR)rI.adr();
            vR.a(vR2, rI);
        }
        catch (Exception exception) {
            vR.dT().error((Object)"Erreur lors de l'extraction d'une DemonTotem du pool", (Throwable)exception);
            vR2 = new vR();
        }
        return vR2;
    }
}

