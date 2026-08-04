/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from sR
 */
public class sr_0
implements aho_0 {
    private static final Logger a = Logger.getLogger(sr_0.class);
    private static abk_0 amd;
    private static abk_0 ame;
    private static abk_0 amf;

    public static abk_0 a(ss_2 ss_22) {
        switch (ss_22) {
            case bLU: {
                return amd;
            }
            case bLV: {
                return ame;
            }
            case bLW: {
                return amf;
            }
        }
        return null;
    }

    public void a(ro_2 ro_22, boolean bl2) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible d'enregistrer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return;
        }
        abk_02.t(string, bl2);
        aor_1.aYh().a(this, string);
    }

    public void a(ro_2 ro_22, double d) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible d'enregistrer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return;
        }
        abk_02.c(string, d);
        aor_1.aYh().a(this, string);
    }

    public void a(ro_2 ro_22, float f) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible d'enregistrer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return;
        }
        abk_02.b(string, f);
        aor_1.aYh().a(this, string);
    }

    public void a(ro_2 ro_22, int n2) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible d'enregistrer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return;
        }
        abk_02.r(string, n2);
        aor_1.aYh().a(this, string);
    }

    public void a(ro_2 ro_22, long l2) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible d'enregistrer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return;
        }
        abk_02.g(string, l2);
        aor_1.aYh().a(this, string);
    }

    public void a(ro_2 ro_22, String string) {
        abk_0 abk_02 = ro_22.wp();
        String string2 = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible d'enregistrer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string2 + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return;
        }
        abk_02.T(string2, string);
        aor_1.aYh().a(this, string2);
    }

    public void b(ro_2 ro_22, boolean bl2) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible d'enregistrer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return;
        }
        abk_02.s(string, bl2);
        aor_1.aYh().a(this, string);
    }

    public void b(ro_2 ro_22, double d) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible d'enregistrer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return;
        }
        abk_02.b(string, d);
        aor_1.aYh().a(this, string);
    }

    public void b(ro_2 ro_22, float f) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible d'enregistrer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return;
        }
        abk_02.a(string, f);
        aor_1.aYh().a(this, string);
    }

    public void b(ro_2 ro_22, int n2) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible d'enregistrer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return;
        }
        abk_02.q(string, n2);
        aor_1.aYh().a(this, string);
    }

    public void b(ro_2 ro_22, long l2) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible d'enregistrer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return;
        }
        abk_02.f(string, l2);
        aor_1.aYh().a(this, string);
    }

    public void b(ro_2 ro_22, String string) {
        abk_0 abk_02 = ro_22.wp();
        String string2 = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible d'enregistrer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string2 + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return;
        }
        abk_02.S(string2, string);
        aor_1.aYh().a(this, string2);
    }

    public boolean a(ro_2 ro_22) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible de r\u00c3\u00a9cup\u00c3\u00a9rer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return false;
        }
        return abk_02.getBoolean(string);
    }

    public double b(ro_2 ro_22) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible de r\u00c3\u00a9cup\u00c3\u00a9rer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return 0.0;
        }
        return abk_02.getDouble(string);
    }

    public float c(ro_2 ro_22) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible de r\u00c3\u00a9cup\u00c3\u00a9rer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return 0.0f;
        }
        return abk_02.getFloat(string);
    }

    public int d(ro_2 ro_22) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible de r\u00c3\u00a9cup\u00c3\u00a9rer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return 0;
        }
        return abk_02.getInt(string);
    }

    public long e(ro_2 ro_22) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible de r\u00c3\u00a9cup\u00c3\u00a9rer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return 0L;
        }
        return abk_02.getLong(string);
    }

    public String f(ro_2 ro_22) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible de r\u00c3\u00a9cup\u00c3\u00a9rer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return null;
        }
        return abk_02.getString(string);
    }

    public boolean g(ro_2 ro_22) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible de r\u00c3\u00a9cup\u00c3\u00a9rer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return false;
        }
        return abk_02.hi(string);
    }

    public double h(ro_2 ro_22) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible de r\u00c3\u00a9cup\u00c3\u00a9rer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return 0.0;
        }
        return abk_02.hj(string);
    }

    public float i(ro_2 ro_22) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible de r\u00c3\u00a9cup\u00c3\u00a9rer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return 0.0f;
        }
        return abk_02.hk(string);
    }

    public int j(ro_2 ro_22) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible de r\u00c3\u00a9cup\u00c3\u00a9rer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return 0;
        }
        return abk_02.hl(string);
    }

    public long k(ro_2 ro_22) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible de r\u00c3\u00a9cup\u00c3\u00a9rer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return 0L;
        }
        return abk_02.hm(string);
    }

    public String l(ro_2 ro_22) {
        abk_0 abk_02 = ro_22.wp();
        String string = ro_22.getKey();
        if (abk_02 == null) {
            a.error((Object)("[CODE-Prefs] Impossible de r\u00c3\u00a9cup\u00c3\u00a9rer la pr\u00c3\u00a9f\u00c3\u00a9rence de clef=" + string + " \u00c3\u00a0 cet endroit, le preferenceStore n'est pas initialis\u00c3\u00a9."));
            return null;
        }
        return abk_02.hn(string);
    }

    protected void za() {
    }

    protected void zb() {
    }

    protected void zc() {
        this.b((ro_2)akz_1.cEu, akr_2.aVQ().getLocale().getLanguage());
        this.b((ro_2)akz_1.cEw, 1);
        this.b((ro_2)akz_1.cEx, 1);
        this.b((ro_2)akz_1.cEy, 1);
        this.b((ro_2)akz_1.cEz, false);
        this.b((ro_2)akz_1.cEA, false);
        this.b((ro_2)akz_1.cEB, false);
        this.b((ro_2)akz_1.cED, true);
        this.b((ro_2)akz_1.cEC, 3000);
        this.b((ro_2)akz_1.cEE, 2);
    }

    public void b(String string, Object object) {
    }

    public Object getFieldValue(String string) {
        if (string.equals(akz_1.cEu.getKey())) {
            return this.f(akz_1.cEu);
        }
        if (string.equals(akz_1.cEw.getKey())) {
            return Float.valueOf(this.c(akz_1.cEw));
        }
        if (string.equals(akz_1.cEx.getKey())) {
            return Float.valueOf(this.c(akz_1.cEx));
        }
        if (string.equals(akz_1.cEy.getKey())) {
            return Float.valueOf(this.c(akz_1.cEy));
        }
        if (string.equals(akz_1.cEz.getKey())) {
            return this.a(akz_1.cEz);
        }
        if (string.equals(akz_1.cEA.getKey())) {
            return this.a(akz_1.cEA);
        }
        if (string.equals(akz_1.cEB.getKey())) {
            return this.a(akz_1.cEB);
        }
        if (string.equals(akz_1.cEE.getKey())) {
            return Float.valueOf(this.c(akz_1.cEE));
        }
        return null;
    }

    public String[] getFields() {
        akz_1[] akz_1Array = akz_1.values();
        String[] stringArray = new String[akz_1Array.length];
        int n2 = 0;
        for (akz_1 akz_12 : akz_1Array) {
            stringArray[n2] = akz_12.getKey();
            ++n2;
        }
        return stringArray;
    }

    public boolean l(String string) {
        return false;
    }

    public void c(String string, Object object) {
    }

    public void a(String string, Object object) {
    }

    protected void co(String string) {
        aor_1.aYh().a(this, string);
    }

    public void zd() {
        if (amd != null) {
            amd.zd();
        }
        if (ame != null) {
            ame.zd();
        }
        if (amf != null) {
            amf.zd();
        }
    }

    public static abk_0 ze() {
        return amf;
    }

    public static abk_0 zf() {
        return ame;
    }

    public static abk_0 zg() {
        return amd;
    }

    public void a(abk_0 abk_02) {
        amd = abk_02;
        if (amd != null) {
            this.zc();
        }
    }

    public void b(abk_0 abk_02) {
        ame = abk_02;
        if (ame != null) {
            this.za();
        }
    }

    public void c(abk_0 abk_02) {
        amf = abk_02;
        if (amf != null) {
            this.zb();
        }
    }
}

