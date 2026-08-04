/*
 * Decompiled with CFR 0.152.
 */
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ape {
    private static PrintStream ps = System.out;
    static SimpleDateFormat cLW = new SimpleDateFormat("HH:mm:ss,SSS");

    public static void n(PrintStream printStream) {
        ps = printStream;
    }

    public static void b(vU vU2) {
        if (vU2 == null) {
            throw new IllegalArgumentException("Context argument cannot be null");
        }
        Ju ju = vU2.ea();
        if (ju == null) {
            ps.println("WARN: Context named \"" + vU2.getName() + "\" has no status manager");
        } else if (ju.getLevel() == 1) {
            ape.b(ju);
        }
    }

    public static void c(vU vU2) {
        if (vU2 == null) {
            throw new IllegalArgumentException("Context argument cannot be null");
        }
        Ju ju = vU2.ea();
        if (ju == null) {
            ps.println("WARN: Context named \"" + vU2.getName() + "\" has no status manager");
        } else if (ju.getLevel() == 2) {
            ape.b(ju);
        }
    }

    public static void d(vU vU2) {
        if (vU2 == null) {
            throw new IllegalArgumentException("Context argument cannot be null");
        }
        Ju ju = vU2.ea();
        if (ju == null) {
            ps.println("WARN: Context named \"" + vU2.getName() + "\" has no status manager");
        } else {
            ape.b(ju);
        }
    }

    public static void b(Ju ju) {
        StringBuilder stringBuilder = new StringBuilder();
        ape.a(stringBuilder, ju);
        ps.println(stringBuilder.toString());
    }

    public static void p(List list) {
        StringBuilder stringBuilder = new StringBuilder();
        ape.a(stringBuilder, list);
        ps.println(stringBuilder.toString());
    }

    private static void a(StringBuilder stringBuilder, List list) {
        for (amb amb2 : list) {
            ape.a(stringBuilder, "", amb2);
        }
    }

    private static void a(StringBuilder stringBuilder, Ju ju) {
        ape.a(stringBuilder, ju.VS());
    }

    private static void a(StringBuilder stringBuilder, Throwable throwable) {
        String[] stringArray;
        for (String string : stringArray = aqu_0.h(throwable)) {
            if (!string.startsWith("Caused by: ")) {
                if (Character.isDigit(string.charAt(0))) {
                    stringBuilder.append("\t... ");
                } else {
                    stringBuilder.append("\tat ");
                }
            }
            stringBuilder.append(string).append(kJ.sy);
        }
    }

    public static void a(StringBuilder stringBuilder, String string, amb amb2) {
        Object object;
        Object object2;
        String string2 = amb2.hasChildren() ? string + "+ " : string + "|-";
        if (cLW != null) {
            object2 = new Date(amb2.aBh());
            object = cLW.format((Date)object2);
            stringBuilder.append((String)object).append(" ");
        }
        stringBuilder.append(string2).append(amb2).append(kJ.sy);
        if (amb2.getThrowable() != null) {
            ape.a(stringBuilder, amb2.getThrowable());
        }
        if (amb2.hasChildren()) {
            object2 = amb2.iterator();
            while (object2.hasNext()) {
                object = (amb)object2.next();
                ape.a(stringBuilder, string + "  ", (amb)object);
            }
        }
    }
}

