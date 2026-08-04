/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from NI
 */
public class ni_2 {
    private String bAg;
    private String bAh;
    private String defaultValue;

    public ni_2(String string) {
        if (string == null) {
            throw new IllegalArgumentException("prompt must not be null");
        }
        this.bAg = string;
    }

    public String getPrompt() {
        return this.bAg;
    }

    public void fx(String string) {
        this.bAh = string;
    }

    public boolean OR() {
        return true;
    }

    public String getInput() {
        return this.bAh;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String string) {
        this.defaultValue = string;
    }
}

