/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from adO
 */
public class ado_2 {
    public static final String PACKAGE = "dofusarena.exchange";

    public static void setReadyForExchange(ke ke2, Long l2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16821);
        sb_02.e(l2);
        acu_1.ara().c(sb_02);
    }

    public static void dragCard(aly_2 aly_22, Long l2) {
        Object object = aly_22.getValue();
        if (object != null && object instanceof wy_2) {
            rg_0 rg_02 = new rg_0();
            rg_02.f(16808);
            rg_02.cm(l2);
            rg_02.a((wy_2)object);
            acu_1.ara().c(rg_02);
        }
    }

    public static void removeCard(aGJ aGJ2, Long l2) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof wy_2) {
            rg_0 rg_02 = new rg_0();
            rg_02.f(16808);
            rg_02.cm(l2);
            rg_02.a((wy_2)object);
            acu_1.ara().c(rg_02);
        }
    }

    public static void dropCard(aiU aiU2, Long l2) {
        Object object = aiU2.getValue();
        if (object != null && object instanceof wy_2) {
            rg_0 rg_02 = new rg_0();
            rg_02.f(16807);
            rg_02.cm(l2);
            rg_02.a((wy_2)object);
            acu_1.ara().c(rg_02);
        }
    }

    public static void closeCoachExchangeDialog(ke ke2, Long l2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16809);
        sb_02.e(l2);
        acu_1.ara().c(sb_02);
    }
}

