/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Of
 */
public class of_0 {
    private String m_name = "";

    public of_0(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Nom de param\u00e8tre ne peut \u00eatre null.");
        }
        this.m_name = string;
    }

    public final String getName() {
        return this.m_name;
    }
}

