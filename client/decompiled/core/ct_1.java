/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ct
 */
public class ct_1
extends mp_2 {
    public boolean a(mv_1 mv_12, yg_0 yg_02, yg_0 yg_03) {
        int n2 = 0;
        int n3 = 1;
        if (this.JI.length > 1) {
            n3 = this.JI[1];
        }
        cp_2 cp_22 = yg_03.amq();
        long[] lArray = cp_22.eJ();
        for (int j = 0; j < cp_22.size(); ++j) {
            gn_0 gn_02 = (gn_0)cp_22.t(lArray[j]);
            if (gn_02.NY().lV() != this.JI[0] || !gn_02.PR() && !gn_02.PT() || ++n2 < n3) continue;
            return true;
        }
        return false;
    }

    public short getType() {
        return qk_1.bHC.tI();
    }
}

