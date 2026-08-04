/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aHD
 */
public class ahd_1 {
    public static final String PACKAGE = "dofusarena.fusionLaboratory";

    public static void dropCard(aiU aiU2) {
        if (aiU2.getValue() instanceof wy_2) {
            wy_2 wy_22 = (wy_2)aiU2.getValue();
            sb_0 sb_02 = new sb_0();
            sb_02.g(wy_22.jf());
            sb_02.f(20170);
            acu_1.ara().c(sb_02);
        }
    }

    public static final void dragCard(aly_2 aly_22) {
        if (aly_22.getValue() instanceof xj) {
            xj xj2 = (xj)aly_22.getValue();
            sb_0 sb_02 = new sb_0();
            sb_02.g(xj2.jf());
            sb_02.f(20171);
            acu_1.ara().c(sb_02);
        }
    }

    public static final void removeCard(aGJ aGJ2) {
        if (aGJ2.getItemValue() instanceof xj) {
            xj xj2 = (xj)aGJ2.getItemValue();
            sb_0 sb_02 = new sb_0();
            sb_02.g(xj2.jf());
            sb_02.f(20171);
            acu_1.ara().c(sb_02);
        }
    }

    public static void dropFusionCard(aiU aiU2) {
        if (aiU2.getValue() instanceof wy_2) {
            wy_2 wy_22 = (wy_2)aiU2.getValue();
            sb_0 sb_02 = new sb_0();
            sb_02.g(wy_22.jf());
            sb_02.f(20172);
            acu_1.ara().c(sb_02);
        }
    }

    public static final void dragFusionCard(aly_2 aly_22) {
        if (aly_22.getValue() instanceof xj) {
            xj xj2 = (xj)aly_22.getValue();
            sb_0 sb_02 = new sb_0();
            sb_02.g(xj2.jf());
            sb_02.f(20173);
            acu_1.ara().c(sb_02);
        }
    }

    public static final void removeFusionCard(aGJ aGJ2) {
        if (aGJ2.getItemValue() instanceof xj) {
            xj xj2 = (xj)aGJ2.getItemValue();
            sb_0 sb_02 = new sb_0();
            sb_02.g(xj2.jf());
            sb_02.f(20173);
            acu_1.ara().c(sb_02);
        }
    }

    public static void fusionRequest(ke ke2, lj_1 lj_12, lj_1 lj_13, lj_1 lj_14, lj_1 lj_15, lj_1 lj_16, lj_1 lj_17) {
        r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("questionFusion"), 1177L, 102, 1);
        r_02.a(new dP(lj_12, lj_13, lj_14, lj_15, lj_16, lj_17));
    }

    public static void closeFusionLabDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20013);
        acu_1.ara().c(sb_02);
    }
}

