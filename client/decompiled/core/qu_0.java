/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from qU
 */
public class qu_0 {
    public static final String PACKAGE = "xulor";
    private static Wm afB = new Wm(null);

    public static boolean unloadDialog(ke ke2) {
        na_1 na_12 = ke2.oF();
        if (na_12 != null) {
            String string = na_12.getElementMap().getId();
            add_1.aOG().w(string, false);
        }
        return false;
    }

    private static boolean a(adg_2 adg_22, ai_2 ai_22, float f) {
        if (adg_22 != null && ai_22 != null && !ago_2.getInstance().isDragging()) {
            aam_1.aMF().b(afB);
            afB.setPopup(ai_22);
            afB.setWidget(adg_22);
            if (f == 0.0f) {
                ai_22.b((aci_0)adg_22);
            } else {
                aam_1.aMF().a(afB, (long)(1000.0f * f), -1, 1);
            }
        }
        return false;
    }

    public static boolean popup(ke ke2, ai_2 ai_22, String string) {
        return qu_0.a(ke2, ai_22, Float.valueOf(string).floatValue());
    }

    private static boolean a(ke ke2, ai_2 ai_22, float f) {
        na_1 na_12 = ke2.oF();
        if (!(na_12 instanceof adg_2)) {
            return false;
        }
        adg_2 adg_22 = null;
        if (((adg_2)na_12).getPopup() != null) {
            ai_22 = ((adg_2)na_12).getPopup();
            adg_22 = (adg_2)na_12;
        } else if (ai_22 != null) {
            adg_22 = (adg_2)ai_22.getParentOfType(adg_2.class);
        }
        qu_0.a(adg_22, ai_22, f);
        return false;
    }

    public static boolean popup(ke ke2) {
        na_1 na_12 = ke2.oF();
        if (!(na_12 instanceof adg_2)) {
            return false;
        }
        ai_2 ai_22 = ((adg_2)na_12).getPopup();
        if (ai_22 != null) {
            qu_0.popup(ke2, ai_22);
        }
        return false;
    }

    public static boolean popup(ke ke2, ai_2 ai_22) {
        return qu_0.a(ke2, ai_22, 0.0f);
    }

    public static boolean popup(ai_2 ai_22, adg_2 adg_22) {
        return qu_0.a(adg_22, ai_22, 0.0f);
    }

    public static boolean popup(ke ke2, ai_2 ai_22, adg_2 adg_22) {
        return qu_0.a(adg_22, ai_22, 0.0f);
    }

    public static boolean popup(ke ke2, ai_2 ai_22, adg_2 adg_22, String string) {
        return qu_0.a(adg_22, ai_22, Gr.d((Object)string, 0.0f));
    }

    public static boolean closePopup(ke ke2) {
        aam_1.aMF().b(afB);
        ago_2.getInstance().getPopupContainer().hide();
        return false;
    }

    public static boolean closePopup(ke ke2, ai_2 ai_22) {
        aam_1.aMF().b(afB);
        ago_2.getInstance().getPopupContainer().hide();
        return false;
    }

    public static boolean openClosePopup(ke ke2, ai_2 ai_22) {
        if (ago_2.getInstance().isDragging()) {
            return false;
        }
        na_1 na_12 = ke2.oF();
        if (!(na_12 instanceof adg_2)) {
            return false;
        }
        ai_2 ai_23 = ((adg_2)na_12).getPopup();
        if (ai_23 == null) {
            ai_23 = ai_22;
        }
        ai_23.a((adg_2)na_12);
        return false;
    }

    public static boolean toggleVisible(ke ke2, adg_2 adg_22) {
        return qu_0.toggleVisible(adg_22);
    }

    public static boolean toggleVisible(adg_2 adg_22) {
        if (adg_22 != null) {
            adg_22.setVisible(!adg_22.getVisible());
        }
        return false;
    }

    public static boolean setupLook(ke ke2, aab_2 aab_22, String string) {
        aab_22.setupLook(string);
        return false;
    }

    public static boolean foldUnfold(ke ke2) {
        aJS aJS2 = (aJS)ke2.oF().getParentOfType(aJS.class);
        if (aJS2 != null) {
            if (aJS2.isFolded()) {
                aJS2.aVv();
            } else {
                aJS2.aVu();
            }
        }
        return false;
    }

    public static boolean unfold(ke ke2) {
        aJS aJS2 = (aJS)ke2.oF().getParentOfType(aJS.class);
        if (aJS2 != null && aJS2.isFolded()) {
            aJS2.aVv();
        }
        return false;
    }

    public static boolean fold(ke ke2) {
        aJS aJS2 = (aJS)ke2.oF().getParentOfType(aJS.class);
        if (aJS2 != null && !aJS2.isFolded()) {
            aJS2.aVu();
        }
        return false;
    }
}

