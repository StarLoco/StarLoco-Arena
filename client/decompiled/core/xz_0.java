/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;

/*
 * Renamed from XZ
 */
public class xz_0
extends zK
implements aho_0 {
    private static xz_0 bZU = new xz_0();
    public static final String bZV = "allFighters";
    public static final String bZW = "fightersOnBench";
    public static final String bZX = "graveyardFighters";
    public static final String bZY = "legendaryFighters";
    public static final String bZZ = "legendaryFightersOnBench";
    public static final String caa = "teamLeague";
    public static final String cab = "graveyardValue";
    public static final String cac = "legendaryValue";
    public static final String[] ce = new String[]{"allFighters", "fightersOnBench", "graveyardFighters", "teamLeague", "graveyardValue", "legendaryValue", "legendaryFighters", "legendaryFightersOnBench"};
    public static final String[] oT = new String[ce.length + zK.ce.length];

    public static xz_0 amc() {
        return bZU;
    }

    public xz_0() {
        azs_0.aLV().g("evolutionTeam", this);
        this.bk((short)99);
    }

    public String[] getFields() {
        return oT;
    }

    public Object getFieldValue(String string) {
        if (string.equals("fighters")) {
            ArrayList<ee_2> arrayList = new ArrayList<ee_2>();
            aba_0 aba_02 = this.afE();
            for (long l2 : aba_02.eJ()) {
                ee_2 ee_22 = adY.atu().dz(l2);
                if (ee_22 == null || ee_22.NB() != 0 && ee_22.NB() != 2) continue;
                arrayList.add(ee_22);
            }
            Collections.sort(arrayList, new anj_0(this));
            return arrayList.toArray();
        }
        if (string.equals(bZW)) {
            ArrayList<ee_2> arrayList = new ArrayList<ee_2>();
            aba_0 aba_03 = this.afE();
            for (long l3 : aba_03.eJ()) {
                ee_2 ee_23 = adY.atu().dz(l3);
                if (ee_23 == null || ee_23.NB() != 1) continue;
                arrayList.add(ee_23);
            }
            return arrayList.toArray();
        }
        if (string.equals(bZV)) {
            return this.amd().toArray();
        }
        if (string.equals(bZX)) {
            return this.amf().toArray();
        }
        if (string.equals(cab)) {
            return this.ami();
        }
        if (string.equals(cac)) {
            return this.amj();
        }
        if (string.equals(bZY)) {
            return this.amg().toArray();
        }
        if (string.equals(bZZ)) {
            return this.amh().toArray();
        }
        if (string.equals(caa)) {
            int n2 = 0;
            int n3 = 0;
            short s = 0;
            int n4 = 0;
            aba_0 aba_04 = this.afE();
            for (long l4 : aba_04.eJ()) {
                ee_2 ee_24 = adY.atu().dz(l4);
                if (ee_24 == null || ee_24.NB() != 0) continue;
                int n5 = ee_24.Ny();
                n2 += n5;
                ++n4;
                short s2 = nr_0.cs(n5);
                n3 += s2;
                if (s2 <= s) continue;
                s = s2;
            }
            return nr_0.a(n2, n3, n4, s);
        }
        return super.getFieldValue(string);
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }

    public void b(sw_1 sw_12) {
        if (sw_12 != null) {
            bZU.b(sw_12.cd());
        }
    }

    public int getValue() {
        zy_0 zy_02 = new zy_0();
        int n2 = 0;
        long[] lArray = this.afE().eJ();
        for (int j = 0; j < lArray.length; ++j) {
            ee_2 ee_22 = adY.atu().dz(lArray[j]);
            if (ee_22 == null || ee_22.NB() != 0) continue;
            ee_22.PI();
            n2 += ee_22.Oo();
            if (!zy_02.K(ee_22.NY().lV())) {
                zy_02.e(ee_22.NY().lV(), (byte)1);
                continue;
            }
            int n3 = zy_02.H(ee_22.NY().lV());
            n3 = (byte)(n3 + 1);
            zy_02.e(ee_22.NY().lV(), (byte)n3);
        }
        for (byte by : zy_02.GE()) {
            n2 += jn_1.gm(by);
        }
        return n2;
    }

    public ArrayList amd() {
        ArrayList<ee_2> arrayList = new ArrayList<ee_2>();
        aba_0 aba_02 = this.afE();
        for (long l2 : aba_02.eJ()) {
            ee_2 ee_22 = adY.atu().dz(l2);
            if (ee_22 == null) continue;
            arrayList.add(ee_22);
        }
        return arrayList;
    }

    public ArrayList ame() {
        ArrayList<ee_2> arrayList = new ArrayList<ee_2>();
        aba_0 aba_02 = this.afE();
        for (long l2 : aba_02.eJ()) {
            ee_2 ee_22 = adY.atu().dz(l2);
            if (ee_22 == null || ee_22.NB() != 0) continue;
            arrayList.add(ee_22);
        }
        return arrayList;
    }

    public ArrayList amf() {
        ArrayList<ee_2> arrayList = new ArrayList<ee_2>();
        aba_0 aba_02 = this.afE();
        for (long l2 : aba_02.eJ()) {
            ee_2 ee_22 = adY.atu().dz(l2);
            if (ee_22 == null || ee_22.NB() != 3) continue;
            arrayList.add(ee_22);
        }
        return arrayList;
    }

    public ArrayList amg() {
        ArrayList<ee_2> arrayList = new ArrayList<ee_2>();
        aba_0 aba_02 = this.afE();
        for (long l2 : aba_02.eJ()) {
            ee_2 ee_22 = adY.atu().dz(l2);
            if (ee_22 == null || ee_22.NB() != 4) continue;
            arrayList.add(ee_22);
        }
        return arrayList;
    }

    public ArrayList amh() {
        ArrayList<ee_2> arrayList = new ArrayList<ee_2>();
        aba_0 aba_02 = this.afE();
        for (long l2 : aba_02.eJ()) {
            ee_2 ee_22 = adY.atu().dz(l2);
            if (ee_22 == null || ee_22.NB() != 5) continue;
            arrayList.add(ee_22);
        }
        return arrayList;
    }

    public int ami() {
        zy_0 zy_02 = new zy_0();
        int n2 = 0;
        long[] lArray = this.afE().eJ();
        for (int j = 0; j < lArray.length; ++j) {
            ee_2 ee_22 = adY.atu().dz(lArray[j]);
            if (ee_22 == null || ee_22.NB() != 3) continue;
            ee_22.PI();
            n2 += ee_22.Oo();
            if (!zy_02.K(ee_22.NY().lV())) {
                zy_02.e(ee_22.NY().lV(), (byte)1);
                continue;
            }
            byte by = zy_02.H(ee_22.NY().lV());
            by = (byte)(by + 1);
            zy_02.e(ee_22.NY().lV(), by);
        }
        return n2;
    }

    public int amj() {
        zy_0 zy_02 = new zy_0();
        int n2 = 0;
        long[] lArray = this.afE().eJ();
        for (int j = 0; j < lArray.length; ++j) {
            ee_2 ee_22 = adY.atu().dz(lArray[j]);
            if (ee_22 == null || ee_22.NB() != 4) continue;
            ee_22.PI();
            n2 += ee_22.Oo();
            if (!zy_02.K(ee_22.NY().lV())) {
                zy_02.e(ee_22.NY().lV(), (byte)1);
                continue;
            }
            byte by = zy_02.H(ee_22.NY().lV());
            by = (byte)(by + 1);
            zy_02.e(ee_22.NY().lV(), by);
        }
        return n2;
    }

    static {
        System.arraycopy(ce, 0, oT, 0, ce.length);
        System.arraycopy(zK.ce, 0, oT, ce.length, zK.ce.length);
    }
}

