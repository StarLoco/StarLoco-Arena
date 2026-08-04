/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from L
 */
public class l_0
extends wl_1 {
    private static final acl_0 aU = new ym_0(new aju_0());

    private l_0() {
    }

    public static l_0 a(mi_2 mi_22) {
        l_0 l_02;
        try {
            l_02 = (l_0)aU.adr();
            l_02.uG = aU;
        }
        catch (Exception exception) {
            a.error((Object)("Erreur lors d'un checkOut sur un message de type InventoryClearedEvent : " + exception.getMessage()));
            l_02 = new l_0();
        }
        l_02.a(mi_22, aey_2.cqh);
        return l_02;
    }

    public String am() {
        mi_2 mi_22 = this.Di();
        if (!(mi_22 instanceof ju_0)) {
            a.error((Object)("Log de type  " + this.getClass().getName() + " sur un inventaire non-loggable de type " + mi_22.getClass().getName()));
            return null;
        }
        String string = ((ju_0)((Object)mi_22)).am();
        return "clearedInventory=" + string;
    }

    /* synthetic */ l_0(aju_0 aju_02) {
        this();
    }
}

