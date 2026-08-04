/*
 * Decompiled with CFR 0.152.
 */
public class aBR
extends Cs {
    private static ym_0 rI = new ym_0(new ahs_0());

    public do_1 jm() {
        PY pY;
        try {
            pY = (PY)rI.adr();
            PY.a(pY, rI);
        }
        catch (Exception exception) {
            PY.dT().error((Object)"Erreur lors de l'extraction d'une zaap du pool", (Throwable)exception);
            pY = new PY();
        }
        return pY;
    }
}

