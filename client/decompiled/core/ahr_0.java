/*
 * Decompiled with CFR 0.152.
 */
import java.util.StringTokenizer;

/*
 * Renamed from aHR
 */
public final class ahr_0
implements apG {
    public static final Class ach = agj_1.class;

    public agj_1 lv(String string) {
        return this.o(ach, string);
    }

    public agj_1 o(Class clazz, String string) {
        if (string != null) {
            String string2;
            StringTokenizer stringTokenizer = new StringTokenizer(string, ",");
            agj_1 agj_12 = new agj_1();
            if (stringTokenizer.hasMoreTokens()) {
                string2 = stringTokenizer.nextToken().trim();
                if (string2.endsWith("%")) {
                    agj_12.aV(Float.parseFloat(string2.substring(0, string2.length() - 1)));
                } else {
                    agj_12.setWidth(Integer.parseInt(string2));
                }
            }
            if (stringTokenizer.hasMoreTokens()) {
                string2 = stringTokenizer.nextToken().trim();
                if (string2.endsWith("%")) {
                    agj_12.aU(Float.parseFloat(string2.substring(0, string2.length() - 1)));
                } else {
                    agj_12.setHeight(Integer.parseInt(string2));
                }
            }
            return agj_12;
        }
        return null;
    }

    public Class uk() {
        return ach;
    }

    public boolean ul() {
        return true;
    }

    public boolean um() {
        return true;
    }

    public String a(zp_1 zp_12, DS dS, Class clazz, String string, afq_1 afq_12) {
        if (string != null) {
            String string2;
            zp_12.j(ach);
            StringTokenizer stringTokenizer = new StringTokenizer(string, ",");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("new Dimension(");
            if (stringTokenizer.hasMoreTokens()) {
                string2 = stringTokenizer.nextToken().trim();
                if (string2.endsWith("%")) {
                    stringBuilder.append(string2.substring(0, string2.length() - 1)).append("f");
                } else {
                    stringBuilder.append(string2);
                }
            } else {
                stringBuilder.append(0);
            }
            stringBuilder.append(", ");
            if (stringTokenizer.hasMoreTokens()) {
                string2 = stringTokenizer.nextToken().trim();
                if (string2.endsWith("%")) {
                    stringBuilder.append(string2.substring(0, string2.length() - 1)).append("f");
                } else {
                    stringBuilder.append(string2);
                }
            } else {
                stringBuilder.append(0);
            }
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
        return "null";
    }
}

