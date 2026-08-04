/*
 * Decompiled with CFR 0.152.
 */
public class qJ {
    public static final String PACKAGE = "dofusarena.achievement";

    public static void selectAchievementType(aGJ aGJ2) {
        ajk_1 ajk_12 = (ajk_1)aGJ2.getItemValue();
        if (ajk_12 != null) {
            sb_0 sb_02 = new sb_0();
            sb_02.bF(ajk_12.tI());
            sb_02.f(22051);
            acu_1.ara().c(sb_02);
        }
    }

    public static void selectAchievementSubtype(aGJ aGJ2) {
        li_2 li_22 = (li_2)aGJ2.getItemValue();
        if (li_22 != null) {
            sb_0 sb_02 = new sb_0();
            sb_02.bF(li_22.pV());
            sb_02.f(22052);
            acu_1.ara().c(sb_02);
        }
    }

    public static void selectAchievement(aGJ aGJ2) {
        aea_1 aea_12 = (aea_1)aGJ2.getItemValue();
        if (aea_12 != null) {
            sb_0 sb_02 = new sb_0();
            sb_02.bF(aea_12.aty().tI());
            sb_02.f(22053);
            acu_1.ara().c(sb_02);
        }
    }
}

