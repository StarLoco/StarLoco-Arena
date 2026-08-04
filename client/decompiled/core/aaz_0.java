/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;

/*
 * Renamed from aAz
 */
public class aaz_0
extends apF {
    public static HashMap dpm = new HashMap();

    public aaz_0(int n2, String string, float[] fArray, String string2, boolean bl2) {
        super(n2, string, fArray, string2, bl2);
    }

    public void a(zc_0 zc_02) {
        String string;
        super.a(zc_02);
        if (zc_02 != null && (string = zc_02.getSourceName()) != null) {
            atn_0 atn_02 = (atn_0)dpm.get(string);
            if (atn_02 != null) {
                atn_02.setText(zc_02.getMessage());
                atn_02.EL();
            } else {
                atn_02 = new atn_0(zc_02.getMessage());
                dpm.put(string, atn_02);
            }
            mT mT2 = bd_1.Is().bb(zc_02.GI());
            if (mT2 != null) {
                atn_02.c(mT2);
                wj_2.Df().a(atn_02);
            }
        }
    }
}

