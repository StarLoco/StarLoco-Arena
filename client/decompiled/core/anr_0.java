/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aNR
 */
public class anr_0 {
    private static final Logger a = Logger.getLogger(anr_0.class);
    private static final boolean DEBUG = true;
    private static final String dZU = "scenario/";
    private final ano_2 dTR = new ano_2();
    private final lb_0 dZV = new lb_0();
    private static final anr_0 dZW = new anr_0();

    private anr_0() {
    }

    public static anr_0 aXN() {
        return dZW;
    }

    public String pv(int n2) {
        assert (Ky.WG().getPath() != null);
        return String.format("%s%d%s", dZU, n2, Ky.WG().getExtension());
    }

    public JX j(int n2, boolean bl2) {
        JX jX = null;
        int n3 = this.dTR.get(n2);
        if (n3 > 0 && (jX = Ky.WG().gz(n3)) != null) {
            return jX;
        }
        if (bl2) {
            jX = Ky.WG().a(this.pv(n2), (mp_0[])null, false);
            if (jX != null) {
                this.dTR.bz(n2, jX.getId());
                return jX;
            }
            a.trace((Object)("On a pas trouv\u00e9 le script du sc\u00e9nario " + n2));
        }
        return jX;
    }

    public void a(int n2, int n3, long[] lArray, boolean bl2) {
        JX jX = this.j(n2, true);
        if (jX == null) {
            a.error((Object)("runEvent : impossible de trouver un script d'id de sc\u00e9nario " + n2));
            return;
        }
        jJ[] jJArray = new jJ[lArray.length];
        for (int j = 0; j < jJArray.length; ++j) {
            jJArray[j] = new jJ(lArray[j]);
        }
        String string = null;
        string = bl2 ? "reward_" + n2 + "_" + n3 : "event_" + n2 + "_" + n3;
        jX.a(string.replaceAll("-", "_"), jJArray, new amd_0[0]);
    }

    public void b(int n2, String string, String string2) {
        this.dZV.c(n2, new age_1(this, string, string2));
    }

    public void clear() {
        this.dZV.clear();
        this.dTR.clear();
    }
}

