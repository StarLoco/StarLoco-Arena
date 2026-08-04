/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Og
 */
public class og_2 {
    public static final String PACKAGE = "dofusarena.sphereBoard";

    public static void onSphereOver(aGJ aGJ2) {
        ayr_0 ayr_02 = (ayr_0)aGJ2.getItemValue();
        if (ayr_02.azm()) {
            azs_0.aLV().g("sphereboard.overSphere", ayr_02);
            if (ayr_02.el() != 0) {
                azs_0.aLV().g("sphereboard.overSphereSpell", je_1.Wa().el(ayr_02.el()));
            }
            add_1.aOG().a("sphereDetailsDialog", oh_2.bq("sphereDetailsDialog"), null, false, aGJ2.getScreenX() + 30, aGJ2.getScreenY() - 100, 1L, (short)10000);
        }
    }

    public static void onSphereOut(aGJ aGJ2) {
        add_1.aOG().kO("sphereDetailsDialog");
        azs_0.aLV().kb("sphereboard.overSphere");
        azs_0.aLV().kb("sphereboard.overSphereSpell");
    }

    public static void onSphereClick(aGJ aGJ2) {
        ac_2 ac_22 = new ac_2();
        ac_22.a((ayr_0)aGJ2.getItemValue());
        ac_22.f(16921);
        acu_1.ara().c(ac_22);
    }

    public static void onSphereDoubleClick(aGJ aGJ2) {
        ac_2 ac_22 = new ac_2();
        ac_22.a((ahr_2)aGJ2.oE());
        ac_22.a((ayr_0)aGJ2.getItemValue());
        ac_22.f(16926);
        acu_1.ara().c(ac_22);
    }

    public static void unloadDialog(ke ke2) {
        if (apN.aDK().c(afb_1.auN())) {
            apN.aDK().b(afb_1.auN());
        }
    }

    public static void buySphere(ke ke2, ayr_0 ayr_02, ahr_2 ahr_22) {
        ac_2 ac_22 = new ac_2();
        ac_22.a(ahr_22);
        ac_22.a(ayr_02);
        ac_22.f(16926);
        acu_1.ara().c(ac_22);
    }

    public static void teleportToSphere(ke ke2, ayr_0 ayr_02, ahr_2 ahr_22) {
        ac_2 ac_22 = new ac_2();
        ac_22.a(ahr_22);
        ac_22.f(16923);
        acu_1.ara().c(ac_22);
    }

    public static void centerOnToken(ke ke2, ahr_2 ahr_22) {
        ac_2 ac_22 = new ac_2();
        ac_22.a(ahr_22);
        ac_22.f(16925);
        acu_1.ara().c(ac_22);
    }

    public static void zoomOut(ke ke2, ahr_2 ahr_22) {
        ac_2 ac_22 = new ac_2();
        ac_22.a(ahr_22);
        ac_22.f(16927);
        acu_1.ara().c(ac_22);
    }

    public static void zoomIn(ke ke2, ahr_2 ahr_22) {
        ac_2 ac_22 = new ac_2();
        ac_22.a(ahr_22);
        ac_22.f(16928);
        acu_1.ara().c(ac_22);
    }

    public static void showDestinationSphere(ke ke2, ahr_2 ahr_22) {
        ac_2 ac_22 = new ac_2();
        ac_22.a(ahr_22);
        ac_22.f(16924);
        acu_1.ara().c(ac_22);
    }

    public static void selectCard(aGJ aGJ2) {
        wy_2 wy_22;
        wy_2 wy_23 = (wy_2)aGJ2.getItemValue();
        sj_1 sj_12 = apN.aDK().Ln();
        ky_2 ky_22 = sj_12.yD();
        short s = 0;
        wy_2 wy_24 = (wy_2)ky_22.pI().ac(Math.abs(wy_23.jf()));
        if (wy_24 != null && ky_22.bU(Math.abs(wy_23.jf()))) {
            s = (short)(s + wy_24.hG());
        }
        if ((wy_22 = (wy_2)ky_22.pI().ac(-Math.abs(wy_23.jf()))) != null) {
            s = (short)(s + wy_22.hG());
        }
        if (s > 0) {
            azs_0.aLV().g("sphereboard.selectedCard", aGJ2.getItemValue());
        }
    }

    public static void increaseList(ke ke2, rf_0 rf_02) {
        rf_02.setOffset(rf_02.getOffset() + 1.0f);
    }

    public static void decreaseList(ke ke2, rf_0 rf_02) {
        rf_02.setOffset(rf_02.getOffset() - 1.0f);
    }

    public static void showPopup(abd_1 abd_12, ai_2 ai_22) {
        qu_0.popup(abd_12, ai_22);
    }

    public static void hidePopup(abd_1 abd_12, ai_2 ai_22) {
        qu_0.closePopup(abd_12, ai_22);
    }

    public static void previousFighter(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16929);
        acu_1.ara().c(sb_02);
    }

    public static void nextFighter(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16930);
        acu_1.ara().c(sb_02);
    }
}

