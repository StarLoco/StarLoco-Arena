/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aax
 */
public class aax_0
extends aih_0
implements cn_1 {
    private static final ym_0 cfT = new ym_0(new ajc_1());
    private static Logger a = Logger.getLogger(aax_0.class);

    aax_0() {
    }

    public void a(OZ oZ) {
        oZ.a(this);
    }

    public static aax_0 dr(long l2) {
        aax_0 aax_02;
        try {
            aax_02 = (aax_0)cfT.adr();
            aax_02.uG = cfT;
        }
        catch (Exception exception) {
            a.warn((Object)("Erreur au checkOut d'un " + aax_0.class.getSimpleName()));
            aax_02 = new aax_0();
        }
        aax_02.j(l2);
        return aax_02;
    }

    public void release() {
        if (this.uG != null) {
            try {
                this.uG.af(this);
            }
            catch (Exception exception) {
                a.warn((Object)("Erreur au release d'un " + aax_0.class.getSimpleName()));
            }
            this.uG = null;
        } else {
            a.error((Object)("Double release de " + this.getClass().toString()));
        }
    }
}

