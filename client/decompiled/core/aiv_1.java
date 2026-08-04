/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/*
 * Renamed from aIV
 */
public class aiv_1 {
    private static final Logger a = Logger.getLogger(aiv_1.class);
    private static aiv_1 dQP = new aiv_1();
    private static short dQQ;
    private static final lb_0 dQR;
    private static boolean dQS;
    private static Pattern dgc;
    private static Pattern dQT;
    private static Pattern dQU;
    public static final String[] dgf;
    public static final String[] dfX;
    public static final String[] dfY;
    public static final String[] dfZ;
    public static final String[] dga;
    public static final String[] dgb;

    public static aiv_1 aVl() {
        return dQP;
    }

    public void a(mf_2 mf_22) {
        int n2 = mf_22.qS();
        ArrayList<mf_2> arrayList = (ArrayList<mf_2>)dQR.get(n2);
        if (arrayList == null) {
            arrayList = new ArrayList<mf_2>();
        }
        arrayList.add(mf_22);
        dQR.c(n2, arrayList);
    }

    public static void cu(short s) {
        boolean bl2 = s != dQQ;
        dQQ = s;
        if (!dQS && bl2) {
            aiv_1.init();
        }
    }

    public static void init() {
        Object object;
        Object object2;
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        ArrayList<String> arrayList3 = new ArrayList<String>();
        ArrayList<String> arrayList4 = new ArrayList<String>();
        ArrayList<String> arrayList5 = new ArrayList<String>();
        ArrayList<String> arrayList6 = new ArrayList<String>();
        ArrayList arrayList7 = (ArrayList)dQR.get(akn_2.dTY.tI());
        if (arrayList7 != null) {
            object2 = arrayList7.iterator();
            while (object2.hasNext()) {
                object = (mf_2)object2.next();
                switch (abq_0.cj((short)((mf_2)object).qT())) {
                    case drl: {
                        if (((mf_2)object).qU()) {
                            arrayList5.add(((mf_2)object).getText());
                            break;
                        }
                        arrayList6.add(((mf_2)object).getText());
                        break;
                    }
                    case drm: {
                        if (((mf_2)object).qU()) {
                            arrayList4.add(((mf_2)object).getText());
                            break;
                        }
                        arrayList3.add(((mf_2)object).getText());
                        break;
                    }
                    case drk: {
                        if (((mf_2)object).qU()) {
                            arrayList.add(((mf_2)object).getText());
                            break;
                        }
                        arrayList2.add(((mf_2)object).getText());
                    }
                }
            }
        }
        if ((object2 = (ArrayList)dQR.get(dQQ)) != null) {
            object = ((ArrayList)object2).iterator();
            while (object.hasNext()) {
                mf_2 mf_22 = (mf_2)object.next();
                switch (abq_0.cj((short)mf_22.qT())) {
                    case drl: {
                        if (mf_22.qU()) {
                            arrayList5.add(mf_22.getText());
                            break;
                        }
                        arrayList6.add(mf_22.getText());
                        break;
                    }
                    case drm: {
                        if (mf_22.qU()) {
                            arrayList4.add(mf_22.getText());
                            break;
                        }
                        arrayList3.add(mf_22.getText());
                        break;
                    }
                    case drk: {
                        if (mf_22.qU()) {
                            arrayList.add(mf_22.getText());
                            break;
                        }
                        arrayList2.add(mf_22.getText());
                    }
                }
            }
        }
        dQU = Pattern.compile(aiv_1.d(arrayList5, arrayList6), 2);
        dQT = Pattern.compile(aiv_1.a(dQU.pattern(), arrayList, arrayList2), 2);
        dgc = Pattern.compile(aiv_1.a(dQT.pattern(), arrayList4, arrayList3), 2);
        dQS = false;
    }

    private static String d(ArrayList arrayList, ArrayList arrayList2) {
        return aiv_1.a(null, arrayList, arrayList2);
    }

    private static String a(String string, ArrayList arrayList, ArrayList arrayList2) {
        int n2;
        String string2 = "";
        String string3 = "";
        if (arrayList.size() > 0) {
            for (n2 = 0; n2 < arrayList.size(); ++n2) {
                if (n2 > 0) {
                    string2 = string2 + "|";
                }
                string2 = string2 + (String)arrayList.get(n2);
            }
        }
        if (arrayList2.size() > 0) {
            string3 = "(";
            if (string2.length() > 0) {
                string3 = string3 + "([\\p{L}]*(" + string2 + ")[\\p{L}]*)|";
            }
            string3 = string3 + "(([^\\p{L}]|\\A)(";
            for (n2 = 0; n2 < arrayList2.size(); ++n2) {
                if (n2 > 0) {
                    string3 = string3 + "|";
                }
                string3 = string3 + (String)arrayList2.get(n2);
            }
            string3 = string3 + ")([^\\p{L}]|\\z)))";
        }
        if (string != null && string.length() > 0) {
            string3 = string + "|" + string3;
        }
        return string3;
    }

