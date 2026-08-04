/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import org.xml.sax.Attributes;

/*
 * Renamed from aOu
 */
public class aou_1
extends ka_0 {
    boolean kc = false;

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        this.kc = false;
        Object object = qq_02.wa();
        if (!(object instanceof od_1)) {
            String string2 = "Could not find an AppenderAttachable at the top of execution stack. Near [" + string + "] line " + this.c(qq_02);
            this.kc = true;
            this.eg(string2);
            return;
        }
        od_1 od_12 = (od_1)object;
        String string3 = attributes.getValue("ref");
        if (dh_2.isEmpty(string3)) {
            String string4 = "Missing appender ref attribute in <appender-ref> tag.";
            this.kc = true;
            this.eg(string4);
            return;
        }
        HashMap hashMap = (HashMap)qq_02.wc().get("APPENDER_BAG");
        adr_0 adr_02 = (adr_0)hashMap.get(string3);
        if (adr_02 == null) {
            String string5 = "Could not find an appender named [" + string3 + "]. Did you define it below in the config file?";
            this.kc = true;
            this.eg(string5);
            this.eg("See http://logback.qos.ch/codes.html#appender_order for more details.");
            return;
        }
        this.ee("Attaching appender named [" + string3 + "] to " + od_12);
        od_12.a(adr_02);
    }

    public void a(qq_0 qq_02, String string) {
    }
}

