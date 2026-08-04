/*
 * Decompiled with CFR 0.152.
 */
import java.util.EnumSet;

public class Af
extends ZT {
    private static final acl_0 aU = new ym_0(new TB());
    private jb_2 aGP;

    public Af() {
        this.a(EnumSet.of(hz.vY));
        this.aG();
    }

    public Af Hd() {
        Af af;
        try {
            af = (Af)aU.adr();
            af.uG = aU;
        }
        catch (Exception exception) {
            af = new Af();
            af.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un CardEquipped : " + exception.getMessage()));
        }
        return af;
    }

    public static Af a(ea_0 ea_02, gn_0 gn_02, jb_2 jb_22) {
        Af af;
        try {
            af = (Af)aU.adr();
            af.uG = aU;
        }
        catch (Exception exception) {
            af = new Af();
            af.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un CardEquipped : " + exception.getMessage()));
        }
        af.aW = mh_2.bwb.getId();
        af.bWr = ((ZT)mh_2.bwb.getObject()).Oz();
        af.aG();
        af.bWl = gn_02;
        af.aGP = jb_22;
        af.bWm = null;
        af.ahI = -1;
        af.bdv = ea_02;
        return af;
    }

    public void aG() {
        super.aG();
    }

    public void a(xb_2 xb_22, boolean bl2) {
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
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
}

