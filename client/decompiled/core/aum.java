/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public class aum
extends ZT {
    private static final acl_0 aU = new ym_0(new mo_0());

    public aum aHq() {
        aum aum2;
        try {
            aum2 = (aum)aU.adr();
            aum2.uG = aU;
        }
        catch (Exception exception) {
            aum2 = new aum();
            aum2.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return aum2;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        Vector<xb_2> vector = new Vector<xb_2>();
        if (this.bWm instanceof gn_0) {
            for (xb_2 xb_23 : this.bWm.PJ()) {
                if (!(xb_23 instanceof co_0)) continue;
                vector.add(xb_23);
            }
        }
        for (xb_2 xb_23 : vector) {
            xb_23.aky();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
    }

    public void aK() {
        super.aK();
    }

    public boolean aH() {
        return false;
    }

    public boolean aI() {
        return true;
    }

    public boolean aJ() {
        return false;
    }

    public boolean gM() {
        return false;
    }
}

