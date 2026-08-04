/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;

/*
 * Renamed from QY
 */
public class qy_2
extends avq_0
implements aho_0 {
    private static final lb_0 bIh = new lb_0();
    private zm_1 bIi = new zm_1();
    private zm_1 bIj = new zm_1();
    public static final String bIk = "achievementsList";
    public static final String bIl = "achievementTypesList";
    public static final String bIm = "achievementsTotalPoints";
    public static final String[] ce = new String[]{"achievementsList", "achievementTypesList", "achievementsTotalPoints"};
    public static qy_2 bIn = new qy_2();

    public static qy_2 ady() {
        return bIn;
    }

    public String[] getFields() {
        return ce;
    }

    public qy_2() {
        azs_0.aLV().g("achievementManager", this);
    }

    public Object getFieldValue(String string) {
        Object object;
        Object[] objectArray;
        if (string.equals(bIk) && (objectArray = azs_0.aLV().getProperty("selectedAchievementSubtype")) != null && (object = (li_2)objectArray.getValue()) != null) {
            ArrayList<aea_1> arrayList = new ArrayList<aea_1>();
            ArrayList<aea_1> arrayList2 = new ArrayList<aea_1>();
            ArrayList arrayList3 = ((li_2)object).pW();
            int n2 = arrayList3.size();
            for (int j = 0; j < n2; ++j) {
                aea_1 aea_12 = (aea_1)arrayList3.get(j);
                if (aea_12.aty().isHidden()) continue;
                short s = aea_12.aty().adQ();
                short s2 = aea_12.aty().adU();
                if (s2 != 0 && apN.aDK().Ln().c((aau_1)del.an(s2)) || s != 0 && !apN.aDK().Ln().c((aau_1)del.an(s))) continue;
                if (apN.aDK().Ln().c(aea_12.aty())) {
                    arrayList.add(aea_12);
                    continue;
                }
                arrayList2.add(aea_12);
            }
            Collections.sort(arrayList, aea_1.coa);
            Collections.sort(arrayList2, aea_1.cnZ);
            arrayList.addAll(arrayList2);
            return arrayList.toArray();
        }
        if (string.equals(bIl)) {
            objectArray = new ArrayList();
            object = this.bIi.Gj();
            for (int j = 0; j < ((Object)object).length; ++j) {
                objectArray.add(this.bIi.an((short)object[j]));
            }
            return objectArray.toArray();
        }
        if (string.equals(bIm)) {
            objectArray = del.getValues();
            short s = 0;
            for (int j = 0; j < objectArray.length; ++j) {
                if (!apN.aDK().Ln().c((aau_1)objectArray[j])) continue;
                s = (short)(s + ((aau_1)objectArray[j]).adV());
            }
            return s;
        }
        return null;
    }

    public static void a(aau_1 aau_12) {
        avq_0.a(aau_12);
        jg_0 jg_02 = aau_12.adX();
        for (int j = 0; j < jg_02.size(); ++j) {
            int n2 = jg_02.bu(j);
            if (!bIh.contains(n2)) {
                bIh.c(n2, new ArrayList());
            }
            ((ArrayList)bIh.get(n2)).add(aau_12);
        }
    }

    public ArrayList hs(int n2) {
        ArrayList arrayList = (ArrayList)bIh.get(n2);
        ArrayList<aau_1> arrayList2 = new ArrayList<aau_1>();
        if (arrayList != null) {
            asc asc2 = apN.aDK().Ln().aQm();
            int n3 = arrayList.size();
            for (int j = 0; j < n3; ++j) {
                boolean bl2 = true;
                aau_1 aau_12 = (aau_1)arrayList.get(j);
                for (int i2 = 0; i2 < aau_12.adX().size(); ++i2) {
                    if (aau_12.adX().bu(i2) == n2 || asc2.bY(aau_12.adX().bu(i2))) continue;
                    bl2 = false;
                }
                if (!bl2) continue;
                arrayList2.add(aau_12);
            }
        }
        return arrayList2;
    }

    public void aU(short s) {
        aau_1 aau_12 = avq_0.ce(s);
        String string = aon_0.aYc().a(37, s, new Object[0]);
        String string2 = asf_0.b(aau_12);
        gc_2 gc_22 = new gc_2(aon_0.aYc().getString("achievementUnlocked", string), string2);
        iz_1.Vg().b(gc_22);
        azs_0.aLV().a((aho_0)iz_1.Vg(), iz_1.ce);
    }

    public void a(ajk_1 ajk_12) {
        this.bIi.b(ajk_12.tI(), ajk_12);
    }

    public ajk_1 aV(short s) {
        return (ajk_1)this.bIi.an(s);
    }

    public void a(li_2 li_22) {
        this.bIj.b(li_22.pV(), li_22);
    }

    public li_2 aW(short s) {
        return (li_2)this.bIj.an(s);
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

