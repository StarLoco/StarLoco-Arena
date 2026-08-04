/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Renamed from arZ
 */
public class arz_0
extends ii_2
implements aom_2 {
    HashMap cQX = new HashMap();

    public arz_0(vU vU2) {
        this.a(vU2);
    }

    public void a(zf_0 zf_02, ka_0 ka_02) {
        ka_02.a(this.Pb);
        ArrayList<ka_0> arrayList = (ArrayList<ka_0>)this.cQX.get(zf_02);
        if (arrayList == null) {
            arrayList = new ArrayList<ka_0>();
            this.cQX.put(zf_02, arrayList);
        }
        arrayList.add(ka_02);
    }

    public void a(zf_0 zf_02, String string) {
        ka_0 ka_02 = null;
        try {
            ka_02 = (ka_0)dh_2.a(string, ka_0.class, this.Pb);
        }
        catch (Exception exception) {
            this.e("Could not instantiate class [" + string + "]", exception);
        }
        if (ka_02 != null) {
            this.a(zf_02, ka_02);
        }
    }

    public List c(zf_0 zf_02) {
        List list = (List)this.cQX.get(zf_02);
        if (list != null) {
            return list;
        }
        list = this.d(zf_02);
        if (list != null) {
            return list;
        }
        list = this.e(zf_02);
        if (list != null) {
            return list;
        }
        return null;
    }

    List d(zf_0 zf_02) {
        int n2 = 0;
        zf_0 zf_03 = null;
        for (zf_0 zf_04 : this.cQX.keySet()) {
            int n3;
            if (zf_04.size() <= 1 || !zf_04.get(0).equals("*") || (n3 = zf_02.a(zf_04)) <= n2) continue;
            n2 = n3;
            zf_03 = zf_04;
        }
        if (zf_03 != null) {
            return (List)this.cQX.get(zf_03);
        }
        return null;
    }

    List e(zf_0 zf_02) {
        int n2 = 0;
        zf_0 zf_03 = null;
        for (zf_0 zf_04 : this.cQX.keySet()) {
            int n3;
            String string = zf_04.Ga();
            if (!"*".equals(string) || (n3 = zf_02.b(zf_04)) != zf_04.size() - 1 || n3 <= n2) continue;
            n2 = n3;
            zf_03 = zf_04;
        }
        if (zf_03 != null) {
            return (List)this.cQX.get(zf_03);
        }
        return null;
    }

    public String toString() {
        String string = "  ";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SimpleRuleStore ( ").append("rules = ").append(this.cQX).append("  ").append(" )");
        return stringBuilder.toString();
    }
}

