/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aHz
 */
public class ahz_1 {
    protected static final Logger a = Logger.getLogger(ahz_1.class);
    private static final ahz_1 dME = new ahz_1();
    private final aaO dMF = new aaO();
    private long deX = 0L;
    private static final acl_0 dMG = new ym_0(new vs_0());

    public static ahz_1 aUa() {
        return dME;
    }

    public long aUb() {
        if (this.deX == Long.MAX_VALUE) {
            this.deX = 0L;
        }
        return this.deX++;
    }

    public synchronized avE ew(long l2) {
        try {
            avE avE2 = (avE)dMG.adr();
            avE2.ep(true);
            avE2.dV(l2 == -1L ? this.aUb() : l2);
            this.dMF.c(avE2.alE(), avE2);
            return avE2;
        }
        catch (Exception exception) {
            a.error((Object)"Exception lev\u00e9e lors du checkOut d'une source audio : ", (Throwable)exception);
            return null;
        }
    }

    public synchronized void e(avE avE2) {
        try {
            if (avE2 != null) {
                avE2.ep(false);
                this.dMF.u(avE2.alE());
                dMG.af(avE2);
            }
        }
        catch (Exception exception) {
            a.error((Object)"Exception lev\u00e9e lors du release d'une source audio : ", (Throwable)exception);
        }
    }

    public synchronized avE ex(long l2) {
        return (avE)this.dMF.t(l2);
    }

    public avE aUc() {
        byte by = -1;
        avE avE2 = null;
        for (int j = this.dMF.size() - 1; j >= 0; --j) {
            avE avE3 = (avE)this.dMF.jx(j);
            if (avE3.abc() <= by) continue;
            avE2 = avE3;
            by = avE3.abc();
        }
        return avE2;
    }
}