    public static boolean jR(String string) {
        return string == null || string.length() == 0 || !dgc.matcher(aey_0.hJ(string)).matches();
    }

    public static boolean jS(String string) {
        return string == null || string.length() == 0 || !dQT.matcher(aey_0.hJ(string)).find();
    }

    protected static String A(String string, boolean bl2) {
        Pattern pattern;
        if (string == null || string.trim().length() == 0) {
            return "";
        }
        Pattern pattern2 = pattern = bl2 ? dQT : dQU;
        if (pattern.pattern().length() == 0) {
            a.error((Object)("[TRANSLATION] No censor pattern matched for language " + akn_2.cw(dQQ).name()));
            return string;
        }
        Matcher matcher = pattern.matcher(aey_0.hJ(string));
        while (matcher.find()) {
            boolean bl3 = false;
            boolean bl4 = false;
            String string2 = matcher.group();
            if (string2.length() > 0) {
                if (string2.length() > 0 && string2.charAt(0) == ' ') {
                    bl3 = true;
                }
                if (string2.charAt(string2.length() - 1) == ' ') {
                    bl4 = true;
                }
            }
            char[] cArray = string.toCharArray();
            for (int j = matcher.start(); j < matcher.end(); ++j) {
                char c;
                if (j == matcher.start()) {
                    if (bl3) {
                        cArray[j] = 32;
                        continue;
                    }
                    cArray[j] = 123;
                    continue;
                }
                if (j == matcher.end() - 1) {
                    if (bl4) {
                        cArray[j] = 32;
                        continue;
                    }
                    cArray[j] = 125;
                    continue;
                }
                cArray[j] = j == matcher.start() + 1 && bl3 ? 123 : (j == matcher.end() - 2 && bl4 ? 125 : (c = dgf[(int)(Math.random() * (double)dgf.length)].charAt(0)));
            }
            string = String.valueOf(cArray);
            matcher = dQT.matcher(aey_0.hJ(string));
        }
        return string;
    }

