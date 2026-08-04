/*
 * Decompiled with CFR 0.152.
 */
public class aKI
implements oy_0 {
    private Class aTH;
    private String m_name;
    private String arg;
    private boolean dTN;

    public aKI(Class clazz, String string, String string2) {
        this(clazz, string, string2, false);
    }

    public aKI(Class clazz, String string, String string2, boolean bl2) {
        this.aTH = clazz;
        this.m_name = string;
        this.arg = string2;
        this.dTN = bl2;
    }

    public Class abM() {
        return this.aTH;
    }

    public String a(Ga ga) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!this.dTN && !ga.do(this.m_name)) {
            ga.dp(this.m_name);
            stringBuilder.append(this.aTH.getSimpleName()).append(" ");
        }
        stringBuilder.append(this.m_name).append(" = ");
        stringBuilder.append("(").append(this.aTH.getSimpleName()).append(") ");
        stringBuilder.append(this.arg).append(";");
        return stringBuilder.toString();
    }
}

