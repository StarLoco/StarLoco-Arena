/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from QX
 */
public class qx_2
extends agf_2 {
    public static String bIg = "rectangle or square";
    private int fb;
    private int fc;
    private List ku = new ArrayList(1);
    public static final xX kv = new uz_2(new alw_2("Carr\u00e9 plein", new of_0("Taille d'un c\u00f4t\u00e9 (doit \u00eatre impaire)")), new alw_2("Rectangle plein", new of_0("Largeur (doit \u00eatre impaire)"), new of_0("Hauteur (doit \u00eatre impaire)")));

    public xX ff() {
        return kv;
    }

    public List fg() {
        return this.ku;
    }

    public String fh() {
        return bIg + "-" + this.fb + "x" + this.fc;
    }

    public void a(int[] nArray) {
        if (nArray == null) {
            throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type square : 1 \u00e0 2 param\u00e8tre attendu, 0 trouv\u00e9(s)");
        }
        if (nArray.length > 2) {
            throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type carr\u00e9 : 1 \u00e0 2 param\u00e8tre attendu, " + nArray.length + " trouv\u00e9(s)");
        }
        this.fb = nArray[0];
        this.fc = nArray.length == 1 ? this.fb : nArray[1];
        if (this.fb != 0 && this.fb % 2 == 0) {
            ++this.fb;
        }
        if (this.fc != 0 && this.fc % 2 == 0) {
            ++this.fc;
        }
        int n2 = (this.fb - 1) / 2;
        int n3 = (this.fc - 1) / 2;
        for (int j = 0; j < this.fb; ++j) {
            for (int i2 = 0; i2 < this.fc; ++i2) {
                this.ku.add(new int[]{j - n2, i2 - n3});
            }
        }
    }

    protected boolean fi() {
        return this.fb == this.fc;
    }

    public zg_1 fj() {
        return zg_1.cdA;
    }

    public int getWidth() {
        return this.fb;
    }

    public int getHeight() {
        return this.fc;
    }

    public ArrayList fm() {
        ArrayList<qx_2> arrayList = new ArrayList<qx_2>();
        arrayList.add(this);
        return arrayList;
    }

    public xq_2 fn() {
        if (this.fc == 0 && this.fb == 0) {
            return xq_2.bXD;
        }
        if (this.fc == this.fb) {
            return xq_2.bXJ;
        }
        return xq_2.bXL;
    }
}

