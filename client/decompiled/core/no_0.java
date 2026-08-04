/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Attributes;

/*
 * Renamed from No
 */
public class no_0
extends ka_0 {
    static final String bzp = "debug";
    boolean bzq = false;

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        String string2 = attributes.getValue(bzp);
        if (string2 == null || string2.equals("") || string2.equals("false") || string2.equals("null")) {
            this.ee("debug attribute not set");
        } else {
            this.bzq = true;
        }
        qq_02.C(this.QK());
    }

    public void a(qq_0 qq_02, String string) {
        if (this.bzq) {
            this.ee("End of configuration.");
            ahu_0 ahu_02 = (ahu_0)this.Pb;
            ape.d(ahu_02);
        }
        qq_02.wb();
    }
}

