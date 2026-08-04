/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from sp
 */
public class sp_2
extends agf_2 {
    public static String aiX = "t";
    private int aiY;
    private int aiZ;
    private List ku = new ArrayList(1);
    public static final xX kv = new uz_2(new alw_2("Zone en T (order 0: defaut, 1: pied->barre, 2: barre->pied)", new of_0("largeur de la barre (par rapport au centre : 1 = barre de 3 cellules)"), new of_0("hauteur du pied (par rapport au centre : 1 = barre de 2)")));

    public xX ff() {
        return kv;
    }

    public ArrayList fm() {
        ArrayList<agf_2> arrayList = new ArrayList<agf_2>();
        switch (this.awe()) {
            case 1: {
                int n2 = 0;
                while (n2 <= this.aiZ) {
                    acg_0 acg_02 = new acg_0();
                    acg_02.a(new int[]{n2++, 0});
                    arrayList.add(acg_02);
                }
                int[] nArray = new int[this.aiY * 4];
                for (int j = 1; j <= this.aiY; ++j) {
                    int n3 = (j - 1) * 4;
                    nArray[n3 + 0] = this.aiZ;
                    nArray[n3 + 1] = j;
                    nArray[n3 + 2] = this.aiZ;
                    nArray[n3 + 3] = -j;
                }
                acg_0 acg_03 = new acg_0();
                acg_03.a(nArray);
                arrayList.add(acg_03);
                break;
            }
            case 2: {
                int n4;
                int[] nArray = new int[this.aiY * 4];
                for (int j = 1; j <= this.aiY; ++j) {
                    n4 = (j - 1) * 4;
                    nArray[n4 + 0] = this.aiZ;
                    nArray[n4 + 1] = j;
                    nArray[n4 + 2] = this.aiZ;
                    nArray[n4 + 3] = -j;
                }
                acg_0 acg_04 = new acg_0();
                acg_04.a(nArray);
                arrayList.add(acg_04);
                n4 = this.aiZ;
                while (n4 >= 0) {
                    acg_0 acg_05 = new acg_0();
                    acg_05.a(new int[]{n4--, 0});
                    arrayList.add(acg_05);
                }
                break;
            }
            default: {
                arrayList.add(this);
            }
        }
        return arrayList;
    }

    public List fg() {
        return this.ku;
    }

    public String fh() {
        return aiX + "-" + this.aiY + "-" + this.aiZ;
    }

    public void a(int[] nArray) {
        if (nArray == null) {
            throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type T : 2 param\u00e8tres attendus, 0 trouv\u00e9");
        }
        if (nArray.length != 2) {
            throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type T : 2 param\u00e8tres attendus, " + nArray.length + " trouv\u00e9(s)");
        }
        this.aiY = Math.abs(nArray[0]);
        this.aiZ = Math.abs(nArray[1]);
        this.ku.clear();
        this.ku.add(new int[]{0, 0});
        int n2 = 1;
        while (n2 <= this.aiZ) {
            this.ku.add(new int[]{n2++, 0});
        }
        for (n2 = 1; n2 <= this.aiY; ++n2) {
            this.ku.add(new int[]{this.aiZ, n2});
            this.ku.add(new int[]{this.aiZ, -n2});
        }
    }

    protected boolean fi() {
        return false;
    }

    public zg_1 fj() {
        return zg_1.cdy;
    }

    public int yq() {
        return this.aiY;
    }

    public int yr() {
        return this.aiZ;
    }

    public xq_2 fn() {
        if (this.aiY == 0 && this.aiZ == 0) {
            return xq_2.bXD;
        }
        if (this.aiY == 0) {
            return xq_2.bXE;
        }
        if (this.aiZ == 0) {
            return xq_2.bXF;
        }
        return xq_2.bXI;
    }
}

