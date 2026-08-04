/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from MU
 */
public class mu_2
implements hR {
    private static mu_2 byj = new mu_2();

    public static mu_2 Zx() {
        return byj;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.console");
    }

    public void a(mk_1 mk_12) {
        apk_0.aDz().a(ao_0.aU());
        apk_0.aDz().a(new ajc_0());
        if (mx_0.Km == null) {
            throw new Exception("Impossible de trouver la d\u00e9finition des commandes de console.");
        }
        apk_0.aDz().f(mx_0.Km);
        apk_0.aDz().f(mx_0.Kn);
        mk_12.b(this);
    }
}

