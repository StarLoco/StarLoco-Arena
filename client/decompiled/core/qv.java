/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

public class qv
extends agf_2 {
    public static final String aeC = "cross";
    private int aeD;
    private int aeE;
    private int aeF;
    private int aeG;
    private List ku = new ArrayList(1);
    public static final xX kv = new uz_2(new alw_2("Croix (deux barres de tailles identiques)", new of_0("distance centre -> extr\u00e9mit\u00e9")), new alw_2("Croix (deux barres de tailles diff\u00e9rentes)", new of_0("distance centre -> extr\u00e9mit\u00e9 face \u00e0 soi"), new of_0("distance centre -> extr\u00e9mit\u00e9 sur le c\u00f4t\u00e9")), new alw_2("Croix (4 barres de tailles diff\u00e9rentes)", new of_0("distance centre -> extr\u00e9mit\u00e9 face \u00e0 soi vers le haut"), new of_0("distance centre -> extr\u00e9mit\u00e9 face \u00e0 soi vers le bas"), new of_0("distance centre -> extr\u00e9mit\u00e9 sur le c\u00f4t\u00e9 vers la gauche"), new of_0("distance centre -> extr\u00e9mit\u00e9 sur le c\u00f4t\u00e9 vers la droite")));

    public xX ff() {
        return kv;
    }

    public void a(int[] nArray) {
        int n2;
        if (nArray == null || nArray.length == 0) {
            throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type cross : 1 param\u00e8tre attendu, 0 trouv\u00e9(s)");
        }
        if (nArray.length != 1 && nArray.length != 2 && nArray.length != 4) {
            throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type cross : 1 ou 2 ou 4 param\u00e8tres attendus, " + nArray.length + " trouv\u00e9(s)");
        }
        this.aeD = nArray[0];
        if (nArray.length == 2) {
            this.aeG = nArray[1];
            this.aeF = this.aeD;
            this.aeE = this.aeG;
        } else if (nArray.length == 4) {
            this.aeF = nArray[1];
            this.aeG = nArray[2];
            this.aeE = nArray[3];
        } else {
            this.aeG = this.aeD;
            this.aeF = this.aeD;
            this.aeE = this.aeD;
        }
        this.ku.clear();
        this.ku.add(new int[]{0, 0});
        for (n2 = 1; n2 <= this.aeG; ++n2) {
            this.ku.add(new int[]{0, -n2});
        }
        n2 = 1;
        while (n2 <= this.aeE) {
            this.ku.add(new int[]{0, n2++});
        }
        n2 = 1;
        while (n2 <= this.aeD) {
            this.ku.add(new int[]{n2++, 0});
        }
        for (n2 = 1; n2 <= this.aeF; ++n2) {
            this.ku.add(new int[]{-n2, 0});
        }
    }

    protected boolean fi() {
        return true;
    }

    public List fg() {
        return this.ku;
    }

    public String fh() {
        return "cross-h" + this.aeD + "b" + this.aeF + "-g" + this.aeG + "d" + this.aeE;
    }

    public zg_1 fj() {
        return zg_1.cdx;
    }

    public ArrayList fm() {
        ArrayList<qv> arrayList = new ArrayList<qv>();
        arrayList.add(this);
        return arrayList;
    }

    public int vD() {
        return this.aeG;
    }

    public xq_2 fn() {
        if (this.aeF == 0 && this.aeD == 0 && this.aeG == 0 && this.aeE == 0) {
            return xq_2.bXD;
        }
        if (this.aeF == 0 && this.aeD == 0) {
            return xq_2.bXE;
        }
        if (this.aeG == 0 && this.aeE == 0) {
            return xq_2.bXF;
        }
        return xq_2.bXG;
    }
}

