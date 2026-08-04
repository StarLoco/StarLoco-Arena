/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Renamed from JE
 */
public class je_1
extends azk {
    private static final je_1 bma = new je_1();

    public static je_1 Wa() {
        return bma;
    }

    public ArrayList gu(int n2) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        akz_0 akz_02 = this.aLQ().eI();
        while (akz_02.hasNext()) {
            akz_02.fK();
            if (((yp_2)akz_02.value()).iQ() != n2) continue;
            arrayList.add(akz_02.value());
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public yp_2 D(ByteBuffer byteBuffer) {
        yp_2 yp_22 = (yp_2)super.E(byteBuffer);
        zd_2 zd_22 = null;
        if (yp_22 != null) {
            zd_22 = new zd_2(yp_22);
        }
        return zd_22;
    }

    public void Wb() {
        akz_0 akz_02 = this.aLQ().eI();
        while (akz_02.hasNext()) {
            akz_02.fK();
            ((yp_2)akz_02.value()).setDescription(null);
        }
    }
}

