/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aEk
 */
public class aek_2 {
    public static final String PACKAGE = "dofusarena.fight";
    protected static final Logger a = Logger.getLogger(aek_2.class);

    public static void setReadyForPlacement(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(18009);
        acu_1.ara().c(sb_02);
    }

    public static void setReadyForObservation(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(18010);
        acu_1.ara().c(sb_02);
    }

    public static void setReadyForAction(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(18011);
        acu_1.ara().c(sb_02);
    }

    public static void giveUpFight(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(18000);
        acu_1.ara().c(sb_02);
    }

    public static void useRuler(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(18018);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseFighterInformations(ke ke2, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(ee_22);
        ayd_02.f(18012);
        acu_1.ara().c(ayd_02);
    }

    public static void openCloseFighterBuff(ke ke2, ee_2 ee_22, ai_2 ai_22) {
        azs_0.aLV().g("fight.timeline.selectedFighter", ee_22);
        qu_0.openClosePopup(ke2, ai_22);
    }

    public static void onOverTimelineFighter(ke ke2, aht_1 aht_12, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(ee_22);
        ayd_02.f(18016);
        acu_1.ara().c(ayd_02);
    }

    public static void onOutTimelineFighter(ke ke2, aht_1 aht_12, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(ee_22);
        ayd_02.f(18017);
        acu_1.ara().c(ayd_02);
    }

    public static void openCloseEventCard(aGJ aGJ2) {
        kq kq2 = new kq();
        kq2.a((tO)aGJ2.getItemValue());
        kq2.f(18015);
        acu_1.ara().c(kq2);
    }

    public static void fighterEndsTurn(ke ke2, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(ee_22);
        ayd_02.f(18001);
        acu_1.ara().c(ayd_02);
    }

    public static void fighterSetSouthEastDirection(ke ke2, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(ee_22);
        ayd_02.f(18002);
        acu_1.ara().c(ayd_02);
    }

    public static void fighterSetSouthWestDirection(ke ke2, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(ee_22);
        ayd_02.f(18003);
        acu_1.ara().c(ayd_02);
    }

    public static void fighterSetNorthWestDirection(ke ke2, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(ee_22);
        ayd_02.f(18004);
        acu_1.ara().c(ayd_02);
    }

    public static void fighterSetNorthEastDirection(ke ke2, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(ee_22);
        ayd_02.f(18005);
        acu_1.ara().c(ayd_02);
    }

    public static void fighterSelectSpell(abd_1 abd_12, ee_2 ee_22, yp_2 yp_22, rf_0 rf_02) {
        if (abd_12.getButton() == 1) {
            if (yp_22.jc() == null) {
                da_1 da_12 = new da_1();
                da_12.b(ee_22);
                da_12.a(yp_22);
                da_12.f(18006);
                acu_1.ara().c(da_12);
                if (yp_22.jd() != null) {
                    rf_02.setVisible(!rf_02.getVisible());
                }
            } else {
                rf_02.setVisible(!rf_02.getVisible());
            }
        } else {
            aek_2.openCloseSpellInfos(abd_12, yp_22);
        }
    }

    public static void fighterSelectSpell(abd_1 abd_12, ee_2 ee_22, yp_2 yp_22) {
        if (abd_12.getButton() == 1) {
            da_1 da_12 = new da_1();
            da_12.b(ee_22);
            da_12.a(yp_22);
            da_12.f(18006);
            acu_1.ara().c(da_12);
        } else {
            aek_2.openCloseSpellInfos(abd_12, yp_22);
        }
    }

    public static void fighterSelectFighterCard(abd_1 abd_12, ee_2 ee_22, ve_0 ve_02) {
        if (abd_12.getButton() == 1) {
            lf_0 lf_02 = new lf_0();
            lf_02.b(ee_22);
            lf_02.a(ve_02);
            lf_02.f(18007);
            acu_1.ara().c(lf_02);
        } else {
            aek_2.openCloseFighterCardInfos(abd_12, ve_02);
        }
    }

    public static void fighterSelectCloseCombat(ke ke2, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(ee_22);
        ayd_02.f(18008);
        acu_1.ara().c(ayd_02);
    }

    public static void openCloseSpellInfos(ke ke2, yp_2 yp_22) {
        da_1 da_12 = new da_1();
        da_12.a(yp_22);
        da_12.f(18013);
        acu_1.ara().c(da_12);
    }

    public static void openCloseFighterCardInfos(ke ke2, ve_0 ve_02) {
        lf_0 lf_02 = new lf_0();
        lf_02.a(ve_02);
        lf_02.f(18014);
        acu_1.ara().c(lf_02);
    }

    public static void showEquipmentInfos(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof ve_0) {
            pd_2 pd_22 = new pd_2();
            pd_22.d((ve_0)object);
            pd_22.f(16622);
            acu_1.ara().c(pd_22);
        }
    }

    public static void hideEquipmentInfos(aGJ aGJ2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16623);
        acu_1.ara().c(sb_02);
    }

    public static void showSpellInfos(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof yp_2) {
            da_1 da_12 = new da_1();
            da_12.a((yp_2)object);
            da_12.f(16624);
            acu_1.ara().c(da_12);
        }
    }

    public static void hideSpellInfos(aGJ aGJ2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16625);
        acu_1.ara().c(sb_02);
    }

    public static void showCoachCardInfos(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof wy_2) {
            ia_2 ia_22 = new ia_2();
            ia_22.b((wy_2)object);
            ia_22.f(16700);
            acu_1.ara().c(ia_22);
        }
    }

    public static void hideCoachCardInfos(aGJ aGJ2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16701);
        acu_1.ara().c(sb_02);
    }

    public static void highlightSelectedFighter(abd_1 abd_12, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(ee_22);
        ayd_02.f(18016);
        acu_1.ara().c(ayd_02);
    }

    public static void unlightSelectedFighter(abd_1 abd_12, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(ee_22);
        ayd_02.f(18017);
        acu_1.ara().c(ayd_02);
    }

    public static void hideFighter(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(18019);
        acu_1.ara().c(sb_02);
    }

    public static void showSubstitutes(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(23064);
        acu_1.ara().c(sb_02);
    }

    public static void selectAchievement(aGJ aGJ2) {
        azs_0.aLV().g("selectedEndFightAchievement", aGJ2.getItemValue());
    }
}

