/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from mj
 */
public class mj_2
extends Cs {
    private static ym_0 rI = new ym_0(new mq_2());

    public do_1 jm() {
        ayF ayF2;
        try {
            ayF2 = (ayF)rI.adr();
            ayF.a(ayF2, rI);
        }
        catch (Exception exception) {
            ayF.dT().error((Object)"Erreur lors de l'extraction d'un CARDMASTER du pool", (Throwable)exception);
            ayF2 = new ayF();
        }
        return ayF2;
    }
}

