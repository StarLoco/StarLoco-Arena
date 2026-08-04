/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from nd
 */
public class nd_1
extends agf_2 {
    public static String NF = "rectangular or square ring";
    private int NG;
    private int NH;
    private int NI;
    private int NJ;
    private List ku = new ArrayList(1);
    public static final xX kv = new uz_2(new alw_2("Pourtour d'un carr\u00e9", new of_0("demi c\u00f4t\u00e9 inf\u00e9rieur (cellule comprise dedans)"), new of_0("demi c\u00f4t\u00e9 sup\u00e9rieur (cellule comprise dedans)")), new alw_2("Pourtour d'un rectangle", new of_0("demi longueur inf\u00e9rieure X (cellule comprise dedans)"), new of_0("demi longueur inf\u00e9rieure Y (cellule comprise dedans)"), new of_0("demi longueur sup\u00e9rieure X (cellule comprise dedans)"), new of_0("demi longueur sup\u00e9rieure Y (cellule comprise dedans)")));

    public xX ff() {
        return kv;
    }

    public List fg() {
        return this.ku;
    }

    public String fh() {
        return NF + "-w:" + this.NG + "-" + this.NH + " / h:" + this.NI + "-" + this.NJ;
    }

    public void a(int[] nArray) {
        if (nArray == null) {
            throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type rectring : 4 param\u00e8tres attendus, 0 trouv\u00e9");
        }
        if (nArray.length != 4 && nArray.length != 2) {
            throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type rectring :4 param\u00e8tres attendus, " + nArray.length + " trouv\u00e9(s)");
        }
        if (nArray.length == 2) {
            this.NI = this.NG = Math.min(nArray[0], nArray[1]);
            this.NJ = this.NH = Math.max(nArray[0], nArray[1]);
        } else {
            this.NG = nArray[0];
            this.NI = nArray[1];
            this.NH = nArray[2];
            this.NJ = nArray[3];
            if (this.NI > this.NJ) {
                throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type rectring : innerY > outerY");
            }
            if (this.NG > this.NH) {
                throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type rectring : innerX > outerX");
            }
        }
        this.ku.clear();
        for (int j = 0; j <= this.NH; ++j) {
            for (int i2 = 0; i2 <= this.NJ; ++i2) {
                if (j < this.NG && i2 < this.NI) continue;
                if (j == 0 && i2 == 0) {
                    this.ku.add(new int[]{j, i2});
                    continue;
                }
                if (j == 0) {
                    this.ku.add(new int[]{j, i2});
                    this.ku.add(new int[]{j, -i2});
                    continue;
                }
                if (i2 == 0) {
                    this.ku.add(new int[]{j, i2});
                    this.ku.add(new int[]{-j, i2});
                    continue;
                }
                this.ku.add(new int[]{j, i2});
                this.ku.add(new int[]{-j, i2});
                this.ku.add(new int[]{j, -i2});
                this.ku.add(new int[]{-j, -i2});
            }
        }
    }

    protected boolean fi() {
        return false;
    }

    public zg_1 fj() {
        return zg_1.cdB;
    }

    public int rX() {
        return this.NH;
    }

    public int rY() {
        return this.NG;
    }

    public ArrayList fm() {
        ArrayList<nd_1> arrayList = new ArrayList<nd_1>();
        arrayList.add(this);
        return arrayList;
    }

    public xq_2 fn() {
        if (this.NJ == 0 && this.NH == 0) {
            return xq_2.bXD;
        }
        if (this.NJ == this.NH && this.NI == this.NG) {
            return xq_2.bXK;
        }
        return xq_2.bXM;
    }
}

