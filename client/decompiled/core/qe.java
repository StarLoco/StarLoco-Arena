/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.java.games.joal.AL
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Collection;
import net.java.games.joal.AL;
import org.apache.log4j.Logger;

public class qe
extends nu_1 {
    protected static final Logger a = Logger.getLogger(qe.class);
    private final ArrayList adp = new ArrayList();
    private final ArrayList adq = new ArrayList();
    private static AL cY;
    private static final int[][] adr;
    private final jg_0 ads = new jg_0();
    private float adt = 1.0f;
    private boolean adu = true;

    public qe(String string) {
        super(string);
    }

    public qe(String string, byte by) {
        super(string, by);
    }

    public avE a(auk auk2, boolean bl2, boolean bl3, boolean bl4, long l2) {
        return null;
    }

    public void b(avE avE2) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public or_1 a(auk auk2, float f, qq_1 qq_12, float f2, float f3, float f4, int n2, int n3, boolean bl2, boolean bl3, boolean bl4, float f5, long l2) {
        avE avE2 = this.a(auk2, l2);
        if (avE2 == null) {
            a.error((Object)("le son " + auk2.getDescription() + " ne peut pas \u00eatre pr\u00e9par\u00e9"));
            return null;
        }
        avE2.eq(bl2 && n3 == 0);
        avE2.setReferenceDistance(f2);
        avE2.setMaxDistance(f3);
        avE2.setRolloffFactor(f4 != 0.0f ? f4 : 1.0f);
        avE2.setMaxGain(f);
        avE2.aj(this.getGain());
        avE2.setMute(this.abg());
        if (this.bAV != -1) {
            avE2.mx(this.bAV);
        }
        if (this.bAW) {
            avE2.er(true);
        }
        avE2.aIS();
        or_1 or_12 = or_1.a(avE2, qq_12, f3, bl3, bl4, f5);
        if (bl2 && n3 > 0) {
            or_12.aD(n2, n3);
        }
        ArrayList arrayList = this.adp;
        synchronized (arrayList) {
            this.adq.add(or_12);
        }
        return or_12;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void bM() {
        ArrayList arrayList = this.adp;
        synchronized (arrayList) {
            int n2 = this.adq.size();
            for (int j = 0; j < n2; ++j) {
                or_1 or_12 = (or_1)this.adq.get(j);
                this.adp.add(or_12);
            }
            this.adq.clear();
        }
    }

    public void al(boolean bl2) {
        this.adu = bl2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void G(float f) {
        ArrayList arrayList = this.adp;
        synchronized (arrayList) {
            for (or_1 or_12 : this.adp) {
                or_12.aj(f);
                or_12.abI();
            }
        }
    }

    public void n(float f, float f2) {
    }

    public void o(float f, float f2) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void c(boolean bl2, boolean bl3) {
        ArrayList arrayList = this.adp;
        synchronized (arrayList) {
            for (or_1 or_12 : this.adp) {
                or_12.bZ(bl3);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void ay(long l2) {
        super.ay(l2);
        agv_0 agv_02 = this.dj != null ? this.dj.FA() : agv_0.dIL;
        float f = this.dj != null ? this.dj.FC() : 1.0f;
        int n2 = this.dj != null ? this.dj.zU() : 0;
        this.adt = f;
        ArrayList arrayList = this.adp;
        synchronized (arrayList) {
            int n3;
            int n4 = this.adp.size();
            for (n3 = 0; n3 < n4; ++n3) {
                or_1 or_12 = (or_1)this.adp.get(n3);
                or_12.a(agv_02, f, this.adu, n2);
                if (!or_12.abG()) continue;
                this.ads.add(n3);
                this.bAT.a(or_12.abF());
            }
            n3 = this.ads.size();
            if (n3 > 0) {
                for (n4 = n3 - 1; n4 >= 0; --n4) {
                    this.adp.remove(this.ads.bu(n4));
                }
                this.ads.nl();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void stop() {
        ArrayList arrayList = this.adp;
        synchronized (arrayList) {
            for (or_1 or_12 : this.adp) {
                this.bAT.a(or_12.abF());
            }
            this.adp.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void c(avE avE2) {
        ArrayList arrayList = this.adp;
        synchronized (arrayList) {
            for (int j = this.adp.size() - 1; j >= 0; --j) {
                or_1 or_12 = (or_1)this.adp.get(j);
                if (or_12.abF() != avE2) continue;
                this.adp.remove(j);
                this.bAT.a(avE2);
            }
        }
    }

    public boolean cL(int n2) {
        if (!super.cL(n2)) {
            return false;
        }
        int n3 = this.adp.size();
        for (int j = 0; j < n3; ++j) {
            ((or_1)this.adp.get(j)).abF().mx(n2);
        }
        return true;
    }

    public Collection uZ() {
        throw new UnsupportedOperationException("Pas d'acc\u00e8s direct \u00e0 la liste des sources du FieldSourceGroup");
    }

    static {
        adr = new int[][]{{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {0, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};
    }
}

