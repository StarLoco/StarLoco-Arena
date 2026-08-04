/*
 * Decompiled with CFR 0.152.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class avQ
extends aiv_1 {
    public static final String[] dfX;
    public static final String[] dfY;
    public static final String[] dfZ;
    public static final String[] dga;
    public static final String[] dgb;
    private static final Pattern dgc;
    public static final Pattern dgd;
    public static final Pattern dge;
    public static final String[] dgf;

    public static boolean jR(String string) {
        return string == null || string.length() == 0 || !dgc.matcher(aey_0.hJ(string)).matches();
    }

    public static boolean jS(String string) {
        return string == null || string.length() == 0 || !dge.matcher(aey_0.hJ(string)).find();
    }

    public static String jT(String string) {
        if (string == null || string.trim().length() == 0) {
            return "";
        }
        Matcher matcher = dge.matcher(aey_0.hJ(string));
        while (matcher.find()) {
            boolean bl2 = false;
            boolean bl3 = false;
            String string2 = matcher.group();
            if (string2.length() > 0) {
                if (string2.length() > 0 && string2.charAt(0) == ' ') {
                    bl2 = true;
                }
                if (string2.charAt(string2.length() - 1) == ' ') {
                    bl3 = true;
                }
            }
            char[] cArray = string.toCharArray();
            for (int j = matcher.start(); j < matcher.end(); ++j) {
                char c;
                if (j == matcher.start()) {
                    if (bl2) {
                        cArray[j] = 32;
                        continue;
                    }
                    cArray[j] = 123;
                    continue;
                }
                if (j == matcher.end() - 1) {
                    if (bl3) {
                        cArray[j] = 32;
                        continue;
                    }
                    cArray[j] = 125;
                    continue;
                }
                cArray[j] = j == matcher.start() + 1 && bl2 ? 123 : (j == matcher.end() - 2 && bl3 ? 125 : (c = dgf[(int)(Math.random() * (double)dgf.length)].charAt(0)));
            }
            string = String.valueOf(cArray);
            matcher = dge.matcher(aey_0.hJ(string));
        }
        return string;
    }

    static {
        int n2;
        dfX = new String[]{"KAM", "TOT", "BO", "MANU", "LICHEN", "YAMATO", "VENOM", "XYO", "BEK", "ADULINE", "TIX", "TANUKI", "KRALA", "GM", "MJ", "MOD", "MOD", "IOP", "CRA", "ENI", "ECA", "SRAM", "SADI", "SACRI", "OSA", "FECA", "ENU", "PYM", "YUL", "BUL", "WYD", "KIT", "KLOX", "JAIN", "KRAD", "SILAS", "SOLAR", "VLAD", "DOMEN", "SBAB", "GOULTARD", "RUEL", "LOUF", "OLAF", "OSH", "GLACIALENA", "TANGUYKUN", "TALENTYRE", "SEPHY", "ZOUZO", "SYLFAEN", "ERHUNE", "JEANDELANO", "JEAN-DE-LANO", "FULLCANELLI", "VLIIR", "THETURTLE", "ZEORUS", "MELUZE", "OMA", "OMMA", "MOON", "UNDEFINED", "LAKHA", "ZIDRUNE", "ESKARINA", "XAV", "AZ", "ARTY", "ISYNDRA", "MISOLO", "CREPUSCULE", "KEBORO", "TOFUKI", "LOKKI", "FLUKE", "QUELEX", "PIKHRAC", "GLUMI", "DALIKAEOR", "KNONAUT", "JENKOUH", "DECODEINE", "LYOTHIS", "ISSEHO"};
        dfY = new String[]{"PIOU-", "KAM-", "TOT-", "BO-", "MANU-", "LICHEN-", "YAMATO-", "VENOM-", "BEK-", "MODER", "MOD-", "MJ-", "GM-", "MD-", "TANUKI-", "MODO", "WYD", "KRAD", "RUEL", "STROUD", "NOX", "KRALA-", "GLACIALENA-", "TALENTYRE-", "SEPHY-", "ZOUZO-", "SYLFAEN-", "ERHUNE-", "JEANDELANO-", "FULLCANELLI-", "VLIIR-", "THETURTLE-", "ZEORUS-", "MELUZE-", "BONTA", "BRAKMAR", "ASTRUB", "EMELKA", "SUFOKIA", "WABBIT", "PANDALA", "GROBE"};
        dfZ = new String[]{"ADMIN", "ADMINISTRATEUR", "MODERATEUR", "WAKFU", "DOFUS", "DIEU", "MODERATION", "ENIRIPSA", "OSAMODAS", "SACRIEUR", "XELOR", "PANDAWA", "ECAFLIP", "ENUTROF", "SADIDA", "ELIATROPE", "ELYATROPE", "ARIMATH", "LIMERO", "MALYCORNE", "BUTOR", "MINUIT", "LETHALINE", "SIGISBUL", "OGREST", "DHATURA", "NUMBRUS", "KROKKER", "FULIDULI", "GRILEMBORE", "RYKKE", "TREPANEM", "RUTGER", "PEPITOX", "FISTULE", "RUSHU", "DJAUL", "SILVOSSE", "BRUMAIRE", "ULGRUDE", "JIVA", "POUCHECOT", "HECATE", "ROSAL", "SUMMENS", "RAVAL", "FALLANSTER", "AMAYIRO", "HYRKUL", "DARDONDAKAL", "ALLISTER", "HELIOBOROS", "OURONIGRIDE", "SPIRITETIA", "CROCOBURIO", "CROCABULIA", "GROUGALORASALAR", "CROCOBUTOR", "CROULAKLAKOSS", "BOLGROT", "HELSEPHINE", "AGUABRIAL", "IGNEMIKHAL", "TERRAKOURIAL", "AERAFAL", "MUSTAM", "KAFFRA", "RAGAA", "SATIREV", "JERHYN", "DANATHOR", "BISHOM", "LENALD", "OTOMAI", "AMALIA", "AMALIA SHERAN SHARM", "AMALYA", "TRISTEPIN", "TRISTEPAIN", "TRYSTEPIN", "STROUD", "RUEL STROUD", "EVANGELYNE", "EVANGELINE", "YUGO", "ADAMAI", "RUBILAX", "RUBYLAX", "VAMPYRO", "VAMPIRO", "GROUGALORAGRAN", "DATHURA", "COROPHORIBLE", "ALIBERT", "MIRANDA", "KABROK", "KRISS", "TOLOT", "CORBEAU", "KRALAMOUR", "SHUSHU", "OMBRAGE", "KANNIBOUL", "LEKTERR", "EMPOISONNEUSE", "COLLECTIONNEUR", "HERBACHA", "HONORAPE", "HERBEFOL", "ANERICE", "SHUSHESS", "GAZTON", "MORVEN", "SPASMOL", "ROLAC", "FRANKULA", "REQUIEM", "IMMORTALO", "MORBIDON", "MOMIE NOVA", "CERVELASSE", "USSE BONE", "OLISTER", "SMISSE", "NOCTURNO", "NARUTO", "SNUFFLE"};
        dga = new String[]{"KKK", "COUILLE", "VAGIN", "BATAR", "BATARD", "PUTIN", "PUTAIN", "SUCER", "SUCEUR", "SUCEUSE", "ANUS", "NEGRE", "JUIF", "ARABE", "MERDE", "FDP", "CROTTE", "ZIZI", "BRANL", "JOUIR", "NTM", "TAMERE", "ENCULE", "CHIOTTE", "CLITO", "ETRON", "GOUINE", "TURLUTTE", "CHIASSE", "LESBIENNE", "NIQUER", "SUCE", "LESBOS", "FUCK", "BITCH", "SUCK", "PENIS", "COCK", "NIGG", "CUNT", "FAG", "GAY", "SUCK", "PISS", "CUM", "ARSE", "BIATCH", "BOOB", "DICK", "DILDO", "QUEER", "FART", "WANKER", "GANGBANG", "GANG BANG", "LESBIAN", "SHIT", "POOP", "ARSCH", "BOLLERN", "BAKA", "CHIPATAMA", "HENTAI", "KUSO", "BALALAO", "CHUPAR", "ESPORRA", "QUECA", "ACABAR", "AGILIPOLLAO", "ALMEJA", "tageul", "enculer", "conard", "tagueule", "conar", "connard", "poufiasse", "pouffiasse", "pute", "suceur", "suceurs", "dard", "dare", "SALOP", "SALOPE", "NIQUE", "HOMO", "HOMOS", "SUCE", "CON", "CONNE", "CONS", "CONNES", "PD", "TG", "CHIER", "PEDE", "CUL", "PUTE", "SEXE", "SEX", "ENKULE", "PETASSE", "WTF", "STFU", "ASS"};
        dgb = new String[]{"POUFFIASSE", "CONNAR", "CONNASS", "ZOOPHIL", "POUFIASSE", "GROGNASSE", "HTTP", "WWW", "FTP", "\\.COM", "THXO", "IGHOT", "OURGAME", "OUR GAME", "HITLER", "NAZI", "KUKLUXKLAN", "KU KLUX KLAN", "ADOLF", "OUSSAMA", "BENLADEN", "BEN LADEN", "HUSSEIN", "SADDAM", "BOUGNOUL", "BOUKAK", "ALKAIDA"};
        dgf = new String[]{"&", "~", "#", "@", "\u00a3", "\u00a4", "\u00b5", "%", "!"};
        String string = "";
        for (int j = 0; j < dgb.length; ++j) {
            if (j > 0) {
                string = string + "|";
            }
            string = string + dgb[j];
        }
        String string2 = "(([\\p{L}]*(" + string + ")[\\p{L}]*)|(([^\\p{L}]|\\A)(";
        for (n2 = 0; n2 < dga.length; ++n2) {
            if (n2 > 0) {
                string2 = string2 + "|";
            }
            string2 = string2 + dga[n2];
        }
        string2 = string2 + ")([^\\p{L}]|\\z)))";
        string = ".*(" + string + ").*";
        dge = Pattern.compile(string2, 2);
        dgd = Pattern.compile(string, 2);
        for (n2 = 0; n2 < dfX.length; ++n2) {
            string = string + "|" + dfX[n2];
        }
        if (dfY.length > 0) {
            string = string + "|(";
            for (n2 = 0; n2 < dfY.length; ++n2) {
                if (n2 > 0) {
                    string = string + "|";
                }
                string = string + dfY[n2];
            }
            string = string + ").*";
        }
        if (dfZ.length > 0) {
            string = string + "|.*(";
            for (n2 = 0; n2 < dfZ.length; ++n2) {
                if (n2 > 0) {
                    string = string + "|";
                }
                string = string + dfZ[n2];
            }
            for (n2 = 0; n2 < dgb.length; ++n2) {
                string = string + "|";
                string = string + dgb[n2];
            }
            string = string + ").*";
        }
        if (dga.length > 0) {
            string = string + "|(.*[ '-])*(";
            for (n2 = 0; n2 < dga.length; ++n2) {
                if (n2 > 0) {
                    string = string + "|";
                }
                string = string + dga[n2];
            }
            string = string + ")([ '-].*)*";
        }
        dgc = Pattern.compile(string, 2);
    }
}

