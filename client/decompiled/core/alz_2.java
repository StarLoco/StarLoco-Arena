/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aLz
 */
public class alz_2
implements atG {
    private static Logger a = Logger.getLogger(alz_2.class);
    private final qd_0 asx;

    public alz_2(qd_0 qd_02) {
        this.asx = qd_02;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 2: {
                apg_1 apg_12 = (apg_1)pr_02;
                this.asx.q(apg_12.vK());
                return false;
            }
            case 4: {
                nu_2 nu_22 = (nu_2)pr_02;
                if (!nu_22.ss()) {
                    a.error((Object)"Reco impossible");
                }
                return false;
            }
            case 6: {
                asu asu2 = (asu)pr_02;
                this.asx.w(asu2.aFt());
                return false;
            }
            case 8: {
                oq_1 oq_12 = (oq_1)pr_02;
                this.asx.r(oq_12.tR());
                return false;
            }
            case 100: {
                es_0 es_02 = (es_0)pr_02;
                short s = es_02.ha();
                short s2 = es_02.hy();
                long l2 = es_02.hz();
                if (s == -1) {
                    this.asx.vP();
                } else {
                    this.asx.a(s, s2, l2);
                }
                return false;
            }
            case 102: {
                ja_0 ja_02 = (ja_0)pr_02;
                switch (ja_02.Vj()) {
                    case 0: {
                        apk_0.aDz().trace(ja_02.getMessage());
                        break;
                    }
                    case 1: {
                        apk_0.aDz().log(ja_02.getMessage());
                        break;
                    }
                    case 2: {
                        apk_0.aDz().err(ja_02.getMessage());
                        break;
                    }
                    default: {
                        a.error((Object)("Type de message inconnu " + ja_02.Vj()));
                    }
                }
                return false;
            }
            case 105: {
                Ve ve = (Ve)pr_02;
                switch (ve.Vj()) {
                    case 0: {
                        apk_0.aDz().trace(ve.getMessage());
                        break;
                    }
                    case 1: {
                        apk_0.aDz().log(ve.getMessage());
                        break;
                    }
                    case 2: {
                        apk_0.aDz().err(ve.getMessage());
                        break;
                    }
                    case 3: {
                        apk_0.aDz().b(ve.getMessage(), ve.adA());
                        break;
                    }
                    default: {
                        a.error((Object)("Type de message inconnu " + ve.Vj()));
                    }
                }
                return false;
            }
            case 103: {
                dm_0 dm_02 = (dm_0)pr_02;
                this.asx.cV(dm_02.wd());
                return false;
            }
        }
        return true;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }
}

