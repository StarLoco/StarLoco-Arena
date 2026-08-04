/*
 * Decompiled with CFR 0.152.
 */
public class asE
extends sb_0 {
    private static final acl_0 uG = new ym_0(new bh());
    private String fM;
    private String m_name;

    private asE() {
    }

    public static asE aFE() {
        asE asE2;
        try {
            asE2 = (asE)uG.adr();
            asE2.a(uG);
        }
        catch (Exception exception) {
            asE2 = new asE();
            a.error((Object)("Erreur lors d'un checkOut sur un message de type UITournamentCreationRequestMessage : " + exception.getMessage()));
        }
        return asE2;
    }

    public int getId() {
        return 20072;
    }

    public String getDescription() {
        return this.fM;
    }

    public void setDescription(String string) {
        this.fM = string;
    }

    public String getName() {
        return this.m_name;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    /* synthetic */ asE(bh bh2) {
        this();
    }
}

