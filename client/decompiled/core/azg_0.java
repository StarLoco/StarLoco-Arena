/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from azg
 */
public class azg_0
extends jm_0
implements aho_0 {
    public static final String aFP = "fighters";
    public static final String dnl = "currentTableTurn";
    public static final String dnm = "nextTableTurn";
    public static final String dnn = "timelineSize";
    public static final String[] ce = new String[]{"fighters", "currentTableTurn", "nextTableTurn", "timelineSize"};

    public azg_0(OZ oZ) {
        this.b(oZ);
        this.b((aml_2)((Object)oZ));
        this.b();
    }

    public void n(ee_2 ee_22) {
    }

    public akv_0 a(atD atD2, arm_0 arm_02) {
        long l2 = atD2.TH();
        return this.a(atD2, arm_02, l2);
    }

    public void o(ee_2 ee_22) {
    }

    public void JP() {
        azs_0.aLV().ac("fight.timeline", dnl);
        azs_0.aLV().ac("fight.timeline", dnm);
    }

    public void p(ee_2 ee_22) {
        azs_0.aLV().ac("fight.timeline", aFP);
        azs_0.aLV().ac("fight.timeline", dnn);
    }

    public void q(ee_2 ee_22) {
        azs_0.aLV().ac("fight.timeline", aFP);
        azs_0.aLV().ac("fight.timeline", dnn);
    }

    public void JQ() {
        ee_2 ee_22 = (ee_2)this.nP();
        if (ee_22 != null) {
            azs_0.aLV().g("fight.timeline.currentFighter", ee_22);
            azs_0.aLV().ac("fight.timeline", aFP);
        }
    }

    public ArrayList aLK() {
        ArrayList<ee_2> arrayList = new ArrayList<ee_2>();
        ArrayList<ee_2> arrayList2 = new ArrayList<ee_2>();
        ee_2 ee_22 = (ee_2)this.nP();
        qa_2 qa_22 = this.CA();
        int n2 = qa_22.size();
        boolean bl2 = false;
        for (int j = 0; j < n2; ++j) {
            ee_2 ee_23 = (ee_2)this.V(qa_22.get(j));
            if (ee_22 != null && ee_23.getId() == ee_22.getId()) {
                bl2 = true;
            }
            if (bl2) {
                arrayList.add(ee_23);
                continue;
            }
            arrayList2.add(ee_23);
        }
        arrayList.add(null);
        arrayList.addAll(arrayList2);
        return arrayList;
    }

    public void b(String string, Object object) {
    }

    public Object getFieldValue(String string) {
        if (string.equals(aFP)) {
            return this.aLK().toArray();
        }
        if (string.equals(dnl)) {
            return this.JI();
        }
        if (string.equals(dnm)) {
            return this.JI() + 1;
        }
        if (string.equals(dnn)) {
            ArrayList arrayList = this.nQ();
            int n2 = -1;
            int n3 = 0;
            for (int j = 0; j < arrayList.size(); ++j) {
                if (((ee_2)arrayList.get(j)).Dk()) {
                    n3 = (byte)(n3 + 1);
                    continue;
                }
                n2 = (byte)(n2 + 1);
            }
            return 65 + n3 * 20 + n2 * 35 + 34 + ",105";
        }
        return null;
    }

    public String[] getFields() {
        return ce;
    }

    public boolean l(String string) {
        return false;
    }

    public void c(String string, Object object) {
    }

    public void a(String string, Object object) {
    }

    public boolean a(pr_0 pr_02) {
        return true;
    }
}

