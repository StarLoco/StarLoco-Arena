/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Renamed from aCA
 */
public class aca_0
extends bs_1 {
    private static final aca_0 duu = new aca_0();

    public static aca_0 aOq() {
        return duu;
    }

    public ArrayList b(vi_1 vi_12) {
        je_2 je_22 = jk_1.mf().mg();
        jg_0 jg_02 = je_22.nc();
        ArrayList<Object> arrayList = new ArrayList<Object>();
        ll_0 ll_02 = this.dU().pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            if (((ve_0)ll_02.value()).Vk() != vi_12 || jg_02.contains(((ve_0)ll_02.value()).getId())) continue;
            arrayList.add(ll_02.value());
        }
        if (arrayList.size() > 0) {
            if (((ve_0)arrayList.get(0)).eA() != 0) {
                Collections.sort(arrayList, new akQ(this));
            } else {
                Collections.sort(arrayList);
            }
        }
        return arrayList;
    }

    public ve_0 M(ByteBuffer byteBuffer) {
        ve_0 ve_02 = (ve_0)super.d(byteBuffer);
        if (ve_02 != null && ve_02.isUsable()) {
            return new on_2(ve_02);
        }
        return ve_02;
    }

    public void Wb() {
        ll_0 ll_02 = this.dU().pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            ((ve_0)ll_02.value()).setDescription(null);
        }
    }
}

