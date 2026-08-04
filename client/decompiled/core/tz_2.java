/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Tz
 */
public class tz_2
extends he_1 {
    private static acl_0 bOv = new ym_0(new ach_2());

    public static tz_2 b(rG rG2) {
        tz_2 tz_22;
        try {
            tz_22 = (tz_2)bOv.adr();
            tz_22.a(bOv);
        }
        catch (Exception exception) {
            a.error((Object)("Erreur lors d'un checkOut sur un message de type EffectAreaManager : " + exception.getMessage()));
            tz_22 = new tz_2();
            tz_22.a((acl_0)null);
            tz_22.b();
        }
        tz_22.a(rG2);
        return tz_22;
    }

    public ack_1 bJ(long l2) {
        yl_1 yl_12 = ame_1.aWP().eN(l2);
        return yl_12.a((Es)null);
    }

    public void a(int n2, int n3, short s, int n4, int n5, short s2, kc_2 kc_22) {
        super.a(n2, n3, s, n4, n5, s2, kc_22);
    }
}

