/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ZE
 */
public class ze_1
extends akw_0 {
    public ze_1(int[] nArray, long l2, byte by) {
        super(nArray, l2, by);
    }

    public int a(et_2 et_22) {
        et_22.NF().jT(this.JI[0]);
        return 0;
    }

    public void c(et_2 et_22) {
        short[] sArray = et_22.kh().Gj();
        for (int j = 0; j < sArray.length; ++j) {
            aiz_2 aiz_22 = bf_1.df().g(sArray[j]);
            if (!aiz_22.ayU() || jr_0.VF().nextInt(100) + 1 > this.JI[0]) continue;
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
        return AI.aHA.tI();
    }
}

