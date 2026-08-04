/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from adm
 */
public class adm_1 {
    protected static final Logger a = Logger.getLogger(adm_1.class);
    protected apc_0 cmq;
    protected long cmr;

    public adm_1() {
        this.reset();
    }

    public void a(apc_0 apc_02) {
        this.cmq = apc_02;
        this.cmr = apc_02 != null ? apc_02.getId() : 0L;
    }

    public void reset() {
        this.cmq = null;
        this.cmr = 0L;
    }

    public boolean asi() {
        if (this.cmq == null) {
            return false;
        }
        if (this.cmr == 0L) {
            return false;
        }
        return this.cmq.getId() == this.cmr;
    }

    public apc_0 asj() {
        return this.cmq;
    }

    public long ask() {
        return this.cmr;
    }
}

