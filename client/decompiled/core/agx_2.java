/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.net.URL;
import org.apache.log4j.Logger;

/*
 * Renamed from aGX
 */
public class agx_2 {
    private Logger a = Logger.getLogger(agx_2.class);
    private static agx_2 dJS = new agx_2();

    private agx_2() {
    }

    public ef_1 h(URL uRL) {
        if (uRL == null) {
            return null;
        }
        adz_1 adz_12 = new adz_1();
        String string = uRL.toString();
        long l2 = ej_0.aa(string);
        ef_1 ef_12 = cx_0.JY().a(arX.cQT.iE(), l2, string, adz_12, false);
        if (ef_12 == null) {
            this.a.error((Object)("Probl\u00e8me au chargement de la texture " + uRL));
            aon_2 aon_22 = new aon_2();
            ef_12 = cx_0.JY().a(arX.cQT.iE(), l2, aon_22, adz_12, false);
        }
        return ef_12;
    }

    public ef_1 lo(String string) {
        return if_1.UG().eO(string);
    }

    public ef_1 lp(String string) {
        long l2 = Gr.getLong(string, -1L);
        if (l2 == -1L) {
            return null;
        }
        return cx_0.JY().bt(l2);
    }

    public void aTb() {
    }

    public static agx_2 aTc() {
        return dJS;
    }
}

