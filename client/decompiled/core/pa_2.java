/*
 * Decompiled with CFR 0.152.
 */
import java.util.StringTokenizer;

/*
 * Renamed from pA
 */
public class pa_2
implements apG {
    private Class ach = vP.class;

    public vP bv(String string) {
        return this.b(this.ach, string);
    }

    public vP b(Class clazz, String string) {
        if (string != null && clazz.equals(vP.class)) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, ",");
            float f = 0.0f;
            float f2 = 0.0f;
            float f3 = 0.0f;
            float f4 = 1.0f;
            if (stringTokenizer.hasMoreTokens()) {
                f = Float.parseFloat(stringTokenizer.nextToken().trim());
            }
            if (stringTokenizer.hasMoreTokens()) {
                f2 = Float.parseFloat(stringTokenizer.nextToken().trim());
            }
            if (stringTokenizer.hasMoreTokens()) {
                f3 = Float.parseFloat(stringTokenizer.nextToken().trim());
            }
            if (stringTokenizer.hasMoreTokens()) {
                f4 = Float.parseFloat(stringTokenizer.nextToken().trim());
            }
            return new vP(f, f2, f3, f4);
        }
        return null;
    }

    public Class uk() {
        return this.ach;
    }

    public boolean ul() {
        return true;
    }

    public boolean um() {
        return true;
    }

    public String a(zp_1 zp_12, DS dS, Class clazz, String string, afq_1 afq_12) {
        if (string != null && clazz.equals(vP.class)) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, ",");
            float f = 0.0f;
            float f2 = 0.0f;
            float f3 = 0.0f;
            float f4 = 1.0f;
            if (stringTokenizer.hasMoreTokens()) {
                f = Float.parseFloat(stringTokenizer.nextToken().trim());
            }
            if (stringTokenizer.hasMoreTokens()) {
                f2 = Float.parseFloat(stringTokenizer.nextToken().trim());
            }
            if (stringTokenizer.hasMoreTokens()) {
                f3 = Float.parseFloat(stringTokenizer.nextToken().trim());
            }
            if (stringTokenizer.hasMoreTokens()) {
                f4 = Float.parseFloat(stringTokenizer.nextToken().trim());
            }
            zp_12.j(this.ach);
            return "new " + this.ach.getName() + "(" + f + "f, " + f2 + "f, " + f3 + "f, " + f4 + "f)";
        }
        return "null";
    }
}

