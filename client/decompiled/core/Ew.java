/*
 * Decompiled with CFR 0.152.
 */
public class Ew
extends ays {
    private static final acl_0 aU = new ym_0(new st_0());

    public Ew() {
        super(avx_0.deI);
    }

    public Ew NN() {
        Ew ew;
        try {
            ew = (Ew)aU.adr();
            ew.uG = aU;
        }
        catch (Exception exception) {
            ew = new Ew();
            ew.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un " + ew.getClass().getSimpleName() + " : " + exception.getMessage()));
        }
        return ew;
    }
}

