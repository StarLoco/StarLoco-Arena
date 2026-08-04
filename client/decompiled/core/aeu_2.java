/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Insets;
import java.util.StringTokenizer;

/*
 * Renamed from aeu
 */
public class aeu_2
implements apG {
    private Class ach = Insets.class;

    public Insets hD(String string) {
        return this.f(this.ach, string);
    }

    public Insets f(Class clazz, String string) {
        if (string != null && clazz.equals(Insets.class)) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, ",");
            Insets insets = new Insets(0, 0, 0, 0);
            if (stringTokenizer.hasMoreTokens()) {
                insets.top = Gr.R(stringTokenizer.nextToken().trim());
            }
            if (stringTokenizer.hasMoreTokens()) {
                insets.bottom = Gr.R(stringTokenizer.nextToken().trim());
            }
            if (stringTokenizer.hasMoreTokens()) {
                insets.left = Gr.R(stringTokenizer.nextToken().trim());
            }
            if (stringTokenizer.hasMoreTokens()) {
                insets.right = Gr.R(stringTokenizer.nextToken().trim());
            }
            return insets;
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
        Insets insets = this.f(clazz, string);
        zp_12.j(clazz);
        return "new " + clazz.getSimpleName() + "(" + insets.top + ", " + insets.left + ", " + insets.bottom + ", " + insets.right + ")";
    }
}

