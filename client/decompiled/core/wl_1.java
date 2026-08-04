/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from wL
 */
public abstract class wl_1
implements JG,
ju_0 {
    protected static final Logger a = Logger.getLogger(wl_1.class);
    protected aey_2 avr = null;
    protected mi_2 avs = null;
    protected acl_0 uG;

    protected wl_1() {
    }

    protected void a(mi_2 mi_22, aey_2 aey_22) {
        this.avs = mi_22;
        this.avr = aey_22;
    }

    public void release() {
        if (this.uG != null) {
            this.uG.af(this);
            this.uG = null;
        } else {
            a.error((Object)("Double release de " + this.getClass().toString()));
            this.j();
        }
    }

    public aey_2 Dh() {
        return this.avr;
    }

    public mi_2 Di() {
        return this.avs;
    }

    public void b() {
    }

    public void j() {
        this.avs = null;
        this.avr = null;
    }
}

