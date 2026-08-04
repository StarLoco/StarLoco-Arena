/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aBG
 */
public class abg_1
extends qs_0 {
    private arh_0 bfh;

    public abg_1(int n2, int n3, int n4, long l2, arh_0 arh_02) {
        super(n2, n3, n4);
        this.bC(l2);
        this.bfh = arh_02;
    }

    public long oS() {
        if (this.bfh != null) {
            abm_2 abm_22 = (abm_2)bd_1.Is().bb(this.mS());
            if (abm_22 != null) {
                abm_22.a(this.bfh, true, true);
            }
            return this.bfh.aEF() * 300;
        }
        return 0L;
    }

    protected void ax() {
    }
}

