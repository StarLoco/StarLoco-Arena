/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aCL
 */
public class acl_2
extends ays {
    private static final acl_0 aU = new ym_0(new rn_1());

    public acl_2() {
        super(avx_0.deH);
    }

    public acl_2 aOw() {
        acl_2 acl_22;
        try {
            acl_22 = (acl_2)aU.adr();
            acl_22.uG = aU;
        }
        catch (Exception exception) {
            acl_22 = new acl_2();
            acl_22.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un " + acl_22.getClass().getSimpleName() + " : " + exception.getMessage()));
        }
        return acl_22;
    }
}

