/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;

public class aFM {
    public static ma_1 au(String string, String string2) {
        try {
            ti_1 ti_12 = new ti_1();
            ti_12.l(string, string2);
            return ti_12;
        }
        catch (IOException iOException) {
            try {
                zq_0 zq_02 = new zq_0();
                zq_02.l(string, string2);
                return zq_02;
            }
            catch (IOException iOException2) {
                return null;
            }
        }
    }

    public static af_1 b(ma_1 ma_12) {
        if (ma_12.getClass() == zq_0.class) {
            oa_0 oa_02 = new oa_0();
            oa_02.a((zq_0)ma_12);
            return oa_02;
        }
        if (ma_12.getClass() == ti_1.class) {
            aAU aAU2 = new aAU();
            aAU2.a((ti_1)ma_12);
            return aAU2;
        }
        return null;
    }
}

