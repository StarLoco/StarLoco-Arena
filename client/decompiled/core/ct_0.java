/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Attributes;

/*
 * Renamed from cT
 */
public class ct_0
extends ka_0 {
    public static final String kb = "level";
    boolean kc = false;
    arN kd;

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        this.kc = false;
        this.kd = null;
        ahu_0 ahu_02 = (ahu_0)this.Pb;
        String string2 = attributes.getValue("name");
        if (dh_2.isEmpty(string2)) {
            this.kc = true;
            String string3 = this.d(qq_02);
            String string4 = "No 'name' attribute in element " + string + ", around " + string3;
            this.eg(string4);
            return;
        }
        this.kd = ahu_02.lw(string2);
        String string5 = qq_02.subst(attributes.getValue(kb));
        if (!dh_2.isEmpty(string5)) {
            if ("INHERITED".equalsIgnoreCase(string5) || "NULL".equalsIgnoreCase(string5)) {
                this.ee("Setting level of logger [" + string2 + "] to null, i.e. INHERITED");
                this.kd.b((rl_2)null);
            } else {
                rl_2 rl_22 = rl_2.bH(string5);
                this.ee("Setting level of logger [" + string2 + "] to " + rl_22);
                this.kd.b(rl_22);
            }
        }
        if (!dh_2.isEmpty("additivity")) {
            boolean bl2 = dh_2.toBoolean(attributes.getValue("additivity"), true);
            this.ee("Setting additivity of logger [" + string2 + "] to " + bl2);
            this.kd.dU(bl2);
        }
        qq_02.C(this.kd);
    }

    public void a(qq_0 qq_02, String string) {
        if (this.kc) {
            return;
        }
        Object object = qq_02.wa();
        if (object != this.kd) {
            this.ef("The object on the top the of the stack is not " + this.kd + " pushed earlier");
            this.ef("It is: " + object);
        } else {
            qq_02.wb();
        }
    }

    public void a(qq_0 qq_02) {
    }
}

