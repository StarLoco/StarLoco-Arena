/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aic
 */
public class aic_1
extends akw_0 {
    public aic_1(int[] nArray, long l2, byte by) {
        super(nArray, l2, by);
    }

    public int a(et_2 et_22) {
        if (jr_0.VF().nextInt(100) + 1 <= this.JI[0]) {
            et_22.NF().dh(true);
        }
        return 0;
    }

    public void c(et_2 et_22) {
        short[] sArray = et_22.kh().Gj();
        for (int j = 0; j < sArray.length; ++j) {
            aiz_2 aiz_22 = bf_1.df().g(sArray[j]);
            if (!aiz_22.ayT() || jr_0.VF().nextInt(100) + 1 > this.JI[0]) continue;
            et_22.kh().bq(sArray[j]);
        }
    }

    public boolean fc() {
        return true;
    }

    protected int aa() {
        return 1;
    }

    public int getType() {
        return AI.aHG.tI();
    }
}

