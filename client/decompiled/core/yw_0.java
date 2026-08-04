/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Yw
 */
class yw_0
implements JG {
    protected static final Logger a = Logger.getLogger(yw_0.class);
    private long caY;
    private long caZ;
    Runnable cba;
    private int cbb;
    private boolean cbc;
    private static final acl_0 uG = new ym_0(new adp());

    private yw_0() {
    }

    static yw_0 amB() {
        yw_0 yw_02;
        try {
            yw_02 = (yw_0)uG.adr();
            yw_02.cbc = true;
        }
        catch (Exception exception) {
            yw_02 = new yw_0();
            yw_02.cbc = false;
        }
        return yw_02;
    }

    void release() {
        try {
            if (this.cbc) {
                uG.af(this);
            }
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
    }

    public void b() {
        this.caZ = -1L;
        this.caY = -1L;
        this.cba = null;
        this.cbb = 0;
    }

    public void j() {
        this.caZ = -1L;
        this.caY = -1L;
        this.cba = null;
        this.cbb = 0;
    }

    public long amC() {
        return this.caY;
    }

    public void dm(long l2) {
        this.caY = l2;
    }

    public long amD() {
        return this.caZ;
    }

    public void dn(long l2) {
        this.caZ = l2;
    }

    public Runnable amE() {
        return this.cba;
    }

    public void c(Runnable runnable) {
        this.cba = runnable;
    }

    public int amF() {
        return this.cbb;
    }

    public void jf(int n2) {
        this.cbb = n2;
    }

    /* synthetic */ yw_0(adp adp2) {
        this();
    }
}

