/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aKK
 */
public final class akk_0 {
    private static Logger bnO = Logger.getLogger((String)"ParticleScripts");
    private static akk_0 dTP = new akk_0();
    private static final String dTQ = "particles/";
    private final ano_2 dTR = new ano_2();
    private mp_0[] dTS;

    private akk_0() {
    }

    public static akk_0 aVL() {
        return dTP;
    }

    public void f(mp_0[] mp_0Array) {
        this.dTS = mp_0Array;
    }

    public String pg(int n2) {
        assert (Ky.WG().getPath() != null);
        return String.format("%s%d%s", dTQ, n2, Ky.WG().getExtension());
    }

    private JX gz(int n2) {
        JX jX;
        int n3 = this.dTR.get(n2);
        if (n3 > 0 && (jX = Ky.WG().gz(n3)) != null) {
            return jX;
        }
        if (n3 == -1) {
            return null;
        }
        jX = Ky.WG().a(this.pg(n2), this.dTS, true);
        this.dTR.bz(n2, jX != null ? jX.getId() : -1);
        return jX;
    }

    public boolean d(int n2, int[] nArray) {
        jJ[] jJArray = this.a(n2, nArray, "playAps");
        if (jJArray == null) {
            return false;
        }
        if (jJArray.length == 0) {
            return false;
        }
        jJ jJ2 = jJArray[0];
        if (jJ2.np() != aos_1.elV) {
            return false;
        }
        return (Boolean)jJArray[0].getValue();
    }

    private jJ[] a(int n2, int[] nArray, String string) {
        JX jX = this.gz(n2);
        if (jX == null) {
            return null;
        }
        jJ[] jJArray = new jJ[nArray.length];
        for (int j = 0; j < jJArray.length; ++j) {
            jJArray[j] = new jJ(nArray[j]);
        }
        return jX.a(string, jJArray, new amd_0[0]);
    }

    public void clear() {
        this.dTR.clear();
    }
}

