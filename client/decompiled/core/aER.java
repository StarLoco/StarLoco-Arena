/*
 * Decompiled with CFR 0.152.
 */
public final class aER
implements apG {
    public static final Class ach = auC.class;

    public auC lc(String string) {
        return this.n(ach, string);
    }

    public auC n(Class clazz, String string) {
        if (string != null) {
            return auC.jC(string);
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
            zp_12.j(ach);
            StringBuilder stringBuilder = new StringBuilder();
            auC auC2 = auC.jC(string);
            double d = auC2.getValue();
            stringBuilder.append("new Percentage(").append(d).append(")");
            return stringBuilder.toString();
        }
        return "null";
    }
}

