/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import org.xml.sax.Attributes;

public class aED
extends ka_0 {
    boolean kc = false;

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        this.kc = false;
        String string2 = attributes.getValue("conversionWord");
        String string3 = attributes.getValue("converterClass");
        if (dh_2.isEmpty(string2)) {
            this.kc = true;
            String string4 = "No 'conversionWord' attribute in <conversionRule>";
            this.eg(string4);
            return;
        }
        if (dh_2.isEmpty(string3)) {
            this.kc = true;
            String string5 = "No 'converterClass' attribute in <conversionRule>";
            qq_02.eg(string5);
            return;
        }
        try {
            HashMap<String, String> hashMap = (HashMap<String, String>)this.Pb.getObject("PATTERN_RULE_REGISTRY");
            if (hashMap == null) {
                hashMap = new HashMap<String, String>();
                this.Pb.d("PATTERN_RULE_REGISTRY", hashMap);
            }
            this.ee("registering conversion word " + string2 + " with class [" + string3 + "]");
            hashMap.put(string2, string3);
        }
        catch (Exception exception) {
            this.kc = true;
            String string6 = "Could not add conversion rule to PatternLayout.";
            this.eg(string6);
        }
    }

    public void a(qq_0 qq_02, String string) {
    }

    public void a(qq_0 qq_02) {
    }
}

