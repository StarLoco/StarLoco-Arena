/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from vM
 */
public class vm_2
extends akw_0 {
    public vm_2(int[] nArray, long l2, byte by) {
        super(nArray, l2, by);
    }

    public int a(et_2 et_22) {
        byte by;
        aiz_2 aiz_22 = bf_1.df().g((short)this.JI[0]);
        short[] sArray = et_22.kh().Gj();
        for (by = 0; by < sArray.length; ++by) {
            if ((bf_1.df().g(sArray[by]).getType() != aiz_22.getType() || aiz_22.getType() == 21) && aiz_22.getType() != 70) continue;
            return 0;
        }
        by = (byte)this.JI[1];
        if (by != -1) {
            by = (byte)(by + 1);
        }
        et_22.kh().b((short)this.JI[0], by);
        return 0;
    }

    public void c(et_2 et_22) {
        aiz_2 aiz_22 = bf_1.df().g((short)this.JI[0]);
        short[] sArray = et_22.kh().Gj();
        for (int j = 0; j < sArray.length; ++j) {
            if ((bf_1.df().g(sArray[j]).getType() != aiz_22.getType() || aiz_22.getType() == 21) && aiz_22.getType() != 70) continue;
            return;
        }
        et_22.kh().b((short)this.JI[0], (byte)this.JI[1]);
    }

    public void b(vy_1 vy_12) {
        aiz_2 aiz_22 = bf_1.df().g((short)this.JI[0]);
        short[] sArray = vy_12.Gj();
        for (int j = 0; j < sArray.length; ++j) {
            if ((bf_1.df().g(sArray[j]).getType() != aiz_22.getType() || aiz_22.getType() == 21) && aiz_22.getType() != 70) continue;
            return;
        }
        vy_12.b((short)this.JI[0], (byte)this.JI[1]);
    }

    public void d(et_2 et_22) {
        aiz_2 aiz_22 = bf_1.df().g((short)this.JI[0]);
        if (aiz_22.getType() == 70) {
            et_22.kh().b((short)this.JI[0], (byte)this.JI[1]);
        }
    }

    protected int aa() {
        return 2;
    }

    public boolean fc() {
        return true;
    }

    public int getType() {
        return AI.aHK.tI();
    }
}

