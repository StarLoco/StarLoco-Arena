/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from iD
 */
public class id_1
implements wb_2 {
    private wb_2 yz;

    public id_1() {
    }

    public id_1(wb_2 wb_22) {
        this.a(wb_22);
    }

    public void a(wb_2 wb_22) {
        if (this.yz != null) {
            throw new IllegalStateException("The Not ResourceSelector accepts a single nested ResourceSelector");
        }
        this.yz = wb_22;
    }

    public boolean a(iv_1 iv_12) {
        return !this.yz.a(iv_12);
    }
}

