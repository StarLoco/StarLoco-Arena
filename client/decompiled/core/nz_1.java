/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Nz
 */
public class nz_1
extends akw_0 {
    public nz_1(int[] nArray, long l2, byte by) {
        super(nArray, l2, by);
    }

    public int a(et_2 et_22) {
        if (jr_0.VF().nextInt(100) + 1 <= this.JI[0]) {
            et_22.NF().di(true);
        }
        return 0;
    }

    public void c(et_2 et_22) {
        if (jr_0.VF().nextInt(100) + 1 <= this.JI[0]) {
            if (et_22.NB() == 2) {
                et_22.V((byte)0);
            } else if (et_22.NB() == 3) {
                et_22.V((byte)1);
            }
        }
    }

    public boolean fc() {
        return true;
    }

    public boolean aaF() {
        return true;
    }

    protected int aa() {
        return 1;
    }

    public int getType() {
        return AI.aHI.tI();
    }
}

