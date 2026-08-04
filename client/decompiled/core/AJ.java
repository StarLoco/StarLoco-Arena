/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Locator;

public class AJ
extends xg_0 {
    private String text;

    AJ(String string, Locator locator) {
        super(null, null, null, locator);
        this.text = string;
    }

    public String getText() {
        return this.text;
    }

    public String toString() {
        return "BodyEvent(" + this.getText() + ")" + this.awt.getLineNumber() + "," + this.awt.getColumnNumber();
    }

    public void append(String string) {
        this.text = this.text + string;
    }
}

