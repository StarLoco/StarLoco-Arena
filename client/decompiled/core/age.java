/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.Logger;

public class age
implements JG,
Iterator {
    protected static final Logger a = Logger.getLogger(age.class);
    private static final ym_0 aoa = new ym_0(new hn_0());
    private alf_1 aob;
    private akz_0 aoc;
    private xb_2 aod = null;
    private kc_2 bOO;
    private ArrayList ctF = new ArrayList();
    private boolean ctG = false;
    private boolean aof = false;

    public void b() {
    }

    public void j() {
        for (int j = 0; j < this.ctF.size(); ++j) {
            this.aob.q((xb_2)this.ctF.get(j));
        }
        this.ctF.clear();
        this.aoc = null;
        this.aod = null;
        this.bOO = null;
        this.aob = null;
        this.ctG = false;
    }

    public static age a(alf_1 alf_12, akz_0 akz_02, kc_2 kc_22, boolean bl2) {
        if (akz_02 != null && kc_22 != null) {
            age age2;
            try {
                age2 = (age)aoa.adr();
            }
            catch (Exception exception) {
                age2 = new age();
                a.error((Object)("erreur dans le checkOut de " + age2.getClass()));
            }
            age2.aob = alf_12;
            age2.aoc = akz_02;
            age2.bOO = kc_22;
            age2.ctG = bl2;
            return age2;
        }
        if (akz_02 == null) {
            throw new UnsupportedOperationException("checkOut d'un iterator sans liste derri\u00e8re");
        }
        throw new UnsupportedOperationException("checkOut d'un  LinkedToEffectUserIterator sans effectUser");
    }

    public boolean hasNext() {
        this.aof = true;
        if (!this.aoc.hasNext()) {
            return false;
        }
        while (this.aoc.hasNext()) {
            this.aoc.fK();
            this.aod = (xb_2)this.aoc.value();
            if ((this.aod.ajQ() != this.bOO || this.ctG) && this.aod.ajR() != this.bOO) continue;
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
        this.ctF.add(this.aod);
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

