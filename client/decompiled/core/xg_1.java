/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from xG
 */
public class xg_1
extends tj_0 {
    public xg_1(agj_1 agj_12, agj_1 agj_13, adg_2 adg_22, int n2, int n3, ys ys2) {
        super(agj_12, agj_13, adg_22, n2, n3, ys2);
    }

    public boolean aS(int n2) {
        if (!super.aS(n2)) {
            return false;
        }
        if (this.amA != null) {
            agj_1 agj_12 = (agj_1)this.eoN;
            agj_1 agj_13 = (agj_1)this.eoO;
            int n3 = (int)this.amA.b(agj_12.width, agj_13.width, this.IP, this.wg);
            int n4 = (int)this.amA.b(agj_12.height, agj_13.height, this.IP, this.wg);
            this.getWidget().setSize(n3, n4, true);
        }
        return true;
    }

    public void ly() {
        agj_1 agj_12 = (agj_1)this.eoO;
        this.getWidget().setSize(agj_12.width, agj_12.height);
        super.ly();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ResizeTween] ").append(this.eoN).append(" -> ").append(this.eoO);
        return stringBuilder.toString();
    }
}

