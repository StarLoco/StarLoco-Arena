/*
 * Decompiled with CFR 0.152.
 */
import java.util.GregorianCalendar;

public class aug {
    public static final String PACKAGE = "dofusarena.calendar";

    public static final void showNextMonth(ke ke2) {
        GregorianCalendar gregorianCalendar = de_2.Mc().Mf();
        gregorianCalendar.set(2, gregorianCalendar.get(2) + 1);
        azs_0.aLV().a((aho_0)de_2.Mc(), de_2.ce);
    }

    public static final void showPreviousMonth(ke ke2) {
        GregorianCalendar gregorianCalendar = de_2.Mc().Mf();
        gregorianCalendar.set(2, gregorianCalendar.get(2) - 1);
        azs_0.aLV().a((aho_0)de_2.Mc(), de_2.ce);
    }

    public static final void highlightEvent(aGJ aGJ2) {
        azs_0.aLV().g("itemOver", aGJ2.getItemValue());
    }

    public static final void unhighlightEvent(aGJ aGJ2) {
        azs_0.aLV().g("itemOver", (Object)null);
    }

    public static final void showFullEventList(ke ke2, aht_1 aht_12, aht_1 aht_13, ai_2 ai_22, avF avF2) {
        abz_2 abz_22 = ago_2.getInstance().getPopupContainer();
        if (!abz_22.getVisible()) {
            azs_0.aLV().g("calendar.fullEventList", avF2);
            qu_0.openClosePopup(ke2, ai_22);
        } else {
            azs_0.aLV().g("calendar.fullEventList", (Object)null);
            azs_0.aLV().g("itemSelected", (Object)null);
            aht_12.setVisible(true);
            aht_13.setVisible(false);
            ai_22.hide();
        }
    }

    public static void showCoachCardInfosEvolution(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object;
            ia_2 ia_22 = new ia_2();
            ia_22.b(wy_22);
            ia_22.f(16700);
            acu_1.ara().c(ia_22);
        }
    }

    public static void hideCoachCardInfosEvolution(aGJ aGJ2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16701);
        acu_1.ara().c(sb_02);
    }

    public static final void registerTournament(ke ke2, iz_0 iz_02) {
        if (iz_02 instanceof qr_0) {
            qr_0 qr_02 = (qr_0)iz_02;
            vg vg2 = vk_1.BZ().aQ(qr_02.fx());
            if (vg2 != null) {
                aub aub2 = LS.Yf().gG(vg2.Bw());
                int n2 = aub2.qo();
                if (n2 != 0) {
                    Object object;
                    int n3;
                    int n4 = 0;
                    ky_2 ky_22 = apN.aDK().Ln().yD();
                    wy_2 wy_22 = (wy_2)ky_22.pI().ac(Math.abs(n2));
                    if (wy_22 != null && ky_22.bU(Math.abs(n2))) {
                        n4 += wy_22.hG();
                        n3 = Math.abs(n2);
                    } else {
                        object = (wy_2)ky_22.pI().ac(-Math.abs(n2));
                        if (object != null) {
                            n4 += ((eb_1)object).hG();
                            n3 = -Math.abs(n2);
                        } else {
                            n3 = 0;
                        }
                    }
                    if (n4 > 0) {
                        object = add_1.aOG().a(aon_0.aYc().getString("registerToTournament", wy_22.getName()), 24L, 102, 0);
                        ((r_0)object).a(new ado_1(qr_02, n3, vg2));
                    } else {
                        object = aon_0.aYc().getString("cardNeededToRegister", wy_22.getName());
                        add_1.aOG().a((String)object, 2L, 102, 0);
                    }
                } else {
                    aik_0 aik_02 = new aik_0();
                    aik_02.ad(qr_02.fx());
                    aik_02.aj(apN.aDK().Ln().getId());
                    aik_02.C((short)-1);
                    apN.aDK().vJ().b(aik_02);
                    if (vg2.By().length > 0) {
                        r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("saveProfileQuestion"), 24L, 102, 0);
                        r_02.a(new adm_2(vg2));
                    }
                }
            }
        }
    }

    public static final void selectEventTypeFilter(vY vY2, String string) {
        tx_1 tx_12 = de_2.Mc().Mg();
        int n2 = Integer.valueOf(string);
        boolean bl2 = vY2.isSelected();
        if (tx_12.contains(n2) && !bl2) {
            tx_12.dH(n2);
        } else if (!tx_12.contains(n2) && bl2) {
            tx_12.dG(n2);
        }
        azs_0.aLV().a((aho_0)tx_12, tx_1.ce);
        azs_0.aLV().a((aho_0)de_2.Mc(), "calendar");
    }

    public static final void selectAllEventTypeFilter(ke ke2) {
        tx_1 tx_12 = de_2.Mc().Mg();
        if (tx_12.isFull()) {
            tx_12.zG();
        } else {
            tx_12.zF();
        }
        azs_0.aLV().a((aho_0)de_2.Mc(), "calendar");
        azs_0.aLV().a((aho_0)tx_12, tx_1.ce);
    }

    public static final void openTournamentDetailsDialog(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object instanceof qr_0) {
            qr_0 qr_02 = (qr_0)object;
            azs_0.aLV().g("selectedTournamentInformation", vk_1.BZ().aQ(qr_02.fx()));
            azs_0.aLV().g("calendar.selectedEvent", qr_02);
            add_1.aOG().a("calendarTournamentDetailsDialog", oh_2.bq("calendarTournamentDetailsDialog"), 256L, (short)10000);
        }
    }

    public static final void openTournamentDetailsDialogInFullList(aGJ aGJ2, aht_1 aht_12, aht_1 aht_13, ai_2 ai_22) {
        Object object = aGJ2.getItemValue();
        if (object instanceof qr_0) {
            qr_0 qr_02 = (qr_0)object;
            azs_0.aLV().g("selectedTournamentInformation", vk_1.BZ().aQ(qr_02.fx()));
            azs_0.aLV().g("calendar.selectedEvent", qr_02);
            add_1.aOG().a("calendarTournamentDetailsDialog", oh_2.bq("calendarTournamentDetailsDialog"), 256L, (short)10000);
            abz_2 abz_22 = ago_2.getInstance().getPopupContainer();
            if (abz_22.getVisible()) {
                azs_0.aLV().g("calendar.fullEventList", (Object)null);
                azs_0.aLV().g("itemSelected", (Object)null);
                aht_12.setVisible(true);
                aht_13.setVisible(false);
                ai_22.hide();
            }
        }
    }

    public static final void closeTournamentDetailsDialog(ke ke2) {
        azs_0.aLV().kb("selectedTournamentInformation");
        azs_0.aLV().kb("calendar.selectedEvent");
        add_1.aOG().kO("calendarTournamentDetailsDialog");
    }
}

