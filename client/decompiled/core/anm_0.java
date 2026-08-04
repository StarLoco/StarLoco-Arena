/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aNM
 */
public class anm_0
extends Vb
implements AW {
    private static boolean DEBUG = false;
    private static Logger a = Logger.getLogger(anm_0.class);

    public void bind() {
        cW.fd().b(this);
        super.bind();
    }

    public void b() {
    }

    public void j() {
        this.reset();
    }

    public void a(aaf_2 aaf_22) {
        zG zG2 = (zG)aaf_22;
        anm_0 anm_02 = (anm_0)zG2.api();
        if (anm_02.aib() == null) {
            ph_1 ph_12 = null;
            try {
                ph_12 = cW.F(zG2.getFileName());
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
            }
            if (ph_12 != null) {
                anm_02.b(ph_12);
            }
            if (DEBUG) {
                a.info((Object)("reload de la Texture " + zG.a(zG2)));
            }
        }
    }

    public void b(aaf_2 aaf_22) {
        zG zG2 = (zG)aaf_22;
        anm_0 anm_02 = (anm_0)zG2.api();
        anm_02.reset();
        if (DEBUG) {
            a.info((Object)("unload de la Texture " + zG.a(zG2)));
        }
    }

    public long HV() {
        ph_1 ph_12 = this.aib();
        if (ph_12 != null) {
            return ph_12.uc();
        }
        return 0L;
    }
}

