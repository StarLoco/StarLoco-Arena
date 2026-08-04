/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;

public class zK
extends sw_1
implements aho_0 {
    public static final String xX = "name";
    public static final String aFO = "selectedFighters";
    public static final String aFP = "fighters";
    public static final String Oj = "value";
    public static final String aFQ = "valueDetails";
    public static final String aFR = "budgetDetails";
    public static final String aFS = "isEditable";
    public static final String aFT = "totalVictories";
    public static final String aFU = "totalDefeats";
    public static final String aFV = "consecutiveVictories";
    public static final String STRENGTH = "strength";
    public static final String aFW = "level";
    public static final String aFX = "isBestTeam";
    public static final String aFY = "coachs";
    public static final String aFZ = "isMirroredTeam";
    public static final String aGa = "icon";
    public static final String aGb = "iconColor";
    public static final String aGc = "background";
    public static final String aGd = "backgroundColor";
    public static final String[] ce = new String[]{"name", "selectedFighters", "fighters", "value", "valueDetails", "budgetDetails", "isEditable", "totalVictories", "totalDefeats", "consecutiveVictories", "strength", "isBestTeam", "coachs", "isMirroredTeam", "icon", "background"};

    public int getValue() {
        zy_0 zy_02 = new zy_0();
        int n2 = 0;
        long[] lArray = this.afE().eJ();
        for (int j = 0; j < lArray.length; ++j) {
            ee_2 ee_22 = adY.atu().dz(lArray[j]);
            if (ee_22 == null) continue;
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

    public int GK() {
        zy_0 zy_02 = new zy_0();
        int n2 = 0;
        long[] lArray = this.afE().eJ();
        for (int j = 0; j < lArray.length; ++j) {
            ee_2 ee_22 = adY.atu().dz(lArray[j]);
            if (ee_22 == null) continue;
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

    public void setName(String string) {
        boolean bl2 = !string.equals(this.getName());
        super.setName(string);
        if (bl2) {
            this.bk((short)-1);
        }
    }

    public void b(String string, Object object) {
    }

    public Object getFieldValue(String string) {
        float[] fArray;
        if (string.equals(xX)) {
            return this.getName();
        }
        if (string.equals(aFO)) {
            ArrayList<ee_2> arrayList = new ArrayList<ee_2>();
            long[] lArray = this.afE().eJ();
            for (int j = 0; j < lArray.length; ++j) {
                ee_2 ee_22 = adY.atu().dz(lArray[j]);
                if (ee_22 == null) continue;
                arrayList.add(ee_22);
            }
            Collections.sort(arrayList, new HQ(this));
            return arrayList.toArray();
        }
        if (string.equals(aFP)) {
            if (this.afK()) {
                ArrayList<ee_2> arrayList = new ArrayList<ee_2>();
                aba_0 aba_02 = this.afE();
                for (long l2 : aba_02.eJ()) {
                    ee_2 ee_23 = adY.atu().dz(l2);
                    if (ee_23 == null) continue;
                    arrayList.add(ee_23);
                }
                Collections.sort(arrayList, new hr_1(this));
                return arrayList.toArray();
            }
            long[] lArray = this.afF().adg();
            ArrayList<Object[]> arrayList = new ArrayList<Object[]>();
            for (int j = 0; j < lArray.length; ++j) {
                qa_2 qa_22 = this.cF(lArray[j]);
                ArrayList<ee_2> arrayList2 = new ArrayList<ee_2>(qa_22.size());
                if (qa_22.size() > 0) {
                    for (long l3 : qa_22.adg()) {
                        ee_2 ee_24 = adY.atu().dz(l3);
                        if (ee_24 == null) continue;
                        arrayList2.add(ee_24);
                    }
                }
                Collections.sort(arrayList2, new HL(this));
                arrayList.add(arrayList2.toArray());
            }
            return arrayList.toArray();
        }
        if (string.equals(Oj)) {
            return this.getValue();
        }
        if (string.equals(aFQ)) {
            int n2 = this.GK();
            int n3 = this.getValue();
            if (n2 > 0) {
                return n3 - n2 + "+" + n2;
            }
            return n3;
        }
        if (string.equals(aFR)) {
            int n4 = this.GK();
            int n5 = this.afE().size() * 600;
            int n6 = this.getValue();
            String string2 = aon_0.aYc().getString("teamManagement.fightersCost", n5);
            string2 = string2 + System.getProperty("line.separator") + aon_0.aYc().getString("teamManagement.equipmentAndSpellCost", n6 - n4 - n5);
            string2 = string2 + System.getProperty("line.separator") + aon_0.aYc().getString("teamManagement.breedOvercost", n4);
            string2 = string2 + System.getProperty("line.separator") + aon_0.aYc().getString("teamManagement.teamValue", n6);
            return string2;
        }
        if (string.equals(aFS)) {
            return this.isEditable();
        }
        if (string.equals(aFT)) {
            return this.afB();
        }
        if (string.equals(aFU)) {
            return this.afC();
        }
        if (string.equals(aFV)) {
            return this.afD();
        }
        if (string.equals(STRENGTH)) {
            short s = this.afA();
            return s > 0 ? Short.valueOf(s) : "-";
        }
        if (string.equals(aFW)) {
            short s = this.afA();
            return s > 0 ? Integer.valueOf(aet_0.nH(s)) : "-";
        }
        if (string.equals(aFX)) {
            return this.afA() == apN.aDK().Ln().aQs().dN();
        }
        if (string.equals(aFY)) {
            qa_2 qa_23 = this.afF();
            ArrayList arrayList = new ArrayList();
            qa_23.b(new ho_1(this, arrayList));
            return arrayList.toArray();
        }
        if (string.equals(aFZ)) {
            return this.getType() == -10;
        }
        if (string.equals(aGa)) {
            try {
                return String.format(mu_1.rM().getString("teamsIconsPath"), this.afw());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(aGc)) {
            try {
                return String.format(mu_1.rM().getString("teamsBackgroundsPath"), this.afy());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(aGb)) {
            fArray = tn_0.hT(this.afx()).Aa();
            return fArray[0] + "," + fArray[1] + "," + fArray[2] + ",1";
        }
        if (string.equals(aGd)) {
            fArray = tn_0.hT(this.afz()).Aa();
            return fArray[0] + "," + fArray[1] + "," + fArray[2] + ",1";
        }
        return null;
    }

    public String[] getFields() {
        return ce;
    }

    public boolean l(String string) {
        return string.equals(xX);
    }

    public void c(String string, Object object) {
    }

    public void a(String string, Object object) {
        if (string.equals(xX) && object instanceof String) {
            this.setName((String)object);
        }
    }

    public static zK a(sw_1 sw_12) {
        zK zK2 = new zK();
        if (sw_12 != null) {
            zK2.b(sw_12.cd());
        }
        return zK2;
    }

    public String toString() {
        return this.getName();
    }

    public boolean isEditable() {
        return this.tI() < 100;
    }

    private boolean GL() {
        zK zK2 = bs_0.IF().II();
        return zK2 != null && zK2.tI() == this.tI();
    }
}

