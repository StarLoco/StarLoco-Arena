/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

public class cX
extends agf_2 {
    public static String kr = "ring";
    private int ks;
    private int kt;
    private List ku = new ArrayList(1);
    public static final xX kv = new uz_2(new alw_2("Anneau", new of_0("rayon int\u00e9rieur"), new of_0("rayon ext\u00e9rieur")));

    public xX ff() {
        return kv;
    }

    public List fg() {
        return this.ku;
    }

    public String fh() {
        return kr + "-" + this.ks + "-" + this.kt;
    }

    public void a(int[] nArray) {
        if (nArray == null) {
            throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type ring : 2 param\u00e8tres attendus, 0 trouv\u00e9");
        }
        if (nArray.length != 2) {
            throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type ring : 2 param\u00e8tres attendus, " + nArray.length + " trouv\u00e9(s)");
        }
        this.ks = nArray[0] < nArray[1] ? nArray[0] : nArray[1];
        this.kt = nArray[0] > nArray[1] ? nArray[0] : nArray[1];
        this.ku.clear();
        for (int j = 0; j <= this.kt; ++j) {
            for (int i2 = Math.max(this.ks - j, 0); i2 <= Math.max(this.kt - j, 0); ++i2) {
                this.ku.add(new int[]{j, i2});
                if (i2 != 0) {
                    this.ku.add(new int[]{j, -i2});
                }
                if (j == 0) continue;
                this.ku.add(new int[]{-j, i2});
                if (i2 == 0) continue;
                this.ku.add(new int[]{-j, -i2});
            }
        }
    }

    protected boolean fi() {
        return true;
    }

    public zg_1 fj() {
        return zg_1.cdz;
    }

    public int fk() {
        return this.kt;
    }

    public int fl() {
        return this.ks;
    }

    public ArrayList fm() {
        ArrayList<cX> arrayList = new ArrayList<cX>();
        arrayList.add(this);
        return arrayList;
    }

    public xq_2 fn() {
        if (this.ks == 0 && this.kt == 0) {
            return xq_2.bXD;
        }
        if (this.ks == 0) {
            return xq_2.bXH;
        }
        return xq_2.bXI;
    }
}

