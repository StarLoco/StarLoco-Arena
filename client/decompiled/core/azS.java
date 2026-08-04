/*
 * Decompiled with CFR 0.152.
 */
public class azS
extends sb_0 {
    private static final acl_0 uG = new ym_0(new aye_0());
    private long ap;

    private azS() {
    }

    public static azS aMv() {
        azS azS2;
        try {
            azS2 = (azS)uG.adr();
            azS2.a(uG);
        }
        catch (Exception exception) {
            azS2 = new azS();
            a.error((Object)("Erreur lors d'un checkOut sur un message de type UIRejectFightInvitationRequestMessage : " + exception.getMessage()));
        }
        return azS2;
    }

    public int getId() {
        return 16802;
    }

    public long Y() {
        return this.ap;
    }

    public void d(long l2) {
        this.ap = l2;
    }

    /* synthetic */ azS(aye_0 aye_02) {
        this();
    }
}

