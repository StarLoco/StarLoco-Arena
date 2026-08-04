/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from alS
 */
public class als_1
extends aih_0
implements cn_1 {
    private static final ym_0 cfT = new ym_0(new ci_1());
    private static Logger a = Logger.getLogger(als_1.class);

    als_1() {
    }

    public void a(OZ oZ) {
        oZ.a(this);
    }

    public static als_1 dP(long l2) {
        als_1 als_12;
        try {
            als_12 = (als_1)cfT.adr();
            als_12.uG = cfT;
        }
        catch (Exception exception) {
            a.warn((Object)("Erreur au checkOut d'un " + als_1.class.getSimpleName()));
            als_12 = new als_1();
        }
        als_12.j(l2);
        return als_12;
    }

    public void release() {
        if (this.uG != null) {
            try {
                this.uG.af(this);
            }
            catch (Exception exception) {
                a.warn((Object)("Erreur au release d'un " + als_1.class.getSimpleName()));
            }
            this.uG = null;
        } else {
            a.error((Object)("Double release de " + this.getClass().toString()));
        }
    }
}

