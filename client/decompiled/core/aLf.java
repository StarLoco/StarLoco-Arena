/*
 * Decompiled with CFR 0.152.
 */
public class aLf
extends Cs {
    private static ym_0 rI = new ym_0(new alg_1());

    public do_1 jm() {
        aof_0 aof_02;
        try {
            aof_02 = (aof_0)rI.adr();
            aof_0.a(aof_02, rI);
        }
        catch (Exception exception) {
            aof_0.dT().error((Object)"Erreur lors de l'extraction d'une mailbox du pool", (Throwable)exception);
            aof_02 = new aof_0();
        }
        return aof_02;
    }
}

