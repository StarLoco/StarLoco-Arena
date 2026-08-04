/*
 * Decompiled with CFR 0.152.
 */
public class ado
extends sb_0 {
    private static final acl_0 uG = new ym_0(new e_0());
    private int cbD;
    private int bk;
    private int bl;

    protected ado() {
    }

    public static ado asl() {
        ado ado2;
        try {
            ado2 = (ado)uG.adr();
            ado2.a(uG);
        }
        catch (Exception exception) {
            ado2 = new ado();
            a.error((Object)("Erreur lors d'un checkOut sur un message de type UIWorldSceneMouseReleasedMessage : " + exception.getMessage()));
        }
        return ado2;
    }

    public int getId() {
        return 30000;
    }

    public void jH(int n2) {
        this.cbD = n2;
    }

    public void jL(int n2) {
        this.bk = n2;
    }

    public void jM(int n2) {
        this.bl = n2;
    }

    public int aqY() {
        return this.cbD;
    }

    public int au() {
        return this.bk;
    }

    public int av() {
        return this.bl;
    }
}

