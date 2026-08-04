/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/*
 * Renamed from arE
 */
public class are_0 {
    public static final Logger a = Logger.getLogger(are_0.class);
    private static Pattern cQc = Pattern.compile("\\{((\\[[^\\[\\]\\{\\}?:]*\\])+)\\?([^\\{\\}]*):([^\\{\\}]*)\\}");
    private static Pattern cQd = Pattern.compile("\\[([^\\[\\]]*)([~\\*\\+\\->!=])([^\\[\\]]*)\\]");
    private static Pattern cQe = Pattern.compile("\\[([\\\u00a3#])([0-9]+)\\]");
    private static Pattern cQf = Pattern.compile("\\[(#name)\\]");
    public static byte cQg;
    private static byte cQh;
    public static String cQi;
    private static String m_name;

    public static String format(String string, Object ... objectArray) {
        boolean bl2;
        Object object;
        Object object2;
        do {
            object2 = new StringBuffer();
            object = cQc.matcher(string);
            while (((Matcher)object).find()) {
                bl2 = true;
                String string2 = ((Matcher)object).group(1);
                Matcher matcher = cQd.matcher(string2);
                block11: while (matcher.find()) {
                    String string3 = matcher.group(1);
                    int n2 = 1;
                    if (string3.length() > 0) {
                        n2 = Integer.parseInt(string3);
                    }
                    char c = matcher.group(2).charAt(0);
                    String string4 = matcher.group(3);
                    int n3 = -1;
                    if (string4.length() > 0) {
                        n3 = Integer.parseInt(string4);
                    }
                    switch (c) {
                        case '>': {
                            if (objectArray.length < n3) continue block11;
                            bl2 &= are_0.g(objectArray[n3 - 1], n2);
                            continue block11;
                        }
                        case '=': {
                            if (objectArray.length < n3) continue block11;
                            bl2 &= are_0.i(objectArray[n3 - 1], n2);
                            continue block11;
                        }
                        case '~': {
                            bl2 &= objectArray.length >= n3 && objectArray[n3 - 1] != null && !are_0.i(objectArray[n3 - 1], 0) && (!(objectArray[n3 - 1] instanceof String) || !are_0.Z((String)objectArray[n3 - 1], ""));
                            continue block11;
                        }
                        case '!': {
                            bl2 &= objectArray.length < n3 || objectArray[n3 - 1] == null || are_0.i(objectArray[n3 - 1], 0) || objectArray[n3 - 1] instanceof String && are_0.Z((String)objectArray[n3 - 1], "");
                            continue block11;
                        }
                        case '+': {
                            bl2 &= objectArray.length >= n3 && objectArray[n3 - 1] != null && are_0.h(objectArray[n3 - 1], 0);
                            continue block11;
                        }
                        case '-': {
                            bl2 &= objectArray.length >= n3 && objectArray[n3 - 1] != null && !are_0.h(objectArray[n3 - 1], 0);
                            continue block11;
                        }
                        case '*': {
                            bl2 &= are_0.lT(n2);
                            continue block11;
                        }
                    }
                    a.error((Object)("Impossible de formatter l'expression : " + string));
                }
                if (bl2) {
                    ((Matcher)object).appendReplacement((StringBuffer)object2, ((Matcher)object).group(3));
                    continue;
                }
                ((Matcher)object).appendReplacement((StringBuffer)object2, ((Matcher)object).group(4));
            }
            ((Matcher)object).appendTail((StringBuffer)object2);
        } while (cQc.matcher(string = ((StringBuffer)object2).toString()).find());
        object2 = cQe.matcher(string);
        object = new StringBuffer();
        while (((Matcher)object2).find()) {
            boolean bl3 = bl2 = ((Matcher)object2).group(1).charAt(0) == '\u00a3';
            int n4 = Integer.parseInt(((Matcher)object2).group(2)) - 1;
            if (objectArray.length <= n4 || objectArray[n4] == null) continue;
            ((Matcher)object2).appendReplacement((StringBuffer)object, bl2 ? are_0.aF(objectArray[n4]).toString() : objectArray[n4].toString());
        }
        ((Matcher)object2).appendTail((StringBuffer)object);
        object2 = cQf.matcher(((StringBuffer)object).toString());
        object = new StringBuffer();
        while (((Matcher)object2).find()) {
            ((Matcher)object2).appendReplacement((StringBuffer)object, m_name);
        }
        ((Matcher)object2).appendTail((StringBuffer)object);
        return ((StringBuffer)object).toString();
    }

    private static boolean lT(int n2) {
        if (n2 > 127) {
            a.error((Object)("Constante trop grande pour le test du sex de l'interlocuteur : " + n2));
            return false;
        }
        return (byte)n2 == cQh;
    }

    private static boolean g(Object object, int n2) {
        return are_0.h(object, n2);
    }

