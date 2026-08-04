/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class WN
implements aho_0 {
    protected static final Logger a = Logger.getLogger(WN.class);
    private static final cp_2 bVm = new cp_2();
    private ArrayList bVn = new ArrayList();
    private np_1 bVo = null;
    private int bVp;
    public static final String bDp = "selected";
    public static final String bVq = "coachCards";
    public static final String sW = "iconUrl";
    public static final String bVr = "typeStyle";
    public static final String bhD = "typeIconUrl";
    public static final String sU = "description";
    public static final String bVs = "activated";
    public static final String bVt = "forbidden";
    public static final String[] ce = new String[]{"selected", "coachCards", "iconUrl", "typeStyle", "typeIconUrl", "description", "activated", "forbidden"};

    public WN(int n2, ArrayList arrayList) {
        this.bVn = arrayList;
        np_1[] np_1Array = new np_1[arrayList.size()];
        np_1 np_12 = np_1.b(arrayList.toArray(np_1Array));
        if (np_12.jq()) {
            this.bVo = np_12;
        }
        this.bVp = n2;
        long l2 = 0L;
        for (int j = 0; j < arrayList.size(); ++j) {
            l2 *= (long)la_0.XJ().XK();
            l2 += (long)((np_1)arrayList.get(j)).sn();
        }
        bVm.a(l2, this);
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        Object object;
        if (string.equals(bDp)) {
            return jk_1.mf().me().contains(this);
        }
        if (string.equals(bVq)) {
            rw_2 rw_22 = new rw_2();
            for (int j = 0; j < this.bVn.size(); ++j) {
                rw_22.bJ(((xj)la_0.XJ().pj(((np_1)this.bVn.get(j)).sn())).getName() + "\n");
            }
            return rw_22.wR();
        }
        if (string.equals(sW)) {
            try {
                return String.format(mu_1.rM().getString("fightRuleIconsPath"), this.ajr());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(bhD)) {
            try {
                return String.format(mu_1.rM().getString("fightRuleTypesPath"), this.ao());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(bVr)) {
            object = (np_1)this.bVn.get(0);
            if (object instanceof alu_2 || object instanceof Zi || object instanceof we_0) {
                return "FightRuleForbid";
            }
            if (object instanceof al_0 || object instanceof if_0 || object instanceof aej_2) {
                if (this.bVn.get(1) != null && ((np_1)this.bVn.get(1)).rg()[0] > 0) {
                    return "FightRulePriceUp";
                }
                return "FightRulePriceDown";
            }
            return "";
        }
        if (string.equals(sU)) {
            return this.getDescription();
        }
        if (string.equals(bVs)) {
            object = apN.aDK().Ln().aQn();
            for (int j = 0; j < this.bVn.size(); ++j) {
                if (((ky_2)object).bU(((np_1)this.bVn.get(j)).sn()) || ((ky_2)object).bU(-((np_1)this.bVn.get(j)).sn())) continue;
                return false;
            }
            return true;
        }
        if (string.equals(bVt)) {
            Object object2;
            int n2;
            object = new ArrayList<np_1>();
            ArrayList arrayList = jk_1.mf().me();
            for (n2 = 0; n2 < arrayList.size(); ++n2) {
                object2 = (WN)arrayList.get(n2);
                np_1 np_12 = ((WN)object2).ajq();
                if (np_12 == null || object2 == this) continue;
                ((ArrayList)object).add(np_12);
            }
            for (n2 = 0; n2 < this.bVn.size(); ++n2) {
                np_1 np_13;
                int n3;
                object2 = (np_1)this.bVn.get(n2);
                if (((np_1)object2).getType() == ajr_2.cBG.tI()) {
                    for (int j = 0; j < ((ArrayList)object).size(); ++j) {
                        np_1 np_14 = (np_1)((ArrayList)object).get(j);
                        if (np_14.getType() != ajr_2.cBq.tI() || np_14.rg()[0] != ((np_1)object2).rg()[0]) continue;
                        return true;
                    }
                    continue;
                }
                if (((np_1)object2).getType() >= ajr_2.cBH.tI() && ((np_1)object2).getType() <= ajr_2.cBS.tI()) {
                    yp_2 yp_22 = (yp_2)je_1.Wa().el(((np_1)object2).rg()[0]);
                    for (n3 = 0; n3 < ((ArrayList)object).size(); ++n3) {
                        np_13 = (np_1)((ArrayList)object).get(n3);
                        if ((np_13.getType() != ajr_2.cBq.tI() || yp_22.iQ() != np_13.rg()[0]) && (np_13.getType() != ajr_2.cBd.tI() || yp_22.getId() != np_13.rg()[0])) continue;
                        return true;
                    }
                    continue;
                }
                if (((np_1)object2).getType() < ajr_2.cBU.tI() || ((np_1)object2).getType() > ajr_2.cCd.tI()) continue;
                ve_0 ve_02 = (ve_0)aca_0.aOq().E(((np_1)object2).rg()[0]);
                for (n3 = 0; n3 < ((ArrayList)object).size(); ++n3) {
                    np_13 = (np_1)((ArrayList)object).get(n3);
                    if (np_13.getType() != ajr_2.cBe.tI() || ve_02.getId() != np_13.rg()[0]) continue;
                    return true;
                }
            }
            return false;
        }
        return null;
    }

    public static ArrayList A(int[] nArray) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        long l2 = 0L;
        for (int j = 0; j < nArray.length; ++j) {
            l2 *= (long)la_0.XJ().XK();
            if (!bVm.m(l2 += (long)nArray[j])) continue;
            arrayList.add(bVm.t(l2));
            l2 = 0L;
        }
        if (l2 != 0L) {
            a.error((Object)"Profil invalide");
        }
        return arrayList;
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

    public ArrayList ajp() {
        return this.bVn;
    }

    public np_1 ajq() {
        return this.bVo;
    }

    public String ajr() {
        return this.bVp + "_" + ((np_1)this.bVn.get(this.bVn.size() - 1)).sn();
    }

    public int ao() {
        return this.bVn.size() > 1 ? ((np_1)this.bVn.get(this.bVn.size() - 2)).sn() : 0;
    }

    public String getDescription() {
        np_1[] np_1Array = new np_1[this.bVn.size()];
        return asf_0.c(np_1.b(this.bVn.toArray(np_1Array)));
    }
}

