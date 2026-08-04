/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Vr
 */
public class vr_2
extends sb_0 {
    private static final acl_0 uG = new ym_0(new aig_0());
    private long ap;

    private vr_2() {
    }

    public static vr_2 ain() {
        vr_2 vr_22;
        try {
            vr_22 = (vr_2)uG.adr();
            vr_22.a(uG);
        }
        catch (Exception exception) {
            vr_22 = new vr_2();
            a.error((Object)("Erreur lors d'un checkOut sur un message de type UIExchangeInvitationAcceptRequestMessage : " + exception.getMessage()));
        }
        return vr_22;
    }

    public int getId() {
        return 16801;
    }

    public long Y() {
        return this.ap;
    }

    public void d(long l2) {
        this.ap = l2;
    }

    /* synthetic */ vr_2(aig_0 aig_02) {
        this();
    }
}

