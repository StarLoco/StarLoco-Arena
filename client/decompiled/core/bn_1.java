/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Attributes;

/*
 * Renamed from Bn
 */
public class bn_1
extends ka_0 {
    boolean kc = false;

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        Object object = qq_02.wa();
        if (!(object instanceof arN)) {
            this.kc = true;
            this.eg("For element <level>, could not find a logger at the top of execution stack.");
            return;
        }
        arN arN2 = (arN)object;
        String string2 = arN2.getName();
        String string3 = qq_02.subst(attributes.getValue("value"));
        if ("INHERITED".equalsIgnoreCase(string3) || "NULL".equalsIgnoreCase(string3)) {
            arN2.b((rl_2)null);
        } else {
            arN2.b(rl_2.a(string3, rl_2.agc));
        }
        this.ee(string2 + " level set to " + arN2.agr());
    }

    public void a(qq_0 qq_02) {
    }

    public void a(qq_0 qq_02, String string) {
    }
}

