/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;

/*
 * Renamed from FE
 */
public class fe_1
extends aqy_0
implements aho_0,
Comparable {
    public static String xX = "name";
    public static String aog = "description";
    public static String arR = "id";
    public static String aVa = "collection";
    public static String aVb = "collectionForEvolution";
    public static String aVc = "completed";
    public static String aVd = "setValue";
    public static String aVe = "size";
    public static String aVf = "completion";
    public static String aVg = "isHalfComplete";
    public static String aVh = "isComplete";
    public static String aVi = "halfSetEffects";
    public static String aVj = "fullSetEffects";
    public static String[] ce = new String[]{xX, aog, arR, aVa, aVc, aVd, aVi, aVj};

    public fe_1(int n2, akw_0[] akw_0Array) {
        super(n2, akw_0Array);
    }

    public String getName() {
        return aon_0.aYc().a(25, this.getId(), new Object[0]);
    }

    public String getDescription() {
        return aon_0.aYc().a(26, this.getId(), new Object[0]);
    }

    public int getValue() {
        oj_0[] oj_0Array = this.aEb();
        int n2 = 0;
        for (int j = 0; j < oj_0Array.length; ++j) {
            n2 += oj_0Array[j].getValue();
        }
        return n2;
    }

    public ArrayList Pe() {
        ArrayList<wy_2> arrayList = new ArrayList<wy_2>();
        ky_2 ky_22 = apN.aDK().Ln().yD();
        oj_0[] oj_0Array = this.aEb();
        if (oj_0Array != null) {
            for (oj_0 oj_02 : oj_0Array) {
                wy_2 wy_22 = (wy_2)ky_22.bW(oj_02.jf());
                if (wy_22 == null) {
                    wy_22 = new wy_2(oj_02.jf());
                    wy_22.q((short)1);
                }
                arrayList.add(wy_22);
            }
            Collections.sort(arrayList, new alu_1(this));
        }
        return arrayList;
    }

    public boolean Pf() {
        return this.a(apN.aDK().Ln().aQn());
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        ArrayList<akw_0> arrayList;
        if (string.equals(xX)) {
            return this.getName();
        }
        if (string.equals(aog)) {
            return this.getDescription();
        }
        if (string.equals(arR)) {
            return this.getId();
        }
        if (string.equals(aVa)) {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(null);
            arrayList2.add(null);
            arrayList2.addAll(this.Pe());
            wy_2[] wy_2Array = new wy_2[arrayList2.size()];
            arrayList2.toArray(wy_2Array);
            return wy_2Array;
        }
        if (string.equals(aVb)) {
            arrayList = new ArrayList();
            if (this.aEb()[0].tj() == aMK.dYy) {
                arrayList.addAll(this.Pe());
                wy_2[] wy_2Array = new wy_2[arrayList.size()];
                arrayList.toArray(wy_2Array);
                return wy_2Array;
            }
        }
        if (string.equals(aVc)) {
            return this.Pf();
        }
        if (string.equals(aVd)) {
            return this.getValue();
        }
        if (string.equals(aVe)) {
            return this.size();
        }
        if (string.equals(aVf)) {
            arrayList = apN.aDK().Ln().aQn();
            byte by = 0;
            for (oj_0 oj_02 : this.aEb()) {
                if (((ky_2)((Object)arrayList)).bT(oj_02.jf()) == null && ((ky_2)((Object)arrayList)).bW(oj_02.jf()) == null && ((ky_2)((Object)arrayList)).bT(-oj_02.jf()) == null && ((ky_2)((Object)arrayList)).bW(-oj_02.jf()) == null) continue;
                by = (byte)(by + 1);
            }
            return by;
        }
        if (string.equals(aVg)) {
            return (double)this.Pg() >= Math.floor((float)this.size() / 2.0f);
        }
        if (string.equals(aVh)) {
            return this.Pg() == this.size();
        }
        if (string.equals(aVi)) {
            arrayList = new ArrayList<akw_0>();
            for (int j = 0; j < this.UD.length; ++j) {
                if (this.UD[j].aAm() >= this.size()) continue;
                arrayList.add(this.UD[j]);
            }
            return asf_0.b(arrayList.toArray(new akw_0[arrayList.size()]));
        }
        if (string.equals(aVj)) {
            arrayList = new ArrayList();
            for (int j = 0; j < this.UD.length; ++j) {
                if (this.UD[j].aAm() != this.size()) continue;
                arrayList.add(this.UD[j]);
            }
            return asf_0.b(arrayList.toArray(new akw_0[arrayList.size()]));
        }
        return null;
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

    public void CP() {
        azs_0.aLV().a((aho_0)this, ce);
    }

    public int compareTo(Object object) {
        fe_1 fe_12 = (fe_1)object;
        return this.getName().compareTo(fe_12.getName());
    }

    public byte Pg() {
        en_1 en_12 = apN.aDK().Ln().yD().pH();
        byte by = 0;
        for (wy_2 wy_22 : en_12) {
            if (((xj)wy_22.NR()).tm() != this.getId()) continue;
            by = (byte)(by + 1);
        }
        return by;
    }
}

