/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aFm
 */
public class afm_2
extends aPk {
    private ArrayList dGS;

    public afm_2(vP vP2, vP vP3, ArrayList arrayList, int n2, int n3, int n4, ys ys2) {
        this.aJ(vP2);
        this.aK(vP3);
        this.dGS = arrayList;
        this.setDelay(n2);
        this.setDuration(n3);
        this.pT(n4);
        this.a(ys2);
    }

    public boolean aS(int n2) {
        if (!super.aS(n2)) {
            return false;
        }
        if (this.amA != null) {
            vP vP2 = (vP)this.eoN;
            vP vP3 = (vP)this.eoO;
            float f = this.amA.b(vP2.Cp(), vP3.Cp(), this.IP, this.wg);
            float f2 = this.amA.b(vP2.Cq(), vP3.Cq(), this.IP, this.wg);
            float f3 = this.amA.b(vP2.Cr(), vP3.Cr(), this.IP, this.wg);
            float f4 = this.amA.b(vP2.getAlpha(), vP3.getAlpha(), this.IP, this.wg);
            vP vP4 = new vP(f, f2, f3, f4);
            for (int j = this.dGS.size() - 1; j >= 0; --j) {
                ((ayi)this.dGS.get(j)).setModulationColor(vP4);
            }
        }
        return true;
    }

    public void ly() {
        for (int j = this.dGS.size() - 1; j >= 0; --j) {
            ((ayi)this.dGS.get(j)).setModulationColor((vP)this.eoO);
        }
        super.ly();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ModulationColorListTween] ").append(this.eoN).append(" -> ").append(this.eoO);
        return stringBuilder.toString();
    }
}

