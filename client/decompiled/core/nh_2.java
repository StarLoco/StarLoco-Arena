/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from nh
 */
public class nh_2
implements hR {
    private yn_2 NV;
    private static final Logger a = Logger.getLogger(nh_2.class);
    private static final rb_0 NW = new rb_0();
    private static nh_2 NX = new nh_2();

    public static nh_2 sa() {
        return NX;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.interactiveElements");
    }

    public void a(mk_1 mk_12) {
        this.NV = new yn_2();
        this.NV.a(asi.values());
        this.NV.a(agr.values());
        try {
            lJ[] lJArray = aly_1.aAQ().a(NW);
            a.trace((Object)("Loading " + lJArray.length + " interactive elements views..."));
            for (lJ lJ2 : lJArray) {
                rb_0 rb_02 = (rb_0)lJ2;
                int n2 = rb_02.getId();
                short s = rb_02.getType();
                int n3 = rb_02.adz();
                int n4 = rb_02.adA();
                byte by = rb_02.PD();
                this.NV.a(n2, s, n3, by, n4);
                if (!a.isTraceEnabled()) continue;
                a.trace((Object)("Loaded view id=" + n2 + " type=" + s + " gfx=" + n3 + " color=" + n4 + " height=" + by));
            }
        }
        catch (Exception exception) {
            a.error((Object)"Erreur lors de la lecture du fichier de vues d'\u00e9l\u00e9ments interactifs", (Throwable)exception);
        }
        me_2.qR().a(this.NV);
        this.NV = null;
        mk_12.b(this);
    }
}

