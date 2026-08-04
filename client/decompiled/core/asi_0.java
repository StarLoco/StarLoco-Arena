/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Locator;

/*
 * Renamed from asI
 */
class asi_0
extends agn_1 {
    asi_0(jh_1 jh_12) {
        super(jh_12);
    }

    protected Object aBg() {
        jh_1 jh_12 = (jh_1)super.aBg();
        Locator locator = jh_12.awt;
        if (locator != null) {
            return jh_1.class.getName() + "@" + locator.getLineNumber() + ":" + locator.getColumnNumber();
        }
        return jh_1.class.getName() + "@NA:NA";
    }
}

