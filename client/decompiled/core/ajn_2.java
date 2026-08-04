/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

/*
 * Renamed from ajn
 */
class ajn_2
implements Comparator {
    ajn_2() {
    }

    public int a(iz_0 iz_02, iz_0 iz_03) {
        rd_1 rd_12 = iz_02.OV;
        rd_1 rd_13 = iz_03.OV;
        if (iz_02 instanceof th_2) {
            rd_12 = ((th_2)iz_02).agh();
        }
        if (iz_03 instanceof th_2) {
            rd_13 = ((th_2)iz_03).agh();
        }
        return rd_12.f(rd_13);
    }
}

