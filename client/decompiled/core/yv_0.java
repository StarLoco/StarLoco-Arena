/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Yv
 */
public class yv_0
extends ahz_0 {
    private final aen_0 bg = new aen_0();
    private static final String aKD = "moveRange";
    private static final yv_0 caX = new yv_0();

    public static yv_0 amA() {
        return caX;
    }

    private yv_0() {
        super(aKD, mx_0.Kx);
        this.bg.cpI = 1000;
        this.bg.cpK = false;
        this.bg.cpH = true;
    }

    public void e(ee_2 ee_22) {
        int n2;
        this.clear();
        this.bg.cpJ = n2 = ee_22.a(Lr.bqz).value();
        mv_1 mv_12 = ee_22.Oc();
        aoq_0 aoq_02 = mv_12.gV();
        int n3 = ee_22.gn();
        int n4 = ee_22.go();
        if (n2 > 0) {
            this.a(aoq_02, n2, 1, n3 + 1, n4, n3, n4);
            this.a(aoq_02, n2, 1, n3 - 1, n4, n3, n4);
            this.a(aoq_02, n2, 1, n3, n4 + 1, n3, n4);
            this.a(aoq_02, n2, 1, n3, n4 - 1, n3, n4);
        }
    }

    private void a(aoq_0 aoq_02, int n2, int n3, int n4, int n5, int n6, int n7) {
        if (!aoq_02.bG(n4, n5)) {
            return;
        }
        short s = aoq_02.bL(n4, n5);
        if (s == Short.MIN_VALUE) {
            return;
        }
        if (aoq_02.bK(n4, n5) != null && n3 != 0) {
            return;
        }
        if (!this.x(n4, n5, s)) {
            this.y(n4, n5, s);
        }
        if (n3 < n2) {
            if (n4 + 1 != n6 || n5 != n7) {
                this.a(aoq_02, n2, n3 + 1, n4 + 1, n5, n4, n5);
            }
            if (n4 - 1 != n6 || n5 != n7) {
                this.a(aoq_02, n2, n3 + 1, n4 - 1, n5, n4, n5);
            }
            if (n4 != n6 || n5 + 1 != n7) {
                this.a(aoq_02, n2, n3 + 1, n4, n5 + 1, n4, n5);
            }
            if (n4 != n6 || n5 - 1 != n7) {
                this.a(aoq_02, n2, n3 + 1, n4, n5 - 1, n4, n5);
            }
        }
    }
}

