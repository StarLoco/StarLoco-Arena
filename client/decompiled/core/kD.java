/*
 * Decompiled with CFR 0.152.
 */
public class kD
extends aPk {
    private final vP EX;
    private final boolean EY;

    public kD(vP vP2, vP vP3, ayi ayi2, int n2, int n3, int n4, ys ys2) {
        this(vP2, vP3, ayi2, n2, n3, n4, true, ys2);
    }

    public kD(vP vP2, vP vP3, ayi ayi2, int n2, int n3, int n4, boolean bl2, ys ys2) {
        this.aJ(vP2);
        this.aK(vP3);
        this.a((acw_1)((Object)ayi2));
        this.setDelay(n2);
        this.setDuration(n3);
        this.pT(n4);
        this.a(ys2);
        this.EX = ayi2.getModulationColor();
        this.EY = bl2;
    }

    public boolean aS(int n2) {
        if (!super.aS(n2)) {
            return false;
        }
        if (this.amA == null) {
            return true;
        }
        float f = this.amA.b(((vP)this.eoN).Cp(), ((vP)this.eoO).Cp(), this.IP, this.wg);
        float f2 = this.amA.b(((vP)this.eoN).Cq(), ((vP)this.eoO).Cq(), this.IP, this.wg);
        float f3 = this.amA.b(((vP)this.eoN).Cr(), ((vP)this.eoO).Cr(), this.IP, this.wg);
        float f4 = this.amA.b(((vP)this.eoN).getAlpha(), ((vP)this.eoO).getAlpha(), this.IP, this.wg);
        vP vP2 = new vP(f, f2, f3, f4);
        ((ayi)((Object)this.eoP)).setModulationColor(vP2);
        return true;
    }

    public void ly() {
        if (this.EY) {
            ((ayi)((Object)this.eoP)).setModulationColor(this.EX);
        } else {
            ((ayi)((Object)this.eoP)).setModulationColor((vP)this.eoO);
        }
        super.ly();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ModulationColorTween] ").append(this.eoN).append(" -> ").append(this.eoO);
        return stringBuilder.toString();
    }
}

