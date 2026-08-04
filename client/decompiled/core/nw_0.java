/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from NW
 */
public class nw_0
extends agf_2 {
    public static final String bBb = "circle";
    private int bBc;
    private List ku = new ArrayList(1);
    public static final xX kv = new uz_2(new alw_2("Cercle", new of_0("rayon")));

    public xX ff() {
        return kv;
    }

    public List fg() {
        return this.ku;
    }

    public String fh() {
        return "circle-" + this.bBc;
    }

    public void a(int[] nArray) {
        if (nArray == null || nArray.length != 1) {
            if (nArray == null || nArray.length == 0) {
                throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type cercle : 1 param\u00e8tre attendu, 0 trouv\u00e9(s)");
            }
            throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type cercle : 1 param\u00e8tre attendu, " + nArray.length + " trouv\u00e9(s)");
        }
        this.bBc = nArray[0];
        this.ku.clear();
        for (int j = -this.bBc; j <= this.bBc; ++j) {
            int n2 = this.bBc - Math.abs(j);
            int n3 = -n2;
            while (n3 <= n2) {
                this.ku.add(new int[]{j, n3++});
            }
        }
    }

    protected boolean fi() {
        return true;
    }

    public zg_1 fj() {
        return zg_1.cdw;
    }

    public int getRadius() {
        return this.bBc;
    }

    public ArrayList fm() {
        ArrayList<nw_0> arrayList = new ArrayList<nw_0>();
        arrayList.add(this);
        return arrayList;
    }

    public xq_2 fn() {
        if (this.bBc == 0) {
            return xq_2.bXD;
        }
        return xq_2.bXH;
    }
}

