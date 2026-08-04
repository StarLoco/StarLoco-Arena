/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from acl
 */
public class acl_1
extends abu_1 {
    private static final acl_0 uG = new ym_0(new arz());
    private int cbD;

    protected acl_1() {
    }

    public static acl_1 aqX() {
        acl_1 acl_12;
        try {
            acl_12 = (acl_1)uG.adr();
            acl_12.a(uG);
        }
        catch (Exception exception) {
            acl_12 = new acl_1();
            a.error((Object)("Erreur lors d'un checkOut sur un message de type UIWorldSceneMouseMovedExtendedMessage : " + exception.getMessage()));
        }
        return acl_12;
    }

    public int getId() {
        return 30002;
    }

    public void jH(int n2) {
        this.cbD = n2;
    }

    public int aqY() {
        return this.cbD;
    }
}

