/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from RC
 */
public class rc_2
extends agf_2 {
    public static String bJu = "point";
    private static final rc_2 bJv = new rc_2();
    private List ku = new ArrayList(1);
    public static final xX kv = new uz_2(new gp_0[0]);

    public xX ff() {
        return kv;
    }

    public static rc_2 aen() {
        return bJv;
    }

    public List fg() {
        return this.ku;
    }

    public String fh() {
        return bJu;
    }

    public void a(int[] nArray) {
        if (nArray != null && nArray.length > 0) {
            throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type Point : 0 attendu, " + nArray.length + " fourni(s)");
        }
        this.ku.clear();
        this.ku.add(new int[]{0, 0});
    }

    protected boolean fi() {
        return true;
    }

    public zg_1 fj() {
        return zg_1.cdv;
    }

    public ArrayList fm() {
        ArrayList<rc_2> arrayList = new ArrayList<rc_2>();
        arrayList.add(this);
        return arrayList;
    }

    public xq_2 fn() {
        return xq_2.bXD;
    }
}

