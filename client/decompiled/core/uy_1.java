/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from uy
 */
public final class uy_1
extends azV
implements eb_0 {
    public final atu_0 aqD;
    private String aqE = null;

    public uy_1(lc_0 lc_02, atu_0 atu_02) {
        super(lc_02, (short)18);
        this.aqD = atu_02;
        this.aqD.a(new us_1(this));
    }

    public void a(qo_1 qo_12) {
        qo_12.c(this);
    }

    public String getClassName() {
        if (this.aqE == null) {
            aim_2 aim_22 = this.Dw();
            while (!(aim_22 instanceof el_1)) {
                aim_22 = aim_22.Dw();
            }
            this.aqE = ((el_1)aim_22).hT();
        }
        return this.aqE;
    }

    public String toString() {
        return this.getClassName();
    }
}

