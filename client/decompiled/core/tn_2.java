/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from tN
 */
public class tn_2
implements JG,
Iterator {
    protected static final Logger a = Logger.getLogger(tn_2.class);
    private static ym_0 aoa = new ym_0(new ada_2());
    private alf_1 aob;
    private akz_0 aoc;
    private xb_2 aod = null;
    private xb_2 aoe;
    private boolean aof = false;

    public void b() {
    }

    public void j() {
        this.aoc = null;
        this.aod = null;
        this.aoe = null;
        this.aob = null;
    }

    public static tn_2 a(alf_1 alf_12, akz_0 akz_02, xb_2 xb_22) {
        if (akz_02 != null && xb_22 != null) {
            tn_2 tn_22;
            try {
                tn_22 = (tn_2)aoa.adr();
            }
            catch (Exception exception) {
                tn_22 = new tn_2();
                a.error((Object)("erreur dans le checkOut de " + tn_22.getClass()));
            }
            tn_22.aob = alf_12;
            tn_22.aoc = akz_02;
            tn_22.aoe = xb_22;
            return tn_22;
        }
        if (akz_02 == null) {
            throw new UnsupportedOperationException("checkOut d'un iterator sans liste derri\u00e8re");
        }
        throw new UnsupportedOperationException("checkOut d'un  ChildIterator sans parent");
    }

    public boolean hasNext() {
        this.aof = true;
        if (!this.aoc.hasNext()) {
            return false;
        }
        while (this.aoc.hasNext()) {
            this.aoc.fK();
            this.aod = (xb_2)this.aoc.value();
            if (this.aod.ajZ() != this.aoe) continue;
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
}

