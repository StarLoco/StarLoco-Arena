/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aBu
 */
public class abu_1
extends sb_0 {
    private static final acl_0 uG = new ym_0(new ajl_0());
    private int bk;
    private int bl;

    protected abu_1() {
    }

    public static abu_1 aNs() {
        abu_1 abu_12;
        try {
            abu_12 = (abu_1)uG.adr();
            abu_12.a(uG);
        }
        catch (Exception exception) {
            abu_12 = new abu_1();
            a.error((Object)("Erreur lors d'un checkOut sur un message de type UIWorldSceneMouseMovedMessage : " + exception.getMessage()));
        }
        return abu_12;
    }

    public int getId() {
        return 30001;
    }

    public void jL(int n2) {
        this.bk = n2;
    }

    public void jM(int n2) {
        this.bl = n2;
    }

    public int au() {
        return this.bk;
    }

    public int av() {
        return this.bl;
    }
}

