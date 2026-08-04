/*
 * Decompiled with CFR 0.152.
 */
import javax.naming.Context;
import javax.naming.NamingException;
import org.xml.sax.Attributes;

/*
 * Renamed from Mh
 */
public class mh_0
extends ka_0 {
    public static String bti = "env-entry-name";
    public static String btj = "as";

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        Object object;
        int n2 = 0;
        String string2 = attributes.getValue(bti);
        String string3 = attributes.getValue(btj);
        if (dh_2.isEmpty(string2)) {
            object = this.d(qq_02);
            this.eg("[" + bti + "] missing, around " + (String)object);
            ++n2;
        }
        if (dh_2.isEmpty(string3)) {
            object = this.d(qq_02);
            this.eg("[" + btj + "] missing, around " + (String)object);
            ++n2;
        }
        if (n2 != 0) {
            return;
        }
        try {
            object = agb_2.avT();
            String string4 = agb_2.a((Context)object, string2);
            if (dh_2.isEmpty(string4)) {
                this.eg("[" + string2 + "] has null or empty value");
            } else {
                this.ee("Setting context variable [" + string3 + "] to [" + string4 + "]");
                this.Pb.c(string3, string4);
            }
        }
        catch (NamingException namingException) {
            this.eg("Failed to lookup JNDI env-entry [" + string2 + "]");
        }
    }

    public void a(qq_0 qq_02, String string) {
    }
}

