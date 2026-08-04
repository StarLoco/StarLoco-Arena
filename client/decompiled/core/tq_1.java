/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from TQ
 */
public class tq_1 {
    public static final String PACKAGE = "dofusarena.cardMaster";
    private static int boJ;
    private static wy_2 boK;
    private static int boL;

    public static final void closeCardMasterDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16435);
        acu_1.ara().c(sb_02);
    }

    public static final void closeDemonIIDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16435);
        acu_1.ara().c(sb_02);
    }

    public static final void selectCardToBuy(aGJ aGJ2, aht_1 aht_12, aht_1 aht_13) {
        aJd aJd2 = (aJd)azs_0.aLV().getProperty("cardMasterTrade").getValue();
        nn_0 nn_02 = (nn_0)aGJ2.getItemValue();
        aJd2.b(nn_02);
        azs_0.aLV().a((aho_0)aJd2, "cardMasterCardsPrice");
        azs_0.aLV().a((aho_0)aJd2, "canBuyCards");
        aht_12.setVisible(false);
        aht_13.setVisible(true);
    }

    public static final void selectCardToBuy(aGJ aGJ2) {
        aJd aJd2 = (aJd)azs_0.aLV().getProperty("cardMasterTrade").getValue();
        nn_0 nn_02 = (nn_0)aGJ2.getItemValue();
        aJd2.b(nn_02);
        sb_0 sb_02 = new sb_0();
        sb_02.f(16903);
        acu_1.ara().c(sb_02);
    }

    public static final void buyDemonIICard(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16905);
        acu_1.ara().c(sb_02);
    }

    public static final void removeCard(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof wy_2) {
            ey_2 ey_22 = new ey_2();
            ey_22.f(16901);
            ey_22.a((wy_2)object);
            acu_1.ara().c(ey_22);
        }
    }

    public static final void dropCard(aiU aiU2) {
        Object object = aiU2.getValue();
        if (object != null && object instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object;
            if (!((xj)wy_22.NR()).tq()) {
                String string = aiU2.ayA().getId();
                if (string != null && !string.equals("localCoachCardsContainer")) {
                    ky_2 ky_22 = apN.aDK().Ln().yD();
                    wy_2 wy_23 = (wy_2)ky_22.bW(-wy_22.jf());
                    wy_2 wy_24 = (wy_2)ky_22.bW(wy_22.jf());
                    boJ = (wy_24 != null ? wy_24.hG() : (short)0) + (wy_23 != null ? wy_23.hG() : (short)0);
                    boK = wy_22;
                    boL = 16900;
                    ki_0 ki_02 = new ki_0();
                    ki_02.a(wy_22);
                    ki_02.aF((short)aiU2.getScreenX());
                    ki_02.aG((short)aiU2.getScreenY());
                    ki_02.f(16820);
                    acu_1.ara().c(ki_02);
                }
            } else {
                add_1.aOG().a(aon_0.aYc().getString("coachInventory.undestructibleCard"), 1058L, 102, 1);
            }
        }
    }

    public static final void dragCard(aly_2 aly_22) {
        Object object = aly_22.getValue();
        if (object != null && object instanceof wy_2) {
            ey_2 ey_22 = new ey_2();
            ey_22.f(16901);
            ey_22.a((wy_2)object);
            ey_22.q(((wy_2)object).hG());
            acu_1.ara().c(ey_22);
        }
    }

    public static void increaseSplitCount(ke ke2) {
        afl_0 afl_02 = azs_0.aLV().getProperty("itemToSplit");
        int n2 = afl_02.getInt();
        if (++n2 > boJ) {
            n2 = boJ;
        }
        azs_0.aLV().g("itemToSplit", n2);
    }

    public static void decreaseSplitCount(ke ke2) {
        afl_0 afl_02 = azs_0.aLV().getProperty("itemToSplit");
        int n2 = afl_02.getInt();
        if (--n2 < 0) {
            n2 = 0;
        }
        azs_0.aLV().g("itemToSplit", n2);
    }

    public static void keyType(aqG aqG2, UV uV) {
        if (uV.getText().length() == 0) {
            return;
        }
        if (aqG2.getKeyChar() == '\n') {
            tq_1.applyQuantity(aqG2);
            return;
        }
        int n2 = Gr.R(uV.getText());
        if (n2 > boJ) {
            n2 = boJ;
            azs_0.aLV().getProperty("itemToSplit").avr();
        }
        azs_0.aLV().g("itemToSplit", n2);
    }

    public static void applyQuantity(ke ke2) {
        add_1.aOG().kO("splitCardMasterTradeDialog");
        short s = azs_0.aLV().getProperty("itemToSplit").getShort();
        if (s > 0) {
            ey_2 ey_22 = new ey_2();
            ey_22.q(s);
            ey_22.a(boK);
            ey_22.f(boL);
            acu_1.ara().c(ey_22);
        }
    }

    public static void buyCards(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16902);
        acu_1.ara().c(sb_02);
    }

    public static void chooseAnotherCard(ke ke2, aht_1 aht_12, aht_1 aht_13) {
        aht_12.setVisible(true);
        aht_13.setVisible(false);
    }

    public static void openCloseCardPurchaseConfirmationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16904);
        acu_1.ara().c(sb_02);
    }

    public static void showTokenPopup(aGJ aGJ2, ai_2 ai_22) {
        azs_0.aLV().g("tokenValue", ((xj)((nn_0)aGJ2.getItemValue()).NR()).DG().toArray());
        qu_0.popup(aGJ2, ai_22);
    }

    public static void hideTokenPopup(aGJ aGJ2, ai_2 ai_22) {
        azs_0.aLV().kb("tokenValue");
        qu_0.closePopup(aGJ2, ai_22);
    }
}

