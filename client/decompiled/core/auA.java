/*
 * Decompiled with CFR 0.152.
 */
public class auA
extends wl_1 {
    protected uh_1 cWx;
    protected short nO;
    protected short oX;
    private static final acl_0 aU = new ym_0(new hj_0());

    private auA() {
    }

    public static auA c(mi_2 mi_22, uh_1 uh_12) {
        return auA.a(mi_22, aey_2.cqa, uh_12, (short)0);
    }

    public static auA b(mi_2 mi_22, uh_1 uh_12, short s) {
        return auA.a(mi_22, aey_2.cqb, uh_12, s);
    }

    public static auA c(mi_2 mi_22, uh_1 uh_12, short s) {
        return auA.a(mi_22, aey_2.cqe, uh_12, (short)0, s);
    }

    public static auA a(mi_2 mi_22, uh_1 uh_12, short s, short s2) {
        return auA.a(mi_22, aey_2.cqe, uh_12, s, s2);
    }

    public static auA d(mi_2 mi_22, uh_1 uh_12, short s) {
        return auA.a(mi_22, aey_2.cqf, uh_12, s);
    }

    public static auA e(mi_2 mi_22, uh_1 uh_12, short s) {
        return auA.a(mi_22, aey_2.cqg, uh_12, s);
    }

    public static auA d(mi_2 mi_22, uh_1 uh_12) {
        return auA.a(mi_22, aey_2.cqc, uh_12, (short)0);
    }

    public static auA f(mi_2 mi_22, uh_1 uh_12, short s) {
        return auA.a(mi_22, aey_2.cqd, uh_12, s);
    }

    static auA a(mi_2 mi_22, aey_2 aey_22, uh_1 uh_12, short s) {
        return auA.a(mi_22, aey_22, uh_12, s, (short)-1);
    }

    static auA a(mi_2 mi_22, aey_2 aey_22, uh_1 uh_12, short s, short s2) {
        auA auA2;
        try {
            auA2 = (auA)aU.adr();
            auA2.uG = aU;
        }
        catch (Exception exception) {
            a.error((Object)("Erreur lors d'un checkOut sur un message de type InventoryItemModifiedEvent : " + exception.getMessage()));
            auA2 = new auA();
        }
        auA2.a(mi_22, aey_22);
        auA2.cWx = uh_12;
        auA2.nO = s;
        auA2.oX = s2;
        return auA2;
    }

    public uh_1 aHD() {
        return this.cWx;
    }

    public short ha() {
        return this.nO;
    }

    public short hG() {
        return this.oX;
    }

    public String am() {
        mi_2 mi_22 = this.Di();
        if (!(mi_22 instanceof ju_0)) {
            a.error((Object)("Log de type  " + this.getClass().getName() + " sur un inventaire non-loggable de type " + mi_22.getClass().getName()));
            return null;
        }
        String string = ((ju_0)((Object)mi_22)).am();
        uh_1 uh_12 = this.aHD();
        if (!(uh_12 instanceof ju_0)) {
            a.error((Object)("Log de type " + this.getClass().getName() + " sur un InventoryItemModifiedEvent d'un item de type non-loggable : " + uh_12.getClass().getName()));
            return null;
        }
        String string2 = ((ju_0)((Object)uh_12)).am();
        switch (this.Dh()) {
            case cqa: 
            case cqb: {
                return "itemAcquired=" + string2 + " in " + string;
            }
            case cqf: 
            case cqg: 
            case cqe: {
                return "itemChange=" + string2 + " in " + string;
            }
            case cqc: 
            case cqd: {
                return "itemLost=" + string2 + " in " + string;
            }
        }
        a.error((Object)("Log de type " + this.getClass().getName() + " sur un InventoryItemModifiedEvent d'action " + (Object)((Object)this.Dh()) + " inconnue"));
        return null;
    }

    /* synthetic */ auA(hj_0 hj_02) {
        this();
    }
}

