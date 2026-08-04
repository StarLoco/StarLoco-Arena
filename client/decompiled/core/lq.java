/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class lq
extends ag_2
implements JG {
    protected static final Logger a = Logger.getLogger(lq.class);
    private oj_1 GZ;
    private static final acl_0 aU = new ym_0(new ate());

    public static lq a(nk nk2, oj_1 oj_12) {
        lq lq2;
        try {
            lq2 = (lq)aU.adr();
            lq2.uG = aU;
        }
        catch (Exception exception) {
            a.error((Object)("Erreur lors d'un checkOut sur un message de type ItemExchangerEndEvent : " + exception.getMessage()));
            lq2 = new lq();
        }
        lq2.b(nk2, oj_12);
        return lq2;
    }

    protected lq() {
        this.aHk = null;
        this.aHl = null;
    }

    protected void b(nk nk2, oj_1 oj_12) {
        super.b(nk2, i_0.aR);
        this.GZ = oj_12;
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

    public oj_1 qj() {
        return this.GZ;
    }

    public void b() {
        super.b();
    }

    public void j() {
        super.j();
        this.GZ = null;
    }
}

