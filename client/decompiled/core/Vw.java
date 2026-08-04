/*
 * Decompiled with CFR 0.152.
 */
public class Vw
extends Cs {
    private static ym_0 rI = new ym_0(new kV());

    public do_1 jm() {
        ed_2 ed_22;
        try {
            ed_22 = (ed_2)rI.adr();
            ed_2.a(ed_22, rI);
        }
        catch (Exception exception) {
            ed_2.dT().error((Object)"Erreur lors de l'extraction d'un cardUsingSwitch du pool", (Throwable)exception);
            ed_22 = new ed_2();
        }
        return ed_22;
    }
}

