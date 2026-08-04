/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Attributes;

/*
 * Renamed from bi
 */
public class bi_1
extends ka_0 {
    private static final String fu = "port";
    private static final Integer fv = 4321;

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        String string2 = attributes.getValue(fu);
        Integer n2 = null;
        if (string2 == null) {
            n2 = fv;
        } else {
            try {
                n2 = Integer.valueOf(string2);
            }
            catch (NumberFormatException numberFormatException) {
                this.eg("Port " + string2 + " in ConsolePlugin config is not a correct number");
            }
        }
        ahu_0 ahu_02 = (ahu_0)qq_02.QK();
        avi_0 avi_02 = new avi_0();
        avi_02.a(ahu_02);
        avi_02.ew(true);
        avi_02.setRemoteHost("localhost");
        avi_02.setPort(n2);
        avi_02.start();
        arN arN2 = ahu_02.lw("root");
        arN2.a(avi_02);
        this.ee("Sending LoggingEvents to the plugin using port " + n2);
    }

    public void a(qq_0 qq_02, String string) {
    }
}

