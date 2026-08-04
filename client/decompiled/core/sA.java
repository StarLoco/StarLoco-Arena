/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class sA {
    public static final String akm = "V";
    public static final String akn = "B";
    public static final String ako = "C";
    public static final String akp = "D";
    public static final String akq = "F";
    public static final String akr = "I";
    public static final String aks = "J";
    public static final String akt = "S";
    public static final String aku = "Z";
    public static final String akv = "Ljava/lang/Object;";
    public static final String STRING = "Ljava/lang/String;";
    public static final String akw = "Ljava/lang/StringBuffer;";
    public static final String akx = "Ljava/lang/StringBuilder;";
    public static final String CLASS = "Ljava/lang/Class;";
    public static final String aky = "Ljava/lang/Throwable;";
    public static final String akz = "Ljava/lang/RuntimeException;";
    public static final String ERROR = "Ljava/lang/Error;";
    public static final String akA = "Ljava/lang/Cloneable;";
    public static final String akB = "Ljava/io/Serializable;";
    public static final String akC = "Ljava/lang/Boolean;";
    public static final String akD = "Ljava/lang/Byte;";
    public static final String akE = "Ljava/lang/Character;";
    public static final String akF = "Ljava/lang/Short;";
    public static final String akG = "Ljava/lang/Integer;";
    public static final String akH = "Ljava/lang/Long;";
    public static final String akI = "Ljava/lang/Float;";
    public static final String akJ = "Ljava/lang/Double;";
    private static final Map akK = new HashMap();
    private static final Map akL = new HashMap();

    private sA() {
    }

    public static boolean bU(String string) {
        return string.length() > 1;
    }

    public static boolean bV(String string) {
        return string.charAt(0) == 'L';
    }

    public static boolean bW(String string) {
        return string.charAt(0) == '[';
    }

    public static String bX(String string) {
        if (string.charAt(0) != '[') {
            throw new aHY("Cannot determine component descriptor from non-array descriptor \"" + string + "\"");
        }
        return string.substring(1);
    }

    public static short bY(String string) {
        if (string.equals(akm)) {
            return 0;
        }
        if (sA.bZ(string)) {
            return 1;
        }
        if (sA.ca(string)) {
            return 2;
        }
        throw new aHY("No size defined for type \"" + sA.toString(string) + "\"");
    }

    public static boolean bZ(String string) {
        if (string.length() == 1) {
            return "BCFISZ".indexOf(string) != -1;
        }
        return sA.bU(string);
    }

    public static boolean ca(String string) {
        return string.equals(aks) || string.equals(akp);
    }

    public static String toString(String string) {
        int n2 = 0;
        StringBuffer stringBuffer = new StringBuffer();
        if (string.charAt(0) == '(') {
            ++n2;
            stringBuffer.append("(");
            while (n2 < string.length() && string.charAt(n2) != ')') {
                if (n2 != 1) {
                    stringBuffer.append(", ");
                }
                n2 = sA.a(string, n2, stringBuffer);
            }
            if (n2 >= string.length()) {
                throw new aHY("Invalid descriptor \"" + string + "\"");
            }
            stringBuffer.append(") => ");
            ++n2;
        }
        sA.a(string, n2, stringBuffer);
        return stringBuffer.toString();
    }

    private static int a(String string, int n2, StringBuffer stringBuffer) {
        int n3 = 0;
        while (n2 < string.length() && string.charAt(n2) == '[') {
            ++n3;
            ++n2;
        }
        if (n2 >= string.length()) {
            throw new aHY("Invalid descriptor \"" + string + "\"");
        }
        switch (string.charAt(n2)) {
            case 'L': {
                int n4 = string.indexOf(59, n2);
                if (n4 == -1) {
                    throw new aHY("Invalid descriptor \"" + string + "\"");
                }
                stringBuffer.append(string.substring(n2 + 1, n4).replace('/', '.'));
                n2 = n4;
                break;
            }
            case 'V': {
                stringBuffer.append("void");
                break;
            }
            case 'B': {
                stringBuffer.append("byte");
                break;
            }
            case 'C': {
                stringBuffer.append("char");
                break;
            }
            case 'D': {
                stringBuffer.append("double");
                break;
            }
            case 'F': {
                stringBuffer.append("float");
                break;
            }
            case 'I': {
                stringBuffer.append("int");
                break;
            }
            case 'J': {
                stringBuffer.append("long");
                break;
            }
            case 'S': {
                stringBuffer.append("short");
                break;
            }
            case 'Z': {
                stringBuffer.append("boolean");
                break;
            }
            default: {
                throw new aHY("Invalid descriptor \"" + string + "\"");
            }
        }
        while (n3 > 0) {
            stringBuffer.append("[]");
            --n3;
        }
        return n2 + 1;
    }

    public static String cb(String string) {
        String string2 = (String)akK.get(string);
        if (string2 != null) {
            return string2;
        }
        if (string.startsWith("[")) {
            return string.replace('.', '/');
        }
        return 'L' + string.replace('.', '/') + ';';
    }

    public static String cc(String string) {
        if (string.charAt(0) == '[') {
            return string;
        }
        return 'L' + string + ';';
    }

    public static String toClassName(String string) {
        String string2 = (String)akL.get(string);
        if (string2 != null) {
            return string2;
        }
        char c = string.charAt(0);
        if (c == 'L' && string.endsWith(";")) {
            return string.substring(1, string.length() - 1).replace('/', '.');
        }
        if (c == '[') {
            return string.replace('/', '.');
        }
        throw new aHY("(Invalid field descriptor \"" + string + "\")");
    }

    public static String cd(String string) {
        if (string.charAt(0) != 'L') {
            throw new aHY("Attempt to convert non-class descriptor \"" + string + "\" into internal form");
        }
        return string.substring(1, string.length() - 1);
    }

    public static boolean ce(String string) {
        return string.length() == 1 && "VBCDFIJSZ".indexOf(string.charAt(0)) != -1;
    }

    public static boolean cf(String string) {
        return string.length() == 1 && "BDFIJSC".indexOf(string.charAt(0)) != -1;
    }

    public static String cg(String string) {
        if (string.charAt(0) != 'L') {
            throw new aHY("Attempt to get package name of non-class descriptor \"" + string + "\"");
        }
        int n2 = string.lastIndexOf(47);
        return n2 == -1 ? null : string.substring(1, n2).replace('/', '.');
    }

    public static boolean k(String string, String string2) {
        String string3 = sA.cg(string);
        String string4 = sA.cg(string2);
        return string3 == null ? string4 == null : string3.equals(string4);
    }

    static {
        akL.put(akm, "void");
        akL.put(akn, "byte");
        akL.put(ako, "char");
        akL.put(akp, "double");
        akL.put(akq, "float");
        akL.put(akr, "int");
        akL.put(aks, "long");
        akL.put(akt, "short");
        akL.put(aku, "boolean");
        akL.put(akv, "java.lang.Object");
        akL.put(STRING, "java.lang.String");
        akL.put(akw, "java.lang.StringBuffer");
        akL.put(akx, "java.lang.StringBuilder");
        akL.put(CLASS, "java.lang.Class");
        akL.put(aky, "java.lang.Throwable");
        akL.put(akz, "java.lang.RuntimeException");
        akL.put(ERROR, "java.lang.Error");
        akL.put(akA, "java.lang.Cloneable");
        akL.put(akB, "java.io.Serializable");
        akL.put(akC, "java.lang.Boolean");
        akL.put(akD, "java.lang.Byte");
        akL.put(akE, "java.lang.Character");
        akL.put(akF, "java.lang.Short");
        akL.put(akG, "java.lang.Integer");
        akL.put(akH, "java.lang.Long");
        akL.put(akI, "java.lang.Float");
        akL.put(akJ, "java.lang.Double");
        Iterator iterator = akL.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            akK.put(entry.getValue(), entry.getKey());
        }
    }
}

