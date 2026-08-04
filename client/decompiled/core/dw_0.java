/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from dW
 */
public class dw_0
extends ZT {
    private static final acl_0 aU = new ym_0(new yh_1());
    int nA;
    boolean nB;
    public aea_0 nC = new YI(this, 4);

    public dw_0() {
        this.aG();
    }

    public dw_0 gL() {
        dw_0 dw_02;
        try {
            dw_02 = (dw_0)aU.adr();
            dw_02.uG = aU;
        }
        catch (Exception exception) {
            dw_02 = new dw_0();
            dw_02.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un " + this.getClass().getSimpleName() + " : " + exception.getMessage()));
        }
        return dw_02;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        ArrayList<xb_2> arrayList = new ArrayList<xb_2>();
        for (xb_2 xb_23 : this.bWm.PJ()) {
            XV xV = xb_23.ajO();
            if (xV == null || xV.ST() != this.r || this.nB && !xb_23.h(xb_23)) continue;
            arrayList.add(xb_23);
        }
        for (xb_2 xb_23 : arrayList) {
            if (this.nA == 0) continue;
            xb_23.aky();
            --this.nA;
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        int n2 = ((xj_0)this.bWj).Tb().length;
        if (n2 == 0) {
            a.error((Object)("Nombre de param\u00e8tres incorrect dans un " + this.getClass().getSimpleName() + " : " + ((xj_0)this.bWj).Tb().length + "."));
        } else {
            this.r = (int)((xj_0)this.bWj).Tb()[0];
            this.nA = 1 < n2 ? (int)((xj_0)this.bWj).Tb()[1] : -1;
            this.nB = 2 < n2 && (int)((xj_0)this.bWj).Tb()[2] == 1;
        }
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

    public aea_0 gN() {
        return this.nC;
    }
}

