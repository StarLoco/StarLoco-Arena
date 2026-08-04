/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Attributes;

/*
 * Renamed from azQ
 */
public class azq_0
extends ka_0 {
    public void a(qq_0 qq_02, String string, Attributes attributes) {
    }

    public void b(qq_0 qq_02, String string) {
        String string2 = qq_02.subst(string);
        this.ee("Setting logger context name as [" + string2 + "]");
        try {
            this.Pb.setName(string2);
        }
        catch (IllegalStateException illegalStateException) {
            this.e("Failed to rename context [" + this.Pb.getName() + "] as [" + string2 + "]", illegalStateException);
        }
    }

    public void a(qq_0 qq_02, String string) {
    }
}

