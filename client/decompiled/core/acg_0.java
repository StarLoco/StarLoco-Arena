/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from aCg
 */
public class acg_0
extends agf_2 {
    public static String dtY = "forme \u00e0 base de points";
    private static final acg_0 dtZ = new acg_0();
    private List ku = new ArrayList(1);
    public static final xX kv = new uz_2(new alw_2("Liste de 1 point : prendre l'axe sud-est pour construire", new of_0("x1"), new of_0("y1")), new alw_2("Liste de 2 points : prendre l'axe sud-est pour construire", new of_0("x1"), new of_0("y1"), new of_0("x2"), new of_0("y2")), new alw_2("Liste de 3 points : prendre l'axe sud-est pour construire", new of_0("x1"), new of_0("y1"), new of_0("x2"), new of_0("y2"), new of_0("x3"), new of_0("y3")), new alw_2("Liste de 4 points : prendre l'axe sud-est pour construire", new of_0("x1"), new of_0("y1"), new of_0("x2"), new of_0("y2"), new of_0("x3"), new of_0("y3"), new of_0("x4"), new of_0("y4")), new alw_2("Liste de 5 points : prendre l'axe sud-est pour construire", new of_0("x1"), new of_0("y1"), new of_0("x2"), new of_0("y2"), new of_0("x3"), new of_0("y3"), new of_0("x4"), new of_0("y4"), new of_0("x5"), new of_0("y5")), new alw_2("Liste de 6 points : prendre l'axe sud-est pour construire", new of_0("x1"), new of_0("y1"), new of_0("x2"), new of_0("y2"), new of_0("x3"), new of_0("y3"), new of_0("x4"), new of_0("y4"), new of_0("x5"), new of_0("y5"), new of_0("x6"), new of_0("y6")), new alw_2("Liste de 7 points : prendre l'axe sud-est pour construire", new of_0("x1"), new of_0("y1"), new of_0("x2"), new of_0("y2"), new of_0("x3"), new of_0("y3"), new of_0("x4"), new of_0("y4"), new of_0("x5"), new of_0("y5"), new of_0("x6"), new of_0("y6"), new of_0("x7"), new of_0("y7")), new alw_2("Liste de 8 points : prendre l'axe sud-est pour construire", new of_0("x1"), new of_0("y1"), new of_0("x2"), new of_0("y2"), new of_0("x3"), new of_0("y3"), new of_0("x4"), new of_0("y4"), new of_0("x5"), new of_0("y5"), new of_0("x6"), new of_0("y6"), new of_0("x7"), new of_0("y7"), new of_0("x8"), new of_0("y8")), new alw_2("Liste de 9 points : prendre l'axe sud-est pour construire", new of_0("x1"), new of_0("y1"), new of_0("x2"), new of_0("y2"), new of_0("x3"), new of_0("y3"), new of_0("x4"), new of_0("y4"), new of_0("x5"), new of_0("y5"), new of_0("x6"), new of_0("y6"), new of_0("x7"), new of_0("y7"), new of_0("x8"), new of_0("y8"), new of_0("x9"), new of_0("y9")));

    public xX ff() {
        return kv;
    }

    public static acg_0 aOn() {
        return dtZ;
    }

    public List fg() {
        return this.ku;
    }

    public String fh() {
        return dtY;
    }

    public void a(int[] nArray) {
        if (nArray == null || nArray.length % 2 != 0) {
            throw new IllegalArgumentException("Param\u00e8tres invalides pour une AOE de type Point : modulo2 attendu, " + nArray.length + " fourni(s)");
        }
        this.ku.clear();
        for (int j = 0; j < nArray.length; j += 2) {
            this.ku.add(new int[]{nArray[j], nArray[j + 1]});
        }
    }

    protected boolean fi() {
        return false;
    }

    public zg_1 fj() {
        return zg_1.cdC;
    }

    public ArrayList fm() {
        ArrayList<acg_0> arrayList = new ArrayList<acg_0>();
        arrayList.add(this);
        return arrayList;
    }

    public xq_2 fn() {
        if (this.ku.size() == 1) {
            return xq_2.bXD;
        }
        return xq_2.bXC;
    }
}

