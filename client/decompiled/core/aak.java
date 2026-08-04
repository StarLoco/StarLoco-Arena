/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class aak
extends ag_2
implements JG {
    protected static final Logger a = Logger.getLogger(aak.class);
    protected acv_1 ceU;
    protected uh_1 ceV;
    protected short ceW;
    private static final acl_0 aU = new ym_0(new nb_2());

    public static aak a(nk nk2, acv_1 acv_12, byte by, uh_1 uh_12, short s) {
        aak aak2;
        try {
            aak2 = (aak)aU.adr();
            aak2.uG = aU;
        }
        catch (Exception exception) {
            a.error((Object)("Erreur lors d'un checkOut sur un message de type ItemExchangerEndEvent : " + exception.getMessage()));
            aak2 = new aak();
        }
        aak2.b(nk2, acv_12, by, uh_12, s);
        return aak2;
    }

    private void b(nk nk2, acv_1 acv_12, byte by, uh_1 uh_12, short s) {
        super.b(nk2, i_0.aP);
        this.ceU = acv_12;
        this.mx = by;
        this.ceV = uh_12;
        this.ceW = s;
    }

    public acv_1 aoS() {
        return this.ceU;
    }

    public uh_1 aoT() {
        return this.ceV;
    }

    public short aoU() {
        return this.ceW;
    }
}

