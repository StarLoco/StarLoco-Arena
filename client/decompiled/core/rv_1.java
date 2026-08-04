/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Rv
 */
public class rv_1
extends Cs {
    private static ym_0 rI = new ym_0(new apv());

    public do_1 jm() {
        ni_0 ni_02;
        try {
            ni_02 = (ni_0)rI.adr();
            ni_0.a(ni_02, rI);
        }
        catch (Exception exception) {
            ni_0.dT().error((Object)"Erreur lors de l'extraction d'un NPCTalker du pool", (Throwable)exception);
            ni_02 = new ni_0();
        }
        return ni_02;
    }
}

