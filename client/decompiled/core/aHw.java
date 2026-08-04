/*
 * Decompiled with CFR 0.152.
 */
public class aHw
extends hx {
    int dMD;

    public aHw(int n2) {
        this.dMD = n2;
    }

    public int a(Object object, Object object2, Object object3, Object object4) {
        if (object == null) {
            return -1;
        }
        if (!(object instanceof gn_0)) {
            return -1;
        }
        gn_0 gn_02 = (gn_0)object;
        if (gn_02.a(Lr.bqx).value() <= Math.round(gn_02.a(Lr.bqx).max() * this.dMD / 100)) {
            return 0;
        }
        return -2;
    }
}

