/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.BitSet;

/*
 * Renamed from cu
 */
public class cu_2
extends ZT {
    private static final acl_0 aU = new ym_0(new any_1());

    public cu_2() {
        this.aG();
    }

    public cu_2 eK() {
        cu_2 cu_22;
        try {
            cu_22 = (cu_2)aU.adr();
            cu_22.uG = aU;
        }
        catch (Exception exception) {
            cu_22 = new cu_2();
            cu_22.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return cu_22;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        ArrayList<xb_2> arrayList = new ArrayList<xb_2>();
        for (xb_2 xb_23 : this.bWm.PJ()) {
            if (xb_23.mi() == null) continue;
            switch (xb_23.mi().iP()) {
                case 13: {
                    if (xb_23 instanceof ds_1) break;
                    arrayList.add(xb_23);
                    break;
                }
                case 3: {
                    break;
                }
            }
        }
        for (xb_2 xb_23 : arrayList) {
            xb_23.aky();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        this.r = 0;
    }

    public void aG() {
        super.aG();
        this.bWt.set(221);
    }

    public BitSet eL() {
        return super.eL();
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

