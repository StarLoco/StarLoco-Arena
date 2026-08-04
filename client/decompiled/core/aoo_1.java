/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Attributes;

/*
 * Renamed from aOO
 */
public class aoo_1
extends ka_0 {
    static String NO_NAME = "No name attribute in <param> element";
    static String emP = "No name attribute in <param> element";
    boolean kc = false;

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        String string2 = attributes.getValue("name");
        String string3 = attributes.getValue("value");
        if (string2 == null) {
            this.kc = true;
            this.eg(NO_NAME);
            return;
        }
        if (string3 == null) {
            this.kc = true;
            this.eg(emP);
            return;
        }
        string3 = string3.trim();
        Object object = qq_02.wa();
        nj_1 nj_12 = new nj_1(object);
        nj_12.a(this.Pb);
        string3 = qq_02.subst(string3);
        string2 = qq_02.subst(string2);
        nj_12.setProperty(string2, string3);
    }

    public void a(qq_0 qq_02, String string) {
    }

    public void a(qq_0 qq_02) {
    }
}

