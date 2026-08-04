/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

public class aJF
extends agf_2 {
    public static String dRO = "Directed rectangle";
    static final uz_2 dRP = new uz_2(new alw_2("Rectangle directionnel", new of_0("Distance maximale \u00e0 la ligne centrale"), new of_0("Longueur")));
    private ArrayList dRQ;
    private int dRR;
    private int m_length;
    private List ku;

    public xX ff() {
        return dRP;
    }

    public void a(int[] nArray) {
        if (nArray == null || nArray.length != 2) {
            int n2 = nArray == null ? 0 : nArray.length;
            throw new IllegalArgumentException("Deux param\u00e8tres attendus pour une aire d'effet rectangle directionnel, " + n2 + " trouv\u00e9s");
        }
        if (nArray[0] < 0) {
            throw new IllegalArgumentException("Rectangle directionnel : " + dRP.eq(2).aJ(0).getName() + " doit \u00eatre au moins 0.");
        }
        if (nArray[1] < 1) {
            throw new IllegalArgumentException("Rectangle directionnel : " + dRP.eq(2).aJ(0).getName() + " doit \u00eatre au moins 1.");
        }
        this.dRR = nArray[0];
        this.m_length = nArray[1];
        this.aVq();
    }

    private void aVq() {
        this.ku = new ArrayList();
        for (int j = -this.dRR; j <= this.dRR; ++j) {
            int n2 = 0;
            while (n2 < this.m_length) {
                this.ku.add(new int[]{n2++, j});
            }
        }
    }

    protected boolean fi() {
        return false;
    }

    public zg_1 fj() {
        return zg_1.cdE;
    }

    public List fg() {
        return this.ku;
    }

    public String fh() {
        return dRO + "-" + (2 * this.dRR + 1) + "x" + this.m_length;
    }

    public ArrayList fm() {
        if (this.dRQ == null) {
            this.dRQ = new ArrayList(1);
            this.dRQ.add(this);
        }
        return this.dRQ;
    }

    public xq_2 fn() {
        if (this.m_length == 1) {
            return this.dRR == 0 ? xq_2.bXD : xq_2.bXF;
        }
        return this.dRR == 0 ? xq_2.bXE : xq_2.bXL;
    }
}

