/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.Ostermiller.util.Browser
 *  org.apache.log4j.Logger
 */
import com.Ostermiller.util.Browser;
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import com.ankamagames.dofusarena.client.console.command.common.AlphaMaskCommand;
import com.ankamagames.dofusarena.client.console.command.fight.ShowGridCommand;
import java.io.IOException;
import org.apache.log4j.Logger;

/*
 * Renamed from avv
 */
public class avv_0 {
    private static final Logger a = Logger.getLogger(avv_0.class);
    public static final String PACKAGE = "dofusarena";

    public static void disconnect(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16386);
        acu_1.ara().c(sb_02);
    }

    public static void destroyCoach(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16388);
        acu_1.ara().c(sb_02);
    }

    public static void quit(ke ke2) {
        apN.aDK().quit();
    }

    public static void setLanguage(ke ke2, String string) {
        yt_2 yt_22 = new yt_2();
        yt_22.a(aie_0.ly(string));
        acu_1.ara().c(yt_22);
    }

    public static void setFullScreen(vY vY2) {
    }

    public static void setSaveReplay(ke ke2) {
        DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.clN, !DofusArenaClientInstance.yl().aod().a(adc_0.clN));
    }

    public static void activateParticles(vY vY2) {
        DofusArenaClientInstance.yl().aod().bp(vY2.isSelected());
    }

    public static void activateVSync(ke ke2) {
        boolean bl2 = !DofusArenaClientInstance.yl().aod().a(adc_0.clT);
        DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.clT, bl2);
        bx_2 bx_22 = DofusArenaClientInstance.yl().kW();
        bx_22.j(bl2);
    }

    public static void activateShaders(ke ke2) {
        boolean bl2 = !DofusArenaClientInstance.yl().aod().a(adc_0.clQ);
        DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.clQ, bl2);
        ahA.axi().dx(bl2);
    }

    public static void setMaskWorld(ke ke2) {
        boolean bl2 = !DofusArenaClientInstance.yl().aod().a(adc_0.clS);
        DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.clS, bl2);
        AlphaMaskCommand.y(bl2);
        AlphaMaskCommand.z(bl2);
    }

    public static void setGridActivated(ke ke2) {
        boolean bl2 = !DofusArenaClientInstance.yl().aod().a(adc_0.clU);
        DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.clU, bl2);
        ShowGridCommand.aI(bl2);
    }

    public static void setInverseMouseControl(ke ke2) {
        boolean bl2 = !DofusArenaClientInstance.yl().aod().a(adc_0.clW);
        DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.clW, bl2);
    }

    public static void setShowFighterMoveRange(ke ke2) {
        boolean bl2 = !DofusArenaClientInstance.yl().aod().a(adc_0.clZ);
        DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.clZ, bl2);
    }

    public static void setMusicVolume(ke ke2) {
        if (ke2 != null && ke2 instanceof Kf) {
            float f = ((Kf)ke2).getValue();
            DofusArenaClientInstance.yl().aod().a((ro_2)akz_1.cEw, f);
            nk_0.aaq().ah(f);
        }
    }

    public static void setSoundsVolume(ke ke2) {
        if (ke2 != null && ke2 instanceof Kf) {
            float f = ((Kf)ke2).getValue();
            DofusArenaClientInstance.yl().aod().a((ro_2)akz_1.cEx, f);
            nk_0.aaq().ai(f);
        }
    }

    public static void setMusicMute(vY vY2) {
        DofusArenaClientInstance.yl().aod().a((ro_2)akz_1.cEz, vY2.isSelected());
        nk_0.aaq().bU(vY2.isSelected());
    }

    public static void setSoundsMute(vY vY2) {
        DofusArenaClientInstance.yl().aod().a((ro_2)akz_1.cEA, vY2.isSelected());
        nk_0.aaq().bV(vY2.isSelected());
    }

    public static boolean validateLoginForm(Ur ur) {
        ur.agN();
        return !ur.getProperty("account.name").isEmpty() && !ur.getProperty("account.password").isEmpty();
    }

    public static void logon(ke ke2, Ur ur) {
        if (ke2 instanceof aqG && ((aqG)ke2).getKeyCode() != 10) {
            return;
        }
        if (ur.isValid()) {
            NM nM = (NM)ur.getProperty("proxy.selected").getValue();
            String string = ur.getProperty("account.name").getString();
            String string2 = ur.getProperty("account.password").getString();
            boolean bl2 = ur.getProperty("account.remember").getBoolean();
            if (string != null && string2 != null && 0 < string.length() && 0 < string2.length() && string.length() <= 19 && string2.length() <= 49) {
                go_1 go_12 = go_1.RY();
                go_12.a(nM);
                go_12.aQ(string);
                go_12.setPassword(string2);
                go_12.a(bl2);
                acu_1.ara().c(go_12);
            } else {
                add_1.aOG().a(aon_0.aYc().getString("error.connection.invalidLogin"), 1091L, 5, 2);
            }
        } else {
            System.out.println("Formulaire invalide");
        }
    }

    public static void closeNotEnoughMembersDialog(ke ke2) {
        add_1.aOG().kO("notEnoughMembersDialog");
    }

    public static void openCloseGuildCreationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16431);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseChallengeDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16439);
        acu_1.ara().c(sb_02);
    }

    public static void closeGuildCreationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16432);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseGuildDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16429);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseSocialDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16437);
        acu_1.ara().c(sb_02);
    }

    public static void closeSocialDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16438);
        acu_1.ara().c(sb_02);
    }

    public static void closeGuildDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16430);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20018);
        acu_1.ara().c(sb_02);
    }

    public static void closeLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20019);
        acu_1.ara().c(sb_02);
    }

    public static void closeDemonLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20132);
        acu_1.ara().c(sb_02);
    }

    public static void forwardFiveLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20020);
        acu_1.ara().c(sb_02);
    }

    public static void forwardTenLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20021);
        acu_1.ara().c(sb_02);
    }

    public static void forwardThirtyLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20022);
        acu_1.ara().c(sb_02);
    }

    public static void forwardOneHundredLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20023);
        acu_1.ara().c(sb_02);
    }

    public static void backwardFiveLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20024);
        acu_1.ara().c(sb_02);
    }

    public static void backwardTenLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20025);
        acu_1.ara().c(sb_02);
    }

    public static void backwardThirtyLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20026);
        acu_1.ara().c(sb_02);
    }

    public static void firstPlayerLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20028);
        acu_1.ara().c(sb_02);
    }

    public static void lastPlayerLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20029);
        acu_1.ara().c(sb_02);
    }

    public static void coachSearchLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20030);
        acu_1.ara().c(sb_02);
    }

    public static void backwardOneHundredLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20027);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseMapDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20031);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseLevelUpDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20000);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseCalendarDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20032);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseTotemTournamentDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20070);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseCoachStatisticsDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20011);
        acu_1.ara().c(sb_02);
    }

    public static void createWebAvatar(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20108);
        acu_1.ara().c(sb_02);
    }

    public static void closeCoachStatisticsDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20012);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseOptionsDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20014);
        acu_1.ara().c(sb_02);
    }

    public static void closeOptionsDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20015);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseCoachInventoryDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20006);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseZaapDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20120);
        acu_1.ara().c(sb_02);
    }

    public static void closeCoachInventoryDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20007);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseTeamManagementDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20004);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseEvolutionDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(23050);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseTournamentEvolutionDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(23067);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseGraveyardDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(23066);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseEvolutionTeamManagementTutoDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(23061);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseEvolutionTeamManagementTutoTwoDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(23065);
        acu_1.ara().c(sb_02);
    }

    public static void closeTeamManagementDialog(ke ke2) {
        if (!apN.aDK().c(B.V())) {
            sb_0 sb_02 = new sb_0();
            sb_02.f(20017);
            acu_1.ara().c(sb_02);
        } else {
            agY agY2 = new agY();
            long l2 = azs_0.aLV().getProperty("fight.id").getLong();
            agY2.cK(l2);
            agY2.f(16601);
            acu_1.ara().c(agY2);
        }
    }

    public static void openCloseMenuDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20008);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseFightMenuDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20010);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseTooltipDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(21000);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseTooltipTutoDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(21001);
        acu_1.ara().c(sb_02);
    }

    public static void closeMenuDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20009);
        acu_1.ara().c(sb_02);
    }

    public static void openBugReportDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16427);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseMailboxDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16436);
        acu_1.ara().c(sb_02);
    }

    public static void closeFightResultDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20005);
        acu_1.ara().c(sb_02);
    }

    public static void closePrivateMessageDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(19004);
        acu_1.ara().c(sb_02);
    }

    private static void a(String string, aag_2 aag_22) {
        try {
            aji_1 aji_12 = add_1.aOG().azj().lh(string);
            if (aji_12 == null) {
                return;
            }
            aht_1 aht_12 = (aht_1)aji_12.R(string);
            if (aht_12 != null) {
                int n2 = Math.max(0, Math.min(aht_12.getX(), aag_22.getScreenWidth() - aht_12.getWidth()));
                int n3 = Math.max(0, Math.min(aht_12.getY(), aag_22.getScreenHeight() - aht_12.getHeight()));
                aht_12.setPosition(n2, n3);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void applyResolution(ke ke2, Ur ur) {
    }

    public static void playEmote(aGJ aGJ2) {
        wy_2 wy_22;
        Object object = aGJ2.getItemValue();
        if (object instanceof wy_2 && (wy_22 = (wy_2)object).tj() == aMK.dYb) {
            sj_1 sj_12 = apN.aDK().Ln();
            up_0 up_02 = up_0.dP(Math.abs(wy_22.jf()));
            if (up_02 == null) {
                a.error((Object)("pas d'emote pour id=" + wy_22.jf()));
                return;
            }
            amg_1 amg_12 = sj_12.Ov();
            if (amg_12.L().acM()) {
                amg_12.b(qc_0.hf((amg_12.L().getIndex() + 1) % 8));
            }
            JY jY = new JY();
            jY.eW(up_02.AU());
            jY.gy(wy_22.jf());
            apN.aDK().vJ().b(jY);
        }
    }

    public static boolean validateEmoteDrop(kn_1 kn_12, Object object, kn_1 kn_13, Object object2, Object object3, sj_1 sj_12) {
        if (object3 != null && object3 instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object3;
            if (sj_12 != null) {
                return wy_22.tj() == aMK.dYb;
            }
        }
        return true;
    }

    public static void firstGuildLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20045);
        acu_1.ara().c(sb_02);
    }

    public static void backwardThirtyGuildLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20044);
        acu_1.ara().c(sb_02);
    }

    public static void backwardTenGuildLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20043);
        acu_1.ara().c(sb_02);
    }

    public static void forwardTenGuildLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20041);
        acu_1.ara().c(sb_02);
    }

    public static void forwardThirtyGuildLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20042);
        acu_1.ara().c(sb_02);
    }

    public static void lastGuildLadderInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20046);
        acu_1.ara().c(sb_02);
    }

    public static void forwardTenLadderTwoVsTwoInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20051);
        acu_1.ara().c(sb_02);
    }

    public static void forwardThirtyLadderTwoVsTwoInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20052);
        acu_1.ara().c(sb_02);
    }

    public static void forwardOneHundredLadderTwoVsTwoInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20053);
        acu_1.ara().c(sb_02);
    }

    public static void backwardTenLadderTwoVsTwoInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20054);
        acu_1.ara().c(sb_02);
    }

    public static void backwardThirtyLadderTwoVsTwoInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20055);
        acu_1.ara().c(sb_02);
    }

    public static void backwardOneHundredLadderTwoVsTwoInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20056);
        acu_1.ara().c(sb_02);
    }

    public static void firstTeamLadderTwoVsTwoInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20057);
        acu_1.ara().c(sb_02);
    }

    public static void lastTeamLadderTwoVsTwoInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20058);
        acu_1.ara().c(sb_02);
    }

    public static void bestTeamSearchLadderTwoVsTwoInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20059);
        acu_1.ara().c(sb_02);
    }

    public static void forwardTenLadderTournanentInTheMonthInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20082);
        acu_1.ara().c(sb_02);
    }

    public static void backwardTenLadderTournanentInTheMonthInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20083);
        acu_1.ara().c(sb_02);
    }

    public static void coachSearchLadderTournanentInTheMonthInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20090);
        acu_1.ara().c(sb_02);
    }

    public static void forwardTenLadderTournanentInTheTrimesterInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20086);
        acu_1.ara().c(sb_02);
    }

    public static void backwardTenLadderTournanentInTheTrimesterInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20087);
        acu_1.ara().c(sb_02);
    }

    public static void coachSearchLadderTournanentInTheTrimesterInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20091);
        acu_1.ara().c(sb_02);
    }

    public static void forwardTenLadderTournanentInTheYearInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20088);
        acu_1.ara().c(sb_02);
    }

    public static void backwardTenLadderTournanentInTheYearInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20089);
        acu_1.ara().c(sb_02);
    }

    public static void coachSearchLadderTournanentInTheYearInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20092);
        acu_1.ara().c(sb_02);
    }

    public static void forwardOneLadderTournanentMonthInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20093);
        acu_1.ara().c(sb_02);
    }

    public static void backwardOneLadderTournanentMonthInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20094);
        acu_1.ara().c(sb_02);
    }

    public static void forwardOneLadderTournanentTrimesterInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20095);
        acu_1.ara().c(sb_02);
    }

    public static void backwardOneLadderTournanentTrimesterInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20096);
        acu_1.ara().c(sb_02);
    }

    public static void forwardOneLadderTournanentYearInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20097);
        acu_1.ara().c(sb_02);
    }

    public static void backwardOneLadderTournanentYearInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20098);
        acu_1.ara().c(sb_02);
    }

    public static void forwardTenLadderReputationInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20101);
        acu_1.ara().c(sb_02);
    }

    public static void forwardOneHundredLadderReputationInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20102);
        acu_1.ara().c(sb_02);
    }

    public static void backwardTenLadderReputationInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20103);
        acu_1.ara().c(sb_02);
    }

    public static void backwardOneHundredLadderReputationInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20104);
        acu_1.ara().c(sb_02);
    }

    public static void firstTeamLadderReputationInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20105);
        acu_1.ara().c(sb_02);
    }

    public static void lastTeamLadderReputationInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20106);
        acu_1.ara().c(sb_02);
    }

    public static void teamSearchLadderReputationInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20107);
        acu_1.ara().c(sb_02);
    }

    public static void firstLadderGuildDemonInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20135);
        acu_1.ara().c(sb_02);
    }

    public static void backwardTenLadderGuildDemonInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20134);
        acu_1.ara().c(sb_02);
    }

    public static void forwardTenLadderGuildDemonInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20133);
        acu_1.ara().c(sb_02);
    }

    public static void lastLadderGuildDemonInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20136);
        acu_1.ara().c(sb_02);
    }

    public static void firstLadderDemonInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20143);
        acu_1.ara().c(sb_02);
    }

    public static void backwardTwelveLadderDemonInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20141);
        acu_1.ara().c(sb_02);
    }

    public static void forwardTwelveLadderDemonInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20142);
        acu_1.ara().c(sb_02);
    }

    public static void lastLadderDemonInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20144);
        acu_1.ara().c(sb_02);
    }

    public static void previousProLeaguedefinitionNameLadderGlickoRatingInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20181);
        acu_1.ara().c(sb_02);
    }

    public static void nextProLeaguedefinitionNameLadderGlickoRatingInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20182);
        acu_1.ara().c(sb_02);
    }

    public static void forwardTenLadderGlickoRatingInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20183);
        acu_1.ara().c(sb_02);
    }

    public static void forwardOneHundredLadderGlickoRatingInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20184);
        acu_1.ara().c(sb_02);
    }

    public static void backwardTenLadderGlickoRatingInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20185);
        acu_1.ara().c(sb_02);
    }

    public static void backwardOneHundredLadderGlickoRatingInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20186);
        acu_1.ara().c(sb_02);
    }

    public static void firstLadderGlickoRatingInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20187);
        acu_1.ara().c(sb_02);
    }

    public static void lastLadderGlickoRatingInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20188);
        acu_1.ara().c(sb_02);
    }

    public static void coachSearchLadderGlickoRatingInformationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20189);
        acu_1.ara().c(sb_02);
    }

    public static void launchChallenge(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16441);
        acu_1.ara().c(sb_02);
    }

    public static void selectChallenge(aGJ aGJ2) {
        afz_0 afz_02 = (afz_0)aGJ2.getItemValue();
        if (afz_02 != null) {
            sb_0 sb_02 = new sb_0();
            sb_02.e(afz_02.getId());
            sb_02.f(16440);
            acu_1.ara().c(sb_02);
        }
    }

    public static void useToolRequest(aGJ aGJ2) {
        aif_2 aif_22 = (aif_2)aGJ2.getItemValue();
        if (aif_22 != null) {
            aqe_0 aqe_02 = new aqe_0();
            aqe_02.a(aif_22);
            acu_1.ara().c(aqe_02);
        }
    }

    public static void getTournamentList(ke ke2) {
        apN.aDK().vJ().b(new wa_2());
    }

    public static void showToolsInMenuBar(ke ke2, String string) {
        azs_0.aLV().g("showToolsInMenuBar", string);
    }

    public static void openCloseAchievementDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(22050);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseDemonAffiliationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20150);
        acu_1.ara().c(sb_02);
    }

    public static void skipTutorial(ke ke2) {
        r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("questionSkipTutorial"), 24L, 102, 0);
        r_02.a(new and_2());
    }

    public static void showUrl(ke ke2, String string) {
        String string2 = aon_0.aYc().Fd().name().toLowerCase();
        try {
            if (string.equals("kard")) {
                Browser.displayURL((String)("http://www.dofus-arena.com/" + string2 + "/" + "kards-boufteurs"));
            } else if (string.equals("gift")) {
                Browser.displayURL((String)("http://www.dofus-arena.com/" + string2 + "/" + "espace-cadeaux"));
            }
        }
        catch (IOException iOException) {
            a.error((Object)"Probl\u00e8me lors du chargement de la page d'aide en ligne");
        }
    }

    public static void openCloseNPCTalkDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(17000);
        acu_1.ara().c(sb_02);
    }

    public static void showEvolutionLevel(ke ke2, String string) {
        DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.clY, Integer.parseInt(string));
        azs_0.aLV().a((aho_0)apN.aDK().Ln(), "level");
    }

    static {
        Browser.init();
    }
}

