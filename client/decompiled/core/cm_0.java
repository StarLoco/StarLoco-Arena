/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from CM
 */
public class cm_0
implements JG,
Iterator {
    protected static final Logger a = Logger.getLogger(cm_0.class);
    private static final ym_0 aoa = new ym_0(new aro_0());
    private alf_1 aob;
    private akz_0 aoc;
    private xb_2 aod = null;
    private Pi aMN;
    private boolean aof = false;

    public void b() {
    }

    public void j() {
        this.aoc = null;
        this.aod = null;
        this.aMN = null;
        this.aob = null;
    }

    public static cm_0 a(alf_1 alf_12, akz_0 akz_02, Pi pi) {
        if (akz_02 != null && pi != null) {
            cm_0 cm_02;
            try {
                cm_02 = (cm_0)aoa.adr();
            }
            catch (Exception exception) {
                cm_02 = new cm_0();
                a.error((Object)("erreur dans le checkOut de " + cm_02.getClass()));
            }
            cm_02.aob = alf_12;
            cm_02.aoc = akz_02;
            cm_02.aMN = pi;
            return cm_02;
        }
        if (akz_02 == null) {
            throw new UnsupportedOperationException("checkOut d'un iterator sans liste derri\u00e8re");
        }
        throw new UnsupportedOperationException("checkOut d'un  LinkedToEffectContainerIterator sans container");
    }

    public boolean hasNext() {
        this.aof = true;
        if (!this.aoc.hasNext()) {
            return false;
        }
        while (this.aoc.hasNext()) {
            this.aoc.fK();
            this.aod = (xb_2)this.aoc.value();
            if (this.aod.mi() == null || this.aod.mi().iP() != this.aMN.iP() || this.aod.mi().iO() != this.aMN.iO()) continue;
            return true;
        }
        return false;
    }

    public xb_2 zP() {
        if (!this.aof && !this.hasNext()) {
            throw new UnsupportedOperationException("Depassement de liste, cause probable : appel de next() sans v\u00e9rification");
        }
        this.aof = false;
        return this.aod;
    }

    public void remove() {
        this.aoc.remove();
        this.aob.q(this.aod);
    }

    public void release() {
        if (aoa != null) {
            try {
                aoa.af(this);
            }
            catch (Exception exception) {
                a.error((Object)"impossible");
            }
        } else {
            this.j();
        }
    }
}

