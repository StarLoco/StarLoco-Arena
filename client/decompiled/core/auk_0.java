/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.helpers.AttributesImpl;

/*
 * Renamed from auK
 */
public class auk_0
extends xg_0 {
    public final Attributes cWI;
    public final zf_0 bjy;

    auk_0(zf_0 zf_02, String string, String string2, String string3, Attributes attributes, Locator locator) {
        super(string, string2, string3, locator);
        this.cWI = new AttributesImpl(attributes);
        this.bjy = zf_02;
    }

    public Attributes getAttributes() {
        return this.cWI;
    }

    public String toString() {
        return "StartEvent(" + this.DD() + ")  [" + this.awt.getLineNumber() + "," + this.awt.getColumnNumber() + "]";
    }
}

