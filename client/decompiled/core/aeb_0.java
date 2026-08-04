/*
 * Decompiled with CFR 0.152.
 */
import java.util.BitSet;

/*
 * Renamed from aEB
 */
public class aeb_0
implements hR {
    private static final aeb_0 dBt = new aeb_0();
    private static final rf_2 dBu = new rf_2();

    public static aeb_0 aQB() {
        return dBt;
    }

    private aeb_0() {
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.staticEffect");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(dBu)) {
            int n22;
            rf_2 rf_22 = (rf_2)lJ2;
            int n3 = rf_22.getId();
            String string = rf_22.xx();
            int n4 = rf_22.eA();
            int n5 = rf_22.xp();
            int[] nArray = rf_22.xt();
            boolean bl2 = rf_22.xy();
            agf_2 agf_22 = zg_1.a(n5, nArray, (short)0);
            if (agf_22 == null) {
                throw new NullPointerException("AOE incorrecte");
            }
            int[] nArray2 = rf_22.xu();
            BitSet bitSet = new BitSet();
            if (nArray2 != null) {
                for (int n22 : nArray2) {
                    bitSet.set(n22);
                }
            }
            int[] nArray3 = rf_22.xv();
            BitSet bitSet2 = new BitSet();
            if (nArray3 != null) {
                for (int n6 : nArray3) {
                    bitSet2.set(n6);
                }
            }
            int n7 = rf_22.xq();
            n22 = rf_22.xr();
            String string2 = rf_22.getType();
            int[] nArray4 = rf_22.xw();
            float[] fArray = new float[2];
            if (nArray4.length == 2) {
                fArray[0] = nArray4[0];
                fArray[1] = nArray4[1];
            } else {
                fArray[0] = 0.0f;
                fArray[1] = 0.0f;
            }
            int n8 = rf_22.xs();
            String string3 = null;
            er_1 er_12 = new er_1(n3, agf_22, bitSet, bitSet2, n7, n4, n22, fArray, n8, string3, string, bl2);
            for (Ht ht : rf_22.eC()) {
                xj_0 xj_02 = abw_2.c(ht);
                er_12.b(xj_02);
                WF.ajj().b(xj_02);
            }
            if (string2.equals("TRAP")) {
                ame_1.aWP().a(er_12);
            }
            if (!string2.equals("SPECIAL")) continue;
            ame_1.aWP().b(er_12);
        }
        mk_12.b(this);
    }
}