    static {
        dQR = new lb_0();
        dQS = true;
        dgf = new String[]{"&", "~", "#", "@", "\u00a3", "\u00a4", "\u00b5", "%", "!"};
        dfX = new String[]{"KAM", "TOT", "BO", "MANU", "LICHEN", "YAMATO", "VENOM", "XYO", "BEK", "ADULINE", "TIX", "TANUKI", "KRALA", "GM", "MJ", "MOD", "MOD", "IOP", "CRA", "ENI", "ECA", "SRAM", "SADI", "SACRI", "OSA", "FECA", "ENU", "PYM", "YUL", "BUL", "WYD", "KIT", "KLOX", "JAIN", "KRAD", "SILAS", "SOLAR", "VLAD", "DOMEN", "SBAB", "GOULTARD", "RUEL", "LOUF", "OLAF", "OSH", "GLACIALENA", "TANGUYKUN", "TALENTYRE", "SEPHY", "ZOUZO", "SYLFAEN", "ERHUNE", "JEANDELANO", "JEAN-DE-LANO", "FULLCANELLI", "VLIIR", "THETURTLE", "ZEORUS", "MELUZE", "OMA", "OMMA", "MOON", "UNDEFINED", "LAKHA", "ZIDRUNE", "ESKARINA", "XAV", "AZ", "ARTY", "ISYNDRA", "MISOLO", "CREPUSCULE", "KEBORO", "TOFUKI", "LOKKI", "FLUKE", "QUELEX", "PIKHRAC", "GLUMI", "DALIKAEOR", "KNONAUT", "JENKOUH", "DECODEINE", "LYOTHIS", "ISSEHO"};
        dfY = new String[]{"PIOU-", "KAM-", "TOT-", "BO-", "MANU-", "LICHEN-", "YAMATO-", "VENOM-", "BEK-", "MODER", "MOD-", "MJ-", "GM-", "MD-", "TANUKI-", "MODO", "WYD", "KRAD", "RUEL", "STROUD", "NOX", "KRALA-", "GLACIALENA-", "TALENTYRE-", "SEPHY-", "ZOUZO-", "SYLFAEN-", "ERHUNE-", "JEANDELANO-", "FULLCANELLI-", "VLIIR-", "THETURTLE-", "ZEORUS-", "MELUZE-", "BONTA", "BRAKMAR", "ASTRUB", "EMELKA", "SUFOKIA", "WABBIT", "PANDALA", "GROBE"};
        dfZ = new String[]{"ADMIN", "ADMINISTRATEUR", "MODERATEUR", "WAKFU", "DOFUS", "DIEU", "MODERATION", "ENIRIPSA", "OSAMODAS", "SACRIEUR", "XELOR", "PANDAWA", "ECAFLIP", "ENUTROF", "SADIDA", "ELIATROPE", "ELYATROPE", "ARIMATH", "LIMERO", "MALYCORNE", "BUTOR", "MINUIT", "LETHALINE", "SIGISBUL", "OGREST", "DHATURA", "NUMBRUS", "KROKKER", "FULIDULI", "GRILEMBORE", "RYKKE", "TREPANEM", "RUTGER", "PEPITOX", "FISTULE", "RUSHU", "DJAUL", "SILVOSSE", "BRUMAIRE", "ULGRUDE", "JIVA", "POUCHECOT", "HECATE", "ROSAL", "SUMMENS", "RAVAL", "FALLANSTER", "AMAYIRO", "HYRKUL", "DARDONDAKAL", "ALLISTER", "HELIOBOROS", "OURONIGRIDE", "SPIRITETIA", "CROCOBURIO", "CROCABULIA", "GROUGALORASALAR", "CROCOBUTOR", "CROULAKLAKOSS", "BOLGROT", "HELSEPHINE", "AGUABRIAL", "IGNEMIKHAL", "TERRAKOURIAL", "AERAFAL", "MUSTAM", "KAFFRA", "RAGAA", "SATIREV", "JERHYN", "DANATHOR", "BISHOM", "LENALD", "OTOMAI", "AMALIA", "AMALIA SHERAN SHARM", "AMALYA", "TRISTEPIN", "TRISTEPAIN", "TRYSTEPIN", "STROUD", "RUEL STROUD", "EVANGELYNE", "EVANGELINE", "YUGO", "ADAMAI", "RUBILAX", "RUBYLAX", "VAMPYRO", "VAMPIRO", "GROUGALORAGRAN", "DATHURA", "COROPHORIBLE", "ALIBERT", "MIRANDA", "KABROK", "KRISS", "TOLOT", "CORBEAU", "KRALAMOUR", "SHUSHU", "OMBRAGE", "KANNIBOUL", "LEKTERR", "EMPOISONNEUSE", "COLLECTIONNEUR", "HERBACHA", "HONORAPE", "HERBEFOL", "ANERICE", "SHUSHESS", "GAZTON", "MORVEN", "SPASMOL", "ROLAC", "FRANKULA", "REQUIEM", "IMMORTALO", "MORBIDON", "MOMIE NOVA", "CERVELASSE", "USSE BONE", "OLISTER", "SMISSE", "NOCTURNO", "NARUTO", "SNUFFLE"};
        dga = new String[]{"KKK", "COUILLE", "VAGIN", "BATAR", "BATARD", "PUTIN", "PUTAIN", "SUCER", "SUCEUR", "SUCEUSE", "ANUS", "NEGRE", "JUIF", "ARABE", "MERDE", "FDP", "CROTTE", "ZIZI", "BRANL", "JOUIR", "NTM", "TAMERE", "ENCULE", "CHIOTTE", "CLITO", "ETRON", "GOUINE", "TURLUTTE", "CHIASSE", "LESBIENNE", "NIQUER", "SUCE", "LESBOS", "FUCK", "BITCH", "SUCK", "PENIS", "COCK", "NIGG", "CUNT", "FAG", "GAY", "SUCK", "PISS", "CUM", "ARSE", "BIATCH", "BOOB", "DICK", "DILDO", "QUEER", "FART", "WANKER", "GANGBANG", "GANG BANG", "LESBIAN", "SHIT", "POOP", "ARSCH", "BOLLERN", "BAKA", "CHIPATAMA", "HENTAI", "KUSO", "BALALAO", "CHUPAR", "ESPORRA", "QUECA", "ACABAR", "AGILIPOLLAO", "ALMEJA", "tageul", "enculer", "conard", "tagueule", "conar", "connard", "poufiasse", "pouffiasse", "pute", "suceur", "suceurs", "dard", "dare", "SALOP", "SALOPE", "NIQUE", "HOMO", "HOMOS", "SUCE", "CON", "CONNE", "CONS", "CONNES", "PD", "TG", "CHIER", "PEDE", "CUL", "PUTE", "SEXE", "SEX", "ENKULE", "PETASSE", "WTF", "STFU", "ASS"};
        dgb = new String[]{"POUFFIASSE", "CONNAR", "CONNASS", "ZOOPHIL", "POUFIASSE", "GROGNASSE", "HTTP", "WWW", "FTP", "\\.COM", "THXO", "IGHOT", "OURGAME", "OUR GAME", "HITLER", "NAZI", "KUKLUXKLAN", "KU KLUX KLAN", "ADOLF", "OUSSAMA", "BENLADEN", "BEN LADEN", "HUSSEIN", "SADDAM", "BOUGNOUL", "BOUKAK", "ALKAIDA"};
    }
}

