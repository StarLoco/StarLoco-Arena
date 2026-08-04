/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from AG
 */
public class ag_2
implements JG {
    protected static final Logger a = Logger.getLogger(ag_2.class);
    protected i_0 aHk = null;
    protected nk aHl = null;
    protected byte mx = (byte)-1;
    protected acl_0 uG;
    private static final acl_0 aU = new ym_0(new ami_0());

    public static ag_2 a(nk nk2, i_0 i_02) {
        ag_2 ag_22;
        try {
            ag_22 = (ag_2)aU.adr();
            ag_22.uG = aU;
        }
        catch (Exception exception) {
            a.error((Object)("Erreur lors d'un checkOut sur un message de type ItemExchangerEvent : " + exception.getMessage()));
            ag_22 = new ag_2();
        }
        ag_22.b(nk2, i_02);
        return ag_22;
    }

    protected ag_2() {
    }

    protected void b(nk nk2, i_0 i_02) {
        this.aHl = nk2;
        this.aHk = i_02;
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

    public i_0 Ho() {
        return this.aHk;
    }

    public nk Hp() {
        return this.aHl;
    }

    public void N(byte by) {
        this.mx = by;
    }

    public byte fY() {
        return this.mx;
    }

    public void b() {
    }

    public void j() {
        this.aHl = null;
        this.aHk = null;
        this.mx = (byte)-1;
    }
}

