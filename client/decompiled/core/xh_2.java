/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import org.xml.sax.Attributes;

/*
 * Renamed from xH
 */
public class xh_2
extends ka_0 {
    adr_0 azq;
    private boolean kc = false;

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        this.azq = null;
        this.kc = false;
        String string2 = attributes.getValue("class");
        if (dh_2.isEmpty(string2)) {
            this.eg("Missing class name for appender. Near [" + string + "] line " + this.c(qq_02));
            this.kc = true;
            return;
        }
        try {
            this.ee("About to instantiate appender of type [" + string2 + "]");
            this.azq = (adr_0)dh_2.a(string2, adr_0.class, this.Pb);
            this.azq.a(this.Pb);
            String string3 = qq_02.subst(attributes.getValue("name"));
            if (dh_2.isEmpty(string3)) {
                this.ef("No appender name given for appender of type " + string2 + "].");
            } else {
                this.azq.setName(string3);
                this.ee("Naming appender as [" + string3 + "]");
            }
            HashMap hashMap = (HashMap)qq_02.wc().get("APPENDER_BAG");
            hashMap.put(string3, this.azq);
            qq_02.C(this.azq);
        }
        catch (Exception exception) {
            this.kc = true;
            this.e("Could not create an Appender of type [" + string2 + "].", exception);
            throw new vf_1(exception);
        }
    }

    public void a(qq_0 qq_02, String string) {
        Object object;
        if (this.kc) {
            return;
        }
        if (this.azq instanceof mt_2) {
            this.azq.start();
        }
        if ((object = qq_02.wa()) != this.azq) {
            this.ef("The object at the of the stack is not the appender named [" + this.azq.getName() + "] pushed earlier.");
        } else {
            this.ee("Popping appender named [" + this.azq.getName() + "] from the object stack");
            qq_02.wb();
        }
    }
}