    private static boolean h(Object object, int n2) {
        if (object instanceof Long) {
            return (Long)object > (long)n2;
        }
        if (object instanceof Integer) {
            return (Integer)object > n2;
        }
        if (object instanceof Float) {
            return ((Float)object).floatValue() > (float)n2;
        }
        if (object instanceof Double) {
            return (Double)object > (double)n2;
        }
        if (object instanceof Short) {
            return (Short)object > n2;
        }
        if (object instanceof Byte) {
            return (Byte)object > n2;
        }
        if (object instanceof String) {
            return Double.parseDouble((String)object) > (double)n2;
        }
        return false;
    }

    private static boolean i(Object object, int n2) {
        if (object instanceof Long) {
            return (Long)object == (long)n2;
        }
        if (object instanceof Integer) {
            return (Integer)object == n2;
        }
        if (object instanceof Float) {
            return ((Float)object).floatValue() == (float)n2;
        }
        if (object instanceof Double) {
            return (Double)object == (double)n2;
        }
        if (object instanceof Short) {
            return (Short)object == n2;
        }
        if (object instanceof Byte) {
            return (Byte)object == n2;
        }
        if (object instanceof String) {
            return object.equals(Integer.toString(n2));
        }
        return false;
    }

    private static boolean Z(String string, String string2) {
        return string.equals(string2);
    }

    private static Object aF(Object object) {
        if (object instanceof Long) {
            return Math.abs((Long)object);
        }
        if (object instanceof Integer) {
            return Math.abs((Integer)object);
        }
        if (object instanceof Float) {
            return Float.valueOf(Math.abs(((Float)object).floatValue()));
        }
        if (object instanceof Double) {
            return Math.abs((Double)object);
        }
        if (object instanceof Short) {
            return Math.abs(((Short)object).shortValue());
        }
        if (object instanceof Byte) {
            return Math.abs(((Byte)object).byteValue());
        }
        if (object instanceof String) {
            return Math.abs(Double.parseDouble(object.toString()));
        }
        return false;
    }

    public static void aS(byte by) {
        cQh = by;
    }

    public static void setName(String string) {
        m_name = string;
    }

    public static void main(String[] stringArray) {
        System.out.println(are_0.format("coucou", 1, 2, 3));
        System.out.println(are_0.format("c'est [#1] chats [#2]", 2, "bleus"));
        System.out.println(are_0.format("c'est [#2] [#1]{[>2]?s:}", "chat", 1));
        System.out.println(are_0.format("c'est [#2] [#1]{[>2]?s:}", "chat", 2));
        are_0.aS((byte)0);
        System.out.println(are_0.format("[#name] a un joli nom, c'est {[1*]?une fille:un gar\u00e7on} et [#2] [#1]{[>2]?s:}", "chat", 2));
        System.out.println(are_0.format("{[~1]?[#1] a verrouill\u00e9 le combat : :}Le combat est maintenant verrouill\u00e9", "chat"));
        System.out.println(are_0.format("{[~1]? [#1] ann\u00e9e{[>1]?s:}:}{[~1][~2]? et :}{[~2]?[#2] mois:}{[~1]?:{[~2][~3]? et :}{[~3]?[#3] jour{[>3]?s:}:}{[~2]?:{[~3][~4]? et :}{[~4]?[#4] heure{[>4]?s:}:}{[~3]?:{[~4][~5]? et :}{[~5]?[#5] minute{[>5]?s:}:}{[~4]?:{[~5][~6]? et :}{[~6]?[#6] seconde{[>6]?s:}:}}}}}", 0, 0, 0, 0, 45, 12));
        System.out.println(are_0.format("Bonjour Emelka ! Allez hop, c\u2019est parti pour les pr\u00e9visions de la journ\u00e9e ! Aujourd\u2019hui, {[1=4]?grand soleil sur toute la zone, petits veinards !:}{[3=4]?le temps risque d\u2019\u00eatre mitig\u00e9. Attendez-vous \u00e0 voir passer des nuages gros comme des Bouftous !:}{[4=4]?pluie sur toute la zone ! C\u2019est le moment d\u2019aller planter vos petits l\u00e9gumes !:}{[5=4]?sortez vos pulls en laine de Bouftou, car il va neiger !:}{[6=4]? mieux vaut ne pas oublier votre parapluie ! Il va faire un temps de Chienchien !:} C\u00f4t\u00e9 vent, {[0=3]?ce sera le calme plat. Pas un m\u00eame un pet de Bouftou !:}{[1=3]?une l\u00e9g\u00e8re brise devrait venir donner du style \u00e0 vos cheveux \u00e9bouriff\u00e9s.:}{[2=3]?\u00e7a va d\u00e9coiffer ! Nos amis Iops devraient forcer sur la gomina s'ils ne veulent pas ressembler \u00e0 un Tofu d\u00e9plum\u00e9 !:} Quant aux temp\u00e9ratures, elles devraient se situer entre [#1]\u00b0C et [#2]\u00b0C. Bonne journ\u00e9e !", 10, 25, (int)(Math.random() * 3.0 + 1.0), (int)(Math.random() * 6.0 + 1.0)));
    }

    static {
        cQh = cQg = 0;
        m_name = cQi = "Unknown";
    }
}

