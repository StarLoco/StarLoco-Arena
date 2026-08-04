/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from gP
 */
public abstract class gp_0 {
    private String m_name = "";
    private of_0[] uP = null;

    public gp_0(String string, of_0 ... of_0Array) {
        if (string == null) {
            throw new IllegalArgumentException("Nom de liste ne peut \u00eatre null.");
        }
        this.m_name = string;
        this.uP = of_0Array;
    }

    public gp_0(of_0 ... of_0Array) {
        this("", of_0Array);
    }

    public final int ko() {
        return this.uP != null ? this.uP.length : 0;
    }

    public final String getName() {
        return this.m_name;
    }

    public of_0 aJ(int n2) {
        return this.uP[n2];
    }

    public abstract of_0[] kp();
}

