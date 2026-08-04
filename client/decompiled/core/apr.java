/*
 * Decompiled with CFR 0.152.
 */
public class apr {
    private String value;

    public final void setValue(String string) {
        if (this.value != null) {
            throw new IllegalStateException("Comment value already set.");
        }
        this.value = string;
    }

    public final String getValue() {
        return this.value;
    }

    public void addText(String string) {
        this.setValue(string);
    }
}

