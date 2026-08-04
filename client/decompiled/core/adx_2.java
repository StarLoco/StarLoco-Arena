/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aDx
 */
class adx_2
implements apx {
    final /* synthetic */ int dxu;
    final /* synthetic */ ArrayList dxv;
    final /* synthetic */ alf_1 dxw;

    adx_2(alf_1 alf_12, int n2, ArrayList arrayList) {
        this.dxw = alf_12;
        this.dxu = n2;
        this.dxv = arrayList;
    }

    public boolean t(xb_2 xb_22) {
        if (xb_22.mi() != null && xb_22.mi().iP() == this.dxu) {
            this.dxv.add(xb_22);
        }
        return true;
    }
}

