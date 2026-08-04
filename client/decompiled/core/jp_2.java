/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Attributes;

/*
 * Renamed from Jp
 */
public class jp_2
extends ka_0 {
    boolean kc = false;

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        this.kc = false;
        String string2 = attributes.getValue("pattern");
        String string3 = attributes.getValue("actionClass");
        if (dh_2.isEmpty(string2)) {
            this.kc = true;
            String string4 = "No 'pattern' attribute in <newRule>";
            this.eg(string4);
            return;
        }
        if (dh_2.isEmpty(string3)) {
            this.kc = true;
            String string5 = "No 'actionClass' attribute in <newRule>";
            this.eg(string5);
            return;
        }
        try {
            this.ee("About to add new Joran parsing rule [" + string2 + "," + string3 + "].");
            qq_02.vY().VA().a(new zf_0(string2), string3);
        }
        catch (Exception exception) {
            this.kc = true;
            String string6 = "Could not add new Joran parsing rule [" + string2 + "," + string3 + "]";
            this.eg(string6);
        }
    }

    public void a(qq_0 qq_02, String string) {
    }

    public void a(qq_0 qq_02) {
    }
}

