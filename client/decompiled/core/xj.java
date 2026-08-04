/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.util.ArrayList;

public class xj
extends oj_0
implements aho_0 {
    public static final String awu = "fighterColor";
    private int awv;
    public static final String arR = "id";
    public static final String xX = "name";
    public static final String aww = "set";
    public static final String aog = "description";
    public static final String awx = "halfSetEffects";
    public static final String awy = "fullSetEffects";
    public static final String awz = "isHalfComplete";
    public static final String awA = "isComplete";
    public static final String awB = "hasHalfSetEffect";
    public static final String awC = "hasFullSetEffect";
    public static final String aoh = "iconUrl";
    public static final String aoi = "illustrationUrl";
    public static final String awD = "typeIconUrl";
    public static final String awE = "cardSetName";
    public static final String awF = "cardSetSize";
    public static final String awG = "cardIndexInSet";
    public static final String awH = "requiredLevel";
    public static final String aoj = "cardType";
    public static final String awI = "colorIndexStyle";
    public static final String Oj = "value";
    public static final String Ok = "tokenValue";
    public static final String awJ = "isInTome";
    public static final String awK = "consumableTypeIcon";
    public static final String awL = "rarity";
    public static final int awM = 1;
    public static final int awN = 2;
    public static final int awO = 3;
    public static final int awP = 4;
    public static final int awQ = 5;
    public static final int awR = 6;
    public static final int awS = 1;
    public static final int awT = 2;
    public static final int awU = 3;
    public static final int awV = 4;
    public static final int awW = 5;
    public static final int awX = 6;
    public static final int awY = 7;
    public static final int awZ = 8;
    public static final int axa = 9;
    public static final int axb = 10;
    public static final int axc = 11;
    public static final int axd = 12;
    public static final int axe = 13;
    public static final String axf = "0,0,0,0";
    public static final String axg = "0.69,0.38,0.28,1.0";
    public static final String axh = "0.78,0.96,0.96,1.0";
    public static final String axi = "1.00,0.75,0.21,1.0";
    public static final String[] ce = new String[]{"id", "name", "set", "description", "halfSetEffects", "fullSetEffects", "isHalfComplete", "hasHalfSetEffect", "isComplete", "hasFullSetEffect", "iconUrl", "illustrationUrl", "typeIconUrl", "cardSetName", "cardSetSize", "cardIndexInSet", "requiredLevel", "cardType", "colorIndexStyle", "value", "tokenValue", "isInTome", "consumableTypeIcon", "rarity"};

    public xj(int n2, aMK aMK2, int n3, int n4, int n5, boolean bl2, boolean bl3, int n6, boolean bl4, boolean bl5, boolean bl6, akw_0[] akw_0Array, byte by, float[] fArray, int n7, np_1[] np_1Array, short s, short s2, short s3, byte by2, int n8, aim_1 aim_12, byte by3, int n9) {
        super(n2, aMK2, n3, n4, n5, bl2, bl3, n6, bl4, bl5, bl6, akw_0Array, by, fArray, np_1Array, s, s2, s3, by2, n8, aim_12, by3, n9);
        this.awv = n7;
    }

    public String getName() {
        return aon_0.aYc().a(23, Math.abs(this.getId()), new Object[0]);
    }

    public String getDescription() {
        String string = this.tj() == aMK.dYB ? aon_0.aYc().a(24, Math.abs(this.getId()), this.UH, this.UI) : aon_0.aYc().a(24, Math.abs(this.getId()), new Object[0]);
        if (string.length() < 0) {
            string = aon_0.aYc().a(26, this.tm(), new Object[0]);
        }
        return string;
    }

    public String CM() {
        if (this.tm() == 0) {
            return "";
        }
        return aon_0.aYc().a(25, this.tm(), new Object[0]);
    }

    public String DE() {
        if (this.tm() == 0) {
            return "";
        }
        return aon_0.aYc().a(26, this.tm(), new Object[0]);
    }

    public boolean DF() {
        for (int j = 0; j < this.UD.length; ++j) {
            if (!this.UD[j].aaF()) continue;
            return true;
        }
        return false;
    }

    public int getRank() {
        return this.awv;
    }

    public String[] getFields() {
        return ce;
    }

    public ArrayList DG() {
        ArrayList arrayList = new ArrayList();
        this.Ur.a(new abM(this, arrayList));
        return arrayList;
    }

    public Object getFieldValue(String string) {
        if (string.equals(arR)) {
            return this.jf();
        }
        if (string.equals(xX)) {
            return this.getName();
        }
        if (string.equals(aww)) {
            int n2 = this.tm();
            if (n2 == 0) {
                return null;
            }
            return ayc_0.aLE().mS(n2);
        }
        if (string.equals(aog)) {
            String string2;
            if (this.tj() == aMK.dYA) {
                return aon_0.aYc().getString("cardModifyingFightRule");
            }
            if (DofusArenaClientInstance.yl().aod().a(adc_0.clX) && (string2 = asf_0.a(this.tu(), this.tt(), false)).length() > 0) {
                return string2;
            }
            return this.getDescription();
        }
        if (string.equals(awx)) {
            int n3 = ((xj)la_0.XJ().pj(Math.abs(this.jf()))).tm();
            if (n3 == 0) {
                return null;
            }
            fe_1 fe_12 = (fe_1)ayc_0.aLE().mS(n3);
            return fe_12.getFieldValue(fe_1.aVi);
        }
        if (string.equals(awy)) {
            int n4 = ((xj)la_0.XJ().pj(Math.abs(this.jf()))).tm();
            if (n4 == 0) {
                return null;
            }
            fe_1 fe_13 = (fe_1)ayc_0.aLE().mS(n4);
            return fe_13.getFieldValue(fe_1.aVj);
        }
        if (string.equals(awz)) {
            int n5 = ((xj)la_0.XJ().pj(Math.abs(this.jf()))).tm();
            if (n5 == 0) {
                return null;
            }
            fe_1 fe_14 = (fe_1)ayc_0.aLE().mS(n5);
            return fe_14.getFieldValue(fe_1.aVg);
        }
        if (string.equals(awA)) {
            int n6 = ((xj)la_0.XJ().pj(Math.abs(this.jf()))).tm();
            if (n6 == 0) {
                return null;
            }
            fe_1 fe_15 = (fe_1)ayc_0.aLE().mS(n6);
            return fe_15.getFieldValue(fe_1.aVh);
        }
        if (string.equals(awB)) {
            ArrayList<akw_0> arrayList = new ArrayList<akw_0>();
            int n7 = ((xj)la_0.XJ().pj(Math.abs(this.jf()))).tm();
            if (n7 == 0) {
                return false;
            }
            fe_1 fe_16 = (fe_1)ayc_0.aLE().mS(n7);
            akw_0[] akw_0Array = fe_16.tu();
            for (int j = 0; j < akw_0Array.length; ++j) {
                if (akw_0Array[j].aAm() >= fe_16.size()) continue;
                arrayList.add(akw_0Array[j]);
            }
            return arrayList.size() > 0;
        }
        if (string.equals(awC)) {
            ArrayList<akw_0> arrayList = new ArrayList<akw_0>();
            int n8 = ((xj)la_0.XJ().pj(Math.abs(this.jf()))).tm();
            if (n8 == 0) {
                return false;
            }
            fe_1 fe_17 = (fe_1)ayc_0.aLE().mS(n8);
            akw_0[] akw_0Array = fe_17.tu();
            for (int j = 0; j < akw_0Array.length; ++j) {
                if (akw_0Array[j].aAm() != fe_17.size()) continue;
                arrayList.add(akw_0Array[j]);
            }
            return arrayList.size() > 0;
        }
        if (string.equals(aoh)) {
            try {
                return String.format(mu_1.rM().getString("coachEquipmentIconsPath"), Math.abs(this.jf()));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(aoi)) {
            try {
                return String.format(mu_1.rM().getString("coachEquipmentIllustrationsPath"), Math.abs(this.jf()));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(awD)) {
            try {
                if (this.tj() == aMK.dYy) {
                    int n9 = 0;
                    if (!this.tt() || this.tu().length == 0) {
                        return null;
                    }
                    long l2 = this.tu()[0].aAl();
                    n9 = aap.dp(l2) || aap.do(l2) ? 2 : (aap.dq(l2) ? 3 : (this.tu()[0].getType() == AI.aHK.tI() && bf_1.df().g((short)this.tu()[0].rg()[0]).getType() == 70 ? 4 : 1));
                    return String.format(mu_1.rM().getString("coachEquipmentTypeIconPath"), this.tj().getId() * 10 + n9);
                }
                return String.format(mu_1.rM().getString("coachEquipmentTypeIconPath"), this.tj().getId());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(awE)) {
            return this.CM();
        }
        if (string.equals(awF)) {
            if (this.tn() == null) {
                return 0;
            }
            return this.tn().size();
        }
        if (string.equals(awG)) {
            if (this.tn() == null) {
                return 0;
            }
            return this.tn().c(this) + 1;
        }
        if (string.equals(aoj)) {
            if (this.tj() == aMK.dYy) {
                return "consumableCoachCard";
            }
            return "coachCard";
        }
        if (string.equals(awI)) {
            return awu + this.tE();
        }
        if (string.equals(Oj)) {
            return this.getValue();
        }
        if (string.equals(Ok)) {
            return this.DG().toArray();
        }
        if (string.equals(awJ)) {
            return apN.aDK().Ln().aQm().contains(this.jf());
        }
        if (string.equals(awH)) {
            return this.tr();
        }
        if (string.equals(awK)) {
            int n10 = 3;
            if (this.tj() == aMK.dYz) {
                n10 = 9 + this.tl();
            }
            if (this.tj() == aMK.dYu) {
                n10 = 12;
            }
            if (!this.tt() || this.tu().length == 0) {
                return null;
            }
            if (this.tu()[0].getType() == AI.aHK.tI() && bf_1.df().g((short)this.tu()[0].rg()[0]).getType() == 70) {
                n10 = 13;
            }
            akw_0[] akw_0Array = this.tu();
            for (int j = 0; j < akw_0Array.length; ++j) {
                int n11 = akw_0Array[j].getType();
                if (n11 == AI.aHI.tI()) {
                    n10 = 5;
                    continue;
                }
                if (n11 == AI.aHG.tI()) {
                    n10 = 1;
                    continue;
                }
                if (n11 == AI.aHA.tI()) {
                    n10 = 2;
                    continue;
                }
                if (n11 == AI.aHx.tI()) {
                    n10 = 8;
                    continue;
                }
                if (n11 == AI.aHL.tI()) {
                    n10 = 6;
                    continue;
                }
                if (n11 != AI.aHE.tI()) continue;
                n10 = 7;
            }
            long l3 = this.tu()[0].aAl();
            if (aap.dp(l3)) {
                n10 = 4;
            }
            try {
                return String.format(mu_1.rM().getString("consumableTypeIconPath"), n10);
            }
            catch (aih_2 aih_22) {
                aih_22.printStackTrace();
            }
        }
        if (string.equals(awL)) {
            switch (this.getRank()) {
                case 1: 
                case 2: 
                case 3: {
                    return axf;
                }
                case 4: 
                case 5: 
                case 6: {
                    return axg;
                }
                case 7: 
                case 8: 
                case 9: {
                    return axh;
                }
                case 10: {
                    return axi;
                }
            }
            return axf;
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

