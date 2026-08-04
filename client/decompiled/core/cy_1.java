/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Cy
 */
public class cy_1
extends mp_2 {
    public boolean a(mv_1 mv_12, yg_0 yg_02, yg_0 yg_03) {
        cp_2 cp_22 = yg_02.amq();
        long[] lArray = cp_22.eJ();
        for (int j = 0; j < cp_22.size(); ++j) {
            gn_0 gn_02 = (gn_0)cp_22.t(lArray[j]);
            if (gn_02.PR() || gn_02.PT() || gn_02.gg().getX() != this.JI[0] || gn_02.gg().getY() != this.JI[1]) continue;
            return true;
        }
        return false;
    }

    public short getType() {
        return qk_1.bHA.tI();
    }
}

