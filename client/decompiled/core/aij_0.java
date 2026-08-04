/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;

/*
 * Renamed from aIj
 */
public class aij_0
implements aho_0 {
    private static aij_0 dOX = new aij_0();
    public static final byte dOY = 1;
    public static final byte dOZ = 2;
    public static final byte dPa = 3;
    public static final byte dPb = 4;
    public static final byte dPc = 5;
    public static final byte dPd = 6;
    public static final byte dPe = 7;
    private final ArrayList dPf = new ArrayList();
    private final ArrayList dPg = new ArrayList();
    private final ArrayList dPh = new ArrayList();
    private final ArrayList dPi = new ArrayList();
    private final ArrayList dPj = new ArrayList();
    private final ArrayList dPk = new ArrayList();
    private final ArrayList dPl = new ArrayList();
    private int dPm = 0;
    public static final String dPn = "cheapSets";
    public static final String dPo = "expensiveSets";
    public static final String dPp = "specialSets";
    public static final String dPq = "evolutionSets";
    public static final String dPr = "zaapSets";
    public static final String dPs = "fireworkSets";
    public static final String dPt = "fightSets";
    public static final String[] ce = new String[]{"cheapSets", "expensiveSets", "specialSets", "evolutionSets", "zaapSets", "fireworkSets", "fightSets"};

    private aij_0() {
        Object[] objectArray = ayc_0.aLE().aLF().getValues();
        int n2 = objectArray.length;
        for (int j = 0; j < n2; ++j) {
            fe_1 fe_12 = (fe_1)objectArray[j];
            ArrayList arrayList = fe_12.Pe();
            if (arrayList.isEmpty()) continue;
            wy_2 wy_22 = (wy_2)arrayList.get(0);
            if (wy_22.tj() == aMK.dYy) {
                this.dPi.add(fe_12);
                continue;
            }
            if (wy_22.tj() == aMK.dYA) {
                this.dPl.add(fe_12);
                continue;
            }
            if (wy_22.tj() != aMK.dYu && wy_22.tj() != aMK.dYb && wy_22.tj() != aMK.dYv && wy_22.tj() != aMK.dYz && wy_22.tj() != aMK.dYC && wy_22.tj() != aMK.dYD && wy_22.tj() != aMK.dYE) {
                if (fe_12.getValue() < 100000) {
                    this.dPf.add(fe_12);
                    continue;
                }
                this.dPg.add(fe_12);
                continue;
            }
            this.dPh.add(fe_12);
            if (wy_22.tj() == aMK.dYu) {
                this.dPj.add(fe_12);
            }
            if (wy_22.tj() != aMK.dYz) continue;
            this.dPk.add(fe_12);
        }
        Collections.sort(this.dPf, new akb_0(this));
        Collections.sort(this.dPg, new akc_1(this));
        Collections.sort(this.dPh, new akm_0(this));
        Collections.sort(this.dPi, new ako_0(this));
        Collections.sort(this.dPj, new aKp(this));
        Collections.sort(this.dPk, new akr_1(this));
        Collections.sort(this.dPl, new aki_2(this));
        azs_0.aLV().g("tomeManager", this);
    }

    public static aij_0 aUF() {
        return dOX;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(dPn)) {
            return this.dPf.toArray();
        }
        if (string.equals(dPo)) {
            return this.dPg.toArray();
        }
        if (string.equals(dPp)) {
            return this.dPh.toArray();
        }
        if (string.equals(dPq)) {
            return this.dPi.toArray();
        }
        if (string.equals(dPr)) {
            return this.dPj.toArray();
        }
        if (string.equals(dPs)) {
            return this.dPk.toArray();
        }
        if (string.equals(dPt)) {
            return this.dPl.toArray();
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

    public void t(ud_1 ud_12) {
        ++this.dPm;
        byte by = (byte)ud_12.getSelectedTabIndex();
        switch (by) {
            case 1: {
                this.dPm = Math.min(this.dPf.size() - 1, this.dPm);
                azs_0.aLV().g("coachManagement.currentSet", this.dPf.get(this.dPm));
                break;
            }
            case 2: {
                this.dPm = Math.min(this.dPg.size() - 1, this.dPm);
                azs_0.aLV().g("coachManagement.currentSet", this.dPg.get(this.dPm));
                break;
            }
            case 3: {
                this.dPm = Math.min(this.dPh.size() - 1, this.dPm);
                azs_0.aLV().g("coachManagement.currentSet", this.dPh.get(this.dPm));
                break;
            }
            case 4: {
                this.dPm = Math.min(this.dPi.size() - 1, this.dPm);
                azs_0.aLV().g("coachManagement.currentSet", this.dPi.get(this.dPm));
                break;
            }
            case 7: {
                this.dPm = Math.min(this.dPj.size() - 1, this.dPm);
                azs_0.aLV().g("coachManagement.currentSet", this.dPj.get(this.dPm));
                break;
            }
            case 6: {
                this.dPm = Math.min(this.dPk.size() - 1, this.dPm);
                azs_0.aLV().g("coachManagement.currentSet", this.dPk.get(this.dPm));
                break;
            }
            case 5: {
                this.dPm = Math.min(this.dPl.size() - 1, this.dPm);
                azs_0.aLV().g("coachManagement.currentSet", this.dPl.get(this.dPm));
                break;
            }
        }
    }

    public void u(ud_1 ud_12) {
        --this.dPm;
        this.dPm = Math.max(0, this.dPm);
        byte by = (byte)ud_12.getSelectedTabIndex();
        switch (by) {
            case 1: {
                azs_0.aLV().g("coachManagement.currentSet", this.dPf.get(this.dPm));
                break;
            }
            case 2: {
                azs_0.aLV().g("coachManagement.currentSet", this.dPg.get(this.dPm));
                break;
            }
            case 3: {
                azs_0.aLV().g("coachManagement.currentSet", this.dPh.get(this.dPm));
                break;
            }
            case 4: {
                azs_0.aLV().g("coachManagement.currentSet", this.dPi.get(this.dPm));
                break;
            }
            case 7: {
                azs_0.aLV().g("coachManagement.currentSet", this.dPj.get(this.dPm));
                break;
            }
            case 6: {
                azs_0.aLV().g("coachManagement.currentSet", this.dPk.get(this.dPm));
                break;
            }
            case 5: {
                azs_0.aLV().g("coachManagement.currentSet", this.dPl.get(this.dPm));
            }
        }
    }

    public void a(fe_1 fe_12, int n2) {
        int n3 = 0;
        ArrayList arrayList = null;
        switch (n2) {
            case 1: {
                arrayList = this.dPf;
                break;
            }
            case 2: {
                arrayList = this.dPg;
                break;
            }
            case 3: {
                arrayList = this.dPh;
                break;
            }
            case 4: {
                arrayList = this.dPi;
                break;
            }
            case 7: {
                arrayList = this.dPj;
                break;
            }
            case 6: {
                arrayList = this.dPk;
                break;
            }
            case 5: {
                arrayList = this.dPl;
            }
        }
        if (arrayList != null) {
            for (fe_1 fe_13 : arrayList) {
                if (fe_12.getId() == fe_13.getId()) break;
                ++n3;
            }
            this.dPm = n3;
            azs_0.aLV().g("coachManagement.currentSet", fe_12);
        }
    }

    public void aUG() {
        fe_1 fe_12 = null;
        for (fe_1 fe_13 : this.dPh) {
            if (((wy_2)fe_13.Pe().get(0)).tj() != aMK.dYu) continue;
            fe_12 = fe_13;
            break;
        }
        azs_0.aLV().g("coachManagement.currentSet", fe_12);
    }

    public void oL(int n2) {
        this.dPm = 0;
        switch (n2) {
            case 1: {
                azs_0.aLV().g("coachManagement.currentSet", this.dPf.get(0));
                break;
            }
            case 2: {
                azs_0.aLV().g("coachManagement.currentSet", this.dPg.get(0));
                break;
            }
            case 3: {
                azs_0.aLV().g("coachManagement.currentSet", this.dPh.get(0));
                break;
            }
            case 4: {
                azs_0.aLV().g("coachManagement.currentSet", this.dPi.get(0));
                break;
            }
            case 5: {
                azs_0.aLV().g("coachManagement.currentSet", this.dPl.get(0));
            }
        }
    }

    public void aUH() {
        this.dPm = Math.max(this.dPm - 1, 0);
        azs_0.aLV().g("coachManagement.currentSet", this.dPi.get(this.dPm));
    }

    public void aUI() {
        this.dPm = Math.min(this.dPm + 1, this.dPi.size() - 1);
        azs_0.aLV().g("coachManagement.currentSet", this.dPi.get(this.dPm));
    }

    public void c(fe_1 fe_12) {
        azs_0.aLV().g("coachManagement.currentSet", fe_12 != null ? fe_12 : (fe_1)this.dPi.get(0));
    }
}

