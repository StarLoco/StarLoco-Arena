/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Locator;
import org.xml.sax.helpers.LocatorImpl;

/*
 * Renamed from xg
 */
public class xg_0 {
    public final String namespaceURI;
    public final String localName;
    public final String qName;
    public final Locator awt;

    xg_0(String string, String string2, String string3, Locator locator) {
        this.namespaceURI = string;
        this.localName = string2;
        this.qName = string3;
        this.awt = new LocatorImpl(locator);
    }

    public String getLocalName() {
        return this.localName;
    }

    public Locator getLocator() {
        return this.awt;
    }

    public String getNamespaceURI() {
        return this.namespaceURI;
    }

    public String DD() {
        return this.qName;
    }
}

