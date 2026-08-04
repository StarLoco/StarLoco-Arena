/*
 * Decompiled with CFR 0.152.
 */
import java.util.List;

class U
implements apx {
    final /* synthetic */ List bp;
    final /* synthetic */ adY bo;

    U(adY adY2, List list) {
        this.bo = adY2;
        this.bp = list;
    }

    public boolean a(ee_2 ee_22) {
        boolean bl2 = false;
        Object[] objectArray = bs_0.IF().IH().getValues();
        int n2 = objectArray.length;
        for (int j = 0; j < n2; ++j) {
            sw_1 sw_12 = (sw_1)objectArray[j];
            if (!sw_12.m(ee_22.getId())) continue;
            bl2 = true;
            break;
        }
        if (xz_0.amc().afE().m(ee_22.getId())) {
            bl2 = true;
        }
        if (!bl2) {
            this.bp.add(ee_22);
        }
        return true;
    }
}

