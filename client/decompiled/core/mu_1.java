/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from mU
 */
public class mu_1
extends ed_1 {
    private static Logger a = Logger.getLogger(mu_1.class);
    public static final String LH = "config.properties";
    public static final String LI = "coachANMEquipmentPath";
    public static final String LJ = "fighterANMEquipmentPath";
    public static final String LK = "fighterANMSkinPath";
    public static final String LL = "playerGfxPath";
    public static final String LM = "npcGfxPath";
    public static final String LN = "ANMGUIPath";
    public static final String LO = "ANMIndexFile";
    public static final String LP = "ANMInteractiveElementPath";
    public static final String LQ = "gfxConfigFile";
    public static final String LR = "applicationSkinPath";
    public static final String LS = "i18nPath";
    public static final String LT = "dialogsPath";
    public static final String LU = "mapsTplgCoord";
    public static final String LV = "mapsGfxCoord";
    public static final String LW = "mapsGfxPath";
    public static final String LX = "mapsLightPath";
    public static final String LY = "mapsTopologyPath";
    public static final String LZ = "mapsEnvironmentPath";
    public static final String Ma = "mapsFightPath";
    public static final String Mb = "fullMapPath";
    public static final String Mc = "worldInfoFile";
    public static final String Md = "ambienceBankFile";
    public static final String Me = "contentInteractiveElementsTemplateFile";
    public static final String Mf = "gfxPath";
    public static final String Mg = "soundPath";
    public static final String Mh = "soundBank";
    public static final String Mi = "playListBankFile";
    public static final String Mj = "musicPath";
    public static final String Mk = "shadersPath";
    public static final String Ml = "videoPath";
    public static final String Mm = "particlePath";
    public static final String Mn = "scriptPath";
    public static final String Mo = "spellsIconsPath";
    public static final String Mp = "fightRuleIconsPath";
    public static final String Mq = "fightRuleTypesPath";
    public static final String Mr = "graphicalDiagnoticsResultIconsPath";
    public static final String Ms = "spellsIllustrationsPath";
    public static final String Mt = "eventsIconsPath";
    public static final String Mu = "eventsIllustrationsPath";
    public static final String Mv = "coachRankIconsPath";
    public static final String Mw = "coachEquipmentIconsPath";
    public static final String Mx = "coachEquipmentIllustrationsPath";
    public static final String My = "coachEquipmentTypeIconPath";
    public static final String Mz = "consumableTypeIconPath";
    public static final String MA = "fighterEquipmentIconsPath";
    public static final String MB = "fighterEquipmentIllustrationsPath";
    public static final String MC = "fighterEquipmentTypeIconPath";
    public static final String MD = "breedsTimelineIconPath";
    public static final String ME = "breedsBackgroundPath";
    public static final String MF = "tutorialPath";
    public static final String MG = "elementSmallIconsPath";
    public static final String MH = "challengeIllustrationsPath";
    public static final String MI = "tournamentIllustrationsPath";
    public static final String MJ = "toolIconsPath";
    public static final String MK = "achievementIconsPath";
    public static final String ML = "achievementKeyIconsPath";
    public static final String MM = "guildRankIconsPath";
    public static final String MN = "eventTypeIconsPath";
    public static final String MO = "infosTypeIconsPath";
    public static final String MP = "teamsIconsPath";
    public static final String MQ = "tokensIconsPath";
    public static final String MR = "teamsBackgroundsPath";
    public static final String MS = "savesPath";
    public static final String MT = "activateMapParticles";
    public static final String MU = "activateMapVisualEffect";
    public static final String MV = "contentStaticDataStorageDirectory";
    public static final String MW = "themeDirectory";
    public static final String MX = "themeFile";
    public static final String MY = "fightDefinitionsFile";
    public static final String MZ = "shortcutsFile";
    public static final String Na = "playlistFile";
    public static final String Nb = "statisticsReportsModelsFile";
    public static final String Nc = "elementsFile";
    public static final String Nd = "groupsFile";
    public static final String Ne = "highLightGfxFile";
    public static final String Nf = "highLightGfxDefaultFile";
    public static final String Ng = "startInOpenGLThread";
    public static final String Nh = "soundDevice";
    public static final String Ni = "soundEnable";
    public static final String Nj = "connectionRetryCount";
    public static final String Nk = "connectionRetryDelay";
    public static final String Nl = "lastProxyGroupIndex";
    public static final String Nm = "proxyGroup";
    public static final String Nn = "proxyAddresses";
    public static final String No = "bugReportURL";
    public static final String Np = "sphereBoardSpherePath";
    public static final String Nq = "sphereBoardPathPath";
    public static final String Nr = "sphereBoardTokenPath";
    private static mu_1 Ns = new mu_1();
    public boolean Nt = true;

    public static mu_1 rM() {
        return Ns;
    }

    public boolean rN() {
        try {
            return this.getBoolean(Ng);
        }
        catch (aih_2 aih_22) {
            return true;
        }
    }

    public void aa(boolean bl2) {
        this.setBoolean(Ng, bl2);
    }

    public String rO() {
        try {
            return this.getString(LT);
        }
        catch (aih_2 aih_22) {
            return "";
        }
    }

    public boolean rP() {
        return this.load("");
    }

    public boolean load(String string) {
        return super.load(string == null || string.length() == 0 ? LH : string);
    }

    public boolean rQ() {
        return super.save(LH);
    }

    public String a(String string, String string2, Object ... objectArray) {
        try {
            String string3 = String.format(this.getString(string), objectArray);
            if (!an_2.o(string3)) {
                a.warn((Object)("Impossible de trouver l'icone d'URL " + string3));
                string3 = this.getString(string2);
            }
            return string3;
        }
        catch (Exception exception) {
            return null;
        }
    }
}

