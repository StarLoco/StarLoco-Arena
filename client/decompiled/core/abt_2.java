/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from abt
 */
public class abt_2
extends Cs {
    private static ym_0 rI = new ym_0(new xt());

    public do_1 jm() {
        oq oq2;
        try {
            oq2 = (oq)rI.adr();
            oq.a(oq2, rI);
        }
        catch (Exception exception) {
            oq.dT().error((Object)"Erreur lors de l'extraction d'un ZoneTrigger du pool", (Throwable)exception);
            oq2 = new oq();
        }
        return oq2;
    }
}

