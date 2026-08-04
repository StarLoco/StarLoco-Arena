/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * Renamed from aea
 */
public class aea_1
implements aho_0 {
    private aau_1 cnV;
    private static final String Gs = "isSelected";
    private static final String NAME = "name";
    private static final String sU = "description";
    private static final String cnW = "descriptionDone";
    private static final String aTc = "conditions";
    private static final String aVf = "completion";
    private static final String POINTS = "points";
    private static final String sW = "iconUrl";
    private static final String cnX = "keyIconUrl";
    private static final String cnY = "grade";
    private static final String nN = "style";
    public static final String[] ce = new String[]{"isSelected", "name", "descriptionDone", "conditions", "completion", "points", "iconUrl", "keyIconUrl", "grade", "style"};
    public static final Comparator cnZ = new aqN();
    public static final Comparator coa = new aqs();

    public aea_1(aau_1 aau_12) {
        this.cnV = aau_12;
    }

    public String[] getFields() {
        return ce;
    }

    public aau_1 aty() {
        return this.cnV;
    }

    public Object getFieldValue(String string) {
        if (string.equals(Gs)) {
            if (azs_0.aLV().getProperty("selectedAchievement") == null) {
                return false;
            }
            return this == azs_0.aLV().getProperty("selectedAchievement").getValue();
        }
        if (string.equals(NAME)) {
            return aon_0.aYc().a(37, this.cnV.tI(), new Object[0]);
        }
        if (string.equals(cnW)) {
            return asf_0.b(this.aty());
        }
        if (string.equals(sU)) {
            return aon_0.aYc().a(49, this.cnV.tI(), new Object[0]);
        }
        if (string.equals(POINTS)) {
            return this.cnV.adV();
        }
        if (string.equals(aTc)) {
            int n2;
            aGz aGz2 = this.cnV.aoW();
            jg_0 jg_02 = this.cnV.adX();
            ArrayList<ako> arrayList = new ArrayList<ako>();
            short[] sArray = aGz2.Gj();
            for (n2 = 0; n2 < sArray.length; ++n2) {
                arrayList.add(new ako(sArray[n2], aGz2.cp(sArray[n2]), 0));
            }
            for (n2 = 0; n2 < jg_02.size(); ++n2) {
                arrayList.add(new ako(0, 1, jg_02.bu(n2)));
            }
            Collections.sort(arrayList, new aqP(this));
            return arrayList.toArray();
        }
        if (string.equals(aVf)) {
            aGz aGz3 = this.cnV.aoW();
            short[] sArray = aGz3.Gj();
            int n3 = 0;
            int n4 = 0;
            for (int j = 0; j < sArray.length; ++j) {
                ++n3;
                n4 += Math.min(apN.aDK().Ln().qI().cp(sArray[j]) * 100 / aGz3.cp(sArray[j]), 100);
            }
            jg_0 jg_03 = this.cnV.adX();
            for (int j = 0; j < jg_03.size(); ++j) {
                ++n3;
                if (!apN.aDK().Ln().aQm().bY(jg_03.bu(j))) continue;
                n4 += 100;
            }
            return n4 / n3 + "%";
        }
        if (string.equals(sW)) {
            try {
                int n5 = Math.min(this.cnV.adV() / 5, 5);
                boolean bl2 = true;
                aGz aGz4 = this.cnV.aoW();
                short[] sArray = aGz4.Gj();
                for (int j = 0; j < sArray.length; ++j) {
                    bl2 &= apN.aDK().Ln().qI().cp(sArray[j]) >= aGz4.cp(sArray[j]);
                }
                jg_0 jg_04 = this.cnV.adX();
                for (int j = 0; j < jg_04.size(); ++j) {
                    bl2 &= apN.aDK().Ln().aQm().bY(jg_04.bu(j));
                }
                if (!bl2) {
                    n5 *= -1;
                }
                return String.format(mu_1.rM().getString("achievementIconsPath"), n5);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(cnX)) {
            int n6 = Math.min(this.cnV.adV() / 5, 5);
            try {
                return String.format(mu_1.rM().getString("achievementKeyIconsPath"), n6);
            }
            catch (aih_2 aih_22) {
                aih_22.printStackTrace();
            }
        }
        if (string.equals(cnY) && apN.aDK().Ln().c(this.cnV)) {
            short s = this.cnV.adQ();
            ArrayList<aea_1> arrayList = new ArrayList<aea_1>();
            arrayList.add(new aea_1(this.cnV));
            while (s != 0) {
                arrayList.add(new aea_1(qy_2.ce(s)));
                short s2 = qy_2.ce(s).adQ();
                s = s2 == s ? (short)0 : s2;
            }
            return arrayList.toArray();
        }
        if (string.equals(nN)) {
            short s = this.cnV.adV();
            if (s <= 5) {
                return "LadderOdd";
            }
            if (s <= 10) {
                return "LadderThird";
            }
            if (s <= 15) {
                return "LadderSecond";
            }
            if (s <= 20) {
                return "LadderFirst";
            }
            return "PlatinumAchievement";
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
}

