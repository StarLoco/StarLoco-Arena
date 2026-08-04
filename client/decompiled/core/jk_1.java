/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.File;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from jk
 */
public class jk_1
implements aho_0 {
    private static final Logger a = Logger.getLogger(jk_1.class);
    private static File zx = null;
    public static final String zy = "spellCategories";
    public static final String zz = "breedCategories";
    public static final String zA = "equipmentCategories";
    public static final String zB = "fightBudgetCategories";
    public static final String zC = "fightTimeCategories";
    public static final String zD = "arenaCategories";
    public static final String zE = "summary";
    public static final String zF = "profiles";
    public static final String[] ce;
    private static final jk_1 zG;
    private final ArrayList[] zH = new ArrayList[33];
    private final ArrayList[] zI = new ArrayList[33];
    private final ArrayList zJ = new ArrayList();
    private final ArrayList zK = new ArrayList();
    private final ArrayList zL = new ArrayList();
    private final ArrayList zM = new ArrayList();
    private final ArrayList zN = new ArrayList();
    private final ArrayList zO = new ArrayList();
    private final ArrayList zP = new ArrayList();
    private je_2 zQ = new je_2();
    private final je_2 zR = new je_2();
    public static final int zS = 12;
    public static final int zT = 100;
    public static final int zU = 101;
    public static final int zV = 200;
    public static final int zW = 201;
    public static final int zX = 202;
    public static final int zY = 203;
    public static final int zZ = 204;
    public static final int Aa = 205;
    public static final int Ab = 206;
    public static final int Ac = 207;
    public static final int Ad = 208;
    public static final int Ae = 209;
    public static final int Af = 210;
    public static final int Ag = 211;
    public static final int Ah = 212;
    public static final int Ai = 213;
    public static final int Aj = 300;
    public static final int Ak = 301;
    public static final int Al = 302;
    public static final int Am = 303;
    public static final int An = 304;
    public static final int Ao = 400;
    public static final int Ap = 401;
    public static final int Aq = 500;
    public static final int Ar = 600;
    public static final int As = 772;
    public static final int At = 784;

    public jk_1() {
        int n2;
        for (n2 = 0; n2 < this.zH.length; ++n2) {
            this.zH[n2] = new ArrayList();
        }
        for (n2 = 0; n2 < this.zI.length; ++n2) {
            this.zI[n2] = new ArrayList();
        }
        azs_0.aLV().g("coachCardFightParametersManager", this);
        this.zQ.a(jn_1.bkb);
        this.zR.a(jn_1.bkb);
    }

    public void a(np_1 np_12) {
        if (np_12.sp()) {
            this.zI[np_12.getType() - 900].add(np_12);
        } else {
            this.zH[np_12.getType()].add(np_12);
        }
    }

    public ArrayList a(int n2, np_1 np_12, int[] nArray, int[] nArray2) {
        int n3;
        ArrayList<WN> arrayList = new ArrayList<WN>();
        ArrayList<WN> arrayList2 = new ArrayList<WN>();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (n3 = 0; n3 < nArray.length; ++n3) {
            if (nArray[n3] >= 900) {
                arrayList3.addAll(this.zI[nArray[n3] - 900]);
                continue;
            }
            arrayList3.addAll(this.zH[nArray[n3]]);
        }
        for (n3 = 0; n3 < nArray2.length; ++n3) {
            if (nArray2[n3] >= 900) {
                arrayList4.addAll(this.zI[nArray2[n3] - 900]);
                continue;
            }
            arrayList4.addAll(this.zH[nArray2[n3]]);
        }
        for (n3 = 0; n3 < arrayList3.size(); ++n3) {
            np_1 np_13 = (np_1)arrayList3.get(n3);
            ArrayList<np_1> arrayList5 = new ArrayList<np_1>();
            if (np_12 != null) {
                arrayList5.add(np_12);
            }
            arrayList5.add(np_13);
            np_1 np_14 = np_1.b(np_12, np_13);
            if (np_14.rg().length >= np_14.T()) {
                WN wN = new WN(n2, arrayList5);
                arrayList.add(wN);
                continue;
            }
            for (int j = 0; j < arrayList4.size(); ++j) {
                np_1 np_15 = (np_1)arrayList4.get(j);
                ArrayList<np_1> arrayList6 = new ArrayList<np_1>(arrayList5);
                arrayList6.add(np_15);
                WN wN = new WN(n2, arrayList6);
                arrayList2.add(wN);
            }
            while (arrayList2.size() % 12 != 0) {
                arrayList2.add(null);
            }
        }
        while (arrayList.size() % 12 != 0) {
            arrayList.add(null);
        }
        arrayList.addAll(arrayList2);
        return arrayList;
    }

    public void initialize() {
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        arrayList.addAll(this.a(400, null, new int[]{ajr_2.cBa.tI()}, new int[]{ajr_2.cCe.tI()}));
        this.zM.add(new sd_1(aon_0.aYc().getString("customFightCategory400"), arrayList));
        arrayList.clear();
        arrayList.addAll(this.a(401, null, new int[]{ajr_2.cBt.tI()}, new int[]{ajr_2.cCe.tI()}));
        this.zM.add(new sd_1(aon_0.aYc().getString("customFightCategory401"), arrayList));
        azs_0.aLV().g("selectedFightBudgetCategory", this.zM.get(0));
        arrayList.clear();
        arrayList.addAll(this.a(500, null, new int[]{ajr_2.cBj.tI()}, new int[]{ajr_2.cCg.tI()}));
        arrayList.addAll(this.a(500, null, new int[]{ajr_2.cBk.tI()}, new int[]{ajr_2.cCf.tI()}));
        this.zN.add(new sd_1(aon_0.aYc().getString("customFightCategory500"), arrayList));
        azs_0.aLV().g("selectedFightTimeCategory", this.zN.get(0));
        arrayList.clear();
        arrayList.addAll(this.a(100, null, new int[]{ajr_2.cBq.tI()}, new int[]{ajr_2.cBG.tI()}));
        arrayList.addAll(this.a(100, ((xj)la_0.XJ().pj(784)).tv()[0], new int[]{ajr_2.cBT.tI()}, new int[]{ajr_2.cBG.tI()}));
        this.zL.add(new sd_1(aon_0.aYc().getString("customFightCategory100"), arrayList));
        arrayList.clear();
        arrayList.addAll(this.a(101, null, new int[]{ajr_2.cBc.tI()}, new int[]{ajr_2.cCi.tI()}));
        arrayList.addAll(this.a(101, null, new int[]{ajr_2.cBb.tI()}, new int[]{ajr_2.cCi.tI()}));
        this.zL.add(new sd_1(aon_0.aYc().getString("customFightCategory101"), arrayList));
        azs_0.aLV().g("selectedBreedCategory", this.zL.get(0));
        arrayList.clear();
        arrayList.addAll(this.a(200, null, new int[]{ajr_2.cBd.tI()}, new int[]{ajr_2.cBH.tI()}));
        arrayList.addAll(this.a(200, ((xj)la_0.XJ().pj(772)).tv()[0], new int[]{ajr_2.cBT.tI()}, new int[]{ajr_2.cBH.tI()}));
        this.zJ.add(new sd_1(aon_0.aYc().getString("customFightCategory200"), arrayList));
        arrayList.clear();
        arrayList.addAll(this.a(201, null, new int[]{ajr_2.cBd.tI()}, new int[]{ajr_2.cBI.tI()}));
        arrayList.addAll(this.a(201, ((xj)la_0.XJ().pj(772)).tv()[0], new int[]{ajr_2.cBT.tI()}, new int[]{ajr_2.cBI.tI()}));
        this.zJ.add(new sd_1(aon_0.aYc().getString("customFightCategory201"), arrayList));
        arrayList.clear();
        arrayList.addAll(this.a(202, null, new int[]{ajr_2.cBd.tI()}, new int[]{ajr_2.cBJ.tI()}));
        arrayList.addAll(this.a(202, ((xj)la_0.XJ().pj(772)).tv()[0], new int[]{ajr_2.cBT.tI()}, new int[]{ajr_2.cBJ.tI()}));
        this.zJ.add(new sd_1(aon_0.aYc().getString("customFightCategory202"), arrayList));
        arrayList.clear();
        arrayList.addAll(this.a(203, null, new int[]{ajr_2.cBd.tI()}, new int[]{ajr_2.cBK.tI()}));
        arrayList.addAll(this.a(203, ((xj)la_0.XJ().pj(772)).tv()[0], new int[]{ajr_2.cBT.tI()}, new int[]{ajr_2.cBK.tI()}));
        this.zJ.add(new sd_1(aon_0.aYc().getString("customFightCategory203"), arrayList));
        arrayList.clear();
        arrayList.addAll(this.a(204, null, new int[]{ajr_2.cBd.tI()}, new int[]{ajr_2.cBL.tI()}));
        arrayList.addAll(this.a(204, ((xj)la_0.XJ().pj(772)).tv()[0], new int[]{ajr_2.cBT.tI()}, new int[]{ajr_2.cBL.tI()}));
        this.zJ.add(new sd_1(aon_0.aYc().getString("customFightCategory204"), arrayList));
        arrayList.clear();
        arrayList.addAll(this.a(205, null, new int[]{ajr_2.cBd.tI()}, new int[]{ajr_2.cBM.tI()}));
        arrayList.addAll(this.a(205, ((xj)la_0.XJ().pj(772)).tv()[0], new int[]{ajr_2.cBT.tI()}, new int[]{ajr_2.cBM.tI()}));
        this.zJ.add(new sd_1(aon_0.aYc().getString("customFightCategory205"), arrayList));
        arrayList.clear();
        arrayList.addAll(this.a(206, null, new int[]{ajr_2.cBd.tI()}, new int[]{ajr_2.cBN.tI()}));
        arrayList.addAll(this.a(206, ((xj)la_0.XJ().pj(772)).tv()[0], new int[]{ajr_2.cBT.tI()}, new int[]{ajr_2.cBN.tI()}));
        this.zJ.add(new sd_1(aon_0.aYc().getString("customFightCategory206"), arrayList));
        arrayList.clear();
        arrayList.addAll(this.a(207, null, new int[]{ajr_2.cBd.tI()}, new int[]{ajr_2.cBO.tI()}));
        arrayList.addAll(this.a(207, ((xj)la_0.XJ().pj(772)).tv()[0], new int[]{ajr_2.cBT.tI()}, new int[]{ajr_2.cBO.tI()}));
        this.zJ.add(new sd_1(aon_0.aYc().getString("customFightCategory207"), arrayList));
        arrayList.clear();
        arrayList.addAll(this.a(208, null, new int[]{ajr_2.cBd.tI()}, new int[]{ajr_2.cBP.tI()}));
        arrayList.addAll(this.a(208, ((xj)la_0.XJ().pj(772)).tv()[0], new int[]{ajr_2.cBT.tI()}, new int[]{ajr_2.cBP.tI()}));
        this.zJ.add(new sd_1(aon_0.aYc().getString("customFightCategory208"), arrayList));
        arrayList.clear();
        arrayList.addAll(this.a(209, null, new int[]{ajr_2.cBd.tI()}, new int[]{ajr_2.cBQ.tI()}));
        arrayList.addAll(this.a(209, ((xj)la_0.XJ().pj(772)).tv()[0], new int[]{ajr_2.cBT.tI()}, new int[]{ajr_2.cBQ.tI()}));
        this.zJ.add(new sd_1(aon_0.aYc().getString("customFightCategory209"), arrayList));
        arrayList.clear();
        arrayList.addAll(this.a(210, null, new int[]{ajr_2.cBd.tI()}, new int[]{ajr_2.cBR.tI()}));
        arrayList.addAll(this.a(210, ((xj)la_0.XJ().pj(772)).tv()[0], new int[]{ajr_2.cBT.tI()}, new int[]{ajr_2.cBR.tI()}));
        this.zJ.add(new sd_1(aon_0.aYc().getString("customFightCategory210"), arrayList));
        arrayList.clear();
        arrayList.addAll(this.a(211, null, new int[]{ajr_2.cBd.tI()}, new int[]{ajr_2.cBS.tI()}));
        arrayList.addAll(this.a(211, ((xj)la_0.XJ().pj(772)).tv()[0], new int[]{ajr_2.cBT.tI()}, new int[]{ajr_2.cBS.tI()}));
        this.zJ.add(new sd_1(aon_0.aYc().getString("customFightCategory211"), arrayList));
        arrayList.clear();
        arrayList.addAll(this.a(212, null, new int[]{ajr_2.cBd.tI()}, new int[]{ajr_2.cCj.tI()}));
        arrayList.addAll(this.a(212, ((xj)la_0.XJ().pj(772)).tv()[0], new int[]{ajr_2.cBT.tI()}, new int[]{ajr_2.cCj.tI()}));
        this.zJ.add(new sd_1(aon_0.aYc().getString("customFightCategory212"), arrayList));
        arrayList.clear();
        arrayList.addAll(this.a(213, null, new int[]{ajr_2.cBd.tI()}, new int[]{ajr_2.cCk.tI()}));
        arrayList.addAll(this.a(213, ((xj)la_0.XJ().pj(772)).tv()[0], new int[]{ajr_2.cBT.tI()}, new int[]{ajr_2.cCk.tI()}));
        this.zJ.add(new sd_1(aon_0.aYc().getString("customFightCategory213"), arrayList));
        azs_0.aLV().g("selectedSpellCategory", this.zJ.get(0));
        arrayList.clear();
        arrayList.addAll(this.a(600, null, new int[]{ajr_2.cBC.tI()}, new int[]{ajr_2.cCh.tI()}));
        this.zO.add(new sd_1(aon_0.aYc().getString("customFightCategory600"), arrayList));
        azs_0.aLV().g("selectedArenaCategory", this.zO.get(0));
        this.zK.add(new sd_1(aon_0.aYc().getString("customFightCategory300"), this.a(300, null, new int[]{ajr_2.cBe.tI()}, new int[]{ajr_2.cBU.tI(), ajr_2.cBV.tI(), ajr_2.cBX.tI(), ajr_2.cBW.tI(), ajr_2.cBY.tI(), ajr_2.cBZ.tI()})));
        this.zK.add(new sd_1(aon_0.aYc().getString("customFightCategory301"), this.a(301, null, new int[]{ajr_2.cBe.tI()}, new int[]{ajr_2.cCc.tI()})));
        this.zK.add(new sd_1(aon_0.aYc().getString("customFightCategory302"), this.a(302, null, new int[]{ajr_2.cBe.tI()}, new int[]{ajr_2.cCb.tI()})));
        this.zK.add(new sd_1(aon_0.aYc().getString("customFightCategory303"), this.a(303, null, new int[]{ajr_2.cBe.tI()}, new int[]{ajr_2.cCa.tI()})));
        this.zK.add(new sd_1(aon_0.aYc().getString("customFightCategory304"), this.a(304, null, new int[]{ajr_2.cBe.tI()}, new int[]{ajr_2.cCd.tI()})));
        azs_0.aLV().g("selectedEquipmentCategory", this.zK.get(0));
    }

    public void mc() {
        this.zQ.clear();
        this.zQ.a(jn_1.bkb);
    }

    public void md() {
        if (this.zP.size() > 0) {
            this.zQ.clear();
            ArrayList arrayList = new ArrayList();
            int n2 = 0;
            for (int j = 0; j < this.zP.size(); ++j) {
                WN wN = (WN)this.zP.get(j);
                n2 += wN.ajp().size();
                arrayList.addAll(wN.ajp());
            }
            np_1[] np_1Array = new np_1[n2];
            for (int j = 0; j < n2; ++j) {
                np_1Array[j] = (np_1)arrayList.get(j);
            }
            this.zQ.a(np_1Array);
        } else {
            this.zQ.a(jn_1.bkb);
        }
    }

    public void clear() {
        this.zP.clear();
    }

    public ArrayList me() {
        return this.zP;
    }

    public void a(WN wN) {
        this.zP.remove(wN);
    }

    public void b(WN wN) {
        int n2;
        this.zP.add(wN);
        ArrayList<WN> arrayList = new ArrayList<WN>();
        for (n2 = 0; n2 < this.zP.size(); ++n2) {
            WN wN2 = (WN)this.zP.get(n2);
            if (!((Boolean)wN2.getFieldValue("forbidden")).booleanValue()) continue;
            arrayList.add(wN2);
        }
        for (n2 = 0; n2 < arrayList.size(); ++n2) {
            this.a((WN)arrayList.get(n2));
        }
    }

    public static jk_1 mf() {
        return zG;
    }

    public je_2 mg() {
        zK zK2 = bs_0.IF().II();
        if (zK2 != null && zK2.getType() == -5) {
            return this.zQ;
        }
        return this.zR;
    }

    public ArrayList mh() {
        return this.zJ;
    }

    public void d(ArrayList arrayList) {
        this.zP.addAll(arrayList);
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        File[] fileArray;
        if (string.equals(zy)) {
            return this.zJ.toArray();
        }
        if (string.equals(zz)) {
            return this.zL.toArray();
        }
        if (string.equals(zA)) {
            return this.zK.toArray();
        }
        if (string.equals(zB)) {
            return this.zM.toArray();
        }
        if (string.equals(zC)) {
            return this.zN.toArray();
        }
        if (string.equals(zE)) {
            int n2;
            ArrayList arrayList;
            int n3;
            rw_2 rw_22 = new rw_2();
            for (n3 = 0; n3 < this.zL.size(); ++n3) {
                arrayList = ((sd_1)this.zL.get(n3)).aeI();
                if (arrayList.isEmpty()) continue;
                rw_22.bJ("<b>").bJ(((sd_1)this.zL.get(n3)).getName()).bJ("</b>\n");
                for (n2 = 0; n2 < arrayList.size(); ++n2) {
                    rw_22.bJ(((WN)arrayList.get(n2)).getDescription() + "\n");
                }
            }
            for (n3 = 0; n3 < this.zJ.size(); ++n3) {
                arrayList = ((sd_1)this.zJ.get(n3)).aeI();
                if (arrayList.isEmpty()) continue;
                rw_22.bJ("<b>").bJ(((sd_1)this.zJ.get(n3)).getName()).bJ("</b>\n");
                for (n2 = 0; n2 < arrayList.size(); ++n2) {
                    rw_22.bJ(((WN)arrayList.get(n2)).getDescription() + "\n");
                }
            }
            for (n3 = 0; n3 < this.zK.size(); ++n3) {
                arrayList = ((sd_1)this.zK.get(n3)).aeI();
                if (arrayList.isEmpty()) continue;
                rw_22.bJ("<b>").bJ(((sd_1)this.zK.get(n3)).getName()).bJ("</b>\n");
                for (n2 = 0; n2 < arrayList.size(); ++n2) {
                    rw_22.bJ(((WN)arrayList.get(n2)).getDescription() + "\n");
                }
            }
            for (n3 = 0; n3 < this.zM.size(); ++n3) {
                arrayList = ((sd_1)this.zM.get(n3)).aeI();
                if (arrayList.isEmpty()) continue;
                rw_22.bJ("<b>").bJ(((sd_1)this.zM.get(n3)).getName()).bJ("</b>\n");
                for (n2 = 0; n2 < arrayList.size(); ++n2) {
                    rw_22.bJ(((WN)arrayList.get(n2)).getDescription() + "\n");
                }
            }
            for (n3 = 0; n3 < this.zN.size(); ++n3) {
                arrayList = ((sd_1)this.zN.get(n3)).aeI();
                if (arrayList.isEmpty()) continue;
                rw_22.bJ("<b>").bJ(((sd_1)this.zN.get(n3)).getName()).bJ("</b>\n");
                for (n2 = 0; n2 < arrayList.size(); ++n2) {
                    rw_22.bJ(((WN)arrayList.get(n2)).getDescription() + "\n");
                }
            }
            for (n3 = 0; n3 < this.zO.size(); ++n3) {
                arrayList = ((sd_1)this.zO.get(n3)).aeI();
                if (arrayList.isEmpty()) continue;
                rw_22.bJ("<b>").bJ(((sd_1)this.zO.get(n3)).getName()).bJ("</b>\n");
                for (n2 = 0; n2 < arrayList.size(); ++n2) {
                    rw_22.bJ(((WN)arrayList.get(n2)).getDescription() + "\n");
                }
            }
            return rw_22.wR();
        }
        if (string.equals(zF) && (fileArray = zx.listFiles()) != null) {
            ArrayList<String> arrayList = new ArrayList<String>(fileArray.length + 1);
            arrayList.add(aon_0.aYc().getString("defaultFightProfile"));
            for (int j = 0; j < fileArray.length; ++j) {
                String[] stringArray = fileArray[j].getName().split("\\.");
                if (!fileArray[j].isFile() || stringArray.length <= 0 || !stringArray[stringArray.length - 1].equals("apf")) continue;
                arrayList.add(fileArray[j].getName());
            }
            return arrayList.toArray();
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

    static {
        try {
            File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + mu_1.rM().getString("savesPath"));
            if (file.exists() || file.mkdir()) {
                File file2 = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + mu_1.rM().getString("savesPath") + System.getProperty("file.separator") + "FightRules");
                if (file2.exists() || file2.mkdir()) {
                    zx = file2;
                } else {
                    a.error((Object)"Impossible d'initialiser le r\u00e9pertoire des profiles de combats personnalis\u00e9s : R\u00e9pertoire non cr\u00e9\u00e9.");
                }
            } else {
                a.error((Object)"Impossible d'initialiser le r\u00e9pertoire des profiles de combats personnalis\u00e9s : R\u00e9pertoire non cr\u00e9\u00e9.");
            }
        }
        catch (Exception exception) {
            a.error((Object)"Impossible d'initialiser le r\u00e9pertoire des profiles de combats personnalis\u00e9s : ", (Throwable)exception);
        }
        ce = new String[]{zy, zz, zA, zB, zC, zD, zE, zF};
        zG = new jk_1();
    }
}

